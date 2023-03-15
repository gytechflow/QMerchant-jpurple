package cm.clear.qmerchant.modules.orders.data.OrderStates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.actionablebuttons.ClientAddon;
import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.models.Thirdparty;

public abstract class OrderState extends ClientAddon {

    public static final int DEFAULT_VALUE = 0;
    public static final int ORDER_CONFIRMED = 1;
    public static final int ORDER_CANCELED = -1;
    public static final int ORDER_IN_PREPARATION = 2;
    public static final int ORDER_DELIVERED = 3;
    public static final int ALL_ORDERS = -5;

    private final Map<Integer, Integer> confirmTextList = new HashMap<Integer, Integer>(){{
        put(DEFAULT_VALUE, R.string.confirm_text);
        put(ORDER_CONFIRMED, R.string.to_kitchen);
        put(ORDER_IN_PREPARATION, R.string.to_delivery);
        put(ORDER_DELIVERED, R.string.delivered);
        put(ORDER_CANCELED, R.string.canceled);
    }};

    @NonNull
    public static OrderState getOrderState(int state){
        switch (state){
            case DEFAULT_VALUE:{
             return new NewOrderState(state);
            }
            case ORDER_DELIVERED:{
                return new DeliveredOrderState(state);
            }
            case ORDER_CONFIRMED:{
                return new ConfirmedOrderState(state);
            }
            case ORDER_IN_PREPARATION:{
                return new ProcessingOrderState(state);
            }
            case ORDER_CANCELED:{
                return new CanceledOrderState(state);
            }
            default: return new ConfirmedOrderState(state);
        }
    }

    private final int status;
    private Thirdparty client;

    protected OrderState(int status) {
        this.status = status;
    }

    @Override
    public int getAssignButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int getCancelButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.GONE;
    }

    @NonNull
    @Override
    public String getAssignText(@NonNull Context context) {
        return context.getString(R.string.assign);
    }

    @NonNull
    @Override
    public String getConfirmText(@NonNull Context context) {
        return context.getString(getConfirmTextList().get(getStatus()));
    }

    @NonNull
    @Override
    public String getCancelText(@NonNull Context context) {
        return context.getString(R.string.close_text);
    }

    @NonNull
    @Override
    public ColorStateList getRefColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.booking_table_selected);
    }

    @NonNull
    @Override
    public ColorStateList getAssignButtonColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.booking_table_selected);
    }

    @NonNull
    @Override
    public ColorStateList getConfirmButtonColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.secondaryColor);
    }

    @NonNull
    @Override
    public ColorStateList getCancelButtonColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.negative_red);
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getAssignResult() {
        return new MutableLiveData<>();
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getConfirmResult(@NonNull String id) {
        return new MutableLiveData<>();
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getCancelResult(@NonNull String id) {
        return new MutableLiveData<>();
    }
    
    @Nullable
    public Thirdparty getClient() {
        return client;
    }

    public void setClient(@NonNull Thirdparty client) {
        this.client = client;
    }

    public int getStatus() {
        return status;
    }

    @NonNull
    public Map<Integer, Integer> getConfirmTextList() {
        return confirmTextList;
    }
}
