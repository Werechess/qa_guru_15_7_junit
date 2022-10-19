package com.example;

import com.codeborne.selenide.CollectionCondition;
import com.example.data.Locale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class WebTest {

//    static Stream<Arguments> yandexSearchCommonTest() {
//        return Stream.of(
//                Arguments.of("Selenide"),
//                Arguments.of("JUnit")
//        );
//    }

    @ValueSource(strings = {"Selenide", "JUnit"})
    @ParameterizedTest(name = "Проверка числа результатов поиска в Яндексе для запроса {0}")
    void yandexSearchCommonTest(String testData) {
        open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(testData));
    }

//    static Stream<Arguments> yandexSearchCommonTestWithDescriptions() {
//        return Stream.of(
//                Arguments.of("Selenide", "лаконичные и стабильные UI тесты на Java"),
//                Arguments.of("JUnit", "junit.org")
//        );
//    }

//    если в тексте есть запятые - можно сменить разделитель, пример:
//    @CsvSource({
//            "Selenide | Selenide, фреймворк для автоматизированного тестирования",
//            "JUnit | A programmer-oriented testing framework for Java"
//    }, delimiter = '|' )

    @CsvSource({
            "Selenide, лаконичные и стабильные UI тесты на Java",
            "JUnit, junit.org"
    })
    @ParameterizedTest(name = "Проверка числа результатов поиска в Яндексе для запроса {0}")
    void yandexSearchCommonTestWithDescriptions(String searchQuery, String expectedDescription) {
        open("https://ya.ru");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(expectedDescription));
    }

    static Stream<Arguments> selenideSiteButtonsTextDataProvider() {
        return Stream.of(
                Arguments.of(List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes"), Locale.EN),
                Arguments.of(List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"), Locale.RU)
        );
    }

    // если имя теста совпадает с названием провайдера - метод можно оставить пустым
    @MethodSource("selenideSiteButtonsTextDataProvider")
    @ParameterizedTest(name = "Проверка отображения названия кнопок для локали: {1}")
    void selenideSiteButtonsText(List<String> buttonsText, Locale locale) {
        open("https://selenide.org");
        $$("#languages a").find(text(locale.name())).click();
        $$(".main-menu-pages a").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonsText));
    }

    @EnumSource(Locale.class)
    @ParameterizedTest(name = "Проверка отображения кнопки локали: {0}")
    void checkLocaleTest(Locale locale) {
        open("https://selenide.org");
        $$("#languages a").find(text(locale.name())).shouldBe(visible);
    }
}
