const schedule = require('node-schedule');
const async = require('async');
const db = require('./pool.js');
/*
    schedule.scheduleJob('0 0 0 * * *', async function () { //매 십초 
      console.log("test scheduler!");

      let Query = ` 
    	DELETE FROM reservations 
      WHERE  date(delivery_date) > DATE_FORMAT(NOW(),'%Y.%m.%d')
    `
    ;
    await db.Query(Query,[]);



     // where  date(delivery_date) > date(subdate(now(),interval 1 DAY));
     // INSERT INTO  reservations VALUES(? ,DATE_FORMAT(NOW(),'%Y.%m.%d') );
     

    })

*/