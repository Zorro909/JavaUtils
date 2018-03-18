package javautils.RestAPI;

import java.io.IOException;

import javautils.Parser.ParseAble;
import javautils.XML.XmlElement;

abstract class bxml extends XmlElement {

    public bxml(ParseAble f) throws IOException {
        super(f);
        // TODO Auto-generated constructor stub
    }

    public abstract byte[] toBytes();

    @Override
    public String decode(){
        return "bytes";
    }
}
