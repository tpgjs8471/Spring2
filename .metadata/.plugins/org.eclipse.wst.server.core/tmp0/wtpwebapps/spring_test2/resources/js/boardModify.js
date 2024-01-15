console.log("boardModify JS IN");

document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid;
        console.log(uuid);
        removeFileToServer(uuid).then(result =>{
            if(result == '1'){
                alert("파일삭제성공")
                e.target.closest('li').remove();
                location.reload();
            }
        })
    }
})

async function removeFileToServer(uuid){
    try {
        const url = '/board/file/'+uuid;
        const config = {
            method : 'delete'
        };
        console.log("uuid : "+uuid);
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}