package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat
{
  public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
  private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();
  
  public static DisplayManagerCompat getInstance(Context paramContext)
  {
    synchronized (sInstances)
    {
      DisplayManagerCompat localDisplayManagerCompat = (DisplayManagerCompat)sInstances.get(paramContext);
      Object localObject = localDisplayManagerCompat;
      if (localDisplayManagerCompat == null)
      {
        if (Build.VERSION.SDK_INT >= 17)
        {
          localObject = new android/support/v4/hardware/display/DisplayManagerCompat$DisplayManagerCompatApi17Impl;
          ((DisplayManagerCompatApi17Impl)localObject).<init>(paramContext);
        }
        else
        {
          localObject = new DisplayManagerCompatApi14Impl(paramContext);
        }
        sInstances.put(paramContext, localObject);
      }
      return (DisplayManagerCompat)localObject;
    }
  }
  
  public abstract Display getDisplay(int paramInt);
  
  public abstract Display[] getDisplays();
  
  public abstract Display[] getDisplays(String paramString);
  
  private static class DisplayManagerCompatApi14Impl
    extends DisplayManagerCompat
  {
    private final WindowManager mWindowManager;
    
    DisplayManagerCompatApi14Impl(Context paramContext)
    {
      this.mWindowManager = ((WindowManager)paramContext.getSystemService("window"));
    }
    
    public Display getDisplay(int paramInt)
    {
      Display localDisplay = this.mWindowManager.getDefaultDisplay();
      if (localDisplay.getDisplayId() == paramInt) {
        return localDisplay;
      }
      return null;
    }
    
    public Display[] getDisplays()
    {
      return new Display[] { this.mWindowManager.getDefaultDisplay() };
    }
    
    public Display[] getDisplays(String paramString)
    {
      if (paramString == null) {
        paramString = getDisplays();
      } else {
        paramString = new Display[0];
      }
      return paramString;
    }
  }
  
  private static class DisplayManagerCompatApi17Impl
    extends DisplayManagerCompat
  {
    private final DisplayManager mDisplayManager;
    
    DisplayManagerCompatApi17Impl(Context paramContext)
    {
      this.mDisplayManager = ((DisplayManager)paramContext.getSystemService("display"));
    }
    
    public Display getDisplay(int paramInt)
    {
      return this.mDisplayManager.getDisplay(paramInt);
    }
    
    public Display[] getDisplays()
    {
      return this.mDisplayManager.getDisplays();
    }
    
    public Display[] getDisplays(String paramString)
    {
      return this.mDisplayManager.getDisplays(paramString);
    }
  }
}


/* Location:              ~/android/support/v4/hardware/display/DisplayManagerCompat.class
 *
 * Reversed by:           J
 */