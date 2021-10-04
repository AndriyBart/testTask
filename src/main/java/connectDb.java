import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Calendar;

public class connectDb {
    //підключаємося до класу getProperties який вичитує ппараметри налаштування
    getProperties prop = new getProperties(); // обєкт класа
    String url = prop.Url;
    String login = prop.Username;
    String password = prop.Password;
    // об'являємо змінні.....
    Connection connection = null;
    PreparedStatement statement = null;

    public void setConnectionToDb() {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url, login, password);// підключаємося до бази данних
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void InsetToDb(String SqlCmdInsert) {
// реэструэмо драйвер
        setConnectionToDb();
        try {

            //фрагмент для додавання значени в таблицю product
            //if (commerceLogic.InsetInTableProducts  == "isertInTableProducts")
            statement = connection.prepareStatement(SqlCmdInsert);//комадна додати в таблицю
            statement.execute(); // виполнить метод

            //  if (!connection.isClosed()){System.out.println("Connection is successful");}
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
            //  if (connection.isClosed()){System.out.println("Connection close");}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //метод для  вичитування усього з таблиці product
    public void GetOllFromTableProducts(String SqlCmdGet) {
        setConnectionToDb();

        try {
            statement = connection.prepareStatement(SqlCmdGet);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("| id  | name  | price  |  status | data |");
            while (resultSet.next()) {
               // int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                String status = resultSet.getString("status");
               // Date data = resultSet.getDate("created_at");

                System.out.println("| " + name + " | " + price + " | " + status +  " |");

            }
            // if (!connection.isClosed()){System.out.println("Connection is successful");}
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод для  вичитування усього з таблиці product
    public void GetLastId(String SqlCmdGet) {
        setConnectionToDb();

        try {
            statement = connection.prepareStatement(SqlCmdGet);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (id > commerceLogic.lastId) {
                    commerceLogic.lastId = id;
                }


            }
            // if (!connection.isClosed()){System.out.println("Connection is successful");}
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //**видалити занчення з таблиці
    public void DeleteLineInTable(String SqlCmdDelete) {

        setConnectionToDb();
        try {
            statement = connection.prepareStatement(SqlCmdDelete);
            statement.execute();
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //**вивести з таблицi занчення
    public void getIdFromTable(String SqlCmdGetValue) {
        setConnectionToDb();

        try {

            statement = connection.prepareStatement(SqlCmdGetValue);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                commerceLogic.ObtainedIdFromTable = resultSet.getInt("id");
            }

            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // SQL запит на фільтрування
    public void FilterValuesBySql(String SqlCmdFilter) {
        setConnectionToDb();
        try {
            statement = connection.prepareStatement(SqlCmdFilter);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("| name | quantity |");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String quantity = resultSet.getString("quantity");

                System.out.println("| " + name + " | " + quantity + " |");

            }
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // S
    public void ShowAllOrders(String SqlCmdShowOrders) {
        setConnectionToDb();
        try {
            statement = connection.prepareStatement(SqlCmdShowOrders);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("| order_id | price|  name | quantity| created_at| ");
            while (resultSet.next()) {
                String order_id = resultSet.getString("order_id");
                String price = resultSet.getString("price");
                String name = resultSet.getString("name");

                String quantity = resultSet.getString("quantity");
                Date data = resultSet.getDate("created_at");
                System.out.println("| " + order_id + " | " + price + " |" + name + " |" + quantity + " |" + data + " |");

            }
            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // видалити з таблиці все
    public  void  truncateFromTable(String SqlCmdTruncate){
        setConnectionToDb();
        try {

            statement = connection.prepareStatement(SqlCmdTruncate);
            statement.execute();

            statement.close();//закриваєм statemet
            connection.close();//закриваєм з'єднання
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}


