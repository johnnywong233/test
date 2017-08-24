var obj = {
    "username": "root",
    "pwd": "root",
    "sh": function () {
        // print();

        //java can not run this function, throw ReferenceError
        // alert("java call js.");

        //not defined
        // s.sayHello();
        return "test";
    }
};
obj;