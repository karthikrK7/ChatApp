package com.example.demo.Config;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 *
 * @author malike_st
 */

@Component

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
	
	//You can include more than one origin here
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200"); 

	@Override
	public void init(FilterConfig filterConfig) {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		// Access-Control-Allow-Origin
        String origin = request.getHeader("Origin");
		
		//response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		// Access-Control-Allow-Credentials
        response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization,Origin, X-Requested-With, Content-Type, Accept, grant_type, access_token");

		// http://stackoverflow.com/questions/30632200/standalone-spring-oauth2-jwt-
		// authorization-server-cors
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}

	}

	@Override
	public void destroy() {
	}
}
