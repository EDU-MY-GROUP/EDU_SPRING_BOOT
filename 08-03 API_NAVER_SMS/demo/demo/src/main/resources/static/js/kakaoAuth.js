
Kakao.init('324696a0cb3dcbf967e28094b54b57fc'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단

//카카오로그인
function kakaoLogin() {

    console.log('로그아웃 전 엑세스토큰값 : ',Kakao.Auth.getAccessToken());
    if (Kakao.Auth.getAccessToken())
    {
        console.log('로그인 된 상태');
        myinfo();

    }
    Kakao.Auth.login
    ({
    		scope:'profile_nickname,profile_image, account_email, gender',
    		success: function (response)
    		{
                //console.log('인증성공!',response);
                myinfo();

            },
          fail: function (error) {
            console.log(error);
          }
    })

}
//사용자 정보가져오기
function myinfo()
{
    if (!Kakao.Auth.getAccessToken())
    {
        location.href="/login";

    }
     Kakao.API.request
    ({
                  url: '/v2/user/me',
                  success: function (resp)
                  {
                          console.log('나의 정보! : ', resp);
                          let id = resp.id;
                          let email = resp.kakao_account.email;
                          let gender = resp.kakao_account.gender;
                          let profile_image =resp.kakao_account.profile.profile_image_url;
                          console.log('email',email);
                          console.log('gender',gender);
                          console.log('profile_image',profile_image);

                          let params = 'id='+id;
                          params+= '&email='+email;
                          params+='&gender='+gender;
                          params+='&profile_image='+profile_image;
                          location.href='/main?'+params;

                  },
                  fail : function (error)
                  {
                      console.log(error);
                  }
    })

}
//로그아웃
function kakaologout()
{
    console.log('로그아웃 전 엑세스토큰값 : ',Kakao.Auth.getAccessToken());
    if (!Kakao.Auth.getAccessToken())
    {
        console.log('Not logged in.');
        location.href="/login";
        return ;
    }
    Kakao.Auth.logout(function(){
        console.log('로그아웃 후 엑세스토큰값 : ',Kakao.Auth.getAccessToken());
        location.href="/login";
        return ;
    })
}

//연결끊기
function unlink() {
    if (Kakao.Auth.getAccessToken()) {
            Kakao.API.request({
            url : '/v1/user/unlink',
            success : function(response) {
                console.log(response)
                location.href = "/login"
            },
            fail : function(error) {
                console.log(error)
            },
        })
        Kakao.Auth.setAccessToken(undefined);
    }
}
