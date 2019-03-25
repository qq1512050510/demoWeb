package com.winter.web.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/7/18.
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "192.168.10.25,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
                @WebInitParam(name = "deny", value = "192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
                @WebInitParam(name = "loginPassword", value = "123"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能)
        }
)
public class DruidStatViewServlet extends StatViewServlet{
	private static final long serialVersionUID = -5767625680763237936L;
	 
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
		/*String roseFrom = this.getInitParameter("allow");
		PrintWriter out = resp.getWriter();*/
		// out.println("<a href=\"javascript:void(0)\" onclick=\"if(true){alert(true)}else{alert(\"dont know\")}\">" + roseFrom + "</a>");
 
	}

}