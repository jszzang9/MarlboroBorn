package com.marlboro.core.model.dto;

public class Article {
	private String primaryKey;
	private String marlboro_Id;
	private String marlboro_Pw;
	private String marlboro_Content;
	
	public Article() {
	}
	
	public Article(String marlboro_Id, String marlboro_Pw, String marlboro_Content) {
		this.marlboro_Id = marlboro_Id;
		this.marlboro_Pw = marlboro_Pw;
		this.marlboro_Content = marlboro_Content;
	}
	
	public String getMarlboro_Id() {
		return marlboro_Id;
	}

	public void setMarlboro_Id(String marlboro_Id) {
		this.marlboro_Id = marlboro_Id;
	}

	public String getMarlboro_Pw() {
		return marlboro_Pw;
	}

	public void setMarlboro_Pw(String marlboro_Pw) {
		this.marlboro_Pw = marlboro_Pw;
	}

	public String getMarlboro_Content() {
		return marlboro_Content;
	}

	public void setMarlboro_Content(String marlboro_Content) {
		this.marlboro_Content = marlboro_Content;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
}
