package com.explorer.base.customview;

public interface ICustomView<S extends BaseCustomViewModel> {

    void setData(S data);

    int getViewLayoutId();

    void setActionListener(ICustomViewActionListener listener);

}