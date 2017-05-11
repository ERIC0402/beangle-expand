package org.beangle.website.cms.template.filter;

import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HtmlResponseWrapper extends HttpServletResponseWrapper {

	private CharArrayWriter output;

	public HtmlResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new CharArrayWriter();
	}

	public PrintWriter getWriter() {
		return new PrintWriter(output);
	}

	public void writeFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		fw.write(output.toCharArray());
		fw.close();
	}

	public void writeResponse(PrintWriter out) {
		out.print(output.toCharArray());
	}

}
