package org.eclipse.paho.client.mqttv3.internal.security;

public class SimpleBase64Encoder
{
  private static final char[] PWDCHARS_ARRAY = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final String PWDCHARS_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  
  public static byte[] decode(String paramString)
  {
    byte[] arrayOfByte = paramString.getBytes();
    int i = arrayOfByte.length;
    paramString = new byte[i * 3 / 4];
    int j = 0;
    int k = 0;
    long l;
    int n;
    for (;;)
    {
      int m = 2;
      if (i < 4) {
        break;
      }
      l = from64(arrayOfByte, j, 4);
      i -= 4;
      n = j + 4;
      for (j = m; j >= 0; j--)
      {
        paramString[(k + j)] = ((byte)(byte)(int)(l & 0xFF));
        l >>= 8;
      }
      k += 3;
      j = n;
    }
    if (i == 3)
    {
      l = from64(arrayOfByte, j, 3);
      for (n = 1; n >= 0; n--)
      {
        paramString[(k + n)] = ((byte)(byte)(int)(l & 0xFF));
        l >>= 8;
      }
    }
    if (i == 2) {
      paramString[k] = ((byte)(byte)(int)(from64(arrayOfByte, j, 2) & 0xFF));
    }
    return paramString;
  }
  
  public static String encode(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    StringBuffer localStringBuffer = new StringBuffer((i + 2) / 3 * 4);
    int j = 0;
    while (i >= 3)
    {
      localStringBuffer.append(to64((paramArrayOfByte[j] & 0xFF) << 16 | (paramArrayOfByte[(j + 1)] & 0xFF) << 8 | paramArrayOfByte[(j + 2)] & 0xFF, 4));
      j += 3;
      i -= 3;
    }
    if (i == 2) {
      localStringBuffer.append(to64((paramArrayOfByte[j] & 0xFF) << 8 | paramArrayOfByte[(j + 1)] & 0xFF, 3));
    }
    if (i == 1) {
      localStringBuffer.append(to64(paramArrayOfByte[j] & 0xFF, 2));
    }
    return localStringBuffer.toString();
  }
  
  private static final long from64(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l1 = 0L;
    int i = 0;
    int j = paramInt2;
    paramInt2 = i;
    while (j > 0)
    {
      j--;
      i = paramArrayOfByte[paramInt1];
      if (i == 47) {
        l2 = 1L;
      } else {
        l2 = 0L;
      }
      long l3 = l2;
      if (i >= 48)
      {
        l3 = l2;
        if (i <= 57) {
          l3 = i + 2 - 48;
        }
      }
      long l2 = l3;
      if (i >= 65)
      {
        l2 = l3;
        if (i <= 90) {
          l2 = i + 12 - 65;
        }
      }
      l3 = l2;
      if (i >= 97)
      {
        l3 = l2;
        if (i <= 122) {
          l3 = i + 38 - 97;
        }
      }
      l1 += (l3 << paramInt2);
      paramInt2 += 6;
      paramInt1++;
    }
    return l1;
  }
  
  private static final String to64(long paramLong, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    while (paramInt > 0)
    {
      paramInt--;
      localStringBuffer.append(PWDCHARS_ARRAY[((int)(0x3F & paramLong))]);
      paramLong >>= 6;
    }
    return localStringBuffer.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/security/SimpleBase64Encoder.class
 *
 * Reversed by:           J
 */