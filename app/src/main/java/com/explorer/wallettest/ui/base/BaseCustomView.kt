package com.explorer.wallettest.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.explorer.wallettest.logger.Logger

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
    private var data: T? = null


    private fun inflateView() {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutId = getLayoutId()
        if(layoutId != 0) {
            baseView = layoutInflater.inflate(layoutId, this, false)
            addView(baseView)
            rootView.setOnClickListener {
                if(mListener != null) {
                    Logger.e("listener: $mListener baseview: $baseView it: $it")
                    mListener!!.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, rootView, data!!)
                }
            }
        }
    }
    


    override fun setData(data: T) {
        this.data = data
        bindDataToView(data)
    }


    override fun setActionListener(listener: ICustomViewActionListener<T>) {
        mListener = listener
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun bindDataToView(data: T)

}