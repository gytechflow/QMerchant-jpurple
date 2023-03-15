package cm.clear.qmerchant.models.qtable;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QTable implements Serializable {

    private MutableLiveData<Boolean> state = new MutableLiveData<>(false);

    @NonNull
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("ref")
    @Expose
    private String ref;

    @SerializedName("capacity")
    @Expose
    private int capacity;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("room")
    @Expose
    private String room;

    public QTable() {
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return getRef();
    }

    public MutableLiveData<Boolean> getState() {
        return state;
    }

    public void setState(MutableLiveData<Boolean> state) {
        this.state = state;
    }
}
