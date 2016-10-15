package party.sicef.borderless.api.response;

import com.google.gson.annotations.Expose;

import party.sicef.borderless.api.data.RefugeeProfile;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class RefugeResponse extends BaseResponse {

    @Expose
    private RefugeeProfile data;

    public RefugeResponse(String status, String message) {
        super(status, message);
    }

    public RefugeeProfile getData() {
        return data;
    }

    public void setData(RefugeeProfile data) {
        this.data = data;
    }
}
