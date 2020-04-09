/**
 * 이론학습 관리화면 
 */
window.addEventListenr('load',
e=>{
	// 과목 리스트
	const subjectList = document.getElementById('subject-list');
	// 단원 리스트 
	const unitList = document.getElementById('unit-list');
	
	// 단원 depth 왼쪽 
	const moveLeftBtn = document.getElementById('btn-move-left');
	// 단원 depth 오른쪽
	const moveRightBtn = document.getElementById('btn-move-right');
	// 단원 편집기 
	const unitEditorBtn = document.getElementById('btn-unit-editor');
	

}
);

// 과목 목록 불러오기
//도전과제 리스트 갱신하기
function updateSubjectList(subjectListEle){
	// list 갱신
	const subjectListRequest = new XMLHttpRequest();    
	const requestUrl = getContextRootPath()+"/content/challenge/list";    
	subjectListRequest.open("GET",requestUrl);
	subjectListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(subjectListEle);
				const listData = JSON.parse(e.currentTarget.response);
				addChildToList(subjectListEle,listData,makeSubjectItem);
			}
	);
	try{
		subjectListRequest.send();
	}catch (e){
		console.error(e);
	}	
}

//도전과제 항목 만들기
function makeSubjectItem(data){
    const item = document.createElement('li');
    item.setAttribute('data-num',data.cnum);
    item.innerHTML= `<span class="title">${data.title}</span> ${makeListItemTools()}`;    
    return item;
}


// 과목 리스트 핸들러
function subjectListHandler(e){
    const contextPath = getContextRootPath(); // context path

	const attributes = rakeAllAttributesThroughPath(e);
	console.dir(attributes);
	const li = e.target.closest('li');
	const num = li.getAttribute('data-num');
	
	if(e.target.classList.contains('btn-delete')){ // 삭제 버튼
		console.log('삭제')           
		const xhr = new XMLHttpRequest();
		xhr.open('DELETE',`${contextPath}/content/challenge/${num}`)
		xhr.addEventListener('load',
            e=>{
                
            });
        xhr.send();
        li.parentElement.removeChild(li);
	} else if(e.target.classList.contains('btn-edit')){ // 편집 버튼
		console.log('편집')
		li.querySelector('span.title').setAttribute('contenteditable',true);
		li.querySelector('span.title').focus();
		
	}else if(e.target.classList.contains('btn-save')){ // 저장버튼
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/content/challenge/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		const title = li.querySelector('span.title').innerText;
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
		xhr.send(JSON.stringify({"title":title}));
		li.querySelector('span.title').setAttribute('contenteditable',false);
		
	}else {// 리스트 항목 클릭
		console.log('리스트항목')
		childrenList = e.currentTarget.children;
		for(let i =0;i<childrenList.length;i++){
			childrenList[i].classList.remove('selected');                
		}
		li.classList.add('selected');
	}

	e.preventDefault()
}
// 단원 목록 불러오기

