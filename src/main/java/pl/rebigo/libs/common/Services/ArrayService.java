package pl.rebigo.libs.common.Services;


import pl.rebigo.libs.common.Exceptions.CommonException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 14.09.2017
 * Time: 11:33
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class ArrayService {

    /**
     * New array
     *
     * @param args   arguments
     * @return map
     * @throws CommonException CommonException
     */
    public static Map<String, Object> newArray(Object... args) throws CommonException {
        Map<String, Object> map = new HashMap<>();
        if (args.length%2!=0)
            throw new CommonException("Quantity arguments isn't even");
        for (Integer i=0 ; i < args.length ; i += 2)
            map.put((String) args[i], args[i+1]);
        return map;
    }

    /**
     * New array integer
     *
     * @param args   arguments
     * @return map
     * @throws CommonException CommonException
     */
    public static Map<Integer, Object> newArrayInteger(Object... args) throws CommonException {
        Map<Integer, Object> map = new HashMap<>();
        if (args.length%2!=0)
            throw new CommonException("Quantity arguments isn't even");
        for (Integer i=0 ; i < args.length ; i += 2)
            map.put((Integer) args[i], args[i+1]);
        return map;
    }

    /**
     * New array string
     *
     * @param args   arguments
     * @return map
     * @throws CommonException CommonException
     */
    public static Map<String, String> newArrayString(String... args) throws CommonException {
        Map<String, String> map = new HashMap<>();
        if (args.length%2!=0)
            throw new CommonException("Quantity arguments isn't even");
        for (Integer i=0 ; i < args.length ; i += 2)
            map.put(args[i], args[i+1]);
        return map;
    }

    /**
     * New list
     * @param args arguments
     * @return list
     * @throws CommonException CommonException
     */
    public static List<String> newList(String... args) throws CommonException {
        List<String> list = new ArrayList<>();
        if (args.length%2!=0)
            throw new CommonException("Quantity arguments isn't even");
        for (Integer i=0 ; i < args.length ; i ++)
            list.add(args[i]);
        return list;
    }
}
