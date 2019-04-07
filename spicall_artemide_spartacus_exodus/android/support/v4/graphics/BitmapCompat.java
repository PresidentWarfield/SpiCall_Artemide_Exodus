package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;

public final class BitmapCompat
{
  static final BitmapCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new BitmapCompatApi19Impl();
    } else if (Build.VERSION.SDK_INT >= 18) {
      IMPL = new BitmapCompatApi18Impl();
    } else {
      IMPL = new BitmapCompatBaseImpl();
    }
  }
  
  public static int getAllocationByteCount(Bitmap paramBitmap)
  {
    return IMPL.getAllocationByteCount(paramBitmap);
  }
  
  public static boolean hasMipMap(Bitmap paramBitmap)
  {
    return IMPL.hasMipMap(paramBitmap);
  }
  
  public static void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean)
  {
    IMPL.setHasMipMap(paramBitmap, paramBoolean);
  }
  
  static class BitmapCompatApi18Impl
    extends BitmapCompat.BitmapCompatBaseImpl
  {
    public boolean hasMipMap(Bitmap paramBitmap)
    {
      return paramBitmap.hasMipMap();
    }
    
    public void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean)
    {
      paramBitmap.setHasMipMap(paramBoolean);
    }
  }
  
  static class BitmapCompatApi19Impl
    extends BitmapCompat.BitmapCompatApi18Impl
  {
    public int getAllocationByteCount(Bitmap paramBitmap)
    {
      return paramBitmap.getAllocationByteCount();
    }
  }
  
  static class BitmapCompatBaseImpl
  {
    public int getAllocationByteCount(Bitmap paramBitmap)
    {
      return paramBitmap.getByteCount();
    }
    
    public boolean hasMipMap(Bitmap paramBitmap)
    {
      return false;
    }
    
    public void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean) {}
  }
}


/* Location:              ~/android/support/v4/graphics/BitmapCompat.class
 *
 * Reversed by:           J
 */