package com.atypon.crud.client.io.socket;

import java.io.IOException;

/**
 * * Socket Writer interface that write Value
 *
 * @param <T> the type of value that SocketWriter write.
 */
public interface SocketWriter<T> extends AutoCloseable {

  /**
   * * write value to the socket
   *
   * @param value the output to the socket
   * @throws IOException when socket is closed or Occur IOException
   */
  void write(T value) throws IOException;
}
