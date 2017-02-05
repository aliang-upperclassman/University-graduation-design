package com.myweb.domain;

public class BoardReply {
	
	private static final long serialVersionUID = 1L;

	private int replyid;
	
	private String boardid;
	
	private String replyuserid;
	
	private String replyusername;
	
	private String replytime; 
	
	private String replycontent;

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public String getBoardid() {
		return boardid;
	}

	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}

	public String getReplyuserid() {
		return replyuserid;
	}

	public void setReplyuserid(String replyuserid) {
		this.replyuserid = replyuserid;
	}

	public String getReplyusername() {
		return replyusername;
	}

	public void setReplyusername(String replyusername) {
		this.replyusername = replyusername;
	}

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	 
}
