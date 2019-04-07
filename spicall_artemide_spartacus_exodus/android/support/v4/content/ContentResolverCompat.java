package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;

public final class ContentResolverCompat
{
  public static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, android.support.v4.os.CancellationSignal paramCancellationSignal)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      if (paramCancellationSignal != null) {
        try
        {
          paramCancellationSignal = paramCancellationSignal.getCancellationSignalObject();
        }
        catch (Exception paramContentResolver)
        {
          break label49;
        }
      } else {
        paramCancellationSignal = null;
      }
      paramContentResolver = paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, (android.os.CancellationSignal)paramCancellationSignal);
      return paramContentResolver;
      label49:
      if ((paramContentResolver instanceof android.os.OperationCanceledException)) {
        throw new android.support.v4.os.OperationCanceledException();
      }
      throw paramContentResolver;
    }
    if (paramCancellationSignal != null) {
      paramCancellationSignal.throwIfCanceled();
    }
    return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
  }
}


/* Location:              ~/android/support/v4/content/ContentResolverCompat.class
 *
 * Reversed by:           J
 */