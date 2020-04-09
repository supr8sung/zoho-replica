import axios from 'axios';
import shortid from 'shortid';
import APP from  '../constants';

const requestId = shortid.generate();

// axios.interceptors.request.use(function(config) {
//     const token = window.localStorage.getItem('Token');

//     if (token != null) {
//         config.headers.Authorization = `Bearer ${token}`;  
//     }
    
//     return config;
// }, function(err) {

//     return Promise.reject(err);

// });

// axios.interceptors.response.use(function(config) {
//     if(config.headers['content-type'] === "application/download"){  
//         const url = URL.createObjectURL(new Blob([config.data],{type: 'application/vnd.ms-excel'}));
//         const link = document.createElement('a');
//         link.href = url;
//         let fileName = 'download';
//         if(config.config && config.config.url){
//            let arr = config.config.url.split('/');
//            fileName = arr[arr.length - 1]  + "_report.xlsx";
//         }
//         link.setAttribute('download', fileName);
//         document.body.appendChild(link);
//         link.click();
//    }
   
//     return config;

// }, function(error) {
//     if (401 === error.response.status) {
//         window.localStorage.removeItem('Token');
//         window.location = '/';
//     } else {
//         return Promise.reject(error);
//     }
// });

const outputHandler = ({ ins, callbackHandler }) => {
    ins.then((response) => {

        callbackHandler({
            status: 'success',
            message: '',
            payload: response.data
        });

    }).catch((error) => {
        let errMessage = '';
        callbackHandler({
            status: 'failure',
            message: errMessage,
            payload: {}
        });
    });

};

export const fetch = {
    get({ url, requestParams = {}, callbackHandler}) {
        const ins = axios.get(url, {
            params: requestParams,
            requestId
        });
        outputHandler({ ins, callbackHandler });
    },
    // getExcel({ url, requestParams = {}, callbackHandler}) {
    //     const ins = axios.get(`${APP.Constants.THIRD_PARTY_API_HOST}${url}`, {
    //         params: requestParams,
    //         requestId,
    //         responseType: 'arraybuffer'
    //     });
    //     outputHandler({ ins, callbackHandler });
    // },
    postUpload({ url, requestBody = {}, callbackHandler }) {
        let file = new FormData();
        file.append("file", requestBody);
        const headers = {
            'Content-Type': 'multipart/form-data; charset=utf-8; boundary=file' 
        };
        
        const ins = axios.post(url, file,{headers});
        outputHandler({ ins, callbackHandler });
    },
    post({ url, requestBody = {}, callbackHandler }) {
        const headers = {
            'Content-Type': 'application/json'
        };
        
        const ins = axios.post(url, {...requestBody, requestId },{headers});
        outputHandler({ ins, callbackHandler });
    },
    delete({ url, requestBody = {}, callbackHandler }) {
        const ins = axios.delete(`${APP.Constants.THIRD_PARTY_API_HOST}${url}`, {...requestBody, requestId });
        outputHandler({ ins, callbackHandler });
    },
    put({ url, requestBody = {}, callbackHandler }) {
        const ins = axios.put(`${APP.Constants.THIRD_PARTY_API_HOST}${url}`, {...requestBody, requestId });
        outputHandler({ ins, callbackHandler });
    }
    
};



// axios.cancel(requestId);