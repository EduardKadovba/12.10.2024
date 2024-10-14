import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDataBase {

    public void addHotel(Connection connection, String name, String location, double rating) throws SQLException {
        String sql = "INSERT INTO Hotels (name, location, rating) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setDouble(3, rating);
            statement.executeUpdate();
            System.out.println("Отель успешно добавлен.");
        }
    }

    public void addRoom(Connection connection, String roomNumber, String type, double pricePerNight) throws SQLException {
        int hotelId = getLastHotelId(connection);
        if (hotelId == -1) {
            System.out.println("Нет доступных отелей для добавления комнаты.");
            return;
        }
        String sql = "INSERT INTO Rooms (hotel_id, room_number, type, price_per_night) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            statement.setString(2, roomNumber);
            statement.setString(3, type);
            statement.setDouble(4, pricePerNight);
            statement.executeUpdate();
            System.out.println("Комната успешно добавлена.");
        }
    }

    public void addGuest(Connection connection, String firstName, String lastName, String email, String phoneNumber) throws SQLException {
        String sql = "INSERT INTO Guests (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.executeUpdate();
            System.out.println("Гость успешно добавлен.");
        }
    }

    public void addBooking(Connection connection, String checkInDate, String checkOutDate, double totalPrice) throws SQLException {
        int guestId = getLastGuestId(connection);
        int roomId = getLastRoomId(connection);
        if (guestId == -1 || roomId == -1) {
            System.out.println("Нет доступных гостей или комнат для добавления бронирования.");
            return;
        }
        String sql = "INSERT INTO Bookings (guest_id, room_id, check_in_date, check_out_date, total_price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, guestId);
            statement.setInt(2, roomId);
            statement.setString(3, checkInDate);
            statement.setString(4, checkOutDate);
            statement.setDouble(5, totalPrice);
            statement.executeUpdate();
            System.out.println("Бронирование успешно добавлено.");
        }
    }

    public void addService(Connection connection, String name, String description, double price) throws SQLException {
        String sql = "INSERT INTO Services (name, description, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, price);
            statement.executeUpdate();
            System.out.println("Услуга успешно добавлена.");
        }
    }

    private int getLastHotelId(Connection connection) throws SQLException {
        return getLastId(connection, "Hotels", "hotel_id");
    }

    private int getLastGuestId(Connection connection) throws SQLException {
        return getLastId(connection, "Guests", "guest_id");
    }

    private int getLastRoomId(Connection connection) throws SQLException {
        return getLastId(connection, "Rooms", "room_id");
    }

    private int getLastId(Connection connection, String tableName, String idColumn) throws SQLException {
        String query = "SELECT " + idColumn + " FROM " + tableName + " ORDER BY " + idColumn + " DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(idColumn);
            } else {
                return -1; // Если таблица пуста
            }
        }
    }
}