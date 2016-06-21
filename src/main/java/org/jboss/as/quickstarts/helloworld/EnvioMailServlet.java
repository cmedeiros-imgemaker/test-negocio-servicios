package org.jboss.as.quickstarts.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.subdere.componente.negocio.contracts.SendEmailInput;
import cl.subdere.componente.negocio.exceptions.NegocioException;
import cl.subdere.componente.negocio.negocio.SenderEmailManagerRemote;

/**
 * Servlet implementation class EnvioMailServlet
 */
@SuppressWarnings("serial")
@WebServlet("/EnvioMail")
public class EnvioMailServlet extends HttpServlet {
	   
	static String PAGE_HEADER = "<html><head><title>helloworld</title></head><body>";
	static String PAGE_FOOTER = "</body></html>";

	@EJB(mappedName = "java:global/negocio-subdere/SenderEmail!cl.subdere.componente.negocio.negocio.SenderEmailManagerRemote")
	SenderEmailManagerRemote senderEmail;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnvioMailServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Envio mail inicia");
		SendEmailInput input = new SendEmailInput();
		input.setTo(request.getParameter("toAddress"));
		input.setSubject(request.getParameter("subject"));
		input.setType(1);
		try {
			senderEmail.sendAsync(input);
		} catch (NegocioException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(PAGE_HEADER);
        writer.println("<h1>Email enviado</h1><br/><button onclick='javascript:history.back(1);'>Volver</button>");
        writer.println(PAGE_FOOTER);
        writer.close();
	}

}
