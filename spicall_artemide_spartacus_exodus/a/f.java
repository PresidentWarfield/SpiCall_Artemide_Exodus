package a;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.annotation.Nullable;

public class f
  implements Serializable, Comparable<f>
{
  static final char[] a = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  public static final f b = a(new byte[0]);
  final byte[] c;
  transient int d;
  transient String e;
  
  f(byte[] paramArrayOfByte)
  {
    this.c = paramArrayOfByte;
  }
  
  private static int a(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    if ((paramChar >= 'a') && (paramChar <= 'f')) {
      return paramChar - 'a' + 10;
    }
    if ((paramChar >= 'A') && (paramChar <= 'F')) {
      return paramChar - 'A' + 10;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected hex digit: ");
    localStringBuilder.append(paramChar);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  static int a(String paramString, int paramInt)
  {
    int i = paramString.length();
    int j = 0;
    int k = 0;
    while (j < i)
    {
      if (k == paramInt) {
        return j;
      }
      int m = paramString.codePointAt(j);
      if (((Character.isISOControl(m)) && (m != 10) && (m != 13)) || (m == 65533)) {
        return -1;
      }
      k++;
      j += Character.charCount(m);
    }
    return paramString.length();
  }
  
  public static f a(String paramString)
  {
    if (paramString != null)
    {
      f localf = new f(paramString.getBytes(u.a));
      localf.e = paramString;
      return localf;
    }
    throw new IllegalArgumentException("s == null");
  }
  
  public static f a(String paramString, Charset paramCharset)
  {
    if (paramString != null)
    {
      if (paramCharset != null) {
        return new f(paramString.getBytes(paramCharset));
      }
      throw new IllegalArgumentException("charset == null");
    }
    throw new IllegalArgumentException("s == null");
  }
  
  public static f a(byte... paramVarArgs)
  {
    if (paramVarArgs != null) {
      return new f((byte[])paramVarArgs.clone());
    }
    throw new IllegalArgumentException("data == null");
  }
  
  @Nullable
  public static f b(String paramString)
  {
    if (paramString != null)
    {
      paramString = b.a(paramString);
      if (paramString != null) {
        paramString = new f(paramString);
      } else {
        paramString = null;
      }
      return paramString;
    }
    throw new IllegalArgumentException("base64 == null");
  }
  
  public static f c(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() % 2 == 0)
      {
        localObject = new byte[paramString.length() / 2];
        for (int i = 0; i < localObject.length; i++)
        {
          int j = i * 2;
          localObject[i] = ((byte)(byte)((a(paramString.charAt(j)) << 4) + a(paramString.charAt(j + 1))));
        }
        return a((byte[])localObject);
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unexpected hex string: ");
      ((StringBuilder)localObject).append(paramString);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    throw new IllegalArgumentException("hex == null");
  }
  
  private f d(String paramString)
  {
    try
    {
      paramString = a(MessageDigest.getInstance(paramString).digest(this.c));
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      throw new AssertionError(paramString);
    }
  }
  
  public byte a(int paramInt)
  {
    return this.c[paramInt];
  }
  
  public f a(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      Object localObject = this.c;
      if (paramInt2 <= localObject.length)
      {
        int i = paramInt2 - paramInt1;
        if (i >= 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == localObject.length)) {
            return this;
          }
          localObject = new byte[i];
          System.arraycopy(this.c, paramInt1, localObject, 0, i);
          return new f((byte[])localObject);
        }
        throw new IllegalArgumentException("endIndex < beginIndex");
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("endIndex > length(");
      ((StringBuilder)localObject).append(this.c.length);
      ((StringBuilder)localObject).append(")");
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    throw new IllegalArgumentException("beginIndex < 0");
  }
  
  public String a()
  {
    String str = this.e;
    if (str == null)
    {
      str = new String(this.c, u.a);
      this.e = str;
    }
    return str;
  }
  
  void a(c paramc)
  {
    byte[] arrayOfByte = this.c;
    paramc.b(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public boolean a(int paramInt1, f paramf, int paramInt2, int paramInt3)
  {
    return paramf.a(paramInt2, this.c, paramInt1, paramInt3);
  }
  
  public boolean a(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= 0)
    {
      byte[] arrayOfByte = this.c;
      if ((paramInt1 <= arrayOfByte.length - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3) && (u.a(arrayOfByte, paramInt1, paramArrayOfByte, paramInt2, paramInt3)))
      {
        bool = true;
        break label55;
      }
    }
    boolean bool = false;
    label55:
    return bool;
  }
  
  public final boolean a(f paramf)
  {
    return a(0, paramf, 0, paramf.h());
  }
  
  public int b(f paramf)
  {
    int i = h();
    int j = paramf.h();
    int k = Math.min(i, j);
    int n;
    int i1;
    int i2;
    for (int m = 0;; m++)
    {
      n = -1;
      if (m >= k) {
        break label83;
      }
      i1 = a(m) & 0xFF;
      i2 = paramf.a(m) & 0xFF;
      if (i1 != i2) {
        break;
      }
    }
    if (i1 >= i2) {
      n = 1;
    }
    return n;
    label83:
    if (i == j) {
      return 0;
    }
    if (i >= j) {
      n = 1;
    }
    return n;
  }
  
  public String b()
  {
    return b.a(this.c);
  }
  
  public f c()
  {
    return d("MD5");
  }
  
  public f d()
  {
    return d("SHA-1");
  }
  
  public f e()
  {
    return d("SHA-256");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof f))
    {
      paramObject = (f)paramObject;
      int i = ((f)paramObject).h();
      byte[] arrayOfByte = this.c;
      if ((i == arrayOfByte.length) && (((f)paramObject).a(0, arrayOfByte, 0, arrayOfByte.length))) {}
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public String f()
  {
    byte[] arrayOfByte = this.c;
    char[] arrayOfChar1 = new char[arrayOfByte.length * 2];
    int i = arrayOfByte.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      int m = arrayOfByte[j];
      int n = k + 1;
      char[] arrayOfChar2 = a;
      arrayOfChar1[k] = ((char)arrayOfChar2[(m >> 4 & 0xF)]);
      k = n + 1;
      arrayOfChar1[n] = ((char)arrayOfChar2[(m & 0xF)]);
      j++;
    }
    return new String(arrayOfChar1);
  }
  
  public f g()
  {
    for (int i = 0;; i++)
    {
      byte[] arrayOfByte = this.c;
      if (i >= arrayOfByte.length) {
        break;
      }
      int j = arrayOfByte[i];
      if ((j >= 65) && (j <= 90))
      {
        arrayOfByte = (byte[])arrayOfByte.clone();
        int k = i + 1;
        arrayOfByte[i] = ((byte)(byte)(j + 32));
        for (i = k; i < arrayOfByte.length; i++)
        {
          k = arrayOfByte[i];
          if ((k >= 65) && (k <= 90)) {
            arrayOfByte[i] = ((byte)(byte)(k + 32));
          }
        }
        return new f(arrayOfByte);
      }
    }
    return this;
  }
  
  public int h()
  {
    return this.c.length;
  }
  
  public int hashCode()
  {
    int i = this.d;
    if (i == 0)
    {
      i = Arrays.hashCode(this.c);
      this.d = i;
    }
    return i;
  }
  
  public byte[] i()
  {
    return (byte[])this.c.clone();
  }
  
  byte[] j()
  {
    return this.c;
  }
  
  public String toString()
  {
    if (this.c.length == 0) {
      return "[size=0]";
    }
    Object localObject1 = a();
    int i = a((String)localObject1, 64);
    if (i == -1)
    {
      if (this.c.length <= 64)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[hex=");
        ((StringBuilder)localObject2).append(f());
        ((StringBuilder)localObject2).append("]");
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[size=");
        ((StringBuilder)localObject2).append(this.c.length);
        ((StringBuilder)localObject2).append(" hex=");
        ((StringBuilder)localObject2).append(a(0, 64).f());
        ((StringBuilder)localObject2).append("…]");
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      return (String)localObject2;
    }
    Object localObject2 = ((String)localObject1).substring(0, i).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
    if (i < ((String)localObject1).length())
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[size=");
      ((StringBuilder)localObject1).append(this.c.length);
      ((StringBuilder)localObject1).append(" text=");
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("…]");
      localObject2 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[text=");
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("]");
      localObject2 = ((StringBuilder)localObject1).toString();
    }
    return (String)localObject2;
  }
}


/* Location:              ~/a/f.class
 *
 * Reversed by:           J
 */