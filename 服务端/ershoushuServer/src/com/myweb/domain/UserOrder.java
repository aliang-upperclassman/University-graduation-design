package com.myweb.domain;

public class UserOrder {

	private static final long serialVersionUID = 1L;

	private int id;

	private String ordernum;

	private int userid;
	
	private String recusername;

	private String address;

	
	private float sumprice;

	private String sendtype;

	private String state;

	private String tel;

	private String createtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getRecusername() {
		return recusername;
	}

	public void setRecusername(String recusername) {
		this.recusername = recusername;
	}

	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getSumprice() {
		return sumprice;
	}

	public void setSumprice(float sumprice) {
		this.sumprice = sumprice;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
