package org.zerock.dao;

import lombok.Cleanup;
import org.fs2.w0926.dao.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectTests {
    @Test
    public void testConnect() throws Exception{
        //드라이버 로딩
        Class.forName("org.mariadb.jdbc.Driver");
        //커넥션
        Connection connection = DriverManager.getConnection("jdbc:mariadb://192.168.30.25:3306/webdb", "webuser", "webuser");
        String query = "select now()";
        //쿼리
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); //메타데이터 건너 뛰기

        String str = resultSet.getString(1);

        System.out.println(str);
        //close()
    }

    @Test
    public void testConnect100() throws Exception{

        long start = System.currentTimeMillis();


        for (int i = 0; i < 100; i++){
            //커넥션
//            @Cleanup Connection connection = DriverManager.getConnection("jdbc:mariadb://192.168.30.25:3306/webdb", "webuser", "webuser");
            @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
            String query = "select now()";
            //쿼리
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next(); //메타데이터 건너 뛰기

            String str = resultSet.getString(1);

//            System.out.println(str);
            //close()
        }

        long end = System.currentTimeMillis();

        long gap = end - start;

        System.out.println("TIME : " + gap);
    }

    @Test
    public void testConnectDS() throws Exception{
        //커넥션
        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        String query = "select now()";
        //쿼리
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); //메타데이터 건너 뛰기

        String str = resultSet.getString(1);

        System.out.println(str);
        //close()
    }
}
