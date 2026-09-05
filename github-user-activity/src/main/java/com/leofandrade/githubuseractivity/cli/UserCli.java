package com.leofandrade.githubuseractivity.cli;

public class UserCli {
    private String githubUsername;

    public UserCli() {
    }

    public String getGitHubUsername() {
        return githubUsername;
    }

    public void setGitHubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public void processArgs(String[] args) {

        if (args == null || args.length == 0) {
            System.err.println("Error: No username provided.");
            System.exit(1);
        }
        this.githubUsername = args[0];
    }
}
