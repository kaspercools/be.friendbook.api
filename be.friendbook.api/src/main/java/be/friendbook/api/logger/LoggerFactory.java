package be.friendbook.api.logger;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;


/**
 *
 * @author kaspercools
 */
public class LoggerFactory {

    private Logger logger;

    public LoggerFactory() {
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Produces
    Logger getLogger(InjectionPoint caller) {
        return Logger.getLogger(caller.getMember().getDeclaringClass().getName());
    }
}
