package com.example.minorproject

class User (){
    var fname = ""
    var lname = ""
    var uname = ""
    var phone:Double = 0.0
    var password = ""

    constructor(fname:String, lname:String, uname:String, password:String, phone:Double):this()
    {
        this.fname=fname
        this.lname=lname
        this.uname=uname
        this.phone=phone
        this.password=password
    }
}