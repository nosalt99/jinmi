package com.cyl.musiclake.ui.my;

import com.cyl.musiclake.ui.base.BaseContract;



public interface RegisterContract {

    interface View extends BaseContract.BaseView {
        void showErrorInfo(int flag, String msg);

        void showSuccessInfo(String msg);

        void updateView();
    }

    interface Presenter extends BaseContract.BasePresenter<RegisterContract.View> {
        void register(String email, String username, String password);
    }

}
