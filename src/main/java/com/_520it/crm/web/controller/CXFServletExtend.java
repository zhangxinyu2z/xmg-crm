package com._520it.crm.web.controller;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collection;

public class CXFServletExtend extends CXFNonSpringServlet
    implements ApplicationListener<ContextRefreshedEvent> {
    private static final long serialVersionUID = -5922443981969455305L;
    private static final String BUS_PARAMETER = "bus";
    
    private boolean busCreated;
    private XmlWebApplicationContext createdContext; 
    
    public CXFServletExtend() {
    }

    
    @Override
    protected void loadBus(ServletConfig servletConfig) {
    	//因为spring和springMVC
        ApplicationContext wac = (ApplicationContext) servletConfig.getServletContext()
        		.getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX+"springMVC");
        
        if (wac instanceof AbstractApplicationContext) {
            addListener((AbstractApplicationContext)wac);
        }
        
        String configLocation = servletConfig.getInitParameter("config-location");
        if (configLocation == null) {
            try {
                InputStream is = servletConfig.getServletContext().getResourceAsStream("/WEB-INF/cxf-servlet.xml");
                if (is != null && is.available() > 0) {
                    is.close();
                    configLocation = "/WEB-INF/cxf-servlet.xml";
                }
            } catch (Exception ex) {
                //ignore
            }
        }
        if (configLocation != null) {
            wac = createSpringContext(wac, servletConfig, configLocation);
        }
        if (wac != null) {
            String busParam = servletConfig.getInitParameter(BUS_PARAMETER);
            String busName = busParam == null ? "cxf" : busParam.trim();
            
            setBus((Bus)wac.getBean(busName, Bus.class));
        } else {
            busCreated = true;
            setBus(BusFactory.newInstance().createBus());
        }
    }

    protected void addListener(AbstractApplicationContext wac) {
        try {
            //spring 2 vs spring 3 return type is different
            Method m = wac.getClass().getMethod("getApplicationListeners");
            Collection<Object> c = CastUtils.cast((Collection<?>) ReflectionUtil
                                                      .setAccessible(m).invoke(wac));
            c.add(this);
        } catch (Throwable t) {
            //ignore.
        }
    }

    /**
     * Try to create a spring application context from the config location.
     * Will first try to resolve the location using the servlet context.
     * If that does not work then the location is given as is to spring
     * 
     * @param ctx
     * @return
     */
    private ApplicationContext createSpringContext(ApplicationContext ctx,
                                                   ServletConfig servletConfig,
                                                   String location) {
        XmlWebApplicationContext ctx2 = new XmlWebApplicationContext();
        createdContext = ctx2;
        
        ctx2.setServletConfig(servletConfig);
        Resource r = ctx2.getResource(location);
        try {
            InputStream in = r.getInputStream();
            in.close();
        } catch (IOException e) {
            //ignore
            r = ctx2.getResource("classpath:" + location);
            try {
                r.getInputStream().close();
            } catch (IOException e2) {
                //ignore
                r = null;
            }
        }
        try {
            if (r != null) {
                location = r.getURL().toExternalForm();
            }
        } catch (IOException e) {
            //ignore
        }        
        if (ctx != null) {
            ctx2.setParent(ctx);
            String names[] = ctx.getBeanNamesForType(Bus.class);
            if (names == null || names.length == 0) {
                ctx2.setConfigLocations(new String[] {"classpath:/META-INF/cxf/cxf.xml",
                                                      location});                
            } else {
                ctx2.setConfigLocations(new String[] {location});                                
            }
        } else {
            ctx2.setConfigLocations(new String[] {"classpath:/META-INF/cxf/cxf.xml",
                                                  location});
            createdContext = ctx2;
        }
        ctx2.refresh();
        return ctx2;
    }
    @Override
    public void destroyBus() {
        if (busCreated) {
            //if we created the Bus, we need to destroy it.  Otherwise, spring will handleit.
            getBus().shutdown(true);
            setBus(null);
        }
        if (createdContext != null) {
            createdContext.close();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        destroy();
        setBus(null);
        try {
            init(getServletConfig());
        } catch (ServletException e) {
            throw new RuntimeException("Unable to reinitialize the CXFServlet", e);
        }
    }

}

