package com.bumu.bran.admin.system.command;

/**
 * majun
 */
public class IdVersion {

	private String id;
	private Long version;
	private String name;
	public IdVersion() {
	}
	public IdVersion(String id, Long version) {
		this.id = id;
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
