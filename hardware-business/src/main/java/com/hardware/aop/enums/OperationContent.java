package com.hardware.aop.enums;


import com.hardware.domain.LogOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * 操作内容枚举
 * @author sunzhongshuai
 */
public enum OperationContent {
    ADD_PWD("新增密码"),DEL_PWD("删除密码"),MODIFY_PWD("修改密码"),FROZEN_PWD("冻结密码"),UNFROZEN_PWD("解冻密码"),
    BIND_USER("绑定用户"),UNBIND_USER("解绑用户"), DOORLOCK("全部"), AMMETER("全部"),
    CONTROL_AMMETER("控制电表"),RESET_AMMETER("重置电表"),MODIFY_AMMETER_PRICE("修改电价"),CHARGE_AMMETER("电表充值"),
    CONTROL_AMMETER_ON("通电"), CONTROL_AMMETER_OFF("断电"),
    SEARCH_AMMETER("查询电表"),SEARCH_DOORLOCK("查询门锁");

    private String desc;//描述

    OperationContent(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /*
     * 获取电表操作类型
     */
    public static List<LogOperation> getAmmeterOperations(){
        List<LogOperation> operations = new ArrayList<LogOperation>();
    	
    	operations.add(new LogOperation(AMMETER.name(), AMMETER.desc));
    	operations.add(new LogOperation(SEARCH_AMMETER.name(), "查询"));
    	operations.add(new LogOperation(CONTROL_AMMETER_ON.name(), CONTROL_AMMETER_ON.desc));
    	operations.add(new LogOperation(CONTROL_AMMETER_OFF.name(), CONTROL_AMMETER_OFF.desc));
    	operations.add(new LogOperation(MODIFY_AMMETER_PRICE.name(), "修改电价"));
    	operations.add(new LogOperation(CHARGE_AMMETER.name(), "充值"));
    	operations.add(new LogOperation(RESET_AMMETER.name(), "重置"));
    	
    	return operations;
    }
    
    /*
     * 获取门锁操作类型
     */
    public static List<LogOperation> getDoorLockOperations(){
    	List<LogOperation> operations = new ArrayList<LogOperation>();
    	
    	operations.add(new LogOperation(DOORLOCK.name(), DOORLOCK.desc));
    	operations.add(new LogOperation(SEARCH_DOORLOCK.name(), "查询"));
    	
    	operations.add(new LogOperation(ADD_PWD.name(), ADD_PWD.desc));
    	operations.add(new LogOperation(DEL_PWD.name(), DEL_PWD.desc));
    	operations.add(new LogOperation(MODIFY_PWD.name(), MODIFY_PWD.desc));
    	operations.add(new LogOperation(FROZEN_PWD.name(), FROZEN_PWD.desc));
    	operations.add(new LogOperation(UNFROZEN_PWD.name(), UNFROZEN_PWD.desc));
    	
    	operations.add(new LogOperation(BIND_USER.name(), BIND_USER.desc));
    	operations.add(new LogOperation(UNBIND_USER.name(), UNBIND_USER.desc));
    	
    	return operations;
    }
    
    public static String getDoorLockOptTypeHQLCondition(){
    	StringBuilder sb = new StringBuilder(" (");
    	sb.append("'").append(SEARCH_DOORLOCK.name()).append("'");
    	sb.append(", '").append(ADD_PWD.name()).append("'");
    	sb.append(", '").append(DEL_PWD.name()).append("'");
    	sb.append(", '").append(FROZEN_PWD.name()).append("'");
    	sb.append(", '").append(UNFROZEN_PWD.name()).append("'");
    	sb.append(", '").append(BIND_USER.name()).append("'");
    	sb.append(", '").append(UNBIND_USER.name()).append("'");
    	sb.append(") ");
    	
    	return sb.toString();
    }
    
    public static String getAmmeterOptTypeHQLCondition(){
    	StringBuilder sb = new StringBuilder(" (");
    	sb.append("'").append(SEARCH_AMMETER.name()).append("'");
    	sb.append(", '").append(CONTROL_AMMETER_ON.name()).append("'");
    	sb.append(", '").append(CONTROL_AMMETER_OFF.name()).append("'");
    	sb.append(", '").append(MODIFY_AMMETER_PRICE.name()).append("'");
    	sb.append(", '").append(CHARGE_AMMETER.name()).append("'");
    	sb.append(", '").append(RESET_AMMETER.name()).append("'");
    	sb.append(") ");
    	
    	return sb.toString();
    }
}
