package com.bumu.bran.admin.corporation.helper;

import com.bumu.bran.admin.corporation.result.DepartmentNode;
import com.bumu.bran.model.entity.DepartmentEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author majun
 * @date 2016/10/10
 */
public class DepartmentNodeHelper extends NodeCoreHelper<DepartmentNode, DepartmentEntity> {

    @Override
    public List<DepartmentNode> nodeConverter(List<DepartmentEntity> todoList) {
        List<DepartmentNode> list = new LinkedList<>();
        for (DepartmentEntity entity : todoList) {
            DepartmentNode node = new DepartmentNode();
            node.setName(entity.getDepartmentName());
            node.setId(entity.getId());
            node.setParentId(entity.getParentId());
            list.add(node);
        }
        return list;
    }
}
