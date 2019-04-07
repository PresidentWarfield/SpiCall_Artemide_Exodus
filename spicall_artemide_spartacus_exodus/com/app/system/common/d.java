package com.app.system.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
{
  public static b a(Context paramContext, String paramString1, String paramString2)
  {
    int i = 0;
    Object localObject1 = paramContext.getSharedPreferences("device_names", 0);
    String str = String.format("%s:%s", new Object[] { paramString1, paramString2 });
    Object localObject2 = ((SharedPreferences)localObject1).getString(str, null);
    Object localObject3;
    if (localObject2 != null) {
      try
      {
        localObject3 = new org/json/JSONObject;
        ((JSONObject)localObject3).<init>((String)localObject2);
        localObject2 = new b((JSONObject)localObject3, null);
        return (b)localObject2;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    int j;
    if (paramContext.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0)
    {
      paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((paramContext != null) && (paramContext.isConnected())) {
        j = 1;
      } else {
        j = 0;
      }
    }
    else
    {
      j = 1;
    }
    if (j != 0) {
      try
      {
        paramContext = b(String.format("https://raw.githubusercontent.com/jaredrummler/AndroidDeviceNames/master/json/devices/%s.json", new Object[] { paramString1.toLowerCase(Locale.ENGLISH) }));
        localObject3 = new org/json/JSONArray;
        ((JSONArray)localObject3).<init>(paramContext);
        int k = ((JSONArray)localObject3).length();
        for (j = i; j < k; j++)
        {
          paramContext = ((JSONArray)localObject3).getJSONObject(j);
          b localb = new com/app/system/common/d$b;
          localb.<init>(paramContext, null);
          if (((paramString1.equalsIgnoreCase(localb.c)) && (paramString2 == null)) || ((paramString1.equalsIgnoreCase(localb.c)) && (paramString2.equalsIgnoreCase(localb.d))))
          {
            localObject1 = ((SharedPreferences)localObject1).edit();
            ((SharedPreferences.Editor)localObject1).putString(str, paramContext.toString());
            if (Build.VERSION.SDK_INT >= 9) {
              ((SharedPreferences.Editor)localObject1).apply();
            } else {
              ((SharedPreferences.Editor)localObject1).commit();
            }
            return localb;
          }
        }
        if (!paramString1.equals(Build.DEVICE)) {
          break label344;
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    if (paramString2.equals(Build.MODEL)) {
      return new b(Build.MANUFACTURER, a(), paramString1, paramString2);
    }
    label344:
    return new b(null, null, paramString1, paramString2);
  }
  
  public static c a(Context paramContext)
  {
    return new c(paramContext.getApplicationContext(), null);
  }
  
  public static String a()
  {
    return a(Build.DEVICE, Build.MODEL, a(Build.MODEL));
  }
  
  private static String a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    paramString = "";
    int j = 0;
    int k = 1;
    while (j < i)
    {
      char c = arrayOfChar[j];
      StringBuilder localStringBuilder;
      if ((k != 0) && (Character.isLetter(c)))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append(Character.toUpperCase(c));
        paramString = localStringBuilder.toString();
        k = 0;
      }
      else
      {
        if (Character.isWhitespace(c)) {
          k = 1;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append(c);
        paramString = localStringBuilder.toString();
      }
      j++;
    }
    return paramString;
  }
  
  public static String a(String paramString1, String paramString2, String paramString3)
  {
    if (((paramString1 != null) && (paramString1.equals("acer_S57"))) || ((paramString2 != null) && (paramString2.equals("S57")))) {
      return "Liquid Jade Z";
    }
    if (((paramString1 != null) && (paramString1.equals("acer_t08"))) || ((paramString2 != null) && (paramString2.equals("T08")))) {
      return "Liquid Zest Plus";
    }
    if ((paramString1 != null) && ((paramString1.equals("grouper")) || (paramString1.equals("tilapia")))) {
      return "Nexus 7 (2012)";
    }
    if ((paramString1 != null) && ((paramString1.equals("deb")) || (paramString1.equals("flo")))) {
      return "Nexus 7 (2013)";
    }
    if ((paramString1 != null) && (paramString1.equals("sailfish"))) {
      return "Pixel";
    }
    if ((paramString1 != null) && (paramString1.equals("walleye"))) {
      return "Pixel 2";
    }
    if ((paramString1 != null) && (paramString1.equals("taimen"))) {
      return "Pixel 2 XL";
    }
    if ((paramString1 != null) && (paramString1.equals("dragon"))) {
      return "Pixel C";
    }
    if ((paramString1 != null) && (paramString1.equals("marlin"))) {
      return "Pixel XL";
    }
    if ((paramString1 != null) && (paramString1.equals("flounder"))) {
      return "Nexus 9";
    }
    if (((paramString1 != null) && (paramString1.equals("HWBND-H"))) || ((paramString2 != null) && ((paramString2.equals("BND-L21")) || (paramString2.equals("BND-L24"))))) {
      return "Honor 7X";
    }
    if (((paramString1 != null) && (paramString1.equals("HWBKL"))) || ((paramString2 != null) && (paramString2.equals("BKL-L09")))) {
      return "Honor View 10";
    }
    if (((paramString1 != null) && (paramString1.equals("HWALP"))) || ((paramString2 != null) && ((paramString2.equals("ALP-AL00")) || (paramString2.equals("ALP-L09")) || (paramString2.equals("ALP-L29")) || (paramString2.equals("ALP-TL00"))))) {
      return "Mate 10";
    }
    if (((paramString1 != null) && (paramString1.equals("HWMHA"))) || ((paramString2 != null) && ((paramString2.equals("MHA-AL00")) || (paramString2.equals("MHA-L09")) || (paramString2.equals("MHA-L29")) || (paramString2.equals("MHA-TL00"))))) {
      return "Mate 9";
    }
    if ((paramString1 != null) && (paramString1.equals("angler"))) {
      return "Nexus 6P";
    }
    if (((paramString1 != null) && (paramString1.equals("g2"))) || ((paramString2 != null) && ((paramString2.equals("LG-D800")) || (paramString2.equals("LG-D801")) || (paramString2.equals("LG-D802")) || (paramString2.equals("LG-D802T")) || (paramString2.equals("LG-D802TR")) || (paramString2.equals("LG-D803")) || (paramString2.equals("LG-D805")) || (paramString2.equals("LG-D806")) || (paramString2.equals("LG-F320K")) || (paramString2.equals("LG-F320L")) || (paramString2.equals("LG-F320S")) || (paramString2.equals("LG-LS980")) || (paramString2.equals("VS980 4G"))))) {
      return "LG G2";
    }
    if (((paramString1 != null) && (paramString1.equals("g3"))) || ((paramString2 != null) && ((paramString2.equals("AS985")) || (paramString2.equals("LG-AS990")) || (paramString2.equals("LG-D850")) || (paramString2.equals("LG-D851")) || (paramString2.equals("LG-D852")) || (paramString2.equals("LG-D852G")) || (paramString2.equals("LG-D855")) || (paramString2.equals("LG-D856")) || (paramString2.equals("LG-D857")) || (paramString2.equals("LG-D858")) || (paramString2.equals("LG-D858HK")) || (paramString2.equals("LG-D859")) || (paramString2.equals("LG-F400K")) || (paramString2.equals("LG-F400L")) || (paramString2.equals("LG-F400S")) || (paramString2.equals("LGL24")) || (paramString2.equals("LGLS990")) || (paramString2.equals("LGUS990")) || (paramString2.equals("LGV31")) || (paramString2.equals("VS985 4G"))))) {
      return "LG G3";
    }
    if (((paramString1 != null) && (paramString1.equals("p1"))) || ((paramString2 != null) && ((paramString2.equals("AS986")) || (paramString2.equals("LG-AS811")) || (paramString2.equals("LG-AS991")) || (paramString2.equals("LG-F500K")) || (paramString2.equals("LG-F500L")) || (paramString2.equals("LG-F500S")) || (paramString2.equals("LG-H810")) || (paramString2.equals("LG-H811")) || (paramString2.equals("LG-H812")) || (paramString2.equals("LG-H815")) || (paramString2.equals("LG-H818")) || (paramString2.equals("LG-H819")) || (paramString2.equals("LGLS991")) || (paramString2.equals("LGUS991")) || (paramString2.equals("LGV32")) || (paramString2.equals("VS986"))))) {
      return "LG G4";
    }
    if (((paramString1 != null) && (paramString1.equals("h1"))) || ((paramString2 != null) && ((paramString2.equals("LG-F700K")) || (paramString2.equals("LG-F700L")) || (paramString2.equals("LG-F700S")) || (paramString2.equals("LG-H820")) || (paramString2.equals("LG-H820PR")) || (paramString2.equals("LG-H830")) || (paramString2.equals("LG-H831")) || (paramString2.equals("LG-H850")) || (paramString2.equals("LG-H858")) || (paramString2.equals("LG-H860")) || (paramString2.equals("LG-H868")) || (paramString2.equals("LGAS992")) || (paramString2.equals("LGLS992")) || (paramString2.equals("LGUS992")) || (paramString2.equals("RS988")) || (paramString2.equals("VS987"))))) {
      return "LG G5";
    }
    if (((paramString1 != null) && (paramString1.equals("lucye"))) || ((paramString2 != null) && ((paramString2.equals("LG-AS993")) || (paramString2.equals("LG-H870")) || (paramString2.equals("LG-H870AR")) || (paramString2.equals("LG-H870DS")) || (paramString2.equals("LG-H870I")) || (paramString2.equals("LG-H870S")) || (paramString2.equals("LG-H871")) || (paramString2.equals("LG-H872")) || (paramString2.equals("LG-H872PR")) || (paramString2.equals("LG-H873")) || (paramString2.equals("LG-LS993")) || (paramString2.equals("LGM-G600K")) || (paramString2.equals("LGM-G600L")) || (paramString2.equals("LGM-G600S")) || (paramString2.equals("LGUS997")) || (paramString2.equals("VS988"))))) {
      return "LG G6";
    }
    if ((paramString1 != null) && (paramString1.equals("mako"))) {
      return "Nexus 4";
    }
    if ((paramString1 != null) && (paramString1.equals("hammerhead"))) {
      return "Nexus 5";
    }
    if ((paramString1 != null) && (paramString1.equals("bullhead"))) {
      return "Nexus 5X";
    }
    if ((paramString1 != null) && (paramString1.equals("shamu"))) {
      return "Nexus 6";
    }
    if (((paramString1 != null) && (paramString1.equals("OnePlus3"))) || ((paramString2 != null) && (paramString2.equals("ONEPLUS A3000")))) {
      return "OnePlus3";
    }
    if (((paramString1 != null) && (paramString1.equals("OnePlus3T"))) || ((paramString2 != null) && (paramString2.equals("ONEPLUS A3000")))) {
      return "OnePlus3T";
    }
    if (((paramString1 != null) && (paramString1.equals("OnePlus5"))) || ((paramString2 != null) && (paramString2.equals("ONEPLUS A5000")))) {
      return "OnePlus5";
    }
    if (((paramString1 != null) && (paramString1.equals("OnePlus5T"))) || ((paramString2 != null) && (paramString2.equals("ONEPLUS A5010")))) {
      return "OnePlus5T";
    }
    if (((paramString1 != null) && ((paramString1.equals("a53g")) || (paramString1.equals("a5lte")) || (paramString1.equals("a5ltechn")) || (paramString1.equals("a5ltectc")) || (paramString1.equals("a5ltezh")) || (paramString1.equals("a5ltezt")) || (paramString1.equals("a5ulte")) || (paramString1.equals("a5ultebmc")) || (paramString1.equals("a5ultektt")) || (paramString1.equals("a5ultelgt")) || (paramString1.equals("a5ulteskt")))) || ((paramString2 != null) && ((paramString2.equals("SM-A5000")) || (paramString2.equals("SM-A5009")) || (paramString2.equals("SM-A500F")) || (paramString2.equals("SM-A500F1")) || (paramString2.equals("SM-A500FU")) || (paramString2.equals("SM-A500G")) || (paramString2.equals("SM-A500H")) || (paramString2.equals("SM-A500K")) || (paramString2.equals("SM-A500L")) || (paramString2.equals("SM-A500M")) || (paramString2.equals("SM-A500S")) || (paramString2.equals("SM-A500W")) || (paramString2.equals("SM-A500X")) || (paramString2.equals("SM-A500XZ")) || (paramString2.equals("SM-A500Y")) || (paramString2.equals("SM-A500YZ"))))) {
      return "Galaxy A5";
    }
    if (((paramString1 != null) && (paramString1.equals("vivaltods5m"))) || ((paramString2 != null) && ((paramString2.equals("SM-G313HU")) || (paramString2.equals("SM-G313HY")) || (paramString2.equals("SM-G313M")) || (paramString2.equals("SM-G313MY"))))) {
      return "Galaxy Ace 4";
    }
    if (((paramString1 != null) && ((paramString1.equals("GT-S6352")) || (paramString1.equals("GT-S6802")) || (paramString1.equals("GT-S6802B")) || (paramString1.equals("SCH-I579")) || (paramString1.equals("SCH-I589")) || (paramString1.equals("SCH-i579")) || (paramString1.equals("SCH-i589")))) || ((paramString2 != null) && ((paramString2.equals("GT-S6352")) || (paramString2.equals("GT-S6802")) || (paramString2.equals("GT-S6802B")) || (paramString2.equals("SCH-I589")) || (paramString2.equals("SCH-i579")) || (paramString2.equals("SCH-i589"))))) {
      return "Galaxy Ace Duos";
    }
    if (((paramString1 != null) && ((paramString1.equals("GT-S7500")) || (paramString1.equals("GT-S7500L")) || (paramString1.equals("GT-S7500T")) || (paramString1.equals("GT-S7500W")) || (paramString1.equals("GT-S7508")))) || ((paramString2 != null) && ((paramString2.equals("GT-S7500")) || (paramString2.equals("GT-S7500L")) || (paramString2.equals("GT-S7500T")) || (paramString2.equals("GT-S7500W")) || (paramString2.equals("GT-S7508"))))) {
      return "Galaxy Ace Plus";
    }
    if (((paramString1 != null) && ((paramString1.equals("heat3gtfnvzw")) || (paramString1.equals("heatnfc3g")) || (paramString1.equals("heatqlte")))) || ((paramString2 != null) && ((paramString2.equals("SM-G310HN")) || (paramString2.equals("SM-G357FZ")) || (paramString2.equals("SM-S765C")) || (paramString2.equals("SM-S766C"))))) {
      return "Galaxy Ace Style";
    }
    if (((paramString1 != null) && ((paramString1.equals("vivalto3g")) || (paramString1.equals("vivalto3mve3g")) || (paramString1.equals("vivalto5mve3g")) || (paramString1.equals("vivaltolte")) || (paramString1.equals("vivaltonfc3g")))) || ((paramString2 != null) && ((paramString2.equals("SM-G313F")) || (paramString2.equals("SM-G313HN")) || (paramString2.equals("SM-G313ML")) || (paramString2.equals("SM-G313MU")) || (paramString2.equals("SM-G316H")) || (paramString2.equals("SM-G316HU")) || (paramString2.equals("SM-G316M")) || (paramString2.equals("SM-G316MY"))))) {
      return "Galaxy Ace4";
    }
    if (((paramString1 != null) && ((paramString1.equals("core33g")) || (paramString1.equals("coreprimelte")) || (paramString1.equals("coreprimelteaio")) || (paramString1.equals("coreprimeltelra")) || (paramString1.equals("coreprimeltespr")) || (paramString1.equals("coreprimeltetfnvzw")) || (paramString1.equals("coreprimeltevzw")) || (paramString1.equals("coreprimeve3g")) || (paramString1.equals("coreprimevelte")) || (paramString1.equals("cprimeltemtr")) || (paramString1.equals("cprimeltetmo")) || (paramString1.equals("rossalte")) || (paramString1.equals("rossaltectc")) || (paramString1.equals("rossaltexsa")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G360AZ")) || (paramString2.equals("SM-G3606")) || (paramString2.equals("SM-G3608")) || (paramString2.equals("SM-G3609")) || (paramString2.equals("SM-G360F")) || (paramString2.equals("SM-G360FY")) || (paramString2.equals("SM-G360GY")) || (paramString2.equals("SM-G360H")) || (paramString2.equals("SM-G360HU")) || (paramString2.equals("SM-G360M")) || (paramString2.equals("SM-G360P")) || (paramString2.equals("SM-G360R6")) || (paramString2.equals("SM-G360T")) || (paramString2.equals("SM-G360T1")) || (paramString2.equals("SM-G360V")) || (paramString2.equals("SM-G361F")) || (paramString2.equals("SM-G361H")) || (paramString2.equals("SM-G361HU")) || (paramString2.equals("SM-G361M")) || (paramString2.equals("SM-S820L"))))) {
      return "Galaxy Core Prime";
    }
    if (((paramString1 != null) && ((paramString1.equals("kanas")) || (paramString1.equals("kanas3g")) || (paramString1.equals("kanas3gcmcc")) || (paramString1.equals("kanas3gctc")) || (paramString1.equals("kanas3gnfc")))) || ((paramString2 != null) && ((paramString2.equals("SM-G3556D")) || (paramString2.equals("SM-G3558")) || (paramString2.equals("SM-G3559")) || (paramString2.equals("SM-G355H")) || (paramString2.equals("SM-G355HN")) || (paramString2.equals("SM-G355HQ")) || (paramString2.equals("SM-G355M"))))) {
      return "Galaxy Core2";
    }
    if (((paramString1 != null) && ((paramString1.equals("e53g")) || (paramString1.equals("e5lte")) || (paramString1.equals("e5ltetfnvzw")) || (paramString1.equals("e5ltetw")))) || ((paramString2 != null) && ((paramString2.equals("SM-E500F")) || (paramString2.equals("SM-E500H")) || (paramString2.equals("SM-E500M")) || (paramString2.equals("SM-E500YZ")) || (paramString2.equals("SM-S978L"))))) {
      return "Galaxy E5";
    }
    if (((paramString1 != null) && ((paramString1.equals("e73g")) || (paramString1.equals("e7lte")) || (paramString1.equals("e7ltechn")) || (paramString1.equals("e7ltectc")) || (paramString1.equals("e7ltehktw")))) || ((paramString2 != null) && ((paramString2.equals("SM-E7000")) || (paramString2.equals("SM-E7009")) || (paramString2.equals("SM-E700F")) || (paramString2.equals("SM-E700H")) || (paramString2.equals("SM-E700M"))))) {
      return "Galaxy E7";
    }
    if (((paramString1 != null) && ((paramString1.equals("SCH-I629")) || (paramString1.equals("nevis")) || (paramString1.equals("nevis3g")) || (paramString1.equals("nevis3gcmcc")) || (paramString1.equals("nevisds")) || (paramString1.equals("nevisnvess")) || (paramString1.equals("nevisp")) || (paramString1.equals("nevisvess")) || (paramString1.equals("nevisw")))) || ((paramString2 != null) && ((paramString2.equals("GT-S6790")) || (paramString2.equals("GT-S6790E")) || (paramString2.equals("GT-S6790L")) || (paramString2.equals("GT-S6790N")) || (paramString2.equals("GT-S6810")) || (paramString2.equals("GT-S6810B")) || (paramString2.equals("GT-S6810E")) || (paramString2.equals("GT-S6810L")) || (paramString2.equals("GT-S6810M")) || (paramString2.equals("GT-S6810P")) || (paramString2.equals("GT-S6812")) || (paramString2.equals("GT-S6812B")) || (paramString2.equals("GT-S6812C")) || (paramString2.equals("GT-S6812i")) || (paramString2.equals("GT-S6818")) || (paramString2.equals("GT-S6818V")) || (paramString2.equals("SCH-I629"))))) {
      return "Galaxy Fame";
    }
    if (((paramString1 != null) && (paramString1.equals("grandprimelteatt"))) || ((paramString2 != null) && (paramString2.equals("SAMSUNG-SM-G530A")))) {
      return "Galaxy Go Prime";
    }
    if (((paramString1 != null) && ((paramString1.equals("baffinlite")) || (paramString1.equals("baffinlitedtv")) || (paramString1.equals("baffinq3g")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9060")) || (paramString2.equals("GT-I9060L")) || (paramString2.equals("GT-I9063T")) || (paramString2.equals("GT-I9082C")) || (paramString2.equals("GT-I9168")) || (paramString2.equals("GT-I9168I"))))) {
      return "Galaxy Grand Neo";
    }
    if (((paramString1 != null) && ((paramString1.equals("fortuna3g")) || (paramString1.equals("fortuna3gdtv")) || (paramString1.equals("fortunalte")) || (paramString1.equals("fortunaltectc")) || (paramString1.equals("fortunaltezh")) || (paramString1.equals("fortunaltezt")) || (paramString1.equals("fortunave3g")) || (paramString1.equals("gprimelteacg")) || (paramString1.equals("gprimeltecan")) || (paramString1.equals("gprimeltemtr")) || (paramString1.equals("gprimeltespr")) || (paramString1.equals("gprimeltetfnvzw")) || (paramString1.equals("gprimeltetmo")) || (paramString1.equals("gprimelteusc")) || (paramString1.equals("grandprimelte")) || (paramString1.equals("grandprimelteaio")) || (paramString1.equals("grandprimeve3g")) || (paramString1.equals("grandprimeve3gdtv")) || (paramString1.equals("grandprimevelte")) || (paramString1.equals("grandprimevelteltn")) || (paramString1.equals("grandprimeveltezt")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G530AZ")) || (paramString2.equals("SM-G5306W")) || (paramString2.equals("SM-G5308W")) || (paramString2.equals("SM-G5309W")) || (paramString2.equals("SM-G530BT")) || (paramString2.equals("SM-G530F")) || (paramString2.equals("SM-G530FZ")) || (paramString2.equals("SM-G530H")) || (paramString2.equals("SM-G530M")) || (paramString2.equals("SM-G530MU")) || (paramString2.equals("SM-G530P")) || (paramString2.equals("SM-G530R4")) || (paramString2.equals("SM-G530R7")) || (paramString2.equals("SM-G530T")) || (paramString2.equals("SM-G530T1")) || (paramString2.equals("SM-G530W")) || (paramString2.equals("SM-G530Y")) || (paramString2.equals("SM-G531BT")) || (paramString2.equals("SM-G531F")) || (paramString2.equals("SM-G531H")) || (paramString2.equals("SM-G531M")) || (paramString2.equals("SM-G531Y")) || (paramString2.equals("SM-S920L")) || (paramString2.equals("gprimelteacg"))))) {
      return "Galaxy Grand Prime";
    }
    if (((paramString1 != null) && ((paramString1.equals("ms013g")) || (paramString1.equals("ms013gdtv")) || (paramString1.equals("ms013gss")) || (paramString1.equals("ms01lte")) || (paramString1.equals("ms01ltektt")) || (paramString1.equals("ms01ltelgt")) || (paramString1.equals("ms01lteskt")))) || ((paramString2 != null) && ((paramString2.equals("SM-G710")) || (paramString2.equals("SM-G7102")) || (paramString2.equals("SM-G7102T")) || (paramString2.equals("SM-G7105")) || (paramString2.equals("SM-G7105H")) || (paramString2.equals("SM-G7105L")) || (paramString2.equals("SM-G7106")) || (paramString2.equals("SM-G7108")) || (paramString2.equals("SM-G7109")) || (paramString2.equals("SM-G710K")) || (paramString2.equals("SM-G710L")) || (paramString2.equals("SM-G710S"))))) {
      return "Galaxy Grand2";
    }
    if (((paramString1 != null) && ((paramString1.equals("j13g")) || (paramString1.equals("j13gtfnvzw")) || (paramString1.equals("j1lte")) || (paramString1.equals("j1nlte")) || (paramString1.equals("j1qltevzw")) || (paramString1.equals("j1xlte")) || (paramString1.equals("j1xlteaio")) || (paramString1.equals("j1xlteatt")) || (paramString1.equals("j1xltecan")) || (paramString1.equals("j1xqltespr")) || (paramString1.equals("j1xqltetfnvzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-J120A")) || (paramString2.equals("SAMSUNG-SM-J120AZ")) || (paramString2.equals("SM-J100F")) || (paramString2.equals("SM-J100FN")) || (paramString2.equals("SM-J100G")) || (paramString2.equals("SM-J100H")) || (paramString2.equals("SM-J100M")) || (paramString2.equals("SM-J100ML")) || (paramString2.equals("SM-J100MU")) || (paramString2.equals("SM-J100VPP")) || (paramString2.equals("SM-J100Y")) || (paramString2.equals("SM-J120F")) || (paramString2.equals("SM-J120FN")) || (paramString2.equals("SM-J120M")) || (paramString2.equals("SM-J120P")) || (paramString2.equals("SM-J120W")) || (paramString2.equals("SM-S120VL")) || (paramString2.equals("SM-S777C"))))) {
      return "Galaxy J1";
    }
    if (((paramString1 != null) && ((paramString1.equals("j1acelte")) || (paramString1.equals("j1acelteltn")) || (paramString1.equals("j1acevelte")) || (paramString1.equals("j1pop3g")))) || ((paramString2 != null) && ((paramString2.equals("SM-J110F")) || (paramString2.equals("SM-J110G")) || (paramString2.equals("SM-J110H")) || (paramString2.equals("SM-J110L")) || (paramString2.equals("SM-J110M")) || (paramString2.equals("SM-J111F")) || (paramString2.equals("SM-J111M"))))) {
      return "Galaxy J1 Ace";
    }
    if (((paramString1 != null) && ((paramString1.equals("j53g")) || (paramString1.equals("j5lte")) || (paramString1.equals("j5ltechn")) || (paramString1.equals("j5ltekx")) || (paramString1.equals("j5nlte")) || (paramString1.equals("j5ylte")))) || ((paramString2 != null) && ((paramString2.equals("SM-J5007")) || (paramString2.equals("SM-J5008")) || (paramString2.equals("SM-J500F")) || (paramString2.equals("SM-J500FN")) || (paramString2.equals("SM-J500G")) || (paramString2.equals("SM-J500H")) || (paramString2.equals("SM-J500M")) || (paramString2.equals("SM-J500N0")) || (paramString2.equals("SM-J500Y"))))) {
      return "Galaxy J5";
    }
    if (((paramString1 != null) && ((paramString1.equals("j75ltektt")) || (paramString1.equals("j7e3g")) || (paramString1.equals("j7elte")) || (paramString1.equals("j7ltechn")))) || ((paramString2 != null) && ((paramString2.equals("SM-J7008")) || (paramString2.equals("SM-J700F")) || (paramString2.equals("SM-J700H")) || (paramString2.equals("SM-J700K")) || (paramString2.equals("SM-J700M"))))) {
      return "Galaxy J7";
    }
    if (((paramString1 != null) && ((paramString1.equals("maguro")) || (paramString1.equals("toro")) || (paramString1.equals("toroplus")))) || ((paramString2 != null) && (paramString2.equals("Galaxy X")))) {
      return "Galaxy Nexus";
    }
    if (((paramString1 != null) && ((paramString1.equals("lt033g")) || (paramString1.equals("lt03ltektt")) || (paramString1.equals("lt03ltelgt")) || (paramString1.equals("lt03lteskt")) || (paramString1.equals("p4notelte")) || (paramString1.equals("p4noteltektt")) || (paramString1.equals("p4noteltelgt")) || (paramString1.equals("p4notelteskt")) || (paramString1.equals("p4noteltespr")) || (paramString1.equals("p4notelteusc")) || (paramString1.equals("p4noteltevzw")) || (paramString1.equals("p4noterf")) || (paramString1.equals("p4noterfktt")) || (paramString1.equals("p4notewifi")) || (paramString1.equals("p4notewifi43241any")) || (paramString1.equals("p4notewifiany")) || (paramString1.equals("p4notewifiktt")) || (paramString1.equals("p4notewifiww")))) || ((paramString2 != null) && ((paramString2.equals("GT-N8000")) || (paramString2.equals("GT-N8005")) || (paramString2.equals("GT-N8010")) || (paramString2.equals("GT-N8013")) || (paramString2.equals("GT-N8020")) || (paramString2.equals("SCH-I925")) || (paramString2.equals("SCH-I925U")) || (paramString2.equals("SHV-E230K")) || (paramString2.equals("SHV-E230L")) || (paramString2.equals("SHV-E230S")) || (paramString2.equals("SHW-M480K")) || (paramString2.equals("SHW-M480W")) || (paramString2.equals("SHW-M485W")) || (paramString2.equals("SHW-M486W")) || (paramString2.equals("SM-P601")) || (paramString2.equals("SM-P602")) || (paramString2.equals("SM-P605K")) || (paramString2.equals("SM-P605L")) || (paramString2.equals("SM-P605S")) || (paramString2.equals("SPH-P600"))))) {
      return "Galaxy Note 10.1";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-01G")) || (paramString1.equals("SCL24")) || (paramString1.equals("tbeltektt")) || (paramString1.equals("tbeltelgt")) || (paramString1.equals("tbelteskt")) || (paramString1.equals("tblte")) || (paramString1.equals("tblteatt")) || (paramString1.equals("tbltecan")) || (paramString1.equals("tbltechn")) || (paramString1.equals("tbltespr")) || (paramString1.equals("tbltetmo")) || (paramString1.equals("tblteusc")) || (paramString1.equals("tbltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-N915A")) || (paramString2.equals("SC-01G")) || (paramString2.equals("SCL24")) || (paramString2.equals("SM-N9150")) || (paramString2.equals("SM-N915F")) || (paramString2.equals("SM-N915FY")) || (paramString2.equals("SM-N915G")) || (paramString2.equals("SM-N915K")) || (paramString2.equals("SM-N915L")) || (paramString2.equals("SM-N915P")) || (paramString2.equals("SM-N915R4")) || (paramString2.equals("SM-N915S")) || (paramString2.equals("SM-N915T")) || (paramString2.equals("SM-N915T3")) || (paramString2.equals("SM-N915V")) || (paramString2.equals("SM-N915W8")) || (paramString2.equals("SM-N915X"))))) {
      return "Galaxy Note Edge";
    }
    if (((paramString1 != null) && ((paramString1.equals("v1a3g")) || (paramString1.equals("v1awifi")) || (paramString1.equals("v1awifikx")) || (paramString1.equals("viennalte")) || (paramString1.equals("viennalteatt")) || (paramString1.equals("viennaltekx")) || (paramString1.equals("viennaltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-P907A")) || (paramString2.equals("SM-P900")) || (paramString2.equals("SM-P901")) || (paramString2.equals("SM-P905")) || (paramString2.equals("SM-P905F0")) || (paramString2.equals("SM-P905M")) || (paramString2.equals("SM-P905V"))))) {
      return "Galaxy Note Pro 12.2";
    }
    if (((paramString1 != null) && ((paramString1.equals("tre3caltektt")) || (paramString1.equals("tre3caltelgt")) || (paramString1.equals("tre3calteskt")) || (paramString1.equals("tre3g")) || (paramString1.equals("trelte")) || (paramString1.equals("treltektt")) || (paramString1.equals("treltelgt")) || (paramString1.equals("trelteskt")) || (paramString1.equals("trhplte")) || (paramString1.equals("trlte")) || (paramString1.equals("trlteatt")) || (paramString1.equals("trltecan")) || (paramString1.equals("trltechn")) || (paramString1.equals("trltechnzh")) || (paramString1.equals("trltespr")) || (paramString1.equals("trltetmo")) || (paramString1.equals("trlteusc")) || (paramString1.equals("trltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-N910A")) || (paramString2.equals("SM-N9100")) || (paramString2.equals("SM-N9106W")) || (paramString2.equals("SM-N9108V")) || (paramString2.equals("SM-N9109W")) || (paramString2.equals("SM-N910C")) || (paramString2.equals("SM-N910F")) || (paramString2.equals("SM-N910G")) || (paramString2.equals("SM-N910H")) || (paramString2.equals("SM-N910K")) || (paramString2.equals("SM-N910L")) || (paramString2.equals("SM-N910P")) || (paramString2.equals("SM-N910R4")) || (paramString2.equals("SM-N910S")) || (paramString2.equals("SM-N910T")) || (paramString2.equals("SM-N910T2")) || (paramString2.equals("SM-N910T3")) || (paramString2.equals("SM-N910U")) || (paramString2.equals("SM-N910V")) || (paramString2.equals("SM-N910W8")) || (paramString2.equals("SM-N910X")) || (paramString2.equals("SM-N916K")) || (paramString2.equals("SM-N916L")) || (paramString2.equals("SM-N916S"))))) {
      return "Galaxy Note4";
    }
    if (((paramString1 != null) && ((paramString1.equals("noblelte")) || (paramString1.equals("noblelteacg")) || (paramString1.equals("noblelteatt")) || (paramString1.equals("nobleltebmc")) || (paramString1.equals("nobleltechn")) || (paramString1.equals("nobleltecmcc")) || (paramString1.equals("nobleltehk")) || (paramString1.equals("nobleltektt")) || (paramString1.equals("nobleltelgt")) || (paramString1.equals("nobleltelra")) || (paramString1.equals("noblelteskt")) || (paramString1.equals("nobleltespr")) || (paramString1.equals("nobleltetmo")) || (paramString1.equals("noblelteusc")) || (paramString1.equals("nobleltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-N920A")) || (paramString2.equals("SM-N9200")) || (paramString2.equals("SM-N9208")) || (paramString2.equals("SM-N920C")) || (paramString2.equals("SM-N920F")) || (paramString2.equals("SM-N920G")) || (paramString2.equals("SM-N920I")) || (paramString2.equals("SM-N920K")) || (paramString2.equals("SM-N920L")) || (paramString2.equals("SM-N920P")) || (paramString2.equals("SM-N920R4")) || (paramString2.equals("SM-N920R6")) || (paramString2.equals("SM-N920R7")) || (paramString2.equals("SM-N920S")) || (paramString2.equals("SM-N920T")) || (paramString2.equals("SM-N920V")) || (paramString2.equals("SM-N920W8")) || (paramString2.equals("SM-N920X"))))) {
      return "Galaxy Note5";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-01J")) || (paramString1.equals("SCV34")) || (paramString1.equals("gracelte")) || (paramString1.equals("graceltektt")) || (paramString1.equals("graceltelgt")) || (paramString1.equals("gracelteskt")) || (paramString1.equals("graceqlteacg")) || (paramString1.equals("graceqlteatt")) || (paramString1.equals("graceqltebmc")) || (paramString1.equals("graceqltechn")) || (paramString1.equals("graceqltedcm")) || (paramString1.equals("graceqltelra")) || (paramString1.equals("graceqltespr")) || (paramString1.equals("graceqltetfnvzw")) || (paramString1.equals("graceqltetmo")) || (paramString1.equals("graceqlteue")) || (paramString1.equals("graceqlteusc")) || (paramString1.equals("graceqltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-N930A")) || (paramString2.equals("SC-01J")) || (paramString2.equals("SCV34")) || (paramString2.equals("SGH-N037")) || (paramString2.equals("SM-N9300")) || (paramString2.equals("SM-N930F")) || (paramString2.equals("SM-N930K")) || (paramString2.equals("SM-N930L")) || (paramString2.equals("SM-N930P")) || (paramString2.equals("SM-N930R4")) || (paramString2.equals("SM-N930R6")) || (paramString2.equals("SM-N930R7")) || (paramString2.equals("SM-N930S")) || (paramString2.equals("SM-N930T")) || (paramString2.equals("SM-N930U")) || (paramString2.equals("SM-N930V")) || (paramString2.equals("SM-N930VL")) || (paramString2.equals("SM-N930W8")) || (paramString2.equals("SM-N930X"))))) {
      return "Galaxy Note7";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-01K")) || (paramString1.equals("SCV37")) || (paramString1.equals("greatlte")) || (paramString1.equals("greatlteks")) || (paramString1.equals("greatqlte")) || (paramString1.equals("greatqltechn")) || (paramString1.equals("greatqltecmcc")) || (paramString1.equals("greatqltecs")) || (paramString1.equals("greatqlteue")))) || ((paramString2 != null) && ((paramString2.equals("SC-01K")) || (paramString2.equals("SCV37")) || (paramString2.equals("SM-N9500")) || (paramString2.equals("SM-N9508")) || (paramString2.equals("SM-N950F")) || (paramString2.equals("SM-N950N")) || (paramString2.equals("SM-N950U")) || (paramString2.equals("SM-N950U1")) || (paramString2.equals("SM-N950W")) || (paramString2.equals("SM-N950XN"))))) {
      return "Galaxy Note8";
    }
    if (((paramString1 != null) && ((paramString1.equals("o5lte")) || (paramString1.equals("o5ltechn")) || (paramString1.equals("o5prolte")) || (paramString1.equals("on5ltemtr")) || (paramString1.equals("on5ltetfntmo")) || (paramString1.equals("on5ltetmo")))) || ((paramString2 != null) && ((paramString2.equals("SM-G5500")) || (paramString2.equals("SM-G550FY")) || (paramString2.equals("SM-G550T")) || (paramString2.equals("SM-G550T1")) || (paramString2.equals("SM-G550T2")) || (paramString2.equals("SM-S550TL"))))) {
      return "Galaxy On5";
    }
    if (((paramString1 != null) && ((paramString1.equals("o7lte")) || (paramString1.equals("o7ltechn")) || (paramString1.equals("on7elte")))) || ((paramString2 != null) && ((paramString2.equals("SM-G6000")) || (paramString2.equals("SM-G600F")) || (paramString2.equals("SM-G600FY"))))) {
      return "Galaxy On7";
    }
    if (((paramString1 != null) && ((paramString1.equals("GT-I9000")) || (paramString1.equals("GT-I9000B")) || (paramString1.equals("GT-I9000M")) || (paramString1.equals("GT-I9000T")) || (paramString1.equals("GT-I9003")) || (paramString1.equals("GT-I9003L")) || (paramString1.equals("GT-I9008L")) || (paramString1.equals("GT-I9010")) || (paramString1.equals("GT-I9018")) || (paramString1.equals("GT-I9050")) || (paramString1.equals("SC-02B")) || (paramString1.equals("SCH-I500")) || (paramString1.equals("SCH-S950C")) || (paramString1.equals("SCH-i909")) || (paramString1.equals("SGH-I897")) || (paramString1.equals("SGH-T959V")) || (paramString1.equals("SGH-T959W")) || (paramString1.equals("SHW-M110S")) || (paramString1.equals("SHW-M190S")) || (paramString1.equals("SPH-D700")) || (paramString1.equals("loganlte")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9000")) || (paramString2.equals("GT-I9000B")) || (paramString2.equals("GT-I9000M")) || (paramString2.equals("GT-I9000T")) || (paramString2.equals("GT-I9003")) || (paramString2.equals("GT-I9003L")) || (paramString2.equals("GT-I9008L")) || (paramString2.equals("GT-I9010")) || (paramString2.equals("GT-I9018")) || (paramString2.equals("GT-I9050")) || (paramString2.equals("GT-S7275")) || (paramString2.equals("SAMSUNG-SGH-I897")) || (paramString2.equals("SC-02B")) || (paramString2.equals("SCH-I500")) || (paramString2.equals("SCH-S950C")) || (paramString2.equals("SCH-i909")) || (paramString2.equals("SGH-T959V")) || (paramString2.equals("SGH-T959W")) || (paramString2.equals("SHW-M110S")) || (paramString2.equals("SHW-M190S")) || (paramString2.equals("SPH-D700"))))) {
      return "Galaxy S";
    }
    if (((paramString1 != null) && ((paramString1.equals("kylechn")) || (paramString1.equals("kyleopen")) || (paramString1.equals("kyletdcmcc")))) || ((paramString2 != null) && ((paramString2.equals("GT-S7562")) || (paramString2.equals("GT-S7568"))))) {
      return "Galaxy S Duos";
    }
    if (((paramString1 != null) && (paramString1.equals("kyleprods"))) || ((paramString2 != null) && ((paramString2.equals("GT-S7582")) || (paramString2.equals("GT-S7582L"))))) {
      return "Galaxy S Duos2";
    }
    if (((paramString1 != null) && (paramString1.equals("vivalto3gvn"))) || ((paramString2 != null) && (paramString2.equals("SM-G313HZ")))) {
      return "Galaxy S Duos3";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-03E")) || (paramString1.equals("c1att")) || (paramString1.equals("c1ktt")) || (paramString1.equals("c1lgt")) || (paramString1.equals("c1skt")) || (paramString1.equals("d2att")) || (paramString1.equals("d2can")) || (paramString1.equals("d2cri")) || (paramString1.equals("d2dcm")) || (paramString1.equals("d2lteMetroPCS")) || (paramString1.equals("d2lterefreshspr")) || (paramString1.equals("d2ltetmo")) || (paramString1.equals("d2mtr")) || (paramString1.equals("d2spi")) || (paramString1.equals("d2spr")) || (paramString1.equals("d2tfnspr")) || (paramString1.equals("d2tfnvzw")) || (paramString1.equals("d2tmo")) || (paramString1.equals("d2usc")) || (paramString1.equals("d2vmu")) || (paramString1.equals("d2vzw")) || (paramString1.equals("d2xar")) || (paramString1.equals("m0")) || (paramString1.equals("m0apt")) || (paramString1.equals("m0chn")) || (paramString1.equals("m0cmcc")) || (paramString1.equals("m0ctc")) || (paramString1.equals("m0ctcduos")) || (paramString1.equals("m0skt")) || (paramString1.equals("m3")) || (paramString1.equals("m3dcm")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9300")) || (paramString2.equals("GT-I9300T")) || (paramString2.equals("GT-I9305")) || (paramString2.equals("GT-I9305N")) || (paramString2.equals("GT-I9305T")) || (paramString2.equals("GT-I9308")) || (paramString2.equals("Gravity")) || (paramString2.equals("GravityQuad")) || (paramString2.equals("SAMSUNG-SGH-I747")) || (paramString2.equals("SC-03E")) || (paramString2.equals("SC-06D")) || (paramString2.equals("SCH-I535")) || (paramString2.equals("SCH-I535PP")) || (paramString2.equals("SCH-I939")) || (paramString2.equals("SCH-I939D")) || (paramString2.equals("SCH-L710")) || (paramString2.equals("SCH-R530C")) || (paramString2.equals("SCH-R530M")) || (paramString2.equals("SCH-R530U")) || (paramString2.equals("SCH-R530X")) || (paramString2.equals("SCH-S960L")) || (paramString2.equals("SCH-S968C")) || (paramString2.equals("SGH-I747M")) || (paramString2.equals("SGH-I748")) || (paramString2.equals("SGH-T999")) || (paramString2.equals("SGH-T999L")) || (paramString2.equals("SGH-T999N")) || (paramString2.equals("SGH-T999V")) || (paramString2.equals("SHV-E210K")) || (paramString2.equals("SHV-E210L")) || (paramString2.equals("SHV-E210S")) || (paramString2.equals("SHW-M440S")) || (paramString2.equals("SPH-L710")) || (paramString2.equals("SPH-L710T"))))) {
      return "Galaxy S3";
    }
    if (((paramString1 != null) && ((paramString1.equals("golden")) || (paramString1.equals("goldenlteatt")) || (paramString1.equals("goldenltebmc")) || (paramString1.equals("goldenltevzw")) || (paramString1.equals("goldenve3g")))) || ((paramString2 != null) && ((paramString2.equals("GT-I8190")) || (paramString2.equals("GT-I8190L")) || (paramString2.equals("GT-I8190N")) || (paramString2.equals("GT-I8190T")) || (paramString2.equals("GT-I8200L")) || (paramString2.equals("SAMSUNG-SM-G730A")) || (paramString2.equals("SM-G730V")) || (paramString2.equals("SM-G730W8"))))) {
      return "Galaxy S3 Mini";
    }
    if (((paramString1 != null) && ((paramString1.equals("goldenve3g")) || (paramString1.equals("goldenvess3g")))) || ((paramString2 != null) && ((paramString2.equals("GT-I8200")) || (paramString2.equals("GT-I8200N")) || (paramString2.equals("GT-I8200Q"))))) {
      return "Galaxy S3 Mini Value Edition";
    }
    if (((paramString1 != null) && ((paramString1.equals("s3ve3g")) || (paramString1.equals("s3ve3gdd")) || (paramString1.equals("s3ve3gds")) || (paramString1.equals("s3ve3gdsdd")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9300I")) || (paramString2.equals("GT-I9301I")) || (paramString2.equals("GT-I9301Q"))))) {
      return "Galaxy S3 Neo";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-04E")) || (paramString1.equals("ja3g")) || (paramString1.equals("ja3gduosctc")) || (paramString1.equals("jaltektt")) || (paramString1.equals("jaltelgt")) || (paramString1.equals("jalteskt")) || (paramString1.equals("jflte")) || (paramString1.equals("jflteMetroPCS")) || (paramString1.equals("jflteaio")) || (paramString1.equals("jflteatt")) || (paramString1.equals("jfltecan")) || (paramString1.equals("jfltecri")) || (paramString1.equals("jfltecsp")) || (paramString1.equals("jfltelra")) || (paramString1.equals("jflterefreshspr")) || (paramString1.equals("jfltespr")) || (paramString1.equals("jfltetfnatt")) || (paramString1.equals("jfltetfntmo")) || (paramString1.equals("jfltetmo")) || (paramString1.equals("jflteusc")) || (paramString1.equals("jfltevzw")) || (paramString1.equals("jfltevzwpp")) || (paramString1.equals("jftdd")) || (paramString1.equals("jfvelte")) || (paramString1.equals("jfwifi")) || (paramString1.equals("jsglte")) || (paramString1.equals("ks01lte")) || (paramString1.equals("ks01ltektt")) || (paramString1.equals("ks01ltelgt")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9500")) || (paramString2.equals("GT-I9505")) || (paramString2.equals("GT-I9505X")) || (paramString2.equals("GT-I9506")) || (paramString2.equals("GT-I9507")) || (paramString2.equals("GT-I9507V")) || (paramString2.equals("GT-I9508")) || (paramString2.equals("GT-I9508C")) || (paramString2.equals("GT-I9508V")) || (paramString2.equals("GT-I9515")) || (paramString2.equals("GT-I9515L")) || (paramString2.equals("SAMSUNG-SGH-I337")) || (paramString2.equals("SAMSUNG-SGH-I337Z")) || (paramString2.equals("SC-04E")) || (paramString2.equals("SCH-I545")) || (paramString2.equals("SCH-I545L")) || (paramString2.equals("SCH-I545PP")) || (paramString2.equals("SCH-I959")) || (paramString2.equals("SCH-R970")) || (paramString2.equals("SCH-R970C")) || (paramString2.equals("SCH-R970X")) || (paramString2.equals("SGH-I337M")) || (paramString2.equals("SGH-M919")) || (paramString2.equals("SGH-M919N")) || (paramString2.equals("SGH-M919V")) || (paramString2.equals("SGH-S970G")) || (paramString2.equals("SHV-E300K")) || (paramString2.equals("SHV-E300L")) || (paramString2.equals("SHV-E300S")) || (paramString2.equals("SHV-E330K")) || (paramString2.equals("SHV-E330L")) || (paramString2.equals("SM-S975L")) || (paramString2.equals("SPH-L720")) || (paramString2.equals("SPH-L720T"))))) {
      return "Galaxy S4";
    }
    if (((paramString1 != null) && ((paramString1.equals("serrano3g")) || (paramString1.equals("serranods")) || (paramString1.equals("serranolte")) || (paramString1.equals("serranoltebmc")) || (paramString1.equals("serranoltektt")) || (paramString1.equals("serranoltekx")) || (paramString1.equals("serranoltelra")) || (paramString1.equals("serranoltespr")) || (paramString1.equals("serranolteusc")) || (paramString1.equals("serranoltevzw")) || (paramString1.equals("serranove3g")) || (paramString1.equals("serranovelte")) || (paramString1.equals("serranovolteatt")))) || ((paramString2 != null) && ((paramString2.equals("GT-I9190")) || (paramString2.equals("GT-I9192")) || (paramString2.equals("GT-I9192I")) || (paramString2.equals("GT-I9195")) || (paramString2.equals("GT-I9195I")) || (paramString2.equals("GT-I9195L")) || (paramString2.equals("GT-I9195T")) || (paramString2.equals("GT-I9195X")) || (paramString2.equals("GT-I9197")) || (paramString2.equals("SAMSUNG-SGH-I257")) || (paramString2.equals("SCH-I435")) || (paramString2.equals("SCH-I435L")) || (paramString2.equals("SCH-R890")) || (paramString2.equals("SGH-I257M")) || (paramString2.equals("SHV-E370D")) || (paramString2.equals("SHV-E370K")) || (paramString2.equals("SPH-L520"))))) {
      return "Galaxy S4 Mini";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-04F")) || (paramString1.equals("SCL23")) || (paramString1.equals("k3g")) || (paramString1.equals("klte")) || (paramString1.equals("klteMetroPCS")) || (paramString1.equals("klteacg")) || (paramString1.equals("klteaio")) || (paramString1.equals("klteatt")) || (paramString1.equals("kltecan")) || (paramString1.equals("klteduoszn")) || (paramString1.equals("kltektt")) || (paramString1.equals("kltelgt")) || (paramString1.equals("kltelra")) || (paramString1.equals("klteskt")) || (paramString1.equals("kltespr")) || (paramString1.equals("kltetfnvzw")) || (paramString1.equals("kltetmo")) || (paramString1.equals("klteusc")) || (paramString1.equals("kltevzw")) || (paramString1.equals("kwifi")) || (paramString1.equals("lentisltektt")) || (paramString1.equals("lentisltelgt")) || (paramString1.equals("lentislteskt")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G900A")) || (paramString2.equals("SAMSUNG-SM-G900AZ")) || (paramString2.equals("SC-04F")) || (paramString2.equals("SCL23")) || (paramString2.equals("SM-G9006W")) || (paramString2.equals("SM-G9008W")) || (paramString2.equals("SM-G9009W")) || (paramString2.equals("SM-G900F")) || (paramString2.equals("SM-G900FQ")) || (paramString2.equals("SM-G900H")) || (paramString2.equals("SM-G900I")) || (paramString2.equals("SM-G900K")) || (paramString2.equals("SM-G900L")) || (paramString2.equals("SM-G900M")) || (paramString2.equals("SM-G900MD")) || (paramString2.equals("SM-G900P")) || (paramString2.equals("SM-G900R4")) || (paramString2.equals("SM-G900R6")) || (paramString2.equals("SM-G900R7")) || (paramString2.equals("SM-G900S")) || (paramString2.equals("SM-G900T")) || (paramString2.equals("SM-G900T1")) || (paramString2.equals("SM-G900T3")) || (paramString2.equals("SM-G900T4")) || (paramString2.equals("SM-G900V")) || (paramString2.equals("SM-G900W8")) || (paramString2.equals("SM-G900X")) || (paramString2.equals("SM-G906K")) || (paramString2.equals("SM-G906L")) || (paramString2.equals("SM-G906S")) || (paramString2.equals("SM-S903VL"))))) {
      return "Galaxy S5";
    }
    if (((paramString1 != null) && ((paramString1.equals("s5neolte")) || (paramString1.equals("s5neoltecan")))) || ((paramString2 != null) && ((paramString2.equals("SM-G903F")) || (paramString2.equals("SM-G903M")) || (paramString2.equals("SM-G903W"))))) {
      return "Galaxy S5 Neo";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-05G")) || (paramString1.equals("zeroflte")) || (paramString1.equals("zeroflteacg")) || (paramString1.equals("zeroflteaio")) || (paramString1.equals("zeroflteatt")) || (paramString1.equals("zerofltebmc")) || (paramString1.equals("zerofltechn")) || (paramString1.equals("zerofltectc")) || (paramString1.equals("zerofltektt")) || (paramString1.equals("zerofltelgt")) || (paramString1.equals("zerofltelra")) || (paramString1.equals("zerofltemtr")) || (paramString1.equals("zeroflteskt")) || (paramString1.equals("zerofltespr")) || (paramString1.equals("zerofltetfnvzw")) || (paramString1.equals("zerofltetmo")) || (paramString1.equals("zeroflteusc")) || (paramString1.equals("zerofltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G920A")) || (paramString2.equals("SAMSUNG-SM-G920AZ")) || (paramString2.equals("SC-05G")) || (paramString2.equals("SM-G9200")) || (paramString2.equals("SM-G9208")) || (paramString2.equals("SM-G9209")) || (paramString2.equals("SM-G920F")) || (paramString2.equals("SM-G920I")) || (paramString2.equals("SM-G920K")) || (paramString2.equals("SM-G920L")) || (paramString2.equals("SM-G920P")) || (paramString2.equals("SM-G920R4")) || (paramString2.equals("SM-G920R6")) || (paramString2.equals("SM-G920R7")) || (paramString2.equals("SM-G920S")) || (paramString2.equals("SM-G920T")) || (paramString2.equals("SM-G920T1")) || (paramString2.equals("SM-G920V")) || (paramString2.equals("SM-G920W8")) || (paramString2.equals("SM-G920X")) || (paramString2.equals("SM-S906L")) || (paramString2.equals("SM-S907VL"))))) {
      return "Galaxy S6";
    }
    if (((paramString1 != null) && ((paramString1.equals("404SC")) || (paramString1.equals("SC-04G")) || (paramString1.equals("SCV31")) || (paramString1.equals("zerolte")) || (paramString1.equals("zerolteacg")) || (paramString1.equals("zerolteatt")) || (paramString1.equals("zeroltebmc")) || (paramString1.equals("zeroltechn")) || (paramString1.equals("zeroltektt")) || (paramString1.equals("zeroltelgt")) || (paramString1.equals("zeroltelra")) || (paramString1.equals("zerolteskt")) || (paramString1.equals("zeroltespr")) || (paramString1.equals("zeroltetmo")) || (paramString1.equals("zerolteusc")) || (paramString1.equals("zeroltevzw")))) || ((paramString2 != null) && ((paramString2.equals("404SC")) || (paramString2.equals("SAMSUNG-SM-G925A")) || (paramString2.equals("SC-04G")) || (paramString2.equals("SCV31")) || (paramString2.equals("SM-G9250")) || (paramString2.equals("SM-G925F")) || (paramString2.equals("SM-G925I")) || (paramString2.equals("SM-G925K")) || (paramString2.equals("SM-G925L")) || (paramString2.equals("SM-G925P")) || (paramString2.equals("SM-G925R4")) || (paramString2.equals("SM-G925R6")) || (paramString2.equals("SM-G925R7")) || (paramString2.equals("SM-G925S")) || (paramString2.equals("SM-G925T")) || (paramString2.equals("SM-G925V")) || (paramString2.equals("SM-G925W8")) || (paramString2.equals("SM-G925X"))))) {
      return "Galaxy S6 Edge";
    }
    if (((paramString1 != null) && ((paramString1.equals("zenlte")) || (paramString1.equals("zenlteatt")) || (paramString1.equals("zenltebmc")) || (paramString1.equals("zenltechn")) || (paramString1.equals("zenltektt")) || (paramString1.equals("zenltekx")) || (paramString1.equals("zenltelgt")) || (paramString1.equals("zenlteskt")) || (paramString1.equals("zenltespr")) || (paramString1.equals("zenltetmo")) || (paramString1.equals("zenlteusc")) || (paramString1.equals("zenltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G928A")) || (paramString2.equals("SM-G9280")) || (paramString2.equals("SM-G9287")) || (paramString2.equals("SM-G9287C")) || (paramString2.equals("SM-G928C")) || (paramString2.equals("SM-G928F")) || (paramString2.equals("SM-G928G")) || (paramString2.equals("SM-G928I")) || (paramString2.equals("SM-G928K")) || (paramString2.equals("SM-G928L")) || (paramString2.equals("SM-G928N0")) || (paramString2.equals("SM-G928P")) || (paramString2.equals("SM-G928R4")) || (paramString2.equals("SM-G928S")) || (paramString2.equals("SM-G928T")) || (paramString2.equals("SM-G928V")) || (paramString2.equals("SM-G928W8")) || (paramString2.equals("SM-G928X"))))) {
      return "Galaxy S6 Edge+";
    }
    if (((paramString1 != null) && ((paramString1.equals("herolte")) || (paramString1.equals("heroltebmc")) || (paramString1.equals("heroltektt")) || (paramString1.equals("heroltelgt")) || (paramString1.equals("herolteskt")) || (paramString1.equals("heroqlteacg")) || (paramString1.equals("heroqlteaio")) || (paramString1.equals("heroqlteatt")) || (paramString1.equals("heroqltecctvzw")) || (paramString1.equals("heroqltechn")) || (paramString1.equals("heroqltelra")) || (paramString1.equals("heroqltemtr")) || (paramString1.equals("heroqltespr")) || (paramString1.equals("heroqltetfnvzw")) || (paramString1.equals("heroqltetmo")) || (paramString1.equals("heroqlteue")) || (paramString1.equals("heroqlteusc")) || (paramString1.equals("heroqltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G930A")) || (paramString2.equals("SAMSUNG-SM-G930AZ")) || (paramString2.equals("SM-G9300")) || (paramString2.equals("SM-G9308")) || (paramString2.equals("SM-G930F")) || (paramString2.equals("SM-G930K")) || (paramString2.equals("SM-G930L")) || (paramString2.equals("SM-G930P")) || (paramString2.equals("SM-G930R4")) || (paramString2.equals("SM-G930R6")) || (paramString2.equals("SM-G930R7")) || (paramString2.equals("SM-G930S")) || (paramString2.equals("SM-G930T")) || (paramString2.equals("SM-G930T1")) || (paramString2.equals("SM-G930U")) || (paramString2.equals("SM-G930V")) || (paramString2.equals("SM-G930VC")) || (paramString2.equals("SM-G930VL")) || (paramString2.equals("SM-G930W8")) || (paramString2.equals("SM-G930X"))))) {
      return "Galaxy S7";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-02H")) || (paramString1.equals("SCV33")) || (paramString1.equals("hero2lte")) || (paramString1.equals("hero2ltebmc")) || (paramString1.equals("hero2ltektt")) || (paramString1.equals("hero2ltelgt")) || (paramString1.equals("hero2lteskt")) || (paramString1.equals("hero2qlteatt")) || (paramString1.equals("hero2qltecctvzw")) || (paramString1.equals("hero2qltechn")) || (paramString1.equals("hero2qltespr")) || (paramString1.equals("hero2qltetmo")) || (paramString1.equals("hero2qlteue")) || (paramString1.equals("hero2qlteusc")) || (paramString1.equals("hero2qltevzw")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-G935A")) || (paramString2.equals("SC-02H")) || (paramString2.equals("SCV33")) || (paramString2.equals("SM-G9350")) || (paramString2.equals("SM-G935F")) || (paramString2.equals("SM-G935K")) || (paramString2.equals("SM-G935L")) || (paramString2.equals("SM-G935P")) || (paramString2.equals("SM-G935R4")) || (paramString2.equals("SM-G935S")) || (paramString2.equals("SM-G935T")) || (paramString2.equals("SM-G935U")) || (paramString2.equals("SM-G935V")) || (paramString2.equals("SM-G935VC")) || (paramString2.equals("SM-G935W8")) || (paramString2.equals("SM-G935X"))))) {
      return "Galaxy S7 Edge";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-02J")) || (paramString1.equals("SCV36")) || (paramString1.equals("dreamlte")) || (paramString1.equals("dreamlteks")) || (paramString1.equals("dreamqltecan")) || (paramString1.equals("dreamqltechn")) || (paramString1.equals("dreamqltecmcc")) || (paramString1.equals("dreamqltesq")) || (paramString1.equals("dreamqlteue")))) || ((paramString2 != null) && ((paramString2.equals("SC-02J")) || (paramString2.equals("SCV36")) || (paramString2.equals("SM-G9500")) || (paramString2.equals("SM-G9508")) || (paramString2.equals("SM-G950F")) || (paramString2.equals("SM-G950N")) || (paramString2.equals("SM-G950U")) || (paramString2.equals("SM-G950U1")) || (paramString2.equals("SM-G950W"))))) {
      return "Galaxy S8";
    }
    if (((paramString1 != null) && ((paramString1.equals("SC-03J")) || (paramString1.equals("SCV35")) || (paramString1.equals("dream2lte")) || (paramString1.equals("dream2lteks")) || (paramString1.equals("dream2qltecan")) || (paramString1.equals("dream2qltechn")) || (paramString1.equals("dream2qltesq")) || (paramString1.equals("dream2qlteue")))) || ((paramString2 != null) && ((paramString2.equals("SC-03J")) || (paramString2.equals("SCV35")) || (paramString2.equals("SM-G9550")) || (paramString2.equals("SM-G955F")) || (paramString2.equals("SM-G955N")) || (paramString2.equals("SM-G955U")) || (paramString2.equals("SM-G955U1")) || (paramString2.equals("SM-G955W"))))) {
      return "Galaxy S8+";
    }
    if (((paramString1 != null) && ((paramString1.equals("starlte")) || (paramString1.equals("starlteks")) || (paramString1.equals("starqltechn")) || (paramString1.equals("starqltecmcc")) || (paramString1.equals("starqltecs")) || (paramString1.equals("starqltesq")) || (paramString1.equals("starqlteue")))) || ((paramString2 != null) && ((paramString2.equals("SM-G9600")) || (paramString2.equals("SM-G9608")) || (paramString2.equals("SM-G960F")) || (paramString2.equals("SM-G960N")) || (paramString2.equals("SM-G960U")) || (paramString2.equals("SM-G960U1")) || (paramString2.equals("SM-G960W"))))) {
      return "Galaxy S9";
    }
    if (((paramString1 != null) && ((paramString1.equals("star2lte")) || (paramString1.equals("star2lteks")) || (paramString1.equals("star2qltechn")) || (paramString1.equals("star2qltecs")) || (paramString1.equals("star2qltesq")) || (paramString1.equals("star2qlteue")))) || ((paramString2 != null) && ((paramString2.equals("SM-G9650")) || (paramString2.equals("SM-G965F")) || (paramString2.equals("SM-G965N")) || (paramString2.equals("SM-G965U")) || (paramString2.equals("SM-G965U1")) || (paramString2.equals("SM-G965W"))))) {
      return "Galaxy S9+";
    }
    if (((paramString1 != null) && ((paramString1.equals("GT-P7500")) || (paramString1.equals("GT-P7500D")) || (paramString1.equals("GT-P7503")) || (paramString1.equals("GT-P7510")) || (paramString1.equals("SC-01D")) || (paramString1.equals("SCH-I905")) || (paramString1.equals("SGH-T859")) || (paramString1.equals("SHW-M300W")) || (paramString1.equals("SHW-M380K")) || (paramString1.equals("SHW-M380S")) || (paramString1.equals("SHW-M380W")))) || ((paramString2 != null) && ((paramString2.equals("GT-P7500")) || (paramString2.equals("GT-P7500D")) || (paramString2.equals("GT-P7503")) || (paramString2.equals("GT-P7510")) || (paramString2.equals("SC-01D")) || (paramString2.equals("SCH-I905")) || (paramString2.equals("SGH-T859")) || (paramString2.equals("SHW-M300W")) || (paramString2.equals("SHW-M380K")) || (paramString2.equals("SHW-M380S")) || (paramString2.equals("SHW-M380W"))))) {
      return "Galaxy Tab 10.1";
    }
    if (((paramString1 != null) && ((paramString1.equals("GT-P6200")) || (paramString1.equals("GT-P6200L")) || (paramString1.equals("GT-P6201")) || (paramString1.equals("GT-P6210")) || (paramString1.equals("GT-P6211")) || (paramString1.equals("SC-02D")) || (paramString1.equals("SGH-T869")) || (paramString1.equals("SHW-M430W")))) || ((paramString2 != null) && ((paramString2.equals("GT-P6200")) || (paramString2.equals("GT-P6200L")) || (paramString2.equals("GT-P6201")) || (paramString2.equals("GT-P6210")) || (paramString2.equals("GT-P6211")) || (paramString2.equals("SC-02D")) || (paramString2.equals("SGH-T869")) || (paramString2.equals("SHW-M430W"))))) {
      return "Galaxy Tab 7.0 Plus";
    }
    if (((paramString1 != null) && ((paramString1.equals("gteslteatt")) || (paramString1.equals("gtesltebmc")) || (paramString1.equals("gtesltelgt")) || (paramString1.equals("gteslteskt")) || (paramString1.equals("gtesltetmo")) || (paramString1.equals("gtesltetw")) || (paramString1.equals("gtesltevzw")) || (paramString1.equals("gtesqltespr")) || (paramString1.equals("gtesqlteusc")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-T377A")) || (paramString2.equals("SM-T375L")) || (paramString2.equals("SM-T375S")) || (paramString2.equals("SM-T3777")) || (paramString2.equals("SM-T377P")) || (paramString2.equals("SM-T377R4")) || (paramString2.equals("SM-T377T")) || (paramString2.equals("SM-T377V")) || (paramString2.equals("SM-T377W"))))) {
      return "Galaxy Tab E 8.0";
    }
    if (((paramString1 != null) && ((paramString1.equals("gtel3g")) || (paramString1.equals("gtelltevzw")) || (paramString1.equals("gtelwifi")) || (paramString1.equals("gtelwifichn")) || (paramString1.equals("gtelwifiue")))) || ((paramString2 != null) && ((paramString2.equals("SM-T560")) || (paramString2.equals("SM-T560NU")) || (paramString2.equals("SM-T561")) || (paramString2.equals("SM-T561M")) || (paramString2.equals("SM-T561Y")) || (paramString2.equals("SM-T562")) || (paramString2.equals("SM-T567V"))))) {
      return "Galaxy Tab E 9.6";
    }
    if (((paramString1 != null) && ((paramString1.equals("403SC")) || (paramString1.equals("degas2wifi")) || (paramString1.equals("degas2wifibmwchn")) || (paramString1.equals("degas3g")) || (paramString1.equals("degaslte")) || (paramString1.equals("degasltespr")) || (paramString1.equals("degasltevzw")) || (paramString1.equals("degasvelte")) || (paramString1.equals("degasveltechn")) || (paramString1.equals("degaswifi")) || (paramString1.equals("degaswifibmwzc")) || (paramString1.equals("degaswifidtv")) || (paramString1.equals("degaswifiopenbnn")) || (paramString1.equals("degaswifiue")))) || ((paramString2 != null) && ((paramString2.equals("403SC")) || (paramString2.equals("SM-T230")) || (paramString2.equals("SM-T230NT")) || (paramString2.equals("SM-T230NU")) || (paramString2.equals("SM-T230NW")) || (paramString2.equals("SM-T230NY")) || (paramString2.equals("SM-T230X")) || (paramString2.equals("SM-T231")) || (paramString2.equals("SM-T232")) || (paramString2.equals("SM-T235")) || (paramString2.equals("SM-T235Y")) || (paramString2.equals("SM-T237P")) || (paramString2.equals("SM-T237V")) || (paramString2.equals("SM-T239")) || (paramString2.equals("SM-T2397")) || (paramString2.equals("SM-T239C")) || (paramString2.equals("SM-T239M"))))) {
      return "Galaxy Tab4 7.0";
    }
    if (((paramString1 != null) && ((paramString1.equals("gvlte")) || (paramString1.equals("gvlteatt")) || (paramString1.equals("gvltevzw")) || (paramString1.equals("gvltexsp")) || (paramString1.equals("gvwifijpn")) || (paramString1.equals("gvwifiue")))) || ((paramString2 != null) && ((paramString2.equals("SAMSUNG-SM-T677A")) || (paramString2.equals("SM-T670")) || (paramString2.equals("SM-T677")) || (paramString2.equals("SM-T677V"))))) {
      return "Galaxy View";
    }
    if ((paramString1 != null) && (paramString1.equals("manta"))) {
      return "Nexus 10";
    }
    if (((paramString1 != null) && ((paramString1.equals("D2104")) || (paramString1.equals("D2105")))) || ((paramString2 != null) && ((paramString2.equals("D2104")) || (paramString2.equals("D2105"))))) {
      return "Xperia E1 dual";
    }
    if (((paramString1 != null) && ((paramString1.equals("D2202")) || (paramString1.equals("D2203")) || (paramString1.equals("D2206")) || (paramString1.equals("D2243")))) || ((paramString2 != null) && ((paramString2.equals("D2202")) || (paramString2.equals("D2203")) || (paramString2.equals("D2206")) || (paramString2.equals("D2243"))))) {
      return "Xperia E3";
    }
    if (((paramString1 != null) && ((paramString1.equals("E5603")) || (paramString1.equals("E5606")) || (paramString1.equals("E5653")))) || ((paramString2 != null) && ((paramString2.equals("E5603")) || (paramString2.equals("E5606")) || (paramString2.equals("E5653"))))) {
      return "Xperia M5";
    }
    if (((paramString1 != null) && ((paramString1.equals("E5633")) || (paramString1.equals("E5643")) || (paramString1.equals("E5663")))) || ((paramString2 != null) && ((paramString2.equals("E5633")) || (paramString2.equals("E5643")) || (paramString2.equals("E5663"))))) {
      return "Xperia M5 Dual";
    }
    if (((paramString1 != null) && (paramString1.equals("LT26i"))) || ((paramString2 != null) && (paramString2.equals("LT26i")))) {
      return "Xperia S";
    }
    if (((paramString1 != null) && ((paramString1.equals("D5303")) || (paramString1.equals("D5306")) || (paramString1.equals("D5316")) || (paramString1.equals("D5316N")) || (paramString1.equals("D5322")))) || ((paramString2 != null) && ((paramString2.equals("D5303")) || (paramString2.equals("D5306")) || (paramString2.equals("D5316")) || (paramString2.equals("D5316N")) || (paramString2.equals("D5322"))))) {
      return "Xperia T2 Ultra";
    }
    if (((paramString1 != null) && (paramString1.equals("txs03"))) || ((paramString2 != null) && ((paramString2.equals("SGPT12")) || (paramString2.equals("SGPT13"))))) {
      return "Xperia Tablet S";
    }
    if (((paramString1 != null) && ((paramString1.equals("SGP311")) || (paramString1.equals("SGP312")) || (paramString1.equals("SGP321")) || (paramString1.equals("SGP351")))) || ((paramString2 != null) && ((paramString2.equals("SGP311")) || (paramString2.equals("SGP312")) || (paramString2.equals("SGP321")) || (paramString2.equals("SGP351"))))) {
      return "Xperia Tablet Z";
    }
    if (((paramString1 != null) && ((paramString1.equals("D6502")) || (paramString1.equals("D6503")) || (paramString1.equals("D6543")) || (paramString1.equals("SO-03F")))) || ((paramString2 != null) && ((paramString2.equals("D6502")) || (paramString2.equals("D6503")) || (paramString2.equals("D6543")) || (paramString2.equals("SO-03F"))))) {
      return "Xperia Z2";
    }
    if (((paramString1 != null) && ((paramString1.equals("401SO")) || (paramString1.equals("D6603")) || (paramString1.equals("D6616")) || (paramString1.equals("D6643")) || (paramString1.equals("D6646")) || (paramString1.equals("D6653")) || (paramString1.equals("SO-01G")) || (paramString1.equals("SOL26")) || (paramString1.equals("leo")))) || ((paramString2 != null) && ((paramString2.equals("401SO")) || (paramString2.equals("D6603")) || (paramString2.equals("D6616")) || (paramString2.equals("D6643")) || (paramString2.equals("D6646")) || (paramString2.equals("D6653")) || (paramString2.equals("SO-01G")) || (paramString2.equals("SOL26"))))) {
      return "Xperia Z3";
    }
    if (((paramString1 != null) && ((paramString1.equals("402SO")) || (paramString1.equals("SO-03G")) || (paramString1.equals("SOV31")))) || ((paramString2 != null) && ((paramString2.equals("402SO")) || (paramString2.equals("SO-03G")) || (paramString2.equals("SOV31"))))) {
      return "Xperia Z4";
    }
    if (((paramString1 != null) && ((paramString1.equals("E5803")) || (paramString1.equals("E5823")) || (paramString1.equals("SO-02H")))) || ((paramString2 != null) && ((paramString2.equals("E5803")) || (paramString2.equals("E5823")) || (paramString2.equals("SO-02H"))))) {
      return "Xperia Z5 Compact";
    }
    if (((paramString1 != null) && ((paramString1.equals("LT26i")) || (paramString1.equals("SO-02D")))) || ((paramString2 != null) && ((paramString2.equals("LT26i")) || (paramString2.equals("SO-02D"))))) {
      return "Xperia S";
    }
    if (((paramString1 != null) && ((paramString1.equals("SGP311")) || (paramString1.equals("SGP321")) || (paramString1.equals("SGP341")) || (paramString1.equals("SO-03E")))) || ((paramString2 != null) && ((paramString2.equals("SGP311")) || (paramString2.equals("SGP321")) || (paramString2.equals("SGP341")) || (paramString2.equals("SO-03E"))))) {
      return "Xperia Tablet Z";
    }
    return paramString3;
  }
  
  private static String b(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject1 = null;
    InputStreamReader localInputStreamReader = null;
    Object localObject2 = localObject1;
    try
    {
      Object localObject4 = new java/net/URL;
      localObject2 = localObject1;
      ((URL)localObject4).<init>(paramString);
      localObject2 = localObject1;
      localObject4 = (HttpURLConnection)((URL)localObject4).openConnection();
      localObject2 = localObject1;
      ((HttpURLConnection)localObject4).setReadTimeout(10000);
      localObject2 = localObject1;
      ((HttpURLConnection)localObject4).setConnectTimeout(15000);
      localObject2 = localObject1;
      ((HttpURLConnection)localObject4).setRequestMethod("GET");
      localObject2 = localObject1;
      ((HttpURLConnection)localObject4).setDoInput(true);
      localObject2 = localObject1;
      ((HttpURLConnection)localObject4).connect();
      paramString = localInputStreamReader;
      localObject2 = localObject1;
      if (((HttpURLConnection)localObject4).getResponseCode() == 200)
      {
        localObject2 = localObject1;
        paramString = new java/io/BufferedReader;
        localObject2 = localObject1;
        localInputStreamReader = new java/io/InputStreamReader;
        localObject2 = localObject1;
        localInputStreamReader.<init>(((HttpURLConnection)localObject4).getInputStream());
        localObject2 = localObject1;
        paramString.<init>(localInputStreamReader);
        try
        {
          for (;;)
          {
            localObject2 = paramString.readLine();
            if (localObject2 == null) {
              break;
            }
            localStringBuilder.append((String)localObject2);
            localStringBuilder.append('\n');
          }
        }
        finally
        {
          localObject1 = paramString;
          paramString = (String)localObject3;
          break label205;
        }
      }
      str = paramString;
      localObject1 = localStringBuilder.toString();
      if (paramString != null) {
        paramString.close();
      }
      return (String)localObject1;
    }
    finally
    {
      String str;
      localObject1 = str;
      label205:
      if (localObject1 != null) {
        ((BufferedReader)localObject1).close();
      }
    }
  }
  
  public static abstract interface a
  {
    public abstract void a(d.b paramb, Exception paramException);
  }
  
  public static final class b
  {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    
    public b(String paramString1, String paramString2, String paramString3, String paramString4)
    {
      this.a = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.d = paramString4;
    }
    
    private b(JSONObject paramJSONObject)
    {
      this.a = paramJSONObject.getString("manufacturer");
      this.b = paramJSONObject.getString("market_name");
      this.c = paramJSONObject.getString("codename");
      this.d = paramJSONObject.getString("model");
    }
  }
  
  public static final class c
  {
    final Context a;
    final Handler b;
    String c;
    String d;
    
    private c(Context paramContext)
    {
      this.a = paramContext;
      this.b = new Handler(paramContext.getMainLooper());
    }
    
    public void a(d.a parama)
    {
      if ((this.c == null) && (this.d == null))
      {
        this.c = Build.DEVICE;
        this.d = Build.MODEL;
      }
      parama = new a(parama);
      if (Looper.myLooper() == Looper.getMainLooper()) {
        new Thread(parama).start();
      } else {
        parama.run();
      }
    }
    
    private final class a
      implements Runnable
    {
      final d.a a;
      d.b b;
      Exception c;
      
      public a(d.a parama)
      {
        this.a = parama;
      }
      
      public void run()
      {
        try
        {
          this.b = d.a(d.c.this.a, d.c.this.c, d.c.this.d);
        }
        catch (Exception localException)
        {
          this.c = localException;
        }
        d.c.this.b.post(new Runnable()
        {
          public void run()
          {
            d.c.a.this.a.a(d.c.a.this.b, d.c.a.this.c);
          }
        });
      }
    }
  }
}


/* Location:              ~/com/app/system/common/d.class
 *
 * Reversed by:           J
 */