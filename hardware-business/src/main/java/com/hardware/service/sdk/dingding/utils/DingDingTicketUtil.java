package com.hardware.service.sdk.dingding.utils;

import com.hardware.business.model.dingding.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class DingDingTicketUtil extends DingDingUtil {


    //{"ReqID":"55db09d023","ErrNo":0,"ErrMsg":"","ticket_id":"ISCN1705032099988"}
    public static AddTicketResponse addTicket(AddTicketRequest ticket) {
        String url= BASE_URL +"add_ticket";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id",ticket.getHomeId());
        params.put("service_target",ticket.getServiceTarget());
        params.put("room_ids",ticket.getRoomIds());
        params.put("subscribe", ticket.getSubscribe());
        params.put("ticket_description",ticket.getTicketDescription());
        params.put("service_type",ticket.getServiceType());
        return restTemplate.postForObject(url,params,AddTicketResponse.class);
    }


    public static TicketResponse getTicketById(String ticketId) {
        String url= BASE_URL +"get_ticket_by_id?access_token={access_token}&ticket_id={ticket_id}";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("ticket_id",ticketId);
        return restTemplate.getForObject(url,TicketResponse.class,params);
    }

    public static BaseResponse updateTicketState(String ticketId, Integer state) {
        String url= BASE_URL +"update_ticket_state";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("ticket_id",ticketId);
        params.put("ticket_state",state);
        return restTemplate.postForObject(url,params,BaseResponse.class);
    }


    public static BaseResponse updateTicket(UpdateTicketRequest ticketRequest) {
        String url= BASE_URL +"update_ticket";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("ticket_id",ticketRequest.getTicketId());
        params.put("ticket_description",ticketRequest.getTicketDescription());
        params.put("room_ids",ticketRequest.getRoomIds());
        params.put("ticket_subscribe",ticketRequest.getTicketSubscribe());
        return restTemplate.postForObject(url,params,BaseResponse.class);
    }




}
