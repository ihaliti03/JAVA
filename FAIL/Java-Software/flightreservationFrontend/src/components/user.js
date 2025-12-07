import React, { useState } from 'react';
import './user.css';

function User() {
  const [airlines, setAirlines] = useState([]);
  const [airports, setAirports] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [showAirlines, setShowAirlines] = useState(false);
  const [showAirports, setShowAirports] = useState(false);
  const [showFlightsMenu, setShowFlightsMenu] = useState(false);
  const [flights, setFlights] = useState([]);
  const [showFlights, setShowFlights] = useState(false);

  const fetchAirlines = async () => {
    setIsLoading(true);
    setError('');
    setShowAirports(false);
    setShowFlights(false);
    try {
      const response = await fetch('http://localhost:8080/airlines');
      if (!response.ok) {
        throw new Error(`HTTP error: ${response.status}`);
      }
      const data = await response.json();
      setAirlines(data);
      setShowAirlines(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchAirports = async () => {
    setIsLoading(true);
    setError('');
    setShowAirlines(false);
    setShowFlights(false);
    try {
      const response = await fetch('http://localhost:8080/airports');
      if (!response.ok) {
        throw new Error(`HTTP error: ${response.status}`);
      }
      const data = await response.json();
      setAirports(data);
      setShowAirports(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchFlights = async (type) => {
    setIsLoading(true);
    setError('');
    setShowAirlines(false);
    setShowAirports(false);
    try {
      const response = await fetch(`http://localhost:8080/${type}`);
      if (!response.ok) {
        throw new Error(`HTTP error: ${response.status}`);
      }
      const data = await response.json();
      setFlights(data);
      setShowFlights(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container">
      <nav className="navbar">
        <div className="logo">Flight Reservation</div>
        <div className="menu">
          <button onClick={fetchAirlines} className="nav-link">Airlines</button>
          <button onClick={fetchAirports} className="nav-link">Airports</button>
          <button className="nav-link" onClick={() => setShowFlightsMenu(!showFlightsMenu)}>Flights</button>
          {showFlightsMenu && (
            <div className="dropdown-menu">
              <button className="dropdown-item" onClick={() => fetchFlights('domestic-flights')}>Domestic</button>
              <button className="dropdown-item" onClick={() => fetchFlights('international-flights')}>International</button>
            </div>
          )}
        </div>
      </nav>
      <header className="hero">
        <h1>Explore the World</h1>
      </header>
      {showAirlines && (
        <div className="modal">
          <div className="modal-content">
            <span className="close" onClick={() => setShowAirlines(false)}>&times;</span>
            <h2>Airlines that we offer</h2>
            {isLoading && <p>Loading airlines...</p>}
            {error && <p>Error fetching airlines: {error}</p>}
            <div className="cards">
              {airlines.map((airline) => (
                <div className="card" key={airline.id}>
                  <h3>{airline.name}</h3>
                  <p>Number of Flights: {airline.numOfFlights}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
      {showAirports && (
        <div className="modal">
          <div className="modal-content">
            <span className="close" onClick={() => setShowAirports(false)}>&times;</span>
            <h2>Airports that we offer</h2>
            {isLoading && <p>Loading airports...</p>}
            {error && <p>Error fetching airports: {error}</p>}
            <div className="cards">
              {airports.map((airport) => (
                <div className="card" key={airport.id}>
                  <h3>{airport.name}</h3>
                  <p>{airport.city}, {airport.country}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
      {showFlights && (
        <div className="modal">
          <div className="modal-content">
            <span className="close" onClick={() => setShowFlights(false)}>&times;</span>
            <h2>Flights</h2>
            {isLoading && <p>Loading flights...</p>}
            {error && <p>Error fetching flights: {error}</p>}
            <div className="cards">
            {flights.map((flight, index) => (
              <div className="card" key={index}>
                <h3>{flight.airline.name}</h3>
                <p>Departure: {flight.departureAirport.name}</p>
                <p>Arrival: {flight.arrivalAirport.name}</p>
                <p>Flight Number: {flight.flightNumber}</p>
                <p>Departure Time: {flight.departureTime}</p>
                <p>Arrival Time: {flight.arrivalTime}</p>
              </div>
            ))}
            </div>
          </div>
        </div>
      )}
      <footer>
        <p>Flight Reservation © 2024. All Rights Reserved.</p>
      </footer>
    </div>
  );
}

export default User;
