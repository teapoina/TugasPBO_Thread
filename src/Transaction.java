import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction implements Runnable {
    private final int manhwaId;
    private final int quantity;

    public Transaction(int manhwaId, int quantity) {
        this.manhwaId = manhwaId;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            try (PreparedStatement checkStockStatement = connection.prepareStatement("SELECT stock FROM manhwa WHERE id = ? FOR UPDATE")) {
                checkStockStatement.setInt(1, manhwaId);
                try (ResultSet resultSet = checkStockStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int currentStock = resultSet.getInt("stock");
                        if (currentStock >= quantity) {
                            try (PreparedStatement updateStockStatement = connection.prepareStatement("UPDATE manhwa SET stock = ? WHERE id = ?")) {
                                updateStockStatement.setInt(1, currentStock - quantity);
                                updateStockStatement.setInt(2, manhwaId);
                                updateStockStatement.executeUpdate();
                            }
                            connection.commit();
                            System.out.println("Transaksi berhasil untuk manhwa " + manhwaId + ": " + quantity + " dibeli.");
                        } else {
                            connection.rollback();
                            System.out.println("Transaksi gagal untuk manhwa " + manhwaId + ": stok tidak cukup.");
                        }
                    } else {
                        connection.rollback();
                        System.out.println("Transaksi gagal untuk manhwa " + manhwaId + ": manhwa tidak ditemukan.");
                    }
                }
            } catch (SQLException inner) {
                connection.rollback();
                throw inner;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Kesalahan transaksi untuk manhwa " + manhwaId + ": " + e.getMessage());
        }
    }
}