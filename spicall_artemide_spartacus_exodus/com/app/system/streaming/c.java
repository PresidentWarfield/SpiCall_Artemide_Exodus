package com.app.system.streaming;

import android.content.Context;
import android.preference.PreferenceManager;

public class c
{
  private static volatile c n;
  private com.app.system.streaming.h.c a = com.app.system.streaming.h.c.a;
  private com.app.system.streaming.a.c b = com.app.system.streaming.a.c.a;
  private Context c;
  private int d = 2;
  private int e = 3;
  private int f = 0;
  private int g = 64;
  private int h = 0;
  private boolean i = false;
  private com.app.system.streaming.b.b j = null;
  private String k = null;
  private String l = null;
  private b.a m = null;
  
  public static final c a()
  {
    if (n == null) {
      try
      {
        if (n == null)
        {
          c localc = new com/app/system/streaming/c;
          localc.<init>();
          n = localc;
        }
      }
      finally {}
    }
    return n;
  }
  
  public c a(int paramInt)
  {
    this.e = paramInt;
    return this;
  }
  
  public c a(Context paramContext)
  {
    this.c = paramContext;
    return this;
  }
  
  public c a(com.app.system.streaming.a.c paramc)
  {
    this.b = paramc.a();
    return this;
  }
  
  public c a(b.a parama)
  {
    this.m = parama;
    return this;
  }
  
  public c a(com.app.system.streaming.b.b paramb)
  {
    this.j = paramb;
    return this;
  }
  
  public c a(com.app.system.streaming.h.c paramc)
  {
    this.a = paramc.a();
    return this;
  }
  
  public c a(String paramString)
  {
    this.l = paramString;
    return this;
  }
  
  public c a(boolean paramBoolean)
  {
    this.i = paramBoolean;
    return this;
  }
  
  public b b()
  {
    b localb = new b();
    localb.a(this.k);
    localb.b(this.l);
    localb.a(this.g);
    localb.a(this.m);
    int i1 = this.e;
    Object localObject1;
    Object localObject2;
    if (i1 != 3)
    {
      if (i1 == 5)
      {
        localObject1 = new com.app.system.streaming.a.a();
        localb.a((com.app.system.streaming.a.d)localObject1);
        localObject2 = this.c;
        if (localObject2 != null) {
          ((com.app.system.streaming.a.a)localObject1).a(PreferenceManager.getDefaultSharedPreferences((Context)localObject2));
        }
      }
    }
    else {
      localb.a(new com.app.system.streaming.a.b());
    }
    switch (this.d)
    {
    default: 
      break;
    case 2: 
      localb.a(new com.app.system.streaming.h.a(this.f));
      break;
    case 1: 
      localObject2 = new com.app.system.streaming.h.b(this.f);
      localObject1 = this.c;
      if (localObject1 != null) {
        ((com.app.system.streaming.h.b)localObject2).a(PreferenceManager.getDefaultSharedPreferences((Context)localObject1));
      }
      localb.a((com.app.system.streaming.h.d)localObject2);
    }
    if (localb.d() != null)
    {
      localObject2 = localb.d();
      ((com.app.system.streaming.h.d)localObject2).a(this.i);
      ((com.app.system.streaming.h.d)localObject2).a(this.a);
      ((com.app.system.streaming.h.d)localObject2).a(this.j);
      ((com.app.system.streaming.h.d)localObject2).d(this.h);
      ((com.app.system.streaming.h.d)localObject2).a(5006);
    }
    if (localb.c() != null)
    {
      localObject2 = localb.c();
      ((com.app.system.streaming.a.d)localObject2).a(this.b);
      ((com.app.system.streaming.a.d)localObject2).a(5004);
    }
    return localb;
  }
  
  public c b(int paramInt)
  {
    this.d = paramInt;
    return this;
  }
  
  public c b(String paramString)
  {
    this.k = paramString;
    return this;
  }
  
  public c c()
  {
    return new c().a(this.l).b(this.k).a(this.j).e(this.h).a(this.a).b(this.d).a(this.i).c(this.f).d(this.g).a(this.e).a(this.b).a(this.c).a(this.m);
  }
  
  public c c(int paramInt)
  {
    this.f = paramInt;
    return this;
  }
  
  public c d(int paramInt)
  {
    this.g = paramInt;
    return this;
  }
  
  public c e(int paramInt)
  {
    this.h = paramInt;
    return this;
  }
}


/* Location:              ~/com/app/system/streaming/c.class
 *
 * Reversed by:           J
 */