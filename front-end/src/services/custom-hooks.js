import { useState, useEffect } from 'react';

import {fetch} from '../services/httpServices';

const useTimer = (user) => {
  let interval = 0;
  let loggedUser = JSON.parse(user);
  const [loggedInUserCheckInTime,setloggedInUserCheckInTime] = useState(loggedUser.lastCheckin);
  const [hours, setHours] = useState(0);
  const [minutes, setMinutes] = useState(0);
  const [timer,setTimer] = useState(0);
  const [checkin,setCheckin] = useState(false);
  const [attendanceId,setAttendanceId] = useState(loggedUser.checkinId);
  const [prevTotalHours,setPrevTotalHours] = useState(loggedUser.totalHours);
  const checkIncheckOut = () => {
    event.preventDefault();
    
    fetch.post({
        url: checkin?  '/zoho/user/checkout/'+attendanceId:'/zoho/user/checkin',
        callbackHandler: saveDataSuccessHandler
    });
  }

  useEffect(() =>{
    fetch.get({
      url: '/zoho/loggedinuser',
      callbackHandler: getLoggedInUser
    });
  },[]);

  function getLoggedInUser(response){
    loggedUser = response.payload.data;
  }

  useEffect(() =>{
    if(loggedInUserCheckInTime && loggedInUserCheckInTime!=="00:00:00"){
      interval = setInterval(() => {
          setTimer(timer => (timer + 1));
      }, 1000); 
      let hr = 0;
      let min = 0;
          setCheckin(true);
          let runningTime = calculateHoursAndMinutes(loggedInUserCheckInTime);
          hr = Math.abs(runningTime.H) ;
          min = Math.abs(runningTime.M);
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
    let diff = getCurrentTimeInSeconds() - totalSeconds;
    if(prevTotalHours && prevTotalHours !== "00:00"){
      const prevHours = prevTotalHours.split(":")[0] *60 *60;;
      const prevMinutes = prevTotalHours.split(":")[1] *60;
      diff = diff + prevHours  + prevMinutes;
    }
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
      const totalSeconds = (_chHours * 60 * 60) + (_chMinutes * 60) + parseInt(_chSeconds,10);
      return calculateDiffAndReturnHours(totalSeconds);
    }
 }

function saveDataSuccessHandler(response){
    setCheckin(!checkin)
    if(!checkin){
      setAttendanceId(response.payload.data); 
      setloggedInUserCheckInTime(response.payload.message);
    }
    
    if(checkin){
      setPrevTotalHours(convertTotalHours(response.payload.data).prev);
      clearInterval(interval);
      setMinutes(convertTotalHours(response.payload.data).curr.M);
      setHours(convertTotalHours(response.payload.data).curr.H);
    }
    console.log(response);
  }

  function convertTotalHours(currentTotalHours){
    const x= prevTotalHours.split(":")[0] *60 *60 + prevTotalHours.split(":")[1] *60;
     const y =currentTotalHours.split(":")[0] *60 *60 + currentTotalHours.split(":")[1] *60;
     const z = x + y;
     const totalHours = Math.floor( z/ (60 * 60));          
    const totalMinutes = Math.floor((z % (60 * 60)) / 60);
    return {prev: totalHours + ':'+ totalMinutes, curr: {H: totalHours,M:totalMinutes}};
     
  }
  // function loadData(response){
  //   loggedUser = response.payload.data;
  // }
  
  return {
    checkIncheckOut,
    hours,
    timer,
    minutes,
    checkin
  }
};

export default useTimer;