package cm.clear.qmerchant.modules.tables.events.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.interfaces.QListChangeManager;
import cm.clear.qmerchant.common.interfaces.QNavigationManager;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

public class EventMessenger {
    private static EventMessenger Instance;
    @Nullable
    protected NavigationListener navigationListener;
    @Nullable
    protected ListChangeObserver listChangeObserver;

    private final QNavigationManager navigationManager = new QNavigationManager() {
        @Nullable
        @Override
        public NavigationListener getNavigationListener() {
            return navigationListener;
        }
    };

    private final QListChangeManager listChangeManager = new QListChangeManager() {
        @Nullable
        @Override
        public ListChangeObserver getListChangeObserver() {
            return listChangeObserver;
        }
    };

    public void setListChangeObserver(@Nullable ListChangeObserver listChangeObserver) {
        this.listChangeObserver = listChangeObserver;
    }

    public void setNavigationListener(@Nullable NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    @Nullable
    public QNavigationManager getNavigationManager() {
        return navigationManager;
    }

    @NonNull
    public QListChangeManager getListChangeManager() {
        return listChangeManager;
    }

    @NonNull
    public static EventMessenger getInstance() {
        if (Instance==null)
            Instance = new EventMessenger();
        return Instance;
    }
}
