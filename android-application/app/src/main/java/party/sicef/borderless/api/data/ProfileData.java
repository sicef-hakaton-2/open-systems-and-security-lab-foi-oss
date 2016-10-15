package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

import party.sicef.borderless.api.request.BaseRequest;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class ProfileData extends BaseData {

    @Expose
    private String username;

    @Expose
    private String email;

    @Expose
    private String token;

    @Expose
    private String registrationId;

    public ProfileData(String username, String email, String token, String registrationId) {
        this.username = username;
        this.email = email;
        this.token = token;
        this.registrationId = registrationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
