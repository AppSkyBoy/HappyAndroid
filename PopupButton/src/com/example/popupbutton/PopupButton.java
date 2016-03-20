package com.example.popupbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopupButton extends Button implements PopupWindow.OnDismissListener{

	private Context context;
	private PopupWindow popupWindow;
	private PopupButtonListener listener;
	/*
	 * 按钮点击效果
	 */
	private int normalBg;
	private int pressBg;
	private int normalIcon;
	private int pressIcon;
	/*
	 * 按钮内间距
	 */
	private int paddingTop;
	private int paddingLeft;
	private int paddingRight;
	private int paddingBottom;
	/*
	 * 屏幕宽高
	 */
	private int screenWidth;
	private int screenHeight;
	
	@SuppressLint("NewApi")
	public PopupButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		inItAttrs(attrs);
		inItViews();
	}

	public PopupButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inItAttrs(attrs);
		inItViews();
	}

	public PopupButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		inItAttrs(attrs);
		inItViews();
	}
	
	public PopupButton(Context context) {
		super(context);
		this.context = context;
	}

	private void inItViews() {
		paddingTop = this.getPaddingTop();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paddingBottom = this.getPaddingBottom();
        setNormal();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
		
	}

	private void setNormal() {
		if (normalBg != -1) {
            this.setBackgroundResource(normalBg);
            this.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        }
        if (normalIcon != -1) {
            Drawable drawable = getResources().getDrawable(normalIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
		
	}
	
	/**
     * 设置自定义接口
     * @param listener
     */
    public void setListener(PopupButtonListener listener) {
        this.listener = listener;
    }
	
	/**
     * 设置popupwindow的view
     * @param view
     */
    public void setPopupView(final View view) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow == null) {
                    LinearLayout layout = new LinearLayout(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.6));
                    view.setLayoutParams(params);
                    layout.addView(view);
                    layout.setBackgroundColor(Color.argb(60, 0, 0, 0));
                    popupWindow = new PopupWindow(layout,screenWidth,screenHeight);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(PopupButton.this);
                    layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                if(listener != null) {
                    listener.onShow();
                }
                setPress();
                popupWindow.showAsDropDown(PopupButton.this);
            }
        });
    }
    
    /**
     * 设置选中时候的按钮状态
     */
    private void setPress() {
        if (pressBg != -1) {
            this.setBackgroundResource(pressBg);
            this.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        }
        if (pressIcon != -1) {
            Drawable drawable = getResources().getDrawable(pressIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }
    
    /**
     * 隐藏弹出框
     */
    public void hidePopup(){
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

	private void inItAttrs(AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.popupbtn);
        normalBg = typedArray.getResourceId(R.styleable.popupbtn_normalBg, -1);
        pressBg = typedArray.getResourceId(R.styleable.popupbtn_pressBg, -1);
        normalIcon = typedArray.getResourceId(R.styleable.popupbtn_normalIcon, -1);
        pressIcon = typedArray.getResourceId(R.styleable.popupbtn_pressIcon, -1);
		typedArray.recycle();
	}

	@Override
	public void onDismiss() {
		setNormal();
        if(listener != null) {
            listener.onHide();
        }
	}

}
