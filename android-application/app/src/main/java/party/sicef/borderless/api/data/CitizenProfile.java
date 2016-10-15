package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class CitizenProfile {

    @Expose
    private int id;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String country;

    @Expose
    private String createdAt;

    @Expose
    private String profileImage;

    public CitizenProfile(int id, String firstName, String lastName, String country, String createdAt, String profileImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.createdAt = createdAt;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
