const request = require('request');
const _ = require('lodash');

var getWeather = (lat, long, callback) => {


    request({
        url: `https://api.darksky.net/forecast/72b74cbc94f78a3c9833a8ec858b6ad3/${lat},${long}`,
        json: true
    }, (error, response, body) => {


        if (error) {
            callback(`Unable to connect to forecast.io server`);
        } else if (response.statusCode === 400) {
            callback(`unable to fetch weather bcoz invalid input`);

        } else if (!error && response.statusCode === 200) {
            callback(undefined, {
                currentWeather: body.currently.temperature,
                apparentTemperature: body.currently.apparentTemperature
            });

        }

    })
};

module.exports = {
    getWeather
};