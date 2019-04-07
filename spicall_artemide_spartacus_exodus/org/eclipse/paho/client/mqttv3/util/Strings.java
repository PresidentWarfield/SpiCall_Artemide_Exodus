package org.eclipse.paho.client.mqttv3.util;

public final class Strings
{
  private static final int INDEX_NOT_FOUND = -1;
  
  public static boolean containsAny(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (paramCharSequence2 == null) {
      return false;
    }
    return containsAny(paramCharSequence1, toCharArray(paramCharSequence2));
  }
  
  public static boolean containsAny(CharSequence paramCharSequence, char[] paramArrayOfChar)
  {
    if ((!isEmpty(paramCharSequence)) && (!isEmpty(paramArrayOfChar)))
    {
      int i = paramCharSequence.length();
      int j = paramArrayOfChar.length;
      for (int k = 0; k < i; k++)
      {
        char c = paramCharSequence.charAt(k);
        for (int m = 0; m < j; m++) {
          if (paramArrayOfChar[m] == c) {
            if (Character.isHighSurrogate(c))
            {
              if (m == j - 1) {
                return true;
              }
              if ((k < i - 1) && (paramArrayOfChar[(m + 1)] == paramCharSequence.charAt(k + 1))) {
                return true;
              }
            }
            else
            {
              return true;
            }
          }
        }
      }
      return false;
    }
    return false;
  }
  
  public static int countMatches(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    boolean bool = isEmpty(paramCharSequence1);
    int i = 0;
    if ((!bool) && (!isEmpty(paramCharSequence2)))
    {
      int j = 0;
      for (;;)
      {
        i = indexOf(paramCharSequence1, paramCharSequence2, i);
        if (i == -1) {
          break;
        }
        j++;
        i += paramCharSequence2.length();
      }
      return j;
    }
    return 0;
  }
  
  public static boolean equalsAny(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence)
  {
    boolean bool1;
    if (paramCharSequence == null)
    {
      if (paramArrayOfCharSequence == null) {
        bool1 = true;
      } else {
        bool1 = false;
      }
    }
    else {
      bool1 = false;
    }
    boolean bool2 = bool1;
    if (paramArrayOfCharSequence != null)
    {
      for (int i = 0; i < paramArrayOfCharSequence.length; i++) {
        if ((!bool1) && (!paramArrayOfCharSequence[i].equals(paramCharSequence))) {
          bool1 = false;
        } else {
          bool1 = true;
        }
      }
      bool2 = bool1;
    }
    return bool2;
  }
  
  private static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
  {
    return paramCharSequence1.toString().indexOf(paramCharSequence2.toString(), paramInt);
  }
  
  public static boolean isEmpty(CharSequence paramCharSequence)
  {
    boolean bool;
    if ((paramCharSequence != null) && (paramCharSequence.length() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isEmpty(char[] paramArrayOfChar)
  {
    boolean bool;
    if ((paramArrayOfChar != null) && (paramArrayOfChar.length != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static char[] toCharArray(CharSequence paramCharSequence)
  {
    if ((paramCharSequence instanceof String)) {
      return ((String)paramCharSequence).toCharArray();
    }
    int i = paramCharSequence.length();
    char[] arrayOfChar = new char[paramCharSequence.length()];
    for (int j = 0; j < i; j++) {
      arrayOfChar[j] = paramCharSequence.charAt(j);
    }
    return arrayOfChar;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/util/Strings.class
 *
 * Reversed by:           J
 */