package android.support.v4.media;

import android.os.Bundle;

public class MediaBrowserCompatUtils
{
  public static boolean areSameOptions(Bundle paramBundle1, Bundle paramBundle2)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (paramBundle1 == paramBundle2) {
      return true;
    }
    if (paramBundle1 == null)
    {
      if ((paramBundle2.getInt("android.media.browse.extra.PAGE", -1) != -1) || (paramBundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) != -1)) {
        bool3 = false;
      }
      return bool3;
    }
    if (paramBundle2 == null)
    {
      if ((paramBundle1.getInt("android.media.browse.extra.PAGE", -1) == -1) && (paramBundle1.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1)) {
        bool3 = bool1;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    if ((paramBundle1.getInt("android.media.browse.extra.PAGE", -1) == paramBundle2.getInt("android.media.browse.extra.PAGE", -1)) && (paramBundle1.getInt("android.media.browse.extra.PAGE_SIZE", -1) == paramBundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1))) {
      bool3 = bool2;
    } else {
      bool3 = false;
    }
    return bool3;
  }
  
  public static boolean hasDuplicatedItems(Bundle paramBundle1, Bundle paramBundle2)
  {
    int i;
    if (paramBundle1 == null) {
      i = -1;
    } else {
      i = paramBundle1.getInt("android.media.browse.extra.PAGE", -1);
    }
    int j;
    if (paramBundle2 == null) {
      j = -1;
    } else {
      j = paramBundle2.getInt("android.media.browse.extra.PAGE", -1);
    }
    int k;
    if (paramBundle1 == null) {
      k = -1;
    } else {
      k = paramBundle1.getInt("android.media.browse.extra.PAGE_SIZE", -1);
    }
    int m;
    if (paramBundle2 == null) {
      m = -1;
    } else {
      m = paramBundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1);
    }
    int n = Integer.MAX_VALUE;
    if ((i != -1) && (k != -1))
    {
      i *= k;
      k = k + i - 1;
    }
    else
    {
      k = Integer.MAX_VALUE;
      i = 0;
    }
    if ((j != -1) && (m != -1))
    {
      n = m * j;
      j = m + n - 1;
      m = n;
    }
    else
    {
      m = 0;
      j = n;
    }
    if ((i <= m) && (m <= k)) {
      return true;
    }
    return (i <= j) && (j <= k);
  }
}


/* Location:              ~/android/support/v4/media/MediaBrowserCompatUtils.class
 *
 * Reversed by:           J
 */