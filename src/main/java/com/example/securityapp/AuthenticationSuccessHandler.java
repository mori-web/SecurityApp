package com.example.securityapp;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationSuccessHandler implements
    org.springframework.security.web.authentication.AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    Set<String>roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

    System.out.println("『" + request + "』");

    if(roles.contains("ROLE_ADMIN")) {
      response.sendRedirect(request.getContextPath() + "/admin");
    } else if (roles.contains("ROLE_USER")) {
      response.sendRedirect(request.getContextPath() + "/secret");
    } else {
      response.sendRedirect(request.getContextPath() + "/login?error");
    }

  }
}

