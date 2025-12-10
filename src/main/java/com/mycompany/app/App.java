package com.mycompany.app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {

    public App() {}

    // 🔹 Méthode utilisée par les tests JUnit
    public String getMessage() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Starting server on port 8080...");

        // Petit serveur HTTP natif
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Endpoint GET /hello → pour Postman
        server.createContext("/hello", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "Hello from my-app via Kubernetes & Jenkins!";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Server started! Waiting for requests...");
    }
}
