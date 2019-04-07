package org.apache.commons.net.telnet;

public class InvalidTelnetOptionException
  extends Exception
{
  private final int a;
  private final String b;
  
  public String getMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.b);
    localStringBuilder.append(": ");
    localStringBuilder.append(this.a);
    return localStringBuilder.toString();
  }
}


/* Location:              ~/org/apache/commons/net/telnet/InvalidTelnetOptionException.class
 *
 * Reversed by:           J
 */