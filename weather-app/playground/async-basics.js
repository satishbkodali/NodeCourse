console.log('starting app');

setTimeout(() => {
    console.log('Inside of callback timeout');
}, 2000);

setTimeout(() => {
    console.log('Inside of second callback timeout');
}, 0);

console.log('finishing app');