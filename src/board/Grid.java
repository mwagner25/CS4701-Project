package board;

import creature.Consumable;
import creature.Creature;
import creature.DNA;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Grid {
	
	public static double screenWidth;
	public static double screenHeight;
	
	// Variable to represent the grid
	private Pane grid;
	
	public static Tile[][] tiles;
	
	/*
	 * Constructor to create a grid with a specified number of
	 * rows and columns
	 * 
	 * @param rows - the number of rows
	 * @param columns - the number of columns
	 * 
	 * */
	public Grid(int rows, int columns){
		Grid.tiles = new Tile[rows][columns];
		this.grid = (Pane) generateGrid(rows, columns);
	}
	
	/*
	 * Function to generate the Pane of a specified number of 
	 * rows and columns
	 * 
	 * @param rows - the number of rows
	 * @param columns - the number of columns
	 * 
	 * @returns the Pane object
	 * */
	public static Parent generateGrid(int rows, int columns){
		Pane root = new Pane();
		root.setPrefSize(Grid.screenWidth, Grid.screenHeight);
		
		// Calculate the width of each rectangle, depending on screen size
		double tileWidth = Grid.screenWidth / rows;
		double tileHeight = Grid.screenHeight / columns;
		
		for(int i = 0; i < rows; i++){
			for(int g = 0; g < columns; g++){
				Tile t = new Tile(tileWidth, tileHeight);
				Grid.tiles[g][i] = t;
				
				t.setTranslateX(g * tileWidth);
				t.setTranslateY(i * tileHeight);
				
				root.getChildren().add(t);
			}
		}
		
		return root;
	}
	
	public static void addConsumable(Consumable d, int x, int y) throws Exception{
		Grid.tiles[x][y].setConsumable(d);
	}
	
	/*
	 * Getter to return the grid
	 * 
	 * */
	public Pane getGrid(){
		return this.grid;
	}
	
	/*
	 * Getter to return the tiles on the grid
	 */
	public static Tile[][] getTiles(){
		return Grid.tiles;
	}
	
	public static void refresh() throws Exception{
		for(int x = 0; x < tiles[0].length; x++){
			for(int y = 0; y < tiles.length; y++){
				Tile oldTile = Grid.tiles[x][y];
				Creature c = oldTile.getCreature();
				if(c != null){
					int xCoord = c.getX();
					int yCoord = c.getY();
					
					// Inconsistency
					if(xCoord != x || yCoord != y){
						Tile nextTile = tiles[xCoord][yCoord];
						
						if(nextTile.getConsumable() != null){
							if(c.consumedDNA(nextTile.getConsumable())){
								nextTile.clearTile();
								nextTile.setConsumable(c);		
								
								if (nextTile.getConsumable() instanceof Creature){
									GameState.creatures.remove(nextTile.getConsumable());
								}
								
							}
							
							oldTile.clearTile();
							continue;
						}
						
						nextTile.setConsumable(c);
						oldTile.clearTile();
					}
				}
			}
		}
	}

}
