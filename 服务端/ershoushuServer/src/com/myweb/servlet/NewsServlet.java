package com.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.dao.GoodsDao;
import com.myweb.dao.GoodsTypeDao;
import com.myweb.dao.NewsDao;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.domain.News;

public class NewsServlet extends HttpServlet {

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

			addNews();

		} else if (action.equals("list")) {

			listNews();

		} else if (action.equals("edit")) {

			editNews();

		} else if (action.equals("editSave")) {

			editSaveNews();

		} else if (action.equals("delete")) {
             deleteNews();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 删除新闻
	public void deleteNews() throws ServletException, IOException {
		String id = _request.getParameter("id");

		NewsDao dao = new NewsDao();

		try {
			dao.delNewsById(id);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("NewsServlet?action=list").forward(_request,
				_response);
	}
	
	// 添加新闻
	public void addNews() throws ServletException, IOException {

		String title = _request.getParameter("title");

		String imgpath = _request.getParameter("imgpath");

		String content = _request.getParameter("content");

		News news = new News();

		news.setTitle(title);

		news.setImgpath(imgpath);

		news.setContent(content);

		news.setCreateuser("cc");

		Date date = new Date();

		news.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date));

		NewsDao dao = new NewsDao();

		try {
			dao.addNews(news);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}
		_request.getRequestDispatcher("/admin/news_Add.jsp").forward(_request,
				_response);

	}

	// 编辑新闻
	public void editNews() throws ServletException, IOException {

		String id = _request.getParameter("id");

		NewsDao dao = new NewsDao();

		News news = null;

		try {
			news = dao.getNewsById(id);

			_request.setAttribute("news", news);

		} catch (Exception ex) {

		}

		_request.getRequestDispatcher("/admin/news_Edit.jsp").forward(_request,
				_response);
	}

	// 保存编辑新闻
	public void editSaveNews() throws ServletException, IOException {
		
		String title = _request.getParameter("title");

		String imgpath = _request.getParameter("imgpath");

		String content = _request.getParameter("content");

		String id = _request.getParameter("id");

		NewsDao dao = new NewsDao();

		News news = null;

		try {
			news = dao.getNewsById(id);

			news.setTitle(title);

			news.setImgpath(imgpath);

			news.setContent(content);

			boolean flag = dao.updateNews(news);
			
			_request.setAttribute("news", news);

			if (flag) {
				_request.setAttribute("alertNote", "1");
			} else {
				_request.setAttribute("alertNote", "0");
			}
		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/admin/news_Edit.jsp").forward(_request,
				_response);
	}

	// 新闻列表
	public void listNews() throws ServletException, IOException {

		NewsDao dao = new NewsDao();

		try {

			List<Map<String, Object>> list = dao.getNewsList();

			_request.setAttribute("newsList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/news_List.jsp").forward(_request,
				_response);

	}

}
