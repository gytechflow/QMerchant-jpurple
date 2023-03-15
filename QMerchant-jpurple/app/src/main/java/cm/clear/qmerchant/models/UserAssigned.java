package cm.clear.qmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class UserAssigned {

    @SerializedName("1")
    @Expose
    private AssignedUser assignedUser;

    public UserAssigned() {
    }

    public AssignedUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignedUser assignedUser) {
        this.assignedUser = assignedUser;
    }
}
