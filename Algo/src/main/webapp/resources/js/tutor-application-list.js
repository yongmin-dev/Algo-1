/**
 * 튜터신청서 화면  
 */
window.addEventListener('load',
e=>{
	console.log("init");
	const applicationTable = document.getElementById('application-table');
	if(applicationTable){
		applicationTable.addEventListener('click',tableHandler);
		
	}
});

function tableHandler(e){
    const contextPath = getContextRootPath(); // context path

	const row = e.target.closest('tr');
	const num = row.getAttribute('data-num');
	const target = e.target;
	console.log(row,num);
	if(e.target.classList.contains('btn-reject')){ // 삭제 버튼
		console.log('거부')           
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/admin/application/aproval`)
		xhr.addEventListener('load',
            e=>{
            	console.log('e.response:'+e.response);
            	if(e.target.response==="success"){
					const td = target.closest('td');
					const btnGroup = target.closest('div');
					btnGroup.remove();
					console.log("td"+td);
					td.innerHTML=`<span>거절</span>`;
				}
            });
		const data = {"applicationNum":num, "approval":'r'};
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")

        xhr.send(JSON.stringify(data));
	} else if(e.target.classList.contains('btn-approval')){ // 승인
		const xhr = new XMLHttpRequest();
		xhr.open('PUT',`${contextPath}/admin/application/aproval`)
		xhr.addEventListener('load',
				e=>{
					console.log('e.response:'+e.target.response);
					if(e.target.response==="success"){
						const td = target.closest('td');
						const btnGroup = target.closest('div');
						btnGroup.remove();
						console.log("td"+td);
						td.innerHTML=`<span>승인</span>`;
					}
				});
		const data = {"applicationNum":num, "approval":'a'};
		xhr.setRequestHeader('Content-Type',"application/json;charset=UTF-8")

		xhr.send(JSON.stringify(data));		
	}
	else {
	}

	e.preventDefault()
}