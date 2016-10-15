package party.sicef.borderless.model;

import party.sicef.borderless.api.data.OfferData;

/**
 * Created by Andro on 11/14/2015.
 */
public class RecyclerItem {

    private String title;
    private String imageUrl;
    private OfferData offer;
    public String distance;

    public RecyclerItem() {
    }

    public RecyclerItem(String title, String imageUrl, OfferData offer) {
        this.title = offer.getTitle();
        this.imageUrl = imageUrl;
        this.offer=offer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OfferData getOffer() {
        return offer;
    }

    public void setOffer(OfferData offer) {
        this.offer = offer;
    }
}
