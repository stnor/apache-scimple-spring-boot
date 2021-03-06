package com.selessia.scim.framework.rest;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class BaseWebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    public Response toResponse(WebApplicationException e) {
        ErrorMessage em = new ErrorMessage(Status.BAD_REQUEST);

        Response waeResponse = e.getResponse();
        Status waeStatus = Status.fromStatusCode(waeResponse.getStatus());
        em.setStatus(waeStatus);

        if (waeStatus.getFamily().equals(Family.CLIENT_ERROR)) {
            em.addErrorMessage(e.getMessage());
            log.info(e.getMessage());

        } else if (waeStatus.getFamily().equals(Family.SERVER_ERROR)) {
            log.error(e.getMessage(), e);
        }

        return em.toResponse();
    }
}
