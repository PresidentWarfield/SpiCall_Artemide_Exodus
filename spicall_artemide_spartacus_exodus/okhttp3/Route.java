package okhttp3;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import javax.annotation.Nullable;

public final class Route
{
  final Address address;
  final InetSocketAddress inetSocketAddress;
  final Proxy proxy;
  
  public Route(Address paramAddress, Proxy paramProxy, InetSocketAddress paramInetSocketAddress)
  {
    if (paramAddress != null)
    {
      if (paramProxy != null)
      {
        if (paramInetSocketAddress != null)
        {
          this.address = paramAddress;
          this.proxy = paramProxy;
          this.inetSocketAddress = paramInetSocketAddress;
          return;
        }
        throw new NullPointerException("inetSocketAddress == null");
      }
      throw new NullPointerException("proxy == null");
    }
    throw new NullPointerException("address == null");
  }
  
  public Address address()
  {
    return this.address;
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    if ((paramObject instanceof Route))
    {
      paramObject = (Route)paramObject;
      if ((((Route)paramObject).address.equals(this.address)) && (((Route)paramObject).proxy.equals(this.proxy)) && (((Route)paramObject).inetSocketAddress.equals(this.inetSocketAddress))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public int hashCode()
  {
    return ((527 + this.address.hashCode()) * 31 + this.proxy.hashCode()) * 31 + this.inetSocketAddress.hashCode();
  }
  
  public Proxy proxy()
  {
    return this.proxy;
  }
  
  public boolean requiresTunnel()
  {
    boolean bool;
    if ((this.address.sslSocketFactory != null) && (this.proxy.type() == Proxy.Type.HTTP)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public InetSocketAddress socketAddress()
  {
    return this.inetSocketAddress;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Route{");
    localStringBuilder.append(this.inetSocketAddress);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/okhttp3/Route.class
 *
 * Reversed by:           J
 */