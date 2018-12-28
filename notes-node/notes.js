const fs = require('fs');

var fetchNotes = () => {
    try {
        var noteString = fs.readFileSync('./notes-data.json');
        return JSON.parse(noteString);
    } catch (e) {
        return [];
    };
};

var saveNotes = (notes) => {
    fs.writeFileSync('./notes-data.json', JSON.stringify(notes));
};
var addNote = (title, body) => {
    var notes = fetchNotes();
    var note = {
        title,
        body
    };


    var duplicateNotes = notes.filter((note) => note.title === title);
    if (duplicateNotes.length === 0) {
        notes.push(note);
        saveNotes(notes);
        console.log(note);
        return note;
    };
}

var getAll = () => {
    return fetchNotes();
}

var readNote = (title) => {
    var notes = fetchNotes();
    var readNotes = notes.filter((note) => note.title === title);
    return (readNotes[0]);
}

var removeNote = (title) => {
    var notes = fetchNotes();
    var rmNotes = notes.filter((note) => note.title !== title);
    saveNotes(rmNotes);

    return notes.length !== rmNotes.length;
}

var logNote = (note) => {
    console.log('---');
    console.log(`Title: ${note.title}`);
    console.log(`Body: ${note.body}`);
}

module.exports = {
    addNote,
    getAll,
    readNote,
    removeNote,
    logNote
};