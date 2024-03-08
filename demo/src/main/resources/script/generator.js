// 把var换成const后报错：
// javax.script.ScriptException: <eval>:1:0 Expected an operand but found const
// const username = "test";
// ^ in <eval> at line number 1 at column number 0
var username = "test";

function create() {
    return this.username;
}

//create();