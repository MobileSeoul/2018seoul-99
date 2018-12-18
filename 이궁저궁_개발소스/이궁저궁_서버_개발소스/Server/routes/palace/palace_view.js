const express = require('express');
const router = express.Router();
const crypto = require('crypto-promise');
const async = require('async');
const bodyParser = require('body-parser');
const db = require('../../module/pool.js');


router.get('/:palace_id', async (req, res,next) => {

  console.log(req.params.palace_id);

    let getImgQuery =
        `
        SELECT palace.palace_img
        FROM palace
        WHERE palace.palace_id = ?
        `;

    let result = {};
    let images = [];
    let randomImg = [];

    try {
        let imgResult = await db.Query(getImgQuery,req.params.palace_id);

        randomImg = JSON.parse(imgResult[0].palace_img);

        // 배열 요소들 랜덤으로 섞기
        for (var i = randomImg.length - 1; i > 0; i--) {
            var j = Math.floor(Math.random() * (i + 1));
            var temp = randomImg[i];
            randomImg[i] = randomImg[j];
            randomImg[j] = temp;
        }

        // 3장만 골라서 담기
        for (let i = 0; i < 3; i++) {
          images.push(randomImg[i]);
        }

        result = {images};

    } catch (error) {
        return next(error)
    }
    return res.r(result);
});



module.exports = router;
