import java.util.ArrayList;
import java.util.Random;

public class RankSimulator {
	
	public static final int BRONZE_V = 1, BRONZE_IV = 2, BRONZE_III = 3, BRONZE_II = 4, BRONZE_I = 5;
	public static final int SILVER_V = 6, SILVER_IV = 7, SILVER_III = 8, SILVER_II = 9, SILVER_I = 10;
	public static final int GOLD_V = 11, GOLD_IV = 12, GOLD_III = 13, GOLD_II = 14, GOLD_I = 15;
	public static final int PLATINUM_V = 16, PLATINUM_IV = 17, PLATINUM_III = 18, PLATINUM_II = 19, PLATINUM_I = 20;
	public static final int DIAMOND_V = 21, DIAMOND_IV = 22, DIAMOND_III = 23, DIAMOND_II = 24, DIAMOND_I = 25;
	public static final int CHALLENGER_V = 26, CHALLENGER_IV = 27, CHALLENGER_III = 28, CHALLENGER_II = 29, CHALLENGER_I = 30;
	
	public static void main(String[] args) {
		Random r = new Random();
		Player[] players = new Player[900];
		ArrayList<Player> waitingPlayers = new ArrayList<Player>();
		ArrayList<Player> finishedPlayers = new ArrayList<Player>();
		
		for(int i = 0; i < players.length; i++) {
			int skillLevel = r.nextInt(900);
			players[i] = new Player(skillLevel, BRONZE_V);
			waitingPlayers.add(players[i]);
		}
		
		while(waitingPlayers.size() > 0) {
			Player[] t1 = new Player[5];
			Player[] t2 = new Player[5];
			double t1Total = 0.0;
			double t2Total = 0.0;
			double t1Chance = 0.0;
			double t2Chance = 0.0;
			
			for(int i = 0; i < 5; i++) {
				int p = r.nextInt(waitingPlayers.size());
				t1[i] = waitingPlayers.get(p);
				int s = t1[i].skillLevel;
				waitingPlayers.remove(p);
				t1Total += Math.pow(s, Math.log(s));
			}
			
			for(int i = 0; i < 5; i++) {
				int p = r.nextInt(waitingPlayers.size());
				t2[i] = waitingPlayers.get(p);
				int s = t2[i].skillLevel;
				waitingPlayers.remove(p);
				t2Total += Math.pow(s, Math.log(s));
			}
			
			t1Chance = t1Total / (t1Total + t2Total);
			t2Chance = t2Total / (t1Total + t2Total);
			
			printTeams(t1, t2, t1Chance, t2Chance);
		}
	}
	
	public static void printTeams(Player[] t1, Player[] t2, double t1Chance, double t2Chance) {
		for(int i = 0; i < 5; i++) {
			System.out.print(t1[i].skillLevel);
			if(i < 4) {
				System.out.print(", ");
			} else {
				System.out.printf(" : %.2f\n", t1Chance);
			}
		}
		
		for(int i = 0; i < 5; i++) {
			System.out.print(t2[i].skillLevel);
			if(i < 4) {
				System.out.print(", ");
			} else {
				System.out.printf(" : %.2f\n", t2Chance);
			}
		}
		
		System.out.println();
	}
	
}
