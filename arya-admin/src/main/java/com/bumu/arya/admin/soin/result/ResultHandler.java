package com.bumu.arya.admin.soin.result;

import org.swiftdao.entity.KeyedPersistable;

/**
 * @author majun
 */
public interface ResultHandler<Entity extends KeyedPersistable> {

    /**
     * 实体类与result对象之间的转换
     *
     * @param entity
     */
    void entityToResult(Entity entity);
}
