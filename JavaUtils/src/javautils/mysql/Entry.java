package javautils.mysql;

import java.util.ArrayList;
import java.util.HashMap;

public class Entry {

  String update_entry_string = "";
  ArrayList<String> vals = new ArrayList<String>();

  public Entry(String table) {
    update_entry_string = "INSERT INTO "
            + table + " (table_variables) VALUES (table_values) ON DUPLICATE KEY UPDATE ";
  }

  public void setValues(HashMap<String, String> h) {
    vals.clear();
    String v = "";
    for (String s : h.keySet()) {
      v = v + s + ",";
      vals.add(h.get(s));
    }
    if (v.contains(",")) {
      v = v.substring(0, v.lastIndexOf(","));
    }
    String values = "";
    for (String s : h.keySet()) {
      values = values + "?,";
    }
    if (values.contains(",")) {
      values = values.substring(0, values.lastIndexOf(","));
    }
    update_entry_string = update_entry_string.replace("table_variables", v);
    update_entry_string = update_entry_string.replace("table_values", values);
    for (String s : h.keySet()) {
      update_entry_string = update_entry_string + " " + s + "=?, ";
    }
    update_entry_string = update_entry_string.substring(0, update_entry_string.lastIndexOf(","));
    System.out.println(update_entry_string);
  }

  String getString() {
    return update_entry_string;
  }

  ArrayList<String> getValues(){
    return vals;
  }
}
