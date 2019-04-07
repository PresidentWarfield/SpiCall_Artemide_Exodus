package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.support.v7.a.a.a;
import android.support.v7.view.b;
import android.support.v7.view.b.a;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class n
  extends Dialog
  implements f
{
  private g a;
  
  public n(Context paramContext, int paramInt)
  {
    super(paramContext, a(paramContext, paramInt));
    a().a(null);
    a().j();
  }
  
  private static int a(Context paramContext, int paramInt)
  {
    int i = paramInt;
    if (paramInt == 0)
    {
      TypedValue localTypedValue = new TypedValue();
      paramContext.getTheme().resolveAttribute(a.a.dialogTheme, localTypedValue, true);
      i = localTypedValue.resourceId;
    }
    return i;
  }
  
  public g a()
  {
    if (this.a == null) {
      this.a = g.a(this, this);
    }
    return this.a;
  }
  
  public b a(b.a parama)
  {
    return null;
  }
  
  public void a(b paramb) {}
  
  public boolean a(int paramInt)
  {
    return a().c(paramInt);
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    a().b(paramView, paramLayoutParams);
  }
  
  public void b(b paramb) {}
  
  public <T extends View> T findViewById(int paramInt)
  {
    return a().a(paramInt);
  }
  
  public void invalidateOptionsMenu()
  {
    a().f();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    a().i();
    super.onCreate(paramBundle);
    a().a(paramBundle);
  }
  
  protected void onStop()
  {
    super.onStop();
    a().d();
  }
  
  public void setContentView(int paramInt)
  {
    a().b(paramInt);
  }
  
  public void setContentView(View paramView)
  {
    a().a(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    a().a(paramView, paramLayoutParams);
  }
  
  public void setTitle(int paramInt)
  {
    super.setTitle(paramInt);
    a().a(getContext().getString(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    super.setTitle(paramCharSequence);
    a().a(paramCharSequence);
  }
}


/* Location:              ~/android/support/v7/app/n.class
 *
 * Reversed by:           J
 */