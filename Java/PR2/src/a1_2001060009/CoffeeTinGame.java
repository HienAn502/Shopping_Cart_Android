package a1_2001040002;

import java.util.Arrays;

import utils.TextIO;

/**
 * @overview
 *     A program that performs the coffee tin game on a tin of beans and display result on the standard output.
 * @author LENOVO
 */
public class CoffeeTinGame {
	/** constant value for the green bean*/
	private static final char GREEN = 'G';
	/** constant value for the blue bean*/
	private static final char BLUE = 'B';
	/** constant for removed beans*/
	private static final char REMOVED = '-';
	/** the null character*/
	private static final char NULL = '\u0000';
	/** length(BeansBag) >= 30 */
	public static char[] BeansBag = new char[100];
	static {
		// for testing purposes
		for (int i = 0; i < 34; i++) 
			BeansBag[i] = BLUE;
		for (int i = 34; i < 67; i++)
			BeansBag[i] = GREEN;
		for (int i = 67; i < BeansBag.length; i++) 
			BeansBag[i] = REMOVED;
	}
	
	/**
	 * the main procedure
	 * @effects
	 *     initialise a coffee tin
	 *     {@link TextIO#putf(String, Object...)}: print the tin content
	 *     {@link @tinGame(char[])}: perform the coffee tin game on tin
	 *     {@link TextIO#putf(String, Object...)}: print the tin content again
	 *     if last bean is correct
	 *         {@link TextIO#putf(String, Object...)}: print its colour
	 *     else
	 *         {@link TextIO#putf(String, Object...)}: print an error message
	 *         
	 * @param args
	 */
	public static void main(String[] args) {
		
		// initialise some beans
		char[] tin = { BLUE, BLUE, BLUE, GREEN, GREEN, GREEN};
		// count number of greens
		int greens = 0;
		for (char bean : tin) {
			if (bean == GREEN)
				greens++;
		}
		
		final char last = (greens % 2 == 1) ? GREEN : BLUE;
		// p0 = green partity /\
		// (p0=1 -> last = GREEN) /\ (p0=0 -> last = BLUE)
		
		// print the content of tin before the game
		System.out.printf("tin before: %s %n", Arrays.toString(tin));
		
		// perform the game
		char lastBean = tinGame(tin);
		
		// print the content of tin and last bean
		System.out.printf("tin after: %s %n", Arrays.toString(tin));
		// lastBean = last \/ lastBean != last
		// check if last bean as expected and print
		if (lastBean == last) {
			System.out.printf("last bean: %c ", lastBean);
		} else {
			System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
		}
	}
	
	/**
	 * Performs the coffee tin game to determine the colour of the last bean
	 * 
	 * @requires tin is not null /\ tin.length > 0
	 * @modifies tin
	 * @effects </pre>
	 *   take out two beans from tin
	 *   if same colour
	 *     throw both away, put one blue bean back
	 *   else
	 *     put green bean back
	 *   let p0 = initial number of green beans
	 *   if p0 = 1
	 *     result = 'G'
	 *   else
	 *     result = 'B'
	 *   </pre>
	 */
	public static char tinGame (char[] tin) {
		while (hasAtLeastTwoBeans(tin)) {
			// take two beans from tin
			char[] takeTwo = takeTwo(tin);
			char b1 = takeTwo[0];
			char b2 = takeTwo[1];
			// process beans to update tin
			updateTin(tin, b1, b2);
		}
		return anyBean(tin);
	}
	
	/**
	 * @effects
	 * if tin has at least two beans
	 *   return true
	 * else
	 *   return false
	 */
	private static boolean hasAtLeastTwoBeans(char[] tin) {
		int count = 0;
		for (char bean : tin) {
			if (bean != REMOVED) {
				count++;
			}
			if (count >= 2) // enough beans
				return true;
		}
		// not enough beans
		return false;
	}
	
	/**
	 * @requires tin has at least 2 beans left
	 * @modifies tin
	 * @effects
	 *     remove any two beans from tin and return them
	 */
	private static char[] takeTwo(char[] tin) {
		char first = takeOne(tin);
		char second = takeOne(tin);
		return new char[] {first, second};
	}
	
	/**
	 * @effects
	 *     if tin is empty
	 *         return true
	 *     else
	 *         return false
	 * @param tin
	 * @return
	 */
	private static boolean isEmpty(char[] tin) {
		for (char bean : tin) {
		    if (bean != REMOVED) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @requires tin has at least one bean
	 * @modifies tin
	 * @effects
	 *   remove any bean from tin and return it
	 */
	public static char takeOne(char[] tin) {	
		if (isEmpty(tin)) return NULL;
		char bean;
		int pos;
		do {
			pos = randInt(tin.length);
			bean = tin[pos];
		} while (bean == REMOVED);
		tin[pos] = REMOVED;
		return bean;
	}
	
	/**
	 * @requires tin has vacant positions for new beans
	 * @modifies tin
	 * @effects
	 *   place bean into any vacant position in tin
	 */
	private static void putIn(char[] tin, char bean) {
		for (int i = 0; i < tin.length; i++) {
			if (tin[i] == REMOVED) { // vacant position
				tin[i] = bean;
				break;
			}
		}
	}
	
	/**
	 * @effects
	 *   if there are beans in tin 
	 *     return any such bean
	 *   else
	 *     return '\u0000' (null character)
	 */
	private static char anyBean(char[] tin) {
		for (char bean: tin)  {
			if (bean != REMOVED) {
				return bean;
			}
		}
		// no beans left
		return NULL;
	}
	
	/**
	 * @effects
	 *   return a random number in range [0, n)
	 */
	private static int randInt(int n) {
		return (int) (Math.random() * n);
	}
	
	/**
	 * @requires
	 *   bag is not null /\ bag.length > 0 /\ 
	 *   (type = BLUE \/ type = GREEN)
	 * @modifies bag
	 * @effects
	 *   remove any bean that match type from bag and return it
	 */
	private static char getBean(char[] bag, char type) {
		
		char bean = takeOne(bag);
		if (bean == NULL) return NULL;
		while(bean != type) {
			putIn(bag, bean);
			bean = takeOne(bag);
		}
		return bean;
		
		
		/*
		// beans is empty
		if (isEmpty(bag)) return NULL;
		// beans is not empty
		else { // randomly-selected bean
			int pos = randInt(bag.length);
		    char bean = bag[pos];
		    while (bean != type) {
				pos = randInt(bag.length);
			    bean = bag[pos];
		    }
		    bag[pos] = REMOVED;
		    return bean;
		} 
		*/
		
	}
	
	/**
	 * @modifies tin
	 * @effects
	 *   if b1 = b2 
	 *     put a blue bean from BeansBag into tin
	 *   else
	 *     if b1 = GREEN 
	 *       put b1 into tin
	 *     else put b2 into tin
	 */
	private static void updateTin(char[] tin, char b1, char b2) {
		if (b1 == b2) {
			char bean = getBean(BeansBag, BLUE);
			if (bean != NULL) 
				putIn(tin, bean);
			else 
				System.err.println("Error: Beansbag is empty!");
		} else {
			if (b1 == GREEN) putIn(tin, b1);
			else putIn(tin, b2);
		}
	}
}
