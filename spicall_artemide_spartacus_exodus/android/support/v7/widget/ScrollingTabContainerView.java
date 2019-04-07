package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.a.a.a;
import android.support.v7.app.a.c;
import android.support.v7.view.a;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ScrollingTabContainerView
  extends HorizontalScrollView
  implements AdapterView.OnItemSelectedListener
{
  private static final int FADE_DURATION = 200;
  private static final String TAG = "ScrollingTabContainerView";
  private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
  private boolean mAllowCollapse;
  private int mContentHeight;
  int mMaxTabWidth;
  private int mSelectedTabIndex;
  int mStackedTabMaxWidth;
  private b mTabClickListener;
  LinearLayoutCompat mTabLayout;
  Runnable mTabSelector;
  private Spinner mTabSpinner;
  protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
  protected ViewPropertyAnimator mVisibilityAnim;
  
  public ScrollingTabContainerView(Context paramContext)
  {
    super(paramContext);
    setHorizontalScrollBarEnabled(false);
    paramContext = a.a(paramContext);
    setContentHeight(paramContext.e());
    this.mStackedTabMaxWidth = paramContext.g();
    this.mTabLayout = createTabLayout();
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
  }
  
  private Spinner createSpinner()
  {
    AppCompatSpinner localAppCompatSpinner = new AppCompatSpinner(getContext(), null, a.a.actionDropDownStyle);
    localAppCompatSpinner.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
    localAppCompatSpinner.setOnItemSelectedListener(this);
    return localAppCompatSpinner;
  }
  
  private LinearLayoutCompat createTabLayout()
  {
    LinearLayoutCompat localLinearLayoutCompat = new LinearLayoutCompat(getContext(), null, a.a.actionBarTabBarStyle);
    localLinearLayoutCompat.setMeasureWithLargestChildEnabled(true);
    localLinearLayoutCompat.setGravity(17);
    localLinearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
    return localLinearLayoutCompat;
  }
  
  private boolean isCollapsed()
  {
    Spinner localSpinner = this.mTabSpinner;
    boolean bool;
    if ((localSpinner != null) && (localSpinner.getParent() == this)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void performCollapse()
  {
    if (isCollapsed()) {
      return;
    }
    if (this.mTabSpinner == null) {
      this.mTabSpinner = createSpinner();
    }
    removeView(this.mTabLayout);
    addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
    if (this.mTabSpinner.getAdapter() == null) {
      this.mTabSpinner.setAdapter(new a());
    }
    Runnable localRunnable = this.mTabSelector;
    if (localRunnable != null)
    {
      removeCallbacks(localRunnable);
      this.mTabSelector = null;
    }
    this.mTabSpinner.setSelection(this.mSelectedTabIndex);
  }
  
  private boolean performExpand()
  {
    if (!isCollapsed()) {
      return false;
    }
    removeView(this.mTabSpinner);
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    setTabSelected(this.mTabSpinner.getSelectedItemPosition());
    return false;
  }
  
  public void addTab(a.c paramc, int paramInt, boolean paramBoolean)
  {
    paramc = createTabView(paramc, false);
    this.mTabLayout.addView(paramc, paramInt, new LinearLayoutCompat.LayoutParams(0, -1, 1.0F));
    Spinner localSpinner = this.mTabSpinner;
    if (localSpinner != null) {
      ((a)localSpinner.getAdapter()).notifyDataSetChanged();
    }
    if (paramBoolean) {
      paramc.setSelected(true);
    }
    if (this.mAllowCollapse) {
      requestLayout();
    }
  }
  
  public void addTab(a.c paramc, boolean paramBoolean)
  {
    paramc = createTabView(paramc, false);
    this.mTabLayout.addView(paramc, new LinearLayoutCompat.LayoutParams(0, -1, 1.0F));
    Spinner localSpinner = this.mTabSpinner;
    if (localSpinner != null) {
      ((a)localSpinner.getAdapter()).notifyDataSetChanged();
    }
    if (paramBoolean) {
      paramc.setSelected(true);
    }
    if (this.mAllowCollapse) {
      requestLayout();
    }
  }
  
  public void animateToTab(int paramInt)
  {
    final View localView = this.mTabLayout.getChildAt(paramInt);
    Runnable localRunnable = this.mTabSelector;
    if (localRunnable != null) {
      removeCallbacks(localRunnable);
    }
    this.mTabSelector = new Runnable()
    {
      public void run()
      {
        int i = localView.getLeft();
        int j = (ScrollingTabContainerView.this.getWidth() - localView.getWidth()) / 2;
        ScrollingTabContainerView.this.smoothScrollTo(i - j, 0);
        ScrollingTabContainerView.this.mTabSelector = null;
      }
    };
    post(this.mTabSelector);
  }
  
  public void animateToVisibility(int paramInt)
  {
    ViewPropertyAnimator localViewPropertyAnimator = this.mVisibilityAnim;
    if (localViewPropertyAnimator != null) {
      localViewPropertyAnimator.cancel();
    }
    if (paramInt == 0)
    {
      if (getVisibility() != 0) {
        setAlpha(0.0F);
      }
      localViewPropertyAnimator = animate().alpha(1.0F);
      localViewPropertyAnimator.setDuration(200L);
      localViewPropertyAnimator.setInterpolator(sAlphaInterpolator);
      localViewPropertyAnimator.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimator, paramInt));
      localViewPropertyAnimator.start();
    }
    else
    {
      localViewPropertyAnimator = animate().alpha(0.0F);
      localViewPropertyAnimator.setDuration(200L);
      localViewPropertyAnimator.setInterpolator(sAlphaInterpolator);
      localViewPropertyAnimator.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimator, paramInt));
      localViewPropertyAnimator.start();
    }
  }
  
  c createTabView(a.c paramc, boolean paramBoolean)
  {
    paramc = new c(getContext(), paramc, paramBoolean);
    if (paramBoolean)
    {
      paramc.setBackgroundDrawable(null);
      paramc.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
    }
    else
    {
      paramc.setFocusable(true);
      if (this.mTabClickListener == null) {
        this.mTabClickListener = new b();
      }
      paramc.setOnClickListener(this.mTabClickListener);
    }
    return paramc;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Runnable localRunnable = this.mTabSelector;
    if (localRunnable != null) {
      post(localRunnable);
    }
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    paramConfiguration = a.a(getContext());
    setContentHeight(paramConfiguration.e());
    this.mStackedTabMaxWidth = paramConfiguration.g();
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Runnable localRunnable = this.mTabSelector;
    if (localRunnable != null) {
      removeCallbacks(localRunnable);
    }
  }
  
  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    ((c)paramView).b().d();
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    paramInt2 = 1;
    boolean bool;
    if (i == 1073741824) {
      bool = true;
    } else {
      bool = false;
    }
    setFillViewport(bool);
    int j = this.mTabLayout.getChildCount();
    if ((j > 1) && ((i == 1073741824) || (i == Integer.MIN_VALUE)))
    {
      if (j > 2) {
        this.mMaxTabWidth = ((int)(View.MeasureSpec.getSize(paramInt1) * 0.4F));
      } else {
        this.mMaxTabWidth = (View.MeasureSpec.getSize(paramInt1) / 2);
      }
      this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
    }
    else
    {
      this.mMaxTabWidth = -1;
    }
    j = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
    if ((bool) || (!this.mAllowCollapse)) {
      paramInt2 = 0;
    }
    if (paramInt2 != 0)
    {
      this.mTabLayout.measure(0, j);
      if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(paramInt1)) {
        performCollapse();
      } else {
        performExpand();
      }
    }
    else
    {
      performExpand();
    }
    paramInt2 = getMeasuredWidth();
    super.onMeasure(paramInt1, j);
    paramInt1 = getMeasuredWidth();
    if ((bool) && (paramInt2 != paramInt1)) {
      setTabSelected(this.mSelectedTabIndex);
    }
  }
  
  public void onNothingSelected(AdapterView<?> paramAdapterView) {}
  
  public void removeAllTabs()
  {
    this.mTabLayout.removeAllViews();
    Spinner localSpinner = this.mTabSpinner;
    if (localSpinner != null) {
      ((a)localSpinner.getAdapter()).notifyDataSetChanged();
    }
    if (this.mAllowCollapse) {
      requestLayout();
    }
  }
  
  public void removeTabAt(int paramInt)
  {
    this.mTabLayout.removeViewAt(paramInt);
    Spinner localSpinner = this.mTabSpinner;
    if (localSpinner != null) {
      ((a)localSpinner.getAdapter()).notifyDataSetChanged();
    }
    if (this.mAllowCollapse) {
      requestLayout();
    }
  }
  
  public void setAllowCollapse(boolean paramBoolean)
  {
    this.mAllowCollapse = paramBoolean;
  }
  
  public void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
    requestLayout();
  }
  
  public void setTabSelected(int paramInt)
  {
    this.mSelectedTabIndex = paramInt;
    int i = this.mTabLayout.getChildCount();
    for (int j = 0; j < i; j++)
    {
      localObject = this.mTabLayout.getChildAt(j);
      boolean bool;
      if (j == paramInt) {
        bool = true;
      } else {
        bool = false;
      }
      ((View)localObject).setSelected(bool);
      if (bool) {
        animateToTab(paramInt);
      }
    }
    Object localObject = this.mTabSpinner;
    if ((localObject != null) && (paramInt >= 0)) {
      ((Spinner)localObject).setSelection(paramInt);
    }
  }
  
  public void updateTab(int paramInt)
  {
    ((c)this.mTabLayout.getChildAt(paramInt)).a();
    Spinner localSpinner = this.mTabSpinner;
    if (localSpinner != null) {
      ((a)localSpinner.getAdapter()).notifyDataSetChanged();
    }
    if (this.mAllowCollapse) {
      requestLayout();
    }
  }
  
  protected class VisibilityAnimListener
    extends AnimatorListenerAdapter
  {
    private boolean mCanceled = false;
    private int mFinalVisibility;
    
    protected VisibilityAnimListener() {}
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      this.mCanceled = true;
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      if (this.mCanceled) {
        return;
      }
      paramAnimator = ScrollingTabContainerView.this;
      paramAnimator.mVisibilityAnim = null;
      paramAnimator.setVisibility(this.mFinalVisibility);
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      ScrollingTabContainerView.this.setVisibility(0);
      this.mCanceled = false;
    }
    
    public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimator paramViewPropertyAnimator, int paramInt)
    {
      this.mFinalVisibility = paramInt;
      ScrollingTabContainerView.this.mVisibilityAnim = paramViewPropertyAnimator;
      return this;
    }
  }
  
  private class a
    extends BaseAdapter
  {
    a() {}
    
    public int getCount()
    {
      return ScrollingTabContainerView.this.mTabLayout.getChildCount();
    }
    
    public Object getItem(int paramInt)
    {
      return ((ScrollingTabContainerView.c)ScrollingTabContainerView.this.mTabLayout.getChildAt(paramInt)).b();
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = ScrollingTabContainerView.this.createTabView((a.c)getItem(paramInt), true);
      } else {
        ((ScrollingTabContainerView.c)paramView).a((a.c)getItem(paramInt));
      }
      return paramView;
    }
  }
  
  private class b
    implements View.OnClickListener
  {
    b() {}
    
    public void onClick(View paramView)
    {
      ((ScrollingTabContainerView.c)paramView).b().d();
      int i = ScrollingTabContainerView.this.mTabLayout.getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = ScrollingTabContainerView.this.mTabLayout.getChildAt(j);
        boolean bool;
        if (localView == paramView) {
          bool = true;
        } else {
          bool = false;
        }
        localView.setSelected(bool);
      }
    }
  }
  
  private class c
    extends LinearLayoutCompat
  {
    private final int[] b = { 16842964 };
    private a.c c;
    private TextView d;
    private ImageView e;
    private View f;
    
    public c(Context paramContext, a.c paramc, boolean paramBoolean)
    {
      super(null, a.a.actionBarTabStyle);
      this.c = paramc;
      this$1 = TintTypedArray.obtainStyledAttributes(paramContext, null, this.b, a.a.actionBarTabStyle, 0);
      if (ScrollingTabContainerView.this.hasValue(0)) {
        setBackgroundDrawable(ScrollingTabContainerView.this.getDrawable(0));
      }
      ScrollingTabContainerView.this.recycle();
      if (paramBoolean) {
        setGravity(8388627);
      }
      a();
    }
    
    public void a()
    {
      a.c localc = this.c;
      Object localObject1 = localc.c();
      Object localObject2 = null;
      if (localObject1 != null)
      {
        localObject2 = ((View)localObject1).getParent();
        if (localObject2 != this)
        {
          if (localObject2 != null) {
            ((ViewGroup)localObject2).removeView((View)localObject1);
          }
          addView((View)localObject1);
        }
        this.f = ((View)localObject1);
        localObject2 = this.d;
        if (localObject2 != null) {
          ((TextView)localObject2).setVisibility(8);
        }
        localObject2 = this.e;
        if (localObject2 != null)
        {
          ((ImageView)localObject2).setVisibility(8);
          this.e.setImageDrawable(null);
        }
      }
      else
      {
        localObject1 = this.f;
        if (localObject1 != null)
        {
          removeView((View)localObject1);
          this.f = null;
        }
        Object localObject3 = localc.a();
        localObject1 = localc.b();
        Object localObject4;
        if (localObject3 != null)
        {
          if (this.e == null)
          {
            AppCompatImageView localAppCompatImageView = new AppCompatImageView(getContext());
            localObject4 = new LinearLayoutCompat.LayoutParams(-2, -2);
            ((LinearLayoutCompat.LayoutParams)localObject4).gravity = 16;
            localAppCompatImageView.setLayoutParams((ViewGroup.LayoutParams)localObject4);
            addView(localAppCompatImageView, 0);
            this.e = localAppCompatImageView;
          }
          this.e.setImageDrawable((Drawable)localObject3);
          this.e.setVisibility(0);
        }
        else
        {
          localObject4 = this.e;
          if (localObject4 != null)
          {
            ((ImageView)localObject4).setVisibility(8);
            this.e.setImageDrawable(null);
          }
        }
        boolean bool = TextUtils.isEmpty((CharSequence)localObject1) ^ true;
        if (bool)
        {
          if (this.d == null)
          {
            localObject3 = new AppCompatTextView(getContext(), null, a.a.actionBarTabTextStyle);
            ((TextView)localObject3).setEllipsize(TextUtils.TruncateAt.END);
            localObject4 = new LinearLayoutCompat.LayoutParams(-2, -2);
            ((LinearLayoutCompat.LayoutParams)localObject4).gravity = 16;
            ((TextView)localObject3).setLayoutParams((ViewGroup.LayoutParams)localObject4);
            addView((View)localObject3);
            this.d = ((TextView)localObject3);
          }
          this.d.setText((CharSequence)localObject1);
          this.d.setVisibility(0);
        }
        else
        {
          localObject1 = this.d;
          if (localObject1 != null)
          {
            ((TextView)localObject1).setVisibility(8);
            this.d.setText(null);
          }
        }
        localObject1 = this.e;
        if (localObject1 != null) {
          ((ImageView)localObject1).setContentDescription(localc.e());
        }
        if (!bool) {
          localObject2 = localc.e();
        }
        TooltipCompat.setTooltipText(this, (CharSequence)localObject2);
      }
    }
    
    public void a(a.c paramc)
    {
      this.c = paramc;
      a();
    }
    
    public a.c b()
    {
      return this.c;
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(a.c.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName(a.c.class.getName());
    }
    
    public void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((ScrollingTabContainerView.this.mMaxTabWidth > 0) && (getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth)) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), paramInt2);
      }
    }
    
    public void setSelected(boolean paramBoolean)
    {
      int i;
      if (isSelected() != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      super.setSelected(paramBoolean);
      if ((i != 0) && (paramBoolean)) {
        sendAccessibilityEvent(4);
      }
    }
  }
}


/* Location:              ~/android/support/v7/widget/ScrollingTabContainerView.class
 *
 * Reversed by:           J
 */