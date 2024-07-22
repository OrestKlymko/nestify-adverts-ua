# -*- coding: utf-8 -*-
import cloudscraper
import g4f
import g4f.Provider
import sys


def generate_query_from_text(text):
    query_template = """
    Дано текст: {text} Твоє завдання у відповідь на цей запит сформувати квері лінк, витягнувши інформацію з тексту, який є вище. Лінк повинен містити лише ті квері параметри, які я тобі дам і більше ніяких додаткових.Ось список: priceFrom. Приклад: 0, priceTo, rooms - Кількість кімнат, які потрібно відфільтрувати, districts - Назви районів, які потрібно відфільтрувати. Якщо районів не вказано, але вказані наприклад побажання щодо харакетиристики місця по типу тихий район, центр то врахуй це і добав в запит найбільш підходящі райони в даному місті. Тут повинно бути вказано конкретні назви районів через кому, якщо їх декілька. Перетвори слова біля, поблизу і так далі на реальні підходящі райони. Якщо таких немає - ігноруй тоді цю частину. Уважно зверни увагу і уважно проаналізуй текст і не використовуй спеціальних символів по типу +, address -Назви адреси, які потрібно відфільтрувати, city- Місто якщо не вказано то по дефолту Братислава, typeOwner - Тип продавця OWNER або REALTY, withPets - Дозволено з тваринами YES, NO, UNDEFINED, withKids - Дозволено з дітьми - YES, NO, UNDEFINED, typeRealty - Тип нерухомості - FLAT (Byt), HOUSE (Dom), HALF_FLAT (Garsónka), DACHA (Záhradný дом), ROOM (Izba), COMMERCIAL_REALTY (Komerčná nehnuteľnosť), feature – Зручності BALCONY (Балкон),ELEVATOR (Ліфт),PARKING (Паркування),GARAGE (Гараж), BASEMENT (Підвал),WITH_REPAIR (З ремонтом),WITHOUT_REPAIR (Без ремонту), WITH_FURNITURE (З меблями),WITHOUT_FURNITURE (Без меблів), INTERNET (Інтернет),TELEVISION (Телевізор),AIR_CONDITIONER (Кондиціонер), REFRIGERATOR (Холодильник),WASHING_MACHINE (Пральна машина),DISHWASHER (Посудомийна машина) Може бути декілька через кому. У відповіді є лише квері параметри і немає жодних пояснень і немає будь якого додаткового тексту. Запит не може містити кирилицю, просто лише квері лінк без будь якого тексту на початку і в кінці і без твоїх коментарів по типу Here is the query link:!!!!!!!.
    """

    scraper = cloudscraper.create_scraper()

    response = g4f.ChatCompletion.create(
        model=g4f.models.gpt_35_turbo,
        messages=[{"role": "user", "content": query_template.format(text=text)}],
    )

    return response

if __name__ == "__main__":
    text = sys.argv[1]
    query = generate_query_from_text(text)
    print(query.replace("Here is the query link:", ""))  # Додано для виведення результату в консоль
