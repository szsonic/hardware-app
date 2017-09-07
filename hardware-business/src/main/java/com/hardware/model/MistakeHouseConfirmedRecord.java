package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "mistake_house_confirmed_record")
@org.hibernate.annotations.Table(appliesTo = "mistake_house_confirmed_record", comment = "虚假房源信息")
public class MistakeHouseConfirmedRecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	public MistakeHouseConfirmedRecord(){
		super();
	}
	
	//用户信息
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	//房屋信息
	@OneToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "houseId")
	private House house;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	

}
