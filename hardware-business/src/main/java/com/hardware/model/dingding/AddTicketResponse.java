package com.hardware.model.dingding;

/**
 * <br>
 * 新增工单返回实体类
 * @author sunzhongshuai
 */
public class AddTicketResponse extends BaseResponse {
    private String ticket_id;

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}
