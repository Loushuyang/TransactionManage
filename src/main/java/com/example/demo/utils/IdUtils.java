package com.example.demo.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils {

    private static Snowflake snowflake = IdUtil.getSnowflake();

    public static Long getId() {
        return snowflake.nextId();
    }

    public static String getIdStr() {
        return snowflake.nextIdStr();
    }
}
