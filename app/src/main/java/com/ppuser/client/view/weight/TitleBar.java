package com.ppuser.client.view.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ppuser.client.R;

/**
 * Created by UPC on 2017/4/7.
 */

public class TitleBar extends RelativeLayout {
    private Activity activity;

    private TextView backTv;
    private TextView titleTv;
    private TextView menuTv;
    private ImageView menuIv;
    private RelativeLayout titleTl;

    private String titleText;

    private OnBackClickListener onBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.titlebar, 0, 0);
        try {
            getStyleable(ta);
            initView();
            initData();
        } finally {
            ta.recycle();
        }
    }

    /**
     * 获取自定义属性
     *
     * @param ta
     */
    private void getStyleable(TypedArray ta){
        titleText = ta.getString(R.styleable.titlebar_titlebar_TitleText);
    }

    /**
     * 初始化布局
     */
    private void initView(){
        backTv= (TextView) findViewById(R.id.titlebar_backTv);
        titleTv= (TextView) findViewById(R.id.titlebar_titleTv);
        menuTv= (TextView) findViewById(R.id.titlebar_menuTv);
        menuIv= (ImageView) findViewById(R.id.titlebar_menuIv);
        titleTl = (RelativeLayout) findViewById(R.id.titlebar_root);

    }

    /**
     * 设置数据
     */
    private void initData(){
        titleTv.setText(titleText+"");

    }

    private OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.titlebar_backTv:
                    if (onBackClickListener != null)
                        onBackClickListener.onClick(v);
                    else
                        activity.finish();

                    break;

                case R.id.titlebar_menuIv:
                    activity.setResult(100);
                    activity.finish();

                    break;

                default:

                    break;
            }
        }
    };

    public void setMenuIvVisibility(boolean isShow){
        if (isShow){
            menuIv.setVisibility(View.VISIBLE);
        }else {
            menuIv.setVisibility(View.GONE);
        }
    }

    public void setMenuIvResouce(int i){
        menuIv.setImageResource(i);
        setMargins(menuIv,0,0,0,0);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void setMenuTvVisibility(boolean isShow,String menuStr){
        if (isShow){
            menuTv.setText(menuStr);
            menuTv.setVisibility(View.VISIBLE);
        }else {
            menuTv.setVisibility(View.GONE);
        }
    }

    public TextView getMenuTv(){
        return menuTv;
    }

    public void setMenuBackVisibility(boolean isShow){
        if (isShow){
            backTv.setVisibility(View.VISIBLE);
        }else {
            backTv.setVisibility(View.GONE);
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        backTv.setOnClickListener(onClickListener);
        menuIv.setOnClickListener(onClickListener);
    }

    public void setBackText(String text) {
        if (text!=null&&!text.equals("")){
            backTv.setText(text);
        }
    }

    public void setTitleText(String text) {
        if (text!=null&&!text.equals("")){
            titleTv.setText(text);
        }
    }

    public void setBackLisetener(OnClickListener onClickListener) {
        backTv.setOnClickListener(onClickListener);
    }

    public void setMenuIvLisetener(OnClickListener onClickListener) {
        menuIv.setOnClickListener(onClickListener);
    }

    public void setMenuTvLisetener(OnClickListener onClickListener) {
        menuTv.setOnClickListener(onClickListener);
    }

    public interface OnBackClickListener{
        void onClick(View view);
    }

    public void setTitleBackround(int color){
        titleTl.setBackgroundResource(color);
    }

}
