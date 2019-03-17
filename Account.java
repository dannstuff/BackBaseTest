//Author: Daniel Morgan <daniel96morgan@hotmail.com>
import java.util.*;

public class Account {
  private Float accountValue;
  private int accountID;
  private String accountType;

    Account(){
      //initiate on 0.00
      accountValue = 0.00f;
    }

  public void setAccountValue(Float transactionValue){
    //applies a change to the account value
    accountValue = accountValue + transactionValue;
    accountValue = Math.round(accountValue * 100.0f) / 100.0f;
  }

  public Float getAccountValue() {
    return accountValue;
  }

  public void setAccountID(int accountID){
    this.accountID = accountID;

  }

  public int getAccountID() {
    return accountID;
  }

  public void setAccountType(String type) {

    accountType = type;
  }

  public String getAccountType(){
    return accountType;
  }

  public Boolean accountBelowZero(){
    if(accountValue < 0) {
      return true;
    }else {
      return false;
    }
  }

  public void withdrawFromAccount(float withdrawAmmount){
    accountValue = accountValue - withdrawAmmount;

  }

  public String toString(){
    return "AccountID: " + accountID + "\n" + "Account Value: " + accountValue;
  }

}
