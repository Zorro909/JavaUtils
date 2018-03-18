package javautils.Parser;

import javautils.XML.NoElementFoundException;

public interface Parser {

	public Section getSection(String name) throws NoElementFoundException;
	public String getValue(String name) throws NoElementFoundException;

}
