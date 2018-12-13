package com.selessia.scim;

import com.selessia.scim.framework.exception.FilterParseExceptionMapper;
import com.selessia.scim.framework.provider.ProviderRegistry;
import com.selessia.scim.framework.rest.*;
import com.selessia.scim.service.GroupProviderImpl;
import com.selessia.scim.service.UserProviderImpl;
import edu.psu.swe.scim.spec.resources.ScimGroup;
import edu.psu.swe.scim.spec.resources.ScimUser;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JerseyConfig extends ResourceConfig {

    @Autowired
    BulkResourceImpl bulkResource;

    @Autowired
    GroupResourceImpl groupResource;

    @Autowired
    ResourceTypesResourceImpl resourceTypesResource;

    @Autowired
    SchemaResourceImpl schemaResource;

    @Autowired
    SearchResourceImpl searchResource;

    @Autowired
    SelfResourceImpl selfResource;

    @Autowired
    ServiceProviderConfigResourceImpl serviceProviderConfigResource;

    @Autowired
    UserResourceImpl userResource;

    @Autowired
    FilterParseExceptionMapper filterParseExceptionMapper;

    @Autowired
    ObjectMapperContextResolver objectMapperContextResolver;

    @Autowired
    private ProviderRegistry providerRegistry;

    @Autowired
    private UserProviderImpl userProviderInstance;

    @Autowired
    private GroupProviderImpl groupProviderInstance;

    @PostConstruct
    public void register() {
        register(bulkResource)
                .register(groupResource)
                .register(resourceTypesResource)
                .register(schemaResource)
                .register(searchResource)
                .register(selfResource)
                .register(serviceProviderConfigResource)
                .register(userResource)
                .register(filterParseExceptionMapper)
                .register(objectMapperContextResolver)
        ;

        try {
            providerRegistry.registerProvider(ScimUser.class, userProviderInstance);
            providerRegistry.registerProvider(ScimGroup.class, groupProviderInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
