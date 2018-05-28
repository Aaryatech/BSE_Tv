package com.ats.bse_tv;

import com.ats.bse_tv.bean.ItemData;
import com.ats.bse_tv.bean.LastOrderListData;
import com.ats.bse_tv.bean.NewsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    //public static final String BASE_URL = "http://192.168.2.11:8062/";

    public static final String BASE_URL = "http://132.148.143.124:8080/BSEWebApis/";
    public static final String MY_PREF = "shailTv";

    @POST("user/getItemListForTv")
    Call<ItemData> getAllItems(@Query("start") int start, @Query("pageSize") int pageSize);

    @GET("user/getLastOrderForTv")
    Call<LastOrderListData> getLastOrderItems();

    @GET("getAllNews")
    Call<NewsData> getAllNews();
}
