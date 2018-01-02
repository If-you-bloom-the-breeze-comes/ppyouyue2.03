package com.ppuser.client.model;

/**
 * Created by mylddw on 17/12/20.
 */
public interface ILoginModel {

    interface  OnLoginlitener
    {
        void  onComplete(Boolean b);
    }

    /**
     * 登录
     *
     * @param phone
     * @param passwod
     * @return
     */
    void login(String phone ,String passwod,OnLoginlitener onLoginlitener);
}
