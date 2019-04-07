package org.apache.commons.net.ftp.parser;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.net.ftp.a;
import org.apache.commons.net.ftp.d;

public class f
  implements a, e
{
  private static final int[] f = { 14, 13, 12, 11, 5, 2, 1 };
  private SimpleDateFormat a;
  private int b;
  private SimpleDateFormat c;
  private int d;
  private boolean e = false;
  
  public f()
  {
    a("MMM d yyyy", null);
    b("MMM d HH:mm", null);
  }
  
  private static int a(int paramInt)
  {
    for (int i = 0;; i++)
    {
      int[] arrayOfInt = f;
      if (i >= arrayOfInt.length) {
        break;
      }
      if (paramInt == arrayOfInt[i]) {
        return i;
      }
    }
    return 0;
  }
  
  private static int a(SimpleDateFormat paramSimpleDateFormat)
  {
    if (paramSimpleDateFormat == null) {
      return 0;
    }
    paramSimpleDateFormat = paramSimpleDateFormat.toPattern();
    for (int k : "SsmHdM".toCharArray()) {
      if (paramSimpleDateFormat.indexOf(k) != -1) {
        if (k != 72)
        {
          if (k != 77)
          {
            if (k != 83)
            {
              if (k != 100)
              {
                if (k != 109)
                {
                  if (k == 115) {
                    return a(13);
                  }
                }
                else {
                  return a(12);
                }
              }
              else {
                return a(5);
              }
            }
            else {
              return a(14);
            }
          }
          else {
            return a(2);
          }
        }
        else {
          return a(11);
        }
      }
    }
    return 0;
  }
  
  private static void a(int paramInt, Calendar paramCalendar)
  {
    if (paramInt <= 0) {
      return;
    }
    paramInt = f[(paramInt - 1)];
    if (paramCalendar.get(paramInt) == 0) {
      paramCalendar.clear(paramInt);
    }
  }
  
  private void a(String paramString, DateFormatSymbols paramDateFormatSymbols)
  {
    if (paramString != null)
    {
      if (paramDateFormatSymbols != null) {
        this.a = new SimpleDateFormat(paramString, paramDateFormatSymbols);
      } else {
        this.a = new SimpleDateFormat(paramString);
      }
      this.a.setLenient(false);
    }
    else
    {
      this.a = null;
    }
    this.b = a(this.a);
  }
  
  private void b(String paramString)
  {
    TimeZone localTimeZone = TimeZone.getDefault();
    if (paramString != null) {
      localTimeZone = TimeZone.getTimeZone(paramString);
    }
    this.a.setTimeZone(localTimeZone);
    paramString = this.c;
    if (paramString != null) {
      paramString.setTimeZone(localTimeZone);
    }
  }
  
  private void b(String paramString, DateFormatSymbols paramDateFormatSymbols)
  {
    if (paramString != null)
    {
      if (paramDateFormatSymbols != null) {
        this.c = new SimpleDateFormat(paramString, paramDateFormatSymbols);
      } else {
        this.c = new SimpleDateFormat(paramString);
      }
      this.c.setLenient(false);
    }
    else
    {
      this.c = null;
    }
    this.d = a(this.c);
  }
  
  public Calendar a(String paramString)
  {
    return a(paramString, Calendar.getInstance());
  }
  
  public Calendar a(String paramString, Calendar paramCalendar)
  {
    Object localObject1 = (Calendar)paramCalendar.clone();
    ((Calendar)localObject1).setTimeZone(a());
    if (this.c != null)
    {
      localObject2 = (Calendar)paramCalendar.clone();
      ((Calendar)localObject2).setTimeZone(a());
      if (this.e) {
        ((Calendar)localObject2).add(5, 1);
      }
      Object localObject3 = Integer.toString(((Calendar)localObject2).get(1));
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(paramString);
      ((StringBuilder)localObject4).append(" ");
      ((StringBuilder)localObject4).append((String)localObject3);
      localObject4 = ((StringBuilder)localObject4).toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(this.c.toPattern());
      ((StringBuilder)localObject3).append(" yyyy");
      Object localObject5 = new SimpleDateFormat(((StringBuilder)localObject3).toString(), this.c.getDateFormatSymbols());
      ((SimpleDateFormat)localObject5).setLenient(false);
      ((SimpleDateFormat)localObject5).setTimeZone(this.c.getTimeZone());
      localObject3 = new ParsePosition(0);
      localObject5 = ((SimpleDateFormat)localObject5).parse((String)localObject4, (ParsePosition)localObject3);
      if ((localObject5 != null) && (((ParsePosition)localObject3).getIndex() == ((String)localObject4).length()))
      {
        ((Calendar)localObject1).setTime((Date)localObject5);
        if (((Calendar)localObject1).after(localObject2)) {
          ((Calendar)localObject1).add(1, -1);
        }
        a(this.d, (Calendar)localObject1);
        return (Calendar)localObject1;
      }
    }
    Object localObject2 = new ParsePosition(0);
    Object localObject4 = this.a.parse(paramString, (ParsePosition)localObject2);
    if ((localObject4 != null) && (((ParsePosition)localObject2).getIndex() == paramString.length()))
    {
      ((Calendar)localObject1).setTime((Date)localObject4);
      a(this.b, (Calendar)localObject1);
      return (Calendar)localObject1;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Timestamp '");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("' could not be parsed using a server time of ");
    ((StringBuilder)localObject1).append(paramCalendar.getTime().toString());
    throw new ParseException(((StringBuilder)localObject1).toString(), ((ParsePosition)localObject2).getErrorIndex());
  }
  
  public TimeZone a()
  {
    return this.a.getTimeZone();
  }
  
  public void a(d paramd)
  {
    Object localObject = paramd.f();
    String str = paramd.e();
    if (str != null) {
      localObject = d.d(str);
    } else if (localObject != null) {
      localObject = d.c((String)localObject);
    } else {
      localObject = d.c("en");
    }
    b(paramd.c(), (DateFormatSymbols)localObject);
    str = paramd.b();
    if (str != null)
    {
      a(str, (DateFormatSymbols)localObject);
      b(paramd.d());
      this.e = paramd.g();
      return;
    }
    throw new IllegalArgumentException("defaultFormatString cannot be null");
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/f.class
 *
 * Reversed by:           J
 */