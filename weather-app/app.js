const yargs = require('yargs');

const geocode = require('./geocode/geocode');
const weather = require('./geocode/weather');
const argv = yargs
    .options({
        a: {
            demand: true,
            alias: 'address',
            describe: 'Address to fetch weather',
            string: true
        }
    })
    .help()
    .alias('help', 'h')
    .argv;

geocode.geocodeAddress(argv.address, (errorMessage, geocoderesult) => {
    if (errorMessage) {
        console.log(errorMessage);
    } else {
        // var geocoderslt = JSON.stringify(geocoderesult, undefined, 2);
        console.log(geocoderesult.address);
        weather.getWeather(geocoderesult.Latitude, geocoderesult.Longitude, (errorMessage, weatherresult) => {
            if (errorMessage) {
                console.log(errorMessage);
            } else {
                console.log(`Its currently ${weatherresult.currentWeather} and feels like ${weatherresult.apparentTemperature}`);
            }
        });
    }
});




// var encodeAddr = encodeURIComponent(argv.address);

// request({
//     url: `http://www.mapquestapi.com/geocoding/v1/address?key=16Z9Ra8Z7JwT0pHid5Url2wPzbHLyqI6&location=${encodeAddr}`,
//     json: true
// }, (error, response, body) => {
//     // console.log(JSON.stringify(response, undefined, 2));
//     const mapQuestQualityCodes = ['P1', 'L1', 'I1', 'B1', 'B2', 'B3', 'A4', 'A5', 'A6', 'Z1', 'Z1', 'Z3', 'Z4'];


//     if (error) {
//         console.log(`Unable to fetch from mapquest api ${error}`);
//     } else if (body == null || response.statusCode != 200 || body.info.statuscode != 0) {
//         console.log(`Empty or invalid response from mapquest check input`);

//     } else if (_.indexOf(mapQuestQualityCodes,  body.results[0].locations[0].geocodeQualityCode.substring(0, 2)) === -1) {
//         console.log('Unable to find addresses');
//     } else {
//         console.log(`Location: ${body.results[0].providedLocation.location}, Latitude: ${body.results[0].locations[0].latLng.lat}, Longitude: ${body.results[0].locations[0].latLng.lng}`);
//     }

//     // var invalid = 0
//     // if (error) {
//     //     console.log('Connection to the server failed.')
//     // } else if (body.info.statuscode > 0) {
//     //     console.log('Error while fetching the results')
//     //     console.log(body.info.messages)
//     // } else {

//     //     body.results.forEach((res) => {
//     //         res.locations.forEach((location) => {
//     //             if (location.geocodeQualityCode.indexOf('X') === 0) {
//     //                 invalid += 1;
//     //             } else {
//     //                 console.log(`Latitude:${location.latLng.lat}`)
//     //                 console.log(`Longitude: ${location.latLng.lng}`)
//     //             }
//     //         })
//     //         if (invalid === res.locations.length)
//     //             console.log('Unable to find the location')
//     //     })
//     // }
// });