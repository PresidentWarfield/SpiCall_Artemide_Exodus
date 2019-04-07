package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat
{
  private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
  public static final String PARCEL_FONT_RESULTS = "font_results";
  public static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
  public static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
  private static final String TAG = "FontsContractCompat";
  private static final SelfDestructiveThread sBackgroundThread;
  private static final Comparator<byte[]> sByteArrayComparator = new Comparator()
  {
    public int compare(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2)
    {
      if (paramAnonymousArrayOfByte1.length != paramAnonymousArrayOfByte2.length) {
        return paramAnonymousArrayOfByte1.length - paramAnonymousArrayOfByte2.length;
      }
      for (int i = 0; i < paramAnonymousArrayOfByte1.length; i++) {
        if (paramAnonymousArrayOfByte1[i] != paramAnonymousArrayOfByte2[i]) {
          return paramAnonymousArrayOfByte1[i] - paramAnonymousArrayOfByte2[i];
        }
      }
      return 0;
    }
  };
  private static final Object sLock;
  private static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>> sPendingReplies;
  private static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  static
  {
    sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    sLock = new Object();
    sPendingReplies = new SimpleArrayMap();
  }
  
  public static Typeface buildTypeface(Context paramContext, CancellationSignal paramCancellationSignal, FontInfo[] paramArrayOfFontInfo)
  {
    return TypefaceCompat.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, 0);
  }
  
  private static List<byte[]> convertToByteArrayList(Signature[] paramArrayOfSignature)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayOfSignature.length; i++) {
      localArrayList.add(paramArrayOfSignature[i].toByteArray());
    }
    return localArrayList;
  }
  
  private static boolean equalsByteArrayList(List<byte[]> paramList1, List<byte[]> paramList2)
  {
    if (paramList1.size() != paramList2.size()) {
      return false;
    }
    for (int i = 0; i < paramList1.size(); i++) {
      if (!Arrays.equals((byte[])paramList1.get(i), (byte[])paramList2.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  public static FontFamilyResult fetchFonts(Context paramContext, CancellationSignal paramCancellationSignal, FontRequest paramFontRequest)
  {
    ProviderInfo localProviderInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    if (localProviderInfo == null) {
      return new FontFamilyResult(1, null);
    }
    return new FontFamilyResult(0, getFontFromProvider(paramContext, paramFontRequest, localProviderInfo.authority, paramCancellationSignal));
  }
  
  private static List<List<byte[]>> getCertificates(FontRequest paramFontRequest, Resources paramResources)
  {
    if (paramFontRequest.getCertificates() != null) {
      return paramFontRequest.getCertificates();
    }
    return FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontInfo[] getFontFromProvider(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal)
  {
    Object localObject1 = new ArrayList();
    Uri localUri1 = new Uri.Builder().scheme("content").authority(paramString).build();
    Uri localUri2 = new Uri.Builder().scheme("content").authority(paramString).appendPath("file").build();
    Object localObject2 = null;
    paramString = (String)localObject2;
    try
    {
      if (Build.VERSION.SDK_INT > 16)
      {
        paramString = (String)localObject2;
        paramContext = paramContext.getContentResolver();
        paramString = (String)localObject2;
        paramFontRequest = paramFontRequest.getQuery();
        paramString = (String)localObject2;
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null, paramCancellationSignal);
      }
      else
      {
        paramString = (String)localObject2;
        paramContext = paramContext.getContentResolver();
        paramString = (String)localObject2;
        paramFontRequest = paramFontRequest.getQuery();
        paramString = (String)localObject2;
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null);
      }
      paramFontRequest = (FontRequest)localObject1;
      if (paramContext != null)
      {
        paramFontRequest = (FontRequest)localObject1;
        paramString = paramContext;
        if (paramContext.getCount() > 0)
        {
          paramString = paramContext;
          int i = paramContext.getColumnIndex("result_code");
          paramString = paramContext;
          paramCancellationSignal = new java/util/ArrayList;
          paramString = paramContext;
          paramCancellationSignal.<init>();
          paramString = paramContext;
          int j = paramContext.getColumnIndex("_id");
          paramString = paramContext;
          int k = paramContext.getColumnIndex("file_id");
          paramString = paramContext;
          int m = paramContext.getColumnIndex("font_ttc_index");
          paramString = paramContext;
          int n = paramContext.getColumnIndex("font_weight");
          paramString = paramContext;
          int i1 = paramContext.getColumnIndex("font_italic");
          for (;;)
          {
            paramString = paramContext;
            if (!paramContext.moveToNext()) {
              break;
            }
            int i2;
            if (i != -1)
            {
              paramString = paramContext;
              i2 = paramContext.getInt(i);
            }
            else
            {
              i2 = 0;
            }
            int i3;
            if (m != -1)
            {
              paramString = paramContext;
              i3 = paramContext.getInt(m);
            }
            else
            {
              i3 = 0;
            }
            if (k == -1)
            {
              paramString = paramContext;
              paramFontRequest = ContentUris.withAppendedId(localUri1, paramContext.getLong(j));
            }
            else
            {
              paramString = paramContext;
              paramFontRequest = ContentUris.withAppendedId(localUri2, paramContext.getLong(k));
            }
            int i4;
            if (n != -1)
            {
              paramString = paramContext;
              i4 = paramContext.getInt(n);
            }
            else
            {
              i4 = 400;
            }
            if (i1 != -1)
            {
              paramString = paramContext;
              if (paramContext.getInt(i1) == 1)
              {
                bool = true;
                break label501;
              }
            }
            boolean bool = false;
            label501:
            paramString = paramContext;
            localObject1 = new android/support/v4/provider/FontsContractCompat$FontInfo;
            paramString = paramContext;
            ((FontInfo)localObject1).<init>(paramFontRequest, i3, i4, bool, i2);
            paramString = paramContext;
            paramCancellationSignal.add(localObject1);
          }
          paramFontRequest = paramCancellationSignal;
        }
      }
      return (FontInfo[])paramFontRequest.toArray(new FontInfo[0]);
    }
    finally
    {
      if (paramString != null) {
        paramString.close();
      }
    }
  }
  
  private static Typeface getFontInternal(Context paramContext, FontRequest paramFontRequest, int paramInt)
  {
    try
    {
      paramFontRequest = fetchFonts(paramContext, null, paramFontRequest);
      if (paramFontRequest.getStatusCode() == 0) {
        return TypefaceCompat.createFromFontInfo(paramContext, null, paramFontRequest.getFonts(), paramInt);
      }
      return null;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return null;
  }
  
  public static Typeface getFontSync(Context paramContext, FontRequest arg1, final TextView paramTextView, int paramInt1, int paramInt2, final int paramInt3)
  {
    final Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(???.getIdentifier());
    ((StringBuilder)localObject1).append("-");
    ((StringBuilder)localObject1).append(paramInt3);
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = (Typeface)sTypefaceCache.get(localObject1);
    if (localObject2 != null) {
      return (Typeface)localObject2;
    }
    if (paramInt1 == 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if ((paramInt1 != 0) && (paramInt2 == -1)) {
      return getFontInternal(paramContext, ???, paramInt3);
    }
    paramContext = new Callable()
    {
      public Typeface call()
      {
        Typeface localTypeface = FontsContractCompat.getFontInternal(this.val$context, paramFontRequest, paramInt3);
        if (localTypeface != null) {
          FontsContractCompat.sTypefaceCache.put(localObject1, localTypeface);
        }
        return localTypeface;
      }
    };
    if (paramInt1 != 0) {
      try
      {
        paramContext = (Typeface)sBackgroundThread.postAndWait(paramContext, paramInt2);
        return paramContext;
      }
      catch (InterruptedException paramContext)
      {
        return null;
      }
    }
    localObject2 = new SelfDestructiveThread.ReplyCallback()
    {
      public void onReply(Typeface paramAnonymousTypeface)
      {
        if ((TextView)this.val$textViewWeak.get() != null) {
          paramTextView.setTypeface(paramAnonymousTypeface, paramInt3);
        }
      }
    };
    synchronized (sLock)
    {
      if (sPendingReplies.containsKey(localObject1))
      {
        ((ArrayList)sPendingReplies.get(localObject1)).add(localObject2);
        return null;
      }
      paramTextView = new java/util/ArrayList;
      paramTextView.<init>();
      paramTextView.add(localObject2);
      sPendingReplies.put(localObject1, paramTextView);
      sBackgroundThread.postAndReply(paramContext, new SelfDestructiveThread.ReplyCallback()
      {
        public void onReply(Typeface paramAnonymousTypeface)
        {
          synchronized (FontsContractCompat.sLock)
          {
            ArrayList localArrayList = (ArrayList)FontsContractCompat.sPendingReplies.get(this.val$id);
            FontsContractCompat.sPendingReplies.remove(this.val$id);
            for (int i = 0; i < localArrayList.size(); i++) {
              ((SelfDestructiveThread.ReplyCallback)localArrayList.get(i)).onReply(paramAnonymousTypeface);
            }
            return;
          }
        }
      });
      return null;
    }
  }
  
  public static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources)
  {
    String str = paramFontRequest.getProviderAuthority();
    int i = 0;
    ProviderInfo localProviderInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (localProviderInfo != null)
    {
      if (localProviderInfo.packageName.equals(paramFontRequest.getProviderPackage()))
      {
        paramPackageManager = convertToByteArrayList(paramPackageManager.getPackageInfo(localProviderInfo.packageName, 64).signatures);
        Collections.sort(paramPackageManager, sByteArrayComparator);
        paramResources = getCertificates(paramFontRequest, paramResources);
        while (i < paramResources.size())
        {
          paramFontRequest = new ArrayList((Collection)paramResources.get(i));
          Collections.sort(paramFontRequest, sByteArrayComparator);
          if (equalsByteArrayList(paramPackageManager, paramFontRequest)) {
            return localProviderInfo;
          }
          i++;
        }
        return null;
      }
      paramPackageManager = new StringBuilder();
      paramPackageManager.append("Found content provider ");
      paramPackageManager.append(str);
      paramPackageManager.append(", but package was not ");
      paramPackageManager.append(paramFontRequest.getProviderPackage());
      throw new PackageManager.NameNotFoundException(paramPackageManager.toString());
    }
    paramPackageManager = new StringBuilder();
    paramPackageManager.append("No package found for authority: ");
    paramPackageManager.append(str);
    throw new PackageManager.NameNotFoundException(paramPackageManager.toString());
  }
  
  public static Map<Uri, ByteBuffer> prepareFontData(Context paramContext, FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayOfFontInfo.length;
    for (int j = 0; j < i; j++)
    {
      Object localObject = paramArrayOfFontInfo[j];
      if (((FontInfo)localObject).getResultCode() == 0)
      {
        localObject = ((FontInfo)localObject).getUri();
        if (!localHashMap.containsKey(localObject)) {
          localHashMap.put(localObject, TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, (Uri)localObject));
        }
      }
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public static void requestFont(Context paramContext, final FontRequest paramFontRequest, final FontRequestCallback paramFontRequestCallback, Handler paramHandler)
  {
    paramHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          final Object localObject1 = FontsContractCompat.fetchFonts(this.val$context, null, paramFontRequest);
          if (((FontsContractCompat.FontFamilyResult)localObject1).getStatusCode() != 0)
          {
            switch (((FontsContractCompat.FontFamilyResult)localObject1).getStatusCode())
            {
            default: 
              this.val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            case 2: 
              this.val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            this.val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-2);
              }
            });
            return;
          }
          localObject1 = ((FontsContractCompat.FontFamilyResult)localObject1).getFonts();
          if ((localObject1 != null) && (localObject1.length != 0))
          {
            int i = localObject1.length;
            for (final int j = 0; j < i; j++)
            {
              Object localObject2 = localObject1[j];
              if (((FontsContractCompat.FontInfo)localObject2).getResultCode() != 0)
              {
                j = ((FontsContractCompat.FontInfo)localObject2).getResultCode();
                if (j < 0) {
                  this.val$callerThreadHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                    }
                  });
                } else {
                  this.val$callerThreadHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(j);
                    }
                  });
                }
                return;
              }
            }
            localObject1 = FontsContractCompat.buildTypeface(this.val$context, null, (FontsContractCompat.FontInfo[])localObject1);
            if (localObject1 == null)
            {
              this.val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            this.val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                FontsContractCompat.4.this.val$callback.onTypefaceRetrieved(localObject1);
              }
            });
            return;
          }
          this.val$callerThreadHandler.post(new Runnable()
          {
            public void run()
            {
              FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(1);
            }
          });
          return;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          this.val$callerThreadHandler.post(new Runnable()
          {
            public void run()
            {
              FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-1);
            }
          });
        }
      }
    });
  }
  
  public static final class Columns
    implements BaseColumns
  {
    public static final String FILE_ID = "file_id";
    public static final String ITALIC = "font_italic";
    public static final String RESULT_CODE = "result_code";
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    public static final int RESULT_CODE_OK = 0;
    public static final String TTC_INDEX = "font_ttc_index";
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    public static final String WEIGHT = "font_weight";
  }
  
  public static class FontFamilyResult
  {
    public static final int STATUS_OK = 0;
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    private final FontsContractCompat.FontInfo[] mFonts;
    private final int mStatusCode;
    
    public FontFamilyResult(int paramInt, FontsContractCompat.FontInfo[] paramArrayOfFontInfo)
    {
      this.mStatusCode = paramInt;
      this.mFonts = paramArrayOfFontInfo;
    }
    
    public FontsContractCompat.FontInfo[] getFonts()
    {
      return this.mFonts;
    }
    
    public int getStatusCode()
    {
      return this.mStatusCode;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    static @interface FontResultStatus {}
  }
  
  public static class FontInfo
  {
    private final boolean mItalic;
    private final int mResultCode;
    private final int mTtcIndex;
    private final Uri mUri;
    private final int mWeight;
    
    public FontInfo(Uri paramUri, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
    {
      this.mUri = ((Uri)Preconditions.checkNotNull(paramUri));
      this.mTtcIndex = paramInt1;
      this.mWeight = paramInt2;
      this.mItalic = paramBoolean;
      this.mResultCode = paramInt3;
    }
    
    public int getResultCode()
    {
      return this.mResultCode;
    }
    
    public int getTtcIndex()
    {
      return this.mTtcIndex;
    }
    
    public Uri getUri()
    {
      return this.mUri;
    }
    
    public int getWeight()
    {
      return this.mWeight;
    }
    
    public boolean isItalic()
    {
      return this.mItalic;
    }
  }
  
  public static class FontRequestCallback
  {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    
    public void onTypefaceRequestFailed(int paramInt) {}
    
    public void onTypefaceRetrieved(Typeface paramTypeface) {}
    
    @Retention(RetentionPolicy.SOURCE)
    static @interface FontRequestFailReason {}
  }
}


/* Location:              ~/android/support/v4/provider/FontsContractCompat.class
 *
 * Reversed by:           J
 */