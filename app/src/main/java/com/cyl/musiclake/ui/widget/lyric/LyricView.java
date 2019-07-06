//package com.cyl.musiclake.ui.view.lyric;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.LinearGradient;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Rect;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.text.Layout;
//import android.text.StaticLayout;
//import android.text.TextPaint;
//import android.util.AttributeSet;
//import com.cyl.musiclake.utils.LogUtil;
//import android.util.TypedValue;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.animation.DecelerateInterpolator;
//import android.view.animation.OvershootInterpolator;
//
//import com.cyl.musiclake.R;
//
//import java.io.File;
//import java.text.DecimalFormat;
//
//
//
//public class LyricView extends View {
//
//    private static final String TAG = "LyricView";
//    private int mBtnColor = Color.parseColor("#0091EA");  // 按钮颜色
//    private int mHintColor = Color.parseColor("#FFFFFF");  // 提示语颜色
//    private int mDefaultColor = Color.parseColor("#FFFFFF");  // 默认字体颜色
//    private int mIndicatorColor = Color.parseColor("#ffb701");  // 指示器颜色
//    private int mHighLightColor = Color.parseColor("#ffb701");  // 当前播放位置的颜色
//    private int mCurrentShowColor = Color.parseColor("#AAAAAA");  // 当前拖动位置的颜色
//
//    private int mLineCount;  // 行数
//    private float mLineHeight;  // 行高
//
//    private float mScrollY = 0;  // 纵轴偏移量
//    private float mVelocity = 0;  // 纵轴上的滑动速度
//    private float mLineSpace = 0;  // 行间距（包含在行高中）
//    private float mShaderWidth = 0;  // 渐变过渡的距离
//    private int mCurrentShowLine = 0;  // 当前拖动位置对应的行数
//    private int mCurrentPlayLine = 0;  // 当前播放位置对应的行数
//    private int mMinStartUpSpeed = 1600;  // 最低滑行启动速度
//
//    private boolean mUserTouch = false;  // 判断当前用户是否触摸
//    private boolean mIndicatorShow = false;  // 判断当前滑动指示器是否显示
//    private boolean mIsMoved = false;//判断用户触摸时是否发生move事件
//    private boolean mPlayerClick = false; //判断当前用户是否点击指示器
//
//
//    private int mBtnWidth = 0;  // Btn 按钮的宽度
//    private int mDefaultMargin = 12;
//    private int maximumFlingVelocity;  // 最大纵向滑动速度
//    private Rect mBtnBound, mTimerBound;
//    private VelocityTracker mVelocityTracker;
//
//    private LyricInfo mLyricInfo;
//    private String mDefaultTime = "00:00";
//    private String mDefaultHint = "暂无歌词";
//    private Paint mTextPaint, mBtnPaint, mIndicatorPaint;
//    private LinearGradient linearGradient;
//    private Bitmap bitmap;
//    private StaticLayout staticLayout;
//    private TextPaint mNextLinePaint;
//
//    private OnPlayerClickListener mClickListener;
//
//    private final int MSG_PLAYER_SLIDE = 0x158;
//    private final int MSG_PLAYER_HIDE = 0x157;
//
//    private ValueAnimator mFlingAnimator;
//    private boolean mPlayable = false;
//    private boolean mSliding = false;
//    private boolean mTouchable = true;
//
//    public LyricView(Context context) {
//        super(context);
//        initMyView(context);
//    }
//
//    public LyricView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initMyView(context);
//    }
//
//    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initMyView(context);
//    }
//
//    private void initMyView(Context context) {
//        maximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
//        initAllPaints();
//        initAllBounds();
//    }
//
//
//
//    private void initAllBounds() {
//        setTextSize(15);
//        setLineSpace(12);
//        mBtnWidth = (int) (getRawSize(TypedValue.COMPLEX_UNIT_SP, 36));
//        mTimerBound = new Rect();
//        mIndicatorPaint.getTextBounds(mDefaultTime, 0, mDefaultTime.length(), mTimerBound);
//
//        measureLineHeight();
//    }
//
//
//    private void initAllPaints() {
//        mTextPaint = new Paint();
//        mTextPaint.setDither(true);
//        mTextPaint.setAntiAlias(true);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);
//
//        mNextLinePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//        mNextLinePaint.setDither(true);
//        mNextLinePaint.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, 18));
//
//        mIndicatorPaint = new Paint();
//        mIndicatorPaint.setDither(true);
//        mIndicatorPaint.setAntiAlias(true);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_location);
//
//        mIndicatorPaint.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, 12));
//        mIndicatorPaint.setTextAlign(Paint.Align.RIGHT);
//
//        mBtnPaint = new Paint();
//        mBtnPaint.setDither(true);
//        mBtnPaint.setAntiAlias(true);
//        mBtnPaint.setColor(mDefaultColor);
//        mBtnPaint.setStrokeWidth(2.0f);
//        mBtnPaint.setStyle(Paint.Style.STROKE);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mShaderWidth = getMeasuredHeight() * 0.3f;
//    }
//
//    @SuppressLint("DrawAllocation")
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (mLyricInfo != null && mLyricInfo.songLines != null && mLyricInfo.songLines.size() > 0) {
//            for (int i = 0, line = 0, size = mLineCount; i < size; i++, line++) {
//                float x = 0f;//getMeasuredWidth() * 0.5f;
//                float y = getMeasuredHeight() * 0.5f + (line + 0.5f) * mLineHeight - 6 - mLineSpace * 0.5f - mScrollY;
//                if (y + mLineHeight * 0.5f < 0) {
//                    continue;
//                }
//                if (y - mLineHeight * 0.5f > getMeasuredHeight()) {
//                    break;
//                }
//                if (i == mCurrentPlayLine - 1) {
//                    mNextLinePaint.setColor(mHighLightColor);
//                } else {
//                    if (mIndicatorShow && i == mCurrentShowLine - 1) {
//                        mNextLinePaint.setColor(mCurrentShowColor);
//                    } else {
//                        mNextLinePaint.setColor(mDefaultColor);
//                    }
//                }
//                if (y > getMeasuredHeight() - mShaderWidth || y < mShaderWidth) {
//                    if (y < mShaderWidth) {
//                        mNextLinePaint.setAlpha(26 + (int) (23000.0f * y / mShaderWidth * 0.01f));
//                    } else {
//                        mNextLinePaint.setAlpha(26 + (int) (23000.0f * (getMeasuredHeight() - y) / mShaderWidth * 0.01f));
//                    }
//                } else {
//                    mNextLinePaint.setAlpha(255);
//                }
//                mTextPaint.setTextAlign(Paint.Align.LEFT);
//
//                String tt = mLyricInfo.songLines.get(i).content;
//                if (tt.trim().length() > 0) {
//                    canvas.save();
//                    int width = getMeasuredWidth() - mTimerBound.width() - mBtnWidth;
//                    canvas.translate(x, y);
//                    staticLayout =
//                            new StaticLayout(tt, mNextLinePaint, width, Layout.Alignment.ALIGN_NORMAL,
//                                    1, 0, true);
//                    line += staticLayout.getLineCount() - 1;
////                    mLineHeight = staticLayout.getLineCount() * mLineHeight;
//                    staticLayout.draw(canvas);
//                    canvas.restore();
//                }
//            }
//        } else {
//            mTextPaint.setColor(mHintColor);
//            mTextPaint.setTextAlign(Paint.Align.CENTER);
//            canvas.drawText(mDefaultHint, getMeasuredWidth() * 0.5f, (getMeasuredHeight() + mLineHeight - 6) * 0.5f, mTextPaint);
//        }
//
//        if (mIndicatorShow && scrollable()) {
//            if (mPlayable) {
//                drawPlayer(canvas);
//                drawIndicator(canvas);
//            }
//        }
//    }
//
//
//
//    private void drawPlayer(Canvas canvas) {
//        try {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_location);
//            mBtnBound = new Rect(getMeasuredWidth() - bitmap.getWidth(), (int) (getMeasuredHeight() * 0.5f - bitmap.getHeight())
//                    , getMeasuredWidth(), (int) (getMeasuredHeight() * 0.5f));
//
//            canvas.drawBitmap(bitmap, mBtnBound.left, mBtnBound.top, mBtnPaint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    private void drawIndicator(Canvas canvas) {
//        //绘制当前时间
//        mIndicatorPaint.setColor(Color.WHITE);
//        mIndicatorPaint.setAlpha(255);
//        mIndicatorPaint.setStyle(Paint.Style.FILL);
//        mIndicatorPaint.setShader(null);
//        canvas.drawText(measureCurrentTime(), getMeasuredWidth() - mBtnBound.width(), getMeasuredHeight() * 0.5f - mTimerBound.height() * 0.5f, mIndicatorPaint);
//
//        //绘制渐变线条
//        Path path = new Path();
//        mIndicatorPaint.setStrokeWidth(5.0f);
//        mIndicatorPaint.setStyle(Paint.Style.STROKE);
//        float x = getMeasuredWidth() - mBtnBound.width() - mTimerBound.width() * 2;
//        float y = getMeasuredHeight() * 0.5f;
//        linearGradient = new LinearGradient(x, y, x + mTimerBound.width() * 2, y,
//                new int[]{Color.TRANSPARENT, mIndicatorColor},
//                null, LinearGradient.TileMode.CLAMP);
//        mIndicatorPaint.setShader(linearGradient);
//        path.moveTo(x, y);
//        path.lineTo(x + mTimerBound.width() * 2, y);
//
//        canvas.drawPath(path, mIndicatorPaint);
//    }
//
//
//    private void measureLineHeight() {
//        Rect lineBound = new Rect();
//        mTextPaint.getTextBounds(mDefaultHint, 0, mDefaultHint.length(), lineBound);
//        mLineHeight = lineBound.height() + mLineSpace;
//    }
//
//
//    private String measureCurrentTime() {
//        DecimalFormat format = new DecimalFormat("00");
//        if (mLyricInfo != null && mLineCount > 0 && mCurrentShowLine - 1 < mLineCount && mCurrentShowLine > 0) {
//            return format.format(mLyricInfo.songLines.get(mCurrentShowLine - 1).start / 1000 / 60) + ":" + format.format(mLyricInfo.songLines.get(mCurrentShowLine - 1).start / 1000 % 60);
//        }
//        if (mLyricInfo != null && mLineCount > 0 && (mCurrentShowLine - 1) >= mLineCount) {
//            return format.format(mLyricInfo.songLines.get(mLineCount - 1).start / 1000 / 60) + ":" + format.format(mLyricInfo.songLines.get(mLineCount - 1).start / 1000 % 60);
//        }
//        if (mLyricInfo != null && mLineCount > 0 && mCurrentShowLine - 1 <= 0) {
//            return format.format(mLyricInfo.songLines.get(0).start / 1000 / 60) + ":" + format.format(mLyricInfo.songLines.get(0).start / 1000 % 60);
//        }
//        return mDefaultTime;
//    }
//
//    private float mDownX, mDownY, mLastScrollY;      // 记录手指按下时的坐标和当前的滑动偏移量
//
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        LogUtil.e("LyricView_dispatch", event.getAction() + "----");
//        final float x = event.getX();
//        final float y = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = x;
//                mDownY = y;
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float deltaX = Math.abs(x - mDownX);
//                final float deltaY = Math.abs(y - mDownY);
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (deltaX < deltaY) {
//                    LogUtil.e("MotionEvent", "down");
//                    setUserTouch(true);
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                } else {
//                    LogUtil.e("MotionEvent", "lefttoright");
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
//
//        return super.dispatchTouchEvent(event);
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.e("LyricView_onTouchEvent", event.getAction() + "----");
//
//        if (!mTouchable) {
//            return super.onTouchEvent(event);
//        }
//
//        if (mVelocityTracker == null) {
//            mVelocityTracker = VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_CANCEL:
//                actionCancel(event);
//                break;
//            case MotionEvent.ACTION_DOWN:
//                actionDown(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                actionMove(event);
//                break;
//            case MotionEvent.ACTION_UP:
//                actionUp(event);
//                break;
//            default:
//                break;
//        }
//        invalidateView();
//        if (mIsMoved || mPlayerClick) {
//            return true;
//        } else {
//            return super.onTouchEvent(event);
//        }
//    }
//
//
//    private void actionCancel(MotionEvent event) {
//        releaseVelocityTracker();
//    }
//
//
//    private void actionDown(MotionEvent event) {
//        postman.removeMessages(MSG_PLAYER_SLIDE);
//        postman.removeMessages(MSG_PLAYER_HIDE);
//        mLastScrollY = mScrollY;
//        mDownX = event.getX();
//        mDownY = event.getY();
//        if (mFlingAnimator != null) {
//            mFlingAnimator.cancel();
//            mFlingAnimator = null;
//        }
//        mIsMoved = false;
//        mPlayerClick = false;
//    }
//
//
//    private void actionMove(MotionEvent event) {
//        LogUtil.e("actionMove", scrollable() + "----");
//        if (scrollable()) {
//            final VelocityTracker tracker = mVelocityTracker;
//            tracker.computeCurrentVelocity(1000, maximumFlingVelocity);
//            float scrollY = mLastScrollY + mDownY - event.getY();   // 102  -2  58  42
//            float value01 = scrollY - (mLineCount * mLineHeight * 0.5f);   // 52  -52  8  -8
//            float value02 = ((Math.abs(value01) - (mLineCount * mLineHeight * 0.5f)));   // 2  2  -42  -42
//            mScrollY = value02 > 0 ? scrollY - (measureDampingDistance(value02) * value01 / Math.abs(value01)) : scrollY;   //   value01 / Math.abs(value01)  控制滑动方向
//            mVelocity = tracker.getYVelocity();
//            measureCurrentLine();
//            if (Math.abs(mVelocity) > 1) {
//                mIsMoved = true;
//            }
//        }
//    }
//
//
//    private final int mMaxDampingDistance = 360;
//
//    private float measureDampingDistance(float value02) {
//        return value02 > mMaxDampingDistance ? (mMaxDampingDistance * 0.6f + (value02 - mMaxDampingDistance) * 0.72f) : value02 * 0.6f;
//    }
//
//
//    private void actionUp(MotionEvent event) {
//        releaseVelocityTracker();
//        // 2.4s 后发送一个指示器隐藏的请求
//        postman.sendEmptyMessageDelayed(MSG_PLAYER_HIDE, 2400);
//        if (scrollable()) {
//            setUserTouch(false);  // 用户手指离开屏幕，取消触摸标记
//            if (overScrolled() && mScrollY < 0) {
//                smoothScrollTo(0);
//                return;
//            }
//            if (overScrolled() && mScrollY > mLineHeight * (mLineCount - 1)) {
//                smoothScrollTo(mLineHeight * (mLineCount - 1));
//                return;
//            }
//            if (Math.abs(mVelocity) > mMinStartUpSpeed) {
//                doFlingAnimator(mVelocity);
//                return;
//            }
//            if (mIndicatorShow && clickPlayer(event)) {
//                if (mCurrentShowLine != mCurrentPlayLine) {
//                    mIndicatorShow = false;
//                    mPlayerClick = true;
//                    if (mClickListener != null) {
//                        mClickListener.onPlayerClicked(mLyricInfo.songLines.get(mCurrentShowLine - 1).start, mLyricInfo.songLines.get(mCurrentShowLine - 1).content);
//                    }
//                }
//            }
//        } else {
//            performClick();
//        }
//    }
//
//
//    private void invalidateView() {
//        if (Looper.getMainLooper() == Looper.myLooper()) {
//            //  当前线程是主UI线程，直接刷新。
//            invalidate();
//        } else {
//            //  当前线程是非UI线程，post刷新。
//            postInvalidate();
//        }
//    }
//
//
//    private void setUserTouch(boolean isUserTouch) {
//        if (mUserTouch == isUserTouch) {
//            return;
//        }
//        mUserTouch = isUserTouch;
//        if (isUserTouch) {
//            mIndicatorShow = isUserTouch;
//        }
//    }
//
//
//    private void releaseVelocityTracker() {
//        if (null != mVelocityTracker) {
//            mVelocityTracker.clear();
//            mVelocityTracker.recycle();
//            mVelocityTracker = null;
//        }
//    }
//
//
//    private void doFlingAnimator(float velocity) {
//        //注：     Math.abs(velocity)  < =  16000
//        float distance = (velocity / Math.abs(velocity) * Math.min((Math.abs(velocity) * 0.050f), 640));   // 计算就已当前的滑动速度理论上的滑行距离是多少
//        float to = Math.min(Math.max(0, (mScrollY - distance)), (mLineCount - 1) * mLineHeight);   // 综合考虑边界问题后得出的实际滑行距离
//
//        mFlingAnimator = ValueAnimator.ofFloat(mScrollY, to);
//        mFlingAnimator.addUpdateListener(animation -> {
//            mScrollY = (float) animation.getAnimatedValue();
//            measureCurrentLine();
//            invalidateView();
//        });
//
//        mFlingAnimator.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                mVelocity = mMinStartUpSpeed - 1;
//                mSliding = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                mSliding = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//        });
//
//        mFlingAnimator.setDuration(420);
//        mFlingAnimator.setInterpolator(new DecelerateInterpolator());
//        mFlingAnimator.start();
//    }
//
//
//    private void measureCurrentLine() {
//        float baseScrollY = mScrollY + mLineHeight * 0.5f;
//        mCurrentShowLine = (int) (baseScrollY / mLineHeight + 1);
//    }
//
//
//    private float measureCurrentScrollY(int line) {
//        return (line - 1) * mLineHeight;
//    }
//
//
//    private boolean clickPlayer(MotionEvent event) {
//        if (mBtnBound != null && mDownX > (mBtnBound.left - mDefaultMargin) && mDownX < (mBtnBound.right + mDefaultMargin) && mDownY > (mBtnBound.top - mDefaultMargin) && mDownY < (mBtnBound.bottom + mDefaultMargin)) {
//            float upX = event.getX();
//            float upY = event.getY();
//            return upX > (mBtnBound.left - mDefaultMargin) && upX < (mBtnBound.right + mDefaultMargin) && upY > (mBtnBound.top - mDefaultMargin) && upY < (mBtnBound.bottom + mDefaultMargin);
//        }
//        return false;
//    }
//
//
//    private void smoothScrollTo(float toY) {
//        final ValueAnimator animator = ValueAnimator.ofFloat(mScrollY, toY);
//        animator.addUpdateListener(animation -> {
//            if (mUserTouch) {
//                animator.cancel();
//                return;
//            }
//            mScrollY = (float) animation.getAnimatedValue();
//            invalidateView();
//        });
//
//        animator.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                mSliding = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                mSliding = false;
//                measureCurrentLine();
//                invalidateView();
//            }
//        });
//        animator.setDuration(640);
//        animator.setInterpolator(new OvershootInterpolator(0.5f));
//
//        animator.start();
//    }
//
//
//    private boolean scrollable() {
//        return mLyricInfo != null && mLyricInfo.songLines != null && mLyricInfo.songLines.size() > 0;
//    }
//
//
//    private boolean overScrolled() {
//        return scrollable() && (mScrollY > mLineHeight * (mLineCount - 1) || mScrollY < 0);
//    }
//
//    @SuppressLint("HandlerLeak")
//    Handler postman = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case MSG_PLAYER_HIDE:
//                    postman.sendEmptyMessageDelayed(MSG_PLAYER_SLIDE, 1200);
//                    mIndicatorShow = false;
//                    invalidateView();
//                case MSG_PLAYER_SLIDE:
//                    smoothScrollTo(measureCurrentScrollY(mCurrentPlayLine));
//                    invalidateView();
//            }
//        }
//    };
//
//
//    private void scrollToCurrentTimeMillis(long time) {
//        int position = 0;
//        if (scrollable()) {
//            for (int i = 0, size = mLineCount; i < size; i++) {
//                LyricInfo.LineInfo lineInfo = mLyricInfo.songLines.get(i);
//                if (lineInfo != null && lineInfo.start > time) {
//                    position = i;
//                    LogUtil.d(TAG, lineInfo.toString() + "--" + mCurrentPlayLine + "----" + i);
//                    break;
//                }
//                if (i == mLineCount - 1) {
//                    position = mLineCount - 1;
//                }
//            }
//        }
//        if (mCurrentPlayLine != position && !mUserTouch && !mSliding && !mIndicatorShow) {
//            mCurrentPlayLine = position;
//            smoothScrollTo(measureCurrentScrollY(position));
//        } else {
//            if (!mSliding && !mIndicatorShow) {
//                mCurrentPlayLine = mCurrentShowLine = position;
//            }
//        }
//    }
//
//
//    private void resetView() {
//        mCurrentPlayLine = mCurrentShowLine = 0;
//        mLyricInfo = null;
//        invalidateView();
//        mLineCount = 0;
//        mScrollY = 0;
//    }
//
//    public interface OnPlayerClickListener {
//        void onPlayerClicked(long progress, String content);
//    }
//
//    private void setRawTextSize(float size) {
//        if (size != mTextPaint.getTextSize()) {
//            mTextPaint.setTextSize(size);
//            measureLineHeight();
//            mScrollY = measureCurrentScrollY(mCurrentPlayLine);
//            invalidateView();
//        }
//    }
//
//    private float getRawSize(int unit, float size) {
//        Context context = getContext();
//        Resources resources;
//        if (context == null) {
//            resources = Resources.getSystem();
//        } else {
//            resources = context.getResources();
//        }
//        return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
//    }
//
//
//
//
//
//    public void setCurrentTimeMillis(long current) {
//        scrollToCurrentTimeMillis(current);
//    }
//
//
//    public void setLyricFile(File file, String charsetName) {
//        mLyricInfo = LyricParseUtils.setLyricResource(file);
//        if (mLyricInfo != null && mLyricInfo.getSongLines() != null)
//            mLineCount = mLyricInfo.getSongLines().size();
//        invalidateView();
//    }
//
//
//    public void setLyricContent(String lyricInfo, String charsetName) {
//        LogUtil.e("LyricView ", lyricInfo);
//        mLyricInfo = LyricParseUtils.setLyricResource(lyricInfo);
//        if (mLyricInfo != null && mLyricInfo.getSongLines() != null)
//            mLineCount = mLyricInfo.getSongLines().size();
//        invalidateView();
//    }
//
//
//    public void setOnPlayerClickListener(OnPlayerClickListener mClickListener) {
//        this.mClickListener = mClickListener;
//    }
//
//
//    public void reset(String message) {
//        mDefaultHint = message;
//        resetView();
//    }
//
//
//    public void setHighLightTextColor(int color) {
//        if (mHighLightColor != color) {
//            mHighLightColor = color;
//            invalidateView();
//        }
//    }
//
//    public void setDefaultColor(int color) {
//        if (mDefaultColor != color) {
//            mDefaultColor = color;
//            invalidateView();
//        }
//    }
//
//    public int getDefaultColor() {
//        return mDefaultColor;
//    }
//
//
//    public void setLineSpace(float lineSpace) {
//        if (mLineSpace != lineSpace) {
//            mLineSpace = getRawSize(TypedValue.COMPLEX_UNIT_SP, lineSpace);
//            measureLineHeight();
//            mScrollY = measureCurrentScrollY(mCurrentPlayLine);
//            invalidateView();
//        }
//    }
//
//
//    public void setTextSize(int unit, float size) {
//        setRawTextSize(getRawSize(unit, size));
//    }
//
//
//    public void setTextSize(float size) {
//        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
//    }
//
//    public void setPlayable(boolean playable) {
//        mPlayable = playable;
//    }
//
//    public void setTouchable(boolean touchable) {
//        mTouchable = touchable;
//    }
//
//    public void setHintColor(int color) {
//        if (mHintColor != color) {
//            mHintColor = color;
//            invalidate();
//        }
//    }
//
//}
