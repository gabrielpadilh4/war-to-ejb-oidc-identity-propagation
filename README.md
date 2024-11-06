### War to EJB Elytron OIDC identity propagation

The identity inside the EJB is anonymous:
```
2024-11-06 17:51:30,521 DEBUG [com.example.ejb.ContextProvider] (default task-2) EJB Principal class class org.wildfly.security.auth.principal.AnonymousPrincipal
2024-11-06 17:51:30,521 DEBUG [com.example.ejb.ContextProvider] (default task-2) EJB Principal username anonymous
```

As well, the method `sessionContext.getCallerPrincipal();` called from the web application throws the null pointer exception:
```
2024-11-06 17:51:30,525 ERROR [io.undertow.request] (default task-2) UT005023: Exception handling request to /web-application/pages/example.xhtml: jakarta.servlet.ServletException: Cannot invoke "org.jboss.invocation.InterceptorContext.getPrivateData(java.lang.Class)" because the return value of "org.jboss.as.ejb3.context.CurrentInvocationContext.get()" is null
	at jakarta.faces.impl@4.0.7.redhat-00001//jakarta.faces.webapp.FacesServlet.executeLifecyle(FacesServlet.java:709)
...
Caused by: java.lang.NullPointerException: Cannot invoke "org.jboss.invocation.InterceptorContext.getPrivateData(java.lang.Class)" because the return value of "org.jboss.as.ejb3.context.CurrentInvocationContext.get()" is null
	at org.jboss.as.ejb3@8.0.6.SP1-redhat-00001//org.jboss.as.ejb3.component.EJBComponent.getCallerSecurityIdentity(EJBComponent.java:638)
	at org.jboss.as.ejb3@8.0.6.SP1-redhat-00001//org.jboss.as.ejb3.component.EJBComponent.getCallerPrincipal(EJBComponent.java:274)
	at org.jboss.as.ejb3@8.0.6.SP1-redhat-00001//org.jboss.as.ejb3.context.EJBContextImpl.getCallerPrincipal(EJBContextImpl.java:52)
	at deployment.web-application-1.0-SNAPSHOT.war//com.example.interceptor.AuditControlInterceptor.interceptMethod(AuditControlInterceptor.java:40)
	...
```