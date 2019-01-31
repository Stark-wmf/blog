package team.cqupt.blog1.servlet;

import team.cqupt.blog1.dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

@WebServlet("/registe")
//注册
public class RegisteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintStream out = new PrintStream(resp.getOutputStream());
        resp.setContentType("text/html;charset = utf-8");
        out.print("请正常打开网页！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码格式为utf-8
        resp.setCharacterEncoding("utf-8");//设置编码格式为utf-8
        String username=req.getParameter("username");//从注册界面获得username
        String password=req.getParameter("password");//从注册界面获得password
        PrintWriter pw = resp.getWriter();
        if(Dao.CheckRegiste(username)){
        pw.print("前台传来了参数：username ="+username+",password = "+password);
        Dao.registe(username, password);
        resp.sendRedirect("/http://localhost:8080/blog1_war/login.html");}
        pw.print("用户名不可用");
        pw.flush();
        pw.close();

    }
}
