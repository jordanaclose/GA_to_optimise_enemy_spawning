package ga_intelligent_enemy_spawning;

import java.util.*;

public class Population {
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Enemy> toBeCrossed = new ArrayList<Enemy>();

	float totalFitness;
	float totalProbability;
	float probability = 0;
	int currentFittest;
	int parent;
	int leastFit;
	
	public void initialiseEnemies() {
		for(int i = 0; i < Globals.populationSize; i++) {
			enemies.add(new Enemy());
		}
	}
	
	public void getTotalFitness() {
		totalFitness = 0;
	    	for(int i = 0; i < enemies.size(); i++) {
	    		totalFitness += enemies.get(i).fitness;
	    	}
	}
	
	public void getProbabilities(float totalFitness) {
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).probability = enemies.get(i).fitness / totalFitness;
		}
	}
	
	public void getTotalProbabilities() {
		totalProbability = 0;
		for(int i = 0; i < enemies.size(); i++) {
    			totalProbability += enemies.get(i).probability;
    		}
	}
	
	public void getFittestChromosome() {
		currentFittest = 0;
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).fitness > enemies.get(currentFittest).fitness) {
				currentFittest = i;
			}
		}
	}
	
	public void getLeastFitChromosome() {
		leastFit = 0;
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).fitness < enemies.get(leastFit).fitness) {
				leastFit = i;
			}
		}
	}
	
	public void chooseParents() {
		getTotalProbabilities();
		Random random = new Random();
		float randomNumber = random.nextFloat() * totalProbability;
		float lastRunningTotal = 0;
		float runningTotal = 0;
		System.out.println("Random number: " + randomNumber);
		for(int i = 0; i < enemies.size(); i++) {
			runningTotal += enemies.get(i).probability;
			if(randomNumber > lastRunningTotal && randomNumber <= runningTotal) {
				parent = i;
				System.out.println("Parent: " + Arrays.toString(enemies.get(i).stats));
				probability = enemies.get(i).probability;
				toBeCrossed.add(enemies.get(i));
				break;
			}
			lastRunningTotal += enemies.get(i).probability;
		}
	}
}
