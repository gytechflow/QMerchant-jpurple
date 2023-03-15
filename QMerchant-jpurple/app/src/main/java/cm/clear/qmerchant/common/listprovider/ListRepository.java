package cm.clear.qmerchant.common.listprovider;

public interface ListRepository {
    void listBottomReached();
    void reload();
    boolean isLoading();
}
