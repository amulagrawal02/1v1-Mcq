import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();
  useEffect(() => {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("username");

    navigate("/user/login");
  });

  return <div>Logout</div>;
}

export default Logout;
