package android.support.v4.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser
{
  private static final String LOGTAG = "PathParser";
  
  private static void addNode(ArrayList<PathDataNode> paramArrayList, char paramChar, float[] paramArrayOfFloat)
  {
    paramArrayList.add(new PathDataNode(paramChar, paramArrayOfFloat));
  }
  
  public static boolean canMorph(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    if ((paramArrayOfPathDataNode1 != null) && (paramArrayOfPathDataNode2 != null))
    {
      if (paramArrayOfPathDataNode1.length != paramArrayOfPathDataNode2.length) {
        return false;
      }
      int i = 0;
      while (i < paramArrayOfPathDataNode1.length) {
        if ((paramArrayOfPathDataNode1[i].mType == paramArrayOfPathDataNode2[i].mType) && (paramArrayOfPathDataNode1[i].mParams.length == paramArrayOfPathDataNode2[i].mParams.length)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  static float[] copyOfRange(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    if (paramInt1 <= paramInt2)
    {
      int i = paramArrayOfFloat.length;
      if ((paramInt1 >= 0) && (paramInt1 <= i))
      {
        paramInt2 -= paramInt1;
        i = Math.min(paramInt2, i - paramInt1);
        float[] arrayOfFloat = new float[paramInt2];
        System.arraycopy(paramArrayOfFloat, paramInt1, arrayOfFloat, 0, i);
        return arrayOfFloat;
      }
      throw new ArrayIndexOutOfBoundsException();
    }
    throw new IllegalArgumentException();
  }
  
  public static PathDataNode[] createNodesFromPathData(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 1;
    int j = 0;
    while (i < paramString.length())
    {
      i = nextStart(paramString, i);
      String str = paramString.substring(j, i).trim();
      if (str.length() > 0)
      {
        float[] arrayOfFloat = getFloats(str);
        addNode(localArrayList, str.charAt(0), arrayOfFloat);
      }
      j = i;
      i++;
    }
    if ((i - j == 1) && (j < paramString.length())) {
      addNode(localArrayList, paramString.charAt(j), new float[0]);
    }
    return (PathDataNode[])localArrayList.toArray(new PathDataNode[localArrayList.size()]);
  }
  
  public static Path createPathFromPathData(String paramString)
  {
    Path localPath = new Path();
    Object localObject = createNodesFromPathData(paramString);
    if (localObject != null) {
      try
      {
        PathDataNode.nodesToPath((PathDataNode[])localObject, localPath);
        return localPath;
      }
      catch (RuntimeException localRuntimeException)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Error in parsing ");
        ((StringBuilder)localObject).append(paramString);
        throw new RuntimeException(((StringBuilder)localObject).toString(), localRuntimeException);
      }
    }
    return null;
  }
  
  public static PathDataNode[] deepCopyNodes(PathDataNode[] paramArrayOfPathDataNode)
  {
    if (paramArrayOfPathDataNode == null) {
      return null;
    }
    PathDataNode[] arrayOfPathDataNode = new PathDataNode[paramArrayOfPathDataNode.length];
    for (int i = 0; i < paramArrayOfPathDataNode.length; i++) {
      arrayOfPathDataNode[i] = new PathDataNode(paramArrayOfPathDataNode[i]);
    }
    return arrayOfPathDataNode;
  }
  
  private static void extract(String paramString, int paramInt, ExtractFloatResult paramExtractFloatResult)
  {
    paramExtractFloatResult.mEndWithNegOrDot = false;
    int i = paramInt;
    int j = 0;
    int k = 0;
    int m = 0;
    while (i < paramString.length())
    {
      int n = paramString.charAt(i);
      if (n != 32)
      {
        if ((n != 69) && (n != 101)) {}
        switch (n)
        {
        default: 
          break;
        case 46: 
          if (k == 0)
          {
            j = 0;
            k = 1;
          }
          else
          {
            paramExtractFloatResult.mEndWithNegOrDot = true;
            j = 0;
            m = 1;
          }
          break;
        case 45: 
          if ((i != paramInt) && (j == 0))
          {
            paramExtractFloatResult.mEndWithNegOrDot = true;
            j = 0;
            m = 1;
          }
          else
          {
            j = 0;
            break label153;
            j = 1;
          }
          break;
        }
      }
      j = 0;
      m = 1;
      label153:
      if (m != 0) {
        break;
      }
      i++;
    }
    paramExtractFloatResult.mEndPosition = i;
  }
  
  private static float[] getFloats(String paramString)
  {
    if ((paramString.charAt(0) != 'z') && (paramString.charAt(0) != 'Z')) {
      try
      {
        float[] arrayOfFloat = new float[paramString.length()];
        localObject = new android/support/v4/graphics/PathParser$ExtractFloatResult;
        ((ExtractFloatResult)localObject).<init>();
        int i = paramString.length();
        int j = 1;
        int k = 0;
        while (j < i)
        {
          extract(paramString, j, (ExtractFloatResult)localObject);
          int m = ((ExtractFloatResult)localObject).mEndPosition;
          int n = k;
          if (j < m)
          {
            arrayOfFloat[k] = Float.parseFloat(paramString.substring(j, m));
            n = k + 1;
          }
          if (((ExtractFloatResult)localObject).mEndWithNegOrDot)
          {
            j = m;
            k = n;
          }
          else
          {
            j = m + 1;
            k = n;
          }
        }
        localObject = copyOfRange(arrayOfFloat, 0, k);
        return (float[])localObject;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("error in parsing \"");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("\"");
        throw new RuntimeException(((StringBuilder)localObject).toString(), localNumberFormatException);
      }
    }
    return new float[0];
  }
  
  private static int nextStart(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((((i - 65) * (i - 90) <= 0) || ((i - 97) * (i - 122) <= 0)) && (i != 101) && (i != 69)) {
        return paramInt;
      }
      paramInt++;
    }
    return paramInt;
  }
  
  public static void updateNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    for (int i = 0; i < paramArrayOfPathDataNode2.length; i++)
    {
      paramArrayOfPathDataNode1[i].mType = ((char)paramArrayOfPathDataNode2[i].mType);
      for (int j = 0; j < paramArrayOfPathDataNode2[i].mParams.length; j++) {
        paramArrayOfPathDataNode1[i].mParams[j] = paramArrayOfPathDataNode2[i].mParams[j];
      }
    }
  }
  
  private static class ExtractFloatResult
  {
    int mEndPosition;
    boolean mEndWithNegOrDot;
  }
  
  public static class PathDataNode
  {
    public float[] mParams;
    public char mType;
    
    PathDataNode(char paramChar, float[] paramArrayOfFloat)
    {
      this.mType = ((char)paramChar);
      this.mParams = paramArrayOfFloat;
    }
    
    PathDataNode(PathDataNode paramPathDataNode)
    {
      this.mType = ((char)paramPathDataNode.mType);
      paramPathDataNode = paramPathDataNode.mParams;
      this.mParams = PathParser.copyOfRange(paramPathDataNode, 0, paramPathDataNode.length);
    }
    
    private static void addCommand(Path paramPath, float[] paramArrayOfFloat1, char paramChar1, char paramChar2, float[] paramArrayOfFloat2)
    {
      Path localPath = paramPath;
      float f1 = paramArrayOfFloat1[0];
      float f2 = paramArrayOfFloat1[1];
      float f3 = paramArrayOfFloat1[2];
      float f4 = paramArrayOfFloat1[3];
      float f5 = paramArrayOfFloat1[4];
      float f6 = paramArrayOfFloat1[5];
      char c1;
      switch (paramChar2)
      {
      default: 
        c1 = '\002';
        break;
      case 'Z': 
      case 'z': 
        paramPath.close();
        localPath.moveTo(f5, f6);
        f1 = f5;
        f3 = f1;
        f2 = f6;
        f4 = f2;
        c1 = '\002';
        break;
      case 'Q': 
      case 'S': 
      case 'q': 
      case 's': 
        c1 = '\004';
        break;
      case 'L': 
      case 'M': 
      case 'T': 
      case 'l': 
      case 'm': 
      case 't': 
        c1 = '\002';
        break;
      case 'H': 
      case 'V': 
      case 'h': 
      case 'v': 
        c1 = '\001';
        break;
      case 'C': 
      case 'c': 
        c1 = '\006';
        break;
      case 'A': 
      case 'a': 
        c1 = '\007';
      }
      float f7 = f5;
      char c2 = '\000';
      int i = paramChar1;
      f5 = f6;
      f6 = f7;
      paramChar1 = c2;
      while (paramChar1 < paramArrayOfFloat2.length)
      {
        f7 = 0.0F;
        float f8;
        float f9;
        int j;
        int k;
        boolean bool1;
        boolean bool2;
        switch (paramChar2)
        {
        default: 
          break;
        case 'v': 
          i = paramChar1 + '\000';
          localPath.rLineTo(0.0F, paramArrayOfFloat2[i]);
          f2 += paramArrayOfFloat2[i];
          break;
        case 't': 
          if ((i != 113) && (i != 116) && (i != 81) && (i != 84))
          {
            f4 = 0.0F;
            f3 = f7;
          }
          else
          {
            f3 = f1 - f3;
            f4 = f2 - f4;
          }
          c2 = paramChar1 + '\000';
          f7 = paramArrayOfFloat2[c2];
          i = paramChar1 + '\001';
          localPath.rQuadTo(f3, f4, f7, paramArrayOfFloat2[i]);
          f7 = f1 + paramArrayOfFloat2[c2];
          f8 = f2 + paramArrayOfFloat2[i];
          f4 += f2;
          f3 += f1;
          f2 = f8;
          f1 = f7;
          break;
        case 's': 
          if ((i != 99) && (i != 115) && (i != 67) && (i != 83))
          {
            f3 = 0.0F;
            f4 = 0.0F;
          }
          else
          {
            f4 = f2 - f4;
            f3 = f1 - f3;
          }
          i = paramChar1 + '\000';
          f9 = paramArrayOfFloat2[i];
          j = paramChar1 + '\001';
          f8 = paramArrayOfFloat2[j];
          k = paramChar1 + '\002';
          f7 = paramArrayOfFloat2[k];
          c2 = paramChar1 + '\003';
          paramPath.rCubicTo(f3, f4, f9, f8, f7, paramArrayOfFloat2[c2]);
          f3 = paramArrayOfFloat2[i];
          f4 = paramArrayOfFloat2[j];
          f7 = f1 + paramArrayOfFloat2[k];
          f8 = f2 + paramArrayOfFloat2[c2];
          f3 += f1;
          f4 += f2;
          f2 = f8;
          f1 = f7;
          break;
        case 'q': 
          j = paramChar1 + '\000';
          f4 = paramArrayOfFloat2[j];
          c2 = paramChar1 + '\001';
          f3 = paramArrayOfFloat2[c2];
          i = paramChar1 + '\002';
          f7 = paramArrayOfFloat2[i];
          k = paramChar1 + '\003';
          localPath.rQuadTo(f4, f3, f7, paramArrayOfFloat2[k]);
          f3 = paramArrayOfFloat2[j];
          f4 = paramArrayOfFloat2[c2];
          f7 = f1 + paramArrayOfFloat2[i];
          f8 = f2 + paramArrayOfFloat2[k];
          f3 += f1;
          f4 += f2;
          f2 = f8;
          f1 = f7;
          break;
        case 'm': 
          i = paramChar1 + '\000';
          f1 += paramArrayOfFloat2[i];
          c2 = paramChar1 + '\001';
          f2 += paramArrayOfFloat2[c2];
          if (paramChar1 > 0)
          {
            localPath.rLineTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
          }
          else
          {
            localPath.rMoveTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
            f5 = f2;
            f6 = f1;
          }
          break;
        case 'l': 
          i = paramChar1 + '\000';
          f7 = paramArrayOfFloat2[i];
          c2 = paramChar1 + '\001';
          localPath.rLineTo(f7, paramArrayOfFloat2[c2]);
          f1 += paramArrayOfFloat2[i];
          f2 += paramArrayOfFloat2[c2];
          break;
        case 'h': 
          i = paramChar1 + '\000';
          localPath.rLineTo(paramArrayOfFloat2[i], 0.0F);
          f1 += paramArrayOfFloat2[i];
          break;
        case 'c': 
          f3 = paramArrayOfFloat2[(paramChar1 + '\000')];
          f9 = paramArrayOfFloat2[(paramChar1 + '\001')];
          j = paramChar1 + '\002';
          f4 = paramArrayOfFloat2[j];
          i = paramChar1 + '\003';
          f7 = paramArrayOfFloat2[i];
          c2 = paramChar1 + '\004';
          f8 = paramArrayOfFloat2[c2];
          k = paramChar1 + '\005';
          paramPath.rCubicTo(f3, f9, f4, f7, f8, paramArrayOfFloat2[k]);
          f3 = paramArrayOfFloat2[j];
          f4 = paramArrayOfFloat2[i];
          f7 = f1 + paramArrayOfFloat2[c2];
          f8 = f2 + paramArrayOfFloat2[k];
          f3 += f1;
          f4 += f2;
          f2 = f8;
          f1 = f7;
          break;
        case 'a': 
          c2 = paramChar1 + '\005';
          f4 = paramArrayOfFloat2[c2];
          i = paramChar1 + '\006';
          f3 = paramArrayOfFloat2[i];
          f9 = paramArrayOfFloat2[(paramChar1 + '\000')];
          f7 = paramArrayOfFloat2[(paramChar1 + '\001')];
          f8 = paramArrayOfFloat2[(paramChar1 + '\002')];
          if (paramArrayOfFloat2[(paramChar1 + '\003')] != 0.0F) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          if (paramArrayOfFloat2[(paramChar1 + '\004')] != 0.0F) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          drawArc(paramPath, f1, f2, f4 + f1, f3 + f2, f9, f7, f8, bool1, bool2);
          f1 += paramArrayOfFloat2[c2];
          f2 += paramArrayOfFloat2[i];
          f4 = f2;
          f3 = f1;
          localPath = paramPath;
          break;
        case 'V': 
          i = paramChar1 + '\000';
          f2 = paramArrayOfFloat2[i];
          localPath = paramPath;
          localPath.lineTo(f1, f2);
          f2 = paramArrayOfFloat2[i];
          break;
        case 'T': 
          f8 = f2;
          f7 = f1;
          c2 = paramChar1;
          if ((i != 113) && (i != 116) && (i != 81))
          {
            f1 = f7;
            f2 = f8;
            if (i != 84) {}
          }
          else
          {
            f2 = f8 * 2.0F - f4;
            f1 = f7 * 2.0F - f3;
          }
          i = c2 + '\000';
          f3 = paramArrayOfFloat2[i];
          c2++;
          localPath.quadTo(f1, f2, f3, paramArrayOfFloat2[c2]);
          f8 = paramArrayOfFloat2[i];
          f7 = paramArrayOfFloat2[c2];
          f3 = f1;
          f4 = f2;
          f2 = f7;
          f1 = f8;
          break;
        case 'S': 
          c2 = paramChar1;
          if ((i != 99) && (i != 115) && (i != 67) && (i != 83))
          {
            f3 = f2;
            f2 = f1;
            f1 = f3;
          }
          else
          {
            f2 = f2 * 2.0F - f4;
            f3 = f1 * 2.0F - f3;
            f1 = f2;
            f2 = f3;
          }
          j = c2 + '\000';
          f4 = paramArrayOfFloat2[j];
          i = c2 + '\001';
          f3 = paramArrayOfFloat2[i];
          k = c2 + '\002';
          f7 = paramArrayOfFloat2[k];
          c2 += 3;
          paramPath.cubicTo(f2, f1, f4, f3, f7, paramArrayOfFloat2[c2]);
          f3 = paramArrayOfFloat2[j];
          f4 = paramArrayOfFloat2[i];
          f1 = paramArrayOfFloat2[k];
          f2 = paramArrayOfFloat2[c2];
          break;
        case 'Q': 
          i = paramChar1;
          k = i + 0;
          f1 = paramArrayOfFloat2[k];
          j = i + 1;
          f2 = paramArrayOfFloat2[j];
          c2 = i + 2;
          f3 = paramArrayOfFloat2[c2];
          i += 3;
          localPath.quadTo(f1, f2, f3, paramArrayOfFloat2[i]);
          f3 = paramArrayOfFloat2[k];
          f4 = paramArrayOfFloat2[j];
          f1 = paramArrayOfFloat2[c2];
          f2 = paramArrayOfFloat2[i];
          break;
        case 'M': 
          i = paramChar1;
          c2 = i + 0;
          f1 = paramArrayOfFloat2[c2];
          j = i + 1;
          f2 = paramArrayOfFloat2[j];
          if (i > 0)
          {
            localPath.lineTo(paramArrayOfFloat2[c2], paramArrayOfFloat2[j]);
          }
          else
          {
            localPath.moveTo(paramArrayOfFloat2[c2], paramArrayOfFloat2[j]);
            f5 = f2;
            f6 = f1;
          }
          break;
        case 'L': 
          i = paramChar1;
          c2 = i + 0;
          f2 = paramArrayOfFloat2[c2];
          i++;
          localPath.lineTo(f2, paramArrayOfFloat2[i]);
          f1 = paramArrayOfFloat2[c2];
          f2 = paramArrayOfFloat2[i];
          break;
        case 'H': 
          i = paramChar1 + '\000';
          localPath.lineTo(paramArrayOfFloat2[i], f2);
          f1 = paramArrayOfFloat2[i];
          break;
        case 'C': 
          i = paramChar1;
          f3 = paramArrayOfFloat2[(i + 0)];
          f7 = paramArrayOfFloat2[(i + 1)];
          c2 = i + 2;
          f4 = paramArrayOfFloat2[c2];
          k = i + 3;
          f1 = paramArrayOfFloat2[k];
          j = i + 4;
          f2 = paramArrayOfFloat2[j];
          i += 5;
          paramPath.cubicTo(f3, f7, f4, f1, f2, paramArrayOfFloat2[i]);
          f1 = paramArrayOfFloat2[j];
          f2 = paramArrayOfFloat2[i];
          f3 = paramArrayOfFloat2[c2];
          f4 = paramArrayOfFloat2[k];
          break;
        case 'A': 
          i = paramChar1;
          j = i + 5;
          f4 = paramArrayOfFloat2[j];
          c2 = i + 6;
          f7 = paramArrayOfFloat2[c2];
          f3 = paramArrayOfFloat2[(i + 0)];
          f9 = paramArrayOfFloat2[(i + 1)];
          f8 = paramArrayOfFloat2[(i + 2)];
          if (paramArrayOfFloat2[(i + 3)] != 0.0F) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          if (paramArrayOfFloat2[(i + 4)] != 0.0F) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          drawArc(paramPath, f1, f2, f4, f7, f3, f9, f8, bool1, bool2);
          f1 = paramArrayOfFloat2[j];
          f2 = paramArrayOfFloat2[c2];
          f4 = f2;
          f3 = f1;
        }
        paramChar1 += c1;
        i = paramChar2;
      }
      paramArrayOfFloat1[0] = f1;
      paramArrayOfFloat1[1] = f2;
      paramArrayOfFloat1[2] = f3;
      paramArrayOfFloat1[3] = f4;
      paramArrayOfFloat1[4] = f6;
      paramArrayOfFloat1[5] = f5;
    }
    
    private static void arcToBezier(Path paramPath, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
    {
      int i = (int)Math.ceil(Math.abs(paramDouble9 * 4.0D / 3.141592653589793D));
      double d1 = Math.cos(paramDouble7);
      double d2 = Math.sin(paramDouble7);
      double d3 = Math.cos(paramDouble8);
      double d4 = Math.sin(paramDouble8);
      paramDouble7 = -paramDouble3;
      double d5 = paramDouble7 * d1;
      double d6 = paramDouble4 * d2;
      paramDouble7 *= d2;
      double d7 = paramDouble4 * d1;
      paramDouble4 = i;
      Double.isNaN(paramDouble4);
      double d8 = paramDouble9 / paramDouble4;
      double d9 = d4 * paramDouble7 + d3 * d7;
      paramDouble9 = d5 * d4 - d6 * d3;
      int j = 0;
      d3 = paramDouble6;
      paramDouble6 = paramDouble5;
      d4 = paramDouble8;
      paramDouble4 = paramDouble7;
      paramDouble7 = d8;
      paramDouble8 = d2;
      paramDouble5 = d1;
      for (;;)
      {
        d2 = paramDouble3;
        if (j >= i) {
          break;
        }
        d8 = d4 + paramDouble7;
        double d10 = Math.sin(d8);
        double d11 = Math.cos(d8);
        d1 = paramDouble1 + d2 * paramDouble5 * d11 - d6 * d10;
        double d12 = paramDouble2 + d2 * paramDouble8 * d11 + d7 * d10;
        d2 = d5 * d10 - d6 * d11;
        d10 = d10 * paramDouble4 + d11 * d7;
        d11 = d8 - d4;
        d4 = Math.tan(d11 / 2.0D);
        d4 = Math.sin(d11) * (Math.sqrt(d4 * 3.0D * d4 + 4.0D) - 1.0D) / 3.0D;
        paramPath.rLineTo(0.0F, 0.0F);
        paramPath.cubicTo((float)(paramDouble6 + paramDouble9 * d4), (float)(d3 + d9 * d4), (float)(d1 - d4 * d2), (float)(d12 - d4 * d10), (float)d1, (float)d12);
        j++;
        d3 = d12;
        d4 = d8;
        d9 = d10;
        paramDouble9 = d2;
        paramDouble6 = d1;
      }
    }
    
    private static void drawArc(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, boolean paramBoolean1, boolean paramBoolean2)
    {
      double d1 = Math.toRadians(paramFloat7);
      double d2 = Math.cos(d1);
      double d3 = Math.sin(d1);
      double d4 = paramFloat1;
      Double.isNaN(d4);
      double d5 = paramFloat2;
      Double.isNaN(d5);
      double d6 = paramFloat5;
      Double.isNaN(d6);
      double d7 = (d4 * d2 + d5 * d3) / d6;
      double d8 = -paramFloat1;
      Double.isNaN(d8);
      Double.isNaN(d5);
      double d9 = paramFloat6;
      Double.isNaN(d9);
      double d10 = (d8 * d3 + d5 * d2) / d9;
      double d11 = paramFloat3;
      Double.isNaN(d11);
      d8 = paramFloat4;
      Double.isNaN(d8);
      Double.isNaN(d6);
      double d12 = (d11 * d2 + d8 * d3) / d6;
      d11 = -paramFloat3;
      Double.isNaN(d11);
      Double.isNaN(d8);
      Double.isNaN(d9);
      double d13 = (d11 * d3 + d8 * d2) / d9;
      double d14 = d7 - d12;
      double d15 = d10 - d13;
      d11 = (d7 + d12) / 2.0D;
      d8 = (d10 + d13) / 2.0D;
      double d16 = d14 * d14 + d15 * d15;
      if (d16 == 0.0D)
      {
        Log.w("PathParser", " Points are coincident");
        return;
      }
      double d17 = 1.0D / d16 - 0.25D;
      if (d17 < 0.0D)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Points are too far apart ");
        localStringBuilder.append(d16);
        Log.w("PathParser", localStringBuilder.toString());
        float f = (float)(Math.sqrt(d16) / 1.99999D);
        drawArc(paramPath, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5 * f, paramFloat6 * f, paramFloat7, paramBoolean1, paramBoolean2);
        return;
      }
      d17 = Math.sqrt(d17);
      d14 *= d17;
      d15 = d17 * d15;
      if (paramBoolean1 == paramBoolean2)
      {
        d11 -= d15;
        d8 += d14;
      }
      else
      {
        d11 += d15;
        d8 -= d14;
      }
      d15 = Math.atan2(d10 - d8, d7 - d11);
      d10 = Math.atan2(d13 - d8, d12 - d11) - d15;
      if (d10 >= 0.0D) {
        paramBoolean1 = true;
      } else {
        paramBoolean1 = false;
      }
      d7 = d10;
      if (paramBoolean2 != paramBoolean1) {
        if (d10 > 0.0D) {
          d7 = d10 - 6.283185307179586D;
        } else {
          d7 = d10 + 6.283185307179586D;
        }
      }
      Double.isNaN(d6);
      d11 *= d6;
      Double.isNaN(d9);
      d8 *= d9;
      arcToBezier(paramPath, d11 * d2 - d8 * d3, d11 * d3 + d8 * d2, d6, d9, d4, d5, d1, d15, d7);
    }
    
    public static void nodesToPath(PathDataNode[] paramArrayOfPathDataNode, Path paramPath)
    {
      float[] arrayOfFloat = new float[6];
      char c1 = 'm';
      int i = 0;
      for (char c2 = c1; i < paramArrayOfPathDataNode.length; c2 = c1)
      {
        addCommand(paramPath, arrayOfFloat, c2, paramArrayOfPathDataNode[i].mType, paramArrayOfPathDataNode[i].mParams);
        c1 = paramArrayOfPathDataNode[i].mType;
        i++;
      }
    }
    
    public void interpolatePathDataNode(PathDataNode paramPathDataNode1, PathDataNode paramPathDataNode2, float paramFloat)
    {
      for (int i = 0;; i++)
      {
        float[] arrayOfFloat = paramPathDataNode1.mParams;
        if (i >= arrayOfFloat.length) {
          break;
        }
        this.mParams[i] = (arrayOfFloat[i] * (1.0F - paramFloat) + paramPathDataNode2.mParams[i] * paramFloat);
      }
    }
  }
}


/* Location:              ~/android/support/v4/graphics/PathParser.class
 *
 * Reversed by:           J
 */