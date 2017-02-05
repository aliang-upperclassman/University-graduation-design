package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Admin;
import com.myweb.domain.User;
import com.myweb.utils.DBManager;

public class UserDao {
	// 添加用户
	public int addUser(User user) {

		int id = 0;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into User (LOGINNAME,LOGINPSW,INTERESTS,JOB,CONCERN,EMAIL,TEL) values (?, ?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();
		 
		params.add(user.getLoginname());

		params.add(user.getLoginpsw());

		params.add(user.getInterests());
		
		params.add(user.getJob());
		
		params.add(user.getConcern());
		
		params.add(user.getEmail());
		
		params.add(user.getTel());
		 

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return id;
	}

	// 查询所有会员
	public List<Map<String, Object>> getUserList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select * from USER  order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	// 删除会员
	public void delUserById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from USER where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	 
	public User getUserById(String id) {

		User user = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from user where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			user = (User) db.findSimpleRefResult(sql, list,
					User.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return user;
	}

	// 获得会员
	public User getUserByLoginNameAndPassword(String loginname, String loginpsw) {

		User user =null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select * from USER where loginname=(?) and loginpsw=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(loginname);

		list.add(loginpsw);

		try {

			Map<String, Object> m = db.findSimpleResult(sql, list);

			if (m.size() > 0) {
				
				user=new User();
				
				user.setId(Integer.parseInt(m.get("ID").toString()));
				
				user.setLoginname(m.get("LOGINNAME").toString());

				user.setUsername(m.get("USERNAME").toString());

				System.out.println(m.get("LOGINNAME").toString());
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return user;
	}
		  
	 
	public boolean updateUser(User user) {

		boolean flag=false;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update user  set  loginpsw=?,interests=?,job=? ,concern=?,email=?,tel=?  where id="+String.valueOf(user.getId());

		List<Object> params = new ArrayList<Object>();
        
		params.add(user.getLoginpsw());		 
		 
		params.add(user.getInterests());
		
		params.add(user.getJob());
		
		params.add(user.getConcern());
		
		params.add(user.getEmail());
		
		params.add(user.getTel());
		
		try {
		    flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}
}
