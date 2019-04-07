package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.a.a;

public class k
{
  private List<String> a = new LinkedList();
  private ListIterator<String> b = this.a.listIterator();
  private final g c;
  private final boolean d;
  
  k(g paramg, d paramd)
  {
    this.c = paramg;
    if (paramd != null) {
      this.d = paramd.h();
    } else {
      this.d = false;
    }
  }
  
  private void b(InputStream paramInputStream, String paramString)
  {
    paramString = new BufferedReader(new InputStreamReader(paramInputStream, a.a(paramString)));
    for (paramInputStream = this.c.a(paramString); paramInputStream != null; paramInputStream = this.c.a(paramString)) {
      this.a.add(paramInputStream);
    }
    paramString.close();
  }
  
  public void a(InputStream paramInputStream, String paramString)
  {
    this.a = new LinkedList();
    b(paramInputStream, paramString);
    this.c.a(this.a);
    b();
  }
  
  public f[] a()
  {
    return a(j.b);
  }
  
  public f[] a(i parami)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      f localf1 = this.c.a(str);
      f localf2 = localf1;
      if (localf1 == null)
      {
        localf2 = localf1;
        if (this.d) {
          localf2 = new f(str);
        }
      }
      if (parami.a(localf2)) {
        localArrayList.add(localf2);
      }
    }
    return (f[])localArrayList.toArray(new f[localArrayList.size()]);
  }
  
  public void b()
  {
    this.b = this.a.listIterator();
  }
}


/* Location:              ~/org/apache/commons/net/ftp/k.class
 *
 * Reversed by:           J
 */