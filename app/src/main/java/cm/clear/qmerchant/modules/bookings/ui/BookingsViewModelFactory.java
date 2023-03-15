package cm.clear.qmerchant.modules.bookings.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BookingsViewModelFactory implements ViewModelProvider.Factory{

    private final Context context;

    public BookingsViewModelFactory(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookingsViewModel(context);
    }
}
