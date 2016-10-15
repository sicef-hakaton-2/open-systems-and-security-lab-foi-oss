package party.sicef.borderless.ui.fragment;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import java.util.ArrayList;
import java.util.List;

import party.sicef.borderless.R;
import party.sicef.borderless.api.data.Category;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Created by Andro on 11/14/2015.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public static final String KEY_DISTANCE = "key_distance";

    EditTextPreference editTextPreference;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getActivity());

        editTextPreference = new EditTextPreference(getActivity());
        editTextPreference.setKey(KEY_DISTANCE);
        editTextPreference.setTitle(R.string.settings_distance);
        editTextPreference.setDefaultValue("5");
        editTextPreference.setSummary(PreferencesManager.getSharedPreferences(getActivity()).getString(KEY_DISTANCE, "5"));

        editTextPreference.setOnPreferenceChangeListener(this);

        screen.addPreference(editTextPreference);

        PreferenceCategory category = new PreferenceCategory(getActivity());
        category.setTitle(R.string.settings_category);

        screen.addPreference(category);

        // Add category prefs to screen
        Category[] categories = AppConstants.categories;
        for (Category c : categories) {
            CheckBoxPreference checkBoxPref = new CheckBoxPreference(getActivity());
            checkBoxPref.setTitle(c.getName());
            checkBoxPref.setChecked(false);
            checkBoxPref.setKey(AppConstants.KEY_CATEGORY + c.getId());

            category.addPreference(checkBoxPref);
        }

        setPreferenceScreen(screen);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        EditTextPreference etp = (EditTextPreference) findPreference(KEY_DISTANCE);
        etp.setSummary(newValue.toString());
        return true;
    }


}