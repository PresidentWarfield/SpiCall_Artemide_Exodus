package android.support.v7.view.menu;

import android.support.v7.a.a.g;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class g
  extends BaseAdapter
{
  static final int a = a.g.abc_popup_menu_item_layout;
  h b;
  private int c = -1;
  private boolean d;
  private final boolean e;
  private final LayoutInflater f;
  
  public g(h paramh, LayoutInflater paramLayoutInflater, boolean paramBoolean)
  {
    this.e = paramBoolean;
    this.f = paramLayoutInflater;
    this.b = paramh;
    b();
  }
  
  public h a()
  {
    return this.b;
  }
  
  public j a(int paramInt)
  {
    ArrayList localArrayList;
    if (this.e) {
      localArrayList = this.b.l();
    } else {
      localArrayList = this.b.i();
    }
    int i = this.c;
    int j = paramInt;
    if (i >= 0)
    {
      j = paramInt;
      if (paramInt >= i) {
        j = paramInt + 1;
      }
    }
    return (j)localArrayList.get(j);
  }
  
  public void a(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  void b()
  {
    j localj = this.b.r();
    if (localj != null)
    {
      ArrayList localArrayList = this.b.l();
      int i = localArrayList.size();
      for (int j = 0; j < i; j++) {
        if ((j)localArrayList.get(j) == localj)
        {
          this.c = j;
          return;
        }
      }
    }
    this.c = -1;
  }
  
  public int getCount()
  {
    ArrayList localArrayList;
    if (this.e) {
      localArrayList = this.b.l();
    } else {
      localArrayList = this.b.i();
    }
    if (this.c < 0) {
      return localArrayList.size();
    }
    return localArrayList.size() - 1;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (paramView == null) {
      localView = this.f.inflate(a, paramViewGroup, false);
    }
    paramView = (p.a)localView;
    if (this.d) {
      ((ListMenuItemView)localView).setForceShowIcon(true);
    }
    paramView.a(a(paramInt), 0);
    return localView;
  }
  
  public void notifyDataSetChanged()
  {
    b();
    super.notifyDataSetChanged();
  }
}


/* Location:              ~/android/support/v7/view/menu/g.class
 *
 * Reversed by:           J
 */