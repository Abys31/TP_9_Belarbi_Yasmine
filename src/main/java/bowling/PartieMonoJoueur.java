package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {

	private final List<Tour> tours;
	private int indexTourActuel;

	public PartieMonoJoueur() {
		this.tours = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			tours.add(new Tour(i + 1, i == 9)); // Le dernier tour est spécial
		}
		this.indexTourActuel = 0;
	}

	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee()) {
			throw new IllegalStateException("La partie est terminée !");
		}

		Tour tourActuel = tours.get(indexTourActuel);
		tourActuel.enregistrerLancer(nombreDeQuillesAbattues);

		if (tourActuel.estComplet() && indexTourActuel < 9) {
			calculerScore(); // Mise à jour des scores après chaque tour complet
			indexTourActuel++;
		}

		return !tourActuel.estComplet(); // Retourne vrai si un autre lancer est nécessaire
	}

	public int score() {
		calculerScore(); // Met à jour les scores avant de retourner le total
		return tours.stream().mapToInt(Tour::getScore).sum();
	}

	private void calculerScore() {
		for (int i = 0; i <= indexTourActuel; i++) {
			Tour tour = tours.get(i);
			if (i < 9) { // Calcul pour les 9 premiers tours
				if (tour.estStrike()) {
					int bonus = calculerBonusStrike(i);
					tour.setScore(10 + bonus);
				} else if (tour.estSpare()) {
					int bonus = calculerBonusSpare(i);
					tour.setScore(10 + bonus);
				} else {
					tour.setScore(tour.getLancer1() + tour.getLancer2());
				}
			} else { // Calcul pour le dernier tour
				tour.setScore(tour.getLancer1() + tour.getLancer2() + tour.getLancer3());
			}
		}
	}

	private int calculerBonusStrike(int index) {
		if (index + 1 < tours.size()) {
			Tour prochainTour = tours.get(index + 1);
			if (prochainTour.estStrike() && index + 2 < tours.size()) {
				return 10 + tours.get(index + 2).getLancer1(); // Deux strikes consécutifs
			}
			return prochainTour.getLancer1() + (prochainTour.getLancer2() == -1 ? 0 : prochainTour.getLancer2());
		}
		return 0;
	}

	private int calculerBonusSpare(int index) {
		if (index + 1 < tours.size()) {
			return tours.get(index + 1).getLancer1();
		}
		return 0;
	}

	public boolean estTerminee() {
		return indexTourActuel == 9 && tours.get(9).estComplet();
	}

	public int numeroTourCourant() {
		return estTerminee() ? 0 : indexTourActuel + 1;
	}

	public int numeroProchainLancer() {
		if (estTerminee()) return 0;
		Tour tourActuel = tours.get(indexTourActuel);
		return tourActuel.getLancer1() == -1 ? 1 : (tourActuel.getLancer2() == -1 ? 2 : 3);
	}
}
