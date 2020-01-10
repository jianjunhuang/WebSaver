package com.jianjun.websaver.view.fragment

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.didichuxing.doraemonkit.DoraemonKit
import com.google.android.material.snackbar.Snackbar
import com.jianjun.websaver.BuildConfig
import com.jianjun.websaver.R
import com.jianjun.websaver.contact.SettingsContact
import com.jianjun.websaver.presenter.SettingsPresenter
import com.jianjun.websaver.utils.CSVUtils

/**
 * Created by jianjunhuang on 10/22/19.
 */
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    SettingsContact.SettingsView {

    private lateinit var presenter: SettingsPresenter
    private var csvUtils: CSVUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SettingsPresenter()
        csvUtils = activity?.let { CSVUtils(it) }
        presenter.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference) {
            importPreference -> {
                presenter.importData(csvUtils)
            }
            exportPreference -> {
                presenter.exportData(csvUtils)
            }
            debugModeSwitch -> {
                DoraemonKit.show()
            }
        }
        return true
    }

    private var importPreference: Preference? = null
    private var exportPreference: Preference? = null
    private var debugModeSwitch: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        importPreference = findPreference(getString(R.string.settings_key_import))
        exportPreference = findPreference(getString(R.string.settings_key_export))
        debugModeSwitch = findPreference(getString(R.string.settings_debug_mode))
        importPreference?.onPreferenceClickListener = this
        exportPreference?.onPreferenceClickListener = this
        debugModeSwitch?.onPreferenceClickListener = this

        debugModeSwitch?.isVisible = BuildConfig.DEBUG
    }

    override fun onDataImport(isImported: Boolean, error: String) {
        showSnackbar(if (isImported) "Data imported" else error)
    }

    override fun onDataExport(isExported: Boolean, error: String) {
        showSnackbar(if (isExported) "Data exported" else error)
    }

    private fun showSnackbar(msg: String) {
        view?.let {
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

}