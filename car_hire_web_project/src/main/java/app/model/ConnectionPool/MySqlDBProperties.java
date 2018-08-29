package app.model.ConnectionPool;

public class MySqlDBProperties {

    public Object[][] getContents() {
        return new Object[][] {
                { "driver", "com.mysql.cj.jdbc.Driver" },
                { "url", "jdbc:mysql://localhost:3306/carhire?serverTimezone=UTC" },
                { "user", "root" },
                { "password", "RootPassword12345" },
                { "poolsize", "5" },
        };
    }

}
