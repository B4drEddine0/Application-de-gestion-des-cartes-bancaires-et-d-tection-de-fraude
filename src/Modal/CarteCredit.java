package Modal;

import java.time.LocalDate;


public final class CarteCredit extends Card {
    private final double plafondMensuel;
    private final double tauxInteret;

    public CarteCredit(int id, String numero, LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondMensuel, double tauxInteret) {
        super(id, numero, dateExpiration, statut, idClient);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }


    public CarteCredit(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondMensuel, double tauxInteret) {
        super(dateExpiration, statut, idClient);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }

    public double getPlafondMensuel() {
        return plafondMensuel;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public boolean isDansPlafond(double montant) {
        return montant <= plafondMensuel;
    }

    public double calculerInteret(double montant) {
        return montant * (tauxInteret / 100);
    }

    public double calculerMontantAvecInteret(double montant) {
        return montant + calculerInteret(montant);
    }

    @Override
    public String getTypeCarte() {
        return "CREDIT";
    }

    @Override
    public String toString() {
        return "CarteCredit{" +
                "id=" + getId() +
                ", numero='" + getNumero() + '\'' +
                ", dateExpiration=" + getDateExpiration() +
                ", statut=" + getStatut() +
                ", plafondMensuel=" + plafondMensuel +
                ", tauxInteret=" + tauxInteret + "%" +
                ", idClient=" + getIdClient() +
                '}';
    }
}
