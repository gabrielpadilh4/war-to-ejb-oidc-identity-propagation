package com.example.ejb;

import java.security.Principal;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

@SecurityDomain("web-application-1.0-SNAPSHOT.war")
@Stateless
public class ContextProvider {

    private static final Logger LOG = Logger.getLogger(ContextProvider.class);

    @Resource
    public SessionContext sessionContext;

    @PermitAll
    public SessionContext getSessionContext() {
        final Principal principal = sessionContext.getCallerPrincipal();

        LOG.debugf("EJB Principal class %s", principal.getClass());
        LOG.debugf("EJB Principal username %s", principal.getName());

        return this.sessionContext;
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }
    
    public Principal getPrincipal() {
        return sessionContext.getCallerPrincipal();
    } 
}
