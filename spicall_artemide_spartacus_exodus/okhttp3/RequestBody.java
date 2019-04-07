package okhttp3;

import a.d;
import a.f;
import a.l;
import a.s;
import java.io.Closeable;
import java.io.File;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public abstract class RequestBody
{
  public static RequestBody create(@Nullable MediaType paramMediaType, final f paramf)
  {
    new RequestBody()
    {
      public long contentLength()
      {
        return paramf.h();
      }
      
      @Nullable
      public MediaType contentType()
      {
        return RequestBody.this;
      }
      
      public void writeTo(d paramAnonymousd)
      {
        paramAnonymousd.c(paramf);
      }
    };
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, final File paramFile)
  {
    if (paramFile != null) {
      new RequestBody()
      {
        public long contentLength()
        {
          return paramFile.length();
        }
        
        @Nullable
        public MediaType contentType()
        {
          return RequestBody.this;
        }
        
        public void writeTo(d paramAnonymousd)
        {
          Object localObject = null;
          try
          {
            s locals = l.a(paramFile);
            localObject = locals;
            paramAnonymousd.a(locals);
            Util.closeQuietly(locals);
            return;
          }
          finally
          {
            Util.closeQuietly((Closeable)localObject);
          }
        }
      };
    }
    throw new NullPointerException("file == null");
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, String paramString)
  {
    Object localObject1 = Util.UTF_8;
    Object localObject2 = paramMediaType;
    if (paramMediaType != null)
    {
      Charset localCharset = paramMediaType.charset();
      localObject1 = localCharset;
      localObject2 = paramMediaType;
      if (localCharset == null)
      {
        localObject1 = Util.UTF_8;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramMediaType);
        ((StringBuilder)localObject2).append("; charset=utf-8");
        localObject2 = MediaType.parse(((StringBuilder)localObject2).toString());
      }
    }
    return create((MediaType)localObject2, paramString.getBytes((Charset)localObject1));
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    return create(paramMediaType, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static RequestBody create(@Nullable MediaType paramMediaType, final byte[] paramArrayOfByte, final int paramInt1, final int paramInt2)
  {
    if (paramArrayOfByte != null)
    {
      Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
      new RequestBody()
      {
        public long contentLength()
        {
          return paramInt2;
        }
        
        @Nullable
        public MediaType contentType()
        {
          return RequestBody.this;
        }
        
        public void writeTo(d paramAnonymousd)
        {
          paramAnonymousd.c(paramArrayOfByte, paramInt1, paramInt2);
        }
      };
    }
    throw new NullPointerException("content == null");
  }
  
  public long contentLength()
  {
    return -1L;
  }
  
  @Nullable
  public abstract MediaType contentType();
  
  public abstract void writeTo(d paramd);
}


/* Location:              ~/okhttp3/RequestBody.class
 *
 * Reversed by:           J
 */