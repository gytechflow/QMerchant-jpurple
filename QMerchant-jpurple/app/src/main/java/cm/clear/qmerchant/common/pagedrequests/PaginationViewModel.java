package cm.clear.qmerchant.common.pagedrequests;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public abstract class PaginationViewModel extends ViewModel implements Paginates{
    private final int DEFAULT_PAGE = 0;
    private final boolean IS_UPDATE = true;
    private int page = 0;
    private boolean gettingElements = false;
    protected final Context context;

    protected PaginationViewModel(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public boolean isGettingElements() {
        return gettingElements;
    }

    @Override
    public void setGettingElements(boolean gettingElements) {
        this.gettingElements = gettingElements;
    }
}
