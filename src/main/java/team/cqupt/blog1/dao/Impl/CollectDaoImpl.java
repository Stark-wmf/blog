package team.cqupt.blog1.dao.Impl;

import team.cqupt.blog1.bean.Collect;
import team.cqupt.blog1.bean.Comment;
import team.cqupt.blog1.dao.BlogDao;
import team.cqupt.blog1.dao.CommentDao;
import team.cqupt.blog1.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectDao {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blog?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USER = "root";
    private static final String PASS = "191513";
    private static CommentDao instance = null;
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return con;
    }


    public void collectMessage(Collect collect) {
        Connection con = DBHelper.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO collect(blogid,username) VALUE(?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,collect.getBlogid());
            pstmt.setString(2, collect.getUsername());
            pstmt.execute();
        } catch (SQLException e) {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }






    public static void close(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

