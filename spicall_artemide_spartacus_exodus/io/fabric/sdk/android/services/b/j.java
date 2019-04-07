package io.fabric.sdk.android.services.b;

public abstract class j
{
  private final String a;
  private final String b;
  
  public j(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public String a()
  {
    return this.a;
  }
  
  public String b()
  {
    return this.b;
  }
  
  public static class a
    extends j
  {
    public a(String paramString1, String paramString2)
    {
      super(paramString2);
    }
  }
  
  public static class b
    extends j
  {
    public b(String paramString1, String paramString2)
    {
      super(paramString2);
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/j.class
 *
 * Reversed by:           J
 */