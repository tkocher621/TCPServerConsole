package com.company;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException
    {
        new TCPServer().Init(6666);
    }
}
