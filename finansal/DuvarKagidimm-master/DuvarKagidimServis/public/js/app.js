var config = {
   apiKey: "AIzaSyAe9_RRIpqfETkJiItsb8C7v3VHXauJ5yE",
   authDomain: "duvarkagidi-50f3e.firebaseapp.com",
   databaseURL: "https://duvarkagidi-50f3e.firebaseio.com",
   projectId: "duvarkagidi-50f3e",
   storageBucket: "duvarkagidi-50f3e.appspot.com",
   messagingSenderId: "89423842809"
 };

  firebase.initializeApp(config);

    firebase.auth.Auth.Persistence.LOCAL;

    $("#btn-login").click(function(){

        var email = $("#email").val();
        var password = $("#password").val();

        var result = firebase.auth().signInWithEmailAndPassword(email, password);

        result.catch(function(error){
            var errorCode = error.code;
            var errorMessage = error.message;

            console.log(errorCode);
            console.log(errorMessage);
        });

    });

    $("#btn-logout").click(function(){
        firebase.auth().signOut();
    });

    function switchView(view){
        $.get({
            url:view,
            cache: false,
        }).then(function(data){
            $("#container").html(data);
        });
    }
