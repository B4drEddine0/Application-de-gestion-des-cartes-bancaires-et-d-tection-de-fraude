package Modal;

import java.time.LocalDate;


public final class CartePrepayee extends Card {
    private final double soldeDisponible;

    public CartePrepayee(int id, String numero, LocalDate dateExpiration, StatutCarte statut, int idClient,
            double soldeDisponible) {
        super(id, numero, dateExpiration, statut, idClient);
        this.soldeDisponible = soldeDisponible;
    }

    public CartePrepayee(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double soldeDisponible) {
        super(dateExpiration, statut, idClient);
        this.soldeDisponible = soldeDisponible;
    }

    public double getSoldeDisponible() {
        return soldeDisponible;
    }

    public boolean aSoldeSuffisant(double montant) {
        return soldeDisponible >= montant;
    }

    public double calculerSoldeApresTransaction(double montant) {
        return soldeDisponible - montant;
    }

    public boolean estVide() {
        return soldeDisponible <= 0;
    }

    @Override
    public String getTypeCarte() {
        return "PREPAYEE";
    }

    @Override
    public String toString() {
        return "CartePrepayee{" +
                "id=" + getId() +
                ", numero='" + getNumero() + '\'' +
                ", dateExpiration=" + getDateExpiration() +
                ", statut=" + getStatut() +
                ", soldeDisponible=" + soldeDisponible +
                ", idClient=" + getIdClient() +
                '}';
    }
}
