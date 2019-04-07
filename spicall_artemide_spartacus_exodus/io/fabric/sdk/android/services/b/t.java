package io.fabric.sdk.android.services.b;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class t
  implements Closeable
{
  private static final Logger b = Logger.getLogger(t.class.getName());
  int a;
  private final RandomAccessFile c;
  private int d;
  private a e;
  private a f;
  private final byte[] g = new byte[16];
  
  public t(File paramFile)
  {
    if (!paramFile.exists()) {
      a(paramFile);
    }
    this.c = b(paramFile);
    e();
  }
  
  private static int a(byte[] paramArrayOfByte, int paramInt)
  {
    return ((paramArrayOfByte[paramInt] & 0xFF) << 24) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8) + (paramArrayOfByte[(paramInt + 3)] & 0xFF);
  }
  
  private a a(int paramInt)
  {
    if (paramInt == 0) {
      return a.a;
    }
    this.c.seek(paramInt);
    return new a(paramInt, this.c.readInt());
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    a(this.g, new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
    this.c.seek(0L);
    this.c.write(this.g);
  }
  
  private void a(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    paramInt1 = b(paramInt1);
    int i = this.a;
    if (paramInt1 + paramInt3 <= i)
    {
      this.c.seek(paramInt1);
      this.c.write(paramArrayOfByte, paramInt2, paramInt3);
    }
    else
    {
      i -= paramInt1;
      this.c.seek(paramInt1);
      this.c.write(paramArrayOfByte, paramInt2, i);
      this.c.seek(16L);
      this.c.write(paramArrayOfByte, paramInt2 + i, paramInt3 - i);
    }
  }
  
  private static void a(File paramFile)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramFile.getPath());
    ((StringBuilder)localObject).append(".tmp");
    File localFile = new File(((StringBuilder)localObject).toString());
    localObject = b(localFile);
    try
    {
      ((RandomAccessFile)localObject).setLength(4096L);
      ((RandomAccessFile)localObject).seek(0L);
      byte[] arrayOfByte = new byte[16];
      a(arrayOfByte, new int[] { 4096, 0, 0, 0 });
      ((RandomAccessFile)localObject).write(arrayOfByte);
      ((RandomAccessFile)localObject).close();
      if (localFile.renameTo(paramFile)) {
        return;
      }
      throw new IOException("Rename failed!");
    }
    finally
    {
      ((RandomAccessFile)localObject).close();
    }
  }
  
  private static void a(byte[] paramArrayOfByte, int... paramVarArgs)
  {
    int i = paramVarArgs.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      b(paramArrayOfByte, k, paramVarArgs[j]);
      k += 4;
      j++;
    }
  }
  
  private int b(int paramInt)
  {
    int i = this.a;
    if (paramInt >= i) {
      paramInt = paramInt + 16 - i;
    }
    return paramInt;
  }
  
  private static RandomAccessFile b(File paramFile)
  {
    return new RandomAccessFile(paramFile, "rwd");
  }
  
  private static <T> T b(T paramT, String paramString)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException(paramString);
  }
  
  private void b(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    paramInt1 = b(paramInt1);
    int i = this.a;
    if (paramInt1 + paramInt3 <= i)
    {
      this.c.seek(paramInt1);
      this.c.readFully(paramArrayOfByte, paramInt2, paramInt3);
    }
    else
    {
      i -= paramInt1;
      this.c.seek(paramInt1);
      this.c.readFully(paramArrayOfByte, paramInt2, i);
      this.c.seek(16L);
      this.c.readFully(paramArrayOfByte, paramInt2 + i, paramInt3 - i);
    }
  }
  
  private static void b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[paramInt1] = ((byte)(byte)(paramInt2 >> 24));
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(byte)(paramInt2 >> 16));
    paramArrayOfByte[(paramInt1 + 2)] = ((byte)(byte)(paramInt2 >> 8));
    paramArrayOfByte[(paramInt1 + 3)] = ((byte)(byte)paramInt2);
  }
  
  private void c(int paramInt)
  {
    int i = paramInt + 4;
    int j = f();
    if (j >= i) {
      return;
    }
    paramInt = this.a;
    int k;
    int m;
    do
    {
      k = j + paramInt;
      m = paramInt << 1;
      j = k;
      paramInt = m;
    } while (k < i);
    d(m);
    paramInt = b(this.f.b + 4 + this.f.c);
    if (paramInt < this.e.b)
    {
      FileChannel localFileChannel = this.c.getChannel();
      localFileChannel.position(this.a);
      long l = paramInt - 4;
      if (localFileChannel.transferTo(16L, l, localFileChannel) != l) {
        throw new AssertionError("Copied insufficient number of bytes!");
      }
    }
    if (this.f.b < this.e.b)
    {
      paramInt = this.a + this.f.b - 16;
      a(m, this.d, this.e.b, paramInt);
      this.f = new a(paramInt, this.f.c);
    }
    else
    {
      a(m, this.d, this.e.b, this.f.b);
    }
    this.a = m;
  }
  
  private void d(int paramInt)
  {
    this.c.setLength(paramInt);
    this.c.getChannel().force(true);
  }
  
  private void e()
  {
    this.c.seek(0L);
    this.c.readFully(this.g);
    this.a = a(this.g, 0);
    if (this.a <= this.c.length())
    {
      this.d = a(this.g, 4);
      int i = a(this.g, 8);
      int j = a(this.g, 12);
      this.e = a(i);
      this.f = a(j);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("File is truncated. Expected length: ");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", Actual length: ");
    localStringBuilder.append(this.c.length());
    throw new IOException(localStringBuilder.toString());
  }
  
  private int f()
  {
    return this.a - a();
  }
  
  public int a()
  {
    if (this.d == 0) {
      return 16;
    }
    if (this.f.b >= this.e.b) {
      return this.f.b - this.e.b + 4 + this.f.c + 16;
    }
    return this.f.b + 4 + this.f.c + this.a - this.e.b;
  }
  
  public void a(c paramc)
  {
    try
    {
      int i = this.e.b;
      for (int j = 0; j < this.d; j++)
      {
        a locala = a(i);
        b localb = new io/fabric/sdk/android/services/b/t$b;
        localb.<init>(this, locala, null);
        paramc.read(localb, locala.c);
        i = b(locala.b + 4 + locala.c);
      }
      return;
    }
    finally {}
  }
  
  public void a(byte[] paramArrayOfByte)
  {
    a(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      b(paramArrayOfByte, "buffer");
      if (((paramInt1 | paramInt2) >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt1))
      {
        c(paramInt2);
        boolean bool = b();
        int i;
        if (bool) {
          i = 16;
        } else {
          i = b(this.f.b + 4 + this.f.c);
        }
        a locala = new io/fabric/sdk/android/services/b/t$a;
        locala.<init>(i, paramInt2);
        b(this.g, 0, paramInt2);
        a(locala.b, this.g, 0, 4);
        a(locala.b + 4, paramArrayOfByte, paramInt1, paramInt2);
        if (bool) {
          paramInt1 = locala.b;
        } else {
          paramInt1 = this.e.b;
        }
        a(this.a, this.d + 1, paramInt1, locala.b);
        this.f = locala;
        this.d += 1;
        if (bool) {
          this.e = this.f;
        }
        return;
      }
      paramArrayOfByte = new java/lang/IndexOutOfBoundsException;
      paramArrayOfByte.<init>();
      throw paramArrayOfByte;
    }
    finally {}
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    boolean bool;
    if (a() + 4 + paramInt1 <= paramInt2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean b()
  {
    try
    {
      int i = this.d;
      boolean bool;
      if (i == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void c()
  {
    try
    {
      if (!b())
      {
        if (this.d == 1)
        {
          d();
        }
        else
        {
          int i = b(this.e.b + 4 + this.e.c);
          b(i, this.g, 0, 4);
          int j = a(this.g, 0);
          a(this.a, this.d - 1, i, this.f.b);
          this.d -= 1;
          localObject1 = new io/fabric/sdk/android/services/b/t$a;
          ((a)localObject1).<init>(i, j);
          this.e = ((a)localObject1);
        }
        return;
      }
      Object localObject1 = new java/util/NoSuchElementException;
      ((NoSuchElementException)localObject1).<init>();
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public void close()
  {
    try
    {
      this.c.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void d()
  {
    try
    {
      a(4096, 0, 0, 0);
      this.d = 0;
      this.e = a.a;
      this.f = a.a;
      if (this.a > 4096) {
        d(4096);
      }
      this.a = 4096;
      return;
    }
    finally {}
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append('[');
    localStringBuilder.append("fileLength=");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", size=");
    localStringBuilder.append(this.d);
    localStringBuilder.append(", first=");
    localStringBuilder.append(this.e);
    localStringBuilder.append(", last=");
    localStringBuilder.append(this.f);
    localStringBuilder.append(", element lengths=[");
    try
    {
      c local1 = new io/fabric/sdk/android/services/b/t$1;
      local1.<init>(this, localStringBuilder);
      a(local1);
    }
    catch (IOException localIOException)
    {
      b.log(Level.WARNING, "read error", localIOException);
    }
    localStringBuilder.append("]]");
    return localStringBuilder.toString();
  }
  
  static class a
  {
    static final a a = new a(0, 0);
    final int b;
    final int c;
    
    a(int paramInt1, int paramInt2)
    {
      this.b = paramInt1;
      this.c = paramInt2;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getClass().getSimpleName());
      localStringBuilder.append("[position = ");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", length = ");
      localStringBuilder.append(this.c);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  private final class b
    extends InputStream
  {
    private int b;
    private int c;
    
    private b(t.a parama)
    {
      this.b = t.a(t.this, parama.b + 4);
      this.c = parama.c;
    }
    
    public int read()
    {
      if (this.c == 0) {
        return -1;
      }
      t.a(t.this).seek(this.b);
      int i = t.a(t.this).read();
      this.b = t.a(t.this, this.b + 1);
      this.c -= 1;
      return i;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      t.a(paramArrayOfByte, "buffer");
      if (((paramInt1 | paramInt2) >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt1))
      {
        int i = this.c;
        if (i > 0)
        {
          int j = paramInt2;
          if (paramInt2 > i) {
            j = i;
          }
          t.a(t.this, this.b, paramArrayOfByte, paramInt1, j);
          this.b = t.a(t.this, this.b + j);
          this.c -= j;
          return j;
        }
        return -1;
      }
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  
  public static abstract interface c
  {
    public abstract void read(InputStream paramInputStream, int paramInt);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/t.class
 *
 * Reversed by:           J
 */