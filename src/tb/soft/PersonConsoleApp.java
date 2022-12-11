package tb.soft;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Program: Aplikacja działająca w oknie konsoli, która umożliwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java
 *          
 *   Autor: Paweł Rogaliński
 *    Data: październik 2018 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja konsolowa\n" + 
	        "Autor: Paweł Rogaliński\n" +
			"Data:  październik 2018 r.\n" +
			"Update: werja wzbogacona o kolekcje\n" +
			"Osoba odpowiedzialna za update: Szymon Wichrowski";

	private static final String MENU = 
			"    M E N U   G Ł Ó W N E  \n" +
			"1) Podaj dane nowej osoby \n" +
			"2) Usuń dane aktualnej osoby        \n" +
			"3) Modyfikuj dane aktualnej osoby   \n" +
			"4) Wczytaj dane z pliku   \n" +
			"5) Zapisz dane do pliku   \n" +
			"6) Wyświetl wszystkie dane  \n" +
			"7) Metody porównawcze \n"	+
			"8) Pokaż dane aktualnej osoby\n" +
			"0) Zakończ program        \n";
	
	private static final String CHANGE_MENU = 
			"   Co zmienić?     \n" + 
	        "1) Imię           \n" +
			"2) Nazwisko       \n" +
	        "3) Rok urodzenia  \n" +
			"4) Stanowisko     \n" +
	        "0) Powrót do menu głównego\n";

	private static final String COMPARISON_MENU =
			"   Którą metodę porównanwczą wykonać?  \n" +
			"1) equals \n" +
			"2) hashCode \n" +
			"3) equals z implementacją \n" +
			"4) hashCode z implementacją \n";
	
	/**
	 * ConsoleUserDialog to pomocnicza klasa zawierająca zestaw
	 * prostych metod do realizacji dialogu z użytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	private static final ConsoleUserDialog UI = new JOptionUserDialog();
	
	
	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie głównej pętli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;
	
	
	/*
	 *  Metoda runMainLoop wykonuje główną pętlę aplikacji.
	 *  UWAGA: Ta metoda zawiera nieskończoną pętlę,
	 *         w której program się zatrzymuje aż do zakończenia
	 *         działania za pomocą metody System.exit(0); 
	 */
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);

		while (true) {
			UI.clearConsole();
			//showCurrentPerson();

			try {
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1: {
					// utworzenie nowej osoby
					currentPerson = createNewPerson();
					//sprawdzam czy nowa osoba nie jest nullem
					if (currentPerson == null) throw new PersonException("Żadna osoba nie została utworzona.");
					//dodaje utworzona osobe do kolekcji
					addPerson(currentPerson);
					showCurrentPerson();
				}
					break;
				case 2: {
					// usunięcie danych aktualnej osoby.
					if (currentPerson == null) throw new PersonException("Żadna osoba nie została utworzona.");
					else {
						Collections.people_arrayList.remove(currentPerson);
						Collections.people_linkedList.remove(currentPerson);
						for (int i = 1; i <= Collections.people_hashMap.size(); i++) {
							if(Objects.equals(Collections.people_hashMap.get(i), currentPerson)) {
								Collections.people_hashMap.remove(i);
							}
						}
						for (int j = 1; j <= Collections.people_treeMap.size(); j++) {
							if(Objects.equals(Collections.people_treeMap.get(j), currentPerson)) {
								Collections.people_treeMap.remove(j);
							}
						}
						Collections.people_hashSet.remove(currentPerson);
						Collections.people_treeSet.remove(currentPerson);
						currentPerson = null;
						UI.printInfoMessage("Dane aktualnej osoby zostały usunięte");
					}
				}
					break;
				case 3: {
					// zmiana danych dla aktualnej osoby
					if (currentPerson == null) throw new PersonException("Żadna osoba nie została utworzona.");
					else {
						changePersonData(currentPerson);//zmiana danych aktualnej osoby; wyswietla dane osoby po i przed
					}
				}
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					Person[] person;
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					person = Person.readFromFile(file_name);
					for (int i = 0; !(person[i] == null) ; i++){
						addPerson(person[i]);
					}
				}
					break;
				case 5: {
					// zapis danych aktualnej osoby do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					Person.printToFile(file_name, Collections.people_arrayList);
					UI.printInfoMessage("Dane zostały zapisane do pliku " + file_name);
				}
					break;
				case 6:	 { // wypisanie wszystkich danych
					switch(UI.enterInt("Wybierz kolekcję, której elementy mają zostać wypisane.\n" +
							    "1) Arraylista\n" +
							    "2) Linkedlista\n" +
							    "3) HashMapa\n" +
							    "4) TreeMapa\n" +
							    "5) HashSet\n" +
							    "6) TreeSet\n" +
							    "Kolekcja nr?:")){
						case 1: { //arraylista
							if (Collections.people_arrayList.isEmpty()) {
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (Person person : Collections.people_arrayList) {
								showPerson(person);
							}
						}
						break;
						case 2: { //linkedlista
							if (Collections.people_linkedList.isEmpty()) {
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (Person person : Collections.people_linkedList) {
								showPerson(person);
							}
						}
						break;
						case 3: {//hashMap
							if (Collections.people_hashMap.isEmpty()) {
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (int i = 1; i <= Collections.people_hashMap.size(); i++) {
								showPerson(Collections.people_hashMap.get(i));
							}
						}
						break;
						case 4: { //treeMap
							if (Collections.people_treeMap.isEmpty()) {
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (int i = 1; i <= Collections.people_treeMap.size(); i++) {
								showPerson(Collections.people_treeMap.get(i));
							}
						}
						break;
						case 5: {//hashSet
							if (Collections.people_hashSet.isEmpty()) {
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (Person person : Collections.people_hashSet) {
								showPerson(person);
							}
						}
						break;
						case 6: { //treeSet
							if (Collections.people_treeSet.isEmpty()){
								throw new PersonException("Wybrana kolekcja jest pusta.");
							}
							for (Person person : Collections.people_treeSet) {
								showPerson(person);
							}
						}
						break;
					}
				}
				    break;
				case 7: {
					UI.printMessage("Wybierz indeksy osób do porównania!");
					int first_person, second_person;
					first_person = UI.enterInt("Indeks pierwszej osoby: " );
					second_person = UI.enterInt("Indeks drugiej osoby: ");
					switch (UI.enterInt(COMPARISON_MENU)) {
						case 1: { //equals
							UI.printMessage("Porównanie wersja equals: " +
									comparisonResult(Collections.people_hashMap.get(first_person)
											.equals(Collections.people_hashMap.get(second_person))));
						}
							break;
						case 2: { //hashCode
							UI.printMessage("Porównanie wersja hashCode: " +
									comparisonResult(Collections.people_hashMap.get(first_person).hashCode()
											      == Collections.people_hashMap.get(second_person).hashCode()));
						}
							break;
						case 3: {
							ComparisonMethods person1 = new ComparisonMethods(Collections.people_hashMap.get(first_person));
							ComparisonMethods person2 = new ComparisonMethods(Collections.people_hashMap.get(second_person));
							UI.printMessage("Porównanie wersja equals z implementacją: " +
									comparisonResult(person1.equals(person2)));
						}
							break;
						case 4: {
							ComparisonMethods person1 = new ComparisonMethods(Collections.people_hashMap.get(first_person));
							ComparisonMethods person2 = new ComparisonMethods(Collections.people_hashMap.get(second_person));
							UI.printMessage("Porównanie wersja hashCode z implementacją: " +
									comparisonResult(person1.hashCode() == person2.hashCode()));
						}
							break;
					}
				}
					break;
				case 8: {
					showCurrentPerson();
				}
				break;
				case 0:
					// zakończenie działania programu
					UI.printInfoMessage("\nProgram zakończył działanie!");
					System.exit(0);
				} // koniec instrukcji switch
		} catch (PersonException e) {
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		} // koniec pętli while
	}
	
	
	/*
	 *  Metoda wyświetla w oknie konsoli dane aktualnej osoby 
	 *  pamiętanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		showPerson(currentPerson);
	} 

	
	/* 
	 * Metoda wyświetla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			sb.append("Aktualna osoba: \n")
			  .append("      Imię: ").append(person.getFirstName()).append("\n")
			  .append("  Nazwisko: ").append(person.getLastName()).append("\n")
			  .append("   Rok ur.: ").append(person.getBirthYear()).append("\n")
			  .append("Stanowisko: ").append(person.getJob()).append("\n");
		} else
			sb.append( "Brak danych osoby\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Metoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wypełnia atrybuty wczytanymi danymi.
	 * Walidacja poprawności danych odbywa się w konstruktorze i setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imię: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie wartości wszystkich atrybutów.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
			// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczytać nowe dane dla poszczególnych atrybutów 
	 * obiekty person i zmienia je poprzez wywołanie odpowiednich setterów z klasy Person.
	 * Walidacja poprawności wczytanych danych odbywa się w setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imię: "));
					break;
				case 2:
					person.setLastName(UI.enterString("Podaj nazwisko: "));
					break;
				case 3:
					person.setBirthYear(UI.enterString("Podaj rok ur.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
					person.setJob(UI.enterString("Podaj stanowisko: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
				showPerson(person);
			} catch (PersonException e) {     
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}

	static void addPerson(Person person){ //dodaje dane do wszystkich kolekcji z klasy Collections
		Collections.people_arrayList.add(person);
		Collections.people_linkedList.add(person);
		Collections.people_hashMap.put(Collections.people_hashMap.size()+1, person);
		Collections.people_treeMap.put(Collections.people_hashMap.size()+1, person);
		Collections.people_hashSet.add(person);
		Collections.people_treeSet = Collections.people_hashSet;
	}

	static String comparisonResult(boolean result) { //wyswietla informacje z w zależności od rezultatu porównania

		if(result == true) return "Są równe.";

		else if (result == false) return "Nie są równe.";

		else return "Nastąpił błąd!";
	}

}  // koniec klasy PersonConsoleApp
