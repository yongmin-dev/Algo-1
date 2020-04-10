/**
 * 이미지 변경하기 
 */

let subjectImageFiles; // 전송할 파일
window.addEventListener("load",
e=>{
	
	//사진 드래그 앤 드롭
	document.addEventListener("dragover",dragOver,false);
	document.addEventListener("dragleave",dragOver,false);
	document.addEventListener("drop",dropImage,true);
	// 사진 파일 업로드 시 미리보기
	const subjectImageInput = document.getElementById('subject-image-input');
	subjectImageInput.addEventListener('change',changePreview)
	const imageSubmitBtn = document.getElementById('btn-image-submit');
	// 클릭 시 파일 업로드
	if(imageSubmitBtn ){
		imageSubmitBtn.addEventListener('click',uploadImage);
	}
}
);

// 파일 업로드 하기
function uploadImage(e){
	e.stopPropagation();
	e.preventDefault();
	const subjectImageInput = document.getElementById('subject-image-input');
	let files= subjectImageFiles || subjectImageInput.files ;
	if (files.length > 1 || files.length <= 0) {
		alert('이미지 하나를 첨부하세요');
		return;
	}
	const subjectNum = e.target.getAttribute('data-num');
	let reg = /image\/\/*/;
	if (files[0].type.match(reg)) {
		const xhr = new XMLHttpRequest();
		xhr.open("POST",`${getContextRootPath()}/content/learning/subject/image/${subjectNum}`);
		xhr.addEventListener('load',e=>{
			console.log("data");
		});
		const formData = new FormData();
		formData.append('file',files[0]);
		xhr.send(formData);
	}else{
	    alert('이미지가 아닙니다.');
	    return;
	} 
}

// 미리보기 변경
function changePreview(e){
	e.stopPropagation();
	e.preventDefault();
	const imageEle = document.getElementById("subject-image-preview");
	let files = e.target.files || e.dataTransfer.files;
	
	if (files.length > 1) {
	    alert('이미지는 하나만 첨부하세요');
	    return;
	}
	let reg = /image\/\/*/;
	if (files[0].type.match(reg)) {
		imageEle.src = URL.createObjectURL(files[0]);
	}else{
	    alert('이미지가 아닙니다.');
	    return;
	} 
}


//사진 드래그이벤트 이거 없으면 body에 안먹힘
function dragOver(e) {
	e.stopPropagation();
	e.preventDefault();
}
//사진 드롭이벤트발생시 이미지 첨부
function dropImage(e) {
	const subjectImageInput = document.getElementById('subject-image-input');
	const imageEle = document.getElementById("subject-image-preview");

	e.stopPropagation();
	e.preventDefault();
	dragOver(e); //1
	console.log(e.target);
	// e.dataTransfer = e.originalEvent.dataTransfer; //2
	let files = e.target.files || e.dataTransfer.files;
	
	if (files.length > 1) {
	    alert('이미지는 하나만 첨부하세요');
	    return;
	}
	//100kb미만의 이미지만 첨부
	//if(files[0].size > 1024*100){
	//	alert('100kb 미만의 이미지만 첨부 가능합니다');
	//	return;
	//}
	
	let reg = /image\/\/*/;
	if (files[0].type.match(reg)) {
		imageEle.src = URL.createObjectURL(files[0]); // 미리보기 업데이트	
	    subjectImageFiles = files; // input 갱신
	}else{
	    alert('이미지가 아닙니다.');
	    return;
	} 
}  
