import React from 'react';
import SignUp from '../SignUp/SignUp'
import Button from '../../UI/Button/Button';

import styles from './MainPage.module.css';
import btnStyle from '../../UI/Button/Button.module.css';


const MainPage = (props) => {
    return props.showSignUpPage ?
        <SignUp showHomePage={props.showHomePage} /> :
        (<div className={styles.MainPage}>
            <Button styles={btnStyle.Button}>Sign In</Button>
            <Button styles={btnStyle.Button} clicked={props.signUpHandler}>Sign Up</Button>
        </div>)
}


export default MainPage;