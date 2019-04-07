package okhttp3.internal.http2;

import a.f;
import okhttp3.Headers;
import okhttp3.internal.Util;

public final class Header
{
  public static final f PSEUDO_PREFIX = f.a(":");
  public static final f RESPONSE_STATUS = f.a(":status");
  public static final String RESPONSE_STATUS_UTF8 = ":status";
  public static final f TARGET_AUTHORITY = f.a(":authority");
  public static final String TARGET_AUTHORITY_UTF8 = ":authority";
  public static final f TARGET_METHOD = f.a(":method");
  public static final String TARGET_METHOD_UTF8 = ":method";
  public static final f TARGET_PATH = f.a(":path");
  public static final String TARGET_PATH_UTF8 = ":path";
  public static final f TARGET_SCHEME = f.a(":scheme");
  public static final String TARGET_SCHEME_UTF8 = ":scheme";
  final int hpackSize;
  public final f name;
  public final f value;
  
  public Header(f paramf1, f paramf2)
  {
    this.name = paramf1;
    this.value = paramf2;
    this.hpackSize = (paramf1.h() + 32 + paramf2.h());
  }
  
  public Header(f paramf, String paramString)
  {
    this(paramf, f.a(paramString));
  }
  
  public Header(String paramString1, String paramString2)
  {
    this(f.a(paramString1), f.a(paramString2));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Header;
    boolean bool2 = false;
    if (bool1)
    {
      paramObject = (Header)paramObject;
      bool1 = bool2;
      if (this.name.equals(((Header)paramObject).name))
      {
        bool1 = bool2;
        if (this.value.equals(((Header)paramObject).value)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public int hashCode()
  {
    return (527 + this.name.hashCode()) * 31 + this.value.hashCode();
  }
  
  public String toString()
  {
    return Util.format("%s: %s", new Object[] { this.name.a(), this.value.a() });
  }
  
  static abstract interface Listener
  {
    public abstract void onHeaders(Headers paramHeaders);
  }
}


/* Location:              ~/okhttp3/internal/http2/Header.class
 *
 * Reversed by:           J
 */