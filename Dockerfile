# Використовуємо базовий образ OpenJDK на основі Debian
FROM ubuntu:latest

# Оновлюємо пакетний індекс і встановлюємо Python та venv
#RUN #apt-get update && apt-get install -y python3 python3-venv

# Копіюємо ваш jar-файл і скрипти в образ
ADD target/main-0.0.1-SNAPSHOT.jar /app/app.jar
#ADD scripts /app/scripts

## Встановлюємо Python-залежності у віртуальне середовище
#RUN python3 -m venv /app/scripts/venv \
#    && /bin/bash -c "source /app/scripts/venv/bin/activate" \
#    && /app/scripts/venv/bin/pip install -r /app/scripts/requirements.txt

# Встановлюємо робочу директорію
WORKDIR /app

# Вказуємо команду для запуску Java програми
ENTRYPOINT ["java", "-jar", "app.jar"]