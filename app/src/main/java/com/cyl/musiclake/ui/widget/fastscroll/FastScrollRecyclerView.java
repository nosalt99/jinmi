

package com.cyl.musiclake.ui.widget.fastscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import com.cyl.musiclake.R;


public class FastScrollRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "FastScrollRecyclerView";

    private FastScroller mScrollbar;

    private boolean mFastScrollEnabled = true;


    public static class ScrollPositionState {
        // The index of the first visible row
        int rowIndex;
        // The offset of the first visible row
        int rowTopOffset;
        // The height of a given row (they are currently all the same height)
        int rowHeight;
    }

    private ScrollPositionState mScrollPosState = new ScrollPositionState();

    private int mDownX;
    private int mDownY;
    private int mLastY;

    private SparseIntArray mScrollOffsets;

    private ScrollOffsetInvalidator mScrollOffsetInvalidator;
    private OnFastScrollStateChangeListener mStateChangeListener;

    public FastScrollRecyclerView(Context context) {
        this(context, null);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.FastScrollRecyclerView, 0, 0);
        try {
            mFastScrollEnabled = typedArray.getBoolean(R.styleable.FastScrollRecyclerView_fastScrollThumbEnabled, true);
        } finally {
            typedArray.recycle();
        }

        mScrollbar = new FastScroller(context, this, attrs);
        mScrollOffsetInvalidator = new ScrollOffsetInvalidator();
        mScrollOffsets = new SparseIntArray();
    }

    public int getScrollBarWidth() {
        return mScrollbar.getWidth();
    }

    public int getScrollBarThumbHeight() {
        return mScrollbar.getThumbHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addOnItemTouchListener(this);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(mScrollOffsetInvalidator);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(mScrollOffsetInvalidator);
        }

        super.setAdapter(adapter);
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
        return handleTouchEvent(ev);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent ev) {
        handleTouchEvent(ev);
    }


    private boolean handleTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Keep track of the down positions
                mDownX = x;
                mDownY = mLastY = y;
                mScrollbar.handleTouchEvent(ev, mDownX, mDownY, mLastY, mStateChangeListener);
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = y;
                mScrollbar.handleTouchEvent(ev, mDownX, mDownY, mLastY, mStateChangeListener);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mScrollbar.handleTouchEvent(ev, mDownX, mDownY, mLastY, mStateChangeListener);
                break;
        }
        return mScrollbar.isDragging();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    protected int getAvailableScrollHeight(int adapterHeight, int yOffset) {
        int visibleHeight = getHeight();
        int scrollHeight = getPaddingTop() + yOffset + adapterHeight + getPaddingBottom();
        return scrollHeight - visibleHeight;
    }


    protected int getAvailableScrollBarHeight() {
        int visibleHeight = getHeight();
        return visibleHeight - mScrollbar.getThumbHeight();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (mFastScrollEnabled) {
            onUpdateScrollbar();
            mScrollbar.draw(c);
        }
    }


    protected void updateThumbPosition(ScrollPositionState scrollPosState, int rowCount) {
        int availableScrollHeight;
        int availableScrollBarHeight;
        int scrolledPastHeight;

        if (getAdapter() instanceof MeasurableAdapter) {
            availableScrollHeight = getAvailableScrollHeight(calculateAdapterHeight(), 0);
            scrolledPastHeight = calculateScrollDistanceToPosition(scrollPosState.rowIndex);
        } else {
            availableScrollHeight = getAvailableScrollHeight(rowCount * scrollPosState.rowHeight, 0);
            scrolledPastHeight = scrollPosState.rowIndex * scrollPosState.rowHeight;
        }

        availableScrollBarHeight = getAvailableScrollBarHeight();

        // Only show the scrollbar if there is height to be scrolled
        if (availableScrollHeight <= 0) {
            mScrollbar.setThumbPosition(-1, -1);
            return;
        }

        // Calculate the current scroll position, the scrollY of the recycler view accounts for the
        // view padding, while the scrollBarY is drawn right up to the background padding (ignoring
        // padding)
        int scrollY = Math.min(availableScrollHeight, getPaddingTop() + scrolledPastHeight - scrollPosState.rowTopOffset);

        int scrollBarY = (int) (((float) scrollY / availableScrollHeight) * availableScrollBarHeight);

        // Calculate the position and size of the scroll bar
        int scrollBarX;
        if (Utils.isRtl(getResources())) {
            scrollBarX = 0;
        } else {
            scrollBarX = getWidth() - mScrollbar.getWidth();
        }
        mScrollbar.setThumbPosition(scrollBarX, scrollBarY);
    }


    public String scrollToPositionAtProgress(float touchFraction) {
        int itemCount = getAdapter().getItemCount();
        if (itemCount == 0) {
            return "";
        }
        int spanCount = 1;
        int rowCount = itemCount;
        if (getLayoutManager() instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
            rowCount = (int) Math.ceil((double) rowCount / spanCount);
        }

        // Stop the scroller if it is scrolling
        stopScroll();

        getCurScrollState(mScrollPosState);

        float itemPos;
        int availableScrollHeight;

        int scrollPosition;
        int scrollOffset;

        if (getAdapter() instanceof MeasurableAdapter) {
            itemPos = findItemPosition(touchFraction);
            availableScrollHeight = getAvailableScrollHeight(calculateAdapterHeight(), 0);
            int passedHeight = (int) (availableScrollHeight * touchFraction);
            scrollPosition = findMeasureAdapterFirstVisiblePosition(passedHeight);
            scrollOffset = calculateScrollDistanceToPosition(scrollPosition) - passedHeight;
        } else {
            itemPos = findItemPosition(touchFraction);
            availableScrollHeight = getAvailableScrollHeight(rowCount * mScrollPosState.rowHeight, 0);

            //The exact position of our desired item
            int exactItemPos = (int) (availableScrollHeight * touchFraction);

            //The offset used here is kind of hard to explain.
            //If the position we wish to scroll to is, say, position 10.5, we scroll to position 10,
            //and then offset by 0.5 * rowHeight. This is how we achieve smooth scrolling.
            scrollPosition = spanCount * exactItemPos / mScrollPosState.rowHeight;
            scrollOffset = -(exactItemPos % mScrollPosState.rowHeight);
        }

        LinearLayoutManager layoutManager = ((LinearLayoutManager) getLayoutManager());
        layoutManager.scrollToPositionWithOffset(scrollPosition, scrollOffset);

        if (!(getAdapter() instanceof SectionedAdapter)) {
            return "";
        }

        int posInt = (int) ((touchFraction == 1) ? getAdapter().getItemCount() - 1 : itemPos);

        SectionedAdapter sectionedAdapter = (SectionedAdapter) getAdapter();
        return sectionedAdapter.getSectionName(posInt);
    }

    @SuppressWarnings("unchecked")
    private int findMeasureAdapterFirstVisiblePosition(int passedHeight) {
        if (getAdapter() instanceof MeasurableAdapter) {
            MeasurableAdapter measurableAdapter = (MeasurableAdapter) getAdapter();
            for (int i = 0; i < getAdapter().getItemCount(); i++) {
                int top = calculateScrollDistanceToPosition(i);
                int bottom = top + measurableAdapter.getViewTypeHeight(this, findViewHolderForAdapterPosition(i), getAdapter().getItemViewType(i));
                if (i == getAdapter().getItemCount() - 1) {
                    if (passedHeight >= top && passedHeight <= bottom) {
                        return i;
                    }
                } else {
                    if (passedHeight >= top && passedHeight < bottom) {
                        return i;
                    }
                }
            }
            int low = calculateScrollDistanceToPosition(0);
            int height = calculateScrollDistanceToPosition(getAdapter().getItemCount() - 1)
                    + measurableAdapter.getViewTypeHeight(this, findViewHolderForAdapterPosition(getAdapter().getItemCount() - 1), getAdapter().getItemViewType(getAdapter().getItemCount() - 1));
            throw new IllegalStateException(String.format("Invalid passed height: %d, [low: %d, height: %d]", passedHeight, low, height));
        } else {
            throw new IllegalStateException("findMeasureAdapterFirstVisiblePosition() should only be called where the RecyclerView.Adapter is an instance of MeasurableAdapter");
        }

    }

    @SuppressWarnings("unchecked")
    private float findItemPosition(float touchFraction) {

        if (getAdapter() instanceof MeasurableAdapter) {
            MeasurableAdapter measurer = (MeasurableAdapter) getAdapter();
            int viewTop = (int) (touchFraction * calculateAdapterHeight());

            for (int i = 0; i < getAdapter().getItemCount(); i++) {
                int top = calculateScrollDistanceToPosition(i);
                int bottom = top + measurer.getViewTypeHeight(this, findViewHolderForAdapterPosition(i), getAdapter().getItemViewType(i));
                if (i == getAdapter().getItemCount() - 1) {
                    if (viewTop >= top && viewTop <= bottom) {
                        return i;
                    }
                } else {
                    if (viewTop >= top && viewTop < bottom) {
                        return i;
                    }
                }
            }

            // Should never happen
            Log.w(TAG, "Failed to find a view at the provided scroll fraction (" + touchFraction + ")");
            return touchFraction * getAdapter().getItemCount();
        } else {
            return getAdapter().getItemCount() * touchFraction;
        }
    }


    public void onUpdateScrollbar() {

        if (getAdapter() == null) {
            return;
        }

        int rowCount = getAdapter().getItemCount();
        if (getLayoutManager() instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
            rowCount = (int) Math.ceil((double) rowCount / spanCount);
        }
        // Skip early if, there are no items.
        if (rowCount == 0) {
            mScrollbar.setThumbPosition(-1, -1);
            return;
        }

        // Skip early if, there no child laid out in the container.
        getCurScrollState(mScrollPosState);
        if (mScrollPosState.rowIndex < 0) {
            mScrollbar.setThumbPosition(-1, -1);
            return;
        }

        updateThumbPosition(mScrollPosState, rowCount);
    }


    @SuppressWarnings("unchecked")
    private void getCurScrollState(ScrollPositionState stateOut) {
        stateOut.rowIndex = -1;
        stateOut.rowTopOffset = -1;
        stateOut.rowHeight = -1;

        int itemCount = getAdapter().getItemCount();

        // Return early if there are no items, or no children.
        if (itemCount == 0 || getChildCount() == 0) {
            return;
        }

        View child = getChildAt(0);

        stateOut.rowIndex = getChildAdapterPosition(child);
        if (getLayoutManager() instanceof GridLayoutManager) {
            stateOut.rowIndex = stateOut.rowIndex / ((GridLayoutManager) getLayoutManager()).getSpanCount();
        }
        if (getAdapter() instanceof MeasurableAdapter) {
            stateOut.rowTopOffset = getLayoutManager().getDecoratedTop(child);
            stateOut.rowHeight = ((MeasurableAdapter) getAdapter()).getViewTypeHeight(this, findViewHolderForAdapterPosition(stateOut.rowIndex), getAdapter().getItemViewType(stateOut.rowIndex));
        } else {
            stateOut.rowTopOffset = getLayoutManager().getDecoratedTop(child);
            stateOut.rowHeight = child.getHeight() + getLayoutManager().getTopDecorationHeight(child)
                    + getLayoutManager().getBottomDecorationHeight(child);
        }
    }


    @SuppressWarnings("unchecked")
    private int calculateScrollDistanceToPosition(int adapterIndex) {
        if (!(getAdapter() instanceof MeasurableAdapter)) {
            throw new IllegalStateException("calculateScrollDistanceToPosition() should only be called where the RecyclerView.Adapter is an instance of MeasurableAdapter");
        }

        if (mScrollOffsets.indexOfKey(adapterIndex) >= 0) {
            return mScrollOffsets.get(adapterIndex);
        }

        int totalHeight = 0;
        MeasurableAdapter measurer = (MeasurableAdapter) getAdapter();

        // TODO Take grid layouts into account

        for (int i = 0; i < adapterIndex; i++) {
            mScrollOffsets.put(i, totalHeight);
            int viewType = getAdapter().getItemViewType(i);
            totalHeight += measurer.getViewTypeHeight(this, findViewHolderForAdapterPosition(i), viewType);
        }

        mScrollOffsets.put(adapterIndex, totalHeight);
        return totalHeight;
    }


    private int calculateAdapterHeight() {
        if (!(getAdapter() instanceof MeasurableAdapter)) {
            throw new IllegalStateException("calculateAdapterHeight() should only be called where the RecyclerView.Adapter is an instance of MeasurableAdapter");
        }
        return calculateScrollDistanceToPosition(getAdapter().getItemCount());
    }

    public void showScrollbar() {
        mScrollbar.show();
    }

    public void setThumbColor(@ColorInt int color) {
        mScrollbar.setThumbColor(color);
    }

    public void setTrackColor(@ColorInt int color) {
        mScrollbar.setTrackColor(color);
    }

    public void setPopupBgColor(@ColorInt int color) {
        mScrollbar.setPopupBgColor(color);
    }

    public void setPopupTextColor(@ColorInt int color) {
        mScrollbar.setPopupTextColor(color);
    }

    public void setPopupTextSize(int textSize) {
        mScrollbar.setPopupTextSize(textSize);
    }

    public void setPopUpTypeface(Typeface typeface) {
        mScrollbar.setPopupTypeface(typeface);
    }

    public void setAutoHideDelay(int hideDelay) {
        mScrollbar.setAutoHideDelay(hideDelay);
    }

    public void setAutoHideEnabled(boolean autoHideEnabled) {
        mScrollbar.setAutoHideEnabled(autoHideEnabled);
    }

    public void setOnFastScrollStateChangeListener(OnFastScrollStateChangeListener stateChangeListener) {
        mStateChangeListener = stateChangeListener;
    }

    @Deprecated
    public void setStateChangeListener(OnFastScrollStateChangeListener stateChangeListener) {
        setOnFastScrollStateChangeListener(stateChangeListener);
    }

    public void setThumbInactiveColor(@ColorInt int color) {
        mScrollbar.setThumbInactiveColor(color);
    }

    public void allowThumbInactiveColor(boolean allowInactiveColor) {
        mScrollbar.enableThumbInactiveColor(allowInactiveColor);
    }

    @Deprecated
    public void setThumbInactiveColor(boolean allowInactiveColor) {
        allowThumbInactiveColor(allowInactiveColor);
    }

    public void setFastScrollEnabled(boolean fastScrollEnabled) {
        mFastScrollEnabled = fastScrollEnabled;
    }

    @Deprecated
    public void setThumbEnabled(boolean thumbEnabled) {
        setFastScrollEnabled(thumbEnabled);
    }


    public void setPopupPosition(@FastScroller.FastScrollerPopupPosition int popupPosition) {
        mScrollbar.setPopupPosition(popupPosition);
    }

    private class ScrollOffsetInvalidator extends AdapterDataObserver {
        private void invalidateAllScrollOffsets() {
            mScrollOffsets.clear();
        }

        @Override
        public void onChanged() {
            invalidateAllScrollOffsets();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            invalidateAllScrollOffsets();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            invalidateAllScrollOffsets();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            invalidateAllScrollOffsets();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            invalidateAllScrollOffsets();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            invalidateAllScrollOffsets();
        }
    }

    public interface SectionedAdapter {
        @NonNull
        String getSectionName(int position);
    }


    public interface MeasurableAdapter<VH extends ViewHolder> {

        int getViewTypeHeight(RecyclerView recyclerView, @Nullable VH viewHolder, int viewType);
    }
}
