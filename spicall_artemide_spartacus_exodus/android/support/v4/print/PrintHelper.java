package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo.Builder;
import android.print.PrintManager;
import android.util.Log;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PrintHelper
{
  public static final int COLOR_MODE_COLOR = 2;
  public static final int COLOR_MODE_MONOCHROME = 1;
  public static final int ORIENTATION_LANDSCAPE = 1;
  public static final int ORIENTATION_PORTRAIT = 2;
  public static final int SCALE_MODE_FILL = 2;
  public static final int SCALE_MODE_FIT = 1;
  private final PrintHelperVersionImpl mImpl;
  
  public PrintHelper(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      this.mImpl = new PrintHelperApi24(paramContext);
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new PrintHelperApi23(paramContext);
    } else if (Build.VERSION.SDK_INT >= 20) {
      this.mImpl = new PrintHelperApi20(paramContext);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mImpl = new PrintHelperApi19(paramContext);
    } else {
      this.mImpl = new PrintHelperStub(null);
    }
  }
  
  public static boolean systemSupportsPrint()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getColorMode()
  {
    return this.mImpl.getColorMode();
  }
  
  public int getOrientation()
  {
    return this.mImpl.getOrientation();
  }
  
  public int getScaleMode()
  {
    return this.mImpl.getScaleMode();
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap)
  {
    this.mImpl.printBitmap(paramString, paramBitmap, null);
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap, OnPrintFinishCallback paramOnPrintFinishCallback)
  {
    this.mImpl.printBitmap(paramString, paramBitmap, paramOnPrintFinishCallback);
  }
  
  public void printBitmap(String paramString, Uri paramUri)
  {
    this.mImpl.printBitmap(paramString, paramUri, null);
  }
  
  public void printBitmap(String paramString, Uri paramUri, OnPrintFinishCallback paramOnPrintFinishCallback)
  {
    this.mImpl.printBitmap(paramString, paramUri, paramOnPrintFinishCallback);
  }
  
  public void setColorMode(int paramInt)
  {
    this.mImpl.setColorMode(paramInt);
  }
  
  public void setOrientation(int paramInt)
  {
    this.mImpl.setOrientation(paramInt);
  }
  
  public void setScaleMode(int paramInt)
  {
    this.mImpl.setScaleMode(paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ColorMode {}
  
  public static abstract interface OnPrintFinishCallback
  {
    public abstract void onFinish();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface Orientation {}
  
  private static class PrintHelperApi19
    implements PrintHelper.PrintHelperVersionImpl
  {
    private static final String LOG_TAG = "PrintHelperApi19";
    private static final int MAX_PRINT_SIZE = 3500;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    protected boolean mIsMinMarginsHandlingCorrect = true;
    private final Object mLock = new Object();
    int mOrientation;
    protected boolean mPrintActivityRespectsOrientation = true;
    int mScaleMode = 2;
    
    PrintHelperApi19(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    private Bitmap convertBitmapForColorMode(Bitmap paramBitmap, int paramInt)
    {
      if (paramInt != 1) {
        return paramBitmap;
      }
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      ColorMatrix localColorMatrix = new ColorMatrix();
      localColorMatrix.setSaturation(0.0F);
      localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
      localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
      localCanvas.setBitmap(null);
      return localBitmap;
    }
    
    private Matrix getMatrix(int paramInt1, int paramInt2, RectF paramRectF, int paramInt3)
    {
      Matrix localMatrix = new Matrix();
      float f1 = paramRectF.width();
      float f2 = paramInt1;
      f1 /= f2;
      if (paramInt3 == 2) {
        f1 = Math.max(f1, paramRectF.height() / paramInt2);
      } else {
        f1 = Math.min(f1, paramRectF.height() / paramInt2);
      }
      localMatrix.postScale(f1, f1);
      localMatrix.postTranslate((paramRectF.width() - f2 * f1) / 2.0F, (paramRectF.height() - paramInt2 * f1) / 2.0F);
      return localMatrix;
    }
    
    private static boolean isPortrait(Bitmap paramBitmap)
    {
      boolean bool;
      if (paramBitmap.getWidth() <= paramBitmap.getHeight()) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    /* Error */
    private Bitmap loadBitmap(Uri paramUri, BitmapFactory.Options paramOptions)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +87 -> 88
      //   4: aload_0
      //   5: getfield 56	android/support/v4/print/PrintHelper$PrintHelperApi19:mContext	Landroid/content/Context;
      //   8: astore_3
      //   9: aload_3
      //   10: ifnull +78 -> 88
      //   13: aconst_null
      //   14: astore 4
      //   16: aload_3
      //   17: invokevirtual 176	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
      //   20: aload_1
      //   21: invokevirtual 182	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
      //   24: astore_3
      //   25: aload_3
      //   26: aconst_null
      //   27: aload_2
      //   28: invokestatic 188	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   31: astore_1
      //   32: aload_3
      //   33: ifnull +20 -> 53
      //   36: aload_3
      //   37: invokevirtual 193	java/io/InputStream:close	()V
      //   40: goto +13 -> 53
      //   43: astore_2
      //   44: ldc 22
      //   46: ldc -61
      //   48: aload_2
      //   49: invokestatic 201	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   52: pop
      //   53: aload_1
      //   54: areturn
      //   55: astore_1
      //   56: aload_3
      //   57: astore_2
      //   58: goto +7 -> 65
      //   61: astore_1
      //   62: aload 4
      //   64: astore_2
      //   65: aload_2
      //   66: ifnull +20 -> 86
      //   69: aload_2
      //   70: invokevirtual 193	java/io/InputStream:close	()V
      //   73: goto +13 -> 86
      //   76: astore_2
      //   77: ldc 22
      //   79: ldc -61
      //   81: aload_2
      //   82: invokestatic 201	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   85: pop
      //   86: aload_1
      //   87: athrow
      //   88: new 203	java/lang/IllegalArgumentException
      //   91: dup
      //   92: ldc -51
      //   94: invokespecial 208	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
      //   97: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	98	0	this	PrintHelperApi19
      //   0	98	1	paramUri	Uri
      //   0	98	2	paramOptions	BitmapFactory.Options
      //   8	49	3	localObject1	Object
      //   14	49	4	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   36	40	43	java/io/IOException
      //   25	32	55	finally
      //   16	25	61	finally
      //   69	73	76	java/io/IOException
    }
    
    /* Error */
    private Bitmap loadConstrainedBitmap(Uri arg1)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +215 -> 216
      //   4: aload_0
      //   5: getfield 56	android/support/v4/print/PrintHelper$PrintHelperApi19:mContext	Landroid/content/Context;
      //   8: ifnull +208 -> 216
      //   11: new 210	android/graphics/BitmapFactory$Options
      //   14: dup
      //   15: invokespecial 211	android/graphics/BitmapFactory$Options:<init>	()V
      //   18: astore_2
      //   19: aload_2
      //   20: iconst_1
      //   21: putfield 214	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   24: aload_0
      //   25: aload_1
      //   26: aload_2
      //   27: invokespecial 216	android/support/v4/print/PrintHelper$PrintHelperApi19:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   30: pop
      //   31: aload_2
      //   32: getfield 219	android/graphics/BitmapFactory$Options:outWidth	I
      //   35: istore_3
      //   36: aload_2
      //   37: getfield 222	android/graphics/BitmapFactory$Options:outHeight	I
      //   40: istore 4
      //   42: iload_3
      //   43: ifle +171 -> 214
      //   46: iload 4
      //   48: ifgt +6 -> 54
      //   51: goto +163 -> 214
      //   54: iload_3
      //   55: iload 4
      //   57: invokestatic 225	java/lang/Math:max	(II)I
      //   60: istore 5
      //   62: iconst_1
      //   63: istore 6
      //   65: iload 5
      //   67: sipush 3500
      //   70: if_icmple +18 -> 88
      //   73: iload 5
      //   75: iconst_1
      //   76: iushr
      //   77: istore 5
      //   79: iload 6
      //   81: iconst_1
      //   82: ishl
      //   83: istore 6
      //   85: goto -20 -> 65
      //   88: iload 6
      //   90: ifle +122 -> 212
      //   93: iload_3
      //   94: iload 4
      //   96: invokestatic 227	java/lang/Math:min	(II)I
      //   99: iload 6
      //   101: idiv
      //   102: ifgt +6 -> 108
      //   105: goto +107 -> 212
      //   108: aload_0
      //   109: getfield 46	android/support/v4/print/PrintHelper$PrintHelperApi19:mLock	Ljava/lang/Object;
      //   112: astore_2
      //   113: aload_2
      //   114: monitorenter
      //   115: new 210	android/graphics/BitmapFactory$Options
      //   118: astore 7
      //   120: aload 7
      //   122: invokespecial 211	android/graphics/BitmapFactory$Options:<init>	()V
      //   125: aload_0
      //   126: aload 7
      //   128: putfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   131: aload_0
      //   132: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   135: iconst_1
      //   136: putfield 230	android/graphics/BitmapFactory$Options:inMutable	Z
      //   139: aload_0
      //   140: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   143: iload 6
      //   145: putfield 233	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   148: aload_0
      //   149: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   152: astore 7
      //   154: aload_2
      //   155: monitorexit
      //   156: aload_0
      //   157: aload_1
      //   158: aload 7
      //   160: invokespecial 216	android/support/v4/print/PrintHelper$PrintHelperApi19:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   163: astore_2
      //   164: aload_0
      //   165: getfield 46	android/support/v4/print/PrintHelper$PrintHelperApi19:mLock	Ljava/lang/Object;
      //   168: astore_1
      //   169: aload_1
      //   170: monitorenter
      //   171: aload_0
      //   172: aconst_null
      //   173: putfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   176: aload_1
      //   177: monitorexit
      //   178: aload_2
      //   179: areturn
      //   180: astore_2
      //   181: aload_1
      //   182: monitorexit
      //   183: aload_2
      //   184: athrow
      //   185: astore_2
      //   186: aload_0
      //   187: getfield 46	android/support/v4/print/PrintHelper$PrintHelperApi19:mLock	Ljava/lang/Object;
      //   190: astore_1
      //   191: aload_1
      //   192: monitorenter
      //   193: aload_0
      //   194: aconst_null
      //   195: putfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
      //   198: aload_1
      //   199: monitorexit
      //   200: aload_2
      //   201: athrow
      //   202: astore_2
      //   203: aload_1
      //   204: monitorexit
      //   205: aload_2
      //   206: athrow
      //   207: astore_1
      //   208: aload_2
      //   209: monitorexit
      //   210: aload_1
      //   211: athrow
      //   212: aconst_null
      //   213: areturn
      //   214: aconst_null
      //   215: areturn
      //   216: new 203	java/lang/IllegalArgumentException
      //   219: dup
      //   220: ldc -21
      //   222: invokespecial 208	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
      //   225: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	226	0	this	PrintHelperApi19
      //   18	161	2	localObject1	Object
      //   180	4	2	localObject2	Object
      //   185	16	2	localObject3	Object
      //   202	7	2	localObject4	Object
      //   35	59	3	i	int
      //   40	55	4	j	int
      //   60	18	5	k	int
      //   63	81	6	m	int
      //   118	41	7	localOptions	BitmapFactory.Options
      // Exception table:
      //   from	to	target	type
      //   171	178	180	finally
      //   181	183	180	finally
      //   156	164	185	finally
      //   193	200	202	finally
      //   203	205	202	finally
      //   115	156	207	finally
      //   208	210	207	finally
    }
    
    private void writeBitmap(final PrintAttributes paramPrintAttributes, final int paramInt, final Bitmap paramBitmap, final ParcelFileDescriptor paramParcelFileDescriptor, final CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
    {
      final PrintAttributes localPrintAttributes;
      if (this.mIsMinMarginsHandlingCorrect) {
        localPrintAttributes = paramPrintAttributes;
      } else {
        localPrintAttributes = copyAttributes(paramPrintAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
      }
      new AsyncTask()
      {
        /* Error */
        protected Throwable doInBackground(Void... paramAnonymousVarArgs)
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 34	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$cancellationSignal	Landroid/os/CancellationSignal;
          //   4: invokevirtual 67	android/os/CancellationSignal:isCanceled	()Z
          //   7: ifeq +5 -> 12
          //   10: aconst_null
          //   11: areturn
          //   12: new 69	android/print/pdf/PrintedPdfDocument
          //   15: astore_2
          //   16: aload_2
          //   17: aload_0
          //   18: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   21: getfield 73	android/support/v4/print/PrintHelper$PrintHelperApi19:mContext	Landroid/content/Context;
          //   24: aload_0
          //   25: getfield 36	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$pdfAttributes	Landroid/print/PrintAttributes;
          //   28: invokespecial 76	android/print/pdf/PrintedPdfDocument:<init>	(Landroid/content/Context;Landroid/print/PrintAttributes;)V
          //   31: aload_0
          //   32: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   35: aload_0
          //   36: getfield 38	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$bitmap	Landroid/graphics/Bitmap;
          //   39: aload_0
          //   40: getfield 36	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$pdfAttributes	Landroid/print/PrintAttributes;
          //   43: invokevirtual 82	android/print/PrintAttributes:getColorMode	()I
          //   46: invokestatic 86	android/support/v4/print/PrintHelper$PrintHelperApi19:access$100	(Landroid/support/v4/print/PrintHelper$PrintHelperApi19;Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
          //   49: astore_3
          //   50: aload_0
          //   51: getfield 34	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$cancellationSignal	Landroid/os/CancellationSignal;
          //   54: invokevirtual 67	android/os/CancellationSignal:isCanceled	()Z
          //   57: istore 4
          //   59: iload 4
          //   61: ifeq +5 -> 66
          //   64: aconst_null
          //   65: areturn
          //   66: aload_2
          //   67: iconst_1
          //   68: invokevirtual 90	android/print/pdf/PrintedPdfDocument:startPage	(I)Landroid/graphics/pdf/PdfDocument$Page;
          //   71: astore 5
          //   73: aload_0
          //   74: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   77: getfield 94	android/support/v4/print/PrintHelper$PrintHelperApi19:mIsMinMarginsHandlingCorrect	Z
          //   80: ifeq +22 -> 102
          //   83: new 96	android/graphics/RectF
          //   86: astore_1
          //   87: aload_1
          //   88: aload 5
          //   90: invokevirtual 102	android/graphics/pdf/PdfDocument$Page:getInfo	()Landroid/graphics/pdf/PdfDocument$PageInfo;
          //   93: invokevirtual 108	android/graphics/pdf/PdfDocument$PageInfo:getContentRect	()Landroid/graphics/Rect;
          //   96: invokespecial 111	android/graphics/RectF:<init>	(Landroid/graphics/Rect;)V
          //   99: goto +60 -> 159
          //   102: new 69	android/print/pdf/PrintedPdfDocument
          //   105: astore 6
          //   107: aload 6
          //   109: aload_0
          //   110: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   113: getfield 73	android/support/v4/print/PrintHelper$PrintHelperApi19:mContext	Landroid/content/Context;
          //   116: aload_0
          //   117: getfield 40	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$attributes	Landroid/print/PrintAttributes;
          //   120: invokespecial 76	android/print/pdf/PrintedPdfDocument:<init>	(Landroid/content/Context;Landroid/print/PrintAttributes;)V
          //   123: aload 6
          //   125: iconst_1
          //   126: invokevirtual 90	android/print/pdf/PrintedPdfDocument:startPage	(I)Landroid/graphics/pdf/PdfDocument$Page;
          //   129: astore 7
          //   131: new 96	android/graphics/RectF
          //   134: astore_1
          //   135: aload_1
          //   136: aload 7
          //   138: invokevirtual 102	android/graphics/pdf/PdfDocument$Page:getInfo	()Landroid/graphics/pdf/PdfDocument$PageInfo;
          //   141: invokevirtual 108	android/graphics/pdf/PdfDocument$PageInfo:getContentRect	()Landroid/graphics/Rect;
          //   144: invokespecial 111	android/graphics/RectF:<init>	(Landroid/graphics/Rect;)V
          //   147: aload 6
          //   149: aload 7
          //   151: invokevirtual 115	android/print/pdf/PrintedPdfDocument:finishPage	(Landroid/graphics/pdf/PdfDocument$Page;)V
          //   154: aload 6
          //   156: invokevirtual 118	android/print/pdf/PrintedPdfDocument:close	()V
          //   159: aload_0
          //   160: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   163: aload_3
          //   164: invokevirtual 123	android/graphics/Bitmap:getWidth	()I
          //   167: aload_3
          //   168: invokevirtual 126	android/graphics/Bitmap:getHeight	()I
          //   171: aload_1
          //   172: aload_0
          //   173: getfield 42	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fittingMode	I
          //   176: invokestatic 130	android/support/v4/print/PrintHelper$PrintHelperApi19:access$200	(Landroid/support/v4/print/PrintHelper$PrintHelperApi19;IILandroid/graphics/RectF;I)Landroid/graphics/Matrix;
          //   179: astore 6
          //   181: aload_0
          //   182: getfield 32	android/support/v4/print/PrintHelper$PrintHelperApi19$2:this$0	Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
          //   185: getfield 94	android/support/v4/print/PrintHelper$PrintHelperApi19:mIsMinMarginsHandlingCorrect	Z
          //   188: ifeq +6 -> 194
          //   191: goto +27 -> 218
          //   194: aload 6
          //   196: aload_1
          //   197: getfield 134	android/graphics/RectF:left	F
          //   200: aload_1
          //   201: getfield 137	android/graphics/RectF:top	F
          //   204: invokevirtual 143	android/graphics/Matrix:postTranslate	(FF)Z
          //   207: pop
          //   208: aload 5
          //   210: invokevirtual 147	android/graphics/pdf/PdfDocument$Page:getCanvas	()Landroid/graphics/Canvas;
          //   213: aload_1
          //   214: invokevirtual 153	android/graphics/Canvas:clipRect	(Landroid/graphics/RectF;)Z
          //   217: pop
          //   218: aload 5
          //   220: invokevirtual 147	android/graphics/pdf/PdfDocument$Page:getCanvas	()Landroid/graphics/Canvas;
          //   223: aload_3
          //   224: aload 6
          //   226: aconst_null
          //   227: invokevirtual 157	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
          //   230: aload_2
          //   231: aload 5
          //   233: invokevirtual 115	android/print/pdf/PrintedPdfDocument:finishPage	(Landroid/graphics/pdf/PdfDocument$Page;)V
          //   236: aload_0
          //   237: getfield 34	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$cancellationSignal	Landroid/os/CancellationSignal;
          //   240: invokevirtual 67	android/os/CancellationSignal:isCanceled	()Z
          //   243: istore 4
          //   245: iload 4
          //   247: ifeq +37 -> 284
          //   250: aload_2
          //   251: invokevirtual 118	android/print/pdf/PrintedPdfDocument:close	()V
          //   254: aload_0
          //   255: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   258: astore_1
          //   259: aload_1
          //   260: ifnull +10 -> 270
          //   263: aload_0
          //   264: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   267: invokevirtual 160	android/os/ParcelFileDescriptor:close	()V
          //   270: aload_3
          //   271: aload_0
          //   272: getfield 38	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$bitmap	Landroid/graphics/Bitmap;
          //   275: if_acmpeq +7 -> 282
          //   278: aload_3
          //   279: invokevirtual 163	android/graphics/Bitmap:recycle	()V
          //   282: aconst_null
          //   283: areturn
          //   284: new 165	java/io/FileOutputStream
          //   287: astore_1
          //   288: aload_1
          //   289: aload_0
          //   290: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   293: invokevirtual 169	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
          //   296: invokespecial 172	java/io/FileOutputStream:<init>	(Ljava/io/FileDescriptor;)V
          //   299: aload_2
          //   300: aload_1
          //   301: invokevirtual 176	android/print/pdf/PrintedPdfDocument:writeTo	(Ljava/io/OutputStream;)V
          //   304: aload_2
          //   305: invokevirtual 118	android/print/pdf/PrintedPdfDocument:close	()V
          //   308: aload_0
          //   309: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   312: astore_1
          //   313: aload_1
          //   314: ifnull +10 -> 324
          //   317: aload_0
          //   318: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   321: invokevirtual 160	android/os/ParcelFileDescriptor:close	()V
          //   324: aload_3
          //   325: aload_0
          //   326: getfield 38	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$bitmap	Landroid/graphics/Bitmap;
          //   329: if_acmpeq +7 -> 336
          //   332: aload_3
          //   333: invokevirtual 163	android/graphics/Bitmap:recycle	()V
          //   336: aconst_null
          //   337: areturn
          //   338: astore_1
          //   339: aload_2
          //   340: invokevirtual 118	android/print/pdf/PrintedPdfDocument:close	()V
          //   343: aload_0
          //   344: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   347: astore_2
          //   348: aload_2
          //   349: ifnull +10 -> 359
          //   352: aload_0
          //   353: getfield 44	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
          //   356: invokevirtual 160	android/os/ParcelFileDescriptor:close	()V
          //   359: aload_3
          //   360: aload_0
          //   361: getfield 38	android/support/v4/print/PrintHelper$PrintHelperApi19$2:val$bitmap	Landroid/graphics/Bitmap;
          //   364: if_acmpeq +7 -> 371
          //   367: aload_3
          //   368: invokevirtual 163	android/graphics/Bitmap:recycle	()V
          //   371: aload_1
          //   372: athrow
          //   373: astore_1
          //   374: aload_1
          //   375: areturn
          //   376: astore_1
          //   377: goto -107 -> 270
          //   380: astore_1
          //   381: goto -57 -> 324
          //   384: astore_2
          //   385: goto -26 -> 359
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	388	0	this	2
          //   0	388	1	paramAnonymousVarArgs	Void[]
          //   15	334	2	localObject1	Object
          //   384	1	2	localIOException	java.io.IOException
          //   49	319	3	localBitmap	Bitmap
          //   57	189	4	bool	boolean
          //   71	161	5	localPage1	android.graphics.pdf.PdfDocument.Page
          //   105	120	6	localObject2	Object
          //   129	21	7	localPage2	android.graphics.pdf.PdfDocument.Page
          // Exception table:
          //   from	to	target	type
          //   66	99	338	finally
          //   102	159	338	finally
          //   159	191	338	finally
          //   194	218	338	finally
          //   218	245	338	finally
          //   284	304	338	finally
          //   0	10	373	java/lang/Throwable
          //   12	59	373	java/lang/Throwable
          //   250	259	373	java/lang/Throwable
          //   263	270	373	java/lang/Throwable
          //   270	282	373	java/lang/Throwable
          //   304	313	373	java/lang/Throwable
          //   317	324	373	java/lang/Throwable
          //   324	336	373	java/lang/Throwable
          //   339	348	373	java/lang/Throwable
          //   352	359	373	java/lang/Throwable
          //   359	371	373	java/lang/Throwable
          //   371	373	373	java/lang/Throwable
          //   263	270	376	java/io/IOException
          //   317	324	380	java/io/IOException
          //   352	359	384	java/io/IOException
        }
        
        protected void onPostExecute(Throwable paramAnonymousThrowable)
        {
          if (paramCancellationSignal.isCanceled())
          {
            paramWriteResultCallback.onWriteCancelled();
          }
          else if (paramAnonymousThrowable == null)
          {
            paramWriteResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
          }
          else
          {
            Log.e("PrintHelperApi19", "Error writing printed content", paramAnonymousThrowable);
            paramWriteResultCallback.onWriteFailed(null);
          }
        }
      }.execute(new Void[0]);
    }
    
    protected PrintAttributes.Builder copyAttributes(PrintAttributes paramPrintAttributes)
    {
      PrintAttributes.Builder localBuilder = new PrintAttributes.Builder().setMediaSize(paramPrintAttributes.getMediaSize()).setResolution(paramPrintAttributes.getResolution()).setMinMargins(paramPrintAttributes.getMinMargins());
      if (paramPrintAttributes.getColorMode() != 0) {
        localBuilder.setColorMode(paramPrintAttributes.getColorMode());
      }
      return localBuilder;
    }
    
    public int getColorMode()
    {
      return this.mColorMode;
    }
    
    public int getOrientation()
    {
      int i = this.mOrientation;
      if (i == 0) {
        return 1;
      }
      return i;
    }
    
    public int getScaleMode()
    {
      return this.mScaleMode;
    }
    
    public void printBitmap(final String paramString, final Bitmap paramBitmap, final PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback)
    {
      if (paramBitmap == null) {
        return;
      }
      final int i = this.mScaleMode;
      PrintManager localPrintManager = (PrintManager)this.mContext.getSystemService("print");
      if (isPortrait(paramBitmap)) {
        localObject = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
      } else {
        localObject = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
      }
      Object localObject = new PrintAttributes.Builder().setMediaSize((PrintAttributes.MediaSize)localObject).setColorMode(this.mColorMode).build();
      localPrintManager.print(paramString, new PrintDocumentAdapter()
      {
        private PrintAttributes mAttributes;
        
        public void onFinish()
        {
          PrintHelper.OnPrintFinishCallback localOnPrintFinishCallback = paramOnPrintFinishCallback;
          if (localOnPrintFinishCallback != null) {
            localOnPrintFinishCallback.onFinish();
          }
        }
        
        public void onLayout(PrintAttributes paramAnonymousPrintAttributes1, PrintAttributes paramAnonymousPrintAttributes2, CancellationSignal paramAnonymousCancellationSignal, PrintDocumentAdapter.LayoutResultCallback paramAnonymousLayoutResultCallback, Bundle paramAnonymousBundle)
        {
          this.mAttributes = paramAnonymousPrintAttributes2;
          paramAnonymousLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(paramString).setContentType(1).setPageCount(1).build(), paramAnonymousPrintAttributes2.equals(paramAnonymousPrintAttributes1) ^ true);
        }
        
        public void onWrite(PageRange[] paramAnonymousArrayOfPageRange, ParcelFileDescriptor paramAnonymousParcelFileDescriptor, CancellationSignal paramAnonymousCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramAnonymousWriteResultCallback)
        {
          PrintHelper.PrintHelperApi19.this.writeBitmap(this.mAttributes, i, paramBitmap, paramAnonymousParcelFileDescriptor, paramAnonymousCancellationSignal, paramAnonymousWriteResultCallback);
        }
      }, (PrintAttributes)localObject);
    }
    
    public void printBitmap(final String paramString, final Uri paramUri, final PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback)
    {
      paramUri = new PrintDocumentAdapter()
      {
        private PrintAttributes mAttributes;
        Bitmap mBitmap = null;
        AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
        
        private void cancelLoad()
        {
          synchronized (PrintHelper.PrintHelperApi19.this.mLock)
          {
            if (PrintHelper.PrintHelperApi19.this.mDecodeOptions != null)
            {
              PrintHelper.PrintHelperApi19.this.mDecodeOptions.requestCancelDecode();
              PrintHelper.PrintHelperApi19.this.mDecodeOptions = null;
            }
            return;
          }
        }
        
        public void onFinish()
        {
          super.onFinish();
          cancelLoad();
          Object localObject = this.mLoadBitmap;
          if (localObject != null) {
            ((AsyncTask)localObject).cancel(true);
          }
          localObject = paramOnPrintFinishCallback;
          if (localObject != null) {
            ((PrintHelper.OnPrintFinishCallback)localObject).onFinish();
          }
          localObject = this.mBitmap;
          if (localObject != null)
          {
            ((Bitmap)localObject).recycle();
            this.mBitmap = null;
          }
        }
        
        public void onLayout(final PrintAttributes paramAnonymousPrintAttributes1, final PrintAttributes paramAnonymousPrintAttributes2, final CancellationSignal paramAnonymousCancellationSignal, final PrintDocumentAdapter.LayoutResultCallback paramAnonymousLayoutResultCallback, Bundle paramAnonymousBundle)
        {
          try
          {
            this.mAttributes = paramAnonymousPrintAttributes2;
            if (paramAnonymousCancellationSignal.isCanceled())
            {
              paramAnonymousLayoutResultCallback.onLayoutCancelled();
              return;
            }
            if (this.mBitmap != null)
            {
              paramAnonymousLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(paramString).setContentType(1).setPageCount(1).build(), paramAnonymousPrintAttributes2.equals(paramAnonymousPrintAttributes1) ^ true);
              return;
            }
            this.mLoadBitmap = new AsyncTask()
            {
              protected Bitmap doInBackground(Uri... paramAnonymous2VarArgs)
              {
                try
                {
                  paramAnonymous2VarArgs = PrintHelper.PrintHelperApi19.this.loadConstrainedBitmap(PrintHelper.PrintHelperApi19.3.this.val$imageFile);
                  return paramAnonymous2VarArgs;
                }
                catch (FileNotFoundException paramAnonymous2VarArgs) {}
                return null;
              }
              
              protected void onCancelled(Bitmap paramAnonymous2Bitmap)
              {
                paramAnonymousLayoutResultCallback.onLayoutCancelled();
                PrintHelper.PrintHelperApi19.3.this.mLoadBitmap = null;
              }
              
              protected void onPostExecute(Bitmap paramAnonymous2Bitmap)
              {
                super.onPostExecute(paramAnonymous2Bitmap);
                Object localObject = paramAnonymous2Bitmap;
                if (paramAnonymous2Bitmap != null) {
                  if (PrintHelper.PrintHelperApi19.this.mPrintActivityRespectsOrientation)
                  {
                    localObject = paramAnonymous2Bitmap;
                    if (PrintHelper.PrintHelperApi19.this.mOrientation != 0) {}
                  }
                  else
                  {
                    try
                    {
                      PrintAttributes.MediaSize localMediaSize = PrintHelper.PrintHelperApi19.3.this.mAttributes.getMediaSize();
                      localObject = paramAnonymous2Bitmap;
                      if (localMediaSize != null)
                      {
                        localObject = paramAnonymous2Bitmap;
                        if (localMediaSize.isPortrait() != PrintHelper.PrintHelperApi19.isPortrait(paramAnonymous2Bitmap))
                        {
                          localObject = new Matrix();
                          ((Matrix)localObject).postRotate(90.0F);
                          localObject = Bitmap.createBitmap(paramAnonymous2Bitmap, 0, 0, paramAnonymous2Bitmap.getWidth(), paramAnonymous2Bitmap.getHeight(), (Matrix)localObject, true);
                        }
                      }
                    }
                    finally {}
                  }
                }
                paramAnonymous2Bitmap = PrintHelper.PrintHelperApi19.3.this;
                paramAnonymous2Bitmap.mBitmap = ((Bitmap)localObject);
                if (localObject != null)
                {
                  paramAnonymous2Bitmap = new PrintDocumentInfo.Builder(paramAnonymous2Bitmap.val$jobName).setContentType(1).setPageCount(1).build();
                  boolean bool = paramAnonymousPrintAttributes2.equals(paramAnonymousPrintAttributes1);
                  paramAnonymousLayoutResultCallback.onLayoutFinished(paramAnonymous2Bitmap, true ^ bool);
                }
                else
                {
                  paramAnonymousLayoutResultCallback.onLayoutFailed(null);
                }
                PrintHelper.PrintHelperApi19.3.this.mLoadBitmap = null;
              }
              
              protected void onPreExecute()
              {
                paramAnonymousCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener()
                {
                  public void onCancel()
                  {
                    PrintHelper.PrintHelperApi19.3.this.cancelLoad();
                    PrintHelper.PrintHelperApi19.3.1.this.cancel(false);
                  }
                });
              }
            }.execute(new Uri[0]);
            return;
          }
          finally {}
        }
        
        public void onWrite(PageRange[] paramAnonymousArrayOfPageRange, ParcelFileDescriptor paramAnonymousParcelFileDescriptor, CancellationSignal paramAnonymousCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramAnonymousWriteResultCallback)
        {
          PrintHelper.PrintHelperApi19.this.writeBitmap(this.mAttributes, this.val$fittingMode, this.mBitmap, paramAnonymousParcelFileDescriptor, paramAnonymousCancellationSignal, paramAnonymousWriteResultCallback);
        }
      };
      PrintManager localPrintManager = (PrintManager)this.mContext.getSystemService("print");
      paramOnPrintFinishCallback = new PrintAttributes.Builder();
      paramOnPrintFinishCallback.setColorMode(this.mColorMode);
      int i = this.mOrientation;
      if ((i != 1) && (i != 0))
      {
        if (i == 2) {
          paramOnPrintFinishCallback.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
      }
      else {
        paramOnPrintFinishCallback.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
      }
      localPrintManager.print(paramString, paramUri, paramOnPrintFinishCallback.build());
    }
    
    public void setColorMode(int paramInt)
    {
      this.mColorMode = paramInt;
    }
    
    public void setOrientation(int paramInt)
    {
      this.mOrientation = paramInt;
    }
    
    public void setScaleMode(int paramInt)
    {
      this.mScaleMode = paramInt;
    }
  }
  
  private static class PrintHelperApi20
    extends PrintHelper.PrintHelperApi19
  {
    PrintHelperApi20(Context paramContext)
    {
      super();
      this.mPrintActivityRespectsOrientation = false;
    }
  }
  
  private static class PrintHelperApi23
    extends PrintHelper.PrintHelperApi20
  {
    PrintHelperApi23(Context paramContext)
    {
      super();
      this.mIsMinMarginsHandlingCorrect = false;
    }
    
    protected PrintAttributes.Builder copyAttributes(PrintAttributes paramPrintAttributes)
    {
      PrintAttributes.Builder localBuilder = super.copyAttributes(paramPrintAttributes);
      if (paramPrintAttributes.getDuplexMode() != 0) {
        localBuilder.setDuplexMode(paramPrintAttributes.getDuplexMode());
      }
      return localBuilder;
    }
  }
  
  private static class PrintHelperApi24
    extends PrintHelper.PrintHelperApi23
  {
    PrintHelperApi24(Context paramContext)
    {
      super();
      this.mIsMinMarginsHandlingCorrect = true;
      this.mPrintActivityRespectsOrientation = true;
    }
  }
  
  private static final class PrintHelperStub
    implements PrintHelper.PrintHelperVersionImpl
  {
    int mColorMode = 2;
    int mOrientation = 1;
    int mScaleMode = 2;
    
    public int getColorMode()
    {
      return this.mColorMode;
    }
    
    public int getOrientation()
    {
      return this.mOrientation;
    }
    
    public int getScaleMode()
    {
      return this.mScaleMode;
    }
    
    public void printBitmap(String paramString, Bitmap paramBitmap, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback) {}
    
    public void printBitmap(String paramString, Uri paramUri, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback) {}
    
    public void setColorMode(int paramInt)
    {
      this.mColorMode = paramInt;
    }
    
    public void setOrientation(int paramInt)
    {
      this.mOrientation = paramInt;
    }
    
    public void setScaleMode(int paramInt)
    {
      this.mScaleMode = paramInt;
    }
  }
  
  static abstract interface PrintHelperVersionImpl
  {
    public abstract int getColorMode();
    
    public abstract int getOrientation();
    
    public abstract int getScaleMode();
    
    public abstract void printBitmap(String paramString, Bitmap paramBitmap, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback);
    
    public abstract void printBitmap(String paramString, Uri paramUri, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback);
    
    public abstract void setColorMode(int paramInt);
    
    public abstract void setOrientation(int paramInt);
    
    public abstract void setScaleMode(int paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ScaleMode {}
}


/* Location:              ~/android/support/v4/print/PrintHelper.class
 *
 * Reversed by:           J
 */