package creature;

public abstract class Creature {
	
	public enum EvolutionTrack{
		CAT, TRUNK, BIRD, HUMAN, TECH, PLANT, GEO
	}
	
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	public void evolve(){
		return;
	}
	public EvolutionTrack evolutionTrack;
	public int evolutionStage;
	public int amountDNA;
	public int x;
	public int y;

	public void move(Direction d){
		if(d == Direction.UP){
			//if(y < stageMax)
				y++;
		}
		if(d == Direction.DOWN){
			if( y > 0)
				y--;
		}
		if(d == Direction.LEFT){
			if(x > 0)
				x--;
		}
		if(d == Direction.RIGHT){
			//if(x < stageMax)
				x++;
		}
	}

}
