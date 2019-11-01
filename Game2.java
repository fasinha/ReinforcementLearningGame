import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
public class Game2 
{
	public static boolean playerisA = true;
	public static int[][][] a_seen; 
	public static int[][][] b_seen;
	public static int A; 
	public static int B_score;
	public static HashMap<String, Integer> state;
	
	public static void main(String[] args)
	{
		int ltarget = 4;
		int utarget = 4;
		int ndice = 2;
		int[][][] winCount = new int[ltarget][ltarget][ndice+1];
		int[][][] loseCount = new int[ltarget][ltarget][ndice+1];
		int m = 500;
		
		state = new HashMap<String, Integer>();//state[0] is X and state[1] is Y
		state.put("X", 0);
		state.put("Y", 0);
		A = 0;
		B_score = 0; 
		a_seen = new int[ltarget][ltarget][ndice+1];
		b_seen = new int[ltarget][ltarget][ndice+1];
		
		
		for (int p = 1; p <= 100000 ; p++)
		{ 
			System.out.println("Game " + p);
			while (A < ltarget && A < utarget && B_score < ltarget && B_score < utarget)
			{
				player_play(2, ltarget, utarget, ndice, m, winCount, loseCount);
				playerisA = !playerisA; //switch 
				int temp_X = state.get("X");
				int temp_Y = state.get("Y");
				state.put("X", temp_Y);
				state.put("Y", temp_X);
				System.out.println(state);
			}
			if (A >= ltarget && A <= utarget)
			{
				System.out.println("A wins");
				for (int i=0; i < a_seen.length; i++)
				{
					for (int j = 0; j < a_seen[i].length; j++)
					{
						for (int k = 0; k < a_seen[i][j].length; k++)
						{
							if(a_seen[i][j][k] != 0)
							{
								winCount[i][j][k] += 1;
								System.out.println("" + i + j + k);
							}
							
						}
					}
				}
				//now loop through b seen and update losecount accordingly
				for (int i=0; i < b_seen.length; i++)
				{
					for (int j = 0; j <b_seen[i].length; j++)
					{
						for (int k = 0; k < b_seen[i][j].length; k++)
						{
							if(b_seen[i][j][k] != 0)
							{
								loseCount[i][j][k] += 1;
								System.out.println("hi");
							}
						
						}
					}
				}
			}
			else if (B_score >= ltarget && B_score <= utarget)
			{
				System.out.println("B wins");
				for (int i=0; i < b_seen.length; i++)
				{
					for (int j = 0; j <b_seen[i].length; j++)
					{
						for (int k = 0; k < b_seen[i][j].length; k++)
						{
							if (b_seen[i][j][k] != 0)
							{
								winCount[i][j][k] += 1;
							}
							
						}
					}
				}
				//now fill in lose count for A because A has lost 
				for (int i=0; i < a_seen.length; i++)
				{
					for (int j = 0; j <a_seen[i].length; j++)
					{
						for (int k = 0; k < a_seen[i][j].length; k++)
						{
							if (a_seen[i][j][k] != 0)
							{
								loseCount[i][j][k] += 1;
							}
							
						}
					}
				}
			}

		A = 0;
		B_score = 0;
		state.put("X", 0);
		state.put("Y", 0);
		}
		
		int[][] correct_toroll = new int[ltarget][ltarget];
		String[][] pmatrix = new String[ltarget][ltarget];
		
		System.out.println("correct dice to roll");
		for (int i = 0; i < correct_toroll.length; i++)
		{
			for (int j = 0; j < correct_toroll[i].length; j++)
			{
				int max = 0;
				for (int kprime = 0; kprime < winCount[i][j].length; kprime++)
				{
					if (winCount[i][j][kprime] > max)
					{
						max = winCount[i][j][kprime];
						correct_toroll[i][j] = kprime; 
					}	
				}
				System.out.print(correct_toroll[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("probability matrix");
		for (int i = 0; i < pmatrix.length; i++)
		{
			for (int j = 0; j < pmatrix[i].length; j++)
			{
				int correct_num = correct_toroll[i][j];
				//System.out.println("win count for correct num is " + winCount[i][j][correct_num]);
				//System.out.println("lose count for correct num is " + loseCount[i][j][correct_num]);
				if ((winCount[i][j][correct_num] + loseCount[i][j][correct_num]) == 0)
				{
					pmatrix[i][j] = "0";
				}
				else {
					//System.out.println("wincount is " + winCount[i][j][correct_num]);
					//System.out.println("losecount is " + loseCount[i][j][correct_num]);
					double prob = (double)(winCount[i][j][correct_num])/(winCount[i][j][correct_num] + loseCount[i][j][correct_num]);
					DecimalFormat df = new DecimalFormat("#.####");
					pmatrix[i][j] = df.format(prob);
					//pmatrix[i][j] = (double)(winCount[i][j][correct_num] / (winCount[i][j][correct_num] + loseCount[i][j][correct_num]));
				}
				System.out.print(pmatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
		
		
	/*	Scanner sc = new Scanner(System.in);
		System.out.println("Please enter number of sides: ");
		int nsides = sc.nextInt();
		System.out.println("please enter lowest winning score: ");
		int ltarget = sc.nextInt();
		System.out.println("please enter highest winning score: ");
		int utarget = sc.nextInt();
		System.out.println("please enter number of dice: ");
		int ndice = sc.nextInt();
		System.out.println("please enter hyperparameter m: ");
		double m = sc.nextDouble();
		System.out.println("please enter number of games to play: ");
		int ngames = sc.nextInt();
		
		int[][][] winCount = new int[ltarget][ltarget][ndice+1];
		int[][][] loseCount = new int[ltarget][ltarget][ndice+1];
		
		for(int i = 1; i <=ngames; i++)
		{
			state = new HashMap<String, Integer>(); //state[0] is X and state[1] is Y
			state.put("X", 0);
			state.put("Y", 0);
			A = 0;
			B = 0; 
			a_seen = new int[ltarget][ltarget][ndice+1];
			b_seen = new int[ltarget][ltarget][ndice+1];
			while (A < ltarget && B < ltarget)
			{
				player_play(nsides, ltarget, utarget, ndice, m, winCount, loseCount);
				playerisA = !playerisA; //switch 
			}
			System.out.println("A is " + A + "B is " + B);
		}*/
		
	
	
	public static int roll(int nsides)
	{
		return (int)(Math.random()*nsides +1); // returns [1, nsides];
	}
	
	public static void player_play(int nsides, int ltarget, int utarget, int ndice, double m, int[][][] winCount, int[][][] loseCount)
	{
		ArrayList<Double> fj_array = new ArrayList<Double>();
		for (int j = 0; j <= ndice; j++)
		{
			if (j == 0)
			{
				double fj = 0.;
				fj_array.add(j, fj);
			}
			else {
				System.out.println(winCount.length);
				System.out.println(state.get("Y"));
				//System.out.println("wc is " + winCount[state.get("X")][state.get("Y")][j]);
				if(winCount[state.get("X")][state.get("Y")][j] + 
						loseCount[state.get("X")][state.get("Y")][j] == 0)
				{
					double fj = 0.5;
					fj_array.add(j, fj);
				}
				else 
				{
					double fj = (double) winCount[state.get("X")][state.get("Y")][j] / (winCount[state.get("X")][state.get("Y")][j] + 
							loseCount[state.get("X")][state.get("Y")][j]);
					fj_array.add(j, fj);
				}
			}
			
		}
		System.out.println("fj array is " + fj_array);
		double max_fj = Collections.max(fj_array); //get the maximum fj
		int B = 0; //B is the value of J that corresponds to the max fj
		for (int i = 0; i < fj_array.size(); i++)
		{
			if(fj_array.get(i) == max_fj)
			{
				B = i; //set B
			}
		}
		
		
		double g = sum_without_B(fj_array, B); //get the sum of all fj without B's value
	
		//T is the number of games that have seen XY
		int T = games_XY(winCount, loseCount, ndice, state.get("X"), state.get("Y"));
	
		double pb = prob_B(T, fj_array.get(B), m, ndice);
		
		HashMap<Integer, Double> probabilities = new HashMap<Integer, Double>();
		System.out.println(B);
		int jloop = 0;
		while (jloop <= ndice)
		{
			System.out.println("j is " + jloop);
			if (jloop == B) //for all J that are NOT equal to B
			{
				jloop++; //so we increment when we reach j =B
			}
			else {
				if (jloop == 0)
				{
					double pj = 0.;
					probabilities.put(jloop, pj);
					jloop++;
				}
				else
				{
					System.out.println("fj is " + fj_array.get(jloop));
					System.out.println("g is " + g);
					double pj = (1-pb)*((T*fj_array.get(jloop) + m)/(g*T + m*(ndice-1)));
					System.out.println("j is" + pj);
					probabilities.put(jloop, pj);
					jloop++;
				}
				
			}
		}
		probabilities.put(B, pb);
		System.out.println("probabilities is " + probabilities); 
		int numdicetoplay = sample(probabilities, ndice);
		System.out.println("num dice to play is " + numdicetoplay); 
		int amtrolled = 0;
		for (int a = 1; a <= numdicetoplay; a++)
		{
			amtrolled += roll(nsides);
		}
		System.out.println("amt rolled is " +amtrolled); 
		if (playerisA)
		{
			a_seen[state.get("X")][state.get("Y")][numdicetoplay] += 1; 
			A += amtrolled; 
			state.put("X", state.get("X")+amtrolled);
		}
		else
		{
			b_seen[state.get("X")][state.get("Y")][numdicetoplay] += 1; 
			B_score += amtrolled; 
			state.put("X", state.get("X")+amtrolled);
		}
		System.out.println("A" + A);
		System.out.println("B" + B_score);
		System.out.println("state is now " + state.get("X") + " " + state.get("Y"));
	}
	
	/*
	 * samples from a hashmap of distributions
	 */
	public static int sample(HashMap<Integer, Double> probabilities, int ndice)
	{
		HashMap<Integer, Double> u = new HashMap<Integer, Double>();
		System.out.println(probabilities.keySet());
		System.out.println(probabilities.values());
		for (int elem : probabilities.keySet())
		{
			u.put(elem, probabilities.get(elem));
		}
		for (int i = 1; i <= ndice; i++)
		{
			System.out.println("u get"  + (i-1) + " is " + u.get(i-1));
			System.out.println("i is" + i + "p is" + probabilities.get(i));
			u.put(i, u.get(i-1)+probabilities.get(i));
		}
		
		System.out.println("U is " + u);
		double x = Math.random();
		for (int i = 0; i < ndice; i++)
		{
			if (x < u.get(i))
			{
				System.out.println("Dice to roll is " + i);
				return i;
				
			}
		}
		return ndice;
	}
	
	/*
	 * finds the probability of the die labeled B
	 * probability that player rolls B 
	 */
	public static double prob_B(int T, double fb, double m, int ndice)
	{
		return ((T*fb) + m) / ((T*fb) + (ndice*m));
	}
	
	/*
	 * calculates the sum of probabilities without B 
	 */
	public static double sum_without_B(ArrayList<Double> list, int B)
	{
		double answer = 0.0;
		for(double elem : list)
		{
			answer += elem;
		}
		return answer - list.get(B);
	}
	
	/*
	 * returns the number of games that have gone through the specified state XY
	 */
	public static int games_XY(int[][][] winCount, int[][][] loseCount, int ndice, int X, int Y)
	{
		int sum = 0;
		for (int j = 0; j <= ndice; j++)
		{
			sum += winCount[X][Y][j] + loseCount[X][Y][j];
		}
		return sum;
	}
}
