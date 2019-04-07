package com.google.gson.a;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface a
{
  boolean a() default true;
  
  boolean b() default true;
}


/* Location:              ~/com/google/gson/a/a.class
 *
 * Reversed by:           J
 */