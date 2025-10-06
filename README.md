# Weather-App #

Приложение позволяет каждому пользователю добавить список городов и просматривать их прогноз погоды.
Без регистрации доступна только форма регистрации и входа.
После входа станет доступен поиск по городам и координатам, а также главная страница станет показывать погоду выбранных
городов.

![Image alt](https://github.com/vltolstov/WeatherApp/raw/master/screen.png)

## Запуск локально: ##

В каталоге docker через командную строку запустить для Win:
<code>docker-compose -p weather-app-db up -d</code>
В каталоге docker через командную строку запустить для Linux:
<code>docker compose -p weather-app-db up -d</code>

Для деплоя создать
<code>/src/main/resources/application.properties</code> (при необходимости изменить параметры под свое окружение).
Обязательно заполнить weather.api.key (можно взять тут https://openweathermap.org)

```
weather.api.key=
app.schema=main
spring.datasource.url=jdbc:postgresql://localhost:5432/weather-app
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=none
spring.flyway.locations=classpath:db/migration
spring.flyway.cleanDisabled=true
spring.thymeleaf.prefix=/WEB-INF/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.contentType=text/html; charset=UTF-8
spring.jpa.packages-to-scan=org.weather.app.model
city.list.json.path=classpath:current.city.list.json

```

Для тестов создать
<code>/src/test/resources/application.properties</code>
(при необходимости изменить параметры под свое окружение)

```

weather.api.key=test
app.schema=test
spring.datasource.url=jdbc:postgresql://localhost:5432/weather-app
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=none
spring.flyway.locations=classpath:db/migration
spring.flyway.cleanDisabled=false
spring.thymeleaf.prefix=/WEB-INF/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.contentType=text/html; charset=UTF-8
spring.jpa.packages-to-scan=org.weather.app.model
city.list.json.path=classpath:current.city.list.json

```

### Деплой на внешнем сервере ###

Скомпилировать артефакт с правильными настройками БД.
Скопировать docker-compose.yaml на сервер и создать контейнер (при необходимости поменять настройки контейнера)
Залить артефакт в webapps сервера. (инициализации может занимать несколько минут)