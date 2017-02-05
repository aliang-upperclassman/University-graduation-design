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
import com.myweb.dao.GoodsDao;
import com.myweb.dao.GoodsTypeDao;
import com.myweb.dao.NoteDao;
import com.myweb.domain.Goods;
import com.myweb.domain.Note;

public class GoodsService extends HttpServlet {

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

					listGoods(jsonObj.getString("goodstype"));

				} else if (action.equals("myList")) {

					myListGoods(jsonObj.getString("userid"));

				} else if (action.equals("searchlist")) {

					searchListGoods(jsonObj.getString("title"));

				} else if (action.equals("view")) {

					viewGoods(jsonObj.getString("id"));

				} else if (action.equals("editsave")) {

					editsaveGoods(jsonObj.getString("goods"));

				} else if (action.equals("add")) {

					addGoods(jsonObj.getString("goods"));
				} else if (action.equals("delete")) {

					deleteGoods(jsonObj.getString("ids"));
				}
			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		}

	}

	public void editsaveGoods(String goods) throws ServletException,
			IOException {

		String result = "";// 返回结果集

		try {

			Goods goodsObj = JSONObject.parseObject(goods, Goods.class);

			GoodsDao goodsDAO = new GoodsDao();

			goodsDAO.updateGoods1(goodsObj);

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

	public void deleteGoods(String ids) throws ServletException, IOException {

		String result = "";// 返回结果集

		String[] _ids = ids.toString().split(",");

		GoodsDao goodsDAO = new GoodsDao();

		try {
			for (int i = 0; i < _ids.length; i++) {
				goodsDAO.delGoodsById(_ids[i]);
			}

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

	public void addGoods(String goods) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			Goods goodsObj = JSONObject.parseObject(goods, Goods.class);

			GoodsDao goodsDAO = new GoodsDao();

			goodsDAO.addGoods(goodsObj);

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

	// 查看
	public void viewGoods(String goodsId) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {
			GoodsDao goodsDAO = new GoodsDao();

			Goods goods = new Goods();

			goods = goodsDAO.getGoodsById(goodsId);

			if (goods != null) {

				result = JSONObject.toJSONString(goods);
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

	public void searchListGoods(String title) throws ServletException,
			IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsListByTitle(title);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

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

	public void searchlistGoods(String goodstype) throws ServletException,
			IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsListByType(goodstype);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

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

	public void myListGoods(String userid) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getMyList(userid);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

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

	public void listGoods(String goodstype) throws ServletException,
			IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsListByType(goodstype);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

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
