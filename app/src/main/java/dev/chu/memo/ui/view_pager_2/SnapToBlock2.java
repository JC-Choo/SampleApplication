//package dev.chu.memo.ui.view_pager_2;
//
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.Interpolator;
//import android.widget.Scroller;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.view.ViewCompat;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.LinearSmoothScroller;
//import androidx.recyclerview.widget.OrientationHelper;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.SnapHelper;
//
//class SnapToBlock2 extends SnapHelper {
//
//    SnapToBlock2(int maxFlingBlocks) {
//        super();
//        mMaxFlingBlocks = maxFlingBlocks;
//    }
//
//    // Borrowed from ViewPager.java
//    private static final Interpolator sInterpolator = t -> {
//        t -= 1.0f;
//        return t * t * t + 1.0f;
//    };
//
//    private static final float MILLISECONDS_PER_INCH = 100f;
//    @SuppressWarnings("unused")
//    private static final String TAG = "SnapToBlock";
//
//    // 가장 활발하게 fling 하는 동안 움직일 최대 블럭들
//    private final int mMaxFlingBlocks;
//
//    private RecyclerView mRecyclerView;
//
//    // 리싸이클러뷰 내 뷰 블럭 내 아이템 전체 개수
//    private int blockSize;
//
//    // fling 에서 이동할 최대 위치 수
//    private int maxPositionsToMove;
//
//    // 방향이 수평이라면, 리싸이클러뷰 아이템의 넓이; 수직이라면 아이템의 높이
//    private int itemDimension;
//
//    // 블럭이 스냅(휙 던지다?)될 때 콜백 인터페이스
//    private SnapBlockCallback2 SnapBlockCallback2;
//
//    // 스내핑할 때, 스냅의 방향을 결정하는데 사용된다.
//    private int priorFirstPosition = RecyclerView.NO_POSITION;
//
//    // 우리의 개인 스크롤러
//    private Scroller scroller;
//
//    // Horizontal/vertical 레이아웃 헬퍼
//    private OrientationHelper mOrientationHelper;
//
//    // LTR/RTL helper
//    private LayoutDirectionHelper mLayoutDirectionHelper;
//
//    @Override
//    public void attachToRecyclerView(@Nullable final RecyclerView recyclerView) throws IllegalStateException {
//        if (recyclerView != null) {
//            mRecyclerView = recyclerView;
//            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            assert layoutManager != null;
//            if (layoutManager.canScrollHorizontally()) {
//                mOrientationHelper =
//                        OrientationHelper.createHorizontalHelper(layoutManager);
//                /** 왜있지? */
//                mLayoutDirectionHelper =
//                        new LayoutDirectionHelper(ViewCompat.getLayoutDirection(mRecyclerView));
//            } else if (layoutManager.canScrollVertically()) {
//                mOrientationHelper =
//                        OrientationHelper.createVerticalHelper(layoutManager);
//                // RTL doesn't matter for vertical scrolling for this class.
//                /** 왜있지? */
//                mLayoutDirectionHelper =
//                        new LayoutDirectionHelper(RecyclerView.LAYOUT_DIRECTION_LTR);
//            } else {
//                throw new IllegalStateException("RecyclerView must be scrollable");
//            }
//            scroller = new Scroller(mRecyclerView.getContext(), sInterpolator);
//            initItemDimensionIfNeeded(layoutManager);
//        }
//        super.attachToRecyclerView(recyclerView);
//    }
//
//    // 타겟 뷰가 사용 가능할 때 호출하며 우리는 리싸이클러뷰의 측면에 라인업(정렬)하기 위해 스크롤해야 할 양을 알아야만 한다.
//    @NonNull
//    @Override
//    public int[] calculateDistanceToFinalSnap(
//            @NonNull RecyclerView.LayoutManager layoutManager,
//            @NonNull View targetView) {
//        int[] out = new int[2];
//
//        if (layoutManager.canScrollHorizontally()) {
//            out[0] = mLayoutDirectionHelper.getScrollToAlignView(targetView);
//        }
//        if (layoutManager.canScrollVertically()) {
//            out[1] = mLayoutDirectionHelper.getScrollToAlignView(targetView);
//        }
//        if (SnapBlockCallback2 != null) {
//            if (out[0] == 0 && out[1] == 0) {
//                SnapBlockCallback2.onBlockSnapped(layoutManager.getPosition(targetView));
//            } else {
//                SnapBlockCallback2.onBlockSnap(layoutManager.getPosition(targetView));
//            }
//        }
//        return out;
//    }
//
//    // 우리는 flinging 하고 있으며 어디로 heading 하는 지 알아야만 한다.
//    @Override
//    public int findTargetSnapPosition(
//            RecyclerView.LayoutManager layoutManager,
//            int velocityX,
//            int velocityY) {
//        LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
//
//        initItemDimensionIfNeeded(layoutManager);
//        scroller.fling(
//                0,
//                0,
//                velocityX,
//                velocityY,
//                Integer.MIN_VALUE,
//                Integer.MAX_VALUE,
//                Integer.MIN_VALUE,
//                Integer.MAX_VALUE
//        );
//
//        if (velocityX != 0) {
//            return mLayoutDirectionHelper
//                    .getPositionsToMove(lm, scroller.getFinalX(), itemDimension);
//        }
//
//        if (velocityY != 0) {
//            return mLayoutDirectionHelper
//                    .getPositionsToMove(lm, scroller.getFinalY(), itemDimension);
//        }
//
//        return RecyclerView.NO_POSITION;
//    }
//
//    // 우리는 우리가 스냅할 곳까지 스크롤해야 한다. 스냅할 위치를 결정해라.
//    @Override
//    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
//        // Snap to a view that is either 1) toward the bottom of the data and therefore on screen,
//        // or, 2) toward the top of the data and may be off-screen.
//        int snapPos = calcTargetPosition((LinearLayoutManager) layoutManager);
//        View snapView =
//                (snapPos == RecyclerView.NO_POSITION) ? null : layoutManager.findViewByPosition(snapPos);
//
//        if (snapView == null) {
//            Log.d(TAG, "<<<<findSnapView is returning null!");
//        }
//        Log.d(TAG, "<<<<findSnapView snapos=" + snapPos);
//        return snapView;
//    }
//
//    // Does the heavy lifting for findSnapView.
//    // findSnapView 를 위한 헤비 리프팅을 한다.
//    private int calcTargetPosition(LinearLayoutManager layoutManager) {
//        int snapPos;
//        int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
//
//        if (firstVisiblePos == RecyclerView.NO_POSITION) {
//            return RecyclerView.NO_POSITION;
//        }
//        initItemDimensionIfNeeded(layoutManager);
//        if (firstVisiblePos >= priorFirstPosition) {
//            // Scrolling toward bottom of data
//            int firstCompletePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
//            if (firstCompletePosition != RecyclerView.NO_POSITION &&
//                    firstCompletePosition % blockSize == 0) {
//                snapPos = firstCompletePosition;
//            } else {
//                snapPos = roundDownToblockSize(firstVisiblePos + blockSize);
//            }
//        } else {
//            // Scrolling toward top of data
//            snapPos = roundDownToblockSize(firstVisiblePos);
//            // Check to see if target view exists. If it doesn't, force a smooth scroll.
//            // SnapHelper only snaps to existing views and will not scroll to a non-existant one.
//            // If limiting fling to single block, then the following is not needed since the
//            // views are likely to be in the RecyclerView pool.
//            if (layoutManager.findViewByPosition(snapPos) == null) {
//                int[] toScroll = mLayoutDirectionHelper.calculateDistanceToScroll(layoutManager, snapPos);
//                mRecyclerView.smoothScrollBy(toScroll[0], toScroll[1], sInterpolator);
//            }
//        }
//
//        priorFirstPosition = firstVisiblePos;
//        return snapPos;
//    }
//
//    private void initItemDimensionIfNeeded(final RecyclerView.LayoutManager layoutManager) {
//        if (itemDimension != 0) {
//            return;
//        }
//
//        View child;
//        if ((child = layoutManager.getChildAt(0)) == null) {
//            return;
//        }
//
//        if (layoutManager.canScrollHorizontally()) {
//            itemDimension = child.getWidth();
//            blockSize = getSpanCount(layoutManager) * (mRecyclerView.getWidth() / itemDimension);
//        } else if (layoutManager.canScrollVertically()) {
//            itemDimension = child.getHeight();
//            blockSize = getSpanCount(layoutManager) * (mRecyclerView.getHeight() / itemDimension);
//        }
//        maxPositionsToMove = blockSize * mMaxFlingBlocks;
//    }
//
//    private int getSpanCount(RecyclerView.LayoutManager layoutManager) {
//        return (layoutManager instanceof GridLayoutManager)
//                ? ((GridLayoutManager) layoutManager).getSpanCount()
//                : 1;
//    }
//
//    private int roundDownToblockSize(int trialPosition) {
//        return trialPosition - trialPosition % blockSize;
//    }
//
//    private int roundUpToblockSize(int trialPosition) {
//        return roundDownToblockSize(trialPosition + blockSize - 1);
//    }
//
//    @Nullable
//    protected LinearSmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
//        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
//            return null;
//        }
//        return new LinearSmoothScroller(mRecyclerView.getContext()) {
//            @Override
//            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
//                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(),
//                        targetView);
//                final int dx = snapDistances[0];
//                final int dy = snapDistances[1];
//                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
//                if (time > 0) {
//                    action.update(dx, dy, time, sInterpolator);
//                }
//            }
//
//            @Override
//            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
//            }
//        };
//    }
//
//    public void setSnapBlockCallback2(@Nullable SnapBlockCallback2 callback) {
//        SnapBlockCallback2 = callback;
//    }
//
//    // LTR and RTL 레이아웃에 대한 계산을 다루는 헬퍼 클래스
//    private class LayoutDirectionHelper {
//
//        // Is the layout an RTL one?
//        private final boolean mIsRTL;
//
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//        LayoutDirectionHelper(int direction) {
//            mIsRTL = direction == View.LAYOUT_DIRECTION_RTL;
//        }
//
//        // 타겟 뷰를 레이아웃 가장자리에 align(맞추는)데 필요한 스크롤의 양을 계산
//        int getScrollToAlignView(View targetView) {
//            return (mIsRTL)
//                    ? mOrientationHelper.getDecoratedEnd(targetView) - mRecyclerView.getWidth()
//                    : mOrientationHelper.getDecoratedStart(targetView);
//        }
//
//        /**
//         * Calculate the distance to final snap position when the view corresponding to the snap
//         * position is not currently available.
//         *
//         * @param layoutManager LinearLayoutManager or descendent class
//         * @param targetPos     - Adapter position to snap to
//         * @return int[2] {x-distance in pixels, y-distance in pixels}
//         */
//        int[] calculateDistanceToScroll(LinearLayoutManager layoutManager, int targetPos) {
//            int[] out = new int[2];
//
//            int firstVisiblePos;
//
//            firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
//            if (layoutManager.canScrollHorizontally()) {
//                if (targetPos <= firstVisiblePos) { // scrolling toward top of data
//                    if (mIsRTL) {
//                        View lastView = layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition());
//                        out[0] = mOrientationHelper.getDecoratedEnd(lastView)
//                                + (firstVisiblePos - targetPos) * itemDimension;
//                    } else {
//                        View firstView = layoutManager.findViewByPosition(firstVisiblePos);
//                        out[0] = mOrientationHelper.getDecoratedStart(firstView)
//                                - (firstVisiblePos - targetPos) * itemDimension;
//                    }
//                }
//            }
//            if (layoutManager.canScrollVertically()) {
//                if (targetPos <= firstVisiblePos) { // scrolling toward top of data
//                    View firstView = layoutManager.findViewByPosition(firstVisiblePos);
//                    out[1] = firstView.getTop() - (firstVisiblePos - targetPos) * itemDimension;
//                }
//            }
//
//            return out;
//        }
//
//        /*
//            Calculate the number of positions to move in the RecyclerView given a scroll amount
//            and the size of the items to be scrolled. Return integral multiple of blockSize not
//            equal to zero.
//         */
//        int getPositionsToMove(LinearLayoutManager llm, int scroll, int itemSize) {
//            int positionsToMove;
//
//            positionsToMove = roundUpToblockSize(Math.abs(scroll) / itemSize);
//
//            if (positionsToMove < blockSize) {
//                // Must move at least one block
//                positionsToMove = blockSize;
//            } else if (positionsToMove > maxPositionsToMove) {
//                // Clamp number of positions to move so we don't get wild flinging.
//                positionsToMove = maxPositionsToMove;
//            }
//
//            if (scroll < 0) {
//                positionsToMove *= -1;
//            }
//            if (mIsRTL) {
//                positionsToMove *= -1;
//            }
//
//            if (mLayoutDirectionHelper.isDirectionToBottom(scroll < 0)) {
//                // Scrolling toward the bottom of data.
//                return roundDownToblockSize(llm.findFirstVisibleItemPosition()) + positionsToMove;
//            }
//            // Scrolling toward the top of the data.
//            return roundDownToblockSize(llm.findLastVisibleItemPosition()) + positionsToMove;
//        }
//
//        boolean isDirectionToBottom(boolean velocityNegative) {
//            //noinspection SimplifiableConditionalExpression
//            return mIsRTL ? velocityNegative : !velocityNegative;
//        }
//    }
//
//    public interface SnapBlockCallback2 {
//        void onBlockSnap(int snapPosition);
//
//        void onBlockSnapped(int snapPosition);
//
//    }
//}