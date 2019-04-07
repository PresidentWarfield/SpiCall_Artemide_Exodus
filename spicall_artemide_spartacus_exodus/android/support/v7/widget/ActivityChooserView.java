package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.a.a.d;
import android.support.v7.a.a.f;
import android.support.v7.a.a.g;
import android.support.v7.a.a.h;
import android.support.v7.a.a.j;
import android.support.v7.view.menu.s;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class ActivityChooserView
  extends ViewGroup
  implements ActivityChooserModel.ActivityChooserModelClient
{
  private static final String LOG_TAG = "ActivityChooserView";
  private final LinearLayoutCompat mActivityChooserContent;
  private final Drawable mActivityChooserContentBackground;
  final a mAdapter;
  private final b mCallbacks;
  private int mDefaultActionButtonContentDescription;
  final FrameLayout mDefaultActivityButton;
  private final ImageView mDefaultActivityButtonImage;
  final FrameLayout mExpandActivityOverflowButton;
  private final ImageView mExpandActivityOverflowButtonImage;
  int mInitialActivityCount = 4;
  private boolean mIsAttachedToWindow;
  boolean mIsSelectingDefaultActivity;
  private final int mListPopupMaxWidth;
  private ListPopupWindow mListPopupWindow;
  final DataSetObserver mModelDataSetObserver = new DataSetObserver()
  {
    public void onChanged()
    {
      super.onChanged();
      ActivityChooserView.this.mAdapter.notifyDataSetChanged();
    }
    
    public void onInvalidated()
    {
      super.onInvalidated();
      ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
    }
  };
  PopupWindow.OnDismissListener mOnDismissListener;
  private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
  {
    public void onGlobalLayout()
    {
      if (ActivityChooserView.this.isShowingPopup()) {
        if (!ActivityChooserView.this.isShown())
        {
          ActivityChooserView.this.getListPopupWindow().dismiss();
        }
        else
        {
          ActivityChooserView.this.getListPopupWindow().show();
          if (ActivityChooserView.this.mProvider != null) {
            ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
          }
        }
      }
    }
  };
  ActionProvider mProvider;
  
  public ActivityChooserView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActivityChooserView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ActivityChooserView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Object localObject = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.ActivityChooserView, paramInt, 0);
    this.mInitialActivityCount = ((TypedArray)localObject).getInt(a.j.ActivityChooserView_initialActivityCount, 4);
    paramAttributeSet = ((TypedArray)localObject).getDrawable(a.j.ActivityChooserView_expandActivityOverflowButtonDrawable);
    ((TypedArray)localObject).recycle();
    LayoutInflater.from(getContext()).inflate(a.g.abc_activity_chooser_view, this, true);
    this.mCallbacks = new b();
    this.mActivityChooserContent = ((LinearLayoutCompat)findViewById(a.f.activity_chooser_view_content));
    this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
    this.mDefaultActivityButton = ((FrameLayout)findViewById(a.f.default_activity_button));
    this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
    this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
    this.mDefaultActivityButtonImage = ((ImageView)this.mDefaultActivityButton.findViewById(a.f.image));
    localObject = (FrameLayout)findViewById(a.f.expand_activities_button);
    ((FrameLayout)localObject).setOnClickListener(this.mCallbacks);
    ((FrameLayout)localObject).setAccessibilityDelegate(new View.AccessibilityDelegate()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfo paramAnonymousAccessibilityNodeInfo)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(paramAnonymousAccessibilityNodeInfo).setCanOpenPopup(true);
      }
    });
    ((FrameLayout)localObject).setOnTouchListener(new ForwardingListener((View)localObject)
    {
      public s getPopup()
      {
        return ActivityChooserView.this.getListPopupWindow();
      }
      
      protected boolean onForwardingStarted()
      {
        ActivityChooserView.this.showPopup();
        return true;
      }
      
      protected boolean onForwardingStopped()
      {
        ActivityChooserView.this.dismissPopup();
        return true;
      }
    });
    this.mExpandActivityOverflowButton = ((FrameLayout)localObject);
    this.mExpandActivityOverflowButtonImage = ((ImageView)((FrameLayout)localObject).findViewById(a.f.image));
    this.mExpandActivityOverflowButtonImage.setImageDrawable(paramAttributeSet);
    this.mAdapter = new a();
    this.mAdapter.registerDataSetObserver(new DataSetObserver()
    {
      public void onChanged()
      {
        super.onChanged();
        ActivityChooserView.this.updateAppearance();
      }
    });
    paramContext = paramContext.getResources();
    this.mListPopupMaxWidth = Math.max(paramContext.getDisplayMetrics().widthPixels / 2, paramContext.getDimensionPixelSize(a.d.abc_config_prefDialogWidth));
  }
  
  public boolean dismissPopup()
  {
    if (isShowingPopup())
    {
      getListPopupWindow().dismiss();
      ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
      if (localViewTreeObserver.isAlive()) {
        localViewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
      }
    }
    return true;
  }
  
  public ActivityChooserModel getDataModel()
  {
    return this.mAdapter.e();
  }
  
  ListPopupWindow getListPopupWindow()
  {
    if (this.mListPopupWindow == null)
    {
      this.mListPopupWindow = new ListPopupWindow(getContext());
      this.mListPopupWindow.setAdapter(this.mAdapter);
      this.mListPopupWindow.setAnchorView(this);
      this.mListPopupWindow.setModal(true);
      this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
      this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
    }
    return this.mListPopupWindow;
  }
  
  public boolean isShowingPopup()
  {
    return getListPopupWindow().isShowing();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ActivityChooserModel localActivityChooserModel = this.mAdapter.e();
    if (localActivityChooserModel != null) {
      localActivityChooserModel.registerObserver(this.mModelDataSetObserver);
    }
    this.mIsAttachedToWindow = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Object localObject = this.mAdapter.e();
    if (localObject != null) {
      ((ActivityChooserModel)localObject).unregisterObserver(this.mModelDataSetObserver);
    }
    localObject = getViewTreeObserver();
    if (((ViewTreeObserver)localObject).isAlive()) {
      ((ViewTreeObserver)localObject).removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
    }
    if (isShowingPopup()) {
      dismissPopup();
    }
    this.mIsAttachedToWindow = false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mActivityChooserContent.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    if (!isShowingPopup()) {
      dismissPopup();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    LinearLayoutCompat localLinearLayoutCompat = this.mActivityChooserContent;
    int i = paramInt2;
    if (this.mDefaultActivityButton.getVisibility() != 0) {
      i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824);
    }
    measureChild(localLinearLayoutCompat, paramInt1, i);
    setMeasuredDimension(localLinearLayoutCompat.getMeasuredWidth(), localLinearLayoutCompat.getMeasuredHeight());
  }
  
  public void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel)
  {
    this.mAdapter.a(paramActivityChooserModel);
    if (isShowingPopup())
    {
      dismissPopup();
      showPopup();
    }
  }
  
  public void setDefaultActionButtonContentDescription(int paramInt)
  {
    this.mDefaultActionButtonContentDescription = paramInt;
  }
  
  public void setExpandActivityOverflowButtonContentDescription(int paramInt)
  {
    String str = getContext().getString(paramInt);
    this.mExpandActivityOverflowButtonImage.setContentDescription(str);
  }
  
  public void setExpandActivityOverflowButtonDrawable(Drawable paramDrawable)
  {
    this.mExpandActivityOverflowButtonImage.setImageDrawable(paramDrawable);
  }
  
  public void setInitialActivityCount(int paramInt)
  {
    this.mInitialActivityCount = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setProvider(ActionProvider paramActionProvider)
  {
    this.mProvider = paramActionProvider;
  }
  
  public boolean showPopup()
  {
    if ((!isShowingPopup()) && (this.mIsAttachedToWindow))
    {
      this.mIsSelectingDefaultActivity = false;
      showPopupUnchecked(this.mInitialActivityCount);
      return true;
    }
    return false;
  }
  
  void showPopupUnchecked(int paramInt)
  {
    if (this.mAdapter.e() != null)
    {
      getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
      int i;
      if (this.mDefaultActivityButton.getVisibility() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      int j = this.mAdapter.c();
      if ((paramInt != Integer.MAX_VALUE) && (j > paramInt + i))
      {
        this.mAdapter.a(true);
        this.mAdapter.a(paramInt - 1);
      }
      else
      {
        this.mAdapter.a(false);
        this.mAdapter.a(paramInt);
      }
      ListPopupWindow localListPopupWindow = getListPopupWindow();
      if (!localListPopupWindow.isShowing())
      {
        if ((!this.mIsSelectingDefaultActivity) && (i != 0)) {
          this.mAdapter.a(false, false);
        } else {
          this.mAdapter.a(true, i);
        }
        localListPopupWindow.setContentWidth(Math.min(this.mAdapter.a(), this.mListPopupMaxWidth));
        localListPopupWindow.show();
        ActionProvider localActionProvider = this.mProvider;
        if (localActionProvider != null) {
          localActionProvider.subUiVisibilityChanged(true);
        }
        localListPopupWindow.getListView().setContentDescription(getContext().getString(a.h.abc_activitychooserview_choose_application));
        localListPopupWindow.getListView().setSelector(new ColorDrawable(0));
      }
      return;
    }
    throw new IllegalStateException("No data model. Did you call #setDataModel?");
  }
  
  void updateAppearance()
  {
    if (this.mAdapter.getCount() > 0) {
      this.mExpandActivityOverflowButton.setEnabled(true);
    } else {
      this.mExpandActivityOverflowButton.setEnabled(false);
    }
    int i = this.mAdapter.c();
    int j = this.mAdapter.d();
    if ((i != 1) && ((i <= 1) || (j <= 0)))
    {
      this.mDefaultActivityButton.setVisibility(8);
    }
    else
    {
      this.mDefaultActivityButton.setVisibility(0);
      Object localObject = this.mAdapter.b();
      PackageManager localPackageManager = getContext().getPackageManager();
      this.mDefaultActivityButtonImage.setImageDrawable(((ResolveInfo)localObject).loadIcon(localPackageManager));
      if (this.mDefaultActionButtonContentDescription != 0)
      {
        localObject = ((ResolveInfo)localObject).loadLabel(localPackageManager);
        localObject = getContext().getString(this.mDefaultActionButtonContentDescription, new Object[] { localObject });
        this.mDefaultActivityButton.setContentDescription((CharSequence)localObject);
      }
    }
    if (this.mDefaultActivityButton.getVisibility() == 0) {
      this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
    } else {
      this.mActivityChooserContent.setBackgroundDrawable(null);
    }
  }
  
  public static class InnerLayout
    extends LinearLayoutCompat
  {
    private static final int[] TINT_ATTRS = { 16842964 };
    
    public InnerLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, TINT_ATTRS);
      setBackgroundDrawable(paramContext.getDrawable(0));
      paramContext.recycle();
    }
  }
  
  private class a
    extends BaseAdapter
  {
    private ActivityChooserModel b;
    private int c = 4;
    private boolean d;
    private boolean e;
    private boolean f;
    
    a() {}
    
    public int a()
    {
      int i = this.c;
      this.c = Integer.MAX_VALUE;
      int j = 0;
      int k = View.MeasureSpec.makeMeasureSpec(0, 0);
      int m = View.MeasureSpec.makeMeasureSpec(0, 0);
      int n = getCount();
      View localView = null;
      int i1 = 0;
      while (j < n)
      {
        localView = getView(j, localView, null);
        localView.measure(k, m);
        i1 = Math.max(i1, localView.getMeasuredWidth());
        j++;
      }
      this.c = i;
      return i1;
    }
    
    public void a(int paramInt)
    {
      if (this.c != paramInt)
      {
        this.c = paramInt;
        notifyDataSetChanged();
      }
    }
    
    public void a(ActivityChooserModel paramActivityChooserModel)
    {
      ActivityChooserModel localActivityChooserModel = ActivityChooserView.this.mAdapter.e();
      if ((localActivityChooserModel != null) && (ActivityChooserView.this.isShown())) {
        localActivityChooserModel.unregisterObserver(ActivityChooserView.this.mModelDataSetObserver);
      }
      this.b = paramActivityChooserModel;
      if ((paramActivityChooserModel != null) && (ActivityChooserView.this.isShown())) {
        paramActivityChooserModel.registerObserver(ActivityChooserView.this.mModelDataSetObserver);
      }
      notifyDataSetChanged();
    }
    
    public void a(boolean paramBoolean)
    {
      if (this.f != paramBoolean)
      {
        this.f = paramBoolean;
        notifyDataSetChanged();
      }
    }
    
    public void a(boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((this.d != paramBoolean1) || (this.e != paramBoolean2))
      {
        this.d = paramBoolean1;
        this.e = paramBoolean2;
        notifyDataSetChanged();
      }
    }
    
    public ResolveInfo b()
    {
      return this.b.b();
    }
    
    public int c()
    {
      return this.b.a();
    }
    
    public int d()
    {
      return this.b.c();
    }
    
    public ActivityChooserModel e()
    {
      return this.b;
    }
    
    public boolean f()
    {
      return this.d;
    }
    
    public int getCount()
    {
      int i = this.b.a();
      int j = i;
      if (!this.d)
      {
        j = i;
        if (this.b.b() != null) {
          j = i - 1;
        }
      }
      i = Math.min(j, this.c);
      j = i;
      if (this.f) {
        j = i + 1;
      }
      return j;
    }
    
    public Object getItem(int paramInt)
    {
      switch (getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        return null;
      }
      int i = paramInt;
      if (!this.d)
      {
        i = paramInt;
        if (this.b.b() != null) {
          i = paramInt + 1;
        }
      }
      return this.b.a(i);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public int getItemViewType(int paramInt)
    {
      if ((this.f) && (paramInt == getCount() - 1)) {
        return 1;
      }
      return 0;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView;
      switch (getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        if (paramView != null)
        {
          localView = paramView;
          if (paramView.getId() == 1) {}
        }
        else
        {
          localView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(a.g.abc_activity_chooser_view_list_item, paramViewGroup, false);
          localView.setId(1);
          ((TextView)localView.findViewById(a.f.title)).setText(ActivityChooserView.this.getContext().getString(a.h.abc_activity_chooser_view_see_all));
        }
        return localView;
      }
      if (paramView != null)
      {
        localView = paramView;
        if (paramView.getId() == a.f.list_item) {}
      }
      else
      {
        localView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(a.g.abc_activity_chooser_view_list_item, paramViewGroup, false);
      }
      PackageManager localPackageManager = ActivityChooserView.this.getContext().getPackageManager();
      paramView = (ImageView)localView.findViewById(a.f.icon);
      paramViewGroup = (ResolveInfo)getItem(paramInt);
      paramView.setImageDrawable(paramViewGroup.loadIcon(localPackageManager));
      ((TextView)localView.findViewById(a.f.title)).setText(paramViewGroup.loadLabel(localPackageManager));
      if ((this.d) && (paramInt == 0) && (this.e)) {
        localView.setActivated(true);
      } else {
        localView.setActivated(false);
      }
      return localView;
    }
    
    public int getViewTypeCount()
    {
      return 3;
    }
  }
  
  private class b
    implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener
  {
    b() {}
    
    private void a()
    {
      if (ActivityChooserView.this.mOnDismissListener != null) {
        ActivityChooserView.this.mOnDismissListener.onDismiss();
      }
    }
    
    public void onClick(View paramView)
    {
      if (paramView == ActivityChooserView.this.mDefaultActivityButton)
      {
        ActivityChooserView.this.dismissPopup();
        paramView = ActivityChooserView.this.mAdapter.b();
        int i = ActivityChooserView.this.mAdapter.e().a(paramView);
        paramView = ActivityChooserView.this.mAdapter.e().b(i);
        if (paramView != null)
        {
          paramView.addFlags(524288);
          ActivityChooserView.this.getContext().startActivity(paramView);
        }
      }
      else
      {
        if (paramView != ActivityChooserView.this.mExpandActivityOverflowButton) {
          break label115;
        }
        paramView = ActivityChooserView.this;
        paramView.mIsSelectingDefaultActivity = false;
        paramView.showPopupUnchecked(paramView.mInitialActivityCount);
      }
      return;
      label115:
      throw new IllegalArgumentException();
    }
    
    public void onDismiss()
    {
      a();
      if (ActivityChooserView.this.mProvider != null) {
        ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
      }
    }
    
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      switch (((ActivityChooserView.a)paramAdapterView.getAdapter()).getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
        break;
      case 0: 
        ActivityChooserView.this.dismissPopup();
        if (ActivityChooserView.this.mIsSelectingDefaultActivity)
        {
          if (paramInt > 0) {
            ActivityChooserView.this.mAdapter.e().c(paramInt);
          }
        }
        else
        {
          if (!ActivityChooserView.this.mAdapter.f()) {
            paramInt++;
          }
          paramAdapterView = ActivityChooserView.this.mAdapter.e().b(paramInt);
          if (paramAdapterView != null)
          {
            paramAdapterView.addFlags(524288);
            ActivityChooserView.this.getContext().startActivity(paramAdapterView);
          }
        }
        break;
      }
    }
    
    public boolean onLongClick(View paramView)
    {
      if (paramView == ActivityChooserView.this.mDefaultActivityButton)
      {
        if (ActivityChooserView.this.mAdapter.getCount() > 0)
        {
          paramView = ActivityChooserView.this;
          paramView.mIsSelectingDefaultActivity = true;
          paramView.showPopupUnchecked(paramView.mInitialActivityCount);
        }
        return true;
      }
      throw new IllegalArgumentException();
    }
  }
}


/* Location:              ~/android/support/v7/widget/ActivityChooserView.class
 *
 * Reversed by:           J
 */