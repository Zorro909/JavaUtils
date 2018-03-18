package javautils.tcpmanager;

import java.io.File;

public class TcpServerMode {
  public static TcpServerMode NO_ENCRYPTION = new TcpServerMode("NO_ENCRYPTION");
  public static TcpServerMode SSL_MODE      = new TcpServerMode("SSL_MODE");

  File   certificate;
  String password;
  int    port = 80;
  String enc;

  public TcpServerMode(String string) {
    enc = string;
  }

  public String getEncryption() {
    return enc;
  }

  public File getCertificateFile() {
    return certificate;
  }

  public String getCertificatePassword() {
    return password;
  }

  public TcpServerMode setCertificate(File certificate, String password) {
    this.certificate = certificate;
    this.password = password;
    return this;
  }

  public TcpServerMode setPort(int port) {
    TcpServerMode m = new TcpServerMode(enc);
    m.port = port;
    return m;
  }

  public int getPort() {
    return port;
  }
}
