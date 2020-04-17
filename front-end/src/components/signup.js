import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import {fetch} from '../services/httpServices';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { withRouter } from 'react-router';
const SignupForm = (props) => {
    const myHistory = props.history;
    const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/;
    const validPassword = /^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$/;
    const notify = (msgObject) => {
        if(msgObject.status === 'S'){
            toast.warn(msgObject.message || 'success', {
                position: toast.POSITION.TOP_RIGHT
              });
        } else{
            toast.error(msgObject.message || 'error', {
                position: toast.POSITION.TOP_RIGHT
              });
        }
        
    };
    const saveDataSuccesshandler = (response) =>{
        if(response.payload.status === 'S'){
            myHistory.push({pathname:'/edituser/' + response.payload.data, appState:{empId: response.payload.data}});
        }  else{
            notify({status: 'F',message:response.payload.message});
        }
    }
    const validUserSuccessHandler = (values) =>{
        fetch.post({
            url:'zoho/user/add',
            requestBody: values,
            callbackHandler: saveDataSuccesshandler
        });
    }
  const formik = useFormik({
    initialValues: {
      username: '',
      fullname: '',
      email: '',
      mobile:'',
      password: '',
      birthday:'',
      city:'',
      designation:''
    },
    validationSchema: Yup.object({
        username: Yup.string().min(3,'Must be 3 characters or more')
          .max(20, 'Must be 15 characters or less')
          .required('Required'),
        fullname: Yup.string()
          .required('Required'),
        email: Yup.string()
          .email('Invalid email address')
          .required('Required'),
        password: Yup.string().required('Required').test('','Invalid Password Combination',val =>{
            return validPassword.test(val);
        }),
        mobile: Yup.string().required('Required')
        .test("len", "Invalid Phone Number", val => {
            return phoneRegExp.test(Number(val));
          }),
        birthday: Yup.string()
        .required('Required'),
        designation: Yup.string()
        .required('Required'),
        city: Yup.string()
        .required('Required'),
      }),
    onSubmit: (values,{ setStatus, resetForm }) => {
        values.birthday = "08/01/1993";
        fetch.get({
            url: 'zoho/user/valid/' + values.username,
            
            callbackHandler: (response) =>{
                if(response.payload.status === 'S'){
                    if(response.payload.data){
                        validUserSuccessHandler(values);
                    } else{
                        notify({status: 'S',message:'Username Already Taken'});
                    }
                } else{
                    notify({status: 'F',message:'Service Error'});
                }
               
                
            }
        });
    },
  });

  return (
    <form onSubmit={formik.handleSubmit}>
        <ToastContainer autoClose={8000}/>
    <div className="field">
        <label htmlFor="username" className="label">UserName</label>
        <div className="control">
            <input className="input" id="username" name="username"
            {...formik.getFieldProps('username')}
            />
            {formik.touched.username && formik.errors.username ? <div><span className="errorMessage">{formik.errors.username}</span></div> : null}
        </div>
    </div> 


    <div className="field">
        <label htmlFor="fullname" className="label">fullName</label>
        <div className="control">
            <input
            id="fullname"
            className="input"
            name="fullname"
            type="text"
            {...formik.getFieldProps('fullname')}
            />
            {formik.touched.fullname && formik.errors.fullname ? <div><span className="errorMessage">{formik.errors.fullname}</span></div> : null}
        </div>
    </div>


    <div className="field">
        <label htmlFor="email" className="label">Email</label>
        <div className="control">
            <input
            className="input"
            id="email"
            name="email"
            type="email"
            {...formik.getFieldProps('email')}
            />
            {formik.touched.email && formik.errors.email ? <div><span className="errorMessage">{formik.errors.email}</span></div> : null}
        </div>
    </div>

    <div className="field">
        <label htmlFor="mobile" className="label">Mobile No.</label>
        <div className="control">
            <input
            id="mobile"
            className="input"
            name="mobile"
            type="text"
            {...formik.getFieldProps('mobile')}
            />
            {formik.touched.mobile && formik.errors.mobile ? <div><span className="errorMessage">{formik.errors.mobile}</span></div> : null}
        </div>
    </div>

    <div className="field">
        <label htmlFor="password" className="label">Password</label>
        <div className="control">
            <input
            id="password"
            className="input"
            name="password"
            type="password"
            {...formik.getFieldProps('password')}
            />
            {formik.touched.password && formik.errors.password ?
             <div><span className="errorMessage">{formik.errors.password}</span>
             {formik.errors.password !== "Required" ?
             <ul className="passwordValidation">
                 <li>Minimum of 8 Characters</li>
                 <li>Minimum one lowercase character</li>
                 <li>Minimum one uppercase character</li>
                 <li>Minimum one Numeric character</li>
                 <li>Minimum one special character from @#$%^&+=</li>
            </ul> : null}
             </div>
             
            : null}
        </div>
    </div>

    <div className="field">
        <label htmlFor="birthday" className="label">Birthday</label>
        <div className="control">
            <input
            id="birthday"
            className="input"
            name="birthday"
            type="date"
            {...formik.getFieldProps('birthday')}
            />
            {formik.touched.birthday && formik.errors.birthday ? <div><span className="errorMessage">{formik.errors.birthday}</span></div> : null}
        </div>
    </div>


    <div className="field desigField">
        <label htmlFor="designation"  className="label">Designation</label>
        <div className="control">
            <div className="select designation">
                <select id="designation" 
                name="designation"
                {...formik.getFieldProps('designation')}>
                    <option value="">Select</option>
                    <option value="CONSULTANT">Consultant</option>
                    <option value="PRINCIPAL_CONSULTANT">Principal Consultant</option>
                    <option value="DIRECTOR">Director</option>
                </select>
                {formik.touched.designation  && formik.errors.designation ? <div><span className="errorMessage">{formik.errors.designation}</span></div> : null}
            </div>
        </div>
    </div>
            

    <div className="field desigField">
        <label htmlFor="city"  className="label">City</label>
        <div className="control">
            <div className="select designation">
                <select id="city" 
                name="city"
                {...formik.getFieldProps('city')}>
                    <option value="">Select</option>
                    <option value="BANGALORE">Bangalore</option>
                    <option value="GURUGRAM">Gurugram</option>
                    <option value="PUNE">Pune</option>
                </select>
                {formik.touched.city && formik.errors.city ? <div><span className="errorMessage">{formik.errors.city}</span></div> : null}
            </div>
        </div>
    </div>
   

    <div className="field is-grouped">
        <div className="control">
            <button type="button submit" className="button is-link">Submit</button>
        </div>
        <div className="control">
            <button className="button is-link is-light">Clear</button>
        </div>
    </div>
    </form>
  );
};

export default withRouter(SignupForm);