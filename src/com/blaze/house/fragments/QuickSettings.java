/*
 * Copyright (C) 2022 SuperiorOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blaze.house.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.blaze.house.preferences.SystemSettingListPreference;

import java.util.List;
import java.util.ArrayList;

public class QuickSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String QS_PAGE_TRANSITIONS = "custom_transitions_page_tile";

    private SystemSettingListPreference mPageTransitions;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.notificationspanel);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        mPageTransitions = (SystemSettingListPreference) findPreference(QS_PAGE_TRANSITIONS);
        mPageTransitions.setOnPreferenceChangeListener(this);
        int customTransitions = Settings.System.getIntForUser(resolver,
                Settings.System.CUSTOM_TRANSITIONS_KEY,
                0, UserHandle.USER_CURRENT);
        mPageTransitions.setValue(String.valueOf(customTransitions));
        mPageTransitions.setSummary(mPageTransitions.getEntry());

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
            if (preference.equals(mPageTransitions)) {
            int customTransitions = Integer.parseInt(((String) newValue).toString());
            Settings.System.putIntForUser(resolver,
                    Settings.System.CUSTOM_TRANSITIONS_KEY, customTransitions, UserHandle.USER_CURRENT);
            int index = mPageTransitions.findIndexOfValue((String) newValue);
            mPageTransitions.setSummary(
                    mPageTransitions.getEntries()[index]);
            return true;
          } 
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.BLAZE_HOUSE;
    }

}