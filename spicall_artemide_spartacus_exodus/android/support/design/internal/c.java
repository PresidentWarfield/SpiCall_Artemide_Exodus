package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.a.c;
import android.support.design.a.g;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.o;
import android.support.v7.view.menu.o.a;
import android.support.v7.view.menu.p;
import android.support.v7.view.menu.u;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class c
  implements o
{
  LinearLayout a;
  h b;
  b c;
  LayoutInflater d;
  int e;
  boolean f;
  ColorStateList g;
  ColorStateList h;
  Drawable i;
  int j;
  final View.OnClickListener k = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      paramAnonymousView = (NavigationMenuItemView)paramAnonymousView;
      c.this.b(true);
      paramAnonymousView = paramAnonymousView.getItemData();
      boolean bool = c.this.b.a(paramAnonymousView, c.this, 0);
      if ((paramAnonymousView != null) && (paramAnonymousView.isCheckable()) && (bool)) {
        c.this.c.a(paramAnonymousView);
      }
      c.this.b(false);
      c.this.a(false);
    }
  };
  private NavigationMenuView l;
  private o.a m;
  private int n;
  private int o;
  
  public p a(ViewGroup paramViewGroup)
  {
    if (this.l == null)
    {
      this.l = ((NavigationMenuView)this.d.inflate(a.g.design_navigation_menu, paramViewGroup, false));
      if (this.c == null) {
        this.c = new b();
      }
      this.a = ((LinearLayout)this.d.inflate(a.g.design_navigation_item_header, this.l, false));
      this.l.setAdapter(this.c);
    }
    return this.l;
  }
  
  public void a(int paramInt)
  {
    this.n = paramInt;
  }
  
  public void a(Context paramContext, h paramh)
  {
    this.d = LayoutInflater.from(paramContext);
    this.b = paramh;
    this.j = paramContext.getResources().getDimensionPixelOffset(a.c.design_navigation_separator_vertical_padding);
  }
  
  public void a(ColorStateList paramColorStateList)
  {
    this.h = paramColorStateList;
    a(false);
  }
  
  public void a(Drawable paramDrawable)
  {
    this.i = paramDrawable;
    a(false);
  }
  
  public void a(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      paramParcelable = (Bundle)paramParcelable;
      Object localObject = paramParcelable.getSparseParcelableArray("android:menu:list");
      if (localObject != null) {
        this.l.restoreHierarchyState((SparseArray)localObject);
      }
      localObject = paramParcelable.getBundle("android:menu:adapter");
      if (localObject != null) {
        this.c.a((Bundle)localObject);
      }
      paramParcelable = paramParcelable.getSparseParcelableArray("android:menu:header");
      if (paramParcelable != null) {
        this.a.restoreHierarchyState(paramParcelable);
      }
    }
  }
  
  public void a(WindowInsetsCompat paramWindowInsetsCompat)
  {
    int i1 = paramWindowInsetsCompat.getSystemWindowInsetTop();
    if (this.o != i1)
    {
      this.o = i1;
      if (this.a.getChildCount() == 0)
      {
        NavigationMenuView localNavigationMenuView = this.l;
        localNavigationMenuView.setPadding(0, this.o, 0, localNavigationMenuView.getPaddingBottom());
      }
    }
    ViewCompat.dispatchApplyWindowInsets(this.a, paramWindowInsetsCompat);
  }
  
  public void a(h paramh, boolean paramBoolean)
  {
    o.a locala = this.m;
    if (locala != null) {
      locala.a(paramh, paramBoolean);
    }
  }
  
  public void a(j paramj)
  {
    this.c.a(paramj);
  }
  
  public void a(o.a parama)
  {
    this.m = parama;
  }
  
  public void a(View paramView)
  {
    this.a.addView(paramView);
    paramView = this.l;
    paramView.setPadding(0, 0, 0, paramView.getPaddingBottom());
  }
  
  public void a(boolean paramBoolean)
  {
    b localb = this.c;
    if (localb != null) {
      localb.a();
    }
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean a(h paramh, j paramj)
  {
    return false;
  }
  
  public boolean a(u paramu)
  {
    return false;
  }
  
  public int b()
  {
    return this.n;
  }
  
  public View b(int paramInt)
  {
    View localView = this.d.inflate(paramInt, this.a, false);
    a(localView);
    return localView;
  }
  
  public void b(ColorStateList paramColorStateList)
  {
    this.g = paramColorStateList;
    a(false);
  }
  
  public void b(boolean paramBoolean)
  {
    b localb = this.c;
    if (localb != null) {
      localb.a(paramBoolean);
    }
  }
  
  public boolean b(h paramh, j paramj)
  {
    return false;
  }
  
  public Parcelable c()
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      Bundle localBundle = new Bundle();
      if (this.l != null)
      {
        localObject = new SparseArray();
        this.l.saveHierarchyState((SparseArray)localObject);
        localBundle.putSparseParcelableArray("android:menu:list", (SparseArray)localObject);
      }
      Object localObject = this.c;
      if (localObject != null) {
        localBundle.putBundle("android:menu:adapter", ((b)localObject).b());
      }
      if (this.a != null)
      {
        localObject = new SparseArray();
        this.a.saveHierarchyState((SparseArray)localObject);
        localBundle.putSparseParcelableArray("android:menu:header", (SparseArray)localObject);
      }
      return localBundle;
    }
    return null;
  }
  
  public void c(int paramInt)
  {
    this.e = paramInt;
    this.f = true;
    a(false);
  }
  
  public int d()
  {
    return this.a.getChildCount();
  }
  
  public ColorStateList e()
  {
    return this.h;
  }
  
  public ColorStateList f()
  {
    return this.g;
  }
  
  public Drawable g()
  {
    return this.i;
  }
  
  private static class a
    extends c.j
  {
    public a(View paramView)
    {
      super();
    }
  }
  
  private class b
    extends RecyclerView.Adapter<c.j>
  {
    private final ArrayList<c.d> b = new ArrayList();
    private j c;
    private boolean d;
    
    b()
    {
      c();
    }
    
    private void a(int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        ((c.f)this.b.get(paramInt1)).a = true;
        paramInt1++;
      }
    }
    
    private void c()
    {
      if (this.d) {
        return;
      }
      this.d = true;
      this.b.clear();
      this.b.add(new c.c());
      int i = c.this.b.i().size();
      int j = 0;
      int k = -1;
      boolean bool1 = false;
      int i1;
      for (int m = 0; j < i; m = i1)
      {
        j localj = (j)c.this.b.i().get(j);
        if (localj.isChecked()) {
          a(localj);
        }
        if (localj.isCheckable()) {
          localj.a(false);
        }
        int n;
        boolean bool2;
        int i4;
        Object localObject;
        if (localj.hasSubMenu())
        {
          SubMenu localSubMenu = localj.getSubMenu();
          n = k;
          bool2 = bool1;
          i1 = m;
          if (localSubMenu.hasVisibleItems())
          {
            if (j != 0) {
              this.b.add(new c.e(c.this.j, 0));
            }
            this.b.add(new c.f(localj));
            int i2 = this.b.size();
            int i3 = localSubMenu.size();
            n = 0;
            for (i4 = 0; n < i3; i4 = i1)
            {
              localObject = (j)localSubMenu.getItem(n);
              i1 = i4;
              if (((j)localObject).isVisible())
              {
                i1 = i4;
                if (i4 == 0)
                {
                  i1 = i4;
                  if (((j)localObject).getIcon() != null) {
                    i1 = 1;
                  }
                }
                if (((j)localObject).isCheckable()) {
                  ((j)localObject).a(false);
                }
                if (localj.isChecked()) {
                  a(localj);
                }
                this.b.add(new c.f((j)localObject));
              }
              n++;
            }
            n = k;
            bool2 = bool1;
            i1 = m;
            if (i4 != 0)
            {
              a(i2, this.b.size());
              n = k;
              bool2 = bool1;
              i1 = m;
            }
          }
        }
        else
        {
          n = localj.getGroupId();
          if (n != k)
          {
            i4 = this.b.size();
            if (localj.getIcon() != null) {
              bool2 = true;
            } else {
              bool2 = false;
            }
            if (j != 0)
            {
              i4++;
              this.b.add(new c.e(c.this.j, c.this.j));
            }
          }
          else
          {
            bool2 = bool1;
            i4 = m;
            if (!bool1)
            {
              bool2 = bool1;
              i4 = m;
              if (localj.getIcon() != null)
              {
                a(m, this.b.size());
                bool2 = true;
                i4 = m;
              }
            }
          }
          localObject = new c.f(localj);
          ((c.f)localObject).a = bool2;
          this.b.add(localObject);
          i1 = i4;
        }
        j++;
        k = n;
        bool1 = bool2;
      }
      this.d = false;
    }
    
    public c.j a(ViewGroup paramViewGroup, int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return null;
      case 3: 
        return new c.a(c.this.a);
      case 2: 
        return new c.h(c.this.d, paramViewGroup);
      case 1: 
        return new c.i(c.this.d, paramViewGroup);
      }
      return new c.g(c.this.d, paramViewGroup, c.this.k);
    }
    
    public void a()
    {
      c();
      notifyDataSetChanged();
    }
    
    public void a(Bundle paramBundle)
    {
      int i = 0;
      int j = paramBundle.getInt("android:menu:checked", 0);
      int m;
      Object localObject1;
      if (j != 0)
      {
        this.d = true;
        int k = this.b.size();
        for (m = 0; m < k; m++)
        {
          localObject1 = (c.d)this.b.get(m);
          if ((localObject1 instanceof c.f))
          {
            localObject1 = ((c.f)localObject1).a();
            if ((localObject1 != null) && (((j)localObject1).getItemId() == j))
            {
              a((j)localObject1);
              break;
            }
          }
        }
        this.d = false;
        c();
      }
      paramBundle = paramBundle.getSparseParcelableArray("android:menu:action_views");
      if (paramBundle != null)
      {
        j = this.b.size();
        for (m = i; m < j; m++)
        {
          localObject1 = (c.d)this.b.get(m);
          if ((localObject1 instanceof c.f))
          {
            Object localObject2 = ((c.f)localObject1).a();
            if (localObject2 != null)
            {
              localObject1 = ((j)localObject2).getActionView();
              if (localObject1 != null)
              {
                localObject2 = (e)paramBundle.get(((j)localObject2).getItemId());
                if (localObject2 != null) {
                  ((View)localObject1).restoreHierarchyState((SparseArray)localObject2);
                }
              }
            }
          }
        }
      }
    }
    
    public void a(c.j paramj)
    {
      if ((paramj instanceof c.g)) {
        ((NavigationMenuItemView)paramj.itemView).a();
      }
    }
    
    public void a(c.j paramj, int paramInt)
    {
      Object localObject;
      switch (getItemViewType(paramInt))
      {
      default: 
        break;
      case 2: 
        localObject = (c.e)this.b.get(paramInt);
        paramj.itemView.setPadding(0, ((c.e)localObject).a(), 0, ((c.e)localObject).b());
        break;
      case 1: 
        ((TextView)paramj.itemView).setText(((c.f)this.b.get(paramInt)).a().getTitle());
        break;
      case 0: 
        localObject = (NavigationMenuItemView)paramj.itemView;
        ((NavigationMenuItemView)localObject).setIconTintList(c.this.h);
        if (c.this.f) {
          ((NavigationMenuItemView)localObject).setTextAppearance(c.this.e);
        }
        if (c.this.g != null) {
          ((NavigationMenuItemView)localObject).setTextColor(c.this.g);
        }
        if (c.this.i != null) {
          paramj = c.this.i.getConstantState().newDrawable();
        } else {
          paramj = null;
        }
        ViewCompat.setBackground((View)localObject, paramj);
        paramj = (c.f)this.b.get(paramInt);
        ((NavigationMenuItemView)localObject).setNeedsEmptyIcon(paramj.a);
        ((NavigationMenuItemView)localObject).a(paramj.a(), 0);
      }
    }
    
    public void a(j paramj)
    {
      if ((this.c != paramj) && (paramj.isCheckable()))
      {
        j localj = this.c;
        if (localj != null) {
          localj.setChecked(false);
        }
        this.c = paramj;
        paramj.setChecked(true);
        return;
      }
    }
    
    public void a(boolean paramBoolean)
    {
      this.d = paramBoolean;
    }
    
    public Bundle b()
    {
      Bundle localBundle = new Bundle();
      Object localObject = this.c;
      if (localObject != null) {
        localBundle.putInt("android:menu:checked", ((j)localObject).getItemId());
      }
      SparseArray localSparseArray = new SparseArray();
      int i = 0;
      int j = this.b.size();
      while (i < j)
      {
        localObject = (c.d)this.b.get(i);
        if ((localObject instanceof c.f))
        {
          j localj = ((c.f)localObject).a();
          if (localj != null) {
            localObject = localj.getActionView();
          } else {
            localObject = null;
          }
          if (localObject != null)
          {
            e locale = new e();
            ((View)localObject).saveHierarchyState(locale);
            localSparseArray.put(localj.getItemId(), locale);
          }
        }
        i++;
      }
      localBundle.putSparseParcelableArray("android:menu:action_views", localSparseArray);
      return localBundle;
    }
    
    public int getItemCount()
    {
      return this.b.size();
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public int getItemViewType(int paramInt)
    {
      c.d locald = (c.d)this.b.get(paramInt);
      if ((locald instanceof c.e)) {
        return 2;
      }
      if ((locald instanceof c.c)) {
        return 3;
      }
      if ((locald instanceof c.f))
      {
        if (((c.f)locald).a().hasSubMenu()) {
          return 1;
        }
        return 0;
      }
      throw new RuntimeException("Unknown item type.");
    }
  }
  
  private static class c
    implements c.d
  {}
  
  private static abstract interface d {}
  
  private static class e
    implements c.d
  {
    private final int a;
    private final int b;
    
    public e(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
    
    public int a()
    {
      return this.a;
    }
    
    public int b()
    {
      return this.b;
    }
  }
  
  private static class f
    implements c.d
  {
    boolean a;
    private final j b;
    
    f(j paramj)
    {
      this.b = paramj;
    }
    
    public j a()
    {
      return this.b;
    }
  }
  
  private static class g
    extends c.j
  {
    public g(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, View.OnClickListener paramOnClickListener)
    {
      super();
      this.itemView.setOnClickListener(paramOnClickListener);
    }
  }
  
  private static class h
    extends c.j
  {
    public h(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static class i
    extends c.j
  {
    public i(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static abstract class j
    extends RecyclerView.ViewHolder
  {
    public j(View paramView)
    {
      super();
    }
  }
}


/* Location:              ~/android/support/design/internal/c.class
 *
 * Reversed by:           J
 */