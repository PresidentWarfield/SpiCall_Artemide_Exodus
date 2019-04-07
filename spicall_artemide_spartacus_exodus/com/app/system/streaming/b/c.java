package com.app.system.streaming.b;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.security.d.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

@SuppressLint({"InlinedApi"})
public class c
{
  private final float[] a = { -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F };
  private FloatBuffer b = ByteBuffer.allocateDirect(this.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
  private float[] c = new float[16];
  private float[] d = new float[16];
  private int e;
  private int f = 53191;
  private int g;
  private int h;
  private int i;
  private int j;
  private SurfaceTexture k;
  
  public c()
  {
    this.b.put(this.a).position(0);
    Matrix.setIdentityM(this.d, 0);
  }
  
  private int a(int paramInt, String paramString)
  {
    int m = GLES20.glCreateShader(paramInt);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("glCreateShader type=");
    localStringBuilder.append(paramInt);
    a(localStringBuilder.toString());
    GLES20.glShaderSource(m, paramString);
    GLES20.glCompileShader(m);
    paramString = new int[1];
    GLES20.glGetShaderiv(m, 35713, paramString, 0);
    int n = m;
    if (paramString[0] == 0)
    {
      paramString = new StringBuilder();
      paramString.append("Could not compile shader ");
      paramString.append(paramInt);
      paramString.append(":");
      a.a("TextureManager", paramString.toString(), new Object[0]);
      paramString = new StringBuilder();
      paramString.append(" ");
      paramString.append(GLES20.glGetShaderInfoLog(m));
      a.a("TextureManager", paramString.toString(), new Object[0]);
      GLES20.glDeleteShader(m);
      n = 0;
    }
    return n;
  }
  
  private int a(String paramString1, String paramString2)
  {
    int m = a(35633, paramString1);
    int n = 0;
    if (m == 0) {
      return 0;
    }
    int i1 = a(35632, paramString2);
    if (i1 == 0) {
      return 0;
    }
    int i2 = GLES20.glCreateProgram();
    a("glCreateProgram");
    if (i2 == 0) {
      a.a("TextureManager", "Could not create program", new Object[0]);
    }
    GLES20.glAttachShader(i2, m);
    a("glAttachShader");
    GLES20.glAttachShader(i2, i1);
    a("glAttachShader");
    GLES20.glLinkProgram(i2);
    paramString1 = new int[1];
    GLES20.glGetProgramiv(i2, 35714, paramString1, 0);
    if (paramString1[0] != 1)
    {
      a.a("TextureManager", "Could not link program: ", new Object[0]);
      a.a("TextureManager", GLES20.glGetProgramInfoLog(i2), new Object[0]);
      GLES20.glDeleteProgram(i2);
      i2 = n;
    }
    return i2;
  }
  
  public SurfaceTexture a()
  {
    return this.k;
  }
  
  public void a(String paramString)
  {
    int m = GLES20.glGetError();
    if (m == 0) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(": glError ");
    localStringBuilder.append(m);
    a.a("TextureManager", localStringBuilder.toString(), new Object[0]);
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(": glError ");
    localStringBuilder.append(m);
    throw new RuntimeException(localStringBuilder.toString());
  }
  
  public void b()
  {
    this.k.updateTexImage();
  }
  
  public void c()
  {
    a("onDrawFrame start");
    this.k.getTransformMatrix(this.d);
    GLES20.glUseProgram(this.e);
    a("glUseProgram");
    GLES20.glActiveTexture(33984);
    GLES20.glBindTexture(36197, 0);
    GLES20.glBindTexture(36197, this.f);
    this.b.position(0);
    GLES20.glVertexAttribPointer(this.i, 3, 5126, false, 20, this.b);
    a("glVertexAttribPointer maPosition");
    GLES20.glEnableVertexAttribArray(this.i);
    a("glEnableVertexAttribArray maPositionHandle");
    this.b.position(3);
    GLES20.glVertexAttribPointer(this.j, 2, 5126, false, 20, this.b);
    a("glVertexAttribPointer maTextureHandle");
    GLES20.glEnableVertexAttribArray(this.j);
    a("glEnableVertexAttribArray maTextureHandle");
    Matrix.setIdentityM(this.c, 0);
    GLES20.glUniformMatrix4fv(this.g, 1, false, this.c, 0);
    GLES20.glUniformMatrix4fv(this.h, 1, false, this.d, 0);
    GLES20.glDrawArrays(5, 0, 4);
    a("glDrawArrays");
    GLES20.glFinish();
  }
  
  public SurfaceTexture d()
  {
    this.e = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
    int m = this.e;
    if (m != 0)
    {
      this.i = GLES20.glGetAttribLocation(m, "aPosition");
      a("glGetAttribLocation aPosition");
      if (this.i != -1)
      {
        this.j = GLES20.glGetAttribLocation(this.e, "aTextureCoord");
        a("glGetAttribLocation aTextureCoord");
        if (this.j != -1)
        {
          this.g = GLES20.glGetUniformLocation(this.e, "uMVPMatrix");
          a("glGetUniformLocation uMVPMatrix");
          if (this.g != -1)
          {
            this.h = GLES20.glGetUniformLocation(this.e, "uSTMatrix");
            a("glGetUniformLocation uSTMatrix");
            if (this.h != -1)
            {
              int[] arrayOfInt = new int[1];
              GLES20.glGenTextures(1, arrayOfInt, 0);
              this.f = arrayOfInt[0];
              GLES20.glBindTexture(36197, this.f);
              a("glBindTexture mTextureID");
              GLES20.glTexParameterf(36197, 10241, 9728.0F);
              GLES20.glTexParameterf(36197, 10240, 9729.0F);
              GLES20.glTexParameteri(36197, 10242, 33071);
              GLES20.glTexParameteri(36197, 10243, 33071);
              a("glTexParameter");
              this.k = new SurfaceTexture(this.f);
              return this.k;
            }
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
          }
          throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        throw new RuntimeException("Could not get attrib location for aTextureCoord");
      }
      throw new RuntimeException("Could not get attrib location for aPosition");
    }
    throw new RuntimeException("failed creating program");
  }
  
  public void e()
  {
    this.k = null;
  }
}


/* Location:              ~/com/app/system/streaming/b/c.class
 *
 * Reversed by:           J
 */