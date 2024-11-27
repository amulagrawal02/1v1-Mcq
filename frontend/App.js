import React from 'react';
import { BrowserRouter, json, Navigate, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Signup from './components/Signup';
import Play from './components/Play'
import QuestionCreate from './components/QuestionCreate';
import GameJoined from './components/GameJoined';
import { useNavigate } from "react-router-dom";
function App() {

    const navigate = useNavigate();
    function BeforeLogin({children})
    {

      const jwtToken = localStorage.getItem('jwtToken');
      console.log("inside the protechRoute"+ jwtToken);
      return jwtToken ? <Navigate to = '/' replace/> : children;

    }

    function AfterLogin({children})
    {
      const jwtToken = localStorage.getItem('jwtToken');
      if(!jwtToken)
      {
        console.log('Inside the after login');
        return <Navigate to = '/user/login' /> 
      }
      
      else{
        console.log('else the after login');
        return children;
      }
      
    }

  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/user/login" element={
          // for protect routing if the jwt token exit then login page wouldn't open. 
          <BeforeLogin>
            <Login />
          </BeforeLogin> 
        } />
        <Route path='/user/create' element = {
            // or protect routing if the jwt token exit then signup page wouldn't open. 
            <BeforeLogin>
              <Signup/>
            </BeforeLogin>
        } />
        <Route path='user/question-create' element = {<QuestionCreate/>} />
        <Route path='/play' element = {
           <AfterLogin>
            <Play/>
           </AfterLogin>
              
           }/>
        
        {/* once the room created then user can joined the group */}
        <Route path='/play/gameid/:id' element={
          <AfterLogin>
            <GameJoined/>
          </AfterLogin>
        }></Route>

      </Routes>
  
  );
}

export default App;
