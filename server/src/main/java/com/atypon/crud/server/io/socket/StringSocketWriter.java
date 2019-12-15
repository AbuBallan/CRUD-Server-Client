package com.atypon.crud.server.io.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * An implementation for SocketWriter for String, for more detailed documentation: {@see
 * SocketWriter}.
 */
public class StringSocketWriter implements SocketWriter<String> {

  private Socket socket;
  private final OutputStream outputStream;
  private final ObjectOutputStream out;

  /**
   * * Parameterized constructor to initialize the StringSocketWriter.
   *
   * @param socket the socket that we will write to
   * @throws IOException
   */
  public StringSocketWriter(Socket socket) throws IOException {
    if (socket == null) throw new IllegalArgumentException();
    this.socket = socket;
    outputStream = socket.getOutputStream();
    out = new ObjectOutputStream(outputStream);
  }

  @Override
  public void write(String value) throws IOException {
    out.writeUTF(value);
  }

  @Override
  public void close() throws Exception {
    out.close();
    outputStream.close();
  }
}
