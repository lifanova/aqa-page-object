package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    public TransferPage(DataHelper.CardInfo cardInfo) {
        //Передаем на страницу данные карты, на которую переводим
        //SelenideElement toField = $("[data-test-id=to] input");
        //toField.setValue(cardInfo.getNumber());
    }

    public void transferToCard(int amount, DataHelper.CardInfo from) {
        SelenideElement amountField = $("[data-test-id=amount] input");
        amountField.setValue(String.valueOf(amount));
        SelenideElement fromField = $("[data-test-id=from] input");
        fromField.setValue(from.getNumber());
        SelenideElement transferButton = $("[data-test-id=action-transfer]");
        transferButton.click();
    }
}
