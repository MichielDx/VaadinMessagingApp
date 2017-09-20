require('app-module-path').addPath(`${__dirname}/src`);

global.R = require('ramda');

const Config = {
  port: 9003
};

global.Config = Config;

const Logger = require('winston');
const Express = require('express');
const App = Express();

/**
 * Middleware
 */
const Cors = require('cors');
App.use(Cors());

const BodyParser = require('body-parser');
App.use(BodyParser.json());

/**
 * Routes
 */
require('routes/ConversationRoutes')(App);
require('routes/UserRoutes')(App);

/**
 * Start the API
 */
App.listen(Config.port, () => Logger.log('info', 'Authentication API started on port '+Config.port));