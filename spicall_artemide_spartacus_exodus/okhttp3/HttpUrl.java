package okhttp3;

import a.c;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

public final class HttpUrl
{
  static final String FORM_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#&!$(),~";
  static final String FRAGMENT_ENCODE_SET = "";
  static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";
  private static final char[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
  static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";
  static final String QUERY_COMPONENT_ENCODE_SET = " !\"#$&'(),/:;<=>?@[]\\^`{|}~";
  static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";
  static final String QUERY_COMPONENT_REENCODE_SET = " \"'<>#&=";
  static final String QUERY_ENCODE_SET = " \"'<>#";
  static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  @Nullable
  private final String fragment;
  final String host;
  private final String password;
  private final List<String> pathSegments;
  final int port;
  @Nullable
  private final List<String> queryNamesAndValues;
  final String scheme;
  private final String url;
  private final String username;
  
  HttpUrl(Builder paramBuilder)
  {
    this.scheme = paramBuilder.scheme;
    this.username = percentDecode(paramBuilder.encodedUsername, false);
    this.password = percentDecode(paramBuilder.encodedPassword, false);
    this.host = paramBuilder.host;
    this.port = paramBuilder.effectivePort();
    this.pathSegments = percentDecode(paramBuilder.encodedPathSegments, false);
    Object localObject1 = paramBuilder.encodedQueryNamesAndValues;
    Object localObject2 = null;
    if (localObject1 != null) {
      localObject1 = percentDecode(paramBuilder.encodedQueryNamesAndValues, true);
    } else {
      localObject1 = null;
    }
    this.queryNamesAndValues = ((List)localObject1);
    localObject1 = localObject2;
    if (paramBuilder.encodedFragment != null) {
      localObject1 = percentDecode(paramBuilder.encodedFragment, false);
    }
    this.fragment = ((String)localObject1);
    this.url = paramBuilder.toString();
  }
  
  static String canonicalize(String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
  {
    int i = paramInt1;
    while (i < paramInt2)
    {
      int j = paramString1.codePointAt(i);
      if ((j >= 32) && (j != 127) && ((j < 128) || (!paramBoolean4)) && (paramString2.indexOf(j) == -1) && ((j != 37) || ((paramBoolean1) && ((!paramBoolean2) || (percentEncoded(paramString1, i, paramInt2))))) && ((j != 43) || (!paramBoolean3)))
      {
        i += Character.charCount(j);
      }
      else
      {
        c localc = new c();
        localc.a(paramString1, paramInt1, i);
        canonicalize(localc, paramString1, i, paramInt2, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramCharset);
        return localc.q();
      }
    }
    return paramString1.substring(paramInt1, paramInt2);
  }
  
  static String canonicalize(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    return canonicalize(paramString1, 0, paramString1.length(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, null);
  }
  
  static String canonicalize(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
  {
    return canonicalize(paramString1, 0, paramString1.length(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramCharset);
  }
  
  static void canonicalize(c paramc, String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
  {
    Object localObject2;
    for (Object localObject1 = null; paramInt1 < paramInt2; localObject1 = localObject2)
    {
      int i = paramString1.codePointAt(paramInt1);
      if (paramBoolean1)
      {
        localObject2 = localObject1;
        if (i == 9) {
          break label318;
        }
        localObject2 = localObject1;
        if (i == 10) {
          break label318;
        }
        localObject2 = localObject1;
        if (i == 12) {
          break label318;
        }
        if (i == 13)
        {
          localObject2 = localObject1;
          break label318;
        }
      }
      Object localObject3;
      if ((i == 43) && (paramBoolean3))
      {
        if (paramBoolean1) {
          localObject3 = "+";
        } else {
          localObject3 = "%2B";
        }
        paramc.a((String)localObject3);
        localObject2 = localObject1;
      }
      else if ((i >= 32) && (i != 127) && ((i < 128) || (!paramBoolean4)) && (paramString2.indexOf(i) == -1) && ((i != 37) || ((paramBoolean1) && ((!paramBoolean2) || (percentEncoded(paramString1, paramInt1, paramInt2))))))
      {
        paramc.a(i);
        localObject2 = localObject1;
      }
      else
      {
        localObject3 = localObject1;
        if (localObject1 == null) {
          localObject3 = new c();
        }
        if ((paramCharset != null) && (!paramCharset.equals(Util.UTF_8))) {
          ((c)localObject3).a(paramString1, paramInt1, Character.charCount(i) + paramInt1, paramCharset);
        } else {
          ((c)localObject3).a(i);
        }
        for (;;)
        {
          localObject2 = localObject3;
          if (((c)localObject3).e()) {
            break;
          }
          int j = ((c)localObject3).h() & 0xFF;
          paramc.b(37);
          paramc.b(HEX_DIGITS[(j >> 4 & 0xF)]);
          paramc.b(HEX_DIGITS[(j & 0xF)]);
        }
      }
      label318:
      paramInt1 += Character.charCount(i);
    }
  }
  
  public static int defaultPort(String paramString)
  {
    if (paramString.equals("http")) {
      return 80;
    }
    if (paramString.equals("https")) {
      return 443;
    }
    return -1;
  }
  
  public static HttpUrl get(String paramString)
  {
    return new Builder().parse(null, paramString).build();
  }
  
  @Nullable
  public static HttpUrl get(URI paramURI)
  {
    return parse(paramURI.toString());
  }
  
  @Nullable
  public static HttpUrl get(URL paramURL)
  {
    return parse(paramURL.toString());
  }
  
  static void namesAndValuesToQueryString(StringBuilder paramStringBuilder, List<String> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j += 2)
    {
      String str1 = (String)paramList.get(j);
      String str2 = (String)paramList.get(j + 1);
      if (j > 0) {
        paramStringBuilder.append('&');
      }
      paramStringBuilder.append(str1);
      if (str2 != null)
      {
        paramStringBuilder.append('=');
        paramStringBuilder.append(str2);
      }
    }
  }
  
  @Nullable
  public static HttpUrl parse(String paramString)
  {
    try
    {
      paramString = get(paramString);
      return paramString;
    }
    catch (IllegalArgumentException paramString) {}
    return null;
  }
  
  static void pathSegmentsToString(StringBuilder paramStringBuilder, List<String> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      paramStringBuilder.append('/');
      paramStringBuilder.append((String)paramList.get(j));
    }
  }
  
  static String percentDecode(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = paramInt1;
    while (i < paramInt2)
    {
      int j = paramString.charAt(i);
      if ((j != 37) && ((j != 43) || (!paramBoolean)))
      {
        i++;
      }
      else
      {
        c localc = new c();
        localc.a(paramString, paramInt1, i);
        percentDecode(localc, paramString, i, paramInt2, paramBoolean);
        return localc.q();
      }
    }
    return paramString.substring(paramInt1, paramInt2);
  }
  
  static String percentDecode(String paramString, boolean paramBoolean)
  {
    return percentDecode(paramString, 0, paramString.length(), paramBoolean);
  }
  
  private List<String> percentDecode(List<String> paramList, boolean paramBoolean)
  {
    int i = paramList.size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      String str = (String)paramList.get(j);
      if (str != null) {
        str = percentDecode(str, paramBoolean);
      } else {
        str = null;
      }
      localArrayList.add(str);
    }
    return Collections.unmodifiableList(localArrayList);
  }
  
  static void percentDecode(c paramc, String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    while (paramInt1 < paramInt2)
    {
      int i = paramString.codePointAt(paramInt1);
      if (i == 37)
      {
        int j = paramInt1 + 2;
        if (j < paramInt2)
        {
          int k = Util.decodeHexDigit(paramString.charAt(paramInt1 + 1));
          int m = Util.decodeHexDigit(paramString.charAt(j));
          if ((k == -1) || (m == -1)) {
            break label105;
          }
          paramc.b((k << 4) + m);
          paramInt1 = j;
          break label112;
        }
      }
      if ((i == 43) && (paramBoolean)) {
        paramc.b(32);
      } else {
        label105:
        paramc.a(i);
      }
      label112:
      paramInt1 += Character.charCount(i);
    }
  }
  
  static boolean percentEncoded(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + 2;
    boolean bool = true;
    if ((i >= paramInt2) || (paramString.charAt(paramInt1) != '%') || (Util.decodeHexDigit(paramString.charAt(paramInt1 + 1)) == -1) || (Util.decodeHexDigit(paramString.charAt(i)) == -1)) {
      bool = false;
    }
    return bool;
  }
  
  static List<String> queryStringToNamesAndValues(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    int k;
    for (int i = 0; i <= paramString.length(); i = k + 1)
    {
      int j = paramString.indexOf('&', i);
      k = j;
      if (j == -1) {
        k = paramString.length();
      }
      j = paramString.indexOf('=', i);
      if ((j != -1) && (j <= k))
      {
        localArrayList.add(paramString.substring(i, j));
        localArrayList.add(paramString.substring(j + 1, k));
      }
      else
      {
        localArrayList.add(paramString.substring(i, k));
        localArrayList.add(null);
      }
    }
    return localArrayList;
  }
  
  @Nullable
  public String encodedFragment()
  {
    if (this.fragment == null) {
      return null;
    }
    int i = this.url.indexOf('#');
    return this.url.substring(i + 1);
  }
  
  public String encodedPassword()
  {
    if (this.password.isEmpty()) {
      return "";
    }
    int i = this.url.indexOf(':', this.scheme.length() + 3);
    int j = this.url.indexOf('@');
    return this.url.substring(i + 1, j);
  }
  
  public String encodedPath()
  {
    int i = this.url.indexOf('/', this.scheme.length() + 3);
    String str = this.url;
    int j = Util.delimiterOffset(str, i, str.length(), "?#");
    return this.url.substring(i, j);
  }
  
  public List<String> encodedPathSegments()
  {
    int i = this.url.indexOf('/', this.scheme.length() + 3);
    Object localObject = this.url;
    int j = Util.delimiterOffset((String)localObject, i, ((String)localObject).length(), "?#");
    localObject = new ArrayList();
    while (i < j)
    {
      int k = i + 1;
      i = Util.delimiterOffset(this.url, k, j, '/');
      ((List)localObject).add(this.url.substring(k, i));
    }
    return (List<String>)localObject;
  }
  
  @Nullable
  public String encodedQuery()
  {
    if (this.queryNamesAndValues == null) {
      return null;
    }
    int i = this.url.indexOf('?') + 1;
    String str = this.url;
    int j = Util.delimiterOffset(str, i, str.length(), '#');
    return this.url.substring(i, j);
  }
  
  public String encodedUsername()
  {
    if (this.username.isEmpty()) {
      return "";
    }
    int i = this.scheme.length() + 3;
    String str = this.url;
    int j = Util.delimiterOffset(str, i, str.length(), ":@");
    return this.url.substring(i, j);
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof HttpUrl)) && (((HttpUrl)paramObject).url.equals(this.url))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Nullable
  public String fragment()
  {
    return this.fragment;
  }
  
  public int hashCode()
  {
    return this.url.hashCode();
  }
  
  public String host()
  {
    return this.host;
  }
  
  public boolean isHttps()
  {
    return this.scheme.equals("https");
  }
  
  public Builder newBuilder()
  {
    Builder localBuilder = new Builder();
    localBuilder.scheme = this.scheme;
    localBuilder.encodedUsername = encodedUsername();
    localBuilder.encodedPassword = encodedPassword();
    localBuilder.host = this.host;
    int i;
    if (this.port != defaultPort(this.scheme)) {
      i = this.port;
    } else {
      i = -1;
    }
    localBuilder.port = i;
    localBuilder.encodedPathSegments.clear();
    localBuilder.encodedPathSegments.addAll(encodedPathSegments());
    localBuilder.encodedQuery(encodedQuery());
    localBuilder.encodedFragment = encodedFragment();
    return localBuilder;
  }
  
  @Nullable
  public Builder newBuilder(String paramString)
  {
    try
    {
      Builder localBuilder = new okhttp3/HttpUrl$Builder;
      localBuilder.<init>();
      paramString = localBuilder.parse(this, paramString);
      return paramString;
    }
    catch (IllegalArgumentException paramString) {}
    return null;
  }
  
  public String password()
  {
    return this.password;
  }
  
  public List<String> pathSegments()
  {
    return this.pathSegments;
  }
  
  public int pathSize()
  {
    return this.pathSegments.size();
  }
  
  public int port()
  {
    return this.port;
  }
  
  @Nullable
  public String query()
  {
    if (this.queryNamesAndValues == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    namesAndValuesToQueryString(localStringBuilder, this.queryNamesAndValues);
    return localStringBuilder.toString();
  }
  
  @Nullable
  public String queryParameter(String paramString)
  {
    List localList = this.queryNamesAndValues;
    if (localList == null) {
      return null;
    }
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      if (paramString.equals(this.queryNamesAndValues.get(i))) {
        return (String)this.queryNamesAndValues.get(i + 1);
      }
      i += 2;
    }
    return null;
  }
  
  public String queryParameterName(int paramInt)
  {
    List localList = this.queryNamesAndValues;
    if (localList != null) {
      return (String)localList.get(paramInt * 2);
    }
    throw new IndexOutOfBoundsException();
  }
  
  public Set<String> queryParameterNames()
  {
    if (this.queryNamesAndValues == null) {
      return Collections.emptySet();
    }
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    int i = 0;
    int j = this.queryNamesAndValues.size();
    while (i < j)
    {
      localLinkedHashSet.add(this.queryNamesAndValues.get(i));
      i += 2;
    }
    return Collections.unmodifiableSet(localLinkedHashSet);
  }
  
  public String queryParameterValue(int paramInt)
  {
    List localList = this.queryNamesAndValues;
    if (localList != null) {
      return (String)localList.get(paramInt * 2 + 1);
    }
    throw new IndexOutOfBoundsException();
  }
  
  public List<String> queryParameterValues(String paramString)
  {
    if (this.queryNamesAndValues == null) {
      return Collections.emptyList();
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = this.queryNamesAndValues.size();
    while (i < j)
    {
      if (paramString.equals(this.queryNamesAndValues.get(i))) {
        localArrayList.add(this.queryNamesAndValues.get(i + 1));
      }
      i += 2;
    }
    return Collections.unmodifiableList(localArrayList);
  }
  
  public int querySize()
  {
    List localList = this.queryNamesAndValues;
    int i;
    if (localList != null) {
      i = localList.size() / 2;
    } else {
      i = 0;
    }
    return i;
  }
  
  public String redact()
  {
    return newBuilder("/...").username("").password("").build().toString();
  }
  
  @Nullable
  public HttpUrl resolve(String paramString)
  {
    paramString = newBuilder(paramString);
    if (paramString != null) {
      paramString = paramString.build();
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public String scheme()
  {
    return this.scheme;
  }
  
  public String toString()
  {
    return this.url;
  }
  
  @Nullable
  public String topPrivateDomain()
  {
    if (Util.verifyAsIpAddress(this.host)) {
      return null;
    }
    return PublicSuffixDatabase.get().getEffectiveTldPlusOne(this.host);
  }
  
  public URI uri()
  {
    Object localObject = newBuilder().reencodeForUri().toString();
    try
    {
      URI localURI = new URI((String)localObject);
      return localURI;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      try
      {
        localObject = URI.create(((String)localObject).replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
        return (URI)localObject;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localURISyntaxException);
      }
    }
  }
  
  public URL url()
  {
    try
    {
      URL localURL = new URL(this.url);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new RuntimeException(localMalformedURLException);
    }
  }
  
  public String username()
  {
    return this.username;
  }
  
  public static final class Builder
  {
    static final String INVALID_HOST = "Invalid URL host";
    @Nullable
    String encodedFragment;
    String encodedPassword = "";
    final List<String> encodedPathSegments = new ArrayList();
    @Nullable
    List<String> encodedQueryNamesAndValues;
    String encodedUsername = "";
    @Nullable
    String host;
    int port = -1;
    @Nullable
    String scheme;
    
    public Builder()
    {
      this.encodedPathSegments.add("");
    }
    
    private Builder addPathSegments(String paramString, boolean paramBoolean)
    {
      int i = 0;
      int j;
      do
      {
        j = Util.delimiterOffset(paramString, i, paramString.length(), "/\\");
        boolean bool;
        if (j < paramString.length()) {
          bool = true;
        } else {
          bool = false;
        }
        push(paramString, i, j, bool, paramBoolean);
        j++;
        i = j;
      } while (j <= paramString.length());
      return this;
    }
    
    private static String canonicalizeHost(String paramString, int paramInt1, int paramInt2)
    {
      return Util.canonicalizeHost(HttpUrl.percentDecode(paramString, paramInt1, paramInt2, false));
    }
    
    private boolean isDot(String paramString)
    {
      boolean bool;
      if ((!paramString.equals(".")) && (!paramString.equalsIgnoreCase("%2e"))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    private boolean isDotDot(String paramString)
    {
      boolean bool;
      if ((!paramString.equals("..")) && (!paramString.equalsIgnoreCase("%2e.")) && (!paramString.equalsIgnoreCase(".%2e")) && (!paramString.equalsIgnoreCase("%2e%2e"))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    private static int parsePort(String paramString, int paramInt1, int paramInt2)
    {
      try
      {
        paramInt1 = Integer.parseInt(HttpUrl.canonicalize(paramString, paramInt1, paramInt2, "", false, false, false, true, null));
        if ((paramInt1 > 0) && (paramInt1 <= 65535)) {
          return paramInt1;
        }
        return -1;
      }
      catch (NumberFormatException paramString) {}
      return -1;
    }
    
    private void pop()
    {
      List localList = this.encodedPathSegments;
      if ((((String)localList.remove(localList.size() - 1)).isEmpty()) && (!this.encodedPathSegments.isEmpty()))
      {
        localList = this.encodedPathSegments;
        localList.set(localList.size() - 1, "");
      }
      else
      {
        this.encodedPathSegments.add("");
      }
    }
    
    private static int portColonOffset(String paramString, int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        int i = paramString.charAt(paramInt1);
        if (i != 58)
        {
          int j = paramInt1;
          if (i != 91)
          {
            j = paramInt1;
          }
          else
          {
            do
            {
              paramInt1 = j + 1;
              j = paramInt1;
              if (paramInt1 >= paramInt2) {
                break;
              }
              j = paramInt1;
            } while (paramString.charAt(paramInt1) != ']');
            j = paramInt1;
          }
          paramInt1 = j + 1;
        }
        else
        {
          return paramInt1;
        }
      }
      return paramInt2;
    }
    
    private void push(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      paramString = HttpUrl.canonicalize(paramString, paramInt1, paramInt2, " \"<>^`{}|/\\?#", paramBoolean2, false, false, true, null);
      if (isDot(paramString)) {
        return;
      }
      if (isDotDot(paramString))
      {
        pop();
        return;
      }
      List localList = this.encodedPathSegments;
      if (((String)localList.get(localList.size() - 1)).isEmpty())
      {
        localList = this.encodedPathSegments;
        localList.set(localList.size() - 1, paramString);
      }
      else
      {
        this.encodedPathSegments.add(paramString);
      }
      if (paramBoolean1) {
        this.encodedPathSegments.add("");
      }
    }
    
    private void removeAllCanonicalQueryParameters(String paramString)
    {
      for (int i = this.encodedQueryNamesAndValues.size() - 2; i >= 0; i -= 2) {
        if (paramString.equals(this.encodedQueryNamesAndValues.get(i)))
        {
          this.encodedQueryNamesAndValues.remove(i + 1);
          this.encodedQueryNamesAndValues.remove(i);
          if (this.encodedQueryNamesAndValues.isEmpty())
          {
            this.encodedQueryNamesAndValues = null;
            return;
          }
        }
      }
    }
    
    private void resolvePath(String paramString, int paramInt1, int paramInt2)
    {
      if (paramInt1 == paramInt2) {
        return;
      }
      int i = paramString.charAt(paramInt1);
      if ((i != 47) && (i != 92))
      {
        List localList = this.encodedPathSegments;
        localList.set(localList.size() - 1, "");
      }
      else
      {
        this.encodedPathSegments.clear();
        this.encodedPathSegments.add("");
        paramInt1++;
      }
      while (paramInt1 < paramInt2)
      {
        i = Util.delimiterOffset(paramString, paramInt1, paramInt2, "/\\");
        boolean bool;
        if (i < paramInt2) {
          bool = true;
        } else {
          bool = false;
        }
        push(paramString, paramInt1, i, bool, true);
        paramInt1 = i;
        if (bool) {
          paramInt1 = i + 1;
        }
      }
    }
    
    private static int schemeDelimiterOffset(String paramString, int paramInt1, int paramInt2)
    {
      if (paramInt2 - paramInt1 < 2) {
        return -1;
      }
      int i = paramString.charAt(paramInt1);
      int j;
      if (i >= 97)
      {
        j = paramInt1;
        if (i <= 122) {}
      }
      else
      {
        if (i < 65) {
          break label151;
        }
        j = paramInt1;
        if (i > 90) {
          break label151;
        }
      }
      for (;;)
      {
        paramInt1 = j + 1;
        if (paramInt1 >= paramInt2) {
          break label149;
        }
        i = paramString.charAt(paramInt1);
        if (i >= 97)
        {
          j = paramInt1;
          if (i <= 122) {}
        }
        else if (i >= 65)
        {
          j = paramInt1;
          if (i <= 90) {}
        }
        else if (i >= 48)
        {
          j = paramInt1;
          if (i <= 57) {}
        }
        else
        {
          j = paramInt1;
          if (i != 43)
          {
            j = paramInt1;
            if (i != 45)
            {
              if (i != 46) {
                break;
              }
              j = paramInt1;
            }
          }
        }
      }
      if (i == 58) {
        return paramInt1;
      }
      return -1;
      label149:
      return -1;
      label151:
      return -1;
    }
    
    private static int slashCount(String paramString, int paramInt1, int paramInt2)
    {
      int i = 0;
      while (paramInt1 < paramInt2)
      {
        int j = paramString.charAt(paramInt1);
        if ((j != 92) && (j != 47)) {
          break;
        }
        i++;
        paramInt1++;
      }
      return i;
    }
    
    public Builder addEncodedPathSegment(String paramString)
    {
      if (paramString != null)
      {
        push(paramString, 0, paramString.length(), false, true);
        return this;
      }
      throw new NullPointerException("encodedPathSegment == null");
    }
    
    public Builder addEncodedPathSegments(String paramString)
    {
      if (paramString != null) {
        return addPathSegments(paramString, true);
      }
      throw new NullPointerException("encodedPathSegments == null");
    }
    
    public Builder addEncodedQueryParameter(String paramString1, @Nullable String paramString2)
    {
      if (paramString1 != null)
      {
        if (this.encodedQueryNamesAndValues == null) {
          this.encodedQueryNamesAndValues = new ArrayList();
        }
        this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(paramString1, " \"'<>#&=", true, false, true, true));
        List localList = this.encodedQueryNamesAndValues;
        if (paramString2 != null) {
          paramString1 = HttpUrl.canonicalize(paramString2, " \"'<>#&=", true, false, true, true);
        } else {
          paramString1 = null;
        }
        localList.add(paramString1);
        return this;
      }
      throw new NullPointerException("encodedName == null");
    }
    
    public Builder addPathSegment(String paramString)
    {
      if (paramString != null)
      {
        push(paramString, 0, paramString.length(), false, false);
        return this;
      }
      throw new NullPointerException("pathSegment == null");
    }
    
    public Builder addPathSegments(String paramString)
    {
      if (paramString != null) {
        return addPathSegments(paramString, false);
      }
      throw new NullPointerException("pathSegments == null");
    }
    
    public Builder addQueryParameter(String paramString1, @Nullable String paramString2)
    {
      if (paramString1 != null)
      {
        if (this.encodedQueryNamesAndValues == null) {
          this.encodedQueryNamesAndValues = new ArrayList();
        }
        this.encodedQueryNamesAndValues.add(HttpUrl.canonicalize(paramString1, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
        List localList = this.encodedQueryNamesAndValues;
        if (paramString2 != null) {
          paramString1 = HttpUrl.canonicalize(paramString2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true);
        } else {
          paramString1 = null;
        }
        localList.add(paramString1);
        return this;
      }
      throw new NullPointerException("name == null");
    }
    
    public HttpUrl build()
    {
      if (this.scheme != null)
      {
        if (this.host != null) {
          return new HttpUrl(this);
        }
        throw new IllegalStateException("host == null");
      }
      throw new IllegalStateException("scheme == null");
    }
    
    int effectivePort()
    {
      int i = this.port;
      if (i == -1) {
        i = HttpUrl.defaultPort(this.scheme);
      }
      return i;
    }
    
    public Builder encodedFragment(@Nullable String paramString)
    {
      if (paramString != null) {
        paramString = HttpUrl.canonicalize(paramString, "", true, false, false, false);
      } else {
        paramString = null;
      }
      this.encodedFragment = paramString;
      return this;
    }
    
    public Builder encodedPassword(String paramString)
    {
      if (paramString != null)
      {
        this.encodedPassword = HttpUrl.canonicalize(paramString, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
        return this;
      }
      throw new NullPointerException("encodedPassword == null");
    }
    
    public Builder encodedPath(String paramString)
    {
      if (paramString != null)
      {
        if (paramString.startsWith("/"))
        {
          resolvePath(paramString, 0, paramString.length());
          return this;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("unexpected encodedPath: ");
        localStringBuilder.append(paramString);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      throw new NullPointerException("encodedPath == null");
    }
    
    public Builder encodedQuery(@Nullable String paramString)
    {
      if (paramString != null) {
        paramString = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(paramString, " \"'<>#", true, false, true, true));
      } else {
        paramString = null;
      }
      this.encodedQueryNamesAndValues = paramString;
      return this;
    }
    
    public Builder encodedUsername(String paramString)
    {
      if (paramString != null)
      {
        this.encodedUsername = HttpUrl.canonicalize(paramString, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
        return this;
      }
      throw new NullPointerException("encodedUsername == null");
    }
    
    public Builder fragment(@Nullable String paramString)
    {
      if (paramString != null) {
        paramString = HttpUrl.canonicalize(paramString, "", false, false, false, false);
      } else {
        paramString = null;
      }
      this.encodedFragment = paramString;
      return this;
    }
    
    public Builder host(String paramString)
    {
      if (paramString != null)
      {
        Object localObject = canonicalizeHost(paramString, 0, paramString.length());
        if (localObject != null)
        {
          this.host = ((String)localObject);
          return this;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("unexpected host: ");
        ((StringBuilder)localObject).append(paramString);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
      throw new NullPointerException("host == null");
    }
    
    Builder parse(@Nullable HttpUrl paramHttpUrl, String paramString)
    {
      int i = Util.skipLeadingAsciiWhitespace(paramString, 0, paramString.length());
      int j = Util.skipTrailingAsciiWhitespace(paramString, i, paramString.length());
      int k = schemeDelimiterOffset(paramString, i, j);
      if (k != -1)
      {
        if (paramString.regionMatches(true, i, "https:", 0, 6))
        {
          this.scheme = "https";
          i += 6;
        }
        else if (paramString.regionMatches(true, i, "http:", 0, 5))
        {
          this.scheme = "http";
          i += 5;
        }
        else
        {
          paramHttpUrl = new StringBuilder();
          paramHttpUrl.append("Expected URL scheme 'http' or 'https' but was '");
          paramHttpUrl.append(paramString.substring(0, k));
          paramHttpUrl.append("'");
          throw new IllegalArgumentException(paramHttpUrl.toString());
        }
      }
      else
      {
        if (paramHttpUrl == null) {
          break label861;
        }
        this.scheme = paramHttpUrl.scheme;
      }
      k = slashCount(paramString, i, j);
      int m;
      if ((k < 2) && (paramHttpUrl != null) && (paramHttpUrl.scheme.equals(this.scheme)))
      {
        this.encodedUsername = paramHttpUrl.encodedUsername();
        this.encodedPassword = paramHttpUrl.encodedPassword();
        this.host = paramHttpUrl.host;
        this.port = paramHttpUrl.port;
        this.encodedPathSegments.clear();
        this.encodedPathSegments.addAll(paramHttpUrl.encodedPathSegments());
        if (i != j)
        {
          k = i;
          if (paramString.charAt(i) != '#') {}
        }
        else
        {
          encodedQuery(paramHttpUrl.encodedQuery());
          k = i;
        }
      }
      else
      {
        m = i + k;
        i = 0;
        k = 0;
        for (;;)
        {
          n = Util.delimiterOffset(paramString, m, j, "@/\\?#");
          int i1;
          if (n != j) {
            i1 = paramString.charAt(n);
          } else {
            i1 = -1;
          }
          if ((i1 == -1) || (i1 == 35) || (i1 == 47) || (i1 == 92)) {
            break;
          }
          switch (i1)
          {
          default: 
            break;
          case 64: 
            if (i == 0)
            {
              int i2 = Util.delimiterOffset(paramString, m, n, ':');
              i1 = n;
              String str = HttpUrl.canonicalize(paramString, m, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
              paramHttpUrl = str;
              if (k != 0)
              {
                paramHttpUrl = new StringBuilder();
                paramHttpUrl.append(this.encodedUsername);
                paramHttpUrl.append("%40");
                paramHttpUrl.append(str);
                paramHttpUrl = paramHttpUrl.toString();
              }
              this.encodedUsername = paramHttpUrl;
              if (i2 != i1)
              {
                this.encodedPassword = HttpUrl.canonicalize(paramString, i2 + 1, i1, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                i = 1;
              }
              k = 1;
            }
            else
            {
              paramHttpUrl = new StringBuilder();
              paramHttpUrl.append(this.encodedPassword);
              paramHttpUrl.append("%40");
              paramHttpUrl.append(HttpUrl.canonicalize(paramString, m, n, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null));
              this.encodedPassword = paramHttpUrl.toString();
            }
            m = n + 1;
          }
        }
        i = portColonOffset(paramString, m, n);
        k = i + 1;
        if (k < n)
        {
          this.host = canonicalizeHost(paramString, m, i);
          this.port = parsePort(paramString, k, n);
          if (this.port == -1)
          {
            paramHttpUrl = new StringBuilder();
            paramHttpUrl.append("Invalid URL port: \"");
            paramHttpUrl.append(paramString.substring(k, n));
            paramHttpUrl.append('"');
            throw new IllegalArgumentException(paramHttpUrl.toString());
          }
        }
        else
        {
          this.host = canonicalizeHost(paramString, m, i);
          this.port = HttpUrl.defaultPort(this.scheme);
        }
        if (this.host == null) {
          break label814;
        }
        k = n;
      }
      int n = Util.delimiterOffset(paramString, k, j, "?#");
      resolvePath(paramString, k, n);
      i = n;
      if (n < j)
      {
        i = n;
        if (paramString.charAt(n) == '?')
        {
          i = Util.delimiterOffset(paramString, n, j, '#');
          this.encodedQueryNamesAndValues = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(paramString, n + 1, i, " \"'<>#", true, false, true, true, null));
        }
      }
      if ((i < j) && (paramString.charAt(i) == '#')) {
        this.encodedFragment = HttpUrl.canonicalize(paramString, 1 + i, j, "", true, false, false, false, null);
      }
      return this;
      label814:
      paramHttpUrl = new StringBuilder();
      paramHttpUrl.append("Invalid URL host: \"");
      paramHttpUrl.append(paramString.substring(m, i));
      paramHttpUrl.append('"');
      throw new IllegalArgumentException(paramHttpUrl.toString());
      label861:
      throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
    }
    
    public Builder password(String paramString)
    {
      if (paramString != null)
      {
        this.encodedPassword = HttpUrl.canonicalize(paramString, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        return this;
      }
      throw new NullPointerException("password == null");
    }
    
    public Builder port(int paramInt)
    {
      if ((paramInt > 0) && (paramInt <= 65535))
      {
        this.port = paramInt;
        return this;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("unexpected port: ");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    public Builder query(@Nullable String paramString)
    {
      if (paramString != null) {
        paramString = HttpUrl.queryStringToNamesAndValues(HttpUrl.canonicalize(paramString, " \"'<>#", false, false, true, true));
      } else {
        paramString = null;
      }
      this.encodedQueryNamesAndValues = paramString;
      return this;
    }
    
    Builder reencodeForUri()
    {
      int i = this.encodedPathSegments.size();
      int j = 0;
      for (int k = 0; k < i; k++)
      {
        localObject = (String)this.encodedPathSegments.get(k);
        this.encodedPathSegments.set(k, HttpUrl.canonicalize((String)localObject, "[]", true, true, false, true));
      }
      Object localObject = this.encodedQueryNamesAndValues;
      if (localObject != null)
      {
        i = ((List)localObject).size();
        for (k = j; k < i; k++)
        {
          localObject = (String)this.encodedQueryNamesAndValues.get(k);
          if (localObject != null) {
            this.encodedQueryNamesAndValues.set(k, HttpUrl.canonicalize((String)localObject, "\\^`{|}", true, true, true, true));
          }
        }
      }
      localObject = this.encodedFragment;
      if (localObject != null) {
        this.encodedFragment = HttpUrl.canonicalize((String)localObject, " \"#<>\\^`{|}", true, true, false, false);
      }
      return this;
    }
    
    public Builder removeAllEncodedQueryParameters(String paramString)
    {
      if (paramString != null)
      {
        if (this.encodedQueryNamesAndValues == null) {
          return this;
        }
        removeAllCanonicalQueryParameters(HttpUrl.canonicalize(paramString, " \"'<>#&=", true, false, true, true));
        return this;
      }
      throw new NullPointerException("encodedName == null");
    }
    
    public Builder removeAllQueryParameters(String paramString)
    {
      if (paramString != null)
      {
        if (this.encodedQueryNamesAndValues == null) {
          return this;
        }
        removeAllCanonicalQueryParameters(HttpUrl.canonicalize(paramString, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
        return this;
      }
      throw new NullPointerException("name == null");
    }
    
    public Builder removePathSegment(int paramInt)
    {
      this.encodedPathSegments.remove(paramInt);
      if (this.encodedPathSegments.isEmpty()) {
        this.encodedPathSegments.add("");
      }
      return this;
    }
    
    public Builder scheme(String paramString)
    {
      if (paramString != null)
      {
        if (paramString.equalsIgnoreCase("http"))
        {
          this.scheme = "http";
        }
        else
        {
          if (!paramString.equalsIgnoreCase("https")) {
            break label43;
          }
          this.scheme = "https";
        }
        return this;
        label43:
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("unexpected scheme: ");
        localStringBuilder.append(paramString);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      throw new NullPointerException("scheme == null");
    }
    
    public Builder setEncodedPathSegment(int paramInt, String paramString)
    {
      if (paramString != null)
      {
        Object localObject = HttpUrl.canonicalize(paramString, 0, paramString.length(), " \"<>^`{}|/\\?#", true, false, false, true, null);
        this.encodedPathSegments.set(paramInt, localObject);
        if ((!isDot((String)localObject)) && (!isDotDot((String)localObject))) {
          return this;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("unexpected path segment: ");
        ((StringBuilder)localObject).append(paramString);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
      throw new NullPointerException("encodedPathSegment == null");
    }
    
    public Builder setEncodedQueryParameter(String paramString1, @Nullable String paramString2)
    {
      removeAllEncodedQueryParameters(paramString1);
      addEncodedQueryParameter(paramString1, paramString2);
      return this;
    }
    
    public Builder setPathSegment(int paramInt, String paramString)
    {
      if (paramString != null)
      {
        Object localObject = HttpUrl.canonicalize(paramString, 0, paramString.length(), " \"<>^`{}|/\\?#", false, false, false, true, null);
        if ((!isDot((String)localObject)) && (!isDotDot((String)localObject)))
        {
          this.encodedPathSegments.set(paramInt, localObject);
          return this;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("unexpected path segment: ");
        ((StringBuilder)localObject).append(paramString);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
      throw new NullPointerException("pathSegment == null");
    }
    
    public Builder setQueryParameter(String paramString1, @Nullable String paramString2)
    {
      removeAllQueryParameters(paramString1);
      addQueryParameter(paramString1, paramString2);
      return this;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = this.scheme;
      if (str != null)
      {
        localStringBuilder.append(str);
        localStringBuilder.append("://");
      }
      else
      {
        localStringBuilder.append("//");
      }
      if ((!this.encodedUsername.isEmpty()) || (!this.encodedPassword.isEmpty()))
      {
        localStringBuilder.append(this.encodedUsername);
        if (!this.encodedPassword.isEmpty())
        {
          localStringBuilder.append(':');
          localStringBuilder.append(this.encodedPassword);
        }
        localStringBuilder.append('@');
      }
      str = this.host;
      if (str != null) {
        if (str.indexOf(':') != -1)
        {
          localStringBuilder.append('[');
          localStringBuilder.append(this.host);
          localStringBuilder.append(']');
        }
        else
        {
          localStringBuilder.append(this.host);
        }
      }
      if ((this.port != -1) || (this.scheme != null))
      {
        int i = effectivePort();
        str = this.scheme;
        if ((str == null) || (i != HttpUrl.defaultPort(str)))
        {
          localStringBuilder.append(':');
          localStringBuilder.append(i);
        }
      }
      HttpUrl.pathSegmentsToString(localStringBuilder, this.encodedPathSegments);
      if (this.encodedQueryNamesAndValues != null)
      {
        localStringBuilder.append('?');
        HttpUrl.namesAndValuesToQueryString(localStringBuilder, this.encodedQueryNamesAndValues);
      }
      if (this.encodedFragment != null)
      {
        localStringBuilder.append('#');
        localStringBuilder.append(this.encodedFragment);
      }
      return localStringBuilder.toString();
    }
    
    public Builder username(String paramString)
    {
      if (paramString != null)
      {
        this.encodedUsername = HttpUrl.canonicalize(paramString, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        return this;
      }
      throw new NullPointerException("username == null");
    }
  }
}


/* Location:              ~/okhttp3/HttpUrl.class
 *
 * Reversed by:           J
 */