package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class ContactData extends BaseData {

    @Expose
    private int id;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String birthDate;

    @Expose
    private String country;

    @Expose
    private String profileImage;

    @Expose
    private String email;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
