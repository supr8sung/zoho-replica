export default function validate(values) {
  let errors = {};
  if(values.hasOwnProperty('email')){
    if (values.hasOwnProperty('email')  && !values.email) {
      errors.email = 'Email address is required';
    } else if (!/\S+@\S+\.\S+/.test(values.email)) {
      errors.email = 'Email address is invalid';
    }
  }
  

  if(values.hasOwnProperty('password')){
    if (values.hasOwnProperty('password')  && !values.password) {
      errors.password = 'Password is required';
    } else if (values.password.length < 8) {
      errors.password = 'Password must be 8 or more characters';
    }
  }
  
  if(values.hasOwnProperty('phone')){
    if(values.hasOwnProperty('phone') && !values.phone){
      errors.phone = 'Phone Number is required';
    } 
    // else if(!/^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$/.test(values.phone)){
    //   errors.phone = 'Phone number is invalid';
    // }  
  }
  if(values.hasOwnProperty('username')){
    if(values.hasOwnProperty('username') && !values.username){
      errors.username = 'Username is required';
    }
  }

  
  return errors;
};



// export const validator = {
//   handleValidation(event, state, modifiedField, type) {
//     var value;value=event;
//     let errors = state;
//     let validators = [];
   
//     if (type) { //checking what are the type of valiations we have applied
     
//     if(Array.isArray(type)){ type= type.toString()}
//     (type.indexOf(",") != -1) ? validators = type.split(",").flat() : validators = type.split()  
  
//     for(let validationType in validators){
      
//         switch (validators[validationType]) {
//             case "required":
//              {  errors[modifiedField] = (value.length === 0 ? "Required!" : "")}
//               break;
//             case "email":
//               errors[modifiedField] = value.length !== 0 && !validEmailRegex.test(value)
//                 ? "Email is not valid!"
//                 : "";
//               break;
//             case "minlength":
//               errors[modifiedField] =
//                 value.length < 3 && value.length !== 0
//                   ? "Must be Minimum 3 characters long!"
//                   : "";
//               break;
//             case "maxlength":
//             errors[modifiedField] =
//               value.length > 10 && value.length !== 0
//                 ? "Must be Maximum 10 characters long!"
//                 : "";
//             break;
//             // case "ipaddress":
//             //   errors[modifiedField] =
//             //   !validIpRegex.test(value)
//             //     ? "Must be valid IP!"
//             //     : "";

//             default:
//               break;
//           }
//        if(errors[modifiedField]){
//             return errors[modifiedField];    
//        }
//     }
    
//   }
// }
// };


// const validEmailRegex = RegExp(/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i);
// const validIpRegex = RegExp(/^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/i);             