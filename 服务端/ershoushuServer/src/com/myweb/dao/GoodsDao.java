package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.utils.DBManager;

public class GoodsDao {

	public void addGoods(Goods goods) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into goods (NAME,AUTHOR,TYPEID,DESCRIPTION,STATE,IMGPATH,WEBPATH,NUM,PRICE,CREATEUSER) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getAuthor());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		params.add(goods.getState());

		params.add(goods.getImgpath());

		params.add(goods.getWebpath());

		params.add(goods.getNum());

		params.add(goods.getPrice());

		params.add(goods.getCreateuser());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getGoodsList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t1.*,t2.TYPENAME as GOODSTYPENAME  from goods t1,goodstype t2 where t1.TYPEID=t2.ID order by  t1.CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getMyList(String userid) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t1.*,t2.TYPENAME as GOODSTYPENAME  from goods t1,goodstype t2 where t1.TYPEID=t2.ID and t1.CREATEUSER='"
				+ userid + "' order by  t1.CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getGoodsListByTitle(String title) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t1.*,t2.TYPENAME as GOODSTYPENAME ,t3.LOGINNAME as createusername from goods t1,goodstype t2,`user` t3 where t1.CREATEUSER=t3.ID and t1.TYPEID=t2.ID and t1.state='1' and t1.name like'%"
				+ title + "%' order by  t1.CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getGoodsListByType(String goodsTypeId) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select t1.*,t2.TYPENAME as GOODSTYPENAME ,t3.LOGINNAME as createusername from goods t1,goodstype t2,`user` t3 where t1.CREATEUSER=t3.ID and t1.TYPEID=t2.ID and t1.state='1' and t1.typeid='"
				+ goodsTypeId + "' order by  t1.CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public Goods getGoodsById(String id) {

		Goods goods = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select  t1.id,t1.name,t1.author,t1.typeid,t1.description,t1.state,t1.imgpath,t1.webpath,t1.createtime,t2.TYPENAME as typename,t1.num,t1.price,t3.LOGINNAME as createusername   from goods t1,goodstype t2 ,`user` t3 where t1.CREATEUSER=t3.ID and t1.TYPEID=t2.ID and t1.id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			goods = (Goods) db.findSimpleRefResult(sql, list, Goods.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return goods;
	}

	public void delGoodsById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from goods where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean updateGoods(Goods goods) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update GOODS  set  NAME=?,TYPEID=?,DESCRIPTION=?,STATE=? where id="
				+ String.valueOf(goods.getId());

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		params.add(goods.getState());

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateGoods1(Goods goods) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update GOODS  set  NAME=?,TYPEID=?,DESCRIPTION=? where id="
				+ String.valueOf(goods.getId());

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}
}
