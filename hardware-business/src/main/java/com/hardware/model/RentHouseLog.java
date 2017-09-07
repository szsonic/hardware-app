package com.hardware.model;

import com.hardware.enums.RentHouseAction;
import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "rent_house_log")
@org.hibernate.annotations.Table(appliesTo = "rent_house_log", comment = "租房行为记录")
public class RentHouseLog extends BaseModel {
    private static final long serialVersionUID = 1L;


    /**
     * 等价合同ID
     */
    @Column(columnDefinition = "varchar(500) comment '等价合同ID'")
    private String rentHouseId;
    

    /**
     * 参数信息
     */
    @Column(columnDefinition = "varchar(500) comment '参数信息'")
    private String params;


    /**
     * 租房行为类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '租房行为类型'")
    private RentHouseAction rentHouseAction;

    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "houseId")
	private House house;
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "memberId")
	private Member member;
    /**
     * 详细信息
     */
    @Column(columnDefinition = "varchar(2000) comment '详细信息'")
    private String message;

    
    public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}



    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

  
    
    public String getRentHouseId() {
		return rentHouseId;
	}

	public void setRentHouseId(String rentHouseId) {
		this.rentHouseId = rentHouseId;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public RentHouseAction getRentHouseAction() {
		return rentHouseAction;
	}

	public void setRentHouseAction(RentHouseAction rentHouseAction) {
		this.rentHouseAction = rentHouseAction;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
    
    
}
