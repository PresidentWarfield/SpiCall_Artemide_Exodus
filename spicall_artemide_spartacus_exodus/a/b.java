package a;

import java.io.UnsupportedEncodingException;

final class b
{
  private static final byte[] a = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final byte[] b = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  
  public static String a(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, a);
  }
  
  private static String a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = new byte[(paramArrayOfByte1.length + 2) / 3 * 4];
    int i = paramArrayOfByte1.length - paramArrayOfByte1.length % 3;
    int j = 0;
    int k = 0;
    int m;
    while (j < i)
    {
      m = k + 1;
      arrayOfByte[k] = ((byte)paramArrayOfByte2[((paramArrayOfByte1[j] & 0xFF) >> 2)]);
      k = m + 1;
      int n = paramArrayOfByte1[j];
      int i1 = j + 1;
      arrayOfByte[m] = ((byte)paramArrayOfByte2[((n & 0x3) << 4 | (paramArrayOfByte1[i1] & 0xFF) >> 4)]);
      m = k + 1;
      n = paramArrayOfByte1[i1];
      i1 = j + 2;
      arrayOfByte[k] = ((byte)paramArrayOfByte2[((n & 0xF) << 2 | (paramArrayOfByte1[i1] & 0xFF) >> 6)]);
      k = m + 1;
      arrayOfByte[m] = ((byte)paramArrayOfByte2[(paramArrayOfByte1[i1] & 0x3F)]);
      j += 3;
    }
    switch (paramArrayOfByte1.length % 3)
    {
    default: 
      break;
    case 2: 
      j = k + 1;
      arrayOfByte[k] = ((byte)paramArrayOfByte2[((paramArrayOfByte1[i] & 0xFF) >> 2)]);
      m = j + 1;
      k = paramArrayOfByte1[i];
      i++;
      arrayOfByte[j] = ((byte)paramArrayOfByte2[((k & 0x3) << 4 | (paramArrayOfByte1[i] & 0xFF) >> 4)]);
      arrayOfByte[m] = ((byte)paramArrayOfByte2[((paramArrayOfByte1[i] & 0xF) << 2)]);
      arrayOfByte[(m + 1)] = ((byte)61);
      break;
    case 1: 
      j = k + 1;
      arrayOfByte[k] = ((byte)paramArrayOfByte2[((paramArrayOfByte1[i] & 0xFF) >> 2)]);
      k = j + 1;
      arrayOfByte[j] = ((byte)paramArrayOfByte2[((paramArrayOfByte1[i] & 0x3) << 4)]);
      arrayOfByte[k] = ((byte)61);
      arrayOfByte[(k + 1)] = ((byte)61);
    }
    try
    {
      paramArrayOfByte1 = new String(arrayOfByte, "US-ASCII");
      return paramArrayOfByte1;
    }
    catch (UnsupportedEncodingException paramArrayOfByte1)
    {
      throw new AssertionError(paramArrayOfByte1);
    }
  }
  
  public static byte[] a(String paramString)
  {
    int j;
    for (int i = paramString.length(); i > 0; i--)
    {
      j = paramString.charAt(i - 1);
      if ((j != 61) && (j != 10) && (j != 13) && (j != 32) && (j != 9)) {
        break;
      }
    }
    byte[] arrayOfByte = new byte[(int)(i * 6L / 8L)];
    int k = 0;
    int m = 0;
    int n = 0;
    for (int i1 = 0; k < i; i1 = j)
    {
      int i2 = paramString.charAt(k);
      if ((i2 >= 65) && (i2 <= 90))
      {
        j = i2 - 65;
      }
      else if ((i2 >= 97) && (i2 <= 122))
      {
        j = i2 - 71;
      }
      else if ((i2 >= 48) && (i2 <= 57))
      {
        j = i2 + 4;
      }
      else if ((i2 != 43) && (i2 != 45))
      {
        if ((i2 != 47) && (i2 != 95))
        {
          i3 = m;
          i4 = n;
          j = i1;
          if (i2 == 10) {
            break label367;
          }
          i3 = m;
          i4 = n;
          j = i1;
          if (i2 == 13) {
            break label367;
          }
          i3 = m;
          i4 = n;
          j = i1;
          if (i2 == 32) {
            break label367;
          }
          if (i2 == 9)
          {
            i3 = m;
            i4 = n;
            j = i1;
            break label367;
          }
          return null;
        }
        j = 63;
      }
      else
      {
        j = 62;
      }
      n = n << 6 | (byte)j;
      m++;
      int i3 = m;
      int i4 = n;
      j = i1;
      if (m % 4 == 0)
      {
        j = i1 + 1;
        arrayOfByte[i1] = ((byte)(byte)(n >> 16));
        i1 = j + 1;
        arrayOfByte[j] = ((byte)(byte)(n >> 8));
        arrayOfByte[i1] = ((byte)(byte)n);
        j = i1 + 1;
        i4 = n;
        i3 = m;
      }
      label367:
      k++;
      m = i3;
      n = i4;
    }
    i = m % 4;
    if (i == 1) {
      return null;
    }
    if (i == 2)
    {
      arrayOfByte[i1] = ((byte)(byte)(n << 12 >> 16));
      j = i1 + 1;
    }
    else
    {
      j = i1;
      if (i == 3)
      {
        k = n << 6;
        i = i1 + 1;
        arrayOfByte[i1] = ((byte)(byte)(k >> 16));
        j = i + 1;
        arrayOfByte[i] = ((byte)(byte)(k >> 8));
      }
    }
    if (j == arrayOfByte.length) {
      return arrayOfByte;
    }
    paramString = new byte[j];
    System.arraycopy(arrayOfByte, 0, paramString, 0, j);
    return paramString;
  }
}


/* Location:              ~/a/b.class
 *
 * Reversed by:           J
 */