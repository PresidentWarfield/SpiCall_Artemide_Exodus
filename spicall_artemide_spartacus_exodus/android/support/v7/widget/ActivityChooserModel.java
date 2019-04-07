package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel
  extends DataSetObservable
{
  static final String a = "ActivityChooserModel";
  private static final Object e = new Object();
  private static final Map<String, ActivityChooserModel> f = new HashMap();
  final Context b;
  final String c;
  boolean d = true;
  private final Object g = new Object();
  private final List<ActivityResolveInfo> h = new ArrayList();
  private final List<HistoricalRecord> i = new ArrayList();
  private Intent j;
  private ActivitySorter k = new a();
  private int l = 50;
  private boolean m = false;
  private boolean n = true;
  private boolean o = false;
  private OnChooseActivityListener p;
  
  private ActivityChooserModel(Context paramContext, String paramString)
  {
    this.b = paramContext.getApplicationContext();
    if ((!TextUtils.isEmpty(paramString)) && (!paramString.endsWith(".xml")))
    {
      paramContext = new StringBuilder();
      paramContext.append(paramString);
      paramContext.append(".xml");
      this.c = paramContext.toString();
    }
    else
    {
      this.c = paramString;
    }
  }
  
  public static ActivityChooserModel a(Context paramContext, String paramString)
  {
    synchronized (e)
    {
      ActivityChooserModel localActivityChooserModel1 = (ActivityChooserModel)f.get(paramString);
      ActivityChooserModel localActivityChooserModel2 = localActivityChooserModel1;
      if (localActivityChooserModel1 == null)
      {
        localActivityChooserModel2 = new android/support/v7/widget/ActivityChooserModel;
        localActivityChooserModel2.<init>(paramContext, paramString);
        f.put(paramString, localActivityChooserModel2);
      }
      return localActivityChooserModel2;
    }
  }
  
  private boolean a(HistoricalRecord paramHistoricalRecord)
  {
    boolean bool = this.i.add(paramHistoricalRecord);
    if (bool)
    {
      this.n = true;
      i();
      d();
      f();
      notifyChanged();
    }
    return bool;
  }
  
  private void d()
  {
    if (this.m)
    {
      if (!this.n) {
        return;
      }
      this.n = false;
      if (!TextUtils.isEmpty(this.c)) {
        new b().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[] { new ArrayList(this.i), this.c });
      }
      return;
    }
    throw new IllegalStateException("No preceding call to #readHistoricalData");
  }
  
  private void e()
  {
    boolean bool1 = g();
    boolean bool2 = h();
    i();
    if ((bool1 | bool2))
    {
      f();
      notifyChanged();
    }
  }
  
  private boolean f()
  {
    if ((this.k != null) && (this.j != null) && (!this.h.isEmpty()) && (!this.i.isEmpty()))
    {
      this.k.sort(this.j, this.h, Collections.unmodifiableList(this.i));
      return true;
    }
    return false;
  }
  
  private boolean g()
  {
    boolean bool = this.o;
    int i1 = 0;
    if ((bool) && (this.j != null))
    {
      this.o = false;
      this.h.clear();
      List localList = this.b.getPackageManager().queryIntentActivities(this.j, 0);
      int i2 = localList.size();
      while (i1 < i2)
      {
        ResolveInfo localResolveInfo = (ResolveInfo)localList.get(i1);
        this.h.add(new ActivityResolveInfo(localResolveInfo));
        i1++;
      }
      return true;
    }
    return false;
  }
  
  private boolean h()
  {
    if ((this.d) && (this.n) && (!TextUtils.isEmpty(this.c)))
    {
      this.d = false;
      this.m = true;
      j();
      return true;
    }
    return false;
  }
  
  private void i()
  {
    int i1 = this.i.size() - this.l;
    if (i1 <= 0) {
      return;
    }
    this.n = true;
    for (int i2 = 0; i2 < i1; i2++) {
      HistoricalRecord localHistoricalRecord = (HistoricalRecord)this.i.remove(0);
    }
  }
  
  /* Error */
  private void j()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 101	android/support/v7/widget/ActivityChooserModel:b	Landroid/content/Context;
    //   4: aload_0
    //   5: getfield 128	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
    //   8: invokevirtual 239	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   11: astore_1
    //   12: invokestatic 245	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   15: astore_2
    //   16: aload_2
    //   17: aload_1
    //   18: ldc -9
    //   20: invokeinterface 253 3 0
    //   25: iconst_0
    //   26: istore_3
    //   27: iload_3
    //   28: iconst_1
    //   29: if_icmpeq +18 -> 47
    //   32: iload_3
    //   33: iconst_2
    //   34: if_icmpeq +13 -> 47
    //   37: aload_2
    //   38: invokeinterface 256 1 0
    //   43: istore_3
    //   44: goto -17 -> 27
    //   47: ldc_w 258
    //   50: aload_2
    //   51: invokeinterface 261 1 0
    //   56: invokevirtual 264	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   59: ifeq +154 -> 213
    //   62: aload_0
    //   63: getfield 80	android/support/v7/widget/ActivityChooserModel:i	Ljava/util/List;
    //   66: astore 4
    //   68: aload 4
    //   70: invokeinterface 202 1 0
    //   75: aload_2
    //   76: invokeinterface 256 1 0
    //   81: istore_3
    //   82: iload_3
    //   83: iconst_1
    //   84: if_icmpne +14 -> 98
    //   87: aload_1
    //   88: ifnull +254 -> 342
    //   91: aload_1
    //   92: invokevirtual 269	java/io/FileInputStream:close	()V
    //   95: goto +247 -> 342
    //   98: iload_3
    //   99: iconst_3
    //   100: if_icmpeq -25 -> 75
    //   103: iload_3
    //   104: iconst_4
    //   105: if_icmpne +6 -> 111
    //   108: goto -33 -> 75
    //   111: ldc_w 271
    //   114: aload_2
    //   115: invokeinterface 261 1 0
    //   120: invokevirtual 264	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   123: ifeq +74 -> 197
    //   126: aload_2
    //   127: aconst_null
    //   128: ldc_w 273
    //   131: invokeinterface 277 3 0
    //   136: astore 5
    //   138: aload_2
    //   139: aconst_null
    //   140: ldc_w 279
    //   143: invokeinterface 277 3 0
    //   148: invokestatic 285	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   151: lstore 6
    //   153: aload_2
    //   154: aconst_null
    //   155: ldc_w 287
    //   158: invokeinterface 277 3 0
    //   163: invokestatic 293	java/lang/Float:parseFloat	(Ljava/lang/String;)F
    //   166: fstore 8
    //   168: new 15	android/support/v7/widget/ActivityChooserModel$HistoricalRecord
    //   171: astore 9
    //   173: aload 9
    //   175: aload 5
    //   177: lload 6
    //   179: fload 8
    //   181: invokespecial 296	android/support/v7/widget/ActivityChooserModel$HistoricalRecord:<init>	(Ljava/lang/String;JF)V
    //   184: aload 4
    //   186: aload 9
    //   188: invokeinterface 148 2 0
    //   193: pop
    //   194: goto -119 -> 75
    //   197: new 233	org/xmlpull/v1/XmlPullParserException
    //   200: astore 9
    //   202: aload 9
    //   204: ldc_w 298
    //   207: invokespecial 299	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   210: aload 9
    //   212: athrow
    //   213: new 233	org/xmlpull/v1/XmlPullParserException
    //   216: astore 9
    //   218: aload 9
    //   220: ldc_w 301
    //   223: invokespecial 299	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   226: aload 9
    //   228: athrow
    //   229: astore 9
    //   231: goto +112 -> 343
    //   234: astore 5
    //   236: getstatic 303	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
    //   239: astore_2
    //   240: new 117	java/lang/StringBuilder
    //   243: astore 9
    //   245: aload 9
    //   247: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   250: aload 9
    //   252: ldc_w 305
    //   255: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: pop
    //   259: aload 9
    //   261: aload_0
    //   262: getfield 128	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
    //   265: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_2
    //   270: aload 9
    //   272: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   275: aload 5
    //   277: invokestatic 310	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   280: pop
    //   281: aload_1
    //   282: ifnull +60 -> 342
    //   285: goto -194 -> 91
    //   288: astore_2
    //   289: getstatic 303	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
    //   292: astore 9
    //   294: new 117	java/lang/StringBuilder
    //   297: astore 5
    //   299: aload 5
    //   301: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   304: aload 5
    //   306: ldc_w 305
    //   309: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: pop
    //   313: aload 5
    //   315: aload_0
    //   316: getfield 128	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
    //   319: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: pop
    //   323: aload 9
    //   325: aload 5
    //   327: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   330: aload_2
    //   331: invokestatic 310	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   334: pop
    //   335: aload_1
    //   336: ifnull +6 -> 342
    //   339: goto -248 -> 91
    //   342: return
    //   343: aload_1
    //   344: ifnull +7 -> 351
    //   347: aload_1
    //   348: invokevirtual 269	java/io/FileInputStream:close	()V
    //   351: aload 9
    //   353: athrow
    //   354: astore_1
    //   355: return
    //   356: astore_1
    //   357: goto -15 -> 342
    //   360: astore_1
    //   361: goto -10 -> 351
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	364	0	this	ActivityChooserModel
    //   11	337	1	localFileInputStream	java.io.FileInputStream
    //   354	1	1	localFileNotFoundException	java.io.FileNotFoundException
    //   356	1	1	localIOException1	java.io.IOException
    //   360	1	1	localIOException2	java.io.IOException
    //   15	255	2	localObject1	Object
    //   288	43	2	localXmlPullParserException	org.xmlpull.v1.XmlPullParserException
    //   26	80	3	i1	int
    //   66	119	4	localList	List
    //   136	40	5	str	String
    //   234	42	5	localIOException3	java.io.IOException
    //   297	29	5	localStringBuilder	StringBuilder
    //   151	27	6	l1	long
    //   166	14	8	f1	float
    //   171	56	9	localObject2	Object
    //   229	1	9	localObject3	Object
    //   243	109	9	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   12	25	229	finally
    //   37	44	229	finally
    //   47	75	229	finally
    //   75	82	229	finally
    //   111	194	229	finally
    //   197	213	229	finally
    //   213	229	229	finally
    //   236	281	229	finally
    //   289	335	229	finally
    //   12	25	234	java/io/IOException
    //   37	44	234	java/io/IOException
    //   47	75	234	java/io/IOException
    //   75	82	234	java/io/IOException
    //   111	194	234	java/io/IOException
    //   197	213	234	java/io/IOException
    //   213	229	234	java/io/IOException
    //   12	25	288	org/xmlpull/v1/XmlPullParserException
    //   37	44	288	org/xmlpull/v1/XmlPullParserException
    //   47	75	288	org/xmlpull/v1/XmlPullParserException
    //   75	82	288	org/xmlpull/v1/XmlPullParserException
    //   111	194	288	org/xmlpull/v1/XmlPullParserException
    //   197	213	288	org/xmlpull/v1/XmlPullParserException
    //   213	229	288	org/xmlpull/v1/XmlPullParserException
    //   0	12	354	java/io/FileNotFoundException
    //   91	95	356	java/io/IOException
    //   347	351	360	java/io/IOException
  }
  
  public int a()
  {
    synchronized (this.g)
    {
      e();
      int i1 = this.h.size();
      return i1;
    }
  }
  
  public int a(ResolveInfo paramResolveInfo)
  {
    synchronized (this.g)
    {
      e();
      List localList = this.h;
      int i1 = localList.size();
      for (int i2 = 0; i2 < i1; i2++) {
        if (((ActivityResolveInfo)localList.get(i2)).resolveInfo == paramResolveInfo) {
          return i2;
        }
      }
      return -1;
    }
  }
  
  public ResolveInfo a(int paramInt)
  {
    synchronized (this.g)
    {
      e();
      ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.h.get(paramInt)).resolveInfo;
      return localResolveInfo;
    }
  }
  
  public void a(Intent paramIntent)
  {
    synchronized (this.g)
    {
      if (this.j == paramIntent) {
        return;
      }
      this.j = paramIntent;
      this.o = true;
      e();
      return;
    }
  }
  
  public void a(OnChooseActivityListener paramOnChooseActivityListener)
  {
    synchronized (this.g)
    {
      this.p = paramOnChooseActivityListener;
      return;
    }
  }
  
  public Intent b(int paramInt)
  {
    synchronized (this.g)
    {
      if (this.j == null) {
        return null;
      }
      e();
      Object localObject2 = (ActivityResolveInfo)this.h.get(paramInt);
      ComponentName localComponentName = new android/content/ComponentName;
      localComponentName.<init>(((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.packageName, ((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.name);
      localObject2 = new android/content/Intent;
      ((Intent)localObject2).<init>(this.j);
      ((Intent)localObject2).setComponent(localComponentName);
      if (this.p != null)
      {
        localObject4 = new android/content/Intent;
        ((Intent)localObject4).<init>((Intent)localObject2);
        if (this.p.onChooseActivity(this, (Intent)localObject4)) {
          return null;
        }
      }
      Object localObject4 = new android/support/v7/widget/ActivityChooserModel$HistoricalRecord;
      ((HistoricalRecord)localObject4).<init>(localComponentName, System.currentTimeMillis(), 1.0F);
      a((HistoricalRecord)localObject4);
      return (Intent)localObject2;
    }
  }
  
  public ResolveInfo b()
  {
    synchronized (this.g)
    {
      e();
      if (!this.h.isEmpty())
      {
        ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.h.get(0)).resolveInfo;
        return localResolveInfo;
      }
      return null;
    }
  }
  
  public int c()
  {
    synchronized (this.g)
    {
      e();
      int i1 = this.i.size();
      return i1;
    }
  }
  
  public void c(int paramInt)
  {
    synchronized (this.g)
    {
      e();
      Object localObject2 = (ActivityResolveInfo)this.h.get(paramInt);
      Object localObject3 = (ActivityResolveInfo)this.h.get(0);
      float f1;
      if (localObject3 != null) {
        f1 = ((ActivityResolveInfo)localObject3).weight - ((ActivityResolveInfo)localObject2).weight + 5.0F;
      } else {
        f1 = 1.0F;
      }
      localObject3 = new android/content/ComponentName;
      ((ComponentName)localObject3).<init>(((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.packageName, ((ActivityResolveInfo)localObject2).resolveInfo.activityInfo.name);
      localObject2 = new android/support/v7/widget/ActivityChooserModel$HistoricalRecord;
      ((HistoricalRecord)localObject2).<init>((ComponentName)localObject3, System.currentTimeMillis(), f1);
      a((HistoricalRecord)localObject2);
      return;
    }
  }
  
  public static abstract interface ActivityChooserModelClient
  {
    public abstract void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel);
  }
  
  public static final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo>
  {
    public final ResolveInfo resolveInfo;
    public float weight;
    
    public ActivityResolveInfo(ResolveInfo paramResolveInfo)
    {
      this.resolveInfo = paramResolveInfo;
    }
    
    public int compareTo(ActivityResolveInfo paramActivityResolveInfo)
    {
      return Float.floatToIntBits(paramActivityResolveInfo.weight) - Float.floatToIntBits(this.weight);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (ActivityResolveInfo)paramObject;
      return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo)paramObject).weight);
    }
    
    public int hashCode()
    {
      return Float.floatToIntBits(this.weight) + 31;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("resolveInfo:");
      localStringBuilder.append(this.resolveInfo.toString());
      localStringBuilder.append("; weight:");
      localStringBuilder.append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface ActivitySorter
  {
    public abstract void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1);
  }
  
  public static final class HistoricalRecord
  {
    public final ComponentName activity;
    public final long time;
    public final float weight;
    
    public HistoricalRecord(ComponentName paramComponentName, long paramLong, float paramFloat)
    {
      this.activity = paramComponentName;
      this.time = paramLong;
      this.weight = paramFloat;
    }
    
    public HistoricalRecord(String paramString, long paramLong, float paramFloat)
    {
      this(ComponentName.unflattenFromString(paramString), paramLong, paramFloat);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (HistoricalRecord)paramObject;
      ComponentName localComponentName = this.activity;
      if (localComponentName == null)
      {
        if (((HistoricalRecord)paramObject).activity != null) {
          return false;
        }
      }
      else if (!localComponentName.equals(((HistoricalRecord)paramObject).activity)) {
        return false;
      }
      if (this.time != ((HistoricalRecord)paramObject).time) {
        return false;
      }
      return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((HistoricalRecord)paramObject).weight);
    }
    
    public int hashCode()
    {
      ComponentName localComponentName = this.activity;
      int i;
      if (localComponentName == null) {
        i = 0;
      } else {
        i = localComponentName.hashCode();
      }
      long l = this.time;
      return ((i + 31) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.weight);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("; activity:");
      localStringBuilder.append(this.activity);
      localStringBuilder.append("; time:");
      localStringBuilder.append(this.time);
      localStringBuilder.append("; weight:");
      localStringBuilder.append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface OnChooseActivityListener
  {
    public abstract boolean onChooseActivity(ActivityChooserModel paramActivityChooserModel, Intent paramIntent);
  }
  
  private static final class a
    implements ActivityChooserModel.ActivitySorter
  {
    private final Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> a = new HashMap();
    
    public void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1)
    {
      paramIntent = this.a;
      paramIntent.clear();
      int i = paramList.size();
      Object localObject;
      for (int j = 0; j < i; j++)
      {
        localObject = (ActivityChooserModel.ActivityResolveInfo)paramList.get(j);
        ((ActivityChooserModel.ActivityResolveInfo)localObject).weight = 0.0F;
        paramIntent.put(new ComponentName(((ActivityChooserModel.ActivityResolveInfo)localObject).resolveInfo.activityInfo.packageName, ((ActivityChooserModel.ActivityResolveInfo)localObject).resolveInfo.activityInfo.name), localObject);
      }
      j = paramList1.size() - 1;
      float f2;
      for (float f1 = 1.0F; j >= 0; f1 = f2)
      {
        localObject = (ActivityChooserModel.HistoricalRecord)paramList1.get(j);
        ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo = (ActivityChooserModel.ActivityResolveInfo)paramIntent.get(((ActivityChooserModel.HistoricalRecord)localObject).activity);
        f2 = f1;
        if (localActivityResolveInfo != null)
        {
          localActivityResolveInfo.weight += ((ActivityChooserModel.HistoricalRecord)localObject).weight * f1;
          f2 = f1 * 0.95F;
        }
        j--;
      }
      Collections.sort(paramList);
    }
  }
  
  private final class b
    extends AsyncTask<Object, Void, Void>
  {
    b() {}
    
    /* Error */
    public Void a(Object... paramVarArgs)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: checkcast 29	java/util/List
      //   6: astore_2
      //   7: aload_1
      //   8: iconst_1
      //   9: aaload
      //   10: checkcast 31	java/lang/String
      //   13: astore_3
      //   14: aload_0
      //   15: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   18: getfield 34	android/support/v7/widget/ActivityChooserModel:b	Landroid/content/Context;
      //   21: aload_3
      //   22: iconst_0
      //   23: invokevirtual 40	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   26: astore_1
      //   27: invokestatic 46	android/util/Xml:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
      //   30: astore_3
      //   31: aload_3
      //   32: aload_1
      //   33: aconst_null
      //   34: invokeinterface 52 3 0
      //   39: aload_3
      //   40: ldc 54
      //   42: iconst_1
      //   43: invokestatic 60	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   46: invokeinterface 64 3 0
      //   51: aload_3
      //   52: aconst_null
      //   53: ldc 66
      //   55: invokeinterface 70 3 0
      //   60: pop
      //   61: aload_2
      //   62: invokeinterface 74 1 0
      //   67: istore 4
      //   69: iconst_0
      //   70: istore 5
      //   72: iload 5
      //   74: iload 4
      //   76: if_icmpge +95 -> 171
      //   79: aload_2
      //   80: iconst_0
      //   81: invokeinterface 78 2 0
      //   86: checkcast 80	android/support/v7/widget/ActivityChooserModel$HistoricalRecord
      //   89: astore 6
      //   91: aload_3
      //   92: aconst_null
      //   93: ldc 82
      //   95: invokeinterface 70 3 0
      //   100: pop
      //   101: aload_3
      //   102: aconst_null
      //   103: ldc 84
      //   105: aload 6
      //   107: getfield 87	android/support/v7/widget/ActivityChooserModel$HistoricalRecord:activity	Landroid/content/ComponentName;
      //   110: invokevirtual 93	android/content/ComponentName:flattenToString	()Ljava/lang/String;
      //   113: invokeinterface 97 4 0
      //   118: pop
      //   119: aload_3
      //   120: aconst_null
      //   121: ldc 99
      //   123: aload 6
      //   125: getfield 102	android/support/v7/widget/ActivityChooserModel$HistoricalRecord:time	J
      //   128: invokestatic 105	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   131: invokeinterface 97 4 0
      //   136: pop
      //   137: aload_3
      //   138: aconst_null
      //   139: ldc 107
      //   141: aload 6
      //   143: getfield 110	android/support/v7/widget/ActivityChooserModel$HistoricalRecord:weight	F
      //   146: invokestatic 113	java/lang/String:valueOf	(F)Ljava/lang/String;
      //   149: invokeinterface 97 4 0
      //   154: pop
      //   155: aload_3
      //   156: aconst_null
      //   157: ldc 82
      //   159: invokeinterface 116 3 0
      //   164: pop
      //   165: iinc 5 1
      //   168: goto -96 -> 72
      //   171: aload_3
      //   172: aconst_null
      //   173: ldc 66
      //   175: invokeinterface 116 3 0
      //   180: pop
      //   181: aload_3
      //   182: invokeinterface 119 1 0
      //   187: aload_0
      //   188: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   191: iconst_1
      //   192: putfield 123	android/support/v7/widget/ActivityChooserModel:d	Z
      //   195: aload_1
      //   196: ifnull +197 -> 393
      //   199: aload_1
      //   200: invokevirtual 128	java/io/FileOutputStream:close	()V
      //   203: goto +190 -> 393
      //   206: astore_2
      //   207: goto +188 -> 395
      //   210: astore_3
      //   211: getstatic 131	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
      //   214: astore_2
      //   215: new 133	java/lang/StringBuilder
      //   218: astore 6
      //   220: aload 6
      //   222: invokespecial 134	java/lang/StringBuilder:<init>	()V
      //   225: aload 6
      //   227: ldc -120
      //   229: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   232: pop
      //   233: aload 6
      //   235: aload_0
      //   236: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   239: getfield 143	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
      //   242: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   245: pop
      //   246: aload_2
      //   247: aload 6
      //   249: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   252: aload_3
      //   253: invokestatic 152	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   256: pop
      //   257: aload_0
      //   258: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   261: iconst_1
      //   262: putfield 123	android/support/v7/widget/ActivityChooserModel:d	Z
      //   265: aload_1
      //   266: ifnull +127 -> 393
      //   269: goto -70 -> 199
      //   272: astore_2
      //   273: getstatic 131	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
      //   276: astore 6
      //   278: new 133	java/lang/StringBuilder
      //   281: astore_3
      //   282: aload_3
      //   283: invokespecial 134	java/lang/StringBuilder:<init>	()V
      //   286: aload_3
      //   287: ldc -120
      //   289: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   292: pop
      //   293: aload_3
      //   294: aload_0
      //   295: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   298: getfield 143	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
      //   301: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   304: pop
      //   305: aload 6
      //   307: aload_3
      //   308: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   311: aload_2
      //   312: invokestatic 152	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   315: pop
      //   316: aload_0
      //   317: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   320: iconst_1
      //   321: putfield 123	android/support/v7/widget/ActivityChooserModel:d	Z
      //   324: aload_1
      //   325: ifnull +68 -> 393
      //   328: goto -129 -> 199
      //   331: astore_2
      //   332: getstatic 131	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
      //   335: astore_3
      //   336: new 133	java/lang/StringBuilder
      //   339: astore 6
      //   341: aload 6
      //   343: invokespecial 134	java/lang/StringBuilder:<init>	()V
      //   346: aload 6
      //   348: ldc -120
      //   350: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   353: pop
      //   354: aload 6
      //   356: aload_0
      //   357: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   360: getfield 143	android/support/v7/widget/ActivityChooserModel:c	Ljava/lang/String;
      //   363: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   366: pop
      //   367: aload_3
      //   368: aload 6
      //   370: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   373: aload_2
      //   374: invokestatic 152	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   377: pop
      //   378: aload_0
      //   379: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   382: iconst_1
      //   383: putfield 123	android/support/v7/widget/ActivityChooserModel:d	Z
      //   386: aload_1
      //   387: ifnull +6 -> 393
      //   390: goto -191 -> 199
      //   393: aconst_null
      //   394: areturn
      //   395: aload_0
      //   396: getfield 14	android/support/v7/widget/ActivityChooserModel$b:a	Landroid/support/v7/widget/ActivityChooserModel;
      //   399: iconst_1
      //   400: putfield 123	android/support/v7/widget/ActivityChooserModel:d	Z
      //   403: aload_1
      //   404: ifnull +7 -> 411
      //   407: aload_1
      //   408: invokevirtual 128	java/io/FileOutputStream:close	()V
      //   411: aload_2
      //   412: athrow
      //   413: astore_2
      //   414: getstatic 131	android/support/v7/widget/ActivityChooserModel:a	Ljava/lang/String;
      //   417: astore 6
      //   419: new 133	java/lang/StringBuilder
      //   422: dup
      //   423: invokespecial 134	java/lang/StringBuilder:<init>	()V
      //   426: astore_1
      //   427: aload_1
      //   428: ldc -120
      //   430: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   433: pop
      //   434: aload_1
      //   435: aload_3
      //   436: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   439: pop
      //   440: aload 6
      //   442: aload_1
      //   443: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   446: aload_2
      //   447: invokestatic 152	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   450: pop
      //   451: aconst_null
      //   452: areturn
      //   453: astore_1
      //   454: goto -61 -> 393
      //   457: astore_1
      //   458: goto -47 -> 411
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	461	0	this	b
      //   0	461	1	paramVarArgs	Object[]
      //   6	74	2	localList	List
      //   206	1	2	localObject1	Object
      //   214	33	2	str	String
      //   272	40	2	localIllegalStateException	IllegalStateException
      //   331	81	2	localIllegalArgumentException	IllegalArgumentException
      //   413	34	2	localFileNotFoundException	java.io.FileNotFoundException
      //   13	169	3	localObject2	Object
      //   210	43	3	localIOException	java.io.IOException
      //   281	155	3	localObject3	Object
      //   67	10	4	i	int
      //   70	96	5	j	int
      //   89	352	6	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   31	69	206	finally
      //   79	165	206	finally
      //   171	187	206	finally
      //   211	257	206	finally
      //   273	316	206	finally
      //   332	378	206	finally
      //   31	69	210	java/io/IOException
      //   79	165	210	java/io/IOException
      //   171	187	210	java/io/IOException
      //   31	69	272	java/lang/IllegalStateException
      //   79	165	272	java/lang/IllegalStateException
      //   171	187	272	java/lang/IllegalStateException
      //   31	69	331	java/lang/IllegalArgumentException
      //   79	165	331	java/lang/IllegalArgumentException
      //   171	187	331	java/lang/IllegalArgumentException
      //   14	27	413	java/io/FileNotFoundException
      //   199	203	453	java/io/IOException
      //   407	411	457	java/io/IOException
    }
  }
}


/* Location:              ~/android/support/v7/widget/ActivityChooserModel.class
 *
 * Reversed by:           J
 */