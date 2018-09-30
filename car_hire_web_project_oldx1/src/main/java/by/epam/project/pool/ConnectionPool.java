package by.epam.project.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

public final class ConnectionPool {

    private final static Logger LOG = LogManager.getRootLogger();

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;


    /** pool object will be initialized the first time "getInstance()" is called */
    private static class ConnectionPoolHolder {
        private final static ConnectionPool instance = new ConnectionPool();
    }


    public static ConnectionPool getInstance(){
        return ConnectionPoolHolder.instance;
    }


    private ConnectionPool() {
        MySqlDBResourceManager resourseManager = MySqlDBResourceManager.getInstance();

        this.driverName = resourseManager.getValue(MySqlDBParameter.DRIVER);
        this.url = resourseManager.getValue(MySqlDBParameter.URL);
        this.user = resourseManager.getValue(MySqlDBParameter.USER);
        this.password = resourseManager.getValue(MySqlDBParameter.PASSWORD);

        try {
            this.poolSize = Integer.parseInt(resourseManager.getValue(MySqlDBParameter.POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
    }


    public void initPoolData() throws ConnectionPoolException {

        try {

            DriverManager.registerDriver( new com.mysql.cj.jdbc.Driver() );

            givenAwayConQueue = new LinkedBlockingDeque<>(poolSize); //new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new LinkedBlockingDeque<>(poolSize); //new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                SafeConnection safeConnection = new SafeConnection(connection);
                connectionQueue.add(safeConnection);
            }
        }
        catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in pool", e);
        }

    }


    public void dispose() {
        try {
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            LOG.error("Error closing the connection.", e);
        }
        try{
            DriverManager.deregisterDriver( new com.mysql.cj.jdbc.Driver() );
        } catch (SQLException e){
            LOG.error("Driver has not been registered.", e);
        }
    }


    public Connection takeConnection()  {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            LOG.error("Error taking the connection.", e);
        }
        return connection;
    }


    public void closeConnection(ResultSet rs, Statement st, Connection con) {
        try {	rs.close();
        } catch (SQLException e) {
            LOG.error("ResultSet isn't closed.", e);
        }
        try {	st.close();
        } catch (SQLException e) {
            LOG.error("Statement isn't closed.", e);
        }
        try {	con.close();
        } catch (SQLException e) {
            LOG.error("Connection isn't return to the pool.", e);
        }

    }


    public void closeConnection(Statement st, Connection con) {
        try {	st.close();
        } catch (SQLException e) {
            LOG.error("Statement isn't closed.", e);
        }
        try {	con.close();
        } catch (SQLException e) {
            LOG.error("Connection isn't return to the pool.", e);
        }
    }



    /** тут реализовать с циклом ФОР и брать конекшн из очереди take()-ом !!!!!!!!!!!!*/
    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((SafeConnection) connection).reallyClose();
        }
    }




    private class SafeConnection implements Connection {

        private Connection connection;

        SafeConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }


        void reallyClose() throws SQLException {
            connection.close();
        }


        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error allocating connection in the pool.");
            }
        }



        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }


        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        public void commit() throws SQLException {
            connection.commit();
        }

        public void rollback() throws SQLException {
            connection.rollback();
        }


        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }


    }


}
