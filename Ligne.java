import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.IllegalFormatException;

import lejos.hardware.Button;

public class Ligne {
	public final static int nbValues = 10;

	public static void main(String[] args) throws Exception{
		Capteur capteur = new Capteur();
		int tailleEchantillon = capteur.getRGBMode().sampleSize();
		float[][] echantillon = new float[nbValues][tailleEchantillon];
		for (int i = 0; i < nbValues; i++) {
			System.out.println("Add a value");
			Button.DOWN.waitForPressAndRelease();
			echantillon[i] = capteur.getColor();
		}
		float[] valeurs = Capteur.moyenne(echantillon, nbValues);
		try {
			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter("line.color", false)));
			w.printf("%.9g\n%.9g\n%.9g\n", valeurs[0], valeurs[1], valeurs[2]);
			w.flush();
			w.close();
			capteur.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			capteur.close();
			throw fnfe;

		} catch (IllegalFormatException ife) {
			ife.printStackTrace();
			capteur.close();
			throw ife;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			capteur.close();
			throw ioe;
		} 
	}
}
