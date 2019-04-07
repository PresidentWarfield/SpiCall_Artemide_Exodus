package android.support.v4.app;

import android.app.RemoteInput;
import android.app.RemoteInput.Builder;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class RemoteInputCompatApi20
{
  private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
  
  public static void addDataResultToIntent(RemoteInputCompatBase.RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap)
  {
    Object localObject1 = getClipDataIntentFromIntent(paramIntent);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new Intent();
    }
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      String str = (String)paramMap.getKey();
      Uri localUri = (Uri)paramMap.getValue();
      if (str != null)
      {
        localObject1 = ((Intent)localObject2).getBundleExtra(getExtraResultsKeyForData(str));
        paramMap = (Map<String, Uri>)localObject1;
        if (localObject1 == null) {
          paramMap = new Bundle();
        }
        paramMap.putString(paramRemoteInput.getResultKey(), localUri.toString());
        ((Intent)localObject2).putExtra(getExtraResultsKeyForData(str), paramMap);
      }
    }
    paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", (Intent)localObject2));
  }
  
  static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle)
  {
    Object localObject = getResultsFromIntent(paramIntent);
    if (localObject != null)
    {
      ((Bundle)localObject).putAll(paramBundle);
      paramBundle = (Bundle)localObject;
    }
    int i = paramArrayOfRemoteInput.length;
    for (int j = 0; j < i; j++)
    {
      RemoteInputCompatBase.RemoteInput localRemoteInput = paramArrayOfRemoteInput[j];
      localObject = getDataResultsFromIntent(paramIntent, localRemoteInput.getResultKey());
      RemoteInput.addResultsToIntent(fromCompat(new RemoteInputCompatBase.RemoteInput[] { localRemoteInput }), paramIntent, paramBundle);
      if (localObject != null) {
        addDataResultToIntent(localRemoteInput, paramIntent, (Map)localObject);
      }
    }
  }
  
  static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    RemoteInput[] arrayOfRemoteInput = new RemoteInput[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++)
    {
      RemoteInputCompatBase.RemoteInput localRemoteInput = paramArrayOfRemoteInput[i];
      arrayOfRemoteInput[i] = new RemoteInput.Builder(localRemoteInput.getResultKey()).setLabel(localRemoteInput.getLabel()).setChoices(localRemoteInput.getChoices()).setAllowFreeFormInput(localRemoteInput.getAllowFreeFormInput()).addExtras(localRemoteInput.getExtras()).build();
    }
    return arrayOfRemoteInput;
  }
  
  private static Intent getClipDataIntentFromIntent(Intent paramIntent)
  {
    paramIntent = paramIntent.getClipData();
    if (paramIntent == null) {
      return null;
    }
    ClipDescription localClipDescription = paramIntent.getDescription();
    if (!localClipDescription.hasMimeType("text/vnd.android.intent")) {
      return null;
    }
    if (!localClipDescription.getLabel().equals("android.remoteinput.results")) {
      return null;
    }
    return paramIntent.getItemAt(0).getIntent();
  }
  
  static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString)
  {
    Intent localIntent = getClipDataIntentFromIntent(paramIntent);
    paramIntent = null;
    if (localIntent == null) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localIntent.getExtras().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      if (str1.startsWith("android.remoteinput.dataTypeResultsData"))
      {
        String str2 = str1.substring(39);
        if ((str2 != null) && (!str2.isEmpty()))
        {
          str1 = localIntent.getBundleExtra(str1).getString(paramString);
          if ((str1 != null) && (!str1.isEmpty())) {
            localHashMap.put(str2, Uri.parse(str1));
          }
        }
      }
    }
    if (!localHashMap.isEmpty()) {
      paramIntent = localHashMap;
    }
    return paramIntent;
  }
  
  private static String getExtraResultsKeyForData(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("android.remoteinput.dataTypeResultsData");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  static Bundle getResultsFromIntent(Intent paramIntent)
  {
    return RemoteInput.getResultsFromIntent(paramIntent);
  }
  
  static RemoteInputCompatBase.RemoteInput[] toCompat(RemoteInput[] paramArrayOfRemoteInput, RemoteInputCompatBase.RemoteInput.Factory paramFactory)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    RemoteInputCompatBase.RemoteInput[] arrayOfRemoteInput = paramFactory.newArray(paramArrayOfRemoteInput.length);
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++)
    {
      RemoteInput localRemoteInput = paramArrayOfRemoteInput[i];
      arrayOfRemoteInput[i] = paramFactory.build(localRemoteInput.getResultKey(), localRemoteInput.getLabel(), localRemoteInput.getChoices(), localRemoteInput.getAllowFreeFormInput(), localRemoteInput.getExtras(), null);
    }
    return arrayOfRemoteInput;
  }
}


/* Location:              ~/android/support/v4/app/RemoteInputCompatApi20.class
 *
 * Reversed by:           J
 */