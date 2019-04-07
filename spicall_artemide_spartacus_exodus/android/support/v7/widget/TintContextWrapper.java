package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper
  extends ContextWrapper
{
  private static final Object CACHE_LOCK = new Object();
  private static ArrayList<WeakReference<TintContextWrapper>> sCache;
  private final Resources mResources;
  private final Resources.Theme mTheme;
  
  private TintContextWrapper(Context paramContext)
  {
    super(paramContext);
    if (VectorEnabledTintResources.shouldBeUsed())
    {
      this.mResources = new VectorEnabledTintResources(this, paramContext.getResources());
      this.mTheme = this.mResources.newTheme();
      this.mTheme.setTo(paramContext.getTheme());
    }
    else
    {
      this.mResources = new af(this, paramContext.getResources());
      this.mTheme = null;
    }
  }
  
  private static boolean shouldWrap(Context paramContext)
  {
    boolean bool1 = paramContext instanceof TintContextWrapper;
    boolean bool2 = false;
    if ((!bool1) && (!(paramContext.getResources() instanceof af)) && (!(paramContext.getResources() instanceof VectorEnabledTintResources)))
    {
      if ((Build.VERSION.SDK_INT < 21) || (VectorEnabledTintResources.shouldBeUsed())) {
        bool2 = true;
      }
      return bool2;
    }
    return false;
  }
  
  public static Context wrap(Context paramContext)
  {
    if (shouldWrap(paramContext)) {
      synchronized (CACHE_LOCK)
      {
        if (sCache == null)
        {
          localObject2 = new java/util/ArrayList;
          ((ArrayList)localObject2).<init>();
          sCache = (ArrayList)localObject2;
        }
        else
        {
          for (int i = sCache.size() - 1; i >= 0; i--)
          {
            localObject2 = (WeakReference)sCache.get(i);
            if ((localObject2 == null) || (((WeakReference)localObject2).get() == null)) {
              sCache.remove(i);
            }
          }
          for (i = sCache.size() - 1; i >= 0; i--)
          {
            localObject2 = (WeakReference)sCache.get(i);
            if (localObject2 != null) {
              localObject2 = (TintContextWrapper)((WeakReference)localObject2).get();
            } else {
              localObject2 = null;
            }
            if ((localObject2 != null) && (((TintContextWrapper)localObject2).getBaseContext() == paramContext)) {
              return (Context)localObject2;
            }
          }
        }
        Object localObject2 = new android/support/v7/widget/TintContextWrapper;
        ((TintContextWrapper)localObject2).<init>(paramContext);
        paramContext = sCache;
        WeakReference localWeakReference = new java/lang/ref/WeakReference;
        localWeakReference.<init>(localObject2);
        paramContext.add(localWeakReference);
        return (Context)localObject2;
      }
    }
    return paramContext;
  }
  
  public AssetManager getAssets()
  {
    return this.mResources.getAssets();
  }
  
  public Resources getResources()
  {
    return this.mResources;
  }
  
  public Resources.Theme getTheme()
  {
    Resources.Theme localTheme1 = this.mTheme;
    Resources.Theme localTheme2 = localTheme1;
    if (localTheme1 == null) {
      localTheme2 = super.getTheme();
    }
    return localTheme2;
  }
  
  public void setTheme(int paramInt)
  {
    Resources.Theme localTheme = this.mTheme;
    if (localTheme == null) {
      super.setTheme(paramInt);
    } else {
      localTheme.applyStyle(paramInt, true);
    }
  }
}


/* Location:              ~/android/support/v7/widget/TintContextWrapper.class
 *
 * Reversed by:           J
 */