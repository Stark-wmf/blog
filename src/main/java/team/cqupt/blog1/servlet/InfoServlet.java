package team.cqupt.blog1.servlet;


import net.sf.json.JSONArray;
import team.cqupt.blog1.bean.Message;
import team.cqupt.blog1.service.BlogService;
import team.cqupt.blog1.service.impl.BlogServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

public class InfoServlet extends HttpServlet {
    //博客
    private BlogService blogService =null;
    @Override
    public void init() {
        blogService= BlogServiceImpl.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Message> messageList = null;
        try {
            messageList = blogService.findAllBlogs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
     //   JSONArray json = JSONArray.fromObject(messageList);
        String res = blogService.messagesToJson(messageList);
     //   String res = json.toString();

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        resp.getOutputStream()
                )
        );
        writer.write(res);
        writer.flush();
        writer.close();
    }
}
