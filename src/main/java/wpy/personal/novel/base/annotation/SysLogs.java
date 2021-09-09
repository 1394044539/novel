package wpy.personal.novel.base.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogs {

    /**
     * 操作行为
     */
    String fun() default "";

    /**
     * 请求方式
     */
    String method() default "";
}
