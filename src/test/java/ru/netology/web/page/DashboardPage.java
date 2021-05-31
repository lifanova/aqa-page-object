package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // основной селектор, показываем, что страница загрузилась
    private SelenideElement heading = $("[data-test-id=dashboard]");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

     public TransferPage getTransferPageForFirstCard() {
        SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();
        firstCardButton.click();

        return new TransferPage();
    }


    public TransferPage getTransferPageForSecondCard() {
        SelenideElement secondCardButton = $$("[data-test-id=action-deposit]").last();
        secondCardButton.click();

        return new TransferPage();
    }

    public int getFirstCardBalance() {
        SelenideElement firstCardBalanceInfo = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");

        return getBalance(firstCardBalanceInfo);
    }

    public int getSecondCardBalance() {
        SelenideElement secondCardBalanceInfo = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");

        return getBalance(secondCardBalanceInfo);
    }

    private int getBalance(SelenideElement element) {
        final String balanceStart = "баланс: ";
        final String balanceFinish = " р.";

        String text = element.getText();

        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String balance = text.substring(start + balanceStart.length(), finish);

        return Integer.parseInt(balance);
    }

}
