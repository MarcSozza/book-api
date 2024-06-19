package org.books.api;

import org.books.api.errors.CustomErrorException;
import org.books.api.errors.ErrorCode;
import org.books.api.errors.NotExhaustiveYear;
import org.books.api.services.UtilsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("Utils_Service")
@DisplayName("Test des utilitaires Globaux")
public class UtilsServiceTest {

    @Autowired
    private UtilsService utilsService;

    @Nested
    @Tag("replace_accent_test")
    @DisplayName("Test de la fonction replaceAccent")
    class ReplaceAccent {
        @ParameterizedTest(name = "doit remplacer l'accent dans le paramètre d'entrée {0} et le résultat doit être {1}")
        @CsvSource({
                "'épine', 'epine'",
                "'Éric', 'Eric'",
                "'arrêt', 'arret'"
        })
        void givenTextShouldReplaceAccentAigu(String value, String expectedResult) {

            String result = utilsService.replaceAccent(value);

            assertThat(result).isEqualTo(expectedResult);
        }

        @Test
        @Tag("replace_accent_test")
        @DisplayName("doit renvoyer le même paramètre d'entrée s'il n'y a pas d'accent")
        void givenTextWithoutAccentShouldReturnSameText() {
            String value = "texte sans accent";
            String result = utilsService.replaceAccent(value);
            assertThat(value).isEqualTo(result);
        }

    }

    @Nested
    @Tag("same_input_value")
    @DisplayName("Test de la fonction isStringEqualIgnoreCase")
    class SameInputValue {
        @Tag("same_input_value")
        @ParameterizedTest(name = "Si la valeur 1 est ({0}) est la valeur 2 est ({1}) alors la fonction isStringEqualIgnoreCase doit renvoyer vrai")
        @CsvSource({
                "'John Doe','John Doe'",
                "'John Doe', 'John doe'",
                "'  John Doe', 'John Doe'",
                "'Eric Bidule', 'Éric Bidule'"
        })
        void givenTwoStringShouldReturnTrueIfEquals(String value, String value2) {

            boolean result = utilsService.isStringEqualIgnoreCase(value, value2);

            assertThat(result).isTrue();
        }

        @Test
        @Tag("same_input_value")
        @DisplayName("doit renvoyer faux si les 2 valeurs ne correspondent pas")
        void givenTwoStringShouldReturnFalseIfNotEquals() {
            String value = "value";
            String value2 = "not_same_value";

            boolean result = utilsService.isStringEqualIgnoreCase(value, value2);

            assertThat(result).isFalse();
        }
    }

    @Nested
    @Tag("year_inferior_or_same_at_now")
    @DisplayName("Test de la fonction isPublicationYearInPastOrPresent")
    class YearInferiorOrSameAtNow {

        @Test
        @DisplayName("Renvoyer vrai si l'année de publication est dans le passé par rapport à la date d'aujourdui")
        void givenYearInferiorAtNowShouldReturnTrue() {
            String yearToTest = "2000";
            String currentYear = "2023";

            boolean result = utilsService.isPublicationYearInPastOrPresent(yearToTest, currentYear);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Renvoyer vrai si l'année de publication est la même date qu'aujourd'hui")
        void givenYearSameAtNowShouldReturnTrue() {
            String yearToTest = "2023";
            String currentYear = "2023";

            boolean result = utilsService.isPublicationYearInPastOrPresent(currentYear, yearToTest);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Renvoyer faux si l'année de publication est supérieur à la date d'aujourd'hui")
        void givenYearSuperiorAtNowShouldReturnFalse() {
            String yearToTest = "2024";
            String currentYear = "2023";

            boolean result = utilsService.isPublicationYearInPastOrPresent(yearToTest, currentYear);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Renvoyer une erreur si l'année de publication n'est pas une donnée valable")
        void givenYearNotExhaustiveShouldThrowError() {
            String yearToTest = "Je ne suis pas une année";
            String currentYear = "2023";

            CustomErrorException exception = assertThrows(NotExhaustiveYear.class,
                                                          () -> utilsService.isPublicationYearInPastOrPresent(
                                                                  yearToTest,
                                                                  currentYear));
            assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ERROR_CODE_NOT_EXHAUSTIVE_YEAR.getCode());
        }
    }
}
