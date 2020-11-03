import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LigneCouleur {

	float[] ligne;
	float[] arrierePlan;
	
	public LigneCouleur() throws IOException{
		try {
			ligne = new float[3];
			arrierePlan = new float[3];
			BufferedReader lineReader = new BufferedReader(new FileReader("line.color"));
			BufferedReader backgroundReader = new BufferedReader(new FileReader("background.color"));
			for (int i = 0; i < 3; i++) {
				ligne[i] = Float.parseFloat(lineReader.readLine());
				arrierePlan[i] = Float.parseFloat(backgroundReader.readLine());
			}
			lineReader.close();
			backgroundReader.close();
		} catch (IOException e) {
			throw e;
		}	
	}

	
	private static float distance(float[] d1, float[] d2){
		float r, g, b;
		r = d2[0] - d1[0];
		g = d2[1] - d1[1];
		b = d2[2] - d1[2];
		return (float) Math.sqrt((Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2)));
	}
	
	public boolean surLigne(float[] valeurs){		
		return distance(valeurs, ligne) <= distance(valeurs, arrierePlan);
	}

	public boolean surArrierePlan(float[] valeurs){
		return distance(valeurs, ligne) >= distance(valeurs, arrierePlan);
	}
	
	public boolean surFrontiere(float[] valeurs){
		return Math.abs(distance(valeurs, ligne) - distance(valeurs, arrierePlan)) 
				<= (Math.abs(distance(ligne, arrierePlan))/2.0f);
	}
}
