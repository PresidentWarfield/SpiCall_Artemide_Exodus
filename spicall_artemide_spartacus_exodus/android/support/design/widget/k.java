package android.support.design.widget;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

class k
{
  private static k a;
  private final Object b = new Object();
  private final Handler c = new Handler(Looper.getMainLooper(), new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what != 0) {
        return false;
      }
      k.this.a((k.b)paramAnonymousMessage.obj);
      return true;
    }
  });
  private b d;
  private b e;
  
  static k a()
  {
    if (a == null) {
      a = new k();
    }
    return a;
  }
  
  private boolean a(b paramb, int paramInt)
  {
    a locala = (a)paramb.a.get();
    if (locala != null)
    {
      this.c.removeCallbacksAndMessages(paramb);
      locala.a(paramInt);
      return true;
    }
    return false;
  }
  
  private void b()
  {
    Object localObject = this.e;
    if (localObject != null)
    {
      this.d = ((b)localObject);
      this.e = null;
      localObject = (a)this.d.a.get();
      if (localObject != null) {
        ((a)localObject).a();
      } else {
        this.d = null;
      }
    }
  }
  
  private void b(b paramb)
  {
    if (paramb.b == -2) {
      return;
    }
    int i = 2750;
    if (paramb.b > 0) {
      i = paramb.b;
    } else if (paramb.b == -1) {
      i = 1500;
    }
    this.c.removeCallbacksAndMessages(paramb);
    Handler localHandler = this.c;
    localHandler.sendMessageDelayed(Message.obtain(localHandler, 0, paramb), i);
  }
  
  private boolean f(a parama)
  {
    b localb = this.d;
    boolean bool;
    if ((localb != null) && (localb.a(parama))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean g(a parama)
  {
    b localb = this.e;
    boolean bool;
    if ((localb != null) && (localb.a(parama))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void a(a parama)
  {
    synchronized (this.b)
    {
      if (f(parama))
      {
        this.d = null;
        if (this.e != null) {
          b();
        }
      }
      return;
    }
  }
  
  public void a(a parama, int paramInt)
  {
    synchronized (this.b)
    {
      if (f(parama)) {
        a(this.d, paramInt);
      } else if (g(parama)) {
        a(this.e, paramInt);
      }
      return;
    }
  }
  
  void a(b paramb)
  {
    synchronized (this.b)
    {
      if ((this.d == paramb) || (this.e == paramb)) {
        a(paramb, 2);
      }
      return;
    }
  }
  
  public void b(a parama)
  {
    synchronized (this.b)
    {
      if (f(parama)) {
        b(this.d);
      }
      return;
    }
  }
  
  public void c(a parama)
  {
    synchronized (this.b)
    {
      if ((f(parama)) && (!this.d.c))
      {
        this.d.c = true;
        this.c.removeCallbacksAndMessages(this.d);
      }
      return;
    }
  }
  
  public void d(a parama)
  {
    synchronized (this.b)
    {
      if ((f(parama)) && (this.d.c))
      {
        this.d.c = false;
        b(this.d);
      }
      return;
    }
  }
  
  public boolean e(a parama)
  {
    synchronized (this.b)
    {
      boolean bool;
      if ((!f(parama)) && (!g(parama))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  static abstract interface a
  {
    public abstract void a();
    
    public abstract void a(int paramInt);
  }
  
  private static class b
  {
    final WeakReference<k.a> a;
    int b;
    boolean c;
    
    boolean a(k.a parama)
    {
      boolean bool;
      if ((parama != null) && (this.a.get() == parama)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              ~/android/support/design/widget/k.class
 *
 * Reversed by:           J
 */