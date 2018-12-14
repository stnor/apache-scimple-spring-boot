Apache SCIMple Example using Spring
===================================

This example is a self contained SCIM server using Apache SCIMple.

All EJB 3.0 and CDI dependencies are removed and it's a pure Spring boot 2.0 application.

To run, clone the project and run `mvn spring-boot:run`

There is support for JWT authentication, but it's turned off in the code.

The endpoints are available under /scim/v2/ eg. /scim/v2/Users.

## Acknowledgements
* Code and dependencies from Apache Directory SCIMple https://github.com/apache/directory-scimple/tree/develop/scim-server
* Inspiration from @bdemers https://github.com/bdemers/apache-scimple-spring-demo
