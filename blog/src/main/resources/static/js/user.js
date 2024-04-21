let index = {
    init: function () {
        // .on(이벤트명, 행위) --- Listener 만들기
        $("#btn-save").on("click", () => {
            // () => {} 화살표 함수, this를 바인딩하기 위해 사용!!
            // function(){} 를 사용하면 this가 윈도우 객체를 가리킴.
            this.save();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });

    },

    save: function () {
        //alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        //console.log(data);

        // ajax 호출시 default가 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 dataType: "json"을 지정하지 않아도 자동으로 자바 오브젝트로 변환해줌!!!
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body데이터, java 오브젝트인 data를 json으로 변경됨.
            contentType: "application/json; charset=utf-8", // MIME 데이터, body데이터가 어떤 타입인지 지정.
            dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 문자열로 들어옴(but 생긴게 json이라면) => JS 오브젝트로 변경.
        }).done(function (resp){
            if (resp.status === 500) {
                alert("회원가입에 실패하였습니다.");
            } else {
                alert("회원가입이 완료되었습니다.");
                //console.log(resp);
                location.href = "/"; // 메인화면으로 이동.
                
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

}

index.init();
