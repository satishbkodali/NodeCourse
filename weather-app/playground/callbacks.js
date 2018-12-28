var getUser = (id, callback) => {
    var user = {
        id: id,
        name: 'sat'
    };
    setTimeout(() => {
        callback(user);
    }, 5000);

};

getUser(23, (usr) => {
    console.log(usr);
});