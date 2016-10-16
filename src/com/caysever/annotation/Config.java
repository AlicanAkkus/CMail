package com.caysever.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Config {
	String from() default "no_reply@caysever.com";
	String subject() default "cayserver mail sender";
	String charset() default "UTF-8";
	String contentType() default "text/plain";
	String[] to();
	String[] cc();
	String smtpHost() default "";
	String smtpPort() default "";
	String smtpUsername() default "";
	String smtpPassword() default "";
	boolean isAuth() default false;
	boolean isDebug() default true;
}
