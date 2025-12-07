import React, { useState, useEffect } from 'react';
import './AirlineAdmin.css';

function Admin() {
  const [airlines, setAirlines] = useState([]);
  const [newAirline, setNewAirline] = useState({ name: '', numOfFlights: 0 });
  const [updateInfo, setUpdateInfo] = useState({});

  useEffect(() => {
    // Fetch all airlines on component mount
    fetch('http://localhost:8080/airlines')
      .then((response) => response.json())
      .then((data) => setAirlines(data))
      .catch((error) => console.error('Error fetching airlines:', error));
  }, []);

  const handleAddAirline = () => {
    // Simulate adding a new airline (replace with actual API call)
    fetch('http://localhost:8080/airlines', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newAirline),
    })
      .then((response) => response.json())
      .then((createdAirline) => setAirlines([...airlines, createdAirline]))
      .catch((error) => console.error('Error creating airline:', error));
  };

  const handleUpdateAirline = (id) => {
    // Simulate updating an airline (replace with actual API call)
    fetch(`http://localhost:8080/airlines/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updateInfo[id]),
    })
      .then((response) => response.json())
      .then((updatedAirline) =>
        setAirlines(airlines.map((airline) => (airline.id === id ? updatedAirline : airline)))
      )
      .catch((error) => console.error('Error updating airline:', error));
  };

  const handleDeleteAirline = (id) => {
    // Simulate deleting an airline (replace with actual API call)
    fetch(`http://localhost:8080/airlines/${id}`, {
      method: 'DELETE',
    })
      .then(() => setAirlines(airlines.filter((airline) => airline.id !== id)))
      .catch((error) => console.error('Error deleting airline:', error));
  };

  const handleUpdateInputChange = (id, field, value) => {
    setUpdateInfo({
      ...updateInfo,
      [id]: {
        ...updateInfo[id],
        [field]: value,
      },
    });
  };

  return (
    <div className="airline-admin-container">
      <h2>Airlines</h2>
      <ul>
        {airlines.map((airline) => (
          <li key={airline.id}>
            <span>
              {airline.name} - {airline.numOfFlights} flights
            </span>
            <div>
              <input
                type="text"
                placeholder="New Airline Name"
                value={updateInfo[airline.id]?.name || ''}
                onChange={(e) => handleUpdateInputChange(airline.id, 'name', e.target.value)}
              />
              <input
                type="number"
                placeholder="New Number of Flights"
                value={updateInfo[airline.id]?.numOfFlights || ''}
                onChange={(e) =>
                  handleUpdateInputChange(airline.id, 'numOfFlights', e.target.value)
                }
              />
              <button onClick={() => handleUpdateAirline(airline.id)}>Update</button>
              <button onClick={() => handleDeleteAirline(airline.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>

      <h3>Create New Airline</h3>
      <div>
        <input
          type="text"
          placeholder="Airline Name"
          value={newAirline.name}
          onChange={(e) => setNewAirline({ ...newAirline, name: e.target.value })}
        />
        <input
          type="number"
          placeholder="Number of Flights"
          value={newAirline.numOfFlights}
          onChange={(e) => setNewAirline({ ...newAirline, numOfFlights: e.target.value })}
        />
        <button onClick={handleAddAirline}>Create</button>
      </div>
      <footer className='footer-admin'>
        <p>Flight Reservation © 2024. All Rights Reserved.</p>
      </footer>
    </div>
  );
}

export default Admin;