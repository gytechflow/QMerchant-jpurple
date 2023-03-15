package cm.clear.qmerchant.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QRoom implements Serializable {
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ref")
    @Expose
    private String ref;

    @SerializedName("capacity")
    @Expose
    private Integer capacity;

    @SerializedName("description")
    @Expose
    private String description;
}
