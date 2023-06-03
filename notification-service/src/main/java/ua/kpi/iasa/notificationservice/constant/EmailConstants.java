package ua.kpi.iasa.notificationservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmailConstants {

  public static final String REPAIRMENT_SUBJECT = "Успішне створення заявки на відновлення";
  public static final String REPAIRMENT_TEXT_WITH_APARTMENT = """
      Вітаємо Вас.
      На Вашу електронну адресу уло зареєстровано заявку на відновлення нерухомості на ім'я:
      {0}, {1}, {2}.
      
      Вашу заявку на відновлення прийнято і опрацьовано з кодом: {3}
      
      Адреса:
      населений пункт : {4}
      вул.{5}
      буд.{6}
      кв.{7}
      
      Дата створення заявки: {8}.
      
      Відповідно до внесених даних, відновлення вашої нерухомості оцінено в {9} гривень.
      
      З цим листом та кодом заявки можете звернутись до найближчого представництва.
      """;
  public static final String REPAIRMENT_TEXT_WITHOUT_APARTMENT = """
      Вітаємо Вас, {0}, {1}, {2}.
      
      Вашу заявку на відновлення прийнято і опрацьовано з кодом: {3}
      
      Адреса:
      населений пункт : {4}
      вул.{5}
      буд.{6}
      
      Дата створення заявки: {7}.
      
      Відповідно до внесених даних, відновлення вашої нерухомості оцінено в {8} гривень.
      
      З цим листом та кодом заявки можете звернутись до найближчого представництва.
      """;

}
