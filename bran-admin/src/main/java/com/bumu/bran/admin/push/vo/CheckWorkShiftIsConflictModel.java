package com.bumu.bran.admin.push.vo;

/**
 * 检验班组是否有冲突
 */
public class CheckWorkShiftIsConflictModel {

	private Boolean isConflict;

	private String conflictWorkShiftId;

	private String conflictWorkShiftName;

	public CheckWorkShiftIsConflictModel(){

	}

	public CheckWorkShiftIsConflictModel(Boolean isConflict, String conflictWorkShiftId, String conflictWorkShiftName){
		this.isConflict = isConflict;
		this.conflictWorkShiftId = conflictWorkShiftId;
		this.conflictWorkShiftName = conflictWorkShiftName;
	}

	public Boolean getConflict() {
		return isConflict;
	}

	public void setConflict(Boolean conflict) {
		isConflict = conflict;
	}

	public String getConflictWorkShiftId() {
		return conflictWorkShiftId;
	}

	public void setConflictWorkShiftId(String conflictWorkShiftId) {
		this.conflictWorkShiftId = conflictWorkShiftId;
	}

	public String getConflictWorkShiftName() {
		return conflictWorkShiftName;
	}

	public void setConflictWorkShiftName(String conflictWorkShiftName) {
		this.conflictWorkShiftName = conflictWorkShiftName;
	}
}
