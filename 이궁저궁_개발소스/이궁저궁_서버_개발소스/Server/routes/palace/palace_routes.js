/*
 Default module
*/
const express = require('express');
const router = express.Router();


router.use('/palace_view', require('./palace_view'));

module.exports = router;
