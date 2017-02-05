package com.myweb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.dao.GoodsDao;
import com.myweb.dao.GoodsTypeDao;
import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;

public class GoodsServlet extends HttpServlet {

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

		if (action.equals("addInit")) {

			addGoodsInit();

		} else if (action.equals("add")) {

			addGoods();

		} else if (action.equals("list")) {

			listGoods();

		} else if (action.equals("delete")) {

			delGoods();

		} else if (action.equals("edit")) {

			editGoods();

		} else if (action.equals("editSave")) {

			editSaveGoods();

		} else if (action.equals("send")) {

			sendGoods();

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// 初始化添加商品
	public void addGoodsInit() throws ServletException, IOException {

		GoodsTypeDao dao = new GoodsTypeDao();
		try {
			List<Map<String, Object>> list = dao.getGoodsTypeList();

			_request.setAttribute("goodsTypeList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		_request.getRequestDispatcher("/admin/goods_Add.jsp").forward(_request,
				_response);

	}

	// 添加商品
	public void addGoods() throws ServletException, IOException {

		String goodsname = _request.getParameter("goodsname");

		String goodsTypeId = _request.getParameter("goodstypeid");

		String webpath = _request.getParameter("webpath");

		String imgpath = _request.getParameter("imgpath");

		String author = _request.getParameter("author");

		String description = _request.getParameter("description");

		String num = _request.getParameter("num");

		String price = _request.getParameter("price");

		Goods goods = new Goods();

		goods.setName(goodsname);

		goods.setTypeid(goodsTypeId);

		goods.setWebpath(webpath);

		goods.setImgpath(imgpath);

		goods.setAuthor(author);

		goods.setState("0");

		goods.setDescription(description);

		goods.setNum(Integer.parseInt(num));

		goods.setPrice(Float.parseFloat(price));

		GoodsDao dao = new GoodsDao();

		GoodsTypeDao typedao = new GoodsTypeDao();

		try {
			dao.addGoods(goods);

			_request.setAttribute("alertNote", "1");

			List<Map<String, Object>> list = typedao.getGoodsTypeList();

			_request.setAttribute("goodsTypeList", list);

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}
		_request.getRequestDispatcher("/admin/goods_Add.jsp").forward(_request,
				_response);
	}

	// 编辑商品
	public void editGoods() throws ServletException, IOException {

		String id = _request.getParameter("id");

		GoodsDao dao = new GoodsDao();

		Goods goods = null;

		GoodsType goodsType = null;

		GoodsTypeDao typedao = new GoodsTypeDao();

		try {
			goods = dao.getGoodsById(id);

			_request.setAttribute("goods", goods);

			List<Map<String, Object>> list = typedao.getGoodsTypeList();

			_request.setAttribute("goodsTypeList", list);

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}
		_request.getRequestDispatcher("/admin/goods_Edit.jsp").forward(
				_request, _response);

	}

	public void editSaveGoods() throws ServletException, IOException {

		String id = _request.getParameter("id");

		GoodsDao dao = new GoodsDao();

		Goods goods = dao.getGoodsById(id);

		String goodsname = _request.getParameter("goodsname");

		String goodsTypeId = _request.getParameter("goodstypeid");

		String description = _request.getParameter("description");

		goods.setName(goodsname);

		goods.setTypeid(goodsTypeId);

		goods.setDescription(description);

		GoodsTypeDao typedao = new GoodsTypeDao();

		try {

			boolean flag = dao.updateGoods(goods);

			_request.setAttribute("goods", goods);

			List<Map<String, Object>> list = typedao.getGoodsTypeList();

			_request.setAttribute("goodsTypeList", list);

			if (flag) {

				_request.setAttribute("alertNote", "1");

			} else {

				_request.setAttribute("alertNote", "0");

			}
		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/admin/goods_Edit.jsp").forward(
				_request, _response);

	}

	public void sendGoods() throws ServletException, IOException {

		String state = _request.getParameter("state");

		String id = _request.getParameter("id");

		GoodsDao dao = new GoodsDao();

		Goods goods = null;

		try {
			goods = dao.getGoodsById(id);

			goods.setState(state);

			boolean flag = dao.updateGoods(goods);

			if (flag) {
				_request.setAttribute("alertNote", "1");
			} else {
				_request.setAttribute("alertNote", "0");
			}
			List<Map<String, Object>> list = dao.getGoodsList();

			_request.setAttribute("goodsList", list);

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");

		}

		_request.getRequestDispatcher("/admin/goods_List.jsp").forward(
				_request, _response);

	}

	public void listGoods() throws ServletException, IOException {
		GoodsDao dao = new GoodsDao();
		try {

			List<Map<String, Object>> list = dao.getGoodsList();

			_request.setAttribute("goodsList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		_request.getRequestDispatcher("/admin/goods_List.jsp").forward(
				_request, _response);
	}

	public void delGoods() throws ServletException, IOException {

		String id = _request.getParameter("id");

		GoodsDao dao = new GoodsDao();
		try {
			dao.delGoodsById(id);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
			ex.printStackTrace();
		}

		_request.getRequestDispatcher("GoodsServlet?action=list").forward(
				_request, _response);
	}
}
