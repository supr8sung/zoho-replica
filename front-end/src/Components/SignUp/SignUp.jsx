import React, { Component } from "react";

class SignUp extends Component {
  submit = e => {
    const {
      _username,
      _fullname,
      _email,
      _password,
      _mobile,
      _company
    } = this.refs;
    const userData = {
      username: _username.value,
      fullname: _fullname.value,
      email: _email.value,
      password: _password.value,
      mobile: _mobile.value,
      company: _company.value
    };
    fetch("http://localhost:8080/zoho/signup", {
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
      <form class="form-signin">
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
          <label for="fullname" class="sr-only">
            Fullname
          </label>
          <input
            ref="_fullname"
            type="text"
            id="fullname"
            class="form-control"
            placeholder="Fullname"
            required=""
            autoFocus=""
          />
        </div>
        <div>
          <label for="email" class="sr-only">
            Email
          </label>
          <input
            ref="_email"
            type="text"
            id="email"
            class="form-control"
            placeholder="Email"
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
        <div>
          <label for="mobile" class="sr-only">
            Mobile
          </label>
          <input
            ref="_mobile"
            type="text"
            id="mobile"
            class="form-control"
            placeholder="Mobile"
            required=""
            autoFocus=""
          />
        </div>
        <div>
          <label for="company" class="sr-only">
            Company
          </label>
          <input
            ref="_company"
            type="text"
            id="company"
            class="form-control"
            placeholder="Company"
            required=""
            autoFocus=""
          />
        </div>
        <button
          class="btn btn-lg btn-primary btn-block"
          type="submit"
          clicked={this.submit}
        >
          Signup
        </button>
      </form>
    );
  }
}
export default SignUp;
