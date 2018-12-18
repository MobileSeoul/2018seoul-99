
const jwt = require('jsonwebtoken');
const secretKey = require('../config/secretKey').key;
/*
 Modularize DB Connection
*/

module.exports = {
    // Issue jwt Token
    sign : function(email, user_id) {
        const options = {
            algorithm : "HS256",
            expiresIn : 60 * 60 * 24 * 30 //30 days
        };
        //token에 넣을 값
        const payload = {
            "email" : email,
            "user_id" : user_id
        };
        
        let token = jwt.sign(payload, secretKey, options);
        return token;
    },
    // Check jwt
    verify : function(token) {
        let decoded;
        try {
            decoded = jwt.verify(token, secretKey);
            if(!decoded) {
                return -1;
            }else {
                return decoded;
            }
        }
        catch(err) {
            if(err.message === 'jwt expired') console.log('expired token');
            else if(err.message === 'invalid token') console.log('invalid token');
        }
    }
};
