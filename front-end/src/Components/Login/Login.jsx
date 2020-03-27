import React, { Component } from "react";

import styles from "./SignUp.module.css";

class Login extends Component {
  submit = e => {
    const {
      _username,

      _password
    } = this.refs;
    const userData = {
      username: _username.value,
      password: _password.value
    };
    fetch("http://localhost:8080/login", {
      method: "post",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData)
    })
      .then(res => {
        console.log(res);
        if (res.status === 201) {
          this.props.showHomePage();
        }
        return res.json();
      })
      .then(data => {
        console.log("data", data);
      })
      .catch(err => {
        console.log("error", err);
      });
    e.preventDefault();
  };

  render() {
    return (
      <form action="/login">
        <h2 class="form-signin-heading">Register here</h2>
        <div>
          <label for="username" class="sr-only">
            Username
          </label>
          <input
            ref="_username"
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            required=""
            autoFocus=""
          />
        </div>

        <div>
          <label for="password" class="sr-only">
            Password
          </label>
          <input
            ref="_password"
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            required=""
            autoFocus=""
          />
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" clicked>
          Singup
        </button>
      </form>
    );
  }
}
export default SignUp;
