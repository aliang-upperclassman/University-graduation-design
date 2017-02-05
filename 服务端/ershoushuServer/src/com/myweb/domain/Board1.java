package com.myweb.domain;

public class Board1 {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String userid;
	
	private String username;
	
	private String createtime;
	
	private String title;
	
	private String content;
	
	private String imgpath;
	
	private int ticketnum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTicketnum() {
		return ticketnum;
	}

	public void setTicketnum(int ticketnum) {
		this.ticketnum = ticketnum;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	 
}
