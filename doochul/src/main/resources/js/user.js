'use strict';

let index = {
    init: function () {
        $("#btn-save").on("click", () => { //this를 바인딩하기 위해 화샬표 함수 사용
            let form = document.querySelector("#needs-validation");
            if (form.checkValidity() === false) {
                console.log("회원가입 안됨")
            } else {
                this.save();
            }
        });
        $("#btn-update").on("click", () => {
            let form = document.querySelector("#needs-validation");
            if (form.checkValidity() === false) {
                console.log("회원수정 안됨")
            } else {
                this.update();
            }
        });

    },

    isUsernameChecked: false,
    isEmailChecked: false,
    isNicknameChecked: false,

    save: function () {
        this.checkAllFields();

        let data = { //JavaScript Object
            username: $("#username").val(),
            password: $("#password").val(),
            gender: $("#gender").val(),
        }

        $.ajax({
            type: "POST", //Http method
            url: "/join", //API 주소
            data: JSON.stringify(data), //JSON으로 변환
            contentType: "application/json; charset=utf-8", //MIME 타입
            dataType: "json" //응답 데이터
        }).done(function (res) {
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },
    checkAllFields: function () {
        if (index.isUsernameChecked && index.isEmailChecked && index.isNicknameChecked) {
            // 모든 중복 체크가 완료되었을 때만 회원가입 가능
            $("#btn-save").prop("disabled", false); // 회원가입 버튼 활성화
        } else {
            $("#btn-save").prop("disabled", true); // 회원가입 버튼 비활성화
        }
    }
}

/*
    update: function () {
        let data = {
            id: $("#id").val(),
            password: $("#password").val(),
            nickname: $("#nickname").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/v1/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원수정이 완료되었습니다.");
            location.href = "user/update";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    }*/