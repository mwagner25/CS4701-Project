package application;
	
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

import board.EvolutionTrack;
import board.GameState;
import board.Grid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import creature.*;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Get screen width
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			double width = primaryScreenBounds.getWidth();
			double height = primaryScreenBounds.getHeight();
			
			StackPane root = new StackPane();
			VBox titleComponents = new VBox();
			titleComponents.setAlignment(Pos.CENTER);
			
			Label title = new Label("Welcome to Darwin's Nightmare");
			titleComponents.getChildren().add(title);
			
			Button btn = new Button();
			btn.setText("Play");
			btn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					try {
						startGame(primaryStage, width, height);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			titleComponents.getChildren().add(btn);
			
			root.getChildren().add(titleComponents);
			Scene scene = new Scene(root, width, height);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Darwin's Nightmare");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startGame(Stage primaryStage, double width, double height) throws Exception{
		
		// Create grid
		Grid.screenWidth = width;
		Grid.screenHeight = height;
		int gridHeight = 20;
		int gridWidth = 20;
		
		final Grid grid = new Grid(gridHeight, gridWidth);
		
		// Populate the grid
		addRandomCreatures(1);
		addRandomDNA(10);
		
		Scene scene = new Scene(grid.getGrid(), width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Darwin's Nightmare");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		
		int x = (int)(Math.random() * gridHeight);
		int y = (int)(Math.random() * gridWidth);
		final Creature c = new Creature(EvolutionTrack.CAT, x, y);
		Grid.addConsumable(c, x, y);
		
		x = (int)(Math.random() * gridHeight);
		y = (int)(Math.random() * gridWidth);
		final Creature c2 = new Creature(EvolutionTrack.ELEPHANT, x, y);
		Grid.addConsumable(c2, x, y);
		
		x = (int)(Math.random() * gridHeight);
		y = (int)(Math.random() * gridWidth);
		final Creature c3 = new Creature(EvolutionTrack.BIRD, x, y);
		Grid.addConsumable(c3, x, y);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), ev -> {
			 Direction d;
			 Direction d2;
			 Direction d3;
			try {
				d = GameState.nextBestMove(c);
				d2 = GameState.nextBestMove(c2);
				d3 = GameState.nextBestMove(c3);
				
				System.out.println(d);
				c.move(d);
				c2.move(d2);
				c3.move(d3);
				
				System.out.println("CAT:" + c.getDNA() + "," + d);
				System.out.println("ELEPHANT:" + c2.getDNA() + "," + d2);
				System.out.println("BIRD:" + c3.getDNA() + "," + d3);
				Grid.refresh();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addRandomCreatures(int n) throws Exception{
		for(int i = 0; i < n; i++){
			// Insert the creature at a random point in the grid
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getConsumable() == null){
				Creature c = new Creature(EvolutionTrack.ROCK, randX, randY);
				Grid.addConsumable(c, randX, randY);
			}
			else{
				i--;
			}
		}
	}
	
	private void addRandomDNA(int n) throws Exception{
		System.out.println("GENERATING RANDOM DNA:");
		
		for(int i = 0; i < n; i++){
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getCreature() == null){
				DNA dna = new DNA(randX, randY, 10);
				System.out.println("X: " + randX + ", Y:" + randY);
				Grid.addConsumable(dna, randX, randY);
			}
			else{
				i--;
			}
			
		}
	}
}
