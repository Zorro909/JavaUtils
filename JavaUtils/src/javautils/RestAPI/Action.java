package javautils.RestAPI;

import java.util.HashMap;

import javautils.Parser.ParseObject;

public interface Action {

	public ParseObject executeRequest(HashMap<String,String> conf, HashMap<String,String> vars);

    public boolean isRaw();

}
