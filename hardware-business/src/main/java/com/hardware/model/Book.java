package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;


@Entity
@Table(name = "book")
@org.hibernate.annotations.Table(appliesTo = "book", comment = "房源信息模型")
public class Book extends BaseModel {
	private static final long serialVersionUID = 1L;

	
	

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "personId")
	private Person person;
	
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	
}
