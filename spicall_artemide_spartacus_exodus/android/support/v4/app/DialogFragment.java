package android.support.v4.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DialogFragment
  extends Fragment
  implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener
{
  private static final String SAVED_BACK_STACK_ID = "android:backStackId";
  private static final String SAVED_CANCELABLE = "android:cancelable";
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
  private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
  private static final String SAVED_STYLE = "android:style";
  private static final String SAVED_THEME = "android:theme";
  public static final int STYLE_NORMAL = 0;
  public static final int STYLE_NO_FRAME = 2;
  public static final int STYLE_NO_INPUT = 3;
  public static final int STYLE_NO_TITLE = 1;
  int mBackStackId = -1;
  boolean mCancelable = true;
  Dialog mDialog;
  boolean mDismissed;
  boolean mShownByMe;
  boolean mShowsDialog = true;
  int mStyle = 0;
  int mTheme = 0;
  boolean mViewDestroyed;
  
  public void dismiss()
  {
    dismissInternal(false);
  }
  
  public void dismissAllowingStateLoss()
  {
    dismissInternal(true);
  }
  
  void dismissInternal(boolean paramBoolean)
  {
    if (this.mDismissed) {
      return;
    }
    this.mDismissed = true;
    this.mShownByMe = false;
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      ((Dialog)localObject).dismiss();
      this.mDialog = null;
    }
    this.mViewDestroyed = true;
    if (this.mBackStackId >= 0)
    {
      getFragmentManager().popBackStack(this.mBackStackId, 1);
      this.mBackStackId = -1;
    }
    else
    {
      localObject = getFragmentManager().beginTransaction();
      ((FragmentTransaction)localObject).remove(this);
      if (paramBoolean) {
        ((FragmentTransaction)localObject).commitAllowingStateLoss();
      } else {
        ((FragmentTransaction)localObject).commit();
      }
    }
  }
  
  public Dialog getDialog()
  {
    return this.mDialog;
  }
  
  public boolean getShowsDialog()
  {
    return this.mShowsDialog;
  }
  
  public int getTheme()
  {
    return this.mTheme;
  }
  
  public boolean isCancelable()
  {
    return this.mCancelable;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!this.mShowsDialog) {
      return;
    }
    Object localObject = getView();
    if (localObject != null) {
      if (((View)localObject).getParent() == null) {
        this.mDialog.setContentView((View)localObject);
      } else {
        throw new IllegalStateException("DialogFragment can not be attached to a container view");
      }
    }
    localObject = getActivity();
    if (localObject != null) {
      this.mDialog.setOwnerActivity((Activity)localObject);
    }
    this.mDialog.setCancelable(this.mCancelable);
    this.mDialog.setOnCancelListener(this);
    this.mDialog.setOnDismissListener(this);
    if (paramBundle != null)
    {
      paramBundle = paramBundle.getBundle("android:savedDialogState");
      if (paramBundle != null) {
        this.mDialog.onRestoreInstanceState(paramBundle);
      }
    }
  }
  
  public void onAttach(Context paramContext)
  {
    super.onAttach(paramContext);
    if (!this.mShownByMe) {
      this.mDismissed = false;
    }
  }
  
  public void onCancel(DialogInterface paramDialogInterface) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    boolean bool;
    if (this.mContainerId == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mShowsDialog = bool;
    if (paramBundle != null)
    {
      this.mStyle = paramBundle.getInt("android:style", 0);
      this.mTheme = paramBundle.getInt("android:theme", 0);
      this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
      this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
      this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new Dialog(getActivity(), getTheme());
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    Dialog localDialog = this.mDialog;
    if (localDialog != null)
    {
      this.mViewDestroyed = true;
      localDialog.dismiss();
      this.mDialog = null;
    }
  }
  
  public void onDetach()
  {
    super.onDetach();
    if ((!this.mShownByMe) && (!this.mDismissed)) {
      this.mDismissed = true;
    }
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (!this.mViewDestroyed) {
      dismissInternal(true);
    }
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle)
  {
    if (!this.mShowsDialog) {
      return super.onGetLayoutInflater(paramBundle);
    }
    this.mDialog = onCreateDialog(paramBundle);
    paramBundle = this.mDialog;
    if (paramBundle != null)
    {
      setupDialog(paramBundle, this.mStyle);
      return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
    }
    return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      localObject = ((Dialog)localObject).onSaveInstanceState();
      if (localObject != null) {
        paramBundle.putBundle("android:savedDialogState", (Bundle)localObject);
      }
    }
    int i = this.mStyle;
    if (i != 0) {
      paramBundle.putInt("android:style", i);
    }
    i = this.mTheme;
    if (i != 0) {
      paramBundle.putInt("android:theme", i);
    }
    boolean bool = this.mCancelable;
    if (!bool) {
      paramBundle.putBoolean("android:cancelable", bool);
    }
    bool = this.mShowsDialog;
    if (!bool) {
      paramBundle.putBoolean("android:showsDialog", bool);
    }
    i = this.mBackStackId;
    if (i != -1) {
      paramBundle.putInt("android:backStackId", i);
    }
  }
  
  public void onStart()
  {
    super.onStart();
    Dialog localDialog = this.mDialog;
    if (localDialog != null)
    {
      this.mViewDestroyed = false;
      localDialog.show();
    }
  }
  
  public void onStop()
  {
    super.onStop();
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.hide();
    }
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    this.mCancelable = paramBoolean;
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.setCancelable(paramBoolean);
    }
  }
  
  public void setShowsDialog(boolean paramBoolean)
  {
    this.mShowsDialog = paramBoolean;
  }
  
  public void setStyle(int paramInt1, int paramInt2)
  {
    this.mStyle = paramInt1;
    paramInt1 = this.mStyle;
    if ((paramInt1 == 2) || (paramInt1 == 3)) {
      this.mTheme = 16973913;
    }
    if (paramInt2 != 0) {
      this.mTheme = paramInt2;
    }
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 3: 
      paramDialog.getWindow().addFlags(24);
    case 1: 
    case 2: 
      paramDialog.requestWindowFeature(1);
    }
  }
  
  public int show(FragmentTransaction paramFragmentTransaction, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentTransaction.add(this, paramString);
    this.mViewDestroyed = false;
    this.mBackStackId = paramFragmentTransaction.commit();
    return this.mBackStackId;
  }
  
  public void show(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentManager = paramFragmentManager.beginTransaction();
    paramFragmentManager.add(this, paramString);
    paramFragmentManager.commit();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface DialogStyle {}
}


/* Location:              ~/android/support/v4/app/DialogFragment.class
 *
 * Reversed by:           J
 */