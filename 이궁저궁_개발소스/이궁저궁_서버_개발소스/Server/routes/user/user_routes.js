/*
 Default module
*/
const express = require('express');
const router = express.Router();

 router.use('/user_info', require('./user_info'));

module.exports = router;
