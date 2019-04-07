package android.support.v4.widget;

public abstract interface AutoSizeableTextView
{
  public abstract int getAutoSizeMaxTextSize();
  
  public abstract int getAutoSizeMinTextSize();
  
  public abstract int getAutoSizeStepGranularity();
  
  public abstract int[] getAutoSizeTextAvailableSizes();
  
  public abstract int getAutoSizeTextType();
  
  public abstract void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfInt, int paramInt);
  
  public abstract void setAutoSizeTextTypeWithDefaults(int paramInt);
}


/* Location:              ~/android/support/v4/widget/AutoSizeableTextView.class
 *
 * Reversed by:           J
 */