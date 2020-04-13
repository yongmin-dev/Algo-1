/**
 * 답안 내용 편집기
 */

window.addEventListener('load',
e=>{
	const contextPath = getContextRootPath(); // context path
	// main element
	const editorMain = document.getElementById('unit-editor-main');
	const unitNum = editorMain.getAttribute("data-num");
	console.log("unitNum:"+unitNum);
	// 퀴즈 목록
	const quizList = document.getElementById('quiz-list');
	if(quizList){
		quizList.addEventListener('click',quizListHandler);
		updateQuizList(quizList,unitNum);	
		
	}
	// 퀴즈 추가 버튼
	const addQuizBtn = document.getElementById('btn-add-quiz');
	if(addQuizBtn){		
		addQuizBtn.addEventListener('click',e=>{
			console.log('click');
			const xhr = new XMLHttpRequest();
			xhr.open('POST',`${contextPath}/content/learning/quiz/new/${unitNum}`);     
			xhr.addEventListener('load',e=>{
				// 리스트 갱신
				updateQuizList(quizList,unitNum);
			});			
			xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
			xhr.send();
		});
	}
	const saveQuizContentBtn = document.getElementById('btn-save-quiz-content'); //
	if(saveQuizContentBtn){
		
	} 
	const answerList = document.getElementById('answer-list');
	if(answerList){
		answerList.addEventListener('click',answerListHandler);
		const selectedQuiz = quizList.querySelector('li.selected');
		(new Promise((resolve,reject)=>{
			updateQuizList(quizList,unitNum);				
		}).then(data=>{
			const quizNum = selectedQuiz.getAttribute('data-num');
			updateAnswerList(answerList,quizNum);
		}));
	}
	
	const addAnswerBtn = document.getElementById('btn-add-answer'); // 답안 추가 
	if(addAnswerBtn ){
		addAnswerBtn.addEventListener('click',e=>{
			const xhr = new XMLHttpRequest();
			const quizNum = addAnswerBtn.getAttribute('data-num');
			xhr.open('POST',`${contextPath}/content/learning/answer/new/${quizNum}`);     
			xhr.addEventListener('load',e=>{
				// 리스트 갱신
				updateAnswerList(answerList,quizNum);
			});			
			xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8");
			xhr.send();
		});
	}
	
});

// 퀴즈 목록 갱신
function updateQuizList(quizListEle,unitNum){
	// list 갱신
	const quizListRequest = new XMLHttpRequest();    
	const requestUrl = `${getContextRootPath()}/content/learning/quiz/list?unitNum=${unitNum}`;    
	quizListRequest.open("GET",requestUrl);
	quizListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(quizListEle);
				const listData = JSON.parse(e.currentTarget.response);

				addChildToList(quizListEle,listData,makeQuizItem);
				if(quizListEle && quizListEle.children[0]){ 
//					quizListEle.children[0].classList.add('selected');
					selectQuiz(quizListEle.children[0]); // 첫번 째 과목 선택
				}
			}
	);
	try{
		quizListRequest.send();
	}catch (e){
		console.error(e);
	}	
}

//과목 항목 만들기
function makeQuizItem(data){
    const item = document.createElement('li');
    item.setAttribute('data-num',data.quizNum);
    item.innerHTML= `<span class="title">${data.title}</span> ${makeListItemTools()}`;    
    return item;
}

//과목 선택
function selectQuiz(quizItemEle){
	const quizList = document.getElementById('quiz-list');

	const childrenList = quizList.children;
	for(let i =0;i<childrenList.length;i++){
		childrenList[i].classList.remove('selected');                
	}
	quizItemEle.classList.add('selected');
//	const li = quizItemEle.closest('li');
	const num = quizItemEle.getAttribute('data-num');
	
	// 내용 갱신
	const quizContent = document.getElementById('quiz-content'); // 
	updateQuizContent(quizContent,num);
	
	// 답안 리스트 갱신
	const answerList = document.getElementById('answer-list');
	updateAnswerList(answerList,num);	
}
// 퀴즈 내용 갱신 
function updateQuizContent(contentEle,quizNum){
	const quizRequest = new XMLHttpRequest();    
	
	const saveQuizContentBtn = document.getElementById('btn-save-quiz-content'); // 퀴즈 저장 버튼
	const addAnswerBtn = document.getElementById('btn-add-answer'); // 답안 추가 버튼 

	const requestUrl = `${getContextRootPath()}/content/learning/quiz/${quizNum}`;   
	quizRequest.open("GET",requestUrl);
	quizRequest.addEventListener(
			"load",
			e=>{				
				const data = JSON.parse(e.currentTarget.response);
				console.log(data);
				// 퀴즈번호  저장
				saveQuizContentBtn.setAttribute("data-num",data.quizNum);  
				addAnswerBtn.setAttribute("data-num",data.quizNum); 
				contentEle.innerHTML = data.content;
			}
	);
	try{
		quizRequest.send();
	}catch (e){
		console.error(e);
	}
}

// 답안 리스트 갱신
function updateAnswerList(answerListEle, quizNum){
	// list 갱신
	const answerListRequest = new XMLHttpRequest();    
	const requestUrl = `${getContextRootPath()}/content/learning/answer/list?quizNum=${quizNum}`;   
	answerListRequest.open("GET",requestUrl);
	answerListRequest.addEventListener(
			"load",
			e=>{
				removeAllChildren(answerListEle);
				const listData = JSON.parse(e.currentTarget.response);
				
				addChildToList(answerListEle,listData,makeAnswerItem);
				if(answerListEle && answerListEle.children[0]){
					selectAnswer(answerListEle.children[0]); // 과목 선택
				}
			}
	);
	try{
		
		answerListRequest.send();
	}catch (e){
		console.error(e);
	}	
}

//답안 선택
function selectAnswer(answerItemEle){
	const answerList = document.getElementById('answer-list');
	
	const childrenList = answerList.children;
	for(let i =0;i<childrenList.length;i++){
		childrenList[i].classList.remove('selected');                
	}
	answerItemEle.classList.add('selected');
}

// 답안 항목 생성
function makeAnswerItem(data){
	const item = document.createElement('li');
	item.setAttribute('data-num',data.answerNum);
	item.setAttribute('data-depth',data.chapterDepth);
	item.innerHTML= `<span class="content">${data.content}</span><input class="correctness" type="checkbox" ${data.correct?"checked":""}> ${makeListItemTools()}`;    
	return item;
}
// 퀴즈 리스트 핸들러
function quizListHandler(e){
    const contextPath = getContextRootPath(); // context path
	const li = e.target.closest('li');
	const num = li.getAttribute('data-num');
	
	if(e.target.classList.contains('btn-delete')){ // 삭제 버튼
		console.log('삭제')           
		const xhr = new XMLHttpRequest();
		xhr.open('DELETE',`${contextPath}/content/learning/quiz/${num}`)
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
		xhr.open('PUT',`${contextPath}/content/learning/quiz/${num}`);     
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
		selectQuiz(li);
//		childrenList = e.currentTarget.children;
//		for(let i =0;i<childrenList.length;i++){
//			childrenList[i].classList.remove('selected');                
//		}
//		li.classList.add('selected');
	}

	e.preventDefault()
}


//답안 리스트 핸들러
function answerListHandler(e){
	const contextPath = getContextRootPath(); // context path
	
	const attributes = rakeAllAttributesThroughPath(e);
	console.dir(attributes);
	const li = e.target.closest('li');
	const num = li.getAttribute('data-num');
	
	if(e.target.classList.contains('btn-delete')){ // 삭제 버튼
		console.log('삭제')           
		const xhr = new XMLHttpRequest();
		xhr.open('DELETE',`${contextPath}/content/learning/answer/${num}`)
		xhr.addEventListener('load',
				e=>{
					
				});
		xhr.send();
		li.parentElement.removeChild(li);
	} else if(e.target.classList.contains('btn-edit')){ // 편집 버튼
		console.log('편집')
		li.querySelector('span.content').setAttribute('contenteditable',true);
		li.querySelector('span.content').focus();
		
	}else if(e.target.classList.contains('btn-save')){ // 저장버튼
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/content/learning/answer/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		const content = li.querySelector('span.content').innerText;
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
		xhr.send(JSON.stringify({"content":content}));
		li.querySelector('span.content').setAttribute('contenteditable',false);
		
	}else if(e.target.classList.contains('correctness')){ // 저장버튼
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/content/learning/answer/${num}`);     
		xhr.addEventListener('load',
				e=>{
					
				});
		console.dir(e.target);
		console.log(e.target.checked);
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")
		// xhr.send(JSON.stringify({"title":title}));
		xhr.send(JSON.stringify({"correctness":e.target.checked}));
		return;
		
	}else if(e.target.tagName ==='IMG'){
		console.log("이미지 클릭");
		imageEditPopup(num);
	}
	else {// 리스트 항목 클릭
		console.log('리스트항목')
		selectAnswer(li);
//		childrenList = e.currentTarget.children;
//		for(let i =0;i<childrenList.length;i++){
//			childrenList[i].classList.remove('selected');                
//		}
//		li.classList.add('selected');
	}
	
	e.preventDefault()
}
