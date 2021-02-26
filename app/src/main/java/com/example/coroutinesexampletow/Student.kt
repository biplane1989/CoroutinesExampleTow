package com.example.coroutinesexampletow


fun Student.getID(): Int {          // extention Funtion
    return id
}

fun Student.setName(newName: String) {
    name = newName
}

data class Student(val id: Int, var name: String) {
}