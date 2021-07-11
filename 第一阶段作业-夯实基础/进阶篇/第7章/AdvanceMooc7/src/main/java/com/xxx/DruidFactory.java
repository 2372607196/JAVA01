package com.xxx;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DruidFactory {
    private static DruidDataSource dataSource = null;

    private static void init(){
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("wanghao456..");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useSLL=false&serverTimezone=UTC");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
    }
    public static Connection getConnection() throws SQLException{
        if(dataSource == null){
            init();
        }
        return dataSource.getConnection();
    }
}
