package com.purplefront.jiro_dev.utils;

import java.util.Collection;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class Utils {
    public static <T> T find(Collection<?> arrayList, Class<T> clazz)
    {
        for(Object o : arrayList)
        {
            if (o != null && o.getClass() == clazz)
            {
                return clazz.cast(o);
            }
        }

        return null;
    }
}
