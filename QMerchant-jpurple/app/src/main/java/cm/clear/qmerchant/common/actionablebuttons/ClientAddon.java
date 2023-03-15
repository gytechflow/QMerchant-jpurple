package cm.clear.qmerchant.common.actionablebuttons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.io.Serializable;

import cm.clear.qmerchant.models.Thirdparty;

public abstract class ClientAddon implements IModifiableButtons, Serializable {
    private Thirdparty client;

    @Nullable
    public abstract LiveData<ResourceResult> getConfirmResult(@NonNull String id);

    @Nullable
    public Thirdparty getClient() {
        return client;
    }

    public void setClient(@NonNull Thirdparty client) {
        this.client = client;
    }
}
