package com.company;
import java.io.*;
import java.util.*;
import java.net.ServerSocket;

public class TCPServer extends Thread {

    private int numClients;
    private List<TCPClientListener> clientList = new ArrayList<>();
    private ServerSocket serverSocket;

    public void Init(int port) throws IOException
    {
        System.out.println("Server started on port localhost with port " + port + ". Listening for clients...");
        numClients = 0;
        serverSocket = new ServerSocket(port);

        while (true) // REPLACE THIS
        {
            TCPClientListener client = new TCPClientListener(this, serverSocket.accept(), numClients);
            clientList.add(client);
            new Thread(client).start();
            numClients++;
        }

    }

    public void SendToAllClients(String name, String message)
    {
        for (TCPClientListener client : clientList)
        {
            if (client.clientName != name && message != null && name != null)
            {
                client.writer.println(name + ": " + message);
            }
        }
    }

}
