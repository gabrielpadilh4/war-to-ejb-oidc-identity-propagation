package com.example.interceptor;

import java.io.Serializable;
import java.security.Principal;

import org.jboss.logging.Logger;

import com.example.annotations.AuditControl;
import com.example.ejb.ContextProvider;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ejb.SessionContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;


@AuditControl
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class AuditControlInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CONTEXT_EJB = "java:global/ejb-application-1.0-SNAPSHOT/ContextProvider!com.example.ejb.ContextProvider";

    private static final Logger LOG = Logger.getLogger(AuditControlInterceptor.class);

    public AuditControlInterceptor() {
        LOG.debug("Starting interceptor AuditControlInterceptor.....");
    }

    @AroundInvoke
    public Object interceptMethod(InvocationContext invocationContext) throws Exception {
        LOG.debug("Executing interceptor AuditControlInterceptor.....");

        final ContextProvider contextProvider = getSessionContextProvider();

        final SessionContext sessionContext = contextProvider.getSessionContext();

        final Principal principal = sessionContext.getCallerPrincipal();

        LOG.debugf("Principal class %s", principal.getClass());
        LOG.debugf("Principal username %s", principal.getName());
        // LOG.debugf("Principal hostname %s", principal.getHostname());
        // LOG.debugf("Principal IP %s", principal.getIp());

        Object responseMethod = invocationContext.proceed();

        return responseMethod;
    }

    private ContextProvider getSessionContextProvider() {
        InitialContext ic;
		ContextProvider sctxLookup = null;
		try {
			ic = new InitialContext();
			sctxLookup = (ContextProvider) ic.lookup(CONTEXT_EJB);
		} catch (NamingException e) {
			LOG.debug("ERROR Calling EJB: " + e.getStackTrace());
		}

		return sctxLookup;
    }
}
