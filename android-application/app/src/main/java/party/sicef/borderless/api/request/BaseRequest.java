package party.sicef.borderless.api.request;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class BaseRequest {

    @Expose
    private String method;

    @Expose
    private long id;

    public BaseRequest() {

    }

    public BaseRequest(String method) {
        this.method = method;
    }



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
