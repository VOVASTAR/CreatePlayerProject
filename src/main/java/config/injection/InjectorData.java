package config.injection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.Logger;


public class InjectorData {
    private static final Logger LOG = Logger.getLogger(InjectorData.class);

    public static InjectionModule injectorModule;
    public static Injector injector;

    static {
        injectorModule = new InjectionModule();
        injector = Guice.createInjector(injectorModule);
        LOG.info("Injector created successfully with module: " + injectorModule.getClass().getSimpleName());
    }
}
