package android.support.v7.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class b
  implements o
{
  protected Context a;
  protected Context b;
  protected h c;
  protected LayoutInflater d;
  protected LayoutInflater e;
  protected p f;
  private o.a g;
  private int h;
  private int i;
  private int j;
  
  public b(Context paramContext, int paramInt1, int paramInt2)
  {
    this.a = paramContext;
    this.d = LayoutInflater.from(paramContext);
    this.h = paramInt1;
    this.i = paramInt2;
  }
  
  public p a(ViewGroup paramViewGroup)
  {
    if (this.f == null)
    {
      this.f = ((p)this.d.inflate(this.h, paramViewGroup, false));
      this.f.initialize(this.c);
      a(true);
    }
    return this.f;
  }
  
  public View a(j paramj, View paramView, ViewGroup paramViewGroup)
  {
    if ((paramView instanceof p.a)) {
      paramView = (p.a)paramView;
    } else {
      paramView = b(paramViewGroup);
    }
    a(paramj, paramView);
    return (View)paramView;
  }
  
  public void a(int paramInt)
  {
    this.j = paramInt;
  }
  
  public void a(Context paramContext, h paramh)
  {
    this.b = paramContext;
    this.e = LayoutInflater.from(this.b);
    this.c = paramh;
  }
  
  public void a(h paramh, boolean paramBoolean)
  {
    o.a locala = this.g;
    if (locala != null) {
      locala.a(paramh, paramBoolean);
    }
  }
  
  public abstract void a(j paramj, p.a parama);
  
  public void a(o.a parama)
  {
    this.g = parama;
  }
  
  protected void a(View paramView, int paramInt)
  {
    ViewGroup localViewGroup = (ViewGroup)paramView.getParent();
    if (localViewGroup != null) {
      localViewGroup.removeView(paramView);
    }
    ((ViewGroup)this.f).addView(paramView, paramInt);
  }
  
  public void a(boolean paramBoolean)
  {
    ViewGroup localViewGroup = (ViewGroup)this.f;
    if (localViewGroup == null) {
      return;
    }
    Object localObject = this.c;
    int k = 0;
    if (localObject != null)
    {
      ((h)localObject).j();
      ArrayList localArrayList = this.c.i();
      int m = localArrayList.size();
      int n = 0;
      int i1;
      for (k = 0; n < m; k = i1)
      {
        j localj = (j)localArrayList.get(n);
        i1 = k;
        if (a(k, localj))
        {
          View localView1 = localViewGroup.getChildAt(k);
          if ((localView1 instanceof p.a)) {
            localObject = ((p.a)localView1).getItemData();
          } else {
            localObject = null;
          }
          View localView2 = a(localj, localView1, localViewGroup);
          if (localj != localObject)
          {
            localView2.setPressed(false);
            localView2.jumpDrawablesToCurrentState();
          }
          if (localView2 != localView1) {
            a(localView2, k);
          }
          i1 = k + 1;
        }
        n++;
      }
    }
    while (k < localViewGroup.getChildCount()) {
      if (!a(localViewGroup, k)) {
        k++;
      }
    }
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean a(int paramInt, j paramj)
  {
    return true;
  }
  
  public boolean a(h paramh, j paramj)
  {
    return false;
  }
  
  public boolean a(u paramu)
  {
    o.a locala = this.g;
    if (locala != null) {
      return locala.a(paramu);
    }
    return false;
  }
  
  protected boolean a(ViewGroup paramViewGroup, int paramInt)
  {
    paramViewGroup.removeViewAt(paramInt);
    return true;
  }
  
  public int b()
  {
    return this.j;
  }
  
  public p.a b(ViewGroup paramViewGroup)
  {
    return (p.a)this.d.inflate(this.i, paramViewGroup, false);
  }
  
  public boolean b(h paramh, j paramj)
  {
    return false;
  }
  
  public o.a d()
  {
    return this.g;
  }
}


/* Location:              ~/android/support/v7/view/menu/b.class
 *
 * Reversed by:           J
 */