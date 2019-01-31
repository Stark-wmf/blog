package team.cqupt.blog1.servlet;

import team.cqupt.blog1.bean.Message;
import team.cqupt.blog1.service.BlogService;
import team.cqupt.blog1.service.impl.BlogServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
@WebServlet("/send")
//发送消息
public class SendServlet extends HttpServlet {
    private BlogService blogService=null;
    //失败
    private static final String ERROR = "{\"status\":\"10001\"}";
    //成功
    private static final String OK = "{\"status\":\"10000\"}";


    @Override
    public void init() {
        blogService= BlogServiceImpl.getInstance();
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");//设置编码格式为utf-8
        HttpSession session = request.getSession();
//        String username=user.getUsername();
//        String username = request.getParameter("username");
        String text = request.getParameter("text");
        String title = request.getParameter("title");
        String username = (String) session.getAttribute("username");
        int pid = Integer.parseInt(request.getParameter("pid"));
        int type = Integer.parseInt(request.getParameter("type"));


        Message message = new Message(username,title, text, pid, type);

        String res = ERROR;

        //如果插入成功 就回复成功
        try {
            if (blogService.insertContent(message)) {
                res = OK;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        response.getOutputStream()
                )
        );

        writer.write(res);
        writer.flush();
        writer.close();
    }
}
