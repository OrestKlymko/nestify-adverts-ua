# Базовий образ
FROM openjdk:17-jdk-slim

# Встановлення Python та необхідних пакетів
RUN apt-get update && apt-get install -y \
    python3 \
    python3-venv \
    python3-pip

# Створення робочої директорії
WORKDIR /app

# Додавання файлів додатку
ADD target/main-0.0.1-SNAPSHOT.jar /app/app.jar
ADD scripts /app/scripts

# Створення віртуального середовища та встановлення залежностей
RUN python3 -m venv /app/scripts/venv \
    && /app/scripts/venv/bin/pip install -r /app/scripts/requirements.txt

# Запуск додатку
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
