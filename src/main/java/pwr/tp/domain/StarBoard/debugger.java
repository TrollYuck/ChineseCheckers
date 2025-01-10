package pwr.tp.domain.StarBoard;

public class debugger {
    public static void main(String[] args) {
        StarBoard starBoard = new StarBoard();
        System.out.println(starBoard.stripes.size());
        for(Stripe stripe: starBoard.stripes) {
            System.out.println(stripe.getFieldsInRow());
        }
    }
}
