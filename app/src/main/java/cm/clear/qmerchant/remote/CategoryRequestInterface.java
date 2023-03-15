package cm.clear.qmerchant.remote;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.models.category.Category;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryRequestInterface {

    @NonNull
    @Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })
    @GET("status")
    Call<Object> ping(
    );

    @NonNull
    @GET("categories")
    Call<List<Category>> getCategories(
            @Query(value = "type") @NonNull String type
    );

    @NonNull
    @GET("categories")
    Call<List<Category>> getCategories(
            @Query(value = "sortfield") String sortfield,
            @Query(value = "sortorder") String sortorder,
            @Query(value = "limit") String limit,
            @Query(value = "page") String page,
            @Query(value = "type") String type,
            @Query(value = "sqlfilters") String sqlfilters
    );

    @NonNull
    @GET("categories/{id}")
    Call<Category> getCategory(
            @Path(value = "id") @NonNull String id
    );

    /**
     * List categories of an object
     * @param id
     * @param type
     * @param sqlfilter
     * @param sortorder
     * @param limit
     * @param page
     * @return
     */
    @NonNull
    @GET("categories/object/{type}/{id}")
    Call<List<Category>> getCategoriesForObjectById(
            @Path("id") int id,
            @Path("type") @NonNull String type,
            @Query(value = "sqlfilters") @NonNull String sqlfilter,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "page") @NonNull String page
    );

    /**
     * List categories of an object
     * @param id
     * @param type
     * @return
     */

    @NonNull
    @GET("categories/object/{type}/{id}")
    Call<List<Category>> getCategoriesForObjectById(
            @Path("id") int id,
            @Path("type") @NonNull String type
    );

    /**
     * Unlink an object from a category by id
     * @param category_id
     * @param type
     * @param object_id
     * @return
     */
    @NonNull
    @DELETE("categories/{id}/objects/{type}/{object_id}")
    Call<Object> removeCategoryFromObject(
            @Path("id") @NonNull String category_id,
            @Path("type") @NonNull String type,
            @Path("object_id") @NonNull String object_id
    );

    /**
     * Link an object to a category by id
     * @param category_id
     * @param type
     * @param object_id
     * @return
     */
    @NonNull
    @POST("categories/{id}/objects/{type}/{object_id}")
    Call<Object> addCategoryToObject(
            @Path("id") int category_id,
            @Path("type") @NonNull String type,
            @Path("object_id") @NonNull String object_id
    );
}
