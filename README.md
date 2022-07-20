# my-flashcards-app
 Aplikacja na Androida do fiszek na przedmiot "Wzorce projektowe".

Możliwości:
- automatyczne dodanie fiszek do bazy danych (mysqlite) na podstawie listy angielskich słówek. Automatycznie do każdej fiszki dodane jest również tłumaczenie danego słowa oraz przykładowe zdania na podstawie internetowego słownika Cambridge (technologia jsoup)
- dodawanie własnych fiszek
- edycja fiszek
- nauka fiszek. W zalezności od odpowiedzi użytkownika fiszka pokaże się znowu za parę dni
- fiszka ma 5 stanów. Pokazuje się użytkownikowi coraz później, aż za piątym razem zostaje uznana za nauczoną i nie jest już pokazywana.
- możliwość zresetowania swojej bazy danych
- fiszki do nauki zmieniają się codziennie


Wykorzystane wzorce projektowe:
1. Fasada - ukrycie przed klientem działań związanych z bazą danych
2. Singleton - użyty wraz z fasadą (tylko jedna jej instancja)
3. Adapter - adaptuje bibliotekę Jsoup na potrzeby kienta
4. Metoda wytwórcza - by tworzyć obiekty "zdań" (kontekstem jest fiszka)
5. Iterator - iteruje po liście fiszek
6. Strategia - wybór czy zapisać dzisiejsze fiszki do pliku czy je pobrać z niego
7. Polecenie - wysłanie polecenia "zatwierdzenia" edycji fiszki
8. Łańcuch zobowiązań - przechodzi po kolejnych krokach sprawdzeń przy zakończeniu edycji fiszki

![alt text](https://github.com/pawel1999f/my-flashcards-app/blob/main/screenshots/main_menu.PNG?raw=true)
![alt text](https://github.com/pawel1999f/my-flashcards-app/blob/main/screenshots/hidden.PNG?raw=true)
![alt text](https://github.com/pawel1999f/my-flashcards-app/blob/main/screenshots/shown.PNG?raw=true)
![alt text](https://github.com/pawel1999f/my-flashcards-app/blob/main/screenshots/adding.PNG?raw=true)
