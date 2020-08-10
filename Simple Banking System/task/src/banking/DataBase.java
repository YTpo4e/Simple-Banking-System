package banking;

import java.sql.*;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DataBase {
    static String url = "";

    public static void createNewDataBase(String fileName) {
        url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String DELETE_TABLE = "DROP TABLE IF EXISTS card";
        String CREATE_TABLE = "CREATE TABLE card (\n" +
                    "id INTEGER, \n" +
                    "number TEXT, \n" +
                    "pin TEXT, \n" +
                    "balance INTEGER DEFAULT 0 \n" +
                    ");";

        try (Connection conn = DriverManager.getConnection(url)) {
            try (Statement stmt = conn.createStatement();) {
                stmt.execute(DELETE_TABLE);
                stmt.execute(CREATE_TABLE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void insert (Account account) {
        String INSERT_TABLE = "INSERT INTO card(number, pin) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(INSERT_TABLE)) {
                stmt.setLong(1, account.getAccNumber());
                stmt.setInt(2, account.getPass());
                stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkNUmberCard(Long number) {
        String sql = "SELECT number FROM card WHERE number = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, number);
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public static boolean checkPinCard(Long number, int pin) {
        String sql = "SELECT pin FROM card WHERE number = ?";
        String out = null;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, number);
            ResultSet rs = pstmt.executeQuery();
            out  = rs.getString("pin");
        } catch (SQLException e) {

        }

        if (Objects.equals(Integer.toString(pin), out)) {
            return true;
        } else {
            return false;
        }
    }

    static int getBalance(Long cardNumber) {
        int balance = 0;
        String sql = "SELECT balance FROM card WHERE number = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cardNumber);
            ResultSet rs = pstmt.executeQuery(sql);
            balance = Integer.parseInt(rs.getString(balance));
        } catch (SQLException e) {

        }

        return balance;
    }

   static void addIncome(Long cardNumber, int money) {
       String sql = "UPDATE card SET balance + ? WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, money);
            statement.setLong(2, cardNumber);
            statement.executeUpdate();
            System.out.println("Income was added!");
        } catch (SQLException e) {

        }
   }

   static void doTransfer(Long cardNumber, Long to, int money) {
        if (cardNumber == to) {
            System.out.println("You can't transfer money to the same account");
            return;
        }
        if(checkNUmberCard(to)) {
            System.out.println("Such a card does not exist.");
            return;
        }
        if(Account.checkValidity(to)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }

        int firstBalance = getBalance(cardNumber);
        if (firstBalance < money) {
            System.out.println("Not enough money!");
        } else {
            addIncome(cardNumber, -money);
            addIncome(to, money);
            System.out.println("Success!");
        }
   }

   static void deleteAccount(Long cardNumber) {
       String sql = "DELETE FROM card WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, cardNumber);
            statement.executeUpdate();
            System.out.println("The account has been closed!");
        } catch (SQLException throwables) {

        }
   }
}
