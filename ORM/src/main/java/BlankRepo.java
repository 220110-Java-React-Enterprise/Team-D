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
            //Checks if Table annotation is present first
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
            //Test to see what if the prepared statement is correct
            System.out.println(complete);





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

        }catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    //Probably need to change this so that the parameter is either an int id or string of some kind
    //Because this seems to not really be necessary/do anything. As we give it an object and it just sort of returns it
@Override
public Object read(Object obj){
        try {
            //reads object. make sure the primary key is named tableName_id where tableName is the name of the table
            String tableName = obj.getClass().getAnnotation(Table.class).tableName();
            String sql = "SELECT * FROM " + tableName + " WHERE " + tableName + "_id = ?";
            Integer primaryKey = null;

            Field[] field = obj.getClass().getDeclaredFields();
            //Looks for column marked as primary key (where primaryKey() = true) and then retrieves that object
            for(Field f : field){
                if(f.getAnnotation(Column.class).primaryKey()){
                    f.setAccessible(true);
                    primaryKey = (Integer)f.get(obj);
                    f.setAccessible(false);
                }
            }
           // System.out.println(sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, primaryKey);

            return obj;
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
           //getting the correct statement
            System.out.println(complete);
            PreparedStatement ps = con.prepareStatement(complete);
            int count = 0;
            for (Field f : field) {
                f.setAccessible(true);
                Object fieldValue = f.get(obj);
                System.out.println(fieldValue);
                if(f.getAnnotation(Column.class).primaryKey()){
                    ps.setObject(field.length, fieldValue);
                }else{
                    ps.setObject(count, fieldValue);
                }
                f.setAccessible(false);
                count++;
            }
            ps.executeUpdate();
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
            System.out.println(id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
