package com.company;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {
        TCPServer server = new TCPServer();
        server.Init(6666);
    }
}
