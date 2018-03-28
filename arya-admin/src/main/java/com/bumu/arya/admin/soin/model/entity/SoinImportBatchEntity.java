package com.bumu.arya.admin.soin.model.entity;

import com.bumu.arya.model.entity.BaseTxVersionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单导入批次表
 */
@Entity
@Table(name = "ARYA_SOIN_IMPORT_BATCH")
public class SoinImportBatchEntity extends BaseTxVersionEntity {

    public static final String TABLE_NAME_ARYA_SOIN_IMPORT_BATCH = "ARYA_SOIN_IMPORT_BATCH";


    /**
     * 主键
     */
    public static final String COL_NAME_ID = "ID";

    /**
     * 导入者ID
     */
    public static final String COL_NAME_OPERATOR_ID = "OPERATOR_ID";

    /**
     * 批次号，唯一
     */
    public static final String COL_NAME_BATCH_NO = "BATCH_NO";

    /**
     * 导入的文件名
     */
    public static final String COL_NAME_IMPORT_FILE_NAME = "IMPORT_FILE_NAME";

    /**
     * 创建时间
     */
    public static final String COL_NAME_CREATE_TIME = "CREATE_TIME";


    /**
     * 主键
     */

    @Id()
    @Column(name = COL_NAME_ID, length = 32, columnDefinition = "char(32)")
    private String id;

    /**
     * 导入者ID
     */

    @Column(name = COL_NAME_OPERATOR_ID, length = 32, columnDefinition = "char(32)", nullable = false)
    private String operatorId;

    /**
     * 批次号，唯一
     */

    @Column(name = COL_NAME_BATCH_NO, length = 32, nullable = false)
    private String batchNo;

    /**
     * 导入的文件名
     */

    @Column(name = COL_NAME_IMPORT_FILE_NAME, length = 64, nullable = false)
    private String importFileName;

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
     * 导入者ID
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * 导入者ID
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }


    /**
     * 批次号，唯一
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 批次号，唯一
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }


    /**
     * 导入的文件名
     */
    public String getImportFileName() {
        return importFileName;
    }

    /**
     * 导入的文件名
     */
    public void setImportFileName(String importFileName) {
        this.importFileName = importFileName;
    }
    
}