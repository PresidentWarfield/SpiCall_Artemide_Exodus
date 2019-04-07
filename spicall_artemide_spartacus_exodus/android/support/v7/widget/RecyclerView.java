package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.e.a.a;
import android.support.v7.e.a.c;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
  extends ViewGroup
  implements NestedScrollingChild2, ScrollingView
{
  static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
  private static final boolean ALLOW_THREAD_GAP_WORK;
  private static final int[] CLIP_TO_PADDING_ATTR;
  static final boolean DEBUG = false;
  static final boolean DISPATCH_TEMP_DETACH = false;
  private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
  static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
  static final long FOREVER_NS = Long.MAX_VALUE;
  public static final int HORIZONTAL = 0;
  private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
  private static final int INVALID_POINTER = -1;
  public static final int INVALID_TYPE = -1;
  private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = { Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE };
  static final int MAX_SCROLL_DURATION = 2000;
  private static final int[] NESTED_SCROLLING_ATTRS = { 16843830 };
  public static final long NO_ID = -1L;
  public static final int NO_POSITION = -1;
  static final boolean POST_UPDATES_ON_ANIMATION;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  static final String TAG = "RecyclerView";
  public static final int TOUCH_SLOP_DEFAULT = 0;
  public static final int TOUCH_SLOP_PAGING = 1;
  static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
  static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
  private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
  static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
  private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
  private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
  static final String TRACE_PREFETCH_TAG = "RV Prefetch";
  static final String TRACE_SCROLL_TAG = "RV Scroll";
  static final boolean VERBOSE_TRACING = false;
  public static final int VERTICAL = 1;
  static final Interpolator sQuinticInterpolator = new Interpolator()
  {
    public float getInterpolation(float paramAnonymousFloat)
    {
      paramAnonymousFloat -= 1.0F;
      return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
    }
  };
  RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
  private final AccessibilityManager mAccessibilityManager;
  private OnItemTouchListener mActiveOnItemTouchListener;
  Adapter mAdapter;
  d mAdapterHelper;
  boolean mAdapterUpdateDuringMeasure;
  private EdgeEffect mBottomGlow;
  private ChildDrawingOrderCallback mChildDrawingOrderCallback;
  r mChildHelper;
  boolean mClipToPadding;
  boolean mDataSetHasChangedAfterLayout = false;
  private int mDispatchScrollCounter = 0;
  private int mEatRequestLayout = 0;
  private int mEatenAccessibilityChangeFlags;
  boolean mEnableFastScroller;
  boolean mFirstLayoutComplete;
  u mGapWorker;
  boolean mHasFixedSize;
  private boolean mIgnoreMotionEventTillDown;
  private int mInitialTouchX;
  private int mInitialTouchY;
  boolean mIsAttached;
  ItemAnimator mItemAnimator = new DefaultItemAnimator();
  private RecyclerView.ItemAnimator.a mItemAnimatorListener;
  private Runnable mItemAnimatorRunner;
  final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
  boolean mItemsAddedOrRemoved;
  boolean mItemsChanged;
  private int mLastTouchX;
  private int mLastTouchY;
  LayoutManager mLayout;
  boolean mLayoutFrozen;
  private int mLayoutOrScrollCounter = 0;
  boolean mLayoutRequestEaten;
  private EdgeEffect mLeftGlow;
  private final int mMaxFlingVelocity;
  private final int mMinFlingVelocity;
  private final int[] mMinMaxLayoutPositions;
  private final int[] mNestedOffsets;
  private final c mObserver = new c();
  private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
  private OnFlingListener mOnFlingListener;
  private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
  final List<ViewHolder> mPendingAccessibilityImportanceChange;
  private SavedState mPendingSavedState;
  boolean mPostedAnimatorRunner;
  u.a mPrefetchRegistry;
  private boolean mPreserveFocusAfterLayout;
  final Recycler mRecycler = new Recycler();
  RecyclerListener mRecyclerListener;
  private EdgeEffect mRightGlow;
  private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;
  private float mScaledVerticalScrollFactor = Float.MIN_VALUE;
  private final int[] mScrollConsumed;
  private OnScrollListener mScrollListener;
  private List<OnScrollListener> mScrollListeners;
  private final int[] mScrollOffset;
  private int mScrollPointerId = -1;
  private int mScrollState = 0;
  private NestedScrollingChildHelper mScrollingChildHelper;
  final State mState;
  final Rect mTempRect = new Rect();
  private final Rect mTempRect2 = new Rect();
  final RectF mTempRectF = new RectF();
  private EdgeEffect mTopGlow;
  private int mTouchSlop;
  final Runnable mUpdateChildViewsRunnable = new Runnable()
  {
    public void run()
    {
      if ((RecyclerView.this.mFirstLayoutComplete) && (!RecyclerView.this.isLayoutRequested()))
      {
        if (!RecyclerView.this.mIsAttached)
        {
          RecyclerView.this.requestLayout();
          return;
        }
        if (RecyclerView.this.mLayoutFrozen)
        {
          RecyclerView.this.mLayoutRequestEaten = true;
          return;
        }
        RecyclerView.this.consumePendingUpdateOperations();
        return;
      }
    }
  };
  private VelocityTracker mVelocityTracker;
  final d mViewFlinger;
  private final ai.b mViewInfoProcessCallback;
  final ai mViewInfoStore = new ai();
  
  static
  {
    CLIP_TO_PADDING_ATTR = new int[] { 16842987 };
    boolean bool;
    if ((Build.VERSION.SDK_INT != 18) && (Build.VERSION.SDK_INT != 19) && (Build.VERSION.SDK_INT != 20)) {
      bool = false;
    } else {
      bool = true;
    }
    FORCE_INVALIDATE_DISPLAY_LIST = bool;
    if (Build.VERSION.SDK_INT >= 23) {
      bool = true;
    } else {
      bool = false;
    }
    ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bool;
    if (Build.VERSION.SDK_INT >= 16) {
      bool = true;
    } else {
      bool = false;
    }
    POST_UPDATES_ON_ANIMATION = bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    ALLOW_THREAD_GAP_WORK = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    }
    FORCE_ABS_FOCUS_SEARCH_DIRECTION = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    }
    IGNORE_DETACHED_FOCUSED_CHILD = bool;
  }
  
  public RecyclerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    boolean bool1 = true;
    this.mPreserveFocusAfterLayout = true;
    this.mViewFlinger = new d();
    if (ALLOW_THREAD_GAP_WORK) {
      localObject = new u.a();
    } else {
      localObject = null;
    }
    this.mPrefetchRegistry = ((u.a)localObject);
    this.mState = new State();
    this.mItemsAddedOrRemoved = false;
    this.mItemsChanged = false;
    this.mItemAnimatorListener = new b();
    this.mPostedAnimatorRunner = false;
    this.mMinMaxLayoutPositions = new int[2];
    this.mScrollOffset = new int[2];
    this.mScrollConsumed = new int[2];
    this.mNestedOffsets = new int[2];
    this.mPendingAccessibilityImportanceChange = new ArrayList();
    this.mItemAnimatorRunner = new Runnable()
    {
      public void run()
      {
        if (RecyclerView.this.mItemAnimator != null) {
          RecyclerView.this.mItemAnimator.runPendingAnimations();
        }
        RecyclerView.this.mPostedAnimatorRunner = false;
      }
    };
    this.mViewInfoProcessCallback = new ai.b()
    {
      public void a(RecyclerView.ViewHolder paramAnonymousViewHolder)
      {
        RecyclerView.this.mLayout.removeAndRecycleView(paramAnonymousViewHolder.itemView, RecyclerView.this.mRecycler);
      }
      
      public void a(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        RecyclerView.this.mRecycler.unscrapView(paramAnonymousViewHolder);
        RecyclerView.this.animateDisappearance(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2);
      }
      
      public void b(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        RecyclerView.this.animateAppearance(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2);
      }
      
      public void c(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        paramAnonymousViewHolder.setIsRecyclable(false);
        if (RecyclerView.this.mDataSetHasChangedAfterLayout)
        {
          if (RecyclerView.this.mItemAnimator.animateChange(paramAnonymousViewHolder, paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2)) {
            RecyclerView.this.postAnimationRunner();
          }
        }
        else if (RecyclerView.this.mItemAnimator.animatePersistence(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2)) {
          RecyclerView.this.postAnimationRunner();
        }
      }
    };
    if (paramAttributeSet != null)
    {
      localObject = paramContext.obtainStyledAttributes(paramAttributeSet, CLIP_TO_PADDING_ATTR, paramInt, 0);
      this.mClipToPadding = ((TypedArray)localObject).getBoolean(0, true);
      ((TypedArray)localObject).recycle();
    }
    else
    {
      this.mClipToPadding = true;
    }
    setScrollContainer(true);
    setFocusableInTouchMode(true);
    Object localObject = ViewConfiguration.get(paramContext);
    this.mTouchSlop = ((ViewConfiguration)localObject).getScaledTouchSlop();
    this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor((ViewConfiguration)localObject, paramContext);
    this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor((ViewConfiguration)localObject, paramContext);
    this.mMinFlingVelocity = ((ViewConfiguration)localObject).getScaledMinimumFlingVelocity();
    this.mMaxFlingVelocity = ((ViewConfiguration)localObject).getScaledMaximumFlingVelocity();
    boolean bool2;
    if (getOverScrollMode() == 2) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    setWillNotDraw(bool2);
    this.mItemAnimator.setListener(this.mItemAnimatorListener);
    initAdapterManager();
    initChildrenHelper();
    if (ViewCompat.getImportantForAccessibility(this) == 0) {
      ViewCompat.setImportantForAccessibility(this, 1);
    }
    this.mAccessibilityManager = ((AccessibilityManager)getContext().getSystemService("accessibility"));
    setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, a.c.RecyclerView, paramInt, 0);
      localObject = localTypedArray.getString(a.c.RecyclerView_layoutManager);
      if (localTypedArray.getInt(a.c.RecyclerView_android_descendantFocusability, -1) == -1) {
        setDescendantFocusability(262144);
      }
      this.mEnableFastScroller = localTypedArray.getBoolean(a.c.RecyclerView_fastScrollEnabled, false);
      if (this.mEnableFastScroller) {
        initFastScroller((StateListDrawable)localTypedArray.getDrawable(a.c.RecyclerView_fastScrollVerticalThumbDrawable), localTypedArray.getDrawable(a.c.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)localTypedArray.getDrawable(a.c.RecyclerView_fastScrollHorizontalThumbDrawable), localTypedArray.getDrawable(a.c.RecyclerView_fastScrollHorizontalTrackDrawable));
      }
      localTypedArray.recycle();
      createLayoutManager(paramContext, (String)localObject, paramAttributeSet, paramInt, 0);
      bool2 = bool1;
      if (Build.VERSION.SDK_INT >= 21)
      {
        paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, NESTED_SCROLLING_ATTRS, paramInt, 0);
        bool2 = paramContext.getBoolean(0, true);
        paramContext.recycle();
      }
    }
    else
    {
      setDescendantFocusability(262144);
      bool2 = bool1;
    }
    setNestedScrollingEnabled(bool2);
  }
  
  private void addAnimatingView(ViewHolder paramViewHolder)
  {
    View localView = paramViewHolder.itemView;
    int i;
    if (localView.getParent() == this) {
      i = 1;
    } else {
      i = 0;
    }
    this.mRecycler.unscrapView(getChildViewHolder(localView));
    if (paramViewHolder.isTmpDetached()) {
      this.mChildHelper.a(localView, -1, localView.getLayoutParams(), true);
    } else if (i == 0) {
      this.mChildHelper.a(localView, true);
    } else {
      this.mChildHelper.d(localView);
    }
  }
  
  private void animateChange(ViewHolder paramViewHolder1, ViewHolder paramViewHolder2, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramViewHolder1.setIsRecyclable(false);
    if (paramBoolean1) {
      addAnimatingView(paramViewHolder1);
    }
    if (paramViewHolder1 != paramViewHolder2)
    {
      if (paramBoolean2) {
        addAnimatingView(paramViewHolder2);
      }
      paramViewHolder1.mShadowedHolder = paramViewHolder2;
      addAnimatingView(paramViewHolder1);
      this.mRecycler.unscrapView(paramViewHolder1);
      paramViewHolder2.setIsRecyclable(false);
      paramViewHolder2.mShadowingHolder = paramViewHolder1;
    }
    if (this.mItemAnimator.animateChange(paramViewHolder1, paramViewHolder2, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  private void cancelTouch()
  {
    resetTouch();
    setScrollState(0);
  }
  
  static void clearNestedRecyclerViewIfNotNested(ViewHolder paramViewHolder)
  {
    if (paramViewHolder.mNestedRecyclerView != null)
    {
      Object localObject = (View)paramViewHolder.mNestedRecyclerView.get();
      while (localObject != null)
      {
        if (localObject == paramViewHolder.itemView) {
          return;
        }
        localObject = ((View)localObject).getParent();
        if ((localObject instanceof View)) {
          localObject = (View)localObject;
        } else {
          localObject = null;
        }
      }
      paramViewHolder.mNestedRecyclerView = null;
    }
  }
  
  private void createLayoutManager(Context paramContext, String paramString, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    if (paramString != null)
    {
      paramString = paramString.trim();
      if (!paramString.isEmpty())
      {
        String str = getFullClassName(paramContext, paramString);
        try
        {
          if (isInEditMode()) {
            paramString = getClass().getClassLoader();
          } else {
            paramString = paramContext.getClassLoader();
          }
          Class localClass = paramString.loadClass(str).asSubclass(LayoutManager.class);
          paramString = null;
          try
          {
            Constructor localConstructor = localClass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
            paramString = new Object[] { paramContext, paramAttributeSet, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) };
            paramContext = localConstructor;
          }
          catch (NoSuchMethodException localNoSuchMethodException) {}
          try
          {
            paramContext = localClass.getConstructor(new Class[0]);
            paramContext.setAccessible(true);
            setLayoutManager((LayoutManager)paramContext.newInstance(paramString));
          }
          catch (NoSuchMethodException paramContext)
          {
            paramContext.initCause(localNoSuchMethodException);
            paramString = new java/lang/IllegalStateException;
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append(paramAttributeSet.getPositionDescription());
            localStringBuilder.append(": Error creating LayoutManager ");
            localStringBuilder.append(str);
            paramString.<init>(localStringBuilder.toString(), paramContext);
            throw paramString;
          }
          return;
        }
        catch (ClassCastException paramString)
        {
          paramContext = new StringBuilder();
          paramContext.append(paramAttributeSet.getPositionDescription());
          paramContext.append(": Class is not a LayoutManager ");
          paramContext.append(str);
          throw new IllegalStateException(paramContext.toString(), paramString);
        }
        catch (IllegalAccessException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Cannot access non-public constructor ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (InstantiationException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Could not instantiate the LayoutManager: ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (InvocationTargetException paramString)
        {
          paramContext = new StringBuilder();
          paramContext.append(paramAttributeSet.getPositionDescription());
          paramContext.append(": Could not instantiate the LayoutManager: ");
          paramContext.append(str);
          throw new IllegalStateException(paramContext.toString(), paramString);
        }
        catch (ClassNotFoundException paramString)
        {
          paramContext = new StringBuilder();
          paramContext.append(paramAttributeSet.getPositionDescription());
          paramContext.append(": Unable to find LayoutManager ");
          paramContext.append(str);
          throw new IllegalStateException(paramContext.toString(), paramString);
        }
      }
    }
  }
  
  private boolean didChildRangeChange(int paramInt1, int paramInt2)
  {
    findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
    int[] arrayOfInt = this.mMinMaxLayoutPositions;
    boolean bool = false;
    if ((arrayOfInt[0] != paramInt1) || (arrayOfInt[1] != paramInt2)) {
      bool = true;
    }
    return bool;
  }
  
  private void dispatchContentChangedIfNecessary()
  {
    int i = this.mEatenAccessibilityChangeFlags;
    this.mEatenAccessibilityChangeFlags = 0;
    if ((i != 0) && (isAccessibilityEnabled()))
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain();
      localAccessibilityEvent.setEventType(2048);
      AccessibilityEventCompat.setContentChangeTypes(localAccessibilityEvent, i);
      sendAccessibilityEventUnchecked(localAccessibilityEvent);
    }
  }
  
  private void dispatchLayoutStep1()
  {
    Object localObject = this.mState;
    boolean bool = true;
    ((State)localObject).assertLayoutStep(1);
    fillRemainingScrollValues(this.mState);
    this.mState.mIsMeasuring = false;
    eatRequestLayout();
    this.mViewInfoStore.a();
    onEnterLayoutOrScroll();
    processAdapterUpdatesAndSetAnimationFlags();
    saveFocusInfo();
    localObject = this.mState;
    if ((!((State)localObject).mRunSimpleAnimations) || (!this.mItemsChanged)) {
      bool = false;
    }
    ((State)localObject).mTrackOldChangeHolders = bool;
    this.mItemsChanged = false;
    this.mItemsAddedOrRemoved = false;
    localObject = this.mState;
    ((State)localObject).mInPreLayout = ((State)localObject).mRunPredictiveAnimations;
    this.mState.mItemCount = this.mAdapter.getItemCount();
    findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
    int i;
    int j;
    ViewHolder localViewHolder;
    if (this.mState.mRunSimpleAnimations)
    {
      i = this.mChildHelper.b();
      for (j = 0; j < i; j++)
      {
        localViewHolder = getChildViewHolderInt(this.mChildHelper.b(j));
        if ((!localViewHolder.shouldIgnore()) && ((!localViewHolder.isInvalid()) || (this.mAdapter.hasStableIds())))
        {
          localObject = this.mItemAnimator.recordPreLayoutInformation(this.mState, localViewHolder, ItemAnimator.buildAdapterChangeFlagsForAnimations(localViewHolder), localViewHolder.getUnmodifiedPayloads());
          this.mViewInfoStore.a(localViewHolder, (RecyclerView.ItemAnimator.ItemHolderInfo)localObject);
          if ((this.mState.mTrackOldChangeHolders) && (localViewHolder.isUpdated()) && (!localViewHolder.isRemoved()) && (!localViewHolder.shouldIgnore()) && (!localViewHolder.isInvalid()))
          {
            long l = getChangedHolderKey(localViewHolder);
            this.mViewInfoStore.a(l, localViewHolder);
          }
        }
      }
    }
    if (this.mState.mRunPredictiveAnimations)
    {
      saveOldPositions();
      bool = this.mState.mStructureChanged;
      localObject = this.mState;
      ((State)localObject).mStructureChanged = false;
      this.mLayout.onLayoutChildren(this.mRecycler, (State)localObject);
      this.mState.mStructureChanged = bool;
      for (j = 0; j < this.mChildHelper.b(); j++)
      {
        localViewHolder = getChildViewHolderInt(this.mChildHelper.b(j));
        if ((!localViewHolder.shouldIgnore()) && (!this.mViewInfoStore.d(localViewHolder)))
        {
          int k = ItemAnimator.buildAdapterChangeFlagsForAnimations(localViewHolder);
          bool = localViewHolder.hasAnyOfTheFlags(8192);
          i = k;
          if (!bool) {
            i = k | 0x1000;
          }
          localObject = this.mItemAnimator.recordPreLayoutInformation(this.mState, localViewHolder, i, localViewHolder.getUnmodifiedPayloads());
          if (bool) {
            recordAnimationInfoIfBouncedHiddenView(localViewHolder, (RecyclerView.ItemAnimator.ItemHolderInfo)localObject);
          } else {
            this.mViewInfoStore.b(localViewHolder, (RecyclerView.ItemAnimator.ItemHolderInfo)localObject);
          }
        }
      }
      clearOldPositions();
    }
    else
    {
      clearOldPositions();
    }
    onExitLayoutOrScroll();
    resumeRequestLayout(false);
    this.mState.mLayoutStep = 2;
  }
  
  private void dispatchLayoutStep2()
  {
    eatRequestLayout();
    onEnterLayoutOrScroll();
    this.mState.assertLayoutStep(6);
    this.mAdapterHelper.e();
    this.mState.mItemCount = this.mAdapter.getItemCount();
    State localState = this.mState;
    localState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
    localState.mInPreLayout = false;
    this.mLayout.onLayoutChildren(this.mRecycler, localState);
    localState = this.mState;
    localState.mStructureChanged = false;
    this.mPendingSavedState = null;
    boolean bool;
    if ((localState.mRunSimpleAnimations) && (this.mItemAnimator != null)) {
      bool = true;
    } else {
      bool = false;
    }
    localState.mRunSimpleAnimations = bool;
    this.mState.mLayoutStep = 4;
    onExitLayoutOrScroll();
    resumeRequestLayout(false);
  }
  
  private void dispatchLayoutStep3()
  {
    this.mState.assertLayoutStep(4);
    eatRequestLayout();
    onEnterLayoutOrScroll();
    Object localObject = this.mState;
    ((State)localObject).mLayoutStep = 1;
    if (((State)localObject).mRunSimpleAnimations)
    {
      for (int i = this.mChildHelper.b() - 1; i >= 0; i--)
      {
        ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.b(i));
        if (!localViewHolder.shouldIgnore())
        {
          long l = getChangedHolderKey(localViewHolder);
          RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo1 = this.mItemAnimator.recordPostLayoutInformation(this.mState, localViewHolder);
          localObject = this.mViewInfoStore.a(l);
          if ((localObject != null) && (!((ViewHolder)localObject).shouldIgnore()))
          {
            boolean bool1 = this.mViewInfoStore.a((ViewHolder)localObject);
            boolean bool2 = this.mViewInfoStore.a(localViewHolder);
            if ((bool1) && (localObject == localViewHolder))
            {
              this.mViewInfoStore.c(localViewHolder, localItemHolderInfo1);
            }
            else
            {
              RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo2 = this.mViewInfoStore.b((ViewHolder)localObject);
              this.mViewInfoStore.c(localViewHolder, localItemHolderInfo1);
              localItemHolderInfo1 = this.mViewInfoStore.c(localViewHolder);
              if (localItemHolderInfo2 == null) {
                handleMissingPreInfoForChangeError(l, localViewHolder, (ViewHolder)localObject);
              } else {
                animateChange((ViewHolder)localObject, localViewHolder, localItemHolderInfo2, localItemHolderInfo1, bool1, bool2);
              }
            }
          }
          else
          {
            this.mViewInfoStore.c(localViewHolder, localItemHolderInfo1);
          }
        }
      }
      this.mViewInfoStore.a(this.mViewInfoProcessCallback);
    }
    this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
    localObject = this.mState;
    ((State)localObject).mPreviousLayoutItemCount = ((State)localObject).mItemCount;
    this.mDataSetHasChangedAfterLayout = false;
    localObject = this.mState;
    ((State)localObject).mRunSimpleAnimations = false;
    ((State)localObject).mRunPredictiveAnimations = false;
    this.mLayout.mRequestedSimpleAnimations = false;
    if (this.mRecycler.mChangedScrap != null) {
      this.mRecycler.mChangedScrap.clear();
    }
    if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch)
    {
      localObject = this.mLayout;
      ((LayoutManager)localObject).mPrefetchMaxCountObserved = 0;
      ((LayoutManager)localObject).mPrefetchMaxObservedInInitialPrefetch = false;
      this.mRecycler.updateViewCacheSize();
    }
    this.mLayout.onLayoutCompleted(this.mState);
    onExitLayoutOrScroll();
    resumeRequestLayout(false);
    this.mViewInfoStore.a();
    localObject = this.mMinMaxLayoutPositions;
    if (didChildRangeChange(localObject[0], localObject[1])) {
      dispatchOnScrolled(0, 0);
    }
    recoverFocusFromState();
    resetFocusInfo();
  }
  
  private boolean dispatchOnItemTouch(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    OnItemTouchListener localOnItemTouchListener = this.mActiveOnItemTouchListener;
    if (localOnItemTouchListener != null) {
      if (i == 0)
      {
        this.mActiveOnItemTouchListener = null;
      }
      else
      {
        localOnItemTouchListener.onTouchEvent(this, paramMotionEvent);
        if ((i == 3) || (i == 1)) {
          this.mActiveOnItemTouchListener = null;
        }
        return true;
      }
    }
    if (i != 0)
    {
      int j = this.mOnItemTouchListeners.size();
      for (i = 0; i < j; i++)
      {
        localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(i);
        if (localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent))
        {
          this.mActiveOnItemTouchListener = localOnItemTouchListener;
          return true;
        }
      }
    }
    return false;
  }
  
  private boolean dispatchOnItemTouchIntercept(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((i == 3) || (i == 0)) {
      this.mActiveOnItemTouchListener = null;
    }
    int j = this.mOnItemTouchListeners.size();
    for (int k = 0; k < j; k++)
    {
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(k);
      if ((localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent)) && (i != 3))
      {
        this.mActiveOnItemTouchListener = localOnItemTouchListener;
        return true;
      }
    }
    return false;
  }
  
  private void findMinMaxChildLayoutPositions(int[] paramArrayOfInt)
  {
    int i = this.mChildHelper.b();
    if (i == 0)
    {
      paramArrayOfInt[0] = -1;
      paramArrayOfInt[1] = -1;
      return;
    }
    int j = 0;
    int k = Integer.MAX_VALUE;
    int n;
    for (int m = Integer.MIN_VALUE; j < i; m = n)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.b(j));
      if (localViewHolder.shouldIgnore())
      {
        n = m;
      }
      else
      {
        int i1 = localViewHolder.getLayoutPosition();
        int i2 = k;
        if (i1 < k) {
          i2 = i1;
        }
        k = i2;
        n = m;
        if (i1 > m)
        {
          n = i1;
          k = i2;
        }
      }
      j++;
    }
    paramArrayOfInt[0] = k;
    paramArrayOfInt[1] = m;
  }
  
  static RecyclerView findNestedRecyclerView(View paramView)
  {
    if (!(paramView instanceof ViewGroup)) {
      return null;
    }
    if ((paramView instanceof RecyclerView)) {
      return (RecyclerView)paramView;
    }
    paramView = (ViewGroup)paramView;
    int i = paramView.getChildCount();
    for (int j = 0; j < i; j++)
    {
      RecyclerView localRecyclerView = findNestedRecyclerView(paramView.getChildAt(j));
      if (localRecyclerView != null) {
        return localRecyclerView;
      }
    }
    return null;
  }
  
  private View findNextViewToFocus()
  {
    if (this.mState.mFocusedItemPosition != -1) {
      i = this.mState.mFocusedItemPosition;
    } else {
      i = 0;
    }
    int j = this.mState.getItemCount();
    ViewHolder localViewHolder;
    for (int k = i; k < j; k++)
    {
      localViewHolder = findViewHolderForAdapterPosition(k);
      if (localViewHolder == null) {
        break;
      }
      if (localViewHolder.itemView.hasFocusable()) {
        return localViewHolder.itemView;
      }
    }
    for (int i = Math.min(j, i) - 1; i >= 0; i--)
    {
      localViewHolder = findViewHolderForAdapterPosition(i);
      if (localViewHolder == null) {
        return null;
      }
      if (localViewHolder.itemView.hasFocusable()) {
        return localViewHolder.itemView;
      }
    }
    return null;
  }
  
  static ViewHolder getChildViewHolderInt(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    return ((LayoutParams)paramView.getLayoutParams()).mViewHolder;
  }
  
  static void getDecoratedBoundsWithMarginsInt(View paramView, Rect paramRect)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect localRect = localLayoutParams.mDecorInsets;
    paramRect.set(paramView.getLeft() - localRect.left - localLayoutParams.leftMargin, paramView.getTop() - localRect.top - localLayoutParams.topMargin, paramView.getRight() + localRect.right + localLayoutParams.rightMargin, paramView.getBottom() + localRect.bottom + localLayoutParams.bottomMargin);
  }
  
  private int getDeepestFocusedViewWithId(View paramView)
  {
    int i = paramView.getId();
    while ((!paramView.isFocused()) && ((paramView instanceof ViewGroup)) && (paramView.hasFocus()))
    {
      View localView = ((ViewGroup)paramView).getFocusedChild();
      paramView = localView;
      if (localView.getId() != -1)
      {
        i = localView.getId();
        paramView = localView;
      }
    }
    return i;
  }
  
  private String getFullClassName(Context paramContext, String paramString)
  {
    if (paramString.charAt(0) == '.')
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    if (paramString.contains(".")) {
      return paramString;
    }
    paramContext = new StringBuilder();
    paramContext.append(RecyclerView.class.getPackage().getName());
    paramContext.append('.');
    paramContext.append(paramString);
    return paramContext.toString();
  }
  
  private NestedScrollingChildHelper getScrollingChildHelper()
  {
    if (this.mScrollingChildHelper == null) {
      this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
    }
    return this.mScrollingChildHelper;
  }
  
  private void handleMissingPreInfoForChangeError(long paramLong, ViewHolder paramViewHolder1, ViewHolder paramViewHolder2)
  {
    int i = this.mChildHelper.b();
    for (int j = 0; j < i; j++)
    {
      localObject = getChildViewHolderInt(this.mChildHelper.b(j));
      if ((localObject != paramViewHolder1) && (getChangedHolderKey((ViewHolder)localObject) == paramLong))
      {
        paramViewHolder2 = this.mAdapter;
        if ((paramViewHolder2 != null) && (paramViewHolder2.hasStableIds()))
        {
          paramViewHolder2 = new StringBuilder();
          paramViewHolder2.append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
          paramViewHolder2.append(localObject);
          paramViewHolder2.append(" \n View Holder 2:");
          paramViewHolder2.append(paramViewHolder1);
          paramViewHolder2.append(exceptionLabel());
          throw new IllegalStateException(paramViewHolder2.toString());
        }
        paramViewHolder2 = new StringBuilder();
        paramViewHolder2.append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
        paramViewHolder2.append(localObject);
        paramViewHolder2.append(" \n View Holder 2:");
        paramViewHolder2.append(paramViewHolder1);
        paramViewHolder2.append(exceptionLabel());
        throw new IllegalStateException(paramViewHolder2.toString());
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
    ((StringBuilder)localObject).append(paramViewHolder2);
    ((StringBuilder)localObject).append(" cannot be found but it is necessary for ");
    ((StringBuilder)localObject).append(paramViewHolder1);
    ((StringBuilder)localObject).append(exceptionLabel());
    Log.e("RecyclerView", ((StringBuilder)localObject).toString());
  }
  
  private boolean hasUpdatedView()
  {
    int i = this.mChildHelper.b();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.b(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (localViewHolder.isUpdated())) {
        return true;
      }
    }
    return false;
  }
  
  private void initChildrenHelper()
  {
    this.mChildHelper = new r(new r.b()
    {
      public int a()
      {
        return RecyclerView.this.getChildCount();
      }
      
      public int a(View paramAnonymousView)
      {
        return RecyclerView.this.indexOfChild(paramAnonymousView);
      }
      
      public void a(int paramAnonymousInt)
      {
        View localView = RecyclerView.this.getChildAt(paramAnonymousInt);
        if (localView != null)
        {
          RecyclerView.this.dispatchChildDetached(localView);
          localView.clearAnimation();
        }
        RecyclerView.this.removeViewAt(paramAnonymousInt);
      }
      
      public void a(View paramAnonymousView, int paramAnonymousInt)
      {
        RecyclerView.this.addView(paramAnonymousView, paramAnonymousInt);
        RecyclerView.this.dispatchChildAttached(paramAnonymousView);
      }
      
      public void a(View paramAnonymousView, int paramAnonymousInt, ViewGroup.LayoutParams paramAnonymousLayoutParams)
      {
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (localViewHolder != null)
        {
          if ((!localViewHolder.isTmpDetached()) && (!localViewHolder.shouldIgnore()))
          {
            paramAnonymousView = new StringBuilder();
            paramAnonymousView.append("Called attach on a child which is not detached: ");
            paramAnonymousView.append(localViewHolder);
            paramAnonymousView.append(RecyclerView.this.exceptionLabel());
            throw new IllegalArgumentException(paramAnonymousView.toString());
          }
          localViewHolder.clearTmpDetachFlag();
        }
        RecyclerView.this.attachViewToParent(paramAnonymousView, paramAnonymousInt, paramAnonymousLayoutParams);
      }
      
      public RecyclerView.ViewHolder b(View paramAnonymousView)
      {
        return RecyclerView.getChildViewHolderInt(paramAnonymousView);
      }
      
      public View b(int paramAnonymousInt)
      {
        return RecyclerView.this.getChildAt(paramAnonymousInt);
      }
      
      public void b()
      {
        int i = a();
        for (int j = 0; j < i; j++)
        {
          View localView = b(j);
          RecyclerView.this.dispatchChildDetached(localView);
          localView.clearAnimation();
        }
        RecyclerView.this.removeAllViews();
      }
      
      public void c(int paramAnonymousInt)
      {
        Object localObject = b(paramAnonymousInt);
        if (localObject != null)
        {
          localObject = RecyclerView.getChildViewHolderInt((View)localObject);
          if (localObject != null)
          {
            if ((((RecyclerView.ViewHolder)localObject).isTmpDetached()) && (!((RecyclerView.ViewHolder)localObject).shouldIgnore()))
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("called detach on an already detached child ");
              localStringBuilder.append(localObject);
              localStringBuilder.append(RecyclerView.this.exceptionLabel());
              throw new IllegalArgumentException(localStringBuilder.toString());
            }
            ((RecyclerView.ViewHolder)localObject).addFlags(256);
          }
        }
        RecyclerView.this.detachViewFromParent(paramAnonymousInt);
      }
      
      public void c(View paramAnonymousView)
      {
        paramAnonymousView = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (paramAnonymousView != null) {
          RecyclerView.ViewHolder.access$200(paramAnonymousView, RecyclerView.this);
        }
      }
      
      public void d(View paramAnonymousView)
      {
        paramAnonymousView = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (paramAnonymousView != null) {
          RecyclerView.ViewHolder.access$300(paramAnonymousView, RecyclerView.this);
        }
      }
    });
  }
  
  private boolean isPreferredNextFocus(View paramView1, View paramView2, int paramInt)
  {
    int i = 0;
    if ((paramView2 != null) && (paramView2 != this))
    {
      if (paramView1 == null) {
        return true;
      }
      if ((paramInt != 2) && (paramInt != 1)) {
        return isPreferredNextFocusAbsolute(paramView1, paramView2, paramInt);
      }
      int j;
      if (this.mLayout.getLayoutDirection() == 1) {
        j = 1;
      } else {
        j = 0;
      }
      if (paramInt == 2) {
        i = 1;
      }
      if ((i ^ j) != 0) {
        j = 66;
      } else {
        j = 17;
      }
      if (isPreferredNextFocusAbsolute(paramView1, paramView2, j)) {
        return true;
      }
      if (paramInt == 2) {
        return isPreferredNextFocusAbsolute(paramView1, paramView2, 130);
      }
      return isPreferredNextFocusAbsolute(paramView1, paramView2, 33);
    }
    return false;
  }
  
  private boolean isPreferredNextFocusAbsolute(View paramView1, View paramView2, int paramInt)
  {
    this.mTempRect.set(0, 0, paramView1.getWidth(), paramView1.getHeight());
    this.mTempRect2.set(0, 0, paramView2.getWidth(), paramView2.getHeight());
    offsetDescendantRectToMyCoords(paramView1, this.mTempRect);
    offsetDescendantRectToMyCoords(paramView2, this.mTempRect2);
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool4 = true;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130)
          {
            if (((this.mTempRect.top >= this.mTempRect2.top) && (this.mTempRect.bottom > this.mTempRect2.top)) || (this.mTempRect.bottom >= this.mTempRect2.bottom)) {
              bool4 = false;
            }
            return bool4;
          }
          paramView1 = new StringBuilder();
          paramView1.append("direction must be absolute. received:");
          paramView1.append(paramInt);
          paramView1.append(exceptionLabel());
          throw new IllegalArgumentException(paramView1.toString());
        }
        if (((this.mTempRect.left < this.mTempRect2.left) || (this.mTempRect.right <= this.mTempRect2.left)) && (this.mTempRect.right < this.mTempRect2.right)) {
          bool4 = bool1;
        } else {
          bool4 = false;
        }
        return bool4;
      }
      if (((this.mTempRect.bottom > this.mTempRect2.bottom) || (this.mTempRect.top >= this.mTempRect2.bottom)) && (this.mTempRect.top > this.mTempRect2.top)) {
        bool4 = bool2;
      } else {
        bool4 = false;
      }
      return bool4;
    }
    if (((this.mTempRect.right > this.mTempRect2.right) || (this.mTempRect.left >= this.mTempRect2.right)) && (this.mTempRect.left > this.mTempRect2.left)) {
      bool4 = bool3;
    } else {
      bool4 = false;
    }
    return bool4;
  }
  
  private void onPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mScrollPointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.mScrollPointerId = paramMotionEvent.getPointerId(i);
      int j = (int)(paramMotionEvent.getX(i) + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
    }
  }
  
  private boolean predictiveItemAnimationsEnabled()
  {
    boolean bool;
    if ((this.mItemAnimator != null) && (this.mLayout.supportsPredictiveItemAnimations())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void processAdapterUpdatesAndSetAnimationFlags()
  {
    if (this.mDataSetHasChangedAfterLayout)
    {
      this.mAdapterHelper.a();
      this.mLayout.onItemsChanged(this);
    }
    if (predictiveItemAnimationsEnabled()) {
      this.mAdapterHelper.b();
    } else {
      this.mAdapterHelper.e();
    }
    boolean bool1 = this.mItemsAddedOrRemoved;
    boolean bool2 = false;
    int i;
    if ((!bool1) && (!this.mItemsChanged)) {
      i = 0;
    } else {
      i = 1;
    }
    State localState = this.mState;
    if ((this.mFirstLayoutComplete) && (this.mItemAnimator != null) && ((this.mDataSetHasChangedAfterLayout) || (i != 0) || (this.mLayout.mRequestedSimpleAnimations)) && ((!this.mDataSetHasChangedAfterLayout) || (this.mAdapter.hasStableIds()))) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localState.mRunSimpleAnimations = bool1;
    localState = this.mState;
    bool1 = bool2;
    if (localState.mRunSimpleAnimations)
    {
      bool1 = bool2;
      if (i != 0)
      {
        bool1 = bool2;
        if (!this.mDataSetHasChangedAfterLayout)
        {
          bool1 = bool2;
          if (predictiveItemAnimationsEnabled()) {
            bool1 = true;
          }
        }
      }
    }
    localState.mRunPredictiveAnimations = bool1;
  }
  
  private void pullGlows(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i = 1;
    int j;
    if (paramFloat2 < 0.0F)
    {
      ensureLeftGlow();
      EdgeEffectCompat.onPull(this.mLeftGlow, -paramFloat2 / getWidth(), 1.0F - paramFloat3 / getHeight());
      j = 1;
    }
    else if (paramFloat2 > 0.0F)
    {
      ensureRightGlow();
      EdgeEffectCompat.onPull(this.mRightGlow, paramFloat2 / getWidth(), paramFloat3 / getHeight());
      j = 1;
    }
    else
    {
      j = 0;
    }
    if (paramFloat4 < 0.0F)
    {
      ensureTopGlow();
      EdgeEffectCompat.onPull(this.mTopGlow, -paramFloat4 / getHeight(), paramFloat1 / getWidth());
      j = i;
    }
    else if (paramFloat4 > 0.0F)
    {
      ensureBottomGlow();
      EdgeEffectCompat.onPull(this.mBottomGlow, paramFloat4 / getHeight(), 1.0F - paramFloat1 / getWidth());
      j = i;
    }
    if ((j != 0) || (paramFloat2 != 0.0F) || (paramFloat4 != 0.0F)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private void recoverFocusFromState()
  {
    if ((this.mPreserveFocusAfterLayout) && (this.mAdapter != null) && (hasFocus()) && (getDescendantFocusability() != 393216) && ((getDescendantFocusability() != 131072) || (!isFocused())))
    {
      Object localObject;
      if (!isFocused())
      {
        localObject = getFocusedChild();
        if ((IGNORE_DETACHED_FOCUSED_CHILD) && ((((View)localObject).getParent() == null) || (!((View)localObject).hasFocus())))
        {
          if (this.mChildHelper.b() == 0) {
            requestFocus();
          }
        }
        else if (!this.mChildHelper.c((View)localObject)) {
          return;
        }
      }
      long l = this.mState.mFocusedItemId;
      View localView = null;
      if ((l != -1L) && (this.mAdapter.hasStableIds())) {
        localObject = findViewHolderForItemId(this.mState.mFocusedItemId);
      } else {
        localObject = null;
      }
      if ((localObject != null) && (!this.mChildHelper.c(((ViewHolder)localObject).itemView)) && (((ViewHolder)localObject).itemView.hasFocusable()))
      {
        localObject = ((ViewHolder)localObject).itemView;
      }
      else
      {
        localObject = localView;
        if (this.mChildHelper.b() > 0) {
          localObject = findNextViewToFocus();
        }
      }
      if (localObject != null)
      {
        if (this.mState.mFocusedSubChildId != -1L)
        {
          localView = ((View)localObject).findViewById(this.mState.mFocusedSubChildId);
          if ((localView != null) && (localView.isFocusable())) {
            localObject = localView;
          }
        }
        ((View)localObject).requestFocus();
      }
      return;
    }
  }
  
  private void releaseGlows()
  {
    EdgeEffect localEdgeEffect = this.mLeftGlow;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool1 = this.mLeftGlow.isFinished();
    }
    else
    {
      bool1 = false;
    }
    localEdgeEffect = this.mTopGlow;
    boolean bool2 = bool1;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool2 = bool1 | this.mTopGlow.isFinished();
    }
    localEdgeEffect = this.mRightGlow;
    boolean bool1 = bool2;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool1 = bool2 | this.mRightGlow.isFinished();
    }
    localEdgeEffect = this.mBottomGlow;
    bool2 = bool1;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool2 = bool1 | this.mBottomGlow.isFinished();
    }
    if (bool2) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private void requestChildOnScreen(View paramView1, View paramView2)
  {
    if (paramView2 != null) {
      localObject = paramView2;
    } else {
      localObject = paramView1;
    }
    this.mTempRect.set(0, 0, ((View)localObject).getWidth(), ((View)localObject).getHeight());
    Object localObject = ((View)localObject).getLayoutParams();
    if ((localObject instanceof LayoutParams))
    {
      localObject = (LayoutParams)localObject;
      if (!((LayoutParams)localObject).mInsetsDirty)
      {
        localObject = ((LayoutParams)localObject).mDecorInsets;
        localRect = this.mTempRect;
        localRect.left -= ((Rect)localObject).left;
        localRect = this.mTempRect;
        localRect.right += ((Rect)localObject).right;
        localRect = this.mTempRect;
        localRect.top -= ((Rect)localObject).top;
        localRect = this.mTempRect;
        localRect.bottom += ((Rect)localObject).bottom;
      }
    }
    if (paramView2 != null)
    {
      offsetDescendantRectToMyCoords(paramView2, this.mTempRect);
      offsetRectIntoDescendantCoords(paramView1, this.mTempRect);
    }
    localObject = this.mLayout;
    Rect localRect = this.mTempRect;
    boolean bool1 = this.mFirstLayoutComplete;
    boolean bool2;
    if (paramView2 == null) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    ((LayoutManager)localObject).requestChildRectangleOnScreen(this, paramView1, localRect, bool1 ^ true, bool2);
  }
  
  private void resetFocusInfo()
  {
    State localState = this.mState;
    localState.mFocusedItemId = -1L;
    localState.mFocusedItemPosition = -1;
    localState.mFocusedSubChildId = -1;
  }
  
  private void resetTouch()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null) {
      localVelocityTracker.clear();
    }
    stopNestedScroll(0);
    releaseGlows();
  }
  
  private void saveFocusInfo()
  {
    boolean bool = this.mPreserveFocusAfterLayout;
    State localState = null;
    Object localObject;
    if ((bool) && (hasFocus()) && (this.mAdapter != null)) {
      localObject = getFocusedChild();
    } else {
      localObject = null;
    }
    if (localObject == null) {
      localObject = localState;
    } else {
      localObject = findContainingViewHolder((View)localObject);
    }
    if (localObject == null)
    {
      resetFocusInfo();
    }
    else
    {
      localState = this.mState;
      long l;
      if (this.mAdapter.hasStableIds()) {
        l = ((ViewHolder)localObject).getItemId();
      } else {
        l = -1L;
      }
      localState.mFocusedItemId = l;
      localState = this.mState;
      int i;
      if (this.mDataSetHasChangedAfterLayout) {
        i = -1;
      } else if (((ViewHolder)localObject).isRemoved()) {
        i = ((ViewHolder)localObject).mOldPosition;
      } else {
        i = ((ViewHolder)localObject).getAdapterPosition();
      }
      localState.mFocusedItemPosition = i;
      this.mState.mFocusedSubChildId = getDeepestFocusedViewWithId(((ViewHolder)localObject).itemView);
    }
  }
  
  private void setAdapterInternal(Adapter paramAdapter, boolean paramBoolean1, boolean paramBoolean2)
  {
    Adapter localAdapter = this.mAdapter;
    if (localAdapter != null)
    {
      localAdapter.unregisterAdapterDataObserver(this.mObserver);
      this.mAdapter.onDetachedFromRecyclerView(this);
    }
    if ((!paramBoolean1) || (paramBoolean2)) {
      removeAndRecycleViews();
    }
    this.mAdapterHelper.a();
    localAdapter = this.mAdapter;
    this.mAdapter = paramAdapter;
    if (paramAdapter != null)
    {
      paramAdapter.registerAdapterDataObserver(this.mObserver);
      paramAdapter.onAttachedToRecyclerView(this);
    }
    paramAdapter = this.mLayout;
    if (paramAdapter != null) {
      paramAdapter.onAdapterChanged(localAdapter, this.mAdapter);
    }
    this.mRecycler.onAdapterChanged(localAdapter, this.mAdapter, paramBoolean1);
    this.mState.mStructureChanged = true;
    setDataSetChangedAfterLayout();
  }
  
  private void stopScrollersInternal()
  {
    this.mViewFlinger.b();
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.stopSmoothScroller();
    }
  }
  
  void absorbGlows(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
    {
      ensureLeftGlow();
      this.mLeftGlow.onAbsorb(-paramInt1);
    }
    else if (paramInt1 > 0)
    {
      ensureRightGlow();
      this.mRightGlow.onAbsorb(paramInt1);
    }
    if (paramInt2 < 0)
    {
      ensureTopGlow();
      this.mTopGlow.onAbsorb(-paramInt2);
    }
    else if (paramInt2 > 0)
    {
      ensureBottomGlow();
      this.mBottomGlow.onAbsorb(paramInt2);
    }
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if ((localLayoutManager == null) || (!localLayoutManager.onAddFocusables(this, paramArrayList, paramInt1, paramInt2))) {
      super.addFocusables(paramArrayList, paramInt1, paramInt2);
    }
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration)
  {
    addItemDecoration(paramItemDecoration, -1);
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration, int paramInt)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
    }
    if (this.mItemDecorations.isEmpty()) {
      setWillNotDraw(false);
    }
    if (paramInt < 0) {
      this.mItemDecorations.add(paramItemDecoration);
    } else {
      this.mItemDecorations.add(paramInt, paramItemDecoration);
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    if (this.mOnChildAttachStateListeners == null) {
      this.mOnChildAttachStateListeners = new ArrayList();
    }
    this.mOnChildAttachStateListeners.add(paramOnChildAttachStateChangeListener);
  }
  
  public void addOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    this.mOnItemTouchListeners.add(paramOnItemTouchListener);
  }
  
  public void addOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    if (this.mScrollListeners == null) {
      this.mScrollListeners = new ArrayList();
    }
    this.mScrollListeners.add(paramOnScrollListener);
  }
  
  void animateAppearance(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2)
  {
    paramViewHolder.setIsRecyclable(false);
    if (this.mItemAnimator.animateAppearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  void animateDisappearance(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2)
  {
    addAnimatingView(paramViewHolder);
    paramViewHolder.setIsRecyclable(false);
    if (this.mItemAnimator.animateDisappearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  void assertInLayoutOrScroll(String paramString)
  {
    if (!isComputingLayout())
    {
      if (paramString == null)
      {
        paramString = new StringBuilder();
        paramString.append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
        paramString.append(exceptionLabel());
        throw new IllegalStateException(paramString.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(exceptionLabel());
      throw new IllegalStateException(localStringBuilder.toString());
    }
  }
  
  void assertNotInLayoutOrScroll(String paramString)
  {
    if (isComputingLayout())
    {
      if (paramString == null)
      {
        paramString = new StringBuilder();
        paramString.append("Cannot call this method while RecyclerView is computing a layout or scrolling");
        paramString.append(exceptionLabel());
        throw new IllegalStateException(paramString.toString());
      }
      throw new IllegalStateException(paramString);
    }
    if (this.mDispatchScrollCounter > 0)
    {
      paramString = new StringBuilder();
      paramString.append("");
      paramString.append(exceptionLabel());
      Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(paramString.toString()));
    }
  }
  
  boolean canReuseUpdatedViewHolder(ViewHolder paramViewHolder)
  {
    ItemAnimator localItemAnimator = this.mItemAnimator;
    boolean bool;
    if ((localItemAnimator != null) && (!localItemAnimator.canReuseUpdatedViewHolder(paramViewHolder, paramViewHolder.getUnmodifiedPayloads()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if (((paramLayoutParams instanceof LayoutParams)) && (this.mLayout.checkLayoutParams((LayoutParams)paramLayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void clearOldPositions()
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      if (!localViewHolder.shouldIgnore()) {
        localViewHolder.clearOldPosition();
      }
    }
    this.mRecycler.clearOldPositions();
  }
  
  public void clearOnChildAttachStateChangeListeners()
  {
    List localList = this.mOnChildAttachStateListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public void clearOnScrollListeners()
  {
    List localList = this.mScrollListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public int computeHorizontalScrollExtent()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      i = this.mLayout.computeHorizontalScrollExtent(this.mState);
    }
    return i;
  }
  
  public int computeHorizontalScrollOffset()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      i = this.mLayout.computeHorizontalScrollOffset(this.mState);
    }
    return i;
  }
  
  public int computeHorizontalScrollRange()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      i = this.mLayout.computeHorizontalScrollRange(this.mState);
    }
    return i;
  }
  
  public int computeVerticalScrollExtent()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      i = this.mLayout.computeVerticalScrollExtent(this.mState);
    }
    return i;
  }
  
  public int computeVerticalScrollOffset()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      i = this.mLayout.computeVerticalScrollOffset(this.mState);
    }
    return i;
  }
  
  public int computeVerticalScrollRange()
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = 0;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      i = this.mLayout.computeVerticalScrollRange(this.mState);
    }
    return i;
  }
  
  void considerReleasingGlowsOnScroll(int paramInt1, int paramInt2)
  {
    EdgeEffect localEdgeEffect = this.mLeftGlow;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()) && (paramInt1 > 0))
    {
      this.mLeftGlow.onRelease();
      bool1 = this.mLeftGlow.isFinished();
    }
    else
    {
      bool1 = false;
    }
    localEdgeEffect = this.mRightGlow;
    boolean bool2 = bool1;
    if (localEdgeEffect != null)
    {
      bool2 = bool1;
      if (!localEdgeEffect.isFinished())
      {
        bool2 = bool1;
        if (paramInt1 < 0)
        {
          this.mRightGlow.onRelease();
          bool2 = bool1 | this.mRightGlow.isFinished();
        }
      }
    }
    localEdgeEffect = this.mTopGlow;
    boolean bool1 = bool2;
    if (localEdgeEffect != null)
    {
      bool1 = bool2;
      if (!localEdgeEffect.isFinished())
      {
        bool1 = bool2;
        if (paramInt2 > 0)
        {
          this.mTopGlow.onRelease();
          bool1 = bool2 | this.mTopGlow.isFinished();
        }
      }
    }
    localEdgeEffect = this.mBottomGlow;
    bool2 = bool1;
    if (localEdgeEffect != null)
    {
      bool2 = bool1;
      if (!localEdgeEffect.isFinished())
      {
        bool2 = bool1;
        if (paramInt2 < 0)
        {
          this.mBottomGlow.onRelease();
          bool2 = bool1 | this.mBottomGlow.isFinished();
        }
      }
    }
    if (bool2) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  void consumePendingUpdateOperations()
  {
    if ((this.mFirstLayoutComplete) && (!this.mDataSetHasChangedAfterLayout))
    {
      if (!this.mAdapterHelper.d()) {
        return;
      }
      if ((this.mAdapterHelper.a(4)) && (!this.mAdapterHelper.a(11)))
      {
        TraceCompat.beginSection("RV PartialInvalidate");
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mAdapterHelper.b();
        if (!this.mLayoutRequestEaten) {
          if (hasUpdatedView()) {
            dispatchLayout();
          } else {
            this.mAdapterHelper.c();
          }
        }
        resumeRequestLayout(true);
        onExitLayoutOrScroll();
        TraceCompat.endSection();
      }
      else if (this.mAdapterHelper.d())
      {
        TraceCompat.beginSection("RV FullInvalidate");
        dispatchLayout();
        TraceCompat.endSection();
      }
      return;
    }
    TraceCompat.beginSection("RV FullInvalidate");
    dispatchLayout();
    TraceCompat.endSection();
  }
  
  void defaultOnMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(LayoutManager.chooseSize(paramInt1, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(paramInt2, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
  }
  
  void dispatchChildAttached(View paramView)
  {
    Object localObject = getChildViewHolderInt(paramView);
    onChildAttachedToWindow(paramView);
    Adapter localAdapter = this.mAdapter;
    if ((localAdapter != null) && (localObject != null)) {
      localAdapter.onViewAttachedToWindow((ViewHolder)localObject);
    }
    localObject = this.mOnChildAttachStateListeners;
    if (localObject != null) {
      for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewAttachedToWindow(paramView);
      }
    }
  }
  
  void dispatchChildDetached(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    onChildDetachedFromWindow(paramView);
    Object localObject = this.mAdapter;
    if ((localObject != null) && (localViewHolder != null)) {
      ((Adapter)localObject).onViewDetachedFromWindow(localViewHolder);
    }
    localObject = this.mOnChildAttachStateListeners;
    if (localObject != null) {
      for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewDetachedFromWindow(paramView);
      }
    }
  }
  
  void dispatchLayout()
  {
    if (this.mAdapter == null)
    {
      Log.e("RecyclerView", "No adapter attached; skipping layout");
      return;
    }
    if (this.mLayout == null)
    {
      Log.e("RecyclerView", "No layout manager attached; skipping layout");
      return;
    }
    State localState = this.mState;
    localState.mIsMeasuring = false;
    if (localState.mLayoutStep == 1)
    {
      dispatchLayoutStep1();
      this.mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    }
    else if ((!this.mAdapterHelper.f()) && (this.mLayout.getWidth() == getWidth()) && (this.mLayout.getHeight() == getHeight()))
    {
      this.mLayout.setExactMeasureSpecsFrom(this);
    }
    else
    {
      this.mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    }
    dispatchLayoutStep3();
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return getScrollingChildHelper().dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return getScrollingChildHelper().dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3)
  {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramInt3);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5)
  {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
  }
  
  void dispatchOnScrollStateChanged(int paramInt)
  {
    Object localObject = this.mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).onScrollStateChanged(paramInt);
    }
    onScrollStateChanged(paramInt);
    localObject = this.mScrollListener;
    if (localObject != null) {
      ((OnScrollListener)localObject).onScrollStateChanged(this, paramInt);
    }
    localObject = this.mScrollListeners;
    if (localObject != null) {
      for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
        ((OnScrollListener)this.mScrollListeners.get(i)).onScrollStateChanged(this, paramInt);
      }
    }
  }
  
  void dispatchOnScrolled(int paramInt1, int paramInt2)
  {
    this.mDispatchScrollCounter += 1;
    int i = getScrollX();
    int j = getScrollY();
    onScrollChanged(i, j, i, j);
    onScrolled(paramInt1, paramInt2);
    Object localObject = this.mScrollListener;
    if (localObject != null) {
      ((OnScrollListener)localObject).onScrolled(this, paramInt1, paramInt2);
    }
    localObject = this.mScrollListeners;
    if (localObject != null) {
      for (j = ((List)localObject).size() - 1; j >= 0; j--) {
        ((OnScrollListener)this.mScrollListeners.get(j)).onScrolled(this, paramInt1, paramInt2);
      }
    }
    this.mDispatchScrollCounter -= 1;
  }
  
  void dispatchPendingImportantForAccessibilityChanges()
  {
    for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; i--)
    {
      ViewHolder localViewHolder = (ViewHolder)this.mPendingAccessibilityImportanceChange.get(i);
      if ((localViewHolder.itemView.getParent() == this) && (!localViewHolder.shouldIgnore()))
      {
        int j = localViewHolder.mPendingAccessibilityState;
        if (j != -1)
        {
          ViewCompat.setImportantForAccessibility(localViewHolder.itemView, j);
          localViewHolder.mPendingAccessibilityState = -1;
        }
      }
    }
    this.mPendingAccessibilityImportanceChange.clear();
  }
  
  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchThawSelfOnly(paramSparseArray);
  }
  
  protected void dispatchSaveInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchFreezeSelfOnly(paramSparseArray);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i = this.mItemDecorations.size();
    int j = 0;
    for (int k = 0; k < i; k++) {
      ((ItemDecoration)this.mItemDecorations.get(k)).onDrawOver(paramCanvas, this, this.mState);
    }
    EdgeEffect localEdgeEffect = this.mLeftGlow;
    int m;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()))
    {
      m = paramCanvas.save();
      if (this.mClipToPadding) {
        k = getPaddingBottom();
      } else {
        k = 0;
      }
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-getHeight() + k, 0.0F);
      localEdgeEffect = this.mLeftGlow;
      if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
        i = 1;
      } else {
        i = 0;
      }
      paramCanvas.restoreToCount(m);
    }
    else
    {
      i = 0;
    }
    localEdgeEffect = this.mTopGlow;
    k = i;
    if (localEdgeEffect != null)
    {
      k = i;
      if (!localEdgeEffect.isFinished())
      {
        m = paramCanvas.save();
        if (this.mClipToPadding) {
          paramCanvas.translate(getPaddingLeft(), getPaddingTop());
        }
        localEdgeEffect = this.mTopGlow;
        if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
          k = 1;
        } else {
          k = 0;
        }
        k = i | k;
        paramCanvas.restoreToCount(m);
      }
    }
    localEdgeEffect = this.mRightGlow;
    i = k;
    if (localEdgeEffect != null)
    {
      i = k;
      if (!localEdgeEffect.isFinished())
      {
        m = paramCanvas.save();
        int n = getWidth();
        if (this.mClipToPadding) {
          i = getPaddingTop();
        } else {
          i = 0;
        }
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-i, -n);
        localEdgeEffect = this.mRightGlow;
        if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
          i = 1;
        } else {
          i = 0;
        }
        i = k | i;
        paramCanvas.restoreToCount(m);
      }
    }
    localEdgeEffect = this.mBottomGlow;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()))
    {
      m = paramCanvas.save();
      paramCanvas.rotate(180.0F);
      if (this.mClipToPadding) {
        paramCanvas.translate(-getWidth() + getPaddingRight(), -getHeight() + getPaddingBottom());
      } else {
        paramCanvas.translate(-getWidth(), -getHeight());
      }
      localEdgeEffect = this.mBottomGlow;
      k = j;
      if (localEdgeEffect != null)
      {
        k = j;
        if (localEdgeEffect.draw(paramCanvas)) {
          k = 1;
        }
      }
      k |= i;
      paramCanvas.restoreToCount(m);
    }
    else
    {
      k = i;
    }
    i = k;
    if (k == 0)
    {
      i = k;
      if (this.mItemAnimator != null)
      {
        i = k;
        if (this.mItemDecorations.size() > 0)
        {
          i = k;
          if (this.mItemAnimator.isRunning()) {
            i = 1;
          }
        }
      }
    }
    if (i != 0) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  void eatRequestLayout()
  {
    this.mEatRequestLayout += 1;
    if ((this.mEatRequestLayout == 1) && (!this.mLayoutFrozen)) {
      this.mLayoutRequestEaten = false;
    }
  }
  
  void ensureBottomGlow()
  {
    if (this.mBottomGlow != null) {
      return;
    }
    this.mBottomGlow = new EdgeEffect(getContext());
    if (this.mClipToPadding) {
      this.mBottomGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
    } else {
      this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
    }
  }
  
  void ensureLeftGlow()
  {
    if (this.mLeftGlow != null) {
      return;
    }
    this.mLeftGlow = new EdgeEffect(getContext());
    if (this.mClipToPadding) {
      this.mLeftGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
    } else {
      this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
    }
  }
  
  void ensureRightGlow()
  {
    if (this.mRightGlow != null) {
      return;
    }
    this.mRightGlow = new EdgeEffect(getContext());
    if (this.mClipToPadding) {
      this.mRightGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
    } else {
      this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
    }
  }
  
  void ensureTopGlow()
  {
    if (this.mTopGlow != null) {
      return;
    }
    this.mTopGlow = new EdgeEffect(getContext());
    if (this.mClipToPadding) {
      this.mTopGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
    } else {
      this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
    }
  }
  
  String exceptionLabel()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" ");
    localStringBuilder.append(super.toString());
    localStringBuilder.append(", adapter:");
    localStringBuilder.append(this.mAdapter);
    localStringBuilder.append(", layout:");
    localStringBuilder.append(this.mLayout);
    localStringBuilder.append(", context:");
    localStringBuilder.append(getContext());
    return localStringBuilder.toString();
  }
  
  final void fillRemainingScrollValues(State paramState)
  {
    if (getScrollState() == 2)
    {
      OverScroller localOverScroller = d.a(this.mViewFlinger);
      paramState.mRemainingScrollHorizontal = (localOverScroller.getFinalX() - localOverScroller.getCurrX());
      paramState.mRemainingScrollVertical = (localOverScroller.getFinalY() - localOverScroller.getCurrY());
    }
    else
    {
      paramState.mRemainingScrollHorizontal = 0;
      paramState.mRemainingScrollVertical = 0;
    }
  }
  
  public View findChildViewUnder(float paramFloat1, float paramFloat2)
  {
    for (int i = this.mChildHelper.b() - 1; i >= 0; i--)
    {
      View localView = this.mChildHelper.b(i);
      float f1 = localView.getTranslationX();
      float f2 = localView.getTranslationY();
      if ((paramFloat1 >= localView.getLeft() + f1) && (paramFloat1 <= localView.getRight() + f1) && (paramFloat2 >= localView.getTop() + f2) && (paramFloat2 <= localView.getBottom() + f2)) {
        return localView;
      }
    }
    return null;
  }
  
  public View findContainingItemView(View paramView)
  {
    for (ViewParent localViewParent = paramView.getParent(); (localViewParent != null) && (localViewParent != this) && ((localViewParent instanceof View)); localViewParent = paramView.getParent()) {
      paramView = (View)localViewParent;
    }
    if (localViewParent != this) {
      paramView = null;
    }
    return paramView;
  }
  
  public ViewHolder findContainingViewHolder(View paramView)
  {
    paramView = findContainingItemView(paramView);
    if (paramView == null) {
      paramView = null;
    } else {
      paramView = getChildViewHolder(paramView);
    }
    return paramView;
  }
  
  public ViewHolder findViewHolderForAdapterPosition(int paramInt)
  {
    boolean bool = this.mDataSetHasChangedAfterLayout;
    Object localObject1 = null;
    if (bool) {
      return null;
    }
    int i = this.mChildHelper.c();
    int j = 0;
    while (j < i)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      Object localObject2 = localObject1;
      if (localViewHolder != null)
      {
        localObject2 = localObject1;
        if (!localViewHolder.isRemoved())
        {
          localObject2 = localObject1;
          if (getAdapterPositionFor(localViewHolder) == paramInt) {
            if (this.mChildHelper.c(localViewHolder.itemView)) {
              localObject2 = localViewHolder;
            } else {
              return localViewHolder;
            }
          }
        }
      }
      j++;
      localObject1 = localObject2;
    }
    return (ViewHolder)localObject1;
  }
  
  public ViewHolder findViewHolderForItemId(long paramLong)
  {
    Object localObject1 = this.mAdapter;
    Object localObject2 = null;
    if ((localObject1 != null) && (((Adapter)localObject1).hasStableIds()))
    {
      int i = this.mChildHelper.c();
      int j = 0;
      while (j < i)
      {
        ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
        localObject1 = localObject2;
        if (localViewHolder != null)
        {
          localObject1 = localObject2;
          if (!localViewHolder.isRemoved())
          {
            localObject1 = localObject2;
            if (localViewHolder.getItemId() == paramLong) {
              if (this.mChildHelper.c(localViewHolder.itemView)) {
                localObject1 = localViewHolder;
              } else {
                return localViewHolder;
              }
            }
          }
        }
        j++;
        localObject2 = localObject1;
      }
      return (ViewHolder)localObject2;
    }
    return null;
  }
  
  public ViewHolder findViewHolderForLayoutPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }
  
  @Deprecated
  public ViewHolder findViewHolderForPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }
  
  ViewHolder findViewHolderForPosition(int paramInt, boolean paramBoolean)
  {
    int i = this.mChildHelper.c();
    Object localObject1 = null;
    int j = 0;
    while (j < i)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      Object localObject2 = localObject1;
      if (localViewHolder != null)
      {
        localObject2 = localObject1;
        if (!localViewHolder.isRemoved())
        {
          if (paramBoolean)
          {
            if (localViewHolder.mPosition != paramInt)
            {
              localObject2 = localObject1;
              break label116;
            }
          }
          else if (localViewHolder.getLayoutPosition() != paramInt)
          {
            localObject2 = localObject1;
            break label116;
          }
          if (this.mChildHelper.c(localViewHolder.itemView)) {
            localObject2 = localViewHolder;
          } else {
            return localViewHolder;
          }
        }
      }
      label116:
      j++;
      localObject1 = localObject2;
    }
    return (ViewHolder)localObject1;
  }
  
  public boolean fling(int paramInt1, int paramInt2)
  {
    Object localObject = this.mLayout;
    int i = 0;
    if (localObject == null)
    {
      Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return false;
    }
    if (this.mLayoutFrozen) {
      return false;
    }
    boolean bool1 = ((LayoutManager)localObject).canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    int j;
    if (bool1)
    {
      j = paramInt1;
      if (Math.abs(paramInt1) >= this.mMinFlingVelocity) {}
    }
    else
    {
      j = 0;
    }
    int k;
    if (bool2)
    {
      k = paramInt2;
      if (Math.abs(paramInt2) >= this.mMinFlingVelocity) {}
    }
    else
    {
      k = 0;
    }
    if ((j == 0) && (k == 0)) {
      return false;
    }
    float f1 = j;
    float f2 = k;
    if (!dispatchNestedPreFling(f1, f2))
    {
      boolean bool3;
      if ((!bool1) && (!bool2)) {
        bool3 = false;
      } else {
        bool3 = true;
      }
      dispatchNestedFling(f1, f2, bool3);
      localObject = this.mOnFlingListener;
      if ((localObject != null) && (((OnFlingListener)localObject).onFling(j, k))) {
        return true;
      }
      if (bool3)
      {
        paramInt1 = i;
        if (bool1) {
          paramInt1 = 1;
        }
        paramInt2 = paramInt1;
        if (bool2) {
          paramInt2 = paramInt1 | 0x2;
        }
        startNestedScroll(paramInt2, 1);
        paramInt1 = this.mMaxFlingVelocity;
        paramInt1 = Math.max(-paramInt1, Math.min(j, paramInt1));
        paramInt2 = this.mMaxFlingVelocity;
        paramInt2 = Math.max(-paramInt2, Math.min(k, paramInt2));
        this.mViewFlinger.a(paramInt1, paramInt2);
        return true;
      }
    }
    return false;
  }
  
  public View focusSearch(View paramView, int paramInt)
  {
    Object localObject = this.mLayout.onInterceptFocusSearch(paramView, paramInt);
    if (localObject != null) {
      return (View)localObject;
    }
    int i;
    if ((this.mAdapter != null) && (this.mLayout != null) && (!isComputingLayout()) && (!this.mLayoutFrozen)) {
      i = 1;
    } else {
      i = 0;
    }
    localObject = FocusFinder.getInstance();
    if ((i != 0) && ((paramInt == 2) || (paramInt == 1)))
    {
      int k;
      if (this.mLayout.canScrollVertically())
      {
        if (paramInt == 2) {
          j = 130;
        } else {
          j = 33;
        }
        if (((FocusFinder)localObject).findNextFocus(this, paramView, j) == null) {
          k = 1;
        } else {
          k = 0;
        }
        i = k;
        if (FORCE_ABS_FOCUS_SEARCH_DIRECTION)
        {
          paramInt = j;
          i = k;
        }
      }
      else
      {
        i = 0;
      }
      int m = i;
      int j = paramInt;
      if (i == 0)
      {
        m = i;
        j = paramInt;
        if (this.mLayout.canScrollHorizontally())
        {
          if (this.mLayout.getLayoutDirection() == 1) {
            i = 1;
          } else {
            i = 0;
          }
          if (paramInt == 2) {
            j = 1;
          } else {
            j = 0;
          }
          if ((i ^ j) != 0) {
            i = 66;
          } else {
            i = 17;
          }
          if (((FocusFinder)localObject).findNextFocus(this, paramView, i) == null) {
            k = 1;
          } else {
            k = 0;
          }
          m = k;
          j = paramInt;
          if (FORCE_ABS_FOCUS_SEARCH_DIRECTION)
          {
            j = i;
            m = k;
          }
        }
      }
      if (m != 0)
      {
        consumePendingUpdateOperations();
        if (findContainingItemView(paramView) == null) {
          return null;
        }
        eatRequestLayout();
        this.mLayout.onFocusSearchFailed(paramView, j, this.mRecycler, this.mState);
        resumeRequestLayout(false);
      }
      localObject = ((FocusFinder)localObject).findNextFocus(this, paramView, j);
      paramInt = j;
    }
    else
    {
      localObject = ((FocusFinder)localObject).findNextFocus(this, paramView, paramInt);
      if ((localObject == null) && (i != 0))
      {
        consumePendingUpdateOperations();
        if (findContainingItemView(paramView) == null) {
          return null;
        }
        eatRequestLayout();
        localObject = this.mLayout.onFocusSearchFailed(paramView, paramInt, this.mRecycler, this.mState);
        resumeRequestLayout(false);
      }
    }
    if ((localObject != null) && (!((View)localObject).hasFocusable()))
    {
      if (getFocusedChild() == null) {
        return super.focusSearch(paramView, paramInt);
      }
      requestChildOnScreen((View)localObject, null);
      return paramView;
    }
    if (!isPreferredNextFocus(paramView, (View)localObject, paramInt)) {
      localObject = super.focusSearch(paramView, paramInt);
    }
    return (View)localObject;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    Object localObject = this.mLayout;
    if (localObject != null) {
      return ((LayoutManager)localObject).generateDefaultLayoutParams();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("RecyclerView has no LayoutManager");
    ((StringBuilder)localObject).append(exceptionLabel());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.generateLayoutParams(getContext(), paramAttributeSet);
    }
    paramAttributeSet = new StringBuilder();
    paramAttributeSet.append("RecyclerView has no LayoutManager");
    paramAttributeSet.append(exceptionLabel());
    throw new IllegalStateException(paramAttributeSet.toString());
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.generateLayoutParams(paramLayoutParams);
    }
    paramLayoutParams = new StringBuilder();
    paramLayoutParams.append("RecyclerView has no LayoutManager");
    paramLayoutParams.append(exceptionLabel());
    throw new IllegalStateException(paramLayoutParams.toString());
  }
  
  public Adapter getAdapter()
  {
    return this.mAdapter;
  }
  
  int getAdapterPositionFor(ViewHolder paramViewHolder)
  {
    if ((!paramViewHolder.hasAnyOfTheFlags(524)) && (paramViewHolder.isBound())) {
      return this.mAdapterHelper.c(paramViewHolder.mPosition);
    }
    return -1;
  }
  
  public int getBaseline()
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.getBaseline();
    }
    return super.getBaseline();
  }
  
  long getChangedHolderKey(ViewHolder paramViewHolder)
  {
    long l;
    if (this.mAdapter.hasStableIds()) {
      l = paramViewHolder.getItemId();
    } else {
      l = paramViewHolder.mPosition;
    }
    return l;
  }
  
  public int getChildAdapterPosition(View paramView)
  {
    paramView = getChildViewHolderInt(paramView);
    int i;
    if (paramView != null) {
      i = paramView.getAdapterPosition();
    } else {
      i = -1;
    }
    return i;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    ChildDrawingOrderCallback localChildDrawingOrderCallback = this.mChildDrawingOrderCallback;
    if (localChildDrawingOrderCallback == null) {
      return super.getChildDrawingOrder(paramInt1, paramInt2);
    }
    return localChildDrawingOrderCallback.onGetChildDrawingOrder(paramInt1, paramInt2);
  }
  
  public long getChildItemId(View paramView)
  {
    Adapter localAdapter = this.mAdapter;
    long l = -1L;
    if ((localAdapter != null) && (localAdapter.hasStableIds()))
    {
      paramView = getChildViewHolderInt(paramView);
      if (paramView != null) {
        l = paramView.getItemId();
      }
      return l;
    }
    return -1L;
  }
  
  public int getChildLayoutPosition(View paramView)
  {
    paramView = getChildViewHolderInt(paramView);
    int i;
    if (paramView != null) {
      i = paramView.getLayoutPosition();
    } else {
      i = -1;
    }
    return i;
  }
  
  @Deprecated
  public int getChildPosition(View paramView)
  {
    return getChildAdapterPosition(paramView);
  }
  
  public ViewHolder getChildViewHolder(View paramView)
  {
    Object localObject = paramView.getParent();
    if ((localObject != null) && (localObject != this))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("View ");
      ((StringBuilder)localObject).append(paramView);
      ((StringBuilder)localObject).append(" is not a direct child of ");
      ((StringBuilder)localObject).append(this);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    return getChildViewHolderInt(paramView);
  }
  
  public boolean getClipToPadding()
  {
    return this.mClipToPadding;
  }
  
  public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate()
  {
    return this.mAccessibilityDelegate;
  }
  
  public void getDecoratedBoundsWithMargins(View paramView, Rect paramRect)
  {
    getDecoratedBoundsWithMarginsInt(paramView, paramRect);
  }
  
  public ItemAnimator getItemAnimator()
  {
    return this.mItemAnimator;
  }
  
  Rect getItemDecorInsetsForChild(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!localLayoutParams.mInsetsDirty) {
      return localLayoutParams.mDecorInsets;
    }
    if ((this.mState.isPreLayout()) && ((localLayoutParams.isItemChanged()) || (localLayoutParams.isViewInvalid()))) {
      return localLayoutParams.mDecorInsets;
    }
    Rect localRect = localLayoutParams.mDecorInsets;
    localRect.set(0, 0, 0, 0);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++)
    {
      this.mTempRect.set(0, 0, 0, 0);
      ((ItemDecoration)this.mItemDecorations.get(j)).getItemOffsets(this.mTempRect, paramView, this, this.mState);
      localRect.left += this.mTempRect.left;
      localRect.top += this.mTempRect.top;
      localRect.right += this.mTempRect.right;
      localRect.bottom += this.mTempRect.bottom;
    }
    localLayoutParams.mInsetsDirty = false;
    return localRect;
  }
  
  public ItemDecoration getItemDecorationAt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mItemDecorations.size())) {
      return (ItemDecoration)this.mItemDecorations.get(paramInt);
    }
    return null;
  }
  
  public LayoutManager getLayoutManager()
  {
    return this.mLayout;
  }
  
  public int getMaxFlingVelocity()
  {
    return this.mMaxFlingVelocity;
  }
  
  public int getMinFlingVelocity()
  {
    return this.mMinFlingVelocity;
  }
  
  long getNanoTime()
  {
    if (ALLOW_THREAD_GAP_WORK) {
      return System.nanoTime();
    }
    return 0L;
  }
  
  public OnFlingListener getOnFlingListener()
  {
    return this.mOnFlingListener;
  }
  
  public boolean getPreserveFocusAfterLayout()
  {
    return this.mPreserveFocusAfterLayout;
  }
  
  public RecycledViewPool getRecycledViewPool()
  {
    return this.mRecycler.getRecycledViewPool();
  }
  
  public int getScrollState()
  {
    return this.mScrollState;
  }
  
  public boolean hasFixedSize()
  {
    return this.mHasFixedSize;
  }
  
  public boolean hasNestedScrollingParent()
  {
    return getScrollingChildHelper().hasNestedScrollingParent();
  }
  
  public boolean hasNestedScrollingParent(int paramInt)
  {
    return getScrollingChildHelper().hasNestedScrollingParent(paramInt);
  }
  
  public boolean hasPendingAdapterUpdates()
  {
    boolean bool;
    if ((this.mFirstLayoutComplete) && (!this.mDataSetHasChangedAfterLayout) && (!this.mAdapterHelper.d())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void initAdapterManager()
  {
    this.mAdapterHelper = new d(new d.a()
    {
      public RecyclerView.ViewHolder a(int paramAnonymousInt)
      {
        RecyclerView.ViewHolder localViewHolder = RecyclerView.this.findViewHolderForPosition(paramAnonymousInt, true);
        if (localViewHolder == null) {
          return null;
        }
        if (RecyclerView.this.mChildHelper.c(localViewHolder.itemView)) {
          return null;
        }
        return localViewHolder;
      }
      
      public void a(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, true);
        Object localObject = RecyclerView.this;
        ((RecyclerView)localObject).mItemsAddedOrRemoved = true;
        localObject = ((RecyclerView)localObject).mState;
        ((RecyclerView.State)localObject).mDeletedInvisibleItemCountSincePreviousLayout += paramAnonymousInt2;
      }
      
      public void a(int paramAnonymousInt1, int paramAnonymousInt2, Object paramAnonymousObject)
      {
        RecyclerView.this.viewRangeUpdate(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousObject);
        RecyclerView.this.mItemsChanged = true;
      }
      
      public void a(d.b paramAnonymousb)
      {
        c(paramAnonymousb);
      }
      
      public void b(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, false);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }
      
      public void b(d.b paramAnonymousb)
      {
        c(paramAnonymousb);
      }
      
      public void c(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForInsert(paramAnonymousInt1, paramAnonymousInt2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }
      
      void c(d.b paramAnonymousb)
      {
        int i = paramAnonymousb.a;
        if (i != 4)
        {
          if (i != 8) {
            switch (i)
            {
            default: 
              break;
            case 2: 
              RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, paramAnonymousb.b, paramAnonymousb.d);
              break;
            case 1: 
              RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, paramAnonymousb.b, paramAnonymousb.d);
              break;
            }
          } else {
            RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, paramAnonymousb.b, paramAnonymousb.d, 1);
          }
        }
        else {
          RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, paramAnonymousb.b, paramAnonymousb.d, paramAnonymousb.c);
        }
      }
      
      public void d(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForMove(paramAnonymousInt1, paramAnonymousInt2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }
    });
  }
  
  void initFastScroller(StateListDrawable paramStateListDrawable1, Drawable paramDrawable1, StateListDrawable paramStateListDrawable2, Drawable paramDrawable2)
  {
    if ((paramStateListDrawable1 != null) && (paramDrawable1 != null) && (paramStateListDrawable2 != null) && (paramDrawable2 != null))
    {
      Resources localResources = getContext().getResources();
      new t(this, paramStateListDrawable1, paramDrawable1, paramStateListDrawable2, paramDrawable2, localResources.getDimensionPixelSize(a.a.fastscroll_default_thickness), localResources.getDimensionPixelSize(a.a.fastscroll_minimum_range), localResources.getDimensionPixelOffset(a.a.fastscroll_margin));
      return;
    }
    paramStateListDrawable1 = new StringBuilder();
    paramStateListDrawable1.append("Trying to set fast scroller without both required drawables.");
    paramStateListDrawable1.append(exceptionLabel());
    throw new IllegalArgumentException(paramStateListDrawable1.toString());
  }
  
  void invalidateGlows()
  {
    this.mBottomGlow = null;
    this.mTopGlow = null;
    this.mRightGlow = null;
    this.mLeftGlow = null;
  }
  
  public void invalidateItemDecorations()
  {
    if (this.mItemDecorations.size() == 0) {
      return;
    }
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  boolean isAccessibilityEnabled()
  {
    AccessibilityManager localAccessibilityManager = this.mAccessibilityManager;
    boolean bool;
    if ((localAccessibilityManager != null) && (localAccessibilityManager.isEnabled())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isAnimating()
  {
    ItemAnimator localItemAnimator = this.mItemAnimator;
    boolean bool;
    if ((localItemAnimator != null) && (localItemAnimator.isRunning())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isAttachedToWindow()
  {
    return this.mIsAttached;
  }
  
  public boolean isComputingLayout()
  {
    boolean bool;
    if (this.mLayoutOrScrollCounter > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isLayoutFrozen()
  {
    return this.mLayoutFrozen;
  }
  
  public boolean isNestedScrollingEnabled()
  {
    return getScrollingChildHelper().isNestedScrollingEnabled();
  }
  
  void jumpToPositionForSmoothScroller(int paramInt)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null) {
      return;
    }
    localLayoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  void markItemDecorInsetsDirty()
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++) {
      ((LayoutParams)this.mChildHelper.d(j).getLayoutParams()).mInsetsDirty = true;
    }
    this.mRecycler.markItemDecorInsetsDirty();
  }
  
  void markKnownViewsInvalid()
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore())) {
        localViewHolder.addFlags(6);
      }
    }
    markItemDecorInsetsDirty();
    this.mRecycler.markKnownViewsInvalid();
  }
  
  public void offsetChildrenHorizontal(int paramInt)
  {
    int i = this.mChildHelper.b();
    for (int j = 0; j < i; j++) {
      this.mChildHelper.b(j).offsetLeftAndRight(paramInt);
    }
  }
  
  public void offsetChildrenVertical(int paramInt)
  {
    int i = this.mChildHelper.b();
    for (int j = 0; j < i; j++) {
      this.mChildHelper.b(j).offsetTopAndBottom(paramInt);
    }
  }
  
  void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (localViewHolder.mPosition >= paramInt1))
      {
        localViewHolder.offsetPosition(paramInt2, false);
        this.mState.mStructureChanged = true;
      }
    }
    this.mRecycler.offsetPositionRecordsForInsert(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.c();
    int j;
    int k;
    int m;
    if (paramInt1 < paramInt2)
    {
      j = paramInt1;
      k = paramInt2;
      m = -1;
    }
    else
    {
      k = paramInt1;
      j = paramInt2;
      m = 1;
    }
    for (int n = 0; n < i; n++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(n));
      if ((localViewHolder != null) && (localViewHolder.mPosition >= j) && (localViewHolder.mPosition <= k))
      {
        if (localViewHolder.mPosition == paramInt1) {
          localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
        } else {
          localViewHolder.offsetPosition(m, false);
        }
        this.mState.mStructureChanged = true;
      }
    }
    this.mRecycler.offsetPositionRecordsForMove(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore())) {
        if (localViewHolder.mPosition >= paramInt1 + paramInt2)
        {
          localViewHolder.offsetPosition(-paramInt2, paramBoolean);
          this.mState.mStructureChanged = true;
        }
        else if (localViewHolder.mPosition >= paramInt1)
        {
          localViewHolder.flagRemovedAndOffsetPosition(paramInt1 - 1, -paramInt2, paramBoolean);
          this.mState.mStructureChanged = true;
        }
      }
    }
    this.mRecycler.offsetPositionRecordsForRemove(paramInt1, paramInt2, paramBoolean);
    requestLayout();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mLayoutOrScrollCounter = 0;
    boolean bool = true;
    this.mIsAttached = true;
    if ((!this.mFirstLayoutComplete) || (isLayoutRequested())) {
      bool = false;
    }
    this.mFirstLayoutComplete = bool;
    Object localObject = this.mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).dispatchAttachedToWindow(this);
    }
    this.mPostedAnimatorRunner = false;
    if (ALLOW_THREAD_GAP_WORK)
    {
      this.mGapWorker = ((u)u.a.get());
      if (this.mGapWorker == null)
      {
        this.mGapWorker = new u();
        localObject = ViewCompat.getDisplay(this);
        float f;
        if ((!isInEditMode()) && (localObject != null))
        {
          f = ((Display)localObject).getRefreshRate();
          if (f >= 30.0F) {}
        }
        else
        {
          f = 60.0F;
        }
        this.mGapWorker.d = ((1.0E9F / f));
        u.a.set(this.mGapWorker);
      }
      this.mGapWorker.a(this);
    }
  }
  
  public void onChildAttachedToWindow(View paramView) {}
  
  public void onChildDetachedFromWindow(View paramView) {}
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Object localObject = this.mItemAnimator;
    if (localObject != null) {
      ((ItemAnimator)localObject).endAnimations();
    }
    stopScroll();
    this.mIsAttached = false;
    localObject = this.mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).dispatchDetachedFromWindow(this, this.mRecycler);
    }
    this.mPendingAccessibilityImportanceChange.clear();
    removeCallbacks(this.mItemAnimatorRunner);
    this.mViewInfoStore.b();
    if (ALLOW_THREAD_GAP_WORK)
    {
      this.mGapWorker.b(this);
      this.mGapWorker = null;
    }
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++) {
      ((ItemDecoration)this.mItemDecorations.get(j)).onDraw(paramCanvas, this, this.mState);
    }
  }
  
  void onEnterLayoutOrScroll()
  {
    this.mLayoutOrScrollCounter += 1;
  }
  
  void onExitLayoutOrScroll()
  {
    onExitLayoutOrScroll(true);
  }
  
  void onExitLayoutOrScroll(boolean paramBoolean)
  {
    this.mLayoutOrScrollCounter -= 1;
    if (this.mLayoutOrScrollCounter < 1)
    {
      this.mLayoutOrScrollCounter = 0;
      if (paramBoolean)
      {
        dispatchContentChangedIfNecessary();
        dispatchPendingImportantForAccessibilityChanges();
      }
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (this.mLayout == null) {
      return false;
    }
    if (this.mLayoutFrozen) {
      return false;
    }
    if (paramMotionEvent.getAction() == 8)
    {
      float f1;
      float f2;
      if ((paramMotionEvent.getSource() & 0x2) != 0)
      {
        if (this.mLayout.canScrollVertically()) {
          f1 = -paramMotionEvent.getAxisValue(9);
        } else {
          f1 = 0.0F;
        }
        if (this.mLayout.canScrollHorizontally()) {
          f2 = paramMotionEvent.getAxisValue(10);
        } else {
          f2 = 0.0F;
        }
      }
      else if ((paramMotionEvent.getSource() & 0x400000) != 0)
      {
        f2 = paramMotionEvent.getAxisValue(26);
        if (this.mLayout.canScrollVertically())
        {
          f1 = -f2;
          f2 = 0.0F;
        }
        else if (this.mLayout.canScrollHorizontally())
        {
          f1 = 0.0F;
        }
        else
        {
          f1 = 0.0F;
          f2 = 0.0F;
        }
      }
      else
      {
        f1 = 0.0F;
        f2 = 0.0F;
      }
      if ((f1 != 0.0F) || (f2 != 0.0F)) {
        scrollByInternal((int)(f2 * this.mScaledHorizontalScrollFactor), (int)(f1 * this.mScaledVerticalScrollFactor), paramMotionEvent);
      }
    }
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mLayoutFrozen;
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (dispatchOnItemTouchIntercept(paramMotionEvent))
    {
      cancelTouch();
      return true;
    }
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null) {
      return false;
    }
    bool1 = localLayoutManager.canScrollHorizontally();
    boolean bool3 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getActionIndex();
    switch (i)
    {
    case 4: 
    default: 
      break;
    case 6: 
      onPointerUp(paramMotionEvent);
      break;
    case 5: 
      this.mScrollPointerId = paramMotionEvent.getPointerId(j);
      i = (int)(paramMotionEvent.getX(j) + 0.5F);
      this.mLastTouchX = i;
      this.mInitialTouchX = i;
      j = (int)(paramMotionEvent.getY(j) + 0.5F);
      this.mLastTouchY = j;
      this.mInitialTouchY = j;
      break;
    case 3: 
      cancelTouch();
      break;
    case 2: 
      i = paramMotionEvent.findPointerIndex(this.mScrollPointerId);
      if (i < 0)
      {
        paramMotionEvent = new StringBuilder();
        paramMotionEvent.append("Error processing scroll; pointer index for id ");
        paramMotionEvent.append(this.mScrollPointerId);
        paramMotionEvent.append(" not found. Did any MotionEvents get skipped?");
        Log.e("RecyclerView", paramMotionEvent.toString());
        return false;
      }
      j = (int)(paramMotionEvent.getX(i) + 0.5F);
      int k = (int)(paramMotionEvent.getY(i) + 0.5F);
      if (this.mScrollState != 1)
      {
        i = this.mInitialTouchX;
        int m = this.mInitialTouchY;
        if ((bool1) && (Math.abs(j - i) > this.mTouchSlop))
        {
          this.mLastTouchX = j;
          j = 1;
        }
        else
        {
          j = 0;
        }
        i = j;
        if (bool3)
        {
          i = j;
          if (Math.abs(k - m) > this.mTouchSlop)
          {
            this.mLastTouchY = k;
            i = 1;
          }
        }
        if (i != 0) {
          setScrollState(1);
        }
      }
      break;
    case 1: 
      this.mVelocityTracker.clear();
      stopNestedScroll(0);
      break;
    case 0: 
      if (this.mIgnoreMotionEventTillDown) {
        this.mIgnoreMotionEventTillDown = false;
      }
      this.mScrollPointerId = paramMotionEvent.getPointerId(0);
      j = (int)(paramMotionEvent.getX() + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      j = (int)(paramMotionEvent.getY() + 0.5F);
      this.mLastTouchY = j;
      this.mInitialTouchY = j;
      if (this.mScrollState == 2)
      {
        getParent().requestDisallowInterceptTouchEvent(true);
        setScrollState(1);
      }
      paramMotionEvent = this.mNestedOffsets;
      paramMotionEvent[1] = 0;
      paramMotionEvent[0] = 0;
      if (bool1) {
        j = 1;
      } else {
        j = 0;
      }
      i = j;
      if (bool3) {
        i = j | 0x2;
      }
      startNestedScroll(i, 0);
    }
    if (this.mScrollState == 1) {
      bool2 = true;
    }
    return bool2;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    TraceCompat.beginSection("RV OnLayout");
    dispatchLayout();
    TraceCompat.endSection();
    this.mFirstLayoutComplete = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Object localObject = this.mLayout;
    if (localObject == null)
    {
      defaultOnMeasure(paramInt1, paramInt2);
      return;
    }
    boolean bool = ((LayoutManager)localObject).mAutoMeasure;
    int i = 0;
    if (bool)
    {
      int j = View.MeasureSpec.getMode(paramInt1);
      int k = View.MeasureSpec.getMode(paramInt2);
      int m = i;
      if (j == 1073741824)
      {
        m = i;
        if (k == 1073741824) {
          m = 1;
        }
      }
      this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
      if ((m == 0) && (this.mAdapter != null))
      {
        if (this.mState.mLayoutStep == 1) {
          dispatchLayoutStep1();
        }
        this.mLayout.setMeasureSpecs(paramInt1, paramInt2);
        this.mState.mIsMeasuring = true;
        dispatchLayoutStep2();
        this.mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
        if (!this.mLayout.shouldMeasureTwice()) {
          return;
        }
        this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        this.mState.mIsMeasuring = true;
        dispatchLayoutStep2();
        this.mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
      }
    }
    else
    {
      if (this.mHasFixedSize)
      {
        this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
        return;
      }
      if (this.mAdapterUpdateDuringMeasure)
      {
        eatRequestLayout();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        onExitLayoutOrScroll();
        if (this.mState.mRunPredictiveAnimations)
        {
          this.mState.mInPreLayout = true;
        }
        else
        {
          this.mAdapterHelper.e();
          this.mState.mInPreLayout = false;
        }
        this.mAdapterUpdateDuringMeasure = false;
        resumeRequestLayout(false);
      }
      else if (this.mState.mRunPredictiveAnimations)
      {
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        return;
      }
      localObject = this.mAdapter;
      if (localObject != null) {
        this.mState.mItemCount = ((Adapter)localObject).getItemCount();
      } else {
        this.mState.mItemCount = 0;
      }
      eatRequestLayout();
      this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
      resumeRequestLayout(false);
      this.mState.mInPreLayout = false;
    }
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    if (isComputingLayout()) {
      return false;
    }
    return super.onRequestFocusInDescendants(paramInt, paramRect);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    this.mPendingSavedState = ((SavedState)paramParcelable);
    super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
    if ((this.mLayout != null) && (this.mPendingSavedState.mLayoutState != null)) {
      this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    Object localObject = this.mPendingSavedState;
    if (localObject != null)
    {
      localSavedState.copyFrom((SavedState)localObject);
    }
    else
    {
      localObject = this.mLayout;
      if (localObject != null) {
        localSavedState.mLayoutState = ((LayoutManager)localObject).onSaveInstanceState();
      } else {
        localSavedState.mLayoutState = null;
      }
    }
    return localSavedState;
  }
  
  public void onScrollStateChanged(int paramInt) {}
  
  public void onScrolled(int paramInt1, int paramInt2) {}
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4)) {
      invalidateGlows();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mLayoutFrozen;
    int i = 0;
    if ((!bool1) && (!this.mIgnoreMotionEventTillDown))
    {
      if (dispatchOnItemTouch(paramMotionEvent))
      {
        cancelTouch();
        return true;
      }
      Object localObject = this.mLayout;
      if (localObject == null) {
        return false;
      }
      bool1 = ((LayoutManager)localObject).canScrollHorizontally();
      boolean bool2 = this.mLayout.canScrollVertically();
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      localObject = MotionEvent.obtain(paramMotionEvent);
      int j = paramMotionEvent.getActionMasked();
      int k = paramMotionEvent.getActionIndex();
      if (j == 0)
      {
        arrayOfInt = this.mNestedOffsets;
        arrayOfInt[1] = 0;
        arrayOfInt[0] = 0;
      }
      int[] arrayOfInt = this.mNestedOffsets;
      ((MotionEvent)localObject).offsetLocation(arrayOfInt[0], arrayOfInt[1]);
      switch (j)
      {
      case 4: 
      default: 
        k = i;
        break;
      case 6: 
        onPointerUp(paramMotionEvent);
        k = i;
        break;
      case 5: 
        this.mScrollPointerId = paramMotionEvent.getPointerId(k);
        j = (int)(paramMotionEvent.getX(k) + 0.5F);
        this.mLastTouchX = j;
        this.mInitialTouchX = j;
        k = (int)(paramMotionEvent.getY(k) + 0.5F);
        this.mLastTouchY = k;
        this.mInitialTouchY = k;
        k = i;
        break;
      case 3: 
        cancelTouch();
        k = i;
        break;
      case 2: 
        k = paramMotionEvent.findPointerIndex(this.mScrollPointerId);
        if (k < 0)
        {
          paramMotionEvent = new StringBuilder();
          paramMotionEvent.append("Error processing scroll; pointer index for id ");
          paramMotionEvent.append(this.mScrollPointerId);
          paramMotionEvent.append(" not found. Did any MotionEvents get skipped?");
          Log.e("RecyclerView", paramMotionEvent.toString());
          return false;
        }
        int m = (int)(paramMotionEvent.getX(k) + 0.5F);
        int n = (int)(paramMotionEvent.getY(k) + 0.5F);
        int i1 = this.mLastTouchX - m;
        int i2 = this.mLastTouchY - n;
        j = i1;
        k = i2;
        if (dispatchNestedPreScroll(i1, i2, this.mScrollConsumed, this.mScrollOffset, 0))
        {
          paramMotionEvent = this.mScrollConsumed;
          j = i1 - paramMotionEvent[0];
          k = i2 - paramMotionEvent[1];
          paramMotionEvent = this.mScrollOffset;
          ((MotionEvent)localObject).offsetLocation(paramMotionEvent[0], paramMotionEvent[1]);
          paramMotionEvent = this.mNestedOffsets;
          i2 = paramMotionEvent[0];
          arrayOfInt = this.mScrollOffset;
          paramMotionEvent[0] = (i2 + arrayOfInt[0]);
          paramMotionEvent[1] += arrayOfInt[1];
        }
        i2 = j;
        i1 = k;
        if (this.mScrollState != 1)
        {
          if (bool1)
          {
            i2 = Math.abs(j);
            i1 = this.mTouchSlop;
            if (i2 > i1)
            {
              if (j > 0) {
                j -= i1;
              } else {
                j += i1;
              }
              i2 = 1;
              break label552;
            }
          }
          i2 = 0;
          int i3 = i2;
          int i4 = k;
          if (bool2)
          {
            int i5 = Math.abs(k);
            i1 = this.mTouchSlop;
            i3 = i2;
            i4 = k;
            if (i5 > i1)
            {
              if (k > 0) {
                i4 = k - i1;
              } else {
                i4 = k + i1;
              }
              i3 = 1;
            }
          }
          i2 = j;
          i1 = i4;
          if (i3 != 0)
          {
            setScrollState(1);
            i1 = i4;
            i2 = j;
          }
        }
        k = i;
        if (this.mScrollState == 1)
        {
          paramMotionEvent = this.mScrollOffset;
          this.mLastTouchX = (m - paramMotionEvent[0]);
          this.mLastTouchY = (n - paramMotionEvent[1]);
          if (bool1) {
            k = i2;
          } else {
            k = 0;
          }
          if (bool2) {
            j = i1;
          } else {
            j = 0;
          }
          if (scrollByInternal(k, j, (MotionEvent)localObject)) {
            getParent().requestDisallowInterceptTouchEvent(true);
          }
          k = i;
          if (this.mGapWorker != null) {
            if (i2 == 0)
            {
              k = i;
              if (i1 == 0) {
                break;
              }
            }
            else
            {
              this.mGapWorker.a(this, i2, i1);
              k = i;
            }
          }
        }
        break;
      case 1: 
        this.mVelocityTracker.addMovement((MotionEvent)localObject);
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
        float f1;
        if (bool1) {
          f1 = -this.mVelocityTracker.getXVelocity(this.mScrollPointerId);
        } else {
          f1 = 0.0F;
        }
        float f2;
        if (bool2) {
          f2 = -this.mVelocityTracker.getYVelocity(this.mScrollPointerId);
        } else {
          f2 = 0.0F;
        }
        if (((f1 == 0.0F) && (f2 == 0.0F)) || (!fling((int)f1, (int)f2))) {
          setScrollState(0);
        }
        resetTouch();
        k = 1;
        break;
      case 0: 
        label552:
        this.mScrollPointerId = paramMotionEvent.getPointerId(0);
        k = (int)(paramMotionEvent.getX() + 0.5F);
        this.mLastTouchX = k;
        this.mInitialTouchX = k;
        k = (int)(paramMotionEvent.getY() + 0.5F);
        this.mLastTouchY = k;
        this.mInitialTouchY = k;
        if (bool1) {
          k = 1;
        } else {
          k = 0;
        }
        j = k;
        if (bool2) {
          j = k | 0x2;
        }
        startNestedScroll(j, 0);
        k = i;
      }
      if (k == 0) {
        this.mVelocityTracker.addMovement((MotionEvent)localObject);
      }
      ((MotionEvent)localObject).recycle();
      return true;
    }
    return false;
  }
  
  void postAnimationRunner()
  {
    if ((!this.mPostedAnimatorRunner) && (this.mIsAttached))
    {
      ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
      this.mPostedAnimatorRunner = true;
    }
  }
  
  void recordAnimationInfoIfBouncedHiddenView(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    paramViewHolder.setFlags(0, 8192);
    if ((this.mState.mTrackOldChangeHolders) && (paramViewHolder.isUpdated()) && (!paramViewHolder.isRemoved()) && (!paramViewHolder.shouldIgnore()))
    {
      long l = getChangedHolderKey(paramViewHolder);
      this.mViewInfoStore.a(l, paramViewHolder);
    }
    this.mViewInfoStore.a(paramViewHolder, paramItemHolderInfo);
  }
  
  void removeAndRecycleViews()
  {
    Object localObject = this.mItemAnimator;
    if (localObject != null) {
      ((ItemAnimator)localObject).endAnimations();
    }
    localObject = this.mLayout;
    if (localObject != null)
    {
      ((LayoutManager)localObject).removeAndRecycleAllViews(this.mRecycler);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
    }
    this.mRecycler.clear();
  }
  
  boolean removeAnimatingView(View paramView)
  {
    eatRequestLayout();
    boolean bool = this.mChildHelper.f(paramView);
    if (bool)
    {
      paramView = getChildViewHolderInt(paramView);
      this.mRecycler.unscrapView(paramView);
      this.mRecycler.recycleViewHolderInternal(paramView);
    }
    resumeRequestLayout(bool ^ true);
    return bool;
  }
  
  protected void removeDetachedView(View paramView, boolean paramBoolean)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null) {
      if (localViewHolder.isTmpDetached())
      {
        localViewHolder.clearTmpDetachFlag();
      }
      else if (!localViewHolder.shouldIgnore())
      {
        paramView = new StringBuilder();
        paramView.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
        paramView.append(localViewHolder);
        paramView.append(exceptionLabel());
        throw new IllegalArgumentException(paramView.toString());
      }
    }
    paramView.clearAnimation();
    dispatchChildDetached(paramView);
    super.removeDetachedView(paramView, paramBoolean);
  }
  
  public void removeItemDecoration(ItemDecoration paramItemDecoration)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
    }
    this.mItemDecorations.remove(paramItemDecoration);
    if (this.mItemDecorations.isEmpty())
    {
      boolean bool;
      if (getOverScrollMode() == 2) {
        bool = true;
      } else {
        bool = false;
      }
      setWillNotDraw(bool);
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    List localList = this.mOnChildAttachStateListeners;
    if (localList == null) {
      return;
    }
    localList.remove(paramOnChildAttachStateChangeListener);
  }
  
  public void removeOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    this.mOnItemTouchListeners.remove(paramOnItemTouchListener);
    if (this.mActiveOnItemTouchListener == paramOnItemTouchListener) {
      this.mActiveOnItemTouchListener = null;
    }
  }
  
  public void removeOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    List localList = this.mScrollListeners;
    if (localList != null) {
      localList.remove(paramOnScrollListener);
    }
  }
  
  void repositionShadowingViews()
  {
    int i = this.mChildHelper.b();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mChildHelper.b(j);
      Object localObject = getChildViewHolder(localView);
      if ((localObject != null) && (((ViewHolder)localObject).mShadowingHolder != null))
      {
        localObject = ((ViewHolder)localObject).mShadowingHolder.itemView;
        int k = localView.getLeft();
        int m = localView.getTop();
        if ((k != ((View)localObject).getLeft()) || (m != ((View)localObject).getTop())) {
          ((View)localObject).layout(k, m, ((View)localObject).getWidth() + k, ((View)localObject).getHeight() + m);
        }
      }
    }
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    if ((!this.mLayout.onRequestChildFocus(this, this.mState, paramView1, paramView2)) && (paramView2 != null)) {
      requestChildOnScreen(paramView1, paramView2);
    }
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    return this.mLayout.requestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    int i = this.mOnItemTouchListeners.size();
    for (int j = 0; j < i; j++) {
      ((OnItemTouchListener)this.mOnItemTouchListeners.get(j)).onRequestDisallowInterceptTouchEvent(paramBoolean);
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    if ((this.mEatRequestLayout == 0) && (!this.mLayoutFrozen)) {
      super.requestLayout();
    } else {
      this.mLayoutRequestEaten = true;
    }
  }
  
  void resumeRequestLayout(boolean paramBoolean)
  {
    if (this.mEatRequestLayout < 1) {
      this.mEatRequestLayout = 1;
    }
    if (!paramBoolean) {
      this.mLayoutRequestEaten = false;
    }
    if (this.mEatRequestLayout == 1)
    {
      if ((paramBoolean) && (this.mLayoutRequestEaten) && (!this.mLayoutFrozen) && (this.mLayout != null) && (this.mAdapter != null)) {
        dispatchLayout();
      }
      if (!this.mLayoutFrozen) {
        this.mLayoutRequestEaten = false;
      }
    }
    this.mEatRequestLayout -= 1;
  }
  
  void saveOldPositions()
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.d(j));
      if (!localViewHolder.shouldIgnore()) {
        localViewHolder.saveOldPosition();
      }
    }
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    if (this.mLayoutFrozen) {
      return;
    }
    boolean bool1 = localLayoutManager.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if ((bool1) || (bool2))
    {
      if (!bool1) {
        paramInt1 = 0;
      }
      if (!bool2) {
        paramInt2 = 0;
      }
      scrollByInternal(paramInt1, paramInt2, null);
    }
  }
  
  boolean scrollByInternal(int paramInt1, int paramInt2, MotionEvent paramMotionEvent)
  {
    consumePendingUpdateOperations();
    Object localObject = this.mAdapter;
    boolean bool = false;
    int i;
    int j;
    int k;
    int m;
    if (localObject != null)
    {
      eatRequestLayout();
      onEnterLayoutOrScroll();
      TraceCompat.beginSection("RV Scroll");
      fillRemainingScrollValues(this.mState);
      if (paramInt1 != 0)
      {
        i = this.mLayout.scrollHorizontallyBy(paramInt1, this.mRecycler, this.mState);
        j = paramInt1 - i;
      }
      else
      {
        i = 0;
        j = 0;
      }
      if (paramInt2 != 0)
      {
        k = this.mLayout.scrollVerticallyBy(paramInt2, this.mRecycler, this.mState);
        m = paramInt2 - k;
      }
      else
      {
        k = 0;
        m = 0;
      }
      TraceCompat.endSection();
      repositionShadowingViews();
      onExitLayoutOrScroll();
      resumeRequestLayout(false);
    }
    else
    {
      i = 0;
      j = 0;
      k = 0;
      m = 0;
    }
    if (!this.mItemDecorations.isEmpty()) {
      invalidate();
    }
    if (dispatchNestedScroll(i, k, j, m, this.mScrollOffset, 0))
    {
      paramInt1 = this.mLastTouchX;
      localObject = this.mScrollOffset;
      this.mLastTouchX = (paramInt1 - localObject[0]);
      this.mLastTouchY -= localObject[1];
      if (paramMotionEvent != null) {
        paramMotionEvent.offsetLocation(localObject[0], localObject[1]);
      }
      paramMotionEvent = this.mNestedOffsets;
      paramInt1 = paramMotionEvent[0];
      localObject = this.mScrollOffset;
      paramMotionEvent[0] = (paramInt1 + localObject[0]);
      paramMotionEvent[1] += localObject[1];
    }
    else if (getOverScrollMode() != 2)
    {
      if ((paramMotionEvent != null) && (!MotionEventCompat.isFromSource(paramMotionEvent, 8194))) {
        pullGlows(paramMotionEvent.getX(), j, paramMotionEvent.getY(), m);
      }
      considerReleasingGlowsOnScroll(paramInt1, paramInt2);
    }
    if ((i != 0) || (k != 0)) {
      dispatchOnScrolled(i, k);
    }
    if (!awakenScrollBars()) {
      invalidate();
    }
    if ((i != 0) || (k != 0)) {
      bool = true;
    }
    return bool;
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
  }
  
  public void scrollToPosition(int paramInt)
  {
    if (this.mLayoutFrozen) {
      return;
    }
    stopScroll();
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    localLayoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent)
  {
    if (shouldDeferAccessibilityEvent(paramAccessibilityEvent)) {
      return;
    }
    super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
  }
  
  public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate paramRecyclerViewAccessibilityDelegate)
  {
    this.mAccessibilityDelegate = paramRecyclerViewAccessibilityDelegate;
    ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
  }
  
  public void setAdapter(Adapter paramAdapter)
  {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, false, true);
    requestLayout();
  }
  
  public void setChildDrawingOrderCallback(ChildDrawingOrderCallback paramChildDrawingOrderCallback)
  {
    if (paramChildDrawingOrderCallback == this.mChildDrawingOrderCallback) {
      return;
    }
    this.mChildDrawingOrderCallback = paramChildDrawingOrderCallback;
    boolean bool;
    if (this.mChildDrawingOrderCallback != null) {
      bool = true;
    } else {
      bool = false;
    }
    setChildrenDrawingOrderEnabled(bool);
  }
  
  boolean setChildImportantForAccessibilityInternal(ViewHolder paramViewHolder, int paramInt)
  {
    if (isComputingLayout())
    {
      paramViewHolder.mPendingAccessibilityState = paramInt;
      this.mPendingAccessibilityImportanceChange.add(paramViewHolder);
      return false;
    }
    ViewCompat.setImportantForAccessibility(paramViewHolder.itemView, paramInt);
    return true;
  }
  
  public void setClipToPadding(boolean paramBoolean)
  {
    if (paramBoolean != this.mClipToPadding) {
      invalidateGlows();
    }
    this.mClipToPadding = paramBoolean;
    super.setClipToPadding(paramBoolean);
    if (this.mFirstLayoutComplete) {
      requestLayout();
    }
  }
  
  void setDataSetChangedAfterLayout()
  {
    this.mDataSetHasChangedAfterLayout = true;
    markKnownViewsInvalid();
  }
  
  public void setHasFixedSize(boolean paramBoolean)
  {
    this.mHasFixedSize = paramBoolean;
  }
  
  public void setItemAnimator(ItemAnimator paramItemAnimator)
  {
    ItemAnimator localItemAnimator = this.mItemAnimator;
    if (localItemAnimator != null)
    {
      localItemAnimator.endAnimations();
      this.mItemAnimator.setListener(null);
    }
    this.mItemAnimator = paramItemAnimator;
    paramItemAnimator = this.mItemAnimator;
    if (paramItemAnimator != null) {
      paramItemAnimator.setListener(this.mItemAnimatorListener);
    }
  }
  
  public void setItemViewCacheSize(int paramInt)
  {
    this.mRecycler.setViewCacheSize(paramInt);
  }
  
  public void setLayoutFrozen(boolean paramBoolean)
  {
    if (paramBoolean != this.mLayoutFrozen)
    {
      assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
      if (!paramBoolean)
      {
        this.mLayoutFrozen = false;
        if ((this.mLayoutRequestEaten) && (this.mLayout != null) && (this.mAdapter != null)) {
          requestLayout();
        }
        this.mLayoutRequestEaten = false;
      }
      else
      {
        long l = SystemClock.uptimeMillis();
        onTouchEvent(MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0));
        this.mLayoutFrozen = true;
        this.mIgnoreMotionEventTillDown = true;
        stopScroll();
      }
    }
  }
  
  public void setLayoutManager(LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager == this.mLayout) {
      return;
    }
    stopScroll();
    Object localObject;
    if (this.mLayout != null)
    {
      localObject = this.mItemAnimator;
      if (localObject != null) {
        ((ItemAnimator)localObject).endAnimations();
      }
      this.mLayout.removeAndRecycleAllViews(this.mRecycler);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      this.mRecycler.clear();
      if (this.mIsAttached) {
        this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
      }
      this.mLayout.setRecyclerView(null);
      this.mLayout = null;
    }
    else
    {
      this.mRecycler.clear();
    }
    this.mChildHelper.a();
    this.mLayout = paramLayoutManager;
    if (paramLayoutManager != null) {
      if (paramLayoutManager.mRecyclerView == null)
      {
        this.mLayout.setRecyclerView(this);
        if (this.mIsAttached) {
          this.mLayout.dispatchAttachedToWindow(this);
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("LayoutManager ");
        ((StringBuilder)localObject).append(paramLayoutManager);
        ((StringBuilder)localObject).append(" is already attached to a RecyclerView:");
        ((StringBuilder)localObject).append(paramLayoutManager.mRecyclerView.exceptionLabel());
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
    }
    this.mRecycler.updateViewCacheSize();
    requestLayout();
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    getScrollingChildHelper().setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnFlingListener(OnFlingListener paramOnFlingListener)
  {
    this.mOnFlingListener = paramOnFlingListener;
  }
  
  @Deprecated
  public void setOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    this.mScrollListener = paramOnScrollListener;
  }
  
  public void setPreserveFocusAfterLayout(boolean paramBoolean)
  {
    this.mPreserveFocusAfterLayout = paramBoolean;
  }
  
  public void setRecycledViewPool(RecycledViewPool paramRecycledViewPool)
  {
    this.mRecycler.setRecycledViewPool(paramRecycledViewPool);
  }
  
  public void setRecyclerListener(RecyclerListener paramRecyclerListener)
  {
    this.mRecyclerListener = paramRecyclerListener;
  }
  
  void setScrollState(int paramInt)
  {
    if (paramInt == this.mScrollState) {
      return;
    }
    this.mScrollState = paramInt;
    if (paramInt != 2) {
      stopScrollersInternal();
    }
    dispatchOnScrollStateChanged(paramInt);
  }
  
  public void setScrollingTouchSlop(int paramInt)
  {
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append("; using default value");
      Log.w("RecyclerView", localStringBuilder.toString());
      break;
    case 1: 
      this.mTouchSlop = localViewConfiguration.getScaledPagingTouchSlop();
      break;
    }
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
  }
  
  public void setViewCacheExtension(ViewCacheExtension paramViewCacheExtension)
  {
    this.mRecycler.setViewCacheExtension(paramViewCacheExtension);
  }
  
  boolean shouldDeferAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (isComputingLayout())
    {
      int i;
      if (paramAccessibilityEvent != null) {
        i = AccessibilityEventCompat.getContentChangeTypes(paramAccessibilityEvent);
      } else {
        i = 0;
      }
      int j = i;
      if (i == 0) {
        j = 0;
      }
      this.mEatenAccessibilityChangeFlags = (j | this.mEatenAccessibilityChangeFlags);
      return true;
    }
    return false;
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2)
  {
    smoothScrollBy(paramInt1, paramInt2, null);
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2, Interpolator paramInterpolator)
  {
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    if (this.mLayoutFrozen) {
      return;
    }
    if (!localLayoutManager.canScrollHorizontally()) {
      paramInt1 = 0;
    }
    if (!this.mLayout.canScrollVertically()) {
      paramInt2 = 0;
    }
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      this.mViewFlinger.a(paramInt1, paramInt2, paramInterpolator);
    }
  }
  
  public void smoothScrollToPosition(int paramInt)
  {
    if (this.mLayoutFrozen) {
      return;
    }
    LayoutManager localLayoutManager = this.mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    localLayoutManager.smoothScrollToPosition(this, this.mState, paramInt);
  }
  
  public boolean startNestedScroll(int paramInt)
  {
    return getScrollingChildHelper().startNestedScroll(paramInt);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2)
  {
    return getScrollingChildHelper().startNestedScroll(paramInt1, paramInt2);
  }
  
  public void stopNestedScroll()
  {
    getScrollingChildHelper().stopNestedScroll();
  }
  
  public void stopNestedScroll(int paramInt)
  {
    getScrollingChildHelper().stopNestedScroll(paramInt);
  }
  
  public void stopScroll()
  {
    setScrollState(0);
    stopScrollersInternal();
  }
  
  public void swapAdapter(Adapter paramAdapter, boolean paramBoolean)
  {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, true, paramBoolean);
    requestLayout();
  }
  
  void viewRangeUpdate(int paramInt1, int paramInt2, Object paramObject)
  {
    int i = this.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mChildHelper.d(j);
      ViewHolder localViewHolder = getChildViewHolderInt(localView);
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (localViewHolder.mPosition >= paramInt1) && (localViewHolder.mPosition < paramInt1 + paramInt2))
      {
        localViewHolder.addFlags(2);
        localViewHolder.addChangePayload(paramObject);
        ((LayoutParams)localView.getLayoutParams()).mInsetsDirty = true;
      }
    }
    this.mRecycler.viewRangeUpdate(paramInt1, paramInt2);
  }
  
  public static abstract class Adapter<VH extends RecyclerView.ViewHolder>
  {
    private boolean mHasStableIds = false;
    private final RecyclerView.a mObservable = new RecyclerView.a();
    
    public final void bindViewHolder(VH paramVH, int paramInt)
    {
      paramVH.mPosition = paramInt;
      if (hasStableIds()) {
        paramVH.mItemId = getItemId(paramInt);
      }
      paramVH.setFlags(1, 519);
      TraceCompat.beginSection("RV OnBindView");
      onBindViewHolder(paramVH, paramInt, paramVH.getUnmodifiedPayloads());
      paramVH.clearPayload();
      paramVH = paramVH.itemView.getLayoutParams();
      if ((paramVH instanceof RecyclerView.LayoutParams)) {
        ((RecyclerView.LayoutParams)paramVH).mInsetsDirty = true;
      }
      TraceCompat.endSection();
    }
    
    public final VH createViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      TraceCompat.beginSection("RV CreateView");
      paramViewGroup = onCreateViewHolder(paramViewGroup, paramInt);
      paramViewGroup.mItemViewType = paramInt;
      TraceCompat.endSection();
      return paramViewGroup;
    }
    
    public abstract int getItemCount();
    
    public long getItemId(int paramInt)
    {
      return -1L;
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public final boolean hasObservers()
    {
      return this.mObservable.a();
    }
    
    public final boolean hasStableIds()
    {
      return this.mHasStableIds;
    }
    
    public final void notifyDataSetChanged()
    {
      this.mObservable.b();
    }
    
    public final void notifyItemChanged(int paramInt)
    {
      this.mObservable.a(paramInt, 1);
    }
    
    public final void notifyItemChanged(int paramInt, Object paramObject)
    {
      this.mObservable.a(paramInt, 1, paramObject);
    }
    
    public final void notifyItemInserted(int paramInt)
    {
      this.mObservable.b(paramInt, 1);
    }
    
    public final void notifyItemMoved(int paramInt1, int paramInt2)
    {
      this.mObservable.d(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeChanged(int paramInt1, int paramInt2)
    {
      this.mObservable.a(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      this.mObservable.a(paramInt1, paramInt2, paramObject);
    }
    
    public final void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      this.mObservable.b(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeRemoved(int paramInt1, int paramInt2)
    {
      this.mObservable.c(paramInt1, paramInt2);
    }
    
    public final void notifyItemRemoved(int paramInt)
    {
      this.mObservable.c(paramInt, 1);
    }
    
    public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {}
    
    public abstract void onBindViewHolder(VH paramVH, int paramInt);
    
    public void onBindViewHolder(VH paramVH, int paramInt, List<Object> paramList)
    {
      onBindViewHolder(paramVH, paramInt);
    }
    
    public abstract VH onCreateViewHolder(ViewGroup paramViewGroup, int paramInt);
    
    public void onDetachedFromRecyclerView(RecyclerView paramRecyclerView) {}
    
    public boolean onFailedToRecycleView(VH paramVH)
    {
      return false;
    }
    
    public void onViewAttachedToWindow(VH paramVH) {}
    
    public void onViewDetachedFromWindow(VH paramVH) {}
    
    public void onViewRecycled(VH paramVH) {}
    
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.registerObserver(paramAdapterDataObserver);
    }
    
    public void setHasStableIds(boolean paramBoolean)
    {
      if (!hasObservers())
      {
        this.mHasStableIds = paramBoolean;
        return;
      }
      throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
    }
    
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.unregisterObserver(paramAdapterDataObserver);
    }
  }
  
  public static abstract class AdapterDataObserver
  {
    public void onChanged() {}
    
    public void onItemRangeChanged(int paramInt1, int paramInt2) {}
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      onItemRangeChanged(paramInt1, paramInt2);
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2) {}
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3) {}
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2) {}
  }
  
  public static abstract interface ChildDrawingOrderCallback
  {
    public abstract int onGetChildDrawingOrder(int paramInt1, int paramInt2);
  }
  
  public static abstract class ItemAnimator
  {
    public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    public static final int FLAG_CHANGED = 2;
    public static final int FLAG_INVALIDATED = 4;
    public static final int FLAG_MOVED = 2048;
    public static final int FLAG_REMOVED = 8;
    private long mAddDuration = 120L;
    private long mChangeDuration = 250L;
    private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
    private a mListener = null;
    private long mMoveDuration = 250L;
    private long mRemoveDuration = 120L;
    
    static int buildAdapterChangeFlagsForAnimations(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = RecyclerView.ViewHolder.access$1600(paramViewHolder) & 0xE;
      if (paramViewHolder.isInvalid()) {
        return 4;
      }
      int j = i;
      if ((i & 0x4) == 0)
      {
        int k = paramViewHolder.getOldPosition();
        int m = paramViewHolder.getAdapterPosition();
        j = i;
        if (k != -1)
        {
          j = i;
          if (m != -1)
          {
            j = i;
            if (k != m) {
              j = i | 0x800;
            }
          }
        }
      }
      return j;
    }
    
    public abstract boolean animateAppearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateDisappearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animatePersistence(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder)
    {
      return true;
    }
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder, List<Object> paramList)
    {
      return canReuseUpdatedViewHolder(paramViewHolder);
    }
    
    public final void dispatchAnimationFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      onAnimationFinished(paramViewHolder);
      a locala = this.mListener;
      if (locala != null) {
        locala.a(paramViewHolder);
      }
    }
    
    public final void dispatchAnimationStarted(RecyclerView.ViewHolder paramViewHolder)
    {
      onAnimationStarted(paramViewHolder);
    }
    
    public final void dispatchAnimationsFinished()
    {
      int i = this.mFinishedListeners.size();
      for (int j = 0; j < i; j++) {
        ((ItemAnimatorFinishedListener)this.mFinishedListeners.get(j)).onAnimationsFinished();
      }
      this.mFinishedListeners.clear();
    }
    
    public abstract void endAnimation(RecyclerView.ViewHolder paramViewHolder);
    
    public abstract void endAnimations();
    
    public long getAddDuration()
    {
      return this.mAddDuration;
    }
    
    public long getChangeDuration()
    {
      return this.mChangeDuration;
    }
    
    public long getMoveDuration()
    {
      return this.mMoveDuration;
    }
    
    public long getRemoveDuration()
    {
      return this.mRemoveDuration;
    }
    
    public abstract boolean isRunning();
    
    public final boolean isRunning(ItemAnimatorFinishedListener paramItemAnimatorFinishedListener)
    {
      boolean bool = isRunning();
      if (paramItemAnimatorFinishedListener != null) {
        if (!bool) {
          paramItemAnimatorFinishedListener.onAnimationsFinished();
        } else {
          this.mFinishedListeners.add(paramItemAnimatorFinishedListener);
        }
      }
      return bool;
    }
    
    public ItemHolderInfo obtainHolderInfo()
    {
      return new ItemHolderInfo();
    }
    
    public void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder) {}
    
    public void onAnimationStarted(RecyclerView.ViewHolder paramViewHolder) {}
    
    public ItemHolderInfo recordPostLayoutInformation(RecyclerView.State paramState, RecyclerView.ViewHolder paramViewHolder)
    {
      return obtainHolderInfo().setFrom(paramViewHolder);
    }
    
    public ItemHolderInfo recordPreLayoutInformation(RecyclerView.State paramState, RecyclerView.ViewHolder paramViewHolder, int paramInt, List<Object> paramList)
    {
      return obtainHolderInfo().setFrom(paramViewHolder);
    }
    
    public abstract void runPendingAnimations();
    
    public void setAddDuration(long paramLong)
    {
      this.mAddDuration = paramLong;
    }
    
    public void setChangeDuration(long paramLong)
    {
      this.mChangeDuration = paramLong;
    }
    
    void setListener(a parama)
    {
      this.mListener = parama;
    }
    
    public void setMoveDuration(long paramLong)
    {
      this.mMoveDuration = paramLong;
    }
    
    public void setRemoveDuration(long paramLong)
    {
      this.mRemoveDuration = paramLong;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface AdapterChanges {}
    
    public static abstract interface ItemAnimatorFinishedListener
    {
      public abstract void onAnimationsFinished();
    }
    
    public static class ItemHolderInfo
    {
      public int bottom;
      public int changeFlags;
      public int left;
      public int right;
      public int top;
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder paramViewHolder)
      {
        return setFrom(paramViewHolder, 0);
      }
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder paramViewHolder, int paramInt)
      {
        paramViewHolder = paramViewHolder.itemView;
        this.left = paramViewHolder.getLeft();
        this.top = paramViewHolder.getTop();
        this.right = paramViewHolder.getRight();
        this.bottom = paramViewHolder.getBottom();
        return this;
      }
    }
    
    static abstract interface a
    {
      public abstract void a(RecyclerView.ViewHolder paramViewHolder);
    }
  }
  
  public static abstract class ItemDecoration
  {
    @Deprecated
    public void getItemOffsets(Rect paramRect, int paramInt, RecyclerView paramRecyclerView)
    {
      paramRect.set(0, 0, 0, 0);
    }
    
    public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      getItemOffsets(paramRect, ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition(), paramRecyclerView);
    }
    
    @Deprecated
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
    
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDraw(paramCanvas, paramRecyclerView);
    }
    
    @Deprecated
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
    
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDrawOver(paramCanvas, paramRecyclerView);
    }
  }
  
  public static abstract class LayoutManager
  {
    boolean mAutoMeasure = false;
    r mChildHelper;
    private int mHeight;
    private int mHeightMode;
    ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
    private final ViewBoundsCheck.b mHorizontalBoundCheckCallback = new ViewBoundsCheck.b()
    {
      public int a()
      {
        return RecyclerView.LayoutManager.this.getPaddingLeft();
      }
      
      public int a(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return RecyclerView.LayoutManager.this.getDecoratedLeft(paramAnonymousView) - localLayoutParams.leftMargin;
      }
      
      public View a(int paramAnonymousInt)
      {
        return RecyclerView.LayoutManager.this.getChildAt(paramAnonymousInt);
      }
      
      public int b()
      {
        return RecyclerView.LayoutManager.this.getWidth() - RecyclerView.LayoutManager.this.getPaddingRight();
      }
      
      public int b(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return RecyclerView.LayoutManager.this.getDecoratedRight(paramAnonymousView) + localLayoutParams.rightMargin;
      }
    };
    boolean mIsAttachedToWindow = false;
    private boolean mItemPrefetchEnabled = true;
    private boolean mMeasurementCacheEnabled = true;
    int mPrefetchMaxCountObserved;
    boolean mPrefetchMaxObservedInInitialPrefetch;
    RecyclerView mRecyclerView;
    boolean mRequestedSimpleAnimations = false;
    RecyclerView.SmoothScroller mSmoothScroller;
    ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
    private final ViewBoundsCheck.b mVerticalBoundCheckCallback = new ViewBoundsCheck.b()
    {
      public int a()
      {
        return RecyclerView.LayoutManager.this.getPaddingTop();
      }
      
      public int a(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return RecyclerView.LayoutManager.this.getDecoratedTop(paramAnonymousView) - localLayoutParams.topMargin;
      }
      
      public View a(int paramAnonymousInt)
      {
        return RecyclerView.LayoutManager.this.getChildAt(paramAnonymousInt);
      }
      
      public int b()
      {
        return RecyclerView.LayoutManager.this.getHeight() - RecyclerView.LayoutManager.this.getPaddingBottom();
      }
      
      public int b(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return RecyclerView.LayoutManager.this.getDecoratedBottom(paramAnonymousView) + localLayoutParams.bottomMargin;
      }
    };
    private int mWidth;
    private int mWidthMode;
    
    private void addViewInt(View paramView, int paramInt, boolean paramBoolean)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((!paramBoolean) && (!localViewHolder.isRemoved())) {
        this.mRecyclerView.mViewInfoStore.f(localViewHolder);
      } else {
        this.mRecyclerView.mViewInfoStore.e(localViewHolder);
      }
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      if ((!localViewHolder.wasReturnedFromScrap()) && (!localViewHolder.isScrap()))
      {
        Object localObject;
        if (paramView.getParent() == this.mRecyclerView)
        {
          int i = this.mChildHelper.b(paramView);
          int j = paramInt;
          if (paramInt == -1) {
            j = this.mChildHelper.b();
          }
          if (i != -1)
          {
            if (i != j) {
              this.mRecyclerView.mLayout.moveView(i, j);
            }
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
            ((StringBuilder)localObject).append(this.mRecyclerView.indexOfChild(paramView));
            ((StringBuilder)localObject).append(this.mRecyclerView.exceptionLabel());
            throw new IllegalStateException(((StringBuilder)localObject).toString());
          }
        }
        else
        {
          this.mChildHelper.a(paramView, paramInt, false);
          localLayoutParams.mInsetsDirty = true;
          localObject = this.mSmoothScroller;
          if ((localObject != null) && (((RecyclerView.SmoothScroller)localObject).isRunning())) {
            this.mSmoothScroller.onChildAttachedToWindow(paramView);
          }
        }
      }
      else
      {
        if (localViewHolder.isScrap()) {
          localViewHolder.unScrap();
        } else {
          localViewHolder.clearReturnedFromScrapFlag();
        }
        this.mChildHelper.a(paramView, paramInt, paramView.getLayoutParams(), false);
      }
      if (localLayoutParams.mPendingInvalidate)
      {
        localViewHolder.itemView.invalidate();
        localLayoutParams.mPendingInvalidate = false;
      }
    }
    
    public static int chooseSize(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      paramInt1 = View.MeasureSpec.getSize(paramInt1);
      if (i != Integer.MIN_VALUE)
      {
        if (i != 1073741824) {
          return Math.max(paramInt2, paramInt3);
        }
        return paramInt1;
      }
      return Math.min(paramInt1, Math.max(paramInt2, paramInt3));
    }
    
    private void detachViewInternal(int paramInt, View paramView)
    {
      this.mChildHelper.e(paramInt);
    }
    
    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    {
      int i = 0;
      int j = Math.max(0, paramInt1 - paramInt3);
      if (paramBoolean)
      {
        if (paramInt4 >= 0)
        {
          paramInt1 = paramInt4;
          paramInt3 = 1073741824;
          break label149;
        }
        if (paramInt4 == -1)
        {
          if (paramInt2 != Integer.MIN_VALUE) {
            if (paramInt2 != 0)
            {
              if (paramInt2 != 1073741824)
              {
                paramInt2 = 0;
                paramInt1 = 0;
                break label67;
              }
            }
            else
            {
              paramInt2 = 0;
              paramInt1 = 0;
              break label67;
            }
          }
          paramInt1 = j;
          label67:
          paramInt3 = paramInt2;
          break label149;
        }
        if (paramInt4 == -2)
        {
          paramInt1 = 0;
          paramInt3 = i;
          break label149;
        }
      }
      else
      {
        if (paramInt4 >= 0)
        {
          paramInt1 = paramInt4;
          paramInt3 = 1073741824;
          break label149;
        }
        if (paramInt4 == -1)
        {
          paramInt1 = j;
          paramInt3 = paramInt2;
          break label149;
        }
        if (paramInt4 == -2)
        {
          if (paramInt2 != Integer.MIN_VALUE)
          {
            paramInt1 = j;
            paramInt3 = i;
            if (paramInt2 != 1073741824) {
              break label149;
            }
          }
          paramInt3 = Integer.MIN_VALUE;
          paramInt1 = j;
          break label149;
        }
      }
      paramInt1 = 0;
      paramInt3 = i;
      label149:
      return View.MeasureSpec.makeMeasureSpec(paramInt1, paramInt3);
    }
    
    @Deprecated
    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int i = 0;
      paramInt1 = Math.max(0, paramInt1 - paramInt2);
      if (paramBoolean)
      {
        if (paramInt3 >= 0)
        {
          paramInt1 = paramInt3;
          paramInt2 = 1073741824;
        }
        else
        {
          paramInt1 = 0;
          paramInt2 = i;
        }
      }
      else if (paramInt3 >= 0)
      {
        paramInt1 = paramInt3;
        paramInt2 = 1073741824;
      }
      else if (paramInt3 == -1)
      {
        paramInt2 = 1073741824;
      }
      else if (paramInt3 == -2)
      {
        paramInt2 = Integer.MIN_VALUE;
      }
      else
      {
        paramInt1 = 0;
        paramInt2 = i;
      }
      return View.MeasureSpec.makeMeasureSpec(paramInt1, paramInt2);
    }
    
    private int[] getChildRectangleOnScreenScrollAmount(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean)
    {
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int m = getPaddingRight();
      int n = getHeight();
      int i1 = getPaddingBottom();
      int i2 = paramView.getLeft() + paramRect.left - paramView.getScrollX();
      int i3 = paramView.getTop() + paramRect.top - paramView.getScrollY();
      int i4 = paramRect.width();
      int i5 = paramRect.height();
      int i6 = i2 - i;
      i = Math.min(0, i6);
      int i7 = i3 - j;
      j = Math.min(0, i7);
      m = i4 + i2 - (k - m);
      i4 = Math.max(0, m);
      i1 = Math.max(0, i5 + i3 - (n - i1));
      if (getLayoutDirection() == 1)
      {
        if (i4 != 0) {
          i = i4;
        } else {
          i = Math.max(i, m);
        }
      }
      else if (i == 0) {
        i = Math.min(i6, i4);
      }
      if (j == 0) {
        j = Math.min(i7, i1);
      }
      return new int[] { i, j };
    }
    
    public static Properties getProperties(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
      Properties localProperties = new Properties();
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.c.RecyclerView, paramInt1, paramInt2);
      localProperties.orientation = paramContext.getInt(a.c.RecyclerView_android_orientation, 1);
      localProperties.spanCount = paramContext.getInt(a.c.RecyclerView_spanCount, 1);
      localProperties.reverseLayout = paramContext.getBoolean(a.c.RecyclerView_reverseLayout, false);
      localProperties.stackFromEnd = paramContext.getBoolean(a.c.RecyclerView_stackFromEnd, false);
      paramContext.recycle();
      return localProperties;
    }
    
    private boolean isFocusedChildVisibleAfterScrolling(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
      paramRecyclerView = paramRecyclerView.getFocusedChild();
      if (paramRecyclerView == null) {
        return false;
      }
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int m = getPaddingRight();
      int n = getHeight();
      int i1 = getPaddingBottom();
      Rect localRect = this.mRecyclerView.mTempRect;
      getDecoratedBoundsWithMargins(paramRecyclerView, localRect);
      return (localRect.left - paramInt1 < k - m) && (localRect.right - paramInt1 > i) && (localRect.top - paramInt2 < n - i1) && (localRect.bottom - paramInt2 > j);
    }
    
    private static boolean isMeasurementUpToDate(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = View.MeasureSpec.getMode(paramInt2);
      paramInt2 = View.MeasureSpec.getSize(paramInt2);
      boolean bool1 = false;
      boolean bool2 = false;
      if ((paramInt3 > 0) && (paramInt1 != paramInt3)) {
        return false;
      }
      if (i != Integer.MIN_VALUE)
      {
        if (i != 0)
        {
          if (i != 1073741824) {
            return false;
          }
          if (paramInt2 == paramInt1) {
            bool2 = true;
          }
          return bool2;
        }
        return true;
      }
      bool2 = bool1;
      if (paramInt2 >= paramInt1) {
        bool2 = true;
      }
      return bool2;
    }
    
    private void onSmoothScrollerStopped(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      if (this.mSmoothScroller == paramSmoothScroller) {
        this.mSmoothScroller = null;
      }
    }
    
    private void scrapOrRecycleView(RecyclerView.Recycler paramRecycler, int paramInt, View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.shouldIgnore()) {
        return;
      }
      if ((localViewHolder.isInvalid()) && (!localViewHolder.isRemoved()) && (!this.mRecyclerView.mAdapter.hasStableIds()))
      {
        removeViewAt(paramInt);
        paramRecycler.recycleViewHolderInternal(localViewHolder);
      }
      else
      {
        detachViewAt(paramInt);
        paramRecycler.scrapView(paramView);
        this.mRecyclerView.mViewInfoStore.h(localViewHolder);
      }
    }
    
    public void addDisappearingView(View paramView)
    {
      addDisappearingView(paramView, -1);
    }
    
    public void addDisappearingView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, true);
    }
    
    public void addView(View paramView)
    {
      addView(paramView, -1);
    }
    
    public void addView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, false);
    }
    
    public void assertInLayoutOrScroll(String paramString)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.assertInLayoutOrScroll(paramString);
      }
    }
    
    public void assertNotInLayoutOrScroll(String paramString)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.assertNotInLayoutOrScroll(paramString);
      }
    }
    
    public void attachView(View paramView)
    {
      attachView(paramView, -1);
    }
    
    public void attachView(View paramView, int paramInt)
    {
      attachView(paramView, paramInt, (RecyclerView.LayoutParams)paramView.getLayoutParams());
    }
    
    public void attachView(View paramView, int paramInt, RecyclerView.LayoutParams paramLayoutParams)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isRemoved()) {
        this.mRecyclerView.mViewInfoStore.e(localViewHolder);
      } else {
        this.mRecyclerView.mViewInfoStore.f(localViewHolder);
      }
      this.mChildHelper.a(paramView, paramInt, paramLayoutParams, localViewHolder.isRemoved());
    }
    
    public void calculateItemDecorationsForChild(View paramView, Rect paramRect)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView == null)
      {
        paramRect.set(0, 0, 0, 0);
        return;
      }
      paramRect.set(localRecyclerView.getItemDecorInsetsForChild(paramView));
    }
    
    public boolean canScrollHorizontally()
    {
      return false;
    }
    
    public boolean canScrollVertically()
    {
      return false;
    }
    
    public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      boolean bool;
      if (paramLayoutParams != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {}
    
    public void collectInitialPrefetchPositions(int paramInt, LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {}
    
    public int computeHorizontalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void detachAndScrapAttachedViews(RecyclerView.Recycler paramRecycler)
    {
      for (int i = getChildCount() - 1; i >= 0; i--) {
        scrapOrRecycleView(paramRecycler, i, getChildAt(i));
      }
    }
    
    public void detachAndScrapView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, this.mChildHelper.b(paramView), paramView);
    }
    
    public void detachAndScrapViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, paramInt, getChildAt(paramInt));
    }
    
    public void detachView(View paramView)
    {
      int i = this.mChildHelper.b(paramView);
      if (i >= 0) {
        detachViewInternal(i, paramView);
      }
    }
    
    public void detachViewAt(int paramInt)
    {
      detachViewInternal(paramInt, getChildAt(paramInt));
    }
    
    void dispatchAttachedToWindow(RecyclerView paramRecyclerView)
    {
      this.mIsAttachedToWindow = true;
      onAttachedToWindow(paramRecyclerView);
    }
    
    void dispatchDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      this.mIsAttachedToWindow = false;
      onDetachedFromWindow(paramRecyclerView, paramRecycler);
    }
    
    public void endAnimation(View paramView)
    {
      if (this.mRecyclerView.mItemAnimator != null) {
        this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(paramView));
      }
    }
    
    public View findContainingItemView(View paramView)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView == null) {
        return null;
      }
      paramView = localRecyclerView.findContainingItemView(paramView);
      if (paramView == null) {
        return null;
      }
      if (this.mChildHelper.c(paramView)) {
        return null;
      }
      return paramView;
    }
    
    public View findViewByPosition(int paramInt)
    {
      int i = getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = getChildAt(j);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if ((localViewHolder != null) && (localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.shouldIgnore()) && ((this.mRecyclerView.mState.isPreLayout()) || (!localViewHolder.isRemoved()))) {
          return localView;
        }
      }
      return null;
    }
    
    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();
    
    public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      return new RecyclerView.LayoutParams(paramContext, paramAttributeSet);
    }
    
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      if ((paramLayoutParams instanceof RecyclerView.LayoutParams)) {
        return new RecyclerView.LayoutParams((RecyclerView.LayoutParams)paramLayoutParams);
      }
      if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
        return new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
      }
      return new RecyclerView.LayoutParams(paramLayoutParams);
    }
    
    public int getBaseline()
    {
      return -1;
    }
    
    public int getBottomDecorationHeight(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.bottom;
    }
    
    public View getChildAt(int paramInt)
    {
      Object localObject = this.mChildHelper;
      if (localObject != null) {
        localObject = ((r)localObject).b(paramInt);
      } else {
        localObject = null;
      }
      return (View)localObject;
    }
    
    public int getChildCount()
    {
      r localr = this.mChildHelper;
      int i;
      if (localr != null) {
        i = localr.b();
      } else {
        i = 0;
      }
      return i;
    }
    
    public boolean getClipToPadding()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      boolean bool;
      if ((localRecyclerView != null) && (localRecyclerView.mClipToPadding)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      paramRecycler = this.mRecyclerView;
      int i = 1;
      if ((paramRecycler != null) && (paramRecycler.mAdapter != null))
      {
        if (canScrollHorizontally()) {
          i = this.mRecyclerView.mAdapter.getItemCount();
        }
        return i;
      }
      return 1;
    }
    
    public int getDecoratedBottom(View paramView)
    {
      return paramView.getBottom() + getBottomDecorationHeight(paramView);
    }
    
    public void getDecoratedBoundsWithMargins(View paramView, Rect paramRect)
    {
      RecyclerView.getDecoratedBoundsWithMarginsInt(paramView, paramRect);
    }
    
    public int getDecoratedLeft(View paramView)
    {
      return paramView.getLeft() - getLeftDecorationWidth(paramView);
    }
    
    public int getDecoratedMeasuredHeight(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredHeight() + localRect.top + localRect.bottom;
    }
    
    public int getDecoratedMeasuredWidth(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredWidth() + localRect.left + localRect.right;
    }
    
    public int getDecoratedRight(View paramView)
    {
      return paramView.getRight() + getRightDecorationWidth(paramView);
    }
    
    public int getDecoratedTop(View paramView)
    {
      return paramView.getTop() - getTopDecorationHeight(paramView);
    }
    
    public View getFocusedChild()
    {
      Object localObject = this.mRecyclerView;
      if (localObject == null) {
        return null;
      }
      localObject = ((RecyclerView)localObject).getFocusedChild();
      if ((localObject != null) && (!this.mChildHelper.c((View)localObject))) {
        return (View)localObject;
      }
      return null;
    }
    
    public int getHeight()
    {
      return this.mHeight;
    }
    
    public int getHeightMode()
    {
      return this.mHeightMode;
    }
    
    public int getItemCount()
    {
      Object localObject = this.mRecyclerView;
      if (localObject != null) {
        localObject = ((RecyclerView)localObject).getAdapter();
      } else {
        localObject = null;
      }
      int i;
      if (localObject != null) {
        i = ((RecyclerView.Adapter)localObject).getItemCount();
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getItemViewType(View paramView)
    {
      return RecyclerView.getChildViewHolderInt(paramView).getItemViewType();
    }
    
    public int getLayoutDirection()
    {
      return ViewCompat.getLayoutDirection(this.mRecyclerView);
    }
    
    public int getLeftDecorationWidth(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.left;
    }
    
    public int getMinimumHeight()
    {
      return ViewCompat.getMinimumHeight(this.mRecyclerView);
    }
    
    public int getMinimumWidth()
    {
      return ViewCompat.getMinimumWidth(this.mRecyclerView);
    }
    
    public int getPaddingBottom()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = localRecyclerView.getPaddingBottom();
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPaddingEnd()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = ViewCompat.getPaddingEnd(localRecyclerView);
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPaddingLeft()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = localRecyclerView.getPaddingLeft();
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPaddingRight()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = localRecyclerView.getPaddingRight();
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPaddingStart()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = ViewCompat.getPaddingStart(localRecyclerView);
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPaddingTop()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      int i;
      if (localRecyclerView != null) {
        i = localRecyclerView.getPaddingTop();
      } else {
        i = 0;
      }
      return i;
    }
    
    public int getPosition(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition();
    }
    
    public int getRightDecorationWidth(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.right;
    }
    
    public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      paramRecycler = this.mRecyclerView;
      int i = 1;
      if ((paramRecycler != null) && (paramRecycler.mAdapter != null))
      {
        if (canScrollVertically()) {
          i = this.mRecyclerView.mAdapter.getItemCount();
        }
        return i;
      }
      return 1;
    }
    
    public int getSelectionModeForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int getTopDecorationHeight(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.top;
    }
    
    public void getTransformedBoundingBox(View paramView, boolean paramBoolean, Rect paramRect)
    {
      Object localObject;
      if (paramBoolean)
      {
        localObject = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
        paramRect.set(-((Rect)localObject).left, -((Rect)localObject).top, paramView.getWidth() + ((Rect)localObject).right, paramView.getHeight() + ((Rect)localObject).bottom);
      }
      else
      {
        paramRect.set(0, 0, paramView.getWidth(), paramView.getHeight());
      }
      if (this.mRecyclerView != null)
      {
        Matrix localMatrix = paramView.getMatrix();
        if ((localMatrix != null) && (!localMatrix.isIdentity()))
        {
          localObject = this.mRecyclerView.mTempRectF;
          ((RectF)localObject).set(paramRect);
          localMatrix.mapRect((RectF)localObject);
          paramRect.set((int)Math.floor(((RectF)localObject).left), (int)Math.floor(((RectF)localObject).top), (int)Math.ceil(((RectF)localObject).right), (int)Math.ceil(((RectF)localObject).bottom));
        }
      }
      paramRect.offset(paramView.getLeft(), paramView.getTop());
    }
    
    public int getWidth()
    {
      return this.mWidth;
    }
    
    public int getWidthMode()
    {
      return this.mWidthMode;
    }
    
    boolean hasFlexibleChildInBothOrientations()
    {
      int i = getChildCount();
      for (int j = 0; j < i; j++)
      {
        ViewGroup.LayoutParams localLayoutParams = getChildAt(j).getLayoutParams();
        if ((localLayoutParams.width < 0) && (localLayoutParams.height < 0)) {
          return true;
        }
      }
      return false;
    }
    
    public boolean hasFocus()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      boolean bool;
      if ((localRecyclerView != null) && (localRecyclerView.hasFocus())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void ignoreView(View paramView)
    {
      ViewParent localViewParent = paramView.getParent();
      RecyclerView localRecyclerView = this.mRecyclerView;
      if ((localViewParent == localRecyclerView) && (localRecyclerView.indexOfChild(paramView) != -1))
      {
        paramView = RecyclerView.getChildViewHolderInt(paramView);
        paramView.addFlags(128);
        this.mRecyclerView.mViewInfoStore.g(paramView);
        return;
      }
      paramView = new StringBuilder();
      paramView.append("View should be fully attached to be ignored");
      paramView.append(this.mRecyclerView.exceptionLabel());
      throw new IllegalArgumentException(paramView.toString());
    }
    
    public boolean isAttachedToWindow()
    {
      return this.mIsAttachedToWindow;
    }
    
    public boolean isAutoMeasureEnabled()
    {
      return this.mAutoMeasure;
    }
    
    public boolean isFocused()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      boolean bool;
      if ((localRecyclerView != null) && (localRecyclerView.isFocused())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final boolean isItemPrefetchEnabled()
    {
      return this.mItemPrefetchEnabled;
    }
    
    public boolean isLayoutHierarchical(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return false;
    }
    
    public boolean isMeasurementCacheEnabled()
    {
      return this.mMeasurementCacheEnabled;
    }
    
    public boolean isSmoothScrolling()
    {
      RecyclerView.SmoothScroller localSmoothScroller = this.mSmoothScroller;
      boolean bool;
      if ((localSmoothScroller != null) && (localSmoothScroller.isRunning())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isViewPartiallyVisible(View paramView, boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((this.mHorizontalBoundCheck.a(paramView, 24579)) && (this.mVerticalBoundCheck.a(paramView, 24579))) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      if (paramBoolean1) {
        return paramBoolean2;
      }
      return paramBoolean2 ^ true;
    }
    
    public void layoutDecorated(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      paramView.layout(paramInt1 + localRect.left, paramInt2 + localRect.top, paramInt3 - localRect.right, paramInt4 - localRect.bottom);
    }
    
    public void layoutDecoratedWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = localLayoutParams.mDecorInsets;
      paramView.layout(paramInt1 + localRect.left + localLayoutParams.leftMargin, paramInt2 + localRect.top + localLayoutParams.topMargin, paramInt3 - localRect.right - localLayoutParams.rightMargin, paramInt4 - localRect.bottom - localLayoutParams.bottomMargin);
    }
    
    public void measureChild(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = this.mRecyclerView.getItemDecorInsetsForChild(paramView);
      int i = localRect.left;
      int j = localRect.right;
      int k = localRect.top;
      int m = localRect.bottom;
      paramInt1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + (paramInt1 + (i + j)), localLayoutParams.width, canScrollHorizontally());
      paramInt2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + (paramInt2 + (k + m)), localLayoutParams.height, canScrollVertically());
      if (shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams)) {
        paramView.measure(paramInt1, paramInt2);
      }
    }
    
    public void measureChildWithMargins(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = this.mRecyclerView.getItemDecorInsetsForChild(paramView);
      int i = localRect.left;
      int j = localRect.right;
      int k = localRect.top;
      int m = localRect.bottom;
      paramInt1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + localLayoutParams.leftMargin + localLayoutParams.rightMargin + (paramInt1 + (i + j)), localLayoutParams.width, canScrollHorizontally());
      paramInt2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + localLayoutParams.topMargin + localLayoutParams.bottomMargin + (paramInt2 + (k + m)), localLayoutParams.height, canScrollVertically());
      if (shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams)) {
        paramView.measure(paramInt1, paramInt2);
      }
    }
    
    public void moveView(int paramInt1, int paramInt2)
    {
      Object localObject = getChildAt(paramInt1);
      if (localObject != null)
      {
        detachViewAt(paramInt1);
        attachView((View)localObject, paramInt2);
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Cannot move a child from non-existing index:");
      ((StringBuilder)localObject).append(paramInt1);
      ((StringBuilder)localObject).append(this.mRecyclerView.toString());
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    
    public void offsetChildrenHorizontal(int paramInt)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.offsetChildrenHorizontal(paramInt);
      }
    }
    
    public void offsetChildrenVertical(int paramInt)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.offsetChildrenVertical(paramInt);
      }
    }
    
    public void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2) {}
    
    public boolean onAddFocusables(RecyclerView paramRecyclerView, ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
    {
      return false;
    }
    
    public void onAttachedToWindow(RecyclerView paramRecyclerView) {}
    
    @Deprecated
    public void onDetachedFromWindow(RecyclerView paramRecyclerView) {}
    
    public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      onDetachedFromWindow(paramRecyclerView);
    }
    
    public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return null;
    }
    
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityEvent paramAccessibilityEvent)
    {
      paramRecycler = this.mRecyclerView;
      if ((paramRecycler != null) && (paramAccessibilityEvent != null))
      {
        boolean bool1 = true;
        boolean bool2 = bool1;
        if (!paramRecycler.canScrollVertically(1))
        {
          bool2 = bool1;
          if (!this.mRecyclerView.canScrollVertically(-1))
          {
            bool2 = bool1;
            if (!this.mRecyclerView.canScrollHorizontally(-1)) {
              if (this.mRecyclerView.canScrollHorizontally(1)) {
                bool2 = bool1;
              } else {
                bool2 = false;
              }
            }
          }
        }
        paramAccessibilityEvent.setScrollable(bool2);
        if (this.mRecyclerView.mAdapter != null) {
          paramAccessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        }
        return;
      }
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramAccessibilityEvent);
    }
    
    void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramAccessibilityNodeInfoCompat);
    }
    
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if ((this.mRecyclerView.canScrollVertically(-1)) || (this.mRecyclerView.canScrollHorizontally(-1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(8192);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      if ((this.mRecyclerView.canScrollVertically(1)) || (this.mRecyclerView.canScrollHorizontally(1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(4096);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      paramAccessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(paramRecycler, paramState), getColumnCountForAccessibility(paramRecycler, paramState), isLayoutHierarchical(paramRecycler, paramState), getSelectionModeForAccessibility(paramRecycler, paramState)));
    }
    
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      int i;
      if (canScrollVertically()) {
        i = getPosition(paramView);
      } else {
        i = 0;
      }
      int j;
      if (canScrollHorizontally()) {
        j = getPosition(paramView);
      } else {
        j = 0;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, 1, false, false));
    }
    
    void onInitializeAccessibilityNodeInfoForItem(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()) && (!this.mChildHelper.c(localViewHolder.itemView))) {
        onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramView, paramAccessibilityNodeInfoCompat);
      }
    }
    
    public View onInterceptFocusSearch(View paramView, int paramInt)
    {
      return null;
    }
    
    public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsChanged(RecyclerView paramRecyclerView) {}
    
    public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3) {}
    
    public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
    {
      onItemsUpdated(paramRecyclerView, paramInt1, paramInt2);
    }
    
    public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
    }
    
    public void onLayoutCompleted(RecyclerView.State paramState) {}
    
    public void onMeasure(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
    {
      this.mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
    }
    
    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, RecyclerView.State paramState, View paramView1, View paramView2)
    {
      return onRequestChildFocus(paramRecyclerView, paramView1, paramView2);
    }
    
    @Deprecated
    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, View paramView1, View paramView2)
    {
      boolean bool;
      if ((!isSmoothScrolling()) && (!paramRecyclerView.isComputingLayout())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void onRestoreInstanceState(Parcelable paramParcelable) {}
    
    public Parcelable onSaveInstanceState()
    {
      return null;
    }
    
    public void onScrollStateChanged(int paramInt) {}
    
    boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
    {
      return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramInt, paramBundle);
    }
    
    public boolean performAccessibilityAction(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt, Bundle paramBundle)
    {
      paramRecycler = this.mRecyclerView;
      if (paramRecycler == null) {
        return false;
      }
      int i;
      if (paramInt != 4096)
      {
        if (paramInt != 8192)
        {
          i = 0;
          paramInt = 0;
        }
        else
        {
          if (paramRecycler.canScrollVertically(-1)) {
            i = -(getHeight() - getPaddingTop() - getPaddingBottom());
          } else {
            i = 0;
          }
          if (this.mRecyclerView.canScrollHorizontally(-1)) {
            paramInt = -(getWidth() - getPaddingLeft() - getPaddingRight());
          } else {
            paramInt = 0;
          }
        }
      }
      else
      {
        if (paramRecycler.canScrollVertically(1)) {
          i = getHeight() - getPaddingTop() - getPaddingBottom();
        } else {
          i = 0;
        }
        if (this.mRecyclerView.canScrollHorizontally(1)) {
          paramInt = getWidth() - getPaddingLeft() - getPaddingRight();
        } else {
          paramInt = 0;
        }
      }
      if ((i == 0) && (paramInt == 0)) {
        return false;
      }
      this.mRecyclerView.scrollBy(paramInt, i);
      return true;
    }
    
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, int paramInt, Bundle paramBundle)
    {
      return false;
    }
    
    boolean performAccessibilityActionForItem(View paramView, int paramInt, Bundle paramBundle)
    {
      return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramView, paramInt, paramBundle);
    }
    
    public void postOnAnimation(Runnable paramRunnable)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        ViewCompat.postOnAnimation(localRecyclerView, paramRunnable);
      }
    }
    
    public void removeAllViews()
    {
      for (int i = getChildCount() - 1; i >= 0; i--) {
        this.mChildHelper.a(i);
      }
    }
    
    public void removeAndRecycleAllViews(RecyclerView.Recycler paramRecycler)
    {
      for (int i = getChildCount() - 1; i >= 0; i--) {
        if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore()) {
          removeAndRecycleViewAt(i, paramRecycler);
        }
      }
    }
    
    void removeAndRecycleScrapInt(RecyclerView.Recycler paramRecycler)
    {
      int i = paramRecycler.getScrapCount();
      for (int j = i - 1; j >= 0; j--)
      {
        View localView = paramRecycler.getScrapViewAt(j);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if (!localViewHolder.shouldIgnore())
        {
          localViewHolder.setIsRecyclable(false);
          if (localViewHolder.isTmpDetached()) {
            this.mRecyclerView.removeDetachedView(localView, false);
          }
          if (this.mRecyclerView.mItemAnimator != null) {
            this.mRecyclerView.mItemAnimator.endAnimation(localViewHolder);
          }
          localViewHolder.setIsRecyclable(true);
          paramRecycler.quickRecycleScrapView(localView);
        }
      }
      paramRecycler.clearScrap();
      if (i > 0) {
        this.mRecyclerView.invalidate();
      }
    }
    
    public void removeAndRecycleView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      removeView(paramView);
      paramRecycler.recycleView(paramView);
    }
    
    public void removeAndRecycleViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      View localView = getChildAt(paramInt);
      removeViewAt(paramInt);
      paramRecycler.recycleView(localView);
    }
    
    public boolean removeCallbacks(Runnable paramRunnable)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.removeCallbacks(paramRunnable);
      }
      return false;
    }
    
    public void removeDetachedView(View paramView)
    {
      this.mRecyclerView.removeDetachedView(paramView, false);
    }
    
    public void removeView(View paramView)
    {
      this.mChildHelper.a(paramView);
    }
    
    public void removeViewAt(int paramInt)
    {
      if (getChildAt(paramInt) != null) {
        this.mChildHelper.a(paramInt);
      }
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean)
    {
      return requestChildRectangleOnScreen(paramRecyclerView, paramView, paramRect, paramBoolean, false);
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean1, boolean paramBoolean2)
    {
      paramView = getChildRectangleOnScreenScrollAmount(paramRecyclerView, paramView, paramRect, paramBoolean1);
      int i = paramView[0];
      int j = paramView[1];
      if (((paramBoolean2) && (!isFocusedChildVisibleAfterScrolling(paramRecyclerView, i, j))) || ((i == 0) && (j == 0))) {
        return false;
      }
      if (paramBoolean1) {
        paramRecyclerView.scrollBy(i, j);
      } else {
        paramRecyclerView.smoothScrollBy(i, j);
      }
      return true;
    }
    
    public void requestLayout()
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.requestLayout();
      }
    }
    
    public void requestSimpleAnimationsInNextLayout()
    {
      this.mRequestedSimpleAnimations = true;
    }
    
    public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void scrollToPosition(int paramInt) {}
    
    public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void setAutoMeasureEnabled(boolean paramBoolean)
    {
      this.mAutoMeasure = paramBoolean;
    }
    
    void setExactMeasureSpecsFrom(RecyclerView paramRecyclerView)
    {
      setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getHeight(), 1073741824));
    }
    
    public final void setItemPrefetchEnabled(boolean paramBoolean)
    {
      if (paramBoolean != this.mItemPrefetchEnabled)
      {
        this.mItemPrefetchEnabled = paramBoolean;
        this.mPrefetchMaxCountObserved = 0;
        RecyclerView localRecyclerView = this.mRecyclerView;
        if (localRecyclerView != null) {
          localRecyclerView.mRecycler.updateViewCacheSize();
        }
      }
    }
    
    void setMeasureSpecs(int paramInt1, int paramInt2)
    {
      this.mWidth = View.MeasureSpec.getSize(paramInt1);
      this.mWidthMode = View.MeasureSpec.getMode(paramInt1);
      if ((this.mWidthMode == 0) && (!RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)) {
        this.mWidth = 0;
      }
      this.mHeight = View.MeasureSpec.getSize(paramInt2);
      this.mHeightMode = View.MeasureSpec.getMode(paramInt2);
      if ((this.mHeightMode == 0) && (!RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)) {
        this.mHeight = 0;
      }
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      this.mRecyclerView.setMeasuredDimension(paramInt1, paramInt2);
    }
    
    public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
    {
      int i = paramRect.width();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = paramRect.height();
      int n = getPaddingTop();
      int i1 = getPaddingBottom();
      setMeasuredDimension(chooseSize(paramInt1, i + j + k, getMinimumWidth()), chooseSize(paramInt2, m + n + i1, getMinimumHeight()));
    }
    
    void setMeasuredDimensionFromChildren(int paramInt1, int paramInt2)
    {
      int i = getChildCount();
      if (i == 0)
      {
        this.mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
        return;
      }
      int j = 0;
      int k = Integer.MAX_VALUE;
      int m = Integer.MAX_VALUE;
      int n = Integer.MIN_VALUE;
      int i4;
      for (int i1 = Integer.MIN_VALUE; j < i; i1 = i4)
      {
        View localView = getChildAt(j);
        Rect localRect = this.mRecyclerView.mTempRect;
        getDecoratedBoundsWithMargins(localView, localRect);
        int i2 = k;
        if (localRect.left < k) {
          i2 = localRect.left;
        }
        int i3 = n;
        if (localRect.right > n) {
          i3 = localRect.right;
        }
        n = m;
        if (localRect.top < m) {
          n = localRect.top;
        }
        i4 = i1;
        if (localRect.bottom > i1) {
          i4 = localRect.bottom;
        }
        j++;
        m = n;
        k = i2;
        n = i3;
      }
      this.mRecyclerView.mTempRect.set(k, m, n, i1);
      setMeasuredDimension(this.mRecyclerView.mTempRect, paramInt1, paramInt2);
    }
    
    public void setMeasurementCacheEnabled(boolean paramBoolean)
    {
      this.mMeasurementCacheEnabled = paramBoolean;
    }
    
    void setRecyclerView(RecyclerView paramRecyclerView)
    {
      if (paramRecyclerView == null)
      {
        this.mRecyclerView = null;
        this.mChildHelper = null;
        this.mWidth = 0;
        this.mHeight = 0;
      }
      else
      {
        this.mRecyclerView = paramRecyclerView;
        this.mChildHelper = paramRecyclerView.mChildHelper;
        this.mWidth = paramRecyclerView.getWidth();
        this.mHeight = paramRecyclerView.getHeight();
      }
      this.mWidthMode = 1073741824;
      this.mHeightMode = 1073741824;
    }
    
    boolean shouldMeasureChild(View paramView, int paramInt1, int paramInt2, RecyclerView.LayoutParams paramLayoutParams)
    {
      boolean bool;
      if ((!paramView.isLayoutRequested()) && (this.mMeasurementCacheEnabled) && (isMeasurementUpToDate(paramView.getWidth(), paramInt1, paramLayoutParams.width)) && (isMeasurementUpToDate(paramView.getHeight(), paramInt2, paramLayoutParams.height))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    boolean shouldMeasureTwice()
    {
      return false;
    }
    
    boolean shouldReMeasureChild(View paramView, int paramInt1, int paramInt2, RecyclerView.LayoutParams paramLayoutParams)
    {
      boolean bool;
      if ((this.mMeasurementCacheEnabled) && (isMeasurementUpToDate(paramView.getMeasuredWidth(), paramInt1, paramLayoutParams.width)) && (isMeasurementUpToDate(paramView.getMeasuredHeight(), paramInt2, paramLayoutParams.height))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
    {
      Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
    }
    
    public void startSmoothScroll(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      RecyclerView.SmoothScroller localSmoothScroller = this.mSmoothScroller;
      if ((localSmoothScroller != null) && (paramSmoothScroller != localSmoothScroller) && (localSmoothScroller.isRunning())) {
        this.mSmoothScroller.stop();
      }
      this.mSmoothScroller = paramSmoothScroller;
      this.mSmoothScroller.start(this.mRecyclerView, this);
    }
    
    public void stopIgnoringView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      paramView.stopIgnoring();
      paramView.resetInternal();
      paramView.addFlags(4);
    }
    
    void stopSmoothScroller()
    {
      RecyclerView.SmoothScroller localSmoothScroller = this.mSmoothScroller;
      if (localSmoothScroller != null) {
        localSmoothScroller.stop();
      }
    }
    
    public boolean supportsPredictiveItemAnimations()
    {
      return false;
    }
    
    public static abstract interface LayoutPrefetchRegistry
    {
      public abstract void addPosition(int paramInt1, int paramInt2);
    }
    
    public static class Properties
    {
      public int orientation;
      public boolean reverseLayout;
      public int spanCount;
      public boolean stackFromEnd;
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    final Rect mDecorInsets = new Rect();
    boolean mInsetsDirty = true;
    boolean mPendingInvalidate = false;
    RecyclerView.ViewHolder mViewHolder;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public int getViewAdapterPosition()
    {
      return this.mViewHolder.getAdapterPosition();
    }
    
    public int getViewLayoutPosition()
    {
      return this.mViewHolder.getLayoutPosition();
    }
    
    @Deprecated
    public int getViewPosition()
    {
      return this.mViewHolder.getPosition();
    }
    
    public boolean isItemChanged()
    {
      return this.mViewHolder.isUpdated();
    }
    
    public boolean isItemRemoved()
    {
      return this.mViewHolder.isRemoved();
    }
    
    public boolean isViewInvalid()
    {
      return this.mViewHolder.isInvalid();
    }
    
    public boolean viewNeedsUpdate()
    {
      return this.mViewHolder.needsUpdate();
    }
  }
  
  public static abstract interface OnChildAttachStateChangeListener
  {
    public abstract void onChildViewAttachedToWindow(View paramView);
    
    public abstract void onChildViewDetachedFromWindow(View paramView);
  }
  
  public static abstract class OnFlingListener
  {
    public abstract boolean onFling(int paramInt1, int paramInt2);
  }
  
  public static abstract interface OnItemTouchListener
  {
    public abstract boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);
    
    public abstract void onRequestDisallowInterceptTouchEvent(boolean paramBoolean);
    
    public abstract void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);
  }
  
  public static abstract class OnScrollListener
  {
    public void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt) {}
    
    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
  }
  
  public static class RecycledViewPool
  {
    private static final int DEFAULT_MAX_SCRAP = 5;
    private int mAttachCount = 0;
    SparseArray<a> mScrap = new SparseArray();
    
    private a getScrapDataForType(int paramInt)
    {
      a locala1 = (a)this.mScrap.get(paramInt);
      a locala2 = locala1;
      if (locala1 == null)
      {
        locala2 = new a();
        this.mScrap.put(paramInt, locala2);
      }
      return locala2;
    }
    
    void attach(RecyclerView.Adapter paramAdapter)
    {
      this.mAttachCount += 1;
    }
    
    public void clear()
    {
      for (int i = 0; i < this.mScrap.size(); i++) {
        ((a)this.mScrap.valueAt(i)).a.clear();
      }
    }
    
    void detach()
    {
      this.mAttachCount -= 1;
    }
    
    void factorInBindTime(int paramInt, long paramLong)
    {
      a locala = getScrapDataForType(paramInt);
      locala.d = runningAverage(locala.d, paramLong);
    }
    
    void factorInCreateTime(int paramInt, long paramLong)
    {
      a locala = getScrapDataForType(paramInt);
      locala.c = runningAverage(locala.c, paramLong);
    }
    
    public RecyclerView.ViewHolder getRecycledView(int paramInt)
    {
      Object localObject = (a)this.mScrap.get(paramInt);
      if ((localObject != null) && (!((a)localObject).a.isEmpty()))
      {
        localObject = ((a)localObject).a;
        return (RecyclerView.ViewHolder)((ArrayList)localObject).remove(((ArrayList)localObject).size() - 1);
      }
      return null;
    }
    
    public int getRecycledViewCount(int paramInt)
    {
      return getScrapDataForType(paramInt).a.size();
    }
    
    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      if (paramAdapter1 != null) {
        detach();
      }
      if ((!paramBoolean) && (this.mAttachCount == 0)) {
        clear();
      }
      if (paramAdapter2 != null) {
        attach(paramAdapter2);
      }
    }
    
    public void putRecycledView(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = paramViewHolder.getItemViewType();
      ArrayList localArrayList = getScrapDataForType(i).a;
      if (((a)this.mScrap.get(i)).b <= localArrayList.size()) {
        return;
      }
      paramViewHolder.resetInternal();
      localArrayList.add(paramViewHolder);
    }
    
    long runningAverage(long paramLong1, long paramLong2)
    {
      if (paramLong1 == 0L) {
        return paramLong2;
      }
      return paramLong1 / 4L * 3L + paramLong2 / 4L;
    }
    
    public void setMaxRecycledViews(int paramInt1, int paramInt2)
    {
      Object localObject = getScrapDataForType(paramInt1);
      ((a)localObject).b = paramInt2;
      localObject = ((a)localObject).a;
      if (localObject != null) {
        while (((ArrayList)localObject).size() > paramInt2) {
          ((ArrayList)localObject).remove(((ArrayList)localObject).size() - 1);
        }
      }
    }
    
    int size()
    {
      int i = 0;
      int k;
      for (int j = 0; i < this.mScrap.size(); j = k)
      {
        ArrayList localArrayList = ((a)this.mScrap.valueAt(i)).a;
        k = j;
        if (localArrayList != null) {
          k = j + localArrayList.size();
        }
        i++;
      }
      return j;
    }
    
    boolean willBindInTime(int paramInt, long paramLong1, long paramLong2)
    {
      long l = getScrapDataForType(paramInt).d;
      boolean bool;
      if ((l != 0L) && (paramLong1 + l >= paramLong2)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    boolean willCreateInTime(int paramInt, long paramLong1, long paramLong2)
    {
      long l = getScrapDataForType(paramInt).c;
      boolean bool;
      if ((l != 0L) && (paramLong1 + l >= paramLong2)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    static class a
    {
      ArrayList<RecyclerView.ViewHolder> a = new ArrayList();
      int b = 5;
      long c = 0L;
      long d = 0L;
    }
  }
  
  public final class Recycler
  {
    static final int DEFAULT_CACHE_SIZE = 2;
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList();
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    RecyclerView.RecycledViewPool mRecyclerPool;
    private int mRequestedCacheMax = 2;
    private final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
    private RecyclerView.ViewCacheExtension mViewCacheExtension;
    int mViewCacheMax = 2;
    
    public Recycler() {}
    
    private void attachAccessibilityDelegateOnBind(RecyclerView.ViewHolder paramViewHolder)
    {
      if (RecyclerView.this.isAccessibilityEnabled())
      {
        View localView = paramViewHolder.itemView;
        if (ViewCompat.getImportantForAccessibility(localView) == 0) {
          ViewCompat.setImportantForAccessibility(localView, 1);
        }
        if (!ViewCompat.hasAccessibilityDelegate(localView))
        {
          paramViewHolder.addFlags(16384);
          ViewCompat.setAccessibilityDelegate(localView, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
        }
      }
    }
    
    private void invalidateDisplayListInt(RecyclerView.ViewHolder paramViewHolder)
    {
      if ((paramViewHolder.itemView instanceof ViewGroup)) {
        invalidateDisplayListInt((ViewGroup)paramViewHolder.itemView, false);
      }
    }
    
    private void invalidateDisplayListInt(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      for (int i = paramViewGroup.getChildCount() - 1; i >= 0; i--)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView instanceof ViewGroup)) {
          invalidateDisplayListInt((ViewGroup)localView, true);
        }
      }
      if (!paramBoolean) {
        return;
      }
      if (paramViewGroup.getVisibility() == 4)
      {
        paramViewGroup.setVisibility(0);
        paramViewGroup.setVisibility(4);
      }
      else
      {
        i = paramViewGroup.getVisibility();
        paramViewGroup.setVisibility(4);
        paramViewGroup.setVisibility(i);
      }
    }
    
    private boolean tryBindViewHolderByDeadline(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, long paramLong)
    {
      paramViewHolder.mOwnerRecyclerView = RecyclerView.this;
      int i = paramViewHolder.getItemViewType();
      long l = RecyclerView.this.getNanoTime();
      if ((paramLong != Long.MAX_VALUE) && (!this.mRecyclerPool.willBindInTime(i, l, paramLong))) {
        return false;
      }
      RecyclerView.this.mAdapter.bindViewHolder(paramViewHolder, paramInt1);
      paramLong = RecyclerView.this.getNanoTime();
      this.mRecyclerPool.factorInBindTime(paramViewHolder.getItemViewType(), paramLong - l);
      attachAccessibilityDelegateOnBind(paramViewHolder);
      if (RecyclerView.this.mState.isPreLayout()) {
        paramViewHolder.mPreLayoutPosition = paramInt2;
      }
      return true;
    }
    
    void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
      RecyclerView.clearNestedRecyclerViewIfNotNested(paramViewHolder);
      if (paramViewHolder.hasAnyOfTheFlags(16384))
      {
        paramViewHolder.setFlags(0, 16384);
        ViewCompat.setAccessibilityDelegate(paramViewHolder.itemView, null);
      }
      if (paramBoolean) {
        dispatchViewRecycled(paramViewHolder);
      }
      paramViewHolder.mOwnerRecyclerView = null;
      getRecycledViewPool().putRecycledView(paramViewHolder);
    }
    
    public void bindViewToPosition(View paramView, int paramInt)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder != null)
      {
        int i = RecyclerView.this.mAdapterHelper.b(paramInt);
        if ((i >= 0) && (i < RecyclerView.this.mAdapter.getItemCount()))
        {
          tryBindViewHolderByDeadline(localViewHolder, i, paramInt, Long.MAX_VALUE);
          paramView = localViewHolder.itemView.getLayoutParams();
          if (paramView == null)
          {
            paramView = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
            localViewHolder.itemView.setLayoutParams(paramView);
          }
          else if (!RecyclerView.this.checkLayoutParams(paramView))
          {
            paramView = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams(paramView);
            localViewHolder.itemView.setLayoutParams(paramView);
          }
          else
          {
            paramView = (RecyclerView.LayoutParams)paramView;
          }
          boolean bool = true;
          paramView.mInsetsDirty = true;
          paramView.mViewHolder = localViewHolder;
          if (localViewHolder.itemView.getParent() != null) {
            bool = false;
          }
          paramView.mPendingInvalidate = bool;
          return;
        }
        paramView = new StringBuilder();
        paramView.append("Inconsistency detected. Invalid item position ");
        paramView.append(paramInt);
        paramView.append("(offset:");
        paramView.append(i);
        paramView.append(").");
        paramView.append("state:");
        paramView.append(RecyclerView.this.mState.getItemCount());
        paramView.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(paramView.toString());
      }
      paramView = new StringBuilder();
      paramView.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
      paramView.append(RecyclerView.this.exceptionLabel());
      throw new IllegalArgumentException(paramView.toString());
    }
    
    public void clear()
    {
      this.mAttachedScrap.clear();
      recycleAndClearCachedViews();
    }
    
    void clearOldPositions()
    {
      int i = this.mCachedViews.size();
      int j = 0;
      for (int k = 0; k < i; k++) {
        ((RecyclerView.ViewHolder)this.mCachedViews.get(k)).clearOldPosition();
      }
      i = this.mAttachedScrap.size();
      for (k = 0; k < i; k++) {
        ((RecyclerView.ViewHolder)this.mAttachedScrap.get(k)).clearOldPosition();
      }
      ArrayList localArrayList = this.mChangedScrap;
      if (localArrayList != null)
      {
        i = localArrayList.size();
        for (k = j; k < i; k++) {
          ((RecyclerView.ViewHolder)this.mChangedScrap.get(k)).clearOldPosition();
        }
      }
    }
    
    void clearScrap()
    {
      this.mAttachedScrap.clear();
      ArrayList localArrayList = this.mChangedScrap;
      if (localArrayList != null) {
        localArrayList.clear();
      }
    }
    
    public int convertPreLayoutPositionToPostLayout(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt < RecyclerView.this.mState.getItemCount()))
      {
        if (!RecyclerView.this.mState.isPreLayout()) {
          return paramInt;
        }
        return RecyclerView.this.mAdapterHelper.b(paramInt);
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("invalid position ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(". State ");
      localStringBuilder.append("item count is ");
      localStringBuilder.append(RecyclerView.this.mState.getItemCount());
      localStringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IndexOutOfBoundsException(localStringBuilder.toString());
    }
    
    void dispatchViewRecycled(RecyclerView.ViewHolder paramViewHolder)
    {
      if (RecyclerView.this.mRecyclerListener != null) {
        RecyclerView.this.mRecyclerListener.onViewRecycled(paramViewHolder);
      }
      if (RecyclerView.this.mAdapter != null) {
        RecyclerView.this.mAdapter.onViewRecycled(paramViewHolder);
      }
      if (RecyclerView.this.mState != null) {
        RecyclerView.this.mViewInfoStore.g(paramViewHolder);
      }
    }
    
    RecyclerView.ViewHolder getChangedScrapViewForPosition(int paramInt)
    {
      Object localObject = this.mChangedScrap;
      if (localObject != null)
      {
        int i = ((ArrayList)localObject).size();
        if (i != 0)
        {
          int j = 0;
          for (int k = 0; k < i; k++)
          {
            localObject = (RecyclerView.ViewHolder)this.mChangedScrap.get(k);
            if ((!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) && (((RecyclerView.ViewHolder)localObject).getLayoutPosition() == paramInt))
            {
              ((RecyclerView.ViewHolder)localObject).addFlags(32);
              return (RecyclerView.ViewHolder)localObject;
            }
          }
          if (RecyclerView.this.mAdapter.hasStableIds())
          {
            paramInt = RecyclerView.this.mAdapterHelper.b(paramInt);
            if ((paramInt > 0) && (paramInt < RecyclerView.this.mAdapter.getItemCount()))
            {
              long l = RecyclerView.this.mAdapter.getItemId(paramInt);
              for (paramInt = j; paramInt < i; paramInt++)
              {
                localObject = (RecyclerView.ViewHolder)this.mChangedScrap.get(paramInt);
                if ((!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) && (((RecyclerView.ViewHolder)localObject).getItemId() == l))
                {
                  ((RecyclerView.ViewHolder)localObject).addFlags(32);
                  return (RecyclerView.ViewHolder)localObject;
                }
              }
            }
          }
          return null;
        }
      }
      return null;
    }
    
    RecyclerView.RecycledViewPool getRecycledViewPool()
    {
      if (this.mRecyclerPool == null) {
        this.mRecyclerPool = new RecyclerView.RecycledViewPool();
      }
      return this.mRecyclerPool;
    }
    
    int getScrapCount()
    {
      return this.mAttachedScrap.size();
    }
    
    public List<RecyclerView.ViewHolder> getScrapList()
    {
      return this.mUnmodifiableAttachedScrap;
    }
    
    RecyclerView.ViewHolder getScrapOrCachedViewForId(long paramLong, int paramInt, boolean paramBoolean)
    {
      RecyclerView.ViewHolder localViewHolder;
      for (int i = this.mAttachedScrap.size() - 1; i >= 0; i--)
      {
        localViewHolder = (RecyclerView.ViewHolder)this.mAttachedScrap.get(i);
        if ((localViewHolder.getItemId() == paramLong) && (!localViewHolder.wasReturnedFromScrap()))
        {
          if (paramInt == localViewHolder.getItemViewType())
          {
            localViewHolder.addFlags(32);
            if ((localViewHolder.isRemoved()) && (!RecyclerView.this.mState.isPreLayout())) {
              localViewHolder.setFlags(2, 14);
            }
            return localViewHolder;
          }
          if (!paramBoolean)
          {
            this.mAttachedScrap.remove(i);
            RecyclerView.this.removeDetachedView(localViewHolder.itemView, false);
            quickRecycleScrapView(localViewHolder.itemView);
          }
        }
      }
      for (i = this.mCachedViews.size() - 1; i >= 0; i--)
      {
        localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(i);
        if (localViewHolder.getItemId() == paramLong)
        {
          if (paramInt == localViewHolder.getItemViewType())
          {
            if (!paramBoolean) {
              this.mCachedViews.remove(i);
            }
            return localViewHolder;
          }
          if (!paramBoolean)
          {
            recycleCachedViewAt(i);
            return null;
          }
        }
      }
      return null;
    }
    
    RecyclerView.ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int paramInt, boolean paramBoolean)
    {
      int i = this.mAttachedScrap.size();
      int j = 0;
      RecyclerView.ViewHolder localViewHolder;
      for (int k = 0; k < i; k++)
      {
        localViewHolder = (RecyclerView.ViewHolder)this.mAttachedScrap.get(k);
        if ((!localViewHolder.wasReturnedFromScrap()) && (localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.isInvalid()) && ((RecyclerView.this.mState.mInPreLayout) || (!localViewHolder.isRemoved())))
        {
          localViewHolder.addFlags(32);
          return localViewHolder;
        }
      }
      if (!paramBoolean)
      {
        Object localObject = RecyclerView.this.mChildHelper.c(paramInt);
        if (localObject != null)
        {
          localViewHolder = RecyclerView.getChildViewHolderInt((View)localObject);
          RecyclerView.this.mChildHelper.e((View)localObject);
          paramInt = RecyclerView.this.mChildHelper.b((View)localObject);
          if (paramInt != -1)
          {
            RecyclerView.this.mChildHelper.e(paramInt);
            scrapView((View)localObject);
            localViewHolder.addFlags(8224);
            return localViewHolder;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("layout index should not be -1 after unhiding a view:");
          ((StringBuilder)localObject).append(localViewHolder);
          ((StringBuilder)localObject).append(RecyclerView.this.exceptionLabel());
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
      }
      i = this.mCachedViews.size();
      for (k = j; k < i; k++)
      {
        localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(k);
        if ((!localViewHolder.isInvalid()) && (localViewHolder.getLayoutPosition() == paramInt))
        {
          if (!paramBoolean) {
            this.mCachedViews.remove(k);
          }
          return localViewHolder;
        }
      }
      return null;
    }
    
    View getScrapViewAt(int paramInt)
    {
      return ((RecyclerView.ViewHolder)this.mAttachedScrap.get(paramInt)).itemView;
    }
    
    public View getViewForPosition(int paramInt)
    {
      return getViewForPosition(paramInt, false);
    }
    
    View getViewForPosition(int paramInt, boolean paramBoolean)
    {
      return tryGetViewHolderForPositionByDeadline(paramInt, paramBoolean, Long.MAX_VALUE).itemView;
    }
    
    void markItemDecorInsetsDirty()
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)((RecyclerView.ViewHolder)this.mCachedViews.get(j)).itemView.getLayoutParams();
        if (localLayoutParams != null) {
          localLayoutParams.mInsetsDirty = true;
        }
      }
    }
    
    void markKnownViewsInvalid()
    {
      int i;
      int j;
      if ((RecyclerView.this.mAdapter != null) && (RecyclerView.this.mAdapter.hasStableIds()))
      {
        i = this.mCachedViews.size();
        j = 0;
      }
      while (j < i)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder != null)
        {
          localViewHolder.addFlags(6);
          localViewHolder.addChangePayload(null);
        }
        j++;
        continue;
        recycleAndClearCachedViews();
      }
    }
    
    void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if ((localViewHolder != null) && (localViewHolder.mPosition >= paramInt1)) {
          localViewHolder.offsetPosition(paramInt2, true);
        }
      }
    }
    
    void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
    {
      int i;
      int j;
      int k;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1;
        j = paramInt2;
        k = -1;
      }
      else
      {
        j = paramInt1;
        i = paramInt2;
        k = 1;
      }
      int m = this.mCachedViews.size();
      for (int n = 0; n < m; n++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(n);
        if ((localViewHolder != null) && (localViewHolder.mPosition >= i) && (localViewHolder.mPosition <= j)) {
          if (localViewHolder.mPosition == paramInt1) {
            localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
          } else {
            localViewHolder.offsetPosition(k, false);
          }
        }
      }
    }
    
    void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(i);
        if (localViewHolder != null) {
          if (localViewHolder.mPosition >= paramInt1 + paramInt2)
          {
            localViewHolder.offsetPosition(-paramInt2, paramBoolean);
          }
          else if (localViewHolder.mPosition >= paramInt1)
          {
            localViewHolder.addFlags(8);
            recycleCachedViewAt(i);
          }
        }
      }
    }
    
    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      clear();
      getRecycledViewPool().onAdapterChanged(paramAdapter1, paramAdapter2, paramBoolean);
    }
    
    void quickRecycleScrapView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      RecyclerView.ViewHolder.access$1002(paramView, null);
      RecyclerView.ViewHolder.access$1102(paramView, false);
      paramView.clearReturnedFromScrapFlag();
      recycleViewHolderInternal(paramView);
    }
    
    void recycleAndClearCachedViews()
    {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
        recycleCachedViewAt(i);
      }
      this.mCachedViews.clear();
      if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
        RecyclerView.this.mPrefetchRegistry.a();
      }
    }
    
    void recycleCachedViewAt(int paramInt)
    {
      addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)this.mCachedViews.get(paramInt), true);
      this.mCachedViews.remove(paramInt);
    }
    
    public void recycleView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isTmpDetached()) {
        RecyclerView.this.removeDetachedView(paramView, false);
      }
      if (localViewHolder.isScrap()) {
        localViewHolder.unScrap();
      } else if (localViewHolder.wasReturnedFromScrap()) {
        localViewHolder.clearReturnedFromScrapFlag();
      }
      recycleViewHolderInternal(localViewHolder);
    }
    
    void recycleViewHolderInternal(RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool1 = paramViewHolder.isScrap();
      boolean bool2 = false;
      int i = 0;
      if ((!bool1) && (paramViewHolder.itemView.getParent() == null))
      {
        if (!paramViewHolder.isTmpDetached())
        {
          if (!paramViewHolder.shouldIgnore())
          {
            bool2 = RecyclerView.ViewHolder.access$900(paramViewHolder);
            int j;
            if ((RecyclerView.this.mAdapter != null) && (bool2) && (RecyclerView.this.mAdapter.onFailedToRecycleView(paramViewHolder))) {
              j = 1;
            } else {
              j = 0;
            }
            int k;
            if ((j == 0) && (!paramViewHolder.isRecyclable()))
            {
              k = 0;
            }
            else
            {
              if ((this.mViewCacheMax > 0) && (!paramViewHolder.hasAnyOfTheFlags(526)))
              {
                k = this.mCachedViews.size();
                j = k;
                if (k >= this.mViewCacheMax)
                {
                  j = k;
                  if (k > 0)
                  {
                    recycleCachedViewAt(0);
                    j = k - 1;
                  }
                }
                k = j;
                if (RecyclerView.ALLOW_THREAD_GAP_WORK)
                {
                  k = j;
                  if (j > 0)
                  {
                    k = j;
                    if (!RecyclerView.this.mPrefetchRegistry.a(paramViewHolder.mPosition))
                    {
                      j--;
                      while (j >= 0)
                      {
                        k = ((RecyclerView.ViewHolder)this.mCachedViews.get(j)).mPosition;
                        if (!RecyclerView.this.mPrefetchRegistry.a(k)) {
                          break;
                        }
                        j--;
                      }
                      k = j + 1;
                    }
                  }
                }
                this.mCachedViews.add(k, paramViewHolder);
                j = 1;
              }
              else
              {
                j = 0;
              }
              k = j;
              if (j == 0)
              {
                addViewHolderToRecycledViewPool(paramViewHolder, true);
                i = 1;
                k = j;
              }
            }
            RecyclerView.this.mViewInfoStore.g(paramViewHolder);
            if ((k == 0) && (i == 0) && (bool2)) {
              paramViewHolder.mOwnerRecyclerView = null;
            }
            return;
          }
          paramViewHolder = new StringBuilder();
          paramViewHolder.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
          paramViewHolder.append(RecyclerView.this.exceptionLabel());
          throw new IllegalArgumentException(paramViewHolder.toString());
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
        localStringBuilder.append(paramViewHolder);
        localStringBuilder.append(RecyclerView.this.exceptionLabel());
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
      localStringBuilder.append(paramViewHolder.isScrap());
      localStringBuilder.append(" isAttached:");
      if (paramViewHolder.itemView.getParent() != null) {
        bool2 = true;
      }
      localStringBuilder.append(bool2);
      localStringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    void recycleViewInternal(View paramView)
    {
      recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(paramView));
    }
    
    void scrapView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      if ((!paramView.hasAnyOfTheFlags(12)) && (paramView.isUpdated()) && (!RecyclerView.this.canReuseUpdatedViewHolder(paramView)))
      {
        if (this.mChangedScrap == null) {
          this.mChangedScrap = new ArrayList();
        }
        paramView.setScrapContainer(this, true);
        this.mChangedScrap.add(paramView);
      }
      else
      {
        if ((paramView.isInvalid()) && (!paramView.isRemoved()) && (!RecyclerView.this.mAdapter.hasStableIds()))
        {
          paramView = new StringBuilder();
          paramView.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
          paramView.append(RecyclerView.this.exceptionLabel());
          throw new IllegalArgumentException(paramView.toString());
        }
        paramView.setScrapContainer(this, false);
        this.mAttachedScrap.add(paramView);
      }
    }
    
    void setRecycledViewPool(RecyclerView.RecycledViewPool paramRecycledViewPool)
    {
      RecyclerView.RecycledViewPool localRecycledViewPool = this.mRecyclerPool;
      if (localRecycledViewPool != null) {
        localRecycledViewPool.detach();
      }
      this.mRecyclerPool = paramRecycledViewPool;
      if (paramRecycledViewPool != null) {
        this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
      }
    }
    
    void setViewCacheExtension(RecyclerView.ViewCacheExtension paramViewCacheExtension)
    {
      this.mViewCacheExtension = paramViewCacheExtension;
    }
    
    public void setViewCacheSize(int paramInt)
    {
      this.mRequestedCacheMax = paramInt;
      updateViewCacheSize();
    }
    
    RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int paramInt, boolean paramBoolean, long paramLong)
    {
      if ((paramInt >= 0) && (paramInt < RecyclerView.this.mState.getItemCount()))
      {
        boolean bool1 = RecyclerView.this.mState.isPreLayout();
        boolean bool2 = true;
        int i;
        if (bool1)
        {
          localObject1 = getChangedScrapViewForPosition(paramInt);
          if (localObject1 != null) {
            i = 1;
          } else {
            i = 0;
          }
        }
        else
        {
          localObject1 = null;
          i = 0;
        }
        localObject2 = localObject1;
        int j = i;
        if (localObject1 == null)
        {
          localObject1 = getScrapOrHiddenOrCachedHolderForPosition(paramInt, paramBoolean);
          localObject2 = localObject1;
          j = i;
          if (localObject1 != null) {
            if (!validateViewHolderForOffsetPosition((RecyclerView.ViewHolder)localObject1))
            {
              if (!paramBoolean)
              {
                ((RecyclerView.ViewHolder)localObject1).addFlags(4);
                if (((RecyclerView.ViewHolder)localObject1).isScrap())
                {
                  RecyclerView.this.removeDetachedView(((RecyclerView.ViewHolder)localObject1).itemView, false);
                  ((RecyclerView.ViewHolder)localObject1).unScrap();
                }
                else if (((RecyclerView.ViewHolder)localObject1).wasReturnedFromScrap())
                {
                  ((RecyclerView.ViewHolder)localObject1).clearReturnedFromScrapFlag();
                }
                recycleViewHolderInternal((RecyclerView.ViewHolder)localObject1);
              }
              localObject2 = null;
              j = i;
            }
            else
            {
              j = 1;
              localObject2 = localObject1;
            }
          }
        }
        if (localObject2 == null)
        {
          int k = RecyclerView.this.mAdapterHelper.b(paramInt);
          if ((k >= 0) && (k < RecyclerView.this.mAdapter.getItemCount()))
          {
            int m = RecyclerView.this.mAdapter.getItemViewType(k);
            i = j;
            if (RecyclerView.this.mAdapter.hasStableIds())
            {
              localObject1 = getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(k), m, paramBoolean);
              localObject2 = localObject1;
              i = j;
              if (localObject1 != null)
              {
                ((RecyclerView.ViewHolder)localObject1).mPosition = k;
                i = 1;
                localObject2 = localObject1;
              }
            }
            localObject1 = localObject2;
            if (localObject2 == null)
            {
              Object localObject3 = this.mViewCacheExtension;
              localObject1 = localObject2;
              if (localObject3 != null)
              {
                localObject3 = ((RecyclerView.ViewCacheExtension)localObject3).getViewForPositionAndType(this, paramInt, m);
                localObject1 = localObject2;
                if (localObject3 != null)
                {
                  localObject1 = RecyclerView.this.getChildViewHolder((View)localObject3);
                  if (localObject1 != null)
                  {
                    if (((RecyclerView.ViewHolder)localObject1).shouldIgnore())
                    {
                      localObject2 = new StringBuilder();
                      ((StringBuilder)localObject2).append("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                      ((StringBuilder)localObject2).append(RecyclerView.this.exceptionLabel());
                      throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
                    }
                  }
                  else
                  {
                    localObject2 = new StringBuilder();
                    ((StringBuilder)localObject2).append("getViewForPositionAndType returned a view which does not have a ViewHolder");
                    ((StringBuilder)localObject2).append(RecyclerView.this.exceptionLabel());
                    throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
                  }
                }
              }
            }
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              localObject1 = getRecycledViewPool().getRecycledView(m);
              localObject2 = localObject1;
              if (localObject1 != null)
              {
                ((RecyclerView.ViewHolder)localObject1).resetInternal();
                localObject2 = localObject1;
                if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST)
                {
                  invalidateDisplayListInt((RecyclerView.ViewHolder)localObject1);
                  localObject2 = localObject1;
                }
              }
            }
            if (localObject2 == null)
            {
              long l1 = RecyclerView.this.getNanoTime();
              if ((paramLong != Long.MAX_VALUE) && (!this.mRecyclerPool.willCreateInTime(m, l1, paramLong))) {
                return null;
              }
              localObject2 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, m);
              if (RecyclerView.ALLOW_THREAD_GAP_WORK)
              {
                localObject1 = RecyclerView.findNestedRecyclerView(((RecyclerView.ViewHolder)localObject2).itemView);
                if (localObject1 != null) {
                  ((RecyclerView.ViewHolder)localObject2).mNestedRecyclerView = new WeakReference(localObject1);
                }
              }
              long l2 = RecyclerView.this.getNanoTime();
              this.mRecyclerPool.factorInCreateTime(m, l2 - l1);
              j = i;
            }
            else
            {
              j = i;
            }
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Inconsistency detected. Invalid item position ");
            ((StringBuilder)localObject2).append(paramInt);
            ((StringBuilder)localObject2).append("(offset:");
            ((StringBuilder)localObject2).append(k);
            ((StringBuilder)localObject2).append(").");
            ((StringBuilder)localObject2).append("state:");
            ((StringBuilder)localObject2).append(RecyclerView.this.mState.getItemCount());
            ((StringBuilder)localObject2).append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(((StringBuilder)localObject2).toString());
          }
        }
        if ((j != 0) && (!RecyclerView.this.mState.isPreLayout()) && (((RecyclerView.ViewHolder)localObject2).hasAnyOfTheFlags(8192)))
        {
          ((RecyclerView.ViewHolder)localObject2).setFlags(0, 8192);
          if (RecyclerView.this.mState.mRunSimpleAnimations)
          {
            i = RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations((RecyclerView.ViewHolder)localObject2);
            localObject1 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (RecyclerView.ViewHolder)localObject2, i | 0x1000, ((RecyclerView.ViewHolder)localObject2).getUnmodifiedPayloads());
            RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((RecyclerView.ViewHolder)localObject2, (RecyclerView.ItemAnimator.ItemHolderInfo)localObject1);
          }
        }
        if ((RecyclerView.this.mState.isPreLayout()) && (((RecyclerView.ViewHolder)localObject2).isBound())) {
          ((RecyclerView.ViewHolder)localObject2).mPreLayoutPosition = paramInt;
        } else {
          if ((!((RecyclerView.ViewHolder)localObject2).isBound()) || (((RecyclerView.ViewHolder)localObject2).needsUpdate()) || (((RecyclerView.ViewHolder)localObject2).isInvalid())) {
            break label909;
          }
        }
        paramBoolean = false;
        break label929;
        label909:
        paramBoolean = tryBindViewHolderByDeadline((RecyclerView.ViewHolder)localObject2, RecyclerView.this.mAdapterHelper.b(paramInt), paramInt, paramLong);
        label929:
        Object localObject1 = ((RecyclerView.ViewHolder)localObject2).itemView.getLayoutParams();
        if (localObject1 == null)
        {
          localObject1 = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
          ((RecyclerView.ViewHolder)localObject2).itemView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        }
        else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)localObject1))
        {
          localObject1 = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)localObject1);
          ((RecyclerView.ViewHolder)localObject2).itemView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        }
        else
        {
          localObject1 = (RecyclerView.LayoutParams)localObject1;
        }
        ((RecyclerView.LayoutParams)localObject1).mViewHolder = ((RecyclerView.ViewHolder)localObject2);
        if ((j != 0) && (paramBoolean)) {
          paramBoolean = bool2;
        } else {
          paramBoolean = false;
        }
        ((RecyclerView.LayoutParams)localObject1).mPendingInvalidate = paramBoolean;
        return (RecyclerView.ViewHolder)localObject2;
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Invalid item position ");
      ((StringBuilder)localObject2).append(paramInt);
      ((StringBuilder)localObject2).append("(");
      ((StringBuilder)localObject2).append(paramInt);
      ((StringBuilder)localObject2).append("). Item count:");
      ((StringBuilder)localObject2).append(RecyclerView.this.mState.getItemCount());
      ((StringBuilder)localObject2).append(RecyclerView.this.exceptionLabel());
      throw new IndexOutOfBoundsException(((StringBuilder)localObject2).toString());
    }
    
    void unscrapView(RecyclerView.ViewHolder paramViewHolder)
    {
      if (RecyclerView.ViewHolder.access$1100(paramViewHolder)) {
        this.mChangedScrap.remove(paramViewHolder);
      } else {
        this.mAttachedScrap.remove(paramViewHolder);
      }
      RecyclerView.ViewHolder.access$1002(paramViewHolder, null);
      RecyclerView.ViewHolder.access$1102(paramViewHolder, false);
      paramViewHolder.clearReturnedFromScrapFlag();
    }
    
    void updateViewCacheSize()
    {
      if (RecyclerView.this.mLayout != null) {
        i = RecyclerView.this.mLayout.mPrefetchMaxCountObserved;
      } else {
        i = 0;
      }
      this.mViewCacheMax = (this.mRequestedCacheMax + i);
      for (int i = this.mCachedViews.size() - 1; (i >= 0) && (this.mCachedViews.size() > this.mViewCacheMax); i--) {
        recycleCachedViewAt(i);
      }
    }
    
    boolean validateViewHolderForOffsetPosition(RecyclerView.ViewHolder paramViewHolder)
    {
      if (paramViewHolder.isRemoved()) {
        return RecyclerView.this.mState.isPreLayout();
      }
      if ((paramViewHolder.mPosition >= 0) && (paramViewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()))
      {
        boolean bool1 = RecyclerView.this.mState.isPreLayout();
        boolean bool2 = false;
        if ((!bool1) && (RecyclerView.this.mAdapter.getItemViewType(paramViewHolder.mPosition) != paramViewHolder.getItemViewType())) {
          return false;
        }
        if (RecyclerView.this.mAdapter.hasStableIds())
        {
          if (paramViewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(paramViewHolder.mPosition)) {
            bool2 = true;
          }
          return bool2;
        }
        return true;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
      localStringBuilder.append(paramViewHolder);
      localStringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IndexOutOfBoundsException(localStringBuilder.toString());
    }
    
    void viewRangeUpdate(int paramInt1, int paramInt2)
    {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(i);
        if (localViewHolder != null)
        {
          int j = localViewHolder.mPosition;
          if ((j >= paramInt1) && (j < paramInt2 + paramInt1))
          {
            localViewHolder.addFlags(2);
            recycleCachedViewAt(i);
          }
        }
      }
    }
  }
  
  public static abstract interface RecyclerListener
  {
    public abstract void onViewRecycled(RecyclerView.ViewHolder paramViewHolder);
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public RecyclerView.SavedState a(Parcel paramAnonymousParcel)
      {
        return new RecyclerView.SavedState(paramAnonymousParcel, null);
      }
      
      public RecyclerView.SavedState a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new RecyclerView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public RecyclerView.SavedState[] a(int paramAnonymousInt)
      {
        return new RecyclerView.SavedState[paramAnonymousInt];
      }
    };
    Parcelable mLayoutState;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      if (paramClassLoader == null) {
        paramClassLoader = RecyclerView.LayoutManager.class.getClassLoader();
      }
      this.mLayoutState = paramParcel.readParcelable(paramClassLoader);
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    void copyFrom(SavedState paramSavedState)
    {
      this.mLayoutState = paramSavedState.mLayoutState;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeParcelable(this.mLayoutState, 0);
    }
  }
  
  public static class SimpleOnItemTouchListener
    implements RecyclerView.OnItemTouchListener
  {
    public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean) {}
    
    public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {}
  }
  
  public static abstract class SmoothScroller
  {
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mPendingInitialRun;
    private RecyclerView mRecyclerView;
    private final Action mRecyclingAction = new Action(0, 0);
    private boolean mRunning;
    private int mTargetPosition = -1;
    private View mTargetView;
    
    private void onAnimation(int paramInt1, int paramInt2)
    {
      RecyclerView localRecyclerView = this.mRecyclerView;
      if ((!this.mRunning) || (this.mTargetPosition == -1) || (localRecyclerView == null)) {
        stop();
      }
      this.mPendingInitialRun = false;
      View localView = this.mTargetView;
      if (localView != null) {
        if (getChildPosition(localView) == this.mTargetPosition)
        {
          onTargetFound(this.mTargetView, localRecyclerView.mState, this.mRecyclingAction);
          this.mRecyclingAction.runIfNecessary(localRecyclerView);
          stop();
        }
        else
        {
          Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
          this.mTargetView = null;
        }
      }
      if (this.mRunning)
      {
        onSeekTargetStep(paramInt1, paramInt2, localRecyclerView.mState, this.mRecyclingAction);
        boolean bool = this.mRecyclingAction.hasJumpTarget();
        this.mRecyclingAction.runIfNecessary(localRecyclerView);
        if (bool) {
          if (this.mRunning)
          {
            this.mPendingInitialRun = true;
            localRecyclerView.mViewFlinger.a();
          }
          else
          {
            stop();
          }
        }
      }
    }
    
    public View findViewByPosition(int paramInt)
    {
      return this.mRecyclerView.mLayout.findViewByPosition(paramInt);
    }
    
    public int getChildCount()
    {
      return this.mRecyclerView.mLayout.getChildCount();
    }
    
    public int getChildPosition(View paramView)
    {
      return this.mRecyclerView.getChildLayoutPosition(paramView);
    }
    
    public RecyclerView.LayoutManager getLayoutManager()
    {
      return this.mLayoutManager;
    }
    
    public int getTargetPosition()
    {
      return this.mTargetPosition;
    }
    
    @Deprecated
    public void instantScrollToPosition(int paramInt)
    {
      this.mRecyclerView.scrollToPosition(paramInt);
    }
    
    public boolean isPendingInitialRun()
    {
      return this.mPendingInitialRun;
    }
    
    public boolean isRunning()
    {
      return this.mRunning;
    }
    
    protected void normalize(PointF paramPointF)
    {
      float f = (float)Math.sqrt(paramPointF.x * paramPointF.x + paramPointF.y * paramPointF.y);
      paramPointF.x /= f;
      paramPointF.y /= f;
    }
    
    protected void onChildAttachedToWindow(View paramView)
    {
      if (getChildPosition(paramView) == getTargetPosition()) {
        this.mTargetView = paramView;
      }
    }
    
    protected abstract void onSeekTargetStep(int paramInt1, int paramInt2, RecyclerView.State paramState, Action paramAction);
    
    protected abstract void onStart();
    
    protected abstract void onStop();
    
    protected abstract void onTargetFound(View paramView, RecyclerView.State paramState, Action paramAction);
    
    public void setTargetPosition(int paramInt)
    {
      this.mTargetPosition = paramInt;
    }
    
    void start(RecyclerView paramRecyclerView, RecyclerView.LayoutManager paramLayoutManager)
    {
      this.mRecyclerView = paramRecyclerView;
      this.mLayoutManager = paramLayoutManager;
      if (this.mTargetPosition != -1)
      {
        RecyclerView.State.access$1302(this.mRecyclerView.mState, this.mTargetPosition);
        this.mRunning = true;
        this.mPendingInitialRun = true;
        this.mTargetView = findViewByPosition(getTargetPosition());
        onStart();
        this.mRecyclerView.mViewFlinger.a();
        return;
      }
      throw new IllegalArgumentException("Invalid target position");
    }
    
    protected final void stop()
    {
      if (!this.mRunning) {
        return;
      }
      onStop();
      RecyclerView.State.access$1302(this.mRecyclerView.mState, -1);
      this.mTargetView = null;
      this.mTargetPosition = -1;
      this.mPendingInitialRun = false;
      this.mRunning = false;
      this.mLayoutManager.onSmoothScrollerStopped(this);
      this.mLayoutManager = null;
      this.mRecyclerView = null;
    }
    
    public static class Action
    {
      public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
      private boolean mChanged = false;
      private int mConsecutiveUpdates = 0;
      private int mDuration;
      private int mDx;
      private int mDy;
      private Interpolator mInterpolator;
      private int mJumpToPosition = -1;
      
      public Action(int paramInt1, int paramInt2)
      {
        this(paramInt1, paramInt2, Integer.MIN_VALUE, null);
      }
      
      public Action(int paramInt1, int paramInt2, int paramInt3)
      {
        this(paramInt1, paramInt2, paramInt3, null);
      }
      
      public Action(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        this.mDx = paramInt1;
        this.mDy = paramInt2;
        this.mDuration = paramInt3;
        this.mInterpolator = paramInterpolator;
      }
      
      private void validate()
      {
        if ((this.mInterpolator != null) && (this.mDuration < 1)) {
          throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        }
        if (this.mDuration >= 1) {
          return;
        }
        throw new IllegalStateException("Scroll duration must be a positive number");
      }
      
      public int getDuration()
      {
        return this.mDuration;
      }
      
      public int getDx()
      {
        return this.mDx;
      }
      
      public int getDy()
      {
        return this.mDy;
      }
      
      public Interpolator getInterpolator()
      {
        return this.mInterpolator;
      }
      
      boolean hasJumpTarget()
      {
        boolean bool;
        if (this.mJumpToPosition >= 0) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void jumpTo(int paramInt)
      {
        this.mJumpToPosition = paramInt;
      }
      
      void runIfNecessary(RecyclerView paramRecyclerView)
      {
        int i = this.mJumpToPosition;
        if (i >= 0)
        {
          this.mJumpToPosition = -1;
          paramRecyclerView.jumpToPositionForSmoothScroller(i);
          this.mChanged = false;
          return;
        }
        if (this.mChanged)
        {
          validate();
          if (this.mInterpolator == null)
          {
            if (this.mDuration == Integer.MIN_VALUE) {
              paramRecyclerView.mViewFlinger.b(this.mDx, this.mDy);
            } else {
              paramRecyclerView.mViewFlinger.a(this.mDx, this.mDy, this.mDuration);
            }
          }
          else {
            paramRecyclerView.mViewFlinger.a(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
          }
          this.mConsecutiveUpdates += 1;
          if (this.mConsecutiveUpdates > 10) {
            Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
          }
          this.mChanged = false;
        }
        else
        {
          this.mConsecutiveUpdates = 0;
        }
      }
      
      public void setDuration(int paramInt)
      {
        this.mChanged = true;
        this.mDuration = paramInt;
      }
      
      public void setDx(int paramInt)
      {
        this.mChanged = true;
        this.mDx = paramInt;
      }
      
      public void setDy(int paramInt)
      {
        this.mChanged = true;
        this.mDy = paramInt;
      }
      
      public void setInterpolator(Interpolator paramInterpolator)
      {
        this.mChanged = true;
        this.mInterpolator = paramInterpolator;
      }
      
      public void update(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        this.mDx = paramInt1;
        this.mDy = paramInt2;
        this.mDuration = paramInt3;
        this.mInterpolator = paramInterpolator;
        this.mChanged = true;
      }
    }
    
    public static abstract interface ScrollVectorProvider
    {
      public abstract PointF computeScrollVectorForPosition(int paramInt);
    }
  }
  
  public static class State
  {
    static final int STEP_ANIMATIONS = 4;
    static final int STEP_LAYOUT = 2;
    static final int STEP_START = 1;
    private SparseArray<Object> mData;
    int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    long mFocusedItemId;
    int mFocusedItemPosition;
    int mFocusedSubChildId;
    boolean mInPreLayout = false;
    boolean mIsMeasuring = false;
    int mItemCount = 0;
    int mLayoutStep = 1;
    int mPreviousLayoutItemCount = 0;
    int mRemainingScrollHorizontal;
    int mRemainingScrollVertical;
    boolean mRunPredictiveAnimations = false;
    boolean mRunSimpleAnimations = false;
    boolean mStructureChanged = false;
    private int mTargetPosition = -1;
    boolean mTrackOldChangeHolders = false;
    
    void assertLayoutStep(int paramInt)
    {
      if ((this.mLayoutStep & paramInt) != 0) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Layout state should be one of ");
      localStringBuilder.append(Integer.toBinaryString(paramInt));
      localStringBuilder.append(" but it is ");
      localStringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public boolean didStructureChange()
    {
      return this.mStructureChanged;
    }
    
    public <T> T get(int paramInt)
    {
      SparseArray localSparseArray = this.mData;
      if (localSparseArray == null) {
        return null;
      }
      return (T)localSparseArray.get(paramInt);
    }
    
    public int getItemCount()
    {
      int i;
      if (this.mInPreLayout) {
        i = this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
      } else {
        i = this.mItemCount;
      }
      return i;
    }
    
    public int getRemainingScrollHorizontal()
    {
      return this.mRemainingScrollHorizontal;
    }
    
    public int getRemainingScrollVertical()
    {
      return this.mRemainingScrollVertical;
    }
    
    public int getTargetScrollPosition()
    {
      return this.mTargetPosition;
    }
    
    public boolean hasTargetScrollPosition()
    {
      boolean bool;
      if (this.mTargetPosition != -1) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isMeasuring()
    {
      return this.mIsMeasuring;
    }
    
    public boolean isPreLayout()
    {
      return this.mInPreLayout;
    }
    
    void prepareForNestedPrefetch(RecyclerView.Adapter paramAdapter)
    {
      this.mLayoutStep = 1;
      this.mItemCount = paramAdapter.getItemCount();
      this.mInPreLayout = false;
      this.mTrackOldChangeHolders = false;
      this.mIsMeasuring = false;
    }
    
    public void put(int paramInt, Object paramObject)
    {
      if (this.mData == null) {
        this.mData = new SparseArray();
      }
      this.mData.put(paramInt, paramObject);
    }
    
    public void remove(int paramInt)
    {
      SparseArray localSparseArray = this.mData;
      if (localSparseArray == null) {
        return;
      }
      localSparseArray.remove(paramInt);
    }
    
    State reset()
    {
      this.mTargetPosition = -1;
      SparseArray localSparseArray = this.mData;
      if (localSparseArray != null) {
        localSparseArray.clear();
      }
      this.mItemCount = 0;
      this.mStructureChanged = false;
      this.mIsMeasuring = false;
      return this;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("State{mTargetPosition=");
      localStringBuilder.append(this.mTargetPosition);
      localStringBuilder.append(", mData=");
      localStringBuilder.append(this.mData);
      localStringBuilder.append(", mItemCount=");
      localStringBuilder.append(this.mItemCount);
      localStringBuilder.append(", mPreviousLayoutItemCount=");
      localStringBuilder.append(this.mPreviousLayoutItemCount);
      localStringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
      localStringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
      localStringBuilder.append(", mStructureChanged=");
      localStringBuilder.append(this.mStructureChanged);
      localStringBuilder.append(", mInPreLayout=");
      localStringBuilder.append(this.mInPreLayout);
      localStringBuilder.append(", mRunSimpleAnimations=");
      localStringBuilder.append(this.mRunSimpleAnimations);
      localStringBuilder.append(", mRunPredictiveAnimations=");
      localStringBuilder.append(this.mRunPredictiveAnimations);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
    
    public boolean willRunPredictiveAnimations()
    {
      return this.mRunPredictiveAnimations;
    }
    
    public boolean willRunSimpleAnimations()
    {
      return this.mRunSimpleAnimations;
    }
  }
  
  public static abstract class ViewCacheExtension
  {
    public abstract View getViewForPositionAndType(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2);
  }
  
  public static abstract class ViewHolder
  {
    static final int FLAG_ADAPTER_FULLUPDATE = 1024;
    static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
    static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
    static final int FLAG_BOUND = 1;
    static final int FLAG_IGNORE = 128;
    static final int FLAG_INVALID = 4;
    static final int FLAG_MOVED = 2048;
    static final int FLAG_NOT_RECYCLABLE = 16;
    static final int FLAG_REMOVED = 8;
    static final int FLAG_RETURNED_FROM_SCRAP = 32;
    static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
    static final int FLAG_TMP_DETACHED = 256;
    static final int FLAG_UPDATE = 2;
    private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
    static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
    public final View itemView;
    private int mFlags;
    private boolean mInChangeScrap = false;
    private int mIsRecyclableCount = 0;
    long mItemId = -1L;
    int mItemViewType = -1;
    WeakReference<RecyclerView> mNestedRecyclerView;
    int mOldPosition = -1;
    RecyclerView mOwnerRecyclerView;
    List<Object> mPayloads = null;
    int mPendingAccessibilityState = -1;
    int mPosition = -1;
    int mPreLayoutPosition = -1;
    private RecyclerView.Recycler mScrapContainer = null;
    ViewHolder mShadowedHolder = null;
    ViewHolder mShadowingHolder = null;
    List<Object> mUnmodifiedPayloads = null;
    private int mWasImportantForAccessibilityBeforeHidden = 0;
    
    public ViewHolder(View paramView)
    {
      if (paramView != null)
      {
        this.itemView = paramView;
        return;
      }
      throw new IllegalArgumentException("itemView may not be null");
    }
    
    private void createPayloadsIfNeeded()
    {
      if (this.mPayloads == null)
      {
        this.mPayloads = new ArrayList();
        this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
      }
    }
    
    private boolean doesTransientStatePreventRecycling()
    {
      boolean bool;
      if (((this.mFlags & 0x10) == 0) && (ViewCompat.hasTransientState(this.itemView))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private void onEnteredHiddenState(RecyclerView paramRecyclerView)
    {
      this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
      paramRecyclerView.setChildImportantForAccessibilityInternal(this, 4);
    }
    
    private void onLeftHiddenState(RecyclerView paramRecyclerView)
    {
      paramRecyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
      this.mWasImportantForAccessibilityBeforeHidden = 0;
    }
    
    private boolean shouldBeKeptAsChild()
    {
      boolean bool;
      if ((this.mFlags & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void addChangePayload(Object paramObject)
    {
      if (paramObject == null)
      {
        addFlags(1024);
      }
      else if ((0x400 & this.mFlags) == 0)
      {
        createPayloadsIfNeeded();
        this.mPayloads.add(paramObject);
      }
    }
    
    void addFlags(int paramInt)
    {
      this.mFlags = (paramInt | this.mFlags);
    }
    
    void clearOldPosition()
    {
      this.mOldPosition = -1;
      this.mPreLayoutPosition = -1;
    }
    
    void clearPayload()
    {
      List localList = this.mPayloads;
      if (localList != null) {
        localList.clear();
      }
      this.mFlags &= 0xFBFF;
    }
    
    void clearReturnedFromScrapFlag()
    {
      this.mFlags &= 0xFFFFFFDF;
    }
    
    void clearTmpDetachFlag()
    {
      this.mFlags &= 0xFEFF;
    }
    
    void flagRemovedAndOffsetPosition(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      addFlags(8);
      offsetPosition(paramInt2, paramBoolean);
      this.mPosition = paramInt1;
    }
    
    public final int getAdapterPosition()
    {
      RecyclerView localRecyclerView = this.mOwnerRecyclerView;
      if (localRecyclerView == null) {
        return -1;
      }
      return localRecyclerView.getAdapterPositionFor(this);
    }
    
    public final long getItemId()
    {
      return this.mItemId;
    }
    
    public final int getItemViewType()
    {
      return this.mItemViewType;
    }
    
    public final int getLayoutPosition()
    {
      int i = this.mPreLayoutPosition;
      int j = i;
      if (i == -1) {
        j = this.mPosition;
      }
      return j;
    }
    
    public final int getOldPosition()
    {
      return this.mOldPosition;
    }
    
    @Deprecated
    public final int getPosition()
    {
      int i = this.mPreLayoutPosition;
      int j = i;
      if (i == -1) {
        j = this.mPosition;
      }
      return j;
    }
    
    List<Object> getUnmodifiedPayloads()
    {
      if ((this.mFlags & 0x400) == 0)
      {
        List localList = this.mPayloads;
        if ((localList != null) && (localList.size() != 0)) {
          return this.mUnmodifiedPayloads;
        }
        return FULLUPDATE_PAYLOADS;
      }
      return FULLUPDATE_PAYLOADS;
    }
    
    boolean hasAnyOfTheFlags(int paramInt)
    {
      boolean bool;
      if ((paramInt & this.mFlags) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isAdapterPositionUnknown()
    {
      boolean bool;
      if (((this.mFlags & 0x200) == 0) && (!isInvalid())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    boolean isBound()
    {
      int i = this.mFlags;
      boolean bool = true;
      if ((i & 0x1) == 0) {
        bool = false;
      }
      return bool;
    }
    
    boolean isInvalid()
    {
      boolean bool;
      if ((this.mFlags & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final boolean isRecyclable()
    {
      boolean bool;
      if (((this.mFlags & 0x10) == 0) && (!ViewCompat.hasTransientState(this.itemView))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isRemoved()
    {
      boolean bool;
      if ((this.mFlags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isScrap()
    {
      boolean bool;
      if (this.mScrapContainer != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isTmpDetached()
    {
      boolean bool;
      if ((this.mFlags & 0x100) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isUpdated()
    {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean needsUpdate()
    {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void offsetPosition(int paramInt, boolean paramBoolean)
    {
      if (this.mOldPosition == -1) {
        this.mOldPosition = this.mPosition;
      }
      if (this.mPreLayoutPosition == -1) {
        this.mPreLayoutPosition = this.mPosition;
      }
      if (paramBoolean) {
        this.mPreLayoutPosition += paramInt;
      }
      this.mPosition += paramInt;
      if (this.itemView.getLayoutParams() != null) {
        ((RecyclerView.LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
      }
    }
    
    void resetInternal()
    {
      this.mFlags = 0;
      this.mPosition = -1;
      this.mOldPosition = -1;
      this.mItemId = -1L;
      this.mPreLayoutPosition = -1;
      this.mIsRecyclableCount = 0;
      this.mShadowedHolder = null;
      this.mShadowingHolder = null;
      clearPayload();
      this.mWasImportantForAccessibilityBeforeHidden = 0;
      this.mPendingAccessibilityState = -1;
      RecyclerView.clearNestedRecyclerViewIfNotNested(this);
    }
    
    void saveOldPosition()
    {
      if (this.mOldPosition == -1) {
        this.mOldPosition = this.mPosition;
      }
    }
    
    void setFlags(int paramInt1, int paramInt2)
    {
      this.mFlags = (paramInt1 & paramInt2 | this.mFlags & (paramInt2 ^ 0xFFFFFFFF));
    }
    
    public final void setIsRecyclable(boolean paramBoolean)
    {
      if (paramBoolean) {
        i = this.mIsRecyclableCount - 1;
      } else {
        i = this.mIsRecyclableCount + 1;
      }
      this.mIsRecyclableCount = i;
      int i = this.mIsRecyclableCount;
      if (i < 0)
      {
        this.mIsRecyclableCount = 0;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
        localStringBuilder.append(this);
        Log.e("View", localStringBuilder.toString());
      }
      else if ((!paramBoolean) && (i == 1))
      {
        this.mFlags |= 0x10;
      }
      else if ((paramBoolean) && (this.mIsRecyclableCount == 0))
      {
        this.mFlags &= 0xFFFFFFEF;
      }
    }
    
    void setScrapContainer(RecyclerView.Recycler paramRecycler, boolean paramBoolean)
    {
      this.mScrapContainer = paramRecycler;
      this.mInChangeScrap = paramBoolean;
    }
    
    boolean shouldIgnore()
    {
      boolean bool;
      if ((this.mFlags & 0x80) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void stopIgnoring()
    {
      this.mFlags &= 0xFF7F;
    }
    
    public String toString()
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("ViewHolder{");
      ((StringBuilder)localObject).append(Integer.toHexString(hashCode()));
      ((StringBuilder)localObject).append(" position=");
      ((StringBuilder)localObject).append(this.mPosition);
      ((StringBuilder)localObject).append(" id=");
      ((StringBuilder)localObject).append(this.mItemId);
      ((StringBuilder)localObject).append(", oldPos=");
      ((StringBuilder)localObject).append(this.mOldPosition);
      ((StringBuilder)localObject).append(", pLpos:");
      ((StringBuilder)localObject).append(this.mPreLayoutPosition);
      StringBuilder localStringBuilder = new StringBuilder(((StringBuilder)localObject).toString());
      if (isScrap())
      {
        localStringBuilder.append(" scrap ");
        if (this.mInChangeScrap) {
          localObject = "[changeScrap]";
        } else {
          localObject = "[attachedScrap]";
        }
        localStringBuilder.append((String)localObject);
      }
      if (isInvalid()) {
        localStringBuilder.append(" invalid");
      }
      if (!isBound()) {
        localStringBuilder.append(" unbound");
      }
      if (needsUpdate()) {
        localStringBuilder.append(" update");
      }
      if (isRemoved()) {
        localStringBuilder.append(" removed");
      }
      if (shouldIgnore()) {
        localStringBuilder.append(" ignored");
      }
      if (isTmpDetached()) {
        localStringBuilder.append(" tmpDetached");
      }
      if (!isRecyclable())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(" not recyclable(");
        ((StringBuilder)localObject).append(this.mIsRecyclableCount);
        ((StringBuilder)localObject).append(")");
        localStringBuilder.append(((StringBuilder)localObject).toString());
      }
      if (isAdapterPositionUnknown()) {
        localStringBuilder.append(" undefined adapter position");
      }
      if (this.itemView.getParent() == null) {
        localStringBuilder.append(" no parent");
      }
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    void unScrap()
    {
      this.mScrapContainer.unscrapView(this);
    }
    
    boolean wasReturnedFromScrap()
    {
      boolean bool;
      if ((this.mFlags & 0x20) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  static class a
    extends Observable<RecyclerView.AdapterDataObserver>
  {
    public void a(int paramInt1, int paramInt2)
    {
      a(paramInt1, paramInt2, null);
    }
    
    public void a(int paramInt1, int paramInt2, Object paramObject)
    {
      for (int i = this.mObservers.size() - 1; i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(paramInt1, paramInt2, paramObject);
      }
    }
    
    public boolean a()
    {
      return this.mObservers.isEmpty() ^ true;
    }
    
    public void b()
    {
      for (int i = this.mObservers.size() - 1; i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onChanged();
      }
    }
    
    public void b(int paramInt1, int paramInt2)
    {
      for (int i = this.mObservers.size() - 1; i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(paramInt1, paramInt2);
      }
    }
    
    public void c(int paramInt1, int paramInt2)
    {
      for (int i = this.mObservers.size() - 1; i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(paramInt1, paramInt2);
      }
    }
    
    public void d(int paramInt1, int paramInt2)
    {
      for (int i = this.mObservers.size() - 1; i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(paramInt1, paramInt2, 1);
      }
    }
  }
  
  private class b
    implements RecyclerView.ItemAnimator.a
  {
    b() {}
    
    public void a(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if ((paramViewHolder.mShadowedHolder != null) && (paramViewHolder.mShadowingHolder == null)) {
        paramViewHolder.mShadowedHolder = null;
      }
      paramViewHolder.mShadowingHolder = null;
      if ((!paramViewHolder.shouldBeKeptAsChild()) && (!RecyclerView.this.removeAnimatingView(paramViewHolder.itemView)) && (paramViewHolder.isTmpDetached())) {
        RecyclerView.this.removeDetachedView(paramViewHolder.itemView, false);
      }
    }
  }
  
  private class c
    extends RecyclerView.AdapterDataObserver
  {
    c() {}
    
    void a()
    {
      RecyclerView localRecyclerView;
      if ((RecyclerView.POST_UPDATES_ON_ANIMATION) && (RecyclerView.this.mHasFixedSize) && (RecyclerView.this.mIsAttached))
      {
        localRecyclerView = RecyclerView.this;
        ViewCompat.postOnAnimation(localRecyclerView, localRecyclerView.mUpdateChildViewsRunnable);
      }
      else
      {
        localRecyclerView = RecyclerView.this;
        localRecyclerView.mAdapterUpdateDuringMeasure = true;
        localRecyclerView.requestLayout();
      }
    }
    
    public void onChanged()
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      RecyclerView.this.mState.mStructureChanged = true;
      RecyclerView.this.setDataSetChangedAfterLayout();
      if (!RecyclerView.this.mAdapterHelper.d()) {
        RecyclerView.this.requestLayout();
      }
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.a(paramInt1, paramInt2, paramObject)) {
        a();
      }
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.b(paramInt1, paramInt2)) {
        a();
      }
    }
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.a(paramInt1, paramInt2, paramInt3)) {
        a();
      }
    }
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.c(paramInt1, paramInt2)) {
        a();
      }
    }
  }
  
  class d
    implements Runnable
  {
    Interpolator a = RecyclerView.sQuinticInterpolator;
    private int c;
    private int d;
    private OverScroller e = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
    private boolean f = false;
    private boolean g = false;
    
    d() {}
    
    private float a(float paramFloat)
    {
      return (float)Math.sin((paramFloat - 0.5F) * 0.47123894F);
    }
    
    private int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int i = Math.abs(paramInt1);
      int j = Math.abs(paramInt2);
      int k;
      if (i > j) {
        k = 1;
      } else {
        k = 0;
      }
      paramInt3 = (int)Math.sqrt(paramInt3 * paramInt3 + paramInt4 * paramInt4);
      paramInt2 = (int)Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
      if (k != 0) {
        paramInt1 = RecyclerView.this.getWidth();
      } else {
        paramInt1 = RecyclerView.this.getHeight();
      }
      paramInt4 = paramInt1 / 2;
      float f1 = paramInt2;
      float f2 = paramInt1;
      float f3 = Math.min(1.0F, f1 * 1.0F / f2);
      f1 = paramInt4;
      f3 = a(f3);
      if (paramInt3 > 0)
      {
        paramInt1 = Math.round(Math.abs((f1 + f3 * f1) / paramInt3) * 1000.0F) * 4;
      }
      else
      {
        if (k != 0) {
          paramInt1 = i;
        } else {
          paramInt1 = j;
        }
        paramInt1 = (int)((paramInt1 / f2 + 1.0F) * 300.0F);
      }
      return Math.min(paramInt1, 2000);
    }
    
    private void c()
    {
      this.g = false;
      this.f = true;
    }
    
    private void d()
    {
      this.f = false;
      if (this.g) {
        a();
      }
    }
    
    void a()
    {
      if (this.f)
      {
        this.g = true;
      }
      else
      {
        RecyclerView.this.removeCallbacks(this);
        ViewCompat.postOnAnimation(RecyclerView.this, this);
      }
    }
    
    public void a(int paramInt1, int paramInt2)
    {
      RecyclerView.this.setScrollState(2);
      this.d = 0;
      this.c = 0;
      this.e.fling(0, 0, paramInt1, paramInt2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
      a();
    }
    
    public void a(int paramInt1, int paramInt2, int paramInt3)
    {
      a(paramInt1, paramInt2, paramInt3, RecyclerView.sQuinticInterpolator);
    }
    
    public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      a(paramInt1, paramInt2, b(paramInt1, paramInt2, paramInt3, paramInt4));
    }
    
    public void a(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
    {
      if (this.a != paramInterpolator)
      {
        this.a = paramInterpolator;
        this.e = new OverScroller(RecyclerView.this.getContext(), paramInterpolator);
      }
      RecyclerView.this.setScrollState(2);
      this.d = 0;
      this.c = 0;
      this.e.startScroll(0, 0, paramInt1, paramInt2, paramInt3);
      if (Build.VERSION.SDK_INT < 23) {
        this.e.computeScrollOffset();
      }
      a();
    }
    
    public void a(int paramInt1, int paramInt2, Interpolator paramInterpolator)
    {
      int i = b(paramInt1, paramInt2, 0, 0);
      Interpolator localInterpolator = paramInterpolator;
      if (paramInterpolator == null) {
        localInterpolator = RecyclerView.sQuinticInterpolator;
      }
      a(paramInt1, paramInt2, i, localInterpolator);
    }
    
    public void b()
    {
      RecyclerView.this.removeCallbacks(this);
      this.e.abortAnimation();
    }
    
    public void b(int paramInt1, int paramInt2)
    {
      a(paramInt1, paramInt2, 0, 0);
    }
    
    public void run()
    {
      if (RecyclerView.this.mLayout == null)
      {
        b();
        return;
      }
      c();
      RecyclerView.this.consumePendingUpdateOperations();
      OverScroller localOverScroller = this.e;
      RecyclerView.SmoothScroller localSmoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
      if (localOverScroller.computeScrollOffset())
      {
        Object localObject = RecyclerView.this.mScrollConsumed;
        int i = localOverScroller.getCurrX();
        int j = localOverScroller.getCurrY();
        int k = i - this.c;
        int m = j - this.d;
        this.c = i;
        this.d = j;
        int n = k;
        int i1 = m;
        if (RecyclerView.this.dispatchNestedPreScroll(k, m, (int[])localObject, null, 1))
        {
          n = k - localObject[0];
          i1 = m - localObject[1];
        }
        int i4;
        int i5;
        int i6;
        int i7;
        if (RecyclerView.this.mAdapter != null)
        {
          RecyclerView.this.eatRequestLayout();
          RecyclerView.this.onEnterLayoutOrScroll();
          TraceCompat.beginSection("RV Scroll");
          localObject = RecyclerView.this;
          ((RecyclerView)localObject).fillRemainingScrollValues(((RecyclerView)localObject).mState);
          if (n != 0)
          {
            k = RecyclerView.this.mLayout.scrollHorizontallyBy(n, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            m = n - k;
          }
          else
          {
            k = 0;
            m = 0;
          }
          int i2;
          int i3;
          if (i1 != 0)
          {
            i2 = RecyclerView.this.mLayout.scrollVerticallyBy(i1, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            i3 = i1 - i2;
          }
          else
          {
            i2 = 0;
            i3 = 0;
          }
          TraceCompat.endSection();
          RecyclerView.this.repositionShadowingViews();
          RecyclerView.this.onExitLayoutOrScroll();
          RecyclerView.this.resumeRequestLayout(false);
          i4 = k;
          i5 = m;
          i6 = i2;
          i7 = i3;
          if (localSmoothScroller != null)
          {
            i4 = k;
            i5 = m;
            i6 = i2;
            i7 = i3;
            if (!localSmoothScroller.isPendingInitialRun())
            {
              i4 = k;
              i5 = m;
              i6 = i2;
              i7 = i3;
              if (localSmoothScroller.isRunning())
              {
                i4 = RecyclerView.this.mState.getItemCount();
                if (i4 == 0)
                {
                  localSmoothScroller.stop();
                  i4 = k;
                  i5 = m;
                  i6 = i2;
                  i7 = i3;
                }
                else if (localSmoothScroller.getTargetPosition() >= i4)
                {
                  localSmoothScroller.setTargetPosition(i4 - 1);
                  localSmoothScroller.onAnimation(n - m, i1 - i3);
                  i4 = k;
                  i5 = m;
                  i6 = i2;
                  i7 = i3;
                }
                else
                {
                  localSmoothScroller.onAnimation(n - m, i1 - i3);
                  i4 = k;
                  i5 = m;
                  i6 = i2;
                  i7 = i3;
                }
              }
            }
          }
        }
        else
        {
          i4 = 0;
          i5 = 0;
          i6 = 0;
          i7 = 0;
        }
        if (!RecyclerView.this.mItemDecorations.isEmpty()) {
          RecyclerView.this.invalidate();
        }
        if (RecyclerView.this.getOverScrollMode() != 2) {
          RecyclerView.this.considerReleasingGlowsOnScroll(n, i1);
        }
        if ((!RecyclerView.this.dispatchNestedScroll(i4, i6, i5, i7, null, 1)) && ((i5 != 0) || (i7 != 0)))
        {
          k = (int)localOverScroller.getCurrVelocity();
          if (i5 != i)
          {
            if (i5 < 0) {
              m = -k;
            } else if (i5 > 0) {
              m = k;
            } else {
              m = 0;
            }
          }
          else {
            m = 0;
          }
          if (i7 != j)
          {
            if (i7 < 0) {
              k = -k;
            } else if (i7 <= 0) {
              k = 0;
            }
          }
          else {
            k = 0;
          }
          if (RecyclerView.this.getOverScrollMode() != 2) {
            RecyclerView.this.absorbGlows(m, k);
          }
          if (((m != 0) || (i5 == i) || (localOverScroller.getFinalX() == 0)) && ((k != 0) || (i7 == j) || (localOverScroller.getFinalY() == 0))) {
            localOverScroller.abortAnimation();
          }
        }
        if ((i4 != 0) || (i6 != 0)) {
          RecyclerView.this.dispatchOnScrolled(i4, i6);
        }
        if (!RecyclerView.this.awakenScrollBars()) {
          RecyclerView.this.invalidate();
        }
        if ((i1 != 0) && (RecyclerView.this.mLayout.canScrollVertically()) && (i6 == i1)) {
          m = 1;
        } else {
          m = 0;
        }
        if ((n != 0) && (RecyclerView.this.mLayout.canScrollHorizontally()) && (i4 == n)) {
          k = 1;
        } else {
          k = 0;
        }
        if (((n != 0) || (i1 != 0)) && (k == 0) && (m == 0)) {
          m = 0;
        } else {
          m = 1;
        }
        if ((!localOverScroller.isFinished()) && ((m != 0) || (RecyclerView.this.hasNestedScrollingParent(1))))
        {
          a();
          if (RecyclerView.this.mGapWorker != null) {
            RecyclerView.this.mGapWorker.a(RecyclerView.this, n, i1);
          }
        }
        else
        {
          RecyclerView.this.setScrollState(0);
          if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
            RecyclerView.this.mPrefetchRegistry.a();
          }
          RecyclerView.this.stopNestedScroll(1);
        }
      }
      if (localSmoothScroller != null)
      {
        if (localSmoothScroller.isPendingInitialRun()) {
          localSmoothScroller.onAnimation(0, 0);
        }
        if (!this.g) {
          localSmoothScroller.stop();
        }
      }
      d();
    }
  }
}


/* Location:              ~/android/support/v7/widget/RecyclerView.class
 *
 * Reversed by:           J
 */