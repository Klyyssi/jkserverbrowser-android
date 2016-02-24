package com.thelairofmarkus.markus.jk2serverbrowser.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.thelairofmarkus.markus.jk2serverbrowser.R;

/**
 * Created by markus on 24.2.2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
