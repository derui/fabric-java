package statelessservice;

import java.time.Duration;
import java.util.logging.Logger;
import java.util.logging.Level;

import microsoft.servicefabric.services.runtime.ServiceRuntime;

public class base64ServiceHost {

    private static final Logger logger = Logger.getLogger(base64ServiceHost.class.getName());

    public static void main(String[] args) throws Exception {
        try {
            ServiceRuntime.registerStatelessServiceAsync("base64Type", (context) -> new base64Service(), Duration.ofSeconds(10));
            logger.log(Level.INFO, "Registered stateless service of type base64Type. ");
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception occured", ex);
            throw ex;
        }
    }
}
