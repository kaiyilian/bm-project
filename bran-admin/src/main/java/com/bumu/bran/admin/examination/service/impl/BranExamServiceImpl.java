package com.bumu.bran.admin.examination.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.examination.service.BranExamService;
import com.bumu.bran.employee.command.ExamCommand;
import com.bumu.bran.common.Constants;
import com.bumu.bran.employee.model.dao.ExamCategoryDao;
import com.bumu.bran.employee.model.dao.ExamDao;
import com.bumu.bran.employee.model.entity.ExamCategoryEntity;
import com.bumu.bran.employee.model.entity.ExamEntity;
import com.bumu.bran.employee.result.ExamCategoryResult;
import com.bumu.bran.employee.result.ExamDetailsResult;
import com.bumu.bran.employee.result.ExamResult;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranExamServiceImpl implements BranExamService {

	@Autowired
	private ExamDao examDao;

	@Autowired
	private ExamCategoryDao categorydao;

//	@Autowired
//	private BranConfigService branConfigService;

	@Autowired
	private BranAdminConfigService branAdminConfigService;

	@Override
	public ExamResult querybyId(ExamCommand examCommand) throws Exception {
		ExamResult exam = new ExamResult();

		ExamEntity examEntity = examDao.finbyId(examCommand.getExam_id());
		if (examEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_EXAM_NOT_FOUND);
		}
		// 查询体检单
		exam.setExamId(examEntity.getId());
		exam.setExamCode(examEntity.getCode());
		exam.setCardNumber(examEntity.getCard());
		exam.setName(examEntity.getName());
		exam.setExamTime(SysUtils.getDateStringFormTimestamp(examEntity.getTime(), Constants.TIME_FORMAT));
		exam.setExamResult(examEntity.getResult());
		exam.setExamAdvise(examEntity.getAdvise());
		examEntity.setUpdateTime(System.currentTimeMillis());
		examDao.update(examEntity);

		// 查询分项
		List<ExamCategoryEntity> list = categorydao.findByExamId(examEntity.getId());
		if (list != null) {
			Map<String, ExamCategoryResult> map = new HashMap<>();
			for (ExamCategoryEntity c : list) {
//						返回response分项信息
				ExamDetailsResult detailResult = new ExamDetailsResult();
				detailResult.setRange(c.getRange());
				detailResult.setUnit(c.getUnit());
				detailResult.setItemName(c.getName());
				detailResult.setResult(c.getResult());
				if (map.containsKey(c.getCategory())) {
					ExamCategoryResult categoryResult = map.get(c.getCategory());
					categoryResult.getDetails().add(detailResult);

				} else {
					ExamCategoryResult categoryResult = new ExamCategoryResult();
					categoryResult.setCategory(c.getCategory());
					List<ExamDetailsResult> details = new ArrayList<>();
					details.add(detailResult);
					categoryResult.setDetails(details);
					map.put(c.getCategory(), categoryResult);
				}
			}
			exam.setItems((new ArrayList<>(map.values())));
		}
		exam.setUrl(branAdminConfigService.makeExamUrlForAdmin(exam.getExamId(), Constants.DETAILS_PAGE));
		for (ExamCategoryResult e : exam.getItems()) {

			for (ExamDetailsResult c : e.getDetails()) {

				double min = 0, max = 0;

				if (StringUtils.isNotBlank(c.getRange()) && "-".equals(c.getRange())) {
					continue;
				}
				if (StringUtils.isNotBlank(c.getRange()) && !"-".equals(c.getRange())) {
					String[] sp = c.getRange().split("-");
					min = Double.valueOf(sp[0]);
					max = Double.valueOf(sp[1]);
				}

				if (StringUtils.isNotBlank(c.getResult()) && !"-".equals(c.getResult()) && ValidateUtils.isNumber(c.getResult())) {

					double result = Double.valueOf(c.getResult());
					if (!(result >= min && result <= max)) {
						c.setQualified(1);
					}
				}
			}
		}
		return exam;
	}
}
