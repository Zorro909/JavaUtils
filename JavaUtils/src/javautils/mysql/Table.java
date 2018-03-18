package javautils.mysql;

public class Table {

  String tableCreateString = "";
  String endTable           = "";

  public Table(String name) {
    tableCreateString = "CREATE TABLE IF NOT EXISTS " + name + " (";
  }

  public void addColumn(
          String name,
          String dataType,
          boolean notNull,
          boolean autoIncrement,
          boolean unique,
          boolean key) {
    tableCreateString = tableCreateString
            + name + " " + dataType + (notNull ? " NOT NULL" : "")
            + (autoIncrement ? " AUTO_INCREMENT" : "") + ",";
    if (unique) {
      endTable =
              endTable + "," + (unique ? "UNIQUE " + (key ? "KEY " : "") : "") + "(" + name + ")";
    }
  }

  public void addColumn(
          String name,
          DATA_TYPE varchar,
          boolean notNull,
          boolean autoIncrement,
          boolean unique,
          boolean key) {
    addColumn(name, varchar.toString(), notNull, autoIncrement, unique, key);
  }

  String getString() {
    return tableCreateString.substring(0, tableCreateString.lastIndexOf(",")) + endTable + ")";
  }

}