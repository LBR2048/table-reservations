# Table Reservations
Android app used by a restaurant to make table reservations.

## Overview
The first screen displays a list of all the restaurants clients. After selecting a client, the user is presented with a map of all the restaurants tables, where it can be seen which ones are available.
- Available tables are shown in green
- Unavailable tables are shown in red
- The table reserved for the current customer is shown in blue

![Customers screenshot](https://raw.githubusercontent.com/LBR2048/table-reservations/master/images/screen-A.png)
![Tables screenshot](https://raw.githubusercontent.com/LBR2048/table-reservations/master/images/screen-B.png)

## Use cases

![Use case diagram](https://raw.githubusercontent.com/LBR2048/table-reservations/master/images/use-case.png)

## Architecture
The app was created using the MVVM architecture using Android Architecture Componentes

This allows for a more modular code that is easier to understand, modify and test. The diagram below shows how all the classes are connected. The first draft of the diagram was created before writing any piece of code and was updated to reflect its current state.

![Use case diagram](https://raw.githubusercontent.com/LBR2048/table-reservations/master/images/architecture.png)

## Libraries used
- LiveData
- ViewModel
- Room
- WorkManager
- Retrofit
- Mockito
- Espresso
- Stetho
