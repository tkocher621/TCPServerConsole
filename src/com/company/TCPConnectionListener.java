package com.company;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPConnectionListener implements Runnable {

    private TCPServer server;
    private int totalConnections;

    public void run()
    {
        try
        {
            ListenForClients();
        }
        catch (IOException x)
        {
            System.out.println("Error: " + x.getMessage());
        }
    }

    public TCPConnectionListener(TCPServer serv)
    {
        server = serv;
        totalConnections = 0;
    }

    private void ListenForClients() throws IOException
    {
        while (true) // REPLACE THIS
        {
            TCPClientListener client = new TCPClientListener(server, server.serverSocket.accept(), totalConnections);
            server.clientList.add(client);
            new Thread(client).start();
            totalConnections++;
        }
    }

}
