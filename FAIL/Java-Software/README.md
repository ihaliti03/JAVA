# FLIGHT MANAGEMENT SYSTEM

## Overview

* **Name:** FLIGHT MANAGEMENT SYSTEM
* **Description:**
A flight airline reservation application can serve several purposes for both airlines and travelers, including:
- A comprehensive airline management system offering seamless flight search and visa check functionalities.
- Booking flights: A flight airline reservation application allows travelers to book flights easily and conveniently. Travelers can search for available flights based on their preferred dates, times, and destinations, view flight schedules, and choose seats.
- Flight information: The application can provide real-time flight information, such as departure and arrival times,gate numbers, and delays. This can help travelers plan their travel and avoid any unexpected delays.
* **Target Users:** Travelers and travel agencies

## Key Features

* Real-time flight database
* Automated visa check system
* User-friendly interface
* Secure and reliable

## Technology Stack

* Backend: Java (OpenJDK 21), Spring Boot, MySQL
* Frontend: React, CSS

## Getting Started

**Prerequisites:**

* OpenJDK 21
* MySQL Community Server
* XAMPP

**Project Setup:**

1. Clone the repository: `git clone https://github.com/mz29986/Java-Software.git`
2. Configure database settings in `application.properties`
3. Build and run:
   - Backend: Execute `FlightreservationApplication.java`
   - Frontend: Run `npm start`

### Stopping the Applications

If needed, stop the running applications:
   - For the backend, terminate the execution of `FlightreservationApplication.java`.
   - For the frontend, close the terminal or command prompt where `npm start` is running.

## Development Details

* **Unit Testing:** JUnit

## Testing

The project includes a comprehensive set of unit tests to ensure the correctness and reliability of the codebase. The tests cover various aspects of the system, including the core business logic, data models, and API endpoints.

### Running Tests

To run the unit tests, follow these steps:

1. Open the project in your IDE.
2. Locate the test classes within the `src/test/java` directory.
3. Right-click on the test class or individual test methods.
4. Choose the "Run" option.

### Test Coverage

The unit tests cover key functionalities such as:

- **AbstractFlightTest:** Testing getters and flight type determination.
- **AirlineTest:** Validating getters and setters for the Airline class.
- **AirportTest:** Verifying getters for the Airport class.
- **DomesticFlightTest:** Comprehensive testing of DomesticFlight class, covering flight type, flight number, departure/arrival airports, capacity, and the number of passengers.


## API Endpoints - Representation for Airlines

* **Get All Airlines:**
  - **Endpoint:** `/airlines`
  - **Method:** GET
  - **Response Type:** 
[
    {
        "id": 1,
        "name": "Wizz Air",
        "numOfFlights": 100
    },
    {
        "id": 2,
        "name": "Turkish Airlines",
        "numOfFlights": 55
    },
    {
        "id": 3,
        "name": "Swiss Ariline",
        "numOfFlights": 80
    },
    {
        "id": 4,
        "name": "British Air",
        "numOfFlights": 20
    }
]

* **Get Airline by ID:**
  - **Endpoint:** `/airlines/2`
  - **Method:** GET
   **Response Type:** {
        "id": 2,
        "name": "Turkish Airlines",
        "numOfFlights": 55
    }

* **Create Airline:**
  - **Endpoint:** `/airlines`
  - **Method:** POST
  - **Body:** {
        "id": 5,
        "name": "Croatia Airlines",
        "numOfFlights": 65
      }

* **Update Airline:**
  - **Endpoint:** `/airlines/3`
  - **Method:** PUT
  - **Body:** {
        "name": "Swiss Ariline",
        "numOfFlights": 100
      }

* **Delete Airline:**
  - **Endpoint:** `/airlines/{id}`
  - **Method:** DELETE

* **Get Number of Airlines:**
  **Endpoint:** `/statistics/numAirlines`
  **Method:** GET
  **Response Type:** 5

* **Get Number of Domestic Flights:**
    **Endpoint:** `/statistics/numDomesticFlights`
    **Method:** GET
    **Response Type:** 2

* **Get Number of International Flights:**
    **Endpoint:** `/statistics/numInternationalFlights`
    **Method:** GET
    **Response Type:** 3

* **Get Average Passengers Per Flight:**
    **Endpoint:** `/statistics/averagePassengersPerFlight`
    **Method:** GET
    **Response Type:** 89.4

* **Get Busiest Airline:**
    **Endpoint:** `/statistics/busiestAirline`
    **Method:** GET
    **Response Type:** {
        "id": 1,
        "name": "Wizz Air",
        "numOfFlights": 100
    }
    
**Happy Flight! ✈️**