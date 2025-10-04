package Modal;

import java.time.LocalDate;
import java.util.UUID;

public sealed abstract class Card permits CarteDebit, CarteCredit, CartePrepayee {
    private int id;
    private final String numero;
    private final LocalDate dateExpiration;
    private final StatutCarte statut;
    private final int idClient;


    public Card(int id, String numero, LocalDate dateExpiration, StatutCarte statut, int idClient) {
        this.id = id;
        this.numero = numero;
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.idClient = idClient;
    }

    public Card(LocalDate dateExpiration, StatutCarte statut, int idClient) {
        this.numero = UUID.randomUUID().toString();
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public StatutCarte getStatut() {
        return statut;
    }

    public int getIdClient() {
        return idClient;
    }

    public abstract String getTypeCarte();

    public boolean isExpired() {
        return LocalDate.now().isAfter(dateExpiration);
    }

    public boolean isActive() {
        return statut == StatutCarte.ACTIVE;
    }

    public boolean isValid() {
        return isActive() && !isExpired();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", dateExpiration=" + dateExpiration +
                ", statut=" + statut +
                ", type=" + getTypeCarte() +
                ", idClient=" + idClient +
                '}';
    }
}
