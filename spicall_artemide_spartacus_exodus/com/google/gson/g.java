package com.google.gson;

import com.google.gson.b.a.n;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class g
{
  private com.google.gson.b.d a = com.google.gson.b.d.a;
  private r b = r.a;
  private e c = d.a;
  private final Map<Type, h<?>> d = new HashMap();
  private final List<t> e = new ArrayList();
  private final List<t> f = new ArrayList();
  private boolean g = false;
  private String h;
  private int i = 2;
  private int j = 2;
  private boolean k = false;
  private boolean l = false;
  private boolean m = true;
  private boolean n = false;
  private boolean o = false;
  private boolean p = false;
  
  private void a(String paramString, int paramInt1, int paramInt2, List<t> paramList)
  {
    a locala1;
    a locala2;
    if ((paramString != null) && (!"".equals(paramString.trim())))
    {
      locala1 = new a(java.util.Date.class, paramString);
      locala2 = new a(Timestamp.class, paramString);
      paramString = new a(java.sql.Date.class, paramString);
    }
    else
    {
      if ((paramInt1 == 2) || (paramInt2 == 2)) {
        return;
      }
      locala1 = new a(java.util.Date.class, paramInt1, paramInt2);
      locala2 = new a(Timestamp.class, paramInt1, paramInt2);
      paramString = new a(java.sql.Date.class, paramInt1, paramInt2);
    }
    paramList.add(n.a(java.util.Date.class, locala1));
    paramList.add(n.a(Timestamp.class, locala2));
    paramList.add(n.a(java.sql.Date.class, paramString));
    return;
  }
  
  public g a()
  {
    this.m = false;
    return this;
  }
  
  public g a(double paramDouble)
  {
    this.a = this.a.a(paramDouble);
    return this;
  }
  
  public f b()
  {
    ArrayList localArrayList = new ArrayList(this.e.size() + this.f.size() + 3);
    localArrayList.addAll(this.e);
    Collections.reverse(localArrayList);
    Collections.reverse(this.f);
    localArrayList.addAll(this.f);
    a(this.h, this.i, this.j, localArrayList);
    return new f(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.p, this.l, this.b, localArrayList);
  }
}


/* Location:              ~/com/google/gson/g.class
 *
 * Reversed by:           J
 */