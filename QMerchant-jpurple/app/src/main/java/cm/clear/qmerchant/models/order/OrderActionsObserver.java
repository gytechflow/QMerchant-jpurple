package cm.clear.qmerchant.models.order;

public interface OrderActionsObserver {
    default void onConfirm(boolean isActionStart){onAction(isActionStart);};
    default void onCancel(boolean isActionStart){onAction(isActionStart);};
    default void onClose(boolean isActionStart){onAction(isActionStart);};
    default void onPrepare(boolean isActionStart){onAction(isActionStart);};
    void onAction(boolean isActionStart);
}
