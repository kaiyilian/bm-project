package com.bumu.arya.admin.operation.controller.command;

import com.bumu.arya.news.constants.NewsConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel
public class NewsCommand {
    /**
     * ID
     */
    @JsonProperty("ID")
    String id;

    /**
     * 标题
     *
     */
    @JsonProperty("TITLE")
    String title;

    /**
     * 链接
     */
    @JsonProperty("URL")
    String uri;

    /**
     * 图片资源路径
     */
    @JsonProperty("IMG_SRC")
    String imgSrc;

    /**
     * 类型
     */
    @JsonProperty("TYPE")
    NewsConstants.NewsType type;

    /**
     * 新闻来源网站名称，如：新浪、腾讯、今日头条
     */
    @JsonProperty("SOURCE_NAME")
    private String soureceName;

    /**
     * 新闻来源网站地址，如http://news.qq.com
     */
    @JsonProperty( "SOURCE_URL")
    private String source_url;

    /**
     * 是否被选中，可能用于每天显示5跳数据
     */
    @JsonProperty( "IS_CHOOSE")
    private Integer isChoose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public NewsConstants.NewsType getType() {
        return type;
    }

    public void setType(NewsConstants.NewsType type) {
        this.type = type;
    }

    public String getSoureceName() {
        return soureceName;
    }

    public void setSoureceName(String soureceName) {
        this.soureceName = soureceName;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public Integer getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Integer isChoose) {
        this.isChoose = isChoose;
    }
}

