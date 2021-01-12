package com.mudhales.haqdarshak.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public class RetrofitInterfaces {
    // Created interface to fetch list data by SM
    public interface IGetInfoData {
        @GET("everything?q=bitcoin&from=2020-12-30&sortBy=publishedAt&apiKey=4893ef496564434ab0daf0e51b60d638")
        Call<ResponseBody> getInfo();
    }
}
