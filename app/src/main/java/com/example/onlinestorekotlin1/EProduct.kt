package com.example.onlinestorekotlin1

class EProduct {

    var id: Int
    var name: String
    var price: Int
    var pictureName: String

    //constructor to initialize
    constructor(id: Int, name: String, price: Int, picture: String){

        //this bcoz name of var and arg is same
        this.id = id
        this.name = name
        this.price = price
        this.pictureName = picture

    }

}