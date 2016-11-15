package application;
	
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import board.EvolutionTrack;
import board.GameState;
import board.Grid;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import creature.*;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
		
			// Get screen width
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			double width = primaryScreenBounds.getWidth();
			double height = primaryScreenBounds.getHeight();
			
			// Create grid
			Grid.screenWidth = width;
			Grid.screenHeight = height;
			int gridHeight = 30;
			int gridWidth = 30;
			
			Grid grid = new Grid(gridHeight, gridWidth);
			
			// GameState variables
			ArrayList<Creature> creatures = new ArrayList<Creature>();	
			GameState.grid = grid;
			GameState.creatures = creatures;
			
			// Populate the grid
			addRandomCreatures(10);
			
			// Render scene
			Scene scene = new Scene(grid.getGrid(), width, height);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Darwin's Nightmare");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addRandomCreatures(int n){
		for(int i = 0; i < n; i++){
			Creature c = new Creature(EvolutionTrack.ROCK);
			
			// Insert the creature at a random point in the grid
			int randX = (int) (Math.random() * GameState.grid.getTiles().length);
			int randY = (int) (Math.random() * GameState.grid.getTiles()[0].length);
			GameState.grid.addCreature(c, randX, randY);
		}
	}
}
