$( document ).ready(function() {
    $("#create").click(function() {
        let username = $("#username").val();
        let password = $("#password").val();
        let cpassword = $("#cpassword").val();
        let firstname = $("#firstname").val();
        let lastname = $("#lastname").val();
        let email = $("#email").val();

        if (username == '' || password == '' || cpassword == '') {
            alert("Please fill all the fields!");
        } else if ((password.length) < 8) {
            alert("Password should be at least 8 character!");
        } else if (!(password).match(cpassword)) {
            alert("Your passwords don't match!");
        } else {
            let data = {};
            data['username'] = username;
            data['password'] = password;
            data['firstname'] = firstname;
            data['lastname'] = lastname;
            data['email'] = email;

            $.ajax({
                url: 'user',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                success: function(response){
                    window.location.href = '/'
                    //$("form")[0].reset();
                },
            });
        }
    });
});
