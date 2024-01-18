console.log("member Register in");

function passwordCheck(){
    let pw = document.getElementById('p').value;
    let pw2 = document.getElementById('p2').value;
    let pwCheck = document.getElementById('pwdCheck');
    let registerBtn = document.getElementById('registerBtn');

    if(pw != pw2){
        pwCheck.innerHTML="비밀번호가 일치하지 않습니다";
        registerBtn.disabled = true;
    } else {
        pwCheck.innerHTML="확인 완료";
        registerBtn.disabled = false;
    }
}

// 아이디 중복 체크
// 버튼 클릭 > DB 뺑뺑이?

document.getElementById('emailCheck').addEventListener('click',  (e) => {
    console.log(e.target);
    let email = document.getElementById('e').value;
    let eCheck = document.getElementById('eCheck');
    let registerBtn = document.getElementById('registerBtn');

    if (email.trim() == "") {
        eCheck.innerHTML = "이메일을 입력하세요";
        registerBtn.disabled = true;
        return;
    }

    // 이메일 중복체크 요청
    checkEmail(email).then(result =>{
        if(result == "1"){
            eCheck.innerHTML = "확인되었씁니다";
            registerBtn.disabled = true;
        } else {
            eCheck.innerHTML = "이메일 중복!";
            registerBtn.disabled = false;
        }
    })
});

async function checkEmail(email) {
    console.log(email);
    try {
        const url = `/member/emailCheck?email=${email}`;
        const response = await fetch(url, {
            method: 'GET'
        });
        console.log(url);
        const result = await response.text();
        return result;
    } catch (error) {
        console.error('오류:', error);
    }
}