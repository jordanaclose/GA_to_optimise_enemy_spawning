package ga_intelligent_enemy_spawning;

import java.util.*;
import processing.core.PApplet;

public class Enemy{
	
	float fitness;
	float probability = 0;
	PApplet p;
	
	int statPoints = Globals.statPoints;
	int[] stats = new int[Globals.geneLength];
    Random random = new Random();
    
	public Enemy(){
	    while(statPoints > 0) {
	        	int insert = random.nextInt(8);
	        	if(stats[insert] < 10) {
	        		stats[insert] += 1;
	        		statPoints -= 1;
	        	}
	    	}
	}
	
	public void draw(PApplet parent) { //draw enemy at random point around centre of window
		p = parent;
		Random random = new Random();
		int offset = random.nextInt(30);
		int negative = random.nextInt(2);
		if(negative == 1) {
			offset *= -1;
		}
		int offset2 = random.nextInt(30);
		int negative2 = random.nextInt(2);
		if(negative2 == 1) {
			offset2 *= -1;
		}
		p.fill(140, 0, 255);
		p.noStroke();
		p.ellipse(Globals.imageWidth/2 + offset, Globals.imageHeight/2 + offset2, Globals.enemySize, Globals.enemySize);
	}
}
