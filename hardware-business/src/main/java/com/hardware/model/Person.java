package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "person")
@org.hibernate.annotations.Table(appliesTo = "person", comment = "房源信息模型")
public class Person extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String id,Long houseCount){  
	     setId(id);
	     this.houseCount=houseCount;
	   } 
	
	
	/**
	 * 房源所属的安裝申請集合
	 */
	@OneToMany(mappedBy = "person", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Book>  bookList ;
	
	
	private Long houseCount;
	
	
	
	public Long getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(Long houseCount) {
		this.houseCount = houseCount;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
}
