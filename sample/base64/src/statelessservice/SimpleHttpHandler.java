package statelessservice;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class SimpleHttpHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!"POST".equals(request.getMethod())) {
			response.setStatus(405);
			baseRequest.setHandled(true);

			return;
		}

		String body = request.getReader().lines().collect(Collectors.joining("\n"));
		String encoded = Base64.getUrlEncoder().encodeToString(body.getBytes());
		response.setStatus(200);
		response.getWriter().write(encoded);
		response.setContentType(MimeTypes.Type.TEXT_PLAIN_UTF_8.getCharsetString());
		baseRequest.setHandled(true);
	}

}
