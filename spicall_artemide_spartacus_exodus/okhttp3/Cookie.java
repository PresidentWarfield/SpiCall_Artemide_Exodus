package okhttp3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

public final class Cookie
{
  private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
  private static final Pattern MONTH_PATTERN;
  private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
  private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
  private final String domain;
  private final long expiresAt;
  private final boolean hostOnly;
  private final boolean httpOnly;
  private final String name;
  private final String path;
  private final boolean persistent;
  private final boolean secure;
  private final String value;
  
  static
  {
    MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
  }
  
  private Cookie(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.name = paramString1;
    this.value = paramString2;
    this.expiresAt = paramLong;
    this.domain = paramString3;
    this.path = paramString4;
    this.secure = paramBoolean1;
    this.httpOnly = paramBoolean2;
    this.hostOnly = paramBoolean3;
    this.persistent = paramBoolean4;
  }
  
  Cookie(Builder paramBuilder)
  {
    if (paramBuilder.name != null)
    {
      if (paramBuilder.value != null)
      {
        if (paramBuilder.domain != null)
        {
          this.name = paramBuilder.name;
          this.value = paramBuilder.value;
          this.expiresAt = paramBuilder.expiresAt;
          this.domain = paramBuilder.domain;
          this.path = paramBuilder.path;
          this.secure = paramBuilder.secure;
          this.httpOnly = paramBuilder.httpOnly;
          this.persistent = paramBuilder.persistent;
          this.hostOnly = paramBuilder.hostOnly;
          return;
        }
        throw new NullPointerException("builder.domain == null");
      }
      throw new NullPointerException("builder.value == null");
    }
    throw new NullPointerException("builder.name == null");
  }
  
  private static int dateCharacterOffset(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    while (paramInt1 < paramInt2)
    {
      int i = paramString.charAt(paramInt1);
      if (((i >= 32) || (i == 9)) && (i < 127) && ((i < 48) || (i > 57)) && ((i < 97) || (i > 122)) && ((i < 65) || (i > 90)) && (i != 58)) {
        i = 0;
      } else {
        i = 1;
      }
      if (i == (paramBoolean ^ true)) {
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  private static boolean domainMatch(String paramString1, String paramString2)
  {
    if (paramString1.equals(paramString2)) {
      return true;
    }
    return (paramString1.endsWith(paramString2)) && (paramString1.charAt(paramString1.length() - paramString2.length() - 1) == '.') && (!Util.verifyAsIpAddress(paramString1));
  }
  
  @Nullable
  static Cookie parse(long paramLong, HttpUrl paramHttpUrl, String paramString)
  {
    int i = paramString.length();
    int j = Util.delimiterOffset(paramString, 0, i, ';');
    int k = Util.delimiterOffset(paramString, 0, j, '=');
    if (k == j) {
      return null;
    }
    String str1 = Util.trimSubstring(paramString, 0, k);
    if ((!str1.isEmpty()) && (Util.indexOfControlOrNonAscii(str1) == -1))
    {
      String str2 = Util.trimSubstring(paramString, k + 1, j);
      if (Util.indexOfControlOrNonAscii(str2) != -1) {
        return null;
      }
      j++;
      localObject1 = null;
      localObject2 = localObject1;
      l1 = -1L;
      l2 = 253402300799999L;
      bool1 = false;
      boolean bool2 = false;
      bool3 = true;
      for (bool4 = false; j < i; bool4 = bool5)
      {
        k = Util.delimiterOffset(paramString, j, i, ';');
        int m = Util.delimiterOffset(paramString, j, k, '=');
        String str3 = Util.trimSubstring(paramString, j, m);
        if (m < k) {
          localObject3 = Util.trimSubstring(paramString, m + 1, k);
        } else {
          localObject3 = "";
        }
        if (str3.equalsIgnoreCase("expires")) {}
        try
        {
          l3 = parseExpires((String)localObject3, 0, ((String)localObject3).length());
          bool5 = true;
          localObject3 = localObject1;
          l4 = l1;
          bool6 = bool1;
          localObject5 = localObject2;
          bool7 = bool3;
        }
        catch (IllegalArgumentException|NumberFormatException localIllegalArgumentException)
        {
          for (;;)
          {
            Object localObject4 = localObject1;
            long l4 = l1;
            boolean bool6 = bool1;
            Object localObject5 = localObject2;
            boolean bool7 = bool3;
            long l3 = l2;
            boolean bool5 = bool4;
          }
        }
        if (str3.equalsIgnoreCase("max-age"))
        {
          l4 = parseMaxAge((String)localObject3);
          bool5 = true;
          localObject3 = localObject1;
          bool6 = bool1;
          localObject5 = localObject2;
          bool7 = bool3;
          l3 = l2;
        }
        else if (str3.equalsIgnoreCase("domain"))
        {
          localObject3 = parseDomain((String)localObject3);
          bool7 = false;
          l4 = l1;
          bool6 = bool1;
          localObject5 = localObject2;
          l3 = l2;
          bool5 = bool4;
        }
        else if (str3.equalsIgnoreCase("path"))
        {
          localObject5 = localObject3;
          localObject3 = localObject1;
          l4 = l1;
          bool6 = bool1;
          bool7 = bool3;
          l3 = l2;
          bool5 = bool4;
        }
        else if (str3.equalsIgnoreCase("secure"))
        {
          bool6 = true;
          localObject3 = localObject1;
          l4 = l1;
          localObject5 = localObject2;
          bool7 = bool3;
          l3 = l2;
          bool5 = bool4;
        }
        else
        {
          localObject3 = localObject1;
          l4 = l1;
          bool6 = bool1;
          localObject5 = localObject2;
          bool7 = bool3;
          l3 = l2;
          bool5 = bool4;
          if (str3.equalsIgnoreCase("httponly"))
          {
            bool2 = true;
            bool5 = bool4;
            l3 = l2;
            bool7 = bool3;
            localObject5 = localObject2;
            bool6 = bool1;
            l4 = l1;
            localObject3 = localObject1;
          }
        }
        j = k + 1;
        localObject1 = localObject3;
        l1 = l4;
        bool1 = bool6;
        localObject2 = localObject5;
        bool3 = bool7;
        l2 = l3;
      }
      if (l1 == Long.MIN_VALUE)
      {
        paramLong = Long.MIN_VALUE;
      }
      else if (l1 != -1L)
      {
        if (l1 <= 9223372036854775L) {
          l2 = l1 * 1000L;
        } else {
          l2 = Long.MAX_VALUE;
        }
        l2 = paramLong + l2;
        if ((l2 >= paramLong) && (l2 <= 253402300799999L)) {
          paramLong = l2;
        } else {
          paramLong = 253402300799999L;
        }
      }
      else
      {
        paramLong = l2;
      }
      Object localObject3 = paramHttpUrl.host();
      if (localObject1 == null)
      {
        paramString = (String)localObject3;
      }
      else
      {
        if (!domainMatch((String)localObject3, (String)localObject1)) {
          return null;
        }
        paramString = (String)localObject1;
      }
      if (((String)localObject3).length() != paramString.length()) {
        if (PublicSuffixDatabase.get().getEffectiveTldPlusOne(paramString) == null) {
          return null;
        }
      }
      if ((localObject2 != null) && (((String)localObject2).startsWith("/")))
      {
        paramHttpUrl = (HttpUrl)localObject2;
      }
      else
      {
        paramHttpUrl = paramHttpUrl.encodedPath();
        j = paramHttpUrl.lastIndexOf('/');
        if (j != 0) {
          paramHttpUrl = paramHttpUrl.substring(0, j);
        } else {
          paramHttpUrl = "/";
        }
      }
      return new Cookie(str1, str2, paramLong, paramString, paramHttpUrl, bool1, bool2, bool3, bool4);
    }
    return null;
  }
  
  @Nullable
  public static Cookie parse(HttpUrl paramHttpUrl, String paramString)
  {
    return parse(System.currentTimeMillis(), paramHttpUrl, paramString);
  }
  
  public static List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders)
  {
    List localList = paramHeaders.values("Set-Cookie");
    int i = localList.size();
    Headers localHeaders = null;
    int j = 0;
    while (j < i)
    {
      Cookie localCookie = parse(paramHttpUrl, (String)localList.get(j));
      if (localCookie == null)
      {
        paramHeaders = localHeaders;
      }
      else
      {
        paramHeaders = localHeaders;
        if (localHeaders == null) {
          paramHeaders = new ArrayList();
        }
        paramHeaders.add(localCookie);
      }
      j++;
      localHeaders = paramHeaders;
    }
    if (localHeaders != null) {
      paramHttpUrl = Collections.unmodifiableList(localHeaders);
    } else {
      paramHttpUrl = Collections.emptyList();
    }
    return paramHttpUrl;
  }
  
  private static String parseDomain(String paramString)
  {
    if (!paramString.endsWith("."))
    {
      String str = paramString;
      if (paramString.startsWith(".")) {
        str = paramString.substring(1);
      }
      paramString = Util.canonicalizeHost(str);
      if (paramString != null) {
        return paramString;
      }
      throw new IllegalArgumentException();
    }
    throw new IllegalArgumentException();
  }
  
  private static long parseExpires(String paramString, int paramInt1, int paramInt2)
  {
    int i = dateCharacterOffset(paramString, paramInt1, paramInt2, false);
    Matcher localMatcher = TIME_PATTERN.matcher(paramString);
    paramInt1 = -1;
    int j = -1;
    int k = -1;
    int m = -1;
    int n = -1;
    int i1 = -1;
    while (i < paramInt2)
    {
      int i2 = dateCharacterOffset(paramString, i + 1, paramInt2, true);
      localMatcher.region(i, i2);
      int i3;
      int i4;
      int i5;
      int i6;
      int i7;
      if ((j == -1) && (localMatcher.usePattern(TIME_PATTERN).matches()))
      {
        i = Integer.parseInt(localMatcher.group(1));
        i3 = Integer.parseInt(localMatcher.group(2));
        i4 = Integer.parseInt(localMatcher.group(3));
        i5 = paramInt1;
        i6 = k;
        i7 = m;
      }
      else if ((k == -1) && (localMatcher.usePattern(DAY_OF_MONTH_PATTERN).matches()))
      {
        i6 = Integer.parseInt(localMatcher.group(1));
        i5 = paramInt1;
        i = j;
        i7 = m;
        i3 = n;
        i4 = i1;
      }
      else if ((m == -1) && (localMatcher.usePattern(MONTH_PATTERN).matches()))
      {
        String str = localMatcher.group(1).toLowerCase(Locale.US);
        i7 = MONTH_PATTERN.pattern().indexOf(str) / 4;
        i5 = paramInt1;
        i = j;
        i6 = k;
        i3 = n;
        i4 = i1;
      }
      else
      {
        i5 = paramInt1;
        i = j;
        i6 = k;
        i7 = m;
        i3 = n;
        i4 = i1;
        if (paramInt1 == -1)
        {
          i5 = paramInt1;
          i = j;
          i6 = k;
          i7 = m;
          i3 = n;
          i4 = i1;
          if (localMatcher.usePattern(YEAR_PATTERN).matches())
          {
            i5 = Integer.parseInt(localMatcher.group(1));
            i4 = i1;
            i3 = n;
            i7 = m;
            i6 = k;
            i = j;
          }
        }
      }
      i2 = dateCharacterOffset(paramString, i2 + 1, paramInt2, false);
      paramInt1 = i5;
      j = i;
      k = i6;
      m = i7;
      n = i3;
      i1 = i4;
      i = i2;
    }
    paramInt2 = paramInt1;
    if (paramInt1 >= 70)
    {
      paramInt2 = paramInt1;
      if (paramInt1 <= 99) {
        paramInt2 = paramInt1 + 1900;
      }
    }
    paramInt1 = paramInt2;
    if (paramInt2 >= 0)
    {
      paramInt1 = paramInt2;
      if (paramInt2 <= 69) {
        paramInt1 = paramInt2 + 2000;
      }
    }
    if (paramInt1 >= 1601)
    {
      if (m != -1)
      {
        if ((k >= 1) && (k <= 31))
        {
          if ((j >= 0) && (j <= 23))
          {
            if ((n >= 0) && (n <= 59))
            {
              if ((i1 >= 0) && (i1 <= 59))
              {
                paramString = new GregorianCalendar(Util.UTC);
                paramString.setLenient(false);
                paramString.set(1, paramInt1);
                paramString.set(2, m - 1);
                paramString.set(5, k);
                paramString.set(11, j);
                paramString.set(12, n);
                paramString.set(13, i1);
                paramString.set(14, 0);
                return paramString.getTimeInMillis();
              }
              throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException();
          }
          throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
      }
      throw new IllegalArgumentException();
    }
    throw new IllegalArgumentException();
  }
  
  private static long parseMaxAge(String paramString)
  {
    long l1 = Long.MIN_VALUE;
    try
    {
      long l2 = Long.parseLong(paramString);
      if (l2 > 0L) {
        l1 = l2;
      }
      return l1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      if (paramString.matches("-?\\d+"))
      {
        if (!paramString.startsWith("-")) {
          l1 = Long.MAX_VALUE;
        }
        return l1;
      }
      throw localNumberFormatException;
    }
  }
  
  private static boolean pathMatch(HttpUrl paramHttpUrl, String paramString)
  {
    paramHttpUrl = paramHttpUrl.encodedPath();
    if (paramHttpUrl.equals(paramString)) {
      return true;
    }
    if (paramHttpUrl.startsWith(paramString))
    {
      if (paramString.endsWith("/")) {
        return true;
      }
      if (paramHttpUrl.charAt(paramString.length()) == '/') {
        return true;
      }
    }
    return false;
  }
  
  public String domain()
  {
    return this.domain;
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool1 = paramObject instanceof Cookie;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (Cookie)paramObject;
    bool1 = bool2;
    if (((Cookie)paramObject).name.equals(this.name))
    {
      bool1 = bool2;
      if (((Cookie)paramObject).value.equals(this.value))
      {
        bool1 = bool2;
        if (((Cookie)paramObject).domain.equals(this.domain))
        {
          bool1 = bool2;
          if (((Cookie)paramObject).path.equals(this.path))
          {
            bool1 = bool2;
            if (((Cookie)paramObject).expiresAt == this.expiresAt)
            {
              bool1 = bool2;
              if (((Cookie)paramObject).secure == this.secure)
              {
                bool1 = bool2;
                if (((Cookie)paramObject).httpOnly == this.httpOnly)
                {
                  bool1 = bool2;
                  if (((Cookie)paramObject).persistent == this.persistent)
                  {
                    bool1 = bool2;
                    if (((Cookie)paramObject).hostOnly == this.hostOnly) {
                      bool1 = true;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public long expiresAt()
  {
    return this.expiresAt;
  }
  
  public int hashCode()
  {
    int i = this.name.hashCode();
    int j = this.value.hashCode();
    int k = this.domain.hashCode();
    int m = this.path.hashCode();
    long l = this.expiresAt;
    return ((((((((527 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + (int)(l ^ l >>> 32)) * 31 + (this.secure ^ true)) * 31 + (this.httpOnly ^ true)) * 31 + (this.persistent ^ true)) * 31 + (this.hostOnly ^ true);
  }
  
  public boolean hostOnly()
  {
    return this.hostOnly;
  }
  
  public boolean httpOnly()
  {
    return this.httpOnly;
  }
  
  public boolean matches(HttpUrl paramHttpUrl)
  {
    boolean bool;
    if (this.hostOnly) {
      bool = paramHttpUrl.host().equals(this.domain);
    } else {
      bool = domainMatch(paramHttpUrl.host(), this.domain);
    }
    if (!bool) {
      return false;
    }
    if (!pathMatch(paramHttpUrl, this.path)) {
      return false;
    }
    return (!this.secure) || (paramHttpUrl.isHttps());
  }
  
  public String name()
  {
    return this.name;
  }
  
  public String path()
  {
    return this.path;
  }
  
  public boolean persistent()
  {
    return this.persistent;
  }
  
  public boolean secure()
  {
    return this.secure;
  }
  
  public String toString()
  {
    return toString(false);
  }
  
  String toString(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.name);
    localStringBuilder.append('=');
    localStringBuilder.append(this.value);
    if (this.persistent) {
      if (this.expiresAt == Long.MIN_VALUE)
      {
        localStringBuilder.append("; max-age=0");
      }
      else
      {
        localStringBuilder.append("; expires=");
        localStringBuilder.append(HttpDate.format(new Date(this.expiresAt)));
      }
    }
    if (!this.hostOnly)
    {
      localStringBuilder.append("; domain=");
      if (paramBoolean) {
        localStringBuilder.append(".");
      }
      localStringBuilder.append(this.domain);
    }
    localStringBuilder.append("; path=");
    localStringBuilder.append(this.path);
    if (this.secure) {
      localStringBuilder.append("; secure");
    }
    if (this.httpOnly) {
      localStringBuilder.append("; httponly");
    }
    return localStringBuilder.toString();
  }
  
  public String value()
  {
    return this.value;
  }
  
  public static final class Builder
  {
    @Nullable
    String domain;
    long expiresAt = 253402300799999L;
    boolean hostOnly;
    boolean httpOnly;
    @Nullable
    String name;
    String path = "/";
    boolean persistent;
    boolean secure;
    @Nullable
    String value;
    
    private Builder domain(String paramString, boolean paramBoolean)
    {
      if (paramString != null)
      {
        Object localObject = Util.canonicalizeHost(paramString);
        if (localObject != null)
        {
          this.domain = ((String)localObject);
          this.hostOnly = paramBoolean;
          return this;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("unexpected domain: ");
        ((StringBuilder)localObject).append(paramString);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
      throw new NullPointerException("domain == null");
    }
    
    public Cookie build()
    {
      return new Cookie(this);
    }
    
    public Builder domain(String paramString)
    {
      return domain(paramString, false);
    }
    
    public Builder expiresAt(long paramLong)
    {
      long l = paramLong;
      if (paramLong <= 0L) {
        l = Long.MIN_VALUE;
      }
      paramLong = l;
      if (l > 253402300799999L) {
        paramLong = 253402300799999L;
      }
      this.expiresAt = paramLong;
      this.persistent = true;
      return this;
    }
    
    public Builder hostOnlyDomain(String paramString)
    {
      return domain(paramString, true);
    }
    
    public Builder httpOnly()
    {
      this.httpOnly = true;
      return this;
    }
    
    public Builder name(String paramString)
    {
      if (paramString != null)
      {
        if (paramString.trim().equals(paramString))
        {
          this.name = paramString;
          return this;
        }
        throw new IllegalArgumentException("name is not trimmed");
      }
      throw new NullPointerException("name == null");
    }
    
    public Builder path(String paramString)
    {
      if (paramString.startsWith("/"))
      {
        this.path = paramString;
        return this;
      }
      throw new IllegalArgumentException("path must start with '/'");
    }
    
    public Builder secure()
    {
      this.secure = true;
      return this;
    }
    
    public Builder value(String paramString)
    {
      if (paramString != null)
      {
        if (paramString.trim().equals(paramString))
        {
          this.value = paramString;
          return this;
        }
        throw new IllegalArgumentException("value is not trimmed");
      }
      throw new NullPointerException("value == null");
    }
  }
}


/* Location:              ~/okhttp3/Cookie.class
 *
 * Reversed by:           J
 */