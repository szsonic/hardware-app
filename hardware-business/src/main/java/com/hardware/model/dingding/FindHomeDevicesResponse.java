package com.hardware.model.dingding;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class FindHomeDevicesResponse extends BaseResponse {


    /**
     * 其中key表示一个homeID，value表示这个home下的设备列表
     */
    private LinkedHashMap<String,List<ResultBean>> result;

    public LinkedHashMap<String, List<ResultBean>> getResult() {
        return result;
    }

    public void setResult(LinkedHashMap<String, List<ResultBean>> result) {
        this.result = result;
    }

    public static class ResultBean {

            private String type;
            private String sn;
            private String uuid;
            private String room_id;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }
    }
}
