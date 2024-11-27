import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";

function Signup() {

  const navigate = useNavigate();
  const [data, setdata] = useState({
    userFirstName :  "", 
    userLastName : "", 
    username : "", 
    userPassword : ""
  })

  async function handleSubmit(e)
  {
      e.preventDefault();
      console.log(data);

      try {
        const response = await axios({
          url : "http://localhost:8080/user/create", 
          method : "POST", 
          data : data
        })
        console.log(response);
        if(response)
        {
            navigate('/user/login');
        }

        setdata({
          userFirstName :  "", 
          userLastName : "", 
          username : "", 
          userPassword : ""
        })


      } catch (error) {
          console.log('Error while signup' , e);
      } 

  }


  return (
    <div>
        <form>
          <div className='firstname' >
          <label>Firstname</label>
          <input type='text' placeholder='Enter firstname' onChange={(e) => {setdata({...data, userFirstName : e.target.value})}}/>
          </div>
          <div className='lastname' >
          <label>LastName</label>
          <input type='text' placeholder='Enter lastname' onChange={(e) => {setdata({...data, userLastName : e.target.value})}}/>
          </div>
          <div className='username' >
          <label>username</label>
          <input type='text' placeholder='Enter username' onChange={(e) => {setdata({...data, username : e.target.value})}}/>
          </div>
          <div className='password' >
          <label>password</label>
          <input type='password' placeholder='Enter password' onChange={(e) => {setdata({...data, userPassword : e.target.value})}}/>
          </div>

          <div>
            <button type='submit' onClick={handleSubmit}>Signup</button>
          </div>
        </form>

    </div>
  )
}

export default Signup