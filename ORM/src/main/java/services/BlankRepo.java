package services;

import annotations.Column;
import annotations.Table;
import interfaces.CRUD;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public class BlankRepo implements CRUD<Object> {
    private final Connection con;

    public BlankRepo(){
        con = ConnectionManager.getConnection();
    }

    /**
     * This method adds a row to a SQL table. The table to add to is found through the object's class being marked
     * by the @Table annotation.
     * @param obj is used to determine the table to add to and what fields correspond to what column
     * @return
     */
    @Override
    public Object create(Object obj){
        try{
            //Checks if Table annotation is present first, might need to change how this is handled
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }
            //Uses annotations.Table Annotation for the table name
            String sql = "INSERT INTO " + obj.getClass().getAnnotation(Table.class).tableName();
            String columnNames = " (";
            Field[] field = obj.getClass().getDeclaredFields();
            for(int i = 0; i < field.length; i++){
                if(field[i].isAnnotationPresent(Column.class)){
                    if(i == field.length -1){
                        columnNames = columnNames.concat(field[i].getAnnotation(Column.class).columnName() + ")");
                    }else{
                        columnNames = columnNames.concat(field[i].getAnnotation(Column.class).columnName() + ", ");
                    }
                }
            }
            String sql2 = " VALUES (";
            //Sets up the rest of the sql statement with the amount of parameters required(this works)

            for(int i = 0; i <field.length; i++){
                if(field[i].isAnnotationPresent(Column.class)) {
                    if (i == field.length - 1) {
                        sql2 = sql2.concat("?)");
                    } else {
                        sql2 = sql2.concat("?,");
                    }
                }
            }
            String complete = sql + columnNames + sql2;
            PreparedStatement ps = con.prepareStatement(complete, Statement.RETURN_GENERATED_KEYS);

       //adds values to the columns
            int count = 1;
    for (Field f : field) {
        f.setAccessible(true);
        Object fieldValue = f.get(obj);
        System.out.println(fieldValue);
        ps.setObject(count, fieldValue);
        f.setAccessible(false);
        count++;
    }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }

            //Sets the table_id equal to the generated key
            for(Field f : field){
                if(f.isAnnotationPresent(Column.class)){
                    if(f.getAnnotation(Column.class).primaryKey()){
                        f.setAccessible(true);
                        f.set(obj, id);
                        f.setAccessible(false);
                    }
                }
            }

            //Probably have to change the catch blocks, don't want to print stacktraces
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * Queries a row(s) in a table with a column that contains String s,
     * and returns an object with fields equal to the columns of the table.
     * obj's class needs to be marked with @Table annotation.
     * It may be desired to have String s be a unique SQL column in order to retrieve data from only one row.
     * @param obj this is used to determine which table to query
     * @param s (String) s is used to find a row in the table where a column has String s as a value
     * @return
     */
    @Override
public Object read(Object obj, String s){
        try {
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }

            //reads object. make sure the primary key is named tableName_id where tableName is the name of the table
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String columnName = "";
            Field[] field = obj.getClass().getDeclaredFields();
            for(Field f : field){
                f.setAccessible(true);
                if(f.isAnnotationPresent(Column.class)){
                    if(f.get(obj).equals(s)){
                        columnName = f.getAnnotation(Column.class).columnName();
                    }
                }
                f.setAccessible(false);
            }
            String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, s);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                for(Field f : field){
                    if(f.isAnnotationPresent(Column.class)){
                            f.setAccessible(true);
                        System.out.println(rs.getObject(f.getAnnotation(Column.class).columnName()));
                            f.set(obj, rs.getObject(f.getAnnotation(Column.class).columnName()));
                            f.setAccessible(false);
                    }
                }

            }

            return obj;

            //change this later
        } catch(Exception e){
            e.printStackTrace();

        }
        return null;
}


    /**
     * Queries a row in a table at id, and returns an object with fields equal to the columns of the table
     * obj's class needs to be marked with @Table annotation.
     * @param obj the object determines which table that is to be read from
     * @param id id is used to identify which row to query from
     * @return
     */
    public Object read(Object obj, Integer id){
        try {
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }
            //reads object. make sure the primary key is named tableName_id where tableName is the name of the table
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String sql = "SELECT * FROM " + tableName + " WHERE " + tableName + "_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            Field[] field = obj.getClass().getDeclaredFields();
            int i = 1;
            while(rs.next()){
                for(Field f : field){
                    if(f.isAnnotationPresent(Column.class)){
                        f.setAccessible(true);
                        Object o = rs.getObject(i);
                        i++;
                        f.set(obj, o);
                        f.setAccessible(false);
                    }
                }
            }


            return obj;

            //change this later
        } catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Retrieves data from a table at a specific reference id as a list
     * @param obj - Denotes what table to query from
     * @param ref - Used to find the foreign key id column to retrieve data from
     * @param id - Used to determine what row(s) to retrieve
     * @param list - Contains each row associated with id as an object to this list
     * @return
     */
    public Object readAll(Object obj, Object ref, Integer id, List<Object> list){
        try {
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }
            //reads object. make sure the primary key is named tableName_id where tableName is the name of the table
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String table2Name = ref.getClass().getAnnotation(Table.class).tableName();
            String sql = "SELECT * FROM "+ tableName + " WHERE " + table2Name + "_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();

            Field[] field = obj.getClass().getDeclaredFields();
            int i = 1;
            while(rs.next()){
                for(Field f : field){
                    if(f.isAnnotationPresent(Column.class)){
                        f.setAccessible(true);
                        Object o = rs.getObject(i);
                        i++;
                        f.set(obj, o);
                        f.setAccessible(false);
                    }
                }
                list.add(field);
            }


            return obj;

            //change this later
        } catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }

    /**
     * This method updates the SQL database for a given object. Change the object through its getters and setters then
     * put the object through this method to update the database.
     * obj's class needs to be marked with @Table annotation
     * @param obj this is used to determine which table to manipulate
     * @return
     */
@Override
public Object update(Object obj){
        try{
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String idName = tableName + "_id";
            String sql = "UPDATE " + tableName + " SET";
            String columnName = " ";
            Field[] field = obj.getClass().getDeclaredFields();
            //Creates the columnName string with the appropriate information
            for(int i = 0; i <field.length; i++){
                if(field[i].isAnnotationPresent(Column.class) & !field[i].getAnnotation(Column.class).primaryKey()) {
                    if (i == field.length - 1) {
                        columnName = columnName.concat(field[i].getAnnotation(Column.class).columnName() + " = ? ");
                    } else {
                        columnName = columnName.concat(field[i].getAnnotation(Column.class).columnName() + " = ?, ");
                    }
                }
            }
            String sql2 = "WHERE " + idName + " = ?";
           String complete = sql + columnName + sql2;

            PreparedStatement ps = con.prepareStatement(complete);
            int count = 0;
            for (Field f : field) {
                f.setAccessible(true);
                Object fieldValue = f.get(obj);
                if(f.getAnnotation(Column.class).primaryKey()){
                    ps.setObject(field.length, fieldValue);
                }else{
                    ps.setObject(count, fieldValue);
                }
                f.setAccessible(false);
                count++;
            }
            ps.executeUpdate();

            //change this later
        } catch (Exception e) {
            e.printStackTrace();
        }
    return obj;
}


    /**
     * This method deletes a column from a SQL table.
     * obj's class needs to be marked with @Table annotation.
     * @param obj this is used to determine which table to manipulate
     * @param id id is the primary id of the row to be deleted from the table
     */
    @Override
    public void delete(Object obj, Integer id){
        try{
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @annotations.Table Annotation");
            }
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String idName = tableName + "_id";
            String sql = "DELETE FROM " + tableName + " WHERE " + idName + " = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            //Change this later
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
