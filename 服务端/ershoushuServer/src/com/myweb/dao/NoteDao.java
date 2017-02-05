package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.News;
import com.myweb.domain.Note;
import com.myweb.utils.DBManager;

public class NoteDao {
	// 添加笔记
	public void addNote(Note note) {
	
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into Note (TITLE,CONTENT,CREATEUSER,CREATEUSERID) values ( ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(note.getTitle());
		
		params.add(note.getContent());

		params.add(note.getCreateuser());		 

		params.add(note.getCreateuserid());
		
		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		 
	}

	// 修改新闻
	public boolean updateNote(Note note) {

		boolean flag=false;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update Note  set  TITLE=?,CONTENT=? where id="+String.valueOf(note.getId());

		List<Object> params = new ArrayList<Object>();
        
		params.add(note.getTitle());
		
		params.add(note.getContent());

		try {
		    flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	
	// 查询所有笔记
	public List<Map<String, Object>> getNoteList(String searchtitle) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 ="";
		
		if(searchtitle.equals(""))
		{
		   sql2= "select * from note  order by CREATETIME desc";
		}
		else
		{
			sql2= "select * from note where title like '%"+searchtitle+"%' order by CREATETIME desc";
		}
		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	// 删除笔记
	public void delNewsById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from note where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// 查询笔记
	public Note getNotesById(String id) {

		Note note = null;
		
		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from note where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			note = (Note) db.findSimpleRefResult(sql, list, Note.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return note;
	}
}
