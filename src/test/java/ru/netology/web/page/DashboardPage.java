package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection cards = $$("div[data-test-id]");

    // Получение баланса по последним 4 цифрам карты
    public int getCardBalance(String last4Digits) {
        // Используем просто text(...) — ищет подстроку
        String cardText = cards.findBy(Condition.text(last4Digits)).getText();
        return extractBalance(cardText);
    }

    private int extractBalance(String text) {
        String balance = text.substring(
                text.indexOf("баланс: ") + 8,
                text.indexOf(" р.")
        );
        return Integer.parseInt(balance.trim());
    }

    // Выбор карты для пополнения
    public TransferPage selectCardToTransfer(String last4Digits) {
        cards.findBy(Condition.text(last4Digits))
                .$("button[data-test-id='action-deposit']")
                .click();
        return new TransferPage();
    }
}



