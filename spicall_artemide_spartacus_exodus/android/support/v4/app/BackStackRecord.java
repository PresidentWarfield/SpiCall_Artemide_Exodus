package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.util.LogWriter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class BackStackRecord
  extends FragmentTransaction
  implements FragmentManager.BackStackEntry, FragmentManagerImpl.OpGenerator
{
  static final int OP_ADD = 1;
  static final int OP_ATTACH = 7;
  static final int OP_DETACH = 6;
  static final int OP_HIDE = 4;
  static final int OP_NULL = 0;
  static final int OP_REMOVE = 3;
  static final int OP_REPLACE = 2;
  static final int OP_SET_PRIMARY_NAV = 8;
  static final int OP_SHOW = 5;
  static final int OP_UNSET_PRIMARY_NAV = 9;
  static final boolean SUPPORTS_TRANSITIONS;
  static final String TAG = "FragmentManager";
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  ArrayList<Runnable> mCommitRunnables;
  boolean mCommitted;
  int mEnterAnim;
  int mExitAnim;
  int mIndex = -1;
  final FragmentManagerImpl mManager;
  String mName;
  ArrayList<Op> mOps = new ArrayList();
  int mPopEnterAnim;
  int mPopExitAnim;
  boolean mReorderingAllowed = false;
  ArrayList<String> mSharedElementSourceNames;
  ArrayList<String> mSharedElementTargetNames;
  int mTransition;
  int mTransitionStyle;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    SUPPORTS_TRANSITIONS = bool;
  }
  
  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl)
  {
    this.mManager = paramFragmentManagerImpl;
  }
  
  private void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    Object localObject = paramFragment.getClass();
    int i = ((Class)localObject).getModifiers();
    if ((!((Class)localObject).isAnonymousClass()) && (Modifier.isPublic(i)) && ((!((Class)localObject).isMemberClass()) || (Modifier.isStatic(i))))
    {
      paramFragment.mFragmentManager = this.mManager;
      if (paramString != null)
      {
        if ((paramFragment.mTag != null) && (!paramString.equals(paramFragment.mTag)))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Can't change tag of fragment ");
          ((StringBuilder)localObject).append(paramFragment);
          ((StringBuilder)localObject).append(": was ");
          ((StringBuilder)localObject).append(paramFragment.mTag);
          ((StringBuilder)localObject).append(" now ");
          ((StringBuilder)localObject).append(paramString);
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
        paramFragment.mTag = paramString;
      }
      if (paramInt1 != 0) {
        if (paramInt1 != -1)
        {
          if ((paramFragment.mFragmentId != 0) && (paramFragment.mFragmentId != paramInt1))
          {
            paramString = new StringBuilder();
            paramString.append("Can't change container ID of fragment ");
            paramString.append(paramFragment);
            paramString.append(": was ");
            paramString.append(paramFragment.mFragmentId);
            paramString.append(" now ");
            paramString.append(paramInt1);
            throw new IllegalStateException(paramString.toString());
          }
          paramFragment.mFragmentId = paramInt1;
          paramFragment.mContainerId = paramInt1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Can't add fragment ");
          ((StringBuilder)localObject).append(paramFragment);
          ((StringBuilder)localObject).append(" with tag ");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(" to container view with no id");
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      addOp(new Op(paramInt2, paramFragment));
      return;
    }
    paramFragment = new StringBuilder();
    paramFragment.append("Fragment ");
    paramFragment.append(((Class)localObject).getCanonicalName());
    paramFragment.append(" must be a public static class to be  properly recreated from");
    paramFragment.append(" instance state.");
    throw new IllegalStateException(paramFragment.toString());
  }
  
  private static boolean isFragmentPostponed(Op paramOp)
  {
    paramOp = paramOp.fragment;
    boolean bool;
    if ((paramOp != null) && (paramOp.mAdded) && (paramOp.mView != null) && (!paramOp.mDetached) && (!paramOp.mHidden) && (paramOp.isPostponed())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }
  
  public FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }
  
  void addOp(Op paramOp)
  {
    this.mOps.add(paramOp);
    paramOp.enterAnim = this.mEnterAnim;
    paramOp.exitAnim = this.mExitAnim;
    paramOp.popEnterAnim = this.mPopEnterAnim;
    paramOp.popExitAnim = this.mPopExitAnim;
  }
  
  public FragmentTransaction addSharedElement(View paramView, String paramString)
  {
    if (SUPPORTS_TRANSITIONS)
    {
      paramView = ViewCompat.getTransitionName(paramView);
      if (paramView != null)
      {
        if (this.mSharedElementSourceNames == null)
        {
          this.mSharedElementSourceNames = new ArrayList();
          this.mSharedElementTargetNames = new ArrayList();
        }
        else
        {
          if (this.mSharedElementTargetNames.contains(paramString)) {
            break label132;
          }
          if (this.mSharedElementSourceNames.contains(paramView)) {
            break label90;
          }
        }
        this.mSharedElementSourceNames.add(paramView);
        this.mSharedElementTargetNames.add(paramString);
        return this;
        label90:
        paramString = new StringBuilder();
        paramString.append("A shared element with the source name '");
        paramString.append(paramView);
        paramString.append(" has already been added to the transaction.");
        throw new IllegalArgumentException(paramString.toString());
        label132:
        paramView = new StringBuilder();
        paramView.append("A shared element with the target name '");
        paramView.append(paramString);
        paramView.append("' has already been added to the transaction.");
        throw new IllegalArgumentException(paramView.toString());
      }
      else
      {
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
      }
    }
    return this;
  }
  
  public FragmentTransaction addToBackStack(String paramString)
  {
    if (this.mAllowAddToBackStack)
    {
      this.mAddToBackStack = true;
      this.mName = paramString;
      return this;
    }
    throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
  }
  
  public FragmentTransaction attach(Fragment paramFragment)
  {
    addOp(new Op(7, paramFragment));
    return this;
  }
  
  void bumpBackStackNesting(int paramInt)
  {
    if (!this.mAddToBackStack) {
      return;
    }
    Object localObject1;
    if (FragmentManagerImpl.DEBUG)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Bump nesting in ");
      ((StringBuilder)localObject1).append(this);
      ((StringBuilder)localObject1).append(" by ");
      ((StringBuilder)localObject1).append(paramInt);
      Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
    }
    int i = this.mOps.size();
    for (int j = 0; j < i; j++)
    {
      localObject1 = (Op)this.mOps.get(j);
      if (((Op)localObject1).fragment != null)
      {
        Object localObject2 = ((Op)localObject1).fragment;
        ((Fragment)localObject2).mBackStackNesting += paramInt;
        if (FragmentManagerImpl.DEBUG)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Bump nesting of ");
          ((StringBuilder)localObject2).append(((Op)localObject1).fragment);
          ((StringBuilder)localObject2).append(" to ");
          ((StringBuilder)localObject2).append(((Op)localObject1).fragment.mBackStackNesting);
          Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
        }
      }
    }
  }
  
  public int commit()
  {
    return commitInternal(false);
  }
  
  public int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }
  
  int commitInternal(boolean paramBoolean)
  {
    if (!this.mCommitted)
    {
      if (FragmentManagerImpl.DEBUG)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Commit: ");
        ((StringBuilder)localObject).append(this);
        Log.v("FragmentManager", ((StringBuilder)localObject).toString());
        localObject = new PrintWriter(new LogWriter("FragmentManager"));
        dump("  ", null, (PrintWriter)localObject, null);
        ((PrintWriter)localObject).close();
      }
      this.mCommitted = true;
      if (this.mAddToBackStack) {
        this.mIndex = this.mManager.allocBackStackIndex(this);
      } else {
        this.mIndex = -1;
      }
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
    throw new IllegalStateException("commit already called");
  }
  
  public void commitNow()
  {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, false);
  }
  
  public void commitNowAllowingStateLoss()
  {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, true);
  }
  
  public FragmentTransaction detach(Fragment paramFragment)
  {
    addOp(new Op(6, paramFragment));
    return this;
  }
  
  public FragmentTransaction disallowAddToBackStack()
  {
    if (!this.mAddToBackStack)
    {
      this.mAllowAddToBackStack = false;
      return this;
    }
    throw new IllegalStateException("This transaction is already being added to the back stack");
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    dump(paramString, paramPrintWriter, true);
  }
  
  public void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        paramPrintWriter.print(Integer.toHexString(this.mTransition));
        paramPrintWriter.print(" mTransitionStyle=#");
        paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
      }
      if ((this.mEnterAnim != 0) || (this.mExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mEnterAnim));
        paramPrintWriter.print(" mExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mExitAnim));
      }
      if ((this.mPopEnterAnim != 0) || (this.mPopExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mPopEnterAnim));
        paramPrintWriter.print(" mPopExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mPopExitAnim));
      }
      if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      }
      if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      }
    }
    if (!this.mOps.isEmpty())
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("    ");
      ((StringBuilder)localObject).toString();
      int i = this.mOps.size();
      for (int j = 0; j < i; j++)
      {
        Op localOp = (Op)this.mOps.get(j);
        switch (localOp.cmd)
        {
        default: 
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("cmd=");
          ((StringBuilder)localObject).append(localOp.cmd);
          localObject = ((StringBuilder)localObject).toString();
          break;
        case 9: 
          localObject = "UNSET_PRIMARY_NAV";
          break;
        case 8: 
          localObject = "SET_PRIMARY_NAV";
          break;
        case 7: 
          localObject = "ATTACH";
          break;
        case 6: 
          localObject = "DETACH";
          break;
        case 5: 
          localObject = "SHOW";
          break;
        case 4: 
          localObject = "HIDE";
          break;
        case 3: 
          localObject = "REMOVE";
          break;
        case 2: 
          localObject = "REPLACE";
          break;
        case 1: 
          localObject = "ADD";
          break;
        case 0: 
          localObject = "NULL";
        }
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  Op #");
        paramPrintWriter.print(j);
        paramPrintWriter.print(": ");
        paramPrintWriter.print((String)localObject);
        paramPrintWriter.print(" ");
        paramPrintWriter.println(localOp.fragment);
        if (paramBoolean)
        {
          if ((localOp.enterAnim != 0) || (localOp.exitAnim != 0))
          {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("enterAnim=#");
            paramPrintWriter.print(Integer.toHexString(localOp.enterAnim));
            paramPrintWriter.print(" exitAnim=#");
            paramPrintWriter.println(Integer.toHexString(localOp.exitAnim));
          }
          if ((localOp.popEnterAnim != 0) || (localOp.popExitAnim != 0))
          {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("popEnterAnim=#");
            paramPrintWriter.print(Integer.toHexString(localOp.popEnterAnim));
            paramPrintWriter.print(" popExitAnim=#");
            paramPrintWriter.println(Integer.toHexString(localOp.popExitAnim));
          }
        }
      }
    }
  }
  
  void executeOps()
  {
    int i = this.mOps.size();
    Object localObject1;
    for (int j = 0; j < i; j++)
    {
      localObject1 = (Op)this.mOps.get(j);
      Object localObject2 = ((Op)localObject1).fragment;
      if (localObject2 != null) {
        ((Fragment)localObject2).setNextTransition(this.mTransition, this.mTransitionStyle);
      }
      int k = ((Op)localObject1).cmd;
      if (k != 1)
      {
        switch (k)
        {
        default: 
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Unknown cmd: ");
          ((StringBuilder)localObject2).append(((Op)localObject1).cmd);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        case 9: 
          this.mManager.setPrimaryNavigationFragment(null);
          break;
        case 8: 
          this.mManager.setPrimaryNavigationFragment((Fragment)localObject2);
          break;
        case 7: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).enterAnim);
          this.mManager.attachFragment((Fragment)localObject2);
          break;
        case 6: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).exitAnim);
          this.mManager.detachFragment((Fragment)localObject2);
          break;
        case 5: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).enterAnim);
          this.mManager.showFragment((Fragment)localObject2);
          break;
        case 4: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).exitAnim);
          this.mManager.hideFragment((Fragment)localObject2);
          break;
        case 3: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).exitAnim);
          this.mManager.removeFragment((Fragment)localObject2);
          break;
        }
      }
      else
      {
        ((Fragment)localObject2).setNextAnim(((Op)localObject1).enterAnim);
        this.mManager.addFragment((Fragment)localObject2, false);
      }
      if ((!this.mReorderingAllowed) && (((Op)localObject1).cmd != 1) && (localObject2 != null)) {
        this.mManager.moveFragmentToExpectedState((Fragment)localObject2);
      }
    }
    if (!this.mReorderingAllowed)
    {
      localObject1 = this.mManager;
      ((FragmentManagerImpl)localObject1).moveToState(((FragmentManagerImpl)localObject1).mCurState, true);
    }
  }
  
  void executePopOps(boolean paramBoolean)
  {
    Object localObject1;
    for (int i = this.mOps.size() - 1; i >= 0; i--)
    {
      localObject1 = (Op)this.mOps.get(i);
      Object localObject2 = ((Op)localObject1).fragment;
      if (localObject2 != null) {
        ((Fragment)localObject2).setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
      }
      int j = ((Op)localObject1).cmd;
      if (j != 1)
      {
        switch (j)
        {
        default: 
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Unknown cmd: ");
          ((StringBuilder)localObject2).append(((Op)localObject1).cmd);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        case 9: 
          this.mManager.setPrimaryNavigationFragment((Fragment)localObject2);
          break;
        case 8: 
          this.mManager.setPrimaryNavigationFragment(null);
          break;
        case 7: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).popExitAnim);
          this.mManager.detachFragment((Fragment)localObject2);
          break;
        case 6: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).popEnterAnim);
          this.mManager.attachFragment((Fragment)localObject2);
          break;
        case 5: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).popExitAnim);
          this.mManager.hideFragment((Fragment)localObject2);
          break;
        case 4: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).popEnterAnim);
          this.mManager.showFragment((Fragment)localObject2);
          break;
        case 3: 
          ((Fragment)localObject2).setNextAnim(((Op)localObject1).popEnterAnim);
          this.mManager.addFragment((Fragment)localObject2, false);
          break;
        }
      }
      else
      {
        ((Fragment)localObject2).setNextAnim(((Op)localObject1).popExitAnim);
        this.mManager.removeFragment((Fragment)localObject2);
      }
      if ((!this.mReorderingAllowed) && (((Op)localObject1).cmd != 3) && (localObject2 != null)) {
        this.mManager.moveFragmentToExpectedState((Fragment)localObject2);
      }
    }
    if ((!this.mReorderingAllowed) && (paramBoolean))
    {
      localObject1 = this.mManager;
      ((FragmentManagerImpl)localObject1).moveToState(((FragmentManagerImpl)localObject1).mCurState, true);
    }
  }
  
  Fragment expandOps(ArrayList<Fragment> paramArrayList, Fragment paramFragment)
  {
    int i = 0;
    for (Fragment localFragment1 = paramFragment; i < this.mOps.size(); localFragment1 = paramFragment)
    {
      Op localOp = (Op)this.mOps.get(i);
      int j;
      switch (localOp.cmd)
      {
      case 4: 
      case 5: 
      default: 
        paramFragment = localFragment1;
        j = i;
        break;
      case 8: 
        this.mOps.add(i, new Op(9, localFragment1));
        j = i + 1;
        paramFragment = localOp.fragment;
        break;
      case 3: 
      case 6: 
        paramArrayList.remove(localOp.fragment);
        paramFragment = localFragment1;
        j = i;
        if (localOp.fragment == localFragment1)
        {
          this.mOps.add(i, new Op(9, localOp.fragment));
          j = i + 1;
          paramFragment = null;
        }
        break;
      case 2: 
        Fragment localFragment2 = localOp.fragment;
        int k = localFragment2.mContainerId;
        j = paramArrayList.size() - 1;
        paramFragment = localFragment1;
        int i1;
        for (int m = 0; j >= 0; m = i1)
        {
          Fragment localFragment3 = (Fragment)paramArrayList.get(j);
          int n = i;
          localFragment1 = paramFragment;
          i1 = m;
          if (localFragment3.mContainerId == k) {
            if (localFragment3 == localFragment2)
            {
              i1 = 1;
              n = i;
              localFragment1 = paramFragment;
            }
            else
            {
              i1 = i;
              localFragment1 = paramFragment;
              if (localFragment3 == paramFragment)
              {
                this.mOps.add(i, new Op(9, localFragment3));
                i1 = i + 1;
                localFragment1 = null;
              }
              paramFragment = new Op(3, localFragment3);
              paramFragment.enterAnim = localOp.enterAnim;
              paramFragment.popEnterAnim = localOp.popEnterAnim;
              paramFragment.exitAnim = localOp.exitAnim;
              paramFragment.popExitAnim = localOp.popExitAnim;
              this.mOps.add(i1, paramFragment);
              paramArrayList.remove(localFragment3);
              n = i1 + 1;
              i1 = m;
            }
          }
          j--;
          i = n;
          paramFragment = localFragment1;
        }
        if (m != 0)
        {
          this.mOps.remove(i);
          j = i - 1;
        }
        else
        {
          localOp.cmd = 1;
          paramArrayList.add(localFragment2);
          j = i;
        }
        break;
      case 1: 
      case 7: 
        paramArrayList.add(localOp.fragment);
        j = i;
        paramFragment = localFragment1;
      }
      i = j + 1;
    }
    return localFragment1;
  }
  
  public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    if (FragmentManagerImpl.DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Run: ");
      localStringBuilder.append(this);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    paramArrayList.add(this);
    paramArrayList1.add(Boolean.valueOf(false));
    if (this.mAddToBackStack) {
      this.mManager.addBackStackState(this);
    }
    return true;
  }
  
  public CharSequence getBreadCrumbShortTitle()
  {
    if (this.mBreadCrumbShortTitleRes != 0) {
      return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
    }
    return this.mBreadCrumbShortTitleText;
  }
  
  public int getBreadCrumbShortTitleRes()
  {
    return this.mBreadCrumbShortTitleRes;
  }
  
  public CharSequence getBreadCrumbTitle()
  {
    if (this.mBreadCrumbTitleRes != 0) {
      return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
    }
    return this.mBreadCrumbTitleText;
  }
  
  public int getBreadCrumbTitleRes()
  {
    return this.mBreadCrumbTitleRes;
  }
  
  public int getId()
  {
    return this.mIndex;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public int getTransition()
  {
    return this.mTransition;
  }
  
  public int getTransitionStyle()
  {
    return this.mTransitionStyle;
  }
  
  public FragmentTransaction hide(Fragment paramFragment)
  {
    addOp(new Op(4, paramFragment));
    return this;
  }
  
  boolean interactsWith(int paramInt)
  {
    int i = this.mOps.size();
    for (int j = 0; j < i; j++)
    {
      Op localOp = (Op)this.mOps.get(j);
      int k;
      if (localOp.fragment != null) {
        k = localOp.fragment.mContainerId;
      } else {
        k = 0;
      }
      if ((k != 0) && (k == paramInt)) {
        return true;
      }
    }
    return false;
  }
  
  boolean interactsWith(ArrayList<BackStackRecord> paramArrayList, int paramInt1, int paramInt2)
  {
    if (paramInt2 == paramInt1) {
      return false;
    }
    int i = this.mOps.size();
    int j = 0;
    int n;
    for (int k = -1; j < i; k = n)
    {
      Object localObject = (Op)this.mOps.get(j);
      int m;
      if (((Op)localObject).fragment != null) {
        m = ((Op)localObject).fragment.mContainerId;
      } else {
        m = 0;
      }
      n = k;
      if (m != 0)
      {
        n = k;
        if (m != k)
        {
          for (k = paramInt1; k < paramInt2; k++)
          {
            localObject = (BackStackRecord)paramArrayList.get(k);
            int i1 = ((BackStackRecord)localObject).mOps.size();
            for (n = 0; n < i1; n++)
            {
              Op localOp = (Op)((BackStackRecord)localObject).mOps.get(n);
              int i2;
              if (localOp.fragment != null) {
                i2 = localOp.fragment.mContainerId;
              } else {
                i2 = 0;
              }
              if (i2 == m) {
                return true;
              }
            }
          }
          n = m;
        }
      }
      j++;
    }
    return false;
  }
  
  public boolean isAddToBackStackAllowed()
  {
    return this.mAllowAddToBackStack;
  }
  
  public boolean isEmpty()
  {
    return this.mOps.isEmpty();
  }
  
  boolean isPostponed()
  {
    for (int i = 0; i < this.mOps.size(); i++) {
      if (isFragmentPostponed((Op)this.mOps.get(i))) {
        return true;
      }
    }
    return false;
  }
  
  public FragmentTransaction remove(Fragment paramFragment)
  {
    addOp(new Op(3, paramFragment));
    return this;
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment)
  {
    return replace(paramInt, paramFragment, null);
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt != 0)
    {
      doAddOp(paramInt, paramFragment, paramString, 2);
      return this;
    }
    throw new IllegalArgumentException("Must use non-zero containerViewId");
  }
  
  public FragmentTransaction runOnCommit(Runnable paramRunnable)
  {
    if (paramRunnable != null)
    {
      disallowAddToBackStack();
      if (this.mCommitRunnables == null) {
        this.mCommitRunnables = new ArrayList();
      }
      this.mCommitRunnables.add(paramRunnable);
      return this;
    }
    throw new IllegalArgumentException("runnable cannot be null");
  }
  
  public void runOnCommitRunnables()
  {
    ArrayList localArrayList = this.mCommitRunnables;
    if (localArrayList != null)
    {
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        ((Runnable)this.mCommitRunnables.get(i)).run();
        i++;
      }
      this.mCommitRunnables = null;
    }
  }
  
  public FragmentTransaction setAllowOptimization(boolean paramBoolean)
  {
    return setReorderingAllowed(paramBoolean);
  }
  
  public FragmentTransaction setBreadCrumbShortTitle(int paramInt)
  {
    this.mBreadCrumbShortTitleRes = paramInt;
    this.mBreadCrumbShortTitleText = null;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbShortTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbShortTitleRes = 0;
    this.mBreadCrumbShortTitleText = paramCharSequence;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbTitle(int paramInt)
  {
    this.mBreadCrumbTitleRes = paramInt;
    this.mBreadCrumbTitleText = null;
    return this;
  }
  
  public FragmentTransaction setBreadCrumbTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbTitleRes = 0;
    this.mBreadCrumbTitleText = paramCharSequence;
    return this;
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2)
  {
    return setCustomAnimations(paramInt1, paramInt2, 0, 0);
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = paramInt3;
    this.mPopExitAnim = paramInt4;
    return this;
  }
  
  void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener paramOnStartEnterTransitionListener)
  {
    for (int i = 0; i < this.mOps.size(); i++)
    {
      Op localOp = (Op)this.mOps.get(i);
      if (isFragmentPostponed(localOp)) {
        localOp.fragment.setOnStartEnterTransitionListener(paramOnStartEnterTransitionListener);
      }
    }
  }
  
  public FragmentTransaction setPrimaryNavigationFragment(Fragment paramFragment)
  {
    addOp(new Op(8, paramFragment));
    return this;
  }
  
  public FragmentTransaction setReorderingAllowed(boolean paramBoolean)
  {
    this.mReorderingAllowed = paramBoolean;
    return this;
  }
  
  public FragmentTransaction setTransition(int paramInt)
  {
    this.mTransition = paramInt;
    return this;
  }
  
  public FragmentTransaction setTransitionStyle(int paramInt)
  {
    this.mTransitionStyle = paramInt;
    return this;
  }
  
  public FragmentTransaction show(Fragment paramFragment)
  {
    addOp(new Op(5, paramFragment));
    return this;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("BackStackEntry{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    if (this.mIndex >= 0)
    {
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mIndex);
    }
    if (this.mName != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mName);
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  Fragment trackAddedFragmentsInPop(ArrayList<Fragment> paramArrayList, Fragment paramFragment)
  {
    for (int i = 0; i < this.mOps.size(); i++)
    {
      Op localOp = (Op)this.mOps.get(i);
      int j = localOp.cmd;
      if (j != 1)
      {
        if (j != 3) {}
        switch (j)
        {
        default: 
          break;
        case 9: 
          paramFragment = localOp.fragment;
          break;
        case 8: 
          paramFragment = null;
          break;
        case 6: 
          paramArrayList.add(localOp.fragment);
          break;
        }
      }
      else
      {
        paramArrayList.remove(localOp.fragment);
      }
    }
    return paramFragment;
  }
  
  static final class Op
  {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    int popEnterAnim;
    int popExitAnim;
    
    Op() {}
    
    Op(int paramInt, Fragment paramFragment)
    {
      this.cmd = paramInt;
      this.fragment = paramFragment;
    }
  }
}


/* Location:              ~/android/support/v4/app/BackStackRecord.class
 *
 * Reversed by:           J
 */