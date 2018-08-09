package ga_intelligent_enemy_spawning;

import java.util.*;
import processing.core.PApplet;

public class GA extends PApplet{
	
	int generationNumber = 0;
	static PApplet p;
	Population population = new Population();
	Population randomPopulation = new Population();
	Player player = new Player();
	int turns = 0;

	public static void main(String[] args) {
		PApplet.main("ga_intelligent_enemy_spawning.GA");
		GA ga = new GA();
		GA randomGA = new GA();
		ga.population.initialiseEnemies();
		randomGA.randomPopulation.initialiseEnemies();
		System.out.println("Player: " + Arrays.toString(ga.player.stats));
		while(ga.generationNumber <= 200) {
			System.out.println("Current generation: " + ga.generationNumber);
			ga.fakeAttack();
			ga.population.getTotalFitness();
			ga.population.getProbabilities(ga.population.totalFitness);
			System.out.println("Total fitness: " + ga.population.totalFitness);
			for(int i = 0; i < ga.population.enemies.size(); i++) {
				System.out.print("Fitness: " + ga.population.enemies.get(i).fitness + " ");
				System.out.print("Probability: " + ga.population.enemies.get(i).probability + " ");
				System.out.println(Arrays.toString(ga.population.enemies.get(i).stats));
			}
			ga.population.getFittestChromosome();
			System.out.println("Fittest: " + Arrays.toString(ga.population.enemies.get(ga.population.currentFittest).stats));
			ga.selection(ga.population);
			for(int j = 0; j < ga.population.enemies.size(); j++) {
				System.out.print("Fitness: " + ga.population.enemies.get(j).fitness + " ");
				System.out.print("Probability: " + ga.population.enemies.get(j).probability + " ");
				System.out.println(Arrays.toString(ga.population.enemies.get(j).stats));
			}
			ga.fakeAttack();
			ga.population.getTotalFitness();
			ga.population.getProbabilities(ga.population.totalFitness);
			ga.crossover(ga.population);
			for(int k = 0; k < ga.population.enemies.size(); k++) {
				System.out.print("Fitness: " + ga.population.enemies.get(k).fitness + " ");
				System.out.print("Probability: " + ga.population.enemies.get(k).probability + " ");
				System.out.println(Arrays.toString(ga.population.enemies.get(k).stats));
			}
			ga.fakeAttack();
			ga.population.getTotalFitness();
			ga.population.getProbabilities(ga.population.totalFitness);
			for(int l = 0; l < 2; l++) {
				ga.population.getLeastFitChromosome();
				System.out.println("Least fit: " + Arrays.toString(ga.population.enemies.get(ga.population.leastFit).stats));
				ga.population.enemies.remove(ga.population.leastFit);
			}
			ga.fakeAttack();
			ga.population.getTotalFitness();
			ga.population.getProbabilities(ga.population.totalFitness);
			for(int m = 0; m < ga.population.enemies.size(); m++) {
				System.out.print("Fitness: " + ga.population.enemies.get(m).fitness + " ");
				System.out.print("Probability: " + ga.population.enemies.get(m).probability + " ");
				System.out.println(Arrays.toString(ga.population.enemies.get(m).stats));
			}
			ga.generationNumber++;
		}
		while(ga.player.health > 0) {
			ga.attack(ga.population.enemies);
			System.out.println("Health :" + ga.player.health);
		}
		while(randomGA.player.health > 0) {
			randomGA.attack(randomGA.randomPopulation.enemies);
			System.out.println("Health :" + randomGA.player.health);
		}
		System.out.println("Player killed in " + ga.turns + " turns with GA enemy selection");
		System.out.println("Player killed in " + randomGA.turns + " turns with random enemy selection");
	}
	
	public void settings(){
		size(Globals.imageWidth, Globals.imageHeight);
    }

    public void setup(){
	
    }

    public void draw(){
    		drawScreen();
    		player.draw(this);
    		for(int i = 0; i < population.enemies.size(); i++) {
    			population.enemies.get(i).draw(this);
    		}
    }
    
    public void mousePressed() {
    		
    }
    
    public void drawScreen() {
    		background(255, 200, 220);
		textSize(10);
		text("Player Health", 10, 25);
		stroke(0);
		noFill();
		rect(30, 30, 200, 10);
		fill(255, 0, 0);
		rect(30, 30, player.health, 10); //draw health bar for player
		textSize(20);
		//int seconds = millis()/1000;
		//int millis = millis()%1000;
		//text(seconds + ":" + millis, 500, 560);
    }
    
    public void selection(Population population) {
    		for(int j = 0; j < 2; j++) {
    			population.chooseParents();
    		}
    		//population.chromosomes.clear();
    		for(int i = 0; i < population.toBeCrossed.size(); i++) {
    			System.out.print("Fitness: " + population.toBeCrossed.get(i).fitness + " ");
    			System.out.println("Selected: " + Arrays.toString(population.toBeCrossed.get(i).stats));
    		}
    }
    
	public void crossover(Population population) {
		for(int i = 0; i < Globals.crossoverPoint; i++) {
    			int tempValue = population.toBeCrossed.get(0).stats[i];
    			population.toBeCrossed.get(0).stats[i] = population.toBeCrossed.get(1).stats[i];
    			population.toBeCrossed.get(1).stats[i] = tempValue;
		}
		for(int j = 0; j < population.toBeCrossed.size(); j++) {
			System.out.println("Crossed: " + Arrays.toString(population.toBeCrossed.get(j).stats));
		}
		mutate(population);
		population.enemies.add(population.toBeCrossed.get(0));
		population.toBeCrossed.remove(0);
		population.enemies.add(population.toBeCrossed.get(0));
		population.toBeCrossed.clear();
	}
	
	public void mutate(Population population) {
		for(int i = 0; i < population.toBeCrossed.size(); i++) {
			while(population.toBeCrossed.get(i).fitness > 40) {
				Random random = new Random();
				int randomInt = random.nextInt(Globals.geneLength);
				if(population.toBeCrossed.get(i).stats[randomInt] > 0) {
					population.toBeCrossed.get(i).stats[randomInt] -= 1;
				}
			}
		}
	}
	
	public void fakeAttack() {
		for(int i = 0; i < population.enemies.size(); i++) {
			int lifeLost = 0;
			if(population.enemies.get(i).stats[Globals.archery] >= 5 && player.stats[Globals.lightArmour] < 5) {
				lifeLost += 5;
			}
			if(population.enemies.get(i).stats[Globals.destruction] >= 5 && player.stats[Globals.block] < 5) {
				lifeLost += 5;
			}
			if(population.enemies.get(i).stats[Globals.twoHanded] >= 5 && player.stats[Globals.heavyArmour] < 5) {
				lifeLost += 5;
			}
			if(population.enemies.get(i).stats[Globals.oneHanded] >= 5 && player.stats[Globals.restoration] < 5) {
				lifeLost += 5;
			}
			population.enemies.get(i).fitness = lifeLost;
			System.out.print("Fitness: " + population.enemies.get(i).fitness);
			System.out.println(Arrays.toString(population.enemies.get(i).stats));
		}
	}
	
	public void attack(ArrayList<Enemy> enemies) {
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).stats[Globals.archery] >= 5 && player.stats[Globals.lightArmour] < 5) {
				player.health -= 5;
			}
			if(player.health <= 0) {
				break;
			}
			if(enemies.get(i).stats[Globals.destruction] >= 5 && player.stats[Globals.block] < 5) {
				player.health -= 5;
			}
			if(player.health <= 0) {
				break;
			}
			if(enemies.get(i).stats[Globals.twoHanded] >= 5 && player.stats[Globals.heavyArmour] < 5) {
				player.health -= 5;
			}
			if(player.health <= 0) {
				break;
			}
			if(enemies.get(i).stats[Globals.oneHanded] >= 5 && player.stats[Globals.restoration] < 5) {
				player.health -= 5;
			}
			if(player.health <= 0) {
				break;
			}
		}
		turns += 1;
		System.out.println("Health: " + player.health);
	}
}
