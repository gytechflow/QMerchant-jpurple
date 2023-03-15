package cm.clear.qmerchant.remote;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.models.AffectTable;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.QBookingType;
import cm.clear.qmerchant.models.category.Category;
import cm.clear.qmerchant.models.qtable.QTable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingsRequestInterface {

    @NonNull
    @Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })
    @GET("status")
    Call<Object> ping(
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents")
    Call<List<Booking>> getBookings(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "sqlfilters") String sqlFilter

    );


    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/count")
    Call<String> getBookingsCount(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "sqlfilters") String sqlFilter

    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/{id}")
    Call<Booking> getBooking(
            @Path(value = "id") String id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents")
    Call<List<Booking>> getBookings(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "page") String page,
            @Query(value = "sqlfilters") String sqlFilter
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/table")
    Call<List<QTable>> getTables(
            @Query(value = "page") String page,
            @Query(value = "limit") String limit,
            @Query(value = "sqlfilters") String sqlfilters
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/table")
    Call<List<QTable>> getTables();

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/typebooking")
    Call<List<QBookingType>> getBookingTypes();

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/fcheck_the_free_table")
    Call<List<QTable>> getFreeTables(
            @Query(value = "datestart") String datestart,
            @Query(value = "dateend") String dateend
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/list_free_table_for_reservation/{id}")
    Call<List<QTable>> getFreeTables(
            @Path("id") int id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @POST("agendaevents/book/{id}")
    Call<Object> createBookingWithResource(
            @Path("id") int id,
            @Body Booking booking
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @POST("agendaevents/addevent")
    Call<Integer> createBooking(
            @Body Booking booking
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @PUT("agendaevents/{id}")
    Call<Booking> updateBooking(
            @Path("id") String id,
            @Body @NonNull Booking booking
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/endevent")
    Call<Object> endBooking(
            @Query(value = "event_id") String event_id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/confirmevent")
    Call<Object> confirmBooking(
            @Query(value = "event_id") String event_id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @POST("agendaevents/affect_table")
    Call<Object> assignResource(
            @Body AffectTable affectTable
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/unlinktable")
    Call<String> removeResource(
            @Query(value = "event_id") String event_id,
            @Query(value = "table_id") String table_id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/unlinkalltable")
    Call<Object> removeAllResources(
            @Query(value = "event_id") String event_id
    );

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @NonNull
    @GET("agendaevents/check_resource_for_event/{id}")
    Call<List<QTable>> getAssignedResources(
            @Path("id") int booking_id
    );

    @NonNull
    @GET("agendaevents/listevent_for_resource/{id}")
    Call<List<Booking>> getEventsForResource(
            @Path("id") int resourceId,
            @Query(value = "sqlfilters") @NonNull String sqlfilter
    );

    /**
     * List categories of an object
     * @param booking_id
     * @param actioncomm
     * @param sqlfilter
     * @param sortorder
     * @param limit
     * @param page
     * @return
     */
    @NonNull
    @GET("categories/object/{type}/{id}")
    Call<List<Category>> getCategoriesForBookingById(
            @Path("id") int booking_id,
            @Path("type") @NonNull String actioncomm,
            @Query(value = "sqlfilters") @NonNull String sqlfilter,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "page") @NonNull String page
    );

    /**
     * List categories of an object
     * @param booking_id
     * @param actioncomm
     * @return
     */

    @NonNull
    @GET("categories/object/{type}/{id}")
    Call<List<Category>> getCategoriesForBookingById(
            @Path("id") int booking_id,
            @Path("type") @NonNull String actioncomm
    );

    /**
     * Unlink an object from a category by id
     * @param category_id
     * @param actioncomm
     * @param booking_id
     * @return
     */
    @NonNull
    @DELETE("categories/{id}/objects/{type}/{object_id}")
    Call<Object> removeCategoryFromBooking(
            @Path("id") @NonNull String category_id,
            @Path("type") @NonNull String actioncomm,
            @Path("object_id") @NonNull String booking_id
    );

    /**
     * Link an object to a category by id
     * @param category_id
     * @param actioncomm
     * @param booking_id
     * @return
     */
    @NonNull
    @POST("categories/{id}/objects/{type}/{object_id}")
    Call<Object> addCategoryToBooking(
            @Path("id") int category_id,
            @Path("type") @NonNull String actioncomm,
            @Path("object_id") @NonNull String booking_id
    );
}
