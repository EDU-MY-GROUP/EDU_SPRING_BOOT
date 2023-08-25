function addr(){
    const myform = document.forms[0];
    new daum.Postcode({
        oncomplete: function (data) {
            console.log('data', data);
            console.log('addressType', data.addressType)
            console.log('jibunAddress', data.jibunAddress)
            console.log('roadAddress', data.roadAddress)
            console.log('zonecode', data.zonecode)
        
            map_kakao(data.roadAddress);
        }
    }).open();
}