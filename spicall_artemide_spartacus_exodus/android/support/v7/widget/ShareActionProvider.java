package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.support.v4.view.ActionProvider;
import android.support.v7.a.a.a;
import android.support.v7.a.a.h;
import android.support.v7.c.a.b;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class ShareActionProvider
  extends ActionProvider
{
  private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
  public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
  final Context mContext;
  private int mMaxShownActivityCount = 4;
  private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
  private final b mOnMenuItemClickListener = new b();
  OnShareTargetSelectedListener mOnShareTargetSelectedListener;
  String mShareHistoryFileName = "share_history.xml";
  
  public ShareActionProvider(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  private void setActivityChooserPolicyIfNeeded()
  {
    if (this.mOnShareTargetSelectedListener == null) {
      return;
    }
    if (this.mOnChooseActivityListener == null) {
      this.mOnChooseActivityListener = new a();
    }
    ActivityChooserModel.a(this.mContext, this.mShareHistoryFileName).a(this.mOnChooseActivityListener);
  }
  
  public boolean hasSubMenu()
  {
    return true;
  }
  
  public View onCreateActionView()
  {
    ActivityChooserView localActivityChooserView = new ActivityChooserView(this.mContext);
    if (!localActivityChooserView.isInEditMode()) {
      localActivityChooserView.setActivityChooserModel(ActivityChooserModel.a(this.mContext, this.mShareHistoryFileName));
    }
    TypedValue localTypedValue = new TypedValue();
    this.mContext.getTheme().resolveAttribute(a.a.actionModeShareDrawable, localTypedValue, true);
    localActivityChooserView.setExpandActivityOverflowButtonDrawable(b.b(this.mContext, localTypedValue.resourceId));
    localActivityChooserView.setProvider(this);
    localActivityChooserView.setDefaultActionButtonContentDescription(a.h.abc_shareactionprovider_share_with_application);
    localActivityChooserView.setExpandActivityOverflowButtonContentDescription(a.h.abc_shareactionprovider_share_with);
    return localActivityChooserView;
  }
  
  public void onPrepareSubMenu(SubMenu paramSubMenu)
  {
    paramSubMenu.clear();
    ActivityChooserModel localActivityChooserModel = ActivityChooserModel.a(this.mContext, this.mShareHistoryFileName);
    PackageManager localPackageManager = this.mContext.getPackageManager();
    int i = localActivityChooserModel.a();
    int j = Math.min(i, this.mMaxShownActivityCount);
    ResolveInfo localResolveInfo;
    for (int k = 0; k < j; k++)
    {
      localResolveInfo = localActivityChooserModel.a(k);
      paramSubMenu.add(0, k, k, localResolveInfo.loadLabel(localPackageManager)).setIcon(localResolveInfo.loadIcon(localPackageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
    }
    if (j < i)
    {
      paramSubMenu = paramSubMenu.addSubMenu(0, j, j, this.mContext.getString(a.h.abc_activity_chooser_view_see_all));
      for (k = 0; k < i; k++)
      {
        localResolveInfo = localActivityChooserModel.a(k);
        paramSubMenu.add(0, k, k, localResolveInfo.loadLabel(localPackageManager)).setIcon(localResolveInfo.loadIcon(localPackageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
      }
    }
  }
  
  public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener paramOnShareTargetSelectedListener)
  {
    this.mOnShareTargetSelectedListener = paramOnShareTargetSelectedListener;
    setActivityChooserPolicyIfNeeded();
  }
  
  public void setShareHistoryFileName(String paramString)
  {
    this.mShareHistoryFileName = paramString;
    setActivityChooserPolicyIfNeeded();
  }
  
  public void setShareIntent(Intent paramIntent)
  {
    if (paramIntent != null)
    {
      String str = paramIntent.getAction();
      if (("android.intent.action.SEND".equals(str)) || ("android.intent.action.SEND_MULTIPLE".equals(str))) {
        updateIntent(paramIntent);
      }
    }
    ActivityChooserModel.a(this.mContext, this.mShareHistoryFileName).a(paramIntent);
  }
  
  void updateIntent(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      paramIntent.addFlags(134742016);
    } else {
      paramIntent.addFlags(524288);
    }
  }
  
  public static abstract interface OnShareTargetSelectedListener
  {
    public abstract boolean onShareTargetSelected(ShareActionProvider paramShareActionProvider, Intent paramIntent);
  }
  
  private class a
    implements ActivityChooserModel.OnChooseActivityListener
  {
    a() {}
    
    public boolean onChooseActivity(ActivityChooserModel paramActivityChooserModel, Intent paramIntent)
    {
      if (ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
        ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, paramIntent);
      }
      return false;
    }
  }
  
  private class b
    implements MenuItem.OnMenuItemClickListener
  {
    b() {}
    
    public boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      Intent localIntent = ActivityChooserModel.a(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).b(paramMenuItem.getItemId());
      if (localIntent != null)
      {
        paramMenuItem = localIntent.getAction();
        if (("android.intent.action.SEND".equals(paramMenuItem)) || ("android.intent.action.SEND_MULTIPLE".equals(paramMenuItem))) {
          ShareActionProvider.this.updateIntent(localIntent);
        }
        ShareActionProvider.this.mContext.startActivity(localIntent);
      }
      return true;
    }
  }
}


/* Location:              ~/android/support/v7/widget/ShareActionProvider.class
 *
 * Reversed by:           J
 */