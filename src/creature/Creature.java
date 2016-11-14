package creature;

public abstract class Creature {
	
	public enum EvolutionTrack{
		CAT, TRUNK, BIRD, HUMAN, TECH, PLANT, GEO
	}

	public void evolve(){
		return;
	}
	public EvolutionTrack evolutionTrack;
	public int evolutionStage;
	public int amountDNA;


}
