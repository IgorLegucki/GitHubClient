package com.githubclient;

import com.githubclient.service.GitHubProxy;
import com.githubclient.exception.ErrorResponse;
import com.githubclient.model.Branch;
import com.githubclient.model.GitHubRepository;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
@Log4j2
public class GitHubClientApplication {

    @Autowired
    GitHubProxy gitHubClient;

    String username = "Kaszalot1021"; // Możesz zmienić na testową nazwę


    public static void main(String[] args) {
        SpringApplication.run(GitHubClientApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        try {
            List<GitHubRepository> response = gitHubClient.getRepositories(username);

            if (response == null || response.isEmpty()) {
                log.info("Użytkownik '{}' istnieje, ale nie ma repozytoriów.", username);
                return;
            }

            List<GitHubRepository> filteredRepos = response.stream()
                    .filter(repo -> !repo.fork())
                    .toList();

            log.info("Otrzymane repozytoria:");
            for (GitHubRepository repo : filteredRepos) {
                log.info("Repozytorium: {}, Owner: {}", repo.name(), repo.owner().login());

                List<Branch> branches = gitHubClient.getBranches(repo.owner().login(), repo.name());

                for (Branch branch : branches) {
                    log.info("  - Branch: {}, Last Commit SHA: {}", branch.name(), branch.commit().sha());
                }
            }

        } catch (FeignException.NotFound e) {
            logErrorResponse(HttpStatus.NOT_FOUND, "Użytkownik '" + username + "' nie istnieje.");
        } catch (FeignException.BadRequest e) {
            logErrorResponse(HttpStatus.BAD_REQUEST, "Błędne zapytanie - nieprawidłowe dane wejściowe.");
        } catch (FeignException e) {
            logErrorResponse(HttpStatus.valueOf(e.status()), e.getMessage());
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Błąd serwera: " + e.getMessage());
        }
    }

    private void logErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        log.error(errorResponse.toString());
    }
}


