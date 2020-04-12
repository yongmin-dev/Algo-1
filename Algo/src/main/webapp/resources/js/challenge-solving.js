/**
 * 도전과제 풀기 화면 
 */

// init
window.addEventListener("load",(e)=>{
	const submitCodeBtn = document.getElementById('btn-submit-code');
	const codeEditor = document.getElementById('code-editor');

	// 전송 버튼 클릭
	if(submitCodeBtn){
		submitCodeBtn.addEventListener("click",(e)=>{
			const contextPath = e.currentTarget.getAttribute("data-context-path"); 
			const cNum = e.currentTarget.getAttribute("data-cnum");
			console.log("contextPath:",contextPath ); 
			console.log("cNum:",cNum ); 
			var xhr = new XMLHttpRequest();
			xhr.open('POST',contextPath+"/challenge/code/"+cNum); 		
			const formdata = new FormData();
			codeEditor.value= editor.getValue();
			formdata.append('code',codeEditor.value);
			codeEditor.setAttribute('disabled',false);
			submitCodeBtn.setAttribute('disabled',true);
			// 전송 완료 시
			xhr.addEventListener('load',(e)=>{
				console.log(e.currentTarget.response);
				const {result} = JSON.parse(e.currentTarget.response);
				const contextPath = getContextRootPath();
				console.log(result);
				const evtSource = new EventSource(contextPath+'/challenge/result/realtime/'+result.resultNum);
// const evtSource = new

				evtSource.onopen = e=>{

				}
				evtSource.onmessage = function(e) { // sse
				  const resultMsg = document.querySelector('#result-msg');
				  const responseData = JSON.parse(e.data);
				  const {status,resultComment,processingTime,usedMemory} =responseData; 
				  if(status==='S' || status==='F'){ // 성공이나 실패 시
					  resultMsg.innerHTML=`상태 : ${resultComment}(${status})<br>`+
					  `메모리 사용량 : ${usedMemory} byte<br>`+
					  `실행시간 : ${processingTime} ns<br>`;
					  evtSource.close();
					  const afterSubmitEle = document.getElementById('after-submit');
					  const rankingButton = document.createElement('button');
					  rankingButton.innerText = "랭킹보기";
					  rankingButton.addEventListener('click',
					  e=>{
						  location.href=`${contextPath}/ranking/${cNum}/1`;
					  });
					  afterSubmitEle.appendChild(rankingButton );
				  }else {
					resultMsg.innerHTML=responseData.resultComment;
					}
				}
				evtSource.onerror = function(err) {
				  
				};
			});
			//
			xhr.send(formdata);
		});				
	}
	
});
var editor;
window.addEventListener('load',
e=>{
	editor = 
		CodeMirror.fromTextArea(document.getElementById("code-editor"), {
		lineNumbers: true,
		lineWrapping: true,
		foldGutter: true,			
		matchBrackets: true,
// gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
		extraKeys: {"Ctrl-Space": "autocomplete"},
// mode: {name: "javascript", globalVars: true}
		mode: "text/x-java"
// mode: "java"
	});

	if (typeof Promise !== "undefined") {
	  var comp = [
	    ["here", "hither"],
	    ["asynchronous", "nonsynchronous"],
	    ["completion", "achievement", "conclusion", "culmination", "expirations"],
	    ["hinting", "advive", "broach", "imply"],
	    ["function","action"],
	    ["provide", "add", "bring", "give"],
	    ["synonyms", "equivalents"],
	    ["words", "token"],
	    ["each", "every"],
	  ]

  function synonyms(cm, option) {
    return new Promise(function(accept) {
      setTimeout(function() {
        var cursor = cm.getCursor(), line = cm.getLine(cursor.line)
        var start = cursor.ch, end = cursor.ch
        while (start && /\w/.test(line.charAt(start - 1))) --start
        while (end < line.length && /\w/.test(line.charAt(end))) ++end
        var word = line.slice(start, end).toLowerCase()
        for (var i = 0; i < comp.length; i++) if (comp[i].indexOf(word) != -1)
          return accept({list: comp[i],
                         from: CodeMirror.Pos(cursor.line, start),
                         to: CodeMirror.Pos(cursor.line, end)})
        return accept(null)
      }, 100)
    })
  }
	}
    var mac = CodeMirror.keyMap.default == CodeMirror.keyMap.macDefault;
    CodeMirror.keyMap.default[(mac ? "Cmd" : "Ctrl") + "-Space"] = "autocomplete";
});
		
		