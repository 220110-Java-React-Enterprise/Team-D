import annotations.Column;
import annotations.Table;

@Table(tableName = "test_table")
public class TestReflection {
    @Column(columnName = "test_table_id", primaryKey = true)
    private int num;
    @Column(columnName = "test_name", primaryKey = false)
    private String name;
    @Column(columnName = "boolo", primaryKey = false)
    private boolean b;

    public TestReflection(){
    }
    public TestReflection(String name, boolean b){
        this.name = name;
        this.b = b;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBool() {
        return b;
    }

    public void setBool(boolean bool) {
        this.b = bool;
    }
}
