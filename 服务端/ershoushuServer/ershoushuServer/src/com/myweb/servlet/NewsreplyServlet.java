package com.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.dao.NewsreplyDao;
import com.myweb.dao.NewsDao;
 
import com.myweb.domain.Admin;
import com.myweb.domain.Newsreply;
import com.myweb.domain.News;
import com.myweb.domain.User;

public class NewsreplyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		action = request.getParameter("action").toString();

		if (action.equals("add")) {

			addNewsreply();

		} else if (action.equals("list")) {

			listNewsreply();

		}  else if (action.equals("shenhe")) {

			shenpiNewsreply();

		} else if (action.equals("delete")) {

			delNewsreply();

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 添加留言
	public void addNewsreply() throws ServletException, IOException {

		String username = "";

		Newsreply newsreply = new Newsreply();

		if (_session.getAttribute("logintype").equals("0")) {
			User user = (User) _session.getAttribute("user");

			username = user.getLoginname();
		} else if (_session.getAttribute("logintype").equals("1")) {
			Admin admin = (Admin) _session.getAttribute("admin");

			username = admin.getLoginname();
		}

		String content = _request.getParameter("content");

		newsreply.setContent(content);

		newsreply.setUsername(username);

		NewsreplyDao dao = new NewsreplyDao();
		try {
			dao.addNewsreply(newsreply);

			List<Map<String, Object>> list = dao.getNewsreplyListByNewsId(newsreply
					.getNewsid());

			_request.setAttribute("newsreplyList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/newsreply_List.jsp").forward(
				_request, _response);
	}

	// 留言列表
	public void listNewsreply() throws ServletException, IOException {
		NewsreplyDao dao = new NewsreplyDao();
		try {

			String id = _request.getParameter("id");

			List<Map<String, Object>> list = dao.getNewsreplyListByNewsId(id);

			_request.setAttribute("newsreplyList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/newsreply_List.jsp").forward(
				_request, _response);
	}

	// 删除留言
	public void delNewsreply() throws ServletException, IOException {

		String id = _request.getParameter("id");

		String newsid = _request.getParameter("newsid");

		NewsreplyDao dao = new NewsreplyDao();
		try {
			dao.delNewsreplyById(id);

			List<Map<String, Object>> list = dao.getNewsreplyListByNewsId(newsid);

			_request.setAttribute("newsreplyList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/newsreply_List.jsp").forward(
				_request, _response);
	}

	public void shenpiNewsreply() throws ServletException, IOException {

		String id = _request.getParameter("id");

		String newsid = _request.getParameter("newsid");

		NewsreplyDao dao = new NewsreplyDao();

		try {

			boolean flag = dao.shengpi(id, "已审核");

			List<Map<String, Object>> list = dao.getNewsreplyListByNewsId(newsid);

			_request.setAttribute("newsreplyList", list);

			if (flag) {
				_request.setAttribute("alertNote", "1");
			} else {
				_request.setAttribute("alertNote", "0");
			}
		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/admin/newsreply_List.jsp").forward(
				_request, _response);
	}
}
