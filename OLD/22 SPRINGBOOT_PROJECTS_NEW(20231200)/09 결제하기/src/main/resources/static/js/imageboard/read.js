

const previewImageEls = document.querySelectorAll('#preview img');
const mainImageEl = document.querySelector('.upload-box img');

previewImageEls.forEach(previewImageEl=>{
    previewImageEl.addEventListener('click',function(){
       mainImageEl.src = this.src;
    })
})
