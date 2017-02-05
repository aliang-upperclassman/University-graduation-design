package com.myweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.myweb.dao.NewsreplyDao;
import com.myweb.dao.NewsDao;
 
import com.myweb.domain.Newsreply;
import com.myweb.domain.News;
 

public class NewsreplyService extends HttpServlet {

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

					
					listNewsreply(jsonObj.getString("newsid"));

				} else if (action.equals("add")) {

					addNewsreply(jsonObj.getString("newsreply"));

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

	public void listNewsreply(String newsid) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			NewsreplyDao newsreplyDao = new NewsreplyDao();

			List<Map<String, Object>> newsreplyList = new ArrayList<Map<String, Object>>();

			newsreplyList = newsreplyDao.getNewsreplyListByNewsIdService(newsid);

			if (newsreplyList != null) {

				String json = JSONArray.toJSONString(newsreplyList);

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

	public void addNewsreply(String newsreply) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			Newsreply newsreplyObj = JSONObject.parseObject(newsreply, Newsreply.class);

			NewsreplyDao newsreplyDao = new NewsreplyDao();

			newsreplyDao.addNewsreply(newsreplyObj);

			result = "ok";

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
