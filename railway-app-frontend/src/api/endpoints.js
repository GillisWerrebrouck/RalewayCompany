import axios from 'axios';
import {URL} from './constants';

const endpoints = {
    getStations: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/station', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getTrains: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/train', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getStaff: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/staff', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getTimetable: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/timetable/all', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
}

export default endpoints;