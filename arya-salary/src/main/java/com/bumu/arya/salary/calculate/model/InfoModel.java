package com.bumu.arya.salary.calculate.model;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 保存薪资计算过程中出现的提示信息，用于显示给最终用户。
 * TODO
 * Created by allen on 2017/7/7.
 */
public class InfoModel {

    // 行数 -> 一行中出现的提示信息
    Map<Integer, List<Info>> infoOfRows = new HashMap<>();

    public void addInfo(int row, String msg) {
        addInfo(row, msg, StringUtils.EMPTY);
    }

    public void addInfo(int row, String msg, String detail) {
        List<Info> infos = infoOfRows.get(row);

        if (infos == null) {
            infos = new ArrayList<>();
            infoOfRows.put(row, infos);
        }

        Info info = new Info(msg, detail);
        infos.add(info);
    }

    public boolean hasErrors(int row) {
        return infoOfRows.containsKey(row);
    }

    /**
     * 返回指定行标号的提示信息，如果不存在则返回 null
     * @param row
     * @return
     */
    public List<Info> getInfos(int row) {
        return infoOfRows.get(row);
    }

    /**
     * 获取存在提示信息的所有行标号
     * @return
     */
    public Set<Integer> getInfoRowNums() {
        return infoOfRows.keySet();
    }

    public String getErrorsString(int row) {
        // TODO 格式化多个错误信息
        throw new NotImplementedException("TODO");
    }

    public int size() {
        return infoOfRows.size();
    }

    /**
     * 记录信息的描述（必要）和详细内容（可选）
     */
    public static class Info {
        String msg;
        String detail;

        public Info(String msg) {
            this.msg = msg;
        }

        public Info(String msg, String detail) {
            this.msg = msg;
            this.detail = detail;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "msg='" + msg + '\'' +
                    ", detail='" + detail + '\'' +
                    '}';
        }
    }
}
