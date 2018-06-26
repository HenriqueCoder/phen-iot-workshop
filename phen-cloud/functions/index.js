const functions = require('firebase-functions');
const express = require('express');
const cors = require('cors');
const admin = require('firebase-admin');

const serviceAccount = require('./phen-dev-credentials.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://phen-dev.firebaseio.com/'
});
const database = admin.database();

const app = express();
// Automatically allow cross-origin requests
app.use(cors({ origin: true }));
// build multiple CRUD interfaces:
app.post('/', (req, res) => {
  database.ref('alert').push().update({
    wetness: req.body.value,
    timestamp: Date.now()
  });
  console.log(req.body.value);
  res.send();
});
// Expose Express API as a single Cloud Function:
exports.alert = functions.https.onRequest(app);

let id = 0;
exports.notify = functions.database.ref('/alert/{alertId}').onCreate((alert, context) => {
  return new Promise((resolve, reject) => {
    database.ref('user').once('value').then((users) => {
      users.forEach((user) => {
        const message = {
          data: {
            id: (id++ % 1024).toString(),
            title: 'System Alert',
            message: 'Humidity level is low!',
            humidity: alert.val().humidity.toString(),
            timestamp: alert.val().timestamp.toString(),
            sound: 'alert.mp3'
          },
          token: user.val()
        };
  
        admin.messaging().send(message).then((response) => {
            console.log('Successfully sent message:', response);
            console.log('Sent message: ' + JSON.stringify(message));
            resolve();
          }).catch((error) => {
            console.log('Error sending message:', error);
            reject(error);
          });
      });
    }).catch((err) => {
      console.log(err);
    });
  });
});

