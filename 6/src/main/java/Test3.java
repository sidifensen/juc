import java.math.BigDecimal;

public class Test3 {
    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountSafeCas(new BigDecimal("10000")));
    }
}
