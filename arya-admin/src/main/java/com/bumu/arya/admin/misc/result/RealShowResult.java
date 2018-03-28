package com.bumu.arya.admin.misc.result;

import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2017/3/9
 * 较真接口返回对象
 */
public class RealShowResult {

	private static Logger logger = LoggerFactory.getLogger(RealShowResult.class);

	private Integer responseCode;
	private String message;
	private Body body = new Body();

	public static RealShowResult createByJson(String result) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(result, RealShowResult.class);
	}

	public static RealShowResult getMock() throws IOException {
		String result = "{\"body\":{\"orderNumber\":\"CN11489129883556210635\",\"data\":[{\"transactionType\":150,\"entity\":{\"id\":638,\"orderId\":1755,\"resCode\":3,\"resMsg\":\"查询异常\",\"caseTime\":null,\"des\":null,\"createBy\":null,\"createTime\":1489129883815,\"updateBy\":null,\"updateTime\":1489129883815}}],\"paymentTradeNo\":\"6d439afa9ff74d0a873315c6aea6b598\",\"orderId\":1755},\"responseCode\":500,\"message\":\"网络错误\"}";
		return RealShowResult.createByJson(result);
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Integer getResCode() {
		if (body.data == null || body.data.isEmpty()) {
			return null;
		}
		return body.data.get(0).entity.resCode;
	}

	public String getResMsg() {
		if (body.data == null || body.data.isEmpty()) {
			return null;
		}
		return body.data.get(0).entity.resMsg;
	}

	public String getCaseTime() {
		if (body.data == null || body.data.isEmpty()) {
			return null;
		}
		return body.data.get(0).entity.caseTime;
	}

	public String getDes() {
		if (body.data == null || body.data.isEmpty()) {
			return null;
		}
		return body.data.get(0).entity.caseTime;
	}

	public CriminalCommand toCriminalCommand(CriminalCommand criminalCommand) {
		logger.info("message: " + message);
		// 判断http 状态码
		if (responseCode == null || responseCode != 200 ||
				body == null || body.data == null || body.data.isEmpty() ||
				body.data.get(0).entity.resCode == null
				) {

			criminalCommand.setQueryStatus(1);
			criminalCommand.setCriminalDetail(message);
			return criminalCommand;
		}

		// 判断犯罪记录类型码
		Entity entity = body.data.get(0).entity;
		logger.info("resMsg: " + entity.resMsg);

		if (entity.resCode == 1) {
			criminalCommand.setCriminalDetail(entity.des);
		} else {
			criminalCommand.setCriminalDetail(entity.resMsg);
		}
		criminalCommand.setQueryStatus(0);

		return criminalCommand;
	}

	@Override
	public String toString() {
		return "RealShowResult{" +
				"responseCode=" + responseCode +
				", message='" + message + '\'' +
				", body=" + body +
				'}';
	}

	private static class Body {
		private String orderNumber;
		private String paymentTradeNo;
		private Integer orderId;
		private List<Data> data = new ArrayList<>();

		public String getOrderNumber() {
			return orderNumber;
		}

		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}

		public String getPaymentTradeNo() {
			return paymentTradeNo;
		}

		public void setPaymentTradeNo(String paymentTradeNo) {
			this.paymentTradeNo = paymentTradeNo;
		}

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public List<Data> getData() {
			return data;
		}

		public void setData(List<Data> data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "Body{" +
					"orderNumber='" + orderNumber + '\'' +
					", paymentTradeNo='" + paymentTradeNo + '\'' +
					", orderId=" + orderId +
					", data=" + data +
					'}';
		}
	}

	private static class Data {
		private Integer transactionType;
		private Entity entity = new Entity();

		public Integer getTransactionType() {
			return transactionType;
		}

		public void setTransactionType(Integer transactionType) {
			this.transactionType = transactionType;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}

		@Override
		public String toString() {
			return "Data{" +
					"transactionType=" + transactionType +
					", entity=" + entity +
					'}';
		}
	}

	private static class Entity {
		private Integer id;
		private Integer orderId;
		private Integer resCode;
		private String resMsg;
		private String caseTime;
		private String des;
		private String createBy;
		private String updateBy;
		private Long createTime;
		private Long updateTime;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public Integer getResCode() {
			return resCode;
		}

		public void setResCode(Integer resCode) {
			this.resCode = resCode;
		}

		public String getResMsg() {
			return resMsg;
		}

		public void setResMsg(String resMsg) {
			this.resMsg = resMsg;
		}

		public String getCaseTime() {
			return caseTime;
		}

		public void setCaseTime(String caseTime) {
			this.caseTime = caseTime;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		public String getCreateBy() {
			return createBy;
		}

		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}

		public String getUpdateBy() {
			return updateBy;
		}

		public void setUpdateBy(String updateBy) {
			this.updateBy = updateBy;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}

		public Long getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Long updateTime) {
			this.updateTime = updateTime;
		}

		@Override
		public String toString() {
			return "Entity{" +
					"id=" + id +
					", orderId=" + orderId +
					", resCode=" + resCode +
					", resMsg='" + resMsg + '\'' +
					", caseTime='" + caseTime + '\'' +
					", des='" + des + '\'' +
					", createBy='" + createBy + '\'' +
					", updateBy='" + updateBy + '\'' +
					", createTime=" + createTime +
					", updateTime=" + updateTime +
					'}';
		}
	}
}
