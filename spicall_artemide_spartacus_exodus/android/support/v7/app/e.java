package android.support.v7.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.TaskStackBuilder.SupportParentable;
import android.support.v7.view.b;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

public class e
  extends FragmentActivity
  implements TaskStackBuilder.SupportParentable, b.b, f
{
  private g a;
  private int b = 0;
  private Resources c;
  
  private boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((Build.VERSION.SDK_INT < 26) && (!paramKeyEvent.isCtrlPressed()) && (!KeyEvent.metaStateHasNoModifiers(paramKeyEvent.getMetaState())) && (paramKeyEvent.getRepeatCount() == 0) && (!KeyEvent.isModifierKey(paramKeyEvent.getKeyCode())))
    {
      Window localWindow = getWindow();
      if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().dispatchKeyShortcutEvent(paramKeyEvent))) {
        return true;
      }
    }
    return false;
  }
  
  public b.a a()
  {
    return e().h();
  }
  
  public b a(android.support.v7.view.b.a parama)
  {
    return null;
  }
  
  public void a(TaskStackBuilder paramTaskStackBuilder)
  {
    paramTaskStackBuilder.addParentStack(this);
  }
  
  public void a(b paramb) {}
  
  public void a(Toolbar paramToolbar)
  {
    e().a(paramToolbar);
  }
  
  public boolean a(Intent paramIntent)
  {
    return NavUtils.shouldUpRecreateTask(this, paramIntent);
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    e().b(paramView, paramLayoutParams);
  }
  
  public a b()
  {
    return e().a();
  }
  
  public void b(Intent paramIntent)
  {
    NavUtils.navigateUpTo(this, paramIntent);
  }
  
  public void b(TaskStackBuilder paramTaskStackBuilder) {}
  
  public void b(b paramb) {}
  
  public boolean c()
  {
    Object localObject = getSupportParentActivityIntent();
    if (localObject != null)
    {
      if (a((Intent)localObject))
      {
        localObject = TaskStackBuilder.create(this);
        a((TaskStackBuilder)localObject);
        b((TaskStackBuilder)localObject);
        ((TaskStackBuilder)localObject).startActivities();
        try
        {
          ActivityCompat.finishAffinity(this);
        }
        catch (IllegalStateException localIllegalStateException)
        {
          finish();
        }
      }
      else
      {
        b(localIllegalStateException);
      }
      return true;
    }
    return false;
  }
  
  public void closeOptionsMenu()
  {
    a locala = b();
    if ((getWindow().hasFeature(0)) && ((locala == null) || (!locala.d()))) {
      super.closeOptionsMenu();
    }
  }
  
  @Deprecated
  public void d() {}
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    a locala = b();
    if ((i == 82) && (locala != null) && (locala.a(paramKeyEvent))) {
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public g e()
  {
    if (this.a == null) {
      this.a = g.a(this, this);
    }
    return this.a;
  }
  
  public <T extends View> T findViewById(int paramInt)
  {
    return e().a(paramInt);
  }
  
  public MenuInflater getMenuInflater()
  {
    return e().b();
  }
  
  public Resources getResources()
  {
    if ((this.c == null) && (VectorEnabledTintResources.shouldBeUsed())) {
      this.c = new VectorEnabledTintResources(this, super.getResources());
    }
    Resources localResources1 = this.c;
    Resources localResources2 = localResources1;
    if (localResources1 == null) {
      localResources2 = super.getResources();
    }
    return localResources2;
  }
  
  public Intent getSupportParentActivityIntent()
  {
    return NavUtils.getParentActivityIntent(this);
  }
  
  public void invalidateOptionsMenu()
  {
    e().f();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    e().a(paramConfiguration);
    if (this.c != null)
    {
      DisplayMetrics localDisplayMetrics = super.getResources().getDisplayMetrics();
      this.c.updateConfiguration(paramConfiguration, localDisplayMetrics);
    }
  }
  
  public void onContentChanged()
  {
    d();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    g localg = e();
    localg.i();
    localg.a(paramBundle);
    if ((localg.j()) && (this.b != 0)) {
      if (Build.VERSION.SDK_INT >= 23) {
        onApplyThemeResource(getTheme(), this.b, false);
      } else {
        setTheme(this.b);
      }
    }
    super.onCreate(paramBundle);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    e().g();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (a(paramInt, paramKeyEvent)) {
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public final boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (super.onMenuItemSelected(paramInt, paramMenuItem)) {
      return true;
    }
    a locala = b();
    if ((paramMenuItem.getItemId() == 16908332) && (locala != null) && ((locala.a() & 0x4) != 0)) {
      return c();
    }
    return false;
  }
  
  public boolean onMenuOpened(int paramInt, Menu paramMenu)
  {
    return super.onMenuOpened(paramInt, paramMenu);
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu)
  {
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    e().b(paramBundle);
  }
  
  protected void onPostResume()
  {
    super.onPostResume();
    e().e();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    e().c(paramBundle);
  }
  
  protected void onStart()
  {
    super.onStart();
    e().c();
  }
  
  protected void onStop()
  {
    super.onStop();
    e().d();
  }
  
  protected void onTitleChanged(CharSequence paramCharSequence, int paramInt)
  {
    super.onTitleChanged(paramCharSequence, paramInt);
    e().a(paramCharSequence);
  }
  
  public void openOptionsMenu()
  {
    a locala = b();
    if ((getWindow().hasFeature(0)) && ((locala == null) || (!locala.c()))) {
      super.openOptionsMenu();
    }
  }
  
  public void setContentView(int paramInt)
  {
    e().b(paramInt);
  }
  
  public void setContentView(View paramView)
  {
    e().a(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    e().a(paramView, paramLayoutParams);
  }
  
  public void setTheme(int paramInt)
  {
    super.setTheme(paramInt);
    this.b = paramInt;
  }
  
  public void supportInvalidateOptionsMenu()
  {
    e().f();
  }
}


/* Location:              ~/android/support/v7/app/e.class
 *
 * Reversed by:           J
 */