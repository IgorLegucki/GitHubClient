package com.githubclient;

import com.githubclient.service.GitHubProxy;
import com.githubclient.model.Branch;
import com.githubclient.model.GitHubRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
class GithuberApplicationTests {

    @Autowired
    private GitHubProxy gitHubClient;

    @Test
    void shouldFetchRepositoriesForExistingUser() {
        // GIVEN
        String username = "octocat";

        // WHEN
        List<GitHubRepository> repositories = gitHubClient.getRepositories(username);

        // THEN
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty(), "Lista repozytoriów nie powinna być pusta");
    }

    @Test
    void shouldReturn404ForNonExistentUser() {
        // GIVEN
        String username = "nonexistentuser12345";

        // WHEN & THEN
        FeignException exception = assertThrows(FeignException.NotFound.class, () -> {
            gitHubClient.getRepositories(username);
        });

        assertEquals(404, exception.status(), "Powinien zostać zwrócony kod 404");
    }

    @Test
    void shouldFetchBranchesForValidRepository() {
        // GIVEN
        String owner = "octocat";
        String repo = "Hello-World";

        // WHEN
        List<Branch> branches = gitHubClient.getBranches(owner, repo);

        // THEN
        assertNotNull(branches);
        assertFalse(branches.isEmpty(), "Lista gałęzi nie powinna być pusta");
    }

    @Test
    void shouldReturnEmptyListForUserWithoutRepos() {
        // GIVEN
        String username = "aaa";

        // WHEN
        List<GitHubRepository> repositories = gitHubClient.getRepositories(username);

        // THEN
        assertNotNull(repositories);
        assertTrue(repositories.isEmpty(), "Lista repozytoriów powinna być pusta");
    }
}
