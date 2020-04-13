//window.addEventListener("load",init,false);

window.addEventListener('load',e=>{
	

	 ClassicEditor.create( document.querySelector( '.content_text' ),
			 {toolbar: [ ]}
			 )
    .then( editor => {
            console.log( editor );
            editor.isReadOnly =true;
            
    } )
    .catch( error => {
            console.error( error );
    } );
	
});

