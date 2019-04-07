package com.google.gson.b.a.a;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class a
{
  private static final TimeZone a = TimeZone.getTimeZone("UTC");
  
  private static int a(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((i >= 48) && (i <= 57)) {
        paramInt++;
      } else {
        return paramInt;
      }
    }
    return paramString.length();
  }
  
  private static int a(String paramString, int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt2 <= paramString.length()) && (paramInt1 <= paramInt2))
    {
      int i;
      int j;
      StringBuilder localStringBuilder;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1 + 1;
        j = Character.digit(paramString.charAt(paramInt1), 10);
        if (j >= 0)
        {
          j = -j;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      else
      {
        i = paramInt1;
        j = 0;
      }
      while (i < paramInt2)
      {
        int k = Character.digit(paramString.charAt(i), 10);
        if (k >= 0)
        {
          j = j * 10 - k;
          i++;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      return -j;
    }
    throw new NumberFormatException(paramString);
  }
  
  public static Date a(String paramString, ParsePosition paramParsePosition)
  {
    label377:
    Object localObject2;
    try
    {
      int i = paramParsePosition.getIndex();
      int j = i + 4;
      int k = a(paramString, i, j);
      i = j;
      if (a(paramString, j, '-')) {
        i = j + 1;
      }
      j = i + 2;
      int m = a(paramString, i, j);
      i = j;
      if (a(paramString, j, '-')) {
        i = j + 1;
      }
      j = i + 2;
      int n = a(paramString, i, j);
      boolean bool = a(paramString, j, 'T');
      if ((!bool) && (paramString.length() <= j))
      {
        localObject1 = new java/util/GregorianCalendar;
        ((GregorianCalendar)localObject1).<init>(k, m - 1, n);
        paramParsePosition.setIndex(j);
        return ((Calendar)localObject1).getTime();
      }
      int i1;
      int i2;
      int i3;
      if (bool)
      {
        i = j + 1;
        j = i + 2;
        i1 = a(paramString, i, j);
        i = j;
        if (a(paramString, j, ':')) {
          i = j + 1;
        }
        i2 = i + 2;
        i3 = a(paramString, i, i2);
        j = i2;
        if (a(paramString, i2, ':')) {
          j = i2 + 1;
        }
        if (paramString.length() > j)
        {
          i = paramString.charAt(j);
          if ((i != 90) && (i != 43) && (i != 45))
          {
            i = j + 2;
            i2 = a(paramString, j, i);
            j = 59;
            if ((i2 > 59) && (i2 < 63)) {
              i2 = j;
            }
            if (a(paramString, i, '.'))
            {
              int i4 = i + 1;
              j = a(paramString, i4 + 1);
              int i5 = Math.min(j, i4 + 3);
              i = a(paramString, i4, i5);
              switch (i5 - i4)
              {
              default: 
                break;
              case 2: 
                i *= 10;
                break;
              case 1: 
                i *= 100;
              }
              break label377;
            }
            j = i;
            i = 0;
            break label377;
          }
        }
        i = 0;
        i2 = 0;
      }
      else
      {
        i1 = 0;
        i3 = 0;
        i = 0;
        i2 = 0;
      }
      if (paramString.length() > j)
      {
        char c = paramString.charAt(j);
        if (c == 'Z')
        {
          localObject1 = a;
          j++;
        }
        else
        {
          if ((c != '+') && (c != '-'))
          {
            localObject2 = new java/lang/IndexOutOfBoundsException;
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((StringBuilder)localObject1).append("Invalid time zone indicator '");
            ((StringBuilder)localObject1).append(c);
            ((StringBuilder)localObject1).append("'");
            ((IndexOutOfBoundsException)localObject2).<init>(((StringBuilder)localObject1).toString());
            throw ((Throwable)localObject2);
          }
          localObject1 = paramString.substring(j);
          if (((String)localObject1).length() < 5)
          {
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append("00");
            localObject1 = ((StringBuilder)localObject2).toString();
          }
          j += ((String)localObject1).length();
          if ((!"+0000".equals(localObject1)) && (!"+00:00".equals(localObject1)))
          {
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append("GMT");
            ((StringBuilder)localObject2).append((String)localObject1);
            localObject2 = ((StringBuilder)localObject2).toString();
            localObject1 = TimeZone.getTimeZone((String)localObject2);
            localObject3 = ((TimeZone)localObject1).getID();
            if ((!((String)localObject3).equals(localObject2)) && (!((String)localObject3).replace(":", "").equals(localObject2)))
            {
              IndexOutOfBoundsException localIndexOutOfBoundsException2 = new java/lang/IndexOutOfBoundsException;
              localObject3 = new java/lang/StringBuilder;
              ((StringBuilder)localObject3).<init>();
              ((StringBuilder)localObject3).append("Mismatching time zone indicator: ");
              ((StringBuilder)localObject3).append((String)localObject2);
              ((StringBuilder)localObject3).append(" given, resolves to ");
              ((StringBuilder)localObject3).append(((TimeZone)localObject1).getID());
              localIndexOutOfBoundsException2.<init>(((StringBuilder)localObject3).toString());
              throw localIndexOutOfBoundsException2;
            }
          }
          else
          {
            localObject1 = a;
          }
        }
        localObject2 = new java/util/GregorianCalendar;
        ((GregorianCalendar)localObject2).<init>((TimeZone)localObject1);
        ((Calendar)localObject2).setLenient(false);
        ((Calendar)localObject2).set(1, k);
        ((Calendar)localObject2).set(2, m - 1);
        ((Calendar)localObject2).set(5, n);
        ((Calendar)localObject2).set(11, i1);
        ((Calendar)localObject2).set(12, i3);
        ((Calendar)localObject2).set(13, i2);
        ((Calendar)localObject2).set(14, i);
        paramParsePosition.setIndex(j);
        return ((Calendar)localObject2).getTime();
      }
      Object localObject1 = new java/lang/IllegalArgumentException;
      ((IllegalArgumentException)localObject1).<init>("No time zone indicator");
      throw ((Throwable)localObject1);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}catch (NumberFormatException localNumberFormatException) {}catch (IndexOutOfBoundsException localIndexOutOfBoundsException1) {}
    if (paramString == null)
    {
      paramString = null;
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append('"');
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append("'");
      paramString = ((StringBuilder)localObject2).toString();
    }
    Object localObject3 = localIndexOutOfBoundsException1.getMessage();
    if (localObject3 != null)
    {
      localObject2 = localObject3;
      if (!((String)localObject3).isEmpty()) {}
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("(");
      ((StringBuilder)localObject2).append(localIndexOutOfBoundsException1.getClass().getName());
      ((StringBuilder)localObject2).append(")");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Failed to parse date [");
    ((StringBuilder)localObject3).append(paramString);
    ((StringBuilder)localObject3).append("]: ");
    ((StringBuilder)localObject3).append((String)localObject2);
    paramString = new ParseException(((StringBuilder)localObject3).toString(), paramParsePosition.getIndex());
    paramString.initCause(localIndexOutOfBoundsException1);
    throw paramString;
  }
  
  private static boolean a(String paramString, int paramInt, char paramChar)
  {
    boolean bool;
    if ((paramInt < paramString.length()) && (paramString.charAt(paramInt) == paramChar)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/com/google/gson/b/a/a/a.class
 *
 * Reversed by:           J
 */