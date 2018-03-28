package com.bumu.bran.admin.work_shift_rule.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询可用班组人员列表
 *
 * @author majun
 * @date 2017/9/4
 * @email 351264830@qq.com
 */
@ApiModel
public class AvailableWorkShiftEmpResult {
    @ApiModelProperty(value = "员工姓名")
    private String empName;
    @ApiModelProperty(value = "部门")
    private String departmentName;
    @ApiModelProperty(value = "岗位")
    private String positionName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
