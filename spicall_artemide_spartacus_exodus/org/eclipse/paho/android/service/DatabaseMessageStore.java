package org.eclipse.paho.android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class DatabaseMessageStore
  implements MessageStore
{
  private static final String ARRIVED_MESSAGE_TABLE_NAME = "MqttArrivedMessageTable";
  private static final String MTIMESTAMP = "mtimestamp";
  private static final String TAG = "DatabaseMessageStore";
  private SQLiteDatabase db = null;
  private MQTTDatabaseHelper mqttDb = null;
  private MqttTraceHandler traceHandler = null;
  
  public DatabaseMessageStore(MqttService paramMqttService, Context paramContext)
  {
    this.traceHandler = paramMqttService;
    this.mqttDb = new MQTTDatabaseHelper(this.traceHandler, paramContext);
    this.traceHandler.traceDebug("DatabaseMessageStore", "DatabaseMessageStore<init> complete");
  }
  
  private int getArrivedRowCount(String paramString)
  {
    int i = 0;
    paramString = this.db.query("MqttArrivedMessageTable", new String[] { "messageId" }, "clientHandle=?", new String[] { paramString }, null, null, null);
    if (paramString.moveToFirst()) {
      i = paramString.getInt(0);
    }
    paramString.close();
    return i;
  }
  
  public void clearArrivedMessages(String paramString)
  {
    this.db = this.mqttDb.getWritableDatabase();
    int i;
    if (paramString == null)
    {
      this.traceHandler.traceDebug("DatabaseMessageStore", "clearArrivedMessages: clearing the table");
      i = this.db.delete("MqttArrivedMessageTable", null, null);
    }
    else
    {
      localObject = this.traceHandler;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("clearArrivedMessages: clearing the table of ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" messages");
      ((MqttTraceHandler)localObject).traceDebug("DatabaseMessageStore", localStringBuilder.toString());
      i = this.db.delete("MqttArrivedMessageTable", "clientHandle=?", new String[] { paramString });
    }
    paramString = this.traceHandler;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("clearArrivedMessages: rows affected = ");
    ((StringBuilder)localObject).append(i);
    paramString.traceDebug("DatabaseMessageStore", ((StringBuilder)localObject).toString());
  }
  
  public void close()
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    if (localSQLiteDatabase != null) {
      localSQLiteDatabase.close();
    }
  }
  
  public boolean discardArrived(String paramString1, String paramString2)
  {
    this.db = this.mqttDb.getWritableDatabase();
    MqttTraceHandler localMqttTraceHandler = this.traceHandler;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("discardArrived{");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("}, {");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append("}");
    localMqttTraceHandler.traceDebug("DatabaseMessageStore", ((StringBuilder)localObject).toString());
    try
    {
      int i = this.db.delete("MqttArrivedMessageTable", "messageId=? AND clientHandle=?", new String[] { paramString2, paramString1 });
      if (i != 1)
      {
        localObject = this.traceHandler;
        paramString1 = new StringBuilder();
        paramString1.append("discardArrived - Error deleting message {");
        paramString1.append(paramString2);
        paramString1.append("} from database: Rows affected = ");
        paramString1.append(i);
        ((MqttTraceHandler)localObject).traceError("DatabaseMessageStore", paramString1.toString());
        return false;
      }
      i = getArrivedRowCount(paramString1);
      paramString2 = this.traceHandler;
      paramString1 = new StringBuilder();
      paramString1.append("discardArrived - Message deleted successfully. - messages in db for this clientHandle ");
      paramString1.append(i);
      paramString2.traceDebug("DatabaseMessageStore", paramString1.toString());
      return true;
    }
    catch (SQLException paramString1)
    {
      this.traceHandler.traceException("DatabaseMessageStore", "discardArrived", paramString1);
      throw paramString1;
    }
  }
  
  public Iterator<MessageStore.StoredMessage> getAllArrivedMessages(final String paramString)
  {
    new Iterator()
    {
      private Cursor c;
      private boolean hasNext;
      private final String[] selectionArgs = { paramString };
      
      protected void finalize()
      {
        this.c.close();
        super.finalize();
      }
      
      public boolean hasNext()
      {
        if (!this.hasNext) {
          this.c.close();
        }
        return this.hasNext;
      }
      
      public MessageStore.StoredMessage next()
      {
        Object localObject1 = this.c;
        localObject1 = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("messageId"));
        Object localObject2 = this.c;
        localObject2 = ((Cursor)localObject2).getString(((Cursor)localObject2).getColumnIndex("clientHandle"));
        Object localObject3 = this.c;
        localObject3 = ((Cursor)localObject3).getString(((Cursor)localObject3).getColumnIndex("destinationName"));
        Object localObject4 = this.c;
        localObject4 = ((Cursor)localObject4).getBlob(((Cursor)localObject4).getColumnIndex("payload"));
        Cursor localCursor = this.c;
        int i = localCursor.getInt(localCursor.getColumnIndex("qos"));
        localCursor = this.c;
        boolean bool1 = Boolean.parseBoolean(localCursor.getString(localCursor.getColumnIndex("retained")));
        localCursor = this.c;
        boolean bool2 = Boolean.parseBoolean(localCursor.getString(localCursor.getColumnIndex("duplicate")));
        localObject4 = new DatabaseMessageStore.MqttMessageHack(DatabaseMessageStore.this, (byte[])localObject4);
        ((DatabaseMessageStore.MqttMessageHack)localObject4).setQos(i);
        ((DatabaseMessageStore.MqttMessageHack)localObject4).setRetained(bool1);
        ((DatabaseMessageStore.MqttMessageHack)localObject4).setDuplicate(bool2);
        this.hasNext = this.c.moveToNext();
        return new DatabaseMessageStore.DbStoredData(DatabaseMessageStore.this, (String)localObject1, (String)localObject2, (String)localObject3, (MqttMessage)localObject4);
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }
  
  public String storeArrived(String paramString1, String paramString2, MqttMessage paramMqttMessage)
  {
    this.db = this.mqttDb.getWritableDatabase();
    Object localObject1 = this.traceHandler;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("storeArrived{");
    ((StringBuilder)localObject2).append(paramString1);
    ((StringBuilder)localObject2).append("}, {");
    ((StringBuilder)localObject2).append(paramMqttMessage.toString());
    ((StringBuilder)localObject2).append("}");
    ((MqttTraceHandler)localObject1).traceDebug("DatabaseMessageStore", ((StringBuilder)localObject2).toString());
    localObject1 = paramMqttMessage.getPayload();
    int i = paramMqttMessage.getQos();
    boolean bool1 = paramMqttMessage.isRetained();
    boolean bool2 = paramMqttMessage.isDuplicate();
    localObject2 = new ContentValues();
    paramMqttMessage = UUID.randomUUID().toString();
    ((ContentValues)localObject2).put("messageId", paramMqttMessage);
    ((ContentValues)localObject2).put("clientHandle", paramString1);
    ((ContentValues)localObject2).put("destinationName", paramString2);
    ((ContentValues)localObject2).put("payload", (byte[])localObject1);
    ((ContentValues)localObject2).put("qos", Integer.valueOf(i));
    ((ContentValues)localObject2).put("retained", Boolean.valueOf(bool1));
    ((ContentValues)localObject2).put("duplicate", Boolean.valueOf(bool2));
    ((ContentValues)localObject2).put("mtimestamp", Long.valueOf(System.currentTimeMillis()));
    try
    {
      this.db.insertOrThrow("MqttArrivedMessageTable", null, (ContentValues)localObject2);
      i = getArrivedRowCount(paramString1);
      paramString1 = this.traceHandler;
      paramString2 = new StringBuilder();
      paramString2.append("storeArrived: inserted message with id of {");
      paramString2.append(paramMqttMessage);
      paramString2.append("} - Number of messages in database for this clientHandle = ");
      paramString2.append(i);
      paramString1.traceDebug("DatabaseMessageStore", paramString2.toString());
      return paramMqttMessage;
    }
    catch (SQLException paramString1)
    {
      this.traceHandler.traceException("DatabaseMessageStore", "onUpgrade", paramString1);
      throw paramString1;
    }
  }
  
  private class DbStoredData
    implements MessageStore.StoredMessage
  {
    private String clientHandle;
    private MqttMessage message;
    private String messageId;
    private String topic;
    
    DbStoredData(String paramString1, String paramString2, String paramString3, MqttMessage paramMqttMessage)
    {
      this.messageId = paramString1;
      this.topic = paramString3;
      this.message = paramMqttMessage;
    }
    
    public String getClientHandle()
    {
      return this.clientHandle;
    }
    
    public MqttMessage getMessage()
    {
      return this.message;
    }
    
    public String getMessageId()
    {
      return this.messageId;
    }
    
    public String getTopic()
    {
      return this.topic;
    }
  }
  
  private static class MQTTDatabaseHelper
    extends SQLiteOpenHelper
  {
    private static final String DATABASE_NAME = "mqttAndroidService.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "MQTTDatabaseHelper";
    private MqttTraceHandler traceHandler = null;
    
    public MQTTDatabaseHelper(MqttTraceHandler paramMqttTraceHandler, Context paramContext)
    {
      super("mqttAndroidService.db", null, 1);
      this.traceHandler = paramMqttTraceHandler;
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      MqttTraceHandler localMqttTraceHandler = this.traceHandler;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onCreate {");
      localStringBuilder.append("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
      localStringBuilder.append("}");
      localMqttTraceHandler.traceDebug("MQTTDatabaseHelper", localStringBuilder.toString());
      try
      {
        paramSQLiteDatabase.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
        this.traceHandler.traceDebug("MQTTDatabaseHelper", "created the table");
        return;
      }
      catch (SQLException paramSQLiteDatabase)
      {
        this.traceHandler.traceException("MQTTDatabaseHelper", "onCreate", paramSQLiteDatabase);
        throw paramSQLiteDatabase;
      }
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      this.traceHandler.traceDebug("MQTTDatabaseHelper", "onUpgrade");
      try
      {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
        onCreate(paramSQLiteDatabase);
        this.traceHandler.traceDebug("MQTTDatabaseHelper", "onUpgrade complete");
        return;
      }
      catch (SQLException paramSQLiteDatabase)
      {
        this.traceHandler.traceException("MQTTDatabaseHelper", "onUpgrade", paramSQLiteDatabase);
        throw paramSQLiteDatabase;
      }
    }
  }
  
  private class MqttMessageHack
    extends MqttMessage
  {
    public MqttMessageHack(byte[] paramArrayOfByte)
    {
      super();
    }
    
    protected void setDuplicate(boolean paramBoolean)
    {
      super.setDuplicate(paramBoolean);
    }
  }
}


/* Location:              ~/org/eclipse/paho/android/service/DatabaseMessageStore.class
 *
 * Reversed by:           J
 */