package com.gwenson.search.interceptor;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class InterfaceInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(InterfaceInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse rsp, Object obj, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse rsp, Object obj, ModelAndView mode)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse rsp, Object obj) throws Exception {
		String referer = req.getHeader("Referer");
		try {
			URL u = new URL(referer);
			String host = u.getHost().toLowerCase();
			logger.debug("request referer host=" + host);

			if ((host.equals("gwenson.com")) || (host.endsWith(".gwenson.com"))) {
				addP3PHeader(rsp);
				fixCrossDomain(rsp, req.getHeader("Origin"));
			}

			/*if ((host.equals("xkjzx.com")) || (host.endsWith(".xkjzx.com"))) {
				addP3PHeader(rsp);
				fixCrossDomain(rsp, req.getHeader("Origin"));
			}*/
		} catch (MalformedURLException localMalformedURLException) {
		}
		return true;
	}

	private void addP3PHeader(HttpServletResponse rsp) {
		rsp.setHeader("P3P", "CP=CAO PSA OUR");
	}

	private void fixCrossDomain(HttpServletResponse rsp, String origin) {
		rsp.setHeader("Access-Control-Allow-Origin", origin);
		rsp.setHeader("Access-Control-Allow-Credentials", "true");
	}
}
