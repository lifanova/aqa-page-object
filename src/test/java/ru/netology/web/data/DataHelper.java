package ru.netology.web.data;


public class DataHelper {
    public static final String LOGIN = "vasya";
    public static final String PASSWORD = "qwerty123";
    public static final String VER_CODE = "12345";
    public static final String FIRST_CARD_NUMBER = "5559000000000001";
    public static final String SECOND_CARD_NUMBER = "5559000000000002";
    public static final Integer BALANCE = 10000;

    private DataHelper() {
    }


    /* Методы */
    public static AuthInfo getAuthInfo() {
        return new AuthInfo(LOGIN, PASSWORD);
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode(VER_CODE);
    }


    public static class AuthInfo {
        private String login;
        private String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    public static class VerificationCode {
        private String code;

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class CardInfo {
        private String number;
        private Integer balance;

        public CardInfo(String number, Integer balance) {
            this.number = number;
            this.balance = balance;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo(FIRST_CARD_NUMBER, BALANCE);
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo(SECOND_CARD_NUMBER, BALANCE);
    }

    public static int subtractFromBalance(int balance, int amount) {
        int result = balance - amount;

        if (result < 0) {
            result = balance;
        }

        return result;
    }

    public static int addToBalance(int balance, int amount) {
        return balance + amount;
    }
}
