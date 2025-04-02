package com.githubclient.service;

import com.githubclient.model.Branch;
import com.githubclient.model.GitHubRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "GitHub-client")
//@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GitHubProxy {
    @GetMapping("/users/{username}/repos")
    List<GitHubRepository> getRepositories(@PathVariable("username") String username);

    @GetMapping("/repos/{owner}/{repo}/branches")
    List<Branch> getBranches(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
