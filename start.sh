#!/bin/bash

# Встановлення Python віртуального середовища
python3 -m venv scripts/venv

# Активування віртуального середовища
source scripts/venv/bin/activate

# Встановлення залежностей
pip install -r scripts/requirements.txt

# Запуск Java програми
java -jar target/backend-0.0.1-SNAPSHOT.jar
