package com.myweb.domain;

public class UserOrderDetail {

	private static final long serialVersionUID = 1L;

	private int id;

	private String orderid;

	private String goodsid;

	private String goodsname;

	private String imgpath;

	private int buynum;

	private float sumprice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public int getBuynum() {
		return buynum;
	}

	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	public float getSumprice() {
		return sumprice;
	}

	public void setSumprice(float sumprice) {
		this.sumprice = sumprice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
