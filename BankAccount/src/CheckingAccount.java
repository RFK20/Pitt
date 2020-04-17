
public class CheckingAccount extends BankAccount
{
    private static final int Balance = 0;
	private int Overdraft;


    public CheckingAccount(String Customer, String AccountNumb, int Balance, int Overdraw)
      {

          super (Customer, AccountNumb, Balance);

          Overdraft = Overdraw;
      }

     public float getOverdraftAmount()
     {
         return(Overdraft);
     }

    public void setOverdraft()
      {
          if (Balance < Overdraft)
              System.out.println("Account overdrawn");

     }
      public void Withdraw(int WithdrawAmount)
     {  
         if(WithdrawAmount < 0)
              System.out.println("Sorry, you can not withdraw a negative amount");
         else if (Balance-WithdrawAmount<getOverdraftAmount())
              System.out.println("Sorry, you cannot withdraw that amount");
         else
              Balance = Balance - WithdrawAmount;
     }
  }
