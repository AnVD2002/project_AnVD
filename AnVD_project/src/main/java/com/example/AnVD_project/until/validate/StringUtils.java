package com.example.AnVD_project.until.validate;

import java.text.MessageFormat;

public class StringUtils {

    public static String formatMessage(String message, Object... args) {
        return MessageFormat.format(message, args);
    }

    public static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
