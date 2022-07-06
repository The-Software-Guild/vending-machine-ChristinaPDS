package ui;
import java.math.BigDecimal;
import java.util.Scanner;
public class UserIOConsoleImpl implements UserIO{
    private Scanner keyboard;

    public UserIOConsoleImpl(){
        this.keyboard = new Scanner(System.in);
    }

    @Override
    public void print(String msg){
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt){
        System.out.println(prompt);
        return this.keyboard.nextDouble();
    }

    @Override
    public double readDouble(String prompt, double min, double max){
        System.out.println(prompt);

        double userResponse = -999.0;
        boolean keepGoing = true;

        while(keepGoing) {
            System.out.println(prompt);
            userResponse = this.keyboard.nextDouble();
            keepGoing = userResponse < min || userResponse > max;
        }
        return userResponse;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        return this.keyboard.nextFloat();
    }

    @Override
    public float readFloat(String prompt, float min, float max){
        float userResponse = -999;
        boolean keepGoing = true;

        while(keepGoing) {
            System.out.println(prompt);
            userResponse = this.keyboard.nextFloat();
            keepGoing = userResponse < min || userResponse > max;
            System.out.println(keepGoing);
        }
        return userResponse;
    }

    @Override
    public int readInt(String prompt){
        System.out.println(prompt);
        return this.keyboard.nextInt();
    }

    @Override
    public int readInt(String prompt, int min, int max){
        int userResponse = -999;
        boolean keepGoing = true;

        while(keepGoing) {
            System.out.println(prompt);
            userResponse = this.keyboard.nextInt();
            keepGoing = userResponse < min || userResponse > max;
            System.out.println(keepGoing);
        }
        return userResponse;
    }

    @Override
    public long readLong(String prompt){
        System.out.println(prompt);
        return this.keyboard.nextLong();
    }

    @Override
    public long readLong(String prompt, long min, long max){
        long userResponse = -999;
        boolean keepGoing = true;

        while(keepGoing) {
            System.out.println(prompt);
            userResponse = this.keyboard.nextLong();
            keepGoing = userResponse < min || userResponse > max;
            System.out.println(keepGoing);
        }
        return userResponse;
    }

    @Override
    public String readString(String prompt, boolean consumeNextLineChar){
        System.out.println(prompt);
        if (consumeNextLineChar){
            keyboard.nextLine();
        }
        return keyboard.nextLine();
    }

    @Override
    public BigDecimal readBigDecimal(String prompt){
        System.out.println(prompt);
        String userInput = keyboard.nextLine();
        BigDecimal bigDecimalInput = new BigDecimal(userInput);
        return bigDecimalInput;
    }
}
