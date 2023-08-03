import React, { useEffect, useState } from "react";
import "./App.css";

const App = () => {
  const [text, setText] = useState([]);

  useEffect(() => {
    //Get cars from backend, using nginx proxy
    fetch("http://localhost:8080/api/helloworld")
      .then(data => data.text())
      .then(data =>  setText(data))
  }, []);

  return (
    <div className="App">
      This is the text coming from SpringBoot application
      <div style={{ paddingTop: 20 }} className="data">
         <b>{text}</b>
      </div>
    </div>
  );
};

export default App;
