package com.hardware.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * <br>
 *
 * @author chengzhifeng
 */
public class FlowCardJSON {

	@JsonProperty("error_code")
	private Integer errorCode;
	@JsonProperty("total_num")
	private Integer totalNum;
	@JsonProperty("total_page")
	private Integer totalPage;
	private boolean next;
	private boolean prev;
	@JsonProperty("attrs")
	private FlowCardBean flowCard;

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

	public FlowCardBean getFlowCard() {
		return flowCard;
	}

	public void setFlowCard(FlowCardBean flowCard) {
		this.flowCard = flowCard;
	}

	public static class FlowCardBean {

		private Integer isOver;
		private String noRecord;
		@JsonProperty("flowQueryResultList")
		private List<FlowCardComboBean> FlowCardCombo;

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

		public List<FlowCardComboBean> getFlowCardCombo() {
			return FlowCardCombo;
		}

		public void setFlowCardCombo(List<FlowCardComboBean> flowCardCombo) {
			FlowCardCombo = flowCardCombo;
		}

		public static class FlowCardComboBean {

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
