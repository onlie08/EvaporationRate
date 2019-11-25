package com.ch.base.base;

import com.ch.base.mvp.IPresenter;
import com.ch.base.mvp.IView;

/**
 * @author RedLi
 * @date
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V mView;
    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}
