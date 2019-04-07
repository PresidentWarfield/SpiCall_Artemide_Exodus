package android.support.v4.util;

import java.io.PrintWriter;

public final class TimeUtils
{
  public static final int HUNDRED_DAY_FIELD_LEN = 19;
  private static final int SECONDS_PER_DAY = 86400;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MINUTE = 60;
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();
  
  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramInt1 <= 99) && ((!paramBoolean) || (paramInt3 < 3)))
    {
      if ((paramInt1 <= 9) && ((!paramBoolean) || (paramInt3 < 2)))
      {
        if ((!paramBoolean) && (paramInt1 <= 0)) {
          return 0;
        }
        return paramInt2 + 1;
      }
      return paramInt2 + 2;
    }
    return paramInt2 + 3;
  }
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L)
    {
      paramPrintWriter.print("--");
      return;
    }
    formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration(paramLong, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt)
  {
    synchronized (sFormatSync)
    {
      paramInt = formatDurationLocked(paramLong, paramInt);
      String str = new java/lang/String;
      str.<init>(sFormatStr, 0, paramInt);
      paramPrintWriter.print(str);
      return;
    }
  }
  
  public static void formatDuration(long paramLong, StringBuilder paramStringBuilder)
  {
    synchronized (sFormatSync)
    {
      int i = formatDurationLocked(paramLong, 0);
      paramStringBuilder.append(sFormatStr, 0, i);
      return;
    }
  }
  
  private static int formatDurationLocked(long paramLong, int paramInt)
  {
    if (sFormatStr.length < paramInt) {
      sFormatStr = new char[paramInt];
    }
    char[] arrayOfChar = sFormatStr;
    if (paramLong == 0L)
    {
      while (paramInt - 1 > 0) {
        arrayOfChar[0] = ((char)32);
      }
      arrayOfChar[0] = ((char)48);
      return 1;
    }
    if (paramLong > 0L)
    {
      i = 43;
    }
    else
    {
      i = 45;
      paramLong = -paramLong;
    }
    int j = (int)(paramLong % 1000L);
    int k = (int)Math.floor(paramLong / 1000L);
    if (k > 86400)
    {
      m = k / 86400;
      k -= 86400 * m;
    }
    else
    {
      m = 0;
    }
    int n;
    if (k > 3600)
    {
      n = k / 3600;
      k -= n * 3600;
    }
    else
    {
      n = 0;
    }
    int i1;
    int i2;
    if (k > 60)
    {
      i1 = k / 60;
      i2 = k - i1 * 60;
    }
    else
    {
      i1 = 0;
      i2 = k;
    }
    boolean bool;
    if (paramInt != 0)
    {
      k = accumField(m, 1, false, 0);
      if (k > 0) {
        bool = true;
      } else {
        bool = false;
      }
      k += accumField(n, 1, bool, 2);
      if (k > 0) {
        bool = true;
      } else {
        bool = false;
      }
      k += accumField(i1, 1, bool, 2);
      if (k > 0) {
        bool = true;
      } else {
        bool = false;
      }
      int i3 = k + accumField(i2, 1, bool, 2);
      if (i3 > 0) {
        k = 3;
      } else {
        k = 0;
      }
      i3 += accumField(j, 2, true, k) + 1;
      k = 0;
      for (;;)
      {
        i4 = k;
        if (i3 >= paramInt) {
          break;
        }
        arrayOfChar[k] = ((char)32);
        k++;
        i3++;
      }
    }
    int i4 = 0;
    arrayOfChar[i4] = ((char)i);
    int i = i4 + 1;
    if (paramInt != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int m = printField(arrayOfChar, m, 'd', i, false, 0);
    if (m != i) {
      bool = true;
    } else {
      bool = false;
    }
    if (paramInt != 0) {
      k = 2;
    } else {
      k = 0;
    }
    m = printField(arrayOfChar, n, 'h', m, bool, k);
    if (m != i) {
      bool = true;
    } else {
      bool = false;
    }
    if (paramInt != 0) {
      k = 2;
    } else {
      k = 0;
    }
    m = printField(arrayOfChar, i1, 'm', m, bool, k);
    if (m != i) {
      bool = true;
    } else {
      bool = false;
    }
    if (paramInt != 0) {
      k = 2;
    } else {
      k = 0;
    }
    k = printField(arrayOfChar, i2, 's', m, bool, k);
    if ((paramInt != 0) && (k != i)) {
      paramInt = 3;
    } else {
      paramInt = 0;
    }
    paramInt = printField(arrayOfChar, j, 'm', k, true, paramInt);
    arrayOfChar[paramInt] = ((char)115);
    return paramInt + 1;
  }
  
  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if (!paramBoolean)
    {
      i = paramInt2;
      if (paramInt1 <= 0) {}
    }
    else
    {
      int j;
      if (((paramBoolean) && (paramInt3 >= 3)) || (paramInt1 > 99))
      {
        j = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(char)(j + 48));
        i = paramInt2 + 1;
        paramInt1 -= j * 100;
      }
      else
      {
        i = paramInt2;
      }
      if (((!paramBoolean) || (paramInt3 < 2)) && (paramInt1 <= 9))
      {
        j = i;
        paramInt3 = paramInt1;
        if (paramInt2 == i) {}
      }
      else
      {
        paramInt2 = paramInt1 / 10;
        paramArrayOfChar[i] = ((char)(char)(paramInt2 + 48));
        j = i + 1;
        paramInt3 = paramInt1 - paramInt2 * 10;
      }
      paramArrayOfChar[j] = ((char)(char)(paramInt3 + 48));
      paramInt1 = j + 1;
      paramArrayOfChar[paramInt1] = ((char)paramChar);
      i = paramInt1 + 1;
    }
    return i;
  }
}


/* Location:              ~/android/support/v4/util/TimeUtils.class
 *
 * Reversed by:           J
 */