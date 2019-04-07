package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;

abstract class m
  implements o, s, AdapterView.OnItemClickListener
{
  private Rect a;
  
  protected static int a(ListAdapter paramListAdapter, ViewGroup paramViewGroup, Context paramContext, int paramInt)
  {
    int i = 0;
    int j = View.MeasureSpec.makeMeasureSpec(0, 0);
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int m = paramListAdapter.getCount();
    ViewGroup localViewGroup = paramViewGroup;
    paramViewGroup = null;
    int n = 0;
    int i1 = 0;
    while (i < m)
    {
      int i2 = paramListAdapter.getItemViewType(i);
      int i3 = i1;
      Object localObject = paramViewGroup;
      if (i2 != i1)
      {
        localObject = null;
        i3 = i2;
      }
      paramViewGroup = localViewGroup;
      if (localViewGroup == null) {
        paramViewGroup = new FrameLayout(paramContext);
      }
      localObject = paramListAdapter.getView(i, (View)localObject, paramViewGroup);
      ((View)localObject).measure(j, k);
      i1 = ((View)localObject).getMeasuredWidth();
      if (i1 >= paramInt) {
        return paramInt;
      }
      i2 = n;
      if (i1 > n) {
        i2 = i1;
      }
      i++;
      i1 = i3;
      localViewGroup = paramViewGroup;
      paramViewGroup = (ViewGroup)localObject;
      n = i2;
    }
    return n;
  }
  
  protected static g a(ListAdapter paramListAdapter)
  {
    if ((paramListAdapter instanceof HeaderViewListAdapter)) {
      return (g)((HeaderViewListAdapter)paramListAdapter).getWrappedAdapter();
    }
    return (g)paramListAdapter;
  }
  
  protected static boolean b(h paramh)
  {
    int i = paramh.size();
    boolean bool1 = false;
    boolean bool2;
    for (int j = 0;; j++)
    {
      bool2 = bool1;
      if (j >= i) {
        break;
      }
      MenuItem localMenuItem = paramh.getItem(j);
      if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null))
      {
        bool2 = true;
        break;
      }
    }
    return bool2;
  }
  
  public abstract void a(int paramInt);
  
  public void a(Context paramContext, h paramh) {}
  
  public void a(Rect paramRect)
  {
    this.a = paramRect;
  }
  
  public abstract void a(h paramh);
  
  public abstract void a(View paramView);
  
  public abstract void a(PopupWindow.OnDismissListener paramOnDismissListener);
  
  public boolean a(h paramh, j paramj)
  {
    return false;
  }
  
  public int b()
  {
    return 0;
  }
  
  public abstract void b(int paramInt);
  
  public abstract void b(boolean paramBoolean);
  
  public boolean b(h paramh, j paramj)
  {
    return false;
  }
  
  public abstract void c(int paramInt);
  
  public abstract void c(boolean paramBoolean);
  
  protected boolean d()
  {
    return true;
  }
  
  public Rect e()
  {
    return this.a;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramView = (ListAdapter)paramAdapterView.getAdapter();
    paramAdapterView = a(paramView).b;
    paramView = (MenuItem)paramView.getItem(paramInt);
    if (d()) {
      paramInt = 0;
    } else {
      paramInt = 4;
    }
    paramAdapterView.a(paramView, this, paramInt);
  }
}


/* Location:              ~/android/support/v7/view/menu/m.class
 *
 * Reversed by:           J
 */