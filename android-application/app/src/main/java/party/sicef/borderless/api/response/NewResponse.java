package party.sicef.borderless.api.response;

import com.google.gson.annotations.Expose;

import party.sicef.borderless.api.data.NewsData;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class NewResponse extends BaseResponse {

    @Expose
    private NewsData data;

    public NewResponse(String status, String message) {
        super(status, message);
    }

    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}
