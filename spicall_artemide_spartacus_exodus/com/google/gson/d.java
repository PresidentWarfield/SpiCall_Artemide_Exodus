package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;

public enum d
  implements e
{
  private d() {}
  
  private static String a(char paramChar, String paramString, int paramInt)
  {
    if (paramInt < paramString.length())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramChar);
      localStringBuilder.append(paramString.substring(paramInt));
      paramString = localStringBuilder.toString();
    }
    else
    {
      paramString = String.valueOf(paramChar);
    }
    return paramString;
  }
  
  static String a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    char c1 = paramString.charAt(0);
    int j = paramString.length();
    for (char c2 = c1; (i < j - 1) && (!Character.isLetter(c2)); c2 = c1)
    {
      localStringBuilder.append(c2);
      i++;
      c1 = paramString.charAt(i);
    }
    if (!Character.isUpperCase(c2))
    {
      localStringBuilder.append(a(Character.toUpperCase(c2), paramString, i + 1));
      return localStringBuilder.toString();
    }
    return paramString;
  }
  
  static String a(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramString1.length();
    for (int j = 0; j < i; j++)
    {
      char c1 = paramString1.charAt(j);
      if ((Character.isUpperCase(c1)) && (localStringBuilder.length() != 0)) {
        localStringBuilder.append(paramString2);
      }
      localStringBuilder.append(c1);
    }
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/google/gson/d.class
 *
 * Reversed by:           J
 */