package com.example.firebase.database

interface Database {
    data class Books (val name:String,val readed: Boolean)
    data class Movies(val name:String,val seen: Boolean)
    data class Games(val name: String,val played: Boolean)
}