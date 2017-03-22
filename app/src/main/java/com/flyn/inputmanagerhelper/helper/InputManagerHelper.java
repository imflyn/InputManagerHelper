package com.flyn.inputmanagerhelper.helper;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyn.inputmanagerhelper.R;
import com.flyn.inputmanagerhelper.view.KeyboardListenLayout;

public class InputManagerHelper {


    private Activity activity;
    private int lastKeyBoardHeight;
    private int offset;

    private InputManagerHelper(Activity activity) {
        this.activity = activity;
    }

    public static InputManagerHelper attachToActivity(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return new InputManagerHelper(activity);
    }

    public InputManagerHelper bind(ViewGroup viewGroup, View lastVisibleView) {
        if (viewGroup instanceof KeyboardListenLayout) {
            bindKeyboardListenLayout((KeyboardListenLayout) viewGroup, lastVisibleView);
        } else {
            if (viewGroup instanceof RecyclerView || viewGroup instanceof ScrollView) {
                bindViewGroup(viewGroup);
            } else {
                bindLayout(viewGroup, lastVisibleView);
            }
        }
        return this;
    }

    public InputManagerHelper bind(ViewGroup viewGroup) {
        bind(viewGroup, activity.getCurrentFocus());
        return this;
    }

    public InputManagerHelper offset(int offset) {
        this.offset = offset;
        return this;
    }

    private void bindKeyboardListenLayout(final KeyboardListenLayout keyboardListenLayout, final View view) {
        keyboardListenLayout.setOnSizeChangedListener(new KeyboardListenLayout.onSizeChangedListener() {
            @Override
            public void onChanged(final boolean showKeyboard, final int h, final int oldh) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastVisibleView = view;
                        if (lastVisibleView == null) {
                            lastVisibleView = activity.getCurrentFocus();
                        }
                        if (showKeyboard) {
                            //oldh代表输入法未弹出前最外层布局高度，h代表当前最外层布局高度，oldh-h可以计算出布局大小改变后输入法的高度
                            //整个布局的高度-输入法高度=键盘最顶端处在布局中的位置，其实直接用h计算就可以，代码这么写为了便于理解
                            int keyboardTop = oldh - (oldh - h);
                            int[] location = new int[2];
                            lastVisibleView.getLocationOnScreen(location);
                            //登录按钮顶部在屏幕中的位置+登录按钮的高度=登录按钮底部在屏幕中的位置
                            int lastVisibleViewBottom = location[1] + lastVisibleView.getHeight();
                            //登录按钮底部在布局中的位置-输入法顶部的位置=需要将布局弹起多少高度
                            int reSizeLayoutHeight = lastVisibleViewBottom - keyboardTop;
                            //因为keyboardListenLayout的高度不包括外层的statusbar的高度和actionbar的高度
                            //所以需要减去status bar的高度
                            reSizeLayoutHeight -= getStatusBarHeight();
                            //如果界面里有actionbar并且处于显示状态则需要少减去actionbar的高度
                            if (null != (((AppCompatActivity) activity).getSupportActionBar()) && (((AppCompatActivity) activity).getSupportActionBar()).isShowing()) {
                                reSizeLayoutHeight -= getActionBarHeight();
                            }
                            //设置登录按钮与输入法之间间距
                            reSizeLayoutHeight += offset;
                            if (reSizeLayoutHeight > 0)
                                keyboardListenLayout.setPadding(0, -reSizeLayoutHeight, 0, 0);
                        } else {
                            //还原布局
                            keyboardListenLayout.setPadding(0, 0, 0, 0);
                        }
                    }
                }, 50);
            }
        });
    }

    private void bindLayout(final ViewGroup viewGroup, final View view) {
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastVisibleView = view;
                        if (lastVisibleView == null) {
                            lastVisibleView = activity.getCurrentFocus();
                        }
                        //获得屏幕高度
                        int screenHeight = viewGroup.getRootView().getHeight();
                        //r.bottom - r.top计算出输入法弹起后viewGroup的高度，屏幕高度-viewGroup高度即为键盘高度
                        Rect r = new Rect();
                        viewGroup.getWindowVisibleDisplayFrame(r);
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
                            int[] location = new int[2];
                            lastVisibleView.getLocationOnScreen(location);
                            //获取登录按钮底部在屏幕中的位置
                            int lastVisibleViewBottom = location[1] + lastVisibleView.getHeight();
                            //登录按钮底部在布局中的位置-输入法顶部的位置=需要将布局弹起多少高度
                            int reSizeLayoutHeight = lastVisibleViewBottom - keyboardTop;
                            //需要多弹起一个StatusBar的高度
                            reSizeLayoutHeight -= getStatusBarHeight();
                            //设置登录按钮与输入法之间间距
                            reSizeLayoutHeight += offset;
                            if (reSizeLayoutHeight > 0)
                                viewGroup.setPadding(0, -reSizeLayoutHeight, 0, 0);
                        }
                    }
                }, 50);
            }
        });
    }

    private void bindViewGroup(final ViewGroup viewGroup) {
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {   //获得屏幕高度
                        int screenHeight = viewGroup.getRootView().getHeight();
                        //r.bottom - r.top计算出输入法弹起后viewGroup的高度，屏幕高度-viewGroup高度即为键盘高度
                        Rect r = new Rect();
                        viewGroup.getWindowVisibleDisplayFrame(r);
                        int keyboardHeight = screenHeight - (r.bottom - r.top);
                        //当设置layout_keyboard设置完padding以后会重绘布局再次执行onGlobalLayout()
                        //所以判断如果键盘高度未改变就不执行下去
                        if (keyboardHeight == lastKeyBoardHeight) {
                            return;
                        }
                        lastKeyBoardHeight = keyboardHeight;
                        View view = activity.getWindow().getCurrentFocus();
                        if (keyboardHeight > 300 && null != view) {
                            if (view instanceof TextView) {
                                //计算出键盘最顶端在布局中的位置
                                int keyboardTop = screenHeight - keyboardHeight;
                                int[] location = new int[2];
                                view.getLocationOnScreen(location);
                                //获取登录按钮底部在屏幕中的位置
                                int viewBottom = location[1] + view.getHeight();
                                //比较输入框与键盘的位置关系，如果输入框在键盘之上的位置就不做处理
                                if (viewBottom <= keyboardTop)
                                    return;
                                //需要滚动的距离即为输入框底部到键盘的距离
                                int reSizeLayoutHeight = viewBottom - keyboardTop;
                                reSizeLayoutHeight -= getStatusBarHeight();
                                reSizeLayoutHeight += offset;
                                if (viewGroup instanceof ScrollView) {
                                    ((ScrollView) viewGroup).smoothScrollBy(0, reSizeLayoutHeight);
                                } else if (viewGroup instanceof RecyclerView) {
                                    ((RecyclerView) viewGroup).smoothScrollBy(0, reSizeLayoutHeight);
                                }
                            }
                        }
                    }
                }, 50);
            }
        });
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = activity.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    private int getActionBarHeight() {
        if (null != (((AppCompatActivity) activity).getSupportActionBar())) {
            //如果界面里有actionbar则需要多向上弹起一个actionbar的高度
            TypedValue typedValue = new TypedValue();
            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                return TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
            }
        }
        return 0;
    }

}
