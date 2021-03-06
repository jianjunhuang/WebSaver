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
import com.jianjun.base.adapter.BaseAdapter
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
    BaseAdapter.OnItemClickListener<PagerListAdapter.PagersViewHolder, Pager> {

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

    override fun onPagers(pagers: List<Pager>?) {
        pagerAdapter.refresh(pagers!!)
    }

    override fun onDeletedSuccess() {
        showSnack("pagers delete success")
    }

    override fun onDeletedFailed(reason: String) {
        showSnack("pagers delete failed: $reason")
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
        pagerAdapter.onItemClickListener = this
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
                    getPresenter()?.deletePagers(checkItems)
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

    override fun onClick(holder: PagerListAdapter.PagersViewHolder, pos: Int, data: Pager) {
        val intent = Intent()
        activity?.let {
            val compat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeScaleUpAnimation(
                    holder.itemView,
                    holder.itemView.width,
                    0,
                    0,
                    0
                )
            intent.setClass(
                it, PagerViewerActivity::class.java
            )
            intent.putExtra(Intent.EXTRA_TEXT, data.url)
            ActivityCompat.startActivity(it, intent, compat.toBundle())
        }
    }

}