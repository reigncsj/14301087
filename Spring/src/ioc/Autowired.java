package ioc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Autowired {
	String value()default " ";
}