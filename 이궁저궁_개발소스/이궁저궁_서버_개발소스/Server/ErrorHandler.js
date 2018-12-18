const expressValidation = require('express-validation');
const errors = require('./errors');
const moment = require('moment');

module.exports = (app) => {
    const error_code = {
        INVALID_PARAMETER: 9401,
        SERVER_ERROR: 500
    };
    
    app.use((err, req, res, next) => {

        // 에러 로그
        log.error(` :  ${req.path}] ` + " [ Status Code: " + err + " : " + errors[err].description + " ] [ Time : " + moment().format('YYYY.MM.DD h:mm:ss a ]') );

        let miss_param = false;
        if (err instanceof expressValidation.ValidationError) {  // 잘못된 파라미터 확인
            miss_param = err.errors.map(error => error.messages.join('. ')).join('\n');
            console.log(`\n\x1b[36m[Miss Params] \u001b[0m \n${miss_param}`);
            
            err = error_code.INVALID_PARAMETER;
        } else if (isNaN(err)) {  // 서버쪽 에러
            err = error_code.SERVER_ERROR;
        }
        
        const response_error = errors[err];
        response_error.miss_param = miss_param ? miss_param : undefined;
        
        return res.status(response_error.status).json(
            response_error
        );
    });
};