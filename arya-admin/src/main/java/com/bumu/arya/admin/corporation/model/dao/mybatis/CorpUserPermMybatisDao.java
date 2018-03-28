package com.bumu.arya.admin.corporation.model.dao.mybatis;

import com.bumu.arya.admin.corporation.result.CorpUserPermResult;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majun
 * @date 2018/1/3
 * @email 351264830@qq.com
 */
@Repository
@MapperScan
public interface CorpUserPermMybatisDao {

    List<CorpUserPermResult> findCorpPermsWithCorpUser(@Param(value = "branCorpUserId") String branCorpUserId);
}
