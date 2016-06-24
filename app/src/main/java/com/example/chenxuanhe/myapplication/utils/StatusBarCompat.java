package com.example.chenxuanhe.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by chenxuanhe on 2016/4/28.
 * git上别人的StatusBar
 */
public class StatusBarCompat {
    public static final String TAG = StatusBarCompat.class.getName();

    public static final int COLOR_DEFAULT_WHITE = Color.parseColor("#FFFFFFFF");
    public static final int COLOR_DEFAULT_PINK = Color.parseColor("#F0F8FF");

    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity, COLOR_DEFAULT_PINK);
    }

    /**
     * set statusBarColor
     * @param statusColor color
     * @param alpha 0 - 255
     */
    public static void setStatusBarColor(Activity activity, int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //第一个半透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //不是透明状态后
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //通知 setStatusBarColor.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusColor);
                //设置子视图不填充系统窗口
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                }
            } else {
                int statusBarHeight = getStatusBarHeight(activity);

                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                    //如果边缘顶部已经设置，只跳过.
                    if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                        //不使用 fitsSystemWindows
                        ViewCompat.setFitsSystemWindows(mChildView, false);
                        //添加内容到内容
                        lp.topMargin += statusBarHeight;
                        mChildView.setLayoutParams(lp);
                    }
                }

                //创建一个虚拟的状态栏
                View statusBarView = mContentView.getChildAt(0);
                if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                    //如果假状态栏视图存在，我们可以设置TabHost的背景颜色和回报
                    statusBarView.setBackgroundColor(statusColor);
                    return;
                }
                statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                statusBarView.setBackgroundColor(statusColor);
                mContentView.addView(statusBarView, 0, lp);
            }
        }
    }

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * change to full screen mode
     * @param hideStatusBarBackground hide status bar Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //设置子视图不填充系统窗口
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(activity);

            //第一个半透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //布局参数
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (hideStatusBarBackground) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.parseColor("#00000000"));
                } else {
                    window.setStatusBarColor(Color.parseColor("#55000000"));
                }
                //必须调用其requestLayout方法，否则会在屏幕底部有空间
                if (mChildView != null) {
                    ViewCompat.requestApplyInsets(mChildView);
                }
            } else {
                if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
                    //去除状态栏视图.
                    mContentView.removeView(mChildView);
                    mChildView = mContentView.getChildAt(0);
                }
                if (mChildView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                    //cancel the margin top
                    if (lp != null && lp.topMargin >= statusBarHeight) {
                        lp.topMargin -= statusBarHeight;
                        mChildView.setLayoutParams(lp);
                    }
                }
            }
        }
    }

    //获得状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    //得到阿尔法的颜色
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

}
