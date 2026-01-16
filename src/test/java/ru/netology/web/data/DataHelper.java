package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String number;

        public String getMaskedNumber() {
            return "**** **** **** " + number.substring(number.length() - 4);
        }
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }


    public static String getLast4Digits(CardInfo card) {
        return card.getNumber().substring(card.getNumber().length() - 4);
    }
    public static int getTransferAmount(int balance) {
        // Возьмём половину баланса для перевода
        return balance / 2;
    }

}

