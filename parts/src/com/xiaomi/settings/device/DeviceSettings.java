/*
 * Copyright (C) 2018 The Xiaomi-SDM660 Project
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
 * limitations under the License
 */

package com.xiaomi.settings.device;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;

import com.xiaomi.settings.R;

public class DeviceSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private final String ENABLE_HAL3_KEY = "hal3";
    private final String ENABLE_EIS_KEY = "eis";

    private final String HAL3_SYSTEM_PROPERTY = "persist.camera.HAL3.enabled";
    private final String EIS_SYSTEM_PROPERTY = "persist.camera.eis.enable";

    private SwitchPreference mEnableHAL3;
    private SwitchPreference mEnableEIS;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main, rootKey);
        mEnableHAL3 = (SwitchPreference) findPreference(ENABLE_HAL3_KEY);
        mEnableHAL3.setChecked(FileUtils.getProp(HAL3_SYSTEM_PROPERTY, false));
        mEnableHAL3.setOnPreferenceChangeListener(this);

        mEnableEIS = (SwitchPreference) findPreference(ENABLE_EIS_KEY);
        mEnableEIS.setChecked(FileUtils.getProp(EIS_SYSTEM_PROPERTY, false));
        mEnableEIS.setOnPreferenceChangeListener(this);

    }


    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case ENABLE_HAL3_KEY:
                mEnableHAL3.setChecked((Boolean) value);
                FileUtils.setProp(HAL3_SYSTEM_PROPERTY, (Boolean) value);
                break;

            case ENABLE_EIS_KEY:
                mEnableEIS.setChecked((Boolean) value);
                FileUtils.setProp(EIS_SYSTEM_PROPERTY, (Boolean) value);
                break;

            default:
                break;
        }
        return true;
    }

    private boolean isAppInstalled(String uri) {
        PackageManager packageManager = DeviceSettingsActivity.getContext().getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // Throw it far away
            return false;
        }
    }
}
