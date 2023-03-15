package cm.clear.qmerchant.remote;

import java.util.List;

import cm.clear.qmerchant.models.order.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrdersRequestInterface {

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders")
    Call<List<Order>> getOrders(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders")
    Call<List<Order>> getOrders(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "sqlfilters") String sqlfilters
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders/count")
    Call<String> getOrdersCount(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "sqlfilters") String sqlfilters
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders")
    Call<List<Order>> getOrders(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "page") String page,
            @Query(value = "sqlfilters") String sqlfilters
    );


    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders")
    Call<List<Order>> getOrders(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "page") String page,
            @Query(value = "thirdparty_ids") String thirdparty_ids,
            @Query(value = "sqlfilters") String sqlfilters
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("orders/{id}")
    Call<Order> getOrderById(
            @Path(value = "id") String id,
            @Query(value = "contact_list") String contact_list
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("orders/{id}/validate")
    Call<Object> confirmOrder(
            @Path(value = "id") String id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("orders/{id}/cancel")
    Call<Object> cancelOrder(
            @Path(value = "id") String id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("orders/{id}/close")
    Call<Object> closeOrder(
            @Path(value = "id") String id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("orders/{id}/setinvoiced")
    Call<Object> orderTokitchen(
            @Path(value = "id") String id
    );

}
