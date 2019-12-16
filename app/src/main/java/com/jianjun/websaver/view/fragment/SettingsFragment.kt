package com.jianjun.websaver.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.jianjun.websaver.R
import com.jianjun.websaver.base.BaseFragment
import com.jianjun.websaver.contact.SettingsContact
import com.jianjun.websaver.presenter.SettingsPresenter
import com.jianjun.websaver.utils.CSVUtils
import io.reactivex.CompletableObserver
import io.reactivex.functions.Action
import io.reactivex.functions.Function

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
        }
        return true
    }

    private var importPreference: Preference? = null
    private var exportPreference: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        importPreference = findPreference<Preference>(getString(R.string.settings_key_import))
        exportPreference = findPreference(getString(R.string.settings_key_export))
        importPreference?.onPreferenceClickListener = this
        exportPreference?.onPreferenceClickListener = this
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