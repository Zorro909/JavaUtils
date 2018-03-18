package javautils.tcpmanager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Einfacher TcpServer, der selbst nach Clients Ausschau hält.
 *
 * @author Max
 * @see TCPManager
 * @see TcpConnection
 */
public class TcpServer {

  private ServerSocket             s;
  private SSLServerSocket          ss;
  private ArrayList<TcpConnection> clients = new ArrayList<TcpConnection>();

  /**
   * @param port
   *          The Port where The Server runs on
   */
  TcpServer(int port) throws IOException {
    s = new ServerSocket(port);
  }

  public TcpServer(int port, SSLServerSocketFactory ssl) throws IOException {
    ss = (SSLServerSocket) ssl.createServerSocket(port);
    for (String s : ss.getEnabledCipherSuites()) {
      System.out.println(s);
    }
  }

  /**
   * Looks for a Client that would connect
   *
   * @return The Connecting Client
   */
  public TcpConnection lookForClients() throws IOException {
    TcpConnection con;
    try {
      con = new TcpConnection(
              (s != null ? s.accept() : ss.accept()), TCPManager.connections.size());
    } catch (SocketException e) {
      return null;
    }
    addClient(con);
    return con;
  }

  void addClient(TcpConnection c) {
    clients.add(c);
  }

  /**
   * Stops the Server
   *
   * @return If the Server is successfully stopped
   */
  public boolean stopServer() {
    try {
      if (s != null) {
        s.close();
      } else {
        ss.close();
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public ArrayList<TcpConnection> getClients() {
    ArrayList<TcpConnection> remove = new ArrayList<TcpConnection>();
    for (TcpConnection c : clients) {
      if (!c.isConnected()) {
        remove.add(c);
      }
    }
    clients.removeAll(remove);
    return clients;
  }

  public boolean isOnline() {
    return !(s != null ? s.isClosed() : ss.isClosed());
  }

  public int getPort() {
    return s.getLocalPort();
  }

}
