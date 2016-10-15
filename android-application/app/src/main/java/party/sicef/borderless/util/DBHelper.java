package party.sicef.borderless.util;

import android.util.Log;

import com.activeandroid.query.Select;

import java.util.List;

import party.sicef.borderless.api.data.AuthorityProfile;
import party.sicef.borderless.api.data.CitizenProfile;
import party.sicef.borderless.api.data.OfferData;
import party.sicef.borderless.api.data.RefugeeProfile;
import party.sicef.borderless.model.OfferModel;
import party.sicef.borderless.model.ProfileModel;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class DBHelper {

    public static ProfileModel getProfile(){
        List<ProfileModel> profiles=new Select().from(ProfileModel.class).execute();
        return profiles.get(profiles.size()-1);
    }


    public static void saveRefuge(RefugeeProfile profile){
        ProfileModel model=new ProfileModel();
        model.setImage(profile.getProfileImage());
        model.setFirstName(profile.getFirstName());
        model.setLastName(profile.getLastName());
        model.setBirthDate(profile.getBirthDate());
        model.setCountry(profile.getCountry());
        model.save();
    }

    public static ProfileModel convertRefuge(RefugeeProfile profile){
        ProfileModel model=new ProfileModel();
        model.setImage(profile.getProfileImage());
        Log.d("test", "IMAGE: " + profile.getProfileImage());
        model.setFirstName(profile.getFirstName());
        model.setLastName(profile.getLastName());
        model.setBirthDate(profile.getBirthDate());
        model.setCountry(profile.getCountry());
       return model;
    }

    public static void saveCitizen(CitizenProfile profile){
        ProfileModel model=new ProfileModel();
        model.setImage(profile.getProfileImage());
        model.setFirstName(profile.getFirstName());
        model.setLastName(profile.getLastName());
        model.setCountry(profile.getCountry());
        model.save();
    }
    public static void saveAuthority(AuthorityProfile profile){
        ProfileModel model=new ProfileModel();
        model.setImage(profile.getProfileImage());
        model.setFirstName(profile.getFirstName());
        model.setLastName(profile.getLastName());
        model.setCountry(profile.getCountry());
        model.save();
    }


    public static void saveOffer(OfferData profile){
        if(new Select().from(OfferModel.class).where("globalId=?",profile.getId()).executeSingle()==null) {
            OfferModel model = new OfferModel();
            model.setDescription(profile.getDescription());
            model.setGlobalId(profile.getId());
            model.setLatitude(profile.getLatitude());
            model.setLongitude(profile.getLongitude());
            model.setTitle(profile.getTitle());
            model.save();
        }
    }

    public static OfferData convertOffer(OfferModel profile){
        OfferData model = new OfferData();
            model.setDescription(profile.getDescription());
            model.setId(profile.getGlobalId());
            model.setLatitude(profile.getLatitude());
            model.setLongitude(profile.getLongitude());
            model.setTitle(profile.getTitle());
        return model;

    }

}
