package pl.rebigo.libs.common.Services;

import pl.rebigo.libs.common.Exceptions.RouteException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 14.09.2017
 * Time: 10:35
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class RouteService {

    /**
     * Call route
     *
     * @param controllerClass controller class
     * @param functionName    function name
     * @param params          parameters
     * @return object
     * @throws RouteException RouteException
     */
    public static Object call(Class<?> controllerClass, String functionName, Map<String, Object> params) throws RouteException {
        try {
            Constructor<?> cons = controllerClass.getConstructor();
            Object object = cons.newInstance();
            if (Objects.nonNull(params))
                return object.getClass().getDeclaredMethod(functionName, Map.class).invoke(object, params);
            else
                return object.getClass().getDeclaredMethod(functionName).invoke(object);
        } catch (Exception e) {
            throw new RouteException(e.getMessage(), e);
        }

    }
}
