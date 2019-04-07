package android.support.v4.media.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Notification.DecoratedMediaCustomViewStyle;
import android.app.Notification.MediaStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.media.session.MediaSession.Token;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.c.a.a;
import android.support.c.a.b;
import android.support.c.a.c;
import android.support.c.a.d;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.widget.RemoteViews;
import java.util.ArrayList;

public class NotificationCompat
{
  public static class DecoratedMediaCustomViewStyle
    extends NotificationCompat.MediaStyle
  {
    private void setBackgroundColor(RemoteViews paramRemoteViews)
    {
      int i;
      if (this.mBuilder.getColor() != 0) {
        i = this.mBuilder.getColor();
      } else {
        i = this.mBuilder.mContext.getResources().getColor(a.a.notification_material_background_media_default_color);
      }
      paramRemoteViews.setInt(a.b.status_bar_latest_event_content, "setBackgroundColor", i);
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        paramNotificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.DecoratedMediaCustomViewStyle()));
      } else {
        super.apply(paramNotificationBuilderWithBuilderAccessor);
      }
    }
    
    int getBigContentViewLayoutResource(int paramInt)
    {
      if (paramInt <= 3) {
        paramInt = a.d.notification_template_big_media_narrow_custom;
      } else {
        paramInt = a.d.notification_template_big_media_custom;
      }
      return paramInt;
    }
    
    int getContentViewLayoutResource()
    {
      int i;
      if (this.mBuilder.getContentView() != null) {
        i = a.d.notification_template_media_custom;
      } else {
        i = super.getContentViewLayoutResource();
      }
      return i;
    }
    
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      if (this.mBuilder.getBigContentView() != null) {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getBigContentView();
      } else {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
      }
      if (paramNotificationBuilderWithBuilderAccessor == null) {
        return null;
      }
      RemoteViews localRemoteViews = generateBigContentView();
      buildIntoRemoteViews(localRemoteViews, paramNotificationBuilderWithBuilderAccessor);
      if (Build.VERSION.SDK_INT >= 21) {
        setBackgroundColor(localRemoteViews);
      }
      return localRemoteViews;
    }
    
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
      int i = 1;
      int j;
      if (paramNotificationBuilderWithBuilderAccessor != null) {
        j = 1;
      } else {
        j = 0;
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        int k = i;
        if (j == 0) {
          if (this.mBuilder.getBigContentView() != null) {
            k = i;
          } else {
            k = 0;
          }
        }
        if (k != 0)
        {
          paramNotificationBuilderWithBuilderAccessor = generateContentView();
          if (j != 0) {
            buildIntoRemoteViews(paramNotificationBuilderWithBuilderAccessor, this.mBuilder.getContentView());
          }
          setBackgroundColor(paramNotificationBuilderWithBuilderAccessor);
          return paramNotificationBuilderWithBuilderAccessor;
        }
      }
      else
      {
        paramNotificationBuilderWithBuilderAccessor = generateContentView();
        if (j != 0)
        {
          buildIntoRemoteViews(paramNotificationBuilderWithBuilderAccessor, this.mBuilder.getContentView());
          return paramNotificationBuilderWithBuilderAccessor;
        }
      }
      return null;
    }
    
    public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      if (this.mBuilder.getHeadsUpContentView() != null) {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getHeadsUpContentView();
      } else {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
      }
      if (paramNotificationBuilderWithBuilderAccessor == null) {
        return null;
      }
      RemoteViews localRemoteViews = generateBigContentView();
      buildIntoRemoteViews(localRemoteViews, paramNotificationBuilderWithBuilderAccessor);
      if (Build.VERSION.SDK_INT >= 21) {
        setBackgroundColor(localRemoteViews);
      }
      return localRemoteViews;
    }
  }
  
  public static class MediaStyle
    extends NotificationCompat.Style
  {
    private static final int MAX_MEDIA_BUTTONS = 5;
    private static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
    int[] mActionsToShowInCompact = null;
    PendingIntent mCancelButtonIntent;
    boolean mShowCancelButton;
    MediaSessionCompat.Token mToken;
    
    public MediaStyle() {}
    
    public MediaStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }
    
    private RemoteViews generateMediaActionButton(NotificationCompat.Action paramAction)
    {
      int i;
      if (paramAction.getActionIntent() == null) {
        i = 1;
      } else {
        i = 0;
      }
      RemoteViews localRemoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), a.d.notification_media_action);
      localRemoteViews.setImageViewResource(a.b.action0, paramAction.getIcon());
      if (i == 0) {
        localRemoteViews.setOnClickPendingIntent(a.b.action0, paramAction.getActionIntent());
      }
      if (Build.VERSION.SDK_INT >= 15) {
        localRemoteViews.setContentDescription(a.b.action0, paramAction.getTitle());
      }
      return localRemoteViews;
    }
    
    public static MediaSessionCompat.Token getMediaSession(Notification paramNotification)
    {
      paramNotification = android.support.v4.app.NotificationCompat.getExtras(paramNotification);
      if (paramNotification != null) {
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramNotification = paramNotification.getParcelable("android.mediaSession");
          if (paramNotification != null) {
            return MediaSessionCompat.Token.fromToken(paramNotification);
          }
        }
        else
        {
          Object localObject = BundleCompat.getBinder(paramNotification, "android.mediaSession");
          if (localObject != null)
          {
            paramNotification = Parcel.obtain();
            paramNotification.writeStrongBinder((IBinder)localObject);
            paramNotification.setDataPosition(0);
            localObject = (MediaSessionCompat.Token)MediaSessionCompat.Token.CREATOR.createFromParcel(paramNotification);
            paramNotification.recycle();
            return (MediaSessionCompat.Token)localObject;
          }
        }
      }
      return null;
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramNotificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.MediaStyle()));
      } else if (this.mShowCancelButton) {
        paramNotificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
      }
    }
    
    Notification.MediaStyle fillInMediaStyle(Notification.MediaStyle paramMediaStyle)
    {
      Object localObject = this.mActionsToShowInCompact;
      if (localObject != null) {
        paramMediaStyle.setShowActionsInCompactView((int[])localObject);
      }
      localObject = this.mToken;
      if (localObject != null) {
        paramMediaStyle.setMediaSession((MediaSession.Token)((MediaSessionCompat.Token)localObject).getToken());
      }
      return paramMediaStyle;
    }
    
    RemoteViews generateBigContentView()
    {
      int i = Math.min(this.mBuilder.mActions.size(), 5);
      RemoteViews localRemoteViews1 = applyStandardTemplate(false, getBigContentViewLayoutResource(i), false);
      localRemoteViews1.removeAllViews(a.b.media_actions);
      if (i > 0) {
        for (int j = 0; j < i; j++)
        {
          RemoteViews localRemoteViews2 = generateMediaActionButton((NotificationCompat.Action)this.mBuilder.mActions.get(j));
          localRemoteViews1.addView(a.b.media_actions, localRemoteViews2);
        }
      }
      if (this.mShowCancelButton)
      {
        localRemoteViews1.setViewVisibility(a.b.cancel_action, 0);
        localRemoteViews1.setInt(a.b.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(a.c.cancel_button_image_alpha));
        localRemoteViews1.setOnClickPendingIntent(a.b.cancel_action, this.mCancelButtonIntent);
      }
      else
      {
        localRemoteViews1.setViewVisibility(a.b.cancel_action, 8);
      }
      return localRemoteViews1;
    }
    
    RemoteViews generateContentView()
    {
      RemoteViews localRemoteViews = applyStandardTemplate(false, getContentViewLayoutResource(), true);
      int i = this.mBuilder.mActions.size();
      Object localObject = this.mActionsToShowInCompact;
      int j;
      if (localObject == null) {
        j = 0;
      } else {
        j = Math.min(localObject.length, 3);
      }
      localRemoteViews.removeAllViews(a.b.media_actions);
      if (j > 0)
      {
        int k = 0;
        while (k < j) {
          if (k < i)
          {
            localObject = generateMediaActionButton((NotificationCompat.Action)this.mBuilder.mActions.get(this.mActionsToShowInCompact[k]));
            localRemoteViews.addView(a.b.media_actions, (RemoteViews)localObject);
            k++;
          }
          else
          {
            throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", new Object[] { Integer.valueOf(k), Integer.valueOf(i - 1) }));
          }
        }
      }
      if (this.mShowCancelButton)
      {
        localRemoteViews.setViewVisibility(a.b.end_padder, 8);
        localRemoteViews.setViewVisibility(a.b.cancel_action, 0);
        localRemoteViews.setOnClickPendingIntent(a.b.cancel_action, this.mCancelButtonIntent);
        localRemoteViews.setInt(a.b.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(a.c.cancel_button_image_alpha));
      }
      else
      {
        localRemoteViews.setViewVisibility(a.b.end_padder, 0);
        localRemoteViews.setViewVisibility(a.b.cancel_action, 8);
      }
      return localRemoteViews;
    }
    
    int getBigContentViewLayoutResource(int paramInt)
    {
      if (paramInt <= 3) {
        paramInt = a.d.notification_template_big_media_narrow;
      } else {
        paramInt = a.d.notification_template_big_media;
      }
      return paramInt;
    }
    
    int getContentViewLayoutResource()
    {
      return a.d.notification_template_media;
    }
    
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        return null;
      }
      return generateBigContentView();
    }
    
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        return null;
      }
      return generateContentView();
    }
    
    public MediaStyle setCancelButtonIntent(PendingIntent paramPendingIntent)
    {
      this.mCancelButtonIntent = paramPendingIntent;
      return this;
    }
    
    public MediaStyle setMediaSession(MediaSessionCompat.Token paramToken)
    {
      this.mToken = paramToken;
      return this;
    }
    
    public MediaStyle setShowActionsInCompactView(int... paramVarArgs)
    {
      this.mActionsToShowInCompact = paramVarArgs;
      return this;
    }
    
    public MediaStyle setShowCancelButton(boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT < 21) {
        this.mShowCancelButton = paramBoolean;
      }
      return this;
    }
  }
}


/* Location:              ~/android/support/v4/media/app/NotificationCompat.class
 *
 * Reversed by:           J
 */