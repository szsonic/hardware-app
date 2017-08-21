package com.support.model;
import java.util.Map;

/**
 * 分页接口类
 *
 * @author shenpeng
 *
 */
public interface Pager {
    /**
     * 获取每页显示的记录数
     *
     * @return int 记录数
     */
    public int getPageSize();

    /**
     * 设置每页显示的记录数
     *
     * @param pageSize
     *            每页记录数
     */
    public void setPageSize(int pageSize);

    /**
     * 获取总页数
     *
     * @return int 总页数
     */
    public int getPageCount();

    /**
     * 设置总页数
     *
     * @param pageCount
     *            总页数
     */
    public void setPageCount(int pageCount);

    /**
     * 获取记录总数
     *
     * @return int 记录总数
     */
    public int getRecordCount();

    /**
     * 设置记录总数
     *
     * @param recordCount
     */
    public void setRecordCount(int recordCount);

    /**
     * 获取当前页数
     *
     * @return int 当前页数
     */
    public int getPage();

    /**
     * 设置当前页数
     *
     * @param page
     *            当前页数
     */
    public void setPage(int page);

    /**
     * 获取分页过滤条件
     *
     * @return Map<String, String> 分页过滤条件
     */
    public Map<String, String> getFilter();

    /**
     * 返回分页的Map参数对象
     *
     * @return Map<String, Integer> 分页的Map参数对象
     */
    public Map<String, Integer> toMap();

    /**
     * 返回分页的JSON参数对象
     *
     * @return String 分页的JSON参数对象
     */
    public String toJsonString();
}