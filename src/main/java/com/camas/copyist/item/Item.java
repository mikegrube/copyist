package com.camas.copyist.item;

import javax.persistence.*;

@Entity
public class Item {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private int position;

	@Column(columnDefinition="CLOB NOT NULL")
	@Lob
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "(" + position + ") " + name;
	}
}
