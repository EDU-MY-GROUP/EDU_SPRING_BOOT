		const formData = new FormData();	//폼관련 정보 저장

		const uploadBox_el = document.querySelector('.upload-box');
		//dragenter / dragover /dragleave / drop

		uploadBox_el.addEventListener('dragenter',function(e){
			e.preventDefault();
			console.log("dragenter...");
		});
		uploadBox_el.addEventListener('dragover',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='0.5';
			console.log("dragover...");

		});
		uploadBox_el.addEventListener('dragleave',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='1';
			console.log("dragleave...");

		});

        //----------------------------------------------
        //
        //----------------------------------------------
		uploadBox_el.addEventListener('drop',function(e){
			e.preventDefault();
			console.log("drop...");
			console.log(e);
			console.log(e.dataTransfer);
			console.log(e.dataTransfer.files[0]);

			//유효성체크(이미지만, 하나씩만 , 용량제한 ...)
            // 파일 유형 확인 및 이미지 파일만 처리
            const imageFiles = Array.from(e.dataTransfer.files).filter(file => file.type.startsWith('image/'));
            if (imageFiles.length === 0) {
                    alert("이미지 파일만 가능합니다");
                    return;
            }
            //총이미지의 개수가 5개 이상이면 return(아직..)

			//미리보기
			const file = e.dataTransfer.files[0];
			const reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(event) {
				//PREVIEW BLOCK 에 저장
				const preview = document.getElementById('preview');
				const img = document.createElement('img')
				img.setAttribute('src',event.target.result);
				preview.appendChild(img);

	

			};
			//formData에 저장
			formData.append('files',file);

		});


		const add_product_btn_el = document.querySelector('.add_product_btn');
		add_product_btn_el.addEventListener('click',function(){
			const title =	document.imageform.title.value;
			const content = document.imageform.content.value;

			formData.append('title',title);
			formData.append('content',content);


			axios.post('/imageboard/add',formData,	{headers: {'Content-Type' : 'multipart/form-data' } }   )
			.then(response =>{
				alert("SUCCESS");
				location.href="/imageboard/list";
			})
			.catch(error =>{alert("FAIL");})


		})