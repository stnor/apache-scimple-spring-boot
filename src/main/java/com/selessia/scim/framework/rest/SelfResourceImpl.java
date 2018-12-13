/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at

* http://www.apache.org/licenses/LICENSE-2.0

* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package com.selessia.scim.framework.rest;

import com.selessia.scim.framework.exception.UnableToResolveIdException;
import com.selessia.scim.framework.provider.SelfIdResolver;
import edu.psu.swe.scim.spec.protocol.SelfResource;
import edu.psu.swe.scim.spec.protocol.UserResource;
import edu.psu.swe.scim.spec.protocol.attribute.AttributeReferenceListWrapper;
import edu.psu.swe.scim.spec.protocol.data.ErrorResponse;
import edu.psu.swe.scim.spec.protocol.data.PatchRequest;
import edu.psu.swe.scim.spec.protocol.exception.ScimException;
import edu.psu.swe.scim.spec.resources.ScimUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.security.Principal;

@Slf4j
@Component
@Path("/scim/v2/Me")
public class SelfResourceImpl implements SelfResource {

  @Autowired
  UserResource userResource;

  @Autowired
  SelfIdResolver selfIdResolver;

  @Override
  public Response getSelf(AttributeReferenceListWrapper attributes, AttributeReferenceListWrapper excludedAttributes) {
    try {
      String internalId = getInternalId();
      return userResource.getById(internalId, attributes, excludedAttributes);
    } catch (UnableToResolveIdException e) {
      return createErrorResponse(e);
    } catch (ScimException e) {
      return createErrorResponse(e);
    }
  }

  // @Override
  // public Response create(ScimUser resource, AttributeReferenceListWrapper
  // attributes, AttributeReferenceListWrapper excludedAttributes) {
  // String internalId = getInternalId();
  // //TODO check if ids match in request
  // return userResourceImpl.create(resource, attributes, excludedAttributes);
  // }

  @Override
  public Response update(ScimUser resource, AttributeReferenceListWrapper attributes, AttributeReferenceListWrapper excludedAttributes) {
    try {
      String internalId = getInternalId();
      return userResource.update(resource, internalId, attributes, excludedAttributes);
    } catch (UnableToResolveIdException e) {
      return createErrorResponse(e);
    } catch (ScimException e) {
      return createErrorResponse(e);
    }
  }

  @Override
  public Response patch(PatchRequest patchRequest, AttributeReferenceListWrapper attributes, AttributeReferenceListWrapper excludedAttributes) {
    try {
      String internalId = getInternalId();
      return userResource.patch(patchRequest, internalId, attributes, excludedAttributes);
    } catch (UnableToResolveIdException e) {
      return createErrorResponse(e);
    } catch (ScimException e) {
      return createErrorResponse(e);
    }
  }

  @Override
  public Response delete() {
    try {
      String internalId = getInternalId();
      return userResource.delete(internalId);
    } catch (UnableToResolveIdException e) {
      return createErrorResponse(e);
    } catch (ScimException e) {
      return createErrorResponse(e);
    }
  }

  private Response createErrorResponse(ScimException e) {
    ErrorResponse er = new ErrorResponse(e.getStatus(), "Error");
    er.addErrorMessage(e.getMessage());
    return er.toResponse();
  }

  private Response createErrorResponse(UnableToResolveIdException e) {
    ErrorResponse er = new ErrorResponse(e.getStatus(), "Error");
    er.addErrorMessage(e.getMessage());
    return er.toResponse();
  }

  private String getInternalId() throws UnableToResolveIdException {
    Principal callerPrincipal = null; //sessionContext.getCallerPrincipal();

    if (callerPrincipal != null) {
      log.debug("Resolved SelfResource principal to : {}", callerPrincipal.getName());
    } else {
      throw new UnableToResolveIdException(Status.UNAUTHORIZED, "Unauthorized");
    }

    return selfIdResolver.resolveToInternalId(callerPrincipal);
  }
}
