package com.company;
import java.io.*;
import java.util.*;
import java.net.ServerSocket;

public class TCPServer {

    public List<TCPClientListener> clientList = new ArrayList<>();
    public ServerSocket serverSocket;
    public boolean IsOnline = false;

    public void Init(int port) throws IOException
    {
        System.out.println("Server started on port localhost with port " + port + ". Listening for clients...");
        serverSocket = new ServerSocket(port);
        IsOnline = true;
        new Thread(new TCPServerCommandListener(this)).start();
        new Thread(new TCPConnectionListener(this)).start();
    }

    public void DisconnectClient(TCPClientListener client) throws IOException
    {
        client.socket.getInputStream().close();
    }

    public void ShutdownServer() throws IOException
    {
        System.out.println("<SYSTEM> Server shutting down...");
        for (TCPClientListener client : clientList)
        {
            client.WriteMessage("Server shutting down...");
            DisconnectClient(client);
        }
        IsOnline = false;
        serverSocket.close();
        System.out.println("<SYSTEM> Server closed.");
    }

    public void SendToAllClients(String name, String message)
    {
        for (TCPClientListener client : clientList)
        {
            if (!client.clientName.equals(name) && message != null && name != null)
            {
                client.writer.println(name + ": " + message);
            }
        }
    }

}
