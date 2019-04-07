package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v7.a.a.b;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class h
  implements SupportMenu
{
  private static final int[] d = { 1, 4, 5, 3, 2, 0 };
  CharSequence a;
  Drawable b;
  View c;
  private final Context e;
  private final Resources f;
  private boolean g;
  private boolean h;
  private a i;
  private ArrayList<j> j;
  private ArrayList<j> k;
  private boolean l;
  private ArrayList<j> m;
  private ArrayList<j> n;
  private boolean o;
  private int p = 0;
  private ContextMenu.ContextMenuInfo q;
  private boolean r = false;
  private boolean s = false;
  private boolean t = false;
  private boolean u = false;
  private boolean v = false;
  private ArrayList<j> w = new ArrayList();
  private CopyOnWriteArrayList<WeakReference<o>> x = new CopyOnWriteArrayList();
  private j y;
  private boolean z;
  
  public h(Context paramContext)
  {
    this.e = paramContext;
    this.f = paramContext.getResources();
    this.j = new ArrayList();
    this.k = new ArrayList();
    this.l = true;
    this.m = new ArrayList();
    this.n = new ArrayList();
    this.o = true;
    e(true);
  }
  
  private static int a(ArrayList<j> paramArrayList, int paramInt)
  {
    for (int i1 = paramArrayList.size() - 1; i1 >= 0; i1--) {
      if (((j)paramArrayList.get(i1)).b() <= paramInt) {
        return i1 + 1;
      }
    }
    return 0;
  }
  
  private j a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5)
  {
    return new j(this, paramInt1, paramInt2, paramInt3, paramInt4, paramCharSequence, paramInt5);
  }
  
  private void a(int paramInt1, CharSequence paramCharSequence, int paramInt2, Drawable paramDrawable, View paramView)
  {
    Resources localResources = d();
    if (paramView != null)
    {
      this.c = paramView;
      this.a = null;
      this.b = null;
    }
    else
    {
      if (paramInt1 > 0) {
        this.a = localResources.getText(paramInt1);
      } else if (paramCharSequence != null) {
        this.a = paramCharSequence;
      }
      if (paramInt2 > 0) {
        this.b = ContextCompat.getDrawable(e(), paramInt2);
      } else if (paramDrawable != null) {
        this.b = paramDrawable;
      }
      this.c = null;
    }
    a(false);
  }
  
  private void a(int paramInt, boolean paramBoolean)
  {
    if ((paramInt >= 0) && (paramInt < this.j.size()))
    {
      this.j.remove(paramInt);
      if (paramBoolean) {
        a(true);
      }
      return;
    }
  }
  
  private boolean a(u paramu, o paramo)
  {
    boolean bool1 = this.x.isEmpty();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (paramo != null) {
      bool2 = paramo.a(paramu);
    }
    Iterator localIterator = this.x.iterator();
    while (localIterator.hasNext())
    {
      paramo = (WeakReference)localIterator.next();
      o localo = (o)paramo.get();
      if (localo == null) {
        this.x.remove(paramo);
      } else if (!bool2) {
        bool2 = localo.a(paramu);
      }
    }
    return bool2;
  }
  
  private void d(boolean paramBoolean)
  {
    if (this.x.isEmpty()) {
      return;
    }
    g();
    Iterator localIterator = this.x.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      o localo = (o)localWeakReference.get();
      if (localo == null) {
        this.x.remove(localWeakReference);
      } else {
        localo.a(paramBoolean);
      }
    }
    h();
  }
  
  private void e(Bundle paramBundle)
  {
    if (this.x.isEmpty()) {
      return;
    }
    SparseArray localSparseArray = new SparseArray();
    Iterator localIterator = this.x.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      Object localObject = (o)localWeakReference.get();
      if (localObject == null)
      {
        this.x.remove(localWeakReference);
      }
      else
      {
        int i1 = ((o)localObject).b();
        if (i1 > 0)
        {
          localObject = ((o)localObject).c();
          if (localObject != null) {
            localSparseArray.put(i1, localObject);
          }
        }
      }
    }
    paramBundle.putSparseParcelableArray("android:menu:presenters", localSparseArray);
  }
  
  private void e(boolean paramBoolean)
  {
    boolean bool = true;
    if ((paramBoolean) && (this.f.getConfiguration().keyboard != 1) && (this.f.getBoolean(a.b.abc_config_showMenuShortcutsWhenKeyboardPresent))) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    }
    this.h = paramBoolean;
  }
  
  private static int f(int paramInt)
  {
    int i1 = (0xFFFF0000 & paramInt) >> 16;
    if (i1 >= 0)
    {
      int[] arrayOfInt = d;
      if (i1 < arrayOfInt.length) {
        return paramInt & 0xFFFF | arrayOfInt[i1] << 16;
      }
    }
    throw new IllegalArgumentException("order does not contain a valid category.");
  }
  
  private void f(Bundle paramBundle)
  {
    SparseArray localSparseArray = paramBundle.getSparseParcelableArray("android:menu:presenters");
    if ((localSparseArray != null) && (!this.x.isEmpty()))
    {
      Iterator localIterator = this.x.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (WeakReference)localIterator.next();
        paramBundle = (o)((WeakReference)localObject).get();
        if (paramBundle == null)
        {
          this.x.remove(localObject);
        }
        else
        {
          int i1 = paramBundle.b();
          if (i1 > 0)
          {
            localObject = (Parcelable)localSparseArray.get(i1);
            if (localObject != null) {
              paramBundle.a((Parcelable)localObject);
            }
          }
        }
      }
      return;
    }
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    int i1 = size();
    int i2 = paramInt2;
    if (paramInt2 < 0) {}
    for (i2 = 0; i2 < i1; i2++) {
      if (((j)this.j.get(i2)).getGroupId() == paramInt1) {
        return i2;
      }
    }
    return -1;
  }
  
  public h a(int paramInt)
  {
    this.p = paramInt;
    return this;
  }
  
  protected h a(Drawable paramDrawable)
  {
    a(0, null, 0, paramDrawable, null);
    return this;
  }
  
  protected h a(View paramView)
  {
    a(0, null, 0, null, paramView);
    return this;
  }
  
  protected h a(CharSequence paramCharSequence)
  {
    a(0, paramCharSequence, 0, null, null);
    return this;
  }
  
  j a(int paramInt, KeyEvent paramKeyEvent)
  {
    ArrayList localArrayList = this.w;
    localArrayList.clear();
    a(localArrayList, paramInt, paramKeyEvent);
    if (localArrayList.isEmpty()) {
      return null;
    }
    int i1 = paramKeyEvent.getMetaState();
    KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
    paramKeyEvent.getKeyData(localKeyData);
    int i2 = localArrayList.size();
    if (i2 == 1) {
      return (j)localArrayList.get(0);
    }
    boolean bool = b();
    for (int i3 = 0; i3 < i2; i3++)
    {
      paramKeyEvent = (j)localArrayList.get(i3);
      int i4;
      if (bool) {
        i4 = paramKeyEvent.getAlphabeticShortcut();
      } else {
        i4 = paramKeyEvent.getNumericShortcut();
      }
      if (((i4 == localKeyData.meta[0]) && ((i1 & 0x2) == 0)) || ((i4 == localKeyData.meta[2]) && ((i1 & 0x2) != 0)) || ((bool) && (i4 == 8) && (paramInt == 67))) {
        return paramKeyEvent;
      }
    }
    return null;
  }
  
  protected MenuItem a(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    int i1 = f(paramInt3);
    paramCharSequence = a(paramInt1, paramInt2, paramInt3, i1, paramCharSequence, this.p);
    Object localObject = this.q;
    if (localObject != null) {
      paramCharSequence.a((ContextMenu.ContextMenuInfo)localObject);
    }
    localObject = this.j;
    ((ArrayList)localObject).add(a((ArrayList)localObject, i1), paramCharSequence);
    a(true);
    return paramCharSequence;
  }
  
  protected String a()
  {
    return "android:menu:actionviewstates";
  }
  
  public void a(Bundle paramBundle)
  {
    e(paramBundle);
  }
  
  public void a(a parama)
  {
    this.i = parama;
  }
  
  void a(j paramj)
  {
    this.l = true;
    a(true);
  }
  
  public void a(o paramo)
  {
    a(paramo, this.e);
  }
  
  public void a(o paramo, Context paramContext)
  {
    this.x.add(new WeakReference(paramo));
    paramo.a(paramContext, this);
    this.o = true;
  }
  
  void a(MenuItem paramMenuItem)
  {
    int i1 = paramMenuItem.getGroupId();
    int i2 = this.j.size();
    g();
    for (int i3 = 0; i3 < i2; i3++)
    {
      j localj = (j)this.j.get(i3);
      if ((localj.getGroupId() == i1) && (localj.f()) && (localj.isCheckable()))
      {
        boolean bool;
        if (localj == paramMenuItem) {
          bool = true;
        } else {
          bool = false;
        }
        localj.b(bool);
      }
    }
    h();
  }
  
  void a(List<j> paramList, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = b();
    int i1 = paramKeyEvent.getModifiers();
    KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
    if ((!paramKeyEvent.getKeyData(localKeyData)) && (paramInt != 67)) {
      return;
    }
    int i2 = this.j.size();
    for (int i3 = 0; i3 < i2; i3++)
    {
      j localj = (j)this.j.get(i3);
      if (localj.hasSubMenu()) {
        ((h)localj.getSubMenu()).a(paramList, paramInt, paramKeyEvent);
      }
      int i4;
      if (bool) {
        i4 = localj.getAlphabeticShortcut();
      } else {
        i4 = localj.getNumericShortcut();
      }
      int i5;
      if (bool) {
        i5 = localj.getAlphabeticModifiers();
      } else {
        i5 = localj.getNumericModifiers();
      }
      if ((i1 & 0x1100F) == (i5 & 0x1100F)) {
        i5 = 1;
      } else {
        i5 = 0;
      }
      if ((i5 != 0) && (i4 != 0) && ((i4 == localKeyData.meta[0]) || (i4 == localKeyData.meta[2]) || ((bool) && (i4 == 8) && (paramInt == 67))) && (localj.isEnabled())) {
        paramList.add(localj);
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (!this.r)
    {
      if (paramBoolean)
      {
        this.l = true;
        this.o = true;
      }
      d(paramBoolean);
    }
    else
    {
      this.s = true;
      if (paramBoolean) {
        this.t = true;
      }
    }
  }
  
  boolean a(h paramh, MenuItem paramMenuItem)
  {
    a locala = this.i;
    boolean bool;
    if ((locala != null) && (locala.a(paramh, paramMenuItem))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean a(MenuItem paramMenuItem, int paramInt)
  {
    return a(paramMenuItem, null, paramInt);
  }
  
  public boolean a(MenuItem paramMenuItem, o paramo, int paramInt)
  {
    Object localObject = (j)paramMenuItem;
    if ((localObject != null) && (((j)localObject).isEnabled()))
    {
      boolean bool1 = ((j)localObject).a();
      paramMenuItem = ((j)localObject).getSupportActionProvider();
      int i1;
      if ((paramMenuItem != null) && (paramMenuItem.hasSubMenu())) {
        i1 = 1;
      } else {
        i1 = 0;
      }
      boolean bool2;
      if (((j)localObject).m())
      {
        bool1 |= ((j)localObject).expandActionView();
        bool2 = bool1;
        if (bool1)
        {
          b(true);
          bool2 = bool1;
        }
      }
      else if ((!((j)localObject).hasSubMenu()) && (i1 == 0))
      {
        bool2 = bool1;
        if ((paramInt & 0x1) == 0)
        {
          b(true);
          bool2 = bool1;
        }
      }
      else
      {
        if ((paramInt & 0x4) == 0) {
          b(false);
        }
        if (!((j)localObject).hasSubMenu()) {
          ((j)localObject).a(new u(e(), this, (j)localObject));
        }
        localObject = (u)((j)localObject).getSubMenu();
        if (i1 != 0) {
          paramMenuItem.onPrepareSubMenu((SubMenu)localObject);
        }
        bool1 |= a((u)localObject, paramo);
        bool2 = bool1;
        if (!bool1)
        {
          b(true);
          bool2 = bool1;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public MenuItem add(int paramInt)
  {
    return a(0, 0, 0, this.f.getString(paramInt));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return a(paramInt1, paramInt2, paramInt3, this.f.getString(paramInt4));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return a(paramInt1, paramInt2, paramInt3, paramCharSequence);
  }
  
  public MenuItem add(CharSequence paramCharSequence)
  {
    return a(0, 0, 0, paramCharSequence);
  }
  
  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    PackageManager localPackageManager = this.e.getPackageManager();
    int i1 = 0;
    List localList = localPackageManager.queryIntentActivityOptions(paramComponentName, paramArrayOfIntent, paramIntent, 0);
    int i2;
    if (localList != null) {
      i2 = localList.size();
    } else {
      i2 = 0;
    }
    int i3 = i1;
    if ((paramInt4 & 0x1) == 0) {
      removeGroup(paramInt1);
    }
    for (i3 = i1; i3 < i2; i3++)
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localList.get(i3);
      if (localResolveInfo.specificIndex < 0) {
        paramComponentName = paramIntent;
      } else {
        paramComponentName = paramArrayOfIntent[localResolveInfo.specificIndex];
      }
      paramComponentName = new Intent(paramComponentName);
      paramComponentName.setComponent(new ComponentName(localResolveInfo.activityInfo.applicationInfo.packageName, localResolveInfo.activityInfo.name));
      paramComponentName = add(paramInt1, paramInt2, paramInt3, localResolveInfo.loadLabel(localPackageManager)).setIcon(localResolveInfo.loadIcon(localPackageManager)).setIntent(paramComponentName);
      if ((paramArrayOfMenuItem != null) && (localResolveInfo.specificIndex >= 0)) {
        paramArrayOfMenuItem[localResolveInfo.specificIndex] = paramComponentName;
      }
    }
    return i2;
  }
  
  public SubMenu addSubMenu(int paramInt)
  {
    return addSubMenu(0, 0, 0, this.f.getString(paramInt));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addSubMenu(paramInt1, paramInt2, paramInt3, this.f.getString(paramInt4));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    j localj = (j)a(paramInt1, paramInt2, paramInt3, paramCharSequence);
    paramCharSequence = new u(this.e, this, localj);
    localj.a(paramCharSequence);
    return paramCharSequence;
  }
  
  public SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return addSubMenu(0, 0, 0, paramCharSequence);
  }
  
  public int b(int paramInt)
  {
    int i1 = size();
    for (int i2 = 0; i2 < i1; i2++) {
      if (((j)this.j.get(i2)).getItemId() == paramInt) {
        return i2;
      }
    }
    return -1;
  }
  
  public void b(Bundle paramBundle)
  {
    f(paramBundle);
  }
  
  void b(j paramj)
  {
    this.o = true;
    a(true);
  }
  
  public void b(o paramo)
  {
    Iterator localIterator = this.x.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      o localo = (o)localWeakReference.get();
      if ((localo == null) || (localo == paramo)) {
        this.x.remove(localWeakReference);
      }
    }
  }
  
  public final void b(boolean paramBoolean)
  {
    if (this.v) {
      return;
    }
    this.v = true;
    Iterator localIterator = this.x.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      o localo = (o)localWeakReference.get();
      if (localo == null) {
        this.x.remove(localWeakReference);
      } else {
        localo.a(this, paramBoolean);
      }
    }
    this.v = false;
  }
  
  boolean b()
  {
    return this.g;
  }
  
  public int c(int paramInt)
  {
    return a(paramInt, 0);
  }
  
  public void c(Bundle paramBundle)
  {
    int i1 = size();
    Object localObject1 = null;
    int i2 = 0;
    while (i2 < i1)
    {
      MenuItem localMenuItem = getItem(i2);
      View localView = localMenuItem.getActionView();
      Object localObject2 = localObject1;
      if (localView != null)
      {
        localObject2 = localObject1;
        if (localView.getId() != -1)
        {
          Object localObject3 = localObject1;
          if (localObject1 == null) {
            localObject3 = new SparseArray();
          }
          localView.saveHierarchyState((SparseArray)localObject3);
          localObject2 = localObject3;
          if (localMenuItem.isActionViewExpanded())
          {
            paramBundle.putInt("android:menu:expandedactionview", localMenuItem.getItemId());
            localObject2 = localObject3;
          }
        }
      }
      if (localMenuItem.hasSubMenu()) {
        ((u)localMenuItem.getSubMenu()).c(paramBundle);
      }
      i2++;
      localObject1 = localObject2;
    }
    if (localObject1 != null) {
      paramBundle.putSparseParcelableArray(a(), (SparseArray)localObject1);
    }
  }
  
  public void c(boolean paramBoolean)
  {
    this.z = paramBoolean;
  }
  
  public boolean c()
  {
    return this.h;
  }
  
  public boolean c(j paramj)
  {
    boolean bool1 = this.x.isEmpty();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    g();
    Iterator localIterator = this.x.iterator();
    do
    {
      o localo;
      for (;;)
      {
        bool1 = bool2;
        if (!localIterator.hasNext()) {
          break label97;
        }
        WeakReference localWeakReference = (WeakReference)localIterator.next();
        localo = (o)localWeakReference.get();
        if (localo != null) {
          break;
        }
        this.x.remove(localWeakReference);
      }
      bool1 = localo.a(this, paramj);
      bool2 = bool1;
    } while (!bool1);
    label97:
    h();
    if (bool1) {
      this.y = paramj;
    }
    return bool1;
  }
  
  public void clear()
  {
    j localj = this.y;
    if (localj != null) {
      d(localj);
    }
    this.j.clear();
    a(true);
  }
  
  public void clearHeader()
  {
    this.b = null;
    this.a = null;
    this.c = null;
    a(false);
  }
  
  public void close()
  {
    b(true);
  }
  
  Resources d()
  {
    return this.f;
  }
  
  protected h d(int paramInt)
  {
    a(paramInt, null, 0, null, null);
    return this;
  }
  
  public void d(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    SparseArray localSparseArray = paramBundle.getSparseParcelableArray(a());
    int i1 = size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      MenuItem localMenuItem = getItem(i2);
      View localView = localMenuItem.getActionView();
      if ((localView != null) && (localView.getId() != -1)) {
        localView.restoreHierarchyState(localSparseArray);
      }
      if (localMenuItem.hasSubMenu()) {
        ((u)localMenuItem.getSubMenu()).d(paramBundle);
      }
    }
    i2 = paramBundle.getInt("android:menu:expandedactionview");
    if (i2 > 0)
    {
      paramBundle = findItem(i2);
      if (paramBundle != null) {
        paramBundle.expandActionView();
      }
    }
  }
  
  public boolean d(j paramj)
  {
    boolean bool1 = this.x.isEmpty();
    boolean bool2 = false;
    if ((!bool1) && (this.y == paramj))
    {
      g();
      Iterator localIterator = this.x.iterator();
      do
      {
        o localo;
        for (;;)
        {
          bool1 = bool2;
          if (!localIterator.hasNext()) {
            break label106;
          }
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localo = (o)localWeakReference.get();
          if (localo != null) {
            break;
          }
          this.x.remove(localWeakReference);
        }
        bool1 = localo.b(this, paramj);
        bool2 = bool1;
      } while (!bool1);
      label106:
      h();
      if (bool1) {
        this.y = null;
      }
      return bool1;
    }
    return false;
  }
  
  public Context e()
  {
    return this.e;
  }
  
  protected h e(int paramInt)
  {
    a(0, null, paramInt, null, null);
    return this;
  }
  
  public void f()
  {
    a locala = this.i;
    if (locala != null) {
      locala.a(this);
    }
  }
  
  public MenuItem findItem(int paramInt)
  {
    int i1 = size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      Object localObject = (j)this.j.get(i2);
      if (((j)localObject).getItemId() == paramInt) {
        return (MenuItem)localObject;
      }
      if (((j)localObject).hasSubMenu())
      {
        localObject = ((j)localObject).getSubMenu().findItem(paramInt);
        if (localObject != null) {
          return (MenuItem)localObject;
        }
      }
    }
    return null;
  }
  
  public void g()
  {
    if (!this.r)
    {
      this.r = true;
      this.s = false;
      this.t = false;
    }
  }
  
  public MenuItem getItem(int paramInt)
  {
    return (MenuItem)this.j.get(paramInt);
  }
  
  public void h()
  {
    this.r = false;
    if (this.s)
    {
      this.s = false;
      a(this.t);
    }
  }
  
  public boolean hasVisibleItems()
  {
    if (this.z) {
      return true;
    }
    int i1 = size();
    for (int i2 = 0; i2 < i1; i2++) {
      if (((j)this.j.get(i2)).isVisible()) {
        return true;
      }
    }
    return false;
  }
  
  public ArrayList<j> i()
  {
    if (!this.l) {
      return this.k;
    }
    this.k.clear();
    int i1 = this.j.size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      j localj = (j)this.j.get(i2);
      if (localj.isVisible()) {
        this.k.add(localj);
      }
    }
    this.l = false;
    this.o = true;
    return this.k;
  }
  
  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool;
    if (a(paramInt, paramKeyEvent) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void j()
  {
    ArrayList localArrayList = i();
    if (!this.o) {
      return;
    }
    Iterator localIterator = this.x.iterator();
    int i1 = 0;
    Object localObject;
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      localObject = (o)localWeakReference.get();
      if (localObject == null) {
        this.x.remove(localWeakReference);
      } else {
        i1 |= ((o)localObject).a();
      }
    }
    if (i1 != 0)
    {
      this.m.clear();
      this.n.clear();
      int i2 = localArrayList.size();
      for (i1 = 0; i1 < i2; i1++)
      {
        localObject = (j)localArrayList.get(i1);
        if (((j)localObject).i()) {
          this.m.add(localObject);
        } else {
          this.n.add(localObject);
        }
      }
    }
    this.m.clear();
    this.n.clear();
    this.n.addAll(i());
    this.o = false;
  }
  
  public ArrayList<j> k()
  {
    j();
    return this.m;
  }
  
  public ArrayList<j> l()
  {
    j();
    return this.n;
  }
  
  public CharSequence m()
  {
    return this.a;
  }
  
  public Drawable n()
  {
    return this.b;
  }
  
  public View o()
  {
    return this.c;
  }
  
  public h p()
  {
    return this;
  }
  
  public boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return a(findItem(paramInt1), paramInt2);
  }
  
  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    paramKeyEvent = a(paramInt1, paramKeyEvent);
    boolean bool;
    if (paramKeyEvent != null) {
      bool = a(paramKeyEvent, paramInt2);
    } else {
      bool = false;
    }
    if ((paramInt2 & 0x2) != 0) {
      b(true);
    }
    return bool;
  }
  
  boolean q()
  {
    return this.u;
  }
  
  public j r()
  {
    return this.y;
  }
  
  public void removeGroup(int paramInt)
  {
    int i1 = c(paramInt);
    if (i1 >= 0)
    {
      int i2 = this.j.size();
      for (int i3 = 0; (i3 < i2 - i1) && (((j)this.j.get(i1)).getGroupId() == paramInt); i3++) {
        a(i1, false);
      }
      a(true);
    }
  }
  
  public void removeItem(int paramInt)
  {
    a(b(paramInt), true);
  }
  
  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i1 = this.j.size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      j localj = (j)this.j.get(i2);
      if (localj.getGroupId() == paramInt)
      {
        localj.a(paramBoolean2);
        localj.setCheckable(paramBoolean1);
      }
    }
  }
  
  public void setGroupEnabled(int paramInt, boolean paramBoolean)
  {
    int i1 = this.j.size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      j localj = (j)this.j.get(i2);
      if (localj.getGroupId() == paramInt) {
        localj.setEnabled(paramBoolean);
      }
    }
  }
  
  public void setGroupVisible(int paramInt, boolean paramBoolean)
  {
    int i1 = this.j.size();
    int i2 = 0;
    int i4;
    for (int i3 = 0; i2 < i1; i3 = i4)
    {
      j localj = (j)this.j.get(i2);
      i4 = i3;
      if (localj.getGroupId() == paramInt)
      {
        i4 = i3;
        if (localj.c(paramBoolean)) {
          i4 = 1;
        }
      }
      i2++;
    }
    if (i3 != 0) {
      a(true);
    }
  }
  
  public void setQwertyMode(boolean paramBoolean)
  {
    this.g = paramBoolean;
    a(false);
  }
  
  public int size()
  {
    return this.j.size();
  }
  
  public static abstract interface a
  {
    public abstract void a(h paramh);
    
    public abstract boolean a(h paramh, MenuItem paramMenuItem);
  }
  
  public static abstract interface b
  {
    public abstract boolean invokeItem(j paramj);
  }
}


/* Location:              ~/android/support/v7/view/menu/h.class
 *
 * Reversed by:           J
 */