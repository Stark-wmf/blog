package team.cqupt.blog1.dao;

import team.cqupt.blog1.bean.Message;

import java.sql.SQLException;
import java.util.List;

public interface BlogDao {
    List<Message> findMessagesByPid(int pid) throws SQLException;


    void insertMessage(Message message) throws SQLException;
}
