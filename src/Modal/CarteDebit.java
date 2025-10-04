package Modal;

import java.time.LocalDate;


public final class CarteDebit extends Card {
    private final double plafondJournalier;

    public CarteDebit(int id, String numero, LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondJournalier) {
        super(id, numero, dateExpiration, statut, idClient);
        this.plafondJournalier = plafondJournalier;
    }

    public CarteDebit(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondJournalier) {
        super(dateExpiration, statut, idClient);
        this.plafondJournalier = plafondJournalier;
    }

    public double getPlafondJournalier() {
        return plafondJournalier;
    }

    public boolean isDansPlafond(double montant) {
        return montant <= plafondJournalier;
    }

    @Override
    public String getTypeCarte() {
        return "DEBIT";
    }

    @Override
    public String toString() {
        return "CarteDebit{" +
                "id=" + getId() +
                ", numero='" + getNumero() + '\'' +
                ", dateExpiration=" + getDateExpiration() +
                ", statut=" + getStatut() +
                ", plafondJournalier=" + plafondJournalier +
                ", idClient=" + getIdClient() +
                '}';
    }
}
