package com.bumu.arya.admin.misc.result;

import com.bumu.arya.admin.misc.model.ImportMessage;
import com.bumu.arya.admin.misc.model.CriminalRecordExcelModel;

import java.util.List;

/**
 * Created by liangjun on 2017/3/9.
 */
public class CriminalRecordExcelReadResult {

    List<CriminalRecordExcelModel> models;

    ImportMessage importMessage;



    public CriminalRecordExcelReadResult() {
    }

    public ImportMessage getImportMessage() {
        return importMessage;
    }

    public void setImportMessage(ImportMessage importMessage) {
        this.importMessage = importMessage;
    }

    public List<CriminalRecordExcelModel> getModels() {
        return models;
    }

    public void setModels(List<CriminalRecordExcelModel> models) {
        this.models = models;
    }
}
