package android.support.v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;

public class IconCompat
{
  private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25F;
  private static final int AMBIENT_SHADOW_ALPHA = 30;
  private static final float BLUR_FACTOR = 0.010416667F;
  private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667F;
  private static final float ICON_DIAMETER_FACTOR = 0.9166667F;
  private static final int KEY_SHADOW_ALPHA = 61;
  private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334F;
  private static final int TYPE_ADAPTIVE_BITMAP = 5;
  private static final int TYPE_BITMAP = 1;
  private static final int TYPE_DATA = 3;
  private static final int TYPE_RESOURCE = 2;
  private static final int TYPE_URI = 4;
  private int mInt1;
  private int mInt2;
  private Object mObj1;
  private final int mType;
  
  private IconCompat(int paramInt)
  {
    this.mType = paramInt;
  }
  
  static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap paramBitmap)
  {
    int i = (int)(Math.min(paramBitmap.getWidth(), paramBitmap.getHeight()) * 0.6666667F);
    Bitmap localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint(3);
    float f1 = i;
    float f2 = 0.5F * f1;
    float f3 = 0.9166667F * f2;
    float f4 = 0.010416667F * f1;
    localPaint.setColor(0);
    localPaint.setShadowLayer(f4, 0.0F, f1 * 0.020833334F, 1023410176);
    localCanvas.drawCircle(f2, f2, f3, localPaint);
    localPaint.setShadowLayer(f4, 0.0F, 0.0F, 503316480);
    localCanvas.drawCircle(f2, f2, f3, localPaint);
    localPaint.clearShadowLayer();
    localPaint.setColor(-16777216);
    BitmapShader localBitmapShader = new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    Matrix localMatrix = new Matrix();
    localMatrix.setTranslate(-(paramBitmap.getWidth() - i) / 2, -(paramBitmap.getHeight() - i) / 2);
    localBitmapShader.setLocalMatrix(localMatrix);
    localPaint.setShader(localBitmapShader);
    localCanvas.drawCircle(f2, f2, f3, localPaint);
    localCanvas.setBitmap(null);
    return localBitmap;
  }
  
  public static IconCompat createWithAdaptiveBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      IconCompat localIconCompat = new IconCompat(5);
      localIconCompat.mObj1 = paramBitmap;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static IconCompat createWithBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      IconCompat localIconCompat = new IconCompat(1);
      localIconCompat.mObj1 = paramBitmap;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static IconCompat createWithContentUri(Uri paramUri)
  {
    if (paramUri != null) {
      return createWithContentUri(paramUri.toString());
    }
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithContentUri(String paramString)
  {
    if (paramString != null)
    {
      IconCompat localIconCompat = new IconCompat(4);
      localIconCompat.mObj1 = paramString;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static IconCompat createWithData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte != null)
    {
      IconCompat localIconCompat = new IconCompat(3);
      localIconCompat.mObj1 = paramArrayOfByte;
      localIconCompat.mInt1 = paramInt1;
      localIconCompat.mInt2 = paramInt2;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Data must not be null.");
  }
  
  public static IconCompat createWithResource(Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      IconCompat localIconCompat = new IconCompat(2);
      localIconCompat.mInt1 = paramInt;
      localIconCompat.mObj1 = paramContext;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Context must not be null.");
  }
  
  public void addToShortcutIntent(Intent paramIntent)
  {
    int i = this.mType;
    if (i != 5) {
      switch (i)
      {
      default: 
        throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
      case 2: 
        paramIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext((Context)this.mObj1, this.mInt1));
        break;
      case 1: 
        paramIntent.putExtra("android.intent.extra.shortcut.ICON", (Bitmap)this.mObj1);
        break;
      }
    } else {
      paramIntent.putExtra("android.intent.extra.shortcut.ICON", createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1));
    }
  }
  
  @TargetApi(26)
  public Icon toIcon()
  {
    switch (this.mType)
    {
    default: 
      throw new IllegalArgumentException("Unknown type");
    case 5: 
      if (Build.VERSION.SDK_INT >= 26) {
        return Icon.createWithAdaptiveBitmap((Bitmap)this.mObj1);
      }
      return Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1));
    case 4: 
      return Icon.createWithContentUri((String)this.mObj1);
    case 3: 
      return Icon.createWithData((byte[])this.mObj1, this.mInt1, this.mInt2);
    case 2: 
      return Icon.createWithResource((Context)this.mObj1, this.mInt1);
    }
    return Icon.createWithBitmap((Bitmap)this.mObj1);
  }
}


/* Location:              ~/android/support/v4/graphics/drawable/IconCompat.class
 *
 * Reversed by:           J
 */