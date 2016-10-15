package party.sicef.borderless.api.response;

import com.google.gson.annotations.Expose;

import party.sicef.borderless.api.data.AuthorityProfile;
import party.sicef.borderless.api.data.Config;
import party.sicef.borderless.api.data.ProfileData;
import party.sicef.borderless.api.data.RefugeeProfile;
import party.sicef.borderless.api.data.CitizenProfile;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class LogInResponse extends BaseResponse{

    @Expose
    private ProfileData data;

    @Expose
    private RefugeeProfile profile_refugee;

    @Expose
    private CitizenProfile profile_citizen;

    @Expose
    private AuthorityProfile profile_authority;

    @Expose
    private Config config;

    public LogInResponse(String status, String message, ProfileData data, RefugeeProfile profile_refugee, CitizenProfile profile_citizen, AuthorityProfile profile_authority) {
        super(status, message);
        this.data = data;
        this.profile_refugee = profile_refugee;
        this.profile_citizen = profile_citizen;
        this.profile_authority = profile_authority;
    }

    public ProfileData getData() {
        return data;
    }

    public void setData(ProfileData data) {
        this.data = data;
    }

    public RefugeeProfile getProfile_refugee() {
        return profile_refugee;
    }

    public void setProfile_refugee(RefugeeProfile profile_refugee) {
        this.profile_refugee = profile_refugee;
    }

    public CitizenProfile getProfile_citizen() {
        return profile_citizen;
    }

    public void setProfile_citizen(CitizenProfile profile_citizen) {
        this.profile_citizen = profile_citizen;
    }

    public AuthorityProfile getProfile_authority() {
        return profile_authority;
    }

    public void setProfile_authority(AuthorityProfile profile_authority) {
        this.profile_authority = profile_authority;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
