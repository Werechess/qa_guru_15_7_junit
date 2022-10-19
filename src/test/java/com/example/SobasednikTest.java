package com.example;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

class SobasednikTest {

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
    }

    static Stream<Arguments> sobasednikMenuButtonsWithMethodSourceTest() {
        return Stream.of(
                Arguments.of(List.of("О НАС", "БЛОГ ДЖЕССИ", "МАГАЗИН", "СОВЕТЫ", "QAHACKING"), "https://qahacking.guru/"),
                Arguments.of(List.of("О НАС", "БЛОГ ДЖЕССИ", "МАГАЗИН", "СОВЕТЫ", "QAHACKING"), "https://qahacking.guru/index.php/about"),
                Arguments.of(List.of("О НАС", "БЛОГ ДЖЕССИ", "МАГАЗИН", "СОВЕТЫ", "QAHACKING"), "https://qahacking.guru/index.php/news"),
                Arguments.of(List.of("О НАС", "БЛОГ ДЖЕССИ", "МАГАЗИН", "СОВЕТЫ", "QAHACKING"), "https://qahacking.guru/index.php/magazin")
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Проверка наличия кнопок меню на странице {1}")
    void sobasednikMenuButtonsWithMethodSourceTest(List<String> buttonName, String Link) {
        open(Link);
        $$(".uk-navbar-nav > li > a")
                .shouldHave(CollectionCondition.texts(buttonName));
    }


    @CsvSource({
            "О НАС, https://qahacking.guru/index.php/about",
            "БЛОГ ДЖЕССИ, https://qahacking.guru/index.php/news",
            "МАГАЗИН, https://qahacking.guru/index.php/magazin",
            "СОВЕТЫ, https://qahacking.guru/#",
            "QAHACKING, http://qahacking.ru/",
    })
    @ParameterizedTest(name = "Проверка ссылки и видимости кнопки меню {0}")
    void sobasednikMenuButtonsWithCSVSourceTest(String expectedName, String expectedLink) {
        open("https://qahacking.guru/");
        $$(".uk-navbar-nav > li > a")
                .shouldHave(CollectionCondition.size(5))
                .find(text(expectedName))
                .shouldHave(attribute("href", expectedLink))
                .shouldBe(visible);
    }


    enum ButtonName {
        ABOUT("О НАС"),
        NEWS("БЛОГ ДЖЕССИ"),
        SHOP("МАГАЗИН"),
        ADVICE("СОВЕТЫ"),
        MAIN("QAHACKING");

        private final String buttonName;

        ButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public String getButtonName() {
            return buttonName;
        }
    }

    @ParameterizedTest(name = "Проверка видимости кнопки меню {0} через EnumSource")
    @EnumSource(ButtonName.class)
    void sobasednikMenuButtonsWithEnumSourceTest(ButtonName buttonName) {
        open("https://qahacking.guru/");
        $$(".uk-navbar-nav > li > a")
                .shouldHave(CollectionCondition.size(5))
                .find(text(buttonName.getButtonName()))
                .shouldBe(visible);
    }


    @ValueSource(strings = {"О НАС", "БЛОГ ДЖЕССИ", "МАГАЗИН", "СОВЕТЫ", "QAHACKING"})
    @ParameterizedTest(name = "Проверка видимости кнопки меню {0} через ValueSource")
    void sobasednikMenuButtonsWithValueSourceTest(String buttonName) {
        open("https://qahacking.guru/");
        $$(".uk-navbar-nav > li > a")
                .shouldHave(CollectionCondition.size(5))
                .find(text(buttonName))
                .shouldBe(visible);
    }
}
