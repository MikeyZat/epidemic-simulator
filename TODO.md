# TODO

### Wizualizacja

* plansza 2d ze statycznymi punktami (orkęgami / kołami)
    - zdrowe punkty jako zielone koła
    - chore punkty jako czerwone koła z pulsującymi czerwonymi okręgami
    - martwe punkty jako szare koła

* może jakieś liniowe wykresy przedstawiające stosunek zdrowych / chorych/ martwych. Ja bym podzielił na dwa wykresy - pierwszy pokazuje całkowitą liczbę zdrowych / martwych/ chorych, a drugi to samo tylko że danego dnia
* wykres kołowy dotyczący tego samego co wyżej (tutaj mógłby być jeden wykres dotyczący sumarycznych wartości)

### Działanie

* rozmieszczanie statycznie punktów na planszy
* wybierania początkowych chorych punktów
* dla każdego chorego punktu obliczanie z pewnym prawdopodobieństwem <br> ile następnych
  puntków ma zarazić (może być nawet zero)
* dla chorych punktów następnie obliczenie prawdopodobieństwa <br>
  śmierci i wyzdrowienia i zaaplikowanie jednej z nich według prawdopodobieństwa


### Parametry

* **współczynniki**:
    - zarażenia (liczba z przedziału (0.1 - 10.0), np. dla covid19 to około 3)
    - śmiertelności (w %)
    - średni czas trwania choroby
    - liczba początkowych zarażonych (ew. możemy zawsze zaczynać np. z 10 zarażonymi).

* **możliwość wybrania jednego z wirusów, z wcześniej przygotowanymi paramterami**



### Problemy

*W jaki sposób chcemy stosować podane parametry wraz z rozkładami?*\
Wg mnie z góry ustalamy rozkład\
\
*Czy stają się wtedy parametrami rozkładu?*\
Tak\
\
*Czy może jednak stosujemy same współczynniki, które będą wtedy zwykłym prawdopodobieństwem 
[0-1]? Nie wiem jaki dokładnie był pomysł z tymi parametrami*\
\
Ja mam taką wizję:
każda iteracja to jeden dzień
~~* Zgony / wyzdrowienia:
    - prawdopodobieństwo na to, że osoba danego dnia wyzdrowieje/umrze wynosi 1/{średni czas trwania choroby}, próbkując z tym parametrem dostajemy liczbę osób, które albo wyzdrowieją albo umrą
    - z tych osób z prawdopodobieństwem {współczynnik śmiertelności}/100 próbkujemy ilość osób które zmarły. Reszta wyzdrowiała.~~ **Done**\
~~* Zarażanie:
    - dana osoba w ciągu jednego dnia zaraża kogoś z prawdopodobieństwem {współczynnik zarażenia} / {średni czas trwania choroby}, próbkujemy to z osób aktualnie zarażonych i mamy liczbę nowych zarażonych~~

~~Każdego dnia od całej populacji odejmujemy liczbę zgonów + wyzdrowiałych - tylko w ten sposób będzie można będzie zaobserwować zakończenie epidemii~~

~~Próbkowanie można zrobić jakimś scalowskim Monte Carlo, dzięki temu, że będziemy próbkować tylko na liczbie zarażonych to powinno działać dość szybko~~
**Done**
