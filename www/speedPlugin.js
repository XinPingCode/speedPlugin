var exec = require('cordova/exec');

exports.coolMethod = function(arg0, success, error) {
    exec(success, error, "speedPlugin", "coolMethod", [arg0]);
};
exports.showNetSpeed = function(arg0, success, error) {
    exec(success, error, "speedPlugin", "showNetSpeed", [arg0]);
};
