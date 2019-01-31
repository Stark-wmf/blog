package team.cqupt.blog1.servlet;

import team.cqupt.blog1.dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
//登陆
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码格式为utf-8
        resp.setCharacterEncoding("utf-8");//设置编码格式为utf-8
        String username = req.getParameter("username");
        if (username == null) {
            System.out.println("username null");
        }
        String password = req.getParameter("password");//从jsp中获取password

        Dao.registe(username, password);
        if (Dao.checkLogin(username, password)) { //dao层中判断，如果为true，跳转到欢迎界面
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            resp.sendRedirect("http://localhost:8080/blog1_war/info.html");;
        } else {   //如果为false，跳转到登录界面，并返回错误信息.
            resp.getWriter().write("登陆失败，用户名或密码错误");


        }

    }
}
