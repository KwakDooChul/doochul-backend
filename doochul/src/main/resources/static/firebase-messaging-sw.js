importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-messaging.js');

// Initialize Firebase
const firebaseConfig = {
    apiKey: "AIzaSyAzHogvZK_6BoCm6Qa17wBTEwvfuFEocLA",
    authDomain: "kwakdoochul-bbb43.firebaseapp.com",
    projectId: "kwakdoochul-bbb43",
    storageBucket: "kwakdoochul-bbb43.appspot.com",
    messagingSenderId: "801744474814",
    appId: "1:801744474814:web:7ed72e926feb81dda684ef",
    measurementId: "G-02EJZQPZ4D"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();




