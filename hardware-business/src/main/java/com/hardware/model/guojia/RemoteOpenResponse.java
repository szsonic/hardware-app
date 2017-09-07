package com.hardware.model.guojia;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * 一键开门返回格式
 * @author sunzhongshuai
 */

public class RemoteOpenResponse extends BaseResponse{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        @JsonProperty("lock_no")
        private String lockNo;
        @JsonProperty("business_id")
        private String businessId;

        public String getLockNo() {
            return lockNo;
        }

        public void setLockNo(String lockNo) {
            this.lockNo = lockNo;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }
    }
}
