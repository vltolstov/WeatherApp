Создание контейнера в папке docker
<code>docker-compose -p weather-app-db up -d</code>

Для деплоя создать
<code>/src/main/resources/application.properties</code>
и заполнить необходимые поля

```
weather.api.token=
app.schema=main
spring.datasource.url=jdbc:postgresql://localhost:5432/weather-app
spring.datasource.username=
spring.datasource.password=
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

```

Для тестов создать
<code>/src/test/resources/application.properties</code>
и заполнить необходимые поля

```

app.schema=test
spring.datasource.url=jdbc:postgresql://localhost:5432/weather-app
spring.datasource.username=
spring.datasource.password=
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

```