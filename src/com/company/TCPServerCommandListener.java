package com.company;

import java.io.IOException;
import java.util.Scanner;

public class TCPServerCommandListener implements Runnable {

    private TCPServer server;
    private Scanner scan = new Scanner(System.in);

    public void run()
    {
        try
        {
            ListenForCommands();
        }
        catch (Exception x)
        {
            System.out.println("<SYSTEM ERROR> " + x.getMessage());
        }
    }

    public TCPServerCommandListener(TCPServer serv)
    {
        server = serv;
    }

    private TCPClientListener GetClient(int id)
    {
        for (TCPClientListener client : server.clientList)
        {
            if (client.clientID == id) return client;
        }
        return null;
    }

    private void TryDisconnectClient(String data)
    {
        try
        {
            int id = Integer.parseInt(data);
            TCPClientListener client =  GetClient(id);
            client.Disconnect();
            System.out.println("<SYSTEM> Disconnected client " + id + ".");
        }
        catch (Exception x)
        {
            System.out.println("<SYSTEM ERROR>: Invalid UID");
        }
    }

    private void ListenForCommands() throws IOException
    {
        while (server.IsOnline) // while server is online
        {
            String data = scan.nextLine();
            if (!data.equals(""))
            {
                data = data.toLowerCase();
                if (data.startsWith("dc") || data.startsWith("disconnect"))
                {
                    TryDisconnectClient(data.length() > 10 ? data.substring(11) : data.substring(3));
                }
                else if (data.startsWith("sd") || data.startsWith("shutdown"))
                {
                    server.ShutdownServer();
                }
                else
                {
                    System.out.println("<SYSTEM ERROR> Invalid command.");
                }
            }
        }
    }

}
