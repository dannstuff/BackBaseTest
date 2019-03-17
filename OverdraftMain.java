//Author: Daniel Morgan <daniel96morgan@hotmail.com>
//the main class for the overdraft
import java.util.*;
import java.io.File;

public class OverdraftMain{
  public static void main(String[] args) {
    //we create a new account holder here
    AccountHolder accountHolder = new AccountHolder();
    //the function readFile will return an ArrayList that stores each transaction
    ArrayList<String> csvOutput = CSVReader.readFile(args[0], true);
    //create an ArrayList to store the transactions
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    //the file in which the changes will be logged
    File file = new File(args[1]);



    //iterate over the output from the CSV file and generate a list of transactions.
    for(String line: csvOutput){
      String[] newTransaction = line.split(",");
      Transaction transaction = new Transaction(newTransaction[0], newTransaction[1], newTransaction[2], newTransaction[3], newTransaction[4]);
      transactions.add(transaction);
    }
    CSVReader.writeFirstLine(file); //write the top line in the CSV file, describing the format of the data
    for(int i = 0; i < transactions.size(); i++){
      accountHolder.processTransaction(transactions.get(i), file);
    }
    // if you'd like to see the value of the accounts after processing you can
    //uncomment the line below
    // accountHolder.checkHoldersValue();

  }
}
