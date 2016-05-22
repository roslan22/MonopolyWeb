package MonopolyGameWeb.logic.model;

public class DiceRoll
{
    private int firstDiceResult;
    private int secondDiceResult;

    public DiceRoll(int firstDiceResult, int secondDiceResult)
    {
        validateDiceResult(firstDiceResult);
        validateDiceResult(secondDiceResult);

        this.firstDiceResult = firstDiceResult;
        this.secondDiceResult = secondDiceResult;
    }

    private void validateDiceResult(int diceRollResult)
    {
        if (diceRollResult < 1 || diceRollResult > 6)
        {
            throw new IllegalArgumentException("Dice result must be between 1 and 6");
        }
    }

    public int getFirstDiceResult()
    {
        return firstDiceResult;
    }

    public int getSecondDiceResult()
    {
        return secondDiceResult;
    }
    
    public int getResult()
    {
        return firstDiceResult + secondDiceResult;
    }

    public boolean isDouble(){return firstDiceResult == secondDiceResult;}

    @Override
    public String toString()
    {
        return firstDiceResult + ", " + secondDiceResult;
    }
}
