package com.leofandrade.githubuseractivity;

import java.util.ArrayList;

import com.leofandrade.githubuseractivity.cli.UserCli;
import com.leofandrade.githubuseractivity.client.GitHubApiClient;
import com.leofandrade.githubuseractivity.dto.GitHubEventDto;
import com.leofandrade.githubuseractivity.service.AssignmentService;
import com.leofandrade.githubuseractivity.service.JsonParser;

public class App {
    public static void main(String[] args) {
        UserCli cli = new UserCli();
        JsonParser parser = new JsonParser();
        AssignmentService service = new AssignmentService();
        GitHubApiClient client = new GitHubApiClient();

        cli.processArgs(args);

        String gitHubUsername = cli.getGitHubUsername();

        try {
            String response = client.getUserEvents(gitHubUsername);
            ArrayList<String> jsonToStringArray = parser.jsonToStringArray(response);
            System.out.println("DEBUG: " + response);
            GitHubEventDto dto = service.parseEvent(jsonToStringArray.get(0));
            System.out.println("\n\n\n\nType: " + dto.getType());
            System.out.println("Repo: " + dto.getRepoName());
            System.out.println("Payload: " + dto.getPayloadInfo());
        } catch (Exception e) {
            System.err.println("An error occurred:" + e.getMessage());
        }

    }
}
