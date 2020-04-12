/**
 * 도전과제 편집기
 */
window.addEventListener("load",(e)=>{	
    // list 불러오기
    const challengeList = document.querySelector('#challenge-list');
    const contextPath = getContextRootPath(); // context path
    // list click이벤트 처리
    if(challengeList){    	
    	challengeList.addEventListener('click',challengeListHandler);
    	// 리스트 갱신
    	updateChallengeList(challengeList);
    }

    // 에디터 열기 버튼
    const editorBtn = document.querySelector('#btn-editor');  
    if(editorBtn){    	
    	editorBtn.addEventListener('click',
		e=>{
			const li = challengeList.querySelector('li.selected');
			if(li){
				const cnum = li.getAttribute('data-num');
				location.href=`${contextPath}/content/challenge/editor/${cnum}`;
			}
			else {
				alert("편집할 도전과제를 선택해 주세요")
			}
		});
    }
    
    // 새 도전과제 추가 버튼
    const addNewChallengeBtn = document.querySelector("#btn-new-challenge");
    if(addNewChallengeBtn){
    	addNewChallengeBtn.addEventListener('click',
		e=>{
			const xhr = new XMLHttpRequest();
			xhr.open('POST',`${contextPath}/content/challenge/new`);     
			xhr.addEventListener('load',e=>{
				// 리스트 갱신
		    	updateChallengeList(challengeList);
			});			
			xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
			xhr.send();
		});
    } 
    
    
    
    // 도전과제 케이스 리스트
    const caseList = document.querySelector("ul#case-list");
    if(caseList){
    	
    	const cNum = document.querySelector("input#cNum").value;
    	updateChallengeCaseList(caseList,cNum); // 리스트 갱신
    	caseList.addEventListener('click',caseListHandler);
    }
    
    // 새 케이스 추가 버튼
    const addNewCaseBtn = document.querySelector("#btn-new-case");
    if(addNewCaseBtn){
    	const cNum = document.querySelector("input#cNum").value;
    	addNewCaseBtn.addEventListener('click',
		e=>{
			const xhr = new XMLHttpRequest();
			xhr.open('POST',`${contextPath}/content/challenge/case/new?cNum=${cNum}`);     
			xhr.addEventListener('load',e=>{
				// 리스트 갱신
				updateChallengeCaseList(caseList,cNum);
			});			
			xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
			xhr.send();
		});
    } 
    
    // 케이스 저장 버튼
    const saveDataBtn = document.querySelector("#btn-data-save");
    if(saveDataBtn){
    	saveDataBtn.addEventListener('click',
    	e=>{
    		e.preventDefault();
    		const inputEle = document.getElementById('input-data-editor');
    		const outputEle = document.getElementById('expected-data-editor');
    		const li = document.querySelector('li.selected');
    		const datas= {
    				"input":inputEle.value,
    				"output":outputEle.value,
    				"caseNum":li.getAttribute("data-num")
    				};
    		requestPutCurrentCaseData(datas);
    	}
    	);
    }
});

// 리스트 핸들러
function challengeListHandler(e){
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

// 케이스 리스트 핸들러
function caseListHandler(e){
	const contextPath = getContextRootPath(); // context path
	
	const attributes = rakeAllAttributesThroughPath(e);
	console.dir(attributes);
	const li = e.target.closest('li');
	const num = li.getAttribute('data-num');
	
	if(e.target.classList.contains('btn-delete')){ // 삭제 버튼
		console.log('삭제')           
		const xhr = new XMLHttpRequest();
		xhr.open('DELETE',`${contextPath}/content/challenge/case/${num}`)
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
		xhr.open('PUT',`${contextPath}/content/challenge/case/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		const title = li.querySelector('span.title').innerText;
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
// xhr.send({"title":title});
		li.querySelector('span.title').setAttribute('contenteditable',false);
		
	}else {// 리스트 항목 클릭
		console.log('리스트항목')
		childrenList = e.currentTarget.children;
		for(let i =0;i<childrenList.length;i++){
			childrenList[i].classList.remove('selected');                
		}
		li.classList.add('selected');
		const inputEle = document.querySelector('#input-data-editor');
		const expectedEle = document.querySelector('#expected-data-editor');
		
		const xhr = new XMLHttpRequest();
		xhr.open('GET',`${contextPath}/content/challenge/case/${num}`)
		xhr.addEventListener('load',
				e=>{
					const data = JSON.parse(e.currentTarget.response);
					setInputAndExpected({inputEle,expectedEle},data);
				});
		xhr.send();
	}

	e.preventDefault()
}




// 도전과제 리스트 갱신하기
function updateChallengeList(challengeList){
	// list 갱신
	const challengeListRequest = new XMLHttpRequest();    
	const requestUrl = getContextRootPath()+"/content/challenge/list";    
	challengeListRequest.open("GET",requestUrl);
	challengeListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(challengeList);
				const listData = JSON.parse(e.currentTarget.response);
				addChildToList(challengeList,listData,makeChallengeItem);
			}
	);
	try{
		challengeListRequest.send();
	}catch (e){
		console.error(e);
	}	
}
// 도전과제케이스 리스트 갱신하기
function updateChallengeCaseList(caseListElement,cNum){
	// list 갱신
	const caseListRequest = new XMLHttpRequest();    
	const requestUrl = getContextRootPath()+`/content/challenge/case/list?cNum=${cNum}`;    
	caseListRequest.open("GET",requestUrl);
	caseListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(caseListElement);
				const listData = JSON.parse(e.currentTarget.response);
				addChildToList(caseListElement,listData,makeChallengeCaseItem);
			}
	);
	try{
		caseListRequest.send();
	}catch (e){
		console.error(e);
	}	
}



// 도전과제 항목 만들기
function makeChallengeItem(data){
    const item = document.createElement('li');
    item.setAttribute('data-num',data.cnum);
    item.innerHTML= `<span class="title">${data.title}</span> ${makeListItemTools()}`;    
    return item;
}

// 도전과제 케이스 항목 만들기
function makeChallengeCaseItem(data){
	const item = document.createElement('li');
	item.setAttribute('data-num',data.caseNum);
	item.innerHTML= `<span class="title">${data.caseNum}</span> <button class="btn-delete">삭제</button>`;    
	return item;
}



// 리스트에 항목들 추가
function addChildToList(listElement, listData, childMaker){
	listData.forEach(item  => {
        const childElement = childMaker(item);
        listElement.appendChild(childElement);
    });
}

// 리스트 항목들 삭제
function removeAllChildren(element){	
	while ( element.hasChildNodes() ) { element.removeChild( element.firstChild ); }	
}

// 도전과제 항목 만들기
function makeChallengeItem(data){
    const item = document.createElement('li');
    item.setAttribute('data-num',data.cnum);
    item.innerHTML= `<span class="title">${data.title}</span> ${makeListItemTools()}`;    
    return item;
}

// 삭제 편집 버튼 넣기
function makeListItemTools(){
    return `<div class="list-toolbox"><button class="btn-delete">삭제</button><button class="btn-edit">편집</button><button class="btn-save">저장</button></div>`;
}

// event경로 상 모든 'data'로 시작하는 attribute 를 긁어옴
function rakeAllAttributesThroughPath(event){
    const result = {};
    const root = event.currentTarget;
    const path = event.path;
    for(let i =0;i<path.length;i++){
        if(path[i]===root)
            break;
        const attrs = path[i].attributes;
        for(let attrIndex=0;attrIndex<attrs.length;attrIndex++){
            const item = attrs[attrIndex];
            if(item.name.startsWith('data-'))
                result[item.name]= item.value;
        }
    }
    return result;
}

// input과 output을 셋팅함
function setInputAndExpected(elements,data){	
	const {inputEle,expectedEle} = elements;
	const {input,output} = data;
	console.log("input:"+input,"output:"+output)
	inputEle.value=input;
	expectedEle.value=output;	
}

// 현재 case데이터를 저장함
function requestPutCurrentCaseData(datas){	
	const  {input, output, caseNum} = datas;
	const contextPath = getContextRootPath(); // context path
	const xhr = new XMLHttpRequest();
	xhr.open('PUT',`${contextPath}/content/challenge/case`);     
	xhr.addEventListener('load',
			e=>{
				
			});	
	xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
//	console.log(datas);
// xhr.send(JSON.stringify({"input":input,"output":output,"caseNum":caseNum}));
	xhr.send(JSON.stringify(datas));
// xhr.send({"input":input});
}
