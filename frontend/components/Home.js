import React from 'react'
import { Link } from "react-router-dom";
import './Home.css'

function Home() {


    return (
      <div className='home'>
        <div id='navbar'>
          <Link to={'/user/login'}>
                  <button>Login</button>
            </Link>

            <Link to={'/user/create'}>
              <button>Sign Up</button>
            </Link>
        </div>
      </div>
    );
  }
  
  export default Home;
  