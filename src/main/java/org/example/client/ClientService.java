package org.example.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientService {
    private Connection connection;
    public ClientService(Connection connection) {
        this.connection = connection;
    }



    public long create(String name) {
        if (name.length() < 2 || name.length() > 1000){
            throw new IllegalArgumentException("Name's length must be greater than 2 and less than 1000");
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO client (name) VALUES ?")){

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        long id = 0L;

        try (PreparedStatement statement = connection.prepareStatement("SELECT max(id) as max_id FROM client");
             ResultSet resultSet = statement.executeQuery()){

            resultSet.next();
            id = resultSet.getLong("max_id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
    public String getById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than 1");
        }

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("name");
            }else {
                throw new NoSuchElementException("There is no client with id " + id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }


    public void setName(long id, String name) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than 1");
        }

        if (name.length() < 2 || name.length() > 1000){
            throw new IllegalArgumentException("Name's length must be greater than 2 and less than 1000");
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?")){

            statement.setString(1, name);
            statement.setLong(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM client WHERE id = ?")){
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM client");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                clients.add(client);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return clients;
    }

}
