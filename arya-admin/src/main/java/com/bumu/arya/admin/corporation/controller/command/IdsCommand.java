package com.bumu.arya.admin.corporation.controller.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 16/9/2.
 */
public class IdsCommand {
    List<IdCommand> ids;

    public IdsCommand() {
        this.ids = new ArrayList<>();
    }

    public List<IdCommand> getIds() {
        return ids;
    }

    public void setIds(List<IdCommand> ids) {
        this.ids = ids;
    }

    public static class IdCommand {
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    /**
     * 获取id字符串数组
     *
     * @return
     */
    public List<String> getIdList() {
        List<String> ids = new ArrayList<>();
        for (IdCommand idCommand : this.ids) {
            ids.add(idCommand.getId());
        }
        return ids;
    }
}
