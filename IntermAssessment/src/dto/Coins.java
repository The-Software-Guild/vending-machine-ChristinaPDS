package dto;

public enum Coins {
    ONE_HUNDRED(100),  FIFTY(50),TWENTY(20),TEN(10),FIVE(5), PENNY(1)  ;
    private int value;

    Coins(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
