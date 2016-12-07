package creature;

import board.EvolutionTrack;
import board.GameState;
import board.Grid;
import javafx.scene.image.Image;

public class Creature implements Consumable{
	
	private EvolutionTrack evolutionTrack;
	private int evolutionStage;
	private int amountDNA;
	private int x;
	private int y;
	private String spritePathName;
	private Image sprite;
	
	public Creature(EvolutionTrack type, int x, int y){
		this.x = x;
		this.y = y;
		this.evolutionStage = 0;
		this.amountDNA = 0;
		this.evolutionTrack = type;
		this.spritePathName = "file:assets/png/" + type.toString() + evolutionStage + ".png";
		this.sprite = new Image(this.spritePathName, true);
		
		GameState.creatures.put(this, GameState.lastCreatureID);
		GameState.lastCreatureID++;
	}

	// Function to move the creature in a given direction
	public void move(Direction d){
		if(d == Direction.RIGHT && x < Grid.tiles[0].length - 1){
			x++;
		}
		if(d == Direction.UP && y > 0){
			y--;
		}
		
		if(d == Direction.LEFT && x > 0){
			x--;
		}
		if(d == Direction.DOWN && y < Grid.tiles.length - 1){
			y++;
		}
	}
	
	// Function to be called when the creature evolves
	public void evolve(){
		this.evolutionStage++;
		
		// If this creature has reached its maximum point of evolution, stop
		if(evolutionStage > 3){
			return;
		}
		
		this.spritePathName = "file:assets/png/" + this.evolutionTrack.toString() + evolutionStage + ".png";
		this.sprite = new Image(this.spritePathName, true);
	}
	
	// Getter for this creature's current level of DNA
	public int getDNA(){
		return amountDNA;
	}
	
	public int getEvolutionStage(){
		return this.evolutionStage;
	}
	
	// Function to be called when a creature ingests a consumable DNA/creature
	public boolean consumedDNA(Consumable consumable){
		
		if(consumable instanceof Creature){
			if(this.amountDNA > consumable.getDNA()){
				this.amountDNA += consumable.getDNA();
				
				if(amountDNA > 10 * Math.pow(this.evolutionStage + 1, 2)){
					 this.evolve();
				}
				
				return true;
			}
			else{
				return false;
			}
		}
		
		// Else this is a DNA
		this.amountDNA += consumable.getDNA();
		
		if(amountDNA > 10 * Math.pow(this.evolutionStage + 1, 2)){
			 this.evolve();
		}
		
		return true;
	}
	
	// Getter for sprite
	public Image getImage(){
		return sprite;
	}
	
	// Getter for evolution track
	public EvolutionTrack getEvolutionTrack(){
		return this.evolutionTrack;
	}
	
	// Getter for x coordinate
	public int getX(){
		return x;
	}
	
	// Getter for y coordinate
	public int getY(){
		return y;
	}

}
