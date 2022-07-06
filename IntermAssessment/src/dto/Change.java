package dto;

public class Change {
    public int numOf5Cents;
    public int numOf10Cents;
    public int numOf20Cenets;
    public int numOf50Cents;
    public int numOf1Cents;

    Change(int... coinsEntered){
        this.numOf5Cents = coinsEntered[0];
        this.numOf10Cents = coinsEntered[1];
        this.numOf20Cenets = coinsEntered[2];
        this.numOf50Cents = coinsEntered[3];
        this.numOf1Cents = coinsEntered[4];
    }

    public int getNumOf5Cents() {
        return numOf5Cents;
    }

    public int getNumOf10Cents() {
        return numOf10Cents;
    }

    public int getNumOf20Cenets() {
        return numOf20Cenets;
    }

    public int getNumOf50Cents() {
        return numOf50Cents;
    }

    public int getNumOf1Cents() {
        return numOf1Cents;
    }
}
