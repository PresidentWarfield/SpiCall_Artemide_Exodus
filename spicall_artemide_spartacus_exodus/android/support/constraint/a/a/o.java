package android.support.constraint.a.a;

import java.util.HashSet;
import java.util.Iterator;

public class o
{
  HashSet<o> h = new HashSet(2);
  int i = 0;
  
  public void a() {}
  
  public void a(o paramo)
  {
    this.h.add(paramo);
  }
  
  public void b()
  {
    this.i = 0;
    this.h.clear();
  }
  
  public void e()
  {
    this.i = 0;
    Iterator localIterator = this.h.iterator();
    while (localIterator.hasNext()) {
      ((o)localIterator.next()).e();
    }
  }
  
  public void f()
  {
    this.i = 1;
    Iterator localIterator = this.h.iterator();
    while (localIterator.hasNext()) {
      ((o)localIterator.next()).a();
    }
  }
  
  public boolean g()
  {
    int j = this.i;
    boolean bool = true;
    if (j != 1) {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/constraint/a/a/o.class
 *
 * Reversed by:           J
 */