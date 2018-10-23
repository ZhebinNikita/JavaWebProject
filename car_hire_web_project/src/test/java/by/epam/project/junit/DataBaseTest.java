package by.epam.project.junit;

import by.epam.project.database.dao.impl.UserDao;
import by.epam.project.database.pool.ConnectionPool;
import by.epam.project.database.pool.ConnectionPoolException;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class DataBaseTest {

    private static final Logger LOG = LogManager.getRootLogger();
    private static final int DEFAULT_POOL_SIZE = 5;



    @Test
    public void testConnectionPool(){

        initPool();


        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        for(int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            connection = connectionPool.takeConnection();
            connectionPool.closeConnection(statement, connection);
        }


        disposePool();
    }


    @Test
    public void testUserDao() throws ProjectException {

        initPool();


        UserDao userDao = new UserDao();
        for(int i = 0; i < DEFAULT_POOL_SIZE * 10; i++) {
            userDao.insert(new User("testUserNumber"+i, ""));
            userDao.delete(new User("testUserNumber"+i, ""));
        }


        disposePool();
    }


    private void initPool(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
            LOG.info("Connection Pool is initialized!");
        }
        catch (ConnectionPoolException e) {
            LOG.error("Connection Pool is not initialized!", e);
        }
    }


    private void disposePool(){
        AbandonedConnectionCleanupThread.checkedShutdown();
        ConnectionPool.getInstance().dispose();
        LOG.info("Connection Pool is destroyed!");
    }

}
