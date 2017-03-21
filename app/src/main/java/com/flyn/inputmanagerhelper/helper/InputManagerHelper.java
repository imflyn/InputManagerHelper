package com.flyn.inputmanagerhelper.helper;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.flyn.inputmanagerhelper.R;
import com.flyn.inputmanagerhelper.view.KeyboardListenLayout;

public class InputManagerHelper {


    private Activity activity;
    private int lastKeyBoardHeight;

    private InputManagerHelper(Activity activity) {
        this.activity = activity;
    }

    public static InputManagerHelper attachToActivity(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return new InputManagerHelper(activity);
    }

    public void bindCustomLayout(final KeyboardListenLayout keyboardListenLayout, final View lastVisibleView) {
        keyboardListenLayout.setOnSizeChangedListener(new KeyboardListenLayout.onSizeChangedListener() {
            @Override
            public void onChanged(final boolean showKeyboard, final int h, final int oldh) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (showKeyboard) {
                            //oldh代表输入法未弹出前最外层布局高度，h代表当前最外层布局高度，oldh-h
                            // 计算出输入法的高度
                            //oldh-输入法高度即为键盘最顶端的在布局中的位置
                            int keyboardTop = oldh - (oldh - h);
                            //登录按钮底部在布局中的位置-输入法顶部的位置=需要将布局弹起多少高度
                            int reSizeLayoutHeight = lastVisibleView.getBottom() - keyboardTop;
                            //设置登录按钮与输入法之间存有间距
                            reSizeLayoutHeight = reSizeLayoutHeight + getPxFromDp(8);
                            keyboardListenLayout.setPadding(0, -reSizeLayoutHeight, 0, 0);
                        } else {
                            //还原布局
                            keyboardListenLayout.setPadding(0, 0, 0, 0);
                        }
                    }
                }, 100);
            }
        });
    }

    public void bindLayout(final ViewGroup viewGroup, final View lastVisibleView) {
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adjustLayout(viewGroup, lastVisibleView);
                    }
                }, 100);
            }
        });
    }

    private void adjustLayout(ViewGroup viewGroup, View lastVisibleView) {
        Rect r = new Rect();
        viewGroup.getWindowVisibleDisplayFrame(r);
        //获得屏幕高度
        int screenHeight = viewGroup.getRootView().getHeight();
        //r.bottom - r.top计算出输入法弹起后viewGroup的高度，屏幕高度-viewGroup高度即为键盘高度
        int keyboardHeight = screenHeight - (r.bottom - r.top);
        //当设置layout_keyboard设置完padding以后会重绘布局再次执行onGlobalLayout()
        //所以判断如果键盘高度未改变就不执行下去
        if (keyboardHeight == lastKeyBoardHeight) {
            return;
        }
        lastKeyBoardHeight = keyboardHeight;
        if (keyboardHeight < 300) {
            //键盘关闭后恢复布局
            viewGroup.setPadding(0, 0, 0, 0);
        } else {
            //计算出键盘最顶端在布局中的位置
            int keyboardTop = screenHeight - keyboardHeight;
            //登录按钮底部在布局中的位置-输入法顶部的位置=需要将布局弹起多少高度
            int reSizeLayoutHeight = lastVisibleView.getBottom() - keyboardTop;
            //计算actionBar的高度
            if (null != (((AppCompatActivity) activity).getSupportActionBar())) {
                //如果界面里有actionbar则需要多向上弹起一个actionbar的高度
                TypedValue typedValue = new TypedValue();
                if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                    int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
                    reSizeLayoutHeight += actionBarHeight;
                }
            } else if (isTranslucentStatusBar()) {
                //如果界面是全屏的，需要少弹起一个状态栏的高度
                reSizeLayoutHeight -= getStatusBarHeight();
            }
            //设置登录按钮与输入法之间存有间距
            reSizeLayoutHeight += getPxFromDp(8);
            viewGroup.setPadding(0, -reSizeLayoutHeight, 0, 0);
        }
    }

    private int getPxFromDp(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, activity.getResources().getDisplayMetrics());
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = activity.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    private boolean isTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag = activity.getWindow().getAttributes().flags;
            return (flag & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        return false;
    }

    public void bindScrollView(final ScrollView scrollView) {
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll(scrollView);
                    }
                }, 100);
            }
        });
    }

    private void scroll(final ScrollView scrollView) {
        Rect r = new Rect();
        scrollView.getWindowVisibleDisplayFrame(r);
        //获得屏幕高度
        int screenHeight = scrollView.getRootView().getHeight();
        //r.bottom - r.top计算出输入法弹起后viewGroup的高度，屏幕高度-viewGroup高度即为键盘高度
        int keyboardHeight = screenHeight - (r.bottom - r.top);
        //当设置layout_keyboard设置完padding以后会重绘布局再次执行onGlobalLayout()
        //所以判断如果键盘高度未改变就不执行下去
        if (keyboardHeight == lastKeyBoardHeight) {
            return;
        }
        lastKeyBoardHeight = keyboardHeight;
        if (keyboardHeight > 300) {
            Rect rect = new Rect();
            activity.getWindow().getCurrentFocus().getWindowVisibleDisplayFrame(rect);
            scrollView.smoothScrollTo(0, rect.bottom);
        }
    }
}
