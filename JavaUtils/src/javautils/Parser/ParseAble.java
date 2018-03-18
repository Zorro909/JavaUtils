package javautils.Parser;

import java.text.ParseException;

import javautils.XML.NoElementFoundException;


public interface ParseAble {

	ParseObject parse();
	String getId();
	ParseAble fromParser(Section s) throws ParseException, NoElementFoundException;

}
