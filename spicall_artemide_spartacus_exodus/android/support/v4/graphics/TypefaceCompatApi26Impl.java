package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl
  extends TypefaceCompatApi21Impl
{
  private static final String ABORT_CREATION_METHOD = "abortCreation";
  private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
  private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String FREEZE_METHOD = "freeze";
  private static final int RESOLVE_BY_FONT_TABLE = -1;
  private static final String TAG = "TypefaceCompatApi26Impl";
  private static final Method sAbortCreation;
  private static final Method sAddFontFromAssetManager;
  private static final Method sAddFontFromBuffer;
  private static final Method sCreateFromFamiliesWithDefault;
  private static final Class sFontFamily;
  private static final Constructor sFontFamilyCtor;
  private static final Method sFreeze;
  
  static
  {
    Object localObject1 = null;
    try
    {
      localObject2 = Class.forName("android.graphics.FontFamily");
      localObject3 = ((Class)localObject2).getConstructor(new Class[0]);
      localObject4 = ((Class)localObject2).getMethod("addFontFromAssetManager", new Class[] { AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class });
      localObject5 = ((Class)localObject2).getMethod("addFontFromBuffer", new Class[] { ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE });
      localObject6 = ((Class)localObject2).getMethod("freeze", new Class[0]);
      localObject7 = ((Class)localObject2).getMethod("abortCreation", new Class[0]);
      Method localMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance((Class)localObject2, 1).getClass(), Integer.TYPE, Integer.TYPE });
      localMethod.setAccessible(true);
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Object localObject4 = new StringBuilder();
    ((StringBuilder)localObject4).append("Unable to collect necessary methods for class ");
    ((StringBuilder)localObject4).append(localClassNotFoundException.getClass().getName());
    Log.e("TypefaceCompatApi26Impl", ((StringBuilder)localObject4).toString(), localClassNotFoundException);
    Object localObject9 = null;
    Object localObject8 = localObject9;
    localObject4 = localObject8;
    Object localObject3 = localObject4;
    Object localObject2 = localObject3;
    Object localObject7 = localObject2;
    Object localObject6 = localObject2;
    Object localObject5 = localObject3;
    localObject2 = localObject9;
    localObject3 = localObject1;
    sFontFamilyCtor = (Constructor)localObject3;
    sFontFamily = (Class)localObject2;
    sAddFontFromAssetManager = (Method)localObject4;
    sAddFontFromBuffer = (Method)localObject5;
    sFreeze = (Method)localObject6;
    sAbortCreation = (Method)localObject7;
    sCreateFromFamiliesWithDefault = (Method)localObject8;
  }
  
  private static boolean abortCreation(Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)sAbortCreation.invoke(paramObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException((Throwable)paramObject);
  }
  
  private static boolean addFontFromAssetManager(Context paramContext, Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      boolean bool = ((Boolean)sAddFontFromAssetManager.invoke(paramObject, new Object[] { paramContext.getAssets(), paramString, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), null })).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramContext) {}catch (IllegalAccessException paramContext) {}
    throw new RuntimeException(paramContext);
  }
  
  private static boolean addFontFromBuffer(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      boolean bool = ((Boolean)sAddFontFromBuffer.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) })).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException((Throwable)paramObject);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    try
    {
      Object localObject = Array.newInstance(sFontFamily, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { localObject, Integer.valueOf(-1), Integer.valueOf(-1) });
      return (Typeface)paramObject;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException((Throwable)paramObject);
  }
  
  private static boolean freeze(Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)sFreeze.invoke(paramObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException((Throwable)paramObject);
  }
  
  private static boolean isFontFamilyPrivateAPIAvailable()
  {
    if (sAddFontFromAssetManager == null) {
      Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
    }
    boolean bool;
    if (sAddFontFromAssetManager != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static Object newFamily()
  {
    try
    {
      Object localObject = sFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    throw new RuntimeException(localIllegalAccessException);
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromFontFamilyFilesResourceEntry(paramContext, paramFontFamilyFilesResourceEntry, paramResources, paramInt);
    }
    paramResources = newFamily();
    for (paramFontFamilyFilesResourceEntry : paramFontFamilyFilesResourceEntry.getEntries()) {
      if (!addFontFromAssetManager(paramContext, paramResources, paramFontFamilyFilesResourceEntry.getFileName(), 0, paramFontFamilyFilesResourceEntry.getWeight(), paramFontFamilyFilesResourceEntry.isItalic()))
      {
        abortCreation(paramResources);
        return null;
      }
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, android.support.v4.provider.FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: invokestatic 196	android/support/v4/graphics/TypefaceCompatApi26Impl:isFontFamilyPrivateAPIAvailable	()Z
    //   11: ifne +116 -> 127
    //   14: aload_0
    //   15: aload_3
    //   16: iload 4
    //   18: invokevirtual 236	android/support/v4/graphics/TypefaceCompatApi26Impl:findBestInfo	([Landroid/support/v4/provider/FontsContractCompat$FontInfo;I)Landroid/support/v4/provider/FontsContractCompat$FontInfo;
    //   21: astore 5
    //   23: aload_1
    //   24: invokevirtual 240	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   27: astore_1
    //   28: aload_1
    //   29: aload 5
    //   31: invokevirtual 246	android/support/v4/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   34: ldc -8
    //   36: aload_2
    //   37: invokevirtual 254	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   40: astore_3
    //   41: new 256	android/graphics/Typeface$Builder
    //   44: astore_1
    //   45: aload_1
    //   46: aload_3
    //   47: invokevirtual 262	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   50: invokespecial 265	android/graphics/Typeface$Builder:<init>	(Ljava/io/FileDescriptor;)V
    //   53: aload_1
    //   54: aload 5
    //   56: invokevirtual 266	android/support/v4/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   59: invokevirtual 270	android/graphics/Typeface$Builder:setWeight	(I)Landroid/graphics/Typeface$Builder;
    //   62: aload 5
    //   64: invokevirtual 271	android/support/v4/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   67: invokevirtual 275	android/graphics/Typeface$Builder:setItalic	(Z)Landroid/graphics/Typeface$Builder;
    //   70: invokevirtual 279	android/graphics/Typeface$Builder:build	()Landroid/graphics/Typeface;
    //   73: astore_1
    //   74: aload_3
    //   75: ifnull +7 -> 82
    //   78: aload_3
    //   79: invokevirtual 282	android/os/ParcelFileDescriptor:close	()V
    //   82: aload_1
    //   83: areturn
    //   84: astore_2
    //   85: aconst_null
    //   86: astore_1
    //   87: goto +7 -> 94
    //   90: astore_1
    //   91: aload_1
    //   92: athrow
    //   93: astore_2
    //   94: aload_3
    //   95: ifnull +27 -> 122
    //   98: aload_1
    //   99: ifnull +19 -> 118
    //   102: aload_3
    //   103: invokevirtual 282	android/os/ParcelFileDescriptor:close	()V
    //   106: goto +16 -> 122
    //   109: astore_3
    //   110: aload_1
    //   111: aload_3
    //   112: invokevirtual 285	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   115: goto +7 -> 122
    //   118: aload_3
    //   119: invokevirtual 282	android/os/ParcelFileDescriptor:close	()V
    //   122: aload_2
    //   123: athrow
    //   124: astore_1
    //   125: aconst_null
    //   126: areturn
    //   127: aload_1
    //   128: aload_3
    //   129: aload_2
    //   130: invokestatic 291	android/support/v4/provider/FontsContractCompat:prepareFontData	(Landroid/content/Context;[Landroid/support/v4/provider/FontsContractCompat$FontInfo;Landroid/os/CancellationSignal;)Ljava/util/Map;
    //   133: astore 6
    //   135: invokestatic 200	android/support/v4/graphics/TypefaceCompatApi26Impl:newFamily	()Ljava/lang/Object;
    //   138: astore 5
    //   140: aload_3
    //   141: arraylength
    //   142: istore 7
    //   144: iconst_0
    //   145: istore 4
    //   147: iconst_0
    //   148: istore 8
    //   150: iload 4
    //   152: iload 7
    //   154: if_icmpge +68 -> 222
    //   157: aload_3
    //   158: iload 4
    //   160: aaload
    //   161: astore_2
    //   162: aload 6
    //   164: aload_2
    //   165: invokevirtual 246	android/support/v4/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   168: invokeinterface 297 2 0
    //   173: checkcast 75	java/nio/ByteBuffer
    //   176: astore_1
    //   177: aload_1
    //   178: ifnonnull +6 -> 184
    //   181: goto +35 -> 216
    //   184: aload 5
    //   186: aload_1
    //   187: aload_2
    //   188: invokevirtual 300	android/support/v4/provider/FontsContractCompat$FontInfo:getTtcIndex	()I
    //   191: aload_2
    //   192: invokevirtual 266	android/support/v4/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   195: aload_2
    //   196: invokevirtual 271	android/support/v4/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   199: invokestatic 302	android/support/v4/graphics/TypefaceCompatApi26Impl:addFontFromBuffer	(Ljava/lang/Object;Ljava/nio/ByteBuffer;III)Z
    //   202: ifne +11 -> 213
    //   205: aload 5
    //   207: invokestatic 222	android/support/v4/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)Z
    //   210: pop
    //   211: aconst_null
    //   212: areturn
    //   213: iconst_1
    //   214: istore 8
    //   216: iinc 4 1
    //   219: goto -69 -> 150
    //   222: iload 8
    //   224: ifne +11 -> 235
    //   227: aload 5
    //   229: invokestatic 222	android/support/v4/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)Z
    //   232: pop
    //   233: aconst_null
    //   234: areturn
    //   235: aload 5
    //   237: invokestatic 224	android/support/v4/graphics/TypefaceCompatApi26Impl:freeze	(Ljava/lang/Object;)Z
    //   240: ifne +5 -> 245
    //   243: aconst_null
    //   244: areturn
    //   245: aload 5
    //   247: invokestatic 226	android/support/v4/graphics/TypefaceCompatApi26Impl:createFromFamiliesWithDefault	(Ljava/lang/Object;)Landroid/graphics/Typeface;
    //   250: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	this	TypefaceCompatApi26Impl
    //   0	251	1	paramContext	Context
    //   0	251	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	251	3	paramArrayOfFontInfo	android.support.v4.provider.FontsContractCompat.FontInfo[]
    //   0	251	4	paramInt	int
    //   21	225	5	localObject	Object
    //   133	30	6	localMap	java.util.Map
    //   142	13	7	i	int
    //   148	75	8	j	int
    // Exception table:
    //   from	to	target	type
    //   41	74	84	finally
    //   41	74	90	java/lang/Throwable
    //   91	93	93	finally
    //   102	106	109	java/lang/Throwable
    //   28	41	124	java/io/IOException
    //   78	82	124	java/io/IOException
    //   102	106	124	java/io/IOException
    //   110	115	124	java/io/IOException
    //   118	122	124	java/io/IOException
    //   122	124	124	java/io/IOException
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, paramInt2);
    }
    paramResources = newFamily();
    if (!addFontFromAssetManager(paramContext, paramResources, paramString, 0, -1, -1))
    {
      abortCreation(paramResources);
      return null;
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
}


/* Location:              ~/android/support/v4/graphics/TypefaceCompatApi26Impl.class
 *
 * Reversed by:           J
 */