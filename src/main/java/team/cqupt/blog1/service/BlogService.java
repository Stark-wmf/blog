package team.cqupt.blog1.service;

import team.cqupt.blog1.bean.Message;

import java.sql.SQLException;
import java.util.List;

public interface BlogService {

    List<Message> findAllMessages() throws SQLException;
    List<Message> findAllBlogs() throws SQLException;

    String messagesToJson(List<Message> messageList);


    boolean insertContent(Message message) throws SQLException;


}
