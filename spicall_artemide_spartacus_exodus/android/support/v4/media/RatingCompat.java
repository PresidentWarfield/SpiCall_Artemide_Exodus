package android.support.v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat
  implements Parcelable
{
  public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator()
  {
    public RatingCompat createFromParcel(Parcel paramAnonymousParcel)
    {
      return new RatingCompat(paramAnonymousParcel.readInt(), paramAnonymousParcel.readFloat());
    }
    
    public RatingCompat[] newArray(int paramAnonymousInt)
    {
      return new RatingCompat[paramAnonymousInt];
    }
  };
  public static final int RATING_3_STARS = 3;
  public static final int RATING_4_STARS = 4;
  public static final int RATING_5_STARS = 5;
  public static final int RATING_HEART = 1;
  public static final int RATING_NONE = 0;
  private static final float RATING_NOT_RATED = -1.0F;
  public static final int RATING_PERCENTAGE = 6;
  public static final int RATING_THUMB_UP_DOWN = 2;
  private static final String TAG = "Rating";
  private Object mRatingObj;
  private final int mRatingStyle;
  private final float mRatingValue;
  
  RatingCompat(int paramInt, float paramFloat)
  {
    this.mRatingStyle = paramInt;
    this.mRatingValue = paramFloat;
  }
  
  public static RatingCompat fromRating(Object paramObject)
  {
    if ((paramObject != null) && (Build.VERSION.SDK_INT >= 19))
    {
      int i = RatingCompatKitkat.getRatingStyle(paramObject);
      RatingCompat localRatingCompat;
      if (RatingCompatKitkat.isRated(paramObject)) {
        switch (i)
        {
        default: 
          return null;
        case 6: 
          localRatingCompat = newPercentageRating(RatingCompatKitkat.getPercentRating(paramObject));
          break;
        case 3: 
        case 4: 
        case 5: 
          localRatingCompat = newStarRating(i, RatingCompatKitkat.getStarRating(paramObject));
          break;
        case 2: 
          localRatingCompat = newThumbRating(RatingCompatKitkat.isThumbUp(paramObject));
          break;
        case 1: 
          localRatingCompat = newHeartRating(RatingCompatKitkat.hasHeart(paramObject));
          break;
        }
      } else {
        localRatingCompat = newUnratedRating(i);
      }
      localRatingCompat.mRatingObj = paramObject;
      return localRatingCompat;
    }
    return null;
  }
  
  public static RatingCompat newHeartRating(boolean paramBoolean)
  {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    return new RatingCompat(1, f);
  }
  
  public static RatingCompat newPercentageRating(float paramFloat)
  {
    if ((paramFloat >= 0.0F) && (paramFloat <= 100.0F)) {
      return new RatingCompat(6, paramFloat);
    }
    Log.e("Rating", "Invalid percentage-based rating value");
    return null;
  }
  
  public static RatingCompat newStarRating(int paramInt, float paramFloat)
  {
    float f;
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid rating style (");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(") for a star rating");
      Log.e("Rating", localStringBuilder.toString());
      return null;
    case 5: 
      f = 5.0F;
      break;
    case 4: 
      f = 4.0F;
      break;
    case 3: 
      f = 3.0F;
    }
    if ((paramFloat >= 0.0F) && (paramFloat <= f)) {
      return new RatingCompat(paramInt, paramFloat);
    }
    Log.e("Rating", "Trying to set out of range star-based rating");
    return null;
  }
  
  public static RatingCompat newThumbRating(boolean paramBoolean)
  {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    return new RatingCompat(2, f);
  }
  
  public static RatingCompat newUnratedRating(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    return new RatingCompat(paramInt, -1.0F);
  }
  
  public int describeContents()
  {
    return this.mRatingStyle;
  }
  
  public float getPercentRating()
  {
    if ((this.mRatingStyle == 6) && (isRated())) {
      return this.mRatingValue;
    }
    return -1.0F;
  }
  
  public Object getRating()
  {
    if ((this.mRatingObj == null) && (Build.VERSION.SDK_INT >= 19)) {
      if (isRated())
      {
        int i = this.mRatingStyle;
        switch (i)
        {
        default: 
          return null;
        case 6: 
          this.mRatingObj = RatingCompatKitkat.newPercentageRating(getPercentRating());
          break;
        case 3: 
        case 4: 
        case 5: 
          this.mRatingObj = RatingCompatKitkat.newStarRating(i, getStarRating());
          break;
        case 2: 
          this.mRatingObj = RatingCompatKitkat.newThumbRating(isThumbUp());
          break;
        case 1: 
          this.mRatingObj = RatingCompatKitkat.newHeartRating(hasHeart());
          break;
        }
      }
      else
      {
        this.mRatingObj = RatingCompatKitkat.newUnratedRating(this.mRatingStyle);
      }
    }
    return this.mRatingObj;
  }
  
  public int getRatingStyle()
  {
    return this.mRatingStyle;
  }
  
  public float getStarRating()
  {
    switch (this.mRatingStyle)
    {
    default: 
      break;
    case 3: 
    case 4: 
    case 5: 
      if (isRated()) {
        return this.mRatingValue;
      }
      break;
    }
    return -1.0F;
  }
  
  public boolean hasHeart()
  {
    int i = this.mRatingStyle;
    boolean bool = false;
    if (i != 1) {
      return false;
    }
    if (this.mRatingValue == 1.0F) {
      bool = true;
    }
    return bool;
  }
  
  public boolean isRated()
  {
    boolean bool;
    if (this.mRatingValue >= 0.0F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isThumbUp()
  {
    int i = this.mRatingStyle;
    boolean bool = false;
    if (i != 2) {
      return false;
    }
    if (this.mRatingValue == 1.0F) {
      bool = true;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Rating:style=");
    localStringBuilder.append(this.mRatingStyle);
    localStringBuilder.append(" rating=");
    float f = this.mRatingValue;
    String str;
    if (f < 0.0F) {
      str = "unrated";
    } else {
      str = String.valueOf(f);
    }
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mRatingStyle);
    paramParcel.writeFloat(this.mRatingValue);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface StarStyle {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Style {}
}


/* Location:              ~/android/support/v4/media/RatingCompat.class
 *
 * Reversed by:           J
 */