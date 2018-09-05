package app.controller.servlets;

import app.model.ConnectionPool.ConnectionPool;
import app.model.ConnectionPool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    public final static Logger LOG = LogManager.getRootLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        /*ConnectionPool connectionPool = ConnectionPool.getInstance();

        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            LOG.error(e);
        }*/

        LOG.info("Connection Pool is Initialized!");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        //ConnectionPool connectionPool = ConnectionPool.getInstance();

        //connectionPool.dispose();

        LOG.info("Connection Pool is Destroyed!");
    }


}
