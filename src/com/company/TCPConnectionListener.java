package com.company;

import java.io.IOException;

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
            if (server.IsOnline)
            {
                System.out.println("<SYSTEM ERROR> " + x.getMessage());
            }
        }
    }

    public TCPConnectionListener(TCPServer serv)
    {
        server = serv;
        totalConnections = 0;
    }

    private void ListenForClients() throws IOException
    {
        while (server.IsOnline) // REPLACE THIS
        {
            TCPClientListener client = new TCPClientListener(server, server.serverSocket.accept(), totalConnections);
            server.clientList.add(client);
            new Thread(client).start();
            totalConnections++;
        }
    }

}
