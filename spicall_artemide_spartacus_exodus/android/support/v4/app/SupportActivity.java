package android.support.v4.app;

import android.app.Activity;
import android.arch.lifecycle.b;
import android.arch.lifecycle.b.b;
import android.arch.lifecycle.c;
import android.arch.lifecycle.d;
import android.arch.lifecycle.g;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;

public class SupportActivity
  extends Activity
  implements c
{
  private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap();
  private d mLifecycleRegistry = new d(this);
  
  public <T extends ExtraData> T getExtraData(Class<T> paramClass)
  {
    return (ExtraData)this.mExtraDataMap.get(paramClass);
  }
  
  public b getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    g.a(this);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    this.mLifecycleRegistry.a(b.b.c);
    super.onSaveInstanceState(paramBundle);
  }
  
  public void putExtraData(ExtraData paramExtraData)
  {
    this.mExtraDataMap.put(paramExtraData.getClass(), paramExtraData);
  }
  
  public static class ExtraData {}
}


/* Location:              ~/android/support/v4/app/SupportActivity.class
 *
 * Reversed by:           J
 */