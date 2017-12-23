import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
 
/*
/----------- THEORETICAL ANALYSIS--------------------/
 * Each method is commented with it's time complexity in 'Big Oh' notation;
 * Some methods call other methods for clearer layout which are more complex resulting in some methods time complexity being the sum of others etc,as you'll see
 * 'n' here is the input size on the method which varies with each method
 * All analysis is "worst case".
 */
 
public class ADFGVX {
       
        private static boolean pSquareSet= false; //class member boolean to stop pSquare have to be set each run
        private static boolean keySet = false; // same for key
        private static String key;
        private static char[][] pSquare = new char[6][6]; // the 6 x 6 Polybius matrix
        private static char[] letters = {'A', 'D', 'F', 'G', 'V', 'X'};
 
        public static void main(String[] args) throws Exception{
               
                if (keySet==false){
                key = setKey();
                }
                
               
                if (pSquareSet==false){
                pSquare = setPSquare();
                }
                //String url = JOptionPane.showInputDialog("Are you on Mac OSX or Windows?");
                String[] options = new String[] {"Windows", "Mac", "Linux", "Cancel"};
                int response = JOptionPane.showOptionDialog(null, "Message", "Title",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

                // Where response == 0 for Yes, 1 for No, 2 for Maybe and -1 or 3 for Escape/Cancel.
               // PATH below is different for windows 
               // Try : "C:\\Users\\Owner\\Desktop\\Example.txt" etc..
                encode("/Users/EricMcEvoy/desktop/practius.txt", key, "/Users/EricMcEvoy/desktop/todecrypt.txt");    
                decode("/Users/EricMcEvoy/desktop/todecrypt.txt", key, "/Users/EricMcEvoy/desktop/output.txt");    
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//
       
        // Complexity of order : O()
        // This method makes many calls to other methods
        // Hence the complexity of this method is the highest order of the sum of the methods called + assingments and basic operators which are negligible
        public static void encode(String file, String key, String output) throws Exception{
               
                String message = fileToString(file); //** Need to add the try/catch handing in this method  //O(n)
                System.out.println(message); 
               
                char[][] filled = fillArray(message, key, "e"); // O(n)
                char[][] ordered = switchColumns(filled, key); // O()
               
                String code = arrayToString(ordered, key, "e"); // O()
               
                stringToFile(code, output); // O()
        }      
        // Since this method contains many calls to methods, the complexity is the highest order of the sum of the method's complexities
        // This yields us with : O()
        public static void decode(String file, String key, String output) throws Exception{
               
                String s = "s";
                String code = fileToString(file);
                
                char original[][] = fillArray(code, key, "d");
                
                s = arrayToString(original, key, "d");
                s = revertSubstitution(s);
                
                stringToFile(s, output);
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//    
        
        // This method yields complexity O(n) ; linear complexity
        // here n is the length of the string as we are comparing its characters  
        // The worst case here is that for every iteration of the loop we make a match at the very end (last row, last column) of the pSquare
        // if we had an nxn pSquare we would be looking at O(n^2) but since it's always 6x6 
        // This results in max : n x 36 checks ; 0 --> n (for loop) and 0-->36 (pSquare) along with assignments to string x results in O(36n+n) = O(n)
        // All other minor assignments < n are constant time and so are ignored by the nature of this Analysis.
        
        public static String revertSubstitution(String s){
               
                String x = ""; //** This will be our decoded message string
               
                for (int i=0; i<s.length(); i+=2){ //** This will run s.length/2 times, because we iterate at the end as well
                       
                        int j = 0; // O(1)
                        int k=0; // O(1)
                        boolean found = false;
                       //System.out.println("s char outside " + s.charAt(i));
                        do{
                                if (s.charAt(i)==letters[j]){ //** Left hand side of pSquare
                                       System.out.println("i : " + i);
                                       System.out.println("j : " + j);
                                       //------------//
                                        do{
                                        	if (s.charAt(i+1)==letters[k]){ //** Top side of pSquare
                                        		System.out.println("k : "+k);
                                               
                                                x = x + pSquare[k][j]; //** Add our letter from pSquare to the decoded message string
                                                found = true; //breaks inner loop
                                                break;
                                        	}
                                        	
                                        	else{
                                               k++; // O(1)
                                        	}
                                        	
                                        }while(found=false);
                                        
                                }
                                else{
                                        j++; // O(1)
                                }
                        } while (found == false);      
                }
                return x; //** Return the decoded message string
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//    
        
        // This method yields complexity O(n), linear time
        // n in this case would be the length of the filled array 
        // Immediately worst case, we are looking at (O(nlog(n)) for the sorting the of the array  (Dual-Pivot Quicksort : Java Library ) : Linear
        // However, here the key is being sorted which is so small that we can consider it constant
        // Further on we we loop through to the end of the longest row ( key length ) and make a match
        // For each match we then fill the correct column which is O(colLen) : linear, totaling to O(n) for each column
        // the highest order here is O(n) , all lower orders ignored
        
        
        public static char[][] switchColumns(char[][] filled, String key){
               
                        char[] orderedKey = key.toCharArray(); //** Make a char array from our key string
                        Arrays.sort(orderedKey); //** This sorts the char array we just made
               
                        int colLen = filled[0].length; //** The 0th value will never be shorter than any other column
               
                        char[][] sorted = new char[key.length()][colLen-1]; //** We Will fill this array column by column
                       
                        for (int i=0; i<key.length(); i++){ //** Runs for every column
                               
                                boolean found = false;
                                int j=0;
                       
                                do {
                                        for (j=0; j<key.length(); j++)
                                       
                                                if (filled[i][0]==orderedKey[j]){ //** Checks the key header value against our ordered array of the key string
                                                       
                                                        found = true;
                                                       
                                                        for (int x=1; x<colLen; x++){ //** Fills new array column by column. x=1 because we don't want to copy key over
                                                               
                                                                sorted[j][x-1] = filled[i][x]; //O(colLen) where colLen is length of column being filled :constant
                                                        }
                                                }
                                } while (found == false);
                        }
                        return sorted;
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//
        
        // This method yields combined complexity O(n), Linear time
        // Here n is the message length. Key length is so small compared to large n, operations on it are negligible
        // Method "e" : 1. Filling the first row of the array with the key is O(key.length) constant time.
        // 				2. Worst case here is that each iteration of the loop through the message finds a match near/at the end of the pSquare
        // 				3. Thankfully since the pSquare s 6x6 and not nxn we have an O(n) linear complexity whereby for an the "O(1)"s are carried out n times.
        // Method "d" : 1. Sorting the array yields O(klogk),k=key.length, constant time.
        //				2. Loop gamma :  all yield O(keylen), not dependent on n ; constant time
        //				3. loop beta: the worst case leaves us with keylen*keylen + assignment operations of the columns which are dependent on n 
        //				   therefore the complexity is O(n) ; linear.
        
        public static char[][] fillArray(String message, String key, String method){
               
                if (method.equals("e")){ //** We are Encoding
               
                        int w=0; //** Column Index // O(1)
                        int z=1; //** Row Index // O(1)
                        
                        char[][] filled = new char[key.length()][((int) Math.ceil(((double)message.length()*2)/(double)key.length()))+(1)]; //** +1 because we need an extra row for key header // Constant time
                        
                        System.out.println(filled[0].length);
                        //** Add the Keyword as header for each column.
                        for (int i=0; i<key.length();i++){  //O(key.length) constant
                                filled[i][0] = (key.charAt(i)); // fills the first row with the key
                        }
                               
                        for (int j=0; j<message.length(); j++){ //** Runs for each character in our message
                               
                                int k=-1; //** Initialized as -1 because on first run it will be incremented to 0.
                                int p=0;
                                boolean found = false;
                               
                                do {
                                        if (p % 6 == 0) { // O(1)
                                                k++; //O(1) 
                                        }
                                        
                                        if (message.charAt(j) == pSquare[k][p%6]) { // Line ALPHA, see analysis
                                        	System.out.println("p : " +p); 
                                        	System.out.println("k : " +k); 
                                                found=true;
                                               
                                                if ((key.length()-w)==2){
                                                        //("*Two Spaces*");
                                                        filled[w][z]=letters[p%6]; //O(1)
                                                        w++; //O(1)
                                                        filled[w][z]=letters[k]; //O(1)
                                                        w=0; //O(1)
                                                        z++; //O(1) // next row
                                                }
                                                else if ((key.length()-w)>2){
                                                	System.out.println("top");
                                                        //("*Three or More Spaces*");
                                                        filled[w][z]=letters[p%6]; //O(1)
                                                        w++; //O(1)
                                                        filled[w][z]=letters[k]; //O(1)
                                                        w++; //O(1)
                                                        System.out.println("bottom");
                                                }
                                                else if ((key.length()-w)==1){
                                                        //("*One Space*");
                                                        filled[w][z]=letters[p%6]; //O(1)
                                                        w=0; //O(1)
                                                        z++; //O(1)  
                                                        filled[w][z]=letters[k]; //O(1)
                                                        w++; //O(1)
                                                }                                      
                                        }
                                        else{
                                                p++; //O(1)
                                        }
                                } while (found == false);
                        }
                return filled;
                }
               
        if (method.equals("d")){ //** We are decoding
               
                char[] orderedKey = key.toCharArray(); // 
                Arrays.sort(orderedKey); // O(mlog(m)) , Linear ( Java library Sort ) // which is actually negligible as m = key.length
               
                char[][] array = new char[key.length()][((int) Math.ceil((double)(message.length())/(double)key.length()))+(0)]; //** +1 because we need an extra row for key header
               
                //** Add the Keyword as header for each column.
               // for (int i=0; i<key.length();i++){
                 //       array[i][0] = (key.charAt(i)); 
                //}
               
                //** Get the number of full columns
                int numFullCols = (key.length())-((message.length())%(key.length())); // O(c), depending where we start counting operations 
                int[] numItems = new int[key.length()]; //** This will hold the number of chars in each column //O(c) constant time
                 
                // ----- LOOP GAMMA -------------//
                for (int i=0; i<key.length(); i++){ //O(key.length) , constant by key definition being 2 < key.length < 25
                       
                        if (i<numFullCols){ //** Ex: If key is 3 chars, and numFullCols = 3, then 0,1,2 will be full so all are full.
                               
                                numItems[i]=array[0].length; // constant time
                        }
                        else{
                                numItems[i]=array[0].length-1; //** This column is not full, there will be a space.// constant time
                        }
                }
             // ----- END LOOP GAMMA -------------//
               
                int messageIndex=0;   // O(1)
                System.out.println(message.length());
               // System.out.println(numItems[0]);
               // System.out.println(numItems[1]);
                //System.out.println(numItems[2]);
                
                
                // ---- LOOP BETA -------- // 
                for (int k=0; k<key.length(); k++){ 
                       
                        boolean found = false; // O(1)
                        int j=0; // O(1)
               
                        do {
                                for (j=0; j<key.length(); j++){
                               
                                        if (orderedKey[k]==key.charAt(j)){ // O(1)
                                               
                                                found = true; // O(1)
                                                
                                                
                                               
                                                for (int x=0; x<numItems[j]; x++){ //** Fills new array column by column //O(columnLength) :  constant for assingments to array
                                                      
                                                        array[j][x] = message.charAt(messageIndex);
                                                       
                                                        messageIndex++; 
                                                }
                                        }
                                }
                        } while (found == false);              
                } // ---- END LOOP BETA -------- // 
              
                return array;
        }
        return new char[0][0]; //** Return an error here;
               
        }
       
//-------------------------------------------------- Utility Methods ---------------------------------------------------------------//
        
        // This method yields, as we can see clearly yields : O(key.length*key.length) , key.length is a value less then 25 which we can technically say is linear but runs always in constant time.
        // If we had a key of unlimited length we would looking at O(n^2) time for our same letter checking algorithm below
        public static String setKey(){
               
                //** Prompt user to input a string to reverse
                String input = JOptionPane.showInputDialog("You Haven't set a Key yet. Please Enter one Below:");
               
                //** Checks for invalid input
                while (input.length()<2 || input.length()>24){  
                                       
                        input = JOptionPane.showInputDialog("Please Enter a Valid Key (2-24 Characters) ");     //** Prompts user to enter a valid string
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
                                                setKey(); // recursive call to try again
                                        }
                                }
                        }
                }
               
                JOptionPane.showMessageDialog(null, "Your key has been set: [" + input +"]"); //** Prints Key entered by user
               
                keySet=true; // no need to set key again
                return input;
        }      
       
//---------------------------------------------------------------------------------------------------------------------------------------//
        
        // Before we begin analysis here it is important to remember the pSquare is a 6x6 grid, not nxn
        // The various assignments and method calls are either O(1) or O(input.length) which are all constant
        // the filling of the pSquare is also constant time as we go 0-36 call.
        // Yield : O(constant) : 36 + (assignments & basic constant time function calls) , constant time complexity
        public static char[][] setPSquare(){
               
                JOptionPane.showMessageDialog(null, "You haven't defined your Grid yet. Please do so now.." ); 
               
                String input = JOptionPane.showInputDialog("Enter each Grid value as Columns, Top to Bottom, Left to Right:"); // O(1)
               
                input = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase(); //** Removes anything that's not a number/letter, converts to uppercase // // O(input.length + 2), constant in this case
               
                int j=-1; //** Start at -1 because on first run j will iterate
               
                for (int i=0; i<36; i++){ //** Loop to fill our pSquare from the string entered. Must be 36 chars.
                        if (i % 6 == 0)
                                j++;
                        pSquare[j][i%6]= input.charAt(i);
                }
                pSquareSet=true;
                return pSquare;
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//
        
        // Complexity : O(n) , linear time.
        // Here we would consider n to be the array.length
        // Method "e" : Here we loop through 0 --> key.length and create a column string filling it with the contents of that column.
        // 				For exceptionally large value of n we would still expect low time by the standards of modern processors nonetheless 
        // 				Although is it almost constant, it does depend on n therefore it is O(n) linear : fills n spaces
        // Method "d" : Here we loop through each column like above and add to a string each value as we go along.
        // 			  : Like before, although small, this is a linear function as we need to do this n times (depends on n)
        //			  : O(n) , linear time.
        public static String arrayToString(char[][]array, String key, String method){
               
                String string=""; // O(1)
               
                if (method.equals("e")){ //** We are encoding
                       
                        for (int i=0; i<key.length(); i++){ //** Runs for every column. This String will read the columns top to bottom, left to right.
                               
                                String column = new String(array[i]); //**Creates a string from each column array
                               
                                string = (string + column); //** Adds to our return string
                        }
                }
                else if (method.equals("d")){ //** We are decoding
                       
                        for (int i=0; i<array[0].length; i++){ //** Run once for each row
                               
                                for (int j=0; j<key.length(); j++){ //** Run once for each column
                                       
                                        string += array[j][i];
                                }
                               
                        }
                        string.replaceAll("\\s+",""); //** Remove all whitespace if we have empty space in a row.
                }
                return string; //** Return the string we made from array values
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//
        
        public static String fileToString(String location) throws Exception{
               
                FileReader file = new FileReader(location);
                BufferedReader reader = new BufferedReader(file);
               
                String s = "";
                String line = reader.readLine();
               
                while (line != null){ //** Keep reading until we have parsed all text from file
                       
                s += line;
                line = reader.readLine();              
                }
               
                reader.close(); //** Close the BufferedReader
               
                //** Remove all whitespace and punctuation, convert to upper case
                s = s.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
               
                return s;
        }
       
//---------------------------------------------------------------------------------------------------------------------------------------//
        
        public static void stringToFile(String text, String location) throws Exception{
               
                File newFile = new File(location);
               
                if (newFile.exists())
                        System.out.println("File already exists");
               
                else{
                       
                        try{
                                newFile.createNewFile();
                        }
                        catch(Exception e){
                                e.printStackTrace();
                        }
                        try{
                                FileWriter fileW = new FileWriter(newFile);
                                BufferedWriter buffW = new BufferedWriter(fileW);
                               
                                buffW.write(text);
                                buffW.close(); //** Close Buffered Reader
                        }
                        catch (Exception e){
                                e.printStackTrace();
                        }
                 }                                     
        }  
}
       
//---------------------------------------------------------------------------------------------------------------------------------------//    
       
