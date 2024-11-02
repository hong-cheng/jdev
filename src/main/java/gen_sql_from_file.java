/**
 * generate create table and data insert statements given a csv or tab-delimited file
 *  todo: port the python script
 *
 */
public class gen_sql_from_file {
    private String file_name;

    public gen_sql_from_file() {
    }

    public gen_sql_from_file(String s) {
        file_name = s;
    }

    public void gen_create_table() {
        System.out.println("called gen_create_table");
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        gen_sql_from_file g = new gen_sql_from_file("src/main/resources/data.csv");
        g.gen_create_table();
    }
}
