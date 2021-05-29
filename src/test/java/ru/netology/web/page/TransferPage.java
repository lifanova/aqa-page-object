package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    public TransferPage() {
        //Передаем на страницу данные карты, на которую переводим
        //SelenideElement toField = $("[data-test-id=to] input");
        //toField.setValue(cardInfo.getNumber());
    }

    public void transferToCard(int amount, String from) {
        SelenideElement amountField = $("[data-test-id=amount] input");
        amountField.setValue(String.valueOf(amount));
        SelenideElement fromField = $("[data-test-id=from] input");
        fromField.setValue(from);

        //System.out.println("[transferToCard]: " +$("[data-test-id=to] input").getText());

        SelenideElement transferButton = $("[data-test-id=action-transfer]");
        transferButton.click();
    }
}
