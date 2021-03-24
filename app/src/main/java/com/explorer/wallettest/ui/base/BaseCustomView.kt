package com.explorer.wallettest.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:26 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
open abstract class BaseCustomView<T>: FrameLayout, ICustomView<T> {

    constructor(context: Context,
                attributeSet: AttributeSet? = null,
                defStyleAttr: Int = 0,
                defStyleRes: Int = 0
    ): super(context, attributeSet, defStyleAttr, defStyleRes) {
        inflateView()
    }

    private var baseView: View? = null
    private var mListener: ICustomViewActionListener<T>? = null

    private fun inflateView() {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutId = getLayoutId()
        if(layoutId != 0) {
            baseView = layoutInflater.inflate(layoutId, this, false)
            addView(baseView)
            if(mListener != null) {
                baseView!!.setOnClickListener {
                    mListener!!.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, baseView!!, t!!)
                }
            }
        }
    }

    private var t: T? = null

    override fun bindDataToView(data: T) {
        t = data
    }

    override fun getRootView(): View {
        return baseView!!
    }

    @LayoutRes abstract fun getLayoutId(): Int

    override fun setActionListener(listener: ICustomViewActionListener<T>) {
        mListener = listener
    }

}