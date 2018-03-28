package com.bumu.arya.admin.soin.model.entity;

import com.bumu.arya.model.entity.BaseTxVersionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 社保供应商
 */
@Entity
@Table(name = "ARYA_SOIN_SUPPLIER")
public class SoinSupplierEntity extends BaseTxVersionEntity {

    public static final String TABLE_NAME_ARYA_SOIN_SUPPLIER = "ARYA_SOIN_SUPPLIER";


    /**
     * 主键
     */
    public static final String COL_NAME_ID = "ID";

    /**
     * 供应商名称
     */
    public static final String COL_NAME_SUPPLIER_NAME = "SUPPLIER_NAME";

    /**
     * 管理费
     */
    public static final String COL_NAME_SOIN_FEE = "SOIN_FEE";

    /**
     * 创建时间
     */
    public static final String COL_NAME_CREATE_TIME = "CREATE_TIME";


    /**
     * 是否删除
     */
    public static final String COL_NAME_IS_DELETE = "IS_DELETE";

    /**
     * 主键
     */

    @Id()
    @Column(name = COL_NAME_ID, length = 32, columnDefinition = "char(32)")
    private String id;

    /**
     * 供应商名称
     */

    @Column(name = COL_NAME_SUPPLIER_NAME, length = 32, nullable = false)
    private String supplierName;

    /**
     * 管理费
     */

    @Column(name = COL_NAME_SOIN_FEE, precision = 8, scale = 2)
    private BigDecimal soinFee;

    @Column(name = COL_NAME_IS_DELETE)
    private int isDelete;


    /**
     * 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    /**
     * 管理费
     */
    public BigDecimal getSoinFee() {
        return soinFee;
    }

    /**
     * 管理费
     */
    public void setSoinFee(BigDecimal soinFee) {
        this.soinFee = soinFee;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}