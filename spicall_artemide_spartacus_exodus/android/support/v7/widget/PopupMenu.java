package android.support.v7.widget;

import android.content.Context;
import android.support.v7.a.a.a;
import android.support.v7.view.g;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.n;
import android.support.v7.view.menu.s;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow.OnDismissListener;

public class PopupMenu
{
  private final View mAnchor;
  private final Context mContext;
  private View.OnTouchListener mDragListener;
  private final h mMenu;
  OnMenuItemClickListener mMenuItemClickListener;
  OnDismissListener mOnDismissListener;
  final n mPopup;
  
  public PopupMenu(Context paramContext, View paramView)
  {
    this(paramContext, paramView, 0);
  }
  
  public PopupMenu(Context paramContext, View paramView, int paramInt)
  {
    this(paramContext, paramView, paramInt, a.a.popupMenuStyle, 0);
  }
  
  public PopupMenu(Context paramContext, View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mContext = paramContext;
    this.mAnchor = paramView;
    this.mMenu = new h(paramContext);
    this.mMenu.a(new h.a()
    {
      public void a(h paramAnonymoush) {}
      
      public boolean a(h paramAnonymoush, MenuItem paramAnonymousMenuItem)
      {
        if (PopupMenu.this.mMenuItemClickListener != null) {
          return PopupMenu.this.mMenuItemClickListener.onMenuItemClick(paramAnonymousMenuItem);
        }
        return false;
      }
    });
    this.mPopup = new n(paramContext, this.mMenu, paramView, false, paramInt2, paramInt3);
    this.mPopup.a(paramInt1);
    this.mPopup.a(new PopupWindow.OnDismissListener()
    {
      public void onDismiss()
      {
        if (PopupMenu.this.mOnDismissListener != null) {
          PopupMenu.this.mOnDismissListener.onDismiss(PopupMenu.this);
        }
      }
    });
  }
  
  public void dismiss()
  {
    this.mPopup.e();
  }
  
  public View.OnTouchListener getDragToOpenListener()
  {
    if (this.mDragListener == null) {
      this.mDragListener = new ForwardingListener(this.mAnchor)
      {
        public s getPopup()
        {
          return PopupMenu.this.mPopup.c();
        }
        
        protected boolean onForwardingStarted()
        {
          PopupMenu.this.show();
          return true;
        }
        
        protected boolean onForwardingStopped()
        {
          PopupMenu.this.dismiss();
          return true;
        }
      };
    }
    return this.mDragListener;
  }
  
  public int getGravity()
  {
    return this.mPopup.a();
  }
  
  public Menu getMenu()
  {
    return this.mMenu;
  }
  
  public MenuInflater getMenuInflater()
  {
    return new g(this.mContext);
  }
  
  public void inflate(int paramInt)
  {
    getMenuInflater().inflate(paramInt, this.mMenu);
  }
  
  public void setGravity(int paramInt)
  {
    this.mPopup.a(paramInt);
  }
  
  public void setOnDismissListener(OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void show()
  {
    this.mPopup.b();
  }
  
  public static abstract interface OnDismissListener
  {
    public abstract void onDismiss(PopupMenu paramPopupMenu);
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
}


/* Location:              ~/android/support/v7/widget/PopupMenu.class
 *
 * Reversed by:           J
 */