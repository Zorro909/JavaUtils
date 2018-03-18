package javautils.mysql;

public enum DATA_TYPE {
  INT("INT(11)"), TINYINT("TINYINT(4)"), SMALLINT("SMALLINT(5)"), MEDIUMINT("MEDIUMINT(9)"),
  BIGINT("BIGINT(20)"), FLOAT("FLOAT(10,2)"), DOUBLE("DOUBLE(16,4)"), DATE("DATE"),
  DATETIME("DATETIME"), TIMESTAMP("TIMESTAMP"), TIME("TIME"), YEAR("YEAR(4)"), CHAR("CHAR(1)"),
  VARCHAR("VARCHAR(255)"), TEXT("TEXT"), TINYTEXT("TINYTEXT"), MEDIUMTEXT("MEDIUMTEXT"),
  LARGETEXT("LONGTEXT"), BOOLEAN("BOOLEAN");
  String text = "";

  DATA_TYPE(String s) {
    this.text = s;
  }

  public String toString() {
    return text;
  }
}
