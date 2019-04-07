package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.d;
import android.support.v7.a.a.g;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

final class e
  extends m
  implements o, View.OnKeyListener, PopupWindow.OnDismissListener
{
  final Handler a;
  final List<a> b = new ArrayList();
  View c;
  boolean d;
  private final Context e;
  private final int f;
  private final int g;
  private final int h;
  private final boolean i;
  private final List<h> j = new LinkedList();
  private final ViewTreeObserver.OnGlobalLayoutListener k = new ViewTreeObserver.OnGlobalLayoutListener()
  {
    public void onGlobalLayout()
    {
      if ((e.this.isShowing()) && (e.this.b.size() > 0) && (!((e.a)e.this.b.get(0)).a.isModal()))
      {
        Object localObject = e.this.c;
        if ((localObject != null) && (((View)localObject).isShown())) {
          localObject = e.this.b.iterator();
        }
        while (((Iterator)localObject).hasNext())
        {
          ((e.a)((Iterator)localObject).next()).a.show();
          continue;
          e.this.dismiss();
        }
      }
    }
  };
  private final View.OnAttachStateChangeListener l = new View.OnAttachStateChangeListener()
  {
    public void onViewAttachedToWindow(View paramAnonymousView) {}
    
    public void onViewDetachedFromWindow(View paramAnonymousView)
    {
      if (e.a(e.this) != null)
      {
        if (!e.a(e.this).isAlive()) {
          e.a(e.this, paramAnonymousView.getViewTreeObserver());
        }
        e.a(e.this).removeGlobalOnLayoutListener(e.b(e.this));
      }
      paramAnonymousView.removeOnAttachStateChangeListener(this);
    }
  };
  private final MenuItemHoverListener m = new MenuItemHoverListener()
  {
    public void onItemHoverEnter(final h paramAnonymoush, final MenuItem paramAnonymousMenuItem)
    {
      Handler localHandler = e.this.a;
      final e.a locala = null;
      localHandler.removeCallbacksAndMessages(null);
      int i = e.this.b.size();
      for (int j = 0; j < i; j++) {
        if (paramAnonymoush == ((e.a)e.this.b.get(j)).b) {
          break label76;
        }
      }
      j = -1;
      label76:
      if (j == -1) {
        return;
      }
      j++;
      if (j < e.this.b.size()) {
        locala = (e.a)e.this.b.get(j);
      }
      paramAnonymousMenuItem = new Runnable()
      {
        public void run()
        {
          if (locala != null)
          {
            e.this.d = true;
            locala.b.b(false);
            e.this.d = false;
          }
          if ((paramAnonymousMenuItem.isEnabled()) && (paramAnonymousMenuItem.hasSubMenu())) {
            paramAnonymoush.a(paramAnonymousMenuItem, 4);
          }
        }
      };
      long l = SystemClock.uptimeMillis();
      e.this.a.postAtTime(paramAnonymousMenuItem, paramAnonymoush, l + 200L);
    }
    
    public void onItemHoverExit(h paramAnonymoush, MenuItem paramAnonymousMenuItem)
    {
      e.this.a.removeCallbacksAndMessages(paramAnonymoush);
    }
  };
  private int n = 0;
  private int o = 0;
  private View p;
  private int q;
  private boolean r;
  private boolean s;
  private int t;
  private int u;
  private boolean v;
  private boolean w;
  private o.a x;
  private ViewTreeObserver y;
  private PopupWindow.OnDismissListener z;
  
  public e(Context paramContext, View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.e = paramContext;
    this.p = paramView;
    this.g = paramInt1;
    this.h = paramInt2;
    this.i = paramBoolean;
    this.v = false;
    this.q = g();
    paramContext = paramContext.getResources();
    this.f = Math.max(paramContext.getDisplayMetrics().widthPixels / 2, paramContext.getDimensionPixelSize(a.d.abc_config_prefDialogWidth));
    this.a = new Handler();
  }
  
  private MenuItem a(h paramh1, h paramh2)
  {
    int i1 = paramh1.size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      MenuItem localMenuItem = paramh1.getItem(i2);
      if ((localMenuItem.hasSubMenu()) && (paramh2 == localMenuItem.getSubMenu())) {
        return localMenuItem;
      }
    }
    return null;
  }
  
  private View a(a parama, h paramh)
  {
    paramh = a(parama.b, paramh);
    if (paramh == null) {
      return null;
    }
    ListView localListView = parama.a();
    parama = localListView.getAdapter();
    boolean bool = parama instanceof HeaderViewListAdapter;
    int i1 = 0;
    int i2;
    if (bool)
    {
      parama = (HeaderViewListAdapter)parama;
      i2 = parama.getHeadersCount();
      parama = (g)parama.getWrappedAdapter();
    }
    else
    {
      parama = (g)parama;
      i2 = 0;
    }
    int i3 = parama.getCount();
    while (i1 < i3)
    {
      if (paramh == parama.a(i1)) {
        break label105;
      }
      i1++;
    }
    i1 = -1;
    label105:
    if (i1 == -1) {
      return null;
    }
    i1 = i1 + i2 - localListView.getFirstVisiblePosition();
    if ((i1 >= 0) && (i1 < localListView.getChildCount())) {
      return localListView.getChildAt(i1);
    }
    return null;
  }
  
  private void c(h paramh)
  {
    Object localObject1 = LayoutInflater.from(this.e);
    Object localObject2 = new g(paramh, (LayoutInflater)localObject1, this.i);
    if ((!isShowing()) && (this.v)) {
      ((g)localObject2).a(true);
    } else if (isShowing()) {
      ((g)localObject2).a(m.b(paramh));
    }
    int i1 = a((ListAdapter)localObject2, null, this.e, this.f);
    MenuPopupWindow localMenuPopupWindow = f();
    localMenuPopupWindow.setAdapter((ListAdapter)localObject2);
    localMenuPopupWindow.setContentWidth(i1);
    localMenuPopupWindow.setDropDownGravity(this.o);
    if (this.b.size() > 0)
    {
      localObject2 = this.b;
      localObject2 = (a)((List)localObject2).get(((List)localObject2).size() - 1);
      localObject3 = a((a)localObject2, paramh);
    }
    else
    {
      localObject2 = null;
      localObject3 = localObject2;
    }
    if (localObject3 != null)
    {
      localMenuPopupWindow.setTouchModal(false);
      localMenuPopupWindow.setEnterTransition(null);
      int i2 = d(i1);
      int i3;
      if (i2 == 1) {
        i3 = 1;
      } else {
        i3 = 0;
      }
      this.q = i2;
      int i4;
      if (Build.VERSION.SDK_INT >= 26)
      {
        localMenuPopupWindow.setAnchorView((View)localObject3);
        i2 = 0;
        i4 = 0;
      }
      else
      {
        int[] arrayOfInt1 = new int[2];
        this.p.getLocationOnScreen(arrayOfInt1);
        int[] arrayOfInt2 = new int[2];
        ((View)localObject3).getLocationOnScreen(arrayOfInt2);
        i4 = arrayOfInt2[0] - arrayOfInt1[0];
        i2 = arrayOfInt2[1] - arrayOfInt1[1];
      }
      if ((this.o & 0x5) == 5)
      {
        if (i3 != 0) {
          i3 = i4 + i1;
        } else {
          i3 = i4 - ((View)localObject3).getWidth();
        }
      }
      else if (i3 != 0) {
        i3 = i4 + ((View)localObject3).getWidth();
      } else {
        i3 = i4 - i1;
      }
      localMenuPopupWindow.setHorizontalOffset(i3);
      localMenuPopupWindow.setOverlapAnchor(true);
      localMenuPopupWindow.setVerticalOffset(i2);
    }
    else
    {
      if (this.r) {
        localMenuPopupWindow.setHorizontalOffset(this.t);
      }
      if (this.s) {
        localMenuPopupWindow.setVerticalOffset(this.u);
      }
      localMenuPopupWindow.setEpicenterBounds(e());
    }
    Object localObject3 = new a(localMenuPopupWindow, paramh, this.q);
    this.b.add(localObject3);
    localMenuPopupWindow.show();
    localObject3 = localMenuPopupWindow.getListView();
    ((ListView)localObject3).setOnKeyListener(this);
    if ((localObject2 == null) && (this.w) && (paramh.m() != null))
    {
      localObject2 = (FrameLayout)((LayoutInflater)localObject1).inflate(a.g.abc_popup_menu_header_item_layout, (ViewGroup)localObject3, false);
      localObject1 = (TextView)((FrameLayout)localObject2).findViewById(16908310);
      ((FrameLayout)localObject2).setEnabled(false);
      ((TextView)localObject1).setText(paramh.m());
      ((ListView)localObject3).addHeaderView((View)localObject2, null, false);
      localMenuPopupWindow.show();
    }
  }
  
  private int d(int paramInt)
  {
    Object localObject = this.b;
    localObject = ((a)((List)localObject).get(((List)localObject).size() - 1)).a();
    int[] arrayOfInt = new int[2];
    ((ListView)localObject).getLocationOnScreen(arrayOfInt);
    Rect localRect = new Rect();
    this.c.getWindowVisibleDisplayFrame(localRect);
    if (this.q == 1)
    {
      if (arrayOfInt[0] + ((ListView)localObject).getWidth() + paramInt > localRect.right) {
        return 0;
      }
      return 1;
    }
    if (arrayOfInt[0] - paramInt < 0) {
      return 1;
    }
    return 0;
  }
  
  private int d(h paramh)
  {
    int i1 = this.b.size();
    for (int i2 = 0; i2 < i1; i2++) {
      if (paramh == ((a)this.b.get(i2)).b) {
        return i2;
      }
    }
    return -1;
  }
  
  private MenuPopupWindow f()
  {
    MenuPopupWindow localMenuPopupWindow = new MenuPopupWindow(this.e, null, this.g, this.h);
    localMenuPopupWindow.setHoverListener(this.m);
    localMenuPopupWindow.setOnItemClickListener(this);
    localMenuPopupWindow.setOnDismissListener(this);
    localMenuPopupWindow.setAnchorView(this.p);
    localMenuPopupWindow.setDropDownGravity(this.o);
    localMenuPopupWindow.setModal(true);
    localMenuPopupWindow.setInputMethodMode(2);
    return localMenuPopupWindow;
  }
  
  private int g()
  {
    int i1 = ViewCompat.getLayoutDirection(this.p);
    int i2 = 1;
    if (i1 == 1) {
      i2 = 0;
    }
    return i2;
  }
  
  public void a(int paramInt)
  {
    if (this.n != paramInt)
    {
      this.n = paramInt;
      this.o = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this.p));
    }
  }
  
  public void a(Parcelable paramParcelable) {}
  
  public void a(h paramh)
  {
    paramh.a(this, this.e);
    if (isShowing()) {
      c(paramh);
    } else {
      this.j.add(paramh);
    }
  }
  
  public void a(h paramh, boolean paramBoolean)
  {
    int i1 = d(paramh);
    if (i1 < 0) {
      return;
    }
    int i2 = i1 + 1;
    if (i2 < this.b.size()) {
      ((a)this.b.get(i2)).b.b(false);
    }
    Object localObject = (a)this.b.remove(i1);
    ((a)localObject).b.b(this);
    if (this.d)
    {
      ((a)localObject).a.setExitTransition(null);
      ((a)localObject).a.setAnimationStyle(0);
    }
    ((a)localObject).a.dismiss();
    i2 = this.b.size();
    if (i2 > 0) {
      this.q = ((a)this.b.get(i2 - 1)).c;
    } else {
      this.q = g();
    }
    if (i2 == 0)
    {
      dismiss();
      localObject = this.x;
      if (localObject != null) {
        ((o.a)localObject).a(paramh, true);
      }
      paramh = this.y;
      if (paramh != null)
      {
        if (paramh.isAlive()) {
          this.y.removeGlobalOnLayoutListener(this.k);
        }
        this.y = null;
      }
      this.c.removeOnAttachStateChangeListener(this.l);
      this.z.onDismiss();
    }
    else if (paramBoolean)
    {
      ((a)this.b.get(0)).b.b(false);
    }
  }
  
  public void a(o.a parama)
  {
    this.x = parama;
  }
  
  public void a(View paramView)
  {
    if (this.p != paramView)
    {
      this.p = paramView;
      this.o = GravityCompat.getAbsoluteGravity(this.n, ViewCompat.getLayoutDirection(this.p));
    }
  }
  
  public void a(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.z = paramOnDismissListener;
  }
  
  public void a(boolean paramBoolean)
  {
    Iterator localIterator = this.b.iterator();
    while (localIterator.hasNext()) {
      a(((a)localIterator.next()).a().getAdapter()).notifyDataSetChanged();
    }
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean a(u paramu)
  {
    Object localObject = this.b.iterator();
    while (((Iterator)localObject).hasNext())
    {
      a locala = (a)((Iterator)localObject).next();
      if (paramu == locala.b)
      {
        locala.a().requestFocus();
        return true;
      }
    }
    if (paramu.hasVisibleItems())
    {
      a(paramu);
      localObject = this.x;
      if (localObject != null) {
        ((o.a)localObject).a(paramu);
      }
      return true;
    }
    return false;
  }
  
  public void b(int paramInt)
  {
    this.r = true;
    this.t = paramInt;
  }
  
  public void b(boolean paramBoolean)
  {
    this.v = paramBoolean;
  }
  
  public Parcelable c()
  {
    return null;
  }
  
  public void c(int paramInt)
  {
    this.s = true;
    this.u = paramInt;
  }
  
  public void c(boolean paramBoolean)
  {
    this.w = paramBoolean;
  }
  
  protected boolean d()
  {
    return false;
  }
  
  public void dismiss()
  {
    int i1 = this.b.size();
    if (i1 > 0)
    {
      a[] arrayOfa = (a[])this.b.toArray(new a[i1]);
      i1--;
      while (i1 >= 0)
      {
        a locala = arrayOfa[i1];
        if (locala.a.isShowing()) {
          locala.a.dismiss();
        }
        i1--;
      }
    }
  }
  
  public ListView getListView()
  {
    Object localObject;
    if (this.b.isEmpty())
    {
      localObject = null;
    }
    else
    {
      localObject = this.b;
      localObject = ((a)((List)localObject).get(((List)localObject).size() - 1)).a();
    }
    return (ListView)localObject;
  }
  
  public boolean isShowing()
  {
    int i1 = this.b.size();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i1 > 0)
    {
      bool2 = bool1;
      if (((a)this.b.get(0)).a.isShowing()) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public void onDismiss()
  {
    int i1 = this.b.size();
    for (int i2 = 0; i2 < i1; i2++)
    {
      locala = (a)this.b.get(i2);
      if (!locala.a.isShowing()) {
        break label52;
      }
    }
    a locala = null;
    label52:
    if (locala != null) {
      locala.b.b(false);
    }
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 1) && (paramInt == 82))
    {
      dismiss();
      return true;
    }
    return false;
  }
  
  public void show()
  {
    if (isShowing()) {
      return;
    }
    Iterator localIterator = this.j.iterator();
    while (localIterator.hasNext()) {
      c((h)localIterator.next());
    }
    this.j.clear();
    this.c = this.p;
    if (this.c != null)
    {
      int i1;
      if (this.y == null) {
        i1 = 1;
      } else {
        i1 = 0;
      }
      this.y = this.c.getViewTreeObserver();
      if (i1 != 0) {
        this.y.addOnGlobalLayoutListener(this.k);
      }
      this.c.addOnAttachStateChangeListener(this.l);
    }
  }
  
  private static class a
  {
    public final MenuPopupWindow a;
    public final h b;
    public final int c;
    
    public a(MenuPopupWindow paramMenuPopupWindow, h paramh, int paramInt)
    {
      this.a = paramMenuPopupWindow;
      this.b = paramh;
      this.c = paramInt;
    }
    
    public ListView a()
    {
      return this.a.getListView();
    }
  }
}


/* Location:              ~/android/support/v7/view/menu/e.class
 *
 * Reversed by:           J
 */