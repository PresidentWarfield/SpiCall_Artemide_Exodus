package android.support.v4.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;
import java.util.Arrays;

public class ShortcutInfoCompat
{
  private ComponentName mActivity;
  private Context mContext;
  private CharSequence mDisabledMessage;
  private IconCompat mIcon;
  private String mId;
  private Intent[] mIntents;
  private CharSequence mLabel;
  private CharSequence mLongLabel;
  
  Intent addToIntent(Intent paramIntent)
  {
    Object localObject = this.mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", localObject[(localObject.length - 1)]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
    localObject = this.mIcon;
    if (localObject != null) {
      ((IconCompat)localObject).addToShortcutIntent(paramIntent);
    }
    return paramIntent;
  }
  
  public ComponentName getActivity()
  {
    return this.mActivity;
  }
  
  public CharSequence getDisabledMessage()
  {
    return this.mDisabledMessage;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public Intent getIntent()
  {
    Intent[] arrayOfIntent = this.mIntents;
    return arrayOfIntent[(arrayOfIntent.length - 1)];
  }
  
  public Intent[] getIntents()
  {
    Intent[] arrayOfIntent = this.mIntents;
    return (Intent[])Arrays.copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel()
  {
    return this.mLongLabel;
  }
  
  public CharSequence getShortLabel()
  {
    return this.mLabel;
  }
  
  ShortcutInfo toShortcutInfo()
  {
    ShortcutInfo.Builder localBuilder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
    Object localObject = this.mIcon;
    if (localObject != null) {
      localBuilder.setIcon(((IconCompat)localObject).toIcon());
    }
    if (!TextUtils.isEmpty(this.mLongLabel)) {
      localBuilder.setLongLabel(this.mLongLabel);
    }
    if (!TextUtils.isEmpty(this.mDisabledMessage)) {
      localBuilder.setDisabledMessage(this.mDisabledMessage);
    }
    localObject = this.mActivity;
    if (localObject != null) {
      localBuilder.setActivity((ComponentName)localObject);
    }
    return localBuilder.build();
  }
  
  public static class Builder
  {
    private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat(null);
    
    public Builder(Context paramContext, String paramString)
    {
      ShortcutInfoCompat.access$102(this.mInfo, paramContext);
      ShortcutInfoCompat.access$202(this.mInfo, paramString);
    }
    
    public ShortcutInfoCompat build()
    {
      if (!TextUtils.isEmpty(this.mInfo.mLabel))
      {
        if ((this.mInfo.mIntents != null) && (this.mInfo.mIntents.length != 0)) {
          return this.mInfo;
        }
        throw new IllegalArgumentException("Shortcut much have an intent");
      }
      throw new IllegalArgumentException("Shortcut much have a non-empty label");
    }
    
    public Builder setActivity(ComponentName paramComponentName)
    {
      ShortcutInfoCompat.access$802(this.mInfo, paramComponentName);
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence paramCharSequence)
    {
      ShortcutInfoCompat.access$502(this.mInfo, paramCharSequence);
      return this;
    }
    
    public Builder setIcon(int paramInt)
    {
      return setIcon(IconCompat.createWithResource(this.mInfo.mContext, paramInt));
    }
    
    public Builder setIcon(Bitmap paramBitmap)
    {
      return setIcon(IconCompat.createWithBitmap(paramBitmap));
    }
    
    public Builder setIcon(IconCompat paramIconCompat)
    {
      ShortcutInfoCompat.access$702(this.mInfo, paramIconCompat);
      return this;
    }
    
    public Builder setIntent(Intent paramIntent)
    {
      return setIntents(new Intent[] { paramIntent });
    }
    
    public Builder setIntents(Intent[] paramArrayOfIntent)
    {
      ShortcutInfoCompat.access$602(this.mInfo, paramArrayOfIntent);
      return this;
    }
    
    public Builder setLongLabel(CharSequence paramCharSequence)
    {
      ShortcutInfoCompat.access$402(this.mInfo, paramCharSequence);
      return this;
    }
    
    public Builder setShortLabel(CharSequence paramCharSequence)
    {
      ShortcutInfoCompat.access$302(this.mInfo, paramCharSequence);
      return this;
    }
  }
}


/* Location:              ~/android/support/v4/content/pm/ShortcutInfoCompat.class
 *
 * Reversed by:           J
 */