import React from 'react';
import { useNavigate } from 'react-router-dom';
import './welcome.css'

function Welcome() {
    let navigate = useNavigate();

    const handleRedirect = (path) => {
      navigate(path);
    };

  return (
    <div className='welcome-photo'>
      <div className='welcome-info'>
        <h1>Welcome! Who are you?</h1>
        <button onClick={() => handleRedirect('/admin')}>Admin</button>
        <button onClick={() => handleRedirect('/user')}>User</button>
      </div>
    </div>
  );
}

export default Welcome;
