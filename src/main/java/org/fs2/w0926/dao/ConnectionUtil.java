package org.fs2.w0926.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionUtil {
    INSTANCE;

    private DataSource dataSource;

    ConnectionUtil(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");

            HikariConfig config = new HikariConfig();
            //config.setJdbcUrl("jdbc:mariadb://192.168.30.25:3306/webdb");
            config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
            config.setUsername("webuser");
            config.setPassword("webuser");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(200);//커낵션풀의 수와 쓰레드풀의 수를 동일하게 맞추기 위하여 세팅

            dataSource  = new HikariDataSource(config);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public Connection getConnection() throws Exception{
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
