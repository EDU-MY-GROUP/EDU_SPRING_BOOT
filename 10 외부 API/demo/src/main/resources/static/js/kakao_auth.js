
Kakao.init('823e6296f29c70cb7913c86a770bdcfb')
console.log("Kakao",Kakao)
console.log("isInitialized",Kakao.isInitialized())

console.log("getAccessToken",Kakao.Auth.getAccessToken());

//로그인창 활성화 + 인증 이후 accessToken발급&등록
const kakaoLogin = ()=>{
    if(Kakao.Auth.getAccessToken())
    {
        location.href="/main";
        return ;
    }

    Kakao.Auth.loginForm({
        scope:'profile_image,gender,account_email',

        success : function(response){
            console.log("kakaoLogin_Success",response);
            location.href='/main';
        },
        fail : function(error){
            console.log("kakaoLogin_error",error);
        }
    })

}

//내정보 확인
const myInfo = ()=>{
    if(!Kakao.Auth.getAccessToken())
    {
        location.href='/index';
        return ;
    }

    console.log("myInfo-accessToken",Kakao.Auth.getAccessToken());
    Kakao.API.request({
        url : '/v2/user/me',
        success : function(response){
            console.log("myinfo_resp",response);
            
            createProfileEls(response)
        },
        fail : function(error){
            console.log("myinfo_err",error);
        }
    })
}
//로그아웃
const kakaoLogout = ()=>{

        console.log("kakaoLogout-accessToken",Kakao.Auth.getAccessToken());
        Kakao.Auth.logout(function(){
            console.log("kakaoLogout-accessToken",Kakao.Auth.getAccessToken());

        })

        Kakao.Auth.setAccessToken(null);        ////엑세스토큰 제거

        location.href='/index';
}
//연결끊기
const kakaoUnlink = ()=>{

       Kakao.API.request({
                url: '/v1/user/unlink',
                success : function(response){
                    console.log("kakaoUnlink",response);
                    Kakao.Auth.setAccessToken(null);    //엑세스토큰 제거
                    location.href='/index';
                },
                fail : function(error){
                    console.log("kakaoUnlink",error);
                }
       })
}




const createProfileEls = (response)=>{
    const profile_img = response.kakao_account.profile.profile_image_url;
    const email= response.kakao_account.email;
    const gender = response.kakao_account.gender;

    $('.main_body').children().remove()

     $('.main_body').append(
        `
        <div class='profile_contents'>
            <div>
                <img src=${profile_img} class="profile_img" alt='' >
            </div>
            <div>
                <label>Email</label>
                <span>${email}</span><br>
            </div>        
            <div>
                <label>Gender</label>
                <span>${gender}</span><br>
            </div>   
        </div>    
        `
     )

}

