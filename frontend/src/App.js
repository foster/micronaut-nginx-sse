import React, { useEffect, useRef } from 'react';
import './App.css';

function App() {
  const secondHandRef = useRef();
  const minuteHandRef = useRef();
  const hourHandRef = useRef();

  // // seconds
  useEffect(() => {
    const sse = new EventSource('/api/seconds');
    let degrees = 0;
    sse.addEventListener('message', e => {
      const newDegrees = e.data > 0 ? e.data * 6 : 360; // 60 seconds mapped to 360 degrees.
      degrees += (newDegrees - (degrees % 360));

      secondHandRef.current.style.transform = `rotate(${degrees}deg)`;
    });

    // close EventSource if this component is destroyed.
    return () => sse.close();
  }, []);

  // minutes
  useEffect(() => {
    const sse = new EventSource('/api/minutes');
    let degrees = 0;
    sse.addEventListener('message', e => {
      const newDegrees = e.data > 0 ? e.data * 6 : 360; // 60 minutes mapped to 360 degrees.
      degrees += (newDegrees - (degrees % 360));

      minuteHandRef.current.style.transform = `rotate(${degrees}deg)`;
    });

    // close EventSource if this component is destroyed.
    return () => sse.close();
  }, []);

  // hours
  useEffect(() => {
    const sse = new EventSource('/api/hours');
    let degrees = 0;
    sse.addEventListener('message', e => {
      console.log('hours message', e)
      const newDegrees = e.data % 12 > 0 ? e.data * 30 : 360; // 60 minutes mapped to 360 degrees.
      degrees += (newDegrees - (degrees % 360));

      hourHandRef.current.style.transform = `rotate(${degrees}deg)`;
    });

    // close EventSource if this component is destroyed.
    return () => sse.close();
  }, []);
  
  return (
    <div className="App">
      <svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
        <g id="clock">
          <circle cx="50" cy="50" r="48" stroke="black" strokeWidth="2" fill="none" />
          <line ref={secondHandRef} className="secondHand" style={{transformOrigin: '50% 50%', transition: 'transform 1.2s linear'}} x1="50" y1="50" x2="50" y2="4" stroke="red" strokeWidth="2" />
          <line ref={minuteHandRef} className="minuteHand" style={{transformOrigin: '50% 50%', transition: 'transform 1.2s linear'}} x1="50" y1="50" x2="50" y2="12" stroke="black" strokeWidth="2" />
          <line ref={hourHandRef} className="hourHand" style={{transformOrigin: '50% 50%', transition: 'transform 1.2s linear'}} x1="50" y1="50" x2="50" y2="30" stroke="black" strokeWidth="2" />
          <circle cx="50" cy="50" r="3" fill="black" />
        </g>
      </svg>
    </div>
  );
}

export default App;
