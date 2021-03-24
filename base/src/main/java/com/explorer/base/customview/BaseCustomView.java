package com.explorer.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public abstract class BaseCustomView<S extends BaseCustomViewModel> extends LinearLayout implements ICustomView<S>, View.OnClickListener {

    private View rootView;
    private S viewModel;
    private ICustomViewActionListener mListener;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public View getRootView() {
        return rootView;
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getViewLayoutId() != 0) {
            rootView = inflater.inflate(getViewLayoutId(), this, false);
            rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, view, viewModel);
                    }
                    onRootClick(view);
                }
            });
            this.addView(rootView);
        }
    }

    @Override
    public void setData(S data) {
        viewModel = data;
        setDataToView(viewModel);
        onDataUpdated();
    }

    protected abstract void onDataUpdated();


    @Override
    public void setActionListener(ICustomViewActionListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
    }

    protected S getViewModel(){
        return viewModel;
    }

    protected abstract void setDataToView(S data);

    protected abstract void onRootClick(View view);
}
