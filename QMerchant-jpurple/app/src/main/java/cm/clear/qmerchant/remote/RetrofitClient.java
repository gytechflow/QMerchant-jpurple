package cm.clear.qmerchant.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import cm.clear.qmerchant.common.LongDefault0Adapter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitPing = null;


    /**
     *
     * @param url
     * @return
     */
    public static Retrofit getClientX(String url) {

        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

        Interceptor interceptor = chain -> {
            //okhttp3.Request request = chain.request();
            //request.newBuilder()
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("DOLAPIKEY", ApiUtil.getApiKey())
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(request);
        };
        httpBuilder.addInterceptor( interceptor );

        /*
        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("DOLAPIKEY", ApiUtil.getApiKey())
                        .header("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });
        */

        //Todo this should be done better
        OkHttpClient httpClient = httpBuilder
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(createGsonConverter(Long.class, new LongDefault0Adapter()))
                    .client(httpClient)
                    .build();

        } catch (IllegalArgumentException e) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUtil.DUMMY_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    /**
     *
     * @param base_url
     * @return
     */
    public static Retrofit getClient(String base_url) {
        if (retrofit == null) {

            Interceptor interceptor = chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("DOLAPIKEY", ApiUtil.getApiKey())
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            };

            /*
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            */

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    //.addInterceptor(logging)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(createGsonConverter(Long.class, new LongDefault0Adapter()))
                    //.addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getPingClient() {
        if (retrofitPing == null) {
            retrofitPing = new Retrofit.Builder().baseUrl(ApiUtil.DEFAULT_API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofitPing;
    }

    /**
     *
     * @param url
     * @return
     */
    public static Retrofit getLateClient(String url) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(4, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }

        return retrofit;
    }

    /**
     *
     * @param url
     * @return
     */
    public static Retrofit getRetrofit(String url) {
        //if(retrofit == null){
        retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        //}

        return retrofit;
    }

    /**
     *
     * @param type
     * @param typeAdapter
     * @return
     */
    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);

    }

}