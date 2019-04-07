package okhttp3;

import a.c;
import a.d;
import a.f;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public final class MultipartBody
  extends RequestBody
{
  public static final MediaType ALTERNATIVE;
  private static final byte[] COLONSPACE = { 58, 32 };
  private static final byte[] CRLF = { 13, 10 };
  private static final byte[] DASHDASH = { 45, 45 };
  public static final MediaType DIGEST;
  public static final MediaType FORM;
  public static final MediaType MIXED = MediaType.get("multipart/mixed");
  public static final MediaType PARALLEL;
  private final f boundary;
  private long contentLength = -1L;
  private final MediaType contentType;
  private final MediaType originalType;
  private final List<Part> parts;
  
  static
  {
    ALTERNATIVE = MediaType.get("multipart/alternative");
    DIGEST = MediaType.get("multipart/digest");
    PARALLEL = MediaType.get("multipart/parallel");
    FORM = MediaType.get("multipart/form-data");
  }
  
  MultipartBody(f paramf, MediaType paramMediaType, List<Part> paramList)
  {
    this.boundary = paramf;
    this.originalType = paramMediaType;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramMediaType);
    localStringBuilder.append("; boundary=");
    localStringBuilder.append(paramf.a());
    this.contentType = MediaType.get(localStringBuilder.toString());
    this.parts = Util.immutableList(paramList);
  }
  
  static StringBuilder appendQuotedString(StringBuilder paramStringBuilder, String paramString)
  {
    paramStringBuilder.append('"');
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (c != '\n')
      {
        if (c != '\r')
        {
          if (c != '"') {
            paramStringBuilder.append(c);
          } else {
            paramStringBuilder.append("%22");
          }
        }
        else {
          paramStringBuilder.append("%0D");
        }
      }
      else {
        paramStringBuilder.append("%0A");
      }
    }
    paramStringBuilder.append('"');
    return paramStringBuilder;
  }
  
  private long writeOrCountBytes(@Nullable d paramd, boolean paramBoolean)
  {
    d locald;
    if (paramBoolean)
    {
      paramd = new c();
      locald = paramd;
    }
    else
    {
      locald = null;
    }
    int i = this.parts.size();
    long l1 = 0L;
    for (int j = 0; j < i; j++)
    {
      Object localObject1 = (Part)this.parts.get(j);
      Object localObject2 = ((Part)localObject1).headers;
      localObject1 = ((Part)localObject1).body;
      paramd.c(DASHDASH);
      paramd.c(this.boundary);
      paramd.c(CRLF);
      if (localObject2 != null)
      {
        int k = ((Headers)localObject2).size();
        for (int m = 0; m < k; m++) {
          paramd.b(((Headers)localObject2).name(m)).c(COLONSPACE).b(((Headers)localObject2).value(m)).c(CRLF);
        }
      }
      localObject2 = ((RequestBody)localObject1).contentType();
      if (localObject2 != null) {
        paramd.b("Content-Type: ").b(((MediaType)localObject2).toString()).c(CRLF);
      }
      l2 = ((RequestBody)localObject1).contentLength();
      if (l2 != -1L)
      {
        paramd.b("Content-Length: ").n(l2).c(CRLF);
      }
      else if (paramBoolean)
      {
        locald.t();
        return -1L;
      }
      paramd.c(CRLF);
      if (paramBoolean) {
        l1 += l2;
      } else {
        ((RequestBody)localObject1).writeTo(paramd);
      }
      paramd.c(CRLF);
    }
    paramd.c(DASHDASH);
    paramd.c(this.boundary);
    paramd.c(DASHDASH);
    paramd.c(CRLF);
    long l2 = l1;
    if (paramBoolean)
    {
      l2 = l1 + locald.a();
      locald.t();
    }
    return l2;
  }
  
  public String boundary()
  {
    return this.boundary.a();
  }
  
  public long contentLength()
  {
    long l = this.contentLength;
    if (l != -1L) {
      return l;
    }
    l = writeOrCountBytes(null, true);
    this.contentLength = l;
    return l;
  }
  
  public MediaType contentType()
  {
    return this.contentType;
  }
  
  public Part part(int paramInt)
  {
    return (Part)this.parts.get(paramInt);
  }
  
  public List<Part> parts()
  {
    return this.parts;
  }
  
  public int size()
  {
    return this.parts.size();
  }
  
  public MediaType type()
  {
    return this.originalType;
  }
  
  public void writeTo(d paramd)
  {
    writeOrCountBytes(paramd, false);
  }
  
  public static final class Builder
  {
    private final f boundary;
    private final List<MultipartBody.Part> parts = new ArrayList();
    private MediaType type = MultipartBody.MIXED;
    
    public Builder()
    {
      this(UUID.randomUUID().toString());
    }
    
    public Builder(String paramString)
    {
      this.boundary = f.a(paramString);
    }
    
    public Builder addFormDataPart(String paramString1, String paramString2)
    {
      return addPart(MultipartBody.Part.createFormData(paramString1, paramString2));
    }
    
    public Builder addFormDataPart(String paramString1, @Nullable String paramString2, RequestBody paramRequestBody)
    {
      return addPart(MultipartBody.Part.createFormData(paramString1, paramString2, paramRequestBody));
    }
    
    public Builder addPart(@Nullable Headers paramHeaders, RequestBody paramRequestBody)
    {
      return addPart(MultipartBody.Part.create(paramHeaders, paramRequestBody));
    }
    
    public Builder addPart(MultipartBody.Part paramPart)
    {
      if (paramPart != null)
      {
        this.parts.add(paramPart);
        return this;
      }
      throw new NullPointerException("part == null");
    }
    
    public Builder addPart(RequestBody paramRequestBody)
    {
      return addPart(MultipartBody.Part.create(paramRequestBody));
    }
    
    public MultipartBody build()
    {
      if (!this.parts.isEmpty()) {
        return new MultipartBody(this.boundary, this.type, this.parts);
      }
      throw new IllegalStateException("Multipart body must have at least one part.");
    }
    
    public Builder setType(MediaType paramMediaType)
    {
      if (paramMediaType != null)
      {
        if (paramMediaType.type().equals("multipart"))
        {
          this.type = paramMediaType;
          return this;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("multipart != ");
        localStringBuilder.append(paramMediaType);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      throw new NullPointerException("type == null");
    }
  }
  
  public static final class Part
  {
    final RequestBody body;
    @Nullable
    final Headers headers;
    
    private Part(@Nullable Headers paramHeaders, RequestBody paramRequestBody)
    {
      this.headers = paramHeaders;
      this.body = paramRequestBody;
    }
    
    public static Part create(@Nullable Headers paramHeaders, RequestBody paramRequestBody)
    {
      if (paramRequestBody != null)
      {
        if ((paramHeaders != null) && (paramHeaders.get("Content-Type") != null)) {
          throw new IllegalArgumentException("Unexpected header: Content-Type");
        }
        if ((paramHeaders != null) && (paramHeaders.get("Content-Length") != null)) {
          throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
        return new Part(paramHeaders, paramRequestBody);
      }
      throw new NullPointerException("body == null");
    }
    
    public static Part create(RequestBody paramRequestBody)
    {
      return create(null, paramRequestBody);
    }
    
    public static Part createFormData(String paramString1, String paramString2)
    {
      return createFormData(paramString1, null, RequestBody.create(null, paramString2));
    }
    
    public static Part createFormData(String paramString1, @Nullable String paramString2, RequestBody paramRequestBody)
    {
      if (paramString1 != null)
      {
        StringBuilder localStringBuilder = new StringBuilder("form-data; name=");
        MultipartBody.appendQuotedString(localStringBuilder, paramString1);
        if (paramString2 != null)
        {
          localStringBuilder.append("; filename=");
          MultipartBody.appendQuotedString(localStringBuilder, paramString2);
        }
        return create(Headers.of(new String[] { "Content-Disposition", localStringBuilder.toString() }), paramRequestBody);
      }
      throw new NullPointerException("name == null");
    }
    
    public RequestBody body()
    {
      return this.body;
    }
    
    @Nullable
    public Headers headers()
    {
      return this.headers;
    }
  }
}


/* Location:              ~/okhttp3/MultipartBody.class
 *
 * Reversed by:           J
 */