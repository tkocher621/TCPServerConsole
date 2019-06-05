package com.company;

import java.util.Scanner;
import java.net.*;

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
            System.out.println("Error: " + x.getMessage());
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
            System.out.println("Disconnected client " + id + ".");
        }
        catch (Exception x)
        {
            System.out.println("ERROR: Invalid UID");
        }
    }

    private void ListenForCommands()
    {
        while (true) // while server is online
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
                    // shutdown code
                }
                else
                {
                    System.out.println("Invalid command.");
                }
            }
        }
    }

}
