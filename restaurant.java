package dbms;

import java.sql.*;
import java.util.Scanner;

public class restaurant {

    private Connection connection;
    private Scanner scanner;

    // Constructor to initialize the connection and scanner
    public restaurant() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "jyothika");
        scanner = new Scanner(System.in);
    }

    public void createTables() throws SQLException {
    	try {
        Statement smt = connection.createStatement();

        smt.executeUpdate("create table waiterss(wid int primary key, wname varchar(20))");
        System.out.println("waiter table successfully created");
        smt.executeUpdate("create table customers(cid int primary key, cname varchar(20))");
        System.out.println("customer table successfully created");
        smt.executeUpdate("create table menus(id int primary key, name varchar(20), price int)");
        System.out.println("menu table successfully created");
        smt.executeUpdate("create table ordern(ord_no int primary key, wid int, cid int, id int, " +
                "foreign key (wid) references waiterss(wid)on delete cascade on delete update, " +
                "foreign key (cid) references customers(cid) on delete cascade on delete update, " +
                "foreign key (id) references menus(id) on delete cascade on update cascade)");
        System.out.println("orders table successfully created");
    	}
    	catch(SQLException e) {
    		System.out.println("Tables are already existing");
    	}

    }

    public void insertRecords() throws SQLException {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Insert waiter");
            System.out.println("2. Insert customer");
            System.out.println("3. Insert menu");
            System.out.println("4. Insert order");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 5) {
                break;
            }

            switch (choice) {
                case 1:
                	try {
                    System.out.print("Enter waiter id: ");
                    int wid = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter waiter name: ");
                    String wname = scanner.nextLine();

                    String insertWaiter = "INSERT INTO waiterss (wid, wname) VALUES (?, ?)";
                    PreparedStatement pstmtWaiter = connection.prepareStatement(insertWaiter);
                    pstmtWaiter.setInt(1, wid);
                    pstmtWaiter.setString(2, wname);
                    pstmtWaiter.executeUpdate();
                    System.out.println("-----Inserted values into waiter table-----");
                    break;
                	}
                	catch(SQLException e)
                	{
                		System.out.println("error inserting into the table");                	}

                case 2:
                	try {
                    System.out.print("Enter customer id: ");
                    int cid = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter customer name: ");
                    String cname = scanner.nextLine();

                    String insertCustomer = "INSERT INTO customers (cid, cname) VALUES (?, ?)";
                    PreparedStatement pstmtCustomer = connection.prepareStatement(insertCustomer);
                    pstmtCustomer.setInt(1, cid);
                    pstmtCustomer.setString(2, cname);
                    pstmtCustomer.executeUpdate();
                    System.out.println("----Inserted values into customer table----");
                    break;
                	}
                	catch(SQLException e) {
                		System.out.println("error inserting into the table");
                	}

                case 3:
                	try
                	{
                    System.out.print("Enter menu id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter menu name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter menu price: ");
                    int price = scanner.nextInt();

                    String insertMenu = "INSERT INTO menus (id, name, price) VALUES (?, ?, ?)";
                    PreparedStatement pstmtMenu = connection.prepareStatement(insertMenu);
                    pstmtMenu.setInt(1, id);
                    pstmtMenu.setString(2, name);
                    pstmtMenu.setInt(3, price);
                    pstmtMenu.executeUpdate();
                    System.out.println("----Inserted values into menu table----");
                    break;
                	}
                	catch(SQLException e) {
                		System.out.println("error inserting into the table");
                	}


                case 4:
                	try {
                    System.out.print("Enter order number: ");
                    int ord_no = scanner.nextInt();
                    System.out.print("Enter waiter id: ");
                    int waiterId = scanner.nextInt();
                    System.out.print("Enter customer id: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter menu id: ");
                    int menuId = scanner.nextInt();

                    String insertOrder = "INSERT INTO ordern (ord_no, wid, cid, id) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmtOrder = connection.prepareStatement(insertOrder);
                    pstmtOrder.setInt(1, ord_no);
                    pstmtOrder.setInt(2, waiterId);
                    pstmtOrder.setInt(3, customerId);
                    pstmtOrder.setInt(4, menuId);
                    pstmtOrder.executeUpdate();
                    System.out.println("-----Inserted values into orders table-----");
                    break;
                	}
                	catch(SQLException e) {
                		System.out.println("error inserting into the table");
                	}


                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public void deleteRecord() throws SQLException {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Delete waiter");
            System.out.println("2. Delete menu");
            System.out.println("3. Delete order");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 4) {
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter waiter id to delete: ");
                    int wid = scanner.nextInt();

                    String deleteWaiterSQL = "DELETE FROM waiterss WHERE wid = ?";
                    PreparedStatement pstmtDeleteWaiter = connection.prepareStatement(deleteWaiterSQL);
                    pstmtDeleteWaiter.setInt(1, wid);

                    int rowsAffectedWaiter = pstmtDeleteWaiter.executeUpdate();

                    if (rowsAffectedWaiter > 0) {
                        System.out.println("Waiter with id " + wid + " has been deleted.");
                    } else {
                        System.out.println("No waiter found with id " + wid);
                    }
                    break;

                case 2:
                    System.out.print("Enter menu id to delete: ");
                    int id = scanner.nextInt();

                    String deleteMenuSQL = "DELETE FROM menus WHERE id = ?";
                    PreparedStatement pstmtDeleteMenu = connection.prepareStatement(deleteMenuSQL);
                    pstmtDeleteMenu.setInt(1, id);

                    int rowsAffectedMenu = pstmtDeleteMenu.executeUpdate();

                    if (rowsAffectedMenu > 0) {
                        System.out.println("Menu item with id " + id + " has been deleted.");
                    } else {
                        System.out.println("No menu item found with id " + id);
                    }
                    break;

                case 3:
                    System.out.print("Enter order number to delete: ");
                    int ord_no = scanner.nextInt();

                    String deleteOrderSQL = "DELETE FROM ordern WHERE ord_no = ?";
                    PreparedStatement pstmtDeleteOrder = connection.prepareStatement(deleteOrderSQL);
                    pstmtDeleteOrder.setInt(1, ord_no);

                    int rowsAffectedOrder = pstmtDeleteOrder.executeUpdate();

                    if (rowsAffectedOrder > 0) {
                        System.out.println("Order with order number " + ord_no + " has been deleted.");
                    } else {
                        System.out.println("No order found with order number " + ord_no);
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private void displayTable() throws SQLException {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Display waiter table");
            System.out.println("2. Display customer table");
            System.out.println("3. Display menu table");
            System.out.println("4. Display orders table");
            System.out.println("5. Back to Main Menu"); // Option to return to the main menu
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                break; // Exit the loop and return to the main menu
            }

            switch (choice) {
                case 1:
                    displayTable("waiterss");
                    break;
                case 2:
                    displayTable("customers");
                    break;
                case 3:
                    displayTable("menus");
                    break;
                case 4:
                    displayTable("ordern");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private void displayTable(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the table: " + e.getMessage());
        }
    }

    public void displayMenu() throws SQLException {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Create tables");
            System.out.println("2. Insert records");
            System.out.println("3. Delete records");
            System.out.println("4. Display tables");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createTables();
                    break;
                case 2:
                    insertRecords();
                    break;
                case 3:
                    deleteRecord();
                    break;
                case 4:
                    displayTable();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            restaurant system = new restaurant();
            system.displayMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}