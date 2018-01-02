package com.ppuser.client.present;

import com.ppuser.client.model.ILoginModel;
import com.ppuser.client.model.LoginModel;
import com.ppuser.client.view.interfac.ILoginView;

/**
 * Created by mylddw on 17/12/20.
 */
public class LoginPersenter<T> extends BasePersenter<ILoginView> {
    String mAccount;
    String mPassword;

    /**
     * 持有视图层 UI接口的引用  此时的视图层Activity
     */
    ILoginView mLoginView;

    public LoginPersenter(ILoginView loginView) {
        this.mLoginView = loginView;
    }

    public void setLoginDatas(String account,String password){
        this.mAccount = account;
        this.mPassword = password;
    }

    /**
     * 持有模型Model的引用
     */
    ILoginModel mLoginModel=new LoginModel();

    @Override
    public void fectch() {
        mLoginModel.login("", "", new ILoginModel.OnLoginlitener() {
            @Override
            public void onComplete(Boolean b) {
                if(b){
                    mLoginView.onLoginSuccess();
                }else {
                    mLoginView.onLoginFailed("");
                }
            }
        });
    }
}
