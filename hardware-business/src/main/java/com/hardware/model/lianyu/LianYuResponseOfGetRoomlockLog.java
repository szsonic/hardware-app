package com.hardware.model.lianyu;

/*
 * 联寓平台 - 获取日志响应结果
 */
public class LianYuResponseOfGetRoomlockLog extends LianYuResponse{
	private RoomlockLogPage page; 
	public RoomlockLogPage getPage() {
		return page;
	}
	public void setPage(RoomlockLogPage page) {
		this.page = page;
	}
}
