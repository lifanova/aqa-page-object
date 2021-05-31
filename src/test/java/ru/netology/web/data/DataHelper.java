package ru.netology.web.data;


import lombok.Value;

public class DataHelper {


    private DataHelper() {
    }


    /* Методы */
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @Value
    public static class CardInfo {
        private String number;
        private Integer balance;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001", 10000);
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002", 10000);
    }


}
