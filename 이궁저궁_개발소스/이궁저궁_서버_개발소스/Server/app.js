/*
 Default module
*/
const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const path = require('path');
const session = require('express-session');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const helmet = require('helmet');
const cors = require('cors');

const scheduler = require('./module/scheduler')
app.use((req, res, next) => {
  res.r = (result) => {
      res.json({
          status: true,
          message: "success",
          result,
      });
  };
  next();
});
/*
 Custom module
*/
const routes = require('./routes/routes');
const jwt = require('./config/secretKey');

/*
 app.set
*/
app.set('views', path.join(__dirname, './views'));
app.set('view engine', 'ejs');
app.set('jwt-secret', jwt.secret);

/*
 app.use
*/
app.use(helmet());
app.use(logger('dev'));
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use('/',routes);
function ignoreFavicon(req, res, next) {
    if (req.originalUrl === '/favicon.ico') {
      res.status(204).json({nope: true});
    } else {
      next();
    }
  }

app.use(ignoreFavicon);

// error handler
require('./ErrorHandler')(app);

app.use(function(req, res, next)
{
  var err = new Error('Not Found');
  err.status = 404;
  err.path = req.path;
  log.error(err);
  next(err);
});

// Error handler
app.use(function(err, req, res, next)
{
  res.locals.message = err.message;
  console.log("res.locals.message error : " + res.locals.message);
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  console.log("res.locals.error error : " + res.locals.error);

  res.status(err.status || 500);
  res.render('error',{errLog : res.locals.error});
});

module.exports = app;