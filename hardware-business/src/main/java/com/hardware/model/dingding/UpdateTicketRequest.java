package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class UpdateTicketRequest {
    private String ticketId;
    private String ticketDescription;
    private List<String> roomIds;
    private AddTicketSubscribe ticketSubscribe;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public AddTicketSubscribe getTicketSubscribe() {
        return ticketSubscribe;
    }

    public void setTicketSubscribe(AddTicketSubscribe ticketSubscribe) {
        this.ticketSubscribe = ticketSubscribe;
    }
}
