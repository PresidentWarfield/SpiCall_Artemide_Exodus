package com.app.system.streaming.d;

import com.security.d.a;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class b
{
  private HashMap<String, Long> a = new HashMap();
  private final RandomAccessFile b;
  private long c = 0L;
  
  private b(String paramString)
  {
    this.b = new RandomAccessFile(new File(paramString), "r");
    try
    {
      a("", this.b.length());
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      throw new IOException("Parse error: malformed mp4 file");
    }
  }
  
  public static b a(String paramString)
  {
    return new b(paramString);
  }
  
  static String a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
      Object localObject = str;
      if (str.length() < 2)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("0");
        ((StringBuilder)localObject).append(str);
        localObject = ((StringBuilder)localObject).toString();
      }
      localStringBuilder.append((String)localObject);
    }
    return localStringBuilder.toString();
  }
  
  private void a(String paramString, long paramLong)
  {
    byte[] arrayOfByte = new byte[8];
    if (!paramString.equals("")) {
      this.a.put(paramString, Long.valueOf(this.c - 8L));
    }
    long l1 = 0L;
    while (l1 < paramLong)
    {
      this.b.read(arrayOfByte, 0, 8);
      this.c += 8L;
      long l2 = l1 + 8L;
      Object localObject;
      if (a(arrayOfByte))
      {
        localObject = new String(arrayOfByte, 4, 4);
        if (arrayOfByte[3] == 1)
        {
          this.b.read(arrayOfByte, 0, 8);
          this.c += 8L;
          l2 += 8L;
          l1 = ByteBuffer.wrap(arrayOfByte, 0, 8).getLong() - 16L;
        }
        else
        {
          l1 = ByteBuffer.wrap(arrayOfByte, 0, 4).getInt() - 8;
        }
        if ((l1 >= 0L) && (l1 != 1061109559L))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Atom -> name: ");
          localStringBuilder.append((String)localObject);
          localStringBuilder.append(" position: ");
          localStringBuilder.append(this.c);
          localStringBuilder.append(", length: ");
          localStringBuilder.append(l1);
          a.d("MP4Parser", localStringBuilder.toString(), new Object[0]);
          l2 += l1;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramString);
          localStringBuilder.append('/');
          localStringBuilder.append((String)localObject);
          a(localStringBuilder.toString(), l1);
          l1 = l2;
        }
        else
        {
          throw new IOException();
        }
      }
      else if (paramLong < 8L)
      {
        localObject = this.b;
        ((RandomAccessFile)localObject).seek(((RandomAccessFile)localObject).getFilePointer() - 8L + paramLong);
        l1 = l2 + (paramLong - 8L);
      }
      else
      {
        localObject = this.b;
        l1 = paramLong - 8L;
        int i = (int)l1;
        if (((RandomAccessFile)localObject).skipBytes(i) >= i)
        {
          this.c += l1;
          l1 = l2 + l1;
        }
        else
        {
          throw new IOException();
        }
      }
    }
  }
  
  private boolean a(byte[] paramArrayOfByte)
  {
    int i = 0;
    while (i < 4)
    {
      int j = i + 4;
      if (((paramArrayOfByte[j] >= 97) && (paramArrayOfByte[j] <= 122)) || ((paramArrayOfByte[j] >= 48) && (paramArrayOfByte[j] <= 57))) {
        i++;
      } else {
        return false;
      }
    }
    return true;
  }
  
  public void a()
  {
    try
    {
      this.b.close();
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public long b(String paramString)
  {
    if ((Long)this.a.get(paramString) != null) {
      return ((Long)this.a.get(paramString)).longValue();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Box not found: ");
    localStringBuilder.append(paramString);
    throw new IOException(localStringBuilder.toString());
  }
  
  public c b()
  {
    try
    {
      c localc = new c(this.b, b("/moov/trak/mdia/minf/stbl/stsd"));
      return localc;
    }
    catch (IOException localIOException)
    {
      throw new IOException("stsd box could not be found");
    }
  }
}


/* Location:              ~/com/app/system/streaming/d/b.class
 *
 * Reversed by:           J
 */