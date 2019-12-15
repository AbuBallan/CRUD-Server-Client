package com.atypon.crud.server.io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * An implementation for SocketReader for String, for more detailed documentation: {@see
 * SocketReader}.
 */
public class StringSocketReader implements SocketReader<String> {

  private Socket socket;
  private final InputStream inputStream;
  private final ObjectInputStream in;

  /**
   * * Parameterized constructor to initialize the StringSocketReader.
   *
   * @param socket the socket that we will read from
   * @throws IOException
   */
  public StringSocketReader(Socket socket) throws IOException {

    if (socket == null) throw new IllegalArgumentException();

    this.socket = socket;
    inputStream = socket.getInputStream();
    in = new ObjectInputStream(inputStream);
  }

  @Override
  public String readValue() throws IOException {
    return in.readUTF();
  }

  @Override
  public void close() throws Exception {
    inputStream.close();
    in.close();
  }
}
