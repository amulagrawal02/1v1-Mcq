import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';

function Login() {
  const navigate = useNavigate();
  const [data, setData] = useState({
    username: '',
    password: ''
  });

  async function handleLogin(e) {
    e.preventDefault();
    console.log('login button called');
    console.log(data);

    try {
      const response = await axios({
        url: 'http://localhost:8080/user/login',
        method: 'POST',
        data: data
      });
      console.log(response.data.jwtToken);
      if (response.data.jwtToken) {
        localStorage.setItem('jwtToken', response.data.jwtToken);
        localStorage.setItem('username' , response.data.username)
        navigate('/');
      }

      setData({
        username: '',
        password: ''
      });
    } catch (error) {
      console.log('Error while login', error);
    }
  }

  return (
    <Container className="d-flex flex-column justify-content-center align-items-center min-vh-100">
      <h1 className="mb-4 text-light">Login Page</h1>
      <Form style={{ width: '100%', maxWidth: '400px' }} onSubmit={handleLogin}>
        <Form.Group className="mb-3" controlId="formUsername">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter your username"
            value={data.username}
            onChange={(e) => setData({ ...data, username: e.target.value })}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Enter your password"
            value={data.password}
            onChange={(e) => setData({ ...data, password: e.target.value })}
          />
        </Form.Group>

        <Button variant="primary" type="submit" className="w-100">
          Login
        </Button>
      </Form>
      <div className="mt-3 text-light">
        <span>Don't have an account? </span>
        <a href="/user/create" className="text-primary">
          Sign Up
        </a>
      </div>
    </Container>
  );
}

export default Login;
