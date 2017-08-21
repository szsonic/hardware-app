package com.support.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 查询排序工具类
 *
 * @author shenpeng
 *
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 2706429092065668603L;
    private List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();

    public Order() {
    }

    /**
     * 枚举构造排序条件
     *
     * @param orderField
     *            排序字段
     * @param orderDirection
     *            升序|降序
     */
    public Order(String orderField, OrderSort orderDirection) {
        orderInfoList.add(new OrderInfo(orderField, orderDirection.getDesc()));
    }

    /**
     * 字符串构造排序条件（默认创建时间排序）
     *
     * @param orderField
     *            排序字段
     * @param orderDirection
     *            升序|降序
     */
    public Order(String orderField, String orderDirection) {
        if (orderField != null && !orderField.equals("")) {
            orderDirection = orderDirection.equals("asc") ? "asc" : "desc";
        } else {
            orderField = "createTime";
            orderDirection = "desc";
        }
        orderInfoList.add(new OrderInfo(orderField, orderDirection));
    }

    /**
     * 通过Map集合来构造多条件排序
     *
     * @param maps
     *            排序条件集合 key字段名，val为升序或降序
     */
    public Order(Map<String, String> maps) {
        for (Entry<String, String> entry : maps.entrySet()) {
            orderInfoList.add(new OrderInfo(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * 字符串构造排序条件
     *
     * @param request
     *            请求对象，需要传递（orderField|排序字段，orderDirection升序|降序）
     */
    public Order(HttpServletRequest request) {
        if (ValidatorUtils.isNotEmpty(request.getAttribute("orderField")) && ValidatorUtils.isNotEmpty(request.getAttribute("orderDirection"))) {
            String orderField = request.getAttribute("orderField").toString();
            String orderDirection = request.getAttribute("orderDirection").toString();
            orderInfoList.add(new OrderInfo(orderField, orderDirection));
        } else if (ValidatorUtils.isNotEmpty(request.getParameter("orderField")) && ValidatorUtils.isNotEmpty(request.getParameter("orderDirection"))) {
            String orderField = request.getParameter("orderField").toString();
            String orderDirection = request.getParameter("orderDirection").toString();
            orderInfoList.add(new OrderInfo(orderField, orderDirection));
        } else {
            orderInfoList.add(new OrderInfo("createTime", "desc"));
        }
    }

    /**
     * 字符串构造排序条件
     *
     * @param orderField
     *            排序字段
     * @param orderDirection
     *            升序|降序
     */
    public void addOrder(String orderField, OrderSort orderDirection) {
        orderInfoList.add(new OrderInfo(orderField, orderDirection.getDesc()));
    }

    /**
     * 添加字符串排序条件（默认创建时间排序）
     *
     * @param orderField
     *            排序字段
     * @param orderDirection
     *            升序|降序
     */
    public void addOrder(String orderField, String orderDirection) {
        if (orderField != null && !orderField.equals("")) {
            orderDirection = orderDirection.equals("asc") ? "asc" : "desc";
        } else {
            orderField = "createTime";
            orderDirection = "desc";
        }
        orderInfoList.add(new OrderInfo(orderField, orderDirection));
    }

    /**
     * 获取排序列表中第一个排序条件
     *
     * @return OrderInfo 排序条件
     */
    public OrderInfo getOrderInfo() {
        return this.orderInfoList.size() > 0 ? this.orderInfoList.get(0) : null;
    }

    /**
     * 获取排序列表
     *
     * @return List<OrderInfo> 排序条件列表
     */
    public List<OrderInfo> getOrderInfoList() {
        return this.orderInfoList.size() > 0 ? this.orderInfoList : null;
    }

    /**
     * 生成查询排序SQL字符串
     */
    public String toString() {
        String sql = "";
        for (int i = orderInfoList.size() - 1; i >= 0; i--) {
            // 如果是多条件排序，则默认排序数组倒序组织排序条件
            OrderInfo orderInfo = orderInfoList.get(i);
            sql += orderInfo.getOrderField() + " " + orderInfo.getOrderDirection() + ",";
        }
        sql = StringUtils.substringBeforeLast(sql, ",");
        return sql.length() < 1 ? "" : " order by c." + sql;
    }

    /**
     * 返回Map<String, String>排序对象集合
     *
     * @return Map 返回Map<String, String>排序对象集合
     */
    public Map<String, String> toMap() {
        if (ValidatorUtils.isNotEmpty(orderInfoList)) {
            Map<String, String> map = null;
            for (OrderInfo oi : orderInfoList) {
                map = new HashMap<String, String>();
                map.put("orderField", oi.getOrderField());
                map.put("orderDirection", oi.getOrderDirection());
            }
            return map;
        }
        return null;
    }

    /**
     * 返回JSON字符串排序对象集合
     *
     * @return JSON 返回JSON字符串排序对象集合
     */
    public String toJsonString() {
        if (ValidatorUtils.isNotEmpty(orderInfoList)) {
            Map<String, String> map = null;
            for (OrderInfo oi : orderInfoList) {
                map = new HashMap<String, String>();
                map.put("orderField", oi.getOrderField());
                map.put("orderDirection", oi.getOrderDirection());
            }
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    public class OrderInfo implements Serializable {
        private static final long serialVersionUID = 9108879617871998502L;
        private String orderField;
        private String orderDirection;

        public OrderInfo(String orderField, String orderDirection) {
            this.orderField = orderField;
            this.orderDirection = orderDirection;
        }

        public String getOrderField() {
            return orderField;
        }

        public String getOrderDirection() {
            return orderDirection;
        }
    }

}
