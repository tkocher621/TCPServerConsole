package com.company;

import java.io.*;
import java.net.*;

public class TCPClientListener implements Runnable {

    public String clientName;
    public PrintWriter writer;

    private TCPServer server;
    private int clientID;
    private BufferedReader reader;

    public void run()
    {
        try
        {
            ListenForMessage();
        }
        catch (IOException x)
        {
            System.out.println("Error: " + x.getMessage());
        }
    }

    public TCPClientListener(TCPServer serv, Socket clientSocket, int id) throws IOException
    {
        server = serv;
        clientID = id;
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientName = reader.readLine();
        System.out.println("Client '" + clientName +"' (" + clientID + ") connected.");
    }

    public void ListenForMessage() throws IOException
    {
        while (true)
        {
            String data = reader.readLine();
            if (data == null) break;
            System.out.println(clientName + ": " + data);

            server.SendToAllClients(clientName, data);
        }

        System.out.println("Client '" + clientName +"' (" + clientID + ") disconnected.");
    }
}
