package com.crashlytics.android.answers;

import android.os.Bundle;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FirebaseAnalyticsEventMapper
{
  private static final Set<String> EVENT_NAMES = new HashSet(Arrays.asList(new String[] { "app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter" }));
  private static final String FIREBASE_LEVEL_NAME = "level_name";
  private static final String FIREBASE_METHOD = "method";
  private static final String FIREBASE_RATING = "rating";
  private static final String FIREBASE_SUCCESS = "success";
  
  private String mapAttribute(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      String str = paramString.replaceAll("[^\\p{Alnum}_]+", "_");
      if ((!str.startsWith("ga_")) && (!str.startsWith("google_")) && (!str.startsWith("firebase_")))
      {
        paramString = str;
        if (Character.isLetter(str.charAt(0))) {}
      }
      else
      {
        paramString = new StringBuilder();
        paramString.append("fabric_");
        paramString.append(str);
        paramString = paramString.toString();
      }
      if (paramString.length() > 40) {
        return paramString.substring(0, 40);
      }
      return paramString;
    }
    return "fabric_unnamed_parameter";
  }
  
  private Integer mapBooleanValue(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return Integer.valueOf(paramString.equals("true"));
  }
  
  private void mapCustomEventAttributes(Bundle paramBundle, Map<String, Object> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      Object localObject = paramMap.getValue();
      String str = mapAttribute((String)paramMap.getKey());
      if ((localObject instanceof String)) {
        paramBundle.putString(str, paramMap.getValue().toString());
      } else if ((localObject instanceof Double)) {
        paramBundle.putDouble(str, ((Double)paramMap.getValue()).doubleValue());
      } else if ((localObject instanceof Long)) {
        paramBundle.putLong(str, ((Long)paramMap.getValue()).longValue());
      } else if ((localObject instanceof Integer)) {
        paramBundle.putInt(str, ((Integer)paramMap.getValue()).intValue());
      }
    }
  }
  
  private String mapCustomEventName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (EVENT_NAMES.contains(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("fabric_");
        ((StringBuilder)localObject).append(paramString);
        return ((StringBuilder)localObject).toString();
      }
      Object localObject = paramString.replaceAll("[^\\p{Alnum}_]+", "_");
      if ((!((String)localObject).startsWith("ga_")) && (!((String)localObject).startsWith("google_")) && (!((String)localObject).startsWith("firebase_")))
      {
        paramString = (String)localObject;
        if (Character.isLetter(((String)localObject).charAt(0))) {}
      }
      else
      {
        paramString = new StringBuilder();
        paramString.append("fabric_");
        paramString.append((String)localObject);
        paramString = paramString.toString();
      }
      localObject = paramString;
      if (paramString.length() > 40) {
        localObject = paramString.substring(0, 40);
      }
      return (String)localObject;
    }
    return "fabric_unnamed_event";
  }
  
  private Double mapDouble(Object paramObject)
  {
    paramObject = String.valueOf(paramObject);
    if (paramObject == null) {
      return null;
    }
    return Double.valueOf((String)paramObject);
  }
  
  private Bundle mapPredefinedEvent(SessionEvent paramSessionEvent)
  {
    Bundle localBundle = new Bundle();
    if ("purchase".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "item_id", (String)paramSessionEvent.predefinedAttributes.get("itemId"));
      putString(localBundle, "item_name", (String)paramSessionEvent.predefinedAttributes.get("itemName"));
      putString(localBundle, "item_category", (String)paramSessionEvent.predefinedAttributes.get("itemType"));
      putDouble(localBundle, "value", mapPriceValue(paramSessionEvent.predefinedAttributes.get("itemPrice")));
      putString(localBundle, "currency", (String)paramSessionEvent.predefinedAttributes.get("currency"));
    }
    else if ("addToCart".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "item_id", (String)paramSessionEvent.predefinedAttributes.get("itemId"));
      putString(localBundle, "item_name", (String)paramSessionEvent.predefinedAttributes.get("itemName"));
      putString(localBundle, "item_category", (String)paramSessionEvent.predefinedAttributes.get("itemType"));
      putDouble(localBundle, "price", mapPriceValue(paramSessionEvent.predefinedAttributes.get("itemPrice")));
      putDouble(localBundle, "value", mapPriceValue(paramSessionEvent.predefinedAttributes.get("itemPrice")));
      putString(localBundle, "currency", (String)paramSessionEvent.predefinedAttributes.get("currency"));
      localBundle.putLong("quantity", 1L);
    }
    else if ("startCheckout".equals(paramSessionEvent.predefinedType))
    {
      putLong(localBundle, "quantity", Long.valueOf(((Integer)paramSessionEvent.predefinedAttributes.get("itemCount")).intValue()));
      putDouble(localBundle, "value", mapPriceValue(paramSessionEvent.predefinedAttributes.get("totalPrice")));
      putString(localBundle, "currency", (String)paramSessionEvent.predefinedAttributes.get("currency"));
    }
    else if ("contentView".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "content_type", (String)paramSessionEvent.predefinedAttributes.get("contentType"));
      putString(localBundle, "item_id", (String)paramSessionEvent.predefinedAttributes.get("contentId"));
      putString(localBundle, "item_name", (String)paramSessionEvent.predefinedAttributes.get("contentName"));
    }
    else if ("search".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "search_term", (String)paramSessionEvent.predefinedAttributes.get("query"));
    }
    else if ("share".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "method", (String)paramSessionEvent.predefinedAttributes.get("method"));
      putString(localBundle, "content_type", (String)paramSessionEvent.predefinedAttributes.get("contentType"));
      putString(localBundle, "item_id", (String)paramSessionEvent.predefinedAttributes.get("contentId"));
      putString(localBundle, "item_name", (String)paramSessionEvent.predefinedAttributes.get("contentName"));
    }
    else if ("rating".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "rating", String.valueOf(paramSessionEvent.predefinedAttributes.get("rating")));
      putString(localBundle, "content_type", (String)paramSessionEvent.predefinedAttributes.get("contentType"));
      putString(localBundle, "item_id", (String)paramSessionEvent.predefinedAttributes.get("contentId"));
      putString(localBundle, "item_name", (String)paramSessionEvent.predefinedAttributes.get("contentName"));
    }
    else if ("signUp".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "method", (String)paramSessionEvent.predefinedAttributes.get("method"));
    }
    else if ("login".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "method", (String)paramSessionEvent.predefinedAttributes.get("method"));
    }
    else if ("invite".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "method", (String)paramSessionEvent.predefinedAttributes.get("method"));
    }
    else if ("levelStart".equals(paramSessionEvent.predefinedType))
    {
      putString(localBundle, "level_name", (String)paramSessionEvent.predefinedAttributes.get("levelName"));
    }
    else if ("levelEnd".equals(paramSessionEvent.predefinedType))
    {
      putDouble(localBundle, "score", mapDouble(paramSessionEvent.predefinedAttributes.get("score")));
      putString(localBundle, "level_name", (String)paramSessionEvent.predefinedAttributes.get("levelName"));
      putInt(localBundle, "success", mapBooleanValue((String)paramSessionEvent.predefinedAttributes.get("success")));
    }
    mapCustomEventAttributes(localBundle, paramSessionEvent.customAttributes);
    return localBundle;
  }
  
  private String mapPredefinedEventName(String paramString, boolean paramBoolean)
  {
    int i = 0;
    if (paramBoolean)
    {
      j = paramString.hashCode();
      if (j != -902468296)
      {
        if (j != 103149417)
        {
          if ((j == 1743324417) && (paramString.equals("purchase")))
          {
            j = 0;
            break label89;
          }
        }
        else if (paramString.equals("login"))
        {
          j = 2;
          break label89;
        }
      }
      else if (paramString.equals("signUp"))
      {
        j = 1;
        break label89;
      }
      j = -1;
      switch (j)
      {
      default: 
        break;
      case 2: 
        return "failed_login";
      case 1: 
        return "failed_sign_up";
      case 0: 
        label89:
        return "failed_ecommerce_purchase";
      }
    }
    switch (paramString.hashCode())
    {
    default: 
      break;
    case 1743324417: 
      if (paramString.equals("purchase")) {
        j = i;
      }
      break;
    case 1664021448: 
      if (paramString.equals("startCheckout")) {
        j = 2;
      }
      break;
    case 196004670: 
      if (paramString.equals("levelStart")) {
        j = 10;
      }
      break;
    case 109400031: 
      if (paramString.equals("share")) {
        j = 5;
      }
      break;
    case 103149417: 
      if (paramString.equals("login")) {
        j = 8;
      }
      break;
    case 23457852: 
      if (paramString.equals("addToCart")) {
        j = 1;
      }
      break;
    case -389087554: 
      if (paramString.equals("contentView")) {
        j = 3;
      }
      break;
    case -902468296: 
      if (paramString.equals("signUp")) {
        j = 7;
      }
      break;
    case -906336856: 
      if (paramString.equals("search")) {
        j = 4;
      }
      break;
    case -938102371: 
      if (paramString.equals("rating")) {
        j = 6;
      }
      break;
    case -1183699191: 
      if (paramString.equals("invite")) {
        j = 9;
      }
      break;
    case -2131650889: 
      if (paramString.equals("levelEnd")) {
        j = 11;
      }
      break;
    }
    int j = -1;
    switch (j)
    {
    default: 
      return mapCustomEventName(paramString);
    case 11: 
      return "level_end";
    case 10: 
      return "level_start";
    case 9: 
      return "invite";
    case 8: 
      return "login";
    case 7: 
      return "sign_up";
    case 6: 
      return "rate_content";
    case 5: 
      return "share";
    case 4: 
      return "search";
    case 3: 
      return "select_content";
    case 2: 
      return "begin_checkout";
    case 1: 
      return "add_to_cart";
    }
    return "ecommerce_purchase";
  }
  
  private Double mapPriceValue(Object paramObject)
  {
    paramObject = (Long)paramObject;
    if (paramObject == null) {
      return null;
    }
    return Double.valueOf(new BigDecimal(((Long)paramObject).longValue()).divide(AddToCartEvent.MICRO_CONSTANT).doubleValue());
  }
  
  private void putDouble(Bundle paramBundle, String paramString, Double paramDouble)
  {
    paramDouble = mapDouble(paramDouble);
    if (paramDouble == null) {
      return;
    }
    paramBundle.putDouble(paramString, paramDouble.doubleValue());
  }
  
  private void putInt(Bundle paramBundle, String paramString, Integer paramInteger)
  {
    if (paramInteger == null) {
      return;
    }
    paramBundle.putInt(paramString, paramInteger.intValue());
  }
  
  private void putLong(Bundle paramBundle, String paramString, Long paramLong)
  {
    if (paramLong == null) {
      return;
    }
    paramBundle.putLong(paramString, paramLong.longValue());
  }
  
  private void putString(Bundle paramBundle, String paramString1, String paramString2)
  {
    if (paramString2 == null) {
      return;
    }
    paramBundle.putString(paramString1, paramString2);
  }
  
  public FirebaseAnalyticsEvent mapEvent(SessionEvent paramSessionEvent)
  {
    boolean bool1 = SessionEvent.Type.CUSTOM.equals(paramSessionEvent.type);
    boolean bool2 = true;
    int i;
    if ((bool1) && (paramSessionEvent.customType != null)) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((SessionEvent.Type.PREDEFINED.equals(paramSessionEvent.type)) && (paramSessionEvent.predefinedType != null)) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i == 0) && (j == 0)) {
      return null;
    }
    Object localObject1;
    Object localObject2;
    if (j != 0)
    {
      localObject1 = mapPredefinedEvent(paramSessionEvent);
    }
    else
    {
      localObject2 = new Bundle();
      localObject1 = localObject2;
      if (paramSessionEvent.customAttributes != null)
      {
        mapCustomEventAttributes((Bundle)localObject2, paramSessionEvent.customAttributes);
        localObject1 = localObject2;
      }
    }
    if (j != 0)
    {
      localObject2 = (String)paramSessionEvent.predefinedAttributes.get("success");
      if ((localObject2 == null) || (Boolean.parseBoolean((String)localObject2))) {
        bool2 = false;
      }
      paramSessionEvent = mapPredefinedEventName(paramSessionEvent.predefinedType, bool2);
    }
    else
    {
      paramSessionEvent = mapCustomEventName(paramSessionEvent.customType);
    }
    c.g().a("Answers", "Logging event into firebase...");
    return new FirebaseAnalyticsEvent(paramSessionEvent, (Bundle)localObject1);
  }
}


/* Location:              ~/com/crashlytics/android/answers/FirebaseAnalyticsEventMapper.class
 *
 * Reversed by:           J
 */