const Mongoose = require('mongoose');
const model = function (name, schema, options = {versionKey: false}) {
    let schem = new Mongoose.Schema(schema, options);

    /*schem.method('toClient', function () {
        var obj = this.toObject();

        //Rename fields
        obj.id = obj._id;
        delete obj._id;

        return obj;
    });*/

    return Mongoose.model(name, schem, name);
};
//(name, schema, options = { versionKey: false }) => Mongoose.model(name, new Mongoose.Schema(schema, options),name);

module.exports = {model};