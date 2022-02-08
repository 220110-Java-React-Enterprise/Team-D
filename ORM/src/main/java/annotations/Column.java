package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This is an annotation that marks a field as a SQL column. Where the name of the column name is String columnName,
 * and whether it is the primary key by either setting the boolean primaryKey to true or false.
 * True corresponds to the column being the primary key.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String columnName();
    boolean primaryKey();
}
