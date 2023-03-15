package cm.clear.qmerchant.common.listviewmanagement;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cm.clear.qmerchant.interfaces.ResponseListener;

public abstract class ItemStateUpdater implements IItemStateUpdater, ResponseListener {
    private final Map<Object, IItemStateHolder> stateHolderMap = new HashMap<>();
    private final IAdapterItemChangeListener itemChangeListener;

    protected ItemStateUpdater(@NonNull List objects, @NonNull IAdapterItemChangeListener itemChangeListener) {
        this.itemChangeListener = itemChangeListener;
        for (Object object : objects) {
            register(object);
        }
    }

    protected ItemStateUpdater(@NonNull IAdapterItemChangeListener itemChangeListener) {
        this.itemChangeListener = itemChangeListener;
    }

    @Override
    public void register(@NonNull Object o) {
        if (!getStateHolderMap().containsKey(o)){
            addToMap(o);
            loadExtras(o);
        }
    }

    @NonNull
    protected Map<Object, IItemStateHolder> getStateHolderMap() {
        return stateHolderMap;
    }

    @NonNull
    private IAdapterItemChangeListener getItemChangeListener() {
        return itemChangeListener;
    }

    @Override
    public void update(int position) {
        getItemChangeListener().onItemChanged(position);
    }

    @NonNull
    @Override
    public IItemState getObjectState(@NonNull Object o, int position) {
//        Log.d("J-Purple", "getObjectState() called with: o = [" + o + "], position = [" + position + "] found = ["+stateHolderMap.containsKey(o)+"] state = ["+Objects.requireNonNull(getStateHolderMap().get(o)).getState().toString()+"]");
        Objects.requireNonNull(getStateHolderMap().get(o)).setPosition(position);
        return Objects.requireNonNull(getStateHolderMap().get(o)).getState();
    }

    @Override
    public void reloadState(@NonNull Object o) {
//        register(o);
    }

    @Override
    public void reloadRequest() {
        for (Object o : stateHolderMap.keySet()) {
            loadExtras(o);
        }
    }

    public int getNewItemPosition(int position){
        return 0;
    };
}
