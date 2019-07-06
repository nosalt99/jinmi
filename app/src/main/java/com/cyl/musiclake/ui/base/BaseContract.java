package com.cyl.musiclake.ui.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;


public class BaseContract {
    public interface BasePresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }

    public interface BaseView {
        Context getContext();

        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //隐藏进度
        void showError(String message, boolean showRetryButton);

        //显示空状态
        void showEmptyState();


        <T> LifecycleTransformer<T> bindToLife();
    }
}
