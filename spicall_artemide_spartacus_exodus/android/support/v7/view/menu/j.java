package android.support.v7.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.support.v7.c.a.b;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;

public final class j
  implements SupportMenuItem
{
  private static String F;
  private static String G;
  private static String H;
  private static String I;
  private View A;
  private android.support.v4.view.ActionProvider B;
  private MenuItem.OnActionExpandListener C;
  private boolean D = false;
  private ContextMenu.ContextMenuInfo E;
  h a;
  private final int b;
  private final int c;
  private final int d;
  private final int e;
  private CharSequence f;
  private CharSequence g;
  private Intent h;
  private char i;
  private int j = 4096;
  private char k;
  private int l = 4096;
  private Drawable m;
  private int n = 0;
  private u o;
  private Runnable p;
  private MenuItem.OnMenuItemClickListener q;
  private CharSequence r;
  private CharSequence s;
  private ColorStateList t = null;
  private PorterDuff.Mode u = null;
  private boolean v = false;
  private boolean w = false;
  private boolean x = false;
  private int y = 16;
  private int z = 0;
  
  j(h paramh, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5)
  {
    this.a = paramh;
    this.b = paramInt2;
    this.c = paramInt1;
    this.d = paramInt3;
    this.e = paramInt4;
    this.f = paramCharSequence;
    this.z = paramInt5;
  }
  
  private Drawable a(Drawable paramDrawable)
  {
    Drawable localDrawable = paramDrawable;
    if (paramDrawable != null)
    {
      localDrawable = paramDrawable;
      if (this.x) {
        if (!this.v)
        {
          localDrawable = paramDrawable;
          if (!this.w) {}
        }
        else
        {
          localDrawable = DrawableCompat.wrap(paramDrawable).mutate();
          if (this.v) {
            DrawableCompat.setTintList(localDrawable, this.t);
          }
          if (this.w) {
            DrawableCompat.setTintMode(localDrawable, this.u);
          }
          this.x = false;
        }
      }
    }
    return localDrawable;
  }
  
  public SupportMenuItem a(int paramInt)
  {
    Context localContext = this.a.e();
    a(LayoutInflater.from(localContext).inflate(paramInt, new LinearLayout(localContext), false));
    return this;
  }
  
  public SupportMenuItem a(View paramView)
  {
    this.A = paramView;
    this.B = null;
    if ((paramView != null) && (paramView.getId() == -1))
    {
      int i1 = this.b;
      if (i1 > 0) {
        paramView.setId(i1);
      }
    }
    this.a.b(this);
    return this;
  }
  
  CharSequence a(p.a parama)
  {
    if ((parama != null) && (parama.b())) {
      parama = getTitleCondensed();
    } else {
      parama = getTitle();
    }
    return parama;
  }
  
  public void a(u paramu)
  {
    this.o = paramu;
    paramu.setHeaderTitle(getTitle());
  }
  
  void a(ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    this.E = paramContextMenuInfo;
  }
  
  public void a(boolean paramBoolean)
  {
    int i1 = this.y;
    int i2;
    if (paramBoolean) {
      i2 = 4;
    } else {
      i2 = 0;
    }
    this.y = (i2 | i1 & 0xFFFFFFFB);
  }
  
  public boolean a()
  {
    Object localObject = this.q;
    if ((localObject != null) && (((MenuItem.OnMenuItemClickListener)localObject).onMenuItemClick(this))) {
      return true;
    }
    localObject = this.a;
    if (((h)localObject).a((h)localObject, this)) {
      return true;
    }
    localObject = this.p;
    if (localObject != null)
    {
      ((Runnable)localObject).run();
      return true;
    }
    if (this.h != null) {
      try
      {
        this.a.e().startActivity(this.h);
        return true;
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", localActivityNotFoundException);
      }
    }
    android.support.v4.view.ActionProvider localActionProvider = this.B;
    return (localActionProvider != null) && (localActionProvider.onPerformDefaultAction());
  }
  
  public int b()
  {
    return this.e;
  }
  
  public SupportMenuItem b(int paramInt)
  {
    setShowAsAction(paramInt);
    return this;
  }
  
  void b(boolean paramBoolean)
  {
    int i1 = this.y;
    int i2;
    if (paramBoolean) {
      i2 = 2;
    } else {
      i2 = 0;
    }
    this.y = (i2 | i1 & 0xFFFFFFFD);
    if (i1 != this.y) {
      this.a.a(false);
    }
  }
  
  char c()
  {
    char c1;
    char c2;
    if (this.a.b())
    {
      c1 = this.k;
      c2 = c1;
    }
    else
    {
      c1 = this.i;
      c2 = c1;
    }
    return c2;
  }
  
  boolean c(boolean paramBoolean)
  {
    int i1 = this.y;
    boolean bool = false;
    int i2;
    if (paramBoolean) {
      i2 = 0;
    } else {
      i2 = 8;
    }
    this.y = (i2 | i1 & 0xFFFFFFF7);
    paramBoolean = bool;
    if (i1 != this.y) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public boolean collapseActionView()
  {
    if ((this.z & 0x8) == 0) {
      return false;
    }
    if (this.A == null) {
      return true;
    }
    MenuItem.OnActionExpandListener localOnActionExpandListener = this.C;
    if ((localOnActionExpandListener != null) && (!localOnActionExpandListener.onMenuItemActionCollapse(this))) {
      return false;
    }
    return this.a.d(this);
  }
  
  String d()
  {
    char c1 = c();
    if (c1 == 0) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder(F);
    if (c1 != '\b')
    {
      if (c1 != '\n')
      {
        if (c1 != ' ') {
          localStringBuilder.append(c1);
        } else {
          localStringBuilder.append(I);
        }
      }
      else {
        localStringBuilder.append(G);
      }
    }
    else {
      localStringBuilder.append(H);
    }
    return localStringBuilder.toString();
  }
  
  public void d(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.y |= 0x20;
    } else {
      this.y &= 0xFFFFFFDF;
    }
  }
  
  public void e(boolean paramBoolean)
  {
    this.D = paramBoolean;
    this.a.a(false);
  }
  
  boolean e()
  {
    boolean bool;
    if ((this.a.c()) && (c() != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean expandActionView()
  {
    if (!m()) {
      return false;
    }
    MenuItem.OnActionExpandListener localOnActionExpandListener = this.C;
    if ((localOnActionExpandListener != null) && (!localOnActionExpandListener.onMenuItemActionExpand(this))) {
      return false;
    }
    return this.a.c(this);
  }
  
  public boolean f()
  {
    boolean bool;
    if ((this.y & 0x4) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void g()
  {
    this.a.b(this);
  }
  
  public android.view.ActionProvider getActionProvider()
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
  }
  
  public View getActionView()
  {
    Object localObject = this.A;
    if (localObject != null) {
      return (View)localObject;
    }
    localObject = this.B;
    if (localObject != null)
    {
      this.A = ((android.support.v4.view.ActionProvider)localObject).onCreateActionView(this);
      return this.A;
    }
    return null;
  }
  
  public int getAlphabeticModifiers()
  {
    return this.l;
  }
  
  public char getAlphabeticShortcut()
  {
    return this.k;
  }
  
  public CharSequence getContentDescription()
  {
    return this.r;
  }
  
  public int getGroupId()
  {
    return this.c;
  }
  
  public Drawable getIcon()
  {
    Drawable localDrawable = this.m;
    if (localDrawable != null) {
      return a(localDrawable);
    }
    if (this.n != 0)
    {
      localDrawable = b.b(this.a.e(), this.n);
      this.n = 0;
      this.m = localDrawable;
      return a(localDrawable);
    }
    return null;
  }
  
  public ColorStateList getIconTintList()
  {
    return this.t;
  }
  
  public PorterDuff.Mode getIconTintMode()
  {
    return this.u;
  }
  
  public Intent getIntent()
  {
    return this.h;
  }
  
  @ViewDebug.CapturedViewProperty
  public int getItemId()
  {
    return this.b;
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return this.E;
  }
  
  public int getNumericModifiers()
  {
    return this.j;
  }
  
  public char getNumericShortcut()
  {
    return this.i;
  }
  
  public int getOrder()
  {
    return this.d;
  }
  
  public SubMenu getSubMenu()
  {
    return this.o;
  }
  
  public android.support.v4.view.ActionProvider getSupportActionProvider()
  {
    return this.B;
  }
  
  @ViewDebug.CapturedViewProperty
  public CharSequence getTitle()
  {
    return this.f;
  }
  
  public CharSequence getTitleCondensed()
  {
    CharSequence localCharSequence = this.g;
    if (localCharSequence == null) {
      localCharSequence = this.f;
    }
    if ((Build.VERSION.SDK_INT < 18) && (localCharSequence != null) && (!(localCharSequence instanceof String))) {
      return localCharSequence.toString();
    }
    return localCharSequence;
  }
  
  public CharSequence getTooltipText()
  {
    return this.s;
  }
  
  public boolean h()
  {
    return this.a.q();
  }
  
  public boolean hasSubMenu()
  {
    boolean bool;
    if (this.o != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean i()
  {
    boolean bool;
    if ((this.y & 0x20) == 32) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isActionViewExpanded()
  {
    return this.D;
  }
  
  public boolean isCheckable()
  {
    int i1 = this.y;
    boolean bool = true;
    if ((i1 & 0x1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isChecked()
  {
    boolean bool;
    if ((this.y & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isEnabled()
  {
    boolean bool;
    if ((this.y & 0x10) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isVisible()
  {
    android.support.v4.view.ActionProvider localActionProvider = this.B;
    boolean bool1 = true;
    boolean bool2 = true;
    if ((localActionProvider != null) && (localActionProvider.overridesItemVisibility()))
    {
      if (((this.y & 0x8) != 0) || (!this.B.isVisible())) {
        bool2 = false;
      }
      return bool2;
    }
    if ((this.y & 0x8) == 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    return bool2;
  }
  
  public boolean j()
  {
    int i1 = this.z;
    boolean bool = true;
    if ((i1 & 0x1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean k()
  {
    boolean bool;
    if ((this.z & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean l()
  {
    boolean bool;
    if ((this.z & 0x4) == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean m()
  {
    int i1 = this.z;
    boolean bool = false;
    if ((i1 & 0x8) != 0)
    {
      if (this.A == null)
      {
        android.support.v4.view.ActionProvider localActionProvider = this.B;
        if (localActionProvider != null) {
          this.A = localActionProvider.onCreateActionView(this);
        }
      }
      if (this.A != null) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public MenuItem setActionProvider(android.view.ActionProvider paramActionProvider)
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar)
  {
    if (this.k == paramChar) {
      return this;
    }
    this.k = Character.toLowerCase(paramChar);
    this.a.a(false);
    return this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar, int paramInt)
  {
    if ((this.k == paramChar) && (this.l == paramInt)) {
      return this;
    }
    this.k = Character.toLowerCase(paramChar);
    this.l = KeyEvent.normalizeMetaState(paramInt);
    this.a.a(false);
    return this;
  }
  
  public MenuItem setCheckable(boolean paramBoolean)
  {
    int i1 = this.y;
    this.y = (paramBoolean | i1 & 0xFFFFFFFE);
    if (i1 != this.y) {
      this.a.a(false);
    }
    return this;
  }
  
  public MenuItem setChecked(boolean paramBoolean)
  {
    if ((this.y & 0x4) != 0) {
      this.a.a(this);
    } else {
      b(paramBoolean);
    }
    return this;
  }
  
  public SupportMenuItem setContentDescription(CharSequence paramCharSequence)
  {
    this.r = paramCharSequence;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.y |= 0x10;
    } else {
      this.y &= 0xFFFFFFEF;
    }
    this.a.a(false);
    return this;
  }
  
  public MenuItem setIcon(int paramInt)
  {
    this.m = null;
    this.n = paramInt;
    this.x = true;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setIcon(Drawable paramDrawable)
  {
    this.n = 0;
    this.m = paramDrawable;
    this.x = true;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setIconTintList(ColorStateList paramColorStateList)
  {
    this.t = paramColorStateList;
    this.v = true;
    this.x = true;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setIconTintMode(PorterDuff.Mode paramMode)
  {
    this.u = paramMode;
    this.w = true;
    this.x = true;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setIntent(Intent paramIntent)
  {
    this.h = paramIntent;
    return this;
  }
  
  public MenuItem setNumericShortcut(char paramChar)
  {
    if (this.i == paramChar) {
      return this;
    }
    this.i = ((char)paramChar);
    this.a.a(false);
    return this;
  }
  
  public MenuItem setNumericShortcut(char paramChar, int paramInt)
  {
    if ((this.i == paramChar) && (this.j == paramInt)) {
      return this;
    }
    this.i = ((char)paramChar);
    this.j = KeyEvent.normalizeMetaState(paramInt);
    this.a.a(false);
    return this;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    this.C = paramOnActionExpandListener;
    return this;
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.q = paramOnMenuItemClickListener;
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    this.i = ((char)paramChar1);
    this.k = Character.toLowerCase(paramChar2);
    this.a.a(false);
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2)
  {
    this.i = ((char)paramChar1);
    this.j = KeyEvent.normalizeMetaState(paramInt1);
    this.k = Character.toLowerCase(paramChar2);
    this.l = KeyEvent.normalizeMetaState(paramInt2);
    this.a.a(false);
    return this;
  }
  
  public void setShowAsAction(int paramInt)
  {
    switch (paramInt & 0x3)
    {
    default: 
      throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
    }
    this.z = paramInt;
    this.a.b(this);
  }
  
  public SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider paramActionProvider)
  {
    android.support.v4.view.ActionProvider localActionProvider = this.B;
    if (localActionProvider != null) {
      localActionProvider.reset();
    }
    this.A = null;
    this.B = paramActionProvider;
    this.a.a(true);
    paramActionProvider = this.B;
    if (paramActionProvider != null) {
      paramActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener()
      {
        public void onActionProviderVisibilityChanged(boolean paramAnonymousBoolean)
        {
          j.this.a.a(j.this);
        }
      });
    }
    return this;
  }
  
  public MenuItem setTitle(int paramInt)
  {
    return setTitle(this.a.e().getString(paramInt));
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence)
  {
    this.f = paramCharSequence;
    this.a.a(false);
    u localu = this.o;
    if (localu != null) {
      localu.setHeaderTitle(paramCharSequence);
    }
    return this;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    this.g = paramCharSequence;
    if (paramCharSequence == null) {
      paramCharSequence = this.f;
    }
    this.a.a(false);
    return this;
  }
  
  public SupportMenuItem setTooltipText(CharSequence paramCharSequence)
  {
    this.s = paramCharSequence;
    this.a.a(false);
    return this;
  }
  
  public MenuItem setVisible(boolean paramBoolean)
  {
    if (c(paramBoolean)) {
      this.a.a(this);
    }
    return this;
  }
  
  public String toString()
  {
    Object localObject = this.f;
    if (localObject != null) {
      localObject = ((CharSequence)localObject).toString();
    } else {
      localObject = null;
    }
    return (String)localObject;
  }
}


/* Location:              ~/android/support/v7/view/menu/j.class
 *
 * Reversed by:           J
 */