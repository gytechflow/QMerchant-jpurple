package cm.clear.qmerchant.remote;

import cm.clear.qmerchant.models.Mail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SetupRequestInterface {

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("setup/conf/{constantname}")
    Call<String> getStatus(
            @Path("constantname") String constantname
    );

    //
    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("setup/conf/{constantname}/{constantvalue}")
    Call<Void> postStatus(
            @Path("constantname") String constantname,
            @Path("constantvalue") String constantvalue


    );
//

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @GET("setup/mailtemplate")
    Call<Mail> getMail(
            @Query(value = "label") String label

    );
//

    /*@Headers({
            "Accept: application/json",
            "DOLAPIKEY: 12825d52ce2a321c7a64ff8bde75ba135af2a26a"
    })*/
    @POST("setup/mailtemplate/update")
    Call<Void> postMail(
            @Body Mail template

    );
}
