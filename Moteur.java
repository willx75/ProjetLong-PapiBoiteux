import java.io.IOException;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class Moteur {
	
	LigneCouleur ligneCouleur;
	Capteur capteur;
	boolean arrierePlanTrouve;
	boolean recherche;
	
	public Moteur() throws IOException{
		ligneCouleur = new LigneCouleur();
		capteur = new Capteur();
		arrierePlanTrouve = false;
		recherche = true;
	}

	
	public void turnBackwardRight() {
		Motor.A.setSpeed(400);
		Motor.C.setSpeed(150);
		Motor.A.backward();
		Motor.C.backward();
	}
	
	public void turnBackwardLeft() {
		Motor.A.setSpeed(150);
		Motor.C.setSpeed(400);
		Motor.A.backward();
		Motor.C.backward();
	}
	
	public void turnForwardLeft() {
		Motor.A.setSpeed(150);
		Motor.C.setSpeed(400);
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void turnForwardRight() {
		Motor.A.setSpeed(400);
		Motor.C.setSpeed(150);
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void forward() {
		Motor.A.setSpeed(400);
		Motor.C.setSpeed(400);
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void goBackward() {
		Motor.A.setSpeed(400);
		Motor.C.setSpeed(400);
		Motor.A.backward();
		Motor.C.backward();
	}
	
	public void stop() {
		Motor.A.stop();
		Motor.C.stop();
	}
	
	private void chercherLigne() throws Exception{
		Motor.C.setSpeed(150);
		Motor.A.setSpeed(150);
		float[] couleurCapte;
		forward();
		Delay.msDelay(1000);
		couleurCapte = capteur.getColor();
		while (ligneCouleur.surLigne(couleurCapte)) {
			forward();
			couleurCapte = capteur.getColor();
		}
		Motor.C.setSpeed(400);
		Motor.C.setSpeed(400);
		aGauche();
		Delay.msDelay(1000);
		arrierePlanTrouve = true;
		recherche = false;
	}

	private void suivreFrontiere() throws Exception{
		Motor.C.setSpeed(200);
		Motor.A.setSpeed(200);
		float[] couleurCapte = capteur.getColor();
		while (ligneCouleur.surFrontiere(couleurCapte)) {
			forward();
			couleurCapte = capteur.getColor();
		}
	}	
	
	public void suivre() throws Exception{
		float[] couleurCapte = capteur.getColor();
		while (true) {
			if(recherche){
				chercherLigne();
			}
			suivreFrontiere();
			if (ligneCouleur.surLigne(couleurCapte)) {
				ligneABordure();
			} else if (ligneCouleur.surArrierePlan(couleurCapte)) {
				 arrierePlanABordure();
			}
		}
	}

	private void aGauche() throws Exception{
		float[] couleurCapte = capteur.getColor();
		Motor.A.setSpeed(150);
		Stopwatch timer = new Stopwatch();
		int vitesseLeft = 150;
		while ( !ligneCouleur.surLigne(couleurCapte)) {
			if (timer.elapsed() > 50) {
				vitesseLeft -= 2;
				Motor.A.setSpeed(vitesseLeft);
				timer = new Stopwatch();
			}
			couleurCapte = capteur.getColor();
		}
		Motor.A.setSpeed(200);
		
	}

	private void aDroite() throws Exception {
		float[] couleurCapte = capteur.getColor();
		Motor.C.setSpeed(150);
		Stopwatch timer = new Stopwatch();
		int vitesseRight = 150;
		while (!ligneCouleur.surLigne(couleurCapte)) {
			if (timer.elapsed() > 50) {
				vitesseRight -= 2;
				Motor.C.setSpeed(vitesseRight);
				timer = new Stopwatch();
			}
			couleurCapte = capteur.getColor();
		}
		Motor.C.setSpeed(200);
	}

	private void ligneABordure() throws Exception{
		if (arrierePlanTrouve) aDroite();
		else aGauche();
	}

	private void arrierePlanABordure() throws Exception{
		if (arrierePlanTrouve) aGauche();
		else aDroite();
	}

	public void close() {
		capteur.close();
	}



}
