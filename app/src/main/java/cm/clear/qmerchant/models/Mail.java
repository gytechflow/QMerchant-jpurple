package cm.clear.qmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mail implements Serializable {
    @SerializedName("rowid")
    @Expose
    private String rowid;

    @SerializedName("module")
    @Expose
    private String module;

    @SerializedName("label")
    @Expose
    private  String label;

    @SerializedName("type_template")
    @Expose
    private  String type_template;

    @SerializedName("lang")
    @Expose
    private  String lang;

    @SerializedName("fk_user")
    @Expose
    private  String fk_user;

    @SerializedName("private")
    @Expose
    private  String private_;

    @SerializedName("position")
    @Expose
    private  String position;

    @SerializedName("topic")
    @Expose
    private  String topic;

    @SerializedName("joinfiles")
    @Expose
    private  String joinfiles;

    @SerializedName("content_lines")
    @Expose
    private  String content_lines;

    @SerializedName("content")
    @Expose
    private  String content;

    @SerializedName("enabled")
    @Expose
    private  String enabled;

    @SerializedName("active")
    @Expose
    private  String active;

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType_template() {
        return type_template;
    }

    public void setType_template(String type_template) {
        this.type_template = type_template;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFk_user() {
        return fk_user;
    }

    public void setFk_user(String fk_user) {
        this.fk_user = fk_user;
    }

    public String getPrivate_() {
        return private_;
    }

    public void setPrivate_(String private_) {
        this.private_ = private_;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getJoinfiles() {
        return joinfiles;
    }

    public void setJoinfiles(String joinfiles) {
        this.joinfiles = joinfiles;
    }

    public String getContent_lines() {
        return content_lines;
    }

    public void setContent_lines(String content_lines) {
        this.content_lines = content_lines;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
