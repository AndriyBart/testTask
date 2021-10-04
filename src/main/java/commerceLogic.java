import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;


public class commerceLogic {


    public static int lastId = 0;
    public static int ObtainedIdFromTable = 0;
    public static void main(String[] args) {
        connectDb connect = new connectDb();
        Scanner in = new Scanner(System.in);


        System.out.println("Instruction: how to work with the program\n" +
                "\n" +
                "For adding a product to the table fill add_product in the console\n" +
                "For creating an order (to buy a product/products) fill buy_product in the console\n" +
                "For watching the list of products from the table in the database fill show_all_products in the console\n" +
                "For deleting a product from the products table fill delete_product in the console\n" +
                "For deleting all products from the products table fill delete_all_products in the console\n" +
                "To see a list of all products, which have been ordered at least once, with total ordered quantity sorted descending by the quantity, fill filter in the console\n" +
                "For watching the list of orders from the table in the database fill show_all_orders in the console\n" +
                "To remove product by ID, fill delete_product in the console\n" +
                "For leaving the program fill exit in the console ");

        String waitOperation = in.next();
        if (waitOperation.equals("filter")) {
            connect.FilterValuesBySql("SELECT DISTINCT products.name,  SUM(quantity) OVER (PARTITION BY name)"
                    + "as 'quantity' FROM order_items INNER JOIN products ON order_items.product_id=products.id ORDER BY quantity DESC;");
           waitOperation = in.next();
        }

        if (waitOperation.equals("by_product")) {
            connect.GetLastId("SELECT * FROM orders");
            int newId = lastId + 1;
            connect.InsetToDb("INSERT  orders (id, user_id, status, created_at ) VALUES(" + newId
                    + ",'10','new','" + new Date(Calendar.getInstance().getTimeInMillis()) + "');");

            connect.GetOllFromTableProducts("SELECT * FROM products");
            while (waitOperation.equals("by_product")) {

                System.out.println("Please enter product name:");
                String productNameForOrder = in.next();
                System.out.println("Please enter quantity:");
                String QuantityProductForOrder = in.next();
                int orderId = lastId + 1;
                connect.getIdFromTable("SELECT id FROM products WHERE name='" + productNameForOrder + "';");
                               int productId = ObtainedIdFromTable;

                connect.InsetToDb("INSERT  order_items (order_id, product_id, quantity ) VALUES(" + orderId
                        + ",'" + productId + "', '" + QuantityProductForOrder + "');");

                System.out.println("If you want select more product enter YES, else finish: ");
                waitOperation = in.next();
                if (waitOperation.equals("YES")) {
                    waitOperation = "by_product";
                }

            }
        }

        while (waitOperation.equals("add_product")) {
            System.out.println("Please enter name: ");
            String productName = in.next();
            System.out.println("Please enter price: ");
            int productPrice = in.nextInt();
            System.out.println("Please enter status (in_stock;out_of_stock;running_low ): ");
            String productStatus = in.next();
            connect.GetLastId("SELECT * FROM products");
            int newId = lastId + 1;
            connect.InsetToDb("INSERT  products (id, name, price, status, created_at ) VALUES(" + newId
                    + "," + "'" + productName + "'" + "," + "'" + productPrice + "'" + "," + "'" + productStatus + "'"
                    + "," + "'" + new Date(Calendar.getInstance().getTimeInMillis()) + "');");
            System.out.println("Product was added successful!  "
                    + " if you finished enter : exit");
            System.out.println("Do you want to add a product again? For add product enter: add_product. )");
            System.out.println("For delete product enter: delete_product.");
            waitOperation = in.next();
        }
        if (waitOperation.equals("delete_product")) {
            System.out.println("Please enter product id: ");
            //String DeleteById = in.next();
            int DeleteById=Integer.parseInt(in.next());

            connect.DeleteLineInTable("DELETE FROM products WHERE id=" + "'" + DeleteById + "';");
            waitOperation = in.next();
        }
        if (waitOperation.equals("show_all_product")) {
            connect.GetOllFromTableProducts("SELECT * FROM products");
            waitOperation = in.next(); }

        if (waitOperation.equals("delete_all_products")) {
            connect.truncateFromTable("TRUNCATE products");
            waitOperation = in.next();
        }
        if (waitOperation.equals("show_all_orders")) {
            connect.ShowAllOrders("SELECT order_items.order_id, products.price, products.name, order_items.quantity, orders.created_at\n" +
                    "FROM order_items JOIN products ON order_items.product_id=products.id\n" +
                    "JOIN orders ON order_items.order_id=orders.id;");

        } waitOperation = in.next();
        if (waitOperation.equals("exit")) {
            System.out.println("Thanks! goodbye!");
        }

    }
}
