/**
 * Copyright 2014 Juliana Louback
 * This file is part of DialAssist.

    DialAssist is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DialAssist is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DialAssist.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Servlet implementation class DialServlet
 */
@WebServlet("/dialservlet")
public class DialServlet extends HttpServlet {
	
	static final Logger logger = LoggerFactory.getLogger(DialServlet.class);
	
	private static final long serialVersionUID = 1L;
	String sipDomain;
	String webURL;
	
	
	public void init() throws ServletException{
		
		this.sipDomain = getInitParameter("sipDomain");
		logger.debug("SIP Domain: {}", sipDomain);
		
		this.webURL = getInitParameter("webURL");
		logger.debug("Web URL: {} ", webURL);
		
	}      
    public DialServlet() {
    	super();   
        // TODO Auto-generated constructor stub
    }
    
    protected String makeUrl(String number) throws UnsupportedEncodingException {
    	// return "http://webrtc.lumicall.org/?dial=" + number;
    	String sipUri = number + "@" + sipDomain;
    	String sipUriEncoded = URLEncoder.encode(sipUri, "UTF-8");
    	return webURL + sipUriEncoded + "&video=false";
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("fullNumber");
		logger.debug("User requested to dial: {} ", number);
		
		response.sendRedirect(makeUrl(number));
		
	}

}
