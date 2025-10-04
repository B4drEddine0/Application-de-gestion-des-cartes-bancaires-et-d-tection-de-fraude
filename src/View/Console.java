package View;

import Modal.Card;
import Modal.CarteCredit;
import Modal.CarteDebit;
import Modal.CartePrepayee;
import Modal.Client;
import Modal.StatutCarte;
import Service.AuthService;
import Service.CardService;
import Service.OperationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    private static Scanner scanner = new Scanner(System.in);
    private final AuthService authService;
    private final CardService cardService;
    private final OperationService operationService;

    public Console(AuthService authService, CardService cardService, OperationService operationService) {
        this.authService = authService;
        this.cardService = cardService;
        this.operationService = operationService;
    }

    public void Start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Bank Card Management System ===");
            System.out.println("1) Register Client");
            System.out.println("2) List All Clients");
            System.out.println("3) Create Card For A client");
            System.out.println("4) Find Card By Id");
            System.out.println("5) Find Card By Client ID");
            System.out.println("6) Delete card By ID");
            System.out.println("7) Update Card Status");
            System.out.println("8) Make An Operation");
            System.out.println("0) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            HandleMenu(choice);

            if (choice.equals("0")) {
                running = false;
                System.out.println("Goodbye!");
            }
        }
    }

    private void HandleMenu(String choice) {
        try {
            switch (choice) {
                case "1":
                    HandleRegister();
                    break;
                case "2":
                    HandleFetchAllClients();
                    break;
                case "3":
                    HandleCreateCard();
                    break;
                case "4":
                    HandleFindCardById();
                    break;
                case "5":
                    HandleFindCardByClienId();
                    break;
                case "6":
                    HandleDeleteCardById();
                    break;
                case "7":
                    HandleUpdateCardStatus();
                    break;
                case "8":
                    HandleMakingAnOperation();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void HandleRegister() {
        System.out.println("\n--- Register New Client ---");
        System.out.print("Enter Client Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Client Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Client Password: ");
        String password = scanner.nextLine();

        authService.register(name, email, password);
        System.out.println("✓ Client registered successfully!");
    }

    private void HandleFetchAllClients() {
        System.out.println("\n=== List of All Clients ===");
        var clients = authService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            for (Client c : clients) {
                System.out.println("---------------------------");
                System.out.println("ID: " + c.id());
                System.out.println("Name: " + c.name());
                System.out.println("Email: " + c.email());
                System.out.println("---------------------------");
            }
        }
    }

    private void HandleCreateCard() {
        System.out.println("What type of Card you want to make: ");
        System.out.println("1) Credit Card");
        System.out.println("2) Debit Card");
        System.out.println("3) Prepaid Card");
        String choice = scanner.nextLine();

        System.out.println("Enter Expiration Date (YYYY-MM-DD): ");
        LocalDate dateExpiration = LocalDate.parse(scanner.nextLine());
        StatutCarte statut = StatutCarte.ACTIVE;
        System.out.println("Enter Client ID: ");
        int idClient = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case "1":
                System.out.println("Enter Monthly Limit: ");
                double plafondMensuel = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter Interest Rate (%): ");
                double tauxInteret = Double.parseDouble(scanner.nextLine());
                cardService.createCreditCard(dateExpiration, statut, idClient, plafondMensuel, tauxInteret);
                System.out.println("Credit Card created successfully!");
                break;
            case "2":
                System.out.println("Enter Daily Limit: ");
                double plafondJournalier = Double.parseDouble(scanner.nextLine());
                cardService.createDebitCard(dateExpiration, statut, idClient, plafondJournalier);
                System.out.println("Debit Card created successfully!");
                break;
            case "3":
                System.out.println("Enter Available Balance: ");
                double soldeDisponible = Double.parseDouble(scanner.nextLine());
                cardService.createPrepayeeCard(dateExpiration, statut, idClient, soldeDisponible);
                System.out.println("Prepaid Card created successfully!");
                break;
            default:
                System.out.println("invalid option!!");
                break;
        }
    }

    private void HandleFindCardById() {
        System.out.println("Type Card Id: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        Card card = cardService.getCardById(id).orElseThrow(() -> new NoSuchElementException("Card not Found!"));
        System.out.println("Card id: " + card.getId());
        System.out.println("Card number: " + card.getNumero());
        System.out.println("Expiration date: " + card.getDateExpiration());
        System.out.println("Status: " + card.getStatut());
        System.out.println("Client Id: " + card.getIdClient());
        System.out.println("Card type: " + card.getTypeCarte());
        if (card instanceof CarteDebit debitCard) {
            System.out.println("Daily limit (Plafond journalier): " + debitCard.getPlafondJournalier());
        } else if (card instanceof CarteCredit creditCard) {
            System.out.println("Monthly limit (Plafond mensuel): " + creditCard.getPlafondMensuel());
            System.out.println("Interest rate (Taux d'intérêt): " + creditCard.getTauxInteret() + "%");
        } else if (card instanceof CartePrepayee prepCard) {
            System.out.println("Available balance (Solde disponible): " + prepCard.getSoldeDisponible());
        }
    }

    private void HandleFindCardByClienId() {
        System.out.println("Enter The Client ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Card card : cardService.getClientCards(id)) {
            System.out.println("------------------------------------------------");
            System.out.println("Card id: " + card.getId());
            System.out.println("Card number: " + card.getNumero());
            System.out.println("Expiration date: " + card.getDateExpiration());
            System.out.println("Status: " + card.getStatut());
            System.out.println("Client Id: " + card.getIdClient());
            System.out.println("Card type: " + card.getTypeCarte());
            if (card instanceof CarteDebit debitCard) {
                System.out.println("Daily limit (Plafond journalier): " + debitCard.getPlafondJournalier());
            } else if (card instanceof CarteCredit creditCard) {
                System.out.println("Monthly limit (Plafond mensuel): " + creditCard.getPlafondMensuel());
                System.out.println("Interest rate (Taux d'intérêt): " + creditCard.getTauxInteret() + "%");
            } else if (card instanceof CartePrepayee prepCard) {
                System.out.println("Available balance (Solde disponible): " + prepCard.getSoldeDisponible());
            }
        }
    }

    private void HandleDeleteCardById() {
        System.out.println("Enter The Card ID : ");
        int cardId = scanner.nextInt();
        scanner.nextLine();
        cardService.deleteCardById(cardId);
    }

    private void HandleUpdateCardStatus() {
        System.out.println("Enter The Card Id");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Choose Status: ");
        System.out.println("1) ACTIVE");
        System.out.println("2) INACTIVE");
        System.out.println("3) BLOCKED");
        String choice = scanner.nextLine();
        StatutCarte status = null;
        switch (choice) {
            case "1":
                status = StatutCarte.ACTIVE;
                break;
            case "2":
                status = StatutCarte.SUSPENDUE;
                break;
            case "3":
                status = StatutCarte.BLOQUEE;
                break;
        }
        cardService.UpdateCardStatus(id, status);
    }

    private void HandleMakingAnOperation() {
        System.out.println("Enter CardId");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Amount of Operation");
        Double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("What Type Of Operation You Want to Perform:");
        System.out.println("1) ACHAT");
        System.out.println("2) RETRAIT");
        System.out.println("3) PAIEMENTENLIGNE");
        String choice = scanner.nextLine();
        String type = null;
        switch (choice) {
            case "1":
                type = "ACHAT";
                break;
            case "2":
                type = "RETRAIT";
                break;
            case "3":
                type = "PAIEMENTENLIGNE";
                break;
            default:
                break;
        }
        ZoneId zone = ZoneId.systemDefault();
        String zoneId  = zone.getId();
        String city = zoneId.substring(zoneId.lastIndexOf("/") + 1);
        String currentCity = null;
        System.out.println(city);
        System.out.println("Are you Still in " + city);
        System.out.println("1) Yes");
        System.out.println("2) No");
        String choice1 = scanner.nextLine();
        switch (choice1) {
            case "1":
                currentCity = city;
                break;
            case"2":
                System.out.println("Enter Youre City: ");
                currentCity = scanner.nextLine();
                break;
        
            default:
                break;
        }
        operationService.insertOperation(carteId, montant, type, currentCity);

    }
}
