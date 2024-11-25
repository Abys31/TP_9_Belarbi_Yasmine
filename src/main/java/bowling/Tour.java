package bowling;

public class Tour {
	private final int numeroDeTour;
	private int lancer1;
	private int lancer2;
	private int lancer3; // Utile pour le dernier tour
	private int score;
	private boolean estDernierTour;

	public Tour(int numeroDeTour, boolean estDernierTour) {
		this.numeroDeTour = numeroDeTour;
		this.lancer1 = -1; // Valeur par défaut indiquant que le lancer n'a pas encore eu lieu
		this.lancer2 = -1;
		this.lancer3 = -1;
		this.score = 0;
		this.estDernierTour = estDernierTour;
	}

	public void enregistrerLancer(int quilles) {
		if (lancer1 == -1) {
			lancer1 = quilles;
		} else if (lancer2 == -1) {
			lancer2 = quilles;
		} else if (estDernierTour && lancer3 == -1) {
			lancer3 = quilles;
		} else {
			throw new IllegalStateException("Ce tour est déjà complet.");
		}
	}

	public boolean estComplet() {
		if (estDernierTour) {
			if (lancer1 == 10 || lancer1 + lancer2 == 10) {
				return lancer3 != -1; // Nécessite un 3e lancer
			}
			return lancer1 != -1 && lancer2 != -1; // Deux lancers sinon
		}
		return lancer1 == 10 || (lancer1 != -1 && lancer2 != -1); // Strike ou 2 lancers
	}

	public boolean estStrike() {
		return lancer1 == 10;
	}

	public boolean estSpare() {
		return lancer1 != 10 && (lancer1 + lancer2 == 10);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLancer1() {
		return lancer1;
	}

	public int getLancer2() {
		return lancer2;
	}

	public int getLancer3() {
		return lancer3;
	}
}
