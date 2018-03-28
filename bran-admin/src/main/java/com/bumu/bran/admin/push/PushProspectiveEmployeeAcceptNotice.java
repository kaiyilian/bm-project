package com.bumu.bran.admin.push;

import com.bumu.arya.common.Constants;
import com.bumu.bran.admin.push.vo.EmployeeUserModel;
import com.bumu.common.model.PushMessage;
import com.bumu.common.model.PushTo;
import com.bumu.common.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 入职完成推送
 *
 * @author CuiMengxin
 * @date 2016/5/27
 */
public class PushProspectiveEmployeeAcceptNotice extends Thread {
	PushService pushService;
	String branCorpName;
	List<EmployeeUserModel> employeeUserModels;
	private Logger log = LoggerFactory.getLogger(PushProspectiveEmployeeAcceptNotice.class);

	public PushProspectiveEmployeeAcceptNotice(PushService pushService, List<EmployeeUserModel> employeeUserModels, String branCorpName) {
		this.pushService = pushService;
		this.employeeUserModels = employeeUserModels;
		this.branCorpName = branCorpName;
	}

	public void run() {
		PushMessage pushMessage = new PushMessage();
//		pushMessage.setIcon(icon);
		pushMessage.setType(com.bumu.bran.common.Constants.CORP_NOTICE_JUMP_RUZHI_COMPLETE);
		pushMessage.setTitle(branCorpName + "已同意您的入职。");
		for (EmployeeUserModel employeeUserModel : employeeUserModels) {
			pushMessage.setContent("尊敬的" + employeeUserModel.getName() + "：" + branCorpName + "已同意您的入职，工号为" + employeeUserModel.getWorkSn()+"，请按时入职。");
			pushMessage.setUserId(employeeUserModel.getAryaUserId());
			PushTo.PushToOne pushTo = new PushTo.PushToOne(null, employeeUserModel.getAryaUserId(), Constants.ClientType.getClientType(employeeUserModel.getLastClientType()));
			String pushResult = pushService.push(pushMessage, pushTo);
			log.info(pushResult);
		}
	}
}
