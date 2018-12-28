const fs = require('fs');
const _ = require('lodash');
const yargs = require('yargs');

const notes = require('./notes.js');

const title = {
    describe: 'title of note',
    demand: true,
    alias: 't'
};

const argv = yargs
    .command('add', 'Add a new note', {
        title,
        body: {
            describe: 'body of note',
            demand: true,
            alias: 'b'
        }

    })
    .command('list', 'List all notes')
    .command('read', 'Read a note', {
        title
    })
    .command('remove', 'Remove a note', {
        title
    })
    .help()
    .argv;
var command = process.argv[2];


if (command === 'add') {
    var note = notes.addNote(argv.title, argv.body);
    if (_.isUndefined(note)) {
        console.log(`${argv.title} is duplicate so it is not inserted`);
    } else if (note.title === argv.title) {
        console.log(`${argv.title} is inserted`);
        notes.logNote(note);
    }
} else if (command === 'list') {
    var allNotes = notes.getAll();
    console.log(`Printing ${allNotes.length} note(s).`);
    allNotes.forEach((note) => notes.logNote(note));
} else if (command === 'read') {
    var note = notes.readNote(argv.title);
    if (note) {
        console.log('Reading note: ');
        notes.logNote(note);

    } else {
        console.log('Note not found');
    }
} else if (command === 'remove') {
    var noteRemoved = notes.removeNote(argv.title);
    var message = noteRemoved ? 'Note was removed' : 'Note not found';
    console.log(message);
} else {
    console.log('Command not found');
}