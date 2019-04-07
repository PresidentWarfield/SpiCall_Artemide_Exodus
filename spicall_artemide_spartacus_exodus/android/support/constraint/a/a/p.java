package android.support.constraint.a.a;

import java.util.ArrayList;

public class p
{
  private int a;
  private int b;
  private int c;
  private int d;
  private ArrayList<a> e = new ArrayList();
  
  public p(f paramf)
  {
    this.a = paramf.n();
    this.b = paramf.o();
    this.c = paramf.p();
    this.d = paramf.r();
    paramf = paramf.C();
    int i = paramf.size();
    for (int j = 0; j < i; j++)
    {
      e locale = (e)paramf.get(j);
      this.e.add(new a(locale));
    }
  }
  
  public void a(f paramf)
  {
    this.a = paramf.n();
    this.b = paramf.o();
    this.c = paramf.p();
    this.d = paramf.r();
    int i = this.e.size();
    for (int j = 0; j < i; j++) {
      ((a)this.e.get(j)).a(paramf);
    }
  }
  
  public void b(f paramf)
  {
    paramf.h(this.a);
    paramf.i(this.b);
    paramf.j(this.c);
    paramf.k(this.d);
    int i = this.e.size();
    for (int j = 0; j < i; j++) {
      ((a)this.e.get(j)).b(paramf);
    }
  }
  
  static class a
  {
    private e a;
    private e b;
    private int c;
    private e.b d;
    private int e;
    
    public a(e parame)
    {
      this.a = parame;
      this.b = parame.g();
      this.c = parame.e();
      this.d = parame.f();
      this.e = parame.h();
    }
    
    public void a(f paramf)
    {
      this.a = paramf.a(this.a.d());
      paramf = this.a;
      if (paramf != null)
      {
        this.b = paramf.g();
        this.c = this.a.e();
        this.d = this.a.f();
        this.e = this.a.h();
      }
      else
      {
        this.b = null;
        this.c = 0;
        this.d = e.b.b;
        this.e = 0;
      }
    }
    
    public void b(f paramf)
    {
      paramf.a(this.a.d()).a(this.b, this.c, this.d, this.e);
    }
  }
}


/* Location:              ~/android/support/constraint/a/a/p.class
 *
 * Reversed by:           J
 */