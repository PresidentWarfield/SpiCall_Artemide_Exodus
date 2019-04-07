package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

public class DividerItemDecoration
  extends RecyclerView.ItemDecoration
{
  private static final int[] ATTRS = { 16843284 };
  public static final int HORIZONTAL = 0;
  private static final String TAG = "DividerItem";
  public static final int VERTICAL = 1;
  private final Rect mBounds = new Rect();
  private Drawable mDivider;
  private int mOrientation;
  
  public DividerItemDecoration(Context paramContext, int paramInt)
  {
    paramContext = paramContext.obtainStyledAttributes(ATTRS);
    this.mDivider = paramContext.getDrawable(0);
    if (this.mDivider == null) {
      Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
    }
    paramContext.recycle();
    setOrientation(paramInt);
  }
  
  private void drawHorizontal(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    paramCanvas.save();
    boolean bool = paramRecyclerView.getClipToPadding();
    int i = 0;
    int j;
    int k;
    if (bool)
    {
      j = paramRecyclerView.getPaddingTop();
      k = paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom();
      paramCanvas.clipRect(paramRecyclerView.getPaddingLeft(), j, paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight(), k);
    }
    else
    {
      k = paramRecyclerView.getHeight();
      j = 0;
    }
    int m = paramRecyclerView.getChildCount();
    while (i < m)
    {
      View localView = paramRecyclerView.getChildAt(i);
      paramRecyclerView.getLayoutManager().getDecoratedBoundsWithMargins(localView, this.mBounds);
      int n = this.mBounds.right + Math.round(localView.getTranslationX());
      int i1 = this.mDivider.getIntrinsicWidth();
      this.mDivider.setBounds(n - i1, j, n, k);
      this.mDivider.draw(paramCanvas);
      i++;
    }
    paramCanvas.restore();
  }
  
  private void drawVertical(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    paramCanvas.save();
    boolean bool = paramRecyclerView.getClipToPadding();
    int i = 0;
    int j;
    int k;
    if (bool)
    {
      j = paramRecyclerView.getPaddingLeft();
      k = paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight();
      paramCanvas.clipRect(j, paramRecyclerView.getPaddingTop(), k, paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom());
    }
    else
    {
      k = paramRecyclerView.getWidth();
      j = 0;
    }
    int m = paramRecyclerView.getChildCount();
    while (i < m)
    {
      View localView = paramRecyclerView.getChildAt(i);
      paramRecyclerView.getDecoratedBoundsWithMargins(localView, this.mBounds);
      int n = this.mBounds.bottom + Math.round(localView.getTranslationY());
      int i1 = this.mDivider.getIntrinsicHeight();
      this.mDivider.setBounds(j, n - i1, k, n);
      this.mDivider.draw(paramCanvas);
      i++;
    }
    paramCanvas.restore();
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    paramView = this.mDivider;
    if (paramView == null)
    {
      paramRect.set(0, 0, 0, 0);
      return;
    }
    if (this.mOrientation == 1) {
      paramRect.set(0, 0, 0, paramView.getIntrinsicHeight());
    } else {
      paramRect.set(0, 0, paramView.getIntrinsicWidth(), 0);
    }
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    if ((paramRecyclerView.getLayoutManager() != null) && (this.mDivider != null))
    {
      if (this.mOrientation == 1) {
        drawVertical(paramCanvas, paramRecyclerView);
      } else {
        drawHorizontal(paramCanvas, paramRecyclerView);
      }
      return;
    }
  }
  
  public void setDrawable(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      this.mDivider = paramDrawable;
      return;
    }
    throw new IllegalArgumentException("Drawable cannot be null.");
  }
  
  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }
    this.mOrientation = paramInt;
  }
}


/* Location:              ~/android/support/v7/widget/DividerItemDecoration.class
 *
 * Reversed by:           J
 */