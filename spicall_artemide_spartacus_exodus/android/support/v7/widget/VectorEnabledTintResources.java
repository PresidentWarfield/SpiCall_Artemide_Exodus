package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.app.g;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources
  extends Resources
{
  public static final int MAX_SDK_WHERE_REQUIRED = 20;
  private final WeakReference<Context> mContextRef;
  
  public VectorEnabledTintResources(Context paramContext, Resources paramResources)
  {
    super(paramResources.getAssets(), paramResources.getDisplayMetrics(), paramResources.getConfiguration());
    this.mContextRef = new WeakReference(paramContext);
  }
  
  public static boolean shouldBeUsed()
  {
    boolean bool;
    if ((g.l()) && (Build.VERSION.SDK_INT <= 20)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Drawable getDrawable(int paramInt)
  {
    Context localContext = (Context)this.mContextRef.get();
    if (localContext != null) {
      return AppCompatDrawableManager.get().onDrawableLoadedFromResources(localContext, this, paramInt);
    }
    return super.getDrawable(paramInt);
  }
  
  final Drawable superGetDrawable(int paramInt)
  {
    return super.getDrawable(paramInt);
  }
}


/* Location:              ~/android/support/v7/widget/VectorEnabledTintResources.class
 *
 * Reversed by:           J
 */