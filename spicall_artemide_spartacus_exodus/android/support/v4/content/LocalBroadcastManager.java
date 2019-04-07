package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager
{
  private static final boolean DEBUG = false;
  static final int MSG_EXEC_PENDING_BROADCASTS = 1;
  private static final String TAG = "LocalBroadcastManager";
  private static LocalBroadcastManager mInstance;
  private static final Object mLock = new Object();
  private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
  private final Context mAppContext;
  private final Handler mHandler;
  private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
  private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();
  
  private LocalBroadcastManager(Context paramContext)
  {
    this.mAppContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          super.handleMessage(paramAnonymousMessage);
        } else {
          LocalBroadcastManager.this.executePendingBroadcasts();
        }
      }
    };
  }
  
  private void executePendingBroadcasts()
  {
    synchronized (this.mReceivers)
    {
      int i;
      BroadcastRecord[] arrayOfBroadcastRecord;
      do
      {
        i = this.mPendingBroadcasts.size();
        if (i <= 0) {
          return;
        }
        arrayOfBroadcastRecord = new BroadcastRecord[i];
        this.mPendingBroadcasts.toArray(arrayOfBroadcastRecord);
        this.mPendingBroadcasts.clear();
        i = 0;
      } while (i >= arrayOfBroadcastRecord.length);
      ??? = arrayOfBroadcastRecord[i];
      int j = ???.receivers.size();
      for (int k = 0; k < j; k++)
      {
        ReceiverRecord localReceiverRecord = (ReceiverRecord)???.receivers.get(k);
        if (!localReceiverRecord.dead) {
          localReceiverRecord.receiver.onReceive(this.mAppContext, ???.intent);
        }
      }
      i++;
    }
  }
  
  public static LocalBroadcastManager getInstance(Context paramContext)
  {
    synchronized (mLock)
    {
      if (mInstance == null)
      {
        LocalBroadcastManager localLocalBroadcastManager = new android/support/v4/content/LocalBroadcastManager;
        localLocalBroadcastManager.<init>(paramContext.getApplicationContext());
        mInstance = localLocalBroadcastManager;
      }
      paramContext = mInstance;
      return paramContext;
    }
  }
  
  public void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    synchronized (this.mReceivers)
    {
      ReceiverRecord localReceiverRecord = new android/support/v4/content/LocalBroadcastManager$ReceiverRecord;
      localReceiverRecord.<init>(paramIntentFilter, paramBroadcastReceiver);
      Object localObject1 = (ArrayList)this.mReceivers.get(paramBroadcastReceiver);
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        localObject2 = new java/util/ArrayList;
        ((ArrayList)localObject2).<init>(1);
        this.mReceivers.put(paramBroadcastReceiver, localObject2);
      }
      ((ArrayList)localObject2).add(localReceiverRecord);
      for (int i = 0; i < paramIntentFilter.countActions(); i++)
      {
        localObject1 = paramIntentFilter.getAction(i);
        localObject2 = (ArrayList)this.mActions.get(localObject1);
        paramBroadcastReceiver = (BroadcastReceiver)localObject2;
        if (localObject2 == null)
        {
          paramBroadcastReceiver = new java/util/ArrayList;
          paramBroadcastReceiver.<init>(1);
          this.mActions.put(localObject1, paramBroadcastReceiver);
        }
        paramBroadcastReceiver.add(localReceiverRecord);
      }
      return;
    }
  }
  
  public boolean sendBroadcast(Intent paramIntent)
  {
    synchronized (this.mReceivers)
    {
      String str1 = paramIntent.getAction();
      Object localObject1 = paramIntent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
      Uri localUri = paramIntent.getData();
      String str2 = paramIntent.getScheme();
      Set localSet = paramIntent.getCategories();
      int i;
      if ((paramIntent.getFlags() & 0x8) != 0) {
        i = 1;
      } else {
        i = 0;
      }
      Object localObject2;
      if (i != 0)
      {
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("Resolving type ");
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(" scheme ");
        ((StringBuilder)localObject2).append(str2);
        ((StringBuilder)localObject2).append(" of intent ");
        ((StringBuilder)localObject2).append(paramIntent);
        Log.v("LocalBroadcastManager", ((StringBuilder)localObject2).toString());
      }
      ArrayList localArrayList = (ArrayList)this.mActions.get(paramIntent.getAction());
      if (localArrayList != null)
      {
        if (i != 0)
        {
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("Action list: ");
          ((StringBuilder)localObject2).append(localArrayList);
          Log.v("LocalBroadcastManager", ((StringBuilder)localObject2).toString());
        }
        Object localObject3 = null;
        for (int j = 0; j < localArrayList.size(); j++)
        {
          Object localObject4 = (ReceiverRecord)localArrayList.get(j);
          if (i != 0)
          {
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append("Matching against filter ");
            ((StringBuilder)localObject2).append(((ReceiverRecord)localObject4).filter);
            Log.v("LocalBroadcastManager", ((StringBuilder)localObject2).toString());
          }
          if (((ReceiverRecord)localObject4).broadcasting)
          {
            if (i != 0) {
              Log.v("LocalBroadcastManager", "  Filter's target already added");
            }
          }
          else
          {
            IntentFilter localIntentFilter = ((ReceiverRecord)localObject4).filter;
            localObject2 = localObject3;
            int k = localIntentFilter.match(str1, (String)localObject1, str2, localUri, localSet, "LocalBroadcastManager");
            if (k >= 0)
            {
              if (i != 0)
              {
                localObject3 = new java/lang/StringBuilder;
                ((StringBuilder)localObject3).<init>();
                ((StringBuilder)localObject3).append("  Filter matched!  match=0x");
                ((StringBuilder)localObject3).append(Integer.toHexString(k));
                Log.v("LocalBroadcastManager", ((StringBuilder)localObject3).toString());
              }
              if (localObject2 == null)
              {
                localObject3 = new java/util/ArrayList;
                ((ArrayList)localObject3).<init>();
              }
              else
              {
                localObject3 = localObject2;
              }
              ((ArrayList)localObject3).add(localObject4);
              ((ReceiverRecord)localObject4).broadcasting = true;
            }
            else if (i != 0)
            {
              switch (k)
              {
              default: 
                localObject2 = "unknown reason";
                break;
              case -1: 
                localObject2 = "type";
                break;
              case -2: 
                localObject2 = "data";
                break;
              case -3: 
                localObject2 = "action";
                break;
              case -4: 
                localObject2 = "category";
              }
              localObject4 = new java/lang/StringBuilder;
              ((StringBuilder)localObject4).<init>();
              ((StringBuilder)localObject4).append("  Filter did not match: ");
              ((StringBuilder)localObject4).append((String)localObject2);
              Log.v("LocalBroadcastManager", ((StringBuilder)localObject4).toString());
            }
          }
        }
        if (localObject3 != null)
        {
          for (i = 0; i < ((ArrayList)localObject3).size(); i++) {
            ((ReceiverRecord)((ArrayList)localObject3).get(i)).broadcasting = false;
          }
          localObject1 = this.mPendingBroadcasts;
          localObject2 = new android/support/v4/content/LocalBroadcastManager$BroadcastRecord;
          ((BroadcastRecord)localObject2).<init>(paramIntent, (ArrayList)localObject3);
          ((ArrayList)localObject1).add(localObject2);
          if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessage(1);
          }
          return true;
        }
      }
      return false;
    }
  }
  
  public void sendBroadcastSync(Intent paramIntent)
  {
    if (sendBroadcast(paramIntent)) {
      executePendingBroadcasts();
    }
  }
  
  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    synchronized (this.mReceivers)
    {
      ArrayList localArrayList1 = (ArrayList)this.mReceivers.remove(paramBroadcastReceiver);
      if (localArrayList1 == null) {
        return;
      }
      for (int i = localArrayList1.size() - 1; i >= 0; i--)
      {
        ReceiverRecord localReceiverRecord1 = (ReceiverRecord)localArrayList1.get(i);
        localReceiverRecord1.dead = true;
        for (int j = 0; j < localReceiverRecord1.filter.countActions(); j++)
        {
          String str = localReceiverRecord1.filter.getAction(j);
          ArrayList localArrayList2 = (ArrayList)this.mActions.get(str);
          if (localArrayList2 != null)
          {
            for (int k = localArrayList2.size() - 1; k >= 0; k--)
            {
              ReceiverRecord localReceiverRecord2 = (ReceiverRecord)localArrayList2.get(k);
              if (localReceiverRecord2.receiver == paramBroadcastReceiver)
              {
                localReceiverRecord2.dead = true;
                localArrayList2.remove(k);
              }
            }
            if (localArrayList2.size() <= 0) {
              this.mActions.remove(str);
            }
          }
        }
      }
      return;
    }
  }
  
  private static final class BroadcastRecord
  {
    final Intent intent;
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;
    
    BroadcastRecord(Intent paramIntent, ArrayList<LocalBroadcastManager.ReceiverRecord> paramArrayList)
    {
      this.intent = paramIntent;
      this.receivers = paramArrayList;
    }
  }
  
  private static final class ReceiverRecord
  {
    boolean broadcasting;
    boolean dead;
    final IntentFilter filter;
    final BroadcastReceiver receiver;
    
    ReceiverRecord(IntentFilter paramIntentFilter, BroadcastReceiver paramBroadcastReceiver)
    {
      this.filter = paramIntentFilter;
      this.receiver = paramBroadcastReceiver;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(128);
      localStringBuilder.append("Receiver{");
      localStringBuilder.append(this.receiver);
      localStringBuilder.append(" filter=");
      localStringBuilder.append(this.filter);
      if (this.dead) {
        localStringBuilder.append(" DEAD");
      }
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              ~/android/support/v4/content/LocalBroadcastManager.class
 *
 * Reversed by:           J
 */