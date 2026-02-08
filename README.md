# Week 4 – Jetpack Compose Navigation & MVVM

## Navigointi Jetpack Composessa
Navigointi tarkoittaa siirtymistä Composable-näkymien välillä reittien avulla.  
NavController hoitaa siirtymisen ja NavHost määrittelee reitit ja niihin liittyvät näkymät.

## Navigaatiorakenne
Sovelluksessa on HomeScreen, CalendarScreen ja SettingsScreen.  
Home ↔ Calendar tapahtuu TopAppBarin painikkeella.  
Settings avautuu omasta painikkeestaan.

## MVVM + navigointi
Sovellus käyttää yhtä TaskViewModelia, joka jaetaan HomeScreenin ja CalendarScreenin välillä.  
Molemmat ruudut lukevat samaa tasks-tilaa collectAsState()-kutsulla.

## CalendarScreen
Tehtävät ryhmitellään päivämäärän mukaan:

```kotlin
val grouped = tasks.groupBy { it.dueDate }

## Miten AlertDialog hoitaa addTask ja editTask

#addTask:  
- Avautuu “Add task” ‑painikkeesta  
- Käyttäjä syöttää otsikon ja kuvauksen  
- Tallennus kutsuu `viewModel.addTask()`  

#editTask:  
- Avautuu, kun käyttäjä klikkaa olemassa olevaa tehtävää  
- Kentät esitäytetään valitun tehtävän tiedoilla  
- Tallennus kutsuu `viewModel.updateTask()`  
- Poisto kutsuu `viewModel.removeTask()`


