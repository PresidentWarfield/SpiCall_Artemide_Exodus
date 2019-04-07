package okhttp3.internal.cache;

import okhttp3.Request;
import okhttp3.Response;

public abstract interface InternalCache
{
  public abstract Response get(Request paramRequest);
  
  public abstract CacheRequest put(Response paramResponse);
  
  public abstract void remove(Request paramRequest);
  
  public abstract void trackConditionalCacheHit();
  
  public abstract void trackResponse(CacheStrategy paramCacheStrategy);
  
  public abstract void update(Response paramResponse1, Response paramResponse2);
}


/* Location:              ~/okhttp3/internal/cache/InternalCache.class
 *
 * Reversed by:           J
 */