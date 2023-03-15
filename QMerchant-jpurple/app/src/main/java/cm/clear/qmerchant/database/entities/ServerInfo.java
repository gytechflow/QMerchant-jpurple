package cm.clear.qmerchant.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "serverinfo")
public class ServerInfo {

    @NonNull
    @SerializedName("id")
//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @Expose
    int id;

    @SerializedName("server_url")
    @Expose
    String serverUrl;

    @SerializedName("api_key")
    @Expose
    String apiKey;


    public ServerInfo(String serverUrl, String apiKey) {
        this.serverUrl = serverUrl;
        this.apiKey = apiKey;
    }

    public ServerInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
