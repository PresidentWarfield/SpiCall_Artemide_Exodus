package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class a
{
  static class a
    extends a.b
  {
    private float a(RecyclerView paramRecyclerView, View paramView)
    {
      int i = paramRecyclerView.getChildCount();
      float f1 = 0.0F;
      int j = 0;
      while (j < i)
      {
        View localView = paramRecyclerView.getChildAt(j);
        float f2;
        if (localView == paramView)
        {
          f2 = f1;
        }
        else
        {
          float f3 = ViewCompat.getElevation(localView);
          f2 = f1;
          if (f3 > f1) {
            f2 = f3;
          }
        }
        j++;
        f1 = f2;
      }
      return f1;
    }
    
    public void clearView(View paramView)
    {
      Object localObject = paramView.getTag(android.support.v7.e.a.b.item_touch_helper_previous_elevation);
      if ((localObject != null) && ((localObject instanceof Float))) {
        ViewCompat.setElevation(paramView, ((Float)localObject).floatValue());
      }
      paramView.setTag(android.support.v7.e.a.b.item_touch_helper_previous_elevation, null);
      super.clearView(paramView);
    }
    
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, View paramView, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      if ((paramBoolean) && (paramView.getTag(android.support.v7.e.a.b.item_touch_helper_previous_elevation) == null))
      {
        float f = ViewCompat.getElevation(paramView);
        ViewCompat.setElevation(paramView, a(paramRecyclerView, paramView) + 1.0F);
        paramView.setTag(android.support.v7.e.a.b.item_touch_helper_previous_elevation, Float.valueOf(f));
      }
      super.onDraw(paramCanvas, paramRecyclerView, paramView, paramFloat1, paramFloat2, paramInt, paramBoolean);
    }
  }
  
  static class b
    implements ItemTouchUIUtil
  {
    public void clearView(View paramView)
    {
      paramView.setTranslationX(0.0F);
      paramView.setTranslationY(0.0F);
    }
    
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, View paramView, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      paramView.setTranslationX(paramFloat1);
      paramView.setTranslationY(paramFloat2);
    }
    
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, View paramView, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean) {}
    
    public void onSelected(View paramView) {}
  }
}


/* Location:              ~/android/support/v7/widget/helper/a.class
 *
 * Reversed by:           J
 */