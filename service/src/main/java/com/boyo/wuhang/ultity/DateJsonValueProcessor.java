package com.boyo.wuhang.ultity;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * JSON日期格式转换
 * to yyyy-MM-dd HH:mm:ss
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    @Override
    public Object processArrayValue(Object obj, JsonConfig jsonConfig) {
        return process(obj);
    }

    @Override
    public Object processObjectValue(String key, Object obj, JsonConfig jsonConfig) {
        return process(obj);
    }

    private Object process(Object obj) {
        if (obj == null) {// 如果时间为null，则返回空字串
            return "";
        }
        if (obj instanceof Date) {
            obj = new Date(((Date) obj).getTime());
        }
        if (obj instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
                    Locale.CHINA);// 格式化时间为yyyy-MM-dd类型
            return sdf.format(obj);
        } else {
            return new Object();
        }
    }
}
