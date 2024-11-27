import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function QuestionForm() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    que: '',op1: '',op2: '',op3: '',op4: '',
    ans: 'op1', 
    level: 'easy',
    username: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  async function handleSubmit(e) {
    e.preventDefault();

    // Set username from local storage
    const username = localStorage.getItem('username');

    // Set the answer based on the selected option
    let updatedAns;
    if (formData.ans === 'op1') {
      updatedAns = formData.op1;
    } else if (formData.ans === 'op2') {
      updatedAns = formData.op2;
    } else if (formData.ans === 'op3') {
      updatedAns = formData.op3;
    } else {
      updatedAns = formData.op4;
    }

    // Update the form data including username and answer
    const updatedFormData = {
      ...formData,
      username: username || '', // Default to empty if username is null
      ans: updatedAns
    };

    setFormData(updatedFormData);

    console.log('Form Data:', updatedFormData);

    try {
      const response = await axios('http://localhost:8080/que/add-que', {
        method : "POST", 
        headers : {
          Authorization :  `Bearer ${localStorage.getItem('jwtToken')}`
        },
        data : formData
      });
      console.log('Response:', response);
      if(response.request.status == '200')
      {
        window.alert("Added Successfully");
        navigate("/");
      }
    } catch (error) {
      console.error('Error while submitting the form:', error);
    }
  }

  return (
    <Container>
      <h2 className="mt-4">Create a Question</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="que">
          <Form.Label>Question</Form.Label>
          <Form.Control
            type="text"
            name="que"
            placeholder="Enter your question"
            value={formData.que}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="op1">
          <Form.Label>Option 1</Form.Label>
          <Form.Control
            type="text"
            name="op1"
            placeholder="Enter option 1"
            value={formData.op1}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="op2">
          <Form.Label>Option 2</Form.Label>
          <Form.Control
            type="text"
            name="op2"
            placeholder="Enter option 2"
            value={formData.op2}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="op3">
          <Form.Label>Option 3</Form.Label>
          <Form.Control
            type="text"
            name="op3"
            placeholder="Enter option 3"
            value={formData.op3}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="op4">
          <Form.Label>Option 4</Form.Label>
          <Form.Control
            type="text"
            name="op4"
            placeholder="Enter option 4"
            value={formData.op4}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="ans">
          <Form.Label>Select the Correct Answer</Form.Label>
          <Form.Select
            name="ans"
            value={formData.ans}
            onChange={handleChange}
          >
            <option value="op1">Option 1</option>
            <option value="op2">Option 2</option>
            <option value="op3">Option 3</option>
            <option value="op4">Option 4</option>
          </Form.Select>
        </Form.Group>

        <Form.Group className="mb-3" controlId="difficulty">
          <Form.Label>Select Difficulty Level</Form.Label>
          <Form.Select
            name="level"
            value={formData.level}
            onChange={handleChange}
          >
            <option value="easy">Easy</option>
            <option value="medium">Medium</option>
            <option value="hard">Hard</option>
          </Form.Select>
        </Form.Group>

        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </Container>
  );
}

export default QuestionForm;
