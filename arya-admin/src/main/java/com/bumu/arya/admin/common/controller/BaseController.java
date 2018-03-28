package com.bumu.arya.admin.common.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.common.service.AdminContextService;
import com.bumu.arya.model.BumuGenericDao;
import com.bumu.common.result.ValidationResult;
import com.bumu.common.util.JSRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * 所有 Controller 的基础类
 */
public class BaseController {

	@Autowired
	protected BumuGenericDao bumuGenericDao;

	@Autowired
	protected AdminContextService adminContextService;

	@PostConstruct
	public void init() {

	}

    /**
     * 将接口参数对象参数校验结果转换为 HttpResponse 对象，如果不想在代码中直接调用则使用 {@link com.bumu.common.aspect.ValidateResultAspect}
     * @param command
     * @param valResult
     * @return
     */
    protected HttpResponse checkValidationResult(Object command, BindingResult valResult) {
        // 检查验证情况
        if (valResult.hasErrors()) {
            for (ObjectError objectError : valResult.getAllErrors()) {
                System.out.println(JSRUtils.getJsonPropertyKeyFromError(command, objectError.getCodes()));
                System.out.println(objectError.getDefaultMessage());
            }

            ValidationResult inputErrors = JSRUtils.jsrValResultToAryaValResult(command, valResult);

            for (ValidationResult.InputError inputError : inputErrors) {
                System.out.println(inputError.getMsg());
            }
            return new HttpResponse<>(ErrorCode.CODE_VALIDATION_FAIL, inputErrors);
        }
        return null;
    }

	/**
	 * 把 JSR303 校验结果转换成 Arya 接口所需的结果集
	 * @param bean 检验的bean对象
	 * @param valResult 检验后的结果
	 * @return
	 */
	protected ValidationResult jsrValResultToAryaValResult(Object bean, BindingResult valResult) {
		return JSRUtils.jsrValResultToAryaValResult(bean, valResult);
	}

	/**
	 *
	 * @param targetBean
	 * @param error
	 * @return
	 */
	public String getJsonPropertyKeyFromError(Object targetBean, String[] error) {
		return JSRUtils.getJsonPropertyKeyFromError(targetBean,error);
	}

	/**
	 * 创建空的 JQuery Datatables 返回结果集
	 * @return
	 */
	protected JQDatatablesPaginationResult makeEmptyJQDatatable(int draw) {
		JQDatatablesPaginationResult result = new JQDatatablesPaginationResult();
		result.setDraw(draw);
		result.setRecordsTotal(0);
		result.setRecordsFiltered(0);
		result.setData(new ArrayList(0));
		return result;
	}

}
