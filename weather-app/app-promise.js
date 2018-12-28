const yargs = require('yargs');
const axios = require('axios');
const _ = require('lodash');


const argv = yargs
    .options({
        a: {
            demand: true,
            alias: 'address',
            describe: 'Address to fetch weather',
            type: 'string',
            default: '75067'
        },
        c: {
            alias: 'celcius',
            describe: 'displays temp in celcius',
            default: false,
            type: 'boolean'

        }
    })
    .help()
    .alias('help', 'h')
    .argv;

var encodeAddr = encodeURIComponent(argv.address);
var geocodeUrl = `http://www.mapquestapi.com/geocoding/v1/address?key=16Z9Ra8Z7JwT0pHid5Url2wPzbHLyqI6&location=${encodeAddr}`;



axios.get(geocodeUrl).then((response) => {
    const mapQuestQualityCodes = ['P1', 'L1', 'I1', 'B1', 'B2', 'B3', 'A4', 'A5', 'A6', 'Z1', 'Z1', 'Z3', 'Z4'];
    if (response.data == null || response.status != 200 || response.data.info.statuscode != 0) {
        throw new Error(`Empty or invalid response from mapquest check input`);

    } else if (_.indexOf(mapQuestQualityCodes, response.data.results[0].locations[0].geocodeQualityCode.substring(0, 2)) === -1) {
        throw new Error('Unable to find addresses');
    }


    var lat = response.data.results[0].locations[0].latLng.lat;
    var long = response.data.results[0].locations[0].latLng.lng;

    console.log(`address: ${response.data.results[0].providedLocation.location}`);

    var weatherurl = `https://api.darksky.net/forecast/72b74cbc94f78a3c9833a8ec858b6ad3/${lat},${long}`;
    if (argv.celcius === true) {
        var weatherurl = `https://api.darksky.net/forecast/72b74cbc94f78a3c9833a8ec858b6ad3/${lat},${long}?units=si`;
    }
    return axios.get(weatherurl);

}).then((response) => {
    var temp = response.data.currently.temperature;
    var appTemp = response.data.currently.apparentTemperature;
    console.log(`ITs currently ${temp} and its feels like ${appTemp}.`)

}).catch((error) => {
    if (error.code === 'ENOTFOUND') {
        console.log('Unable to connect  api servers');
    } else {
        console.log(error.message);
    };
});