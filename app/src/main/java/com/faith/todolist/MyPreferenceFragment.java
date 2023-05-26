package com.faith.todolist;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.CheckBoxPreference;

        public class MyPreferenceFragment extends PreferenceFragmentCompat {

            @Override
            public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
                setPreferencesFromResource(R.xml.settings, rootKey);

                // Find individual preference items and set listeners or custom logic
                SwitchPreference addNewTasksOnTop = findPreference("add_new_tasks_on_top");
                if (addNewTasksOnTop != null) {
                    addNewTasksOnTop.setOnPreferenceChangeListener((preference, newValue) -> {
                        // Handle preference change
                        return true;
                    });
                }

                SwitchPreference confirmTaskBeforeDeleting = findPreference("confirm_task_before_deleting");
                if (confirmTaskBeforeDeleting != null) {
                    confirmTaskBeforeDeleting.setOnPreferenceChangeListener((preference, newValue) -> {
                        // Handle preference change
                        return true;
                    });
                }

                // Find and handle other preferences in a similar manner
                // ...

            }
        }

