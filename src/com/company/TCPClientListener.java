package com.company;

import java.io.*;
import java.net.*;

public class TCPClientListener implements Runnable {

    public String clientName;
    public PrintWriter writer;
    public int clientID;
    public Socket socket;
    public boolean isConnected;

    private TCPServer server;
    private BufferedReader reader;

    public void run()
    {
        try
        {
            ListenForMessage();
        }
        catch (IOException x)
        {
            // Client disconnected error, no need to output this on server
            //System.out.println("Error: " + x.getMessage());
        }
    }

    public TCPClientListener(TCPServer serv, Socket clientSocket, int id) throws IOException
    {
        server = serv;
        clientID = id;
        socket = clientSocket;
        isConnected = true;
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientName = reader.readLine();
        System.out.println("Client '" + clientName +"' (" + clientID + ") connected.");
    }

    public void Disconnect() throws IOException
    {
        socket.getInputStream().close();
        isConnected = false;
        server.clientList.remove(this);
    }

    public void ListenForMessage() throws IOException
    {
        while (isConnected)
        {
            String data = reader.readLine();
            if (data == null) break;
            System.out.println(clientName + ": " + data);

            server.SendToAllClients(clientName, data);
        }

        System.out.println("Client '" + clientName +"' (" + clientID + ") disconnected.");
    }
}
