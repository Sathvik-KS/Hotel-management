# Hotel Management

A menu-driven, console-based hotel management application written in plain Java (no frameworks, no
database). It lets a user manage rooms, guests, bookings, a small restaurant, and a maintenance
queue, and it persists guests and bookings to text files between runs.

This project was built as a learning exercise to practise core Java: object-oriented design,
collections, custom exceptions, file I/O and the Streams API. It runs with a standard JDK and a
handful of `.java` files.

## Purpose and functionality

- **Rooms** — add rooms, list all rooms sorted by price, show available/occupied rooms, and find the
  cheapest available room(s).
- **Guests** — register guests with an id, name, age and phone number; search by id or name prefix;
  store free-form guest preferences.
- **Bookings** — book an available room for a guest with a date, list bookings, check whether a guest
  has a room, and check rooms out again.
- **Restaurant** — a small menu with a first-in-first-out order queue.
- **Maintenance** — add jobs with a priority (1–9); tasks are handled highest-priority first.
- **Persistence** — guests are stored in `Guests.txt` and bookings in `Customers.txt`; a summary can
  be written to `Hotel-Report.txt`.

## Technologies used

- Java (JDK 17 verified)
- Standard library only: `java.util` (collections + Streams API) and `java.io`
- Custom checked exceptions

## Important classes

| File | Responsibility |
| --- | --- |
| `Main.java` | Console entry point, menu loop and input handling. |
| `Hotel.java` | Core model: rooms, guests, bookings, amenities, maintenance, persistence, report. |
| `Room.java` | A room (number, type, price, availability, assigned guest). `Comparable` by price. |
| `Guest.java` | A guest; extends `Person` and owns a preference map. |
| `Person.java` | Shared name/age/phone data. |
| `Booking.java` | Links a guest to a room with a date and booking id. |
| `Restaurant.java` | Menu (`Map`) and order queue (`Queue`). |
| `*NotFoundException.java`, `InvalidDateException.java`, `MaintenancePriorityException.java` | Checked exceptions for invalid operations. |

## Project structure

```
Hotel-management/
└── Hotel-Practice/          # all source files and runtime data
    ├── Main.java
    ├── Hotel.java
    ├── Room.java
    ├── Guest.java
    ├── Person.java
    ├── Booking.java
    ├── Restaurant.java
    ├── GuestNotFoundException.java
    ├── InvalidDateException.java
    ├── MaintenancePriorityException.java
    ├── RoomNotFoundException.java
    ├── Guests.txt           # generated guest data
    └── Customers.txt        # generated booking data
```

## Prerequisites

- A JDK (Java Development Kit) version 17 or newer, with `java` and `javac` on your `PATH`.

Check with:

```bash
java -version
javac -version
```

## Compile and run

The data files are read and written relative to the working directory, so run the commands from the
`Hotel-Practice` folder:

```bash
cd Hotel-Practice
javac *.java
java Main
```

On Windows (PowerShell) the same commands work unchanged.

## Using the menu

The application prints a menu at start-up. Enter the number of the action you want:

| Choice | Action |
| --- | --- |
| 1 | Add guest |
| 2 | Book a room |
| 3 | Check out a room |
| 4 | Display guests |
| 5 | Show amenities |
| 6 | Show available rooms |
| 7 | Show occupied rooms |
| 8 | Place a restaurant order |
| 9 | Show order queue |
| 10 | Serve next order |
| 11 | Add a maintenance task (priority 1–9) |
| 12 | Show maintenance tasks |
| 13 | Complete next maintenance task |
| 14 | Add a guest preference |
| 15 | Show guest preferences |
| 16 | Show bookings |
| 17 | Show all rooms (sorted by price) |
| 19 | Cheapest available room |
| 20 | Exit |
| 21 | Find guests by name prefix |
| 22 | Two cheapest available rooms |
| 23 | Find guest by id |
| 24 | Show a guest's booking |
| 25 | Generate `Hotel-Report.txt` |
| 26 | Save guests to `Guests.txt` |

Dates are entered as `dd/mm/yyyy` (for example `25/12/2026`).

## Known limitations

- Console-only; there is no GUI and no database.
- Data files use simple, space/pipe-separated text and are resolved relative to the working directory,
  so the app must be started from `Hotel-Practice`.
- Bookings are reloaded by matching the stored guest **name**, and the booking **date is not
  persisted** when bookings are read back (it is shown as `unknown`). Guests with identical names
  cannot be told apart on reload.
- `Customers.txt` shipped in the repository contains accumulated test bookings from earlier runs;
  the application skips lines it cannot match.
- Prices are stored as `double`, not `BigDecimal`.
- All classes are in the default package (no `package` declaration).

## What this project demonstrates

- Object-oriented modelling with inheritance (`Person` → `Guest`) and encapsulation.
- Java Collections: `ArrayList`, `HashSet`, `HashMap`, `LinkedList`/`Queue`, `PriorityQueue`, and
  `Comparable`/`Comparator` sorting.
- Custom checked exceptions and clear error messages.
- File input/output with try-with-resources and buffered readers/writers.
- The Streams API for filtering, mapping, sorting, `min`, and aggregation.
- Structuring a small interactive application into cohesive classes.
