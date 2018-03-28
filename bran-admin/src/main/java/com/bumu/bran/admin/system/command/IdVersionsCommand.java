package com.bumu.bran.admin.system.command;

import com.bumu.bran.employee.command.ModelCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * majun
 */
public class IdVersionsCommand extends ModelCommand {
	private IdVersion[] idVersions;

	public IdVersion[] getIdVersions() {
		return idVersions;
	}

	public void setIdVersions(IdVersion[] idVersions) {
		this.idVersions = idVersions;
	}

	public List<String> getIds() {
		List<String> ids = new ArrayList<>();
		for (IdVersion idVersion : idVersions) {
			ids.add(idVersion.getId());
		}
		return ids;
	}

	public Map<String, Long> getMap() {
		Map<String, Long> map = new HashMap<>();

		for (IdVersion idVersion : idVersions) {
			map.put(idVersion.getId(), idVersion.getVersion());
		}
		return map;
	}
}
