console.log("boardModify JS IN");

document.getElementById('logoutLink').addEventListener('click',(e)=>{
    e.preventDefault(); // 무력화
    document.getElementById('logoutForm').submit();
})