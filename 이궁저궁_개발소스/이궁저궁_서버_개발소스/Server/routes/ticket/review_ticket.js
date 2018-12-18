
const express = require('express');
const router = express.Router();
const crypto = require('crypto-promise');
const async = require('async');
const bodyParser = require('body-parser');
const moment = require('moment');
const db = require('../../module/pool.js');
const jwt = require('../../module/jwt.js');


//후기작성
//POST
router.post('/', async (req, res, next) => {
    let { palace_id, review_traffic, review_crowd, review_attraction, ticket_id } = req.body;
    let {user_id} = req.header;
    let result;
    let review_total = ((parseInt(review_traffic) + parseInt(review_crowd) + parseInt(review_attraction))/3).toPrecision(2);
    
    let Query = 
        ` 
        INSERT INTO review(palace_id, review_total, review_traffic, review_crowd, review_attraction, ticket_id)
        VALUES(?,?,?,?,?,?)
        `
    try {
        result = await db.Query(Query, [palace_id, review_total, review_traffic, review_crowd, review_attraction, ticket_id]);    
        let ticketQuery = ` 
               UPDATE goongs.ticket SET ticket_review = '1'
               WHERE (ticket_id = ?);
             `
        
        await db.Query(ticketQuery, [ticket_id]);
        } catch (error) {
        return next(error);
    }        
    return res.r(result);
});

//후기조회
//GET
router.get('/:palace_id', async (req, res,next) => {
    console.log(req.params.palace_id);

    let selectQuery =
        `
        SELECT avg(review_total) AS total, avg(review_traffic) AS traffic, avg(review_crowd) AS crowd, avg(review_attraction) AS attraction
        FROM review
        WHERE palace_id = ?
        `;

    let result = {}
    let selectResult ={};
    try {
        selectResult = await db.Query(selectQuery, [req.params.palace_id]);
        
        result.total = selectResult[0].total;

        if(selectResult[0].traffic.toPrecision(2) < 5){
            result.traffic = 0;    
        } else if(selectResult[0].traffic.toPrecision(2) < 10){
            result.traffic = 5;    
        } else if(selectResult[0].traffic.toPrecision(2) >= 10){
            result.traffic = 10;    
        } 

        if(selectResult[0].crowd.toPrecision(2) < 5){
            result.crowd = 0;    
        } else if(selectResult[0].crowd.toPrecision(2) < 10){
            result.crowd = 5;    
        } else if(selectResult[0].crowd.toPrecision(2) >= 10){
            result.crowd = 10;
        }

        if(selectResult[0].attraction.toPrecision(2) < 5){
            result.attraction = 0;    
        } else if(selectResult[0].attraction.toPrecision(2) < 10){
            result.attraction = 5;    
        } else if(selectResult[0].attraction.toPrecision(2) >= 10){
            result.attraction = 10;
        }
    } catch (error) {
        return next(error)
    }
    return res.r(result);
});



module.exports = router;
  