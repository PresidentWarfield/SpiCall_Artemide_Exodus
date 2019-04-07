package com.google.gson.b;

public final class a
{
  public static <T> T a(T paramT)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException();
  }
  
  public static void a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new IllegalArgumentException();
  }
}


/* Location:              ~/com/google/gson/b/a.class
 *
 * Reversed by:           J
 */