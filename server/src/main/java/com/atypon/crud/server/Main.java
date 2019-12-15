package com.atypon.crud.server;

import com.atypon.crud.server.core.ServerThread;

import java.sql.SQLException;

/** Main class that runs the application through the main method. */
public class Main {

  public static void main(String[] args) {
    ServerThread serverThread = new ServerThread();
    serverThread.start();
  }
}
