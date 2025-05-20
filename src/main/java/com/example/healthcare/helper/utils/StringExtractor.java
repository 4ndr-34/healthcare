package com.example.healthcare.helper.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExtractor {

    public static List<String> extractFirstAndLastName(String fullName) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(?<firstName>[A-Z][a-z]*) (?<lastName>[A-Z][a-z]*)$");
        Matcher matcher = pattern.matcher(fullName);

        if (matcher.matches()) {
            list.add(matcher.group("firstName"));
            list.add(matcher.group("lastName"));
            return list;
        } else {
            return list;
        }
    }

}
