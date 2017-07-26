package com.study.dzx.library.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.dzx.library.R;
import com.study.dzx.library.utils.DensityUtils;


/**
 * Author : Xuan.
 * Data : 2017/7/12.
 * Description :
 * 星星评分-viewgroup
 * -可动画
 */

public class CustomAnimRatingBar extends LinearLayout {
    //星星个数
    private int mStarNum;
    //星星之间的距离
    private int mStarDistance;
    //星星的大小
    private int mStarSize;
    //空星星图片
    private Drawable mEmptyStar;
    //填充的星星的照片
    private Drawable mFillStar;
    //半个星星的图片
    private Drawable mHalfStar;
    //星星的进度
    private float mTouchStarMark;
    //上次的星星进度
    private float mLastMark;
    //是否显示半个
    private boolean mShowHalf;
    //显示星星的个数
    private int mShowNum;
    //触摸模式 1--单个星星 2--半个星星
    private int mMode;
    //是否可以触摸
    private boolean mTouchAble;

    private int mLastX;
    private int mLastY;

    //星星变化接口
    public interface onStarChangedListener {
        void onStarChange(CustomAnimRatingBar ratingBar, float mark);
    }

    private onStarChangedListener mOnStarChangeListener;

    public void setmOnStarChangeListener(onStarChangedListener mOnStarChangeListener) {
        this.mOnStarChangeListener = mOnStarChangeListener;
    }

    private Context mContext;

    public CustomAnimRatingBar(Context context) {
        this(context, null);
    }

    public CustomAnimRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAnimRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        initAttr(context, attrs);
        initView();
    }

    /**
     * 和ScrollView嵌套时滑动冲突
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            }

        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mTouchAble) {
            return super.onTouchEvent(event);
        }
        float x = event.getX();
        float touchStar = x / getWidth() * mStarNum;
        if (touchStar <= 0.5f) {
            touchStar = 0.5f;
        }
        if (touchStar > mStarNum) {
            touchStar = mStarNum;
        }

        switch (mMode) {
            case 1://整个星星
            {
                touchStar = (float) Math.ceil(touchStar);
                break;
            }
            case 2://half
            {
                if ((touchStar - Math.floor(touchStar) <= 0.5)&& touchStar - Math.floor(touchStar)!=0) {
                    touchStar = (float) (Math.floor(touchStar) + 0.5f);
                } else {
                    touchStar = (float) Math.ceil(touchStar);
                }
                break;
            }
        }

        mShowHalf = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                setRating(touchStar);
                ObjectAnimator
                        .ofFloat(getChildAt(mShowNum - 1), "translationY", 0, -20, 0)
                        .setDuration(300).start();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                setRating(touchStar);
                if (mTouchStarMark != mLastMark) {
                    ObjectAnimator
                            .ofFloat(getChildAt(mShowNum - 1), "translationY", 0, -20, 0)
                            .setDuration(300).start();
                }
                mLastMark = mTouchStarMark;
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }

        }
        return true;
    }

    private void fillStar() {
        mShowNum = (int) Math.ceil(mTouchStarMark);
        if ((mTouchStarMark - Math.floor(mTouchStarMark)) <= 0.5f &&
                mTouchStarMark - Math.floor(mTouchStarMark) != 0) {
            mShowNum = (int) Math.ceil(mTouchStarMark);
            mShowHalf = true;
        }
        for (int i = 0; i < mShowNum - 1; i++) {
            ((ImageView) getChildAt(i)).setImageDrawable(mFillStar);
        }
        if (mShowHalf) {
            ((ImageView) getChildAt(mShowNum - 1)).setImageDrawable(mHalfStar);
        } else {
            ((ImageView) getChildAt(mShowNum - 1)).setImageDrawable(mFillStar);
        }

        resetView();
    }

    /**
     * 设置评分
     */
    public void setRating(float touchStar) {

        if (mOnStarChangeListener != null) {
            this.mOnStarChangeListener.onStarChange(this, touchStar);
        }
        mTouchStarMark = touchStar;
        fillStar();
    }

    /**
     * 获得评分
     */
    public float getRating() {
        return mTouchStarMark;
    }

    /**
     * 设置是否可以点击
     */
    public void setTouchAble(boolean mTouchAble) {
        this.mTouchAble = mTouchAble;
    }

    /**
     * 重置空白星星
     */
    private void resetView() {
        for (int i = mStarNum - 1; i > mShowNum - 1; i--) {
            ((ImageView) getChildAt(i)).setImageDrawable(mEmptyStar);
        }
    }

    private void initView() {
        for (int i = 0; i < mStarNum; i++) {
            ImageView iv = new ImageView(mContext);
            LayoutParams layoutParams = new LayoutParams(mStarSize
                    , mStarSize);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.setMargins(mStarDistance / 2, 0, mStarDistance / 2, 0);
            iv.setLayoutParams(layoutParams);
            iv.setImageDrawable(mEmptyStar);
            this.addView(iv);
        }
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomAnimRatingBar);
        mStarNum = array.getInteger(R.styleable.CustomAnimRatingBar_starAnimNum, 5);
        mStarDistance = array.getDimensionPixelSize(R.styleable.CustomAnimRatingBar_starAnimDistance, DensityUtils.dp2px(context, 0));
        mStarSize = array.getDimensionPixelSize(R.styleable.CustomAnimRatingBar_starAnimSize, DensityUtils.dp2px(context, 20));
        mEmptyStar = array.getDrawable(R.styleable.CustomAnimRatingBar_starAnimEmpty);
        mFillStar = array.getDrawable(R.styleable.CustomAnimRatingBar_starAnimFill);
        mHalfStar = array.getDrawable(R.styleable.CustomAnimRatingBar_starAnimHalf);
        mMode = array.getInt(R.styleable.CustomAnimRatingBar_modeAnim, 2);
        mTouchAble = array.getBoolean(R.styleable.CustomAnimRatingBar_touchAbleAnim, true);
        mTouchStarMark = array.getInt(R.styleable.CustomAnimRatingBar_ratingAnimProgress, 0);
        array.recycle();
        this.mContext = context;
        if (mMode == 1) {
            mHalfStar = mFillStar;
        }
    }
}
