package com.easymall.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WebUtils {
    private WebUtils() {
    }

    //判断是否为空
    public static boolean isNull(String name) {
        return "".equals(name.trim()) || name == null;
    }

    //数据库连接池
    private static ComboPooledDataSource source = new ComboPooledDataSource();

    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    //数据库关闭连接
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }
    }

}
