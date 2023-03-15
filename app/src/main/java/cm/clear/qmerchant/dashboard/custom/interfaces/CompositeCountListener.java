package cm.clear.qmerchant.dashboard.custom.interfaces;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.dashboard.custom.abstractclasses.CompositeDashBoardCounter;

public interface CompositeCountListener extends SimpleCountListener{
    void onCompositeCountChange(@NonNull ITotalCounterAddon totalCounterAddon);
}
