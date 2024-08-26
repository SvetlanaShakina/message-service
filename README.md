# Message Service

## Описание

**Message Service** — это микросервис на основе Spring Boot, предназначенный для отправки текстовых сообщений. 
В текущей версии поддерживается отправка email-сообщений с возможностью отслеживания их статусов. 
Сервис поддерживает асинхронную отправку сообщений и ведет историю изменения статусов для каждого сообщения.

## Стек технологий

- **Java 11**
- **Spring Boot 2.7.5**
- **PostgreSQL**
- **Liquibase**
- **Maven**
- **JavaMailSender** 
- **Hibernate/JPA**

## Миграции базы данных
Liquibase используется для управления миграциями базы данных. 
Все изменения базы данных описаны в файле src/main/resources/db/changelog/db.changelog-master.yaml.

Миграции автоматически применяются при запуске приложения.

## Установка и настройка
   
1 **Настройте базу данных PostgreSQL:**

Создайте базу данных для вашего проекта:

`   CREATE DATABASE message_service;
`

2 **Создайте пользователя и назначьте ему права**

`CREATE USER yourusername WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE message_service TO yourusername;`

3 **В файле src/main/resources/application.properties укажите настройки для подключения к базе данных и почтовому серверу:**

`    Замените username, password, test-mail@gmail.com и другие параметры на реальные значения
`

4 **Запуск приложения**

Вы можете запустить приложение через Maven:

`mvn spring-boot:run
`

Или собрать и запустить jar-файл:

`mvn clean package
java -jar target/message-service-0.0.1-SNAPSHOT.jar`

5 **Использование приложения**

5.1 Создание сообщения
Отправьте POST-запрос на /api/messages с телом JSON, содержащим информацию о сообщении:
Пример запроса:

`curl -X POST http://localhost:8080/api/messages \
-H "Content-Type: application/json" \
-d "{\"content\":\"Hello, World!\", \"recipient\":\"recipient@example.com\"}"`

5.2 Получение статуса сообщения
Отправьте GET-запрос на /api/messages/{id}/status, чтобы получить статус сообщения по его идентификатору:

`curl -X GET http://localhost:8080/api/messages/{id}/status`

5.3 Получение истории статусов сообщения
Отправьте GET-запрос на /api/messages/{id}/history, чтобы получить историю изменения статусов сообщения:

`curl -X GET http://localhost:8080/api/messages/{id}/history`

6 **Тестирование приложения**

Вы можете запустить юнит-тесты с помощью Maven:

`mvn test`
