package com.fiap.avaliacao.model;

import java.util.Date;

public class Tweet {

	private int id;
	private String user;
	private String text;
	private int retweets;
	private Date data;
	private int favoritos;

	public Tweet(int id, String user, String text, int retweets, Date data, int favoritos) {
		this.id = id;
		this.user = user;
		this.text = text;
		this.retweets = retweets;
		this.data = data;
		this.favoritos = favoritos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(int favoritos) {
		this.favoritos = favoritos;
	}

}