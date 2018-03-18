package javautils.RestAPI;

import java.util.HashMap;

import javautils.Parser.ParseObject;

public abstract class RawAction implements Action {

  @Override
  public ParseObject executeRequest(HashMap<String, String> conf, HashMap<String, String> vars) {
    ParseObject po = new ParseObject("");
    po.add("raw", execute(conf,vars));
    po.add("content_type", getContentType());
    return null;
  }

  public abstract String execute(HashMap<String, String> conf, HashMap<String, String> vars);

  public String getContentType() {
    return "text/plain; charset=utf-8";
  }



  @Override
  public boolean isRaw() {
    return true;
  }



}
