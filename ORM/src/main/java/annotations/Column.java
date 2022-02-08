package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
//Sets column name for a field. Set primaryKey to true if that column is the primary key otherwise set it to false
public @interface Column {
    String columnName();
    boolean primaryKey();
}
