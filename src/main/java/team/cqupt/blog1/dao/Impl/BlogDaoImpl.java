package team.cqupt.blog1.dao.Impl;

import team.cqupt.blog1.bean.Message;
import team.cqupt.blog1.dao.BlogDao;
import team.cqupt.blog1.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author win10
 */
public class BlogDaoImpl implements BlogDao {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blog?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USER = "root";
    private static final String PASS = "191513";
    private static BlogDao instance = null;
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static BlogDao getInstance() {
        //双重校验锁 防止高并发的情况下new出来多个message_boardDao的实例
        if (instance == null) {
            synchronized (BlogDao.class) {
                if (instance == null) {
                    instance = new BlogDaoImpl();
                }
            }
        }
        return instance;
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


    @Override
    public void insertMessage(Message message) {
        Connection con = DBHelper.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO message(username,title,texts,pid,type) VALUE(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, message.getUsername());
            pstmt.setString(2, message.getTitle());
            pstmt.setString(3, message.getText());
            pstmt.setInt(4, message.getPid());
            pstmt.setInt(5, message.getType());
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

    @Override
    public List<Message> findMessagesByPid(int pid) {
        Connection con = DBHelper.getConnection();
        String sql = "SELECT * FROM message WHERE pid = ? ";
        PreparedStatement pstmt = null;
        ResultSet res = null;
        List<Message> list = new ArrayList<Message>();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, pid);
            res = pstmt.executeQuery();
            while (res.next()) {
                Message message = new Message();
                message.setId(res.getInt("id"));
                message.setPid(res.getInt("pid"));
                message.setTitle(res.getString("title"));
                message.setText(res.getString("texts"));
                message.setUsername(res.getString("username"));
                message.setType(res.getInt("type"));
                list.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(res, pstmt, con);
        }
        return list;
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
