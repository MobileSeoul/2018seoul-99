/*
 Declare module
 */
const express = require('express');
const router = express.Router();
const _crypto = require('crypto');
const jwt = require('../../module/jwt.js');
const db = require('../../module/pool.js');
// const upload = require('../../module/multer.js');
const secretKey = require('../../config/secretKey').key;


function encrypt(u_password) {
    const encrypted = _crypto.createHmac('sha512', secretKey).update(u_password).digest('base64');
    return encrypted;
}

router.get('/', async (req, res, next) => {
    const chkToken = jwt.verify(req.headers.authorization);
    if (chkToken == undefined) {
        return next("10403")
    }
    let user_id = chkToken.user_id;

    let result = [];
    let end_flag = 0;
    let today = new Date();

    // 오늘 날짜를 YYYYMMDD형식으로 변환
    today = today.toISOString().slice(0,10).replace(/-/g,"");

    let getTicketInfoQuery =
        `
        SELECT ticket.ticket_id, palace.palace_name, ticket.ticket_person, ticket.ticket_title, ticket.ticket_flag, ticket.ticket_start,
               ticket.ticket_end, ticket.ticket_review
        FROM palace, ticket
        WHERE ticket.user_id = ? and palace.palace_id = ticket.palace_id
        `;
    try {
        let getTicketInfoResult = await db.Query(getTicketInfoQuery, [user_id]);

        for(let i=0; i<getTicketInfoResult.length; i++){

          let tmp_result = {};
          tmp_result.ticket_id = getTicketInfoResult[i].ticket_id;
          tmp_result.palace_name = getTicketInfoResult[i].palace_name;
          tmp_result.ticket_people = getTicketInfoResult[i].ticket_person;
          tmp_result.ticket_title = getTicketInfoResult[i].ticket_title;
          tmp_result.ticket_flag = getTicketInfoResult[i].ticket_flag;
          tmp_result.ticket_start = getTicketInfoResult[i].ticket_start.toISOString().slice(0,10).replace(/-/g,"");
          tmp_result.ticket_end = getTicketInfoResult[i].ticket_end.toISOString().slice(0,10).replace(/-/g,"");
          tmp_result.ticket_review = getTicketInfoResult[i].ticket_review;

          if(today < tmp_result.ticket_end) end_flag = 0;
          else end_flag = 1;

          tmp_result.end_flag = end_flag;
          result.push(tmp_result);
        }

    } catch (error) {
        return next(error);
    }

    return res.r(result);
});



module.exports = router;
