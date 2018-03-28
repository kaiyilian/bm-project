package com.bumu.arya.admin.soin.model.entity;

import com.bumu.arya.model.entity.BaseTxVersionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/8/4.
 */
@Entity
@Table(name = "ARYA_SOIN_DISTRICT_SUPPLIER")
public class SoinDistrictSupplierEntity extends BaseTxVersionEntity {
    public static final String TABLE_NAME_ARYA_SOIN_DISTRICT_SUPPLIER = "ARYA_SOIN_DISTRICT_SUPPLIER";


    /**
     * 主键
     */
    public static final String COL_NAME_ID = "ID";

    /**
     * 地区id
     */
    public static final String COL_NAME_DISTRICT_ID = "DISTRICT_ID";

    /**
     * 供应商id
     */
    public static final String COL_NAME_SUPPLIER_ID = "SUPPLIER_ID";

    /**
     * 是否是默认的供应商
     */
    public static final String COL_NAME_IS_DEFAULT = "IS_DEFAULT";

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
     * 地区ID
     */

    @Column(name = COL_NAME_DISTRICT_ID, columnDefinition = "char(6)")
    private String districtId;

    /**
     * 供应商ID
     */
    @Column(name = COL_NAME_SUPPLIER_ID, length = 32, columnDefinition = "char(32)")
    private String supplierId;

    /**
     * 是否是默认的供应商
     */

    @Column(name = COL_NAME_IS_DEFAULT)
    private int isDefault;

    @Column(name = COL_NAME_IS_DELETE)
    private int isDelete;

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

}
