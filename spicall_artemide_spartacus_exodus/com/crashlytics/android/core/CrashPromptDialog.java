package com.crashlytics.android.core;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import android.widget.TextView;
import io.fabric.sdk.android.services.e.o;
import java.util.concurrent.CountDownLatch;

class CrashPromptDialog
{
  private final AlertDialog.Builder dialog;
  private final OptInLatch latch;
  
  private CrashPromptDialog(AlertDialog.Builder paramBuilder, OptInLatch paramOptInLatch)
  {
    this.latch = paramOptInLatch;
    this.dialog = paramBuilder;
  }
  
  public static CrashPromptDialog create(Activity paramActivity, o paramo, AlwaysSendCallback paramAlwaysSendCallback)
  {
    final OptInLatch localOptInLatch = new OptInLatch(null);
    DialogStringResolver localDialogStringResolver = new DialogStringResolver(paramActivity, paramo);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity);
    paramActivity = createDialogView(paramActivity, localDialogStringResolver.getMessage());
    DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        this.val$latch.setOptIn(true);
        paramAnonymousDialogInterface.dismiss();
      }
    };
    localBuilder.setView(paramActivity).setTitle(localDialogStringResolver.getTitle()).setCancelable(false).setNeutralButton(localDialogStringResolver.getSendButtonTitle(), local1);
    if (paramo.d)
    {
      paramActivity = new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          this.val$latch.setOptIn(false);
          paramAnonymousDialogInterface.dismiss();
        }
      };
      localBuilder.setNegativeButton(localDialogStringResolver.getCancelButtonTitle(), paramActivity);
    }
    if (paramo.f)
    {
      paramActivity = new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          this.val$alwaysSendCallback.sendUserReportsWithoutPrompting(true);
          localOptInLatch.setOptIn(true);
          paramAnonymousDialogInterface.dismiss();
        }
      };
      localBuilder.setPositiveButton(localDialogStringResolver.getAlwaysSendButtonTitle(), paramActivity);
    }
    return new CrashPromptDialog(localBuilder, localOptInLatch);
  }
  
  private static ScrollView createDialogView(Activity paramActivity, String paramString)
  {
    float f = paramActivity.getResources().getDisplayMetrics().density;
    int i = dipsToPixels(f, 5);
    TextView localTextView = new TextView(paramActivity);
    localTextView.setAutoLinkMask(15);
    localTextView.setText(paramString);
    localTextView.setTextAppearance(paramActivity, 16973892);
    localTextView.setPadding(i, i, i, i);
    localTextView.setFocusable(false);
    paramActivity = new ScrollView(paramActivity);
    paramActivity.setPadding(dipsToPixels(f, 14), dipsToPixels(f, 2), dipsToPixels(f, 10), dipsToPixels(f, 12));
    paramActivity.addView(localTextView);
    return paramActivity;
  }
  
  private static int dipsToPixels(float paramFloat, int paramInt)
  {
    return (int)(paramFloat * paramInt);
  }
  
  public void await()
  {
    this.latch.await();
  }
  
  public boolean getOptIn()
  {
    return this.latch.getOptIn();
  }
  
  public void show()
  {
    this.dialog.show();
  }
  
  static abstract interface AlwaysSendCallback
  {
    public abstract void sendUserReportsWithoutPrompting(boolean paramBoolean);
  }
  
  private static class OptInLatch
  {
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean send = false;
    
    void await()
    {
      try
      {
        this.latch.await();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
    
    boolean getOptIn()
    {
      return this.send;
    }
    
    void setOptIn(boolean paramBoolean)
    {
      this.send = paramBoolean;
      this.latch.countDown();
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashPromptDialog.class
 *
 * Reversed by:           J
 */