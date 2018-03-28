package com.bumu.arya.admin.payroll.result;

/**
 * @author yousihang
 * @date 2018/3/23
 */
public class WalletPaysCorpResult {

    private String id;

    private String branCorpId;

    private String name;

    private String shortName;

    public WalletPaysCorpResult(String id, String branCorpId, String name, String shortName) {
        this.id = id;
        this.branCorpId = branCorpId;
        this.name = name;
        this.shortName = shortName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranCorpId() {
        return branCorpId;
    }

    public void setBranCorpId(String branCorpId) {
        this.branCorpId = branCorpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
