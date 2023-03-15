package cm.clear.qmerchant.modules.orders.orderDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.models.order.Order;

public class OrderDetailsViewModel {
    private final Order order;
    private Thirdparty thirdparty;
    private ProductsAdapter productsAdapter;


    public OrderDetailsViewModel(@Nullable Order order) {
        this.order = order;
        //this.thirdparty = thirdparty;
        this.productsAdapter = new ProductsAdapter(order==null?new ArrayList<>():new ArrayList<>(order.getLines()));
    }

    @Nullable
    public Order getOrder() {
        return order;
    }

    @Nullable
    public Thirdparty getThirdparty() {
        return thirdparty;
    }

    @NonNull
    public ProductsAdapter getProductsAdapter() {
        return productsAdapter;
    }
}
