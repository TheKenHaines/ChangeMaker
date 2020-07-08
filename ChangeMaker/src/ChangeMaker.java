import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
 
/** I know this is a bit more than you asked for, but considering I already did this problem during my coursework, I wanted to challenge
 * myself.  This program offers a simple menu, allows you to enter multiple items per transactions, and even will keep track of the total daily
 * sales and transactions.
 * @author Kenneth Haines
 *
 */
public class ChangeMaker {
	static Scanner scanner = new Scanner(System.in);
	static int totalTransactions = 0;
	static BigDecimal dailySalesTotal = new BigDecimal(0);

	public static void main(String[] args) {
		runCashierProgram();

	}
	/**my runCashierProgram is the only method called in main, it displays my simplistic menu and allows further navigation into the program.*/
	private static void runCashierProgram() {
		boolean quit = false;
		int userChoice;

		while (quit != true) {
			System.out.println("What do you wish to do? (Enter 1, 2, or 3): ");
			System.out.println("1. New Transaction");
			System.out.println("2. Check Sales For The Day");
			System.out.println("3. Quit");

			userChoice = scanner.nextInt();
			scanner.nextLine();

			if (userChoice == 1) {
				newTransaction();
			} else if (userChoice == 2) {
				printSales();
			} else if (userChoice == 3) {
				quit = true;
				System.out.println("Ending Program: Have a nice day");
				continue;
			}

		}
	}
	/** The newTransaction allows the user to "ring" up multiple items. It uses the BigDecimal class to accurately
	 * maintains totals. Ideally, this would probably be better off as its own class. However, I didn't want to be too overzealous
	 * it calls two methods to keep code cleaner. It also updates my totalDailysales and totalTransactions.
	 */
	private static void newTransaction() {
		BigDecimal changeToGive = new BigDecimal(0);

		BigDecimal totalTransactionCost = getCostOfItems();
		System.out.println("Your total cost today is " + totalTransactionCost);

		BigDecimal moneyGiven = getAmountGiven();
		changeToGive = changeToGive.add(moneyGiven);
		changeToGive = changeToGive.subtract(totalTransactionCost);

		System.out.println("Change to Give is $" + changeToGive);
		System.out.println();
		System.out.println();
		calculateChange(changeToGive);

		totalTransactions++;
		dailySalesTotal = dailySalesTotal.add(totalTransactionCost);

	}
	//*The printSales just displays my static variables dailySalesTotal and totalTransactions*/ 
	private static void printSales() {
		System.out.printf("There were %d transactions today\n", totalTransactions);
		System.out.printf("Total sales were $%.2f for the day\n", dailySalesTotal);
		System.out.println();

	}
	//* getAmountGiven runs logic to collect how much the customer pays with and returns that number. It also verifies proper input.*/
	private static BigDecimal getAmountGiven() {
		BigDecimal amountToReturn = new BigDecimal(0);
		System.out.println("How much are you paying? ");
		String amountGiven = scanner.nextLine();

		if (isValidInput(amountGiven)) {
			BigDecimal exactAmountGiven = new BigDecimal(amountGiven);
			amountToReturn = amountToReturn.add(exactAmountGiven);
		} else {
			System.out.println("Improper input, try again");
			System.out.println();
			getAmountGiven();
		}
		return amountToReturn;
	}
	
	//*getCostOfItem runs logic to collect the total cost of numerous items and return that total. It verifies input before executing*/
	private static BigDecimal getCostOfItems() {
		BigDecimal exactTotal = new BigDecimal(0.00);
		boolean done = false;

		while (done != true) {
			System.out.println("Enter cost of the item: (Enter done to finish): ");
			String costOfItem = scanner.nextLine();

			if (costOfItem.equalsIgnoreCase("done")) {
				done = true;
				continue;
			} else if (isValidInput(costOfItem)) {
				BigDecimal cost = new BigDecimal(costOfItem);
				exactTotal = exactTotal.add(cost);
			} else {
				System.out.println("Improper input, try again");
				System.out.println();
				continue;
			}
		}
		return exactTotal;
	}
	
	/**isValidInput uses a try catch and a parseDouble combo to verify user input. I should improve this code by catching a more narrow 
	 * catch exception.
	 */
	private static boolean isValidInput(String userInput) {
		try {
			double isValid = Double.parseDouble(userInput);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	/** calculateChange calls a method to change my BigDecimal into a whole number, then it breaks down exaclty how many quarters, dimes, etc. 
	 * I will need. Lastly, it passes that information onto a new method to do the printing work*/
	public static void calculateChange(BigDecimal changeToGive) {
		int dollarCount = 0;
		int quarterCount = 0;
		int dimeCount = 0;
		int nickelCount = 0;
		int pennyCount = 0;
		int changePennies = getIntFromBigDecmial(changeToGive);

		while (changePennies > 0) {
			if (changePennies >= 100) {
				dollarCount++;
				changePennies -= 100;
			} else if (changePennies >= 25) {
				quarterCount++;
				changePennies -= 25;
			} else if (changePennies >= 10) {
				dimeCount++;
				changePennies -= 10;
			} else if (changePennies >= 5) {
				nickelCount++;
				changePennies -= 5;
			} else {
				pennyCount++;
				changePennies -= 1;
			}
		}
		outputChange(dollarCount, quarterCount, dimeCount, nickelCount, pennyCount);
		
	}
	//*outputChange simply does formatted output of my change counts*/
	private static void outputChange(int dollarCount, int quarterCount, int dimeCount, int nickelCount,
			int pennyCount) {
		System.out.println("You should receive: ");
		
		if (dollarCount > 0) {
			System.out.printf("%d dollar(s)\n", dollarCount);
			} 
		if (quarterCount > 0) {
			System.out.printf("%d quarter(s)\n", quarterCount);
			}
		if (dimeCount > 0) {
			System.out.printf("%d dime(s)\n", dimeCount);
			}
		if (nickelCount > 0) {
			System.out.printf("%d nickel(s)\n", nickelCount);
			}
		if (pennyCount > 0) {
			System.out.printf("%d pennie(s)\n", pennyCount);
			}
		
		System.out.println();
		
		
	}
	/** getIntFromBigDecimal just returns an int of my BigDecimal * 100 */
	private static int getIntFromBigDecmial(BigDecimal changeToGive) {
		BigDecimal oneHundred = new BigDecimal(100);
		changeToGive = changeToGive.multiply(oneHundred);
		int changeInPennies = changeToGive.intValue();
		return changeInPennies;
	}
	
	

}
