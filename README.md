# GitHubClient

GitHubClient to aplikacja wykorzystująca Spring Boot oraz FeignClient, która komunikuje się z API GitHub w celu uzyskania listy repozytoriów dla użytkownika oraz informacji o branchach i commitach. Aplikacja wyświetla pobrane dane w konsoli a także potrafi obsłużyć błąd nieistniejącego użytkownika.

# Opis projektu
Aplikacja ma na celu przedstawić jak użyć Feign Clienta do komunikacji z REST API serwisu GitHub. Aplikacja pobiera listę repozytoriów dla danego użytkownika i wyświetla parę istotnych szczegółów, takich jak nazwy repozytoriów, login właściciela, nazwy gałęzi z najnowszą zatwierdzoną sumą SHA.

## Technologie
- **Java 21**: Obiektowy język programowania 
- **Spring Boot 2023.0.3**: Podstawowy framework użyty do zbudowania aplikacji.
- **Feign Client**: Deklaratywny klient HTTP służący do uproszczenia wywoływania zewnętrznych interfejsów API.
- **JUnit 5**: Do testów integracyjnych.
- **Lombok**: Został użyty do skrócenia kodu.
- **Maven**: Do zarządzania zależnościami w projekcie i jego budowania.
