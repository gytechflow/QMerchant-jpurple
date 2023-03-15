package cm.clear.qmerchant.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cm.clear.qmerchant.interfaces.QDropDownItem;

public class QBookingType implements Serializable, QDropDownItem {

    @NonNull
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("libelle")
    @Expose
    private String label;

    public QBookingType(@NonNull Integer id, String code, String label) {
        this.id = id;
        this.code = code;
        this.label = label;
    }


    @NonNull
    public String getId() {
        return String.valueOf(id);
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label.toUpperCase();
    }
}
