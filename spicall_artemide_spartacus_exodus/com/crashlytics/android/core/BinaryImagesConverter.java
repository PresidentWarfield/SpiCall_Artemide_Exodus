package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BinaryImagesConverter
{
  private static final String DATA_DIR = "/data";
  private final Context context;
  private final FileIdStrategy fileIdStrategy;
  
  BinaryImagesConverter(Context paramContext, FileIdStrategy paramFileIdStrategy)
  {
    this.context = paramContext;
    this.fileIdStrategy = paramFileIdStrategy;
  }
  
  private File correctDataPath(File paramFile)
  {
    if (Build.VERSION.SDK_INT < 9) {
      return paramFile;
    }
    File localFile1 = paramFile;
    File localFile2;
    if (paramFile.getAbsolutePath().startsWith("/data")) {
      try
      {
        ApplicationInfo localApplicationInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0);
        localFile1 = new java/io/File;
        localFile1.<init>(localApplicationInfo.nativeLibraryDir, paramFile.getName());
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        c.g().e("CrashlyticsCore", "Error getting ApplicationInfo", localNameNotFoundException);
        localFile2 = paramFile;
      }
    }
    return localFile2;
  }
  
  private static JSONObject createBinaryImageJson(String paramString, ProcMapEntry paramProcMapEntry)
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("base_address", paramProcMapEntry.address);
    localJSONObject.put("size", paramProcMapEntry.size);
    localJSONObject.put("name", paramProcMapEntry.path);
    localJSONObject.put("uuid", paramString);
    return localJSONObject;
  }
  
  private static byte[] generateBinaryImagesJsonString(JSONArray paramJSONArray)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("binary_images", paramJSONArray);
      return localJSONObject.toString().getBytes();
    }
    catch (JSONException paramJSONArray)
    {
      c.g().d("CrashlyticsCore", "Binary images string is null", paramJSONArray);
    }
    return new byte[0];
  }
  
  private File getLibraryFile(String paramString)
  {
    File localFile = new File(paramString);
    paramString = localFile;
    if (!localFile.exists()) {
      paramString = correctDataPath(localFile);
    }
    return paramString;
  }
  
  private static boolean isRelevant(ProcMapEntry paramProcMapEntry)
  {
    boolean bool;
    if ((paramProcMapEntry.perms.indexOf('x') != -1) && (paramProcMapEntry.path.indexOf('/') != -1)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static String joinMapsEntries(JSONArray paramJSONArray)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramJSONArray.length(); i++) {
      localStringBuilder.append(paramJSONArray.getString(i));
    }
    return localStringBuilder.toString();
  }
  
  private JSONObject jsonFromMapEntryString(String paramString)
  {
    paramString = ProcMapEntryParser.parse(paramString);
    if ((paramString != null) && (isRelevant(paramString)))
    {
      Object localObject = getLibraryFile(paramString.path);
      try
      {
        localObject = this.fileIdStrategy.createId((File)localObject);
        try
        {
          paramString = createBinaryImageJson((String)localObject, paramString);
          return paramString;
        }
        catch (JSONException paramString)
        {
          c.g().a("CrashlyticsCore", "Could not create a binary image json string", paramString);
          return null;
        }
        k localk;
        StringBuilder localStringBuilder;
        return null;
      }
      catch (IOException localIOException)
      {
        localk = c.g();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Could not generate ID for file ");
        localStringBuilder.append(paramString.path);
        localk.a("CrashlyticsCore", localStringBuilder.toString(), localIOException);
        return null;
      }
    }
  }
  
  private JSONArray parseProcMapsJsonFromStream(BufferedReader paramBufferedReader)
  {
    JSONArray localJSONArray = new JSONArray();
    for (;;)
    {
      Object localObject = paramBufferedReader.readLine();
      if (localObject == null) {
        break;
      }
      localObject = jsonFromMapEntryString((String)localObject);
      if (localObject != null) {
        localJSONArray.put(localObject);
      }
    }
    return localJSONArray;
  }
  
  private JSONArray parseProcMapsJsonFromString(String paramString)
  {
    JSONArray localJSONArray = new JSONArray();
    try
    {
      JSONObject localJSONObject = new org/json/JSONObject;
      localJSONObject.<init>(paramString);
      paramString = joinMapsEntries(localJSONObject.getJSONArray("maps"));
      paramString = paramString.split("\\|");
      for (int i = 0; i < paramString.length; i++)
      {
        localJSONObject = jsonFromMapEntryString(paramString[i]);
        if (localJSONObject != null) {
          localJSONArray.put(localJSONObject);
        }
      }
      return localJSONArray;
    }
    catch (JSONException paramString)
    {
      c.g().d("CrashlyticsCore", "Unable to parse proc maps string", paramString);
    }
    return localJSONArray;
  }
  
  byte[] convert(BufferedReader paramBufferedReader)
  {
    return generateBinaryImagesJsonString(parseProcMapsJsonFromStream(paramBufferedReader));
  }
  
  byte[] convert(String paramString)
  {
    return generateBinaryImagesJsonString(parseProcMapsJsonFromString(paramString));
  }
  
  static abstract interface FileIdStrategy
  {
    public abstract String createId(File paramFile);
  }
}


/* Location:              ~/com/crashlytics/android/core/BinaryImagesConverter.class
 *
 * Reversed by:           J
 */