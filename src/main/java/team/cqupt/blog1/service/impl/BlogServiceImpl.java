package team.cqupt.blog1.service.impl;

import team.cqupt.blog1.bean.Message;
import team.cqupt.blog1.dao.BlogDao;
import team.cqupt.blog1.dao.Impl.BlogDaoImpl;
import team.cqupt.blog1.service.BlogService;

import java.sql.SQLException;
import java.util.List;

public class BlogServiceImpl implements BlogService {
    private BlogDao blogDao =null;
    private static BlogService instance = null;
    public BlogServiceImpl() {
        blogDao = BlogDaoImpl.getInstance();
    }

    /**
     * 得到service的单例
     *
     * @return service
     */
    public static BlogService getInstance() {
        //双重校验锁
        if (instance == null) {
            synchronized (BlogServiceImpl.class) {
                if (instance == null) {
                    instance = new BlogServiceImpl();
                }
            }
        }
        return instance;
    }
    private List<Message> findContentChild(Message content) throws SQLException {
        //找该条评论的子节点 即pid为该条评论id的评论
        List<Message> list = blogDao.findMessagesByPid(content.getId());

        //遍历它的子节点 然后递归找每条评论下的评论 即节点的子节点
        for (Message message : list) {
            //递归找这条评论的节点 然后赋值
            List<Message> childList = findContentChild(message);
            message.setChildContent(childList);
        }
        return list;
    }

    /**
     * 查找所有的留言及其评论
     *
     * @return 留言的集合
     */
    @Override
    public List<Message> findAllMessages() throws SQLException {

        //先找到pid为0的所有留言 即留言板上所有无父节点的留言
        List<Message> list= blogDao.findMessagesByPid(0);

        //然后找每条留言的评论 并赋值
        for (Message message : list) {
            List<Message> childList = findContentChild(message);
            message.setChildContent(childList);
        }

        return list;
    }
    @Override
    public List<Message> findAllBlogs() throws SQLException {

        //先找到pid为0的所有留言 即留言板上所有无父节点的留言
        List<Message> list= blogDao.findMessagesByPid(0);

        return list;
    }

    /**
     * 将留言的集合组装成json
     *
     * @param messageList 留言的集合
     * @return
     */
    @Override
    public String messagesToJson(List<Message> messageList) {
        StringBuffer sb = new StringBuffer();

        //前面的共同的部分
        sb.append("{\"status\":10000,\"data\":[");

        //如果它有子节点
        if (messageList != null && messageList.size() != 0) {

            //这里的思想和上面的思想一样 深度优先遍历(DFS) 组装出来评论的json
            for (Message content : messageList) {
                sb.append(createJson(content));
                sb.append(",");
            }

            if (sb.charAt(sb.length() - 1) == ',') {
                sb.delete(sb.length() - 1, sb.length());
            }
        }
        sb.append("]}");

        return sb.toString();
    }


    @Override
    public boolean insertContent(Message message) throws SQLException {

        if (message.getText() != null&&message.getTitle()!=null) {

            blogDao.insertMessage(message);
            return true;

        }

        return false;

    }


    private String createJson(Message message) {

        StringBuffer sb = new StringBuffer();

        sb.append("{\"id\":").append(message.getId())
                .append(",\"title\":\"").append(message.getTitle())
                .append("\",\"username\":\"").append(message.getUsername())
                .append("\",\"texts\":\"").append(message.getText());


        if (sb.charAt(sb.length() - 1) == ',') {
            sb.delete(sb.length() - 1, sb.length());
        }

        sb.append("\"}");

        return sb.toString();
    }

    private String createsecretJson(Message message) {

        StringBuffer sb = new StringBuffer();

        sb.append("{\"id\":").append(message.getId())
                .append("\",\"texts\":\"").append(message.getText())
                .append("\"").append(",\"child\":[");


        List<Message> child = message.getChildContent();

        for (Message content : child) {

            String json = createsecretJson(content);

            sb.append(json).append(",");

        }

        if (sb.charAt(sb.length() - 1) == ',') {
            sb.delete(sb.length() - 1, sb.length());
        }

        sb.append("]}");

        return sb.toString();
    }


}
