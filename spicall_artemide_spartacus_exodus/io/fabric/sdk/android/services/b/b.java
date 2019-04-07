package io.fabric.sdk.android.services.b;

class b
{
  public final String a;
  public final boolean b;
  
  b(String paramString, boolean paramBoolean)
  {
    this.a = paramString;
    this.b = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (b)paramObject;
      if (this.b != ((b)paramObject).b) {
        return false;
      }
      String str = this.a;
      return str != null ? str.equals(((b)paramObject).a) : ((b)paramObject).a == null;
    }
    return false;
  }
  
  public int hashCode()
  {
    String str = this.a;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + this.b;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/b.class
 *
 * Reversed by:           J
 */