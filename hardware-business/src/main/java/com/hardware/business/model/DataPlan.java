package com.hardware.business.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class DataPlan {

	/**
	 * error_code : 1
	 * total_num : 0
	 * total_page : 0
	 * next : false
	 * prev : false
	 * attrs : {"isOver":0,"flowQueryResultList":[{"number":"8986031649201482151","startTime":"2017-08-01","offerName":"物联网（数据）月流量包非定向2元5MB（201604）","endTime":"2017-08-31","cumulationTotal":"5","cumulationAlready":"5","cumulationLeft":"0"},{"number":"8986031649201482151","startTime":"2017-08-02","offerName":"物联网（数据）月流量包非定向135元6GB（201604）","endTime":"2017-08-31","cumulationTotal":"5945","cumulationAlready":"3330","cumulationLeft":"2615"}],"noRecord":"0"}
	 */
	@JsonProperty("error_code")
	private Integer errorCode;
	@JsonProperty("total_num")
	private Integer totalNum;
	@JsonProperty("total_page")
	private Integer totalPage;
	private boolean next;
	private boolean prev;
	private AttrsBean attrs;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public AttrsBean getAttrs() {
		return attrs;
	}

	public void setAttrs(AttrsBean attrs) {
		this.attrs = attrs;
	}

	public static class AttrsBean {
		/**
		 * isOver : 0
		 * flowQueryResultList : [{"number":"8986031649201482151","startTime":"2017-08-01","offerName":"物联网（数据）月流量包非定向2元5MB（201604）","endTime":"2017-08-31","cumulationTotal":"5","cumulationAlready":"5","cumulationLeft":"0"},{"number":"8986031649201482151","startTime":"2017-08-02","offerName":"物联网（数据）月流量包非定向135元6GB（201604）","endTime":"2017-08-31","cumulationTotal":"5945","cumulationAlready":"3330","cumulationLeft":"2615"}]
		 * noRecord : 0
		 */

		private Integer isOver;
		private String noRecord;
		private List<FlowQueryResultListBean> flowQueryResultList;

		public Integer getIsOver() {
			return isOver;
		}

		public void setIsOver(Integer isOver) {
			this.isOver = isOver;
		}

		public String getNoRecord() {
			return noRecord;
		}

		public void setNoRecord(String noRecord) {
			this.noRecord = noRecord;
		}

		public List<FlowQueryResultListBean> getFlowQueryResultList() {
			return flowQueryResultList;
		}

		public void setFlowQueryResultList(List<FlowQueryResultListBean> flowQueryResultList) {
			this.flowQueryResultList = flowQueryResultList;
		}

		public static class FlowQueryResultListBean {
			/**
			 * number : 8986031649201482151
			 * startTime : 2017-08-01
			 * offerName : 物联网（数据）月流量包非定向2元5MB（201604）
			 * endTime : 2017-08-31
			 * cumulationTotal : 5
			 * cumulationAlready : 5
			 * cumulationLeft : 0
			 */

			private String number;
			private String startTime;
			private String offerName;
			private String endTime;
			private String cumulationTotal;
			private String cumulationAlready;
			private String cumulationLeft;

			public String getNumber() {
				return number;
			}

			public void setNumber(String number) {
				this.number = number;
			}

			public String getStartTime() {
				return startTime;
			}

			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}

			public String getOfferName() {
				return offerName;
			}

			public void setOfferName(String offerName) {
				this.offerName = offerName;
			}

			public String getEndTime() {
				return endTime;
			}

			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}

			public String getCumulationTotal() {
				return cumulationTotal;
			}

			public void setCumulationTotal(String cumulationTotal) {
				this.cumulationTotal = cumulationTotal;
			}

			public String getCumulationAlready() {
				return cumulationAlready;
			}

			public void setCumulationAlready(String cumulationAlready) {
				this.cumulationAlready = cumulationAlready;
			}

			public String getCumulationLeft() {
				return cumulationLeft;
			}

			public void setCumulationLeft(String cumulationLeft) {
				this.cumulationLeft = cumulationLeft;
			}
		}
	}
}
