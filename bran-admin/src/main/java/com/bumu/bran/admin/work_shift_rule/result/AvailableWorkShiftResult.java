package com.bumu.bran.admin.work_shift_rule.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询可用班组
 *
 * @author majun
 * @date 2017/9/4
 * @email 351264830@qq.com
 */
@ApiModel
public class AvailableWorkShiftResult {

    @ApiModelProperty(value = "员工列表")
    List<AvailableWorkShiftEmpResult> emps = new ArrayList<>();

    @ApiModelProperty(value = "班组id")
    private String id;

    @ApiModelProperty(value = "班组名字")
    private String name;

    @ApiModelProperty(value = "班组成员人数")
    private Integer staffNumber;

    public Integer getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(Integer staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AvailableWorkShiftEmpResult> getEmps() {
        return emps;
    }

    public void setEmps(List<AvailableWorkShiftEmpResult> emps) {
        this.emps = emps;
    }
}
