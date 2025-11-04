package Objects;

import Objects.Enums.TextColors;

public class Bet extends Utils {
    private int startingBankRoll = 0;
    private int tableMinimum = 0;
    private int bankRoll = 0;
    private int currentBet = 0;
    private int insurance = 0;

    public int getCurrentBet(){
        return currentBet;
    }
    public int getBankRoll(){
        return bankRoll;
    }
    public int getTableMinimum(){return tableMinimum;}

//    <GetMethods>
    protected boolean verifyBankrollTwiceBet(){
        return currentBet * 2 <= bankRoll;
    }
//    </GetMethods>

    //    <SetMethods>
    protected void setBankRoll() {
        while(true) {
            showTextInLine("How much money would you like to start with? Up to $1000, increments of $1.\n$", TextColors.GREEN);
            startingBankRoll = getIntResponse();
            if (startingBankRoll > 1000){
                showText("Please enter an amount no greater than $1000.");
                continue;
            }
            break;
        }
        bankRoll = startingBankRoll;
        showBankRoll();
    }
    protected void setTableMinimum() {
        int maximumBet = Math.min(bankRoll,100);
        while (true) {
            showTextInLine("What table minimum would you like? Up to $"+maximumBet+", increments of $1.\n$", TextColors.GREEN);
            tableMinimum = getIntResponse();
            if (tableMinimum > startingBankRoll){
                showText("Please enter an amount less than $"+startingBankRoll+".");
                continue;
            }
            break;
        }
    }

    public void makeBet() {
        while(true) {
            showTextInLine("You have $"+bankRoll+". How much would you like to bet?\n(Table minimum is $"+ tableMinimum +". Increments of $1 only.)\n$", TextColors.GREEN);
            currentBet = getIntResponse();
            if (currentBet > bankRoll)
            {
                showText("Please enter a bet less than or equal to your Bank Roll", TextColors.GREEN);
                currentBet = 0;
                continue;
            }
            else if (currentBet < tableMinimum)
            {
                showText("Please enter a bet greater than the minimum.", TextColors.GREEN);
                currentBet = 0;
                continue;
            }
            break;
        }
    }

    protected void resetBet(){
        currentBet = 0;
    }
    protected void resetInsurance() {insurance = 0;}

    public void makeInsuranceBet(){
        while(true) {
            int maxBet = Math.min(bankRoll, currentBet / 2);
            showTextInLine("You can bet up to $"+maxBet+". How much would you like to bet for Black Jack insurance?\n$", TextColors.GREEN);
            insurance = getIntResponse();

            if (insurance > currentBet/2)
            {
                showText("Please enter a bet less than or equal to your Bank Roll", TextColors.GREEN);
                continue;
            }
            else if(currentBet <= 0)
            {
                showText("Please enter a whole dollar bet greater than zero.", TextColors.GREEN);
                continue;
            }
            break;
        }
    }

    protected boolean doubleDown()
    {
        if (currentBet*2<=bankRoll) {
            currentBet *= 2;
            showText("You have doubled down. Your bet is now $"+currentBet+".", TextColors.GREEN);
            pressAnyKey();
            return true;
        }
        else {
            showText("You do not have enough money to double down.", TextColors.GREEN);
            pressAnyKey();
            return false;
        }
    }
//    </SetMethods>

//    <ShowMethods>
    public void showBet()
    {
        showText("You are betting $"+currentBet+" out of $"+bankRoll, TextColors.GREEN);
        pressAnyKey();
    }

    public void showBankRoll(){
        showText("Your bank roll is now $"+bankRoll+".", TextColors.GREEN);
    }

    public void showInsurance(){
        showText("You have bet $"+insurance+" against the dealer having Black Jack", TextColors.GREEN);
        pressAnyKey();
    }
//    </ShowMethods>

//    <BetResolutionMethods>
    protected void insuranceWin(){
        if (insurance>0) {

            bankRoll += insurance * 2;
            showText("You won $" + insurance * 2 + " from your insurance.", TextColors.GREEN);
            pressAnyKey();
        }
            lose();
            pressAnyKey();
    }
    protected void insuranceLose() {
        if (insurance > 0) {
            bankRoll -= insurance;
            showText("You have lost $" + insurance + " from your insurance.", TextColors.GREEN);
            pressAnyKey();
        }
    }
    protected void win()
    {
        bankRoll += currentBet;
        showText("You won $"+currentBet+"!", TextColors.GREEN);
        showBankRoll();
        currentBet *=2;
    }

    protected void blackjackWin()
    {
        int winnings = currentBet*2;
        bankRoll += winnings;
        showText("You won $"+winnings+"!", TextColors.GREEN);
        showBankRoll();
        currentBet *=3;
    }

    protected void lose()
    {
        bankRoll -= currentBet;
        showText("You lost the hand.", TextColors.GREEN);
        showBankRoll();
        currentBet = 0;
    }
    protected void push()
    {
        showText("You push.", TextColors.GREEN);
        showBankRoll();
    }
    //    </BetResolutionMethods>

}
