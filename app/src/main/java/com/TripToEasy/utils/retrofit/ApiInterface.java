package com.TripToEasy.utils.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ptbr-1167 on 4/10/17.
 */

public interface ApiInterface {
    @Multipart
    @POST("user/profile")
    Call<ResponseBody> updateProfile(@Part("title") RequestBody title,
                                     @Part("first_name") RequestBody firstname,
                                     @Part("last_name") RequestBody lastname,
                                     @Part("country_code") RequestBody countrycode,
                                     @Part("phone") RequestBody mobile,
                                     @Part("address") RequestBody address,
                                     @Part("user_id") RequestBody user_id,
                                     @Part MultipartBody.Part image);

}
