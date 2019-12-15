package com.atypon.crud.server.io.socket;

import java.io.IOException;

/**
 * * Socket Reader interface that read Value from a socket
 *
 * @param <T> the type of value that SocketReader read.
 */
public interface SocketReader<T> extends AutoCloseable {

  /**
   * * read value from the socket
   *
   * @return the input form socket
   * @throws IOException when socket is closed or Occur IOException
   */
  T readValue() throws IOException;
}
