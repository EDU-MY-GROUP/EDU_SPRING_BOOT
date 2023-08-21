package com.example.demo.RestfulController;

import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.player.StartResumeUsersPlaybackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

@RestController
@Slf4j
public class C02SpotifyRestController {

    @Value("${spotify.clientId}")       //  import org.springframework.beans.factory.annotation.Value;
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;


    //---------------------------------------
    // 엑세스 토큰 발급받기  Site :  https://developer.spotify.com/documentation/web-api/concepts/access-token
    //---------------------------------------
    @GetMapping("/Spotify/getAccessToken")
    @ResponseBody
    public String getAccessToken() throws Exception {
        

        //  SoituftApi 객체생성(토큰 발급에 필요한 요소 저장)
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        //
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build(); //SpotifyApi 객체를 사용하여 client_credentials 인증 방식의 요청을 생성합니다.
        ClientCredentials clientCredentials = clientCredentialsRequest.execute();   //이전 단계에서 생성한 요청을 실행하고, 액세스 토큰을 받아오는 과정입니다. 이 메서드는 Spotify Web API에 요청을 보내고, 서버로부터 응답을 받습니다.

        //
        String accessToken = clientCredentials.getAccessToken();
        return accessToken;

    }


    //---------------------------------------
    // 음악조회
    //---------------------------------------
    //trackId Test : 7qiZfU4dY1lWllzX7mPBI3
    //https://open.spotify.com/track/6zTbtySCRStJOv5xA4XvRE
    //trackId 6zTbtySCRStJOv5xA4XvRE

    @GetMapping("/Spotify/Search/{trackId}")
    @ResponseBody
    public String Search(@PathVariable String trackId) throws Exception {
        // 액세스 토큰 얻기 (토큰 발급 방식은 다를 수 있음)
           String accessToken = getAccessToken();
        //
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAccessToken(accessToken)
                .build();



        GetTrackRequest trackRequest = spotifyApi.getTrack(trackId).build();
        System.out.println("/Spotify/Search/{trackId} 's result : " + trackRequest.execute());
        return trackRequest.execute().toString();


    }

    //---------------------------------------
    // 음악재생
    //---------------------------------------
    @GetMapping("/Spotify/Play/{trackId}")
    @ResponseBody
    public void PlayMusic(@PathVariable String trackId) throws Exception {
        // 액세스 토큰 얻기 (토큰 발급 방식은 다를 수 있음)
        String accessToken = getAccessToken();
        //
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAccessToken(accessToken)
                .build();

        JsonArray urisArray = new JsonArray();
        urisArray.add( ("spotify:track:" + trackId) );


        StartResumeUsersPlaybackRequest request = spotifyApi.startResumeUsersPlayback()
                .uris(urisArray)
                .build();

        try {
            request.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}


//SPOTIFY 의 주요기능들
//Authorization: Spotify API에 접근하기 위한 사용자 인증 및 권한 부여 기능을 제공합니다. 사용자의 액세스 토큰을 받아와 API 요청에 인증 정보로 사용할 수 있습니다.
//Playback: Spotify 앱과 동기화하여 사용자의 Spotify 계정에서 음악을 재생할 수 있는 기능을 제공합니다. 플레이어 상태를 제어하고, 재생 목록을 관리하며, 음악 재생 위치 등을 제어할 수 있습니다.
//Search: Spotify의 음악 카탈로그에서 검색 기능을 제공합니다. 트랙, 앨범, 아티스트, 재생 목록 등을 검색할 수 있으며, 검색 결과를 받아와서 음악 정보를 조회할 수 있습니다.
//Playlists: 사용자의 플레이리스트를 관리하고, 플레이리스트에 음악을 추가하거나 삭제하는 기능을 제공합니다. 또한, 플레이리스트의 세부 정보를 가져오고 플레이리스트에 저장된 음악 목록을 조회할 수 있습니다.Artists: 아티스트 정보와 관련된 기능을 제공합니다. 특정 아티스트의 프로필, 앨범, 인기 트랙, 관련 아티스트 등의 정보를 가져올 수 있습니다.
//Albums: 앨범 정보와 관련된 기능을 제공합니다. 특정 앨범의 상세 정보, 트랙 목록, 앨범 아트 등을 조회할 수 있습니다.
//Tracks: 트랙 정보와 관련된 기능을 제공합니다. 특정 트랙의 상세 정보, 앨범 정보, 아티스트 정보 등을 조회할 수 있습니다.
//Audio Analysis: 트랙의 오디오 분석 정보를 제공합니다. 비트, 세션, 키, 박자 등의 오디오 특성을 분석하여 제공합니다.
//Audio Features: 트랙의 오디오 특성 정보를 제공합니다. 키, 템포, 에너지, 댄스성 등의 특성을 조회할 수 있습니다.
//User Profile: 사용자의 Spotify 프로필 정보를 가져오고, 사용자의 재생 목록, 팔로워, 팔로잉 목록 등을 조회할 수 있습니다.