package ru.netology.web.test;


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

        // Основная страница, на которой происходят операции
        DashboardPage dashboardPage = shouldLogin();

        int firstBalance = dashboardPage.getFirstCardBalance();
        int secondBalance = dashboardPage.getSecondCardBalance();
        // expected
        int expectedFirstBalance = firstBalance + amount;
        int expectedSecondBalance = secondBalance - amount;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        // осуществляем перевод: передаем сумму перевода (amount) и инфу о карте, с к-рой списываем
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();
        transferPage.transferToCard(amount, secondCard.getNumber());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        System.out.println(" " + expectedFirstBalance + ", " + actualFirstBalance);
        System.out.println(" " + expectedSecondBalance + ", " + actualSecondBalance);

        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferToSecondCard() {
        //arrange
        // сумма перевода
        int amount = 1000;

        // Основная страница, на которой происходят операции
        DashboardPage dashboardPage = shouldLogin();

        int firstBalance = dashboardPage.getFirstCardBalance();
        int secondBalance = dashboardPage.getSecondCardBalance();
        // expected
        int expectedFirstBalance = firstBalance - amount;
        int expectedSecondBalance = secondBalance +  amount;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForSecondCard();

        //act
        // осуществляем перевод с первой карты на вторую
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        transferPage.transferToCard(amount, firstCard.getNumber());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        System.out.println(" " + expectedFirstBalance + ", " + actualFirstBalance);
        System.out.println(" " + expectedSecondBalance + ", " + actualSecondBalance);
        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferMoreThanBalance(){
        //arrange
        // сумма перевода
        int amount = 15000;

        // Основная страница, на которой происходят операции
        DashboardPage dashboardPage = shouldLogin();

        // expected
        int firstBalance = dashboardPage.getFirstCardBalance();
        int secondBalance = dashboardPage.getSecondCardBalance();


        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        // осуществляем перевод: передаем сумму перевода (amount) и инфу о карте, с к-рой списываем
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();
        transferPage.transferToCard(amount, secondCard.getNumber());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        System.out.println(" " + firstBalance + ", " + actualFirstBalance);
        System.out.println(" " + secondBalance + ", " + actualSecondBalance);

        //assertion
        assertEquals(firstBalance, actualFirstBalance);
        assertEquals(secondBalance, actualSecondBalance);
    }
}

