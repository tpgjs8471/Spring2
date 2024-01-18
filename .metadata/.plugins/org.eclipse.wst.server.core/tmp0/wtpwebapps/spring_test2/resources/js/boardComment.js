console.log("Comment JavaScript In");
console.log(">>> bnoVal >>>>>"+bnoVal);

document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    const cmtWriter = document.getElementById('cmtWriter').innerText;
    if(cmtText.value == null || cmtText.value == '' ){
        alert('댓글을 입력해주세요');
        cmtText.focus();
        return false;
    } else {
        cmtData = {
            bno : bnoVal,
            writer : cmtWriter,
            content : cmtText.value
        };
        console.log("cmtData 확인용도"+cmtData);
        postCommentToServer(cmtData).then(result =>{
            console.log(result);
            if(result == '1'){
                alert("댓글 등록 성공~!!");
                cmtText.value = "";
                spreadCommentList(cmtData.bno,1); // cmtData 의 bno 가 bnoVal
            }
            // 화면에 뿌려주기
        })
    }
})

// 비동기 통신 구문
// async function
// 목적지 경로 : const url
// post : 데이터 삽입
        // get : 데이터 조회 => 생략가능
        // put (patch) : 수정
        // delete : 삭제
// header 는 객체로 받아야 함
// resp 가 destPage 라고 생각하면 됨
// headers : 전송되는 데이터가 JSON 형식이며, 문자 인코딩은 UTF-8로 설정
async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config = {
            method : "post",
            headers : {
                'content-type':'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommentListFromServer(bno,page){
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json(); // commentList return
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno,page=1){
    getCommentListFromServer(bno,page).then(result =>{
        console.log(result.cmtList); // result 확인용
        let div = document.getElementById('accordionExample');
        let id = document.getElementById('cmtWriter').value;
        let admin = document.getElementById('cmtWriter');
        // 댓글 모양대로 뿌리기
        if(result.cmtList.length > 0){
            if(page ==1 ){    
                // 댓글을 다시 뿌릴때 기존 값을 삭제 XXX 1page 일 경우만 삭제
                div.innerHTML = "";
            }
            for(let i=0;i<result.cmtList.length;i++){
                let add = `<div class="accordion-item">`;
                add += `<h2 class="accordion-header">`;
                add += `<button class="accordion-button" type="button" data-bs-toggle="collapse"`;
                add +=`data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
                add += `No.${result.cmtList[i].cno} /  Writer: ${result.cmtList[i].writer}   /   ${result.cmtList[i].regAt} </button></h2>`;
                add += `<div id="collapse${i}" class="accordion-collapse collapse show" `;
                add += `data-bs-parent="#accordionExample">`;
                add += `<div class="accordion-body" data-cno="${result.cmtList[i].cno}" data-writer="${result.cmtList[i].writer}">`;
                if( id === `${result.cmtList[i].writer}` ) {
                    add += `<button type="button" data-cno="${result.cmtList[i].cno}" class="btn btn-warning btn-sm  mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                    add += `<button type="button" data-cno="${result.cmtList[i].cno}?" class="btn btn-danger btn-sm del">삭제</button>`;
                } else if(admin.value == `admin`){
                    add += `<button type="button" data-cno="${result.cmtList[i].cno}" class="btn btn-warning btn-sm  mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                    add += `<button type="button" data-cno="${result.cmtList[i].cno}?" class="btn btn-danger btn-sm del">삭제</button>`;
                }
                add += `<input type="text" class="form-control cmtText" value="${result.cmtList[i].content}">`;
                add += `</div></div></div></div>`;
                div.innerHTML += add;
            }
            // 더보기 버튼 코드
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            if(result.pgvo.pageNo < result.endPage){
                // moreBtn 버튼이 표시되는 조건
                moreBtn.style.visibility = 'visible'; //버튼 표시
                moreBtn.dataset.page = page+1;
            } else {
                moreBtn.style.visibility = 'hidden'; // 버튼 숨김
            }
        } else {
            div.innerHTML = `<div class="accordion-body">Comment List Empty</div>`;
        }
    })
};

document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.id == 'moreBtn'){
        let page =parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal,page);
    } else if(e.target.classList.contains('mod')){
        let div = e.target.closest('div');
        console.log(div);
        let cmtText = div.querySelector('.cmtText').value; 
        // nextSibling :  한 부모 안에서 같은(다음) 형제를 찾아서 찾기
        console.log(cmtText);
        // 모달창에 기존 댓글 내용을 반영 (수정하기 편하게...)
        document.getElementById('cmtTextMod').value = cmtText;

        // 수정을 하기 위해선 => cno writer content 을 위한 cno ,writer data- 로 달기
        // data-cno{i} , data-writer{i}
        document.getElementById('cmtModBtn').setAttribute("data-cno",div.dataset.cno);
        document.getElementById('cmtModBtn').setAttribute("data-writer",div.dataset.writer);
    } else if(e.target.id == "cmtModBtn"){
        let cmtDataMod = {
            cno : e.target.dataset.cno,
            writer : e.target.dataset.writer,
            content : document.getElementById('cmtTextMod').value
        }
        console.log(cmtDataMod);
        // 비동기로 보내기
        editConmmentToServer(cmtDataMod).then(result=>{
            if(result == '1'){
                // 수정 성공을 된다면 => 모달창 닫기
                // 모달의 X 버튼 class : btn-close
                document.querySelector('.btn-close').click();
            } else {
                // 수정에 실패한다면
                alert('수정 실패');
                document.querySelector('.btn-close').click();
            }
            // 수정된 댓글 뿌리기 page=1 (댓글페이지)
            spreadCommentList(bnoVal);
        })
        // 여기까지가 모달창을 이용한 수정버튼
    } 
    // 여기서 부턴 삭제버튼 처리
    else if(e.target.classList.contains('del')){
        console.log("삭제버튼 확인");
        let cnoVal = e.target.dataset.cno;
        let WriterVal = e.target.dataset.writer;
        removeCommentToServer(cnoVal).then(result =>{
            if(result == '1'){
                alert("댓글 삭제 성공");
                spreadCommentList(bnoVal);
            }
        })
    }
})

// 업데이트 구문
async function editConmmentToServer(cmtDataMod){
    try {
        const url = '/comment/edit';
        const config ={
            method : "put",
            headers : {
                "content-type":"application/json; charset=utf-8"
            },
            body : JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// 삭제 구문
async function removeCommentToServer(cno){
    try {
        const url = "/comment/del/"+cno;
        const config = {
            method : "delete"
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}