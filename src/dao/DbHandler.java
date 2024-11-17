package dao;

import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    private  String URL;
    private  String userName;
    private  String password;
    private int  poolSize;
    private List<Connection> connectionPool;
    private List<Connection> useConnectionList=new ArrayList<>();


    public DbHandler(String URL, String userName, String password, int poolSize) throws Exception {
        this.URL = URL;
        this.userName = userName;
        this.password = password;
        this.poolSize = poolSize;

        // Initialize the connection pool
        connectionPool = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createConnection());
        }
    }


    public synchronized static DbHandler create(String URL, String userName, String password, int poolSize) throws Exception {
        return new DbHandler(URL, userName, password,poolSize);
    }

    public synchronized Connection createConnection() throws Exception {
        try {
            Connection connection=null;
            connection=DriverManager.getConnection(URL, userName, password);
            return connection;
        }catch (Exception  var){
            var.printStackTrace();
          throw new Exception();

        }
    }

    public synchronized Connection getConnection() throws Exception {
        try {
            if (!connectionPool.isEmpty()) {
                Connection connection = connectionPool.removeLast();
                useConnectionList.add(connection);
                return connection;
            } else {
                throw new SQLException();
            }
        }catch (Exception exception){
            throw new Exception();
        }
    }

    public synchronized void releaseConnectionBackToPool(Connection connection){
        if(useConnectionList.remove(connection)){
            connectionPool.add(connection);
        }
    }

    public synchronized void closeAllConnection() throws SQLException {
        for (Connection connection : connectionPool) {
            connection.close();
        }
        for (Connection connection : useConnectionList) {
            connection.close();
        }
    }

    public int getPoolSize(){
        return connectionPool.size();
    }

}
