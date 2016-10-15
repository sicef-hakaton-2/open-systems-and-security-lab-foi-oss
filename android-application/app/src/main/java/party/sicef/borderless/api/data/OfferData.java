package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class OfferData extends BaseData{

    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    private String longitude;

    @Expose
    private String latitude;

    @Expose
    private ContactData contact;

    public OfferData(int id, String title, String description, String longitude, String latitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public OfferData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public ContactData getContact() {
        return contact;
    }

    public void setContact(ContactData contact) {
        this.contact = contact;
    }
}
