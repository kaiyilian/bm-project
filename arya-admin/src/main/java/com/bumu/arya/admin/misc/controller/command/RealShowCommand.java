package com.bumu.arya.admin.misc.controller.command;

import com.bumu.arya.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author majun
 * @date 2017/3/9
 * 较真接口请求参数
 */
public class RealShowCommand implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(RealShowCommand.class);

	public RealShowCommand(String userName, String identityCard) {
		this.userName = userName;
		this.identityCard = identityCard;
		paymentTradeNo = Utils.makeUUID();
	}

	private String mobile = "13776012606";
	private String companyName = "苏州不木科技有限公司";
	private String userName;
	private String identityCard;
	private String hrUserName = "王宇星";
	private String hrMobile = "13776012606";
	private String hrEmail = "27568294@qq.com";
	private String paymentTradeNo;
	private String transactionType = "150";


	public String toJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	@Override
	public String toString() {
		return "RealShowCommand{" +
				"mobile='" + mobile + '\'' +
				", companyName='" + companyName + '\'' +
				", userName='" + userName + '\'' +
				", identityCard='" + identityCard + '\'' +
				", hrUserName='" + hrUserName + '\'' +
				", hrMobile='" + hrMobile + '\'' +
				", hrEmail='" + hrEmail + '\'' +
				", paymentTradeNo='" + paymentTradeNo + '\'' +
				", transactionType='" + transactionType + '\'' +
				'}';
	}

	public String toRequest() {
		Field[] fields = this.getClass().getDeclaredFields();
		List<Field> fieldList = Arrays.asList(fields);
		fieldList.sort(new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		StringBuilder sb = new StringBuilder();

		for (Field field : fieldList) {

			String value;
			try {
				if (field.get(this) == null) {
					continue;
				}
				if (field.get(this) instanceof String) {
					value = (String) field.get(this);
				} else {
					value = "";
				}

			} catch (IllegalAccessException r) {
				value = "";
			}
			if ("".equals(value) || value == null) {
				continue;
			}
			sb.append(field.getName()).append("=");
			sb.append(value).append("&");
		}
		return sb.toString();
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getHrUserName() {
		return hrUserName;
	}

	public void setHrUserName(String hrUserName) {
		this.hrUserName = hrUserName;
	}

	public String getHrMobile() {
		return hrMobile;
	}

	public void setHrMobile(String hrMobile) {
		this.hrMobile = hrMobile;
	}

	public String getHrEmail() {
		return hrEmail;
	}

	public void setHrEmail(String hrEmail) {
		this.hrEmail = hrEmail;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}
