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
        int amount = 1000;

        int firstBalance = dashboard.getCardBalance(firstCard.getLast4Digits());
        int secondBalance = dashboard.getCardBalance(secondCard.getLast4Digits());

        // Переводим со второй карты на первую
        var transferPage = dashboard.selectCardToTransfer(firstCard.getLast4Digits());
        dashboard = transferPage.makeTransfer(amount, secondCard.getNumber());

        assertEquals(
                firstBalance + amount,
                dashboard.getCardBalance(firstCard.getLast4Digits())
        );

        assertEquals(
                secondBalance - amount,
                dashboard.getCardBalance(secondCard.getLast4Digits())
        );
    }
}

