package cm.clear.qmerchant.remote;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.models.Thirdparty;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomersRequestInterface {

    @NonNull
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("thirdparties")
    Call<List<Thirdparty>> getCustomers(
            @Query(value = "sortfield") @NonNull String sortfield,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit
    );

    @NonNull
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("thirdparties")
    Call<List<Thirdparty>> getCustomers(
            @Query(value = "sortfield") @NonNull String sortfield,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "sqlfilters") @NonNull String sqlfilters
    );

    @NonNull
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("thirdparties/count")
    Call<String> getCustomersCount(
            @Query(value = "sortfield") @NonNull String sortfield,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "sqlfilters") @NonNull String sqlfilters
    );

    @NonNull
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("thirdparties")
    Call<List<Thirdparty>> getCustomers(
            @Query(value = "sortfield") @NonNull String sortfield,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "page") @NonNull String page,
            @Query(value = "mode") @NonNull String mode,
            @Query(value = "category") @NonNull String category,
            @Query(value = "sqlfilters") @NonNull String sqlfilters
    );


    @NonNull
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("thirdparties/{id}")
    Call<Thirdparty> getCustomerById(
            @Path(value = "id") @NonNull String id
    );

    @NonNull
    @PUT("thirdparties/{id}")
    Call<Thirdparty> updateCustomer(
            @Path(value = "id") @NonNull String id,
            @Body @NonNull Thirdparty client);
}
