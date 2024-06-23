package lk.ijse.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DbConnection {
    private static DbConnection dbConnection;
    private final Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tealeavesmanagement",
                "root",
                "Ijse@123"
        );
    }

    public static DbConnection getInstance() throws SQLException {
        if(dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

}
