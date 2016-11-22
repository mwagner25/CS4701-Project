package creature;

import board.EvolutionTrack;
import board.Grid;
import javafx.scene.image.Image;

public class Creature {
	
	private EvolutionTrack evolutionTrack;
	private int evolutionStage;
	private int amountDNA;
	private int x;
	private int y;
	private String spritePathName;
	private Image sprite;
	
	// Constructor to create a creature of a given type
	public Creature(EvolutionTrack type){
		this.x = 0;
		this.y = 0;
		this.evolutionStage = 0;
		this.amountDNA = 0;
		this.evolutionTrack = type;
		this.spritePathName = "file:assets/png/" + type.toString() + evolutionStage + ".png";
		this.sprite = new Image(this.spritePathName, true);
	}
	
	public Creature(EvolutionTrack type, int x, int y){
		this.x = x;
		this.y = y;
		this.evolutionStage = 0;
		this.amountDNA = 0;
		this.evolutionTrack = type;
		this.spritePathName = "file:assets/png/" + type.toString() + evolutionStage + ".png";
		this.sprite = new Image(this.spritePathName, true);
	}

	// Function to move the creature in a given direction
	public void move(Direction d){
		if(d == Direction.RIGHT && y < Grid.tiles[0].length){
			x++;
		}
		if(d == Direction.UP && y > 0){
			y--;
		}
		
		if(d == Direction.LEFT && x >= 0){
			x--;
		}
		if(d == Direction.DOWN && x < Grid.tiles.length - 1){
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
	
	// Function to be called when a creature ingests DNA
	public void consumedDNA(){
		this.amountDNA += 10;
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
