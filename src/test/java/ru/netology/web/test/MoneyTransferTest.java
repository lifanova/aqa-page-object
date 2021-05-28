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
    // Основная страница, на которой происходят операции
    private DashboardPage dashboardPage;

    @BeforeEach
    public void shouldLogin() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);

        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferToFirstCard() {
        //arrange
        // сумма перевода
        int amount = 1000;
        // находимся на dashboard page
        // баланс карты, на которую переводим - 1
        int firstBalance = DataHelper.getFirstCardInfo().getBalance();
        // баланс карты, с которой списываем
        int secondBalance = DataHelper.getSecondCardInfo().getBalance();
        // expected
        int expectedFirstBalance = firstBalance + amount;
        int expectedSecondBalance = secondBalance - amount;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForFirstCard();

        //act
        // осуществляем перевод
        transferPage.transferToCard(amount, DataHelper.getSecondCardInfo());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();
        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    public void shouldTransferToSecondCard() {
        //arrange
        // сумма перевода
        int amount = 1000;
        // находимся на dashboard page
        // баланс карты, на которую переводим - 1
        int secondBalance = DataHelper.getSecondCardInfo().getBalance();
        // баланс карты, с которой списываем
        int firstBalance = DataHelper.getFirstCardInfo().getBalance();
        // expected
        int expectedFirstBalance = firstBalance - amount;
        int expectedSecondBalance = secondBalance + amount;

        // dashboardPage - выбираем карту, на которую переводим, и кликаем против нее на пополнить
        TransferPage transferPage = dashboardPage.getTransferPageForSecondCard();

        //act
        // осуществляем перевод с первой карты на вторую
        transferPage.transferToCard(amount, DataHelper.getFirstCardInfo());

        int actualFirstBalance = dashboardPage.getFirstCardBalance();
        int actualSecondBalance = dashboardPage.getSecondCardBalance();
        //assertion
        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }
}

