package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.a.a.a;
import android.support.v7.view.b;
import android.support.v7.view.i;
import android.support.v7.widget.TintTypedArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

abstract class h
  extends g
{
  private static boolean m;
  private static final boolean n;
  private static final int[] o = { 16842836 };
  final Context a;
  final Window b;
  final Window.Callback c;
  final Window.Callback d;
  final f e;
  a f;
  MenuInflater g;
  boolean h;
  boolean i;
  boolean j;
  boolean k;
  boolean l;
  private CharSequence p;
  private boolean q;
  private boolean r;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    }
    n = bool;
    if ((n) && (!m))
    {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
      {
        private boolean a(Throwable paramAnonymousThrowable)
        {
          boolean bool1 = paramAnonymousThrowable instanceof Resources.NotFoundException;
          boolean bool2 = false;
          if (bool1)
          {
            paramAnonymousThrowable = paramAnonymousThrowable.getMessage();
            bool1 = bool2;
            if (paramAnonymousThrowable != null) {
              if (!paramAnonymousThrowable.contains("drawable"))
              {
                bool1 = bool2;
                if (!paramAnonymousThrowable.contains("Drawable")) {}
              }
              else
              {
                bool1 = true;
              }
            }
            return bool1;
          }
          return false;
        }
        
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          if (a(paramAnonymousThrowable))
          {
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramAnonymousThrowable.getMessage());
            ((StringBuilder)localObject).append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            localObject = new Resources.NotFoundException(((StringBuilder)localObject).toString());
            ((Throwable)localObject).initCause(paramAnonymousThrowable.getCause());
            ((Throwable)localObject).setStackTrace(paramAnonymousThrowable.getStackTrace());
            this.a.uncaughtException(paramAnonymousThread, (Throwable)localObject);
          }
          else
          {
            this.a.uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
          }
        }
      });
      m = true;
    }
  }
  
  h(Context paramContext, Window paramWindow, f paramf)
  {
    this.a = paramContext;
    this.b = paramWindow;
    this.e = paramf;
    this.c = this.b.getCallback();
    paramWindow = this.c;
    if (!(paramWindow instanceof b))
    {
      this.d = a(paramWindow);
      this.b.setCallback(this.d);
      paramContext = TintTypedArray.obtainStyledAttributes(paramContext, null, o);
      paramWindow = paramContext.getDrawableIfKnown(0);
      if (paramWindow != null) {
        this.b.setBackgroundDrawable(paramWindow);
      }
      paramContext.recycle();
      return;
    }
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  public a a()
  {
    m();
    return this.f;
  }
  
  abstract b a(android.support.v7.view.b.a parama);
  
  Window.Callback a(Window.Callback paramCallback)
  {
    return new b(paramCallback);
  }
  
  abstract void a(int paramInt, Menu paramMenu);
  
  public final void a(CharSequence paramCharSequence)
  {
    this.p = paramCharSequence;
    b(paramCharSequence);
  }
  
  abstract boolean a(int paramInt, KeyEvent paramKeyEvent);
  
  abstract boolean a(KeyEvent paramKeyEvent);
  
  public MenuInflater b()
  {
    if (this.g == null)
    {
      m();
      Object localObject = this.f;
      if (localObject != null) {
        localObject = ((a)localObject).b();
      } else {
        localObject = this.a;
      }
      this.g = new android.support.v7.view.g((Context)localObject);
    }
    return this.g;
  }
  
  abstract void b(CharSequence paramCharSequence);
  
  abstract boolean b(int paramInt, Menu paramMenu);
  
  public void c()
  {
    this.q = true;
  }
  
  public void c(Bundle paramBundle) {}
  
  public void d()
  {
    this.q = false;
  }
  
  public void g()
  {
    this.r = true;
  }
  
  public final b.a h()
  {
    return new a();
  }
  
  public boolean j()
  {
    return false;
  }
  
  abstract void m();
  
  final a n()
  {
    return this.f;
  }
  
  final Context o()
  {
    Object localObject1 = a();
    if (localObject1 != null) {
      localObject1 = ((a)localObject1).b();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = this.a;
    }
    return (Context)localObject2;
  }
  
  public boolean p()
  {
    return false;
  }
  
  final boolean q()
  {
    return this.r;
  }
  
  final Window.Callback r()
  {
    return this.b.getCallback();
  }
  
  final CharSequence s()
  {
    Window.Callback localCallback = this.c;
    if ((localCallback instanceof Activity)) {
      return ((Activity)localCallback).getTitle();
    }
    return this.p;
  }
  
  private class a
    implements b.a
  {
    a() {}
    
    public Drawable a()
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(b(), null, new int[] { a.a.homeAsUpIndicator });
      Drawable localDrawable = localTintTypedArray.getDrawable(0);
      localTintTypedArray.recycle();
      return localDrawable;
    }
    
    public void a(int paramInt)
    {
      a locala = h.this.a();
      if (locala != null) {
        locala.a(paramInt);
      }
    }
    
    public void a(Drawable paramDrawable, int paramInt)
    {
      a locala = h.this.a();
      if (locala != null)
      {
        locala.a(paramDrawable);
        locala.a(paramInt);
      }
    }
    
    public Context b()
    {
      return h.this.o();
    }
    
    public boolean c()
    {
      a locala = h.this.a();
      boolean bool;
      if ((locala != null) && ((locala.a() & 0x4) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  class b
    extends i
  {
    b(Window.Callback paramCallback)
    {
      super();
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!h.this.a(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!super.dispatchKeyShortcutEvent(paramKeyEvent)) && (!h.this.a(paramKeyEvent.getKeyCode(), paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void onContentChanged() {}
    
    public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
    {
      if ((paramInt == 0) && (!(paramMenu instanceof android.support.v7.view.menu.h))) {
        return false;
      }
      return super.onCreatePanelMenu(paramInt, paramMenu);
    }
    
    public boolean onMenuOpened(int paramInt, Menu paramMenu)
    {
      super.onMenuOpened(paramInt, paramMenu);
      h.this.b(paramInt, paramMenu);
      return true;
    }
    
    public void onPanelClosed(int paramInt, Menu paramMenu)
    {
      super.onPanelClosed(paramInt, paramMenu);
      h.this.a(paramInt, paramMenu);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      android.support.v7.view.menu.h localh;
      if ((paramMenu instanceof android.support.v7.view.menu.h)) {
        localh = (android.support.v7.view.menu.h)paramMenu;
      } else {
        localh = null;
      }
      if ((paramInt == 0) && (localh == null)) {
        return false;
      }
      if (localh != null) {
        localh.c(true);
      }
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if (localh != null) {
        localh.c(false);
      }
      return bool;
    }
  }
}


/* Location:              ~/android/support/v7/app/h.class
 *
 * Reversed by:           J
 */