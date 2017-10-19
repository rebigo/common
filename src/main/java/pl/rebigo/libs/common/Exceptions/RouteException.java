package pl.rebigo.libs.common.Exceptions;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class RouteException extends Exception {

    /**
     * Constructor
     */
    public RouteException() {

    }

    /**
     * Constructor
     * @param message message exception
     */
    public RouteException(String message) {
        super (message);
    }


    /**
     * Constructor
     * @param cause cause exception
     */
    public RouteException(Throwable cause) {
        super (cause);
    }

    /**
     * Constructor
     * @param message message exception
     * @param cause cause exception
     */
    public RouteException(String message, Throwable cause) {
        super (message, cause);
    }
}
