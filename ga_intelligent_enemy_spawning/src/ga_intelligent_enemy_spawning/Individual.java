package ga_intelligent_enemy_spawning;

import java.util.Random;
import processing.core.PApplet;

public class Individual{
	float fitness;
	float probability = 0;
	int[] genes = new int[Globals.geneLength];
	PApplet p;
	
	public Individual(){
        Random random = new Random();
        
        for(int i = 0; i < genes.length; i++) {
        		genes[i] = random.nextInt(2);
        }
	}
}
