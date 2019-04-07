package org.apache.commons.net;

import java.util.EventObject;

public class a
  extends EventObject
{
  private final int a;
  private final boolean b;
  private final String c;
  private final String d;
  
  public a(Object paramObject, int paramInt, String paramString)
  {
    super(paramObject);
    this.a = paramInt;
    this.c = paramString;
    this.b = false;
    this.d = null;
  }
  
  public a(Object paramObject, String paramString1, String paramString2)
  {
    super(paramObject);
    this.a = 0;
    this.c = paramString2;
    this.b = true;
    this.d = paramString1;
  }
}


/* Location:              ~/org/apache/commons/net/a.class
 *
 * Reversed by:           J
 */