package okhttp3;

import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.http.HttpHeaders;

public final class CacheControl
{
  public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
  public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
  @Nullable
  String headerValue;
  private final boolean immutable;
  private final boolean isPrivate;
  private final boolean isPublic;
  private final int maxAgeSeconds;
  private final int maxStaleSeconds;
  private final int minFreshSeconds;
  private final boolean mustRevalidate;
  private final boolean noCache;
  private final boolean noStore;
  private final boolean noTransform;
  private final boolean onlyIfCached;
  private final int sMaxAgeSeconds;
  
  CacheControl(Builder paramBuilder)
  {
    this.noCache = paramBuilder.noCache;
    this.noStore = paramBuilder.noStore;
    this.maxAgeSeconds = paramBuilder.maxAgeSeconds;
    this.sMaxAgeSeconds = -1;
    this.isPrivate = false;
    this.isPublic = false;
    this.mustRevalidate = false;
    this.maxStaleSeconds = paramBuilder.maxStaleSeconds;
    this.minFreshSeconds = paramBuilder.minFreshSeconds;
    this.onlyIfCached = paramBuilder.onlyIfCached;
    this.noTransform = paramBuilder.noTransform;
    this.immutable = paramBuilder.immutable;
  }
  
  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt3, int paramInt4, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, @Nullable String paramString)
  {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPrivate = paramBoolean3;
    this.isPublic = paramBoolean4;
    this.mustRevalidate = paramBoolean5;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean6;
    this.noTransform = paramBoolean7;
    this.immutable = paramBoolean8;
    this.headerValue = paramString;
  }
  
  private String headerValue()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.noCache) {
      localStringBuilder.append("no-cache, ");
    }
    if (this.noStore) {
      localStringBuilder.append("no-store, ");
    }
    if (this.maxAgeSeconds != -1)
    {
      localStringBuilder.append("max-age=");
      localStringBuilder.append(this.maxAgeSeconds);
      localStringBuilder.append(", ");
    }
    if (this.sMaxAgeSeconds != -1)
    {
      localStringBuilder.append("s-maxage=");
      localStringBuilder.append(this.sMaxAgeSeconds);
      localStringBuilder.append(", ");
    }
    if (this.isPrivate) {
      localStringBuilder.append("private, ");
    }
    if (this.isPublic) {
      localStringBuilder.append("public, ");
    }
    if (this.mustRevalidate) {
      localStringBuilder.append("must-revalidate, ");
    }
    if (this.maxStaleSeconds != -1)
    {
      localStringBuilder.append("max-stale=");
      localStringBuilder.append(this.maxStaleSeconds);
      localStringBuilder.append(", ");
    }
    if (this.minFreshSeconds != -1)
    {
      localStringBuilder.append("min-fresh=");
      localStringBuilder.append(this.minFreshSeconds);
      localStringBuilder.append(", ");
    }
    if (this.onlyIfCached) {
      localStringBuilder.append("only-if-cached, ");
    }
    if (this.noTransform) {
      localStringBuilder.append("no-transform, ");
    }
    if (this.immutable) {
      localStringBuilder.append("immutable, ");
    }
    if (localStringBuilder.length() == 0) {
      return "";
    }
    localStringBuilder.delete(localStringBuilder.length() - 2, localStringBuilder.length());
    return localStringBuilder.toString();
  }
  
  public static CacheControl parse(Headers paramHeaders)
  {
    int i = paramHeaders.size();
    int j = 0;
    int k = 1;
    Object localObject1 = null;
    boolean bool1 = false;
    boolean bool2 = false;
    int m = -1;
    int n = -1;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    int i1 = -1;
    int i2 = -1;
    boolean bool6 = false;
    boolean bool7 = false;
    boolean bool8 = false;
    for (;;)
    {
      Object localObject2 = paramHeaders;
      if (j >= i) {
        break;
      }
      String str1 = ((Headers)localObject2).name(j);
      String str2 = ((Headers)localObject2).value(j);
      if (str1.equalsIgnoreCase("Cache-Control"))
      {
        if (localObject1 != null) {
          k = 0;
        } else {
          localObject1 = str2;
        }
      }
      else
      {
        if (!str1.equalsIgnoreCase("Pragma")) {
          break label1075;
        }
        k = 0;
      }
      int i3 = 0;
      while (i3 < str2.length())
      {
        int i4 = HttpHeaders.skipUntil(str2, i3, "=,;");
        str1 = str2.substring(i3, i4).trim();
        if ((i4 != str2.length()) && (str2.charAt(i4) != ',') && (str2.charAt(i4) != ';'))
        {
          i4 = HttpHeaders.skipWhitespace(str2, i4 + 1);
          if ((i4 < str2.length()) && (str2.charAt(i4) == '"'))
          {
            i4++;
            i3 = HttpHeaders.skipUntil(str2, i4, "\"");
            localObject2 = str2.substring(i4, i3);
            i3++;
          }
          else
          {
            i3 = HttpHeaders.skipUntil(str2, i4, ",;");
            localObject2 = str2.substring(i4, i3).trim();
          }
        }
        else
        {
          i3 = i4 + 1;
          localObject2 = null;
        }
        boolean bool9;
        boolean bool10;
        int i5;
        boolean bool11;
        boolean bool12;
        boolean bool13;
        int i6;
        int i7;
        boolean bool14;
        boolean bool15;
        if ("no-cache".equalsIgnoreCase(str1))
        {
          bool9 = true;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("no-store".equalsIgnoreCase(str1))
        {
          bool10 = true;
          bool9 = bool1;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("max-age".equalsIgnoreCase(str1))
        {
          i4 = HttpHeaders.parseSeconds((String)localObject2, -1);
          bool9 = bool1;
          bool10 = bool2;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("s-maxage".equalsIgnoreCase(str1))
        {
          i5 = HttpHeaders.parseSeconds((String)localObject2, -1);
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("private".equalsIgnoreCase(str1))
        {
          bool11 = true;
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("public".equalsIgnoreCase(str1))
        {
          bool12 = true;
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("must-revalidate".equalsIgnoreCase(str1))
        {
          bool13 = true;
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("max-stale".equalsIgnoreCase(str1))
        {
          i6 = HttpHeaders.parseSeconds((String)localObject2, Integer.MAX_VALUE);
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("min-fresh".equalsIgnoreCase(str1))
        {
          i7 = HttpHeaders.parseSeconds((String)localObject2, -1);
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          bool14 = bool6;
          bool15 = bool7;
        }
        else if ("only-if-cached".equalsIgnoreCase(str1))
        {
          bool14 = true;
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool15 = bool7;
        }
        else if ("no-transform".equalsIgnoreCase(str1))
        {
          bool15 = true;
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
        }
        else
        {
          bool9 = bool1;
          bool10 = bool2;
          i4 = m;
          i5 = n;
          bool11 = bool3;
          bool12 = bool4;
          bool13 = bool5;
          i6 = i1;
          i7 = i2;
          bool14 = bool6;
          bool15 = bool7;
          if ("immutable".equalsIgnoreCase(str1))
          {
            bool8 = true;
            bool15 = bool7;
            bool14 = bool6;
            i7 = i2;
            i6 = i1;
            bool13 = bool5;
            bool12 = bool4;
            bool11 = bool3;
            i5 = n;
            i4 = m;
            bool10 = bool2;
            bool9 = bool1;
          }
        }
        bool1 = bool9;
        bool2 = bool10;
        m = i4;
        n = i5;
        bool3 = bool11;
        bool4 = bool12;
        bool5 = bool13;
        i1 = i6;
        i2 = i7;
        bool6 = bool14;
        bool7 = bool15;
      }
      label1075:
      j++;
    }
    if (k == 0) {
      localObject1 = null;
    }
    return new CacheControl(bool1, bool2, m, n, bool3, bool4, bool5, i1, i2, bool6, bool7, bool8, (String)localObject1);
  }
  
  public boolean immutable()
  {
    return this.immutable;
  }
  
  public boolean isPrivate()
  {
    return this.isPrivate;
  }
  
  public boolean isPublic()
  {
    return this.isPublic;
  }
  
  public int maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }
  
  public int maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }
  
  public int minFreshSeconds()
  {
    return this.minFreshSeconds;
  }
  
  public boolean mustRevalidate()
  {
    return this.mustRevalidate;
  }
  
  public boolean noCache()
  {
    return this.noCache;
  }
  
  public boolean noStore()
  {
    return this.noStore;
  }
  
  public boolean noTransform()
  {
    return this.noTransform;
  }
  
  public boolean onlyIfCached()
  {
    return this.onlyIfCached;
  }
  
  public int sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }
  
  public String toString()
  {
    String str = this.headerValue;
    if (str == null)
    {
      str = headerValue();
      this.headerValue = str;
    }
    return str;
  }
  
  public static final class Builder
  {
    boolean immutable;
    int maxAgeSeconds = -1;
    int maxStaleSeconds = -1;
    int minFreshSeconds = -1;
    boolean noCache;
    boolean noStore;
    boolean noTransform;
    boolean onlyIfCached;
    
    public CacheControl build()
    {
      return new CacheControl(this);
    }
    
    public Builder immutable()
    {
      this.immutable = true;
      return this;
    }
    
    public Builder maxAge(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt >= 0)
      {
        long l = paramTimeUnit.toSeconds(paramInt);
        if (l > 2147483647L) {
          paramInt = Integer.MAX_VALUE;
        } else {
          paramInt = (int)l;
        }
        this.maxAgeSeconds = paramInt;
        return this;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("maxAge < 0: ");
      paramTimeUnit.append(paramInt);
      throw new IllegalArgumentException(paramTimeUnit.toString());
    }
    
    public Builder maxStale(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt >= 0)
      {
        long l = paramTimeUnit.toSeconds(paramInt);
        if (l > 2147483647L) {
          paramInt = Integer.MAX_VALUE;
        } else {
          paramInt = (int)l;
        }
        this.maxStaleSeconds = paramInt;
        return this;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("maxStale < 0: ");
      paramTimeUnit.append(paramInt);
      throw new IllegalArgumentException(paramTimeUnit.toString());
    }
    
    public Builder minFresh(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt >= 0)
      {
        long l = paramTimeUnit.toSeconds(paramInt);
        if (l > 2147483647L) {
          paramInt = Integer.MAX_VALUE;
        } else {
          paramInt = (int)l;
        }
        this.minFreshSeconds = paramInt;
        return this;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("minFresh < 0: ");
      paramTimeUnit.append(paramInt);
      throw new IllegalArgumentException(paramTimeUnit.toString());
    }
    
    public Builder noCache()
    {
      this.noCache = true;
      return this;
    }
    
    public Builder noStore()
    {
      this.noStore = true;
      return this;
    }
    
    public Builder noTransform()
    {
      this.noTransform = true;
      return this;
    }
    
    public Builder onlyIfCached()
    {
      this.onlyIfCached = true;
      return this;
    }
  }
}


/* Location:              ~/okhttp3/CacheControl.class
 *
 * Reversed by:           J
 */