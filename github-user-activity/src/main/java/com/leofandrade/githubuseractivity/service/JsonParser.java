package com.leofandrade.githubuseractivity.service;

import java.util.ArrayList;

public class JsonParser {

    public ArrayList<String> jsonToStringArray(String jsonArray) {
        ArrayList<String> stringList = new ArrayList<>();

        if (jsonArray == null || jsonArray.trim().isEmpty()) {
            return stringList;
        }

        String objectString = "";
        int depth = 0;
        int start = 0;

        jsonArray = jsonArray.substring(1, jsonArray.length() - 1);

        for (int i = 0; i < jsonArray.length(); i++) {
            char actualChar = jsonArray.charAt(i);

            if (actualChar == '{') {
                depth++;
            }

            if (actualChar == '}') {
                depth--;
            }

            if (actualChar == ',' && depth == 0) {
                objectString = jsonArray.substring(start, i);
                start = i + 1;
                stringList.add(objectString);
            }
        }
        stringList.add(jsonArray.substring(start, jsonArray.length()));
        return stringList;
    }

    public ArrayList<String> stringObjectSeparator(String jsonObject) {
        int depth = 0;
        int start = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        boolean insideQuotes = false;

        for (int i = 0; i < jsonObject.length(); i++) {
            char actualChar = jsonObject.charAt(i);

            if (actualChar == '{') {
                depth++;
            }

            if (actualChar == '}') {
                depth--;
            }

            if (actualChar == '"') {
                insideQuotes = !insideQuotes;
            }

            if (actualChar == ',' && !insideQuotes && depth == 0) {
                arrayList.add(jsonObject.substring(start, i));
                start = i + 1;
            }

        }
        arrayList.add(jsonObject.substring(start, jsonObject.length()));
        return arrayList;
    }

    public String[] splitKeyValue(String field) {
        String[] keyValue = field.split(":", 2);

        String key = keyValue[0].replaceAll("\"", "").strip();
        String value = keyValue[1].strip();

        if (!value.startsWith("{")) {
            value = value.replaceAll("\"", "");
        }

        return new String[] { key, value };
    }

}
