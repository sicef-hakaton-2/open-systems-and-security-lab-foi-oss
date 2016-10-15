package party.sicef.borderless.api.response;

import com.google.gson.annotations.Expose;

import party.sicef.borderless.api.data.OfferData;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class OffersResponse extends BaseResponse {

    @Expose
    private OfferData[] data;


    public OffersResponse(String status, String message) {
        super(status, message);
    }

    public OfferData[] getData() {
        return data;
    }

    public void setData(OfferData[] data) {
        this.data = data;
    }
}
