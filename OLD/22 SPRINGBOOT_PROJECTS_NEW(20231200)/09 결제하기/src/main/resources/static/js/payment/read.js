




const pay_phone_btn = document.querySelector('.pay_phone_btn')
pay_phone_btn.addEventListener('click',function(){
    const productnameEls = document.querySelectorAll('.productname');
    const productname = encodeURIComponent(productnameEls[0].innerHTML + ' 외 '+(productnameEls.length-1)+" 건");

    const price = document.querySelector('.totalprice').innerHTML;
    const buyertel = document.querySelector('.phone').innerHTML;
    const address = document.querySelector('.address').innerHTML;
    const cart_id = document.querySelector('.cart_id').value;
    console.log('productname',productname);
    console.log('cart_id',cart_id);


      const userCode = "imp87380722";
        IMP.init(userCode);
              IMP.request_pay({
                pg: "danal",
                pay_method: "phone",
                merchant_uid: "merchant_"+ new Date().getTime(),
                name: productname,
                amount: price,
                buyer_tel: buyertel,
              }, function (rsp) {

                    if(rsp.success){
                        console.log(rsp);


                        const params  =
                        { params:
                            {
                                imp_uid : rsp.imp_uid,
                                merchant_uid : rsp.merchant_uid,
                                pay_method : rsp.pay_method,
                                name :  productname,
                                price : rsp.paid_amount,
                                status : rsp.status,
                                cart_id : encodeURIComponent(cart_id),
                                address : encodeURIComponent(address),

                            }
                        }
                        //axios로 Cart제거(비동기요청)
                        axios.get("/payment/add", params)
                        .then(response=>{
                            alert("결제완료! 결제 확인페이지로 이동합니다");
                            opener.location.href="/payment/list";
                            window.close();
                        })
                        .catch(error=>{
                            console.log(error)
                        });

                    }else{
                        console.log(rsp);
                        alert("결제실패");
                        window.close();
                    }
                }
            );

})