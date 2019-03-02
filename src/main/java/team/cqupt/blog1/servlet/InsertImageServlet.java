package team.cqupt.blog1.servlet;

import team.cqupt.blog1.bean.Agree;
import team.cqupt.blog1.dao.ImageDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
@WebServlet("/Image")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username =(String) session.getAttribute("username");
        String targetpath = req.getParameter("targetpath");
        String path = req.getParameter("path");
        ImageDao.readDB2Image(targetpath);
        ImageDao.readImage2DB(path);

    }

}
