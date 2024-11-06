package com.example.ejb;

import java.security.Principal;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.annotation.security.PermitAll;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@SecurityDomain("web-application-1.0-SNAPSHOT.war")
public class ContextProvider {

    @Resource
    public SessionContext sessionContext;

    @PermitAll
    public SessionContext getSessionContext() {
        return this.sessionContext;
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }
    
    @PermitAll
    public Principal getPrincipal() {
        return sessionContext.getCallerPrincipal();
    } 
}
