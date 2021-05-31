package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    public TransferPage() {

    }

    public void transferToCard(int amount, String from) {
        SelenideElement amountField = $("[data-test-id=amount] input");
        amountField.setValue(String.valueOf(amount));
        SelenideElement fromField = $("[data-test-id=from] input");
        fromField.setValue(from);

        SelenideElement transferButton = $("[data-test-id=action-transfer]");
        transferButton.click();
    }

    public void getErrorMessage(){
        $("[data-test-id=error-notification]").shouldBe(visible);
    }
}
