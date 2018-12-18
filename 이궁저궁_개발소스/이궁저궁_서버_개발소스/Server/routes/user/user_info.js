/*
 Method : POST
 */
// Written By 서연
// 카카오 로그인 사용자 정보 가져오기
const express = require('express');
const router = express.Router();
const crypto = require('crypto-promise');
const async = require('async');
const bodyParser = require('body-parser');
const moment = require('moment');
const db = require('../../module/pool.js');
const jwt = require('../../module/jwt.js');
const request = require('request-promise');

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: false }));

router.get('/', async(req, res, next) => {
    console.log(req.body.accessToken);

})

router.post('/', async(req, res, next) => {
  // 카카오톡 access token

  let accessToken = req.body.accessToken;

    if(!accessToken){
     return next("401");
    }

  let option = {
    method : 'GET',
    uri: 'https://kapi.kakao.com/v2/user/me',
    json : true,
    headers : {
      'authorization': "Bearer " +  accessToken
    }
  }
  try {
      //여기까지가 클라이언트한테 accessToken 받고서 그걸로 카카오 API 요청 하는부분
    let kakaoResult = await request(option);
    let result = {};
    console.log("==================================");
    console.log(kakaoResult);
    console.log("==================================");

    result.thumbnail_image = kakaoResult.properties.thumbnail_image;
    result.age_range = kakaoResult.kakao_account.age_range;

    var user_id = kakaoResult.id;
    var user_img = kakaoResult.properties.thumbnail_image;
    var user_age = kakaoResult.kakao_account.age_range;
    var user_email = kakaoResult.kakao_account.email;
    var token;
    var chkToken;

    console.log("jwt token here ======================");
    console.log(jwt.sign(user_email, user_id));
    console.log("==============================");
    if(req.headers.authorization != undefined){
      chkToken = jwt.verify(req.headers.authorization);
    }

    let checkEmailQuery =
    `
    SELECT * FROM user
    WHERE user_email = ?
    `;

    let insertQuery =
    `
    INSERT INTO user (user_id, user_age, user_img, user_email)
    VALUES (?, ?, ?, ?);
    `;

    if(chkToken != undefined){ // 토큰이 이미 있는 경우 (로그인 되어있는 경우)
      console.log("토큰이 있습니다");
      if(chkToken.email == user_email){
        console.log("성공적으로 로그인 되었습니다");
        token = jwt.sign(user_email, user_id);
        console.log(token);
        res.status(200).send({
            result : {
            message : "success",
            token : token
            }
        });
      } else { // 토큰이 만료된 경우 재발급
        console.log("기간이 만료되었습니다. 재발급 합니다");
        token = jwt.sign(user_email, user_id);
        res.status(200).send({
            "result" : {
            message : "your token ended and reissue new token",
            token : token
            }
          });
        }
    }
    else { // 토큰이 없는 경우
       let checkEmail = await db.Query(checkEmailQuery,[user_email]);

        if(checkEmail.length != 0){ // 기기를 변경했을 경우
            console.log("다른기기에서 접속했습니다");
            token = jwt.sign(user_email, checkEmail[0].user_id);
            res.status(200).send({
                "result" : {
                    message : "new device login",
                    token : token
                }
            });
        } else{ // 다른 기기이고 회원이 아닐때
            console.log("비회원입니다.");
            let insertResult = await db.Query(insertQuery,[user_id, user_age, user_img ,user_email]);

            if(!insertResult){
                return next("500");
              }
            token = jwt.sign(user_email, insertResult[0].user_id);

            res.status(200).send({
                "result" : {
                    message : "sign up success",
                    token : token
                    }
                })
            }
        }
    }
    catch(err) {
         console.log("kakao Error => " + err);
        next(err);
    }
    finally {
        console.log('finally');
    }
});

module.exports = router;
