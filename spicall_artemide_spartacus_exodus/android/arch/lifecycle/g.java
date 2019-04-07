package android.arch.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class g
  extends Fragment
{
  private a a;
  
  public static void a(Activity paramActivity)
  {
    paramActivity = paramActivity.getFragmentManager();
    if (paramActivity.findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag") == null)
    {
      paramActivity.beginTransaction().add(new g(), "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
      paramActivity.executePendingTransactions();
    }
  }
  
  private void a(b.a parama)
  {
    Object localObject = getActivity();
    if ((localObject instanceof e))
    {
      ((e)localObject).a().a(parama);
      return;
    }
    if ((localObject instanceof c))
    {
      localObject = ((c)localObject).getLifecycle();
      if ((localObject instanceof d)) {
        ((d)localObject).a(parama);
      }
    }
  }
  
  private void a(a parama)
  {
    if (parama != null) {
      parama.a();
    }
  }
  
  private void b(a parama)
  {
    if (parama != null) {
      parama.b();
    }
  }
  
  private void c(a parama)
  {
    if (parama != null) {
      parama.c();
    }
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    a(this.a);
    a(b.a.ON_CREATE);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    a(b.a.ON_DESTROY);
    this.a = null;
  }
  
  public void onPause()
  {
    super.onPause();
    a(b.a.ON_PAUSE);
  }
  
  public void onResume()
  {
    super.onResume();
    c(this.a);
    a(b.a.ON_RESUME);
  }
  
  public void onStart()
  {
    super.onStart();
    b(this.a);
    a(b.a.ON_START);
  }
  
  public void onStop()
  {
    super.onStop();
    a(b.a.ON_STOP);
  }
  
  static abstract interface a
  {
    public abstract void a();
    
    public abstract void b();
    
    public abstract void c();
  }
}


/* Location:              ~/android/arch/lifecycle/g.class
 *
 * Reversed by:           J
 */