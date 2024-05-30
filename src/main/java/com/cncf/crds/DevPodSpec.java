package com.cncf.crds;

public class DevPodSpec {

    private String owner;
    private String flavor;
    private String gitRepository;

    public String getOwner() {
        return owner;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getGitRepository() {
        return gitRepository;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public void setGitRepository(String gitRepository) {
        this.gitRepository = gitRepository;
    }
}
