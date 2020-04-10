/**
 * 퀴즈풀기
 */
window.addEventListener('load',e=>{
	const quizList = document.getElementById('quiz-list');
	if(quizList){
		quizList.addEventListener('click',e=>{
			const contextPath = getContextRootPath(); // context path
			if(e.target.classList.contains('btn-submit-answer')){ // 삭제 버튼
				
	
				const li = e.target.closest('li');
				const form = e.target.closest('form');
				const formData = new FormData(form);
					
				console.dir(formData.getAll('quiz-answers'));
				const num = li.getAttribute('data-num');
				
				const xhr = new XMLHttpRequest();
				xhr.open('POST',`${contextPath}/learning/quiz/${num}/check/`)
				xhr.addEventListener('load',
		            e=>{
		                console.log(e.target.response);
		                const responseData = JSON.parse(e.target.response);
		                
		                if(responseData.quizResult.status==='t'){
		                	li.classList.add('passed');
		                	if(li.querySelector('.btn-submit-answer')){ // 버튼 없애기
			                	li.querySelector('.btn-submit-answer').remove();
			                	// checkbox 비활성화
			                }
		                }else if(responseData.quizResult.status==='f'){		                	
		                	li.classList.add('failed')
		                	if(li.querySelector('.btn-submit-answer')){ // 
			                	li.querySelector('.btn-submit-answer').innerText="다시 제출";
			                }
		                	myAnswers = formData.getAll('quiz-answers');
//		                	console.log(formData.getAll('quiz-answers'));
		                	for(let i=0;i<myAnswers.length;i++){
		                		const answerItem = document.getElementById(`answer-${myAnswers[i]}`);
		                		answerItem.classList.add('wrong-answer');
		                	}
		                	const correctAnswers = responseData.correctAnswerList;
		                	for(let i=0;i<correctAnswers.length;i++){
		                		const answerItem = document.getElementById(`answer-${correctAnswers[i]}`);
		                		answerItem.classList.add('correct-answer');
		                	}
		                	const solution = li.querySelector('.solution');
		                	solution.innerHTML =  responseData.quiz.solution;
//		                	const answer = document.getElementById('')
		                }
		            });
		        xhr.send(formData);
			}
		})
		
	}
	
});