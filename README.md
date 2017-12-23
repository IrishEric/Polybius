University Project Java application that is capable of encrypting and decrypting a
text using an ADFGVX cypher. The ADFGVX cypher was used by the German Army in
WW1 from March 1918 to encrypt field communications during the Ludendorff Offensive
(Kaiserschlacht). The cypher is so named because all messages are encrypted into codes from
the small alphabet of ADFGVX to reduce operator error when sending Morse Code signals.
Although an improvement on the ADFGX cypher used by the Germans up until 1918, the
new cypher was broken by the French cryptanalyst Georges Painvin and proved decisive in
repulsing the attack at Compiègne on June 11th 1918.
The ADFGVX cypher uses a Polybius square to encode each letter / number as two symbols
in the alphabet {ADFGVX}. This is not unlike a Vigenère / polyalphabetic cipher.

A D F G V X
A P H 0 Q G 6
D 4 M E A 1 Y
F L 2 N O F D
G X K R 3 C V
V S 5 Z W 7 B
X J 9 U T I 8

Aswell as the "Polybius square" above, the ADFGVX cypher also requires a code word to be used
to diffuse and fractionate the codes derived from the square.

Encrypting a Message
1. To encode a plaintext character using the Polybius Square, locate the character in the
matrix and read off the letter on the far left side on the same row and then the letter
at the top of the same column, i.e. each plaintext character is represented by two
cipher characters as a Matrix reading. For example, the plaintext “OBJECT” will generate the sequence
of pairs {FG, VX, XA, DF, GV, XG};
2. Create a matrix from a code word with the enciphered codes underneath. In this case
the code word JAVA will be used for both encryption and decryption. As each
plaintext character has is represented by two enciphered codes, it creates a degree of
diffusion in the cypher.

J A V A
F G V X
X A D F
G V X G

3. Perform a columnar transposition, by sorting the plaintext alphabetically. This steps
fractionates the cypher by splitting up the two enciphered codes associated with each
plaintext character.

A A J V
G X F V
A F X D
V G G X

4. The final cyphertext is formed by reading off each column:
{GAVXFGFXGVDX}

3. Decrypting a Message
The decryption of a message requires that the columnar transposition in Step 3 above is
undone. The can be performed as by reading each set of codes into the correct column
denoted by the index and then checking each pair of code along each row against the Polybius
Square.

J A V A
2 0 3 1
F G V X
X A D F
G V X G

Note that, because the encrypted message is 12 characters long and the code word JAVA is
four characters long, we can compute the number of rows required in the decoding matrix as
the message-length/code-word-length. Use the modulus operator (%) to test if there is a
remainder – if so, an additional row will be required.


This application that can parse and encrypt / decrypt the
contents of a text file (small or very large...) using the ADFGVX.

The application prompts the user to enter a code word and a file or
URL and then parse the given resource line-by-line, encrypt / decrypt the text and
append it to an output file.

Each method is commented descript it's Time and Space Complexity in typical Big-Oh notation. 


Algorithm():

Encoding:
---------

Step 1: Set a key (Global Variable) (Both people know this key) (This key cannot have two of the same letters)

Step 2: Define a Polybius Square. This is a 6x6 matrix filled with A-Z, 1-9. This is parsed in as a string, each 6 chars is a column. 
 

	"dit2fv60ylgxensq84h3woa5mczkjr1b9up7"	

    Ex:      A  D  F  G  V  X
	   +------------------
   	 A | D  6  E  H  M  1
 	 D | I  0  N  3  C  B
  	 F | T  Y  S  W  Z  9
   	 G | 2  L  Q  O  K  U
   	 V | F  G  8  A  J  P
   	 X | V  X  4  5  R  7


Step 3: Parse in our message from text file into a string.

Step 4: Change each letter of the message into it's corresponding matrix value. Make a new string from these.

Step 5: Now we need to make an Matrix Array with:

	Number of columns = length of your key. 
	Number of rows = Message length doubled (we have two chars for each char now) divided by key length, +1 because we need to add our key as the header.

Step 6: Fill the first row of this new matrix with the letters of your key 

Step 7: Fill the rest of the array row by row from the string we made in step 4.

Step 8: When the array is filled we need to re-arrange the columns so that the first row (the key) is in alphabetical order

Step 9: Now make a string from this array reading column by column. This is our encoded message



Decoding:
---------

Step 1: Register the key we are going to be using to decrypt our coded message with.

Step 2: Parse our coded message from the text file into a string.

Step 3: Make a new Matrix Array with:

	Number of columns = length of your key. 
	Number of rows = Coded message length divided by key length

Step 4: Make an ordered char array from our key, use this with our key to determine which parts of our string go into the Step 3 array in which columns

Step 5: Fill in the columns in the original order they were in before we sorted. Use the string and fill in columns

Step 6: Now read this array into a string row by row. This is equivalent to the string we get in step 4 of encoding process.

Step 7: Look at chars two at a time from the string we made in Step 6. Compare these to the Polybius square and revert the substitution process. Make a string with these chars

Step 8: The String from Step 7 is our decoded message!

This is currently using a brute force algorithm and will be updated soon with the user of hash maps
