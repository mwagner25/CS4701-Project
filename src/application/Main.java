package application;
	
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import board.EvolutionTrack;
import board.GameState;
import board.Grid;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
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
			int gridHeight = 10;
			int gridWidth = 10;
			
			final Grid grid = new Grid(gridHeight, gridWidth);
			
			// GameState variables
			ArrayList<Creature> creatures = new ArrayList<Creature>();	
			GameState.creatures = creatures;
			
			// Populate the grid
			addRandomCreatures(2);
			addRandomDNA(3);
			
			// Render scene
			Scene scene = new Scene(grid.getGrid(), width, height);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Darwin's Nightmare");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			
			final Creature c = new Creature(EvolutionTrack.CAT);
			Grid.addCreature(c, 0, 0);
			
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), ev -> {
				 Direction d = GameState.nextBestMove(c);
				 System.out.println(d);
				 c.move(d);
				 Grid.refresh();
			 }));
		    timeline.setCycleCount(Animation.INDEFINITE);
		    timeline.play();
			
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
			// Insert the creature at a random point in the grid
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getDNA() == null){
				Creature c = new Creature(EvolutionTrack.ROCK, randX, randY);
				Grid.addCreature(c, randX, randY);
			}
			else{
				i--;
			}
		}
	}
	
	private void addRandomDNA(int n){
		System.out.println("GENERATING RANDOM DNA:");
		
		for(int i = 0; i < n; i++){
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getCreature() == null){
				DNA dna = new DNA(randX, randY, 10);
				System.out.println("X: " + randX + ", Y:" + randY);
				Grid.addDNA(dna, randX, randY);
			}
			else{
				i--;
			}
			
		}
	}
}
