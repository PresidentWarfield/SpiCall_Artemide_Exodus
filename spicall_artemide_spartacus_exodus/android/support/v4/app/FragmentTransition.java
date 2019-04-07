package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition
{
  private static final int[] INVERSE_OPS = { 0, 3, 0, 1, 5, 4, 7, 6, 9, 8 };
  
  private static void addSharedElementsWithMatchingNames(ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--)
    {
      View localView = (View)paramArrayMap.valueAt(i);
      if (paramCollection.contains(ViewCompat.getTransitionName(localView))) {
        paramArrayList.add(localView);
      }
    }
  }
  
  private static void addToFirstInLastOut(BackStackRecord paramBackStackRecord, BackStackRecord.Op paramOp, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean1, boolean paramBoolean2)
  {
    Fragment localFragment = paramOp.fragment;
    if (localFragment == null) {
      return;
    }
    int i = localFragment.mContainerId;
    if (i == 0) {
      return;
    }
    int j;
    if (paramBoolean1) {
      j = INVERSE_OPS[paramOp.cmd];
    } else {
      j = paramOp.cmd;
    }
    boolean bool = false;
    int k;
    int m;
    if (j != 1)
    {
      switch (j)
      {
      default: 
        j = 0;
        k = 0;
        m = 0;
        break;
      case 5: 
        if (paramBoolean2)
        {
          if ((localFragment.mHiddenChanged) && (!localFragment.mHidden) && (localFragment.mAdded)) {
            bool = true;
          } else {
            bool = false;
          }
        }
        else {
          bool = localFragment.mHidden;
        }
        j = 1;
        k = 0;
        m = 0;
        break;
      case 4: 
        if (paramBoolean2)
        {
          if ((localFragment.mHiddenChanged) && (localFragment.mAdded) && (localFragment.mHidden)) {
            j = 1;
          } else {
            j = 0;
          }
        }
        else if ((localFragment.mAdded) && (!localFragment.mHidden)) {
          j = 1;
        } else {
          j = 0;
        }
        m = j;
        j = 0;
        k = 1;
        break;
      case 3: 
      case 6: 
        if (paramBoolean2)
        {
          if ((!localFragment.mAdded) && (localFragment.mView != null) && (localFragment.mView.getVisibility() == 0) && (localFragment.mPostponedAlpha >= 0.0F)) {
            j = 1;
          } else {
            j = 0;
          }
        }
        else if ((localFragment.mAdded) && (!localFragment.mHidden)) {
          j = 1;
        } else {
          j = 0;
        }
        m = j;
        j = 0;
        k = 1;
        break;
      }
    }
    else
    {
      if (paramBoolean2) {
        bool = localFragment.mIsNewlyAdded;
      } else if ((!localFragment.mAdded) && (!localFragment.mHidden)) {
        bool = true;
      } else {
        bool = false;
      }
      j = 1;
      k = 0;
      m = 0;
    }
    paramOp = (FragmentContainerTransition)paramSparseArray.get(i);
    if (bool)
    {
      paramOp = ensureContainer(paramOp, paramSparseArray, i);
      paramOp.lastIn = localFragment;
      paramOp.lastInIsPop = paramBoolean1;
      paramOp.lastInTransaction = paramBackStackRecord;
    }
    if ((!paramBoolean2) && (j != 0))
    {
      if ((paramOp != null) && (paramOp.firstOut == localFragment)) {
        paramOp.firstOut = null;
      }
      localObject = paramBackStackRecord.mManager;
      if ((localFragment.mState < 1) && (((FragmentManagerImpl)localObject).mCurState >= 1) && (!paramBackStackRecord.mReorderingAllowed))
      {
        ((FragmentManagerImpl)localObject).makeActive(localFragment);
        ((FragmentManagerImpl)localObject).moveToState(localFragment, 1, 0, 0, false);
      }
    }
    Object localObject = paramOp;
    if (m != 0) {
      if (paramOp != null)
      {
        localObject = paramOp;
        if (paramOp.firstOut != null) {}
      }
      else
      {
        localObject = ensureContainer(paramOp, paramSparseArray, i);
        ((FragmentContainerTransition)localObject).firstOut = localFragment;
        ((FragmentContainerTransition)localObject).firstOutIsPop = paramBoolean1;
        ((FragmentContainerTransition)localObject).firstOutTransaction = paramBackStackRecord;
      }
    }
    if ((!paramBoolean2) && (k != 0) && (localObject != null) && (((FragmentContainerTransition)localObject).lastIn == localFragment)) {
      ((FragmentContainerTransition)localObject).lastIn = null;
    }
  }
  
  public static void calculateFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    int i = paramBackStackRecord.mOps.size();
    for (int j = 0; j < i; j++) {
      addToFirstInLastOut(paramBackStackRecord, (BackStackRecord.Op)paramBackStackRecord.mOps.get(j), paramSparseArray, false, paramBoolean);
    }
  }
  
  private static ArrayMap<String, String> calculateNameOverrides(int paramInt1, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt2, int paramInt3)
  {
    ArrayMap localArrayMap = new ArrayMap();
    paramInt3--;
    while (paramInt3 >= paramInt2)
    {
      Object localObject = (BackStackRecord)paramArrayList.get(paramInt3);
      if (((BackStackRecord)localObject).interactsWith(paramInt1))
      {
        boolean bool = ((Boolean)paramArrayList1.get(paramInt3)).booleanValue();
        if (((BackStackRecord)localObject).mSharedElementSourceNames != null)
        {
          int i = ((BackStackRecord)localObject).mSharedElementSourceNames.size();
          ArrayList localArrayList1;
          ArrayList localArrayList2;
          if (bool)
          {
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          else
          {
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          for (int j = 0; j < i; j++)
          {
            String str1 = (String)localArrayList2.get(j);
            localObject = (String)localArrayList1.get(j);
            String str2 = (String)localArrayMap.remove(localObject);
            if (str2 != null) {
              localArrayMap.put(str1, str2);
            } else {
              localArrayMap.put(str1, localObject);
            }
          }
        }
      }
      paramInt3--;
    }
    return localArrayMap;
  }
  
  public static void calculatePopFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    if (!paramBackStackRecord.mManager.mContainer.onHasView()) {
      return;
    }
    for (int i = paramBackStackRecord.mOps.size() - 1; i >= 0; i--) {
      addToFirstInLastOut(paramBackStackRecord, (BackStackRecord.Op)paramBackStackRecord.mOps.get(i), paramSparseArray, true, paramBoolean);
    }
  }
  
  private static void callSharedElementStartEnd(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean1, ArrayMap<String, View> paramArrayMap, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      paramFragment1 = paramFragment2.getEnterTransitionCallback();
    } else {
      paramFragment1 = paramFragment1.getEnterTransitionCallback();
    }
    if (paramFragment1 != null)
    {
      paramFragment2 = new ArrayList();
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      int j;
      if (paramArrayMap == null) {
        j = 0;
      } else {
        j = paramArrayMap.size();
      }
      while (i < j)
      {
        localArrayList.add(paramArrayMap.keyAt(i));
        paramFragment2.add(paramArrayMap.valueAt(i));
        i++;
      }
      if (paramBoolean2) {
        paramFragment1.onSharedElementStart(localArrayList, paramFragment2, null);
      } else {
        paramFragment1.onSharedElementEnd(localArrayList, paramFragment2, null);
      }
    }
  }
  
  private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    Object localObject = paramFragmentContainerTransition.lastIn;
    View localView = ((Fragment)localObject).getView();
    if ((!paramArrayMap.isEmpty()) && (paramObject != null) && (localView != null))
    {
      ArrayMap localArrayMap = new ArrayMap();
      FragmentTransitionCompat21.findNamedViews(localArrayMap, localView);
      paramObject = paramFragmentContainerTransition.lastInTransaction;
      if (paramFragmentContainerTransition.lastInIsPop)
      {
        paramFragmentContainerTransition = ((Fragment)localObject).getExitTransitionCallback();
        paramObject = ((BackStackRecord)paramObject).mSharedElementSourceNames;
      }
      else
      {
        paramFragmentContainerTransition = ((Fragment)localObject).getEnterTransitionCallback();
        paramObject = ((BackStackRecord)paramObject).mSharedElementTargetNames;
      }
      if (paramObject != null) {
        localArrayMap.retainAll((Collection)paramObject);
      }
      if (paramFragmentContainerTransition != null)
      {
        paramFragmentContainerTransition.onMapSharedElements((List)paramObject, localArrayMap);
        for (int i = ((ArrayList)paramObject).size() - 1; i >= 0; i--)
        {
          localObject = (String)((ArrayList)paramObject).get(i);
          paramFragmentContainerTransition = (View)localArrayMap.get(localObject);
          if (paramFragmentContainerTransition == null)
          {
            paramFragmentContainerTransition = findKeyForValue(paramArrayMap, (String)localObject);
            if (paramFragmentContainerTransition != null) {
              paramArrayMap.remove(paramFragmentContainerTransition);
            }
          }
          else if (!((String)localObject).equals(ViewCompat.getTransitionName(paramFragmentContainerTransition)))
          {
            localObject = findKeyForValue(paramArrayMap, (String)localObject);
            if (localObject != null) {
              paramArrayMap.put(localObject, ViewCompat.getTransitionName(paramFragmentContainerTransition));
            }
          }
        }
      }
      retainValues(paramArrayMap, localArrayMap);
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    if ((!paramArrayMap.isEmpty()) && (paramObject != null))
    {
      Object localObject = paramFragmentContainerTransition.firstOut;
      ArrayMap localArrayMap = new ArrayMap();
      FragmentTransitionCompat21.findNamedViews(localArrayMap, ((Fragment)localObject).getView());
      paramObject = paramFragmentContainerTransition.firstOutTransaction;
      if (paramFragmentContainerTransition.firstOutIsPop)
      {
        paramFragmentContainerTransition = ((Fragment)localObject).getEnterTransitionCallback();
        paramObject = ((BackStackRecord)paramObject).mSharedElementTargetNames;
      }
      else
      {
        paramFragmentContainerTransition = ((Fragment)localObject).getExitTransitionCallback();
        paramObject = ((BackStackRecord)paramObject).mSharedElementSourceNames;
      }
      localArrayMap.retainAll((Collection)paramObject);
      if (paramFragmentContainerTransition != null)
      {
        paramFragmentContainerTransition.onMapSharedElements((List)paramObject, localArrayMap);
        for (int i = ((ArrayList)paramObject).size() - 1; i >= 0; i--)
        {
          localObject = (String)((ArrayList)paramObject).get(i);
          paramFragmentContainerTransition = (View)localArrayMap.get(localObject);
          if (paramFragmentContainerTransition == null)
          {
            paramArrayMap.remove(localObject);
          }
          else if (!((String)localObject).equals(ViewCompat.getTransitionName(paramFragmentContainerTransition)))
          {
            localObject = (String)paramArrayMap.remove(localObject);
            paramArrayMap.put(ViewCompat.getTransitionName(paramFragmentContainerTransition), localObject);
          }
        }
      }
      paramArrayMap.retainAll(localArrayMap.keySet());
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static ArrayList<View> configureEnteringExitingViews(Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, View paramView)
  {
    if (paramObject != null)
    {
      ArrayList localArrayList = new ArrayList();
      paramFragment = paramFragment.getView();
      if (paramFragment != null) {
        FragmentTransitionCompat21.captureTransitioningViews(localArrayList, paramFragment);
      }
      if (paramArrayList != null) {
        localArrayList.removeAll(paramArrayList);
      }
      paramFragment = localArrayList;
      if (!localArrayList.isEmpty())
      {
        localArrayList.add(paramView);
        FragmentTransitionCompat21.addTargets(paramObject, localArrayList);
        paramFragment = localArrayList;
      }
    }
    else
    {
      paramFragment = null;
    }
    return paramFragment;
  }
  
  private static Object configureSharedElementsOrdered(ViewGroup paramViewGroup, final View paramView, ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition paramFragmentContainerTransition, final ArrayList<View> paramArrayList1, final ArrayList<View> paramArrayList2, final Object paramObject1, final Object paramObject2)
  {
    final Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    if ((localFragment1 != null) && (localFragment2 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      final Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(localFragment1, localFragment2, bool);
      }
      ArrayMap localArrayMap = captureOutSharedElements(paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        paramArrayList1.addAll(localArrayMap.values());
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (localObject == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment1, localFragment2, bool, localArrayMap, true);
      if (localObject != null)
      {
        Rect localRect = new Rect();
        FragmentTransitionCompat21.setSharedElementTargets(localObject, paramView, paramArrayList1);
        setOutEpicenter(localObject, paramObject2, localArrayMap, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        if (paramObject1 != null) {
          FragmentTransitionCompat21.setEpicenter(paramObject1, localRect);
        }
        paramObject2 = localRect;
      }
      else
      {
        paramObject2 = null;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          ArrayMap localArrayMap = FragmentTransition.captureInSharedElements(this.val$nameOverrides, localObject, paramFragmentContainerTransition);
          if (localArrayMap != null)
          {
            paramArrayList2.addAll(localArrayMap.values());
            paramArrayList2.add(paramView);
          }
          FragmentTransition.callSharedElementStartEnd(localFragment1, localFragment2, bool, localArrayMap, false);
          Object localObject = localObject;
          if (localObject != null)
          {
            FragmentTransitionCompat21.swapSharedElementTargets(localObject, paramArrayList1, paramArrayList2);
            localObject = FragmentTransition.getInEpicenterView(localArrayMap, paramFragmentContainerTransition, paramObject1, bool);
            if (localObject != null) {
              FragmentTransitionCompat21.getBoundsOnScreen((View)localObject, paramObject2);
            }
          }
        }
      });
      return localObject;
    }
    return null;
  }
  
  private static Object configureSharedElementsReordered(ViewGroup paramViewGroup, final View paramView, final ArrayMap<String, String> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2, Object paramObject1, Object paramObject2)
  {
    Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    if (localFragment1 != null) {
      localFragment1.getView().setVisibility(0);
    }
    if ((localFragment1 != null) && (localFragment2 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(localFragment1, localFragment2, bool);
      }
      ArrayMap localArrayMap1 = captureOutSharedElements(paramArrayMap, localObject, paramFragmentContainerTransition);
      final ArrayMap localArrayMap2 = captureInSharedElements(paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty())
      {
        if (localArrayMap1 != null) {
          localArrayMap1.clear();
        }
        if (localArrayMap2 != null) {
          localArrayMap2.clear();
        }
        localObject = null;
      }
      else
      {
        addSharedElementsWithMatchingNames(paramArrayList1, localArrayMap1, paramArrayMap.keySet());
        addSharedElementsWithMatchingNames(paramArrayList2, localArrayMap2, paramArrayMap.values());
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (localObject == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment1, localFragment2, bool, localArrayMap1, true);
      if (localObject != null)
      {
        paramArrayList2.add(paramView);
        FragmentTransitionCompat21.setSharedElementTargets(localObject, paramView, paramArrayList1);
        setOutEpicenter(localObject, paramObject2, localArrayMap1, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        paramView = new Rect();
        paramArrayMap = getInEpicenterView(localArrayMap2, paramFragmentContainerTransition, paramObject1, bool);
        if (paramArrayMap != null) {
          FragmentTransitionCompat21.setEpicenter(paramObject1, paramView);
        }
      }
      else
      {
        paramArrayMap = null;
        paramView = paramArrayMap;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          FragmentTransition.callSharedElementStartEnd(this.val$inFragment, localFragment2, bool, localArrayMap2, false);
          View localView = paramArrayMap;
          if (localView != null) {
            FragmentTransitionCompat21.getBoundsOnScreen(localView, paramView);
          }
        }
      });
      return localObject;
    }
    return null;
  }
  
  private static void configureTransitionsOrdered(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap)
  {
    if (paramFragmentManagerImpl.mContainer.onHasView()) {
      paramFragmentManagerImpl = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManagerImpl = null;
    }
    if (paramFragmentManagerImpl == null) {
      return;
    }
    Fragment localFragment = paramFragmentContainerTransition.lastIn;
    Object localObject1 = paramFragmentContainerTransition.firstOut;
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    Object localObject2 = getEnterTransition(localFragment, bool1);
    Object localObject3 = getExitTransition((Fragment)localObject1, bool2);
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject4 = configureSharedElementsOrdered(paramFragmentManagerImpl, paramView, paramArrayMap, paramFragmentContainerTransition, localArrayList1, localArrayList2, localObject2, localObject3);
    if ((localObject2 == null) && (localObject4 == null) && (localObject3 == null)) {
      return;
    }
    localArrayList1 = configureEnteringExitingViews(localObject3, (Fragment)localObject1, localArrayList1, paramView);
    if ((localArrayList1 != null) && (!localArrayList1.isEmpty())) {
      break label160;
    }
    localObject3 = null;
    label160:
    FragmentTransitionCompat21.addTarget(localObject2, paramView);
    paramFragmentContainerTransition = mergeTransitions(localObject2, localObject3, localObject4, localFragment, paramFragmentContainerTransition.lastInIsPop);
    if (paramFragmentContainerTransition != null)
    {
      localObject1 = new ArrayList();
      FragmentTransitionCompat21.scheduleRemoveTargets(paramFragmentContainerTransition, localObject2, (ArrayList)localObject1, localObject3, localArrayList1, localObject4, localArrayList2);
      scheduleTargetChange(paramFragmentManagerImpl, localFragment, paramView, localArrayList2, localObject2, (ArrayList)localObject1, localObject3, localArrayList1);
      FragmentTransitionCompat21.setNameOverridesOrdered(paramFragmentManagerImpl, localArrayList2, paramArrayMap);
      FragmentTransitionCompat21.beginDelayedTransition(paramFragmentManagerImpl, paramFragmentContainerTransition);
      FragmentTransitionCompat21.scheduleNameReset(paramFragmentManagerImpl, localArrayList2, paramArrayMap);
    }
  }
  
  private static void configureTransitionsReordered(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap)
  {
    if (paramFragmentManagerImpl.mContainer.onHasView()) {
      paramFragmentManagerImpl = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManagerImpl = null;
    }
    if (paramFragmentManagerImpl == null) {
      return;
    }
    Object localObject1 = paramFragmentContainerTransition.lastIn;
    Object localObject2 = paramFragmentContainerTransition.firstOut;
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject3 = getEnterTransition((Fragment)localObject1, bool1);
    Object localObject4 = getExitTransition((Fragment)localObject2, bool2);
    Object localObject5 = configureSharedElementsReordered(paramFragmentManagerImpl, paramView, paramArrayMap, paramFragmentContainerTransition, localArrayList2, localArrayList1, localObject3, localObject4);
    if ((localObject3 == null) && (localObject5 == null) && (localObject4 == null)) {
      return;
    }
    paramFragmentContainerTransition = (FragmentContainerTransition)localObject4;
    localObject4 = configureEnteringExitingViews(paramFragmentContainerTransition, (Fragment)localObject2, localArrayList2, paramView);
    paramView = configureEnteringExitingViews(localObject3, (Fragment)localObject1, localArrayList1, paramView);
    setViewVisibility(paramView, 4);
    localObject1 = mergeTransitions(localObject3, paramFragmentContainerTransition, localObject5, (Fragment)localObject1, bool1);
    if (localObject1 != null)
    {
      replaceHide(paramFragmentContainerTransition, (Fragment)localObject2, (ArrayList)localObject4);
      localObject2 = FragmentTransitionCompat21.prepareSetNameOverridesReordered(localArrayList1);
      FragmentTransitionCompat21.scheduleRemoveTargets(localObject1, localObject3, paramView, paramFragmentContainerTransition, (ArrayList)localObject4, localObject5, localArrayList1);
      FragmentTransitionCompat21.beginDelayedTransition(paramFragmentManagerImpl, localObject1);
      FragmentTransitionCompat21.setNameOverridesReordered(paramFragmentManagerImpl, localArrayList2, localArrayList1, (ArrayList)localObject2, paramArrayMap);
      setViewVisibility(paramView, 0);
      FragmentTransitionCompat21.swapSharedElementTargets(localObject5, localArrayList2, localArrayList1);
    }
  }
  
  private static FragmentContainerTransition ensureContainer(FragmentContainerTransition paramFragmentContainerTransition, SparseArray<FragmentContainerTransition> paramSparseArray, int paramInt)
  {
    FragmentContainerTransition localFragmentContainerTransition = paramFragmentContainerTransition;
    if (paramFragmentContainerTransition == null)
    {
      localFragmentContainerTransition = new FragmentContainerTransition();
      paramSparseArray.put(paramInt, localFragmentContainerTransition);
    }
    return localFragmentContainerTransition;
  }
  
  private static String findKeyForValue(ArrayMap<String, String> paramArrayMap, String paramString)
  {
    int i = paramArrayMap.size();
    for (int j = 0; j < i; j++) {
      if (paramString.equals(paramArrayMap.valueAt(j))) {
        return (String)paramArrayMap.keyAt(j);
      }
    }
    return null;
  }
  
  private static Object getEnterTransition(Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReenterTransition();
    } else {
      paramFragment = paramFragment.getEnterTransition();
    }
    return FragmentTransitionCompat21.cloneTransition(paramFragment);
  }
  
  private static Object getExitTransition(Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReturnTransition();
    } else {
      paramFragment = paramFragment.getExitTransition();
    }
    return FragmentTransitionCompat21.cloneTransition(paramFragment);
  }
  
  private static View getInEpicenterView(ArrayMap<String, View> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, Object paramObject, boolean paramBoolean)
  {
    paramFragmentContainerTransition = paramFragmentContainerTransition.lastInTransaction;
    if ((paramObject != null) && (paramArrayMap != null) && (paramFragmentContainerTransition.mSharedElementSourceNames != null) && (!paramFragmentContainerTransition.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementSourceNames.get(0);
      } else {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementTargetNames.get(0);
      }
      return (View)paramArrayMap.get(paramFragmentContainerTransition);
    }
    return null;
  }
  
  private static Object getSharedElementTransition(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean)
  {
    if ((paramFragment1 != null) && (paramFragment2 != null))
    {
      if (paramBoolean) {
        paramFragment1 = paramFragment2.getSharedElementReturnTransition();
      } else {
        paramFragment1 = paramFragment1.getSharedElementEnterTransition();
      }
      return FragmentTransitionCompat21.wrapTransitionInSet(FragmentTransitionCompat21.cloneTransition(paramFragment1));
    }
    return null;
  }
  
  private static Object mergeTransitions(Object paramObject1, Object paramObject2, Object paramObject3, Fragment paramFragment, boolean paramBoolean)
  {
    if ((paramObject1 != null) && (paramObject2 != null) && (paramFragment != null))
    {
      if (paramBoolean) {
        paramBoolean = paramFragment.getAllowReturnTransitionOverlap();
      } else {
        paramBoolean = paramFragment.getAllowEnterTransitionOverlap();
      }
    }
    else {
      paramBoolean = true;
    }
    if (paramBoolean) {
      paramObject1 = FragmentTransitionCompat21.mergeTransitionsTogether(paramObject2, paramObject1, paramObject3);
    } else {
      paramObject1 = FragmentTransitionCompat21.mergeTransitionsInSequence(paramObject2, paramObject1, paramObject3);
    }
    return paramObject1;
  }
  
  private static void replaceHide(Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList)
  {
    if ((paramFragment != null) && (paramObject != null) && (paramFragment.mAdded) && (paramFragment.mHidden) && (paramFragment.mHiddenChanged))
    {
      paramFragment.setHideReplaced(true);
      FragmentTransitionCompat21.scheduleHideFragmentView(paramObject, paramFragment.getView(), paramArrayList);
      OneShotPreDrawListener.add(paramFragment.mContainer, new Runnable()
      {
        public void run()
        {
          FragmentTransition.setViewVisibility(this.val$exitingViews, 4);
        }
      });
    }
  }
  
  private static void retainValues(ArrayMap<String, String> paramArrayMap, ArrayMap<String, View> paramArrayMap1)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      if (!paramArrayMap1.containsKey((String)paramArrayMap.valueAt(i))) {
        paramArrayMap.removeAt(i);
      }
    }
  }
  
  private static void scheduleTargetChange(ViewGroup paramViewGroup, final Fragment paramFragment, final View paramView, final ArrayList<View> paramArrayList1, Object paramObject1, final ArrayList<View> paramArrayList2, final Object paramObject2, final ArrayList<View> paramArrayList3)
  {
    OneShotPreDrawListener.add(paramViewGroup, new Runnable()
    {
      public void run()
      {
        Object localObject = this.val$enterTransition;
        if (localObject != null)
        {
          FragmentTransitionCompat21.removeTarget(localObject, paramView);
          localObject = FragmentTransition.configureEnteringExitingViews(this.val$enterTransition, paramFragment, paramArrayList1, paramView);
          paramArrayList2.addAll((Collection)localObject);
        }
        if (paramArrayList3 != null)
        {
          if (paramObject2 != null)
          {
            localObject = new ArrayList();
            ((ArrayList)localObject).add(paramView);
            FragmentTransitionCompat21.replaceTargets(paramObject2, paramArrayList3, (ArrayList)localObject);
          }
          paramArrayList3.clear();
          paramArrayList3.add(paramView);
        }
      }
    });
  }
  
  private static void setOutEpicenter(Object paramObject1, Object paramObject2, ArrayMap<String, View> paramArrayMap, boolean paramBoolean, BackStackRecord paramBackStackRecord)
  {
    if ((paramBackStackRecord.mSharedElementSourceNames != null) && (!paramBackStackRecord.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementTargetNames.get(0);
      } else {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementSourceNames.get(0);
      }
      paramArrayMap = (View)paramArrayMap.get(paramBackStackRecord);
      FragmentTransitionCompat21.setEpicenter(paramObject1, paramArrayMap);
      if (paramObject2 != null) {
        FragmentTransitionCompat21.setEpicenter(paramObject2, paramArrayMap);
      }
    }
  }
  
  private static void setViewVisibility(ArrayList<View> paramArrayList, int paramInt)
  {
    if (paramArrayList == null) {
      return;
    }
    for (int i = paramArrayList.size() - 1; i >= 0; i--) {
      ((View)paramArrayList.get(i)).setVisibility(paramInt);
    }
  }
  
  static void startTransitions(FragmentManagerImpl paramFragmentManagerImpl, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramFragmentManagerImpl.mCurState < 1) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      SparseArray localSparseArray = new SparseArray();
      Object localObject;
      for (int i = paramInt1; i < paramInt2; i++)
      {
        localObject = (BackStackRecord)paramArrayList.get(i);
        if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
          calculatePopFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
        } else {
          calculateFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
        }
      }
      if (localSparseArray.size() != 0)
      {
        localObject = new View(paramFragmentManagerImpl.mHost.getContext());
        int j = localSparseArray.size();
        for (i = 0; i < j; i++)
        {
          int k = localSparseArray.keyAt(i);
          ArrayMap localArrayMap = calculateNameOverrides(k, paramArrayList, paramArrayList1, paramInt1, paramInt2);
          FragmentContainerTransition localFragmentContainerTransition = (FragmentContainerTransition)localSparseArray.valueAt(i);
          if (paramBoolean) {
            configureTransitionsReordered(paramFragmentManagerImpl, k, localFragmentContainerTransition, (View)localObject, localArrayMap);
          } else {
            configureTransitionsOrdered(paramFragmentManagerImpl, k, localFragmentContainerTransition, (View)localObject, localArrayMap);
          }
        }
      }
    }
  }
  
  static class FragmentContainerTransition
  {
    public Fragment firstOut;
    public boolean firstOutIsPop;
    public BackStackRecord firstOutTransaction;
    public Fragment lastIn;
    public boolean lastInIsPop;
    public BackStackRecord lastInTransaction;
  }
}


/* Location:              ~/android/support/v4/app/FragmentTransition.class
 *
 * Reversed by:           J
 */