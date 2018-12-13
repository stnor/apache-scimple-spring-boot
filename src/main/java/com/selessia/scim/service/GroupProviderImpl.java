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

package com.selessia.scim.service;

import com.selessia.scim.framework.provider.Provider;
import com.selessia.scim.framework.exception.UnableToUpdateResourceException;
import com.selessia.scim.framework.provider.UpdateRequest;
import edu.psu.swe.scim.spec.protocol.filter.FilterResponse;
import edu.psu.swe.scim.spec.protocol.search.Filter;
import edu.psu.swe.scim.spec.protocol.search.PageRequest;
import edu.psu.swe.scim.spec.protocol.search.SortRequest;
import edu.psu.swe.scim.spec.resources.ScimExtension;
import edu.psu.swe.scim.spec.resources.ScimGroup;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GroupProviderImpl implements Provider<ScimGroup> {

  private Map<String, ScimGroup> groups = new HashMap<>();

  @Override
  public ScimGroup create(ScimGroup resource) {
    String resourceId = resource.getId();
    int idCandidate = resource.hashCode();
    String id = resourceId != null ? resourceId : Integer.toString(idCandidate);

    while (groups.containsKey(id)) {
      id = Integer.toString(idCandidate);
      ++idCandidate;
    }
    groups.put(id, resource);
    resource.setId(id);
    return resource;
  }

  @Override
  public ScimGroup update(UpdateRequest<ScimGroup> updateRequest) throws UnableToUpdateResourceException {
    String id = updateRequest.getId();
    ScimGroup resource = updateRequest.getResource();
    groups.put(id, resource);
    return resource;
  }

  @Override
  public ScimGroup get(String id) {
    return groups.get(id);
  }

  @Override
  public void delete(String id) {
    groups.remove(id);
  }

  @Override
  public FilterResponse<ScimGroup> find(Filter filter, PageRequest pageRequest, SortRequest sortRequest) {
    return new FilterResponse<>(groups.values(), pageRequest, groups.size());
  }

  @Override
  public List<Class<? extends ScimExtension>> getExtensionList() {
    return Collections.emptyList();
  }

}
