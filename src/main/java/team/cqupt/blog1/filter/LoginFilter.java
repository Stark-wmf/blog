package team.cqupt.blog1.filter;

import team.cqupt.blog1.dao.Dao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName="LoginFilter",urlPatterns={"/send","/agree","/logout"})
//判断是否登陆，再执行的过滤器
public  class LoginFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = ((HttpServletRequest) req).getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        if (Dao.checkLogin(username, password)) { //dao层中判断，如果为true，跳转到欢迎界面

            chain.doFilter(request, response);
        } else {   //如果为false，跳转到登录界面，并返回错误信息.
            resp.getWriter().write("you haven't login,please login");

        }
    }
}