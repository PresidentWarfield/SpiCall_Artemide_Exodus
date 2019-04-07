package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import com.app.system.common.entity.Contact;
import com.security.b;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class d
  extends a
{
  public d(Context paramContext)
  {
    super(paramContext);
  }
  
  public String a(int paramInt)
  {
    Object localObject1 = "";
    if (!b.a(this.a, "android.permission.READ_CONTACTS", "getDisplayName@HistoryFileDBManagerForContact")) {
      return "";
    }
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = localObject1;
    Object localObject5 = localObject1;
    try
    {
      Cursor localCursor = this.a.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, "_id = ?", new String[] { String.valueOf(paramInt) }, null);
      Object localObject6 = localObject1;
      localObject4 = localObject1;
      localObject3 = localCursor;
      localObject5 = localObject1;
      localObject2 = localCursor;
      if (localCursor.getCount() > 0)
      {
        localObject6 = localObject1;
        localObject4 = localObject1;
        localObject3 = localCursor;
        localObject5 = localObject1;
        localObject2 = localCursor;
        if (localCursor.moveToFirst())
        {
          localObject4 = localObject1;
          localObject3 = localCursor;
          localObject5 = localObject1;
          localObject2 = localCursor;
          localObject6 = localCursor.getString(localCursor.getColumnIndex("display_name"));
        }
      }
      localObject4 = localObject6;
      localObject3 = localCursor;
      localObject5 = localObject6;
      localObject2 = localCursor;
      localCursor.close();
      localObject1 = localObject6;
      if (localCursor != null)
      {
        localObject4 = localObject6;
        localObject3 = localCursor;
        localObject5 = localObject6;
        localObject2 = localCursor;
        localObject1 = localObject6;
        if (!localCursor.isClosed())
        {
          localObject4 = localObject6;
          localObject3 = localCursor;
          localObject5 = localObject6;
          localObject2 = localCursor;
          localCursor.close();
          localObject1 = localObject6;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localObject1 = localObject4;
      if (localObject3 != null)
      {
        localObject1 = localObject4;
        if (!((Cursor)localObject3).isClosed())
        {
          ((Cursor)localObject3).close();
          localObject1 = localObject4;
        }
      }
    }
    catch (Exception localException)
    {
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("getDisplayName - ");
      ((StringBuilder)localObject4).append(localException.getMessage());
      com.security.d.a.a("FileContact", ((StringBuilder)localObject4).toString(), new Object[0]);
      localException.printStackTrace();
      localObject1 = localObject5;
      if (localObject2 != null)
      {
        localObject1 = localObject5;
        if (!((Cursor)localObject2).isClosed())
        {
          ((Cursor)localObject2).close();
          localObject1 = localObject5;
        }
      }
    }
    return (String)localObject1;
  }
  
  public List<Contact> a()
  {
    ArrayList localArrayList = new ArrayList();
    if (!b.a(this.a, "android.permission.READ_CONTACTS", "contactHistorydbRead@HistoryFileDBManagerForContact")) {
      return localArrayList;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      Cursor localCursor = this.a.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[] { "contact_id" }, null, null, null);
      localObject2 = localCursor;
      localObject1 = localCursor;
      Object localObject3 = new java/lang/StringBuilder;
      localObject2 = localCursor;
      localObject1 = localCursor;
      ((StringBuilder)localObject3).<init>();
      localObject2 = localCursor;
      localObject1 = localCursor;
      ((StringBuilder)localObject3).append("There are ");
      localObject2 = localCursor;
      localObject1 = localCursor;
      ((StringBuilder)localObject3).append(localCursor.getCount());
      localObject2 = localCursor;
      localObject1 = localCursor;
      ((StringBuilder)localObject3).append(" contacts in db");
      localObject2 = localCursor;
      localObject1 = localCursor;
      com.security.d.a.d("FileContact", ((StringBuilder)localObject3).toString(), new Object[0]);
      localObject2 = localCursor;
      localObject1 = localCursor;
      if (localCursor.moveToFirst()) {
        for (;;)
        {
          localObject2 = localCursor;
          localObject1 = localCursor;
          int i = localCursor.getInt(localCursor.getColumnIndex("contact_id"));
          localObject2 = localCursor;
          localObject1 = localCursor;
          String str1 = a(i);
          localObject2 = localCursor;
          localObject1 = localCursor;
          String str2 = c(i);
          localObject2 = localCursor;
          localObject1 = localCursor;
          String str3 = b(i);
          localObject2 = localCursor;
          localObject1 = localCursor;
          Object localObject4 = this.a.getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, "contact_id = ?", new String[] { String.valueOf(i) }, null);
          Object localObject5 = "";
          localObject2 = localCursor;
          localObject1 = localCursor;
          if (((Cursor)localObject4).moveToFirst())
          {
            localObject2 = localCursor;
            localObject1 = localCursor;
            String str4 = ((Cursor)localObject4).getString(((Cursor)localObject4).getColumnIndex("data4"));
            localObject2 = localCursor;
            localObject1 = localCursor;
            String str5 = ((Cursor)localObject4).getString(((Cursor)localObject4).getColumnIndex("data7"));
            localObject2 = localCursor;
            localObject1 = localCursor;
            String str6 = ((Cursor)localObject4).getString(((Cursor)localObject4).getColumnIndex("data8"));
            localObject3 = localObject5;
            if (str4 != null)
            {
              localObject3 = localObject5;
              localObject2 = localCursor;
              localObject1 = localCursor;
              if (str4.length() > 0)
              {
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject3 = new java/lang/StringBuilder;
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).<init>();
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).append(str4.replace('"', ' '));
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).append(",");
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject3 = ((StringBuilder)localObject3).toString();
              }
            }
            localObject5 = localObject3;
            if (str5 != null)
            {
              localObject5 = localObject3;
              localObject2 = localCursor;
              localObject1 = localCursor;
              if (str5.length() > 0)
              {
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject5 = new java/lang/StringBuilder;
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject5).<init>();
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject5).append((String)localObject3);
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject5).append(str5.replace('"', ' '));
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject5).append(",");
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject5 = ((StringBuilder)localObject5).toString();
              }
            }
            localObject3 = localObject5;
            if (str6 != null)
            {
              localObject3 = localObject5;
              localObject2 = localCursor;
              localObject1 = localCursor;
              if (str6.length() > 0)
              {
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject3 = new java/lang/StringBuilder;
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).<init>();
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).append((String)localObject5);
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).append(str6.replace('"', ' '));
                localObject2 = localCursor;
                localObject1 = localCursor;
                ((StringBuilder)localObject3).append(",");
                localObject2 = localCursor;
                localObject1 = localCursor;
                localObject3 = ((StringBuilder)localObject3).toString();
              }
            }
            localObject2 = localCursor;
            localObject1 = localCursor;
            localObject3 = ((String)localObject3).substring(0, ((String)localObject3).length());
          }
          else
          {
            localObject3 = "";
          }
          localObject2 = localCursor;
          localObject1 = localCursor;
          ((Cursor)localObject4).close();
          localObject2 = localCursor;
          localObject1 = localCursor;
          localObject5 = new com/app/system/common/entity/Contact;
          long l = i;
          localObject2 = localCursor;
          localObject1 = localCursor;
          localObject4 = new java/util/Date;
          localObject2 = localCursor;
          localObject1 = localCursor;
          ((Date)localObject4).<init>();
          localObject2 = localCursor;
          localObject1 = localCursor;
          ((Contact)localObject5).<init>(l, ((Date)localObject4).getTime() / 1000L, str1, "", str2, str3, "", (String)localObject3);
          localObject2 = localCursor;
          localObject1 = localCursor;
          localArrayList.add(localObject5);
          localObject2 = localCursor;
          localObject1 = localCursor;
          if (!localCursor.moveToNext()) {
            break;
          }
        }
      }
      if (localCursor != null)
      {
        localObject2 = localCursor;
        localObject1 = localCursor;
        if (!localCursor.isClosed())
        {
          localObject2 = localCursor;
          localObject1 = localCursor;
          localCursor.close();
        }
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localObject2 != null) && (!((Cursor)localObject2).isClosed())) {
        ((Cursor)localObject2).close();
      }
    }
    catch (Exception localException)
    {
      com.security.d.a.a("FileContact", localException.getMessage(), new Object[0]);
      localException.printStackTrace();
      if ((localObject1 != null) && (!((Cursor)localObject1).isClosed())) {
        ((Cursor)localObject1).close();
      }
    }
    return localArrayList;
  }
  
  public Contact b()
  {
    if (!b.a(this.a, "android.permission.READ_CONTACTS", "contactlHistorydbReadLastRecent@HistoryFileDBManagerForContact")) {
      return null;
    }
    Cursor localCursor;
    try
    {
      localCursor = this.a.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[] { "contact_id", "deleted" }, null, null, null);
      try
      {
        if (localCursor.moveToLast())
        {
          int i = localCursor.getInt(localCursor.getColumnIndex("contact_id"));
          if (localCursor.getInt(localCursor.getColumnIndex("deleted")) == 0)
          {
            String str1 = a(i);
            String str2 = c(i);
            String str3 = b(i);
            localObject1 = this.a.getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, "contact_id = ?", new String[] { String.valueOf(i) }, null);
            Object localObject2 = "";
            if (((Cursor)localObject1).moveToFirst())
            {
              String str4 = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("data4"));
              String str5 = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("data7"));
              localObject3 = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("data8"));
              localObject1 = localObject2;
              if (str4 != null)
              {
                localObject1 = localObject2;
                if (str4.length() > 0)
                {
                  localObject1 = new java/lang/StringBuilder;
                  ((StringBuilder)localObject1).<init>();
                  ((StringBuilder)localObject1).append("");
                  ((StringBuilder)localObject1).append(str4);
                  ((StringBuilder)localObject1).append(",");
                  localObject1 = ((StringBuilder)localObject1).toString();
                }
              }
              localObject2 = localObject1;
              if (str5 != null)
              {
                localObject2 = localObject1;
                if (str5.length() > 0)
                {
                  localObject2 = new java/lang/StringBuilder;
                  ((StringBuilder)localObject2).<init>();
                  ((StringBuilder)localObject2).append((String)localObject1);
                  ((StringBuilder)localObject2).append(str5);
                  ((StringBuilder)localObject2).append(",");
                  localObject2 = ((StringBuilder)localObject2).toString();
                }
              }
              localObject1 = localObject2;
              if (localObject3 != null)
              {
                localObject1 = localObject2;
                if (((String)localObject3).length() > 0)
                {
                  localObject1 = new java/lang/StringBuilder;
                  ((StringBuilder)localObject1).<init>();
                  ((StringBuilder)localObject1).append((String)localObject2);
                  ((StringBuilder)localObject1).append((String)localObject3);
                  ((StringBuilder)localObject1).append(",");
                  localObject1 = ((StringBuilder)localObject1).toString();
                }
              }
              localObject1 = ((String)localObject1).substring(0, ((String)localObject1).length());
            }
            else
            {
              localObject1 = "";
            }
            localObject2 = new com/app/system/common/entity/Contact;
            long l = i;
            Object localObject3 = new java/util/Date;
            ((Date)localObject3).<init>();
            ((Contact)localObject2).<init>(l, ((Date)localObject3).getTime() / 1000L, str1, "", str2, str3, "", (String)localObject1);
            localObject1 = localObject2;
            break label468;
          }
        }
        Object localObject1 = null;
        label468:
        if ((localCursor != null) && (!localCursor.isClosed())) {
          localCursor.close();
        }
        return (Contact)localObject1;
      }
      catch (Throwable localThrowable1) {}catch (Exception localException1) {}
      com.security.d.a.a("FileContact", localException2.getMessage(), new Object[0]);
    }
    catch (Throwable localThrowable2)
    {
      localCursor = null;
      if ((localCursor != null) && (!localCursor.isClosed())) {
        localCursor.close();
      }
      return null;
    }
    catch (Exception localException2)
    {
      localCursor = null;
    }
    if ((localCursor != null) && (!localCursor.isClosed()))
    {
      localCursor.close();
      return null;
    }
    return null;
  }
  
  public String b(int paramInt)
  {
    Object localObject1 = "";
    if (!b.a(this.a, "android.permission.READ_CONTACTS", "getEmailAddressesForContact@HistoryFileDBManagerForContact")) {
      return "";
    }
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = localObject1;
    Object localObject5 = localObject1;
    try
    {
      Cursor localCursor = this.a.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, "contact_id = ?", new String[] { String.valueOf(paramInt) }, null);
      Object localObject6 = localObject1;
      localObject4 = localObject1;
      localObject3 = localCursor;
      localObject5 = localObject1;
      localObject2 = localCursor;
      if (localCursor.moveToFirst())
      {
        localObject4 = localObject1;
        localObject3 = localCursor;
        localObject5 = localObject1;
        localObject2 = localCursor;
        localObject6 = localCursor.getString(localCursor.getColumnIndex("data1"));
      }
      localObject4 = localObject6;
      localObject3 = localCursor;
      localObject5 = localObject6;
      localObject2 = localCursor;
      localCursor.close();
      localObject1 = localObject6;
      if (localCursor != null)
      {
        localObject4 = localObject6;
        localObject3 = localCursor;
        localObject5 = localObject6;
        localObject2 = localCursor;
        localObject1 = localObject6;
        if (!localCursor.isClosed())
        {
          localObject4 = localObject6;
          localObject3 = localCursor;
          localObject5 = localObject6;
          localObject2 = localCursor;
          localCursor.close();
          localObject1 = localObject6;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localObject1 = localObject4;
      if (localObject3 != null)
      {
        localObject1 = localObject4;
        if (!((Cursor)localObject3).isClosed())
        {
          ((Cursor)localObject3).close();
          localObject1 = localObject4;
        }
      }
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getEmailAddressesForContact - ");
      localStringBuilder.append(localException.getMessage());
      com.security.d.a.a("FileContact", localStringBuilder.toString(), new Object[0]);
      localObject1 = localObject5;
      if (localObject2 != null)
      {
        localObject1 = localObject5;
        if (!((Cursor)localObject2).isClosed())
        {
          ((Cursor)localObject2).close();
          localObject1 = localObject5;
        }
      }
    }
    return (String)localObject1;
  }
  
  public String c(int paramInt)
  {
    String str1 = "";
    if (!b.a(this.a, "android.permission.READ_CONTACTS", "getPhoneNumberForContact@HistoryFileDBManagerForContact")) {
      return "";
    }
    Cursor localCursor = this.a.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id = ?", new String[] { String.valueOf(paramInt) }, null);
    paramInt = localCursor.getColumnIndexOrThrow("data1");
    String str2 = str1;
    Object localObject;
    if (localCursor != null)
    {
      str2 = str1;
      String str3 = str1;
      try
      {
        if (localCursor.moveToNext())
        {
          str3 = str1;
          str2 = localCursor.getString(paramInt);
        }
        str3 = str2;
        localCursor.close();
      }
      catch (Throwable localThrowable)
      {
        localCursor.close();
        localObject = str3;
      }
    }
    return (String)localObject;
  }
}


/* Location:              ~/com/app/system/common/a/d.class
 *
 * Reversed by:           J
 */