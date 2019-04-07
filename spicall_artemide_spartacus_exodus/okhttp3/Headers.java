package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

public final class Headers
{
  private final String[] namesAndValues;
  
  Headers(Builder paramBuilder)
  {
    this.namesAndValues = ((String[])paramBuilder.namesAndValues.toArray(new String[paramBuilder.namesAndValues.size()]));
  }
  
  private Headers(String[] paramArrayOfString)
  {
    this.namesAndValues = paramArrayOfString;
  }
  
  static void checkName(String paramString)
  {
    if (paramString != null)
    {
      if (!paramString.isEmpty())
      {
        int i = paramString.length();
        int j = 0;
        while (j < i)
        {
          int k = paramString.charAt(j);
          if ((k > 32) && (k < 127)) {
            j++;
          } else {
            throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", new Object[] { Integer.valueOf(k), Integer.valueOf(j), paramString }));
          }
        }
        return;
      }
      throw new IllegalArgumentException("name is empty");
    }
    throw new NullPointerException("name == null");
  }
  
  static void checkValue(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      int i = paramString1.length();
      int j = 0;
      while (j < i)
      {
        int k = paramString1.charAt(j);
        if (((k > 31) || (k == 9)) && (k < 127)) {
          j++;
        } else {
          throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", new Object[] { Integer.valueOf(k), Integer.valueOf(j), paramString2, paramString1 }));
        }
      }
      return;
    }
    paramString1 = new StringBuilder();
    paramString1.append("value for name ");
    paramString1.append(paramString2);
    paramString1.append(" == null");
    throw new NullPointerException(paramString1.toString());
  }
  
  private static String get(String[] paramArrayOfString, String paramString)
  {
    for (int i = paramArrayOfString.length - 2; i >= 0; i -= 2) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i])) {
        return paramArrayOfString[(i + 1)];
      }
    }
    return null;
  }
  
  public static Headers of(Map<String, String> paramMap)
  {
    if (paramMap != null)
    {
      String[] arrayOfString = new String[paramMap.size() * 2];
      int i = 0;
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Object localObject = (Map.Entry)paramMap.next();
        if ((((Map.Entry)localObject).getKey() != null) && (((Map.Entry)localObject).getValue() != null))
        {
          String str = ((String)((Map.Entry)localObject).getKey()).trim();
          localObject = ((String)((Map.Entry)localObject).getValue()).trim();
          checkName(str);
          checkValue((String)localObject, str);
          arrayOfString[i] = str;
          arrayOfString[(i + 1)] = localObject;
          i += 2;
        }
        else
        {
          throw new IllegalArgumentException("Headers cannot be null");
        }
      }
      return new Headers(arrayOfString);
    }
    throw new NullPointerException("headers == null");
  }
  
  public static Headers of(String... paramVarArgs)
  {
    if (paramVarArgs != null)
    {
      if (paramVarArgs.length % 2 == 0)
      {
        String[] arrayOfString = (String[])paramVarArgs.clone();
        int i = 0;
        int k;
        for (int j = 0;; j++)
        {
          k = i;
          if (j >= arrayOfString.length) {
            break label63;
          }
          if (arrayOfString[j] == null) {
            break;
          }
          arrayOfString[j] = arrayOfString[j].trim();
        }
        throw new IllegalArgumentException("Headers cannot be null");
        label63:
        while (k < arrayOfString.length)
        {
          paramVarArgs = arrayOfString[k];
          String str = arrayOfString[(k + 1)];
          checkName(paramVarArgs);
          checkValue(str, paramVarArgs);
          k += 2;
        }
        return new Headers(arrayOfString);
      }
      throw new IllegalArgumentException("Expected alternating header names and values");
    }
    throw new NullPointerException("namesAndValues == null");
  }
  
  public long byteCount()
  {
    String[] arrayOfString = this.namesAndValues;
    long l = arrayOfString.length * 2;
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++) {
      l += this.namesAndValues[j].length();
    }
    return l;
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof Headers)) && (Arrays.equals(((Headers)paramObject).namesAndValues, this.namesAndValues))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Nullable
  public String get(String paramString)
  {
    return get(this.namesAndValues, paramString);
  }
  
  @Nullable
  public Date getDate(String paramString)
  {
    paramString = get(paramString);
    if (paramString != null) {
      paramString = HttpDate.parse(paramString);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.namesAndValues);
  }
  
  public String name(int paramInt)
  {
    return this.namesAndValues[(paramInt * 2)];
  }
  
  public Set<String> names()
  {
    TreeSet localTreeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    int i = size();
    for (int j = 0; j < i; j++) {
      localTreeSet.add(name(j));
    }
    return Collections.unmodifiableSet(localTreeSet);
  }
  
  public Builder newBuilder()
  {
    Builder localBuilder = new Builder();
    Collections.addAll(localBuilder.namesAndValues, this.namesAndValues);
    return localBuilder;
  }
  
  public int size()
  {
    return this.namesAndValues.length / 2;
  }
  
  public Map<String, List<String>> toMultimap()
  {
    TreeMap localTreeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    int i = size();
    for (int j = 0; j < i; j++)
    {
      String str = name(j).toLowerCase(Locale.US);
      List localList = (List)localTreeMap.get(str);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList(2);
        localTreeMap.put(str, localObject);
      }
      ((List)localObject).add(value(j));
    }
    return localTreeMap;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = size();
    for (int j = 0; j < i; j++)
    {
      localStringBuilder.append(name(j));
      localStringBuilder.append(": ");
      localStringBuilder.append(value(j));
      localStringBuilder.append("\n");
    }
    return localStringBuilder.toString();
  }
  
  public String value(int paramInt)
  {
    return this.namesAndValues[(paramInt * 2 + 1)];
  }
  
  public List<String> values(String paramString)
  {
    int i = size();
    Object localObject1 = null;
    int j = 0;
    while (j < i)
    {
      Object localObject2 = localObject1;
      if (paramString.equalsIgnoreCase(name(j)))
      {
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new ArrayList(2);
        }
        ((List)localObject2).add(value(j));
      }
      j++;
      localObject1 = localObject2;
    }
    if (localObject1 != null) {
      paramString = Collections.unmodifiableList((List)localObject1);
    } else {
      paramString = Collections.emptyList();
    }
    return paramString;
  }
  
  public static final class Builder
  {
    final List<String> namesAndValues = new ArrayList(20);
    
    public Builder add(String paramString)
    {
      int i = paramString.indexOf(":");
      if (i != -1) {
        return add(paramString.substring(0, i).trim(), paramString.substring(i + 1));
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected header: ");
      localStringBuilder.append(paramString);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    public Builder add(String paramString1, String paramString2)
    {
      Headers.checkName(paramString1);
      Headers.checkValue(paramString2, paramString1);
      return addLenient(paramString1, paramString2);
    }
    
    public Builder add(String paramString, Date paramDate)
    {
      if (paramDate != null)
      {
        add(paramString, HttpDate.format(paramDate));
        return this;
      }
      paramDate = new StringBuilder();
      paramDate.append("value for name ");
      paramDate.append(paramString);
      paramDate.append(" == null");
      throw new NullPointerException(paramDate.toString());
    }
    
    public Builder addAll(Headers paramHeaders)
    {
      int i = paramHeaders.size();
      for (int j = 0; j < i; j++) {
        addLenient(paramHeaders.name(j), paramHeaders.value(j));
      }
      return this;
    }
    
    Builder addLenient(String paramString)
    {
      int i = paramString.indexOf(":", 1);
      if (i != -1) {
        return addLenient(paramString.substring(0, i), paramString.substring(i + 1));
      }
      if (paramString.startsWith(":")) {
        return addLenient("", paramString.substring(1));
      }
      return addLenient("", paramString);
    }
    
    Builder addLenient(String paramString1, String paramString2)
    {
      this.namesAndValues.add(paramString1);
      this.namesAndValues.add(paramString2.trim());
      return this;
    }
    
    public Builder addUnsafeNonAscii(String paramString1, String paramString2)
    {
      Headers.checkName(paramString1);
      return addLenient(paramString1, paramString2);
    }
    
    public Headers build()
    {
      return new Headers(this);
    }
    
    public String get(String paramString)
    {
      for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
        if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i))) {
          return (String)this.namesAndValues.get(i + 1);
        }
      }
      return null;
    }
    
    public Builder removeAll(String paramString)
    {
      int j;
      for (int i = 0; i < this.namesAndValues.size(); i = j + 2)
      {
        j = i;
        if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
        {
          this.namesAndValues.remove(i);
          this.namesAndValues.remove(i);
          j = i - 2;
        }
      }
      return this;
    }
    
    public Builder set(String paramString1, String paramString2)
    {
      Headers.checkName(paramString1);
      Headers.checkValue(paramString2, paramString1);
      removeAll(paramString1);
      addLenient(paramString1, paramString2);
      return this;
    }
    
    public Builder set(String paramString, Date paramDate)
    {
      if (paramDate != null)
      {
        set(paramString, HttpDate.format(paramDate));
        return this;
      }
      paramDate = new StringBuilder();
      paramDate.append("value for name ");
      paramDate.append(paramString);
      paramDate.append(" == null");
      throw new NullPointerException(paramDate.toString());
    }
  }
}


/* Location:              ~/okhttp3/Headers.class
 *
 * Reversed by:           J
 */