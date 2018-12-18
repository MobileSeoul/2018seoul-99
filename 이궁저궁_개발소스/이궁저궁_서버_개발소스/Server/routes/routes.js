/*
 Default module
*/
const express = require('express');
const router = express.Router();

router.use('/user', require('./user/user_routes'));
router.use('/ticket', require('./ticket/ticket_routes'));
router.use('/palace', require('./palace/palace_routes.js'));

module.exports = router;
