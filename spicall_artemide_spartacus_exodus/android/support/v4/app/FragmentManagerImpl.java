package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl
  extends FragmentManager
  implements LayoutInflater.Factory2
{
  static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5F);
  static final Interpolator ACCELERATE_QUINT;
  static final int ANIM_DUR = 220;
  public static final int ANIM_STYLE_CLOSE_ENTER = 3;
  public static final int ANIM_STYLE_CLOSE_EXIT = 4;
  public static final int ANIM_STYLE_FADE_ENTER = 5;
  public static final int ANIM_STYLE_FADE_EXIT = 6;
  public static final int ANIM_STYLE_OPEN_ENTER = 1;
  public static final int ANIM_STYLE_OPEN_EXIT = 2;
  static boolean DEBUG = false;
  static final Interpolator DECELERATE_CUBIC;
  static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5F);
  static final String TAG = "FragmentManager";
  static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  static final String TARGET_STATE_TAG = "android:target_state";
  static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  static final String VIEW_STATE_TAG = "android:view_state";
  static Field sAnimationListenerField;
  SparseArray<Fragment> mActive;
  final ArrayList<Fragment> mAdded = new ArrayList();
  ArrayList<Integer> mAvailBackStackIndices;
  ArrayList<BackStackRecord> mBackStack;
  ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  ArrayList<BackStackRecord> mBackStackIndices;
  FragmentContainer mContainer;
  ArrayList<Fragment> mCreatedMenus;
  int mCurState = 0;
  boolean mDestroyed;
  Runnable mExecCommit = new Runnable()
  {
    public void run()
    {
      FragmentManagerImpl.this.execPendingActions();
    }
  };
  boolean mExecutingActions;
  boolean mHavePendingDeferredStart;
  FragmentHostCallback mHost;
  private final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList();
  boolean mNeedMenuInvalidate;
  int mNextFragmentIndex = 0;
  String mNoTransactionsBecause;
  Fragment mParent;
  ArrayList<OpGenerator> mPendingActions;
  ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  Fragment mPrimaryNav;
  FragmentManagerNonConfig mSavedNonConfig;
  SparseArray<Parcelable> mStateArray = null;
  Bundle mStateBundle = null;
  boolean mStateSaved;
  ArrayList<Fragment> mTmpAddedFragments;
  ArrayList<Boolean> mTmpIsPop;
  ArrayList<BackStackRecord> mTmpRecords;
  
  static
  {
    DECELERATE_CUBIC = new DecelerateInterpolator(1.5F);
    ACCELERATE_QUINT = new AccelerateInterpolator(2.5F);
  }
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet)
  {
    int i = this.mCurState;
    if (i < 1) {
      return;
    }
    int j = Math.min(i, 4);
    int k = this.mAdded.size();
    for (i = 0; i < k; i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment.mState < j)
      {
        moveToState(localFragment, j, localFragment.getNextAnim(), localFragment.getNextTransition(), false);
        if ((localFragment.mView != null) && (!localFragment.mHidden) && (localFragment.mIsNewlyAdded)) {
          paramArraySet.add(localFragment);
        }
      }
    }
  }
  
  private void animateRemoveFragment(final Fragment paramFragment, AnimationOrAnimator paramAnimationOrAnimator, int paramInt)
  {
    final View localView = paramFragment.mView;
    paramFragment.setStateAfterAnimating(paramInt);
    final Object localObject;
    if (paramAnimationOrAnimator.animation != null)
    {
      localObject = paramAnimationOrAnimator.animation;
      paramFragment.setAnimatingAway(paramFragment.mView);
      ((Animation)localObject).setAnimationListener(new AnimationListenerWrapper(getAnimationListener((Animation)localObject), paramFragment)
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          super.onAnimationEnd(paramAnonymousAnimation);
          if (paramFragment.getAnimatingAway() != null)
          {
            paramFragment.setAnimatingAway(null);
            FragmentManagerImpl localFragmentManagerImpl = FragmentManagerImpl.this;
            paramAnonymousAnimation = paramFragment;
            localFragmentManagerImpl.moveToState(paramAnonymousAnimation, paramAnonymousAnimation.getStateAfterAnimating(), 0, 0, false);
          }
        }
      });
      setHWLayerAnimListenerIfAlpha(localView, paramAnimationOrAnimator);
      paramFragment.mView.startAnimation((Animation)localObject);
    }
    else
    {
      Animator localAnimator = paramAnimationOrAnimator.animator;
      paramFragment.setAnimator(paramAnimationOrAnimator.animator);
      localObject = paramFragment.mContainer;
      if (localObject != null) {
        ((ViewGroup)localObject).startViewTransition(localView);
      }
      localAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          paramAnonymousAnimator = localObject;
          if (paramAnonymousAnimator != null) {
            paramAnonymousAnimator.endViewTransition(localView);
          }
          if (paramFragment.getAnimator() != null)
          {
            paramFragment.setAnimator(null);
            FragmentManagerImpl localFragmentManagerImpl = FragmentManagerImpl.this;
            paramAnonymousAnimator = paramFragment;
            localFragmentManagerImpl.moveToState(paramAnonymousAnimator, paramAnonymousAnimator.getStateAfterAnimating(), 0, 0, false);
          }
        }
      });
      localAnimator.setTarget(paramFragment.mView);
      setHWLayerAnimListenerIfAlpha(paramFragment.mView, paramAnimationOrAnimator);
      localAnimator.start();
    }
  }
  
  private void burpActive()
  {
    SparseArray localSparseArray = this.mActive;
    if (localSparseArray != null) {
      for (int i = localSparseArray.size() - 1; i >= 0; i--) {
        if (this.mActive.valueAt(i) == null)
        {
          localSparseArray = this.mActive;
          localSparseArray.delete(localSparseArray.keyAt(i));
        }
      }
    }
  }
  
  private void checkStateLoss()
  {
    if (!this.mStateSaved)
    {
      if (this.mNoTransactionsBecause == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Can not perform this action inside of ");
      localStringBuilder.append(this.mNoTransactionsBecause);
      throw new IllegalStateException(localStringBuilder.toString());
    }
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec()
  {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    }
    Object localObject = new ArrayList(1);
    ArrayList localArrayList = new ArrayList(1);
    ((ArrayList)localObject).add(paramBackStackRecord);
    localArrayList.add(Boolean.valueOf(paramBoolean1));
    if (paramBoolean2) {
      FragmentTransition.startTransitions(this, (ArrayList)localObject, localArrayList, 0, 1, true);
    }
    if (paramBoolean3) {
      moveToState(this.mCurState, true);
    }
    localObject = this.mActive;
    if (localObject != null)
    {
      int i = ((SparseArray)localObject).size();
      for (int j = 0; j < i; j++)
      {
        localObject = (Fragment)this.mActive.valueAt(j);
        if ((localObject != null) && (((Fragment)localObject).mView != null) && (((Fragment)localObject).mIsNewlyAdded) && (paramBackStackRecord.interactsWith(((Fragment)localObject).mContainerId)))
        {
          if (((Fragment)localObject).mPostponedAlpha > 0.0F) {
            ((Fragment)localObject).mView.setAlpha(((Fragment)localObject).mPostponedAlpha);
          }
          if (paramBoolean3)
          {
            ((Fragment)localObject).mPostponedAlpha = 0.0F;
          }
          else
          {
            ((Fragment)localObject).mPostponedAlpha = -1.0F;
            ((Fragment)localObject).mIsNewlyAdded = false;
          }
        }
      }
    }
  }
  
  private void dispatchStateChange(int paramInt)
  {
    try
    {
      this.mExecutingActions = true;
      moveToState(paramInt, false);
      this.mExecutingActions = false;
      execPendingActions();
      return;
    }
    finally
    {
      this.mExecutingActions = false;
    }
  }
  
  private void endAnimatingAwayFragments()
  {
    Object localObject = this.mActive;
    int i = 0;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((SparseArray)localObject).size();
    }
    while (i < j)
    {
      localObject = (Fragment)this.mActive.valueAt(i);
      if (localObject != null) {
        if (((Fragment)localObject).getAnimatingAway() != null)
        {
          int k = ((Fragment)localObject).getStateAfterAnimating();
          View localView = ((Fragment)localObject).getAnimatingAway();
          ((Fragment)localObject).setAnimatingAway(null);
          Animation localAnimation = localView.getAnimation();
          if (localAnimation != null)
          {
            localAnimation.cancel();
            localView.clearAnimation();
          }
          moveToState((Fragment)localObject, k, 0, 0, false);
        }
        else if (((Fragment)localObject).getAnimator() != null)
        {
          ((Fragment)localObject).getAnimator().end();
        }
      }
      i++;
    }
  }
  
  private void ensureExecReady(boolean paramBoolean)
  {
    if (!this.mExecutingActions)
    {
      if (Looper.myLooper() == this.mHost.getHandler().getLooper())
      {
        if (!paramBoolean) {
          checkStateLoss();
        }
        if (this.mTmpRecords == null)
        {
          this.mTmpRecords = new ArrayList();
          this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try
        {
          executePostponedTransaction(null, null);
          return;
        }
        finally
        {
          this.mExecutingActions = false;
        }
      }
      throw new IllegalStateException("Must be called from main thread of fragment host");
    }
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(paramInt1);
      boolean bool1 = ((Boolean)paramArrayList1.get(paramInt1)).booleanValue();
      boolean bool2 = true;
      if (bool1)
      {
        localBackStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1) {
          bool2 = false;
        }
        localBackStackRecord.executePopOps(bool2);
      }
      else
      {
        localBackStackRecord.bumpBackStackNesting(1);
        localBackStackRecord.executeOps();
      }
      paramInt1++;
    }
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    boolean bool = ((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed;
    Object localObject = this.mTmpAddedFragments;
    if (localObject == null) {
      this.mTmpAddedFragments = new ArrayList();
    } else {
      ((ArrayList)localObject).clear();
    }
    this.mTmpAddedFragments.addAll(this.mAdded);
    localObject = getPrimaryNavigationFragment();
    int j = i;
    int k = 0;
    while (j < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(j);
      if (!((Boolean)paramArrayList1.get(j)).booleanValue()) {
        localObject = localBackStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)localObject);
      } else {
        localObject = localBackStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)localObject);
      }
      if ((k == 0) && (!localBackStackRecord.mAddToBackStack)) {
        k = 0;
      } else {
        k = 1;
      }
      j++;
    }
    this.mTmpAddedFragments.clear();
    if (!bool) {
      FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, paramInt2, false);
    }
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    int m;
    if (bool)
    {
      localObject = new ArraySet();
      addAddedFragments((ArraySet)localObject);
      m = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, (ArraySet)localObject);
      makeRemovedFragmentsInvisible((ArraySet)localObject);
    }
    else
    {
      m = paramInt2;
    }
    j = i;
    if (m != i)
    {
      j = i;
      if (bool)
      {
        FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, m, true);
        moveToState(this.mCurState, true);
      }
    }
    for (j = i; j < paramInt2; j++)
    {
      localObject = (BackStackRecord)paramArrayList.get(j);
      if ((((Boolean)paramArrayList1.get(j)).booleanValue()) && (((BackStackRecord)localObject).mIndex >= 0))
      {
        freeBackStackIndex(((BackStackRecord)localObject).mIndex);
        ((BackStackRecord)localObject).mIndex = -1;
      }
      ((BackStackRecord)localObject).runOnCommitRunnables();
    }
    if (k != 0) {
      reportBackStackChanged();
    }
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    Object localObject = this.mPostponedTransactions;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((ArrayList)localObject).size();
    }
    int j = 0;
    int k = i;
    int i = j;
    while (i < k)
    {
      localObject = (StartEnterTransitionListener)this.mPostponedTransactions.get(i);
      int m;
      if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
      {
        j = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
        if ((j != -1) && (((Boolean)paramArrayList1.get(j)).booleanValue()))
        {
          ((StartEnterTransitionListener)localObject).cancelTransaction();
          m = i;
          j = k;
          break label227;
        }
      }
      if (!((StartEnterTransitionListener)localObject).isReady())
      {
        m = i;
        j = k;
        if (paramArrayList != null)
        {
          m = i;
          j = k;
          if (!((StartEnterTransitionListener)localObject).mRecord.interactsWith(paramArrayList, 0, paramArrayList.size())) {}
        }
      }
      else
      {
        this.mPostponedTransactions.remove(i);
        m = i - 1;
        j = k - 1;
        if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
        {
          i = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
          if ((i != -1) && (((Boolean)paramArrayList1.get(i)).booleanValue()))
          {
            ((StartEnterTransitionListener)localObject).cancelTransaction();
            break label227;
          }
        }
        ((StartEnterTransitionListener)localObject).completeTransaction();
      }
      label227:
      i = m + 1;
      k = j;
    }
  }
  
  private Fragment findFragmentUnder(Fragment paramFragment)
  {
    ViewGroup localViewGroup = paramFragment.mContainer;
    View localView = paramFragment.mView;
    if ((localViewGroup != null) && (localView != null))
    {
      for (int i = this.mAdded.indexOf(paramFragment) - 1; i >= 0; i--)
      {
        paramFragment = (Fragment)this.mAdded.get(i);
        if ((paramFragment.mContainer == localViewGroup) && (paramFragment.mView != null)) {
          return paramFragment;
        }
      }
      return null;
    }
    return null;
  }
  
  private void forcePostponedTransactions()
  {
    if (this.mPostponedTransactions != null) {
      while (!this.mPostponedTransactions.isEmpty()) {
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();
      }
    }
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    try
    {
      ArrayList localArrayList = this.mPendingActions;
      int i = 0;
      if ((localArrayList != null) && (this.mPendingActions.size() != 0))
      {
        int j = this.mPendingActions.size();
        boolean bool = false;
        while (i < j)
        {
          bool |= ((OpGenerator)this.mPendingActions.get(i)).generateOps(paramArrayList, paramArrayList1);
          i++;
        }
        this.mPendingActions.clear();
        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
        return bool;
      }
      return false;
    }
    finally {}
  }
  
  private static Animation.AnimationListener getAnimationListener(Animation paramAnimation)
  {
    try
    {
      if (sAnimationListenerField == null)
      {
        sAnimationListenerField = Animation.class.getDeclaredField("mListener");
        sAnimationListenerField.setAccessible(true);
      }
      paramAnimation = (Animation.AnimationListener)sAnimationListenerField.get(paramAnimation);
    }
    catch (IllegalAccessException paramAnimation)
    {
      Log.e("FragmentManager", "Cannot access Animation's mListener field", paramAnimation);
    }
    catch (NoSuchFieldException paramAnimation)
    {
      Log.e("FragmentManager", "No field with the name mListener is found in Animation class", paramAnimation);
    }
    paramAnimation = null;
    return paramAnimation;
  }
  
  static AnimationOrAnimator makeFadeAnimation(Context paramContext, float paramFloat1, float paramFloat2)
  {
    paramContext = new AlphaAnimation(paramFloat1, paramFloat2);
    paramContext.setInterpolator(DECELERATE_CUBIC);
    paramContext.setDuration(220L);
    return new AnimationOrAnimator(paramContext, null);
  }
  
  static AnimationOrAnimator makeOpenCloseAnimation(Context paramContext, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramContext = new AnimationSet(false);
    Object localObject = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    ((ScaleAnimation)localObject).setInterpolator(DECELERATE_QUINT);
    ((ScaleAnimation)localObject).setDuration(220L);
    paramContext.addAnimation((Animation)localObject);
    localObject = new AlphaAnimation(paramFloat3, paramFloat4);
    ((AlphaAnimation)localObject).setInterpolator(DECELERATE_CUBIC);
    ((AlphaAnimation)localObject).setDuration(220L);
    paramContext.addAnimation((Animation)localObject);
    return new AnimationOrAnimator(paramContext, null);
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet)
  {
    int i = paramArraySet.size();
    for (int j = 0; j < i; j++)
    {
      Fragment localFragment = (Fragment)paramArraySet.valueAt(j);
      if (!localFragment.mAdded)
      {
        View localView = localFragment.getView();
        localFragment.mPostponedAlpha = localView.getAlpha();
        localView.setAlpha(0.0F);
      }
    }
  }
  
  static boolean modifiesAlpha(Animator paramAnimator)
  {
    if (paramAnimator == null) {
      return false;
    }
    int i;
    if ((paramAnimator instanceof ValueAnimator))
    {
      paramAnimator = ((ValueAnimator)paramAnimator).getValues();
      for (i = 0; i < paramAnimator.length; i++) {
        if ("alpha".equals(paramAnimator[i].getPropertyName())) {
          return true;
        }
      }
    }
    if ((paramAnimator instanceof AnimatorSet))
    {
      paramAnimator = ((AnimatorSet)paramAnimator).getChildAnimations();
      for (i = 0; i < paramAnimator.size(); i++) {
        if (modifiesAlpha((Animator)paramAnimator.get(i))) {
          return true;
        }
      }
    }
    return false;
  }
  
  static boolean modifiesAlpha(AnimationOrAnimator paramAnimationOrAnimator)
  {
    if ((paramAnimationOrAnimator.animation instanceof AlphaAnimation)) {
      return true;
    }
    if ((paramAnimationOrAnimator.animation instanceof AnimationSet))
    {
      paramAnimationOrAnimator = ((AnimationSet)paramAnimationOrAnimator.animation).getAnimations();
      for (int i = 0; i < paramAnimationOrAnimator.size(); i++) {
        if ((paramAnimationOrAnimator.get(i) instanceof AlphaAnimation)) {
          return true;
        }
      }
      return false;
    }
    return modifiesAlpha(paramAnimationOrAnimator.animator);
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2)
  {
    execPendingActions();
    ensureExecReady(true);
    Object localObject = this.mPrimaryNav;
    if ((localObject != null) && (paramInt1 < 0) && (paramString == null))
    {
      localObject = ((Fragment)localObject).peekChildFragmentManager();
      if ((localObject != null) && (((FragmentManager)localObject).popBackStackImmediate())) {
        return true;
      }
    }
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet)
  {
    int i = paramInt2 - 1;
    int m;
    for (int j = paramInt2; i >= paramInt1; j = m)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(i);
      boolean bool = ((Boolean)paramArrayList1.get(i)).booleanValue();
      int k;
      if ((localBackStackRecord.isPostponed()) && (!localBackStackRecord.interactsWith(paramArrayList, i + 1, paramInt2))) {
        k = 1;
      } else {
        k = 0;
      }
      m = j;
      if (k != 0)
      {
        if (this.mPostponedTransactions == null) {
          this.mPostponedTransactions = new ArrayList();
        }
        StartEnterTransitionListener localStartEnterTransitionListener = new StartEnterTransitionListener(localBackStackRecord, bool);
        this.mPostponedTransactions.add(localStartEnterTransitionListener);
        localBackStackRecord.setOnStartPostponedListener(localStartEnterTransitionListener);
        if (bool) {
          localBackStackRecord.executeOps();
        } else {
          localBackStackRecord.executePopOps(false);
        }
        m = j - 1;
        if (i != m)
        {
          paramArrayList.remove(i);
          paramArrayList.add(m, localBackStackRecord);
        }
        addAddedFragments(paramArraySet);
      }
      i--;
    }
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
    {
      if ((paramArrayList1 != null) && (paramArrayList.size() == paramArrayList1.size()))
      {
        executePostponedTransaction(paramArrayList, paramArrayList1);
        int i = paramArrayList.size();
        int j = 0;
        int n;
        for (int k = 0; j < i; k = n)
        {
          int m = j;
          n = k;
          if (!((BackStackRecord)paramArrayList.get(j)).mReorderingAllowed)
          {
            if (k != j) {
              executeOpsTogether(paramArrayList, paramArrayList1, k, j);
            }
            k = j + 1;
            n = k;
            if (((Boolean)paramArrayList1.get(j)).booleanValue()) {
              for (;;)
              {
                n = k;
                if (k >= i) {
                  break;
                }
                n = k;
                if (!((Boolean)paramArrayList1.get(k)).booleanValue()) {
                  break;
                }
                n = k;
                if (((BackStackRecord)paramArrayList.get(k)).mReorderingAllowed) {
                  break;
                }
                k++;
              }
            }
            executeOpsTogether(paramArrayList, paramArrayList1, j, n);
            m = n - 1;
          }
          j = m + 1;
        }
        if (k != i) {
          executeOpsTogether(paramArrayList, paramArrayList1, k, i);
        }
        return;
      }
      throw new IllegalStateException("Internal error with the back stack records");
    }
  }
  
  public static int reverseTransit(int paramInt)
  {
    int i = 8194;
    if (paramInt != 4097) {
      if (paramInt != 4099)
      {
        if (paramInt != 8194) {
          i = 0;
        } else {
          i = 4097;
        }
      }
      else {
        i = 4099;
      }
    }
    return i;
  }
  
  private void scheduleCommit()
  {
    try
    {
      ArrayList localArrayList = this.mPostponedTransactions;
      int i = 0;
      int j;
      if ((localArrayList != null) && (!this.mPostponedTransactions.isEmpty())) {
        j = 1;
      } else {
        j = 0;
      }
      int k = i;
      if (this.mPendingActions != null)
      {
        k = i;
        if (this.mPendingActions.size() == 1) {
          k = 1;
        }
      }
      if ((j != 0) || (k != 0))
      {
        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
        this.mHost.getHandler().post(this.mExecCommit);
      }
      return;
    }
    finally {}
  }
  
  private static void setHWLayerAnimListenerIfAlpha(View paramView, AnimationOrAnimator paramAnimationOrAnimator)
  {
    if ((paramView != null) && (paramAnimationOrAnimator != null))
    {
      if (shouldRunOnHWLayer(paramView, paramAnimationOrAnimator)) {
        if (paramAnimationOrAnimator.animator != null)
        {
          paramAnimationOrAnimator.animator.addListener(new AnimatorOnHWLayerIfNeededListener(paramView));
        }
        else
        {
          Animation.AnimationListener localAnimationListener = getAnimationListener(paramAnimationOrAnimator.animation);
          paramView.setLayerType(2, null);
          paramAnimationOrAnimator.animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(paramView, localAnimationListener));
        }
      }
      return;
    }
  }
  
  private static void setRetaining(FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    if (paramFragmentManagerNonConfig == null) {
      return;
    }
    Object localObject = paramFragmentManagerNonConfig.getFragments();
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Fragment)((Iterator)localObject).next()).mRetaining = true;
      }
    }
    paramFragmentManagerNonConfig = paramFragmentManagerNonConfig.getChildNonConfigs();
    if (paramFragmentManagerNonConfig != null)
    {
      paramFragmentManagerNonConfig = paramFragmentManagerNonConfig.iterator();
      while (paramFragmentManagerNonConfig.hasNext()) {
        setRetaining((FragmentManagerNonConfig)paramFragmentManagerNonConfig.next());
      }
    }
  }
  
  static boolean shouldRunOnHWLayer(View paramView, AnimationOrAnimator paramAnimationOrAnimator)
  {
    boolean bool1 = false;
    if ((paramView != null) && (paramAnimationOrAnimator != null))
    {
      boolean bool2 = bool1;
      if (Build.VERSION.SDK_INT >= 19)
      {
        bool2 = bool1;
        if (paramView.getLayerType() == 0)
        {
          bool2 = bool1;
          if (ViewCompat.hasOverlappingRendering(paramView))
          {
            bool2 = bool1;
            if (modifiesAlpha(paramAnimationOrAnimator)) {
              bool2 = true;
            }
          }
        }
      }
      return bool2;
    }
    return false;
  }
  
  private void throwException(RuntimeException paramRuntimeException)
  {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null) {
      try
      {
        localFragmentHostCallback.onDump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException1)
      {
        Log.e("FragmentManager", "Failed dumping state", localException1);
      }
    } else {
      try
      {
        dump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException2)
      {
        Log.e("FragmentManager", "Failed dumping state", localException2);
      }
    }
    throw paramRuntimeException;
  }
  
  public static int transitToStyleIndex(int paramInt, boolean paramBoolean)
  {
    if (paramInt != 4097)
    {
      if (paramInt != 4099)
      {
        if (paramInt != 8194) {
          paramInt = -1;
        } else if (paramBoolean) {
          paramInt = 3;
        } else {
          paramInt = 4;
        }
      }
      else if (paramBoolean) {
        paramInt = 5;
      } else {
        paramInt = 6;
      }
    }
    else if (paramBoolean) {
      paramInt = 1;
    } else {
      paramInt = 2;
    }
    return paramInt;
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord)
  {
    if (this.mBackStack == null) {
      this.mBackStack = new ArrayList();
    }
    this.mBackStack.add(paramBackStackRecord);
  }
  
  public void addFragment(Fragment paramFragment, boolean paramBoolean)
  {
    if (DEBUG)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("add: ");
      ((StringBuilder)???).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)???).toString());
    }
    makeActive(paramFragment);
    if (!paramFragment.mDetached)
    {
      if (!this.mAdded.contains(paramFragment)) {
        synchronized (this.mAdded)
        {
          this.mAdded.add(paramFragment);
          paramFragment.mAdded = true;
          paramFragment.mRemoving = false;
          if (paramFragment.mView == null) {
            paramFragment.mHiddenChanged = false;
          }
          if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
            this.mNeedMenuInvalidate = true;
          }
          if (!paramBoolean) {
            return;
          }
          moveToState(paramFragment);
        }
      }
      ??? = new StringBuilder();
      ((StringBuilder)???).append("Fragment already added: ");
      ((StringBuilder)???).append(paramFragment);
      throw new IllegalStateException(((StringBuilder)???).toString());
    }
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (this.mBackStackChangeListeners == null) {
      this.mBackStackChangeListeners = new ArrayList();
    }
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  public int allocBackStackIndex(BackStackRecord paramBackStackRecord)
  {
    try
    {
      Object localObject;
      if ((this.mAvailBackStackIndices != null) && (this.mAvailBackStackIndices.size() > 0))
      {
        i = ((Integer)this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
        if (DEBUG)
        {
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          ((StringBuilder)localObject).append("Adding back stack index ");
          ((StringBuilder)localObject).append(i);
          ((StringBuilder)localObject).append(" with ");
          ((StringBuilder)localObject).append(paramBackStackRecord);
          Log.v("FragmentManager", ((StringBuilder)localObject).toString());
        }
        this.mBackStackIndices.set(i, paramBackStackRecord);
        return i;
      }
      if (this.mBackStackIndices == null)
      {
        localObject = new java/util/ArrayList;
        ((ArrayList)localObject).<init>();
        this.mBackStackIndices = ((ArrayList)localObject);
      }
      int i = this.mBackStackIndices.size();
      if (DEBUG)
      {
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Setting back stack index ");
        ((StringBuilder)localObject).append(i);
        ((StringBuilder)localObject).append(" to ");
        ((StringBuilder)localObject).append(paramBackStackRecord);
        Log.v("FragmentManager", ((StringBuilder)localObject).toString());
      }
      this.mBackStackIndices.add(paramBackStackRecord);
      return i;
    }
    finally {}
  }
  
  public void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment)
  {
    if (this.mHost == null)
    {
      this.mHost = paramFragmentHostCallback;
      this.mContainer = paramFragmentContainer;
      this.mParent = paramFragment;
      return;
    }
    throw new IllegalStateException("Already attached");
  }
  
  public void attachFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("attach: ");
      ((StringBuilder)???).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)???).toString());
    }
    if (paramFragment.mDetached)
    {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
      {
        if (!this.mAdded.contains(paramFragment))
        {
          if (DEBUG)
          {
            ??? = new StringBuilder();
            ((StringBuilder)???).append("add from attach: ");
            ((StringBuilder)???).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)???).toString());
          }
          synchronized (this.mAdded)
          {
            this.mAdded.add(paramFragment);
            paramFragment.mAdded = true;
            if ((!paramFragment.mHasMenu) || (!paramFragment.mMenuVisible)) {
              return;
            }
            this.mNeedMenuInvalidate = true;
          }
        }
        ??? = new StringBuilder();
        ((StringBuilder)???).append("Fragment already added: ");
        ((StringBuilder)???).append(paramFragment);
        throw new IllegalStateException(((StringBuilder)???).toString());
      }
    }
  }
  
  public FragmentTransaction beginTransaction()
  {
    return new BackStackRecord(this);
  }
  
  void completeShowHideFragment(final Fragment paramFragment)
  {
    if (paramFragment.mView != null)
    {
      AnimationOrAnimator localAnimationOrAnimator = loadAnimation(paramFragment, paramFragment.getNextTransition(), paramFragment.mHidden ^ true, paramFragment.getNextTransitionStyle());
      if ((localAnimationOrAnimator != null) && (localAnimationOrAnimator.animator != null))
      {
        localAnimationOrAnimator.animator.setTarget(paramFragment.mView);
        if (paramFragment.mHidden)
        {
          if (paramFragment.isHideReplaced())
          {
            paramFragment.setHideReplaced(false);
          }
          else
          {
            final ViewGroup localViewGroup = paramFragment.mContainer;
            final View localView = paramFragment.mView;
            localViewGroup.startViewTransition(localView);
            localAnimationOrAnimator.animator.addListener(new AnimatorListenerAdapter()
            {
              public void onAnimationEnd(Animator paramAnonymousAnimator)
              {
                localViewGroup.endViewTransition(localView);
                paramAnonymousAnimator.removeListener(this);
                if (paramFragment.mView != null) {
                  paramFragment.mView.setVisibility(8);
                }
              }
            });
          }
        }
        else {
          paramFragment.mView.setVisibility(0);
        }
        setHWLayerAnimListenerIfAlpha(paramFragment.mView, localAnimationOrAnimator);
        localAnimationOrAnimator.animator.start();
      }
      else
      {
        if (localAnimationOrAnimator != null)
        {
          setHWLayerAnimListenerIfAlpha(paramFragment.mView, localAnimationOrAnimator);
          paramFragment.mView.startAnimation(localAnimationOrAnimator.animation);
          localAnimationOrAnimator.animation.start();
        }
        int i;
        if ((paramFragment.mHidden) && (!paramFragment.isHideReplaced())) {
          i = 8;
        } else {
          i = 0;
        }
        paramFragment.mView.setVisibility(i);
        if (paramFragment.isHideReplaced()) {
          paramFragment.setHideReplaced(false);
        }
      }
    }
    if ((paramFragment.mAdded) && (paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
      this.mNeedMenuInvalidate = true;
    }
    paramFragment.mHiddenChanged = false;
    paramFragment.onHiddenChanged(paramFragment.mHidden);
  }
  
  public void detachFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("detach: ");
      ((StringBuilder)???).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)???).toString());
    }
    if (!paramFragment.mDetached)
    {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded)
      {
        if (DEBUG)
        {
          ??? = new StringBuilder();
          ((StringBuilder)???).append("remove from detach: ");
          ((StringBuilder)???).append(paramFragment);
          Log.v("FragmentManager", ((StringBuilder)???).toString());
        }
        synchronized (this.mAdded)
        {
          this.mAdded.remove(paramFragment);
          if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
            this.mNeedMenuInvalidate = true;
          }
          paramFragment.mAdded = false;
        }
      }
    }
  }
  
  public void dispatchActivityCreated()
  {
    this.mStateSaved = false;
    dispatchStateChange(2);
  }
  
  public void dispatchConfigurationChanged(Configuration paramConfiguration)
  {
    for (int i = 0; i < this.mAdded.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.performConfigurationChanged(paramConfiguration);
      }
    }
  }
  
  public boolean dispatchContextItemSelected(MenuItem paramMenuItem)
  {
    for (int i = 0; i < this.mAdded.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if ((localFragment != null) && (localFragment.performContextItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  public void dispatchCreate()
  {
    this.mStateSaved = false;
    dispatchStateChange(1);
  }
  
  public boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    int i = 0;
    Object localObject1 = null;
    int j = 0;
    boolean bool2;
    for (boolean bool1 = false; j < this.mAdded.size(); bool1 = bool2)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(j);
      Object localObject2 = localObject1;
      bool2 = bool1;
      if (localFragment != null)
      {
        localObject2 = localObject1;
        bool2 = bool1;
        if (localFragment.performCreateOptionsMenu(paramMenu, paramMenuInflater))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add(localFragment);
          bool2 = true;
        }
      }
      j++;
      localObject1 = localObject2;
    }
    if (this.mCreatedMenus != null) {
      for (j = i; j < this.mCreatedMenus.size(); j++)
      {
        paramMenu = (Fragment)this.mCreatedMenus.get(j);
        if ((localObject1 == null) || (!((ArrayList)localObject1).contains(paramMenu))) {
          paramMenu.onDestroyOptionsMenu();
        }
      }
    }
    this.mCreatedMenus = ((ArrayList)localObject1);
    return bool1;
  }
  
  public void dispatchDestroy()
  {
    this.mDestroyed = true;
    execPendingActions();
    dispatchStateChange(0);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
  }
  
  public void dispatchDestroyView()
  {
    dispatchStateChange(1);
  }
  
  public void dispatchLowMemory()
  {
    for (int i = 0; i < this.mAdded.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.performLowMemory();
      }
    }
  }
  
  public void dispatchMultiWindowModeChanged(boolean paramBoolean)
  {
    for (int i = this.mAdded.size() - 1; i >= 0; i--)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.performMultiWindowModeChanged(paramBoolean);
      }
    }
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentActivityCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentAttached(paramFragment, paramContext, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentAttached(this, paramFragment, paramContext);
      }
    }
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentCreated(paramFragment, paramBundle, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentDestroyed(paramFragment, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentDestroyed(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentDetached(paramFragment, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentDetached(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPaused(paramFragment, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentPaused(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPreAttached(paramFragment, paramContext, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentPreAttached(this, paramFragment, paramContext);
      }
    }
  }
  
  void dispatchOnFragmentPreCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPreCreated(paramFragment, paramBundle, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentPreCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentResumed(paramFragment, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentResumed(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentSaveInstanceState(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentStarted(paramFragment, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentStarted(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentStopped(paramFragment, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentStopped(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true);
      }
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject).next();
      if ((!paramBoolean) || (((Boolean)localPair.second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)localPair.first).onFragmentViewCreated(this, paramFragment, paramView, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentViewDestroyed(paramFragment, true);
      }
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Pair)localIterator.next();
      if ((!paramBoolean) || (((Boolean)((Pair)localObject).second).booleanValue())) {
        ((FragmentManager.FragmentLifecycleCallbacks)((Pair)localObject).first).onFragmentViewDestroyed(this, paramFragment);
      }
    }
  }
  
  public boolean dispatchOptionsItemSelected(MenuItem paramMenuItem)
  {
    for (int i = 0; i < this.mAdded.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if ((localFragment != null) && (localFragment.performOptionsItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  public void dispatchOptionsMenuClosed(Menu paramMenu)
  {
    for (int i = 0; i < this.mAdded.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.performOptionsMenuClosed(paramMenu);
      }
    }
  }
  
  public void dispatchPause()
  {
    dispatchStateChange(4);
  }
  
  public void dispatchPictureInPictureModeChanged(boolean paramBoolean)
  {
    for (int i = this.mAdded.size() - 1; i >= 0; i--)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.performPictureInPictureModeChanged(paramBoolean);
      }
    }
  }
  
  public boolean dispatchPrepareOptionsMenu(Menu paramMenu)
  {
    int i = 0;
    boolean bool2;
    for (boolean bool1 = false; i < this.mAdded.size(); bool1 = bool2)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      bool2 = bool1;
      if (localFragment != null)
      {
        bool2 = bool1;
        if (localFragment.performPrepareOptionsMenu(paramMenu)) {
          bool2 = true;
        }
      }
      i++;
    }
    return bool1;
  }
  
  public void dispatchReallyStop()
  {
    dispatchStateChange(2);
  }
  
  public void dispatchResume()
  {
    this.mStateSaved = false;
    dispatchStateChange(5);
  }
  
  public void dispatchStart()
  {
    this.mStateSaved = false;
    dispatchStateChange(4);
  }
  
  public void dispatchStop()
  {
    this.mStateSaved = true;
    dispatchStateChange(3);
  }
  
  void doPendingDeferredStart()
  {
    if (this.mHavePendingDeferredStart)
    {
      int i = 0;
      boolean bool2;
      for (boolean bool1 = false; i < this.mActive.size(); bool1 = bool2)
      {
        Fragment localFragment = (Fragment)this.mActive.valueAt(i);
        bool2 = bool1;
        if (localFragment != null)
        {
          bool2 = bool1;
          if (localFragment.mLoaderManager != null) {
            bool2 = bool1 | localFragment.mLoaderManager.hasRunningLoaders();
          }
        }
        i++;
      }
      if (!bool1)
      {
        this.mHavePendingDeferredStart = false;
        startPendingDeferredFragments();
      }
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("    ");
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = this.mActive;
    int i = 0;
    int k;
    if (localObject2 != null)
    {
      j = ((SparseArray)localObject2).size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("Active Fragments in ");
        paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
        paramPrintWriter.println(":");
        for (k = 0; k < j; k++)
        {
          localObject2 = (Fragment)this.mActive.valueAt(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(localObject2);
          if (localObject2 != null) {
            ((Fragment)localObject2).dump((String)localObject1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
          }
        }
      }
    }
    int j = this.mAdded.size();
    if (j > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Added Fragments:");
      for (k = 0; k < j; k++)
      {
        localObject2 = (Fragment)this.mAdded.get(k);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(k);
        paramPrintWriter.print(": ");
        paramPrintWriter.println(((Fragment)localObject2).toString());
      }
    }
    localObject2 = this.mCreatedMenus;
    if (localObject2 != null)
    {
      j = ((ArrayList)localObject2).size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Fragments Created Menus:");
        for (k = 0; k < j; k++)
        {
          localObject2 = (Fragment)this.mCreatedMenus.get(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(((Fragment)localObject2).toString());
        }
      }
    }
    localObject2 = this.mBackStack;
    if (localObject2 != null)
    {
      j = ((ArrayList)localObject2).size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Back Stack:");
        for (k = 0; k < j; k++)
        {
          localObject2 = (BackStackRecord)this.mBackStack.get(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(((BackStackRecord)localObject2).toString());
          ((BackStackRecord)localObject2).dump((String)localObject1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
        }
      }
    }
    try
    {
      if (this.mBackStackIndices != null)
      {
        j = this.mBackStackIndices.size();
        if (j > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Back Stack Indices:");
          for (k = 0; k < j; k++)
          {
            paramFileDescriptor = (BackStackRecord)this.mBackStackIndices.get(k);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(k);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(paramFileDescriptor);
          }
        }
      }
      if ((this.mAvailBackStackIndices != null) && (this.mAvailBackStackIndices.size() > 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mAvailBackStackIndices: ");
        paramPrintWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
      }
      paramFileDescriptor = this.mPendingActions;
      if (paramFileDescriptor != null)
      {
        j = paramFileDescriptor.size();
        if (j > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Pending Actions:");
          for (k = i; k < j; k++)
          {
            paramFileDescriptor = (OpGenerator)this.mPendingActions.get(k);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(k);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(paramFileDescriptor);
          }
        }
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("FragmentManager misc state:");
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mHost=");
      paramPrintWriter.println(this.mHost);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mContainer=");
      paramPrintWriter.println(this.mContainer);
      if (this.mParent != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mParent=");
        paramPrintWriter.println(this.mParent);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mCurState=");
      paramPrintWriter.print(this.mCurState);
      paramPrintWriter.print(" mStateSaved=");
      paramPrintWriter.print(this.mStateSaved);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(this.mDestroyed);
      if (this.mNeedMenuInvalidate)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mNeedMenuInvalidate=");
        paramPrintWriter.println(this.mNeedMenuInvalidate);
      }
      if (this.mNoTransactionsBecause != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mNoTransactionsBecause=");
        paramPrintWriter.println(this.mNoTransactionsBecause);
      }
      return;
    }
    finally {}
  }
  
  public void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if (!paramBoolean) {
      checkStateLoss();
    }
    try
    {
      if ((!this.mDestroyed) && (this.mHost != null))
      {
        if (this.mPendingActions == null)
        {
          ArrayList localArrayList = new java/util/ArrayList;
          localArrayList.<init>();
          this.mPendingActions = localArrayList;
        }
        this.mPendingActions.add(paramOpGenerator);
        scheduleCommit();
        return;
      }
      if (paramBoolean) {
        return;
      }
      paramOpGenerator = new java/lang/IllegalStateException;
      paramOpGenerator.<init>("Activity has been destroyed");
      throw paramOpGenerator;
    }
    finally {}
  }
  
  void ensureInflatedFragmentView(Fragment paramFragment)
  {
    if ((paramFragment.mFromLayout) && (!paramFragment.mPerformedCreateView))
    {
      paramFragment.mView = paramFragment.performCreateView(paramFragment.performGetLayoutInflater(paramFragment.mSavedFragmentState), null, paramFragment.mSavedFragmentState);
      if (paramFragment.mView != null)
      {
        paramFragment.mInnerView = paramFragment.mView;
        paramFragment.mView.setSaveFromParentEnabled(false);
        if (paramFragment.mHidden) {
          paramFragment.mView.setVisibility(8);
        }
        paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
        dispatchOnFragmentViewCreated(paramFragment, paramFragment.mView, paramFragment.mSavedFragmentState, false);
      }
      else
      {
        paramFragment.mInnerView = null;
      }
    }
  }
  
  /* Error */
  public boolean execPendingActions()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: invokespecial 684	android/support/v4/app/FragmentManagerImpl:ensureExecReady	(Z)V
    //   5: iconst_0
    //   6: istore_1
    //   7: aload_0
    //   8: aload_0
    //   9: getfield 355	android/support/v4/app/FragmentManagerImpl:mTmpRecords	Ljava/util/ArrayList;
    //   12: aload_0
    //   13: getfield 350	android/support/v4/app/FragmentManagerImpl:mTmpIsPop	Ljava/util/ArrayList;
    //   16: invokespecial 1229	android/support/v4/app/FragmentManagerImpl:generateOpsForPendingActions	(Ljava/util/ArrayList;Ljava/util/ArrayList;)Z
    //   19: ifeq +36 -> 55
    //   22: aload_0
    //   23: iconst_1
    //   24: putfield 348	android/support/v4/app/FragmentManagerImpl:mExecutingActions	Z
    //   27: aload_0
    //   28: aload_0
    //   29: getfield 355	android/support/v4/app/FragmentManagerImpl:mTmpRecords	Ljava/util/ArrayList;
    //   32: aload_0
    //   33: getfield 350	android/support/v4/app/FragmentManagerImpl:mTmpIsPop	Ljava/util/ArrayList;
    //   36: invokespecial 699	android/support/v4/app/FragmentManagerImpl:removeRedundantOperationsAndExecute	(Ljava/util/ArrayList;Ljava/util/ArrayList;)V
    //   39: aload_0
    //   40: invokespecial 701	android/support/v4/app/FragmentManagerImpl:cleanupExec	()V
    //   43: iconst_1
    //   44: istore_1
    //   45: goto -38 -> 7
    //   48: astore_2
    //   49: aload_0
    //   50: invokespecial 701	android/support/v4/app/FragmentManagerImpl:cleanupExec	()V
    //   53: aload_2
    //   54: athrow
    //   55: aload_0
    //   56: invokevirtual 704	android/support/v4/app/FragmentManagerImpl:doPendingDeferredStart	()V
    //   59: aload_0
    //   60: invokespecial 706	android/support/v4/app/FragmentManagerImpl:burpActive	()V
    //   63: iload_1
    //   64: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	FragmentManagerImpl
    //   6	58	1	bool	boolean
    //   48	6	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   27	39	48	finally
  }
  
  public void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if ((paramBoolean) && ((this.mHost == null) || (this.mDestroyed))) {
      return;
    }
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    burpActive();
  }
  
  public boolean executePendingTransactions()
  {
    boolean bool = execPendingActions();
    forcePostponedTransactions();
    return bool;
  }
  
  public Fragment findFragmentById(int paramInt)
  {
    for (int i = this.mAdded.size() - 1; i >= 0; i--)
    {
      localObject = (Fragment)this.mAdded.get(i);
      if ((localObject != null) && (((Fragment)localObject).mFragmentId == paramInt)) {
        return (Fragment)localObject;
      }
    }
    Object localObject = this.mActive;
    if (localObject != null) {
      for (i = ((SparseArray)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (Fragment)this.mActive.valueAt(i);
        if ((localObject != null) && (((Fragment)localObject).mFragmentId == paramInt)) {
          return (Fragment)localObject;
        }
      }
    }
    return null;
  }
  
  public Fragment findFragmentByTag(String paramString)
  {
    int i;
    if (paramString != null) {
      for (i = this.mAdded.size() - 1; i >= 0; i--)
      {
        localObject = (Fragment)this.mAdded.get(i);
        if ((localObject != null) && (paramString.equals(((Fragment)localObject).mTag))) {
          return (Fragment)localObject;
        }
      }
    }
    Object localObject = this.mActive;
    if ((localObject != null) && (paramString != null)) {
      for (i = ((SparseArray)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (Fragment)this.mActive.valueAt(i);
        if ((localObject != null) && (paramString.equals(((Fragment)localObject).mTag))) {
          return (Fragment)localObject;
        }
      }
    }
    return null;
  }
  
  public Fragment findFragmentByWho(String paramString)
  {
    Object localObject = this.mActive;
    if ((localObject != null) && (paramString != null)) {
      for (int i = ((SparseArray)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (Fragment)this.mActive.valueAt(i);
        if (localObject != null)
        {
          localObject = ((Fragment)localObject).findFragmentByWho(paramString);
          if (localObject != null) {
            return (Fragment)localObject;
          }
        }
      }
    }
    return null;
  }
  
  public void freeBackStackIndex(int paramInt)
  {
    try
    {
      this.mBackStackIndices.set(paramInt, null);
      Object localObject1;
      if (this.mAvailBackStackIndices == null)
      {
        localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>();
        this.mAvailBackStackIndices = ((ArrayList)localObject1);
      }
      if (DEBUG)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("Freeing back stack index ");
        ((StringBuilder)localObject1).append(paramInt);
        Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
      }
      this.mAvailBackStackIndices.add(Integer.valueOf(paramInt));
      return;
    }
    finally {}
  }
  
  int getActiveFragmentCount()
  {
    SparseArray localSparseArray = this.mActive;
    if (localSparseArray == null) {
      return 0;
    }
    return localSparseArray.size();
  }
  
  List<Fragment> getActiveFragments()
  {
    Object localObject = this.mActive;
    if (localObject == null) {
      return null;
    }
    int i = ((SparseArray)localObject).size();
    localObject = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      ((ArrayList)localObject).add(this.mActive.valueAt(j));
    }
    return (List<Fragment>)localObject;
  }
  
  public FragmentManager.BackStackEntry getBackStackEntryAt(int paramInt)
  {
    return (FragmentManager.BackStackEntry)this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount()
  {
    ArrayList localArrayList = this.mBackStack;
    int i;
    if (localArrayList != null) {
      i = localArrayList.size();
    } else {
      i = 0;
    }
    return i;
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString)
  {
    int i = paramBundle.getInt(paramString, -1);
    if (i == -1) {
      return null;
    }
    paramBundle = (Fragment)this.mActive.get(i);
    if (paramBundle == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment no longer exists for key ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": index ");
      localStringBuilder.append(i);
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    return paramBundle;
  }
  
  public List<Fragment> getFragments()
  {
    if (this.mAdded.isEmpty()) {
      return Collections.EMPTY_LIST;
    }
    synchronized (this.mAdded)
    {
      List localList = (List)this.mAdded.clone();
      return localList;
    }
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory()
  {
    return this;
  }
  
  public Fragment getPrimaryNavigationFragment()
  {
    return this.mPrimaryNav;
  }
  
  public void hideFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("hide: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (!paramFragment.mHidden)
    {
      paramFragment.mHidden = true;
      paramFragment.mHiddenChanged = (true ^ paramFragment.mHiddenChanged);
    }
  }
  
  public boolean isDestroyed()
  {
    return this.mDestroyed;
  }
  
  boolean isStateAtLeast(int paramInt)
  {
    boolean bool;
    if (this.mCurState >= paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStateSaved()
  {
    return this.mStateSaved;
  }
  
  AnimationOrAnimator loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    int i = paramFragment.getNextAnim();
    Animation localAnimation = paramFragment.onCreateAnimation(paramInt1, paramBoolean, i);
    if (localAnimation != null) {
      return new AnimationOrAnimator(localAnimation, null);
    }
    paramFragment = paramFragment.onCreateAnimator(paramInt1, paramBoolean, i);
    if (paramFragment != null) {
      return new AnimationOrAnimator(paramFragment, null);
    }
    boolean bool;
    if (i != 0)
    {
      bool = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(i));
      j = 0;
      k = j;
      if (!bool) {}
    }
    try
    {
      try
      {
        paramFragment = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
        if (paramFragment != null)
        {
          paramFragment = new AnimationOrAnimator(paramFragment, null);
          return paramFragment;
        }
        k = 1;
      }
      catch (Resources.NotFoundException paramFragment)
      {
        throw paramFragment;
      }
    }
    catch (RuntimeException paramFragment)
    {
      for (;;)
      {
        k = j;
      }
    }
    if (k == 0) {
      try
      {
        paramFragment = AnimatorInflater.loadAnimator(this.mHost.getContext(), i);
        if (paramFragment != null)
        {
          paramFragment = new AnimationOrAnimator(paramFragment, null);
          return paramFragment;
        }
      }
      catch (RuntimeException paramFragment)
      {
        if (!bool)
        {
          paramFragment = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
          if (paramFragment != null) {
            return new AnimationOrAnimator(paramFragment, null);
          }
        }
        else
        {
          throw paramFragment;
        }
      }
    }
    if (paramInt1 == 0) {
      return null;
    }
    paramInt1 = transitToStyleIndex(paramInt1, paramBoolean);
    if (paramInt1 < 0) {
      return null;
    }
    switch (paramInt1)
    {
    default: 
      paramInt1 = paramInt2;
      if (paramInt2 == 0)
      {
        paramInt1 = paramInt2;
        if (this.mHost.onHasWindowAnimations()) {
          paramInt1 = this.mHost.onGetWindowAnimations();
        }
      }
      break;
    case 6: 
      return makeFadeAnimation(this.mHost.getContext(), 1.0F, 0.0F);
    case 5: 
      return makeFadeAnimation(this.mHost.getContext(), 0.0F, 1.0F);
    case 4: 
      return makeOpenCloseAnimation(this.mHost.getContext(), 1.0F, 1.075F, 1.0F, 0.0F);
    case 3: 
      return makeOpenCloseAnimation(this.mHost.getContext(), 0.975F, 1.0F, 0.0F, 1.0F);
    case 2: 
      return makeOpenCloseAnimation(this.mHost.getContext(), 1.0F, 0.975F, 1.0F, 0.0F);
    case 1: 
      return makeOpenCloseAnimation(this.mHost.getContext(), 1.125F, 1.0F, 0.0F, 1.0F);
    }
    if (paramInt1 == 0) {
      return null;
    }
    return null;
  }
  
  void makeActive(Fragment paramFragment)
  {
    if (paramFragment.mIndex >= 0) {
      return;
    }
    int i = this.mNextFragmentIndex;
    this.mNextFragmentIndex = (i + 1);
    paramFragment.setIndex(i, this.mParent);
    if (this.mActive == null) {
      this.mActive = new SparseArray();
    }
    this.mActive.put(paramFragment.mIndex, paramFragment);
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Allocated fragment index ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  void makeInactive(Fragment paramFragment)
  {
    if (paramFragment.mIndex < 0) {
      return;
    }
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Freeing fragment index ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    this.mActive.put(paramFragment.mIndex, null);
    this.mHost.inactivateFragment(paramFragment.mWho);
    paramFragment.initState();
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment)
  {
    if (paramFragment == null) {
      return;
    }
    int i = this.mCurState;
    if (paramFragment.mRemoving) {
      if (paramFragment.isInBackStack()) {
        i = Math.min(i, 1);
      } else {
        i = Math.min(i, 0);
      }
    }
    moveToState(paramFragment, i, paramFragment.getNextTransition(), paramFragment.getNextTransitionStyle(), false);
    if (paramFragment.mView != null)
    {
      Object localObject = findFragmentUnder(paramFragment);
      if (localObject != null)
      {
        View localView = ((Fragment)localObject).mView;
        localObject = paramFragment.mContainer;
        int j = ((ViewGroup)localObject).indexOfChild(localView);
        i = ((ViewGroup)localObject).indexOfChild(paramFragment.mView);
        if (i < j)
        {
          ((ViewGroup)localObject).removeViewAt(i);
          ((ViewGroup)localObject).addView(paramFragment.mView, j);
        }
      }
      if ((paramFragment.mIsNewlyAdded) && (paramFragment.mContainer != null))
      {
        if (paramFragment.mPostponedAlpha > 0.0F) {
          paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha);
        }
        paramFragment.mPostponedAlpha = 0.0F;
        paramFragment.mIsNewlyAdded = false;
        localObject = loadAnimation(paramFragment, paramFragment.getNextTransition(), true, paramFragment.getNextTransitionStyle());
        if (localObject != null)
        {
          setHWLayerAnimListenerIfAlpha(paramFragment.mView, (AnimationOrAnimator)localObject);
          if (((AnimationOrAnimator)localObject).animation != null)
          {
            paramFragment.mView.startAnimation(((AnimationOrAnimator)localObject).animation);
          }
          else
          {
            ((AnimationOrAnimator)localObject).animator.setTarget(paramFragment.mView);
            ((AnimationOrAnimator)localObject).animator.start();
          }
        }
      }
    }
    if (paramFragment.mHiddenChanged) {
      completeShowHideFragment(paramFragment);
    }
  }
  
  void moveToState(int paramInt, boolean paramBoolean)
  {
    if ((this.mHost == null) && (paramInt != 0)) {
      throw new IllegalStateException("No activity");
    }
    if ((!paramBoolean) && (paramInt == this.mCurState)) {
      return;
    }
    this.mCurState = paramInt;
    if (this.mActive != null)
    {
      int i = this.mAdded.size();
      int j = 0;
      Object localObject;
      for (paramInt = 0; j < i; paramInt = k)
      {
        localObject = (Fragment)this.mAdded.get(j);
        moveFragmentToExpectedState((Fragment)localObject);
        k = paramInt;
        if (((Fragment)localObject).mLoaderManager != null) {
          k = paramInt | ((Fragment)localObject).mLoaderManager.hasRunningLoaders();
        }
        j++;
      }
      i = this.mActive.size();
      int k = 0;
      for (j = paramInt; k < i; j = paramInt)
      {
        localObject = (Fragment)this.mActive.valueAt(k);
        paramInt = j;
        if (localObject != null) {
          if (!((Fragment)localObject).mRemoving)
          {
            paramInt = j;
            if (!((Fragment)localObject).mDetached) {}
          }
          else
          {
            paramInt = j;
            if (!((Fragment)localObject).mIsNewlyAdded)
            {
              moveFragmentToExpectedState((Fragment)localObject);
              paramInt = j;
              if (((Fragment)localObject).mLoaderManager != null) {
                paramInt = j | ((Fragment)localObject).mLoaderManager.hasRunningLoaders();
              }
            }
          }
        }
        k++;
      }
      if (j == 0) {
        startPendingDeferredFragments();
      }
      if (this.mNeedMenuInvalidate)
      {
        localObject = this.mHost;
        if ((localObject != null) && (this.mCurState == 5))
        {
          ((FragmentHostCallback)localObject).onSupportInvalidateOptionsMenu();
          this.mNeedMenuInvalidate = false;
        }
      }
    }
  }
  
  void moveToState(Fragment paramFragment)
  {
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  void moveToState(Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    boolean bool1 = paramFragment.mAdded;
    int i = 1;
    boolean bool2 = true;
    if ((bool1) && (!paramFragment.mDetached)) {
      break label44;
    }
    int j = paramInt1;
    paramInt1 = j;
    if (j > 1) {
      paramInt1 = 1;
    }
    label44:
    j = paramInt1;
    if (paramFragment.mRemoving)
    {
      j = paramInt1;
      if (paramInt1 > paramFragment.mState) {
        if ((paramFragment.mState == 0) && (paramFragment.isInBackStack())) {
          j = 1;
        } else {
          j = paramFragment.mState;
        }
      }
    }
    if ((paramFragment.mDeferStart) && (paramFragment.mState < 4) && (j > 3)) {
      paramInt1 = 3;
    } else {
      paramInt1 = j;
    }
    Object localObject2;
    if (paramFragment.mState <= paramInt1)
    {
      if ((paramFragment.mFromLayout) && (!paramFragment.mInLayout)) {
        return;
      }
      if ((paramFragment.getAnimatingAway() != null) || (paramFragment.getAnimator() != null))
      {
        paramFragment.setAnimatingAway(null);
        paramFragment.setAnimator(null);
        moveToState(paramFragment, paramFragment.getStateAfterAnimating(), 0, 0, true);
      }
      paramInt2 = paramInt1;
      j = paramInt1;
      i = paramInt1;
      paramInt3 = paramInt1;
      Object localObject1;
      Object localObject3;
      switch (paramFragment.mState)
      {
      default: 
        break;
      case 0: 
        paramInt2 = paramInt1;
        if (paramInt1 > 0)
        {
          if (DEBUG)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("moveto CREATED: ");
            ((StringBuilder)localObject1).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
          }
          paramInt2 = paramInt1;
          if (paramFragment.mSavedFragmentState != null)
          {
            paramFragment.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
            paramFragment.mSavedViewState = paramFragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
            paramFragment.mTarget = getFragment(paramFragment.mSavedFragmentState, "android:target_state");
            if (paramFragment.mTarget != null) {
              paramFragment.mTargetRequestCode = paramFragment.mSavedFragmentState.getInt("android:target_req_state", 0);
            }
            paramFragment.mUserVisibleHint = paramFragment.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
            paramInt2 = paramInt1;
            if (!paramFragment.mUserVisibleHint)
            {
              paramFragment.mDeferStart = true;
              paramInt2 = paramInt1;
              if (paramInt1 > 3) {
                paramInt2 = 3;
              }
            }
          }
          localObject1 = this.mHost;
          paramFragment.mHost = ((FragmentHostCallback)localObject1);
          localObject3 = this.mParent;
          paramFragment.mParentFragment = ((Fragment)localObject3);
          if (localObject3 != null) {
            localObject1 = ((Fragment)localObject3).mChildFragmentManager;
          } else {
            localObject1 = ((FragmentHostCallback)localObject1).getFragmentManagerImpl();
          }
          paramFragment.mFragmentManager = ((FragmentManagerImpl)localObject1);
          if (paramFragment.mTarget != null) {
            if (this.mActive.get(paramFragment.mTarget.mIndex) == paramFragment.mTarget)
            {
              if (paramFragment.mTarget.mState < 1) {
                moveToState(paramFragment.mTarget, 1, 0, 0, true);
              }
            }
            else
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("Fragment ");
              ((StringBuilder)localObject1).append(paramFragment);
              ((StringBuilder)localObject1).append(" declared target fragment ");
              ((StringBuilder)localObject1).append(paramFragment.mTarget);
              ((StringBuilder)localObject1).append(" that does not belong to this FragmentManager!");
              throw new IllegalStateException(((StringBuilder)localObject1).toString());
            }
          }
          dispatchOnFragmentPreAttached(paramFragment, this.mHost.getContext(), false);
          paramFragment.mCalled = false;
          paramFragment.onAttach(this.mHost.getContext());
          if (paramFragment.mCalled)
          {
            if (paramFragment.mParentFragment == null) {
              this.mHost.onAttachFragment(paramFragment);
            } else {
              paramFragment.mParentFragment.onAttachFragment(paramFragment);
            }
            dispatchOnFragmentAttached(paramFragment, this.mHost.getContext(), false);
            if (!paramFragment.mIsCreated)
            {
              dispatchOnFragmentPreCreated(paramFragment, paramFragment.mSavedFragmentState, false);
              paramFragment.performCreate(paramFragment.mSavedFragmentState);
              dispatchOnFragmentCreated(paramFragment, paramFragment.mSavedFragmentState, false);
            }
            else
            {
              paramFragment.restoreChildFragmentState(paramFragment.mSavedFragmentState);
              paramFragment.mState = 1;
            }
            paramFragment.mRetaining = false;
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Fragment ");
            ((StringBuilder)localObject1).append(paramFragment);
            ((StringBuilder)localObject1).append(" did not call through to super.onAttach()");
            throw new SuperNotCalledException(((StringBuilder)localObject1).toString());
          }
        }
      case 1: 
        ensureInflatedFragmentView(paramFragment);
        j = paramInt2;
        if (paramInt2 > 1)
        {
          if (DEBUG)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("moveto ACTIVITY_CREATED: ");
            ((StringBuilder)localObject1).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
          }
          if (!paramFragment.mFromLayout)
          {
            if (paramFragment.mContainerId != 0)
            {
              if (paramFragment.mContainerId == -1)
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("Cannot create fragment ");
                ((StringBuilder)localObject1).append(paramFragment);
                ((StringBuilder)localObject1).append(" for a container view with no id");
                throwException(new IllegalArgumentException(((StringBuilder)localObject1).toString()));
              }
              localObject3 = (ViewGroup)this.mContainer.onFindViewById(paramFragment.mContainerId);
              localObject1 = localObject3;
              if (localObject3 == null)
              {
                localObject1 = localObject3;
                if (!paramFragment.mRestored)
                {
                  try
                  {
                    localObject1 = paramFragment.getResources().getResourceName(paramFragment.mContainerId);
                  }
                  catch (Resources.NotFoundException localNotFoundException)
                  {
                    localObject2 = "unknown";
                  }
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append("No view found for id 0x");
                  localStringBuilder.append(Integer.toHexString(paramFragment.mContainerId));
                  localStringBuilder.append(" (");
                  localStringBuilder.append((String)localObject2);
                  localStringBuilder.append(") for fragment ");
                  localStringBuilder.append(paramFragment);
                  throwException(new IllegalArgumentException(localStringBuilder.toString()));
                  localObject2 = localObject3;
                }
              }
            }
            else
            {
              localObject2 = null;
            }
            paramFragment.mContainer = ((ViewGroup)localObject2);
            paramFragment.mView = paramFragment.performCreateView(paramFragment.performGetLayoutInflater(paramFragment.mSavedFragmentState), (ViewGroup)localObject2, paramFragment.mSavedFragmentState);
            if (paramFragment.mView != null)
            {
              paramFragment.mInnerView = paramFragment.mView;
              paramFragment.mView.setSaveFromParentEnabled(false);
              if (localObject2 != null) {
                ((ViewGroup)localObject2).addView(paramFragment.mView);
              }
              if (paramFragment.mHidden) {
                paramFragment.mView.setVisibility(8);
              }
              paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
              dispatchOnFragmentViewCreated(paramFragment, paramFragment.mView, paramFragment.mSavedFragmentState, false);
              if ((paramFragment.mView.getVisibility() == 0) && (paramFragment.mContainer != null)) {
                paramBoolean = bool2;
              } else {
                paramBoolean = false;
              }
              paramFragment.mIsNewlyAdded = paramBoolean;
            }
            else
            {
              paramFragment.mInnerView = null;
            }
          }
          paramFragment.performActivityCreated(paramFragment.mSavedFragmentState);
          dispatchOnFragmentActivityCreated(paramFragment, paramFragment.mSavedFragmentState, false);
          if (paramFragment.mView != null) {
            paramFragment.restoreViewState(paramFragment.mSavedFragmentState);
          }
          paramFragment.mSavedFragmentState = null;
          j = paramInt2;
        }
      case 2: 
        i = j;
        if (j > 2)
        {
          paramFragment.mState = 3;
          i = j;
        }
      case 3: 
        paramInt3 = i;
        if (i > 3)
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("moveto STARTED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          paramFragment.performStart();
          dispatchOnFragmentStarted(paramFragment, false);
          paramInt3 = i;
        }
        break;
      }
      if (paramInt3 > 4)
      {
        if (DEBUG)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("moveto RESUMED: ");
          ((StringBuilder)localObject2).append(paramFragment);
          Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
        }
        paramFragment.performResume();
        dispatchOnFragmentResumed(paramFragment, false);
        paramFragment.mSavedFragmentState = null;
        paramFragment.mSavedViewState = null;
      }
      paramInt1 = paramInt3;
    }
    else if (paramFragment.mState > paramInt1)
    {
      switch (paramFragment.mState)
      {
      default: 
        break;
      case 5: 
        if (paramInt1 < 5)
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("movefrom RESUMED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          paramFragment.performPause();
          dispatchOnFragmentPaused(paramFragment, false);
        }
      case 4: 
        if (paramInt1 < 4)
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("movefrom STARTED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          paramFragment.performStop();
          dispatchOnFragmentStopped(paramFragment, false);
        }
      case 3: 
        if (paramInt1 < 3)
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("movefrom STOPPED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          paramFragment.performReallyStop();
        }
      case 2: 
        if (paramInt1 < 2)
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("movefrom ACTIVITY_CREATED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          if ((paramFragment.mView != null) && (this.mHost.onShouldSaveFragmentState(paramFragment)) && (paramFragment.mSavedViewState == null)) {
            saveFragmentViewState(paramFragment);
          }
          paramFragment.performDestroyView();
          dispatchOnFragmentViewDestroyed(paramFragment, false);
          if ((paramFragment.mView != null) && (paramFragment.mContainer != null))
          {
            paramFragment.mView.clearAnimation();
            paramFragment.mContainer.endViewTransition(paramFragment.mView);
            if ((this.mCurState > 0) && (!this.mDestroyed) && (paramFragment.mView.getVisibility() == 0) && (paramFragment.mPostponedAlpha >= 0.0F)) {
              localObject2 = loadAnimation(paramFragment, paramInt2, false, paramInt3);
            } else {
              localObject2 = null;
            }
            paramFragment.mPostponedAlpha = 0.0F;
            if (localObject2 != null) {
              animateRemoveFragment(paramFragment, (AnimationOrAnimator)localObject2, paramInt1);
            }
            paramFragment.mContainer.removeView(paramFragment.mView);
          }
          paramFragment.mContainer = null;
          paramFragment.mView = null;
          paramFragment.mInnerView = null;
          paramFragment.mInLayout = false;
        }
        break;
      }
      if (paramInt1 < 1)
      {
        if (this.mDestroyed) {
          if (paramFragment.getAnimatingAway() != null)
          {
            localObject2 = paramFragment.getAnimatingAway();
            paramFragment.setAnimatingAway(null);
            ((View)localObject2).clearAnimation();
          }
          else if (paramFragment.getAnimator() != null)
          {
            localObject2 = paramFragment.getAnimator();
            paramFragment.setAnimator(null);
            ((Animator)localObject2).cancel();
          }
        }
        if ((paramFragment.getAnimatingAway() == null) && (paramFragment.getAnimator() == null))
        {
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("movefrom CREATED: ");
            ((StringBuilder)localObject2).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
          if (!paramFragment.mRetaining)
          {
            paramFragment.performDestroy();
            dispatchOnFragmentDestroyed(paramFragment, false);
          }
          else
          {
            paramFragment.mState = 0;
          }
          paramFragment.performDetach();
          dispatchOnFragmentDetached(paramFragment, false);
          if (!paramBoolean) {
            if (!paramFragment.mRetaining)
            {
              makeInactive(paramFragment);
            }
            else
            {
              paramFragment.mHost = null;
              paramFragment.mParentFragment = null;
              paramFragment.mFragmentManager = null;
            }
          }
        }
        else
        {
          paramFragment.setStateAfterAnimating(paramInt1);
          paramInt1 = i;
        }
      }
    }
    if (paramFragment.mState != paramInt1)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("moveToState: Fragment state for ");
      ((StringBuilder)localObject2).append(paramFragment);
      ((StringBuilder)localObject2).append(" not updated inline; ");
      ((StringBuilder)localObject2).append("expected state ");
      ((StringBuilder)localObject2).append(paramInt1);
      ((StringBuilder)localObject2).append(" found ");
      ((StringBuilder)localObject2).append(paramFragment.mState);
      Log.w("FragmentManager", ((StringBuilder)localObject2).toString());
      paramFragment.mState = paramInt1;
    }
  }
  
  public void noteStateNotSaved()
  {
    this.mSavedNonConfig = null;
    int i = 0;
    this.mStateSaved = false;
    int j = this.mAdded.size();
    while (i < j)
    {
      Fragment localFragment = (Fragment)this.mAdded.get(i);
      if (localFragment != null) {
        localFragment.noteStateNotSaved();
      }
      i++;
    }
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if (!"fragment".equals(paramString)) {
      return null;
    }
    String str1 = paramAttributeSet.getAttributeValue(null, "class");
    paramString = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
    int i = 0;
    if (str1 == null) {
      str1 = paramString.getString(0);
    }
    int j = paramString.getResourceId(1, -1);
    String str2 = paramString.getString(2);
    paramString.recycle();
    if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), str1)) {
      return null;
    }
    if (paramView != null) {
      i = paramView.getId();
    }
    if ((i == -1) && (j == -1) && (str2 == null))
    {
      paramView = new StringBuilder();
      paramView.append(paramAttributeSet.getPositionDescription());
      paramView.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
      paramView.append(str1);
      throw new IllegalArgumentException(paramView.toString());
    }
    if (j != -1) {
      paramView = findFragmentById(j);
    } else {
      paramView = null;
    }
    paramString = paramView;
    if (paramView == null)
    {
      paramString = paramView;
      if (str2 != null) {
        paramString = findFragmentByTag(str2);
      }
    }
    paramView = paramString;
    if (paramString == null)
    {
      paramView = paramString;
      if (i != -1) {
        paramView = findFragmentById(i);
      }
    }
    if (DEBUG)
    {
      paramString = new StringBuilder();
      paramString.append("onCreateView: id=0x");
      paramString.append(Integer.toHexString(j));
      paramString.append(" fname=");
      paramString.append(str1);
      paramString.append(" existing=");
      paramString.append(paramView);
      Log.v("FragmentManager", paramString.toString());
    }
    if (paramView == null)
    {
      paramView = this.mContainer.instantiate(paramContext, str1, null);
      paramView.mFromLayout = true;
      int k;
      if (j != 0) {
        k = j;
      } else {
        k = i;
      }
      paramView.mFragmentId = k;
      paramView.mContainerId = i;
      paramView.mTag = str2;
      paramView.mInLayout = true;
      paramView.mFragmentManager = this;
      paramString = this.mHost;
      paramView.mHost = paramString;
      paramView.onInflate(paramString.getContext(), paramAttributeSet, paramView.mSavedFragmentState);
      addFragment(paramView, true);
    }
    else
    {
      if (paramView.mInLayout) {
        break label558;
      }
      paramView.mInLayout = true;
      paramView.mHost = this.mHost;
      if (!paramView.mRetaining) {
        paramView.onInflate(this.mHost.getContext(), paramAttributeSet, paramView.mSavedFragmentState);
      }
    }
    if ((this.mCurState < 1) && (paramView.mFromLayout)) {
      moveToState(paramView, 1, 0, 0, false);
    } else {
      moveToState(paramView);
    }
    if (paramView.mView != null)
    {
      if (j != 0) {
        paramView.mView.setId(j);
      }
      if (paramView.mView.getTag() == null) {
        paramView.mView.setTag(str2);
      }
      return paramView.mView;
    }
    paramView = new StringBuilder();
    paramView.append("Fragment ");
    paramView.append(str1);
    paramView.append(" did not create a view.");
    throw new IllegalStateException(paramView.toString());
    label558:
    paramView = new StringBuilder();
    paramView.append(paramAttributeSet.getPositionDescription());
    paramView.append(": Duplicate id 0x");
    paramView.append(Integer.toHexString(j));
    paramView.append(", tag ");
    paramView.append(str2);
    paramView.append(", or parent id 0x");
    paramView.append(Integer.toHexString(i));
    paramView.append(" with another fragment for ");
    paramView.append(str1);
    throw new IllegalArgumentException(paramView.toString());
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void performPendingDeferredStart(Fragment paramFragment)
  {
    if (paramFragment.mDeferStart)
    {
      if (this.mExecutingActions)
      {
        this.mHavePendingDeferredStart = true;
        return;
      }
      paramFragment.mDeferStart = false;
      moveToState(paramFragment, this.mCurState, 0, 0, false);
    }
  }
  
  public void popBackStack()
  {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void popBackStack(String paramString, int paramInt)
  {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate()
  {
    checkStateLoss();
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2)
  {
    checkStateLoss();
    execPendingActions();
    if (paramInt1 >= 0) {
      return popBackStackImmediate(null, paramInt1, paramInt2);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt)
  {
    checkStateLoss();
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = this.mBackStack;
    if (localObject == null) {
      return false;
    }
    if ((paramString == null) && (paramInt1 < 0) && ((paramInt2 & 0x1) == 0))
    {
      paramInt1 = ((ArrayList)localObject).size() - 1;
      if (paramInt1 < 0) {
        return false;
      }
      paramArrayList.add(this.mBackStack.remove(paramInt1));
      paramArrayList1.add(Boolean.valueOf(true));
    }
    else
    {
      int i;
      if ((paramString == null) && (paramInt1 < 0))
      {
        i = -1;
      }
      else
      {
        for (int j = this.mBackStack.size() - 1; j >= 0; j--)
        {
          localObject = (BackStackRecord)this.mBackStack.get(j);
          if (((paramString != null) && (paramString.equals(((BackStackRecord)localObject).getName()))) || ((paramInt1 >= 0) && (paramInt1 == ((BackStackRecord)localObject).mIndex))) {
            break;
          }
        }
        if (j < 0) {
          return false;
        }
        i = j;
        if ((paramInt2 & 0x1) != 0) {
          for (paramInt2 = j - 1;; paramInt2--)
          {
            i = paramInt2;
            if (paramInt2 < 0) {
              break;
            }
            localObject = (BackStackRecord)this.mBackStack.get(paramInt2);
            if ((paramString == null) || (!paramString.equals(((BackStackRecord)localObject).getName())))
            {
              i = paramInt2;
              if (paramInt1 < 0) {
                break;
              }
              i = paramInt2;
              if (paramInt1 != ((BackStackRecord)localObject).mIndex) {
                break;
              }
            }
          }
        }
      }
      if (i == this.mBackStack.size() - 1) {
        return false;
      }
      for (paramInt1 = this.mBackStack.size() - 1; paramInt1 > i; paramInt1--)
      {
        paramArrayList.add(this.mBackStack.remove(paramInt1));
        paramArrayList1.add(Boolean.valueOf(true));
      }
    }
    return true;
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    if (paramFragment.mIndex < 0)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    paramBundle.putInt(paramString, paramFragment.mIndex);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
  {
    this.mLifecycleCallbacks.add(new Pair(paramFragmentLifecycleCallbacks, Boolean.valueOf(paramBoolean)));
  }
  
  public void removeFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("remove: ");
      ((StringBuilder)???).append(paramFragment);
      ((StringBuilder)???).append(" nesting=");
      ((StringBuilder)???).append(paramFragment.mBackStackNesting);
      Log.v("FragmentManager", ((StringBuilder)???).toString());
    }
    boolean bool = paramFragment.isInBackStack();
    if ((!paramFragment.mDetached) || ((bool ^ true))) {}
    synchronized (this.mAdded)
    {
      this.mAdded.remove(paramFragment);
      if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
        this.mNeedMenuInvalidate = true;
      }
      paramFragment.mAdded = false;
      paramFragment.mRemoving = true;
      return;
    }
  }
  
  public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    ArrayList localArrayList = this.mBackStackChangeListeners;
    if (localArrayList != null) {
      localArrayList.remove(paramOnBackStackChangedListener);
    }
  }
  
  void reportBackStackChanged()
  {
    if (this.mBackStackChangeListeners != null) {
      for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
        ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(i)).onBackStackChanged();
      }
    }
  }
  
  void restoreAllState(Parcelable paramParcelable, FragmentManagerNonConfig arg2)
  {
    if (paramParcelable == null) {
      return;
    }
    FragmentManagerState localFragmentManagerState = (FragmentManagerState)paramParcelable;
    if (localFragmentManagerState.mActive == null) {
      return;
    }
    Object localObject1;
    Object localObject2;
    int j;
    Object localObject3;
    if (??? != null)
    {
      localObject1 = ???.getFragments();
      localObject2 = ???.getChildNonConfigs();
      if (localObject1 != null) {
        i = ((List)localObject1).size();
      } else {
        i = 0;
      }
      for (j = 0;; j++)
      {
        paramParcelable = (Parcelable)localObject2;
        if (j >= i) {
          break;
        }
        paramParcelable = (Fragment)((List)localObject1).get(j);
        if (DEBUG)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("restoreAllState: re-attaching retained ");
          ((StringBuilder)localObject3).append(paramParcelable);
          Log.v("FragmentManager", ((StringBuilder)localObject3).toString());
        }
        for (int k = 0; (k < localFragmentManagerState.mActive.length) && (localFragmentManagerState.mActive[k].mIndex != paramParcelable.mIndex); k++) {}
        if (k == localFragmentManagerState.mActive.length)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("Could not find active fragment with index ");
          ((StringBuilder)localObject3).append(paramParcelable.mIndex);
          throwException(new IllegalStateException(((StringBuilder)localObject3).toString()));
        }
        localObject3 = localFragmentManagerState.mActive[k];
        ((FragmentState)localObject3).mInstance = paramParcelable;
        paramParcelable.mSavedViewState = null;
        paramParcelable.mBackStackNesting = 0;
        paramParcelable.mInLayout = false;
        paramParcelable.mAdded = false;
        paramParcelable.mTarget = null;
        if (((FragmentState)localObject3).mSavedFragmentState != null)
        {
          ((FragmentState)localObject3).mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
          paramParcelable.mSavedViewState = ((FragmentState)localObject3).mSavedFragmentState.getSparseParcelableArray("android:view_state");
          paramParcelable.mSavedFragmentState = ((FragmentState)localObject3).mSavedFragmentState;
        }
      }
    }
    paramParcelable = null;
    this.mActive = new SparseArray(localFragmentManagerState.mActive.length);
    for (int i = 0; i < localFragmentManagerState.mActive.length; i++)
    {
      localObject1 = localFragmentManagerState.mActive[i];
      if (localObject1 != null)
      {
        if ((paramParcelable != null) && (i < paramParcelable.size())) {
          localObject2 = (FragmentManagerNonConfig)paramParcelable.get(i);
        } else {
          localObject2 = null;
        }
        localObject3 = ((FragmentState)localObject1).instantiate(this.mHost, this.mContainer, this.mParent, (FragmentManagerNonConfig)localObject2);
        if (DEBUG)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("restoreAllState: active #");
          ((StringBuilder)localObject2).append(i);
          ((StringBuilder)localObject2).append(": ");
          ((StringBuilder)localObject2).append(localObject3);
          Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
        }
        this.mActive.put(((Fragment)localObject3).mIndex, localObject3);
        ((FragmentState)localObject1).mInstance = null;
      }
    }
    if (??? != null)
    {
      paramParcelable = ???.getFragments();
      if (paramParcelable != null) {
        i = paramParcelable.size();
      } else {
        i = 0;
      }
      for (j = 0; j < i; j++)
      {
        ??? = (Fragment)paramParcelable.get(j);
        if (???.mTargetIndex >= 0)
        {
          ???.mTarget = ((Fragment)this.mActive.get(???.mTargetIndex));
          if (???.mTarget == null)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Re-attaching retained fragment ");
            ((StringBuilder)localObject2).append(???);
            ((StringBuilder)localObject2).append(" target no longer exists: ");
            ((StringBuilder)localObject2).append(???.mTargetIndex);
            Log.w("FragmentManager", ((StringBuilder)localObject2).toString());
          }
        }
      }
    }
    this.mAdded.clear();
    if (localFragmentManagerState.mAdded != null)
    {
      i = 0;
      for (;;)
      {
        if (i >= localFragmentManagerState.mAdded.length) {
          break label836;
        }
        paramParcelable = (Fragment)this.mActive.get(localFragmentManagerState.mAdded[i]);
        if (paramParcelable == null)
        {
          ??? = new StringBuilder();
          ???.append("No instantiated fragment for index #");
          ???.append(localFragmentManagerState.mAdded[i]);
          throwException(new IllegalStateException(???.toString()));
        }
        paramParcelable.mAdded = true;
        if (DEBUG)
        {
          ??? = new StringBuilder();
          ???.append("restoreAllState: added #");
          ???.append(i);
          ???.append(": ");
          ???.append(paramParcelable);
          Log.v("FragmentManager", ???.toString());
        }
        if (!this.mAdded.contains(paramParcelable)) {
          synchronized (this.mAdded)
          {
            this.mAdded.add(paramParcelable);
            i++;
          }
        }
      }
      throw new IllegalStateException("Already added!");
    }
    label836:
    if (localFragmentManagerState.mBackStack != null)
    {
      this.mBackStack = new ArrayList(localFragmentManagerState.mBackStack.length);
      for (i = 0; i < localFragmentManagerState.mBackStack.length; i++)
      {
        paramParcelable = localFragmentManagerState.mBackStack[i].instantiate(this);
        if (DEBUG)
        {
          ??? = new StringBuilder();
          ???.append("restoreAllState: back stack #");
          ???.append(i);
          ???.append(" (index ");
          ???.append(paramParcelable.mIndex);
          ???.append("): ");
          ???.append(paramParcelable);
          Log.v("FragmentManager", ???.toString());
          ??? = new PrintWriter(new LogWriter("FragmentManager"));
          paramParcelable.dump("  ", ???, false);
          ???.close();
        }
        this.mBackStack.add(paramParcelable);
        if (paramParcelable.mIndex >= 0) {
          setBackStackIndex(paramParcelable.mIndex, paramParcelable);
        }
      }
    }
    this.mBackStack = null;
    if (localFragmentManagerState.mPrimaryNavActiveIndex >= 0) {
      this.mPrimaryNav = ((Fragment)this.mActive.get(localFragmentManagerState.mPrimaryNavActiveIndex));
    }
    this.mNextFragmentIndex = localFragmentManagerState.mNextFragmentIndex;
  }
  
  FragmentManagerNonConfig retainNonConfig()
  {
    setRetaining(this.mSavedNonConfig);
    return this.mSavedNonConfig;
  }
  
  Parcelable saveAllState()
  {
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions();
    this.mStateSaved = true;
    Object localObject1 = null;
    this.mSavedNonConfig = null;
    Object localObject2 = this.mActive;
    if ((localObject2 != null) && (((SparseArray)localObject2).size() > 0))
    {
      int i = this.mActive.size();
      FragmentState[] arrayOfFragmentState = new FragmentState[i];
      int j = 0;
      int k = 0;
      int m = 0;
      while (k < i)
      {
        localObject2 = (Fragment)this.mActive.valueAt(k);
        if (localObject2 != null)
        {
          if (((Fragment)localObject2).mIndex < 0)
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("Failure saving state: active ");
            ((StringBuilder)localObject3).append(localObject2);
            ((StringBuilder)localObject3).append(" has cleared index: ");
            ((StringBuilder)localObject3).append(((Fragment)localObject2).mIndex);
            throwException(new IllegalStateException(((StringBuilder)localObject3).toString()));
          }
          localObject3 = new FragmentState((Fragment)localObject2);
          arrayOfFragmentState[k] = localObject3;
          if ((((Fragment)localObject2).mState > 0) && (((FragmentState)localObject3).mSavedFragmentState == null))
          {
            ((FragmentState)localObject3).mSavedFragmentState = saveFragmentBasicState((Fragment)localObject2);
            if (((Fragment)localObject2).mTarget != null)
            {
              if (((Fragment)localObject2).mTarget.mIndex < 0)
              {
                localObject4 = new StringBuilder();
                ((StringBuilder)localObject4).append("Failure saving state: ");
                ((StringBuilder)localObject4).append(localObject2);
                ((StringBuilder)localObject4).append(" has target not in fragment manager: ");
                ((StringBuilder)localObject4).append(((Fragment)localObject2).mTarget);
                throwException(new IllegalStateException(((StringBuilder)localObject4).toString()));
              }
              if (((FragmentState)localObject3).mSavedFragmentState == null) {
                ((FragmentState)localObject3).mSavedFragmentState = new Bundle();
              }
              putFragment(((FragmentState)localObject3).mSavedFragmentState, "android:target_state", ((Fragment)localObject2).mTarget);
              if (((Fragment)localObject2).mTargetRequestCode != 0) {
                ((FragmentState)localObject3).mSavedFragmentState.putInt("android:target_req_state", ((Fragment)localObject2).mTargetRequestCode);
              }
            }
          }
          else
          {
            ((FragmentState)localObject3).mSavedFragmentState = ((Fragment)localObject2).mSavedFragmentState;
          }
          if (DEBUG)
          {
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("Saved state of ");
            ((StringBuilder)localObject4).append(localObject2);
            ((StringBuilder)localObject4).append(": ");
            ((StringBuilder)localObject4).append(((FragmentState)localObject3).mSavedFragmentState);
            Log.v("FragmentManager", ((StringBuilder)localObject4).toString());
          }
          m = 1;
        }
        k++;
      }
      if (m == 0)
      {
        if (DEBUG) {
          Log.v("FragmentManager", "saveAllState: no fragments!");
        }
        return null;
      }
      m = this.mAdded.size();
      if (m > 0)
      {
        localObject3 = new int[m];
        for (k = 0;; k++)
        {
          localObject2 = localObject3;
          if (k >= m) {
            break;
          }
          localObject3[k] = ((Fragment)this.mAdded.get(k)).mIndex;
          if (localObject3[k] < 0)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Failure saving state: active ");
            ((StringBuilder)localObject2).append(this.mAdded.get(k));
            ((StringBuilder)localObject2).append(" has cleared index: ");
            ((StringBuilder)localObject2).append(localObject3[k]);
            throwException(new IllegalStateException(((StringBuilder)localObject2).toString()));
          }
          if (DEBUG)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("saveAllState: adding fragment #");
            ((StringBuilder)localObject2).append(k);
            ((StringBuilder)localObject2).append(": ");
            ((StringBuilder)localObject2).append(this.mAdded.get(k));
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
        }
      }
      localObject2 = null;
      Object localObject4 = this.mBackStack;
      Object localObject3 = localObject1;
      if (localObject4 != null)
      {
        m = ((ArrayList)localObject4).size();
        localObject3 = localObject1;
        if (m > 0)
        {
          localObject1 = new BackStackState[m];
          for (k = j;; k++)
          {
            localObject3 = localObject1;
            if (k >= m) {
              break;
            }
            localObject1[k] = new BackStackState((BackStackRecord)this.mBackStack.get(k));
            if (DEBUG)
            {
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("saveAllState: adding back stack #");
              ((StringBuilder)localObject3).append(k);
              ((StringBuilder)localObject3).append(": ");
              ((StringBuilder)localObject3).append(this.mBackStack.get(k));
              Log.v("FragmentManager", ((StringBuilder)localObject3).toString());
            }
          }
        }
      }
      localObject1 = new FragmentManagerState();
      ((FragmentManagerState)localObject1).mActive = arrayOfFragmentState;
      ((FragmentManagerState)localObject1).mAdded = ((int[])localObject2);
      ((FragmentManagerState)localObject1).mBackStack = ((BackStackState[])localObject3);
      localObject2 = this.mPrimaryNav;
      if (localObject2 != null) {
        ((FragmentManagerState)localObject1).mPrimaryNavActiveIndex = ((Fragment)localObject2).mIndex;
      }
      ((FragmentManagerState)localObject1).mNextFragmentIndex = this.mNextFragmentIndex;
      saveNonConfig();
      return (Parcelable)localObject1;
    }
    return null;
  }
  
  Bundle saveFragmentBasicState(Fragment paramFragment)
  {
    if (this.mStateBundle == null) {
      this.mStateBundle = new Bundle();
    }
    paramFragment.performSaveInstanceState(this.mStateBundle);
    dispatchOnFragmentSaveInstanceState(paramFragment, this.mStateBundle, false);
    if (!this.mStateBundle.isEmpty())
    {
      localObject1 = this.mStateBundle;
      this.mStateBundle = null;
    }
    else
    {
      localObject1 = null;
    }
    if (paramFragment.mView != null) {
      saveFragmentViewState(paramFragment);
    }
    Object localObject2 = localObject1;
    if (paramFragment.mSavedViewState != null)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putSparseParcelableArray("android:view_state", paramFragment.mSavedViewState);
    }
    Object localObject1 = localObject2;
    if (!paramFragment.mUserVisibleHint)
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Bundle();
      }
      ((Bundle)localObject1).putBoolean("android:user_visible_hint", paramFragment.mUserVisibleHint);
    }
    return (Bundle)localObject1;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment)
  {
    if (paramFragment.mIndex < 0)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    int i = paramFragment.mState;
    StringBuilder localStringBuilder = null;
    if (i > 0)
    {
      Bundle localBundle = saveFragmentBasicState(paramFragment);
      paramFragment = localStringBuilder;
      if (localBundle != null) {
        paramFragment = new Fragment.SavedState(localBundle);
      }
      return paramFragment;
    }
    return null;
  }
  
  void saveFragmentViewState(Fragment paramFragment)
  {
    if (paramFragment.mInnerView == null) {
      return;
    }
    SparseArray localSparseArray = this.mStateArray;
    if (localSparseArray == null) {
      this.mStateArray = new SparseArray();
    } else {
      localSparseArray.clear();
    }
    paramFragment.mInnerView.saveHierarchyState(this.mStateArray);
    if (this.mStateArray.size() > 0)
    {
      paramFragment.mSavedViewState = this.mStateArray;
      this.mStateArray = null;
    }
  }
  
  void saveNonConfig()
  {
    if (this.mActive != null)
    {
      Object localObject1 = null;
      Object localObject2 = localObject1;
      int i = 0;
      for (;;)
      {
        localObject3 = localObject1;
        localObject4 = localObject2;
        if (i >= this.mActive.size()) {
          break;
        }
        Object localObject5 = (Fragment)this.mActive.valueAt(i);
        localObject3 = localObject1;
        Object localObject6 = localObject2;
        if (localObject5 != null)
        {
          localObject4 = localObject1;
          int j;
          if (((Fragment)localObject5).mRetainInstance)
          {
            localObject3 = localObject1;
            if (localObject1 == null) {
              localObject3 = new ArrayList();
            }
            ((ArrayList)localObject3).add(localObject5);
            if (((Fragment)localObject5).mTarget != null) {
              j = ((Fragment)localObject5).mTarget.mIndex;
            } else {
              j = -1;
            }
            ((Fragment)localObject5).mTargetIndex = j;
            localObject4 = localObject3;
            if (DEBUG)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("retainNonConfig: keeping retained ");
              ((StringBuilder)localObject1).append(localObject5);
              Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
              localObject4 = localObject3;
            }
          }
          if (((Fragment)localObject5).mChildFragmentManager != null)
          {
            ((Fragment)localObject5).mChildFragmentManager.saveNonConfig();
            localObject5 = ((Fragment)localObject5).mChildFragmentManager.mSavedNonConfig;
          }
          else
          {
            localObject5 = ((Fragment)localObject5).mChildNonConfig;
          }
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = localObject2;
            if (localObject5 != null)
            {
              localObject2 = new ArrayList(this.mActive.size());
              for (j = 0;; j++)
              {
                localObject1 = localObject2;
                if (j >= i) {
                  break;
                }
                ((ArrayList)localObject2).add(null);
              }
            }
          }
          localObject3 = localObject4;
          localObject6 = localObject1;
          if (localObject1 != null)
          {
            ((ArrayList)localObject1).add(localObject5);
            localObject6 = localObject1;
            localObject3 = localObject4;
          }
        }
        i++;
        localObject1 = localObject3;
        localObject2 = localObject6;
      }
    }
    Object localObject3 = null;
    Object localObject4 = localObject3;
    if ((localObject3 == null) && (localObject4 == null)) {
      this.mSavedNonConfig = null;
    } else {
      this.mSavedNonConfig = new FragmentManagerNonConfig((List)localObject3, (List)localObject4);
    }
  }
  
  public void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord)
  {
    try
    {
      Object localObject;
      if (this.mBackStackIndices == null)
      {
        localObject = new java/util/ArrayList;
        ((ArrayList)localObject).<init>();
        this.mBackStackIndices = ((ArrayList)localObject);
      }
      int i = this.mBackStackIndices.size();
      int j = i;
      if (paramInt < i)
      {
        if (DEBUG)
        {
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          ((StringBuilder)localObject).append("Setting back stack index ");
          ((StringBuilder)localObject).append(paramInt);
          ((StringBuilder)localObject).append(" to ");
          ((StringBuilder)localObject).append(paramBackStackRecord);
          Log.v("FragmentManager", ((StringBuilder)localObject).toString());
        }
        this.mBackStackIndices.set(paramInt, paramBackStackRecord);
      }
      else
      {
        while (j < paramInt)
        {
          this.mBackStackIndices.add(null);
          if (this.mAvailBackStackIndices == null)
          {
            localObject = new java/util/ArrayList;
            ((ArrayList)localObject).<init>();
            this.mAvailBackStackIndices = ((ArrayList)localObject);
          }
          if (DEBUG)
          {
            localObject = new java/lang/StringBuilder;
            ((StringBuilder)localObject).<init>();
            ((StringBuilder)localObject).append("Adding available back stack index ");
            ((StringBuilder)localObject).append(j);
            Log.v("FragmentManager", ((StringBuilder)localObject).toString());
          }
          this.mAvailBackStackIndices.add(Integer.valueOf(j));
          j++;
        }
        if (DEBUG)
        {
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          ((StringBuilder)localObject).append("Adding back stack index ");
          ((StringBuilder)localObject).append(paramInt);
          ((StringBuilder)localObject).append(" with ");
          ((StringBuilder)localObject).append(paramBackStackRecord);
          Log.v("FragmentManager", ((StringBuilder)localObject).toString());
        }
        this.mBackStackIndices.add(paramBackStackRecord);
      }
      return;
    }
    finally {}
  }
  
  public void setPrimaryNavigationFragment(Fragment paramFragment)
  {
    if ((paramFragment != null) && ((this.mActive.get(paramFragment.mIndex) != paramFragment) || ((paramFragment.mHost != null) && (paramFragment.getFragmentManager() != this))))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not an active fragment of FragmentManager ");
      localStringBuilder.append(this);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    this.mPrimaryNav = paramFragment;
  }
  
  public void showFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("show: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (paramFragment.mHidden)
    {
      paramFragment.mHidden = false;
      paramFragment.mHiddenChanged ^= true;
    }
  }
  
  void startPendingDeferredFragments()
  {
    if (this.mActive == null) {
      return;
    }
    for (int i = 0; i < this.mActive.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mActive.valueAt(i);
      if (localFragment != null) {
        performPendingDeferredStart(localFragment);
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    Fragment localFragment = this.mParent;
    if (localFragment != null) {
      DebugUtils.buildShortClassTag(localFragment, localStringBuilder);
    } else {
      DebugUtils.buildShortClassTag(this.mHost, localStringBuilder);
    }
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks)
  {
    CopyOnWriteArrayList localCopyOnWriteArrayList = this.mLifecycleCallbacks;
    int i = 0;
    try
    {
      int j = this.mLifecycleCallbacks.size();
      while (i < j)
      {
        if (((Pair)this.mLifecycleCallbacks.get(i)).first == paramFragmentLifecycleCallbacks)
        {
          this.mLifecycleCallbacks.remove(i);
          break;
        }
        i++;
      }
      return;
    }
    finally {}
  }
  
  private static class AnimateOnHWLayerIfNeededListener
    extends FragmentManagerImpl.AnimationListenerWrapper
  {
    View mView;
    
    AnimateOnHWLayerIfNeededListener(View paramView, Animation.AnimationListener paramAnimationListener)
    {
      super(null);
      this.mView = paramView;
    }
    
    public void onAnimationEnd(Animation paramAnimation)
    {
      if ((!ViewCompat.isAttachedToWindow(this.mView)) && (Build.VERSION.SDK_INT < 24)) {
        this.mView.setLayerType(0, null);
      } else {
        this.mView.post(new Runnable()
        {
          public void run()
          {
            FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
          }
        });
      }
      super.onAnimationEnd(paramAnimation);
    }
  }
  
  private static class AnimationListenerWrapper
    implements Animation.AnimationListener
  {
    private final Animation.AnimationListener mWrapped;
    
    private AnimationListenerWrapper(Animation.AnimationListener paramAnimationListener)
    {
      this.mWrapped = paramAnimationListener;
    }
    
    public void onAnimationEnd(Animation paramAnimation)
    {
      Animation.AnimationListener localAnimationListener = this.mWrapped;
      if (localAnimationListener != null) {
        localAnimationListener.onAnimationEnd(paramAnimation);
      }
    }
    
    public void onAnimationRepeat(Animation paramAnimation)
    {
      Animation.AnimationListener localAnimationListener = this.mWrapped;
      if (localAnimationListener != null) {
        localAnimationListener.onAnimationRepeat(paramAnimation);
      }
    }
    
    public void onAnimationStart(Animation paramAnimation)
    {
      Animation.AnimationListener localAnimationListener = this.mWrapped;
      if (localAnimationListener != null) {
        localAnimationListener.onAnimationStart(paramAnimation);
      }
    }
  }
  
  private static class AnimationOrAnimator
  {
    public final Animation animation;
    public final Animator animator;
    
    private AnimationOrAnimator(Animator paramAnimator)
    {
      this.animation = null;
      this.animator = paramAnimator;
      if (paramAnimator != null) {
        return;
      }
      throw new IllegalStateException("Animator cannot be null");
    }
    
    private AnimationOrAnimator(Animation paramAnimation)
    {
      this.animation = paramAnimation;
      this.animator = null;
      if (paramAnimation != null) {
        return;
      }
      throw new IllegalStateException("Animation cannot be null");
    }
  }
  
  private static class AnimatorOnHWLayerIfNeededListener
    extends AnimatorListenerAdapter
  {
    View mView;
    
    AnimatorOnHWLayerIfNeededListener(View paramView)
    {
      this.mView = paramView;
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      this.mView.setLayerType(0, null);
      paramAnimator.removeListener(this);
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      this.mView.setLayerType(2, null);
    }
  }
  
  static class FragmentTag
  {
    public static final int[] Fragment = { 16842755, 16842960, 16842961 };
    public static final int Fragment_id = 1;
    public static final int Fragment_name = 0;
    public static final int Fragment_tag = 2;
  }
  
  static abstract interface OpGenerator
  {
    public abstract boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1);
  }
  
  private class PopBackStackState
    implements FragmentManagerImpl.OpGenerator
  {
    final int mFlags;
    final int mId;
    final String mName;
    
    PopBackStackState(String paramString, int paramInt1, int paramInt2)
    {
      this.mName = paramString;
      this.mId = paramInt1;
      this.mFlags = paramInt2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
    {
      if ((FragmentManagerImpl.this.mPrimaryNav != null) && (this.mId < 0) && (this.mName == null))
      {
        FragmentManager localFragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager();
        if ((localFragmentManager != null) && (localFragmentManager.popBackStackImmediate())) {
          return false;
        }
      }
      return FragmentManagerImpl.this.popBackStackState(paramArrayList, paramArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener
  {
    private final boolean mIsBack;
    private int mNumPostponed;
    private final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord paramBackStackRecord, boolean paramBoolean)
    {
      this.mIsBack = paramBoolean;
      this.mRecord = paramBackStackRecord;
    }
    
    public void cancelTransaction()
    {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    public void completeTransaction()
    {
      int i = this.mNumPostponed;
      int j = 0;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      }
      FragmentManagerImpl localFragmentManagerImpl = this.mRecord.mManager;
      int k = localFragmentManagerImpl.mAdded.size();
      while (j < k)
      {
        Fragment localFragment = (Fragment)localFragmentManagerImpl.mAdded.get(j);
        localFragment.setOnStartEnterTransitionListener(null);
        if ((i != 0) && (localFragment.isPostponed())) {
          localFragment.startPostponedEnterTransition();
        }
        j++;
      }
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, i ^ 0x1, true);
    }
    
    public boolean isReady()
    {
      boolean bool;
      if (this.mNumPostponed == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onStartEnterTransition()
    {
      this.mNumPostponed -= 1;
      if (this.mNumPostponed != 0) {
        return;
      }
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening()
    {
      this.mNumPostponed += 1;
    }
  }
}


/* Location:              ~/android/support/v4/app/FragmentManagerImpl.class
 *
 * Reversed by:           J
 */