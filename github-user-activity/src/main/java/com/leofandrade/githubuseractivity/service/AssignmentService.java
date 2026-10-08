package com.leofandrade.githubuseractivity.service;

import java.util.ArrayList;
import com.leofandrade.githubuseractivity.dto.GitHubEventDto;

public class AssignmentService {

    public GitHubEventDto parseEvent(String stringList) {
        JsonParser parser = new JsonParser();
        String type = "";
        String repoName = "";
        String payload = "";
        stringList = stringList.substring(1, stringList.length() - 1);
        ArrayList<String> separatedList = parser.stringObjectSeparator(stringList);

        for (String object : separatedList) {

            String[] keyValueObject = parser.splitKeyValue(object);

            if (keyValueObject[0].equals("type")) {
                type = keyValueObject[1];
            }

            if (keyValueObject[0].equals("repo")) {
                repoName = extractRepoName(keyValueObject[1]);
            }

            if (keyValueObject[0].equals("payload")) {
                String payloadValue = keyValueObject[1].replaceAll("[{}\\[\\]]", "").trim();

                if (!payloadValue.isEmpty()) {
                    for (String item : parser.stringObjectSeparator(payloadValue)) {
                        String[] keyResult = parser.splitKeyValue(item);

                        if (keyResult[0].equals("action")) {
                            payload = keyResult[1];
                        }
                    }
                }
            }
        }
        GitHubEventDto dto = new GitHubEventDto(type, repoName, payload);
        return dto;
    }

    public String extractRepoName(String repoValue) {
        JsonParser parser = new JsonParser();
        repoValue = repoValue.substring(1, repoValue.length() - 1);
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
