package com.bumu.bran.admin.work_shift_rule.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 排班设置
 *
 * @author majun
 * @date 2017/9/4
 * @email 351264830@qq.com
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BranWorkShiftRuleCommand{

    @ApiModelProperty(value = "新增时id可以不填,更新时id必填")
    private String id;
    @ApiModelProperty(value = "排班名称")
    private String name;
    @ApiModelProperty(value = "排班开始时间")
    private String startTime;
    @ApiModelProperty(value = "排班结束时间")
    private String endTime;
    @ApiModelProperty(value = "是否一直循环 0:不循环 1:循环")
    private Integer isLoopAround;
    @ApiModelProperty(value = "排班周期")
    private Integer cycle;
    @JsonProperty("workShiftTypes")
    @ApiModelProperty(value = "班次model")
    private List<SetRuleWorkShiftTypeModel> workShiftTypes;
    @JsonProperty("workShifts")
    @ApiModelProperty(value = "班组model")
    private List<SetRuleWorkShiftModel> workShifts;

    public BranWorkShiftRuleCommand(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public List<SetRuleWorkShiftTypeModel> getWorkShiftTypes() {
        return workShiftTypes;
    }

    public void setWorkShiftTypes(List<SetRuleWorkShiftTypeModel> workShiftTypes) {
        this.workShiftTypes = workShiftTypes;
    }

    public List<SetRuleWorkShiftModel> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<SetRuleWorkShiftModel> workShifts) {
        this.workShifts = workShifts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsLoopAround() {
        return isLoopAround;
    }

    public void setIsLoopAround(Integer isLoopAround) {
        this.isLoopAround = isLoopAround;
    }

    @ApiModel
    public static class SetRuleWorkShiftTypeModel{

        @JsonProperty("id")
        @ApiModelProperty(value = "班次id")
        @NotBlank(message = "班次id不能为空")
        private String id;
        @JsonProperty("shortName")
        @ApiModelProperty(value = "班次简称")
        private String shortName;
        @JsonProperty("color")
        @ApiModelProperty(value = "班次颜色")
        private String color;

        public SetRuleWorkShiftTypeModel(){}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    @ApiModel
    public static class SetRuleWorkShiftModel{

        @JsonProperty("id")
        @ApiModelProperty(value = "班组id")
        @NotBlank(message = "班组id不能为空")
        private String id;
        @JsonProperty("name")
        @ApiModelProperty(value = "班组名字")
        private String name;
        @JsonProperty("staffNumber")
        @ApiModelProperty(value = "班组人数")
        private Integer staffNumber;

        public SetRuleWorkShiftModel(){}

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

        public Integer getStaffNumber() {
            return staffNumber;
        }

        public void setStaffNumber(Integer staffNumber) {
            this.staffNumber = staffNumber;
        }
    }

}
