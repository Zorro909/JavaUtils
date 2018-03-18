package javautils.tcpmanager;

/**
 * @author Max An Listener for a Client
 */
public interface TcpClientListener {

  /**
   * Occures when the given Client receives a Message
   *
   * @param message
   *          The Received Message
   * @param connection
   *          The TcpConnection object from where it comes
   */
  public void input(String message, TcpConnection connection);

}
