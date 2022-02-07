import java.lang.reflect.Field;
import java.sql.*;

public class BlankRepo implements CRUD<Object> {
    private final Connection con;

    public BlankRepo(){
        con = ConnectionManager.getConnection();
    }

    @Override
    public Object create(Object obj){
        try{
            //Checks if Table annotation is present first, might need to change how this is handled
            if(!obj.getClass().isAnnotationPresent(Table.class)){
                throw new Exception("Missing @Table Annotation");
            }
            //Uses Table Annotation for the table name
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
        }catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    //Reads an object from the database that has String s as a unique identifier
    // (not primary key, probably best as a username or email)
    //make sure the column that you are using to read data from is marked unique
    // (might be able to add an annotation to guarantee this)
    //NullPointerException will be thrown if the object's field associated with the string is null
    //To avoid that, you need to set that field to the value that you want to search
    //and then the read method will set the rest of the fields equal to what is in the database
@Override
public Object read(Object obj, String s){
        try {
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



            //Looks for column marked as primary key (where primaryKey() = true) and then retrieves that object

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
        }catch(SQLException | IllegalAccessException e){
            e.printStackTrace();

        }
    return null;
}

//Uses object to find table name,
// and uses the table id in order to retrieve the data associated with it from the database
    public Object read(Object obj, Integer id){
        try {
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
        }catch(SQLException | IllegalAccessException e){
            e.printStackTrace();

        }
        return null;
    }


//This should update the database at the given id. The problem here is that it only works when we already have the
//auto incremented id number. ie we can't update an object in the table that was created before running the project
//this might be fixed if we change the read function to take an integer id instead of the object
@Override
public Object update(Object obj){
        try{
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
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
return obj;
}

//Delete works, just need a good way to get id
    @Override
    public void delete(Object obj, Integer id){
        try{
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String idName = tableName + "_id";
            String sql = "DELETE FROM " + tableName + " WHERE " + idName + " = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            //Change this later
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
