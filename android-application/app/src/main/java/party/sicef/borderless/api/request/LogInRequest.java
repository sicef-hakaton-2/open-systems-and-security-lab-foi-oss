package party.sicef.borderless.api.request;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class LogInRequest extends BaseRequest {

    @Expose
    private String email;

    @Expose
    private String password;

    @Expose
    private String registrationId;

    public LogInRequest(String email, String password, String registrationId) {
        this.email = email;
        this.password = password;
        this.registrationId = registrationId;
    }

    public LogInRequest(String method, String email, String password, String registrationId) {
        super(method);
        this.email = email;
        this.password = password;
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
