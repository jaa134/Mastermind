import java.util.Scanner;
  
/*
 * A class that will represent a popular boardgame called mastermind.
 * @author Jacob Alspaw
 */
public class Mastermind {
  
  /* The game's secret code represented as a String for eacy comparison and large compatibility. */
  private String secretCode;
  /* The length of the secret code as defined in the rules is four integers. */
  private int codeLength = 4;
  /* A number of chances the user has to guess the correct secret code before the game ends and the user loses. */
  private int numChances;
  /* A scanner object that allows input to be given by the user as a String. */
  Scanner userInput = new Scanner(System.in);
  
  
  /*
   * A constructor method that will determine game settings and then play the game with the user.
   */
  public Mastermind() {
    //print the game's instructions
    printInstructions();
    //determine the secret code
    setSecretCode();
    //determine the allowed attempts for this game
    setNumChances();
    //start asking for guesses
    playGame();
  }
  
  
  /*
   * A method to return the length of the secret code.
   * @return codeLength The number of integers getin the secret code.
   */
  private int getCodeLength() {
    return codeLength;
  }
  
  
  /*
   * A method to return an object that can be used to obtain user input.
   * @return userInput A scanner reading input into the system.
   */
  private Scanner getUserInput() {
    return userInput;
  }
  
  
  /*
   * A method to return the secret code.
   * @return secretCode The secret code as a String.
   */
  private String getSecretCode() {
    return secretCode;
  }
  
  
  /*
   * A method that will randomly find four integers between the values of 1-6. According to the rules, there is allowed
   * to be repeats of integers.
   */
  private void setSecretCode() {
    //an object to build the code onto
    StringBuilder secretCode = new StringBuilder();
    //find a fairly random value for each of the four integers that ranges from 1-6 to add to the secret code
    for(int i = 0; i < getCodeLength(); i++) {
      //multiplying random int by 6 and adding 49 will be represented as an ASCII characte value for numbers 1-6 
      secretCode.append((char)((Math.random() * 6) + 49));
    }
    this.secretCode = secretCode.toString();
  }
  
  
  /*
   * A method to return the number of chances that was configured by the game's player.
   * @return numChances The number of allowed guesses before the game's end.
   */
  private int getNumChances() {
    return numChances;
  }  
  
  
  /*
   * A method to set a number of allowed guesse's to an arbitrary number. This method is used to quit the game.
   * @param numChances The number of allowed attempts before the game should quit.
   */
  private void setNumChances(int numChances) {
    this.numChances = numChances;
  }
  
  
  /*
   * A method that will repeatedly ask the user for input to a number of allowed guesses until a vaild number is given.
   */
  private void setNumChances() {
    //represents the number of chances a user may enter. Type string because flexibility with user-input mistakes
    String chances;
    do{
      System.out.println("Please enter the number of chances you would like to allow for this game: ");
      //grab and store all the input that the user enters. Covers many conditions for errors
      chances = getUserInput().nextLine();
    }
    //ask the user for input until input is a reasonable number
    while(!isValidNumChances(chances));
    //create an integer from the acceptable input string
    this.numChances = Integer.parseInt(chances);
  }
 
  
  /*
   * A method to determine if the input given by the user is a valid number that the compiler and the game can 
   * interpret. If the string input is a number that allows gameplay, then the input is valid.
   * @param chances A user inputted string that may or may not be a number.
   * @return boolean The truth behind the input's validity.
   */
  private boolean isValidNumChances(String chances) {
    //if no guess was made, then the input was NOT valid
    if(chances.isEmpty()) {
      System.out.println("You may not leave this field blank!");
      return false;
    }
    //if the user wants no chances to guess, then the input is not valid
    if(chances.equals("0")) {
      System.out.println("You should always give yourself a chance!");
      return false;
    }
    //if the input begins with a zero or more, then the input was NOT valid
    if(chances.charAt(0) == '0') {
      System.out.println("You really don't need the beginning zero(es)!");
      return false;
    }
    //a loop to check the ASCII code of each char in the string
    for(int i = 0; i < chances.length(); i++) {
      //if the input includes a non-number, the input is NOT valid
      if(chances.charAt(i) < '0' || chances.charAt(i) > '9') {
        System.out.println("Remember to only enter positive numbers for this field!");
        return false;
      }
    }
    //if the user wants way too many chances to guess the answer, then the input is NOT valid
    if(isTooLarge(chances)) {
      System.out.println("You may not have that many chances!");
      return false;
    }
    //if the method reaches this point, then the input should be valid
    return true;
  }
  
  
  /*
   * A method to determine if the input number is too large. A number that is too large is any number outside the max 
   * value of an integer because even at this point, that number of gueses is fairly unreasonable. We can assume that 
   * at the point this method is used, it will always be passed a string of solely characters representing a number 0-9.
   * @param chances An user input for the number of allowed chances
   * @return boolean The truth behind if the number is too large.
   */
  private boolean isTooLarge(String chances) {
    //if its a number with 11 digits or more, then the number is too large
    if(chances.length() >= 11) {
      return true;
    }
    //making a long to compare values of leftover cases
    long conversion = Long.valueOf(chances);
    //if the value of the string is ten digits but greater than Integer's max value, then the number is too long
    if(conversion > Integer.MAX_VALUE) {
      return true;
    }
    //if the method reaches this point, then the number should be short enough
    else {
      return false;
    }
  }
  
  
  /*
   * A method to ask the user for a guess, repeatedly, until a vaild guess is input by the user.
   * @return A valid guess inputted by the user with all four digits ranged 1-6.
   */
  private String getGuess() {
    //represents the guess a user may enter. Type string because flexibility with user-input mistakes
    String guess;
    do{
      System.out.println("Enter your guess (or type \"quit\"): ");
      //grab and store all the input that the user enters. Covers many conditions for errors
      guess = userInput.nextLine();
    }
    //continue asking for input until a valid guess is inputted
    while(!isValidGuess(guess));
    return guess;
  }
  
  
  /*
   * A method to determine if a guess from the user is valid or not. A valid guess is a set of four consecutive 
   * integers whose values range from 1-6. If the guess does not follow these rules, then the method will return false.
   * @param guess A user-inputted guess that may or may not be a number.
   * @reutrn boolean The truth behind the input's validity.
   */
  private boolean isValidGuess(String guess) {
    //if the user enters quit, then the guess is valid and the game will end
    if(guess.equals("quit")) {
      quitGame();
      return true;
    }
    //if the user did not make a guess, then the guess is NOT valid
    if(guess.isEmpty()) {
      System.out.println("Remember to make a guess!");
      return false;
    }
    //if the user did not enter four characters, the the guess is NOT valid
    if(guess.length() != 4) {
      System.out.println("Remember to guess exactly four numbers!");
      return false;
    }
    //go through each character to check
    for(int i = 0; i < guess.length(); i++) {
      //if any char in the string is not a number 1-6, then the guess is not valid
      if(guess.charAt(i) < '1' || guess.charAt(i) > '6') {
        System.out.println("Remember to only guess numbers whose values range 1-6!");
        return false;
      }
    }
    //if the method has made it to this point, then the guess was valid.
    return true;
  }
  
  
  /*
   * A method to determine if the winner has won the game.
   * @param guess The guess inputted by the user.
   * @return boolean The truth behind if the user has guessed correctly.
   */
  private boolean isWinner(String guess) {
    //the user has won when the guess equals the secret code
    return guess.equals(getSecretCode());
  }
  
  
  /*
   * An efficient method to print the plus and minus hints to the user. The method first prints the plus signs in 
   * constant time by comparing characters in the same location between the guess and secret code. Then the method 
   * prints the minus signs in constant time by seeing if the char is anywhere else in the string. This method assures 
   * reprints will not happen because once a plus sign is printed, the character will be removed from the comparison 
   * of the two strings.
   * @param guess The user input for the guess of the secret code.
   * @param secretCode The code the user must guess inoder to win the game.
   */
  private void printHints(String guess, String secretCode) {
    //brackets are completely for aesthetic purposes to tell if any guess number was correct
    System.out.print("[ ");
    //a loop to go through all of the secret code for plus sign comparison
    for(int i = 0; i < secretCode.length(); i++) {
      //if the characters match in the same position, then we print a plus sign
      if(guess.charAt(i) == secretCode.charAt(i)) {
        System.out.print(" + ");
        //remove the printed char so it is not compared again
        guess = new StringBuilder(guess).deleteCharAt(i).toString();
        //remove the printed char so it is not compared again
        secretCode = new StringBuilder(secretCode).deleteCharAt(i).toString();
        //need to set back "i" because character indexes have changed from shortening strings
        i--;
      }
    }
    //a loop to go through all of the secret code for negative sign comparison
    for(int i = 0; i < secretCode.length(); i++) {
      //record the matching position in the secretCode
      int index = secretCode.indexOf(guess.charAt(i));
      //if the characters match in a different position, then we print a minus sign
      if(index >= 0) {
        System.out.print(" - ");
        //remove the printed char so it is not compared again
        guess = new StringBuilder(guess).deleteCharAt(i).toString();
        //remove the printed char so it is not compared again
        secretCode = new StringBuilder(secretCode).deleteCharAt(index).toString();
        //need to set back "i" because character indexes have changed from shortening strings
        i--;
      }
    }
    //end brack for aesthetic purposes
    System.out.print(" ]\n");
  }
  
  
  /*
   * A method to quit the game by disallowing another guess and forcing the loss.
   */
  private void quitGame() {
    //set the number of allowed attempts to a number indicating impossibility
    setNumChances(-1);
  }
  
  
  /*
   * A method that will play the guessing portiion of the game with the user. This method ask for input and prints
   * the correct hints, while determining if the user has won.
   */
  public void playGame() {
    //the guess inputted by the user.
    String guess;
    //the number of guesses is intially zero
    int guessCount = 0;
    do {
      guess = getGuess();
      //if the user doesn't wish to quit, then print the hints
      if(!guess.equals("zero")) {
        printHints(guess, secretCode);
      }
      //number of guesses increases by one
      guessCount++;
    }
    //while the user hasn't exceeded his allowed attempts and hasn't won, loop above
    while(guessCount < getNumChances() && !isWinner(guess));
    //if the user won, then tell the user
    if(isWinner(guess)) {
      System.out.println("You solved it!");
    }
    //if the user didn't win, then they lost, so code tells them
    else {
      System.out.println("You lose :(");
    }
    //need to close the scanner to bar the user from entering more input
    userInput.close();
  }
  
  
  /*
   * A method that will print the instructions to this variation of the game of Mastermind.
   */
  public void printInstructions() {
    System.out.println("\nWelcome to Mastermind!");
    System.out.println("The secret code will consist of four integers whose values range from 1 to 6.");
    System.out.println("Here's an example of an allowed guess: 1234");
    System.out.println("Plus signs indicate a correct number in the correct spot.");
    System.out.println("Negative signs indicate a correct number in the incorrect spot.\n");
    System.out.println("Each position in the secret code can only be matched once. For example, a");
    System.out.println("guess of 1134 against a secret code of 1234 would get three plus signs: one");
    System.out.println("for each of the exact matches in the first, third and fourth positions. The");
    System.out.println("number match in the second position would be ignored\n");
  }
  
  
  /*
   * A main method to easily start and play the game.
   */
  public static void main(String[] args) {
    Mastermind game = new Mastermind();
  }
}