package cm.clear.qmerchant.models.category;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class StaticCategoryRepo {
    private static final String TAG = StaticCategoryRepo.class.getName();
    protected static List<Category> categories;
    private static Call<List<Category>> call = ApiUtil.getCategoryService().getCategories("actioncomm");
    //private static List<CustomCallback> pending = new ArrayList<>();

    @NonNull
    public static void loadCategories(@NonNull CustomCallback callback) {
        if (categories==null)
            getCategoriesFromServer(callback);
        else callback.positive("loaded");
    }

    private static void getCategoriesFromServer(@NonNull CustomCallback callback) {
        if (call.isExecuted()){
            call.cancel();
            call = call.clone();
        }
        call.enqueue(new QCallback<List<Category>>() {
            @Override
            public void responseSuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                categories = new ArrayList<>(response.body());
                callback.positive("yay");
            }

            @Override
            public void requestFailure(Call<List<Category>> call, Throwable t) {
                Log.e("JPurple",TAG+ "::requestFailure: "+ t.getLocalizedMessage());
                callback.negative("boo");
            }

            @Override
            public void responseUnsuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                callback.negative("boo");
                Log.e("JPurple",TAG+ "::responseUnsuccessful: "+ response.raw().toString());
            }
        });
    }

    @Nullable
    public static List<Category> getCategories() {
        return categories;
    }
}
