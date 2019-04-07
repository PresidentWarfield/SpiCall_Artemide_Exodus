package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

final class FragmentState
  implements Parcelable
{
  public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator()
  {
    public FragmentState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FragmentState(paramAnonymousParcel);
    }
    
    public FragmentState[] newArray(int paramAnonymousInt)
    {
      return new FragmentState[paramAnonymousInt];
    }
  };
  final Bundle mArguments;
  final String mClassName;
  final int mContainerId;
  final boolean mDetached;
  final int mFragmentId;
  final boolean mFromLayout;
  final boolean mHidden;
  final int mIndex;
  Fragment mInstance;
  final boolean mRetainInstance;
  Bundle mSavedFragmentState;
  final String mTag;
  
  public FragmentState(Parcel paramParcel)
  {
    this.mClassName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    int i = paramParcel.readInt();
    boolean bool1 = true;
    boolean bool2;
    if (i != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mFromLayout = bool2;
    this.mFragmentId = paramParcel.readInt();
    this.mContainerId = paramParcel.readInt();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mRetainInstance = bool2;
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mDetached = bool2;
    this.mArguments = paramParcel.readBundle();
    if (paramParcel.readInt() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    this.mHidden = bool2;
    this.mSavedFragmentState = paramParcel.readBundle();
  }
  
  public FragmentState(Fragment paramFragment)
  {
    this.mClassName = paramFragment.getClass().getName();
    this.mIndex = paramFragment.mIndex;
    this.mFromLayout = paramFragment.mFromLayout;
    this.mFragmentId = paramFragment.mFragmentId;
    this.mContainerId = paramFragment.mContainerId;
    this.mTag = paramFragment.mTag;
    this.mRetainInstance = paramFragment.mRetainInstance;
    this.mDetached = paramFragment.mDetached;
    this.mArguments = paramFragment.mArguments;
    this.mHidden = paramFragment.mHidden;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Fragment instantiate(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment, FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    if (this.mInstance == null)
    {
      Context localContext = paramFragmentHostCallback.getContext();
      Bundle localBundle = this.mArguments;
      if (localBundle != null) {
        localBundle.setClassLoader(localContext.getClassLoader());
      }
      if (paramFragmentContainer != null) {
        this.mInstance = paramFragmentContainer.instantiate(localContext, this.mClassName, this.mArguments);
      } else {
        this.mInstance = Fragment.instantiate(localContext, this.mClassName, this.mArguments);
      }
      paramFragmentContainer = this.mSavedFragmentState;
      if (paramFragmentContainer != null)
      {
        paramFragmentContainer.setClassLoader(localContext.getClassLoader());
        this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
      }
      this.mInstance.setIndex(this.mIndex, paramFragment);
      paramFragmentContainer = this.mInstance;
      paramFragmentContainer.mFromLayout = this.mFromLayout;
      paramFragmentContainer.mRestored = true;
      paramFragmentContainer.mFragmentId = this.mFragmentId;
      paramFragmentContainer.mContainerId = this.mContainerId;
      paramFragmentContainer.mTag = this.mTag;
      paramFragmentContainer.mRetainInstance = this.mRetainInstance;
      paramFragmentContainer.mDetached = this.mDetached;
      paramFragmentContainer.mHidden = this.mHidden;
      paramFragmentContainer.mFragmentManager = paramFragmentHostCallback.mFragmentManager;
      if (FragmentManagerImpl.DEBUG)
      {
        paramFragmentHostCallback = new StringBuilder();
        paramFragmentHostCallback.append("Instantiated fragment ");
        paramFragmentHostCallback.append(this.mInstance);
        Log.v("FragmentManager", paramFragmentHostCallback.toString());
      }
    }
    paramFragmentHostCallback = this.mInstance;
    paramFragmentHostCallback.mChildNonConfig = paramFragmentManagerNonConfig;
    return paramFragmentHostCallback;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mClassName);
    paramParcel.writeInt(this.mIndex);
    paramParcel.writeInt(this.mFromLayout);
    paramParcel.writeInt(this.mFragmentId);
    paramParcel.writeInt(this.mContainerId);
    paramParcel.writeString(this.mTag);
    paramParcel.writeInt(this.mRetainInstance);
    paramParcel.writeInt(this.mDetached);
    paramParcel.writeBundle(this.mArguments);
    paramParcel.writeInt(this.mHidden);
    paramParcel.writeBundle(this.mSavedFragmentState);
  }
}


/* Location:              ~/android/support/v4/app/FragmentState.class
 *
 * Reversed by:           J
 */