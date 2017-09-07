package com.hardware.model.dingding;

import java.util.Date;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class AddTicketSubscribe {
    /**
     * 预约日期
     */
    private Date date;

    /**
     * 100-全天，101-上午，102-下午，103-晚上
     */
    private String time;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 联系人手机号
     */
    private String phone;
    
    /**
     * 备注
     */
    private String note;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
