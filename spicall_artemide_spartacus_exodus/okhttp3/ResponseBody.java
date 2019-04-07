package okhttp3;

import a.c;
import a.e;
import a.f;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public abstract class ResponseBody
  implements Closeable
{
  @Nullable
  private Reader reader;
  
  private Charset charset()
  {
    Object localObject = contentType();
    if (localObject != null) {
      localObject = ((MediaType)localObject).charset(Util.UTF_8);
    } else {
      localObject = Util.UTF_8;
    }
    return (Charset)localObject;
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, final long paramLong, e parame)
  {
    if (parame != null) {
      new ResponseBody()
      {
        public long contentLength()
        {
          return paramLong;
        }
        
        @Nullable
        public MediaType contentType()
        {
          return ResponseBody.this;
        }
        
        public e source()
        {
          return this.val$content;
        }
      };
    }
    throw new NullPointerException("source == null");
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, f paramf)
  {
    c localc = new c().a(paramf);
    return create(paramMediaType, paramf.h(), localc);
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, String paramString)
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
    paramMediaType = new c().a(paramString, (Charset)localObject1);
    return create((MediaType)localObject2, paramMediaType.a(), paramMediaType);
  }
  
  public static ResponseBody create(@Nullable MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    c localc = new c().b(paramArrayOfByte);
    return create(paramMediaType, paramArrayOfByte.length, localc);
  }
  
  public final InputStream byteStream()
  {
    return source().f();
  }
  
  public final byte[] bytes()
  {
    long l = contentLength();
    if (l <= 2147483647L)
    {
      localObject1 = source();
      try
      {
        byte[] arrayOfByte = ((e)localObject1).s();
        Util.closeQuietly((Closeable)localObject1);
        if ((l != -1L) && (l != arrayOfByte.length))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Content-Length (");
          ((StringBuilder)localObject1).append(l);
          ((StringBuilder)localObject1).append(") and stream length (");
          ((StringBuilder)localObject1).append(arrayOfByte.length);
          ((StringBuilder)localObject1).append(") disagree");
          throw new IOException(((StringBuilder)localObject1).toString());
        }
        return arrayOfByte;
      }
      finally
      {
        Util.closeQuietly((Closeable)localObject1);
      }
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Cannot buffer entire body for content length: ");
    ((StringBuilder)localObject1).append(l);
    throw new IOException(((StringBuilder)localObject1).toString());
  }
  
  public final Reader charStream()
  {
    Object localObject = this.reader;
    if (localObject == null)
    {
      localObject = new BomAwareReader(source(), charset());
      this.reader = ((Reader)localObject);
    }
    return (Reader)localObject;
  }
  
  public void close()
  {
    Util.closeQuietly(source());
  }
  
  public abstract long contentLength();
  
  @Nullable
  public abstract MediaType contentType();
  
  public abstract e source();
  
  public final String string()
  {
    e locale = source();
    try
    {
      String str = locale.a(Util.bomAwareCharset(locale, charset()));
      return str;
    }
    finally
    {
      Util.closeQuietly(locale);
    }
  }
  
  static final class BomAwareReader
    extends Reader
  {
    private final Charset charset;
    private boolean closed;
    @Nullable
    private Reader delegate;
    private final e source;
    
    BomAwareReader(e parame, Charset paramCharset)
    {
      this.source = parame;
      this.charset = paramCharset;
    }
    
    public void close()
    {
      this.closed = true;
      Reader localReader = this.delegate;
      if (localReader != null) {
        localReader.close();
      } else {
        this.source.close();
      }
    }
    
    public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      if (!this.closed)
      {
        Reader localReader = this.delegate;
        Object localObject = localReader;
        if (localReader == null)
        {
          localObject = Util.bomAwareCharset(this.source, this.charset);
          localObject = new InputStreamReader(this.source.f(), (Charset)localObject);
          this.delegate = ((Reader)localObject);
        }
        return ((Reader)localObject).read(paramArrayOfChar, paramInt1, paramInt2);
      }
      throw new IOException("Stream closed");
    }
  }
}


/* Location:              ~/okhttp3/ResponseBody.class
 *
 * Reversed by:           J
 */