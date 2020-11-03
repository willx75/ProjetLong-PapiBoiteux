import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicColorSensor;

public class Calibration {
	
	public static void main(String[] args){
		etalonnage();
	}
	
	
	public static void mainour(String[] args){
		etalonnage();
	}

	public static void etalonnage() {
		File data = new File("calibrate");
		OutputStream os = null;
		DataOutputStream dos = null;
		boolean end = false;
		try {
			os = new FileOutputStream(data);
			dos = new DataOutputStream(os);
			byte[] buf = new byte[12];
			int nb_colors = 0;
			int nb_values = 0;
			LCD.drawString("ENTER : Nouvelle Couleur", 0, 2);
			LCD.drawString("RIGHT : Nouvelle Valeur De La Couleur", 0, 4);
			LCD.drawString("ESCAPE : Sauvegarder Fichier", 0, 6);
			while (!end) {
				if (Button.waitForAnyPress() != 0) {
					LCD.clearDisplay();
					if (Button.ESCAPE.isDown()) {
						LCD.drawString("Etalonnage fini", 5, 2);
						end = true;
					}
					else if (Button.ENTER.isDown()) {
						nb_colors++;
						nb_values = 0;
						print(nb_colors, nb_values);
						String Col = "" + nb_colors;
						for (int i = 12 - (12 - Col.length()); i < 12; i++) {
							if (i != 11) {
								Col += " ";
							} else {
								Col += "\n";
							}
						}
						buf = Col.getBytes();
						dos.write(buf);
					}
					else if (Button.RIGHT.isDown()) {
						nb_values++;
						print(nb_colors, nb_values);						
						int[] tab = roundRGB(rgb01toRGB(getRGB01()));
						String rgbLine = ligneRGB(String.valueOf(tab[0]), String.valueOf(tab[1]), String.valueOf(tab[2]));
						buf = rgbLine.getBytes();
						dos.write(buf);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static String ligneRGB(String r, String g, String b){
		return ajouterZeros(r) + " " + ajouterZeros(g) +" " + ajouterZeros(b) + "\n";
	}
	
	public static String ajouterZeros(String value){
		for(int i= 0; i < value.length(); i++)
			if (value.length() < 3)
				value = "0"+value;
		return value;	
	}
	
	public static void print(int i, int n){
		if(n == 0){
			LCD.drawString("Color " + i + " no value", 0, 4);
		}
		if(n == 1){
			LCD.drawString("Color " + i + " : " +n+ " value", 0, 4);
		}else{
			LCD.drawString("Color " + i + " : " +n+ " values", 0, 4);
		}		
	}
	
	public static float[] getRGB01() {
		HiTechnicColorSensor colorSensor = new HiTechnicColorSensor(SensorPort.S2);
		colorSensor.setCurrentMode(1);
		//System.out.println(colorSensor.getCurrentMode());
		//System.out.println(colorSensor.getName());
		//HiTechnicColorSensor.RGBMode colorRGB = (RGBMode) colorSensor.getRGBMode();
		int sampleSize = colorSensor.sampleSize();
		//Tableau de float
		float[] sample = new float[sampleSize];
		colorSensor.fetchSample(sample, 0);
		colorSensor.close();
		return sample;
	}
	 
	public static float[] rgb01toRGB(float[] values){
		float[] rgb = new float[values.length];
		for(int i = 0; i < values.length; i++){
			rgb[i] = values[i]*255;
		}
		return rgb;
	}	
	
	public static int[] roundRGB(float[] values){
		int [] res = new int[values.length];
		for(int i = 0; i < values.length; i++)	
			res[i] = Math.round(values[i]);
		return res;
	}
}
	
