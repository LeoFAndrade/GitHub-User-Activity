package com.leofandrade.githubuseractivity.service;

import java.util.ArrayList;

public class AssignmentService {

    public String extractRepoName(String repoValue) {
        JsonParser parser = new JsonParser();
        ArrayList<String> repoList = parser.stringObjectSeparator(repoValue);
        String result = "";

        for (int i = 0; i < repoList.size(); i++) {
            String[] keyResult = parser.splitKeyValue(repoList.get(i));

            if (keyResult[0].equals("name")) {
                result = keyResult[1];
            }
        }

        return result;
    }
}
