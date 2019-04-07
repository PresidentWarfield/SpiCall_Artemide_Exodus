package okhttp3;

import javax.annotation.Nullable;

public abstract interface Authenticator
{
  public static final Authenticator NONE = new Authenticator()
  {
    public Request authenticate(@Nullable Route paramAnonymousRoute, Response paramAnonymousResponse)
    {
      return null;
    }
  };
  
  @Nullable
  public abstract Request authenticate(@Nullable Route paramRoute, Response paramResponse);
}


/* Location:              ~/okhttp3/Authenticator.class
 *
 * Reversed by:           J
 */