package unknow.orm.annotation;

import java.lang.annotation.*;

@Target({ElementType.LOCAL_VARIABLE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate
	{
	}