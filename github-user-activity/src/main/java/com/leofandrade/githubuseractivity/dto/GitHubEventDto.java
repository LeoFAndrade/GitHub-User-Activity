package com.leofandrade.githubuseractivity.dto;

public class GitHubEventDto {
    private String type;
    private String repoName;
    private String payloadInfo;

    public GitHubEventDto(String type, String repoName, String payloadInfo){
        this.type = type;
        this.repoName = repoName;
        this.payloadInfo = payloadInfo;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRepoName() {
        return repoName;
    }
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
    public String getPayloadInfo() {
        return payloadInfo;
    }
    public void setPayloadInfo(String payloadInfo) {
        this.payloadInfo = payloadInfo;
    }


}
