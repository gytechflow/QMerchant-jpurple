package cm.clear.qmerchant.models.booking.bookingStates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.actionablebuttons.ClientAddon;
import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.models.category.Category;
import cm.clear.qmerchant.models.qtable.QTable;

public abstract class BookingState extends ClientAddon{
    private int totalCapacity;
    private int capacity;
    private List<QTable> assignedTables;
    private List<Category> categories = new ArrayList<>();
    private boolean tablesUpdated = false;
    private boolean tablesLoaded = false;
    private boolean categoriesLoaded = false;
    private List<Runnable> tableStateListeners = new ArrayList<>();
    private List<Runnable> categoryStateListeners = new ArrayList<>();

    public BookingState(int capacity) {
        this.capacity = capacity;
    }

    @NonNull
    public static BookingState getState(@NonNull String percentage, int capacity) {
        switch (percentage) {
            case Booking.PERCENT_0: {
                return new ZeroBookingState(capacity);
            }
            case Booking.PERCENT_25: {
                return new TwentyFiveBookingState(capacity);
            }
            case Booking.PERCENT_50: {
                return new FiftyBookingState(capacity);
            }
            case Booking.PERCENT_100: {
                return new HundredBookingState(capacity);
            }
            default:
                throw new IllegalStateException("Unexpected value: " + percentage);
        }
    }

    @Override
    public int getAssignButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int getCancelButtonVisibility() {
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
        return context.getString(R.string.confirm_text);
    }

    @NonNull
    @Override
    public String getCancelText(@NonNull Context context) {
        return context.getString(R.string.close_text);
    }

    public int getTablesColor() {
        return R.drawable.booking_default_background;
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

    public int getTotalCapacity() {
        if (tablesUpdated)
            if (totalCapacity==0){
                for (QTable assignedTable : assignedTables) {
                    totalCapacity += assignedTable!=null?assignedTable.getCapacity():0;
                }
            }
        return totalCapacity;
    }

    public String getOccupationTally(){
        String total = String.valueOf(getTotalCapacity());
        String sCapacity = String.valueOf(capacity);

        return total +" / "+ sCapacity;
    }

    public String getTablesListString(){
        String resValue = "---";
        if( getAssignedTables() != null){
            if(!getAssignedTables().isEmpty()){

                int size = getAssignedTables().size();
                if( size > 1 ){
                    resValue = size+" Tabs.";
                }
                else {
                    resValue = ""+getAssignedTables().get(0).getRef();
                }
            }
        }

        return resValue;
    }

    @Nullable
    public List<QTable> getAssignedTables() {
        return assignedTables;
    }

    public boolean haveTablesLoaded() {
        return tablesUpdated;
    }

    public boolean hasTables(){
        return true;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @NonNull
    public void setTablesUpdated(boolean tablesUpdated) {
        this.tablesUpdated = tablesUpdated;
    }

    @NonNull
    public void setAssignedTables(@NonNull List<QTable> assignedTables) {
        this.assignedTables = assignedTables;
        notifyTablesListeners();
        setTablesLoaded(true);
    }

    public boolean isTablesLoaded() {
        return tablesLoaded;
    }

    public void setTablesLoaded(boolean tablesLoaded) {
        this.tablesLoaded = tablesLoaded;
    }

    public boolean isCategoriesLoaded() {
        return categoriesLoaded;
    }

    public void setCategoriesLoaded(boolean categoriesLoaded) {
        this.categoriesLoaded = categoriesLoaded;
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getConfirmResult(@NonNull String id) {
//        return BookingActions.confirmBooking(id);
        ResourceResult result = new ResourceResult("","",true);
        return new MutableLiveData<>(result);
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getAssignResult() {
        return new MutableLiveData<>();
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getCancelResult(@NonNull String id) {
        return BookingActions.endEvent(id);
    }

    public void setCategories(@NonNull List<Category> requestedCategory) {
        this.categories = new ArrayList<>(requestedCategory);
        notifyCategoryListeners();
        setCategoriesLoaded(true);
    }

    @NonNull
    public List<Category> getCategories() {
        List<Category> temp = new ArrayList<>();
        temp.add(Category.getNullCategory());
        if (categories.isEmpty())
            return temp;
        return categories;
    }

    public void addCategoryObserver(@NonNull Runnable runnable){
        if (!categoryStateListeners.contains(runnable))
            categoryStateListeners.add(runnable);
    }

    public void addTablesObserver(@NonNull Runnable runnable){
        if (!tableStateListeners.contains(runnable))
            tableStateListeners.add(runnable);
    }

    private void notifyCategoryListeners(){
        for (Runnable categoryStateListener : categoryStateListeners) {
            categoryStateListener.run();
        }
    }

    private void notifyTablesListeners(){
        for (Runnable tableStateListener : tableStateListeners) {
            tableStateListener.run();
        }
    }
}
