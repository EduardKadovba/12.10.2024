import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        MyDataBase myDataBase = new MyDataBase();
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Добавить отель");
                System.out.println("2. Добавить номер");
                System.out.println("3. Добавить гостя");
                System.out.println("4. Добавить бронирование");
                System.out.println("5. Добавить услугу");
                System.out.println("0. Выйти");
                System.out.print("Выберите опцию: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера ввода

                switch (choice) {
                    case 1:
                        addHotel(scanner, myDataBase, connection);
                        break;
                    case 2:
                        addRoom(scanner, myDataBase, connection);
                        break;
                    case 3:
                        addGuest(scanner, myDataBase, connection);
                        break;
                    case 4:
                        addBooking(scanner, myDataBase, connection);
                        break;
                    case 5:
                        addService(scanner, myDataBase, connection);
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неправильный выбор. Пожалуйста, попробуйте снова.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addHotel(Scanner scanner, MyDataBase myDataBase, Connection connection) throws SQLException {
        System.out.print("Введите название отеля: ");
        String hotelName = scanner.nextLine();
        System.out.print("Введите местоположение отеля: ");
        String location = scanner.nextLine();
        System.out.print("Введите рейтинг отеля: ");
        double rating = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        myDataBase.addHotel(connection, hotelName, location, rating);
    }

    private static void addRoom(Scanner scanner, MyDataBase myDataBase, Connection connection) throws SQLException {
        System.out.print("Введите номер комнаты: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Введите тип номера: ");
        String type = scanner.nextLine();
        System.out.print("Введите стоимость за ночь: ");
        double pricePerNight = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        myDataBase.addRoom(connection, roomNumber, type, pricePerNight);
    }

    private static void addGuest(Scanner scanner, MyDataBase myDataBase, Connection connection) throws SQLException {
        System.out.print("Введите имя гостя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию гостя: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите email гостя: ");
        String email = scanner.nextLine();
        System.out.print("Введите номер телефона гостя: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Ввкдите название отеля: ");
        myDataBase.addGuest(connection, firstName, lastName, email, phoneNumber);
    }

    private static void addBooking(Scanner scanner, MyDataBase myDataBase, Connection connection) throws SQLException {
        System.out.print("Введите дату заезда (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Введите дату выезда (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();
        System.out.print("Введите общую стоимость: ");
        double totalPrice = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        myDataBase.addBooking(connection, checkInDate, checkOutDate, totalPrice);
    }

    private static void addService(Scanner scanner, MyDataBase myDataBase, Connection connection) throws SQLException {
        System.out.print("Введите название услуги: ");
        String serviceName = scanner.nextLine();
        System.out.print("Введите описание услуги: ");
        String description = scanner.nextLine();
        System.out.print("Введите стоимость услуги: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        myDataBase.addService(connection, serviceName, description, price);
    }
}