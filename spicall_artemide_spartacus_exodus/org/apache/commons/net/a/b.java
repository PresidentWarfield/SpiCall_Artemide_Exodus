package org.apache.commons.net.a;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class b
  implements Serializable, Iterable<EventListener>
{
  private final CopyOnWriteArrayList<EventListener> a = new CopyOnWriteArrayList();
  
  public int a()
  {
    return this.a.size();
  }
  
  public Iterator<EventListener> iterator()
  {
    return this.a.iterator();
  }
}


/* Location:              ~/org/apache/commons/net/a/b.class
 *
 * Reversed by:           J
 */