package dk.dbc.automarc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class AutoMarcApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoMarcApplication.class);

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(StatusBean.class);

        for (Class<?> clazz : classes) {
            LOGGER.info("Registered {} resource", clazz.getName());
        }
        return classes;
    }
}
