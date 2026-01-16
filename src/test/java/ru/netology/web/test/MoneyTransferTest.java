package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV3;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = open("http://localhost:9999", LoginPageV3.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboard = verificationPage.validVerify(
                DataHelper.getVerificationCodeFor(authInfo)
        );

        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();

        int firstBalance = dashboard.getCardBalance(DataHelper.getMaskedNumber(firstCard));
        int secondBalance = dashboard.getCardBalance(DataHelper.getMaskedNumber(secondCard));

        int amount = DataHelper.getTransferAmount(firstBalance);

        var transferPage = dashboard.selectCardToTransfer(DataHelper.getMaskedNumber(firstCard));

        dashboard = transferPage.makeTransfer(amount, secondCard.getNumber());

        assertEquals(
                firstBalance + amount,
                dashboard.getCardBalance(DataHelper.getLast4Digits(firstCard))
        );

        assertEquals(
                secondBalance - amount,
                dashboard.getCardBalance(DataHelper.getLast4Digits(secondCard))
        );
    }
}


