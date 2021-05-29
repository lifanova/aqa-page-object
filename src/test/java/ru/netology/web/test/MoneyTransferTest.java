package ru.netology.web.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {


    private DashboardPage shouldLogin() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);

        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        return verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferToFirstCard() {
        //arrange
        // сумма перевода
        int amount = 1000;
        // находимся на dashboard page
        // баланс карты, на которую переводим - 1
        // Создаем объект первой карты CardInfo
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        int firstBalance = firstCard.getBalance();

        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();
        // баланс карты, с которой списываем
        int secondBalance = secondCard.getBalance();
        // expected
        int expectedFirstBalance = firstBalance + amount;
        int expectedSecondBalance = secondBalance - amount;

         // Основная страница, на которой происходят операции
        DashboardPage dashboardPage = shouldLogin();
        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        // осуществляем перевод: передаем сумму перевода (amount) и инфу о карте, с к-рой списываем
        transferPage.transferToCard(amount, secondCard);

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        System.out.println(" " + expectedFirstBalance + ", " + actualFirstBalance);
        System.out.println(" " + expectedSecondBalance + ", " + actualSecondBalance);

        //assertion
        //assertEquals(expectedFirstBalance, actualFirstBalance);
        //assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferToSecondCard() {
        //arrange
        // сумма перевода
        int amount = 1000;
        // находимся на dashboard page
        // баланс карты, на которую переводим - 2
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();
        int secondBalance = secondCard.getBalance();
        // баланс карты, с которой списываем
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        int firstBalance = firstCard.getBalance();
        // expected
        int expectedFirstBalance = firstBalance - amount;
        int expectedSecondBalance = secondBalance + amount;

        // Основная страница, на которой происходят операции
        DashboardPage dashboardPage = shouldLogin();
        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForSecondCard();

        //act
        // осуществляем перевод с первой карты на вторую
        transferPage.transferToCard(amount, DataHelper.getFirstCardInfo());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        System.out.println(" " + expectedFirstBalance + ", " + actualFirstBalance);
        System.out.println(" " + expectedSecondBalance + ", " + actualSecondBalance);
        //assertion
        //assertEquals(expectedFirstBalance, actualFirstBalance);
        //assertEquals(expectedSecondBalance, actualSecondBalance);
    }
}

