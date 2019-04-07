package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.a.a.j;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.k;
import android.support.v7.widget.DrawableUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;

public class g
  extends MenuInflater
{
  static final Class<?>[] a = { Context.class };
  static final Class<?>[] b = a;
  final Object[] c;
  final Object[] d;
  Context e;
  private Object f;
  
  public g(Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
    this.c = new Object[] { paramContext };
    this.d = this.c;
  }
  
  private Object a(Object paramObject)
  {
    if ((paramObject instanceof Activity)) {
      return paramObject;
    }
    if ((paramObject instanceof ContextWrapper)) {
      return a(((ContextWrapper)paramObject).getBaseContext());
    }
    return paramObject;
  }
  
  private void a(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Menu paramMenu)
  {
    b localb = new b(paramMenu);
    int i = paramXmlPullParser.getEventType();
    int j;
    do
    {
      if (i == 2)
      {
        paramMenu = paramXmlPullParser.getName();
        if (paramMenu.equals("menu"))
        {
          j = paramXmlPullParser.next();
          break;
        }
        paramXmlPullParser = new StringBuilder();
        paramXmlPullParser.append("Expecting menu, got ");
        paramXmlPullParser.append(paramMenu);
        throw new RuntimeException(paramXmlPullParser.toString());
      }
      j = paramXmlPullParser.next();
      i = j;
    } while (j != 1);
    Object localObject = null;
    i = 0;
    int k = 0;
    int m = j;
    while (i == 0)
    {
      int n;
      switch (m)
      {
      default: 
        j = k;
        paramMenu = (Menu)localObject;
        n = i;
        break;
      case 3: 
        String str = paramXmlPullParser.getName();
        if ((k != 0) && (str.equals(localObject)))
        {
          paramMenu = null;
          j = 0;
          n = i;
        }
        else if (str.equals("group"))
        {
          localb.a();
          j = k;
          paramMenu = (Menu)localObject;
          n = i;
        }
        else if (str.equals("item"))
        {
          j = k;
          paramMenu = (Menu)localObject;
          n = i;
          if (!localb.d()) {
            if ((localb.a != null) && (localb.a.hasSubMenu()))
            {
              localb.c();
              j = k;
              paramMenu = (Menu)localObject;
              n = i;
            }
            else
            {
              localb.b();
              j = k;
              paramMenu = (Menu)localObject;
              n = i;
            }
          }
        }
        else
        {
          j = k;
          paramMenu = (Menu)localObject;
          n = i;
          if (str.equals("menu"))
          {
            n = 1;
            j = k;
            paramMenu = (Menu)localObject;
          }
        }
        break;
      case 2: 
        if (k != 0)
        {
          j = k;
          paramMenu = (Menu)localObject;
          n = i;
        }
        else
        {
          paramMenu = paramXmlPullParser.getName();
          if (paramMenu.equals("group"))
          {
            localb.a(paramAttributeSet);
            j = k;
            paramMenu = (Menu)localObject;
            n = i;
          }
          else if (paramMenu.equals("item"))
          {
            localb.b(paramAttributeSet);
            j = k;
            paramMenu = (Menu)localObject;
            n = i;
          }
          else if (paramMenu.equals("menu"))
          {
            a(paramXmlPullParser, paramAttributeSet, localb.c());
            j = k;
            paramMenu = (Menu)localObject;
            n = i;
          }
          else
          {
            j = 1;
            n = i;
          }
        }
        break;
      case 1: 
        throw new RuntimeException("Unexpected end of document");
      }
      m = paramXmlPullParser.next();
      k = j;
      localObject = paramMenu;
      i = n;
    }
  }
  
  Object a()
  {
    if (this.f == null) {
      this.f = a(this.e);
    }
    return this.f;
  }
  
  /* Error */
  public void inflate(int paramInt, Menu paramMenu)
  {
    // Byte code:
    //   0: aload_2
    //   1: instanceof 138
    //   4: ifne +10 -> 14
    //   7: aload_0
    //   8: iload_1
    //   9: aload_2
    //   10: invokespecial 140	android/view/MenuInflater:inflate	(ILandroid/view/Menu;)V
    //   13: return
    //   14: aconst_null
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aconst_null
    //   20: astore 5
    //   22: aload_0
    //   23: getfield 36	android/support/v7/view/g:e	Landroid/content/Context;
    //   26: invokevirtual 144	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   29: iload_1
    //   30: invokevirtual 150	android/content/res/Resources:getLayout	(I)Landroid/content/res/XmlResourceParser;
    //   33: astore 6
    //   35: aload 6
    //   37: astore 5
    //   39: aload 6
    //   41: astore_3
    //   42: aload 6
    //   44: astore 4
    //   46: aload_0
    //   47: aload 6
    //   49: aload 6
    //   51: invokestatic 156	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   54: aload_2
    //   55: invokespecial 125	android/support/v7/view/g:a	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/view/Menu;)V
    //   58: aload 6
    //   60: ifnull +10 -> 70
    //   63: aload 6
    //   65: invokeinterface 161 1 0
    //   70: return
    //   71: astore_2
    //   72: goto +55 -> 127
    //   75: astore_2
    //   76: aload_3
    //   77: astore 5
    //   79: new 163	android/view/InflateException
    //   82: astore 4
    //   84: aload_3
    //   85: astore 5
    //   87: aload 4
    //   89: ldc -91
    //   91: aload_2
    //   92: invokespecial 168	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: aload_3
    //   96: astore 5
    //   98: aload 4
    //   100: athrow
    //   101: astore_2
    //   102: aload 4
    //   104: astore 5
    //   106: new 163	android/view/InflateException
    //   109: astore_3
    //   110: aload 4
    //   112: astore 5
    //   114: aload_3
    //   115: ldc -91
    //   117: aload_2
    //   118: invokespecial 168	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   121: aload 4
    //   123: astore 5
    //   125: aload_3
    //   126: athrow
    //   127: aload 5
    //   129: ifnull +10 -> 139
    //   132: aload 5
    //   134: invokeinterface 161 1 0
    //   139: aload_2
    //   140: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	g
    //   0	141	1	paramInt	int
    //   0	141	2	paramMenu	Menu
    //   15	111	3	localObject1	Object
    //   17	105	4	localObject2	Object
    //   20	113	5	localObject3	Object
    //   33	31	6	localXmlResourceParser	android.content.res.XmlResourceParser
    // Exception table:
    //   from	to	target	type
    //   22	35	71	finally
    //   46	58	71	finally
    //   79	84	71	finally
    //   87	95	71	finally
    //   98	101	71	finally
    //   106	110	71	finally
    //   114	121	71	finally
    //   125	127	71	finally
    //   22	35	75	java/io/IOException
    //   46	58	75	java/io/IOException
    //   22	35	101	org/xmlpull/v1/XmlPullParserException
    //   46	58	101	org/xmlpull/v1/XmlPullParserException
  }
  
  private static class a
    implements MenuItem.OnMenuItemClickListener
  {
    private static final Class<?>[] a = { MenuItem.class };
    private Object b;
    private Method c;
    
    public a(Object paramObject, String paramString)
    {
      this.b = paramObject;
      Class localClass = paramObject.getClass();
      try
      {
        this.c = localClass.getMethod(paramString, a);
        return;
      }
      catch (Exception paramObject)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Couldn't resolve menu item onClick handler ");
        localStringBuilder.append(paramString);
        localStringBuilder.append(" in class ");
        localStringBuilder.append(localClass.getName());
        paramString = new InflateException(localStringBuilder.toString());
        paramString.initCause((Throwable)paramObject);
        throw paramString;
      }
    }
    
    public boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      try
      {
        if (this.c.getReturnType() == Boolean.TYPE) {
          return ((Boolean)this.c.invoke(this.b, new Object[] { paramMenuItem })).booleanValue();
        }
        this.c.invoke(this.b, new Object[] { paramMenuItem });
        return true;
      }
      catch (Exception paramMenuItem)
      {
        throw new RuntimeException(paramMenuItem);
      }
    }
  }
  
  private class b
  {
    private String A;
    private String B;
    private CharSequence C;
    private CharSequence D;
    private ColorStateList E = null;
    private PorterDuff.Mode F = null;
    ActionProvider a;
    private Menu c;
    private int d;
    private int e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;
    private CharSequence m;
    private CharSequence n;
    private int o;
    private char p;
    private int q;
    private char r;
    private int s;
    private int t;
    private boolean u;
    private boolean v;
    private boolean w;
    private int x;
    private int y;
    private String z;
    
    public b(Menu paramMenu)
    {
      this.c = paramMenu;
      a();
    }
    
    private char a(String paramString)
    {
      if (paramString == null) {
        return '\000';
      }
      return paramString.charAt(0);
    }
    
    private <T> T a(String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    {
      try
      {
        paramArrayOfClass = g.this.e.getClassLoader().loadClass(paramString).getConstructor(paramArrayOfClass);
        paramArrayOfClass.setAccessible(true);
        paramArrayOfClass = paramArrayOfClass.newInstance(paramArrayOfObject);
        return paramArrayOfClass;
      }
      catch (Exception paramArrayOfClass)
      {
        paramArrayOfObject = new StringBuilder();
        paramArrayOfObject.append("Cannot instantiate class: ");
        paramArrayOfObject.append(paramString);
        Log.w("SupportMenuInflater", paramArrayOfObject.toString(), paramArrayOfClass);
      }
      return null;
    }
    
    private void a(MenuItem paramMenuItem)
    {
      Object localObject = paramMenuItem.setChecked(this.u).setVisible(this.v).setEnabled(this.w);
      int i1 = this.t;
      int i2 = 0;
      if (i1 >= 1) {
        bool = true;
      } else {
        bool = false;
      }
      ((MenuItem)localObject).setCheckable(bool).setTitleCondensed(this.n).setIcon(this.o);
      i1 = this.x;
      if (i1 >= 0) {
        paramMenuItem.setShowAsAction(i1);
      }
      if (this.B != null) {
        if (!g.this.e.isRestricted()) {
          paramMenuItem.setOnMenuItemClickListener(new g.a(g.this.a(), this.B));
        } else {
          throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
        }
      }
      boolean bool = paramMenuItem instanceof j;
      if (bool) {
        localObject = (j)paramMenuItem;
      }
      if (this.t >= 2) {
        if (bool) {
          ((j)paramMenuItem).a(true);
        } else if ((paramMenuItem instanceof k)) {
          ((k)paramMenuItem).a(true);
        }
      }
      localObject = this.z;
      if (localObject != null)
      {
        paramMenuItem.setActionView((View)a((String)localObject, g.a, g.this.c));
        i2 = 1;
      }
      i1 = this.y;
      if (i1 > 0) {
        if (i2 == 0) {
          paramMenuItem.setActionView(i1);
        } else {
          Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
        }
      }
      localObject = this.a;
      if (localObject != null) {
        MenuItemCompat.setActionProvider(paramMenuItem, (ActionProvider)localObject);
      }
      MenuItemCompat.setContentDescription(paramMenuItem, this.C);
      MenuItemCompat.setTooltipText(paramMenuItem, this.D);
      MenuItemCompat.setAlphabeticShortcut(paramMenuItem, this.p, this.q);
      MenuItemCompat.setNumericShortcut(paramMenuItem, this.r, this.s);
      localObject = this.F;
      if (localObject != null) {
        MenuItemCompat.setIconTintMode(paramMenuItem, (PorterDuff.Mode)localObject);
      }
      localObject = this.E;
      if (localObject != null) {
        MenuItemCompat.setIconTintList(paramMenuItem, (ColorStateList)localObject);
      }
    }
    
    public void a()
    {
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = 0;
      this.h = true;
      this.i = true;
    }
    
    public void a(AttributeSet paramAttributeSet)
    {
      paramAttributeSet = g.this.e.obtainStyledAttributes(paramAttributeSet, a.j.MenuGroup);
      this.d = paramAttributeSet.getResourceId(a.j.MenuGroup_android_id, 0);
      this.e = paramAttributeSet.getInt(a.j.MenuGroup_android_menuCategory, 0);
      this.f = paramAttributeSet.getInt(a.j.MenuGroup_android_orderInCategory, 0);
      this.g = paramAttributeSet.getInt(a.j.MenuGroup_android_checkableBehavior, 0);
      this.h = paramAttributeSet.getBoolean(a.j.MenuGroup_android_visible, true);
      this.i = paramAttributeSet.getBoolean(a.j.MenuGroup_android_enabled, true);
      paramAttributeSet.recycle();
    }
    
    public void b()
    {
      this.j = true;
      a(this.c.add(this.d, this.k, this.l, this.m));
    }
    
    public void b(AttributeSet paramAttributeSet)
    {
      paramAttributeSet = g.this.e.obtainStyledAttributes(paramAttributeSet, a.j.MenuItem);
      this.k = paramAttributeSet.getResourceId(a.j.MenuItem_android_id, 0);
      this.l = (paramAttributeSet.getInt(a.j.MenuItem_android_menuCategory, this.e) & 0xFFFF0000 | paramAttributeSet.getInt(a.j.MenuItem_android_orderInCategory, this.f) & 0xFFFF);
      this.m = paramAttributeSet.getText(a.j.MenuItem_android_title);
      this.n = paramAttributeSet.getText(a.j.MenuItem_android_titleCondensed);
      this.o = paramAttributeSet.getResourceId(a.j.MenuItem_android_icon, 0);
      this.p = a(paramAttributeSet.getString(a.j.MenuItem_android_alphabeticShortcut));
      this.q = paramAttributeSet.getInt(a.j.MenuItem_alphabeticModifiers, 4096);
      this.r = a(paramAttributeSet.getString(a.j.MenuItem_android_numericShortcut));
      this.s = paramAttributeSet.getInt(a.j.MenuItem_numericModifiers, 4096);
      if (paramAttributeSet.hasValue(a.j.MenuItem_android_checkable)) {
        this.t = paramAttributeSet.getBoolean(a.j.MenuItem_android_checkable, false);
      } else {
        this.t = this.g;
      }
      this.u = paramAttributeSet.getBoolean(a.j.MenuItem_android_checked, false);
      this.v = paramAttributeSet.getBoolean(a.j.MenuItem_android_visible, this.h);
      this.w = paramAttributeSet.getBoolean(a.j.MenuItem_android_enabled, this.i);
      this.x = paramAttributeSet.getInt(a.j.MenuItem_showAsAction, -1);
      this.B = paramAttributeSet.getString(a.j.MenuItem_android_onClick);
      this.y = paramAttributeSet.getResourceId(a.j.MenuItem_actionLayout, 0);
      this.z = paramAttributeSet.getString(a.j.MenuItem_actionViewClass);
      this.A = paramAttributeSet.getString(a.j.MenuItem_actionProviderClass);
      int i1;
      if (this.A != null) {
        i1 = 1;
      } else {
        i1 = 0;
      }
      if ((i1 != 0) && (this.y == 0) && (this.z == null))
      {
        this.a = ((ActionProvider)a(this.A, g.b, g.this.d));
      }
      else
      {
        if (i1 != 0) {
          Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
        }
        this.a = null;
      }
      this.C = paramAttributeSet.getText(a.j.MenuItem_contentDescription);
      this.D = paramAttributeSet.getText(a.j.MenuItem_tooltipText);
      if (paramAttributeSet.hasValue(a.j.MenuItem_iconTintMode)) {
        this.F = DrawableUtils.parseTintMode(paramAttributeSet.getInt(a.j.MenuItem_iconTintMode, -1), this.F);
      } else {
        this.F = null;
      }
      if (paramAttributeSet.hasValue(a.j.MenuItem_iconTint)) {
        this.E = paramAttributeSet.getColorStateList(a.j.MenuItem_iconTint);
      } else {
        this.E = null;
      }
      paramAttributeSet.recycle();
      this.j = false;
    }
    
    public SubMenu c()
    {
      this.j = true;
      SubMenu localSubMenu = this.c.addSubMenu(this.d, this.k, this.l, this.m);
      a(localSubMenu.getItem());
      return localSubMenu;
    }
    
    public boolean d()
    {
      return this.j;
    }
  }
}


/* Location:              ~/android/support/v7/view/g.class
 *
 * Reversed by:           J
 */