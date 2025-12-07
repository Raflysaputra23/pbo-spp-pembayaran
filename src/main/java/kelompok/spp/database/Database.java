package kelompok.spp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

interface IntDatabase {

    public void query(String sql) throws SQLException;

    public void bind(int index, Object value) throws SQLException;

    public boolean execute() throws SQLException;

    public List<Map<String, Object>> resultSet() throws SQLException;

    public Map<String, Object> single() throws SQLException;
}

public class Database implements IntDatabase {

    private final String URL = "jdbc:mysql://localhost:3306/db_spp_pembayaran";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Connection conn;
    private PreparedStatement stmt;

    public Database() {
        try {
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }

    @Override
    public void query(String sql) throws SQLException {
        this.stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public void bind(int index, Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NULL);
        } else if (value instanceof Integer) {
            stmt.setInt(index, (Integer) value);
        } else if (value instanceof Boolean) {
            stmt.setBoolean(index, (Boolean) value);
        } else if (value instanceof Long) {
            stmt.setLong(index, (Long) value);
        } else if (value instanceof Double) {
            stmt.setDouble(index, (Double) value);
        } else {
            stmt.setString(index, value.toString());
        }
    }

    @Override
    public boolean execute() throws SQLException {
        return stmt.execute();
    }

    @Override
    public ObservableList<Map<String, Object>> resultSet() throws SQLException {
        execute();
        ResultSet rs = stmt.getResultSet();
        ObservableList<Map<String, Object>> list = FXCollections.observableArrayList();

        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    @Override
    public Map<String, Object> single() throws SQLException {
        execute();
        ResultSet rs = stmt.getResultSet();

        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        if (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            return row;
        }

        return null;
    }

    public int rowCount() throws SQLException {
        return stmt.getUpdateCount();
    }
    
    public void beginTransaction() throws SQLException {
        this.conn.setAutoCommit(false);
    }
    
    public void commit() throws SQLException {
        this.conn.commit();
    }
    
    public void rollback() throws SQLException {
        this.conn.rollback();
    }
}
