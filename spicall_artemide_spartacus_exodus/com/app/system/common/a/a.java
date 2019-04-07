package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Contacts.People;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneNumberUtils;

public class a
{
  Context a;
  
  public a(Context paramContext)
  {
    this.a = paramContext;
  }
  
  public String a(String paramString)
  {
    localObject1 = paramString;
    try
    {
      Cursor localCursor;
      if (Build.VERSION.SDK_INT >= 5)
      {
        localObject1 = paramString;
        localObject2 = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(paramString));
        localObject1 = paramString;
        localCursor = this.a.getContentResolver().query((Uri)localObject2, new String[] { "display_name" }, null, null, null);
        localObject2 = paramString;
        localObject1 = paramString;
        if (localCursor.moveToFirst())
        {
          localObject1 = paramString;
          localObject2 = localCursor.getString(localCursor.getColumnIndex("display_name"));
        }
        localObject1 = localObject2;
        localCursor.close();
      }
      else
      {
        localObject2 = paramString;
        localObject1 = paramString;
        if (Build.VERSION.SDK_INT <= 4)
        {
          localObject1 = paramString;
          localCursor = this.a.getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
          localObject2 = paramString;
          localObject1 = paramString;
          if (localCursor.moveToNext())
          {
            localObject2 = paramString;
            localObject1 = paramString;
            if (PhoneNumberUtils.compare(paramString, localCursor.getString(localCursor.getColumnIndex("number"))))
            {
              localObject1 = paramString;
              localObject2 = localCursor.getString(localCursor.getColumnIndex("name"));
            }
          }
          localObject1 = localObject2;
          localCursor.close();
        }
      }
    }
    catch (Exception paramString)
    {
      for (;;)
      {
        Object localObject2 = localObject1;
      }
    }
    return (String)localObject2;
  }
}


/* Location:              ~/com/app/system/common/a/a.class
 *
 * Reversed by:           J
 */