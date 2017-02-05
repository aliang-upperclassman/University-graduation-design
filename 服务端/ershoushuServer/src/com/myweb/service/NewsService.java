package com.myweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myweb.dao.AdminDao;
import com.myweb.dao.NewsDao;
import com.myweb.dao.UserDao;
import com.myweb.domain.Admin;
import com.myweb.domain.News;
import com.myweb.domain.User;

public class NewsService extends HttpServlet {

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		try {
			/* 读取数据 */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) _request.getInputStream(), "utf-8"));

			StringBuffer sb = new StringBuffer("");

			String temp;

			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}

			br.close();
 
			if (!sb.toString().equals("")) {

				JSONObject jsonObj = JSONObject.parseObject(sb.toString());

				action = jsonObj.getString("action");

				if (action.equals("list")) {

					listNews();

				} else if (action.equals("view")) {

					viewNews(jsonObj.getString("id"));

				}
			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 新闻列表
	public void listNews() throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			NewsDao newsDao = new NewsDao();

			List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();

			newsList = newsDao.getNewsList();

			if (newsList != null) {

				String json = JSONArray.toJSONString(newsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

	 
			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}

	}

	// 查看新闻
	public void viewNews(String id) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			NewsDao newDAO = new NewsDao();

			News news = new News();

			news = newDAO.getNewsById(id);

			if (news != null) {
				result = JSONObject.toJSONString(news);
			}

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");
 
			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

}
