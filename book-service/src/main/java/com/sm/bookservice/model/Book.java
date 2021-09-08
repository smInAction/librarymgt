package com.sm.bookservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String author;
	private int price;
	
	public Book() {
	}
	
	public Book(String title, String author, int price){
		this.title = title;
		this.author = author;
		this.price = price;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}
	@Override
	public String toString() {
		String str = new StringBuilder("[").append(title).append(", ")
				.append(author).append(", ").append(price).append("]").toString();
		return str;
	}
}
