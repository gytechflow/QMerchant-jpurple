package cm.clear.qmerchant.common.actionablebuttons;

import androidx.annotation.NonNull;

public class ResourceResult {

    private String resourceId;
    private String resultMessage;
    private Boolean successful;

    public ResourceResult(@NonNull String resourceId, @NonNull String resultMessage, @NonNull Boolean successful) {
        this.resourceId = resourceId;
        this.resultMessage = resultMessage;
        this.successful = successful;
    }


    public String getResourceId() {
        return resourceId;
    }

    public ResourceResult setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public ResourceResult setSuccessful(Boolean successful) {
        this.successful = successful;
        return this;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public ResourceResult setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
        return this;
    }
}
