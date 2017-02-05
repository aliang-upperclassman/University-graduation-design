package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import com.myweb.domain.GoodsType;
import com.myweb.utils.DBManager;

public class GoodsTypeDao {
	
	// 添加商品类型
	public void addGoodsType(GoodsType goodsType) {
	 
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into GOODSTYPE (TYPENAME,CREATEUSER) values (?, ?)";

		List<Object> params = new ArrayList<Object>(); 

		params.add(goodsType.getTypename());

		params.add(goodsType.getCreateuser());
	 	
		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		 
	}

	// 查询所有商品类型
	public List<Map<String, Object>> getGoodsTypeList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from GOODSTYPE  order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}
	
	 
	public GoodsType getGoodsTypeById(String id) {

		GoodsType goodsType = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from GoodsType where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			goodsType = (GoodsType) db.findSimpleRefResult(sql, list,
					GoodsType.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return goodsType;
	}

	// 删除商品类型
	public void delGoodsTypeById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from GOODSTYPE where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		
	}
	
	 
	public boolean updateGoodsType(GoodsType goodsType) {

		boolean flag=false;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update GOODSTYPE  set  TYPENAME=?  where id="+String.valueOf(goodsType.getId());

		List<Object> params = new ArrayList<Object>();
        
		params.add(goodsType.getTypename());		 
		 
	 	
		try {
		    flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}
}
