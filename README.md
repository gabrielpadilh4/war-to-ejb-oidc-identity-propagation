### War to EJB Elytron OIDC identity propagation

- Create the virtual security domain
```
/subsystem=elytron/virtual-security-domain=web-application-1.0-SNAPSHOT.war:add()
```

Specified the security domain annotation in the ejb:
```
@SecurityDomain("web-application-1.0-SNAPSHOT.war")
@Stateless
public class ContextProvider {
```

1. We see the error message of method not allowed
```
jakarta.servlet.ServletException: WFLYEJB0364: Invocation on method: public jakarta.ejb.SessionContext com.example.ejb.ContextProvider.getSessionContext() of bean: ContextProvider is not allowed
	at jakarta.faces.impl@4.0.7.redhat-00001//jakarta.faces.webapp.FacesServlet.executeLifecyle(FacesServlet.java:709)
	at jakarta.faces.impl@4.0.7.redhat-00001//jakarta.faces.webapp.FacesServlet.service(FacesServlet.java:449)
```

To fix this, the annotation @PermitAll has been added in the method:
```
    @PermitAll
    public SessionContext getSessionContext() {
        final Principal principal = sessionContext.getCallerPrincipal();

        LOG.debugf("EJB Principal class %s", principal.getClass());
        LOG.debugf("EJB Principal username %s", principal.getName());

        return this.sessionContext;
    }
```

Via security domain annotation in EJB, the identity inside the EJB is the user:
```
2024-11-06 18:22:38,399 DEBUG [com.example.ejb.ContextProvider] (default task-2) EJB Principal class class org.wildfly.security.http.oidc.OidcPrincipal
2024-11-06 18:22:38,399 DEBUG [com.example.ejb.ContextProvider] (default task-2) EJB Principal username gabriel
```

As well, the method `sessionContext.getCallerPrincipal();` called from the web application throws the same null pointer exception:
```
jakarta.servlet.ServletException: Cannot invoke "org.jboss.invocation.InterceptorContext.getPrivateData(java.lang.Class)" because the return value of "org.jboss.as.ejb3.context.CurrentInvocationContext.get()" is null
	at jakarta.faces.impl@4.0.7.redhat-00001//jakarta.faces.webapp.FacesServlet.executeLifecyle(FacesServlet.java:709)
...
Caused by: java.lang.NullPointerException: Cannot invoke "org.jboss.invocation.InterceptorContext.getPrivateData(java.lang.Class)" because the return value of "org.jboss.as.ejb3.context.CurrentInvocationContext.get()" is null
	at org.jboss.as.ejb3@8.0.6.SP1-redhat-00001//org.jboss.as.ejb3.component.EJBComponent.getCallerSecurityIdentity(EJBComponent.java:638)
	at org.jboss.as.ejb3@8.0.6.SP1-redhat-00001//org.jboss.as.ejb3.component.EJBComponent.getCallerPrincipal(EJBComponent.java:274)
	...
```