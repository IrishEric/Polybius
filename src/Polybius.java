 import javax.swing.JOptionPane;

import java.util.*;

/*
	6x6 Matrix. Known by sender and receiver.
 
		A  D  F  G  V  X
	  +------------------
    A | D  6  E  H  M  1
    D | I  0  N  3  C  B
    F | T  Y  S  W  Z  9
    G | 2  L  Q  O  K  U
    V | F  G  8  A  J  P
    X | V  X  4  5  R  7
   dit2fv60ylgxensq84h3woa5mczkjr1b9up7
*/

//Step 1: Change each letter of text to it's corresponding matrix value. Ex: 'G' = VD
//Step 2: We need a code word, known by sender and receiver. Ex: "GARETH" as our codeword.
//Step 3: Write our code word, then underneath, fill in our codes from Step 1. 
//Step 4: Rearrange our columns, so that our codeword is sorted alphabetically.
//Step 5: Our final message is got by reading the letters of each column from top to bottom, left to right.

/* 
 Example:
 
 Codeword: "GARETH"
 Text to encode: "My Name Is P"
 
 M   Y   N   A   M   E   I   S   P
 AV  FD  DF  VG  AV  AF  DA  FF  VX
 
 G A R E T H
 ------------+ 
 A V F D D F | M Y N
 V G A V A F | A M E
 D A F F V X | I S P
 
 A E G H R T
 ------------+
 V D A F F D | 
 G V V F A A |
 A F D X F V |
 
 
 Code = "V G A D V F A V D F F X F A F D A V"
 
 */

public class Polybius {
	
	private static boolean pSquareSet= false;
	private static boolean keySet = false;
	private static String key;
	private static char[][] pSquare = new char[6][6];
	private static char[] letters = {'0','A', 'D', 'F', 'G', 'V', 'X'}; 
	
	// creates the polybius square from input given from the user
	public static char[][] setPSquare(){
		
		JOptionPane.showMessageDialog(null, "You haven't defined your Grid yet. Please do so now.." ); 
		String input = JOptionPane.showInputDialog("Enter each Grid value as Columns, Top to Bottom, Left to Right:");
		input = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase(); //replaces all non alphanumerics with "" and puts to uppercase
		int j=-1;
		
		for (int i=0; i<36; i++){
			if (i % 6 == 0)
				j++;
			pSquare[j][i%6]= input.charAt(i);
		}
		pSquareSet=true;
		return pSquare;
	}
	
	public static void main(String[] args){
		
	
		//if (keySet==false){
		//key = setKey();
		//}
		key = setKey(); // variable = call on the set key method
		setPSquare(); // call on the setPsquare method 
		
		//for (int a=0; a< 6; a++){
			//for (int b=0; b<6; b++){
			//	System.out.print(pSquare[a][b]+ " ");
		//	}
		//}
		String message = "PARADOXAL";
		String code = encode(message, key);
	
	}
	
	// encode method calls 2 methods fillArray to create the array with the key
	// then the switch columns which sorts the key alphabetically bringing the columns with it
	public static String encode(String message, String key){
		
		char[][] filled = fillArray(message, key);
		char[][] ordered = switchColumns(filled, key, "e" );
		
		String code = arrayToCode(ordered, key);
		
		return code;
	}	
	
	public static String arrayToCode(char[][]array, String key){
		
		String code="";
		
		for (int i=0; i<key.length(); i++){ //** Runs for every column
			
			String column = new String(array[0]);
			
			code = (code + column);
		}
		
		return code;
	}
	
	//public static String decode(String code, String key){
		
		//return message;
	//}
	
	public static char[][] switchColumns(char[][] filled, String key, String method){
		
		if (method.equals("e")){ //**We are encoding. Sort Columns alphabetically
			
			char[] orderedKey = key.toCharArray();
			Arrays.sort(orderedKey);
		
			int colLen = filled[0].length;
		
			char[][] sorted = new char[key.length()][colLen]; //** We Will fill this array column by column
			
			for (int i=0; i<key.length(); i++){ //** Runs for every column
				
				boolean found = false;
				int j=0;
			
				if (found == false){
					System.out.println("x");
					if (filled[i][0]==orderedKey[j]){ 
						
						found = true;
						
						
						for (int x=0; x<colLen; x++){ //** Fills new array column by column
							
							sorted[j][x] = filled[i][x]; // fills columns from unordered key 
						}
					}
					else{
						j++;
					}
				}
			}
			return sorted;
		}
		else{
			return new char[3][4];
		}
	}
	
	public static char[][] fillArray(String message, String key){
		
		int w=0; //** Column Index
		int z=1; //** Row Index
		
		char[][] filled = new char[key.length()][((int) Math.ceil((message.length()*2)/key.length()))+(1)]; //** +1 because we need an extra row for key header

		//** Add the Keyword as header for each column.
		for (int i=0; i<key.length();i++){
			filled[i][0] = (key.charAt(i));
		}
			
		for (int j=0; j<message.length(); j++){ //** Runs for each character in our message
			
			int k=-1; //** Initialized as -1 because on first run it will be incremented to 0.
			int p=0;
			boolean found = false;
			
			do {
				
				if (p % 6 == 0) {
					k++;
				}
	
				if (message.charAt(j) == pSquare[k][p%6]) {
					
					found=true;
					
					if ((key.length()-w)==2){
						//("*Two Spaces*");
						filled[w][z]=letters[p%6];
						w++;
						filled[w][z]=letters[k];
						w=0;
						z++;
					}
					else if ((key.length()-w)>2){
						//("*Three or More Spaces*");
						filled[w][z]=letters[p%6];
						w++;
						filled[w][z]=letters[k];
						w++;
					}
					else if ((key.length()-w)==1){
						//("*One Space*");
						filled[w][z]=letters[p%6];
						w=0;
						z++;	
						filled[w][z]=letters[k];
						w++;
					}					
				}
				else{
					p++; 
				}
			} while (found == false);
		}
		
		/*
		System.out.println(filled[0][0]);
		System.out.println(filled[1][0]);
		System.out.println(filled[2][0]);
		System.out.println(filled[0][1]);
		System.out.println(filled[1][1]);
		System.out.println(filled[2][1]);
		System.out.println(filled[0][2]);
		System.out.println(filled[1][2]);
		System.out.println(filled[2][2]);
		System.out.println(filled[0][3]);
		System.out.println(filled[1][3]);
		System.out.println(filled[2][3]);
		System.out.println(filled[0][4]);
		System.out.println(filled[1][4]);
		System.out.println(filled[2][4]);
		System.out.println(filled[0][5]);
		System.out.println(filled[1][5]);
		System.out.println(filled[2][5]);
		System.out.println(filled[0][6]);
		System.out.println(filled[1][6]);
		System.out.println(filled[2][6]);
		*/
		
		return filled;
		
	}
	public static String setKey(){
		
		//** Prompt user to input a string to reverse
		String input = JOptionPane.showInputDialog("You Haven't set a Key yet. Please Enter one Below:");
		
		//** Checks for invalid input
		while (input.length()<2 || input.length()>24){  
					
			input = JOptionPane.showInputDialog("Please Enter a Valid Key (2-24 Characters) ");	//** prompts user to enter a valid string
		}

		//** Remove all whitespace and punctuation, convert to upper case 
		input = input.replaceAll("[^a-zA-Z]", "").toUpperCase();
		
		//**Our Key cannot have two of the same letters. Check for this:
		char[] check = input.toCharArray();
		for(int i=0; i<check.length-1; i++) {
			for(int j=i+1; j<check.length; j++) {
				if(check[i] == check[j]) {
					
				JOptionPane.showMessageDialog(null, "Your key has two of the same characters.. this is not allowed." );	
				String choice = JOptionPane.showInputDialog("Would you like to try again? Y/N :").toUpperCase();
					if (choice.equals("Y")){
						setKey();
					}
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "Your key has been set: [" + input +"]");
		keySet=true;
		return input;
	}	
}

