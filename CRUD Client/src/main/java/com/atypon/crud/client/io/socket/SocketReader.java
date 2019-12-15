package com.atypon.crud.client.io.socket;

import java.io.IOException;

/**
 * * Socket Reader interface that read Value
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
