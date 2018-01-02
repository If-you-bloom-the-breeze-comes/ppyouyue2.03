package com.ppuser.client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ppuser.client.R;
import com.ppuser.client.adapter.LoginOrRegisterAdapter;
import com.ppuser.client.base.BaseActivity;
import com.ppuser.client.present.LoginPersenter;
import com.ppuser.client.utils.StatusBarUtils;
import com.ppuser.client.utils.ToastUtil;
import com.ppuser.client.view.interfac.ILoginView;
import com.ppuser.client.view.weight.AuthCodeTextView;
import java.util.ArrayList;

/**
 * Created by mylddw on 17/12/20.
 *
 * 登录注册页面
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPersenter<ILoginView>> implements
        ILoginView, View.OnClickListener ,View.OnFocusChangeListener{

    TextView mTvToLogin, mTvToRegister;
    private ViewPager mViewpageLoginOrRegister;
    private View mLoginView, mRegisterView;

    private ArrayList<View> mList;
    private LoginOrRegisterAdapter mLorAdapter;
    private EditText mEtLoginPhone,mEtPasswordPassword;//输入框
    private ImageView mIvPhone,mIvPassword;//输入框前面图片
    private View mViewLinePhone,mViewLinePassword;

    private EditText mEtRegisterPhone,mEtRegisterIdentify,mEtRegisterPassword;//输入框
    private ImageView mIvRegisterPhone,mIvRegisterIndefityCode,mIvRegisterPassword;//输入框前面图片
    private View mViewRegisterLinePhone,mViewRegisterLineIdentify,mViewRegisterLinePassword;//输入框下面横线

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        StatusBarUtils.with(this).init();
        LayoutInflater inflater = getLayoutInflater();
        mLoginView = inflater.inflate(R.layout.item_login, null);
        mRegisterView = inflater.inflate(R.layout.item_register, null);
        mTvToLogin = (TextView) findViewById(R.id.login_to_loginTv);
        mTvToRegister = (TextView) findViewById(R.id.login_to_registerTv);
        //登录页面初始化
        mEtLoginPhone = (EditText) mLoginView.findViewById(R.id.login_phoneEt);
        mEtPasswordPassword = (EditText) mLoginView.findViewById(R.id.login_passwordEt);
        mIvPhone = (ImageView) mLoginView.findViewById(R.id.login_image_phone);
        mIvPassword = (ImageView) mLoginView.findViewById(R.id.login_image_password);
        mViewLinePhone = mLoginView.findViewById(R.id.login_view_line_phone);
        mViewLinePassword = mLoginView.findViewById(R.id.login_view_line_password);
        //注册页面初始化
        mEtRegisterPhone = (EditText) mRegisterView.findViewById(R.id.register_phoneEt);
        mEtRegisterIdentify = (EditText) mRegisterView.findViewById(R.id.register_identify_codeEt);
        mEtRegisterPassword = (EditText) mRegisterView.findViewById(R.id.register_passwordEt);
        mIvRegisterPhone = (ImageView) mRegisterView.findViewById(R.id.register_image_phone);
        mIvRegisterIndefityCode = (ImageView) mRegisterView.findViewById(R.id.register_image_identify);
        mIvRegisterPassword = (ImageView) mRegisterView.findViewById(R.id.register_image_password);
        mViewRegisterLinePhone = mRegisterView.findViewById(R.id.register_view_line_phone);
        mViewRegisterLineIdentify = mRegisterView.findViewById(R.id.register_view_line_identify_code);
        mViewRegisterLinePassword = mRegisterView.findViewById(R.id.register_view_line_password);

        mList = new ArrayList<View>();
        mList.add(mLoginView);
        mList.add(mRegisterView);
        mLorAdapter = new LoginOrRegisterAdapter(mList);
        mViewpageLoginOrRegister = (ViewPager) findViewById(R.id.viewpager_login_or_register);
        mViewpageLoginOrRegister.setAdapter(mLorAdapter);
    }

    @Override
    protected void setListener() {

        mEtLoginPhone.setOnFocusChangeListener(this);
        mEtPasswordPassword.setOnFocusChangeListener(this);
        mEtRegisterPhone.setOnFocusChangeListener(this);
        mEtRegisterPassword.setOnFocusChangeListener(this);
        mEtRegisterIdentify.setOnFocusChangeListener(this);

    }



    @Override
    protected void initData() {

    }

    @Override
    protected LoginPersenter createPresent() {
        return new LoginPersenter(this);

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed(String error) {

    }

    //设置监听点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_summitTv:
                ToastUtil.showToast(mContext, "立即登录");
                mPresent.setLoginDatas("", "");
                mPresent.fectch();
                break;

            case R.id.login_to_loginTv:
                ToastUtil.showToast(mContext, "登录");
                mTvToLogin.setTextColor(getResources().getColor(R.color.app_green));
                mTvToRegister.setTextColor(getResources().getColor(R.color.white));
                mViewpageLoginOrRegister.setCurrentItem(0);
                break;
            case R.id.login_to_registerTv:
                ToastUtil.showToast(mContext, "注册");
                mTvToLogin.setTextColor(getResources().getColor(R.color.white));
                mTvToRegister.setTextColor(getResources().getColor(R.color.app_green));
                mViewpageLoginOrRegister.setCurrentItem(1);
                break;
            case R.id.iv_login_wx:
                ToastUtil.showToast(mContext, "wx");
                break;
            case R.id.iv_login_qq:
                ToastUtil.showToast(mContext, "qq");
                break;
            case R.id.iv_login_wb:
                ToastUtil.showToast(mContext, "wb");
                break;
            case R.id.login_forget_password_text://忘记密码
                startActivity(new Intent(mContext,FrogetPasswordActivity.class));
                break;
            case R.id.authCodeTextView://获取验证码
                ((AuthCodeTextView)findViewById(R.id.authCodeTextView)).setHandler();
                break;
        }

    }

    //设置监听焦点事件
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.login_phoneEt:
                if(hasFocus){
                    mIvPhone.setImageResource(R.drawable.login_phone_fouse);
                    mViewLinePhone.setBackgroundColor(getResources().getColor(R.color.colorInputText));
                }else {
                    mIvPhone.setImageResource(R.drawable.login_phone_unfouse);
                    mViewLinePhone.setBackgroundColor(getResources().getColor(R.color.colorInputLine));
                }
                break;
            case R.id.login_passwordEt:
                if(hasFocus){
                    mIvPassword.setImageResource(R.drawable.login_password_fouse);
                    mViewLinePassword.setBackgroundColor(getResources().getColor(R.color.colorInputText));
                }else {
                    mIvPassword.setImageResource(R.drawable.login_password_unfouse);
                    mViewLinePassword.setBackgroundColor(getResources().getColor(R.color.colorInputLine));
                }
                break;
            case R.id.register_phoneEt:
                if(hasFocus){
                    mIvRegisterPhone.setImageResource(R.drawable.login_phone_fouse);
                    mViewRegisterLinePhone.setBackgroundColor(getResources().getColor(R.color.colorInputText));
                }else {
                    mIvRegisterPhone.setImageResource(R.drawable.login_phone_unfouse);
                    mViewRegisterLinePhone.setBackgroundColor(getResources().getColor(R.color.colorInputLine));
                }
                break;
            case R.id.register_identify_codeEt:
                if(hasFocus){
                    mIvRegisterIndefityCode.setImageResource(R.drawable.login_identify_fouse);
                    mViewRegisterLineIdentify.setBackgroundColor(getResources().getColor(R.color.colorInputText));
                }else {
                    mIvRegisterIndefityCode.setImageResource(R.drawable.login_identify_unfouse);
                    mViewRegisterLineIdentify.setBackgroundColor(getResources().getColor(R.color.colorInputLine));
                }
                break;
            case R.id.register_passwordEt:
                if(hasFocus){
                    mIvRegisterPassword.setImageResource(R.drawable.login_password_fouse);
                    mViewRegisterLinePassword.setBackgroundColor(getResources().getColor(R.color.colorInputText));
                }else {
                    mIvRegisterPassword.setImageResource(R.drawable.login_password_unfouse);
                    mViewRegisterLinePassword.setBackgroundColor(getResources().getColor(R.color.colorInputLine));
                }
                break;
        }
    }
}
