package com.ppuser.client.view.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.ppuser.client.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mylddw on 2017/12/13.
 */

public class AuthCodeTextView extends TextView {
    private Timer timer;
    private TimerTask task;
    private int maxTime=60;
    private int time=maxTime;
    private boolean isHandler=false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time!=0){
                setText(""+time--);
            }else {
                timer.cancel();
                time=maxTime;
                setText("再次获取");
                isHandler=false;
                setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
            }
        }
    };

    public AuthCodeTextView(Context context) {
        super(context);
        initView(context);
    }

    public AuthCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AuthCodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
        setTextColor(context.getResources().getColor(R.color.white));
        setText("发送验证码");
        setLines(1);
        setGravity(Gravity.CENTER);
        TextPaint paint = getPaint();
        int textLength = (int) paint.measureText(getText().toString());
        int magins=(int) (10 * (context.getResources().getDisplayMetrics().density) + 0.5f);
        setWidth(textLength+magins*2);
        //setPadding(magins,magins/2,magins,magins/2);

    }

    public void setHandler(){
        if (!isHandler){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
            timer.schedule(task, 0, 1000);
            isHandler=true;
            setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
        }
    }

    public boolean getIsHandler(){
        return isHandler;
    }
}
