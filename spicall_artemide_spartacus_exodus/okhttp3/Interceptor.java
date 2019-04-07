package okhttp3;

import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract interface Interceptor
{
  public abstract Response intercept(Chain paramChain);
  
  public static abstract interface Chain
  {
    public abstract Call call();
    
    public abstract int connectTimeoutMillis();
    
    @Nullable
    public abstract Connection connection();
    
    public abstract Response proceed(Request paramRequest);
    
    public abstract int readTimeoutMillis();
    
    public abstract Request request();
    
    public abstract Chain withConnectTimeout(int paramInt, TimeUnit paramTimeUnit);
    
    public abstract Chain withReadTimeout(int paramInt, TimeUnit paramTimeUnit);
    
    public abstract Chain withWriteTimeout(int paramInt, TimeUnit paramTimeUnit);
    
    public abstract int writeTimeoutMillis();
  }
}


/* Location:              ~/okhttp3/Interceptor.class
 *
 * Reversed by:           J
 */