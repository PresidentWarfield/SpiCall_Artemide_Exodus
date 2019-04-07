package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public abstract interface Dns
{
  public static final Dns SYSTEM = new Dns()
  {
    public List<InetAddress> lookup(String paramAnonymousString)
    {
      if (paramAnonymousString != null) {
        try
        {
          List localList = Arrays.asList(InetAddress.getAllByName(paramAnonymousString));
          return localList;
        }
        catch (NullPointerException localNullPointerException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Broken system behaviour for dns lookup of ");
          localStringBuilder.append(paramAnonymousString);
          paramAnonymousString = new UnknownHostException(localStringBuilder.toString());
          paramAnonymousString.initCause(localNullPointerException);
          throw paramAnonymousString;
        }
      }
      throw new UnknownHostException("hostname == null");
    }
  };
  
  public abstract List<InetAddress> lookup(String paramString);
}


/* Location:              ~/okhttp3/Dns.class
 *
 * Reversed by:           J
 */