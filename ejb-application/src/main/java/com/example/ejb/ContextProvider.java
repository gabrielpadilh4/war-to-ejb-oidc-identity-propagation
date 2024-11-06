package com.example.ejb;

import java.security.Principal;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;

import org.jboss.logging.Logger;

@Stateless
public class ContextProvider {

    private static final Logger LOG = Logger.getLogger(ContextProvider.class);

    @Resource
    public SessionContext sessionContext;

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
