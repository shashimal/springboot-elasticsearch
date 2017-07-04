package com.duleendra.util;

import java.util.UUID;

/**
 * UniqueId util for generating an unique id
 *
 *
 * @author Duleendra Shashimal
 *
 */

public class UniqueIDUtil {

    /**
     * Create an unique id
     *
     *
     * @return string unique id
     */
    public static String getUniqueId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
}
