package com.hardware.model.lianyu;
import java.util.List;

//联寓门锁操作日志-页
public class RoomlockLogPage {

	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private List<RoomlockLog> list;
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public List<RoomlockLog> getList() {
		return list;
	}
	public void setList(List<RoomlockLog> list) {
		this.list = list;
	}
	
}
