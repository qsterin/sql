package ru.netology.data.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import ru.netology.data.data.DataHelper;
import ru.netology.data.data.SQLHelper;
import ru.netology.data.pagedata.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.data.SQLHelper.deleteEverithing;

public class SQLTest {

    @AfterAll
    static void teardown() {
        deleteEverithing();
    } //хотя без него все работает

    @Test
    void shouldLoginwithValidData() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomUser();
        var verificationPage = loginPage.validLogin(authInfo);
        LoginPage.searchErrorMessage("Ошибка");
    }

    @Test
    void shouldNotLoginWithInvalidVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode());
        LoginPage.searchErrorMessage("Ошибка");
    }

    @Test
    void shouldThrowErrorAfterThreeAttemptsWithIncorectVarificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode());
        LoginPage.searchErrorMessage("Ошибка");
        loginPage = open("http://localhost:9999", LoginPage.class);
        authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode());
        LoginPage.searchErrorMessage("Ошибка");
        loginPage = open("http://localhost:9999", LoginPage.class);
        authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode());
        LoginPage.searchErrorMessage("Ошибка");
        loginPage = open("http://localhost:9999", LoginPage.class);
        authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode());
        LoginPage.searchErrorMessage("количество");
    }
}