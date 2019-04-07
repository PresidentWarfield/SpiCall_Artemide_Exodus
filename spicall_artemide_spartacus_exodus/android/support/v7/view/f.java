package android.support.v7.view;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.view.menu.q;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class f
  extends ActionMode
{
  final Context a;
  final b b;
  
  public f(Context paramContext, b paramb)
  {
    this.a = paramContext;
    this.b = paramb;
  }
  
  public void finish()
  {
    this.b.c();
  }
  
  public View getCustomView()
  {
    return this.b.i();
  }
  
  public Menu getMenu()
  {
    return q.a(this.a, (SupportMenu)this.b.b());
  }
  
  public MenuInflater getMenuInflater()
  {
    return this.b.a();
  }
  
  public CharSequence getSubtitle()
  {
    return this.b.g();
  }
  
  public Object getTag()
  {
    return this.b.j();
  }
  
  public CharSequence getTitle()
  {
    return this.b.f();
  }
  
  public boolean getTitleOptionalHint()
  {
    return this.b.k();
  }
  
  public void invalidate()
  {
    this.b.d();
  }
  
  public boolean isTitleOptional()
  {
    return this.b.h();
  }
  
  public void setCustomView(View paramView)
  {
    this.b.a(paramView);
  }
  
  public void setSubtitle(int paramInt)
  {
    this.b.b(paramInt);
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.b.a(paramCharSequence);
  }
  
  public void setTag(Object paramObject)
  {
    this.b.a(paramObject);
  }
  
  public void setTitle(int paramInt)
  {
    this.b.a(paramInt);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.b.b(paramCharSequence);
  }
  
  public void setTitleOptionalHint(boolean paramBoolean)
  {
    this.b.a(paramBoolean);
  }
  
  public static class a
    implements b.a
  {
    final ActionMode.Callback a;
    final Context b;
    final ArrayList<f> c;
    final SimpleArrayMap<Menu, Menu> d;
    
    public a(Context paramContext, ActionMode.Callback paramCallback)
    {
      this.b = paramContext;
      this.a = paramCallback;
      this.c = new ArrayList();
      this.d = new SimpleArrayMap();
    }
    
    private Menu a(Menu paramMenu)
    {
      Menu localMenu1 = (Menu)this.d.get(paramMenu);
      Menu localMenu2 = localMenu1;
      if (localMenu1 == null)
      {
        localMenu2 = q.a(this.b, (SupportMenu)paramMenu);
        this.d.put(paramMenu, localMenu2);
      }
      return localMenu2;
    }
    
    public void a(b paramb)
    {
      this.a.onDestroyActionMode(b(paramb));
    }
    
    public boolean a(b paramb, Menu paramMenu)
    {
      return this.a.onCreateActionMode(b(paramb), a(paramMenu));
    }
    
    public boolean a(b paramb, MenuItem paramMenuItem)
    {
      return this.a.onActionItemClicked(b(paramb), q.a(this.b, (SupportMenuItem)paramMenuItem));
    }
    
    public ActionMode b(b paramb)
    {
      int i = this.c.size();
      for (int j = 0; j < i; j++)
      {
        f localf = (f)this.c.get(j);
        if ((localf != null) && (localf.b == paramb)) {
          return localf;
        }
      }
      paramb = new f(this.b, paramb);
      this.c.add(paramb);
      return paramb;
    }
    
    public boolean b(b paramb, Menu paramMenu)
    {
      return this.a.onPrepareActionMode(b(paramb), a(paramMenu));
    }
  }
}


/* Location:              ~/android/support/v7/view/f.class
 *
 * Reversed by:           J
 */