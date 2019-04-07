package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper
  extends RecyclerView.ItemDecoration
  implements RecyclerView.OnChildAttachStateChangeListener
{
  static final int ACTION_MODE_DRAG_MASK = 16711680;
  private static final int ACTION_MODE_IDLE_MASK = 255;
  static final int ACTION_MODE_SWIPE_MASK = 65280;
  public static final int ACTION_STATE_DRAG = 2;
  public static final int ACTION_STATE_IDLE = 0;
  public static final int ACTION_STATE_SWIPE = 1;
  static final int ACTIVE_POINTER_ID_NONE = -1;
  public static final int ANIMATION_TYPE_DRAG = 8;
  public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
  public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
  static final boolean DEBUG = false;
  static final int DIRECTION_FLAG_COUNT = 8;
  public static final int DOWN = 2;
  public static final int END = 32;
  public static final int LEFT = 4;
  private static final int PIXELS_PER_SECOND = 1000;
  public static final int RIGHT = 8;
  public static final int START = 16;
  static final String TAG = "ItemTouchHelper";
  public static final int UP = 1;
  int mActionState = 0;
  int mActivePointerId = -1;
  Callback mCallback;
  private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
  private List<Integer> mDistances;
  private long mDragScrollStartTimeInMs;
  float mDx;
  float mDy;
  GestureDetectorCompat mGestureDetector;
  float mInitialTouchX;
  float mInitialTouchY;
  float mMaxSwipeVelocity;
  private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener()
  {
    public boolean onInterceptTouchEvent(RecyclerView paramAnonymousRecyclerView, MotionEvent paramAnonymousMotionEvent)
    {
      ItemTouchHelper.this.mGestureDetector.onTouchEvent(paramAnonymousMotionEvent);
      int i = paramAnonymousMotionEvent.getActionMasked();
      boolean bool = true;
      if (i == 0)
      {
        ItemTouchHelper.this.mActivePointerId = paramAnonymousMotionEvent.getPointerId(0);
        ItemTouchHelper.this.mInitialTouchX = paramAnonymousMotionEvent.getX();
        ItemTouchHelper.this.mInitialTouchY = paramAnonymousMotionEvent.getY();
        ItemTouchHelper.this.obtainVelocityTracker();
        if (ItemTouchHelper.this.mSelected == null)
        {
          paramAnonymousRecyclerView = ItemTouchHelper.this.findAnimation(paramAnonymousMotionEvent);
          if (paramAnonymousRecyclerView != null)
          {
            ItemTouchHelper localItemTouchHelper = ItemTouchHelper.this;
            localItemTouchHelper.mInitialTouchX -= paramAnonymousRecyclerView.l;
            localItemTouchHelper = ItemTouchHelper.this;
            localItemTouchHelper.mInitialTouchY -= paramAnonymousRecyclerView.m;
            ItemTouchHelper.this.endRecoverAnimation(paramAnonymousRecyclerView.h, true);
            if (ItemTouchHelper.this.mPendingCleanup.remove(paramAnonymousRecyclerView.h.itemView)) {
              ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, paramAnonymousRecyclerView.h);
            }
            ItemTouchHelper.this.select(paramAnonymousRecyclerView.h, paramAnonymousRecyclerView.i);
            paramAnonymousRecyclerView = ItemTouchHelper.this;
            paramAnonymousRecyclerView.updateDxDy(paramAnonymousMotionEvent, paramAnonymousRecyclerView.mSelectedFlags, 0);
          }
        }
      }
      else if ((i != 3) && (i != 1))
      {
        if (ItemTouchHelper.this.mActivePointerId != -1)
        {
          int j = paramAnonymousMotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
          if (j >= 0) {
            ItemTouchHelper.this.checkSelectForSwipe(i, paramAnonymousMotionEvent, j);
          }
        }
      }
      else
      {
        paramAnonymousRecyclerView = ItemTouchHelper.this;
        paramAnonymousRecyclerView.mActivePointerId = -1;
        paramAnonymousRecyclerView.select(null, 0);
      }
      if (ItemTouchHelper.this.mVelocityTracker != null) {
        ItemTouchHelper.this.mVelocityTracker.addMovement(paramAnonymousMotionEvent);
      }
      if (ItemTouchHelper.this.mSelected == null) {
        bool = false;
      }
      return bool;
    }
    
    public void onRequestDisallowInterceptTouchEvent(boolean paramAnonymousBoolean)
    {
      if (!paramAnonymousBoolean) {
        return;
      }
      ItemTouchHelper.this.select(null, 0);
    }
    
    public void onTouchEvent(RecyclerView paramAnonymousRecyclerView, MotionEvent paramAnonymousMotionEvent)
    {
      ItemTouchHelper.this.mGestureDetector.onTouchEvent(paramAnonymousMotionEvent);
      if (ItemTouchHelper.this.mVelocityTracker != null) {
        ItemTouchHelper.this.mVelocityTracker.addMovement(paramAnonymousMotionEvent);
      }
      if (ItemTouchHelper.this.mActivePointerId == -1) {
        return;
      }
      int i = paramAnonymousMotionEvent.getActionMasked();
      int j = paramAnonymousMotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
      if (j >= 0) {
        ItemTouchHelper.this.checkSelectForSwipe(i, paramAnonymousMotionEvent, j);
      }
      RecyclerView.ViewHolder localViewHolder = ItemTouchHelper.this.mSelected;
      if (localViewHolder == null) {
        return;
      }
      int k = 0;
      if (i != 6)
      {
        switch (i)
        {
        default: 
          break;
        case 3: 
          if (ItemTouchHelper.this.mVelocityTracker != null) {
            ItemTouchHelper.this.mVelocityTracker.clear();
          }
          break;
        case 2: 
          if (j < 0) {
            return;
          }
          paramAnonymousRecyclerView = ItemTouchHelper.this;
          paramAnonymousRecyclerView.updateDxDy(paramAnonymousMotionEvent, paramAnonymousRecyclerView.mSelectedFlags, j);
          ItemTouchHelper.this.moveIfNecessary(localViewHolder);
          ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
          ItemTouchHelper.this.mScrollRunnable.run();
          ItemTouchHelper.this.mRecyclerView.invalidate();
          break;
        }
        ItemTouchHelper.this.select(null, 0);
        ItemTouchHelper.this.mActivePointerId = -1;
      }
      else
      {
        i = paramAnonymousMotionEvent.getActionIndex();
        if (paramAnonymousMotionEvent.getPointerId(i) == ItemTouchHelper.this.mActivePointerId)
        {
          if (i == 0) {
            k = 1;
          }
          ItemTouchHelper.this.mActivePointerId = paramAnonymousMotionEvent.getPointerId(k);
          paramAnonymousRecyclerView = ItemTouchHelper.this;
          paramAnonymousRecyclerView.updateDxDy(paramAnonymousMotionEvent, paramAnonymousRecyclerView.mSelectedFlags, i);
        }
      }
    }
  };
  View mOverdrawChild = null;
  int mOverdrawChildPosition = -1;
  final List<View> mPendingCleanup = new ArrayList();
  List<b> mRecoverAnimations = new ArrayList();
  RecyclerView mRecyclerView;
  final Runnable mScrollRunnable = new Runnable()
  {
    public void run()
    {
      if ((ItemTouchHelper.this.mSelected != null) && (ItemTouchHelper.this.scrollIfNecessary()))
      {
        if (ItemTouchHelper.this.mSelected != null)
        {
          ItemTouchHelper localItemTouchHelper = ItemTouchHelper.this;
          localItemTouchHelper.moveIfNecessary(localItemTouchHelper.mSelected);
        }
        ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
        ViewCompat.postOnAnimation(ItemTouchHelper.this.mRecyclerView, this);
      }
    }
  };
  RecyclerView.ViewHolder mSelected = null;
  int mSelectedFlags;
  float mSelectedStartX;
  float mSelectedStartY;
  private int mSlop;
  private List<RecyclerView.ViewHolder> mSwapTargets;
  float mSwipeEscapeVelocity;
  private final float[] mTmpPosition = new float[2];
  private Rect mTmpRect;
  VelocityTracker mVelocityTracker;
  
  public ItemTouchHelper(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  private void addChildDrawingOrderCallback()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return;
    }
    if (this.mChildDrawingOrderCallback == null) {
      this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback()
      {
        public int onGetChildDrawingOrder(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          if (ItemTouchHelper.this.mOverdrawChild == null) {
            return paramAnonymousInt2;
          }
          int i = ItemTouchHelper.this.mOverdrawChildPosition;
          int j = i;
          if (i == -1)
          {
            j = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
            ItemTouchHelper.this.mOverdrawChildPosition = j;
          }
          if (paramAnonymousInt2 == paramAnonymousInt1 - 1) {
            return j;
          }
          if (paramAnonymousInt2 >= j) {
            paramAnonymousInt2++;
          }
          return paramAnonymousInt2;
        }
      };
    }
    this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
  }
  
  private int checkHorizontalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramInt & 0xC) != 0)
    {
      float f1 = this.mDx;
      int i = 8;
      int j;
      if (f1 > 0.0F) {
        j = 8;
      } else {
        j = 4;
      }
      VelocityTracker localVelocityTracker = this.mVelocityTracker;
      if ((localVelocityTracker != null) && (this.mActivePointerId > -1))
      {
        localVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
        f2 = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        f1 = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        if (f2 <= 0.0F) {
          i = 4;
        }
        f2 = Math.abs(f2);
        if (((i & paramInt) != 0) && (j == i) && (f2 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity)) && (f2 > Math.abs(f1))) {
          return i;
        }
      }
      f1 = this.mRecyclerView.getWidth();
      float f2 = this.mCallback.getSwipeThreshold(paramViewHolder);
      if (((paramInt & j) != 0) && (Math.abs(this.mDx) > f1 * f2)) {
        return j;
      }
    }
    return 0;
  }
  
  private int checkVerticalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramInt & 0x3) != 0)
    {
      float f1 = this.mDy;
      int i = 2;
      int j;
      if (f1 > 0.0F) {
        j = 2;
      } else {
        j = 1;
      }
      VelocityTracker localVelocityTracker = this.mVelocityTracker;
      if ((localVelocityTracker != null) && (this.mActivePointerId > -1))
      {
        localVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
        f1 = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        f2 = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        if (f2 <= 0.0F) {
          i = 1;
        }
        f2 = Math.abs(f2);
        if (((i & paramInt) != 0) && (i == j) && (f2 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity)) && (f2 > Math.abs(f1))) {
          return i;
        }
      }
      float f2 = this.mRecyclerView.getHeight();
      f1 = this.mCallback.getSwipeThreshold(paramViewHolder);
      if (((paramInt & j) != 0) && (Math.abs(this.mDy) > f2 * f1)) {
        return j;
      }
    }
    return 0;
  }
  
  private void destroyCallbacks()
  {
    this.mRecyclerView.removeItemDecoration(this);
    this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--)
    {
      b localb = (b)this.mRecoverAnimations.get(0);
      this.mCallback.clearView(this.mRecyclerView, localb.h);
    }
    this.mRecoverAnimations.clear();
    this.mOverdrawChild = null;
    this.mOverdrawChildPosition = -1;
    releaseVelocityTracker();
  }
  
  private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder paramViewHolder)
  {
    Object localObject1 = paramViewHolder;
    Object localObject2 = this.mSwapTargets;
    if (localObject2 == null)
    {
      this.mSwapTargets = new ArrayList();
      this.mDistances = new ArrayList();
    }
    else
    {
      ((List)localObject2).clear();
      this.mDistances.clear();
    }
    int i = this.mCallback.getBoundingBoxMargin();
    int j = Math.round(this.mSelectedStartX + this.mDx) - i;
    int k = Math.round(this.mSelectedStartY + this.mDy) - i;
    int m = ((RecyclerView.ViewHolder)localObject1).itemView.getWidth();
    i *= 2;
    int n = m + j + i;
    int i1 = ((RecyclerView.ViewHolder)localObject1).itemView.getHeight() + k + i;
    int i2 = (j + n) / 2;
    int i3 = (k + i1) / 2;
    localObject2 = this.mRecyclerView.getLayoutManager();
    int i4 = ((RecyclerView.LayoutManager)localObject2).getChildCount();
    for (i = 0; i < i4; i++)
    {
      localObject1 = ((RecyclerView.LayoutManager)localObject2).getChildAt(i);
      if ((localObject1 != paramViewHolder.itemView) && (((View)localObject1).getBottom() >= k) && (((View)localObject1).getTop() <= i1) && (((View)localObject1).getRight() >= j) && (((View)localObject1).getLeft() <= n))
      {
        RecyclerView.ViewHolder localViewHolder = this.mRecyclerView.getChildViewHolder((View)localObject1);
        if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, localViewHolder))
        {
          m = Math.abs(i2 - (((View)localObject1).getLeft() + ((View)localObject1).getRight()) / 2);
          int i5 = Math.abs(i3 - (((View)localObject1).getTop() + ((View)localObject1).getBottom()) / 2);
          int i6 = m * m + i5 * i5;
          int i7 = this.mSwapTargets.size();
          i5 = 0;
          m = 0;
          while ((i5 < i7) && (i6 > ((Integer)this.mDistances.get(i5)).intValue()))
          {
            m++;
            i5++;
          }
          this.mSwapTargets.add(m, localViewHolder);
          this.mDistances.add(m, Integer.valueOf(i6));
        }
      }
    }
    return this.mSwapTargets;
  }
  
  private RecyclerView.ViewHolder findSwipedView(MotionEvent paramMotionEvent)
  {
    RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
    int i = this.mActivePointerId;
    if (i == -1) {
      return null;
    }
    i = paramMotionEvent.findPointerIndex(i);
    float f1 = paramMotionEvent.getX(i);
    float f2 = this.mInitialTouchX;
    float f3 = paramMotionEvent.getY(i);
    float f4 = this.mInitialTouchY;
    f1 = Math.abs(f1 - f2);
    f3 = Math.abs(f3 - f4);
    i = this.mSlop;
    if ((f1 < i) && (f3 < i)) {
      return null;
    }
    if ((f1 > f3) && (localLayoutManager.canScrollHorizontally())) {
      return null;
    }
    if ((f3 > f1) && (localLayoutManager.canScrollVertically())) {
      return null;
    }
    paramMotionEvent = findChildView(paramMotionEvent);
    if (paramMotionEvent == null) {
      return null;
    }
    return this.mRecyclerView.getChildViewHolder(paramMotionEvent);
  }
  
  private void getSelectedDxDy(float[] paramArrayOfFloat)
  {
    if ((this.mSelectedFlags & 0xC) != 0) {
      paramArrayOfFloat[0] = (this.mSelectedStartX + this.mDx - this.mSelected.itemView.getLeft());
    } else {
      paramArrayOfFloat[0] = this.mSelected.itemView.getTranslationX();
    }
    if ((this.mSelectedFlags & 0x3) != 0) {
      paramArrayOfFloat[1] = (this.mSelectedStartY + this.mDy - this.mSelected.itemView.getTop());
    } else {
      paramArrayOfFloat[1] = this.mSelected.itemView.getTranslationY();
    }
  }
  
  private static boolean hitTest(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool;
    if ((paramFloat1 >= paramFloat3) && (paramFloat1 <= paramFloat3 + paramView.getWidth()) && (paramFloat2 >= paramFloat4) && (paramFloat2 <= paramFloat4 + paramView.getHeight())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void initGestureDetector()
  {
    if (this.mGestureDetector != null) {
      return;
    }
    this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), new a());
  }
  
  private void releaseVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private void setupCallbacks()
  {
    this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
    this.mRecyclerView.addItemDecoration(this);
    this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.addOnChildAttachStateChangeListener(this);
    initGestureDetector();
  }
  
  private int swipeIfNecessary(RecyclerView.ViewHolder paramViewHolder)
  {
    if (this.mActionState == 2) {
      return 0;
    }
    int i = this.mCallback.getMovementFlags(this.mRecyclerView, paramViewHolder);
    int j = (this.mCallback.convertToAbsoluteDirection(i, ViewCompat.getLayoutDirection(this.mRecyclerView)) & 0xFF00) >> 8;
    if (j == 0) {
      return 0;
    }
    i = (i & 0xFF00) >> 8;
    int k;
    if (Math.abs(this.mDx) > Math.abs(this.mDy))
    {
      k = checkHorizontalSwipe(paramViewHolder, j);
      if (k > 0)
      {
        if ((i & k) == 0) {
          return Callback.convertToRelativeDirection(k, ViewCompat.getLayoutDirection(this.mRecyclerView));
        }
        return k;
      }
      i = checkVerticalSwipe(paramViewHolder, j);
      if (i > 0) {
        return i;
      }
    }
    else
    {
      k = checkVerticalSwipe(paramViewHolder, j);
      if (k > 0) {
        return k;
      }
      j = checkHorizontalSwipe(paramViewHolder, j);
      if (j > 0)
      {
        if ((i & j) == 0) {
          return Callback.convertToRelativeDirection(j, ViewCompat.getLayoutDirection(this.mRecyclerView));
        }
        return j;
      }
    }
    return 0;
  }
  
  public void attachToRecyclerView(RecyclerView paramRecyclerView)
  {
    RecyclerView localRecyclerView = this.mRecyclerView;
    if (localRecyclerView == paramRecyclerView) {
      return;
    }
    if (localRecyclerView != null) {
      destroyCallbacks();
    }
    this.mRecyclerView = paramRecyclerView;
    if (this.mRecyclerView != null)
    {
      paramRecyclerView = paramRecyclerView.getResources();
      this.mSwipeEscapeVelocity = paramRecyclerView.getDimension(android.support.v7.e.a.a.item_touch_helper_swipe_escape_velocity);
      this.mMaxSwipeVelocity = paramRecyclerView.getDimension(android.support.v7.e.a.a.item_touch_helper_swipe_escape_max_velocity);
      setupCallbacks();
    }
  }
  
  boolean checkSelectForSwipe(int paramInt1, MotionEvent paramMotionEvent, int paramInt2)
  {
    if ((this.mSelected == null) && (paramInt1 == 2) && (this.mActionState != 2) && (this.mCallback.isItemViewSwipeEnabled()))
    {
      if (this.mRecyclerView.getScrollState() == 1) {
        return false;
      }
      RecyclerView.ViewHolder localViewHolder = findSwipedView(paramMotionEvent);
      if (localViewHolder == null) {
        return false;
      }
      paramInt1 = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, localViewHolder) & 0xFF00) >> 8;
      if (paramInt1 == 0) {
        return false;
      }
      float f1 = paramMotionEvent.getX(paramInt2);
      float f2 = paramMotionEvent.getY(paramInt2);
      f1 -= this.mInitialTouchX;
      float f3 = f2 - this.mInitialTouchY;
      f2 = Math.abs(f1);
      float f4 = Math.abs(f3);
      paramInt2 = this.mSlop;
      if ((f2 < paramInt2) && (f4 < paramInt2)) {
        return false;
      }
      if (f2 > f4)
      {
        if ((f1 < 0.0F) && ((paramInt1 & 0x4) == 0)) {
          return false;
        }
        if ((f1 > 0.0F) && ((paramInt1 & 0x8) == 0)) {
          return false;
        }
      }
      else
      {
        if ((f3 < 0.0F) && ((paramInt1 & 0x1) == 0)) {
          return false;
        }
        if ((f3 > 0.0F) && ((paramInt1 & 0x2) == 0)) {
          return false;
        }
      }
      this.mDy = 0.0F;
      this.mDx = 0.0F;
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      select(localViewHolder, 1);
      return true;
    }
    return false;
  }
  
  int endRecoverAnimation(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
  {
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--)
    {
      b localb = (b)this.mRecoverAnimations.get(i);
      if (localb.h == paramViewHolder)
      {
        localb.n |= paramBoolean;
        if (!localb.o) {
          localb.b();
        }
        this.mRecoverAnimations.remove(i);
        return localb.j;
      }
    }
    return 0;
  }
  
  b findAnimation(MotionEvent paramMotionEvent)
  {
    if (this.mRecoverAnimations.isEmpty()) {
      return null;
    }
    paramMotionEvent = findChildView(paramMotionEvent);
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--)
    {
      b localb = (b)this.mRecoverAnimations.get(i);
      if (localb.h.itemView == paramMotionEvent) {
        return localb;
      }
    }
    return null;
  }
  
  View findChildView(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    paramMotionEvent = this.mSelected;
    if (paramMotionEvent != null)
    {
      paramMotionEvent = paramMotionEvent.itemView;
      if (hitTest(paramMotionEvent, f1, f2, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
        return paramMotionEvent;
      }
    }
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--)
    {
      b localb = (b)this.mRecoverAnimations.get(i);
      paramMotionEvent = localb.h.itemView;
      if (hitTest(paramMotionEvent, f1, f2, localb.l, localb.m)) {
        return paramMotionEvent;
      }
    }
    return this.mRecyclerView.findChildViewUnder(f1, f2);
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    paramRect.setEmpty();
  }
  
  boolean hasRunningRecoverAnim()
  {
    int i = this.mRecoverAnimations.size();
    for (int j = 0; j < i; j++) {
      if (!((b)this.mRecoverAnimations.get(j)).o) {
        return true;
      }
    }
    return false;
  }
  
  void moveIfNecessary(RecyclerView.ViewHolder paramViewHolder)
  {
    if (this.mRecyclerView.isLayoutRequested()) {
      return;
    }
    if (this.mActionState != 2) {
      return;
    }
    float f = this.mCallback.getMoveThreshold(paramViewHolder);
    int i = (int)(this.mSelectedStartX + this.mDx);
    int j = (int)(this.mSelectedStartY + this.mDy);
    if ((Math.abs(j - paramViewHolder.itemView.getTop()) < paramViewHolder.itemView.getHeight() * f) && (Math.abs(i - paramViewHolder.itemView.getLeft()) < paramViewHolder.itemView.getWidth() * f)) {
      return;
    }
    Object localObject = findSwapTargets(paramViewHolder);
    if (((List)localObject).size() == 0) {
      return;
    }
    localObject = this.mCallback.chooseDropTarget(paramViewHolder, (List)localObject, i, j);
    if (localObject == null)
    {
      this.mSwapTargets.clear();
      this.mDistances.clear();
      return;
    }
    int k = ((RecyclerView.ViewHolder)localObject).getAdapterPosition();
    int m = paramViewHolder.getAdapterPosition();
    if (this.mCallback.onMove(this.mRecyclerView, paramViewHolder, (RecyclerView.ViewHolder)localObject)) {
      this.mCallback.onMoved(this.mRecyclerView, paramViewHolder, m, (RecyclerView.ViewHolder)localObject, k, i, j);
    }
  }
  
  void obtainVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null) {
      localVelocityTracker.recycle();
    }
    this.mVelocityTracker = VelocityTracker.obtain();
  }
  
  public void onChildViewAttachedToWindow(View paramView) {}
  
  public void onChildViewDetachedFromWindow(View paramView)
  {
    removeChildDrawingOrderCallbackIfNecessary(paramView);
    paramView = this.mRecyclerView.getChildViewHolder(paramView);
    if (paramView == null) {
      return;
    }
    RecyclerView.ViewHolder localViewHolder = this.mSelected;
    if ((localViewHolder != null) && (paramView == localViewHolder))
    {
      select(null, 0);
    }
    else
    {
      endRecoverAnimation(paramView, false);
      if (this.mPendingCleanup.remove(paramView.itemView)) {
        this.mCallback.clearView(this.mRecyclerView, paramView);
      }
    }
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    this.mOverdrawChildPosition = -1;
    float f1;
    float f2;
    if (this.mSelected != null)
    {
      getSelectedDxDy(this.mTmpPosition);
      paramState = this.mTmpPosition;
      f1 = paramState[0];
      f2 = paramState[1];
    }
    else
    {
      f1 = 0.0F;
      f2 = 0.0F;
    }
    this.mCallback.onDraw(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }
  
  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    float f1;
    float f2;
    if (this.mSelected != null)
    {
      getSelectedDxDy(this.mTmpPosition);
      paramState = this.mTmpPosition;
      f1 = paramState[0];
      f2 = paramState[1];
    }
    else
    {
      f1 = 0.0F;
      f2 = 0.0F;
    }
    this.mCallback.onDrawOver(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }
  
  void postDispatchSwipe(final b paramb, final int paramInt)
  {
    this.mRecyclerView.post(new Runnable()
    {
      public void run()
      {
        if ((ItemTouchHelper.this.mRecyclerView != null) && (ItemTouchHelper.this.mRecyclerView.isAttachedToWindow()) && (!paramb.n) && (paramb.h.getAdapterPosition() != -1))
        {
          RecyclerView.ItemAnimator localItemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
          if (((localItemAnimator == null) || (!localItemAnimator.isRunning(null))) && (!ItemTouchHelper.this.hasRunningRecoverAnim())) {
            ItemTouchHelper.this.mCallback.onSwiped(paramb.h, paramInt);
          } else {
            ItemTouchHelper.this.mRecyclerView.post(this);
          }
        }
      }
    });
  }
  
  void removeChildDrawingOrderCallbackIfNecessary(View paramView)
  {
    if (paramView == this.mOverdrawChild)
    {
      this.mOverdrawChild = null;
      if (this.mChildDrawingOrderCallback != null) {
        this.mRecyclerView.setChildDrawingOrderCallback(null);
      }
    }
  }
  
  boolean scrollIfNecessary()
  {
    if (this.mSelected == null)
    {
      this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
      return false;
    }
    long l1 = System.currentTimeMillis();
    long l2 = this.mDragScrollStartTimeInMs;
    if (l2 == Long.MIN_VALUE) {
      l2 = 0L;
    } else {
      l2 = l1 - l2;
    }
    RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
    if (this.mTmpRect == null) {
      this.mTmpRect = new Rect();
    }
    localLayoutManager.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
    if (localLayoutManager.canScrollHorizontally())
    {
      i = (int)(this.mSelectedStartX + this.mDx);
      j = i - this.mTmpRect.left - this.mRecyclerView.getPaddingLeft();
      if ((this.mDx < 0.0F) && (j < 0)) {
        break label201;
      }
      if (this.mDx > 0.0F)
      {
        j = i + this.mSelected.itemView.getWidth() + this.mTmpRect.right - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
        if (j > 0) {
          break label201;
        }
      }
    }
    int j = 0;
    label201:
    if (localLayoutManager.canScrollVertically())
    {
      int k = (int)(this.mSelectedStartY + this.mDy);
      i = k - this.mTmpRect.top - this.mRecyclerView.getPaddingTop();
      if ((this.mDy < 0.0F) && (i < 0)) {
        break label317;
      }
      if (this.mDy > 0.0F)
      {
        i = k + this.mSelected.itemView.getHeight() + this.mTmpRect.bottom - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
        if (i > 0) {
          break label317;
        }
      }
    }
    int i = 0;
    label317:
    if (j != 0) {
      j = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), j, this.mRecyclerView.getWidth(), l2);
    }
    if (i != 0) {
      i = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), i, this.mRecyclerView.getHeight(), l2);
    }
    if ((j == 0) && (i == 0))
    {
      this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
      return false;
    }
    if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
      this.mDragScrollStartTimeInMs = l1;
    }
    this.mRecyclerView.scrollBy(j, i);
    return true;
  }
  
  void select(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramViewHolder == this.mSelected) && (paramInt == this.mActionState)) {
      return;
    }
    this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
    int i = this.mActionState;
    endRecoverAnimation(paramViewHolder, true);
    this.mActionState = paramInt;
    if (paramInt == 2)
    {
      this.mOverdrawChild = paramViewHolder.itemView;
      addChildDrawingOrderCallback();
    }
    final Object localObject = this.mSelected;
    int k;
    if (localObject != null)
    {
      if (((RecyclerView.ViewHolder)localObject).itemView.getParent() != null)
      {
        final int j;
        if (i == 2) {
          j = 0;
        } else {
          j = swipeIfNecessary((RecyclerView.ViewHolder)localObject);
        }
        releaseVelocityTracker();
        if ((j != 4) && (j != 8) && (j != 16) && (j != 32)) {}
        float f1;
        float f2;
        switch (j)
        {
        default: 
          f1 = 0.0F;
          f2 = 0.0F;
          break;
        case 1: 
        case 2: 
          f2 = Math.signum(this.mDy) * this.mRecyclerView.getHeight();
          f1 = 0.0F;
          break;
          f1 = Math.signum(this.mDx) * this.mRecyclerView.getWidth();
          f2 = 0.0F;
        }
        if (i == 2) {
          k = 8;
        } else if (j > 0) {
          k = 2;
        } else {
          k = 4;
        }
        getSelectedDxDy(this.mTmpPosition);
        float[] arrayOfFloat = this.mTmpPosition;
        float f3 = arrayOfFloat[0];
        float f4 = arrayOfFloat[1];
        localObject = new b((RecyclerView.ViewHolder)localObject, k, i, f3, f4, f1, f2)
        {
          public void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            super.onAnimationEnd(paramAnonymousAnimator);
            if (this.n) {
              return;
            }
            if (j <= 0)
            {
              ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, localObject);
            }
            else
            {
              ItemTouchHelper.this.mPendingCleanup.add(localObject.itemView);
              this.k = true;
              int i = j;
              if (i > 0) {
                ItemTouchHelper.this.postDispatchSwipe(this, i);
              }
            }
            if (ItemTouchHelper.this.mOverdrawChild == localObject.itemView) {
              ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(localObject.itemView);
            }
          }
        };
        ((b)localObject).a(this.mCallback.getAnimationDuration(this.mRecyclerView, k, f1 - f3, f2 - f4));
        this.mRecoverAnimations.add(localObject);
        ((b)localObject).a();
        k = 1;
      }
      else
      {
        removeChildDrawingOrderCallbackIfNecessary(((RecyclerView.ViewHolder)localObject).itemView);
        this.mCallback.clearView(this.mRecyclerView, (RecyclerView.ViewHolder)localObject);
        k = 0;
      }
      this.mSelected = null;
    }
    else
    {
      k = 0;
    }
    if (paramViewHolder != null)
    {
      this.mSelectedFlags = ((this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, paramViewHolder) & (1 << paramInt * 8 + 8) - 1) >> this.mActionState * 8);
      this.mSelectedStartX = paramViewHolder.itemView.getLeft();
      this.mSelectedStartY = paramViewHolder.itemView.getTop();
      this.mSelected = paramViewHolder;
      if (paramInt == 2) {
        this.mSelected.itemView.performHapticFeedback(0);
      } else {}
    }
    boolean bool = false;
    paramViewHolder = this.mRecyclerView.getParent();
    if (paramViewHolder != null)
    {
      if (this.mSelected != null) {
        bool = true;
      }
      paramViewHolder.requestDisallowInterceptTouchEvent(bool);
    }
    if (k == 0) {
      this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
    }
    this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
    this.mRecyclerView.invalidate();
  }
  
  public void startDrag(RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mCallback.hasDragFlag(this.mRecyclerView, paramViewHolder))
    {
      Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
      return;
    }
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView)
    {
      Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
      return;
    }
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 2);
  }
  
  public void startSwipe(RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, paramViewHolder))
    {
      Log.e("ItemTouchHelper", "Start swipe has been called but swiping is not enabled");
      return;
    }
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView)
    {
      Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
      return;
    }
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 1);
  }
  
  void updateDxDy(MotionEvent paramMotionEvent, int paramInt1, int paramInt2)
  {
    float f1 = paramMotionEvent.getX(paramInt2);
    float f2 = paramMotionEvent.getY(paramInt2);
    this.mDx = (f1 - this.mInitialTouchX);
    this.mDy = (f2 - this.mInitialTouchY);
    if ((paramInt1 & 0x4) == 0) {
      this.mDx = Math.max(0.0F, this.mDx);
    }
    if ((paramInt1 & 0x8) == 0) {
      this.mDx = Math.min(0.0F, this.mDx);
    }
    if ((paramInt1 & 0x1) == 0) {
      this.mDy = Math.max(0.0F, this.mDy);
    }
    if ((paramInt1 & 0x2) == 0) {
      this.mDy = Math.min(0.0F, this.mDy);
    }
  }
  
  public static abstract class Callback
  {
    private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
    public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
    public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
    private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000L;
    static final int RELATIVE_DIR_FLAGS = 3158064;
    private static final Interpolator sDragScrollInterpolator = new Interpolator()
    {
      public float getInterpolation(float paramAnonymousFloat)
      {
        return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat;
      }
    };
    private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator()
    {
      public float getInterpolation(float paramAnonymousFloat)
      {
        paramAnonymousFloat -= 1.0F;
        return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
      }
    };
    private static final ItemTouchUIUtil sUICallback;
    private int mCachedMaxScrollSpeed = -1;
    
    static
    {
      if (Build.VERSION.SDK_INT >= 21) {
        sUICallback = new a.a();
      } else {
        sUICallback = new a.b();
      }
    }
    
    public static int convertToRelativeDirection(int paramInt1, int paramInt2)
    {
      int i = paramInt1 & 0xC0C0C;
      if (i == 0) {
        return paramInt1;
      }
      paramInt1 &= (i ^ 0xFFFFFFFF);
      if (paramInt2 == 0) {
        return paramInt1 | i << 2;
      }
      paramInt2 = i << 1;
      return paramInt1 | 0xFFF3F3F3 & paramInt2 | (paramInt2 & 0xC0C0C) << 2;
    }
    
    public static ItemTouchUIUtil getDefaultUIUtil()
    {
      return sUICallback;
    }
    
    private int getMaxDragScroll(RecyclerView paramRecyclerView)
    {
      if (this.mCachedMaxScrollSpeed == -1) {
        this.mCachedMaxScrollSpeed = paramRecyclerView.getResources().getDimensionPixelSize(android.support.v7.e.a.a.item_touch_helper_max_drag_scroll_per_frame);
      }
      return this.mCachedMaxScrollSpeed;
    }
    
    public static int makeFlag(int paramInt1, int paramInt2)
    {
      return paramInt2 << paramInt1 * 8;
    }
    
    public static int makeMovementFlags(int paramInt1, int paramInt2)
    {
      int i = makeFlag(0, paramInt2 | paramInt1);
      paramInt2 = makeFlag(1, paramInt2);
      return makeFlag(2, paramInt1) | paramInt2 | i;
    }
    
    public boolean canDropOver(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2)
    {
      return true;
    }
    
    public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder paramViewHolder, List<RecyclerView.ViewHolder> paramList, int paramInt1, int paramInt2)
    {
      int i = paramViewHolder.itemView.getWidth();
      int j = paramViewHolder.itemView.getHeight();
      int k = paramInt1 - paramViewHolder.itemView.getLeft();
      int m = paramInt2 - paramViewHolder.itemView.getTop();
      int n = paramList.size();
      Object localObject1 = null;
      int i1 = -1;
      for (int i2 = 0; i2 < n; i2++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)paramList.get(i2);
        if (k > 0)
        {
          i3 = localViewHolder.itemView.getRight() - (paramInt1 + i);
          if ((i3 < 0) && (localViewHolder.itemView.getRight() > paramViewHolder.itemView.getRight()))
          {
            i3 = Math.abs(i3);
            if (i3 > i1)
            {
              localObject1 = localViewHolder;
              break label146;
            }
          }
        }
        int i3 = i1;
        label146:
        Object localObject2 = localObject1;
        i1 = i3;
        int i4;
        if (k < 0)
        {
          i4 = localViewHolder.itemView.getLeft() - paramInt1;
          localObject2 = localObject1;
          i1 = i3;
          if (i4 > 0)
          {
            localObject2 = localObject1;
            i1 = i3;
            if (localViewHolder.itemView.getLeft() < paramViewHolder.itemView.getLeft())
            {
              i4 = Math.abs(i4);
              localObject2 = localObject1;
              i1 = i3;
              if (i4 > i3)
              {
                i1 = i4;
                localObject2 = localViewHolder;
              }
            }
          }
        }
        localObject1 = localObject2;
        i3 = i1;
        if (m < 0)
        {
          i4 = localViewHolder.itemView.getTop() - paramInt2;
          localObject1 = localObject2;
          i3 = i1;
          if (i4 > 0)
          {
            localObject1 = localObject2;
            i3 = i1;
            if (localViewHolder.itemView.getTop() < paramViewHolder.itemView.getTop())
            {
              i4 = Math.abs(i4);
              localObject1 = localObject2;
              i3 = i1;
              if (i4 > i1)
              {
                i3 = i4;
                localObject1 = localViewHolder;
              }
            }
          }
        }
        if (m > 0)
        {
          i1 = localViewHolder.itemView.getBottom() - (paramInt2 + j);
          if ((i1 < 0) && (localViewHolder.itemView.getBottom() > paramViewHolder.itemView.getBottom()))
          {
            i1 = Math.abs(i1);
            if (i1 > i3)
            {
              localObject1 = localViewHolder;
              continue;
            }
          }
        }
        i1 = i3;
      }
      return (RecyclerView.ViewHolder)localObject1;
    }
    
    public void clearView(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      sUICallback.clearView(paramViewHolder.itemView);
    }
    
    public int convertToAbsoluteDirection(int paramInt1, int paramInt2)
    {
      int i = paramInt1 & 0x303030;
      if (i == 0) {
        return paramInt1;
      }
      paramInt1 &= (i ^ 0xFFFFFFFF);
      if (paramInt2 == 0) {
        return paramInt1 | i >> 2;
      }
      paramInt2 = i >> 1;
      return paramInt1 | 0xFFCFCFCF & paramInt2 | (paramInt2 & 0x303030) >> 2;
    }
    
    final int getAbsoluteMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return convertToAbsoluteDirection(getMovementFlags(paramRecyclerView, paramViewHolder), ViewCompat.getLayoutDirection(paramRecyclerView));
    }
    
    public long getAnimationDuration(RecyclerView paramRecyclerView, int paramInt, float paramFloat1, float paramFloat2)
    {
      paramRecyclerView = paramRecyclerView.getItemAnimator();
      long l;
      if (paramRecyclerView == null)
      {
        if (paramInt == 8) {
          l = 200L;
        } else {
          l = 250L;
        }
        return l;
      }
      if (paramInt == 8) {
        l = paramRecyclerView.getMoveDuration();
      } else {
        l = paramRecyclerView.getRemoveDuration();
      }
      return l;
    }
    
    public int getBoundingBoxMargin()
    {
      return 0;
    }
    
    public float getMoveThreshold(RecyclerView.ViewHolder paramViewHolder)
    {
      return 0.5F;
    }
    
    public abstract int getMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder);
    
    public float getSwipeEscapeVelocity(float paramFloat)
    {
      return paramFloat;
    }
    
    public float getSwipeThreshold(RecyclerView.ViewHolder paramViewHolder)
    {
      return 0.5F;
    }
    
    public float getSwipeVelocityThreshold(float paramFloat)
    {
      return paramFloat;
    }
    
    boolean hasDragFlag(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool;
      if ((getAbsoluteMovementFlags(paramRecyclerView, paramViewHolder) & 0xFF0000) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean hasSwipeFlag(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool;
      if ((getAbsoluteMovementFlags(paramRecyclerView, paramViewHolder) & 0xFF00) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int interpolateOutOfBoundsScroll(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
      int i = getMaxDragScroll(paramRecyclerView);
      int j = Math.abs(paramInt2);
      paramInt3 = (int)Math.signum(paramInt2);
      float f1 = j;
      float f2 = 1.0F;
      f1 = Math.min(1.0F, f1 * 1.0F / paramInt1);
      paramInt1 = (int)(paramInt3 * i * sDragViewScrollCapInterpolator.getInterpolation(f1));
      if (paramLong <= 2000L) {
        f2 = (float)paramLong / 2000.0F;
      }
      paramInt1 = (int)(paramInt1 * sDragScrollInterpolator.getInterpolation(f2));
      if (paramInt1 == 0)
      {
        if (paramInt2 > 0) {
          paramInt1 = 1;
        } else {
          paramInt1 = -1;
        }
        return paramInt1;
      }
      return paramInt1;
    }
    
    public boolean isItemViewSwipeEnabled()
    {
      return true;
    }
    
    public boolean isLongPressDragEnabled()
    {
      return true;
    }
    
    public void onChildDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      sUICallback.onDraw(paramCanvas, paramRecyclerView, paramViewHolder.itemView, paramFloat1, paramFloat2, paramInt, paramBoolean);
    }
    
    public void onChildDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      sUICallback.onDrawOver(paramCanvas, paramRecyclerView, paramViewHolder.itemView, paramFloat1, paramFloat2, paramInt, paramBoolean);
    }
    
    void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, List<ItemTouchHelper.b> paramList, int paramInt, float paramFloat1, float paramFloat2)
    {
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        ItemTouchHelper.b localb = (ItemTouchHelper.b)paramList.get(j);
        localb.c();
        int k = paramCanvas.save();
        onChildDraw(paramCanvas, paramRecyclerView, localb.h, localb.l, localb.m, localb.i, false);
        paramCanvas.restoreToCount(k);
      }
      if (paramViewHolder != null)
      {
        j = paramCanvas.save();
        onChildDraw(paramCanvas, paramRecyclerView, paramViewHolder, paramFloat1, paramFloat2, paramInt, true);
        paramCanvas.restoreToCount(j);
      }
    }
    
    void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, List<ItemTouchHelper.b> paramList, int paramInt, float paramFloat1, float paramFloat2)
    {
      int i = paramList.size();
      int j = 0;
      for (int k = 0; k < i; k++)
      {
        ItemTouchHelper.b localb = (ItemTouchHelper.b)paramList.get(k);
        int m = paramCanvas.save();
        onChildDrawOver(paramCanvas, paramRecyclerView, localb.h, localb.l, localb.m, localb.i, false);
        paramCanvas.restoreToCount(m);
      }
      if (paramViewHolder != null)
      {
        k = paramCanvas.save();
        onChildDrawOver(paramCanvas, paramRecyclerView, paramViewHolder, paramFloat1, paramFloat2, paramInt, true);
        paramCanvas.restoreToCount(k);
      }
      paramInt = i - 1;
      k = j;
      while (paramInt >= 0)
      {
        paramCanvas = (ItemTouchHelper.b)paramList.get(paramInt);
        if ((paramCanvas.o) && (!paramCanvas.k)) {
          paramList.remove(paramInt);
        } else if (!paramCanvas.o) {
          k = 1;
        }
        paramInt--;
      }
      if (k != 0) {
        paramRecyclerView.invalidate();
      }
    }
    
    public abstract boolean onMove(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2);
    
    public void onMoved(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, int paramInt1, RecyclerView.ViewHolder paramViewHolder2, int paramInt2, int paramInt3, int paramInt4)
    {
      RecyclerView.LayoutManager localLayoutManager = paramRecyclerView.getLayoutManager();
      if ((localLayoutManager instanceof ItemTouchHelper.ViewDropHandler))
      {
        ((ItemTouchHelper.ViewDropHandler)localLayoutManager).prepareForDrop(paramViewHolder1.itemView, paramViewHolder2.itemView, paramInt3, paramInt4);
        return;
      }
      if (localLayoutManager.canScrollHorizontally())
      {
        if (localLayoutManager.getDecoratedLeft(paramViewHolder2.itemView) <= paramRecyclerView.getPaddingLeft()) {
          paramRecyclerView.scrollToPosition(paramInt2);
        }
        if (localLayoutManager.getDecoratedRight(paramViewHolder2.itemView) >= paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight()) {
          paramRecyclerView.scrollToPosition(paramInt2);
        }
      }
      if (localLayoutManager.canScrollVertically())
      {
        if (localLayoutManager.getDecoratedTop(paramViewHolder2.itemView) <= paramRecyclerView.getPaddingTop()) {
          paramRecyclerView.scrollToPosition(paramInt2);
        }
        if (localLayoutManager.getDecoratedBottom(paramViewHolder2.itemView) >= paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom()) {
          paramRecyclerView.scrollToPosition(paramInt2);
        }
      }
    }
    
    public void onSelectedChanged(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
      if (paramViewHolder != null) {
        sUICallback.onSelected(paramViewHolder.itemView);
      }
    }
    
    public abstract void onSwiped(RecyclerView.ViewHolder paramViewHolder, int paramInt);
  }
  
  public static abstract class SimpleCallback
    extends ItemTouchHelper.Callback
  {
    private int mDefaultDragDirs;
    private int mDefaultSwipeDirs;
    
    public SimpleCallback(int paramInt1, int paramInt2)
    {
      this.mDefaultSwipeDirs = paramInt2;
      this.mDefaultDragDirs = paramInt1;
    }
    
    public int getDragDirs(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return this.mDefaultDragDirs;
    }
    
    public int getMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return makeMovementFlags(getDragDirs(paramRecyclerView, paramViewHolder), getSwipeDirs(paramRecyclerView, paramViewHolder));
    }
    
    public int getSwipeDirs(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return this.mDefaultSwipeDirs;
    }
    
    public void setDefaultDragDirs(int paramInt)
    {
      this.mDefaultDragDirs = paramInt;
    }
    
    public void setDefaultSwipeDirs(int paramInt)
    {
      this.mDefaultSwipeDirs = paramInt;
    }
  }
  
  public static abstract interface ViewDropHandler
  {
    public abstract void prepareForDrop(View paramView1, View paramView2, int paramInt1, int paramInt2);
  }
  
  private class a
    extends GestureDetector.SimpleOnGestureListener
  {
    a() {}
    
    public boolean onDown(MotionEvent paramMotionEvent)
    {
      return true;
    }
    
    public void onLongPress(MotionEvent paramMotionEvent)
    {
      Object localObject = ItemTouchHelper.this.findChildView(paramMotionEvent);
      if (localObject != null)
      {
        localObject = ItemTouchHelper.this.mRecyclerView.getChildViewHolder((View)localObject);
        if (localObject != null)
        {
          if (!ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, (RecyclerView.ViewHolder)localObject)) {
            return;
          }
          if (paramMotionEvent.getPointerId(0) == ItemTouchHelper.this.mActivePointerId)
          {
            int i = paramMotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
            float f1 = paramMotionEvent.getX(i);
            float f2 = paramMotionEvent.getY(i);
            paramMotionEvent = ItemTouchHelper.this;
            paramMotionEvent.mInitialTouchX = f1;
            paramMotionEvent.mInitialTouchY = f2;
            paramMotionEvent.mDy = 0.0F;
            paramMotionEvent.mDx = 0.0F;
            if (paramMotionEvent.mCallback.isLongPressDragEnabled()) {
              ItemTouchHelper.this.select((RecyclerView.ViewHolder)localObject, 2);
            }
          }
        }
      }
    }
  }
  
  private static class b
    implements Animator.AnimatorListener
  {
    private final ValueAnimator a;
    private float b;
    final float d;
    final float e;
    final float f;
    final float g;
    final RecyclerView.ViewHolder h;
    final int i;
    final int j;
    public boolean k;
    float l;
    float m;
    boolean n = false;
    boolean o = false;
    
    b(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.i = paramInt2;
      this.j = paramInt1;
      this.h = paramViewHolder;
      this.d = paramFloat1;
      this.e = paramFloat2;
      this.f = paramFloat3;
      this.g = paramFloat4;
      this.a = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
      this.a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          ItemTouchHelper.b.this.a(paramAnonymousValueAnimator.getAnimatedFraction());
        }
      });
      this.a.setTarget(paramViewHolder.itemView);
      this.a.addListener(this);
      a(0.0F);
    }
    
    public void a()
    {
      this.h.setIsRecyclable(false);
      this.a.start();
    }
    
    public void a(float paramFloat)
    {
      this.b = paramFloat;
    }
    
    public void a(long paramLong)
    {
      this.a.setDuration(paramLong);
    }
    
    public void b()
    {
      this.a.cancel();
    }
    
    public void c()
    {
      float f1 = this.d;
      float f2 = this.f;
      if (f1 == f2) {
        this.l = this.h.itemView.getTranslationX();
      } else {
        this.l = (f1 + this.b * (f2 - f1));
      }
      f2 = this.e;
      f1 = this.g;
      if (f2 == f1) {
        this.m = this.h.itemView.getTranslationY();
      } else {
        this.m = (f2 + this.b * (f1 - f2));
      }
    }
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      a(1.0F);
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      if (!this.o) {
        this.h.setIsRecyclable(true);
      }
      this.o = true;
    }
    
    public void onAnimationRepeat(Animator paramAnimator) {}
    
    public void onAnimationStart(Animator paramAnimator) {}
  }
}


/* Location:              ~/android/support/v7/widget/helper/ItemTouchHelper.class
 *
 * Reversed by:           J
 */