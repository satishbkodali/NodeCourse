const request = require('request');
const _ = require('lodash');

var geocodeAddress = (address) => {
    return new Promise((resolve, reject) => {
        var encodeAddr = encodeURIComponent(address);
        request({
            url: `http://www.mapquestapi.com/geocoding/v1/address?key=16Z9Ra8Z7JwT0pHid5Url2wPzbHLyqI6&location=${encodeAddr}`,
            json: true
        }, (error, response, body) => {
            const mapQuestQualityCodes = ['P1', 'L1', 'I1', 'B1', 'B2', 'B3', 'A4', 'A5', 'A6', 'Z1', 'Z1', 'Z3', 'Z4'];
            setTimeout(() => {

                if (error) {
                    reject(`Unable to fetch from mapquest api ${error}`);
                } else if (body == null || response.statusCode != 200 || body.info.statuscode != 0) {
                    reject(`Empty or invalid response from mapquest check input`);

                } else if (_.indexOf(mapQuestQualityCodes,  body.results[0].locations[0].geocodeQualityCode.substring(0, 2)) === -1) {
                    reject('Unable to find addresses');
                } else {
                    resolve({
                        address: body.results[0].providedLocation.location,
                        Latitude: body.results[0].locations[0].latLng.lat,
                        Longitude: body.results[0].locations[0].latLng.lng
                    });

                }
            }, 1500);
        });
    });
};

geocodeAddress('').then((location) => {
    console.log(JSON.stringify(location, undefined, 2));
}).catch((errorMessage) => {
    console.log(errorMessage);
});

// var asyncAdd = (a, b) => {
//     return new Promise((resolve, reject) => {
//         setTimeout(() => {
//             if (typeof a === 'number' && typeof b === 'number') {
//                 resolve(a + b);
//             } else {
//                 reject('Arguments must be numbers');
//             }
//         }, 1500);
//     });
// };

// asyncAdd(4, 'e').then((res) => {
//     console.log('Result:', res);
//     return asyncAdd(res, 33);
// }).then((res) => {
//     console.log('45 Result:', res);
// }).catch((errorMessage) => {
//     console.log(errorMessage);
// });