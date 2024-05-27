package org.example;

import org.example.client.ClientService;
import org.flywaydb.core.Flyway;

public class Main {
    public static void main(String[] args) {

        ClientService clientService = new ClientService(Database.getInstance().getConnection());
        initDb();
        System.out.println("clientService.create(Jon) = " + clientService.create("Jon"));
        System.out.println("clientService.getById(1) = " + clientService.getById(1));
        clientService.setName(2, "Jon");
        clientService.deleteById(3);
        System.out.println("clientService.listAll() = " + clientService.listAll());

    }
    private static void initDb(){
        Flyway flyway = Flyway
                .configure()
                .dataSource("jdbc:h2:./test", null, null)
                .load();

        flyway.migrate();
    }
}