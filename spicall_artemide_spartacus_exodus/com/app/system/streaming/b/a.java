package com.app.system.streaming.b;

import android.annotation.SuppressLint;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.view.Surface;

@SuppressLint({"NewApi"})
public class a
{
  private EGLContext a = null;
  private EGLContext b = null;
  private EGLSurface c = null;
  private EGLDisplay d = null;
  private Surface e;
  
  public a(Surface paramSurface)
  {
    this.e = paramSurface;
    d();
  }
  
  public a(Surface paramSurface, a parama)
  {
    this.e = paramSurface;
    this.b = parama.a;
    d();
  }
  
  private void a(String paramString)
  {
    int i = EGL14.eglGetError();
    if (i == 12288) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(": EGL error: 0x");
    localStringBuilder.append(Integer.toHexString(i));
    throw new RuntimeException(localStringBuilder.toString());
  }
  
  private void d()
  {
    this.d = EGL14.eglGetDisplay(0);
    if (this.d != EGL14.EGL_NO_DISPLAY)
    {
      Object localObject = new int[2];
      if (EGL14.eglInitialize(this.d, (int[])localObject, 0, (int[])localObject, 1))
      {
        if (this.b == null) {
          localObject = new int[] { 12324, 8, 12323, 8, 12322, 8, 12352, 4, 12344 };
        } else {
          localObject = new int[] { 12324, 8, 12323, 8, 12322, 8, 12352, 4, 12610, 1, 12344 };
        }
        EGLConfig[] arrayOfEGLConfig = new EGLConfig[1];
        int[] arrayOfInt = new int[1];
        EGL14.eglChooseConfig(this.d, (int[])localObject, 0, arrayOfEGLConfig, 0, arrayOfEGLConfig.length, arrayOfInt, 0);
        a("eglCreateContext RGB888+recordable ES2");
        arrayOfInt = new int[3];
        int[] tmp208_207 = arrayOfInt;
        tmp208_207[0] = '゘';
        int[] tmp214_208 = tmp208_207;
        tmp214_208[1] = 2;
        int[] tmp218_214 = tmp214_208;
        tmp218_214[2] = '〸';
        tmp218_214;
        localObject = this.b;
        if (localObject == null) {
          this.a = EGL14.eglCreateContext(this.d, arrayOfEGLConfig[0], EGL14.EGL_NO_CONTEXT, arrayOfInt, 0);
        } else {
          this.a = EGL14.eglCreateContext(this.d, arrayOfEGLConfig[0], (EGLContext)localObject, arrayOfInt, 0);
        }
        a("eglCreateContext");
        this.c = EGL14.eglCreateWindowSurface(this.d, arrayOfEGLConfig[0], this.e, new int[] { 12344 }, 0);
        a("eglCreateWindowSurface");
        GLES20.glDisable(2929);
        GLES20.glDisable(2884);
        return;
      }
      throw new RuntimeException("unable to initialize EGL14");
    }
    throw new RuntimeException("unable to get EGL14 display");
  }
  
  public void a()
  {
    EGLDisplay localEGLDisplay = this.d;
    EGLSurface localEGLSurface = this.c;
    if (EGL14.eglMakeCurrent(localEGLDisplay, localEGLSurface, localEGLSurface, this.a)) {
      return;
    }
    throw new RuntimeException("eglMakeCurrent failed");
  }
  
  public void a(long paramLong)
  {
    EGLExt.eglPresentationTimeANDROID(this.d, this.c, paramLong);
    a("eglPresentationTimeANDROID");
  }
  
  public void b()
  {
    EGL14.eglSwapBuffers(this.d, this.c);
  }
  
  public void c()
  {
    if (this.d != EGL14.EGL_NO_DISPLAY)
    {
      EGL14.eglMakeCurrent(this.d, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
      EGL14.eglDestroySurface(this.d, this.c);
      EGL14.eglDestroyContext(this.d, this.a);
      EGL14.eglReleaseThread();
      EGL14.eglTerminate(this.d);
    }
    this.d = EGL14.EGL_NO_DISPLAY;
    this.a = EGL14.EGL_NO_CONTEXT;
    this.c = EGL14.EGL_NO_SURFACE;
    this.e.release();
  }
}


/* Location:              ~/com/app/system/streaming/b/a.class
 *
 * Reversed by:           J
 */