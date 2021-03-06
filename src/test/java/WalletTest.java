import com.thoughtworks.wallet.currency.CurrencyType;
import com.thoughtworks.wallet.exception.LowAmountException;
import com.thoughtworks.wallet.wallet.Wallet;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTest {
    @Test
    void shouldAbleToPutMoneyInWallet() {
        Wallet wallet = new Wallet(0,CurrencyType.RUPEE);
        double value = 10;

        assertDoesNotThrow(() -> wallet.add(value,CurrencyType.RUPEE));
    }

    @Test
    void shouldAbleToWithdrawMoneyFromWallet() throws LowAmountException {
        Wallet wallet = new Wallet(0,CurrencyType.RUPEE);
        double value = 10;
        wallet.add(value,CurrencyType.RUPEE);
        double withdrawAmount = 5;

        wallet.withdraw(withdrawAmount);
        double resultWithdrawAmount = wallet.getWithdrawMoney();

        assertThat(resultWithdrawAmount,is(equalTo(withdrawAmount)));
    }

    @Test
    void shouldThrowAnExceptionIfWithdrawAmountIsGreaterThanAmountInWallet() {
        Wallet wallet = new Wallet(0,CurrencyType.RUPEE);
        int addValue = 100;
        wallet.add(addValue,CurrencyType.RUPEE);
        int withdrawAmount = 500;

        assertThrows(LowAmountException.class,() -> wallet.withdraw(withdrawAmount));
    }

    @Test
    void shouldAbleToAddDifferentCurrencyInWallet() {
        Wallet wallet = new Wallet(0,CurrencyType.RUPEE);

        assertDoesNotThrow(() -> wallet.add(1,CurrencyType.DOLLAR));
    }

    @Test
    void shouldReturnAvailableAmountAsSumOfDifferentCurrencyInPreferredCurrency() {
        Wallet wallet1 = new Wallet(0,CurrencyType.RUPEE);
        wallet1.add(50,CurrencyType.RUPEE);
        wallet1.add(1,CurrencyType.DOLLAR);

        double totalAmount = wallet1.getTotalMoneyAvailable();

        assertThat(totalAmount,is(equalTo(124.85)));


        Wallet wallet2 = new Wallet(0,CurrencyType.DOLLAR);
        wallet2.add(74.85,CurrencyType.RUPEE);
        wallet2.add(1,CurrencyType.DOLLAR);
        wallet2.add(149.7,CurrencyType.RUPEE);

        totalAmount = wallet2.getTotalMoneyAvailable();

        assertThat(totalAmount,is(equalTo(4.0)));
    }
}
