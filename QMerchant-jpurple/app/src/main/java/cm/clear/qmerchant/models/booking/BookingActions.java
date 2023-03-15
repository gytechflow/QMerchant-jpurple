package cm.clear.qmerchant.models.booking;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.AffectTable;
import cm.clear.qmerchant.models.booking.bookingStates.SuppStatusHelper;
import cm.clear.qmerchant.models.category.Category;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingActions {


    private static final String TAG = BookingActions.class.getName();

    @NonNull
    public static LiveData<ResourceResult> createBooking(@NonNull Booking booking) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        ApiUtil.getBookingService().createBooking(booking).enqueue(new QCallback<Integer>() {
            @Override
            public void responseSuccessful(Call<Integer> call, Response<Integer> response) {
                result.setValue(new ResourceResult("responseSuccessful", response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call<Integer> call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::createBooking: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult("requestFailure", Objects.requireNonNull(t.getLocalizedMessage()), false));
            }

            @Override
            public void responseUnsuccessful(Call<Integer> call, Response<Integer> response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::createBooking: " + response.raw().message());
                result.setValue(new ResourceResult("responseUnsuccessful", response.raw().message(), false));
            }
        });

        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> updateBooking(@NonNull Booking booking) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        ApiUtil.getBookingService().updateBooking(booking.getId(), booking).enqueue(new QCallback<Booking>() {
            @Override
            public void responseSuccessful(Call<Booking> call, Response<Booking> response) {
                result.setValue(new ResourceResult("responseSuccessful", response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call<Booking> call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::updateBooking: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult("requestFailure", Objects.requireNonNull(t.getLocalizedMessage()), false));
            }

            @Override
            public void responseUnsuccessful(Call<Booking> call, Response<Booking> response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::updateBooking: " + response.raw().message());
                result.setValue(new ResourceResult("responseUnsuccessful", response.raw().message(), false));
            }
        });

        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> removeResource(@NonNull String bookingId, @NonNull String resourceId) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<String> call = ApiUtil.getBookingService().removeResource(bookingId, resourceId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                if (response.body().toString().contains("200"))
                    result.setValue(new ResourceResult(resourceId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::removeResource: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult(resourceId, t.getLocalizedMessage(), true));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::removeResource: " + response.raw().message());
                result.setValue(new ResourceResult(resourceId, response.raw().message(), true));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);

        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> removeAllResources(String bookingId) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getBookingService().removeAllResources(bookingId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(bookingId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::removeAllResources: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult(bookingId, t.getLocalizedMessage(), true));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::removeAllResources: " + response.raw().message());
                result.setValue(new ResourceResult(bookingId, response.raw().message(), true));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);

        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> endEvent(String bookingId) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getBookingService().endBooking(bookingId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(bookingId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::endEvent: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult(bookingId, t.getLocalizedMessage(), false));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::endEvent: " + response.raw().message());
                result.setValue(new ResourceResult(bookingId, response.raw().message(), false));
            }
        };
        CallAndCallback<Object> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> addResource(String bookingId, String resourceId) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        AffectTable affectTable = new AffectTable(resourceId, bookingId);
        Call<Object> call = ApiUtil.getBookingService().assignResource(affectTable);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(resourceId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::addResource: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult(resourceId, t.getLocalizedMessage(), false));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::addResource: " + response.raw().message());
                result.setValue(new ResourceResult(resourceId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> confirmBooking(String bookingId) {
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getBookingService().confirmBooking(bookingId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(bookingId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                Log.e("JPurple", TAG + "::requestFailure::confirmBooking: " + t.getLocalizedMessage());
                result.setValue(new ResourceResult(bookingId, t.getLocalizedMessage(), false));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                Log.e("JPurple", TAG + "::responseUnsuccessful::confirmBooking: " + response.raw().message());
                result.setValue(new ResourceResult(bookingId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    @NonNull
    public static LiveData<ResourceResult> setSuppStatus(@NonNull String bookingId, @NonNull Category category) {
        //Log.d(TAG, "setSuppStatus() called with: bookingId = [" + bookingId + "], status_id = [" + status_id + "]");
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        setSuppStatus(bookingId, category, new CustomCallback() {
            @Override
            public void positive(@NonNull String message) {
                result.setValue(new ResourceResult(bookingId, message, true));
            }

            @Override
            public void negative(@NonNull String message) {
                result.setValue(new ResourceResult(bookingId, message, false));
            }
        });
        /*removeAllOtherCategories(new CustomCallback() {
            @Override
            public void positive(@NonNull String message) {

            }

            @Override
            public void negative(@NonNull String message) {
                result.setValue(new ResourceResult(bookingId, message, false));
            }
        }, bookingId);*/
        return result;
    }

    public static void setSuppStatus(@NonNull String bookingId, @NonNull Category category, @NonNull CustomCallback callback) {
        removeAllOtherCategories(bookingId, category, new CustomCallback() {
            @Override
            public void positive(@NonNull String message) {
                ApiUtil.getBookingService().addCategoryToBooking(Integer.parseInt(category.getId()), "actioncomm", bookingId).enqueue(new QCallback<Object>() {
                    @Override
                    public void responseSuccessful(Call<Object> call, Response<Object> response) {
                        callback.positive(response.body().toString());

                    }

                    @Override
                    public void requestFailure(Call<Object> call, Throwable t) {
                        Log.e("JPurple", TAG + "::requestFailure::setSuppStatus: " + t.getLocalizedMessage());
                        callback.negative(t.getLocalizedMessage());
                    }

                    @Override
                    public void responseUnsuccessful(Call<Object> call, Response<Object> response) {
                        Log.e("JPurple", TAG + "::responseUnsuccessful::setSuppStatus: " + response.raw().message());
                        callback.negative(response.raw().message());
                    }
                });
            }

            @Override
            public void negative(@NonNull String message) {
                callback.negative(message);
            }
        });
    }

    private static void removeAllOtherCategories(@NonNull String booking_id, @NonNull Category category_to_add, CustomCallback callback) {
        ApiUtil.getBookingService().getCategoriesForBookingById(Integer.parseInt(booking_id), "actioncomm").enqueue(new QCallback<List<Category>>() {
            @Override
            public void responseSuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                List<String> categories_to_check = new ArrayList<>();
                List<Category> categories = SuppStatusHelper.getCategoriesToRemove(response.body(), category_to_add);
                //List<Category> categories = response.body();
                if (categories.isEmpty())
                    callback.positive("yay");
                for (Category category : categories) {
                    categories_to_check.add(category.getId());
                }
                for (Category category : categories) {
                    removeOneCategory(new CustomCallback() {
                        @Override
                        public void positive(@NonNull String message) {
                            categories_to_check.remove(message);
                            if (categories_to_check.isEmpty()) {
                                callback.positive("yay");
                            }
                        }

                        @Override
                        public void negative(@NonNull String message) {
                            callback.negative(message);
                        }
                    }, booking_id, category.getId());
                }
            }

            @Override
            public void requestFailure(Call<List<Category>> call, Throwable t) {
                callback.negative(Objects.requireNonNull(t.getLocalizedMessage()));
                Log.e("JPurple", TAG + "::requestFailure: " + t.getLocalizedMessage());
            }

            @Override
            public void responseUnsuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.raw().message().contains("Not Found"))
                    callback.positive("yay");
                else callback.negative(Objects.requireNonNull(response.raw().message()));
                Log.e("JPurple", TAG + "::responseUnsuccessful: " + response.raw().message());
            }
        });
    }

    private static void removeOneCategory(CustomCallback callback, @NonNull String booking_id, @NonNull String category_id) {
        ApiUtil.getBookingService().removeCategoryFromBooking(category_id, "actioncomm", booking_id).enqueue(new QCallback<Object>() {
            @Override
            public void responseSuccessful(Call<Object> call, Response<Object> response) {
                callback.positive(category_id);
            }

            @Override
            public void requestFailure(Call<Object> call, Throwable t) {
                callback.negative(Objects.requireNonNull(t.getLocalizedMessage()));
                Log.e("JPurple", TAG + "::requestFailure: " + t.getLocalizedMessage());
            }

            @Override
            public void responseUnsuccessful(Call<Object> call, Response<Object> response) {
                callback.negative(Objects.requireNonNull(response.raw().message()));
                Log.e("JPurple", TAG + "::responseUnsuccessful: " + response.raw().message());
            }
        });
    }
}
