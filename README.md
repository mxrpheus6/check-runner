Этот проект реализует Java-приложение для создания чека с данными покупок с использованием CSV-файлов и командной строки.

#### Видеодемонстрация
https://youtu.be/xbD7jFKvw3c

## Стек
- Java 21
- Gradle 8.5
- PostgreSQL

## Структура проекта

- `src/main/java/ru/clevertec/check`: Корневой пакет для всех классов
- `src/main/java/ru/clevertec/check/CheckRunner.java`: Основной класс приложения

## Запуск приложения
1. **Компиляция**: Перейдите в корневую директорию проекта и выполните команду:  
   `gradle build`
2. **Запуск**: Для запуска приложения используйте следующую команду, указав необходимые аргументы, например:  
   `java -jar clevertec-check.jar 3-1 2-5 5-1 discountCard=4444 balanceDebitCard=100 saveToFile=./my_result.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=postgres datasource.password=postgres`

## Аргументы командной строки

- `id-quantity`: Список товаров в формате `id-quantity`, например `3-1`.
- `discountCard=xxxx`: Номер дисконтной карты, длиной 4 цифры, например `1111`.
- `balanceDebitCard=xxxx`: Баланс на дебетовой карте, например `99.9`.
- `saveToFile`: Относительный путь + название файла с расширением, 
предназначенного для сохранения результата, например `/my_result.csv`.

### Добавлено

- `datasource.url`: URL базы данных, например `jdbc:postgresql://localhost:5432/check`.
- `datasource.username`: Имя пользователя для подключения к базе данных, например `postgres`.
- `datasource.password`: Пароль для подключения к базе данных, например `postgres`.
- 
### Убрано

- `pathToFile`: Относительный путь + название файла с расширением,
предназнаеченного для считывания, например `./my_products.csv`.

## Вывод

Результат выполнения сохраняется в файл, указанный в  `saveToFile`. Файл содержит информацию о покупках, с учетом скидок и общей суммы.

## Обработка ошибок

Приложение обрабатывает ошибки, такие как отсутствие товара по указанному ID или неполные командные аргументы. Информация об
ошибках сохраняется также в `result.csv`, если `saveToFile` не передан, иначе ошибки сохраняются в файл, переданный в `saveToFile`.
