package cm.clear.qmerchant.models.coupon;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coupon implements Serializable {
    @NonNull
    @SerializedName("id")
    @Expose
    private String id; // id of said coupon

    @SerializedName("coupon_token")
    @Expose
    private String code; // the code/token of the coupon

    @SerializedName("email")
    @Expose
    private String email; // email to be notified

    @SerializedName("description")
    @Expose
    private String description; // complementary text on token

    @SerializedName("coupon_state")
    @Expose
    private Integer state_code; // an int value which corresponds to a coupon's state(unused/used/canceled)

    @SerializedName("amount")
    @Expose
    private Integer value; //

    @SerializedName("used_date_tms")
    @Expose
    private Long dateUsedTms; // date at which the coupon was used/canceled

    @SerializedName("expiry_date_tms")
    @Expose
    private Long expiryDateTms; // date at which the coupon should have naturally expired

    public Coupon() {
    }

    public Coupon(@NonNull Coupon other) {
        this.id = other.id;
        this.code = other.code;
        this.value = other.value;
        this.state_code = other.state_code;
        this.dateUsedTms = other.dateUsedTms;
        this.expiryDateTms = other.expiryDateTms;
        this.email = other.email;
        this.description = other.description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getState_code() {
        return state_code;
    }

    public void setState_code(Integer state_code) {
        this.state_code = state_code;
    }

    public Long getDateUsedTms() {
        return dateUsedTms;
    }

    public void setDateUsedTms(Long dateUsedTms) {
        this.dateUsedTms = dateUsedTms;
    }

    public Long getExpiryDateTms() {
        return expiryDateTms;
    }

    public void setExpiryDateTms(Long expiryDateTms) {
        this.expiryDateTms = expiryDateTms;
    }
}
