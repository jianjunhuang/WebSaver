package com.jianjun.websaver.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.base.base.ItemClickListener
import com.jianjun.base.mvp.BaseMvpFragment
import com.jianjun.base.multiseletced.MultiSelectedLayout
import com.jianjun.websaver.R
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.TAG_ALL
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.presenter.PagerListPresenter
import com.jianjun.websaver.view.activity.PagerViewerActivity
import com.jianjun.websaver.view.adapter.PagerListAdapter

/**
 * Created by jianjunhuang on 10/25/19.
 */
class PagerListFragment : BaseMvpFragment<PagerListPresenter>(), PagerListContact.IPagersView,
    ItemClickListener<Pager> {

    companion object {
        const val KEY_TAG = "key_tag"
        fun create(tag: String): PagerListFragment {
            val fragment = PagerListFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TAG, tag)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var pagerTag: String = TAG_ALL
    private lateinit var sureAlertDialog: AlertDialog

    override fun onItemClick(
        data: Pager,
        view: View,
        position: Int,
        adapter: RecyclerView.Adapter<*>
    ) {
        val intent = Intent()
        activity?.let {
            val compat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0)
            intent.setClass(
                it, PagerViewerActivity::class.java
            )
            intent.putExtra(Intent.EXTRA_TEXT, data.url)
            ActivityCompat.startActivity(it, intent, compat.toBundle())
        }
    }

    override fun onPagers(pagers: List<Pager>?) {
        pagerAdapter.refresh(pagers!!)
    }

    override fun createPresenter(): PagerListPresenter {
        return PagerListPresenter()
    }

    private var pagerList: RecyclerView? = null
    private lateinit var pagerAdapter: PagerListAdapter
    private var multiSelectedLayout: MultiSelectedLayout<Pager>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerTag = arguments?.getString(KEY_TAG).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pager_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerList = view.findViewById(R.id.rv_pager)
        pagerList?.layoutManager = LinearLayoutManager(context)
        pagerAdapter = PagerListAdapter()
        pagerAdapter.setOnItemCLickListener(this)
        pagerList?.adapter = pagerAdapter
        multiSelectedLayout = view.findViewById(R.id.multi_selected_layout)
        multiSelectedLayout?.setSelectAdapter(pagerAdapter)
        multiSelectedLayout?.selectedListener =
            object : MultiSelectedLayout.MultiSelectedListener<Pager> {

                override fun onClose() {
                }


                override fun onSelectedAll(selectedAll: Boolean) {
                }

                override fun onDeletedClicked(checkItems: ArrayMap<Int, Pager>): Boolean {
                    //show dialog
                    sureAlertDialog.setMessage("Are you sure remove these ${checkItems.size} pagers?")
                    sureAlertDialog.show()
                    return true
                }

                override fun onRealDeleted(checkItems: ArrayMap<Int, Pager>) {
                    //todo delete real
                }

                override fun interceptDelete(checkItems: ArrayMap<Int, Pager>): Boolean {
                    return false
                }
            }
        getPresenter()?.queryPagers(pagerTag)
        sureAlertDialog =
            AlertDialog.Builder(view.context).setTitle("Tips")
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("Yes") { _, _ ->
                    multiSelectedLayout?.delete()
                }.create()
    }

}