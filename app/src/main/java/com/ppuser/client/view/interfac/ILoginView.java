package com.ppuser.client.view.interfac;

/**
 * Created by mylddw on 17/12/20.
 */
public interface ILoginView {

    /**
     * 登录成功
     */
    void onLoginSuccess();

    /**
     * 登录失败
     *
     * @param error
     */
    void onLoginFailed(String error);
}
