package party.sicef.borderless.api;

import party.sicef.borderless.api.request.BaseRequest;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.LogInResponse;
import party.sicef.borderless.api.response.NewResponse;
import party.sicef.borderless.api.response.NewsReponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.api.response.RefugeResponse;
import party.sicef.borderless.api.response.RefugeesResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ahuskano on 14.11.2015..
 */
public interface RestAPI {

    String LOGIN_METHOD="/login";
    String OFFERS="/offers";
    String REFUGEES="/refugees";
    String REQUESTS="/requests";
    String NEWS="/news";
    String HEADER="application/json";

    String API_PREFIX="api";
    String API_LOCATION="http://188.166.68.172/"+API_PREFIX;
    String web="http://sicef.party";


    @POST(LOGIN_METHOD)
    void logIn(@Body BaseRequest body,@Header("Content-type") String authorization, Callback<LogInResponse> callback);

    @GET(OFFERS)
    void getOffers(@Header("Content-type") String authorization, Callback<OffersResponse> callback);

    @GET("/user/profile"+OFFERS)
    void getMyOffers(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,Callback<OffersResponse> callback);

    @POST(OFFERS+"/{id}/accept")
    void sendOffer(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,@Path("id") int id, Callback<BaseResponse> callback);

    @GET(REFUGEES)
    void getRefugees(@Header("Content-type") String authorization, Callback<RefugeesResponse> callback);

    @GET(REFUGEES+"/{id}")
    void getRefugee(@Header("Content-type") String authorization,@Path("id") String id, Callback<RefugeResponse> callback);


    @GET(REQUESTS)
    void getRequests(@Header("Content-type") String authorization, Callback<OffersResponse> callback);

    @GET("/user/profile"+REQUESTS)
    void getMyRequests(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,Callback<OffersResponse> callback);

    @POST(REQUESTS+"/{id}/accept")
    void sendRequest(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,@Path("id") int id, Callback<BaseResponse> callback);

    @GET(NEWS)
    void getNews(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,Callback<NewsReponse> callback);


    @GET(NEWS+"/{id}")
    void getNew(@Header("Content-type") String authorization, @Header("X-Api-Token") String token,@Path("id") int id, Callback<NewResponse> callback);

}