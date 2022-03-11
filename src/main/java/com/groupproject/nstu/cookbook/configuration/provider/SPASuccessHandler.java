package com.groupproject.nstu.cookbook.configuration.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SPASuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final ObjectMapper mapper;

    public SPASuccessHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getOutputStream().print(mapper.writeValueAsString(authentication.getPrincipal()));

        clearAuthenticationAttributes(request);
    }
}
