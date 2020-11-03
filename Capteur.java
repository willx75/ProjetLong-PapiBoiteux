import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicColorSensor;

public class Capteur extends HiTechnicColorSensor{


	public Capteur() {
		super(SensorPort.S2);
		this.setCurrentMode(1);		
	}

	public float[] getColor(){
		int tailleEchantillon = this.sampleSize();
		float[][] valeurs = new float[11][3];
		float[] echantillon = new float[tailleEchantillon];
		for (int i = 0; i < 11; i++) {
			this.fetchSample(echantillon, 0);
			valeurs[i] = echantillon;
		}
		return median(valeurs);
	}	
	
	public static float[] toRGB(float[] valeurs){
		float[] rgb = new float[valeurs.length];
		for(int i = 0; i < valeurs.length; i++){
			rgb[i] = valeurs[i]*255;
		}
		return rgb;
	}
	
	public static float[] moyenne(float[][] valeurs, int n){
		float[] res = new float[n];	
		for(int i=0; i<n; i++){
			res[0] += valeurs[i][0];
			res[1] += valeurs[i][1];
			res[2] += valeurs[i][2];
		}
		for(int i=0; i<n; i++){
			res[i] /= n;
		}
		return res;
	}
	

	
	public static float[] median(float[][] valeurs){
		int cpt;
		float[] element, res;
		for (int i = 1; i < valeurs.length; i++) {
			element = valeurs[i];
			cpt = i - 1;
			while (cpt >= 0){
				valeurs[cpt + 1] = valeurs[cpt];
				cpt--;
			}
			valeurs[cpt + 1] = element;
		}
		res = valeurs[valeurs.length/2];
		return res;
	}

}