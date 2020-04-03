import { useState, useEffect } from 'react';

import {fetch} from '../services/httpServices';

const useTimer = (user) => {
  const loggedUser = JSON.parse(user);
  const [loggedInUserCheckInTime,setloggedInUserCheckInTime] = useState(loggedUser.lastCheckin);
  const [hours, setHours] = useState(0);
  const [minutes, setMinutes] = useState(0);
  const [timer,setTimer] = useState(0);
  const [checkin,setCheckin] = useState(false);

  const checkIncheckOut = () => {
    event.preventDefault();
    
    fetch.post({
        url: checkin?  '/zoho/user/checkout':'/zoho/user/checkin',
        callbackHandler: saveDataSuccessHandler
    });
  }


  useEffect(() =>{
    if(loggedInUserCheckInTime){
      const interval = setInterval(() => {
          setTimer(timer => timer = new Date().getMinutes());
      }, 1000);
      let hr = 0;
      let min = 0;
     // if(loggedInUserCheckInTime){
          setCheckin(true);
          let runningTime = calculateHoursAndMinutes(loggedInUserCheckInTime);
          hr = Math.abs(runningTime.H)  < 10 ? '0' + Math.abs(runningTime.H) : Math.abs(runningTime.H);
          min = Math.abs(runningTime.M)  < 10 ? '0' + Math.abs(runningTime.M) : Math.abs(runningTime.M);
     // } 

      setHours(hr);
      setMinutes(min);
      return () => {
        clearInterval(interval);
      };
    }
     
    },[timer,loggedInUserCheckInTime]);
  
  function getCurrentTimeInSeconds(){
    const currentTime = new Date();
    let _currHours = currentTime.getHours() * 60 * 60;
    let _currMinutes = currentTime.getMinutes() * 60;
    let _currSeconds = currentTime.getSeconds();
    return _currHours + _currMinutes + _currSeconds;
  }

  function calculateDiffAndReturnHours(totalSeconds){
    const diff = getCurrentTimeInSeconds() - totalSeconds;
    const totalHours = Math.floor( diff/ (60 * 60));          
    const totalMinutes = Math.floor((diff % (60 * 60)) / 60);
    return {
      H: totalHours,
      M : totalMinutes
    };
  }

 function calculateHoursAndMinutes(lastCheckinTime){
    if(lastCheckinTime){
      const splittedChTime = lastCheckinTime.split(':');
      const _chHours = splittedChTime[0];
      const _chMinutes = splittedChTime[1];
      const _chSeconds = splittedChTime[2];
      const totalSeconds = (_chHours * 60 * 60) + (_chMinutes * 60) + parseInt(_chSeconds);
      return calculateDiffAndReturnHours(totalSeconds);
    }
 }

function saveDataSuccessHandler(response){
    setCheckin(!checkin)
    if(!checkin){
      setloggedInUserCheckInTime(response.payload.data);
    }
    
    if(checkin){
      setMinutes(0);
      setHours(0);
    }
    console.log(response);
  }
  
  return {
    checkIncheckOut,
    hours,
    timer,
    minutes,
    checkin
  }
};

export default useTimer;