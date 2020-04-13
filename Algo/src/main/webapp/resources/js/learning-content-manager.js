/**
 * 이론학습 관리화면 
 */
window.addEventListener('load',
e=>{
    const contextPath = getContextRootPath(); // context path

	// 과목 리스트
	const subjectList = document.getElementById('subject-list');
	if(subjectList){
		subjectList.addEventListener('click',subjectListHandler);
		subjectList.addEventListener('error',requestBasicImage,true);
		updateSubjectList(subjectList);
	}
	// 단원 리스트
	const unitList = document.getElementById('unit-list');
	if(unitList){
		unitList.addEventListener('click',unitListHandler);
	}
	// 단원 depth 왼쪽 
	const moveLeftBtn = document.getElementById('btn-move-left');
	if(moveLeftBtn){
		moveLeftBtn.addEventListener('click',e=>{
			const li = unitList.querySelector('li.selected');
			let curDepth = li.getAttribute('data-depth')
			if(!li.previousSibling)
				return;
			
			let prevDepth = li.previousSibling.getAttribute('data-depth');// 이전항목
			prevDepth = prevDepth.substr(0,prevDepth.length-1);
			console.log("prevDepth:"+prevDepth);
			const prevDepthTokens = prevDepth.split('.');
			curDepth = curDepth.substr(0,curDepth.length-1);
			console.log("curDepth:"+curDepth);
			const curDepthTokens = curDepth.split('.');
			const curDepthIdx = curDepthTokens.length-2;
		
			if(curDepthTokens.length>1){// 1보다 커야함
				
				curDepthTokens.pop();
			
				for(let i =0;i<curDepthTokens.length;i++){
					if(!isNaN(parseInt(prevDepthTokens[i]))){
						curDepthTokens[i]=prevDepthTokens[i];
						if(i === curDepthTokens.length-1){//마지막일 때						
							curDepthTokens[i]= parseInt(curDepthTokens[i])+1;
						}										
					}
				}
				newChapterDepth = curDepthTokens.join('.')+"."
				changeUnitDepth(li, newChapterDepth);
				changeFollowingUnitDepth(curDepthIdx,li, +1,"next");
			}
			
		});
	}
	// 단원 depth 오른쪽
	const moveRightBtn = document.getElementById('btn-move-right');
	if(moveRightBtn){
		moveRightBtn.addEventListener('click',e=>{
			const li = unitList.querySelector('li.selected');
			let curDepth = li.getAttribute('data-depth')
			if(!li.previousSibling)
				return;
			let prevDepth = li.previousSibling.getAttribute('data-depth');
			prevDepth = prevDepth.substr(0,prevDepth.length-1);
			console.log("prevDepth:"+prevDepth);
			const prevDepthTokens = prevDepth.split('.');
			curDepth = curDepth.substr(0,curDepth.length-1);
			console.log("curDepth:"+curDepth);
			const curDepthTokens = curDepth.split('.');
			const curDepthIdx = curDepthTokens.length-1;
 
			curDepthTokens.push('1');
			for(let i =0;i<curDepthTokens.length;i++){
				if(!isNaN(parseInt(prevDepthTokens[i]))){
					curDepthTokens[i]=prevDepthTokens[i];
					if(i === curDepthTokens.length-1){//마지막일 때						
						curDepthTokens[i]= parseInt(curDepthTokens[i])+1;
					}										
				}
			}
			newChapterDepth = curDepthTokens.join('.')+"."
			changeUnitDepth(li, newChapterDepth);
			changeFollowingUnitDepth(curDepthIdx,li, -1,"next");
		});
	}
	// 에디터 열기 버튼
	const unitEditorBtn = document.getElementById('btn-unit-editor');
	if(unitEditorBtn){
		unitEditorBtn.addEventListener('click',
			e=>{
				const li = unitList.querySelector('li.selected');
				if(li){
					const num = li.getAttribute('data-num');
					location.href=`${contextPath}/content/learning/unit/editor/${num}`;
				}
				else {
					alert("편집할 단원을 선택해 주세요")
				}
				
			}
		);
	}

  
    
    // 새 과목 추가 버튼
    const addNewSubjectBtn = document.querySelector("#btn-new-subject");
    if(addNewSubjectBtn){
    	addNewSubjectBtn.addEventListener('click',
			e=>{
				console.log('click');
				const xhr = new XMLHttpRequest();
				xhr.open('POST',`${contextPath}/content/learning/subject/new`);     
				xhr.addEventListener('load',e=>{
					// 리스트 갱신
					updateSubjectList(subjectList);
				});			
				xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
				xhr.send();
			});
    } 
    // 새 단원 추가 버튼
    const addNewUnitBtn = document.querySelector("#btn-new-unit");
    if(addNewUnitBtn){
    	
    	addNewUnitBtn.addEventListener('click',
		e=>{
			const selectedUnit = document.querySelector("#unit-list li.selected");
			const selectedSubject = document.querySelector("#subject-list li.selected");
			if(!selectedSubject){
				alert('과목을 선택해 주세요')
				return;
			}
			let subjectNum = selectedSubject.getAttribute('data-num');
//			let unitNum = selectedUnit.getAttribute('data-num');
			const xhr = new XMLHttpRequest();
			xhr.open('POST',`${contextPath}/content/learning/unit/new/${subjectNum}`);     
			xhr.addEventListener('load',e=>{
				// 리스트 갱신
				updateUnitList(unitList,subjectNum);
			});			
			xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
			let newChapterDepth="1.";
			// 새 항목 뎁스 증가
			if(selectedUnit) {
				let prevDepth = selectedUnit.getAttribute('data-depth');
				prevDepth = prevDepth.substr(0,prevDepth.length-1)
				console.log("prevDepth:"+prevDepth);
				const depthTokens = prevDepth.split('.');
				const length = depthTokens.length;
				if(depthTokens.length >0)
					depthTokens[length -1] = parseInt(depthTokens[length -1])+1;
				newChapterDepth = depthTokens.join('.')+"."
				changeFollowingUnitDepth(length-1,selectedUnit, +1,"next");
			}
			
			console.log("newChapterDepth:"+newChapterDepth);
			xhr.send(JSON.stringify({"chapterDepth":newChapterDepth}));
		});
    } 
}
);
// 뒤에 있는 것 뎁스 증가
function changeFollowingUnitDepth(depthIndex, itemElemenet, addition, direction ){

	let curSibling = (direction==='next')?itemElemenet.nextSibling : itemElemenet.previousSibling ;
	while(curSibling ){
		
		let depth = curSibling.getAttribute('data-depth');
		depth = depth.substr(0,depth.length-1);//맨뒤 점 자르기
		const depthTokens = depth.split('.');

		if(depthTokens[depthIndex]){// 1씩 증가
			depthTokens[depthIndex] = parseInt(depthTokens[depthIndex])+addition;
			const newChapterDepth = depthTokens.join('.')+"."
			changeUnitDepth(curSibling, newChapterDepth);
		}
		
		curSibling = (direction==='next')?curSibling.nextSibling: curSibling.previousSibling ;
	}
}


// 기본 이미지 불러오기
function requestBasicImage (e){
	if(e.target.tagName ==='IMG'){
//			console.log("에러 이미지");
		const requestUrl = getContextRootPath()+"/resources/images/no-subject-image.jpg";  
		if(e.target.src !== requestUrl){ // 무한 요청 방지			
			e.target.src = requestUrl;				
		}
//		e.target.removeEventListener('error', requestBasicImage);
	}
}

// 과목 목록 불러오기
//과목 리스트 갱신하기
function updateSubjectList(subjectListEle){
	// list 갱신
	const subjectListRequest = new XMLHttpRequest();    
	const requestUrl = getContextRootPath()+"/content/learning/subject/list";    
	subjectListRequest.open("GET",requestUrl);
	subjectListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(subjectListEle);
				const listData = JSON.parse(e.currentTarget.response);

				addChildToList(subjectListEle,listData,makeSubjectItem);
				if(subjectListEle && subjectListEle.children[0]){
//					subjectListEle.children[0].classList.add('selected');
					selectSubject(subjectListEle.children[0]); // 과목 선택
				}
			}
	);
	try{
		subjectListRequest.send();
	}catch (e){
		console.error(e);
	}	
}
//단원 리스트 갱신하기
function updateUnitList(unitListEle, subjectNum){
	// list 갱신
	const unitListRequest = new XMLHttpRequest();    
	const requestUrl = `${getContextRootPath()}/content/learning/unit/list/${subjectNum}`;   
	unitListRequest.open("GET",requestUrl);
	unitListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(unitListEle);
				const listData = JSON.parse(e.currentTarget.response);
				
				addChildToList(unitListEle,listData,makeUnitItem);
				if(unitListEle && unitListEle.children[0]){
					selectUnit(unitListEle.children[0]); // 과목 선택
				}
			}
	);
	try{
		
		unitListRequest.send();
	}catch (e){
		console.error(e);
	}	
}
// 단원 하나 갱신하기
function updateOneUnit(){
	
}


// 과목 선택
function selectSubject(subjectItemEle){
	const subjectList = document.getElementById('subject-list');

	const childrenList = subjectList.children;
	for(let i =0;i<childrenList.length;i++){
		childrenList[i].classList.remove('selected');                
	}
	subjectItemEle.classList.add('selected');
//	const li = subjectItemEle.closest('li');
	const num = subjectItemEle.getAttribute('data-num');
	const unitList = document.getElementById('unit-list');
	updateUnitList(unitList,num);	
}

// 단원 선택
function selectUnit(unitItemEle){
	const unitList = document.getElementById('unit-list');
	
	const childrenList = unitList.children;
	for(let i =0;i<childrenList.length;i++){
		childrenList[i].classList.remove('selected');                
	}
	unitItemEle.classList.add('selected');
}


//과목 항목 만들기
function makeSubjectItem(data){
    const item = document.createElement('li');
    const imageUrl = `${getContextRootPath()}/content/learning/subject/image/${data.subjectNum}`;
    item.setAttribute('data-num',data.subjectNum);
    item.innerHTML= `<img src="${imageUrl}"><span class="title">${data.title}</span> ${makeListItemTools()}`;    
    return item;
}

// 단원 항목 만들기
function makeUnitItem(data){
	const item = document.createElement('li');
	item.setAttribute('data-num',data.unitNum);
	item.setAttribute('data-depth',data.chapterDepth);
	item.innerHTML= `<span class="chapter-depth">${data.chapterDepth}</span><span class="title">${data.title}</span> ${makeListItemTools()}`;    
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
		xhr.open('DELETE',`${contextPath}/content/learning/subject/${num}`)
		xhr.addEventListener('load',
            e=>{
                console.log("response:"+e.target.response);
            });
        xhr.send();
        li.parentElement.removeChild(li);
	} else if(e.target.classList.contains('btn-edit')){ // 편집 버튼
		console.log('편집')
		li.querySelector('span.title').setAttribute('contenteditable',true);
		li.querySelector('span.title').focus();
		
	}else if(e.target.classList.contains('btn-save')){ // 저장버튼
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/content/learning/subject/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		const title = li.querySelector('span.title').innerText;
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
		xhr.send(JSON.stringify({"title":title}));
		li.querySelector('span.title').setAttribute('contenteditable',false);
		
	}else if(e.target.tagName ==='IMG'){
		console.log("이미지 클릭");
		imageEditPopup(num);
	}
	else {// 리스트 항목 클릭
		console.log('리스트항목')
		selectSubject(li);
//		childrenList = e.currentTarget.children;
//		for(let i =0;i<childrenList.length;i++){
//			childrenList[i].classList.remove('selected');                
//		}
//		li.classList.add('selected');
	}

	e.preventDefault()
}

// 단원 리스트 핸들러
function unitListHandler(e){
	const contextPath = getContextRootPath(); // context path
	
	const attributes = rakeAllAttributesThroughPath(e);
	console.dir(attributes);
	const li = e.target.closest('li');
	const num = li.getAttribute('data-num');
	
	if(e.target.classList.contains('btn-delete')){ // 삭제 버튼
		console.log('삭제')           
		const xhr = new XMLHttpRequest();
		xhr.open('DELETE',`${contextPath}/content/learning/unit/${num}`)
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
		xhr.open('PUT',`${contextPath}/content/learning/unit/title/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		const title = li.querySelector('span.title').innerText;
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
		xhr.send(JSON.stringify({"title":title}));
		li.querySelector('span.title').setAttribute('contenteditable',false);
		
	}else if(e.target.tagName ==='IMG'){
		console.log("이미지 클릭");
		imageEditPopup(num);
	}
	else {// 리스트 항목 클릭
		console.log('리스트항목')
		selectUnit(li);
//		childrenList = e.currentTarget.children;
//		for(let i =0;i<childrenList.length;i++){
//			childrenList[i].classList.remove('selected');                
//		}
//		li.classList.add('selected');
	}
	
	e.preventDefault()
}

// 이미지 변경 팝업
function imageEditPopup(subjectNum){
    var url = `${getContextRootPath()}/content/learning/subject/image/editor/${subjectNum}`;
    var name = "이미지 변경";
    var option = "width = 500, height = 500, top = 100, left = 200, location = no"
    window.open(url, name, option);
}

// 단원 depth변경
function changeUnitDepth(listItemElement, depth){
	const contextPath = getContextRootPath(); // context path	
//	const attributes = rakeAllAttributesThroughPath(e);
//	console.dir(attributes);
	const li = listItemElement.closest('li');
	const num = li.getAttribute('data-num');
	
	// 요소의 내용 변경
	li.setAttribute('data-depth',depth);
	if(li.querySelector('.chapter-depth')){
		li.querySelector('.chapter-depth').innerText=depth;
	}
	
	const xhr = new XMLHttpRequest();
	xhr.open("PUT",`${contextPath}/content/learning/unit/depth/${num}`);
	xhr.addEventListener('load',e=>{
		
	});
	xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
	xhr.send(JSON.stringify({"chapterDepth":depth}));
}

