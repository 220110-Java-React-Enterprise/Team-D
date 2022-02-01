import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            String sql = "INSERT INTO " + obj.getClass().getAnnotation(Table.class).tableName() + " VALUES (";
            //Sets up the rest of the sql statement with the amount of parameters required(this works)
            Field[] field = obj.getClass().getDeclaredFields();
            for(int i = 0; i <field.length; i++){
                if(field[i].isAnnotationPresent(Column.class)) {
                    if (i == field.length - 1) {
                        sql = sql.concat("?)");
                    } else {
                        sql = sql.concat("?,");
                    }
                }
            }

            PreparedStatement ps = con.prepareStatement(sql);
            //Test to see what if the prepared statement is correct
            //System.out.println(sql);

       //adds values to the columns
            int count = 1;
    for (Field f : field) {
        f.setAccessible(true);
        Object fieldValue = f.get(obj);
        ps.setObject(count, fieldValue);
        f.setAccessible(false);
        count++;
    }


            ps.executeUpdate();
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
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, primaryKey);

            return obj;
        }catch(SQLException | IllegalAccessException e){
            e.printStackTrace();

        }
    return null;
}

/*@Override
public Object update(Object obj){

} */

    /*@Override
    public delete(Integer id){

    }
     */
}
