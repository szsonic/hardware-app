package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.LinkedHashMap;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class FindHomeStatesResponse extends BaseResponse{

    private LinkedHashMap<String, ResultBean> result;

    public static class ResultBean{
        @JsonProperty(value = "sp_state")
        private String spState;
        @JsonProperty(value = "install_state")
        private String installState;

        public String getSpState() {
            return spState;
        }

        public void setSpState(String spState) {
            this.spState = spState;
        }

        public String getInstallState() {
            return installState;
        }

        public void setInstallState(String installState) {
            this.installState = installState;
        }
    }

    public LinkedHashMap<String, ResultBean> getResult() {
        return result;
    }

    public void setResult(LinkedHashMap<String, ResultBean> result) {
        this.result = result;
    }
}
