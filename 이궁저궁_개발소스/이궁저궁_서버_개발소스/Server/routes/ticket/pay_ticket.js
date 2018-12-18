
const express = require('express');
const router = express.Router();
const crypto = require('crypto-promise');
const async = require('async');
const bodyParser = require('body-parser');
const moment = require('moment');
const db = require('../../module/pool.js');
const jwt = require('../../module/jwt.js');
const secretKey = require('../../config/secretKey').key;

router.post('/', async (req, res, next) => {
    const chkToken = jwt.verify(req.headers.authorization);
    if (chkToken == undefined) {
        return next("10403")
    }
    let user_id = chkToken.user_id;
    console.log(chkToken);
    console.log(user_id);

    let { palace_id, ticket_title, ticket_start, ticket_end, ticket_people, ticket_special, ticket_jongro } = req.body;

    let result;
    let Query =
        `
        INSERT INTO ticket
        VALUES(?,?,?,?,?,?,?,?,?,?,?)
        `
    try {
        result = await db.Query(Query, [user_id, palace_id, null, ticket_title, ticket_start, ticket_end, ticket_people, null, ticket_special, 0, ticket_jongro]);

        } catch (error) {
        return next(error);
    }


    return res.r(result);
});




module.exports = router;
