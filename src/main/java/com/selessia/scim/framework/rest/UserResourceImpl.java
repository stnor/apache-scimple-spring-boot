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

/**
 *
 */
package com.selessia.scim.framework.rest;

import com.selessia.scim.framework.provider.Provider;
import com.selessia.scim.framework.provider.ProviderRegistry;
import edu.psu.swe.scim.spec.protocol.UserResource;
import edu.psu.swe.scim.spec.resources.ScimUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * @author shawn
 *
 */
@Component
@Path("/scim/v2/Users")
public class UserResourceImpl extends BaseResourceTypeResourceImpl<ScimUser> implements UserResource {

  @Autowired
  ProviderRegistry providerRegistry;

  @Override
  public Provider<ScimUser> getProvider() {
    return providerRegistry.getProvider(ScimUser.class);
  }

}
