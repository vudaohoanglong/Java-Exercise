package com.example.javaDemo.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPattern { // Regex for validating user input.
    public static boolean validate(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
