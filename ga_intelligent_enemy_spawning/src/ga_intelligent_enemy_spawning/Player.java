package ga_intelligent_enemy_spawning;

import java.util.*;
import processing.core.PApplet;

public class Player{
	
	float health = 200;
	float fitness;
	float probability = 0;
	PApplet p;
	
	int statPoints = Globals.statPoints;
	int[] stats = new int[Globals.geneLength];
    Random random = new Random();
    
	public Player(){
	    while(statPoints > 0) {
	        	int insert = random.nextInt(8);
	        	if(stats[insert] < 10) {
	        		stats[insert] += 1;
	        		statPoints -= 1;
	        	}
	    	}
	}
	
	public void draw(PApplet parent) {
		p = parent;
		p.fill(140, 0, 255);
		p.noStroke();
		p.ellipse(Globals.imageWidth/2, Globals.imageHeight/2, Globals.playerSize, Globals.playerSize);
	}
}
