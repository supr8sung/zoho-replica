                                                            
export const validator = {
  handleValidation(event, state, modifiedField, type) {
    var value;value=event;
    let errors = state;
    let validators = [];
   
    if (type) { //checking what are the type of valiations we have applied
     
    if(Array.isArray(type)){ type= type.toString()}
    (type.indexOf(",") != -1) ? validators = type.split(",").flat() : validators = type.split()  
  
    for(let validationType in validators){
      
        switch (validators[validationType]) {
            case "required":
             {  errors[modifiedField] = (value.length === 0 ? "Required!" : "")}
              break;
            case "email":
              errors[modifiedField] = value.length !== 0 && !validEmailRegex.test(value)
                ? "Email is not valid!"
                : "";
              break;
            case "minlength":
              errors[modifiedField] =
                value.length < 3 && value.length !== 0
                  ? "Must be Minimum 3 characters long!"
                  : "";
              break;
            case "maxlength":
            errors[modifiedField] =
              value.length > 10 && value.length !== 0
                ? "Must be Maximum 10 characters long!"
                : "";
            break;
            // case "ipaddress":
            //   errors[modifiedField] =
            //   !validIpRegex.test(value)
            //     ? "Must be valid IP!"
            //     : "";

            default:
              break;
          }
       if(errors[modifiedField]){
            return errors[modifiedField];    
       }
    }
    
  }
}
};


const validEmailRegex = RegExp(/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i);
const validIpRegex = RegExp(/^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/i);             