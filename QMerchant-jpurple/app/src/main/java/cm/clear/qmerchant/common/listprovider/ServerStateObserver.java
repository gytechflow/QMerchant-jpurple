package cm.clear.qmerchant.common.listprovider;

public interface ServerStateObserver {
    default void onUpdate(){
        onChange();
    };
    default void onDelete(){
        onChange();
    };
    default void onCreate(){
        onChange();
    };
    public abstract void onChange();
}
