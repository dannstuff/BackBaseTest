//Author: Daniel Morgan <daniel96morgan@hotmail.com>
import java.util.*;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

//a file that sets up the accounts for an account holder
//this will create a current and savings account for a user
//these accounts are based off the Account class
//all processing of transactions are done in this class



public class AccountHolder{


  private Account currentAccount;
  private Account savingsAccount;

  AccountHolder() {
    currentAccount = new CurrentAccount();
    savingsAccount = new SavingsAccount();
  }

  public void processTransaction(Transaction transaction, File filename) {
    //here we process the transactions, checking if they take the current account
    //value into the account holders overdraft
    //we also write the transaction to the new CSV file
    CSVReader.writeTransaction(transaction.toString(), filename);
    if(transaction.getAccountType().equals("CURRENT")) {
      if (currentAccount.getAccountID() == 0 && currentAccount.getAccountType() == null) {
        currentAccount.setAccountID(transaction.getAccountID());
        currentAccount.setAccountType("CURRENT");
      }
      currentAccount.setAccountValue(transaction.getTransactionValue());
      if(currentAccount.accountBelowZero()) {
        //check if account is below 0, then transfer the funds, we pass
        //filename in to write the new transactions to the output CSV file
        transferFunds(filename);
      }
    }else if (transaction.getAccountType().equals("SAVINGS")) {
      if (savingsAccount.getAccountID() == 0 && savingsAccount.getAccountType() == null) {
        savingsAccount.setAccountID(transaction.getAccountID());
        savingsAccount.setAccountType("SAVINGS");
      }
      savingsAccount.setAccountValue(transaction.getTransactionValue());
    }else{
      throw new IllegalArgumentException("Account Type not Valid");
      }
    }
  public Float getCurrentAccountValue() {
    return currentAccount.getAccountValue();
  }

  public void transferFunds(File filename){
    //the function for transfer of funds
    //we need dateTime as when we do this, we create a new transaction
    //which we need to pass the dateTime into.
    TimeZone timeZone = TimeZone.getTimeZone("UTC");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    dateFormat.setTimeZone(timeZone);
    String iso = dateFormat.format(new Date());

    float current = currentAccount.getAccountValue();
    float savings = savingsAccount.getAccountValue();
    //convert the overdraft of current into positive as this is how much we'll need
    float required = Math.abs(current);

    if(savings <= 0.00f){

      //was going to throw an exception here, but decided on this as
      //the program should still process the rest of the transactions
      System.out.println("Cannot transfer from empty savings account");

    }else if(required > savings){
      //if the required ammount is more than we have in savings, we'll take as much as we can
      savingsAccount.withdrawFromAccount(savings);
      currentAccount.setAccountValue(savings);
      //create the new transactions and write them to the ouput CSV file
      Transaction savingsDeduction = new Transaction(Integer.toString(savingsAccount.getAccountID()), savingsAccount.getAccountType(), "SYSTEM", iso,  Float.toString(-savings));
      CSVReader.writeTransaction(savingsDeduction.toString(), filename);
      Transaction currentAddition = new Transaction(Integer.toString(currentAccount.getAccountID()), currentAccount.getAccountType(), "SYSTEM", iso,  Float.toString(savings));
      CSVReader.writeTransaction(currentAddition.toString(), filename);

      // System.out.println("transfering: " + savings);
    }else {
      //otherwise, we assume there is enough to cover the overdraft
      savingsAccount.withdrawFromAccount(required);
      currentAccount.setAccountValue(required);
      Transaction savingsDeduction = new Transaction(Integer.toString(savingsAccount.getAccountID()), savingsAccount.getAccountType(), "SYSTEM", iso,  Float.toString(-required));
      CSVReader.writeTransaction(savingsDeduction.toString(), filename);
      Transaction currentAddition = new Transaction(Integer.toString(currentAccount.getAccountID()), currentAccount.getAccountType(), "SYSTEM", iso,  Float.toString(required));
      CSVReader.writeTransaction(currentAddition.toString(), filename);
      // System.out.println("transfering: " + required);

    }

  }

  public void checkHoldersValue(){
    //check how much is in the holders account
    System.out.println("\n");
    System.out.println("currentID:" + currentAccount.getAccountID() + "\n" + "ammount: " + currentAccount.getAccountValue());
    System.out.println("SavingsID:" + savingsAccount.getAccountID() + "\n" + "ammount: " + savingsAccount.getAccountValue());

  }
}
