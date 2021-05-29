package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // основной селектор, показываем, что страница загрузилась
    private SelenideElement heading = $("[data-test-id=dashboard]");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage getTransferActionForm(SelenideElement button, DataHelper.CardInfo cardInfo) {
        button.click();

        return new TransferPage(cardInfo);
    }

    public TransferPage getTransferPageForFirstCard() {
        SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();

        return getTransferActionForm(firstCardButton, DataHelper.getFirstCardInfo());
    }


    public TransferPage getTransferPageForSecondCard() {
        SelenideElement secondCardButton = $$("[data-test-id=action-deposit]").get(1);

        return getTransferActionForm(secondCardButton, DataHelper.getSecondCardInfo());
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
        System.out.println(text);
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String balance = text.substring(start + balanceStart.length(), finish);

        System.out.println("[getBalance]: " + balance);
        return Integer.parseInt(balance);
    }

}
