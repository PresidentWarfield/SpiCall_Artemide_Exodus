package android.support.v4.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class NotificationManagerCompat
{
  public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
  private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
  public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
  public static final int IMPORTANCE_DEFAULT = 3;
  public static final int IMPORTANCE_HIGH = 4;
  public static final int IMPORTANCE_LOW = 2;
  public static final int IMPORTANCE_MAX = 5;
  public static final int IMPORTANCE_MIN = 1;
  public static final int IMPORTANCE_NONE = 0;
  public static final int IMPORTANCE_UNSPECIFIED = -1000;
  static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
  private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
  private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
  private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
  private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
  private static final String TAG = "NotifManCompat";
  private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
  private static String sEnabledNotificationListeners;
  private static final Object sEnabledNotificationListenersLock = new Object();
  private static final Object sLock = new Object();
  private static SideChannelManager sSideChannelManager;
  private final Context mContext;
  private final NotificationManager mNotificationManager;
  
  private NotificationManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
    this.mNotificationManager = ((NotificationManager)this.mContext.getSystemService("notification"));
  }
  
  public static NotificationManagerCompat from(Context paramContext)
  {
    return new NotificationManagerCompat(paramContext);
  }
  
  public static Set<String> getEnabledListenerPackages(Context paramContext)
  {
    String str = Settings.Secure.getString(paramContext.getContentResolver(), "enabled_notification_listeners");
    paramContext = sEnabledNotificationListenersLock;
    if (str != null) {}
    try
    {
      if (!str.equals(sEnabledNotificationListeners))
      {
        String[] arrayOfString = str.split(":");
        localObject1 = new java/util/HashSet;
        ((HashSet)localObject1).<init>(arrayOfString.length);
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++)
        {
          ComponentName localComponentName = ComponentName.unflattenFromString(arrayOfString[j]);
          if (localComponentName != null) {
            ((Set)localObject1).add(localComponentName.getPackageName());
          }
        }
        sEnabledNotificationListenerPackages = (Set)localObject1;
        sEnabledNotificationListeners = str;
      }
      Object localObject1 = sEnabledNotificationListenerPackages;
      return (Set<String>)localObject1;
    }
    finally {}
  }
  
  private void pushSideChannelQueue(Task paramTask)
  {
    synchronized (sLock)
    {
      if (sSideChannelManager == null)
      {
        SideChannelManager localSideChannelManager = new android/support/v4/app/NotificationManagerCompat$SideChannelManager;
        localSideChannelManager.<init>(this.mContext.getApplicationContext());
        sSideChannelManager = localSideChannelManager;
      }
      sSideChannelManager.queueTask(paramTask);
      return;
    }
  }
  
  private static boolean useSideChannelForNotification(Notification paramNotification)
  {
    paramNotification = NotificationCompat.getExtras(paramNotification);
    boolean bool;
    if ((paramNotification != null) && (paramNotification.getBoolean("android.support.useSideChannel"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean areNotificationsEnabled()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return this.mNotificationManager.areNotificationsEnabled();
    }
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    if (i >= 19)
    {
      AppOpsManager localAppOpsManager = (AppOpsManager)this.mContext.getSystemService("appops");
      Object localObject = this.mContext.getApplicationInfo();
      String str = this.mContext.getApplicationContext().getPackageName();
      i = ((ApplicationInfo)localObject).uid;
      try
      {
        localObject = Class.forName(AppOpsManager.class.getName());
        i = ((Integer)((Class)localObject).getMethod("checkOpNoThrow", new Class[] { Integer.TYPE, Integer.TYPE, String.class }).invoke(localAppOpsManager, new Object[] { Integer.valueOf(((Integer)((Class)localObject).getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), str })).intValue();
        if (i != 0) {
          bool = false;
        }
        return bool;
      }
      catch (ClassNotFoundException|NoSuchMethodException|NoSuchFieldException|InvocationTargetException|IllegalAccessException|RuntimeException localClassNotFoundException)
      {
        return true;
      }
    }
    return true;
  }
  
  public void cancel(int paramInt)
  {
    cancel(null, paramInt);
  }
  
  public void cancel(String paramString, int paramInt)
  {
    this.mNotificationManager.cancel(paramString, paramInt);
    if (Build.VERSION.SDK_INT <= 19) {
      pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), paramInt, paramString));
    }
  }
  
  public void cancelAll()
  {
    this.mNotificationManager.cancelAll();
    if (Build.VERSION.SDK_INT <= 19) {
      pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
    }
  }
  
  public int getImportance()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return this.mNotificationManager.getImportance();
    }
    return 64536;
  }
  
  public void notify(int paramInt, Notification paramNotification)
  {
    notify(null, paramInt, paramNotification);
  }
  
  public void notify(String paramString, int paramInt, Notification paramNotification)
  {
    if (useSideChannelForNotification(paramNotification))
    {
      pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), paramInt, paramString, paramNotification));
      this.mNotificationManager.cancel(paramString, paramInt);
    }
    else
    {
      this.mNotificationManager.notify(paramString, paramInt, paramNotification);
    }
  }
  
  private static class CancelTask
    implements NotificationManagerCompat.Task
  {
    final boolean all;
    final int id;
    final String packageName;
    final String tag;
    
    CancelTask(String paramString)
    {
      this.packageName = paramString;
      this.id = 0;
      this.tag = null;
      this.all = true;
    }
    
    CancelTask(String paramString1, int paramInt, String paramString2)
    {
      this.packageName = paramString1;
      this.id = paramInt;
      this.tag = paramString2;
      this.all = false;
    }
    
    public void send(INotificationSideChannel paramINotificationSideChannel)
    {
      if (this.all) {
        paramINotificationSideChannel.cancelAll(this.packageName);
      } else {
        paramINotificationSideChannel.cancel(this.packageName, this.id, this.tag);
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("CancelTask[");
      localStringBuilder.append("packageName:");
      localStringBuilder.append(this.packageName);
      localStringBuilder.append(", id:");
      localStringBuilder.append(this.id);
      localStringBuilder.append(", tag:");
      localStringBuilder.append(this.tag);
      localStringBuilder.append(", all:");
      localStringBuilder.append(this.all);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  private static class NotifyTask
    implements NotificationManagerCompat.Task
  {
    final int id;
    final Notification notif;
    final String packageName;
    final String tag;
    
    NotifyTask(String paramString1, int paramInt, String paramString2, Notification paramNotification)
    {
      this.packageName = paramString1;
      this.id = paramInt;
      this.tag = paramString2;
      this.notif = paramNotification;
    }
    
    public void send(INotificationSideChannel paramINotificationSideChannel)
    {
      paramINotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("NotifyTask[");
      localStringBuilder.append("packageName:");
      localStringBuilder.append(this.packageName);
      localStringBuilder.append(", id:");
      localStringBuilder.append(this.id);
      localStringBuilder.append(", tag:");
      localStringBuilder.append(this.tag);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  private static class ServiceConnectedEvent
  {
    final ComponentName componentName;
    final IBinder iBinder;
    
    ServiceConnectedEvent(ComponentName paramComponentName, IBinder paramIBinder)
    {
      this.componentName = paramComponentName;
      this.iBinder = paramIBinder;
    }
  }
  
  private static class SideChannelManager
    implements ServiceConnection, Handler.Callback
  {
    private static final int MSG_QUEUE_TASK = 0;
    private static final int MSG_RETRY_LISTENER_QUEUE = 3;
    private static final int MSG_SERVICE_CONNECTED = 1;
    private static final int MSG_SERVICE_DISCONNECTED = 2;
    private Set<String> mCachedEnabledPackages = new HashSet();
    private final Context mContext;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap();
    
    public SideChannelManager(Context paramContext)
    {
      this.mContext = paramContext;
      this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
      this.mHandlerThread.start();
      this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
    }
    
    private boolean ensureServiceBound(ListenerRecord paramListenerRecord)
    {
      if (paramListenerRecord.bound) {
        return true;
      }
      Object localObject = new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL").setComponent(paramListenerRecord.componentName);
      paramListenerRecord.bound = this.mContext.bindService((Intent)localObject, this, 33);
      if (paramListenerRecord.bound)
      {
        paramListenerRecord.retryCount = 0;
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unable to bind to listener ");
        ((StringBuilder)localObject).append(paramListenerRecord.componentName);
        Log.w("NotifManCompat", ((StringBuilder)localObject).toString());
        this.mContext.unbindService(this);
      }
      return paramListenerRecord.bound;
    }
    
    private void ensureServiceUnbound(ListenerRecord paramListenerRecord)
    {
      if (paramListenerRecord.bound)
      {
        this.mContext.unbindService(this);
        paramListenerRecord.bound = false;
      }
      paramListenerRecord.service = null;
    }
    
    private void handleQueueTask(NotificationManagerCompat.Task paramTask)
    {
      updateListenerMap();
      Iterator localIterator = this.mRecordMap.values().iterator();
      while (localIterator.hasNext())
      {
        ListenerRecord localListenerRecord = (ListenerRecord)localIterator.next();
        localListenerRecord.taskQueue.add(paramTask);
        processListenerQueue(localListenerRecord);
      }
    }
    
    private void handleRetryListenerQueue(ComponentName paramComponentName)
    {
      paramComponentName = (ListenerRecord)this.mRecordMap.get(paramComponentName);
      if (paramComponentName != null) {
        processListenerQueue(paramComponentName);
      }
    }
    
    private void handleServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      paramComponentName = (ListenerRecord)this.mRecordMap.get(paramComponentName);
      if (paramComponentName != null)
      {
        paramComponentName.service = INotificationSideChannel.Stub.asInterface(paramIBinder);
        paramComponentName.retryCount = 0;
        processListenerQueue(paramComponentName);
      }
    }
    
    private void handleServiceDisconnected(ComponentName paramComponentName)
    {
      paramComponentName = (ListenerRecord)this.mRecordMap.get(paramComponentName);
      if (paramComponentName != null) {
        ensureServiceUnbound(paramComponentName);
      }
    }
    
    private void processListenerQueue(ListenerRecord paramListenerRecord)
    {
      Object localObject;
      if (Log.isLoggable("NotifManCompat", 3))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Processing component ");
        ((StringBuilder)localObject).append(paramListenerRecord.componentName);
        ((StringBuilder)localObject).append(", ");
        ((StringBuilder)localObject).append(paramListenerRecord.taskQueue.size());
        ((StringBuilder)localObject).append(" queued tasks");
        Log.d("NotifManCompat", ((StringBuilder)localObject).toString());
      }
      if (paramListenerRecord.taskQueue.isEmpty()) {
        return;
      }
      if ((ensureServiceBound(paramListenerRecord)) && (paramListenerRecord.service != null))
      {
        for (;;)
        {
          localObject = (NotificationManagerCompat.Task)paramListenerRecord.taskQueue.peek();
          if (localObject != null) {
            try
            {
              if (Log.isLoggable("NotifManCompat", 3))
              {
                StringBuilder localStringBuilder2 = new java/lang/StringBuilder;
                localStringBuilder2.<init>();
                localStringBuilder2.append("Sending task ");
                localStringBuilder2.append(localObject);
                Log.d("NotifManCompat", localStringBuilder2.toString());
              }
              ((NotificationManagerCompat.Task)localObject).send(paramListenerRecord.service);
              paramListenerRecord.taskQueue.remove();
            }
            catch (RemoteException localRemoteException)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("RemoteException communicating with ");
              ((StringBuilder)localObject).append(paramListenerRecord.componentName);
              Log.w("NotifManCompat", ((StringBuilder)localObject).toString(), localRemoteException);
            }
            catch (DeadObjectException localDeadObjectException)
            {
              if (Log.isLoggable("NotifManCompat", 3))
              {
                StringBuilder localStringBuilder1 = new StringBuilder();
                localStringBuilder1.append("Remote service has died: ");
                localStringBuilder1.append(paramListenerRecord.componentName);
                Log.d("NotifManCompat", localStringBuilder1.toString());
              }
            }
          }
        }
        if (!paramListenerRecord.taskQueue.isEmpty()) {
          scheduleListenerRetry(paramListenerRecord);
        }
        return;
      }
      scheduleListenerRetry(paramListenerRecord);
    }
    
    private void scheduleListenerRetry(ListenerRecord paramListenerRecord)
    {
      if (this.mHandler.hasMessages(3, paramListenerRecord.componentName)) {
        return;
      }
      paramListenerRecord.retryCount += 1;
      StringBuilder localStringBuilder;
      if (paramListenerRecord.retryCount > 6)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Giving up on delivering ");
        localStringBuilder.append(paramListenerRecord.taskQueue.size());
        localStringBuilder.append(" tasks to ");
        localStringBuilder.append(paramListenerRecord.componentName);
        localStringBuilder.append(" after ");
        localStringBuilder.append(paramListenerRecord.retryCount);
        localStringBuilder.append(" retries");
        Log.w("NotifManCompat", localStringBuilder.toString());
        paramListenerRecord.taskQueue.clear();
        return;
      }
      int i = (1 << paramListenerRecord.retryCount - 1) * 1000;
      if (Log.isLoggable("NotifManCompat", 3))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Scheduling retry for ");
        localStringBuilder.append(i);
        localStringBuilder.append(" ms");
        Log.d("NotifManCompat", localStringBuilder.toString());
      }
      paramListenerRecord = this.mHandler.obtainMessage(3, paramListenerRecord.componentName);
      this.mHandler.sendMessageDelayed(paramListenerRecord, i);
    }
    
    private void updateListenerMap()
    {
      Object localObject1 = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
      if (((Set)localObject1).equals(this.mCachedEnabledPackages)) {
        return;
      }
      this.mCachedEnabledPackages = ((Set)localObject1);
      Object localObject2 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 0);
      HashSet localHashSet = new HashSet();
      localObject2 = ((List)localObject2).iterator();
      Object localObject4;
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject3 = (ResolveInfo)((Iterator)localObject2).next();
        if (((Set)localObject1).contains(((ResolveInfo)localObject3).serviceInfo.packageName))
        {
          localObject4 = new ComponentName(((ResolveInfo)localObject3).serviceInfo.packageName, ((ResolveInfo)localObject3).serviceInfo.name);
          if (((ResolveInfo)localObject3).serviceInfo.permission != null)
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("Permission present on component ");
            ((StringBuilder)localObject3).append(localObject4);
            ((StringBuilder)localObject3).append(", not adding listener record.");
            Log.w("NotifManCompat", ((StringBuilder)localObject3).toString());
          }
          else
          {
            localHashSet.add(localObject4);
          }
        }
      }
      localObject1 = localHashSet.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject4 = (ComponentName)((Iterator)localObject1).next();
        if (!this.mRecordMap.containsKey(localObject4))
        {
          if (Log.isLoggable("NotifManCompat", 3))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Adding listener record for ");
            ((StringBuilder)localObject2).append(localObject4);
            Log.d("NotifManCompat", ((StringBuilder)localObject2).toString());
          }
          this.mRecordMap.put(localObject4, new ListenerRecord((ComponentName)localObject4));
        }
      }
      localObject1 = this.mRecordMap.entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject4 = (Map.Entry)((Iterator)localObject1).next();
        if (!localHashSet.contains(((Map.Entry)localObject4).getKey()))
        {
          if (Log.isLoggable("NotifManCompat", 3))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Removing listener record for ");
            ((StringBuilder)localObject2).append(((Map.Entry)localObject4).getKey());
            Log.d("NotifManCompat", ((StringBuilder)localObject2).toString());
          }
          ensureServiceUnbound((ListenerRecord)((Map.Entry)localObject4).getValue());
          ((Iterator)localObject1).remove();
        }
      }
    }
    
    public boolean handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        return false;
      case 3: 
        handleRetryListenerQueue((ComponentName)paramMessage.obj);
        return true;
      case 2: 
        handleServiceDisconnected((ComponentName)paramMessage.obj);
        return true;
      case 1: 
        paramMessage = (NotificationManagerCompat.ServiceConnectedEvent)paramMessage.obj;
        handleServiceConnected(paramMessage.componentName, paramMessage.iBinder);
        return true;
      }
      handleQueueTask((NotificationManagerCompat.Task)paramMessage.obj);
      return true;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      if (Log.isLoggable("NotifManCompat", 3))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Connected to service ");
        localStringBuilder.append(paramComponentName);
        Log.d("NotifManCompat", localStringBuilder.toString());
      }
      this.mHandler.obtainMessage(1, new NotificationManagerCompat.ServiceConnectedEvent(paramComponentName, paramIBinder)).sendToTarget();
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      if (Log.isLoggable("NotifManCompat", 3))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Disconnected from service ");
        localStringBuilder.append(paramComponentName);
        Log.d("NotifManCompat", localStringBuilder.toString());
      }
      this.mHandler.obtainMessage(2, paramComponentName).sendToTarget();
    }
    
    public void queueTask(NotificationManagerCompat.Task paramTask)
    {
      this.mHandler.obtainMessage(0, paramTask).sendToTarget();
    }
    
    private static class ListenerRecord
    {
      public boolean bound = false;
      public final ComponentName componentName;
      public int retryCount = 0;
      public INotificationSideChannel service;
      public LinkedList<NotificationManagerCompat.Task> taskQueue = new LinkedList();
      
      public ListenerRecord(ComponentName paramComponentName)
      {
        this.componentName = paramComponentName;
      }
    }
  }
  
  private static abstract interface Task
  {
    public abstract void send(INotificationSideChannel paramINotificationSideChannel);
  }
}


/* Location:              ~/android/support/v4/app/NotificationManagerCompat.class
 *
 * Reversed by:           J
 */