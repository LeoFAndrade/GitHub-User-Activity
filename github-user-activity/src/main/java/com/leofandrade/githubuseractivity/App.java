package com.leofandrade.githubuseractivity;

import java.util.ArrayList;

import com.leofandrade.githubuseractivity.cli.UserCli;
import com.leofandrade.githubuseractivity.client.GitHubApiClient;
import com.leofandrade.githubuseractivity.dto.GitHubEventDto;
import com.leofandrade.githubuseractivity.service.AssignmentService;
import com.leofandrade.githubuseractivity.service.JsonParser;
import java.util.LinkedHashMap;
import java.util.Map;
import com.leofandrade.githubuseractivity.exception.UserNotFoundException;

public class App {
    public static void main(String[] args) {
        UserCli cli = new UserCli();
        JsonParser parser = new JsonParser();
        AssignmentService service = new AssignmentService();
        GitHubApiClient client = new GitHubApiClient();

        try {
            cli.processArgs(args);
            String gitHubUsername = cli.getGitHubUsername();
            String response = client.getUserEvents(gitHubUsername);
            ArrayList<String> events = parser.jsonToStringArray(response);
            Map<String, Integer> counts = new LinkedHashMap<>();

            if (events.isEmpty()) {
                System.out.println("No recent activity found for this user.");
                return;
            }

            for (String event : events) {
                GitHubEventDto dto = service.parseEvent(event);
                counts.put(service.formatEvent(dto), counts.getOrDefault(service.formatEvent(dto), 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (entry.getValue() != 1) {
                    System.out.println(entry.getKey() + " " + entry.getValue() + " times");
                } else {
                    System.out.println(entry.getKey());
                }

            }

        }

        catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        catch (Exception e) {
            System.err.println("An error occurred:" + e.getMessage());
            System.exit(1);
        }
    }
}
