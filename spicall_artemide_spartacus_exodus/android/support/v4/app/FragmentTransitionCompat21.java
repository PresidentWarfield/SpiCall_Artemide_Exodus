package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class FragmentTransitionCompat21
{
  public static void addTarget(Object paramObject, View paramView)
  {
    if (paramObject != null) {
      ((Transition)paramObject).addTarget(paramView);
    }
  }
  
  public static void addTargets(Object paramObject, ArrayList<View> paramArrayList)
  {
    paramObject = (Transition)paramObject;
    if (paramObject == null) {
      return;
    }
    boolean bool = paramObject instanceof TransitionSet;
    int i = 0;
    int j = 0;
    if (bool)
    {
      paramObject = (TransitionSet)paramObject;
      i = ((TransitionSet)paramObject).getTransitionCount();
      while (j < i)
      {
        addTargets(((TransitionSet)paramObject).getTransitionAt(j), paramArrayList);
        j++;
      }
    }
    if ((!hasSimpleTarget((Transition)paramObject)) && (isNullOrEmpty(((Transition)paramObject).getTargets())))
    {
      int k = paramArrayList.size();
      for (j = i; j < k; j++) {
        ((Transition)paramObject).addTarget((View)paramArrayList.get(j));
      }
    }
  }
  
  public static void beginDelayedTransition(ViewGroup paramViewGroup, Object paramObject)
  {
    TransitionManager.beginDelayedTransition(paramViewGroup, (Transition)paramObject);
  }
  
  private static void bfsAddViewChildren(List<View> paramList, View paramView)
  {
    int i = paramList.size();
    if (containedBeforeIndex(paramList, paramView, i)) {
      return;
    }
    paramList.add(paramView);
    for (int j = i; j < paramList.size(); j++)
    {
      paramView = (View)paramList.get(j);
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        int k = paramView.getChildCount();
        for (int m = 0; m < k; m++)
        {
          View localView = paramView.getChildAt(m);
          if (!containedBeforeIndex(paramList, localView, i)) {
            paramList.add(localView);
          }
        }
      }
    }
  }
  
  public static void captureTransitioningViews(ArrayList<View> paramArrayList, View paramView)
  {
    if (paramView.getVisibility() == 0) {
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        if (paramView.isTransitionGroup())
        {
          paramArrayList.add(paramView);
        }
        else
        {
          int i = paramView.getChildCount();
          for (int j = 0; j < i; j++) {
            captureTransitioningViews(paramArrayList, paramView.getChildAt(j));
          }
        }
      }
      else
      {
        paramArrayList.add(paramView);
      }
    }
  }
  
  public static Object cloneTransition(Object paramObject)
  {
    if (paramObject != null) {
      paramObject = ((Transition)paramObject).clone();
    } else {
      paramObject = null;
    }
    return paramObject;
  }
  
  private static boolean containedBeforeIndex(List<View> paramList, View paramView, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      if (paramList.get(i) == paramView) {
        return true;
      }
    }
    return false;
  }
  
  private static String findKeyForValue(Map<String, String> paramMap, String paramString)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (paramString.equals(localEntry.getValue())) {
        return (String)localEntry.getKey();
      }
    }
    return null;
  }
  
  public static void findNamedViews(Map<String, View> paramMap, View paramView)
  {
    if (paramView.getVisibility() == 0)
    {
      String str = paramView.getTransitionName();
      if (str != null) {
        paramMap.put(str, paramView);
      }
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        int i = paramView.getChildCount();
        for (int j = 0; j < i; j++) {
          findNamedViews(paramMap, paramView.getChildAt(j));
        }
      }
    }
  }
  
  public static void getBoundsOnScreen(View paramView, Rect paramRect)
  {
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    paramRect.set(arrayOfInt[0], arrayOfInt[1], arrayOfInt[0] + paramView.getWidth(), arrayOfInt[1] + paramView.getHeight());
  }
  
  private static boolean hasSimpleTarget(Transition paramTransition)
  {
    boolean bool;
    if ((isNullOrEmpty(paramTransition.getTargetIds())) && (isNullOrEmpty(paramTransition.getTargetNames())) && (isNullOrEmpty(paramTransition.getTargetTypes()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isNullOrEmpty(List paramList)
  {
    boolean bool;
    if ((paramList != null) && (!paramList.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static Object mergeTransitionsInSequence(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    paramObject1 = (Transition)paramObject1;
    paramObject2 = (Transition)paramObject2;
    paramObject3 = (Transition)paramObject3;
    if ((paramObject1 != null) && (paramObject2 != null)) {
      paramObject1 = new TransitionSet().addTransition((Transition)paramObject1).addTransition((Transition)paramObject2).setOrdering(1);
    } else if (paramObject1 == null) {
      if (paramObject2 != null) {
        paramObject1 = paramObject2;
      } else {
        paramObject1 = null;
      }
    }
    if (paramObject3 != null)
    {
      paramObject2 = new TransitionSet();
      if (paramObject1 != null) {
        ((TransitionSet)paramObject2).addTransition((Transition)paramObject1);
      }
      ((TransitionSet)paramObject2).addTransition((Transition)paramObject3);
      return paramObject2;
    }
    return paramObject1;
  }
  
  public static Object mergeTransitionsTogether(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    TransitionSet localTransitionSet = new TransitionSet();
    if (paramObject1 != null) {
      localTransitionSet.addTransition((Transition)paramObject1);
    }
    if (paramObject2 != null) {
      localTransitionSet.addTransition((Transition)paramObject2);
    }
    if (paramObject3 != null) {
      localTransitionSet.addTransition((Transition)paramObject3);
    }
    return localTransitionSet;
  }
  
  public static ArrayList<String> prepareSetNameOverridesReordered(ArrayList<View> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
    {
      View localView = (View)paramArrayList.get(j);
      localArrayList.add(localView.getTransitionName());
      localView.setTransitionName(null);
    }
    return localArrayList;
  }
  
  public static void removeTarget(Object paramObject, View paramView)
  {
    if (paramObject != null) {
      ((Transition)paramObject).removeTarget(paramView);
    }
  }
  
  public static void replaceTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2)
  {
    paramObject = (Transition)paramObject;
    boolean bool = paramObject instanceof TransitionSet;
    int i = 0;
    int j = 0;
    if (bool)
    {
      paramObject = (TransitionSet)paramObject;
      i = ((TransitionSet)paramObject).getTransitionCount();
      while (j < i)
      {
        replaceTargets(((TransitionSet)paramObject).getTransitionAt(j), paramArrayList1, paramArrayList2);
        j++;
      }
    }
    if (!hasSimpleTarget((Transition)paramObject))
    {
      List localList = ((Transition)paramObject).getTargets();
      if ((localList != null) && (localList.size() == paramArrayList1.size()) && (localList.containsAll(paramArrayList1)))
      {
        if (paramArrayList2 == null) {
          j = 0;
        } else {
          j = paramArrayList2.size();
        }
        while (i < j)
        {
          ((Transition)paramObject).addTarget((View)paramArrayList2.get(i));
          i++;
        }
        for (j = paramArrayList1.size() - 1; j >= 0; j--) {
          ((Transition)paramObject).removeTarget((View)paramArrayList1.get(j));
        }
      }
    }
  }
  
  public static void scheduleHideFragmentView(Object paramObject, View paramView, final ArrayList<View> paramArrayList)
  {
    ((Transition)paramObject).addListener(new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition) {}
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition.removeListener(this);
        this.val$fragmentView.setVisibility(8);
        int i = paramArrayList.size();
        for (int j = 0; j < i; j++) {
          ((View)paramArrayList.get(j)).setVisibility(0);
        }
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition) {}
      
      public void onTransitionResume(Transition paramAnonymousTransition) {}
      
      public void onTransitionStart(Transition paramAnonymousTransition) {}
    });
  }
  
  public static void scheduleNameReset(ViewGroup paramViewGroup, ArrayList<View> paramArrayList, final Map<String, String> paramMap)
  {
    OneShotPreDrawListener.add(paramViewGroup, new Runnable()
    {
      public void run()
      {
        int i = this.val$sharedElementsIn.size();
        for (int j = 0; j < i; j++)
        {
          View localView = (View)this.val$sharedElementsIn.get(j);
          String str = localView.getTransitionName();
          localView.setTransitionName((String)paramMap.get(str));
        }
      }
    });
  }
  
  public static void scheduleRemoveTargets(Object paramObject1, Object paramObject2, final ArrayList<View> paramArrayList1, final Object paramObject3, final ArrayList<View> paramArrayList2, final Object paramObject4, final ArrayList<View> paramArrayList3)
  {
    ((Transition)paramObject1).addListener(new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition) {}
      
      public void onTransitionEnd(Transition paramAnonymousTransition) {}
      
      public void onTransitionPause(Transition paramAnonymousTransition) {}
      
      public void onTransitionResume(Transition paramAnonymousTransition) {}
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition = this.val$enterTransition;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.replaceTargets(paramAnonymousTransition, paramArrayList1, null);
        }
        paramAnonymousTransition = paramObject3;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.replaceTargets(paramAnonymousTransition, paramArrayList2, null);
        }
        paramAnonymousTransition = paramObject4;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.replaceTargets(paramAnonymousTransition, paramArrayList3, null);
        }
      }
    });
  }
  
  public static void setEpicenter(Object paramObject, Rect paramRect)
  {
    if (paramObject != null) {
      ((Transition)paramObject).setEpicenterCallback(new Transition.EpicenterCallback()
      {
        public Rect onGetEpicenter(Transition paramAnonymousTransition)
        {
          paramAnonymousTransition = this.val$epicenter;
          if ((paramAnonymousTransition != null) && (!paramAnonymousTransition.isEmpty())) {
            return this.val$epicenter;
          }
          return null;
        }
      });
    }
  }
  
  public static void setEpicenter(Object paramObject, View paramView)
  {
    if (paramView != null)
    {
      Transition localTransition = (Transition)paramObject;
      paramObject = new Rect();
      getBoundsOnScreen(paramView, (Rect)paramObject);
      localTransition.setEpicenterCallback(new Transition.EpicenterCallback()
      {
        public Rect onGetEpicenter(Transition paramAnonymousTransition)
        {
          return this.val$epicenter;
        }
      });
    }
  }
  
  public static void setNameOverridesOrdered(View paramView, ArrayList<View> paramArrayList, final Map<String, String> paramMap)
  {
    OneShotPreDrawListener.add(paramView, new Runnable()
    {
      public void run()
      {
        int i = this.val$sharedElementsIn.size();
        for (int j = 0; j < i; j++)
        {
          View localView = (View)this.val$sharedElementsIn.get(j);
          String str = localView.getTransitionName();
          if (str != null) {
            localView.setTransitionName(FragmentTransitionCompat21.findKeyForValue(paramMap, str));
          }
        }
      }
    });
  }
  
  public static void setNameOverridesReordered(View paramView, final ArrayList<View> paramArrayList1, final ArrayList<View> paramArrayList2, final ArrayList<String> paramArrayList, Map<String, String> paramMap)
  {
    int i = paramArrayList2.size();
    final ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      Object localObject = (View)paramArrayList1.get(j);
      String str = ((View)localObject).getTransitionName();
      localArrayList.add(str);
      if (str != null)
      {
        ((View)localObject).setTransitionName(null);
        localObject = (String)paramMap.get(str);
        for (int k = 0; k < i; k++) {
          if (((String)localObject).equals(paramArrayList.get(k)))
          {
            ((View)paramArrayList2.get(k)).setTransitionName(str);
            break;
          }
        }
      }
    }
    OneShotPreDrawListener.add(paramView, new Runnable()
    {
      public void run()
      {
        for (int i = 0; i < this.val$numSharedElements; i++)
        {
          ((View)paramArrayList2.get(i)).setTransitionName((String)paramArrayList.get(i));
          ((View)paramArrayList1.get(i)).setTransitionName((String)localArrayList.get(i));
        }
      }
    });
  }
  
  public static void setSharedElementTargets(Object paramObject, View paramView, ArrayList<View> paramArrayList)
  {
    TransitionSet localTransitionSet = (TransitionSet)paramObject;
    paramObject = localTransitionSet.getTargets();
    ((List)paramObject).clear();
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++) {
      bfsAddViewChildren((List)paramObject, (View)paramArrayList.get(j));
    }
    ((List)paramObject).add(paramView);
    paramArrayList.add(paramView);
    addTargets(localTransitionSet, paramArrayList);
  }
  
  public static void swapSharedElementTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2)
  {
    paramObject = (TransitionSet)paramObject;
    if (paramObject != null)
    {
      ((TransitionSet)paramObject).getTargets().clear();
      ((TransitionSet)paramObject).getTargets().addAll(paramArrayList2);
      replaceTargets(paramObject, paramArrayList1, paramArrayList2);
    }
  }
  
  public static Object wrapTransitionInSet(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    TransitionSet localTransitionSet = new TransitionSet();
    localTransitionSet.addTransition((Transition)paramObject);
    return localTransitionSet;
  }
}


/* Location:              ~/android/support/v4/app/FragmentTransitionCompat21.class
 *
 * Reversed by:           J
 */