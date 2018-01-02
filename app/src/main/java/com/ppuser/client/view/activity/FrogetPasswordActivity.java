package com.ppuser.client.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.ppuser.client.R;
import com.ppuser.client.base.BaseActivity;
import com.ppuser.client.present.LoginPersenter;
import com.ppuser.client.utils.StatusBarUtils;
import com.ppuser.client.utils.ToastUtil;
import com.ppuser.client.view.interfac.ILoginView;

/**
 * Created by mylddw on 17/12/20.
 * <p>
 * 忘记密码页面
 */
public class FrogetPasswordActivity extends BaseActivity<ILoginView, LoginPersenter<ILoginView>> implements
        ILoginView, View.OnClickListener ,View.OnFocusChangeListener{

    private EditText mEtRegisterPhone,mEtRegisterIdentify,mEtRegisterPassword;//输入框
    private ImageView mIvRegisterPhone,mIvRegisterIndefityCode,mIvRegisterPassword;//输入框前面图片
    private View mViewRegisterLinePhone,mViewRegisterLineIdentify,mViewRegisterLinePassword;//输入框下面横线


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_password);
        StatusBarUtils.with(this).init();

        //注册页面初始化
        mEtRegisterPhone = (EditText) findViewById(R.id.register_phoneEt);
        mEtRegisterIdentify = (EditText) findViewById(R.id.register_identify_codeEt);
        mEtRegisterPassword = (EditText) findViewById(R.id.register_passwordEt);

        mIvRegisterPhone = (ImageView) findViewById(R.id.register_image_phone);
        mIvRegisterIndefityCode = (ImageView) findViewById(R.id.register_image_identify);
        mIvRegisterPassword = (ImageView) findViewById(R.id.register_image_password);
        mViewRegisterLinePhone = findViewById(R.id.register_view_line_phone);
        mViewRegisterLineIdentify = findViewById(R.id.register_view_line_identify_code);
        mViewRegisterLinePassword = findViewById(R.id.register_view_line_password);

    }

    @Override
    protected void setListener() {
        //设置监听焦点事件
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_summitTv:
                ToastUtil.showToast(mContext, "立即登录");
                mPresent.setLoginDatas("", "");
                mPresent.fectch();
                break;
        }

    }

    //设置监听焦点事件
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){

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
