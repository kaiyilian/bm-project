package com.bumu.bran.admin.work_shift_type.service;

import com.bumu.bran.workshift.command.WorkShiftTypeCommand;
import com.bumu.bran.workshift.command.WorkShiftIdCommand;
import com.bumu.bran.workshift.command.WorkShiftTypeNewCommand;
import com.bumu.bran.workshift.result.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Transactional
public interface BranWorkNewShiftTypeService {

    /**
     * 增加班次信息
     *
     * @param workShiftTypeCommand
     */
    void add(WorkShiftTypeNewCommand workShiftTypeCommand);


    /**
     * 查询全部班次
     *
     * @param workShiftTypeCommand
     * @return
     */
    List<BranWorkShiftTypeNewResult> get(WorkShiftTypeCommand workShiftTypeCommand);

    /**
     * 根据id查询班次的信息
     *
     * @param workShiftIdCommand
     * @return
     */
    BranNewWorkShiftTypeResult getById(WorkShiftIdCommand workShiftIdCommand);

    /**
     * 获取加班设置
     *
     * @param workShiftIdCommand
     * @return
     */
    List<BranScheduleRestTimeResult> getRest(WorkShiftIdCommand workShiftIdCommand);

    /**
     * 获取旷工定义
     *
     * @param workShiftIdCommand
     * @return
     */
    List<BranScheduleAbsenteeismResult> getAbs(WorkShiftIdCommand workShiftIdCommand);

    /**
     * 获取加班设置
     *
     * @param workShiftIdCommand
     * @return
     */
    BranScheduleOvertimeResult getOvertime(WorkShiftIdCommand workShiftIdCommand);

    /**
     * 删除班次
     *
     * @param workShiftTypeCommand
     */
    void delete(WorkShiftTypeCommand workShiftTypeCommand);


}
