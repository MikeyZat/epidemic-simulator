# TODO

### Wizualizacja

* plansza 2d ze statycznymi punktami (orkęgami / kołami)
    - zdrowe punkty jako zielone koła
    - chore punkty jako czerwone koła z pulsującymi czerwonymi okręgami
    - martwe punkty jako szare koła

* może jakieś liniowe wykresy przedstawiające stosunek zdrowych / chorych/ martwych
* wykres kołowy dotyczący tego samego co wyżej

### Działanie

* rozmieszczanie statycznie punktów na planszy
* wybierania początkowych chorych punktów
* dla każdego chorego punktu obliczanie z pewnym prawdopodobieństwem <br> ile następnych
  puntków ma zarazić (może być nawet zero)
* dla chorych punktów następnie obliczenie prawdopodobieństwa <br>
  śmierci i wyzdrowienia i zaaplikowanie jednej z nich według prawdopodobieństwa


### Parametry

* **współczynniki**: zarażenia, śmiertelności, wyzdrowienia
* rodzaj stosowanego rozkładu


### Problemy

W jaki sposób chcemy stosować podane parametry wraz z rozkładami?
Czy stają się wtedy parametrami rozkładu?
<br>
Czy może jednak stosujemy same współczynniki, które będą wtedy zwykłym prawdopodobieństwem 
[0-1]? Nie wiem jaki dokładnie był pomysł z tymi parametrami
