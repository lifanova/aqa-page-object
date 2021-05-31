package ru.netology.web.test;


import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    private DashboardPage shouldLogin() {
        open("http://localhost:9999");

        val loginPage = new LoginPage();

        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);

        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        return verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferToFirstCard() {
        //arrange

        // Основная страница, на которой происходят операции
        val dashboardPage = shouldLogin();

        int firstBalance = dashboardPage.getFirstCardBalance();
        int secondBalance = dashboardPage.getSecondCardBalance();

        // сумма перевода
        int amount = 1000;

        // expected
        int expectedFirstBalance = firstBalance + amount;
        int expectedSecondBalance = secondBalance - amount;

        val transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        val secondCard = DataHelper.getSecondCardInfo();
        transferPage.transferToCard(amount, secondCard.getNumber());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferToSecondCard() {
        //arrange

        // Основная страница, на которой происходят операции
        val dashboardPage = shouldLogin();

        int firstBalance = dashboardPage.getFirstCardBalance();
        int secondBalance = dashboardPage.getSecondCardBalance();

        // сумма перевода
        int amount = 1000;

        // expected
        int expectedFirstBalance = firstBalance - amount;
        int expectedSecondBalance = secondBalance + amount;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        val transferPage = dashboardPage.getTransferPageForSecondCard();

        //act
        // осуществляем перевод с первой карты на вторую
        val firstCard = DataHelper.getFirstCardInfo();
        transferPage.transferToCard(amount, firstCard.getNumber());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();

        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferMoreThanBalance() {
        //arrange

        // Основная страница, на которой происходят операции
        val dashboardPage = shouldLogin();

        int secondBalance = dashboardPage.getSecondCardBalance();
        // сумма перевода
        int amount = secondBalance + 5000;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        val transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        // осуществляем перевод: передаем сумму перевода (amount) и инфу о карте, с к-рой списываем
        val secondCard = DataHelper.getSecondCardInfo();
        transferPage.transferToCard(amount, secondCard.getNumber());

        transferPage.getErrorMessage();
    }


}

