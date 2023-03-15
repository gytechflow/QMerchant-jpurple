package cm.clear.qmerchant.common.listviewmanagement.stateStorage;

import androidx.annotation.Nullable;

public class NotAKeyHolderException extends Exception {
    @Nullable
    @Override
    public String getMessage() {
        return "the component calling this method does not extend StateKeyHolder or is not registered";
    }
}
