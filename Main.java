
public class Main {

	public static void main(String[] args) throws Exception{
		Moteur suiveurDeLigne = null;
		try {
			suiveurDeLigne = new Moteur();
			suiveurDeLigne.suivre();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(suiveurDeLigne != null) suiveurDeLigne.close();
				
		}
	}
}
