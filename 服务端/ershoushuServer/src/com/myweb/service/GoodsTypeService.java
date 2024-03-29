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
import com.myweb.dao.GoodsTypeDao;
import com.myweb.dao.NewsDao;

public class GoodsTypeService extends HttpServlet {
	
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

					listGoodsType();

				} else if (action.equals("view")) {

					//viewNews(jsonObj.getString("id"));

				}
			  }
			} catch (Exception e) {

				System.out.print(e.getMessage());

			}
	}

	// 商品类型列表
	public void listGoodsType() throws ServletException, IOException {

	String result = "";// 返回结果集
		
		try {

			GoodsTypeDao goodsTypeDao = new GoodsTypeDao();

			List<Map<String, Object>> goodsTypeList = new ArrayList<Map<String, Object>>();

			goodsTypeList = goodsTypeDao.getGoodsTypeList();

			if (goodsTypeList != null) {

				String json=JSONArray.toJSONString(goodsTypeList);

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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
