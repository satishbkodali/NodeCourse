var asyncAdd = (a, b) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (typeof a === 'number' && typeof b === 'number') {
                resolve(a + b);
            } else {
                reject('Arguments must be numbers');
            }
        }, 1500);
    });
};

asyncAdd(4, 'e').then((res) => {
    console.log('Result:', res);
    return asyncAdd(res, 33);
}).then((res) => {
    console.log('45 Result:', res);
}).catch((errorMessage) => {
    console.log(errorMessage);
});

// var somePromise = new Promise((resolve, reject) => {
//     setTimeout(() => {
//         // resolve('Hey It worked !');
//         reject('Unable to promise')
//     }, 2500);

// });

// somePromise.then((message) => {
//     console.log('Success:', message);
// }, (errorMessage) => {
//     console.log('Error:', errorMessage);
// });