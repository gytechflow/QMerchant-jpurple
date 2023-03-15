package cm.clear.qmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssignedUser implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("mandatory")
    @Expose
    private String mandatory;

    @SerializedName("answer_status")
    @Expose
    private String answer_status;

    @SerializedName("transparency")
    @Expose
    private String transparency;

    public AssignedUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getAnswer_status() {
        return answer_status;
    }

    public void setAnswer_status(String answer_status) {
        this.answer_status = answer_status;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }
}
